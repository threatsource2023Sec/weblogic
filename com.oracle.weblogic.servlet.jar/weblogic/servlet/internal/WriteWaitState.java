package weblogic.servlet.internal;

import java.io.IOException;

class WriteWaitState implements NIOListenerState {
   public void handleEvent(AbstractNIOListenerContext listenerContext) throws IOException {
      WriteListenerStateContext writeListenerContext = (WriteListenerStateContext)listenerContext;
      writeListenerContext.getOutput().flush();
      writeListenerContext.setWriteReadyState();
      listenerContext.process();
   }

   public void handleError(AbstractNIOListenerContext listenerContext, Throwable t) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Error happened when process write wait state IO. handleError: " + (listenerContext.getErrorInfo() != null ? listenerContext.getErrorInfo().getMessage() : "null"));
      }

      ((WriteListenerStateContext)listenerContext).setErrorState(t);
      listenerContext.process();
   }
}
