package org.glassfish.grizzly;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;

public abstract class AbstractSocketConnectorHandler implements SocketConnectorHandler {
   protected final Transport transport;
   private Processor processor;
   private ProcessorSelector processorSelector;
   protected final List probes = new LinkedList();

   public AbstractSocketConnectorHandler(Transport transport) {
      this.transport = transport;
      this.processor = transport.getProcessor();
      this.processorSelector = transport.getProcessorSelector();
   }

   public GrizzlyFuture connect(String host, int port) {
      return this.connect((SocketAddress)(new InetSocketAddress(host, port)));
   }

   public GrizzlyFuture connect(SocketAddress remoteAddress) {
      return this.connect(remoteAddress, (SocketAddress)null);
   }

   public void connect(SocketAddress remoteAddress, CompletionHandler completionHandler) {
      this.connect((SocketAddress)remoteAddress, (SocketAddress)null, completionHandler);
   }

   public GrizzlyFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
      return this.connectAsync(remoteAddress, localAddress, (CompletionHandler)null, true);
   }

   public void connect(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler completionHandler) {
      this.connectAsync(remoteAddress, localAddress, completionHandler, false);
   }

   protected abstract FutureImpl connectAsync(SocketAddress var1, SocketAddress var2, CompletionHandler var3, boolean var4);

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

   public void addMonitoringProbe(ConnectionProbe probe) {
      this.probes.add(probe);
   }

   public boolean removeMonitoringProbe(ConnectionProbe probe) {
      return this.probes.remove(probe);
   }

   public ConnectionProbe[] getMonitoringProbes() {
      return (ConnectionProbe[])this.probes.toArray(new ConnectionProbe[this.probes.size()]);
   }

   protected void preConfigure(Connection connection) {
   }

   protected FutureImpl makeCancellableFuture(final Connection connection) {
      return new SafeFutureImpl() {
         protected void onComplete() {
            try {
               if (!this.isCancelled()) {
                  this.get();
                  return;
               }
            } catch (Throwable var3) {
            }

            try {
               connection.closeSilently();
            } catch (Exception var2) {
            }

         }
      };
   }

   public abstract static class Builder {
      protected Processor processor;
      protected ProcessorSelector processorSelector;
      protected ConnectionProbe connectionProbe;

      public Builder processor(Processor processor) {
         this.processor = processor;
         return this;
      }

      public Builder processorSelector(ProcessorSelector processorSelector) {
         this.processorSelector = processorSelector;
         return this;
      }

      public Builder probe(ConnectionProbe connectionProbe) {
         this.connectionProbe = connectionProbe;
         return this;
      }

      public AbstractSocketConnectorHandler build() {
         AbstractSocketConnectorHandler handler = this.create();
         if (this.processor != null) {
            handler.setProcessor(this.processor);
         }

         if (this.processorSelector != null) {
            handler.setProcessorSelector(this.processorSelector);
         }

         if (this.connectionProbe != null) {
            handler.addMonitoringProbe(this.connectionProbe);
         }

         return handler;
      }

      protected abstract AbstractSocketConnectorHandler create();
   }
}
