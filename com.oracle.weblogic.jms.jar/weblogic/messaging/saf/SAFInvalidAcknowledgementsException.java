package weblogic.messaging.saf;

public final class SAFInvalidAcknowledgementsException extends SAFException {
   static final long serialVersionUID = 3756749163539969006L;

   public SAFInvalidAcknowledgementsException(String reason) {
      super(reason);
   }
}
