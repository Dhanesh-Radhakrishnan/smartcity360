package lk.jiat.smartcity360.chain;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/** The request object that travels along the chain. */
public class Complaint {
    private static final AtomicInteger COUNTER = new AtomicInteger(1000);
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final int id = COUNTER.incrementAndGet();
    private final String citizenName;
    private final String description;
    private final Severity severity;
    private String status = "SUBMITTED";
    private String resolvedBy = null;
    private final List<String> trail = new ArrayList<>();   // traceability

    public Complaint(String citizenName, String description, Severity severity) {
        this.citizenName = citizenName;
        this.description = description;
        this.severity = severity;
        addTrail("Complaint #" + id + " submitted (" + severity + ")");
    }

    public void addTrail(String message) {
        trail.add("[" + LocalTime.now().format(TIME) + "] " + message);
    }

    public void resolve(String by) {
        this.status = "RESOLVED";
        this.resolvedBy = by;
    }

    public void markUnhandled() { this.status = "UNHANDLED"; }

    public int getId() { return id; }
    public String getCitizenName() { return citizenName; }
    public String getDescription() { return description; }
    public Severity getSeverity() { return severity; }
    public String getStatus() { return status; }
    public String getResolvedBy() { return resolvedBy; }
    public List<String> getTrail() { return trail; }
}
