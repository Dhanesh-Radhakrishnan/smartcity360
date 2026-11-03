package lk.jiat.smartcity360.decorator;


public class EnergyMeterStream implements DataStream {
    private final String meterId;
    private byte[] channel = new byte[0]; // simulates the transmission channel

    public EnergyMeterStream(String meterId) {
        this.meterId = meterId;
    }

    @Override
    public void write(byte[] data) {
        this.channel = data.clone();
    }

    @Override
    public byte[] read() {
        return channel.clone();
    }

    public byte[] rawChannel() {
        return channel.clone();
    }

    @Override
    public String describe() {
        return "EnergyMeterStream[" + meterId + "]";
    }
}