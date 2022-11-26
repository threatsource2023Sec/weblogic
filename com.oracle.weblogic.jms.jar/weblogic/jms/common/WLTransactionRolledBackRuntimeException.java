package weblogic.jms.common;

import javax.jms.TransactionRolledBackRuntimeException;
import weblogic.logging.Loggable;

public final class WLTransactionRolledBackRuntimeException extends TransactionRolledBackRuntimeException {
   private static final long serialVersionUID = 1912644581031596672L;

   public WLTransactionRolledBackRuntimeException(String message) {
      super(message);
   }

   public WLTransactionRolledBackRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLTransactionRolledBackRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLTransactionRolledBackRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLTransactionRolledBackRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLTransactionRolledBackRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLTransactionRolledBackRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLTransactionRolledBackRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
