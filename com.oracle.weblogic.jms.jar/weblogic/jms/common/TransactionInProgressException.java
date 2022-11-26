package weblogic.jms.common;

public final class TransactionInProgressException extends javax.jms.TransactionInProgressException {
   static final long serialVersionUID = -502217799400250711L;

   public TransactionInProgressException(String message) {
      super(message);
   }

   public TransactionInProgressException(String message, String errorCode) {
      super(message, errorCode);
   }

   public TransactionInProgressException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public TransactionInProgressException(String message, String errorCode, Throwable cause) {
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
