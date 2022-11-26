package org.glassfish.grizzly.ssl;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseListener;
import org.glassfish.grizzly.CloseType;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.GenericCloseListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.PendingWriteQueueLimitExceededException;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.JdkVersion;

public class SSLFilter extends SSLBaseFilter {
   private static final Logger LOGGER = Grizzly.logger(SSLFilter.class);
   private static final boolean IS_JDK7_OR_HIGHER = JdkVersion.getJdkVersion().compareTo("1.7.0") >= 0;
   private final Attribute handshakeContextAttr;
   private final SSLEngineConfigurator clientSSLEngineConfigurator;
   private final ConnectionCloseListener closeListener;
   protected volatile int maxPendingBytes;

   public SSLFilter() {
      this((SSLEngineConfigurator)null, (SSLEngineConfigurator)null);
   }

   public SSLFilter(SSLEngineConfigurator serverSSLEngineConfigurator, SSLEngineConfigurator clientSSLEngineConfigurator) {
      this(serverSSLEngineConfigurator, clientSSLEngineConfigurator, true);
   }

   public SSLFilter(SSLEngineConfigurator serverSSLEngineConfigurator, SSLEngineConfigurator clientSSLEngineConfigurator, boolean renegotiateOnClientAuthWant) {
      super(serverSSLEngineConfigurator, renegotiateOnClientAuthWant);
      this.closeListener = new ConnectionCloseListener();
      this.maxPendingBytes = Integer.MAX_VALUE;
      if (clientSSLEngineConfigurator == null) {
         this.clientSSLEngineConfigurator = new SSLEngineConfigurator(SSLContextConfigurator.DEFAULT_CONFIG.createSSLContext(true), true, false, false);
      } else {
         this.clientSSLEngineConfigurator = clientSSLEngineConfigurator;
      }

      this.handshakeContextAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("SSLFilter-SSLHandshakeContextAttr");
   }

   public SSLEngineConfigurator getClientSSLEngineConfigurator() {
      return this.clientSSLEngineConfigurator;
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      if (ctx.getMessage() instanceof FileTransfer) {
         throw new IllegalStateException("TLS operations not supported with SendFile messages");
      } else {
         synchronized(connection) {
            SSLConnectionContext sslCtx = this.obtainSslConnectionContext(connection);
            SSLEngine sslEngine = sslCtx.getSslEngine();
            if (sslEngine != null && !SSLUtils.isHandshaking(sslEngine)) {
               return sslCtx.isServerMode() ? super.handleWrite(ctx) : this.accurateWrite(ctx, true);
            } else {
               if (sslEngine == null || !this.handshakeContextAttr.isSet((AttributeStorage)connection)) {
                  this.handshake(connection, (CompletionHandler)null, (Object)null, this.clientSSLEngineConfigurator, ctx, false);
               }

               return this.accurateWrite(ctx, false);
            }
         }
      }
   }

   public int getMaxPendingBytesPerConnection() {
      return this.maxPendingBytes;
   }

   public void setMaxPendingBytesPerConnection(int maxPendingBytes) {
      this.maxPendingBytes = maxPendingBytes;
   }

   public void handshake(Connection connection, CompletionHandler completionHandler) throws IOException {
      this.handshake(connection, completionHandler, (Object)null, this.clientSSLEngineConfigurator);
   }

   public void handshake(Connection connection, CompletionHandler completionHandler, Object dstAddress) throws IOException {
      this.handshake(connection, completionHandler, dstAddress, this.clientSSLEngineConfigurator);
   }

   public void handshake(Connection connection, CompletionHandler completionHandler, Object dstAddress, SSLEngineConfigurator sslEngineConfigurator) throws IOException {
      this.handshake(connection, completionHandler, dstAddress, sslEngineConfigurator, this.createContext(connection, FilterChainContext.Operation.WRITE), true);
   }

   protected void handshake(Connection connection, CompletionHandler completionHandler, Object dstAddress, SSLEngineConfigurator sslEngineConfigurator, FilterChainContext context, boolean forceBeginHandshake) throws IOException {
      SSLConnectionContext sslCtx = this.obtainSslConnectionContext(connection);
      SSLEngine sslEngine = sslCtx.getSslEngine();
      if (sslEngine == null) {
         sslEngine = this.createClientSSLEngine(sslCtx, sslEngineConfigurator);
         sslCtx.configure(sslEngine);
      } else if (!SSLUtils.isHandshaking(sslEngine)) {
         sslEngineConfigurator.configure(sslEngine);
      }

      this.notifyHandshakeStart(connection);
      if (forceBeginHandshake || !sslEngine.getSession().isValid()) {
         sslEngine.beginHandshake();
      }

      this.handshakeContextAttr.set((AttributeStorage)connection, new SSLHandshakeContext(connection, completionHandler));
      connection.addCloseListener((CloseListener)this.closeListener);
      synchronized(connection) {
         Buffer buffer = this.doHandshakeStep(sslCtx, context, (Buffer)null);

         assert buffer == null;

      }
   }

   private NextAction accurateWrite(FilterChainContext ctx, boolean isHandshakeComplete) throws IOException {
      Connection connection = ctx.getConnection();
      SSLHandshakeContext handshakeContext = (SSLHandshakeContext)this.handshakeContextAttr.get((AttributeStorage)connection);
      if (isHandshakeComplete && handshakeContext == null) {
         return super.handleWrite(ctx);
      } else {
         if (handshakeContext == null) {
            handshakeContext = new SSLHandshakeContext(connection, (CompletionHandler)null);
            this.handshakeContextAttr.set((AttributeStorage)connection, handshakeContext);
         }

         return !handshakeContext.add(ctx) ? super.handleWrite(ctx) : ctx.getSuspendAction();
      }
   }

