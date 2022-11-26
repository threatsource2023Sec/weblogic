package weblogic.servlet.internal;

import java.io.IOException;

public interface NIOListenerState {
   void handleEvent(AbstractNIOListenerContext var1) throws IOException;

   void handleError(AbstractNIOListenerContext var1, Throwable var2);
}
