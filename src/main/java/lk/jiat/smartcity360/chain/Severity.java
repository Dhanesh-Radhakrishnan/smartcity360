package lk.jiat.smartcity360.chain;

public enum Severity {
    LOW(1), MEDIUM(2), HIGH(3), CRITICAL(4);

    private final int level;

    Severity(int level) { this.level = level; }

    public int getLevel() { return level; }
}