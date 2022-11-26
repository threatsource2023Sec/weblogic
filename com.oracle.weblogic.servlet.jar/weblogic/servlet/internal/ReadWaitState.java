package weblogic.servlet.internal;

import java.io.IOException;

class ReadWaitState implements NIOListenerState {
   public void handleEvent(AbstractNIOListenerContext listenerContext) throws IOException {
      ((ReadListenerStateContext)listenerContext).getHttpSocket().registerForReadEvent();
   }

   public void handleError(AbstractNIOListenerContext listenerContext, Throwable t) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Error happened when process ReadWaitState. handleError: " + t.getMessage());
      }

      ((ReadListenerStateContext)listenerContext).setErrorState(t);
      listenerContext.process();
   }
}
