package org.glassfish.grizzly;

import java.io.IOException;

public interface Closeable {
   boolean isOpen();

   void assertOpen() throws IOException;

   void terminateSilently();

   GrizzlyFuture terminate();

   void terminateWithReason(IOException var1);

   void closeSilently();

   GrizzlyFuture close();

   /** @deprecated */
   void close(CompletionHandler var1);

   void closeWithReason(IOException var1);

   void addCloseListener(CloseListener var1);

   boolean removeCloseListener(CloseListener var1);

   GrizzlyFuture closeFuture();
}
