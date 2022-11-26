package weblogic.jms.common;

public final class MessageNotWriteableException extends javax.jms.MessageNotWriteableException {
   static final long serialVersionUID = -2186712139636772241L;

   public MessageNotWriteableException(String message) {
      super(message);
   }

   public MessageNotWriteableException(String message, String errorCode) {
      super(message, errorCode);
   }

   public MessageNotWriteableException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public MessageNotWriteableException(String message, String errorCode, Throwable cause) {
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
