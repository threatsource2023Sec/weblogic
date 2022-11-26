package org.python.google.common.util.concurrent;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public class ExecutionError extends Error {
   private static final long serialVersionUID = 0L;

   protected ExecutionError() {
   }

   protected ExecutionError(@Nullable String message) {
      super(message);
   }

   public ExecutionError(@Nullable String message, @Nullable Error cause) {
      super(message, cause);
   }

   public ExecutionError(@Nullable Error cause) {
      super(cause);
   }
}
