package mx.nopaloverflow.vetapi.core.encryption.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import mx.nopaloverflow.vetapi.core.encryption.EncryptionService;

public class DefaultEncryptionService implements EncryptionService {
    private int cost = 12;

    @Override
    public String encode(final String plainText) {
        return BCrypt.withDefaults()
                .hashToString(cost,
                        plainText.toCharArray());
    }

    @Override
    public boolean isSameContent(final String raw, final String encoded) {
        final var result = BCrypt.verifyer()
                .verify(raw.toCharArray(),
                        encoded);
        return result.verified;
    }
}
