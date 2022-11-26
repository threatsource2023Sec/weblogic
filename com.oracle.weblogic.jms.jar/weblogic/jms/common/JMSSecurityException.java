package weblogic.jms.common;

public final class JMSSecurityException extends javax.jms.JMSSecurityException {
   static final long serialVersionUID = -5137046084735113576L;

   public JMSSecurityException(String message) {
      super(message);
   }

   public JMSSecurityException(String message, String errorCode) {
      super(message, errorCode);
   }

   public JMSSecurityException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public JMSSecurityException(String message, String errorCode, Throwable cause) {
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
