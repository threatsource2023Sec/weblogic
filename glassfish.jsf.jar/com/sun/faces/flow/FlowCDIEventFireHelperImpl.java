package com.sun.faces.flow;

import java.io.Serializable;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;

public class FlowCDIEventFireHelperImpl implements Serializable, FlowCDIEventFireHelper {
   private static final long serialVersionUID = -5689195252450178355L;
   @Inject
   @Initialized(FlowScoped.class)
   Event flowInitializedEvent;
   @Inject
   @Destroyed(FlowScoped.class)
   Event flowDestroyedEvent;

   public void fireInitializedEvent(Flow currentFlow) {
      this.flowInitializedEvent.fire(currentFlow);
   }

   public void fireDestroyedEvent(Flow currentFlow) {
      this.flowDestroyedEvent.fire(currentFlow);
   }
}
