package weblogic.jms.extensions;

import weblogic.jms.common.JMSException;

public final class ClientSAFDuplicateException extends JMSException {
   private static final long serialVersionUID = -4895877995099434682L;
   private ClientSAF duplicate;

   public ClientSAFDuplicateException(ClientSAF paramDuplicate) {
      super("A duplicate JMS context was found for this directory");
      this.duplicate = paramDuplicate;
   }

   public ClientSAF getDuplicate() {
      return this.duplicate;
   }
}
