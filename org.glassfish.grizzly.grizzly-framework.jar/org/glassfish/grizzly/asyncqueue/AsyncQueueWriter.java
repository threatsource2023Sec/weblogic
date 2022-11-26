package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.Writer;

public interface AsyncQueueWriter extends Writer, AsyncQueue {
   int UNLIMITED_SIZE = -1;
   int AUTO_SIZE = -2;

   /** @deprecated */
   void write(Connection var1, Object var2, WritableMessage var3, CompletionHandler var4, PushBackHandler var5, MessageCloner var6);

   /** @deprecated */
   boolean canWrite(Connection var1, int var2);

   /** @deprecated */
   void notifyWritePossible(Connection var1, WriteHandler var2, int var3);

   void setMaxPendingBytesPerConnection(int var1);

   int getMaxPendingBytesPerConnection();

   boolean isAllowDirectWrite();

   void setAllowDirectWrite(boolean var1);
}
