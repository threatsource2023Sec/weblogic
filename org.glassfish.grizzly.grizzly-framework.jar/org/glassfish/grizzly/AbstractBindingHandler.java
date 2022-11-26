package org.glassfish.grizzly;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.util.Random;
import org.glassfish.grizzly.nio.NIOTransport;

public abstract class AbstractBindingHandler implements SocketBinder {
   protected static final Random RANDOM = new Random();
   protected final NIOTransport transport;
   protected Processor processor;
   protected ProcessorSelector processorSelector;

   public AbstractBindingHandler(NIOTransport transport) {
      this.transport = transport;
      this.processor = transport.getProcessor();
      this.processorSelector = transport.getProcessorSelector();
   }

   public Processor getProcessor() {
      return this.processor;
   }

   public void setProcessor(Processor processor) {
      this.processor = processor;
   }

   public ProcessorSelector getProcessorSelector() {
      return this.processorSelector;
   }

   public void setProcessorSelector(ProcessorSelector processorSelector) {
      this.processorSelector = processorSelector;
   }

   public Connection bind(int port) throws IOException {
      return this.bind(new InetSocketAddress(port));
   }

   public Connection bind(String host, int port) throws IOException {
      return this.bind(new InetSocketAddress(host, port));
   }

   public Connection bind(String host, int port, int backlog) throws IOException {
      return this.bind(new InetSocketAddress(host, port), backlog);
   }

   public Connection bind(String host, PortRange portRange, int backlog) throws IOException {
      int lower = portRange.getLower();
      int range = portRange.getUpper() - lower + 1;
      int offset = RANDOM.nextInt(range);
      int start = offset;

      while(true) {
         int port = lower + offset;

         try {
            return this.bind(host, port, backlog);
         } catch (IOException var11) {
            offset = (offset + 1) % range;
            if (offset == start) {
               throw var11;
            }
         }
      }
   }

   public final void unbindAll() {
      throw new UnsupportedOperationException();
   }

   protected Object getSystemInheritedChannel(Class channelType) throws IOException {
      Channel inheritedChannel = System.inheritedChannel();
      if (inheritedChannel == null) {
         throw new IOException("Inherited channel is not set");
      } else if (!channelType.isInstance(inheritedChannel)) {
         throw new IOException("Inherited channel is not " + channelType.getName() + ", but " + inheritedChannel.getClass().getName());
      } else {
         return inheritedChannel;
      }
   }

   public abstract static class Builder {
      protected Processor processor;
      protected ProcessorSelector processorSelector;

      public Builder processor(Processor processor) {
         this.processor = processor;
         return this;
      }

      public Builder processorSelector(ProcessorSelector processorSelector) {
         this.processorSelector = processorSelector;
         return this;
      }

      public AbstractBindingHandler build() {
         AbstractBindingHandler bindingHandler = this.create();
         if (this.processor != null) {
            bindingHandler.setProcessor(this.processor);
         }

         if (this.processorSelector != null) {
            bindingHandler.setProcessorSelector(this.processorSelector);
         }

         return bindingHandler;
      }

      protected abstract AbstractBindingHandler create();
   }
}
