package weblogic.jms.common;

import javax.jms.MessageNotWriteableRuntimeException;
import weblogic.logging.Loggable;

public final class WLMessageNotWriteableRuntimeException extends MessageNotWriteableRuntimeException {
   private static final long serialVersionUID = -9154393017519801991L;

   public WLMessageNotWriteableRuntimeException(String message) {
      super(message);
   }

   public WLMessageNotWriteableRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLMessageNotWriteableRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLMessageNotWriteableRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLMessageNotWriteableRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLMessageNotWriteableRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLMessageNotWriteableRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLMessageNotWriteableRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
