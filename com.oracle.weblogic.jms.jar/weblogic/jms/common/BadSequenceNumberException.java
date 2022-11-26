package weblogic.jms.common;

import weblogic.logging.Loggable;

public final class BadSequenceNumberException extends JMSException {
   static final long serialVersionUID = 9127369707336683070L;

   public BadSequenceNumberException(Loggable i18nLog) {
      super(i18nLog);
   }

   /** @deprecated */
   @Deprecated
   public BadSequenceNumberException(String message) {
      super(message);
   }

   public BadSequenceNumberException(Loggable i18nLog, String errorCode) {
      super(i18nLog, errorCode);
   }

   /** @deprecated */
   @Deprecated
   public BadSequenceNumberException(String message, String errorCode) {
      super(message, errorCode);
   }

   public BadSequenceNumberException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage(), cause);
   }

   /** @deprecated */
   @Deprecated
   public BadSequenceNumberException(String message, Throwable cause) {
      super(message, cause);
   }

   public BadSequenceNumberException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog, errorCode, cause);
   }

   /** @deprecated */
   @Deprecated
   public BadSequenceNumberException(String message, String errorCode, Throwable cause) {
      super(message, errorCode, cause);
   }
}
