package weblogic.servlet.internal;

import weblogic.timers.NakedTimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public abstract class NakedTimerListenerBase implements NakedTimerListener {
   protected final TimerManager timerManager;

   protected NakedTimerListenerBase(String baseName, WebAppServletContext ctx) {
      StringBuilder timerManagerName = new StringBuilder();
      timerManagerName.append(baseName).append("-Host='").append(ctx.getServer().getName()).append("',appId='").append(ctx.getApplicationId()).append("',contextPath='").append(ctx.getContextPath()).append("'");
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager(timerManagerName.toString(), WorkManagerFactory.getInstance().getSystem());
   }
}
