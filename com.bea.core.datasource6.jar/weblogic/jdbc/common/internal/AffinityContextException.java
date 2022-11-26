package weblogic.jdbc.common.internal;

public class AffinityContextException extends Exception {
   private static final long serialVersionUID = 1482230800104705073L;

   public AffinityContextException() {
   }

   public AffinityContextException(Throwable cause) {
      super(cause);
   }

   public AffinityContextException(String message) {
      super(message);
   }
}
