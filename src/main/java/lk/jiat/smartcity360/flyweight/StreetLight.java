package lk.jiat.smartcity360.flyweight;

// CONTEXT: unique data of ONE light + a reference to a shared LightType
public class StreetLight {
    private final int id;
    private final String street;
    private final double lat;          // extrinsic: location
    private final double lng;          // extrinsic: location
    private boolean on = false;
    private int brightness = 80;
    private final LightType type;      // shared, not copied

    public StreetLight(int id, String street, double lat, double lng, LightType type) {
        this.id = id;
        this.street = street;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
    }

    public void toggle() { on = !on; }

    public int getId() { return id; }
    public String getStreet() { return street; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public boolean isOn() { return on; }
    public int getBrightness() { return brightness; }
    public LightType getType() { return type; }
    public int getPowerNow() { return type.powerDraw(on, brightness); }
}