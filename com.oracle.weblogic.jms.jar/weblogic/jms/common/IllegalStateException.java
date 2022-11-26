package weblogic.jms.common;

import weblogic.logging.Loggable;

public class IllegalStateException extends javax.jms.IllegalStateException {
   static final long serialVersionUID = 2934913896351092779L;

   public IllegalStateException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   /** @deprecated */
   @Deprecated
   public IllegalStateException(String message) {
      super(message);
   }

   public IllegalStateException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   /** @deprecated */
   @Deprecated
   public IllegalStateException(String message, String errorCode) {
      super(message, errorCode);
   }

   public IllegalStateException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      JMSException.setLinkedException(this, cause);
   }

   /** @deprecated */
   @Deprecated
   public IllegalStateException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public IllegalStateException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      JMSException.setLinkedException(this, cause);
   }

   /** @deprecated */
   @Deprecated
   public IllegalStateException(String message, String errorCode, Throwable cause) {
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
