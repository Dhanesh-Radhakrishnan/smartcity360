package lk.jiat.smartcity360.composite;

import java.util.ArrayList;
import java.util.List;

public class ZoneGroup implements ZoneComponent {

    private final String name;
    private final ZoneLevel level;
    private final List<ZoneComponent> children = new ArrayList<>();

    public ZoneGroup(String name, ZoneLevel level) {
        this.name = name;
        this.level = level;
    }

    public void add(ZoneComponent zone) {
        children.add(zone);
    }

    public void remove(ZoneComponent zone) {
        children.remove(zone);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getUsageKwh() {
        double total = 0;
        for (ZoneComponent child : children) {
            total += child.getUsageKwh();
        }
        return total;
    }

    @Override
    public boolean isPowerOn() {
        for (ZoneComponent child : children) {
            if (child.isPowerOn()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void shutdownPower() {
        for (ZoneComponent child : children) {
            child.shutdownPower();
        }
    }

    @Override
    public void restorePower() {
        for (ZoneComponent child : children) {
            child.restorePower();
        }
    }

    @Override
    public String report(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s+ %s: %s | %.1f kWh | %s%n",
                indent, level, name, getUsageKwh(), isPowerOn() ? "ON" : "OFF"));
        for (ZoneComponent child : children) {
            sb.append(child.report(indent + "    "));
        }
        return sb.toString();
    }

    @Override
    public ZoneComponent find(String searchName) {
        if (name.equalsIgnoreCase(searchName)) {
            return this;
        }
        for (ZoneComponent child : children) {
            ZoneComponent found = child.find(searchName);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}