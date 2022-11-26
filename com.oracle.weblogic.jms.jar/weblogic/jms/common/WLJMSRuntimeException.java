package weblogic.jms.common;

import javax.jms.JMSRuntimeException;
import weblogic.logging.Loggable;

public class WLJMSRuntimeException extends JMSRuntimeException {
   private static final long serialVersionUID = -5491402045208215684L;

   public WLJMSRuntimeException(String message) {
      super(message);
   }

   public WLJMSRuntimeException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public WLJMSRuntimeException(Throwable cause) {
      super(cause.toString());
      this.initCause(cause);
   }

   public WLJMSRuntimeException(Loggable i18nLog, String errorCode) {
      super(i18nLog.getMessage(), errorCode);
   }

   public WLJMSRuntimeException(String message, String errorCode) {
      super(message, errorCode);
   }

   public WLJMSRuntimeException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage());
      this.initCause(cause);
   }

   public WLJMSRuntimeException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public WLJMSRuntimeException(Loggable i18nLog, String errorCode, Throwable cause) {
      super(i18nLog.getMessage(), errorCode);
      this.initCause(cause);
   }

   public static JMSRuntimeException convertJMSException(javax.jms.JMSException e) {
      if (e instanceof javax.jms.IllegalStateException) {
         return new WLIllegalStateRuntimeException(e);
      } else if (e instanceof javax.jms.InvalidClientIDException) {
         return new WLInvalidClientIDRuntimeException(e);
      } else if (e instanceof javax.jms.InvalidDestinationException) {
         return new WLInvalidDestinationRuntimeException(e);
      } else if (e instanceof javax.jms.InvalidSelectorException) {
         return new WLInvalidSelectorRuntimeException(e);
      } else if (e instanceof javax.jms.JMSSecurityException) {
         return new WLJMSSecurityRuntimeException(e);
      } else if (e instanceof javax.jms.MessageEOFException) {
         return new WLJMSRuntimeException(e);
      } else if (e instanceof javax.jms.MessageFormatException) {
         return new WLMessageFormatRuntimeException(e);
      } else if (e instanceof javax.jms.MessageNotReadableException) {
         return new WLJMSRuntimeException(e);
      } else if (e instanceof javax.jms.MessageNotWriteableException) {
         return new WLMessageNotWriteableRuntimeException(e);
      } else if (e instanceof javax.jms.ResourceAllocationException) {
         return new WLResourceAllocationRuntimeException(e);
      } else if (e instanceof javax.jms.TransactionInProgressException) {
         return new WLTransactionInProgressRuntimeException(e);
      } else {
         return (JMSRuntimeException)(e instanceof javax.jms.TransactionRolledBackException ? new WLTransactionRolledBackRuntimeException(e) : new WLJMSRuntimeException(e));
      }
   }
}
