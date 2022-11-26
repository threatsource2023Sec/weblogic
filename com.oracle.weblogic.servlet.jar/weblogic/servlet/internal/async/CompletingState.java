package weblogic.servlet.internal.async;

import javax.servlet.AsyncListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

class CompletingState extends DefaultState {
   public void complete(AsyncStateSupport async) {
   }

   public void returnToContainer(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.COMPLETED);
      this.commitResponse(async);
   }

   public void setTimeout(AsyncStateSupport async, long timeout) {
      async.setTimeoutInternal(timeout);
   }

   public void addListener(AsyncStateSupport async, AsyncListener listener, ServletRequest request, ServletResponse response) {
      async.registerListener(listener, request, response);
   }

   public String toString() {
      return "AsyncCompleting";
   }
}
