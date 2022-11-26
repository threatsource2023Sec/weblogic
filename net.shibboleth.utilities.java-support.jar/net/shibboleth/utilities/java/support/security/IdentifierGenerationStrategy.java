package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nonnull;

public interface IdentifierGenerationStrategy {
   @Nonnull
   String generateIdentifier();

   @Nonnull
   String generateIdentifier(boolean var1);
}
