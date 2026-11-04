package lk.jiat.smartcity360.interpreter;

import java.util.Map;

public class ConsoleDemo {

    public static void main(String[] args) {
        PolicyEngine engine = PolicyEngine.withDefaultRules();

        show(engine, "Home area, 11pm, 65 dB (too loud at night)",
                Map.of("zone", "HOME", "hour", 23, "noise_db", 65, "parked_minutes", 0));

        show(engine, "Town centre, parked 90 minutes (over 60)",
                Map.of("zone", "TOWN", "hour", 10, "noise_db", 40, "parked_minutes", 90));

        show(engine, "Home area, 2pm, 65 dB (allowed in daytime)",
                Map.of("zone", "HOME", "hour", 14, "noise_db", 65, "parked_minutes", 0));

        // Live rule update: lower the night limit from 60 to 50
        Map<String, Object> quiet =
                Map.of("zone", "HOME", "hour", 23, "noise_db", 55, "parked_minutes", 0);

        show(engine, "55 dB at night BEFORE update (limit 60)", quiet);
        engine.putRule("NightNoise", "zone == HOME AND hour >= 22 AND noise_db > 50");
        show(engine, "55 dB at night AFTER update (limit 50)", quiet);

        System.out.println("\n-- Bad rule --");
        try {
            engine.putRule("Broken", "noise_db >> 5");
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }

    private static void show(PolicyEngine engine, String title, Map<String, Object> ctx) {
        System.out.println("\n== " + title + " ==");
        for (PolicyEngine.Result r : engine.evaluate(ctx)) {
            System.out.println("  " + (r.violated() ? "VIOLATION  " : "ok         ") + r.name());
        }
    }
}