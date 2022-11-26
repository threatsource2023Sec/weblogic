package weblogic.messaging.saf;

public final class SAFTransportException extends SAFException {
   static final long serialVersionUID = -8682073983626354233L;

   public SAFTransportException(String reason) {
      super(reason);
   }

   public SAFTransportException(String reason, Throwable thrown) {
      super(reason, thrown);
   }

   public SAFTransportException(String reason, boolean retry, Throwable thrown) {
      super(reason, retry, thrown);
   }
}
