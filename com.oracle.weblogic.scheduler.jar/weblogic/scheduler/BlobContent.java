package weblogic.scheduler;

import java.io.Serializable;
import weblogic.timers.internal.ScheduleExpressionWrapper;

class BlobContent implements Serializable {
   private static final long serialVersionUID = -1577284286735564285L;
   private Serializable timerListener;
   private ScheduleExpressionWrapper scheduleWrapper;

   BlobContent(Serializable timerListener, ScheduleExpressionWrapper scheduleWrapper) {
      this.timerListener = timerListener;
      this.scheduleWrapper = scheduleWrapper;
   }

   ScheduleExpressionWrapper getScheduleWrapper() {
      return this.scheduleWrapper;
   }

   Serializable getTimerListener() {
      return this.timerListener;
   }
}
