package weblogic.jms.common;

import javax.jms.IllegalStateRuntimeException;
import weblogic.logging.Loggable;

public class WLIllegalStateRuntimeException extends IllegalStateRuntimeException {
   private static final long serialVersionUID = 1029742106941261075L;

   public WLIllegalStateRuntimeException(String message) {
      super(message);
   }

   public WLIllegalStateRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLIllegalStateRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLIllegalStateRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLIllegalStateRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLIllegalStateRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLIllegalStateRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLIllegalStateRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
