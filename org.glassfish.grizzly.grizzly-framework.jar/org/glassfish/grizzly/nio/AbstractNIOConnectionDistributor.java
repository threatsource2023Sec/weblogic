package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.utils.Futures;

public abstract class AbstractNIOConnectionDistributor implements NIOChannelDistributor {
   protected final NIOTransport transport;

   public AbstractNIOConnectionDistributor(NIOTransport transport) {
      this.transport = transport;
   }

   public final void registerChannel(SelectableChannel channel) throws IOException {
      this.registerChannel(channel, 0, (Object)null);
   }

   public final void registerChannel(SelectableChannel channel, int interestOps) throws IOException {
      this.registerChannel(channel, interestOps, (Object)null);
   }

   public final GrizzlyFuture registerChannelAsync(SelectableChannel channel) {
      return this.registerChannelAsync(channel, 0, (Object)null);
   }

   public final GrizzlyFuture registerChannelAsync(SelectableChannel channel, int interestOps) {
      return this.registerChannelAsync(channel, interestOps, (Object)null);
   }

   public final GrizzlyFuture registerChannelAsync(SelectableChannel channel, int interestOps, Object attachment) {
      FutureImpl future = Futures.createSafeFuture();
      this.registerChannelAsync(channel, interestOps, attachment, Futures.toCompletionHandler(future));
      return future;
   }

   protected SelectorRunner[] getTransportSelectorRunners() {
      return this.transport.getSelectorRunners();
   }
}
