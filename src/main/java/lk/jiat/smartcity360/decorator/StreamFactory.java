package lk.jiat.smartcity360.decorator;


public class StreamFactory {
    // demo only: load from env/config in real systems
    private static final byte[] AES_KEY = "SmartCity360-Key-32-bytes-long!!".getBytes(); // 32 bytes
    public static final String METER_TOKEN = "meter-secret-token";

    /** Layers are added in order: encryption (inner), compression, authentication (outer). */
    public static DataStream build(EnergyMeterStream base, boolean encrypt, boolean compress,
                                   boolean auth, String suppliedToken) {
        DataStream stream = base;
        if (encrypt)  stream = new EncryptionDecorator(stream, AES_KEY);
        if (compress) stream = new CompressionDecorator(stream);
        if (auth)     stream = new AuthenticationDecorator(stream, METER_TOKEN, suppliedToken);
        return stream;
    }
}