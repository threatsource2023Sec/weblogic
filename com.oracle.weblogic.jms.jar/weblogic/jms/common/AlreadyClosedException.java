package weblogic.jms.common;

import weblogic.logging.Loggable;

public final class AlreadyClosedException extends javax.jms.IllegalStateException {
   static final long serialVersionUID = 2934913896305192779L;

   public AlreadyClosedException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   /** @deprecated */
   @Deprecated
   public AlreadyClosedException(String message) {
      super(message);
   }

   public AlreadyClosedException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   /** @deprecated */
   @Deprecated
   public AlreadyClosedException(String message, String errorCode) {
      super(message, errorCode);
   }

   public AlreadyClosedException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      JMSException.setLinkedException(this, cause);
   }

   /** @deprecated */
   @Deprecated
   public AlreadyClosedException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public AlreadyClosedException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      JMSException.setLinkedException(this, cause);
   }

   /** @deprecated */
   @Deprecated
   public AlreadyClosedException(String message, String errorCode, Throwable cause) {
      super(message, errorCode);
      JMSException.setLinkedException(this, cause);
   }
}
