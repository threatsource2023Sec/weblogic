package weblogic.jms.common;

import javax.jms.MessageFormatRuntimeException;
import weblogic.logging.Loggable;

public final class WLMessageFormatRuntimeException extends MessageFormatRuntimeException {
   private static final long serialVersionUID = 3397132690290828826L;

   public WLMessageFormatRuntimeException(String message) {
      super(message);
   }

   public WLMessageFormatRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLMessageFormatRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLMessageFormatRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLMessageFormatRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLMessageFormatRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLMessageFormatRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLMessageFormatRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
