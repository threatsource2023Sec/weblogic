package weblogic.servlet.internal;

import java.io.IOException;

class Finished implements NIOListenerState {
   public void handleEvent(AbstractNIOListenerContext listenerContext) throws IOException {
   }

   public void handleError(AbstractNIOListenerContext listenerContext, Throwable t) {
   }
}
