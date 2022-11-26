package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractSocketConnectorHandler;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.nio.NIOChannelDistributor;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.nio.SelectionKeyHandler;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.Futures;

public class TCPNIOConnectorHandler extends AbstractSocketConnectorHandler {
   private static final Logger LOGGER = Grizzly.logger(TCPNIOConnectorHandler.class);
   protected static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
   private final InstantConnectHandler instantConnectHandler;
   protected boolean isReuseAddress;
   protected volatile long connectionTimeoutMillis = 30000L;

   protected TCPNIOConnectorHandler(TCPNIOTransport transport) {
      super(transport);
      this.connectionTimeoutMillis = (long)transport.getConnectionTimeout();
      this.isReuseAddress = transport.isReuseAddress();
      this.instantConnectHandler = new InstantConnectHandler();
   }

   public void connect(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler completionHandler) {
      if (!this.transport.isBlocking()) {
         this.connectAsync(remoteAddress, localAddress, completionHandler, false);
      } else {
         this.connectSync(remoteAddress, localAddress, completionHandler);
      }

   }

   protected void connectSync(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler completionHandler) {
      FutureImpl future = this.connectAsync(remoteAddress, localAddress, completionHandler, true);
      this.waitNIOFuture(future, completionHandler);
   }

   protected FutureImpl connectAsync(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler completionHandler, boolean needFuture) {
      TCPNIOTransport nioTransport = (TCPNIOTransport)this.transport;
      final TCPNIOConnection newConnection = null;

      try {
         SocketChannel socketChannel = nioTransport.getSelectorProvider().openSocketChannel();
         newConnection = nioTransport.obtainNIOConnection(socketChannel);
         Socket socket = socketChannel.socket();
         nioTransport.getChannelConfigurator().preConfigure(nioTransport, socketChannel);
         boolean reuseAddr = this.isReuseAddress;
         if (reuseAddr != nioTransport.isReuseAddress()) {
            socket.setReuseAddress(reuseAddr);
         }

         if (localAddress != null) {
            socket.bind(localAddress);
         }

         this.preConfigure(newConnection);
         newConnection.setProcessor(this.getProcessor());
         newConnection.setProcessorSelector(this.getProcessorSelector());
         boolean isConnected = socketChannel.connect(remoteAddress);
         final CompletionHandler completionHandlerToPass;
         FutureImpl futureToReturn;
         if (needFuture) {
            futureToReturn = this.makeCancellableFuture(newConnection);
            completionHandlerToPass = Futures.toCompletionHandler(futureToReturn, completionHandler);
         } else {
            completionHandlerToPass = completionHandler;
            futureToReturn = null;
         }

         newConnection.setConnectResultHandler(new TCPNIOConnection.ConnectResultHandler() {
            public void connected() throws IOException {
               TCPNIOConnectorHandler.onConnectedAsync(newConnection, completionHandlerToPass);
            }

            public void failed(Throwable throwable) {
               TCPNIOConnectorHandler.abortConnection(newConnection, completionHandlerToPass, throwable);
            }
         });
         NIOChannelDistributor nioChannelDistributor = nioTransport.getNIOChannelDistributor();
         if (nioChannelDistributor == null) {
            throw new IllegalStateException("NIOChannelDistributor is null. Is Transport running?");
         } else {
            if (isConnected) {
               nioChannelDistributor.registerChannelAsync(socketChannel, 0, newConnection, this.instantConnectHandler);
            } else {
               nioChannelDistributor.registerChannelAsync(socketChannel, 8, newConnection, new RegisterChannelCompletionHandler(newConnection));
            }

            return futureToReturn;
         }
      } catch (Exception var15) {
         if (newConnection != null) {
            newConnection.closeSilently();
         }

         if (completionHandler != null) {
            completionHandler.failed(var15);
         }

         return needFuture ? ReadyFutureImpl.create((Throwable)var15) : null;
      }
   }

   protected static void onConnectedAsync(TCPNIOConnection connection, CompletionHandler completionHandler) throws IOException {
      TCPNIOTransport tcpTransport = (TCPNIOTransport)connection.getTransport();
      SocketChannel channel = (SocketChannel)connection.getChannel();

      try {
         if (!channel.isConnected()) {
            channel.finishConnect();
         }

         connection.resetProperties();
         connection.disableIOEvent(IOEvent.CLIENT_CONNECTED);
         tcpTransport.getChannelConfigurator().postConfigure(tcpTransport, channel);
      } catch (Exception var5) {
         abortConnection(connection, completionHandler, var5);
         throw Exceptions.makeIOException(var5);
      }

      if (connection.notifyReady()) {
         tcpTransport.fireIOEvent(IOEvent.CONNECTED, connection, new EnableReadHandler(completionHandler));
      }

   }

   public boolean isReuseAddress() {
      return this.isReuseAddress;
   }

   public void setReuseAddress(boolean isReuseAddress) {
      this.isReuseAddress = isReuseAddress;
   }

