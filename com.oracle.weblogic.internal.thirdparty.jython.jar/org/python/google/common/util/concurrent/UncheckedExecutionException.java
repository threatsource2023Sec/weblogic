package org.python.google.common.util.concurrent;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public class UncheckedExecutionException extends RuntimeException {
   private static final long serialVersionUID = 0L;

   protected UncheckedExecutionException() {
   }

   protected UncheckedExecutionException(@Nullable String message) {
      super(message);
   }

   public UncheckedExecutionException(@Nullable String message, @Nullable Throwable cause) {
      super(message, cause);
   }

   public UncheckedExecutionException(@Nullable Throwable cause) {
      super(cause);
   }
}
