package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.nio.SelectionKeyHandler;
import org.glassfish.grizzly.utils.CompletionHandlerAdapter;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.Holder;
import org.glassfish.grizzly.utils.NullaryFunction;

public final class TCPNIOServerConnection extends TCPNIOConnection {
   private static boolean DISABLE_INTERRUPT_CLEAR = Boolean.valueOf(System.getProperty(TCPNIOServerConnection.class.getName() + "_DISABLE_INTERRUPT_CLEAR", "false"));
   private static final Logger LOGGER = Grizzly.logger(TCPNIOServerConnection.class);
   private FutureImpl acceptListener;
   private final RegisterAcceptedChannelCompletionHandler defaultCompletionHandler = new RegisterAcceptedChannelCompletionHandler();
   private final Object acceptSync = new Object();

   public TCPNIOServerConnection(TCPNIOTransport transport, ServerSocketChannel serverSocketChannel) {
      super(transport, serverSocketChannel);
   }

   public void listen() throws IOException {
      CompletionHandler registerCompletionHandler = ((TCPNIOTransport)this.transport).selectorRegistrationHandler;
      FutureImpl future = SafeFutureImpl.create();
      this.transport.getNIOChannelDistributor().registerServiceChannelAsync(this.channel, 16, this, new CompletionHandlerAdapter(future, registerCompletionHandler));

      try {
         future.get(10L, TimeUnit.SECONDS);
      } catch (ExecutionException var4) {
         throw Exceptions.makeIOException(var4.getCause());
      } catch (Exception var5) {
         throw Exceptions.makeIOException(var5);
      }

      this.notifyReady();
      notifyProbesBind(this);
   }

   public boolean isBlocking() {
      return this.transport.isBlocking();
   }

   public boolean isStandalone() {
      return this.transport.isStandalone();
   }

   public GrizzlyFuture accept() throws IOException {
      if (!this.isStandalone()) {
         throw new IllegalStateException("Accept could be used in standalone mode only");
      } else {
         GrizzlyFuture future = this.acceptAsync();
         if (this.isBlocking()) {
            try {
               future.get();
            } catch (Exception var3) {
            }
         }

         return future;
      }
   }

   protected GrizzlyFuture acceptAsync() throws IOException {
      if (!this.isOpen()) {
         throw new IOException("Connection is closed");
      } else {
         synchronized(this.acceptSync) {
            FutureImpl future = SafeFutureImpl.create();
            SocketChannel acceptedChannel = this.doAccept();
            if (acceptedChannel != null) {
               this.configureAcceptedChannel(acceptedChannel);
               TCPNIOConnection clientConnection = this.createClientConnection(acceptedChannel);
               this.registerAcceptedChannel(clientConnection, new RegisterAcceptedChannelCompletionHandler(future), 0);
            } else {
               this.acceptListener = future;
               this.enableIOEvent(IOEvent.SERVER_ACCEPT);
            }

            return future;
         }
      }
   }

   private SocketChannel doAccept() throws IOException {
      if (!DISABLE_INTERRUPT_CLEAR && Thread.currentThread().isInterrupted()) {
         Thread.interrupted();
      }

      return ((ServerSocketChannel)this.getChannel()).accept();
   }

   private void configureAcceptedChannel(SocketChannel acceptedChannel) throws IOException {
      TCPNIOTransport tcpNIOTransport = (TCPNIOTransport)this.transport;
      tcpNIOTransport.getChannelConfigurator().preConfigure(this.transport, acceptedChannel);
      tcpNIOTransport.getChannelConfigurator().postConfigure(this.transport, acceptedChannel);
   }

   private TCPNIOConnection createClientConnection(SocketChannel acceptedChannel) {
      TCPNIOTransport tcpNIOTransport = (TCPNIOTransport)this.transport;
      TCPNIOConnection connection = tcpNIOTransport.obtainNIOConnection(acceptedChannel);
      if (this.processor != null) {
         connection.setProcessor(this.processor);
      }

      if (this.processorSelector != null) {
         connection.setProcessorSelector(this.processorSelector);
      }

      connection.resetProperties();
      return connection;
   }

