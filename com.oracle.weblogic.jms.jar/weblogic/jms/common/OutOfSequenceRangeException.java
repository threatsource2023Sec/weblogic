package weblogic.jms.common;

import weblogic.logging.Loggable;

public final class OutOfSequenceRangeException extends JMSException {
   static final long serialVersionUID = 2320412392366430684L;

   public OutOfSequenceRangeException(Loggable i18nLog) {
      super(i18nLog);
   }

   /** @deprecated */
   @Deprecated
   public OutOfSequenceRangeException(String message) {
      super(message);
   }

   public OutOfSequenceRangeException(Loggable i18nLog, String errorCode) {
      super(i18nLog, errorCode);
   }

   /** @deprecated */
   @Deprecated
   public OutOfSequenceRangeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public OutOfSequenceRangeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage(), cause);
   }

   /** @deprecated */
   @Deprecated
   public OutOfSequenceRangeException(String message, Throwable cause) {
      super(message, cause);
   }

   public OutOfSequenceRangeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog, errorCode, cause);
   }

   /** @deprecated */
   @Deprecated
   public OutOfSequenceRangeException(String message, String errorCode, Throwable cause) {
      super(message, errorCode, cause);
   }
}
