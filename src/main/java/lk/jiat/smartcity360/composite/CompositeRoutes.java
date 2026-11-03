package lk.jiat.smartcity360.composite;

import lk.jiat.smartcity360.PatternRoutes;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class CompositeRoutes implements PatternRoutes {

    private final ZoneGroup city = ZoneFactory.buildCity();

    @Override
    public void register(Javalin app) {

        app.get("/api/composite/report/{name}", ctx -> {
            ZoneComponent zone = lookup(ctx);
            if (zone != null) ctx.result(zone.report(""));
        });

        app.post("/api/composite/shutdown/{name}", ctx -> {
            ZoneComponent zone = lookup(ctx);
            if (zone != null) {
                zone.shutdownPower();
                ctx.result("Power shut down for: " + zone.getName() + "\n\n" + city.report(""));
            }
        });

        app.post("/api/composite/restore/{name}", ctx -> {
            ZoneComponent zone = lookup(ctx);
            if (zone != null) {
                zone.restorePower();
                ctx.result("Power restored for: " + zone.getName() + "\n\n" + city.report(""));
            }
        });
    }

    private ZoneComponent lookup(Context ctx) {
        ZoneComponent zone = city.find(ctx.pathParam("name"));
        if (zone == null) {
            ctx.status(404).result("Zone not found: " + ctx.pathParam("name"));
        }
        return zone;
    }
}