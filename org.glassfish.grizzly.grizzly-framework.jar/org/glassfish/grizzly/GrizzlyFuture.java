package org.glassfish.grizzly;

import java.util.concurrent.Future;

public interface GrizzlyFuture extends Future, Cacheable {
   void addCompletionHandler(CompletionHandler var1);

   /** @deprecated */
   void markForRecycle(boolean var1);

   void recycle(boolean var1);
}
