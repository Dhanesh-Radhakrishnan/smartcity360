package lk.jiat.smartcity360.decorator;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

public class EncryptionDecorator extends StreamDecorator {
    private static final int IV_LEN = 12;
    private static final int TAG_BITS = 128;
    private final SecretKeySpec key;
    private final SecureRandom random = new SecureRandom();

    /** @param secret 16, 24 or 32 byte AES key */
    public EncryptionDecorator(DataStream wrapped, byte[] secret) {
        super(wrapped);
        this.key = new SecretKeySpec(secret, "AES");
    }

    @Override
    public void write(byte[] data) {
        try {
            byte[] iv = new byte[IV_LEN];
            random.nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_BITS, iv));
            byte[] enc = cipher.doFinal(data);
            wrapped.write(ByteBuffer.allocate(iv.length + enc.length).put(iv).put(enc).array());
        } catch (Exception e) {
            throw new IllegalStateException("Encryption failed", e);
        }
    }

    @Override
    public byte[] read() {
        try {
            byte[] all = wrapped.read();
            ByteBuffer buf = ByteBuffer.wrap(all);
            byte[] iv = new byte[IV_LEN];
            buf.get(iv);
            byte[] enc = new byte[buf.remaining()];
            buf.get(enc);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_BITS, iv));
            return cipher.doFinal(enc);
        } catch (Exception e) {
            throw new IllegalStateException("Decryption failed (wrong key or tampered data)", e);
        }
    }

    @Override
    public String describe() {
        return wrapped.describe() + " + Encryption(AES-256-GCM)";
    }
}