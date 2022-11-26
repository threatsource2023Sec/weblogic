package weblogic.servlet.internal.async;

import javax.servlet.ServletContext;

class AsyncWaitState extends DefaultState {
   public void complete(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.COMPLETED);
      this.commitResponse(async);
   }

   public void dispatch(AsyncStateSupport async, ServletContext context, String path) {
      async.setAsyncState(AsyncStates.DISPATCHED);
      async.initDispatcher(context, path);
      async.scheduleDispatch();
   }

   public void returnToContainer(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.COMPLETED);
      this.commitResponse(async);
   }

   public void dispatchErrorPage(AsyncStateSupport async, Throwable error) {
      async.dispatchErrorPage(error);
   }

   public String toString() {
      return "AsyncWait";
   }
}
