package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.SelectorRunner;
import org.glassfish.grizzly.utils.Holder;
import org.glassfish.grizzly.utils.NullaryFunction;

public class TCPNIOConnection extends NIOConnection {
   private static final Logger LOGGER = Grizzly.logger(TCPNIOConnection.class);
   Holder localSocketAddressHolder;
   Holder peerSocketAddressHolder;
   private int readBufferSize = -1;
   private int writeBufferSize = -1;
   private AtomicReference connectHandlerRef;

   public TCPNIOConnection(TCPNIOTransport transport, SelectableChannel channel) {
      super(transport);
      this.channel = channel;
   }

   protected void setSelectionKey(SelectionKey selectionKey) {
      super.setSelectionKey(selectionKey);
   }

   protected void setSelectorRunner(SelectorRunner selectorRunner) {
      super.setSelectorRunner(selectorRunner);
   }

   protected void preClose() {
      this.checkConnectFailed((Throwable)null);
      super.preClose();
   }

   protected boolean notifyReady() {
      return connectCloseSemaphoreUpdater.compareAndSet(this, (Object)null, NOTIFICATION_INITIALIZED);
   }

   public SocketAddress getPeerAddress() {
      return (SocketAddress)this.peerSocketAddressHolder.get();
   }

   public SocketAddress getLocalAddress() {
      return (SocketAddress)this.localSocketAddressHolder.get();
   }

   protected void resetProperties() {
      if (this.channel != null) {
         this.setReadBufferSize(this.transport.getReadBufferSize());
         this.setWriteBufferSize(this.transport.getWriteBufferSize());
         int transportMaxAsyncWriteQueueSize = ((TCPNIOTransport)this.transport).getAsyncQueueIO().getWriter().getMaxPendingBytesPerConnection();
         this.setMaxAsyncWriteQueueSize(transportMaxAsyncWriteQueueSize == -2 ? this.getWriteBufferSize() * 4 : transportMaxAsyncWriteQueueSize);
         this.localSocketAddressHolder = Holder.lazyHolder(new NullaryFunction() {
            public SocketAddress evaluate() {
               return ((SocketChannel)TCPNIOConnection.this.channel).socket().getLocalSocketAddress();
            }
         });
         this.peerSocketAddressHolder = Holder.lazyHolder(new NullaryFunction() {
            public SocketAddress evaluate() {
               return ((SocketChannel)TCPNIOConnection.this.channel).socket().getRemoteSocketAddress();
            }
         });
      }

   }

   public int getReadBufferSize() {
      if (this.readBufferSize >= 0) {
         return this.readBufferSize;
      } else {
         try {
            this.readBufferSize = ((SocketChannel)this.channel).socket().getReceiveBufferSize();
         } catch (IOException var2) {
            LOGGER.log(Level.FINE, LogMessages.WARNING_GRIZZLY_CONNECTION_GET_READBUFFER_SIZE_EXCEPTION(), var2);
            this.readBufferSize = 0;
         }

         return this.readBufferSize;
      }
   }

   public void setReadBufferSize(int readBufferSize) {
      if (readBufferSize > 0) {
         try {
            int currentReadBufferSize = ((SocketChannel)this.channel).socket().getReceiveBufferSize();
            if (readBufferSize > currentReadBufferSize) {
               ((SocketChannel)this.channel).socket().setReceiveBufferSize(readBufferSize);
            }

            this.readBufferSize = readBufferSize;
         } catch (IOException var3) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_SET_READBUFFER_SIZE_EXCEPTION(), var3);
         }
      }

   }

   public int getWriteBufferSize() {
      if (this.writeBufferSize >= 0) {
         return this.writeBufferSize;
      } else {
         try {
            this.writeBufferSize = ((SocketChannel)this.channel).socket().getSendBufferSize();
         } catch (IOException var2) {
            LOGGER.log(Level.FINE, LogMessages.WARNING_GRIZZLY_CONNECTION_GET_WRITEBUFFER_SIZE_EXCEPTION(), var2);
            this.writeBufferSize = 0;
         }

         return this.writeBufferSize;
      }
   }

   public void setWriteBufferSize(int writeBufferSize) {
      if (writeBufferSize > 0) {
         try {
            int currentSendBufferSize = ((SocketChannel)this.channel).socket().getSendBufferSize();
            if (writeBufferSize > currentSendBufferSize) {
               ((SocketChannel)this.channel).socket().setSendBufferSize(writeBufferSize);
            }

            this.writeBufferSize = writeBufferSize;
         } catch (IOException var3) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_SET_WRITEBUFFER_SIZE_EXCEPTION(), var3);
         }
      }

   }

   protected final void setConnectResultHandler(ConnectResultHandler connectHandler) {
      this.connectHandlerRef = new AtomicReference(connectHandler);
   }

   protected final void onConnect() throws IOException {
      AtomicReference localRef = this.connectHandlerRef;
      ConnectResultHandler localConnectHandler;
      if (localRef != null && (localConnectHandler = (ConnectResultHandler)localRef.getAndSet((Object)null)) != null) {
         localConnectHandler.connected();
         this.connectHandlerRef = null;
      }

      notifyProbesConnect(this);
   }

   protected final void checkConnectFailed(Throwable failure) {
      AtomicReference localRef = this.connectHandlerRef;
      ConnectResultHandler localConnectHandler;
      if (localRef != null && (localConnectHandler = (ConnectResultHandler)localRef.getAndSet((Object)null)) != null) {
         if (failure == null) {
            failure = new IOException("closed");
         }

         localConnectHandler.failed((Throwable)failure);
         this.connectHandlerRef = null;
      }

   }

   protected void terminate0(CompletionHandler completionHandler, CloseReason closeReason) {
      super.terminate0(completionHandler, closeReason);
   }

   protected final void onRead(Buffer data, int size) {
      if (size > 0) {
         notifyProbesRead(this, data, size);
      }

      this.checkEmptyRead(size);
   }

   protected void enableInitialOpRead() throws IOException {
      super.enableInitialOpRead();
   }

   protected final void onWrite(Buffer data, long size) {
      notifyProbesWrite(this, data, size);
   }

   public boolean canWrite() {
      return this.transport.getWriter(this).canWrite(this);
   }

   /** @deprecated */
   @Deprecated
   public boolean canWrite(int length) {
      return this.transport.getWriter(this).canWrite(this);
   }

   public void notifyCanWrite(WriteHandler writeHandler) {
      this.transport.getWriter(this).notifyWritePossible(this, writeHandler);
   }

   /** @deprecated */
   @Deprecated
   public void notifyCanWrite(WriteHandler handler, int length) {
      this.transport.getWriter(this).notifyWritePossible(this, handler);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("TCPNIOConnection");
      sb.append("{localSocketAddress=").append(this.localSocketAddressHolder);
      sb.append(", peerSocketAddress=").append(this.peerSocketAddressHolder);
      sb.append('}');
      return sb.toString();
   }

   protected interface ConnectResultHandler {
      void connected() throws IOException;

      void failed(Throwable var1);
   }
}
