package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class FixedStringIdentifierGenerationStrategy implements IdentifierGenerationStrategy {
   private final String identifier;

   public FixedStringIdentifierGenerationStrategy(@Nonnull String id) {
      this.identifier = (String)Constraint.isNotNull(id, "identifier may not be null");
   }

   public String generateIdentifier() {
      return this.identifier;
   }

   public String generateIdentifier(boolean xmlSafe) {
      return this.identifier;
   }
}
