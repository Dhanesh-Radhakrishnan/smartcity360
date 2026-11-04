package lk.jiat.smartcity360.chain;


/** Concrete Handler. */
public class SupervisorHandler extends ComplaintHandler {

    @Override
    protected boolean canHandle(Complaint c) {
        return c.getSeverity().getLevel() <= Severity.HIGH.getLevel();
    }

    @Override
    protected void process(Complaint c) {
        c.addTrail("Action: supervisor assigned a response team");
    }

    @Override
    protected String role() {
        return "Supervisor";
    }
}
