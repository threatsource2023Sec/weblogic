package weblogic.servlet.internal.async;

import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.HTTPDebugLogger;

class DispatchedState extends DefaultState {
   public void start(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.ASYNC_STARTED);
      async.unregisterTimeout();
   }

   public void returnToContainer(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.COMPLETED);
      this.commitResponse(async);
   }

   public void complete(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.COMPLETED);
      this.commitResponse(async);
   }

   public void notifyError(AsyncStateSupport async, Throwable error) {
      async.setAsyncState(AsyncStates.ASYNC_WAIT);

      try {
         async.getAsyncEventsManager().notifyErrorEvent(error);
      } catch (Throwable var4) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPLogger.logException(var4.getMessage(), var4);
         }
      }

   }

   public String toString() {
      return "AsyncDispatched";
   }
}
