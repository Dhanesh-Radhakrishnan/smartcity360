package lk.jiat.smartcity360.decorator;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StreamDemo {
    public static void main(String[] args) {
        String reading = "{\"meterId\":\"EM-1001\",\"kwh\":482.7,\"voltage\":230.1,\"ts\":\"2026-11-02T10:00:00Z\"}"
                .repeat(20);

        EnergyMeterStream base = new EnergyMeterStream("EM-1001");

        // 1. Plain stream
        base.write(reading.getBytes(StandardCharsets.UTF_8));
        System.out.println("Plain       : " + base.describe() + " -> " + base.rawChannel().length + " bytes");

        // 2. Compression only
        DataStream s2 = StreamFactory.build(base, false, true, false, null);
        s2.write(reading.getBytes(StandardCharsets.UTF_8));
        System.out.println("Compressed  : " + s2.describe() + " -> " + base.rawChannel().length + " bytes");

        // 3. Full stack
        DataStream s3 = StreamFactory.build(base, true, true, true, StreamFactory.METER_TOKEN);
        s3.write(reading.getBytes(StandardCharsets.UTF_8));
        System.out.println("Full stack  : " + s3.describe());
        System.out.println("On the wire : " + Base64.getEncoder().encodeToString(base.rawChannel()).substring(0, 40) + "...");
        System.out.println("Round trip OK: " + new String(s3.read(), StandardCharsets.UTF_8).equals(reading));

        // 4. Bad token
        try {
            StreamFactory.build(base, true, true, true, "wrong-token").write(new byte[]{1});
        } catch (SecurityException e) {
            System.out.println("Blocked     : " + e.getMessage());
        }
    }
}