package weblogic.jms.common;

public final class InvalidClientIDException extends javax.jms.InvalidClientIDException {
   static final long serialVersionUID = 7405762579928968370L;

   public InvalidClientIDException(String message) {
      super(message);
   }

   public InvalidClientIDException(String message, String errorCode) {
      super(message, errorCode);
   }

   public InvalidClientIDException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public InvalidClientIDException(String message, String errorCode, Throwable cause) {
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
