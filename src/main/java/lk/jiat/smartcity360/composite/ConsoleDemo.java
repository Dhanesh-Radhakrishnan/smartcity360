package lk.jiat.smartcity360.composite;


public class ConsoleDemo {
    public static void main(String[] args) {
        ZoneGroup city = ZoneFactory.buildCity();

        System.out.println("=== FULL CITY REPORT ===");
        System.out.print(city.report(""));

        System.out.println("\n=== SHUTDOWN: Peradeniya (a whole district) ===");
        city.find("Peradeniya").shutdownPower();
        System.out.print(city.report(""));

        System.out.println("\n=== RESTORE: Library (a single building) ===");
        city.find("Library").restorePower();
        System.out.print(city.find("Peradeniya").report(""));

        System.out.println("\n=== SAME COMMAND, DIFFERENT LEVELS ===");
        System.out.printf("Building (Hospital): %.1f kWh%n", city.find("Hospital").getUsageKwh());
        System.out.printf("Street (Matale Rd):  %.1f kWh%n", city.find("Matale Rd").getUsageKwh());
        System.out.printf("City (Kandy):        %.1f kWh%n", city.getUsageKwh());
    }
}