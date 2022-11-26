package org.glassfish.grizzly;

import org.glassfish.grizzly.asyncqueue.PushBackHandler;

public interface Writeable extends OutputSink {
   GrizzlyFuture write(Object var1);

   void write(Object var1, CompletionHandler var2);

   /** @deprecated */
   void write(Object var1, CompletionHandler var2, PushBackHandler var3);

   void write(Object var1, Object var2, CompletionHandler var3);

   /** @deprecated */
   void write(Object var1, Object var2, CompletionHandler var3, PushBackHandler var4);
}
