package weblogic.timers.internal;

import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.timers.TimerListener;

public class ComponentRequestCalendarTimerImpl extends ServerCalendarTimerImpl implements ComponentRequest {
   private final ComponentRequest componentRequest;

   ComponentRequestCalendarTimerImpl(TimerManagerImpl timerManager, TimerListener listener, ScheduleExpressionWrapper schedule) {
      super(timerManager, listener, schedule);
      this.componentRequest = (ComponentRequest)listener;
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.componentRequest.getComponentInvocationContext();
   }
}
