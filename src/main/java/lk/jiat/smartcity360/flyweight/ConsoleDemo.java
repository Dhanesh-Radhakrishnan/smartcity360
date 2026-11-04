package lk.jiat.smartcity360.flyweight;

public class ConsoleDemo {
    public static void main(String[] args) {
        int count = 200_000;

        // 1) WITH Flyweight
        long before = usedHeap();
        StreetLightNetwork with = new StreetLightNetwork();
        with.generate(count);
        long withKb = (usedHeap() - before) / 1024;
        System.out.println("WITH Flyweight    : " + with.totalLights() + " lights, "
                + with.typeObjects() + " LightType objects, ~" + withKb + " KB");

        StreetLight a = with.find(2);
        StreetLight b = with.find(4);
        System.out.println("Light 2 and 4 share the same LightType? " + (a.getType() == b.getType()));
        System.out.println("Light 2 location: " + a.getLat() + ", " + a.getLng() + "  | Light 4 location: "
                + b.getLat() + ", " + b.getLng());
        a.toggle();
        System.out.println("Light 2 power: " + a.getPowerNow() + " W | Light 4 power: " + b.getPowerNow() + " W");

        with = null; // free it before the second test

        // 2) WITHOUT Flyweight
        before = usedHeap();
        StreetLightNetwork without = new StreetLightNetwork();
        without.generateWithoutSharing(count);
        long withoutKb = (usedHeap() - before) / 1024;
        System.out.println("WITHOUT Flyweight : " + without.totalLights() + " lights, "
                + without.typeObjects() + " LightType objects, ~" + withoutKb + " KB");
    }

    private static long usedHeap() {
        System.gc();
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }
}