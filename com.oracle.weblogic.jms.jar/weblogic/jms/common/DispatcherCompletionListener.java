package weblogic.jms.common;

import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.kernel.KernelListener;
import weblogic.messaging.kernel.KernelRequest;

public class DispatcherCompletionListener implements KernelListener {
   protected Request request;

   public DispatcherCompletionListener(Request request) {
      this.request = request;
   }

   public void onCompletion(KernelRequest r, Object result) {
      this.request.resumeExecution(false);
   }

   public void onException(KernelRequest r, Throwable throwable) {
      this.request.resumeExecution(false);
   }
}
