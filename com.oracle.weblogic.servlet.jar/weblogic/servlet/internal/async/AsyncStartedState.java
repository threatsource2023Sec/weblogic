package weblogic.servlet.internal.async;

import javax.servlet.AsyncListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

class AsyncStartedState extends DefaultState {
   public void addListener(AsyncStateSupport async, AsyncListener listener, ServletRequest request, ServletResponse response) {
      async.registerListener(listener, request, response);
   }

   public void complete(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.COMPLETING);
   }

   public void dispatch(AsyncStateSupport async, ServletContext context, String path) {
      async.setAsyncState(AsyncStates.DISPATCHING);
      async.initDispatcher(context, path);
   }

   public void setTimeout(AsyncStateSupport async, long timeout) {
      async.setTimeoutInternal(timeout);
   }

   public void returnToContainer(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.ASYNC_WAIT);
      async.registerTimeout();
   }

   public String toString() {
      return "AsyncStarted";
   }
}
