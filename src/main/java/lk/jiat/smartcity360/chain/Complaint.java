package lk.jiat.smartcity360.chain;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** The request object that travels along the chain. Also a Hibernate entity (table: complaints). */
@Entity
@Table(name = "complaints")
public class Complaint {
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss"); // static = not stored

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // MySQL AUTO_INCREMENT
    private int id;                                       // 0 until saved

    @Column(nullable = false, length = 100)
    private String citizenName;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)                          // stores "HIGH", not 3
    @Column(nullable = false, length = 10)
    private Severity severity;

    @Column(nullable = false, length = 15)
    private String status = "SUBMITTED";

    @Column(length = 50)
    private String resolvedBy;

    // traceability: one row per step, kept in order
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "complaint_trail", joinColumns = @JoinColumn(name = "complaint_id"))
    @OrderColumn(name = "step_no")
    @Column(name = "entry", length = 300)
    private List<String> trail = new ArrayList<>();

    protected Complaint() { }   // required by Hibernate

    public Complaint(String citizenName, String description, Severity severity) {
        this.citizenName = citizenName;
        this.description = description;
        this.severity = severity;
        addTrail("Complaint submitted (" + severity + ")");
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