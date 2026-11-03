package lk.jiat.smartcity360.decorator;

public class AuthenticationDecorator extends StreamDecorator {
    private final String expectedToken;
    private final String suppliedToken;

    public AuthenticationDecorator(DataStream wrapped, String expectedToken, String suppliedToken) {
        super(wrapped);
        this.expectedToken = expectedToken;
        this.suppliedToken = suppliedToken;
    }

    private void authenticate() {
        if (suppliedToken == null || !java.security.MessageDigest.isEqual(
                expectedToken.getBytes(), suppliedToken.getBytes())) {
            throw new SecurityException("Authentication failed: invalid meter token");
        }
    }

    @Override
    public void write(byte[] data) {
        authenticate();
        wrapped.write(data);
    }

    @Override
    public byte[] read() {
        authenticate();
        return wrapped.read();
    }

    @Override
    public String describe() {
        return wrapped.describe() + " + Authentication(Token)";
    }
}