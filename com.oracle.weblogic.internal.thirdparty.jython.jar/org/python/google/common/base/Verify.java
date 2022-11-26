package org.python.google.common.base;

import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible
public final class Verify {
   public static void verify(boolean expression) {
      if (!expression) {
         throw new VerifyException();
      }
   }

   public static void verify(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
      if (!expression) {
         throw new VerifyException(Preconditions.format(errorMessageTemplate, errorMessageArgs));
      }
   }

   @CanIgnoreReturnValue
   public static Object verifyNotNull(@Nullable Object reference) {
      return verifyNotNull(reference, "expected a non-null reference");
   }

   @CanIgnoreReturnValue
   public static Object verifyNotNull(@Nullable Object reference, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
      verify(reference != null, errorMessageTemplate, errorMessageArgs);
      return reference;
   }

   private Verify() {
   }
}
