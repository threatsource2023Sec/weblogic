package weblogic.rmi.extensions;

import weblogic.rmi.internal.AsyncResultImpl;
import weblogic.rmi.spi.InboundResponse;

public final class AsyncResultFactory {
   private AsyncResultFactory() {
   }

   public static AsyncResult getAsyncResult() {
      return new AsyncResultImpl();
   }

   public static AsyncResult getAsyncResult(int timeout) {
      AsyncResult result = new AsyncResultImpl();
      result.setTimeOut((long)timeout);
      return result;
   }

   public static AsyncResult getCallbackableResult(AsyncResultListener listener) {
      return new CallbackableResultImpl(listener);
   }

   private static class CallbackableResultImpl extends AsyncResultImpl {
      private final AsyncResultListener listener;

      public CallbackableResultImpl(AsyncResultListener listener) {
         this.listener = listener;
      }

      public void setInboundResponse(InboundResponse inboundResponse) {
         super.setInboundResponse(inboundResponse);
         this.listener.handleResult(this);
      }

      public void setThrowable(Throwable throwable) {
         super.setThrowable(throwable);
         this.listener.handleResult(this);
      }
   }
}
