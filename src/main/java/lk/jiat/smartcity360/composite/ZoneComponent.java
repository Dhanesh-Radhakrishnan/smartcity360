package lk.jiat.smartcity360.composite;

public interface ZoneComponent {
    String getName();
    double getUsageKwh();
    boolean isPowerOn();
    void shutdownPower();
    void restorePower();
    String report(String indent);
    ZoneComponent find(String name);
}
