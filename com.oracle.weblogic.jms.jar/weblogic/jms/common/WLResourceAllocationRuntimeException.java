package weblogic.jms.common;

import javax.jms.ResourceAllocationRuntimeException;
import weblogic.logging.Loggable;

public final class WLResourceAllocationRuntimeException extends ResourceAllocationRuntimeException {
   private static final long serialVersionUID = 8486601030768814713L;

   public WLResourceAllocationRuntimeException(String message) {
      super(message);
   }

   public WLResourceAllocationRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLResourceAllocationRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLResourceAllocationRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLResourceAllocationRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLResourceAllocationRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLResourceAllocationRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLResourceAllocationRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
