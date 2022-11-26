package weblogic.servlet.internal;

import java.io.IOException;

class WriteReadyState implements NIOListenerState {
   public void handleEvent(AbstractNIOListenerContext listenerContext) throws IOException {
      WriteListenerStateContext writeListenerContext = (WriteListenerStateContext)listenerContext;
      writeListenerContext.getWriteListener().onWritePossible();
      if (listenerContext.getAsyncContext() != null && (listenerContext.getAsyncContext().isAsyncCompleted() || listenerContext.getAsyncContext().isAsyncCompleting())) {
         writeListenerContext.setFinishedState();
      } else if (writeListenerContext.isWriteWait()) {
         writeListenerContext.getChunkOutput().notifyWritePossible(writeListenerContext);
      } else {
         try {
            writeListenerContext.getOutput().flush();
         } catch (IOException var4) {
            this.handleError(listenerContext, var4);
         }
      }

   }

   public void handleError(AbstractNIOListenerContext listenerContext, Throwable t) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Error happened when process WriteListener's onWritePossible(). handleError: " + (listenerContext.getErrorInfo() != null ? listenerContext.getErrorInfo().getMessage() : "null"));
      }

      ((WriteListenerStateContext)listenerContext).setErrorState(t);
      listenerContext.process();
   }
}
