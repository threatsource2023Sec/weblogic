package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.util.concurrent.atomic.AtomicInteger;
import org.glassfish.grizzly.CompletionHandler;

public final class RoundRobinConnectionDistributor extends AbstractNIOConnectionDistributor {
   private final Iterator it;

   public RoundRobinConnectionDistributor(NIOTransport transport) {
      this(transport, false, false);
   }

   public RoundRobinConnectionDistributor(NIOTransport transport, boolean useDedicatedAcceptor) {
      this(transport, useDedicatedAcceptor, false);
   }

   public RoundRobinConnectionDistributor(NIOTransport transport, boolean useDedicatedAcceptor, boolean isServerOnly) {
      super(transport);
      this.it = (Iterator)(useDedicatedAcceptor ? (isServerOnly ? new ServDedicatedIterator() : new DedicatedIterator()) : (isServerOnly ? new ServSharedIterator() : new SharedIterator()));
   }

   public void registerChannel(SelectableChannel channel, int interestOps, Object attachment) throws IOException {
      this.transport.getSelectorHandler().registerChannel(this.it.next(), channel, interestOps, attachment);
   }

   public void registerChannelAsync(SelectableChannel channel, int interestOps, Object attachment, CompletionHandler completionHandler) {
      this.transport.getSelectorHandler().registerChannelAsync(this.it.next(), channel, interestOps, attachment, completionHandler);
   }

   public void registerServiceChannelAsync(SelectableChannel channel, int interestOps, Object attachment, CompletionHandler completionHandler) {
      this.transport.getSelectorHandler().registerChannelAsync(this.it.nextService(), channel, interestOps, attachment, completionHandler);
   }

   private final class ServSharedIterator implements Iterator {
      private int counter;

      private ServSharedIterator() {
      }

      public SelectorRunner next() {
         SelectorRunner[] runners = RoundRobinConnectionDistributor.this.getTransportSelectorRunners();
         return runners.length == 1 ? runners[0] : runners[(this.counter++ & Integer.MAX_VALUE) % runners.length];
      }

      public SelectorRunner nextService() {
         return this.next();
      }

      // $FF: synthetic method
      ServSharedIterator(Object x1) {
         this();
      }
   }

   private final class ServDedicatedIterator implements Iterator {
      private int counter;

      private ServDedicatedIterator() {
      }

      public SelectorRunner next() {
         SelectorRunner[] runners = RoundRobinConnectionDistributor.this.getTransportSelectorRunners();
         return runners.length == 1 ? runners[0] : runners[(this.counter++ & Integer.MAX_VALUE) % (runners.length - 1) + 1];
      }

      public SelectorRunner nextService() {
         return RoundRobinConnectionDistributor.this.getTransportSelectorRunners()[0];
      }

      // $FF: synthetic method
      ServDedicatedIterator(Object x1) {
         this();
      }
   }

   private final class SharedIterator implements Iterator {
      private final AtomicInteger counter;

      private SharedIterator() {
         this.counter = new AtomicInteger();
      }

      public SelectorRunner next() {
         SelectorRunner[] runners = RoundRobinConnectionDistributor.this.getTransportSelectorRunners();
         return runners.length == 1 ? runners[0] : runners[(this.counter.getAndIncrement() & Integer.MAX_VALUE) % runners.length];
      }

      public SelectorRunner nextService() {
         return this.next();
      }

      // $FF: synthetic method
      SharedIterator(Object x1) {
         this();
      }
   }

   private final class DedicatedIterator implements Iterator {
      private final AtomicInteger counter;

      private DedicatedIterator() {
         this.counter = new AtomicInteger();
      }

      public SelectorRunner next() {
         SelectorRunner[] runners = RoundRobinConnectionDistributor.this.getTransportSelectorRunners();
         return runners.length == 1 ? runners[0] : runners[(this.counter.getAndIncrement() & Integer.MAX_VALUE) % (runners.length - 1) + 1];
      }

      public SelectorRunner nextService() {
         return RoundRobinConnectionDistributor.this.getTransportSelectorRunners()[0];
      }

      // $FF: synthetic method
      DedicatedIterator(Object x1) {
         this();
      }
   }

   private interface Iterator {
      SelectorRunner next();

      SelectorRunner nextService();
   }
}
