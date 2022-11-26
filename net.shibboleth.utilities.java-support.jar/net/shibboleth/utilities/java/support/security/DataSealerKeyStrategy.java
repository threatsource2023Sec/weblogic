package net.shibboleth.utilities.java.support.security;

import java.security.KeyException;
import javax.annotation.Nonnull;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;

public interface DataSealerKeyStrategy {
   @Nonnull
   Pair getDefaultKey() throws KeyException;

   @Nonnull
   SecretKey getKey(@Nonnull @NotEmpty String var1) throws KeyException;
}
