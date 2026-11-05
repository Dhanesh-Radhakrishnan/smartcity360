package lk.jiat.smartcity360.chain;

import java.util.List;

/** Quick test without the web layer. Uses its own database so it doesn't mix with the web app's data. */
public class ConsoleDemo {
    public static void main(String[] args) {
        System.setProperty("smartcity.db.url",
                "jdbc:mysql://localhost:3306/smartcity360_demo"
                        + "?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");

        ComplaintHandler chain = ComplaintChain.build();
        ComplaintRepository repo = new ComplaintRepository();

        List<Complaint> samples = List.of(
                new Complaint("Nimal Perera", "Flickering street light, Peradeniya Road", Severity.LOW),
                new Complaint("Kumari Silva", "Overflowing waste bins near Kandy market", Severity.HIGH),
                new Complaint("Ruwan Bandara", "Water main burst on Katugastota Road", Severity.CRITICAL)
        );

        for (Complaint c : samples) {
            chain.handle(c);
            repo.save(c);
        }

        System.out.println("Reloaded from MySQL:");
        for (Complaint c : repo.findAll()) {
            System.out.println("#" + c.getId() + " " + c.getCitizenName()
                    + " [" + c.getSeverity() + "] -> " + c.getStatus() + " by " + c.getResolvedBy());
            c.getTrail().forEach(line -> System.out.println("   " + line));
        }
        System.exit(0);   // Hibernate's connection pool keeps the JVM alive otherwise
    }
}