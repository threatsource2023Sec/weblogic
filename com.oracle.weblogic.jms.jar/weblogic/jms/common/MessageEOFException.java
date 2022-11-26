package weblogic.jms.common;

public final class MessageEOFException extends javax.jms.MessageEOFException {
   static final long serialVersionUID = -3873139065828167122L;

   public MessageEOFException(String message) {
      super(message);
   }

   public MessageEOFException(String message, String errorCode) {
      super(message, errorCode);
   }

   public MessageEOFException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public MessageEOFException(String message, String errorCode, Throwable cause) {
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
