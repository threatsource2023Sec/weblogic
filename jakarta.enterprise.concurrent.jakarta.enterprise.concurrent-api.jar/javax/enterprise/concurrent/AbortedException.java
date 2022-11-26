package javax.enterprise.concurrent;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class AbortedException extends ExecutionException implements Serializable {
   private static final long serialVersionUID = -8248124070283019190L;

   public AbortedException() {
   }

   public AbortedException(String message, Throwable cause) {
      super(message, cause);
   }

   public AbortedException(String message) {
      super(message);
   }

   public AbortedException(Throwable cause) {
      super(cause);
   }
}
