package lk.jiat.smartcity360.chain;

/** Builds the escalation chain in one place, shared by the web routes and the console demo. */
public final class ComplaintChain {
    private ComplaintChain() { }

    /** Local Staff -> Supervisor -> City Manager. Reorder or add handlers here only. */
    public static ComplaintHandler build() {
        ComplaintHandler first = new LocalStaffHandler();
        first.setNext(new SupervisorHandler()).setNext(new CityManagerHandler());
        return first;
    }
}