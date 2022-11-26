package org.glassfish.grizzly;

import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.utils.Futures;

public abstract class AbstractReader implements Reader {
   public final GrizzlyFuture read(Connection connection) {
      return this.read(connection, (Buffer)null);
   }

   public final GrizzlyFuture read(Connection connection, Buffer buffer) {
      FutureImpl future = Futures.createSafeFuture();
      this.read(connection, buffer, Futures.toCompletionHandler(future), (Interceptor)null);
      return future;
   }

   public final void read(Connection connection, Buffer buffer, CompletionHandler completionHandler) {
      this.read(connection, buffer, completionHandler, (Interceptor)null);
   }
}
