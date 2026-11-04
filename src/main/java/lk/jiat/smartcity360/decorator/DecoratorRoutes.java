package lk.jiat.smartcity360.decorator;

import io.javalin.Javalin;
import lk.jiat.smartcity360.PatternRoutes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

// Only class in this package that touches Javalin.
public class DecoratorRoutes implements PatternRoutes {

    @Override
    public void register(Javalin app) {
        // Query params pick the layers; the request body is the meter reading.
        app.post("/api/decorator/send", ctx -> {
            boolean encrypt  = ctx.queryParamAsClass("encrypt", Boolean.class).getOrDefault(false);
            boolean compress = ctx.queryParamAsClass("compress", Boolean.class).getOrDefault(false);
            boolean auth     = ctx.queryParamAsClass("auth", Boolean.class).getOrDefault(false);
            String token     = ctx.queryParam("token");   // null if missing

            String reading = ctx.body();
            if (reading.isBlank()) {
                ctx.status(400).json(Map.of("error", "Send a meter reading in the request body"));
                return;
            }
            byte[] original = reading.getBytes(StandardCharsets.UTF_8);

            // Fresh meter per request, then wrap it with the chosen layers
            EnergyMeterStream meter = new EnergyMeterStream("EM-1001");
            DataStream stream = StreamFactory.build(meter, encrypt, compress, auth, token);

            try {
                stream.write(original);                       // goes through every layer
                byte[] onWire = meter.rawChannel();           // what travels on the network
                boolean roundTrip = new String(stream.read(), StandardCharsets.UTF_8).equals(reading);

                Map<String, Object> out = new LinkedHashMap<>();
                out.put("layers", stream.describe());
                out.put("originalBytes", original.length);
                out.put("onWireBytes", onWire.length);
                out.put("onWireBase64", Base64.getEncoder().encodeToString(onWire));
                out.put("roundTripOk", roundTrip);
                ctx.json(out);
            } catch (SecurityException e) {                   // thrown by AuthenticationDecorator
                ctx.status(401).json(Map.of("blocked", true, "error", e.getMessage()));
            }
        });
    }
}