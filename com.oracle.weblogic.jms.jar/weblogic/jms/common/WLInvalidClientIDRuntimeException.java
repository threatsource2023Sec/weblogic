package weblogic.jms.common;

import javax.jms.InvalidClientIDRuntimeException;
import weblogic.logging.Loggable;

public final class WLInvalidClientIDRuntimeException extends InvalidClientIDRuntimeException {
   private static final long serialVersionUID = 9012653911415609719L;

   public WLInvalidClientIDRuntimeException(String message) {
      super(message);
   }

   public WLInvalidClientIDRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLInvalidClientIDRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLInvalidClientIDRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLInvalidClientIDRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLInvalidClientIDRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLInvalidClientIDRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLInvalidClientIDRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
