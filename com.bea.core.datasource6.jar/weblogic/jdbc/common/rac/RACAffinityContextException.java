package weblogic.jdbc.common.rac;

public class RACAffinityContextException extends Exception {
   private static final long serialVersionUID = 108202232140482755L;

   public RACAffinityContextException() {
   }

   public RACAffinityContextException(Throwable cause) {
      super(cause);
   }

   public RACAffinityContextException(String message) {
      super(message);
   }
}
