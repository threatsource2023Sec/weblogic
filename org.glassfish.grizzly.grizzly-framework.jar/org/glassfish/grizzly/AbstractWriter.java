package org.glassfish.grizzly;

import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.utils.Futures;

public abstract class AbstractWriter implements Writer {
   public final GrizzlyFuture write(Connection connection, WritableMessage message) {
      return this.write(connection, (Object)null, (WritableMessage)message);
   }

   public final void write(Connection connection, WritableMessage message, CompletionHandler completionHandler) {
      this.write(connection, (Object)null, message, completionHandler, (MessageCloner)null);
   }

   public final GrizzlyFuture write(Connection connection, Object dstAddress, WritableMessage message) {
      FutureImpl future = Futures.createSafeFuture();
      this.write(connection, dstAddress, message, Futures.toCompletionHandler(future), (MessageCloner)null);
      return future;
   }

   public final void write(Connection connection, Object dstAddress, WritableMessage message, CompletionHandler completionHandler) {
      this.write(connection, dstAddress, message, completionHandler, (MessageCloner)null);
   }
}
