package lk.jiat.smartcity360.composite;

public class Building implements ZoneComponent{
    private final String name;
    private final double baseUsageKwh;
    private boolean powerOn = true;

    public Building(String name, double baseUsageKwh) {
        this.name = name;
        this.baseUsageKwh = baseUsageKwh;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getUsageKwh() {
        return powerOn ? baseUsageKwh : 0.0;
    }

    @Override
    public boolean isPowerOn() {
        return powerOn;
    }

    @Override
    public void shutdownPower() {
        powerOn = false;
    }

    @Override
    public void restorePower() {
        powerOn = true;
    }

    @Override
    public String report(String indent) {
        return String.format("%s- %s | %.1f kWh | %s%n",
                indent, name, getUsageKwh(), powerOn ? "ON" : "OFF");
    }

    @Override
    public ZoneComponent find(String searchName) {
        return name.equalsIgnoreCase(searchName) ? this : null;
    }
}
