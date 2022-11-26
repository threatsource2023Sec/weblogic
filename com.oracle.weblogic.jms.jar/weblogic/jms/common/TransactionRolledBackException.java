package weblogic.jms.common;

public final class TransactionRolledBackException extends javax.jms.TransactionRolledBackException {
   static final long serialVersionUID = -1488845190757826359L;

   public TransactionRolledBackException(String message) {
      super(message);
   }

   public TransactionRolledBackException(String message, String errorCode) {
      super(message, errorCode);
   }

   public TransactionRolledBackException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public TransactionRolledBackException(String message, String errorCode, Throwable cause) {
      super(message, errorCode);
      JMSException.setLinkedException(this, cause);
   }

   public void setLinkedException(Exception cause) {
      super.setLinkedException(cause);
      JMSException.setLinkedException(this, cause);
   }

   public Exception getLinkedException() {
      return JMSException.getLinkedException(this);
   }
}
