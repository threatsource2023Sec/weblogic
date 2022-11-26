package weblogic.servlet.internal;

class ReadErrorState implements NIOListenerState {
   public void handleEvent(AbstractNIOListenerContext listenerContext) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Error happened. Invoke ReadListener's onError(). handleError: " + listenerContext.getErrorInfo().getMessage());
      }

      ReadListenerStateContext readListenerContext = (ReadListenerStateContext)listenerContext;

      try {
         readListenerContext.getReadListener().onError(listenerContext.getErrorInfo());
      } finally {
         readListenerContext.closeWebConnection();
      }

   }

   public void handleError(AbstractNIOListenerContext listenerContext, Throwable t) {
   }
}
