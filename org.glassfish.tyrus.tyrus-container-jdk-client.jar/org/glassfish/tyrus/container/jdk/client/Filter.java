package org.glassfish.tyrus.container.jdk.client;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import org.glassfish.tyrus.spi.CompletionHandler;

class Filter {
   protected volatile Filter upstreamFilter = null;
   protected final Filter downstreamFilter;

   Filter(Filter downstreamFilter) {
      this.downstreamFilter = downstreamFilter;
   }

   void write(ByteBuffer data, CompletionHandler completionHandler) {
   }

   void close() {
   }

   void startSsl() {
   }

   final void connect(SocketAddress address, Filter upstreamFilter) {
      this.upstreamFilter = upstreamFilter;
      this.handleConnect(address, upstreamFilter);
      if (this.downstreamFilter != null) {
         this.downstreamFilter.connect(address, this);
      }

   }

   final void onConnect() {
      this.processConnect();
      if (this.upstreamFilter != null) {
         this.upstreamFilter.onConnect();
      }

   }

   final void onRead(ByteBuffer data) {
      if (this.processRead(data) && this.upstreamFilter != null) {
         this.upstreamFilter.onRead(data);
      }

   }

   final void onConnectionClosed() {
      this.processConnectionClosed();
      Filter filter = this.upstreamFilter;
      if (filter != null) {
         filter.onConnectionClosed();
      }

   }

   final void onSslHandshakeCompleted() {
      this.processSslHandshakeCompleted();
      if (this.upstreamFilter != null) {
         this.upstreamFilter.onSslHandshakeCompleted();
      }

   }

   final void onError(Throwable t) {
      this.processError(t);
      if (this.upstreamFilter != null) {
         this.upstreamFilter.onError(t);
      }

   }

   void handleConnect(SocketAddress address, Filter upstreamFilter) {
   }

   void processConnect() {
   }

   boolean processRead(ByteBuffer data) {
      return true;
   }

   void processConnectionClosed() {
   }

   void processSslHandshakeCompleted() {
   }

   void processError(Throwable t) {
   }
}
