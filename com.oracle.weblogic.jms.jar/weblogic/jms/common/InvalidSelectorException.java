package weblogic.jms.common;

public final class InvalidSelectorException extends javax.jms.InvalidSelectorException {
   static final long serialVersionUID = -8059591219768508215L;

   public InvalidSelectorException(String message) {
      super(message);
   }

   public InvalidSelectorException(String message, String errorCode) {
      super(message, errorCode);
   }

   public InvalidSelectorException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public InvalidSelectorException(String message, String errorCode, Throwable cause) {
      super(message, errorCode);
      JMSException.setLinkedException(this, cause);
   }

   public void setLinkedException(Exception cause) {
      JMSException.setLinkedException(this, cause);
   }

   public Exception getLinkedException() {
      return JMSException.getLinkedException(this);
   }
}
