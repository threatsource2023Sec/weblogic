package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.locks.Lock;
import org.glassfish.grizzly.AbstractBindingHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.utils.Exceptions;

public class UDPNIOBindingHandler extends AbstractBindingHandler {
   private final UDPNIOTransport udpTransport;

   public UDPNIOBindingHandler(UDPNIOTransport udpTransport) {
      super(udpTransport);
      this.udpTransport = udpTransport;
   }

   public UDPNIOServerConnection bind(SocketAddress socketAddress) throws IOException {
      return this.bind(socketAddress, -1);
   }

   public UDPNIOServerConnection bind(SocketAddress socketAddress, int backlog) throws IOException {
      return this.bindToChannel(this.udpTransport.getSelectorProvider().openDatagramChannel(), socketAddress);
   }

   public UDPNIOServerConnection bindToInherited() throws IOException {
      return this.bindToChannel((DatagramChannel)this.getSystemInheritedChannel(DatagramChannel.class), (SocketAddress)null);
   }

   public void unbind(Connection connection) {
      this.udpTransport.unbind(connection);
   }

   public static Builder builder(UDPNIOTransport transport) {
      return (new Builder()).transport(transport);
   }

   private UDPNIOServerConnection bindToChannel(DatagramChannel serverDatagramChannel, SocketAddress socketAddress) throws IOException {
      UDPNIOServerConnection serverConnection = null;
      Lock lock = this.udpTransport.getState().getStateLocker().writeLock();
      lock.lock();

      UDPNIOServerConnection var14;
      try {
         this.udpTransport.getChannelConfigurator().preConfigure(this.transport, serverDatagramChannel);
         if (socketAddress != null) {
            DatagramSocket socket = serverDatagramChannel.socket();
            socket.bind(socketAddress);
         }

         this.udpTransport.getChannelConfigurator().postConfigure(this.transport, serverDatagramChannel);
         serverConnection = this.udpTransport.obtainServerNIOConnection(serverDatagramChannel);
         serverConnection.setProcessor(this.getProcessor());
         serverConnection.setProcessorSelector(this.getProcessorSelector());
         this.udpTransport.serverConnections.add(serverConnection);
         if (!this.udpTransport.isStopped()) {
            serverConnection.register();
         }

         var14 = serverConnection;
      } catch (Exception var12) {
         if (serverConnection != null) {
            this.udpTransport.serverConnections.remove(serverConnection);
            serverConnection.closeSilently();
         } else {
            try {
               serverDatagramChannel.close();
            } catch (IOException var11) {
            }
         }

         throw Exceptions.makeIOException(var12);
      } finally {
         lock.unlock();
      }

      return var14;
   }

   public static class Builder extends AbstractBindingHandler.Builder {
      private UDPNIOTransport transport;

      public UDPNIOBindingHandler build() {
         return (UDPNIOBindingHandler)super.build();
      }

      public Builder transport(UDPNIOTransport transport) {
         this.transport = transport;
         return this;
      }

      protected AbstractBindingHandler create() {
         if (this.transport == null) {
            throw new IllegalStateException("Unable to create TCPNIOBindingHandler - transport is null");
         } else {
            return new UDPNIOBindingHandler(this.transport);
         }
      }
   }
}
