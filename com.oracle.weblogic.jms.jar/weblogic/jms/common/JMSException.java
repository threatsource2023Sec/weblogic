package weblogic.jms.common;

import weblogic.logging.Loggable;

public class JMSException extends javax.jms.JMSException {
   static final long serialVersionUID = 8002367427279624380L;

   public JMSException(String message) {
      super(message);
   }

   public JMSException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public JMSException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public JMSException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public JMSException(String message, String errorCode) {
      super(message, errorCode);
   }

   public JMSException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public JMSException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public JMSException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }

   /** @deprecated */
   @Deprecated
   public JMSException(String message, String errorCode, Throwable cause) {
      super(message, errorCode);
      this.initCause(cause);
   }

   public final void setLinkedException(Exception cause) {
      setLinkedException(this, cause);
   }

   public final Exception getLinkedException() {
      return getLinkedException(this);
   }

   static Exception getLinkedException(javax.jms.JMSException jmse) {
      try {
         return (Exception)jmse.getCause();
      } catch (ClassCastException var2) {
         return new JMSException(jmse);
      }
   }

   static void setLinkedException(javax.jms.JMSException jmse, Throwable throwable) {
      jmse.initCause(throwable);
   }

   public boolean isInformational() {
      return false;
   }
}
