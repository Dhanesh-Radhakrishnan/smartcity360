package lk.jiat.smartcity360.decorator;

import java.io.*;
import java.util.zip.*;

public class CompressionDecorator extends StreamDecorator {

    public CompressionDecorator(DataStream wrapped) {
        super(wrapped);
    }

    @Override
    public void write(byte[] data) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPOutputStream gz = new GZIPOutputStream(bos)) {
            gz.write(data);
            gz.finish();
            wrapped.write(bos.toByteArray());
        } catch (IOException e) {
            throw new UncheckedIOException("Compression failed", e);
        }
    }

    @Override
    public byte[] read() {
        try (GZIPInputStream gz = new GZIPInputStream(new ByteArrayInputStream(wrapped.read()))) {
            return gz.readAllBytes();
        } catch (IOException e) {
            throw new UncheckedIOException("Decompression failed", e);
        }
    }

    @Override
    public String describe() {
        return wrapped.describe() + " + Compression(GZIP)";
    }
}
