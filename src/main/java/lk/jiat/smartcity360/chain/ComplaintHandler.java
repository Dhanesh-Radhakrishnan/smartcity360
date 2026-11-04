package lk.jiat.smartcity360.chain;


/** Abstract Handler: holds the link to the next handler and defines the escalation flow. */
public abstract class ComplaintHandler {
    private ComplaintHandler next;

    /** Returns the given handler so calls can be chained fluently. */
    public ComplaintHandler setNext(ComplaintHandler next) {
        this.next = next;
        return next;
    }

    /** Template method: handle it here, or escalate to the next handler. */
    public final void handle(Complaint complaint) {
        if (canHandle(complaint)) {
            complaint.addTrail(role() + " handled the complaint");
            complaint.resolve(role());
            process(complaint);
        } else if (next != null) {
            complaint.addTrail(role() + " escalated to " + next.role());
            next.handle(complaint);
        } else {
            complaint.addTrail(role() + ": end of chain, no one could handle this");
            complaint.markUnhandled();
        }
    }

    protected abstract boolean canHandle(Complaint complaint);

    protected abstract void process(Complaint complaint);

    protected abstract String role();
}