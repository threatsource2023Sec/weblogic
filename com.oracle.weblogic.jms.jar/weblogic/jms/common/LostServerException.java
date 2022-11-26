package weblogic.jms.common;

import weblogic.logging.Loggable;

public final class LostServerException extends JMSException {
   static final long serialVersionUID = -7591145489117685663L;
   boolean replayLastException;

   public LostServerException(String reason) {
      super(reason);
   }

   public LostServerException(Throwable cause) {
      super(cause);
   }

   public LostServerException(Loggable i18nLog) {
      super(i18nLog.getMessage());
   }

   public LostServerException(Loggable i18nLog, Throwable cause) {
      super(i18nLog.getMessage(), cause);
   }

   public LostServerException(String message, Throwable cause) {
      super(message, cause);
   }

   public boolean isReplayLastException() {
      return this.replayLastException;
   }

   public void setReplayLastException(boolean replay) {
      this.replayLastException = replay;
   }
}
