package lk.jiat.smartcity360;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.Map;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
        }).start(7070);

        app.get("/api/health", ctx ->
                ctx.json(Map.of("system", "SmartCity360", "status", "running")));

        // One endpoint per part. Wire your pattern code in here.
        app.get("/api/a/secure-stream", ctx ->
                ctx.json(Map.of("part", "A", "pattern", "Decorator", "result", "TODO")));
        app.get("/api/b/zones", ctx ->
                ctx.json(Map.of("part", "B", "pattern", "Composite", "result", "TODO")));
        app.post("/api/c/complaint", ctx ->
                ctx.json(Map.of("part", "C", "pattern", "Chain of Responsibility", "result", "TODO")));
        app.get("/api/d/streetlights", ctx ->
                ctx.json(Map.of("part", "D", "pattern", "Flyweight", "result", "TODO")));
        app.post("/api/e/policy", ctx ->
                ctx.json(Map.of("part", "E", "pattern", "Interpreter", "result", "TODO")));

        System.out.println("SmartCity360 running at http://localhost:7070");
    }
}