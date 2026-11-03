package lk.jiat.smartcity360.composite;

public class ZoneFactory {

    public static ZoneGroup buildCity() {
        ZoneGroup city = new ZoneGroup("Kandy", ZoneLevel.CITY);

        // District 1
        ZoneGroup peradeniya = new ZoneGroup("Peradeniya", ZoneLevel.DISTRICT);
        ZoneGroup universityRd = new ZoneGroup("University Rd", ZoneLevel.STREET);
        universityRd.add(new Building("Library", 120.5));
        universityRd.add(new Building("Science Block", 340.0));
        ZoneGroup stationRd = new ZoneGroup("Station Rd", ZoneLevel.STREET);
        stationRd.add(new Building("Post Office", 45.0));
        peradeniya.add(universityRd);
        peradeniya.add(stationRd);

        // District 2
        ZoneGroup katugastota = new ZoneGroup("Katugastota", ZoneLevel.DISTRICT);
        ZoneGroup mataleRd = new ZoneGroup("Matale Rd", ZoneLevel.STREET);
        mataleRd.add(new Building("Hospital", 800.0));
        mataleRd.add(new Building("Market", 210.0));
        katugastota.add(mataleRd);

        city.add(peradeniya);
        city.add(katugastota);
        return city;
    }
}