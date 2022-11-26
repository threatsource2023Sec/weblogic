package org.python.google.common.base;

import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible
public class VerifyException extends RuntimeException {
   public VerifyException() {
   }

   public VerifyException(@Nullable String message) {
      super(message);
   }

   public VerifyException(@Nullable Throwable cause) {
      super(cause);
   }

   public VerifyException(@Nullable String message, @Nullable Throwable cause) {
      super(message, cause);
   }
}
