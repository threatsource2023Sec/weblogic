package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorSelector;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.utils.Futures;

public class UDPNIOServerConnection extends UDPNIOConnection {
   private static final Logger LOGGER = Grizzly.logger(UDPNIOServerConnection.class);

   public UDPNIOServerConnection(UDPNIOTransport transport, DatagramChannel channel) {
      super(transport, channel);
   }

   public Processor getProcessor() {
      return this.processor == null ? this.transport.getProcessor() : this.processor;
   }

   public ProcessorSelector getProcessorSelector() {
      return this.processorSelector == null ? this.transport.getProcessorSelector() : this.processorSelector;
   }

   public void register() throws IOException {
      FutureImpl future = Futures.createSafeFuture();
      this.transport.getNIOChannelDistributor().registerServiceChannelAsync(this.channel, 1, this, Futures.toCompletionHandler(future, ((UDPNIOTransport)this.transport).registerChannelCompletionHandler));

      try {
         future.get(10L, TimeUnit.SECONDS);
      } catch (Exception var3) {
         throw new IOException("Error registering server channel key", var3);
      }

      this.notifyReady();
   }

   protected void closeGracefully0(CompletionHandler completionHandler, CloseReason closeReason) {
      this.terminate0(completionHandler, closeReason);
   }

   protected void terminate0(CompletionHandler completionHandler, CloseReason closeReason) {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("UDPNIOServerConnection might be only closed by calling unbind().");
      }

      if (completionHandler != null) {
         completionHandler.completed(this);
      }

   }

   public void unbind(CompletionHandler completionHandler) {
      super.terminate0(completionHandler, CloseReason.LOCALLY_CLOSED_REASON);
   }

   protected void preClose() {
      this.transport.unbind(this);
      super.preClose();
   }
}
