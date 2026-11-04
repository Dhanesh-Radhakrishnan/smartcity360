package lk.jiat.smartcity360;

import lk.jiat.smartcity360.composite.CompositeRoutes;
import lk.jiat.smartcity360.chain.ChainRoutes;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import lk.jiat.smartcity360.decorator.DecoratorRoutes;
import lk.jiat.smartcity360.flyweight.FlyweightRoutes;
import lk.jiat.smartcity360.interpreter.InterpreterRoutes;

import java.util.List;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config ->
                config.staticFiles.add("/public", Location.CLASSPATH)
        ).start(7070);

        List<PatternRoutes> modules = List.of(
                new CompositeRoutes(),
                 new DecoratorRoutes(),
                 new ChainRoutes(),
                new FlyweightRoutes(),
                 new InterpreterRoutes()
        );

        modules.forEach(m -> m.register(app));

        System.out.println("SmartCity360 running at http://localhost:7070");
    }
}