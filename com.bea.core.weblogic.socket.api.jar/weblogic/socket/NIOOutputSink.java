package weblogic.socket;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface NIOOutputSink {
   boolean canWrite();

   void notifyWritePossible(WriteHandler var1);

   boolean isBlocking();

   void configureBlocking() throws InterruptedException;

   void configureBlocking(long var1, TimeUnit var3) throws TimeoutException, InterruptedException;

   void configureNonBlocking(MuxableSocket var1);
}
