package weblogic.jms.common;

public final class MessageNotReadableException extends javax.jms.MessageNotReadableException {
   static final long serialVersionUID = 8594232713475130062L;

   public MessageNotReadableException(String message) {
      super(message);
   }

   public MessageNotReadableException(String message, String errorCode) {
      super(message, errorCode);
   }

   public MessageNotReadableException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public MessageNotReadableException(String message, String errorCode, Throwable cause) {
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
