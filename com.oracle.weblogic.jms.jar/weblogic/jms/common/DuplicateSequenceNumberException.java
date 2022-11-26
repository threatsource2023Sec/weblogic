package weblogic.jms.common;

import weblogic.logging.Loggable;

public final class DuplicateSequenceNumberException extends JMSException {
   static final long serialVersionUID = 5131149317928539158L;

   public DuplicateSequenceNumberException(Loggable i18nLog) {
      super(i18nLog);
   }

   /** @deprecated */
   @Deprecated
   public DuplicateSequenceNumberException(String message) {
      super(message);
   }

   public DuplicateSequenceNumberException(Loggable i18nLog, String errorCode) {
      super(i18nLog, errorCode);
   }

   /** @deprecated */
   @Deprecated
   public DuplicateSequenceNumberException(String message, String errorCode) {
      super(message, errorCode);
   }

   public DuplicateSequenceNumberException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage(), cause);
   }

   /** @deprecated */
   @Deprecated
   public DuplicateSequenceNumberException(String message, Throwable cause) {
      super(message, cause);
   }

   public DuplicateSequenceNumberException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog, errorCode, cause);
   }

   /** @deprecated */
   @Deprecated
   public DuplicateSequenceNumberException(String message, String errorCode, Throwable cause) {
      super(message, errorCode, cause);
   }
}
