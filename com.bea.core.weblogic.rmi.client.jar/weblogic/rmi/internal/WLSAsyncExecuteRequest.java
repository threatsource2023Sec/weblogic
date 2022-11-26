package weblogic.rmi.internal;

import weblogic.rmi.spi.AsyncCallback;
import weblogic.rmi.spi.InboundResponse;
import weblogic.work.WorkAdapter;

public class WLSAsyncExecuteRequest extends WorkAdapter {
   private ServerRequest request;
   private AsyncCallback callback;

   public WLSAsyncExecuteRequest(ServerRequest request, AsyncCallback callback) {
      this.request = request;
      this.callback = callback;
   }

   public void run() {
      InboundResponse response = null;

      try {
         try {
            response = this.request.sendReceive();
            this.callback.setInboundResponse(response);
         } catch (Throwable var6) {
            this.callback.setThrowable(var6);
         }

      } finally {
         ;
      }
   }
}
