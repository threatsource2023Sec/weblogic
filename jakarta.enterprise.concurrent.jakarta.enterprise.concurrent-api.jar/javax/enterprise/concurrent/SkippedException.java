package javax.enterprise.concurrent;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class SkippedException extends ExecutionException implements Serializable {
   private static final long serialVersionUID = 6296866815328432550L;

   public SkippedException() {
   }

   public SkippedException(String message) {
      super(message);
   }

   public SkippedException(String message, Throwable cause) {
      super(message, cause);
   }

   public SkippedException(Throwable cause) {
      super(cause);
   }
}
