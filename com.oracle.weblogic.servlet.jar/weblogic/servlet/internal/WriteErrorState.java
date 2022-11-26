package weblogic.servlet.internal;

class WriteErrorState implements NIOListenerState {
   public void handleEvent(AbstractNIOListenerContext listenerContext) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Error happened. Invoke WriteListener's onError(). handleError: " + (listenerContext.getErrorInfo() != null ? listenerContext.getErrorInfo().getMessage() : "null"));
      }

      WriteListenerStateContext writeListenerContext = (WriteListenerStateContext)listenerContext;

      try {
         writeListenerContext.getWriteListener().onError(listenerContext.getErrorInfo());
      } finally {
         writeListenerContext.closeWebConnection();
      }

   }

   public void handleError(AbstractNIOListenerContext listenerContext, Throwable t) {
   }
}
