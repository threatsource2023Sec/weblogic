package javax.resource.spi.work;

import javax.resource.ResourceException;

public class WorkException extends ResourceException {
   public static final String INTERNAL = "-1";
   public static final String UNDEFINED = "0";
   public static final String START_TIMED_OUT = "1";
   public static final String TX_CONCURRENT_WORK_DISALLOWED = "2";
   public static final String TX_RECREATE_FAILED = "3";

   public WorkException() {
   }

   public WorkException(String message) {
      super(message);
   }

   public WorkException(Throwable cause) {
      super(cause);
   }

   public WorkException(String message, Throwable cause) {
      super(message, cause);
   }

   public WorkException(String message, String errorCode) {
      super(message, errorCode);
   }
}
