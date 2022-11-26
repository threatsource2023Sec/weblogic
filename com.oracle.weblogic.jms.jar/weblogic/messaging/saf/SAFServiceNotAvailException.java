package weblogic.messaging.saf;

public final class SAFServiceNotAvailException extends SAFException {
   static final long serialVersionUID = 7899557758529986861L;

   public SAFServiceNotAvailException(String reason) {
      super(reason);
   }
}
