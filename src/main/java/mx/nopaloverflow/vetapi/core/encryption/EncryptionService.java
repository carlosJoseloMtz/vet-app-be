package mx.nopaloverflow.vetapi.core.encryption;

public interface EncryptionService {
    String encode(final String plainText);
    boolean isSameContent(final String raw, final String encoded);
}
