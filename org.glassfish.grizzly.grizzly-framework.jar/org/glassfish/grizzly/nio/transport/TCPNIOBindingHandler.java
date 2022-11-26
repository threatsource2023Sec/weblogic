package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.locks.Lock;
import org.glassfish.grizzly.AbstractBindingHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.utils.Exceptions;

public class TCPNIOBindingHandler extends AbstractBindingHandler {
   private final TCPNIOTransport tcpTransport;

   TCPNIOBindingHandler(TCPNIOTransport tcpTransport) {
      super(tcpTransport);
      this.tcpTransport = tcpTransport;
   }

   public TCPNIOServerConnection bind(SocketAddress socketAddress) throws IOException {
      return this.bind(socketAddress, this.tcpTransport.getServerConnectionBackLog());
   }

   public TCPNIOServerConnection bind(SocketAddress socketAddress, int backlog) throws IOException {
      return this.bindToChannelAndAddress(this.tcpTransport.getSelectorProvider().openServerSocketChannel(), socketAddress, backlog);
   }

   public TCPNIOServerConnection bindToInherited() throws IOException {
      return this.bindToChannelAndAddress((ServerSocketChannel)this.getSystemInheritedChannel(ServerSocketChannel.class), (SocketAddress)null, -1);
   }

   public void unbind(Connection connection) {
      this.tcpTransport.unbind(connection);
   }

   public static Builder builder(TCPNIOTransport transport) {
      return (new Builder()).transport(transport);
   }

   private TCPNIOServerConnection bindToChannelAndAddress(ServerSocketChannel serverSocketChannel, SocketAddress socketAddress, int backlog) throws IOException {
      TCPNIOServerConnection serverConnection = null;
      Lock lock = this.tcpTransport.getState().getStateLocker().writeLock();
      lock.lock();

      TCPNIOServerConnection var7;
      try {
         ServerSocket serverSocket = serverSocketChannel.socket();
         this.tcpTransport.getChannelConfigurator().preConfigure(this.transport, serverSocketChannel);
         if (socketAddress != null) {
            serverSocket.bind(socketAddress, backlog);
         }

         this.tcpTransport.getChannelConfigurator().postConfigure(this.transport, serverSocketChannel);
         serverConnection = this.tcpTransport.obtainServerNIOConnection(serverSocketChannel);
         serverConnection.setProcessor(this.getProcessor());
         serverConnection.setProcessorSelector(this.getProcessorSelector());
         this.tcpTransport.serverConnections.add(serverConnection);
         serverConnection.resetProperties();
         if (!this.tcpTransport.isStopped()) {
            this.tcpTransport.listenServerConnection(serverConnection);
         }

         var7 = serverConnection;
      } catch (Exception var13) {
         if (serverConnection != null) {
            this.tcpTransport.serverConnections.remove(serverConnection);
            serverConnection.closeSilently();
         } else {
            try {
               serverSocketChannel.close();
            } catch (IOException var12) {
            }
         }

         throw Exceptions.makeIOException(var13);
      } finally {
         lock.unlock();
      }

      return var7;
   }

   public static class Builder extends AbstractBindingHandler.Builder {
      private TCPNIOTransport transport;

      public Builder transport(TCPNIOTransport transport) {
         this.transport = transport;
         return this;
      }

      public TCPNIOBindingHandler build() {
         return (TCPNIOBindingHandler)super.build();
      }

      protected AbstractBindingHandler create() {
         if (this.transport == null) {
            throw new IllegalStateException("Unable to create TCPNIOBindingHandler - transport is null");
         } else {
            return new TCPNIOBindingHandler(this.transport);
         }
      }
   }
}
