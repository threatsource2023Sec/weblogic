package weblogic.jms.common;

public final class MessageFormatException extends javax.jms.MessageFormatException {
   static final long serialVersionUID = 3080563260492762026L;

   public MessageFormatException(String message) {
      super(message);
   }

   public MessageFormatException(String message, String errorCode) {
      super(message, errorCode);
   }

   public MessageFormatException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public MessageFormatException(String message, String errorCode, Throwable cause) {
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
