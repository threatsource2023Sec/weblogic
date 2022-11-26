package weblogic.jms.common;

import javax.jms.TransactionInProgressRuntimeException;
import weblogic.logging.Loggable;

public final class WLTransactionInProgressRuntimeException extends TransactionInProgressRuntimeException {
   private static final long serialVersionUID = -2590047671864474907L;

   public WLTransactionInProgressRuntimeException(String message) {
      super(message);
   }

   public WLTransactionInProgressRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLTransactionInProgressRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLTransactionInProgressRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLTransactionInProgressRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLTransactionInProgressRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLTransactionInProgressRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLTransactionInProgressRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
