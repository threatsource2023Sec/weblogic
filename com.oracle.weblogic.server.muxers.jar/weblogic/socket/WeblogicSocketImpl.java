package weblogic.socket;

import java.io.IOException;
import java.net.Socket;
import weblogic.socket.internal.SocketEnvironment;
import weblogic.utils.concurrent.Latch;

public class WeblogicSocketImpl extends WeblogicSocket {
   private final Latch decrementLatch = new Latch();

   WeblogicSocketImpl(Socket s) {
      super(s);
   }

   public final void close() throws IOException {
      super.close();
      if (this.decrementLatch.tryLock()) {
         SocketEnvironment.getSocketEnvironment().serverThrottleDecrementOpenSocketCount();
      }

   }

   protected final void finalize() throws Throwable {
      this.close();
      super.finalize();
   }
}
