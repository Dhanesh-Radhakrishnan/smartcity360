package lk.jiat.smartcity360.chain;


/** Concrete Handler. */
public class LocalStaffHandler extends ComplaintHandler {

    @Override
    protected boolean canHandle(Complaint c) {
        return c.getSeverity() == Severity.LOW;
    }

    @Override
    protected void process(Complaint c) {
        c.addTrail("Action: minor issue fixed on-site by local staff");
    }

    @Override
    protected String role() {
        return "Local Staff";
    }
}
