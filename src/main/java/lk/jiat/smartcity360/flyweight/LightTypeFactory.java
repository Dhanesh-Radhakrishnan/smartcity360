package lk.jiat.smartcity360.flyweight;

import java.util.HashMap;
import java.util.Map;

// FLYWEIGHT FACTORY: gives back an existing LightType, or creates it only the first time
public class LightTypeFactory {
    private final Map<String, LightType> pool = new HashMap<>();

    public LightType get(String model, int watts, int lumens) {
        LightType type = pool.get(model);
        if (type == null) {                       // not created yet
            type = new LightType(model, watts, lumens);
            pool.put(model, type);
        }
        return type;                              // same object every later time
    }

    public int poolSize() { return pool.size(); }
}