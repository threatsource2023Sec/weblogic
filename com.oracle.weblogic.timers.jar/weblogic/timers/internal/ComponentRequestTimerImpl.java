package weblogic.timers.internal;

import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.timers.TimerListener;

public class ComponentRequestTimerImpl extends ServerTimerImpl implements ComponentRequest {
   private final ComponentRequest componentRequest;

   ComponentRequestTimerImpl(TimerManagerImpl timerManager, TimerListener listener, long timeout, long period) {
      super(timerManager, listener, timeout, period);
      this.componentRequest = (ComponentRequest)listener;
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.componentRequest.getComponentInvocationContext();
   }
}
