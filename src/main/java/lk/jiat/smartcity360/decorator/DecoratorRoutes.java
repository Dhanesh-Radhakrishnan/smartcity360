package lk.jiat.smartcity360.decorator;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import lk.jiat.smartcity360.PatternRoutes;
import lk.jiat.smartcity360.decorator.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class DecoratorRoutes implements PatternRoutes {

    @Override
    public void register(Javalin app) {
        app.post("/api/decorator/send", ctx -> {
            // build your decorator chain here
            ctx.result("TODO");
        });
    }
}