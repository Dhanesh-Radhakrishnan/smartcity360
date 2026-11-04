package lk.jiat.smartcity360.flyweight;

import io.javalin.Javalin;
import lk.jiat.smartcity360.PatternRoutes;

import java.util.Map;

// Only class here that uses Javalin. ctx = the request/response; "ctx -> {...}" runs on each request.
public class FlyweightRoutes implements PatternRoutes {
    private final StreetLightNetwork network = new StreetLightNetwork();

    @Override
    public void register(Javalin app) {
        app.post("/api/flyweight/generate", ctx -> {
            int count = ctx.queryParamAsClass("count", Integer.class).getOrDefault(1000);
            network.generate(count);
            ctx.json(stats());
        });

        app.get("/api/flyweight/stats", ctx -> ctx.json(stats()));

        app.get("/api/flyweight/lights", ctx -> ctx.json(network.first(10)));

        app.post("/api/flyweight/toggle/{id}", ctx -> {
            StreetLight light = network.find(Integer.parseInt(ctx.pathParam("id")));
            if (light == null) {
                ctx.status(404).json(Map.of("error", "Light not found"));
                return;
            }
            light.toggle();
            ctx.json(light);
        });
    }

    private Map<String, Integer> stats() {
        return Map.of("totalLights", network.totalLights(), "typeObjects", network.typeObjects());
    }
}