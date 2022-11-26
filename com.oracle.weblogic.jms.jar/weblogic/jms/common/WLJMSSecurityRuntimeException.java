package weblogic.jms.common;

import javax.jms.JMSSecurityRuntimeException;
import weblogic.logging.Loggable;

public final class WLJMSSecurityRuntimeException extends JMSSecurityRuntimeException {
   private static final long serialVersionUID = 7858911966669654455L;

   public WLJMSSecurityRuntimeException(String message) {
      super(message);
   }

   public WLJMSSecurityRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLJMSSecurityRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLJMSSecurityRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLJMSSecurityRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLJMSSecurityRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLJMSSecurityRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLJMSSecurityRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }
}