   private void registerAcceptedChannel(TCPNIOConnection acceptedConnection, CompletionHandler completionHandler, int initialSelectionKeyInterest) throws IOException {
      TCPNIOTransport tcpNIOTransport = (TCPNIOTransport)this.transport;
      tcpNIOTransport.getNIOChannelDistributor().registerChannelAsync(acceptedConnection.getChannel(), initialSelectionKeyInterest, acceptedConnection, completionHandler);
   }

   public void preClose() {
      if (this.acceptListener != null) {
         this.acceptListener.failure(new IOException("Connection is closed"));
      }

      this.transport.unbind(this);
      super.preClose();
   }

   public void onAccept() throws IOException {
      TCPNIOConnection acceptedConnection;
      if (!this.isStandalone()) {
         SocketChannel acceptedChannel = this.doAccept();
         if (acceptedChannel == null) {
            return;
         }

         this.configureAcceptedChannel(acceptedChannel);
         acceptedConnection = this.createClientConnection(acceptedChannel);
         notifyProbesAccept(this, acceptedConnection);
         this.registerAcceptedChannel(acceptedConnection, this.defaultCompletionHandler, 1);
      } else {
         synchronized(this.acceptSync) {
            if (this.acceptListener == null) {
               this.disableIOEvent(IOEvent.SERVER_ACCEPT);
               return;
            }

            SocketChannel acceptedChannel = this.doAccept();
            if (acceptedChannel == null) {
               return;
            }

            this.configureAcceptedChannel(acceptedChannel);
            acceptedConnection = this.createClientConnection(acceptedChannel);
            notifyProbesAccept(this, acceptedConnection);
            this.registerAcceptedChannel(acceptedConnection, new RegisterAcceptedChannelCompletionHandler(this.acceptListener), 0);
            this.acceptListener = null;
         }
      }

   }

   public void setReadBufferSize(int readBufferSize) {
      throw new IllegalStateException("Use TCPNIOTransport.setReadBufferSize()");
   }

   public void setWriteBufferSize(int writeBufferSize) {
      throw new IllegalStateException("Use TCPNIOTransport.setWriteBufferSize()");
   }

   public int getReadBufferSize() {
      return this.transport.getReadBufferSize();
   }

   public int getWriteBufferSize() {
      return this.transport.getWriteBufferSize();
   }

   protected void closeGracefully0(CompletionHandler completionHandler, CloseReason closeReason) {
      this.terminate0(completionHandler, closeReason);
   }

   protected void resetProperties() {
      this.localSocketAddressHolder = Holder.lazyHolder(new NullaryFunction() {
         public SocketAddress evaluate() {
            return ((ServerSocketChannel)TCPNIOServerConnection.this.channel).socket().getLocalSocketAddress();
         }
      });
      this.peerSocketAddressHolder = Holder.staticHolder((Object)null);
   }

   protected final class RegisterAcceptedChannelCompletionHandler extends EmptyCompletionHandler {
      private final FutureImpl listener;

      public RegisterAcceptedChannelCompletionHandler() {
         this((FutureImpl)null);
      }

      public RegisterAcceptedChannelCompletionHandler(FutureImpl listener) {
         this.listener = listener;
      }

      public void completed(RegisterChannelResult result) {
         try {
            TCPNIOTransport nioTransport = (TCPNIOTransport)TCPNIOServerConnection.this.transport;
            nioTransport.selectorRegistrationHandler.completed(result);
            SelectionKeyHandler selectionKeyHandler = nioTransport.getSelectionKeyHandler();
            SelectionKey acceptedConnectionKey = result.getSelectionKey();
            TCPNIOConnection connection = (TCPNIOConnection)selectionKeyHandler.getConnectionForKey(acceptedConnectionKey);
            if (this.listener != null) {
               this.listener.result(connection);
            }

            if (connection.notifyReady()) {
               TCPNIOServerConnection.this.transport.fireIOEvent(IOEvent.ACCEPTED, connection, (IOEventLifeCycleListener)null);
            }
         } catch (Exception var6) {
            TCPNIOServerConnection.LOGGER.log(Level.FINE, "Exception happened, when trying to accept the connection", var6);
         }

      }
   }
}
