package org.python.google.common.collect;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public class ComputationException extends RuntimeException {
   private static final long serialVersionUID = 0L;

   public ComputationException(@Nullable Throwable cause) {
      super(cause);
   }
}