   protected void notifyHandshakeComplete(Connection connection, SSLEngine sslEngine) {
      SSLHandshakeContext handshakeContext = (SSLHandshakeContext)this.handshakeContextAttr.get((AttributeStorage)connection);
      if (handshakeContext != null) {
         connection.removeCloseListener((CloseListener)this.closeListener);
         handshakeContext.completed(sslEngine);
         this.handshakeContextAttr.remove((AttributeStorage)connection);
      }

      super.notifyHandshakeComplete(connection, sslEngine);
   }

   protected void notifyHandshakeFailed(Connection connection, Throwable t) {
      SSLHandshakeContext handshakeContext = (SSLHandshakeContext)this.handshakeContextAttr.get((AttributeStorage)connection);
      if (handshakeContext != null) {
         connection.removeCloseListener((CloseListener)this.closeListener);
         handshakeContext.failed(t);
      }

      super.notifyHandshakeFailed(connection, t);
   }

   protected Buffer doHandshakeStep(SSLConnectionContext sslCtx, FilterChainContext ctx, Buffer inputBuffer, Buffer tmpAppBuffer0) throws IOException {
      try {
         return super.doHandshakeStep(sslCtx, ctx, inputBuffer, tmpAppBuffer0);
      } catch (IOException var7) {
         SSLHandshakeContext context = (SSLHandshakeContext)this.handshakeContextAttr.get((AttributeStorage)ctx.getConnection());
         if (context != null) {
            context.failed(var7);
         }

         throw var7;
      }
   }

   protected SSLEngine createClientSSLEngine(SSLConnectionContext sslCtx, SSLEngineConfigurator sslEngineConfigurator) {
      return IS_JDK7_OR_HIGHER ? sslEngineConfigurator.createSSLEngine(SSLFilter.HostNameResolver.getPeerHostName(sslCtx.getConnection()), -1) : sslEngineConfigurator.createSSLEngine();
   }

   private static class HostNameResolver {
      public static String getPeerHostName(Connection connection) {
         Object addr = connection.getPeerAddress();
         return addr instanceof InetSocketAddress ? ((InetSocketAddress)addr).getHostString() : null;
      }
   }

   private final class ConnectionCloseListener implements GenericCloseListener {
      private ConnectionCloseListener() {
      }

      public void onClosed(Closeable closeable, CloseType type) throws IOException {
         Connection connection = (Connection)closeable;
         SSLHandshakeContext handshakeContext = (SSLHandshakeContext)SSLFilter.this.handshakeContextAttr.get((AttributeStorage)connection);
         if (handshakeContext != null) {
            handshakeContext.failed(new EOFException());
            SSLFilter.this.handshakeContextAttr.remove((AttributeStorage)connection);
         }

      }

      // $FF: synthetic method
      ConnectionCloseListener(Object x1) {
         this();
      }
   }

   private final class SSLHandshakeContext {
      private CompletionHandler completionHandler;
      private final Connection connection;
      private List pendingWriteContexts;
      private int sizeInBytes = 0;
      private Throwable error;
      private boolean isComplete;

      public SSLHandshakeContext(Connection connection, CompletionHandler completionHandler) {
         this.connection = connection;
         this.completionHandler = completionHandler;
      }

      public boolean add(FilterChainContext context) throws IOException {
         if (this.error != null) {
            throw Exceptions.makeIOException(this.error);
         } else if (this.isComplete) {
            return false;
         } else {
            Buffer buffer = (Buffer)context.getMessage();
            int newSize = this.sizeInBytes + buffer.remaining();
            if (newSize > SSLFilter.this.maxPendingBytes) {
               throw new PendingWriteQueueLimitExceededException("Max queued data limit exceeded: " + newSize + '>' + SSLFilter.this.maxPendingBytes);
            } else {
               this.sizeInBytes = newSize;
               if (this.pendingWriteContexts == null) {
                  this.pendingWriteContexts = new LinkedList();
               }

               this.pendingWriteContexts.add(context);
               return true;
            }
         }
      }

      public void completed(SSLEngine engine) {
         try {
            synchronized(this.connection) {
               this.isComplete = true;
               CompletionHandler completionHandlerLocal = this.completionHandler;
               this.completionHandler = null;
               if (completionHandlerLocal != null) {
                  completionHandlerLocal.completed(engine);
               }

               this.resumePendingWrites();
            }
         } catch (Exception var6) {
            SSLFilter.LOGGER.log(Level.FINE, "Unexpected SSLHandshakeContext.completed() error", var6);
            this.failed(var6);
         }

      }

      public void failed(Throwable throwable) {
         synchronized(this.connection) {
            if (this.error == null) {
               this.error = throwable;
               CompletionHandler completionHandlerLocal = this.completionHandler;
               this.completionHandler = null;
               if (completionHandlerLocal != null) {
                  completionHandlerLocal.failed(throwable);
               }

               this.connection.closeWithReason(Exceptions.makeIOException(throwable));
               this.resumePendingWrites();
            }
         }
      }

      private void resumePendingWrites() {
         List pendingWriteContextsLocal = this.pendingWriteContexts;
         this.pendingWriteContexts = null;
         if (pendingWriteContextsLocal != null) {
            Iterator var2 = pendingWriteContextsLocal.iterator();

            while(var2.hasNext()) {
               FilterChainContext ctx = (FilterChainContext)var2.next();

               try {
                  ctx.resume();
               } catch (Exception var5) {
               }
            }

            pendingWriteContextsLocal.clear();
            this.sizeInBytes = 0;
         }

      }
   }
}
