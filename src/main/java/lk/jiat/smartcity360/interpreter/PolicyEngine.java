package lk.jiat.smartcity360.interpreter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Stores rules (name -> text + parsed tree) and evaluates them. Rules can change at runtime. */
public class PolicyEngine {

    public record Result(String name, boolean violated, String error) {}
    private record Rule(String text, Expression tree) {}

    private final Map<String, Rule> rules = new LinkedHashMap<>();

    public static PolicyEngine withDefaultRules() {
        PolicyEngine e = new PolicyEngine();
        e.putRule("NightNoise", "zone == HOME AND hour >= 22 AND noise_db > 60");
        e.putRule("DayNoise", "zone == HOME AND noise_db > 70");
        e.putRule("ParkingLimit", "zone == TOWN AND parked_minutes > 60");
        return e;
    }

    /** Add or replace a rule. Bad syntax throws IllegalArgumentException and nothing is stored. */
    public synchronized void putRule(String name, String text) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Rule name is empty");
        rules.put(name.trim(), new Rule(text.trim(), RuleParser.parse(text)));
    }

    public synchronized boolean removeRule(String name) {
        return rules.remove(name) != null;
    }

    public synchronized List<Map<String, String>> listRules() {
        List<Map<String, String>> out = new ArrayList<>();
        rules.forEach((name, r) -> out.add(Map.of("name", name, "text", r.text())));
        return out;
    }

    public synchronized List<Result> evaluate(Map<String, Object> context) {
        List<Result> out = new ArrayList<>();
        rules.forEach((name, r) -> {
            try {
                out.add(new Result(name, r.tree().interpret(context), null));
            } catch (IllegalArgumentException | ClassCastException e) {
                out.add(new Result(name, false, "Bad or missing input: " + e.getMessage()));
            }
        });
        return out;
    }
}