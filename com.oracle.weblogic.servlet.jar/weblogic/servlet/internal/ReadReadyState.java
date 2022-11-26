package weblogic.servlet.internal;

import java.io.IOException;

class ReadReadyState implements NIOListenerState {
   public void handleEvent(AbstractNIOListenerContext listenerContext) throws IOException {
      ReadListenerStateContext readListenerContext = (ReadListenerStateContext)listenerContext;
      readListenerContext.getReadListener().onDataAvailable();
      if (listenerContext.getAsyncContext() == null || !listenerContext.getAsyncContext().isAsyncCompleted() && !listenerContext.getAsyncContext().isAsyncCompleting()) {
         if (readListenerContext.isReadComplete()) {
            listenerContext.scheduleProcess();
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("ReadReadyState handleEvent allDataRead!" + this);
            }
         } else if (readListenerContext.isReadWait() && readListenerContext.getHttpSocket() != null) {
            listenerContext.process();
         } else if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("ReadReadyState handleEvent the readListener does not consume all incoming data!" + this);
         }
      } else {
         readListenerContext.setFinishedState();
      }

   }

   public void handleError(AbstractNIOListenerContext listenerContext, Throwable t) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Error happened when process ReadListener's onDataAvailable(). handleError: " + t.getMessage());
      }

      ((ReadListenerStateContext)listenerContext).setErrorState(t);
      listenerContext.process();
   }
}
