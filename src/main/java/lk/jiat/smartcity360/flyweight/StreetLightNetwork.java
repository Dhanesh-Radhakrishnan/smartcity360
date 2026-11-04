package lk.jiat.smartcity360.flyweight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// CLIENT: creates the lights, always asking the factory for the type
public class StreetLightNetwork {
    private final LightTypeFactory factory = new LightTypeFactory();
    private final List<StreetLight> lights = new ArrayList<>();
    private boolean shared = true;

    private static final String[] STREETS = {
            "Peradeniya Road", "Dalada Veediya", "Katugastota Road", "Bahirawakanda Road"};

    // WITH Flyweight: types come from the factory (shared)
    public void generate(int count) {
        build(count, true);
    }

    // WITHOUT Flyweight: a new LightType per light (only for the comparison demo)
    public void generateWithoutSharing(int count) {
        build(count, false);
    }

    private void build(int count, boolean useSharing) {
        lights.clear();
        shared = useSharing;
        Random rnd = new Random(42);
        for (int i = 1; i <= count; i++) {
            String street = STREETS[i % STREETS.length];
            double lat = 7.2906 + rnd.nextDouble() * 0.05;   // around Kandy
            double lng = 80.6337 + rnd.nextDouble() * 0.05;

            boolean ledStreet = (i % 2 == 0);
            LightType type;
            if (useSharing) {
                type = ledStreet ? factory.get("LED-STREET-60W", 60, 7200)
                        : factory.get("SOLAR-LANE-30W", 30, 3600);
            } else {
                type = ledStreet ? new LightType("LED-STREET-60W", 60, 7200)
                        : new LightType("SOLAR-LANE-30W", 30, 3600);
            }
            lights.add(new StreetLight(i, street, lat, lng, type));
        }
    }

    public List<StreetLight> first(int limit) {
        return lights.subList(0, Math.min(limit, lights.size()));
    }

    public StreetLight find(int id) {
        return (id >= 1 && id <= lights.size()) ? lights.get(id - 1) : null;
    }

    public int totalLights() { return lights.size(); }

    public int typeObjects() {
        return shared ? factory.poolSize() : lights.size();
    }
}