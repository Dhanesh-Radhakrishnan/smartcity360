package lk.jiat.smartcity360.chain;


/** Concrete Handler. */
public class CityManagerHandler extends ComplaintHandler {

    @Override
    protected boolean canHandle(Complaint c) {
        return c.getSeverity().getLevel() <= Severity.CRITICAL.getLevel();
    }

    @Override
    protected void process(Complaint c) {
        c.addTrail("Action: city manager launched an emergency response");
    }

    @Override
    protected String role() {
        return "City Manager";
    }
}