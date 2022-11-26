package weblogic.servlet.internal.async;

import javax.servlet.AsyncListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.HTTPDebugLogger;

abstract class DefaultState implements AsyncState {
   public void start(AsyncStateSupport async) {
      throw new IllegalStateException(this.buildErrorMsg("invoke startAsync()", async));
   }

   public void complete(AsyncStateSupport async) {
      throw new IllegalStateException(this.buildErrorMsg("invoke complete()", async));
   }

   public void dispatch(AsyncStateSupport async, ServletContext context, String path) {
      throw new IllegalStateException(this.buildErrorMsg("invoke dispatch()", async));
   }

   public void returnToContainer(AsyncStateSupport async) {
      throw new IllegalStateException(this.buildErrorMsg("return to container", async));
   }

   public void addListener(AsyncStateSupport async, AsyncListener listener, ServletRequest request, ServletResponse response) {
      throw new IllegalStateException(this.buildErrorMsg("add listener", async));
   }

   public void setTimeout(AsyncStateSupport async, long timeout) {
      throw new IllegalStateException(this.buildErrorMsg("set timeout", async));
   }

   public void notifyError(AsyncStateSupport async, Throwable error) {
      throw new IllegalStateException(this.buildErrorMsg("error", async), error);
   }

   public void notifyTimeout(AsyncStateSupport async) {
      async.setAsyncState(AsyncStates.ASYNC_WAIT);

      try {
         async.getAsyncEventsManager().notifyTimeoutEvent();
      } catch (Throwable var3) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPLogger.logException(var3.getMessage(), var3);
         }
      }

   }

   public void dispatchErrorPage(AsyncStateSupport async, Throwable error) {
   }

   protected void commitResponse(AsyncStateSupport async) {
      async.unregisterTimeout();
      async.notifyOnCompleteEvent();
      async.commitResponse();
   }

   private String buildErrorMsg(String action, AsyncStateSupport async) {
      StringBuilder buf = new StringBuilder();
      buf.append("Can NOT ").append(action).append(" at this state: ").append(async.getAsyncState());
      return buf.toString();
   }
}
