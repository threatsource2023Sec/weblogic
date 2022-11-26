package weblogic.jms.common;

public final class ResourceAllocationException extends javax.jms.ResourceAllocationException {
   static final long serialVersionUID = 2419888426222761977L;

   public ResourceAllocationException(String message) {
      super(message);
   }

   public ResourceAllocationException(String message, String errorCode) {
      super(message, errorCode);
   }

   public ResourceAllocationException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public ResourceAllocationException(String message, String errorCode, Throwable cause) {
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
