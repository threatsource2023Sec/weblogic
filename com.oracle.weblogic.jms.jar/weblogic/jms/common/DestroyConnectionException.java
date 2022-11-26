package weblogic.jms.common;

public final class DestroyConnectionException extends JMSException {
   static final long serialVersionUID = -7415014313387218610L;

   public DestroyConnectionException(String reason) {
      super(reason);
   }
}
