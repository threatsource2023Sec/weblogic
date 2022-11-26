package org.glassfish.grizzly;

import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;

public interface Processor {
   Context obtainContext(Connection var1);

   ProcessorResult process(Context var1);

   void read(Connection var1, CompletionHandler var2);

   void write(Connection var1, Object var2, Object var3, CompletionHandler var4);

   void write(Connection var1, Object var2, Object var3, CompletionHandler var4, MessageCloner var5);

   /** @deprecated */
   @Deprecated
   void write(Connection var1, Object var2, Object var3, CompletionHandler var4, PushBackHandler var5);

   boolean isInterested(IOEvent var1);

   void setInterested(IOEvent var1, boolean var2);
}
