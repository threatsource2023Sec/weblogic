package net.shibboleth.utilities.java.support.security;

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class Type4UUIDIdentifierGenerationStrategy implements IdentifierGenerationStrategy {
   @Nonnull
   public String generateIdentifier() {
      return this.generateIdentifier(true);
   }

   public String generateIdentifier(boolean xmlSafe) {
      return xmlSafe ? "_" + UUID.randomUUID().toString() : UUID.randomUUID().toString();
   }
}
