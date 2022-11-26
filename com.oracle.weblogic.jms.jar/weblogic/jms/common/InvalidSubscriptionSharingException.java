package weblogic.jms.common;

import weblogic.logging.Loggable;

public final class InvalidSubscriptionSharingException extends JMSException {
   public InvalidSubscriptionSharingException(String message) {
      super(message);
   }

   public InvalidSubscriptionSharingException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public InvalidSubscriptionSharingException(String message, String errorCode) {
      super(message, errorCode);
   }

   public InvalidSubscriptionSharingException(String message, Throwable cause) {
      super(message);
      JMSException.setLinkedException(this, cause);
   }

   public InvalidSubscriptionSharingException(String message, String errorCode, Throwable cause) {
      super(message, errorCode);
      JMSException.setLinkedException(this, cause);
   }
}
