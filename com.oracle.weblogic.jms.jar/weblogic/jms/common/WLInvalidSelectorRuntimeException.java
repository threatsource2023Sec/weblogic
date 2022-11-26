package weblogic.jms.common;

import javax.jms.InvalidSelectorRuntimeException;
import weblogic.logging.Loggable;

public final class WLInvalidSelectorRuntimeException extends InvalidSelectorRuntimeException {
   public WLInvalidSelectorRuntimeException(String message) {
      super(message);
   }

   public WLInvalidSelectorRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLInvalidSelectorRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLInvalidSelectorRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLInvalidSelectorRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLInvalidSelectorRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLInvalidSelectorRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLInvalidSelectorRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
