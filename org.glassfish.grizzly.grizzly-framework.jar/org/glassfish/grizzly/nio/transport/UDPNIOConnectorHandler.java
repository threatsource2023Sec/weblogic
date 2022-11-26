package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractSocketConnectorHandler;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.nio.NIOChannelDistributor;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.utils.Futures;

public class UDPNIOConnectorHandler extends AbstractSocketConnectorHandler {
   private static final Logger LOGGER = Grizzly.logger(UDPNIOConnectorHandler.class);
   protected boolean isReuseAddress;
   protected volatile long connectionTimeoutMillis = 30000L;

   protected UDPNIOConnectorHandler(UDPNIOTransport transport) {
      super(transport);
      this.connectionTimeoutMillis = (long)transport.getConnectionTimeout();
      this.isReuseAddress = transport.isReuseAddress();
   }

   public GrizzlyFuture connect() {
      return this.connectAsync((SocketAddress)null, (SocketAddress)null, (CompletionHandler)null, true);
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
      UDPNIOTransport nioTransport = (UDPNIOTransport)this.transport;
      UDPNIOConnection newConnection = null;

      try {
         DatagramChannel datagramChannel = nioTransport.getSelectorProvider().openDatagramChannel();
         nioTransport.getChannelConfigurator().preConfigure(nioTransport, datagramChannel);
         DatagramSocket socket = datagramChannel.socket();
         newConnection = nioTransport.obtainNIOConnection(datagramChannel);
         boolean reuseAddr = this.isReuseAddress;
         if (reuseAddr != nioTransport.isReuseAddress()) {
            socket.setReuseAddress(reuseAddr);
         }

         socket.bind(localAddress);
         if (remoteAddress != null) {
            datagramChannel.connect(remoteAddress);
         }

         nioTransport.getChannelConfigurator().postConfigure(nioTransport, datagramChannel);
         this.preConfigure(newConnection);
         newConnection.setProcessor(this.getProcessor());
         newConnection.setProcessorSelector(this.getProcessorSelector());
         NIOChannelDistributor nioChannelDistributor = nioTransport.getNIOChannelDistributor();
         if (nioChannelDistributor == null) {
            throw new IllegalStateException("NIOChannelDistributor is null. Is Transport running?");
         } else {
            CompletionHandler completionHandlerToPass;
            FutureImpl futureToReturn;
            if (needFuture) {
               futureToReturn = this.makeCancellableFuture(newConnection);
               completionHandlerToPass = Futures.toCompletionHandler(futureToReturn, completionHandler);
            } else {
               completionHandlerToPass = completionHandler;
               futureToReturn = null;
            }

            nioChannelDistributor.registerChannelAsync(datagramChannel, 0, newConnection, new ConnectHandler(newConnection, completionHandlerToPass));
            return futureToReturn;
         }
      } catch (Exception var13) {
         if (newConnection != null) {
            newConnection.closeSilently();
         }

         if (completionHandler != null) {
            completionHandler.failed(var13);
         }

         return needFuture ? ReadyFutureImpl.create((Throwable)var13) : null;
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

   private static void abortConnection(UDPNIOConnection connection, CompletionHandler completionHandler, Throwable failure) {
      connection.closeSilently();
      if (completionHandler != null) {
         completionHandler.failed(failure);
      }

   }

   public static Builder builder(UDPNIOTransport transport) {
      return (new Builder()).setTransport(transport);
   }

   public static class Builder extends AbstractSocketConnectorHandler.Builder {
      private UDPNIOTransport transport;
      private Boolean reuseAddress;
      private Long timeout;
      private TimeUnit timeoutTimeunit;

      public UDPNIOConnectorHandler build() {
         UDPNIOConnectorHandler handler = (UDPNIOConnectorHandler)super.build();
         if (this.reuseAddress != null) {
            handler.setReuseAddress(this.reuseAddress);
         }

         if (this.timeout != null) {
            handler.setSyncConnectTimeout(this.timeout, this.timeoutTimeunit);
         }

         return handler;
      }

      public Builder setTransport(UDPNIOTransport transport) {
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
            throw new IllegalStateException("Unable to create UDPNIOConnectorHandler - transport is null");
         } else {
            return new UDPNIOConnectorHandler(this.transport);
         }
      }
   }

   private static class EnableReadHandler extends IOEventLifeCycleListener.Adapter {
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
         UDPNIOConnection connection = (UDPNIOConnection)context.getConnection();
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

   private final class ConnectHandler extends EmptyCompletionHandler {
      private final UDPNIOConnection connection;
      private final CompletionHandler completionHandler;

      private ConnectHandler(UDPNIOConnection connection, CompletionHandler completionHandler) {
         this.connection = connection;
         this.completionHandler = completionHandler;
      }

      public void completed(RegisterChannelResult result) {
         UDPNIOTransport transport = (UDPNIOTransport)UDPNIOConnectorHandler.this.transport;
         transport.registerChannelCompletionHandler.completed(result);

         try {
            this.connection.onConnect();
         } catch (Exception var4) {
            UDPNIOConnectorHandler.abortConnection(this.connection, this.completionHandler, var4);
         }

         if (this.connection.notifyReady()) {
            transport.fireIOEvent(IOEvent.CONNECTED, this.connection, new EnableReadHandler(this.completionHandler));
         }

      }

      public void failed(Throwable throwable) {
         UDPNIOConnectorHandler.abortConnection(this.connection, this.completionHandler, throwable);
      }

      // $FF: synthetic method
      ConnectHandler(UDPNIOConnection x1, CompletionHandler x2, Object x3) {
         this(x1, x2);
      }
   }
}
