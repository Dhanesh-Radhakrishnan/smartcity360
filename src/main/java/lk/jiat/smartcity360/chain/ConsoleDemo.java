package lk.jiat.smartcity360.chain;

import java.util.List;

/** Quick test without the web layer. */
public class ConsoleDemo {
    public static void main(String[] args) {
        ComplaintHandler chain = ComplaintChain.build();

        List<Complaint> samples = List.of(
                new Complaint("Nimal Perera", "Flickering street light, Peradeniya Road", Severity.LOW),
                new Complaint("Kumari Silva", "Overflowing waste bins near Kandy market", Severity.HIGH),
                new Complaint("Ruwan Bandara", "Water main burst on Katugastota Road", Severity.CRITICAL)
        );

        for (Complaint c : samples) {
            chain.handle(c);
            System.out.println("#" + c.getId() + " " + c.getCitizenName()
                    + " [" + c.getSeverity() + "] -> " + c.getStatus() + " by " + c.getResolvedBy());
            c.getTrail().forEach(line -> System.out.println("   " + line));
        }
    }
}