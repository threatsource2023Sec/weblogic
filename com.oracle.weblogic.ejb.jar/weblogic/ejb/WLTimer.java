package weblogic.ejb;

import javax.ejb.Timer;

public interface WLTimer extends Timer {
   int getRetryAttemptCount();

   int getMaximumRetryAttempts();

   int getCompletedTimeoutCount();
}
