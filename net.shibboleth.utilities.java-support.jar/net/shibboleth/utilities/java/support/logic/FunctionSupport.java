package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import javax.annotation.Nonnull;

public final class FunctionSupport {
   private FunctionSupport() {
   }

   @Nonnull
   public static Function constant(@Nonnull Object target) {
      return Functions.constant(target);
   }
}
