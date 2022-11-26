package weblogic.jms.common;

import weblogic.logging.Loggable;

public final class InvalidDestinationException extends javax.jms.InvalidDestinationException {
   static final long serialVersionUID = 4336734569809868546L;

   public InvalidDestinationException(String message) {
      super(message);
   }

   public InvalidDestinationException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public InvalidDestinationException(String message, String errorCode) {
      super(message, errorCode);
   }

   public InvalidDestinationException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public InvalidDestinationException(String message, String errorCode, Throwable cause) {
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
