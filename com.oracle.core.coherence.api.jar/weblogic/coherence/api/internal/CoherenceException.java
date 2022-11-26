package weblogic.coherence.api.internal;

public class CoherenceException extends Exception {
   private static final long serialVersionUID = 354496553045662647L;

   public CoherenceException() {
      super("");
   }

   public CoherenceException(String msg) {
      super(msg);
   }

   public CoherenceException(Throwable th) {
      super(th);
   }

   public CoherenceException(String msg, Throwable th) {
      super(msg, th);
   }
}
