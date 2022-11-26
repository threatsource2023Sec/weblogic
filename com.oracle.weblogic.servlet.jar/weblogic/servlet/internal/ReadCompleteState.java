package weblogic.servlet.internal;

import java.io.IOException;

class ReadCompleteState implements NIOListenerState {
   public void handleEvent(AbstractNIOListenerContext listenerContext) throws IOException {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("ReadListener notifyAllDataRead!" + this);
      }

      ReadListenerStateContext readListenerContext = (ReadListenerStateContext)listenerContext;
      readListenerContext.setFinishedState();
      readListenerContext.getReadListener().onAllDataRead();
   }

   public void handleError(AbstractNIOListenerContext listenerContext, Throwable t) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Error happened when process ReadListener's onAllDataRead(). handleError: " + t.getMessage());
      }

      ((ReadListenerStateContext)listenerContext).setErrorState(t);
      listenerContext.process();
   }
}
