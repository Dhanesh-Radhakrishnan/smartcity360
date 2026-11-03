package lk.jiat.smartcity360.decorator;

public interface DataStream {
    void write(byte[] data);   // outgoing: meter -> network
    byte[] read();             // incoming: network -> consumer
    String describe();         // shows the active layers
}