   public long getSyncConnectTimeout(TimeUnit timeUnit) {
      return timeUnit.convert(this.connectionTimeoutMillis, TimeUnit.MILLISECONDS);
   }

   public void setSyncConnectTimeout(long timeout, TimeUnit timeUnit) {
      this.connectionTimeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
   }

   protected void waitNIOFuture(FutureImpl future, CompletionHandler completionHandler) {
      try {
         future.get(this.connectionTimeoutMillis, TimeUnit.MILLISECONDS);
      } catch (InterruptedException var4) {
         Futures.notifyFailure(future, completionHandler, var4);
      } catch (TimeoutException var5) {
         Futures.notifyFailure(future, completionHandler, new IOException("Channel registration on Selector timeout!"));
      } catch (Exception var6) {
      }

   }

   private static void abortConnection(TCPNIOConnection connection, CompletionHandler completionHandler, Throwable failure) {
      connection.closeSilently();
      if (completionHandler != null) {
         completionHandler.failed(failure);
      }

   }

   public static Builder builder(TCPNIOTransport transport) {
      return (new Builder()).setTransport(transport);
   }

   public static class Builder extends AbstractSocketConnectorHandler.Builder {
      private TCPNIOTransport transport;
      private Boolean reuseAddress;
      private Long timeout;
      private TimeUnit timeoutTimeunit;

      public TCPNIOConnectorHandler build() {
         TCPNIOConnectorHandler handler = (TCPNIOConnectorHandler)super.build();
         if (this.reuseAddress != null) {
            handler.setReuseAddress(this.reuseAddress);
         }

         if (this.timeout != null) {
            handler.setSyncConnectTimeout(this.timeout, this.timeoutTimeunit);
         }

         return handler;
      }

      public Builder setTransport(TCPNIOTransport transport) {
         this.transport = transport;
         return this;
      }

      public Builder setReuseAddress(boolean reuseAddress) {
         this.reuseAddress = reuseAddress;
         return this;
      }

      public Builder setSyncConnectTimeout(long timeout, TimeUnit timeunit) {
         this.timeout = timeout;
         this.timeoutTimeunit = timeunit;
         return this;
      }

      protected AbstractSocketConnectorHandler create() {
         if (this.transport == null) {
            throw new IllegalStateException("Unable to create TCPNIOConnectorHandler - transport is null");
         } else {
            return new TCPNIOConnectorHandler(this.transport);
         }
      }
   }

   private static final class EnableReadHandler extends IOEventLifeCycleListener.Adapter {
      private final CompletionHandler completionHandler;

      private EnableReadHandler(CompletionHandler completionHandler) {
         this.completionHandler = completionHandler;
      }

      public void onReregister(Context context) throws IOException {
         this.onComplete(context, (Object)null);
      }

      public void onNotRun(Context context) throws IOException {
         this.onComplete(context, (Object)null);
      }

      public void onComplete(Context context, Object data) throws IOException {
         TCPNIOConnection connection = (TCPNIOConnection)context.getConnection();
         if (this.completionHandler != null) {
            this.completionHandler.completed(connection);
         }

         if (!connection.isStandalone()) {
            connection.enableInitialOpRead();
         }

      }

      public void onError(Context context, Object description) throws IOException {
         context.getConnection().closeSilently();
      }

      // $FF: synthetic method
      EnableReadHandler(CompletionHandler x0, Object x1) {
         this(x0);
      }
   }

   private static class RegisterChannelCompletionHandler extends EmptyCompletionHandler {
      private final TCPNIOConnection connection;

      public RegisterChannelCompletionHandler(TCPNIOConnection connection) {
         this.connection = connection;
      }

      public void completed(RegisterChannelResult result) {
         TCPNIOTransport transport = (TCPNIOTransport)this.connection.getTransport();
         transport.selectorRegistrationHandler.completed(result);
      }

      public void failed(Throwable throwable) {
         this.connection.checkConnectFailed(throwable);
      }
   }

   private class InstantConnectHandler extends EmptyCompletionHandler {
      private InstantConnectHandler() {
      }

      public void completed(RegisterChannelResult result) {
         TCPNIOTransport transport = (TCPNIOTransport)TCPNIOConnectorHandler.this.transport;
         transport.selectorRegistrationHandler.completed(result);
         SelectionKey selectionKey = result.getSelectionKey();
         SelectionKeyHandler selectionKeyHandler = transport.getSelectionKeyHandler();
         TCPNIOConnection connection = (TCPNIOConnection)selectionKeyHandler.getConnectionForKey(selectionKey);

         try {
            connection.onConnect();
         } catch (Exception var7) {
            TCPNIOConnectorHandler.LOGGER.log(Level.FINE, "Exception happened, when trying to connect the channel", var7);
         }

      }

      // $FF: synthetic method
      InstantConnectHandler(Object x1) {
         this();
      }
   }
}
