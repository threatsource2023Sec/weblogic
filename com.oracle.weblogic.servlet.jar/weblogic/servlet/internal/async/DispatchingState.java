package weblogic.servlet.internal.async;

import javax.servlet.AsyncListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

class DispatchingState extends DefaultState {
   public void returnToContainer(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.DISPATCHED);
      async.scheduleDispatch();
      async.registerTimeout();
   }

   public void setTimeout(AsyncStateSupport async, long timeout) {
      async.setTimeoutInternal(timeout);
   }

   public void addListener(AsyncStateSupport async, AsyncListener listener, ServletRequest request, ServletResponse response) {
      async.registerListener(listener, request, response);
   }

   public String toString() {
      return "AsyncDispatching";
   }
}
