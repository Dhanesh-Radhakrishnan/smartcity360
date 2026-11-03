package lk.jiat.smartcity360.decorator;


public abstract class StreamDecorator implements DataStream {
    protected final DataStream wrapped;

    protected StreamDecorator(DataStream wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void write(byte[] data) {
        wrapped.write(data);
    }

    @Override
    public byte[] read() {
        return wrapped.read();
    }

    @Override
    public String describe() {
        return wrapped.describe();
    }
}
