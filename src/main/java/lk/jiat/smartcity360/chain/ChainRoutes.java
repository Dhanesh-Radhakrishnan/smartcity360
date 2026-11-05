package lk.jiat.smartcity360.chain;

import io.javalin.Javalin;
import lk.jiat.smartcity360.HibernateUtil;
import lk.jiat.smartcity360.PatternRoutes;

import java.util.Map;

/** The only class in this package that touches Javalin. */
public class ChainRoutes implements PatternRoutes {

    /** JSON body sent by chain.html (public fields so Jackson can fill them). */
    public static class ComplaintRequest {
        public String citizenName;
        public String description;
        public String severity;
    }

    private final ComplaintHandler chain = ComplaintChain.build();
    private final ComplaintRepository repository = new ComplaintRepository();

    @Override
    public void register(Javalin app) {
        HibernateUtil.getSessionFactory();   // connect at startup: fails fast if MySQL/config is wrong

        // Submit a complaint: run the chain first, then save the finished complaint
        app.post("/api/chain/complaints", ctx -> {
            ComplaintRequest req = ctx.bodyAsClass(ComplaintRequest.class);
            if (req.citizenName == null || req.citizenName.isBlank()
                    || req.description == null || req.description.isBlank()
                    || req.severity == null) {
                ctx.status(400).json(Map.of("error", "Name, description and severity are required"));
                return;
            }
            Severity severity;
            try {
                severity = Severity.valueOf(req.severity.toUpperCase());
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("error", "Severity must be LOW, MEDIUM, HIGH or CRITICAL"));
                return;
            }
            Complaint complaint = new Complaint(req.citizenName.trim(), req.description.trim(), severity);
            chain.handle(complaint);       // Chain of Responsibility does its job
            repository.save(complaint);    // then Hibernate stores the result
            ctx.status(201).json(complaint);
        });

        // List all complaints (from MySQL) with their escalation trail
        app.get("/api/chain/complaints", ctx -> ctx.json(repository.findAll()));
    }
}