package net.shibboleth.utilities.java.support.primitive;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class LazilyFormattedString {
   private final String template;
   private final Object[] arguments;

   public LazilyFormattedString(@Nonnull String stringTemplate, @Nullable Object... templateArguments) {
      this.template = (String)Constraint.isNotNull(stringTemplate, "String template can not be null");
      this.arguments = templateArguments;
   }

   public String toString() {
      return String.format(this.template, this.arguments);
   }
}
