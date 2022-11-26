package weblogic.jms.common;

import javax.jms.InvalidDestinationRuntimeException;
import weblogic.logging.Loggable;

public final class WLInvalidDestinationRuntimeException extends InvalidDestinationRuntimeException {
   private static final long serialVersionUID = -944307892369330783L;

   public WLInvalidDestinationRuntimeException(String message) {
      super(message);
   }

   public WLInvalidDestinationRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLInvalidDestinationRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLInvalidDestinationRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLInvalidDestinationRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLInvalidDestinationRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLInvalidDestinationRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLInvalidDestinationRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
