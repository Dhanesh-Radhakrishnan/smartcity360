package lk.jiat.smartcity360.interpreter;

import io.javalin.Javalin;
import lk.jiat.smartcity360.PatternRoutes;

import java.util.Map;

public class InterpreterRoutes implements PatternRoutes {

    public record RuleRequest(String name, String text) {}
    public record EvalRequest(Map<String, Object> context) {}

    private final PolicyEngine engine = PolicyEngine.withDefaultRules();

    @Override
    public void register(Javalin app) {
        app.get("/api/interpreter/rules", ctx -> ctx.json(engine.listRules()));

        app.post("/api/interpreter/rules", ctx -> {
            RuleRequest req = ctx.bodyAsClass(RuleRequest.class);
            try {
                engine.putRule(req.name(), req.text());
                ctx.json(engine.listRules());
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("error", e.getMessage()));
            }
        });

        app.delete("/api/interpreter/rules/{name}", ctx -> {
            engine.removeRule(ctx.pathParam("name"));
            ctx.json(engine.listRules());
        });

        app.post("/api/interpreter/evaluate", ctx -> {
            EvalRequest req = ctx.bodyAsClass(EvalRequest.class);
            ctx.json(engine.evaluate(req.context()));
        });
    }
}