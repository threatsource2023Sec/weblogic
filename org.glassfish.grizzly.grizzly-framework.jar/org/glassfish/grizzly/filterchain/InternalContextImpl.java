package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Context;

class InternalContextImpl extends Context {
   final FilterChainContext filterChainContext;

   public InternalContextImpl(FilterChainContext context) {
      this.filterChainContext = context;
   }

   public void recycle() {
      this.filterChainContext.completeAndRecycle();
   }

   protected void release() {
      this.filterChainContext.completeAndRelease();
   }

   void softCopyTo(InternalContextImpl targetContext) {
      targetContext.lifeCycleListeners.copyFrom(this.lifeCycleListeners);
      targetContext.ioEvent = this.ioEvent;
      targetContext.wasSuspended = this.wasSuspended;
      targetContext.isManualIOEventControl = this.isManualIOEventControl;
   }
}
