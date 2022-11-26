package org.glassfish.grizzly.ssl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.Futures;

public class SSLBaseFilter extends BaseFilter {
   private static final Logger LOGGER = Grizzly.logger(SSLBaseFilter.class);
   protected static final MessageCloner COPY_CLONER = new OnWriteCopyCloner();
   private static final SSLConnectionContext.Allocator MM_ALLOCATOR = new SSLConnectionContext.Allocator() {
      public Buffer grow(SSLConnectionContext sslCtx, Buffer oldBuffer, int newSize) {
         MemoryManager mm = sslCtx.getConnection().getMemoryManager();
         return oldBuffer == null ? mm.allocate(newSize) : mm.reallocate(oldBuffer, newSize);
      }
   };
   private static final SSLConnectionContext.Allocator OUTPUT_BUFFER_ALLOCATOR = new SSLConnectionContext.Allocator() {
      public Buffer grow(SSLConnectionContext sslCtx, Buffer oldBuffer, int newSize) {
         return SSLUtils.allocateOutputBuffer(newSize);
      }
   };
   private final SSLEngineConfigurator serverSSLEngineConfigurator;
   private final boolean renegotiateOnClientAuthWant;
   private volatile boolean renegotiationDisabled;
   protected final Set handshakeListeners;
   private long handshakeTimeoutMillis;
   private SSLTransportFilterWrapper optimizedTransportFilter;

   public SSLBaseFilter() {
      this((SSLEngineConfigurator)null);
   }

   public SSLBaseFilter(SSLEngineConfigurator serverSSLEngineConfigurator) {
      this(serverSSLEngineConfigurator, true);
   }

   public SSLBaseFilter(SSLEngineConfigurator serverSSLEngineConfigurator, boolean renegotiateOnClientAuthWant) {
      this.handshakeListeners = Collections.newSetFromMap(new ConcurrentHashMap(2));
      this.handshakeTimeoutMillis = -1L;
      this.renegotiateOnClientAuthWant = renegotiateOnClientAuthWant;
      this.serverSSLEngineConfigurator = serverSSLEngineConfigurator != null ? serverSSLEngineConfigurator : new SSLEngineConfigurator(SSLContextConfigurator.DEFAULT_CONFIG.createSSLContext(true), false, false, false);
   }

   public boolean isRenegotiateOnClientAuthWant() {
      return this.renegotiateOnClientAuthWant;
   }

   public SSLEngineConfigurator getServerSSLEngineConfigurator() {
      return this.serverSSLEngineConfigurator;
   }

   public void addHandshakeListener(HandshakeListener listener) {
      this.handshakeListeners.add(listener);
   }

   public void removeHandshakeListener(HandshakeListener listener) {
      this.handshakeListeners.remove(listener);
   }

   public long getHandshakeTimeout(TimeUnit timeUnit) {
      return this.handshakeTimeoutMillis < 0L ? -1L : timeUnit.convert(this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
   }

   public void setHandshakeTimeout(long handshakeTimeout, TimeUnit timeUnit) {
      if (handshakeTimeout < 0L) {
         this.handshakeTimeoutMillis = -1L;
      } else {
         this.handshakeTimeoutMillis = TimeUnit.MILLISECONDS.convert(handshakeTimeout, timeUnit);
      }

   }

   public void setRenegotiationDisabled(boolean renegotiationDisabled) {
      this.renegotiationDisabled = renegotiationDisabled;
   }

   protected SSLTransportFilterWrapper getOptimizedTransportFilter(TransportFilter childFilter) {
      if (this.optimizedTransportFilter == null || this.optimizedTransportFilter.wrappedFilter != childFilter) {
         this.optimizedTransportFilter = this.createOptimizedTransportFilter(childFilter);
      }

      return this.optimizedTransportFilter;
   }

   protected SSLTransportFilterWrapper createOptimizedTransportFilter(TransportFilter childFilter) {
      return new SSLTransportFilterWrapper(childFilter, this);
   }

   public void onRemoved(FilterChain filterChain) {
      if (this.optimizedTransportFilter != null) {
         int sslTransportFilterIdx = filterChain.indexOf(this.optimizedTransportFilter);
         if (sslTransportFilterIdx >= 0) {
            SSLTransportFilterWrapper wrapper = (SSLTransportFilterWrapper)filterChain.get(sslTransportFilterIdx);
            filterChain.set(sslTransportFilterIdx, wrapper.wrappedFilter);
         }
      }

   }

   public void onAdded(FilterChain filterChain) {
      int sslTransportFilterIdx = filterChain.indexOfType(SSLTransportFilterWrapper.class);
      if (sslTransportFilterIdx == -1) {
         int transportFilterIdx = filterChain.indexOfType(TransportFilter.class);
         if (transportFilterIdx >= 0) {
            filterChain.set(transportFilterIdx, this.getOptimizedTransportFilter((TransportFilter)filterChain.get(transportFilterIdx)));
         }
      }

   }

   public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
      if (event.type() == "CERT_EVENT") {
         CertificateEvent ce = (CertificateEvent)event;

         NextAction var4;
         try {
            var4 = ctx.getSuspendAction();
         } finally {
            this.getPeerCertificateChain(this.obtainSslConnectionContext(ctx.getConnection()), ctx, ce.needClientAuth, ce.certsFuture);
         }

         return var4;
      } else {
         return ctx.getInvokeAction();
      }
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      SSLConnectionContext sslCtx = this.obtainSslConnectionContext(connection);
      SSLEngine sslEngine = sslCtx.getSslEngine();
      if (sslEngine != null && !SSLUtils.isHandshaking(sslEngine)) {
         return this.unwrapAll(ctx, sslCtx);
      } else {
         if (sslEngine == null) {
            sslEngine = this.serverSSLEngineConfigurator.createSSLEngine();
            sslEngine.beginHandshake();
            sslCtx.configure(sslEngine);
            this.notifyHandshakeStart(connection);
         }

         Buffer buffer = this.handshakeTimeoutMillis >= 0L ? this.doHandshakeSync(sslCtx, ctx, (Buffer)ctx.getMessage(), this.handshakeTimeoutMillis) : SSLUtils.makeInputRemainder(sslCtx, ctx, this.doHandshakeStep(sslCtx, ctx, (Buffer)ctx.getMessage()));
         boolean hasRemaining = buffer != null && buffer.hasRemaining();
         boolean isHandshaking = SSLUtils.isHandshaking(sslEngine);
         if (!isHandshaking) {
            this.notifyHandshakeComplete(connection, sslEngine);
            FilterChain connectionFilterChain = sslCtx.getNewConnectionFilterChain();
            sslCtx.setNewConnectionFilterChain((FilterChain)null);
            if (connectionFilterChain != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "Applying new FilterChain afterSSLHandshake. Connection={0} filterchain={1}", new Object[]{connection, connectionFilterChain});
               }

               connection.setProcessor(connectionFilterChain);
               if (hasRemaining) {
                  NextAction suspendAction = ctx.getSuspendAction();
                  ctx.setMessage(buffer);
                  ctx.suspend();
                  FilterChainContext newContext = obtainProtocolChainContext(ctx, connectionFilterChain);
                  ProcessorExecutor.execute(newContext.getInternalContext());
                  return suspendAction;
               }

               return ctx.getStopAction();
            }

            if (hasRemaining) {
               ctx.setMessage(buffer);
               return this.unwrapAll(ctx, sslCtx);
            }
         }

         return ctx.getStopAction(buffer);
      }
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      if (ctx.getMessage() instanceof FileTransfer) {
         throw new IllegalStateException("TLS operations not supported with SendFile messages");
      } else {
         Connection connection = ctx.getConnection();
         synchronized(connection) {
            Buffer output = this.wrapAll(ctx, this.obtainSslConnectionContext(connection));
            FilterChainContext.TransportContext transportContext = ctx.getTransportContext();
            ctx.write((Object)null, output, transportContext.getCompletionHandler(), transportContext.getPushBackHandler(), COPY_CLONER, transportContext.isBlocking());
            return ctx.getStopAction();
         }
      }
   }

   protected NextAction unwrapAll(FilterChainContext ctx, SSLConnectionContext sslCtx) throws SSLException {
      Buffer input = (Buffer)ctx.getMessage();
      Buffer output = null;
      boolean isClosed = false;

      while(true) {
         int len = SSLUtils.getSSLPacketSize(input);
         if (len != -1 && input.remaining() >= len) {
            label52: {
               SSLConnectionContext.SslResult result = sslCtx.unwrap(len, input, output, MM_ALLOCATOR);
               output = result.getOutput();
               if (result.isError()) {
                  output.dispose();
                  throw result.getError();
               }

               if (SSLUtils.isHandshaking(sslCtx.getSslEngine())) {
                  if (result.getSslEngineResult().getStatus() != Status.CLOSED) {
                     input = this.rehandshake(ctx, sslCtx);
                  } else {
                     input = this.silentRehandshake(ctx, sslCtx);
                     isClosed = true;
                  }

                  if (input == null) {
                     break label52;
                  }
               }

               switch (result.getSslEngineResult().getStatus()) {
                  case OK:
                     if (input.hasRemaining()) {
                        continue;
                     }
                     break;
                  case CLOSED:
                     isClosed = true;
                     break;
                  default:
                     throw new IllegalStateException("Unexpected status: " + result.getSslEngineResult().getStatus());
               }
            }
         }

         if (output != null) {
            output.trim();
            if (output.hasRemaining() || isClosed) {
               ctx.setMessage(output);
               return ctx.getInvokeAction(SSLUtils.makeInputRemainder(sslCtx, ctx, input));
            }
         }

         return ctx.getStopAction(SSLUtils.makeInputRemainder(sslCtx, ctx, input));
      }
   }

   protected Buffer wrapAll(FilterChainContext ctx, SSLConnectionContext sslCtx) throws SSLException {
      Buffer input = (Buffer)ctx.getMessage();
      Buffer output = sslCtx.wrapAll(input, OUTPUT_BUFFER_ALLOCATOR);
      input.tryDispose();
      return output;
   }

   protected Buffer doHandshakeSync(SSLConnectionContext sslCtx, FilterChainContext ctx, Buffer inputBuffer, long timeoutMillis) throws IOException {
      Connection connection = ctx.getConnection();
      SSLEngine sslEngine = sslCtx.getSslEngine();
      Buffer tmpAppBuffer = SSLUtils.allocateOutputBuffer(sslCtx.getAppBufferSize());
      long oldReadTimeout = connection.getReadTimeout(TimeUnit.MILLISECONDS);

      try {
         connection.setReadTimeout(timeoutMillis, TimeUnit.MILLISECONDS);

         for(inputBuffer = SSLUtils.makeInputRemainder(sslCtx, ctx, this.doHandshakeStep(sslCtx, ctx, inputBuffer, tmpAppBuffer)); SSLUtils.isHandshaking(sslEngine); inputBuffer = SSLUtils.makeInputRemainder(sslCtx, ctx, this.doHandshakeStep(sslCtx, ctx, inputBuffer, tmpAppBuffer))) {
            ReadResult rr = ctx.read();
            Buffer newBuf = (Buffer)rr.getMessage();
            inputBuffer = Buffers.appendBuffers(ctx.getMemoryManager(), inputBuffer, newBuf);
         }
      } finally {
         tmpAppBuffer.dispose();
         connection.setReadTimeout(oldReadTimeout, TimeUnit.MILLISECONDS);
      }

      return inputBuffer;
   }

   protected Buffer doHandshakeStep(SSLConnectionContext sslCtx, FilterChainContext ctx, Buffer inputBuffer) throws IOException {
      return this.doHandshakeStep(sslCtx, ctx, inputBuffer, (Buffer)null);
   }

   protected Buffer doHandshakeStep(SSLConnectionContext sslCtx, FilterChainContext ctx, Buffer inputBuffer, Buffer tmpAppBuffer0) throws IOException {
      SSLEngine sslEngine = sslCtx.getSslEngine();
      Connection connection = ctx.getConnection();
      boolean isLoggingFinest = LOGGER.isLoggable(Level.FINEST);
      Buffer tmpInputToDispose = null;
      Buffer tmpNetBuffer = null;
      Buffer tmpAppBuffer = tmpAppBuffer0;

      try {
         SSLEngineResult.HandshakeStatus handshakeStatus = sslEngine.getHandshakeStatus();

         do {
            if (isLoggingFinest) {
               LOGGER.log(Level.FINEST, "Loop Engine: {0} handshakeStatus={1}", new Object[]{sslEngine, sslEngine.getHandshakeStatus()});
            }

            switch (handshakeStatus) {
               case NEED_UNWRAP:
                  if (isLoggingFinest) {
                     LOGGER.log(Level.FINEST, "NEED_UNWRAP Engine: {0}", sslEngine);
                  }

                  if (inputBuffer == null || !inputBuffer.hasRemaining()) {
                     return inputBuffer;
                  }

                  int expectedLength = SSLUtils.getSSLPacketSize(inputBuffer);
                  if (expectedLength == -1 || inputBuffer.remaining() < expectedLength) {
                     return inputBuffer;
                  }

                  if (tmpAppBuffer == null) {
                     tmpAppBuffer = SSLUtils.allocateOutputBuffer(sslCtx.getAppBufferSize());
                  }

                  SSLEngineResult sslEngineResult = SSLUtils.handshakeUnwrap(expectedLength, sslCtx, inputBuffer, tmpAppBuffer);
                  if (!inputBuffer.hasRemaining()) {
                     tmpInputToDispose = inputBuffer;
                     inputBuffer = null;
                  }

                  SSLEngineResult.Status status = sslEngineResult.getStatus();
                  if (status == Status.BUFFER_UNDERFLOW || status == Status.BUFFER_OVERFLOW) {
                     throw new SSLException("SSL unwrap error: " + status);
                  }

                  handshakeStatus = sslEngine.getHandshakeStatus();
                  break;
               case NEED_WRAP:
                  if (isLoggingFinest) {
                     LOGGER.log(Level.FINEST, "NEED_WRAP Engine: {0}", sslEngine);
                  }

                  tmpNetBuffer = SSLUtils.handshakeWrap(connection, sslCtx, tmpNetBuffer);
                  handshakeStatus = sslEngine.getHandshakeStatus();
                  break;
               case NEED_TASK:
                  if (isLoggingFinest) {
                     LOGGER.log(Level.FINEST, "NEED_TASK Engine: {0}", sslEngine);
                  }

                  SSLUtils.executeDelegatedTask(sslEngine);
                  handshakeStatus = sslEngine.getHandshakeStatus();
                  break;
               case FINISHED:
               case NOT_HANDSHAKING:
                  return inputBuffer;
            }
         } while(handshakeStatus != HandshakeStatus.FINISHED);
      } catch (IOException var18) {
         this.notifyHandshakeFailed(connection, var18);
         throw var18;
      } finally {
         if (tmpAppBuffer0 == null && tmpAppBuffer != null) {
            tmpAppBuffer.dispose();
         }

         if (tmpInputToDispose != null) {
            tmpInputToDispose.tryDispose();
            inputBuffer = null;
         } else if (inputBuffer != null) {
            inputBuffer.shrink();
         }

         if (tmpNetBuffer != null) {
            if (inputBuffer != null) {
               inputBuffer = SSLUtils.makeInputRemainder(sslCtx, ctx, inputBuffer);
            }

            ctx.write(tmpNetBuffer);
         }

      }

      return inputBuffer;
   }

   protected void renegotiate(SSLConnectionContext sslCtx, FilterChainContext context) throws IOException {
      if (!this.renegotiationDisabled) {
         SSLEngine sslEngine = sslCtx.getSslEngine();
         if (!sslEngine.getWantClientAuth() || this.renegotiateOnClientAuthWant) {
            boolean authConfigured = sslEngine.getWantClientAuth() || sslEngine.getNeedClientAuth();
            if (!authConfigured) {
               sslEngine.setNeedClientAuth(true);
            }

            sslEngine.getSession().invalidate();

            try {
               sslEngine.beginHandshake();
            } catch (SSLHandshakeException var9) {
               if (var9.toString().toLowerCase().contains("insecure renegotiation") && LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.severe("Secure SSL/TLS renegotiation is not supported by the peer.  This is most likely due to the peer using an older SSL/TLS implementation that does not implement RFC 5746.");
               }

               throw var9;
            }

            try {
               this.rehandshake(context, sslCtx);
            } finally {
               if (!authConfigured) {
                  sslEngine.setNeedClientAuth(false);
               }

            }

         }
      }
   }

   private Buffer silentRehandshake(FilterChainContext context, SSLConnectionContext sslCtx) throws SSLException {
      try {
         return this.doHandshakeSync(sslCtx, context, (Buffer)null, this.handshakeTimeoutMillis);
      } catch (Throwable var4) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Error during graceful ssl connection close", var4);
         }

         if (var4 instanceof SSLException) {
            throw (SSLException)var4;
         } else {
            throw new SSLException("Error during re-handshaking", var4);
         }
      }
   }

   private Buffer rehandshake(FilterChainContext context, SSLConnectionContext sslCtx) throws SSLException {
      Connection c = context.getConnection();
      this.notifyHandshakeStart(c);

      try {
         Buffer buffer = this.doHandshakeSync(sslCtx, context, (Buffer)null, this.handshakeTimeoutMillis);
         this.notifyHandshakeComplete(c, sslCtx.getSslEngine());
         return buffer;
      } catch (Throwable var5) {
         this.notifyHandshakeFailed(c, var5);
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Error during re-handshaking", var5);
         }

         if (var5 instanceof SSLException) {
            throw (SSLException)var5;
         } else {
            throw new SSLException("Error during re-handshaking", var5);
         }
      }
   }

   protected void getPeerCertificateChain(final SSLConnectionContext sslCtx, final FilterChainContext context, boolean needClientAuth, final FutureImpl certFuture) {
      Certificate[] certs = getPeerCertificates(sslCtx);
      if (certs != null) {
         certFuture.result(certs);
      } else {
         if (needClientAuth) {
            Transport transport = context.getConnection().getTransport();
            ExecutorService threadPool = transport.getWorkerThreadPool();
            if (threadPool == null) {
               threadPool = transport.getKernelThreadPool();
            }

            threadPool.submit(new Runnable() {
               public void run() {
                  try {
                     try {
                        SSLBaseFilter.this.renegotiate(sslCtx, context);
                     } catch (IOException var6) {
                        certFuture.failure(var6);
                        return;
                     }

                     Certificate[] certs = SSLBaseFilter.getPeerCertificates(sslCtx);
                     if (certs == null) {
                        certFuture.result((Object)null);
                     } else {
                        X509Certificate[] x509Certs = SSLBaseFilter.extractX509Certs(certs);
                        if (x509Certs != null && x509Certs.length >= 1) {
                           certFuture.result(x509Certs);
                        } else {
                           certFuture.result((Object)null);
                        }
                     }
                  } finally {
                     context.resume(context.getStopAction());
                  }
               }
            });
         }

      }
   }

   protected SSLConnectionContext obtainSslConnectionContext(Connection connection) {
      SSLConnectionContext sslCtx = (SSLConnectionContext)SSLUtils.SSL_CTX_ATTR.get((AttributeStorage)connection);
      if (sslCtx == null) {
         sslCtx = this.createSslConnectionContext(connection);
         SSLUtils.SSL_CTX_ATTR.set((AttributeStorage)connection, sslCtx);
      }

      return sslCtx;
   }

   protected SSLConnectionContext createSslConnectionContext(Connection connection) {
      return new SSLConnectionContext(connection);
   }

   private static FilterChainContext obtainProtocolChainContext(FilterChainContext ctx, FilterChain completeProtocolFilterChain) {
      FilterChainContext newFilterChainContext = completeProtocolFilterChain.obtainFilterChainContext(ctx.getConnection(), ctx.getStartIdx(), completeProtocolFilterChain.size(), ctx.getFilterIdx());
      newFilterChainContext.setAddressHolder(ctx.getAddressHolder());
      newFilterChainContext.setMessage(ctx.getMessage());
      newFilterChainContext.getInternalContext().setIoEvent(IOEvent.READ);
      newFilterChainContext.getInternalContext().addLifeCycleListener(new InternalProcessingHandler(ctx));
      return newFilterChainContext;
   }

   private static X509Certificate[] extractX509Certs(Certificate[] certs) {
      X509Certificate[] x509Certs = new X509Certificate[certs.length];
      int i = 0;

      for(int len = certs.length; i < len; ++i) {
         if (certs[i] instanceof X509Certificate) {
            x509Certs[i] = (X509Certificate)certs[i];
         } else {
            try {
               byte[] buffer = certs[i].getEncoded();
               CertificateFactory cf = CertificateFactory.getInstance("X.509");
               ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
               x509Certs[i] = (X509Certificate)cf.generateCertificate(stream);
            } catch (Exception var7) {
               LOGGER.log(Level.INFO, "Error translating cert " + certs[i], var7);
               return null;
            }
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Cert #{0} = {1}", new Object[]{i, x509Certs[i]});
         }
      }

      return x509Certs;
   }

   private static Certificate[] getPeerCertificates(SSLConnectionContext sslCtx) {
      try {
         return sslCtx.getSslEngine().getSession().getPeerCertificates();
      } catch (Throwable var2) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Error getting client certs", var2);
         }

         return null;
      }
   }

   protected void notifyHandshakeStart(Connection connection) {
      if (!this.handshakeListeners.isEmpty()) {
         Iterator var2 = this.handshakeListeners.iterator();

         while(var2.hasNext()) {
            HandshakeListener listener = (HandshakeListener)var2.next();
            listener.onStart(connection);
         }
      }

   }

   protected void notifyHandshakeComplete(Connection connection, SSLEngine sslEngine) {
      if (!this.handshakeListeners.isEmpty()) {
         Iterator var3 = this.handshakeListeners.iterator();

         while(var3.hasNext()) {
            HandshakeListener listener = (HandshakeListener)var3.next();
            listener.onComplete(connection);
         }
      }

   }

   protected void notifyHandshakeFailed(Connection connection, Throwable t) {
      if (!this.handshakeListeners.isEmpty()) {
         Iterator var3 = this.handshakeListeners.iterator();

         while(var3.hasNext()) {
            HandshakeListener listener = (HandshakeListener)var3.next();
            listener.onFailure(connection, t);
         }
      }

   }

   private static final class OnWriteCopyCloner implements MessageCloner {
      private OnWriteCopyCloner() {
      }

      public Buffer clone(Connection connection, Buffer originalMessage) {
         SSLConnectionContext sslCtx = SSLUtils.getSslConnectionContext(connection);
         int copyThreshold = sslCtx.getNetBufferSize() / 2;
         Buffer lastOutputBuffer = sslCtx.resetLastOutputBuffer();
         int totalRemaining = originalMessage.remaining();
         if (totalRemaining < copyThreshold) {
            return SSLUtils.move(connection.getMemoryManager(), originalMessage);
         } else if (lastOutputBuffer.remaining() < copyThreshold) {
            Buffer tmpBuf = SSLUtils.copy(connection.getMemoryManager(), originalMessage);
            if (originalMessage.isComposite()) {
               ((CompositeBuffer)originalMessage).replace(lastOutputBuffer, tmpBuf);
            } else {
               assert originalMessage == lastOutputBuffer;
            }

            lastOutputBuffer.tryDispose();
            return tmpBuf;
         } else {
            return originalMessage;
         }
      }

      // $FF: synthetic method
      OnWriteCopyCloner(Object x0) {
         this();
      }
   }

   protected static class SSLTransportFilterWrapper extends TransportFilter {
      protected final TransportFilter wrappedFilter;
      protected final SSLBaseFilter sslBaseFilter;

      public SSLTransportFilterWrapper(TransportFilter transportFilter, SSLBaseFilter sslBaseFilter) {
         this.wrappedFilter = transportFilter;
         this.sslBaseFilter = sslBaseFilter;
      }

      public NextAction handleAccept(FilterChainContext ctx) throws IOException {
         return this.wrappedFilter.handleAccept(ctx);
      }

      public NextAction handleConnect(FilterChainContext ctx) throws IOException {
         return this.wrappedFilter.handleConnect(ctx);
      }

      public NextAction handleRead(FilterChainContext ctx) throws IOException {
         Connection connection = ctx.getConnection();
         SSLConnectionContext sslCtx = this.sslBaseFilter.obtainSslConnectionContext(connection);
         if (sslCtx.getSslEngine() == null) {
            SSLEngine sslEngine = this.sslBaseFilter.serverSSLEngineConfigurator.createSSLEngine();
            sslEngine.beginHandshake();
            sslCtx.configure(sslEngine);
            this.sslBaseFilter.notifyHandshakeStart(connection);
         }

         ctx.setMessage(SSLUtils.allowDispose(SSLUtils.allocateInputBuffer(sslCtx)));
         return this.wrappedFilter.handleRead(ctx);
      }

      public NextAction handleWrite(FilterChainContext ctx) throws IOException {
         return this.wrappedFilter.handleWrite(ctx);
      }

      public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
         return this.wrappedFilter.handleEvent(ctx, event);
      }

      public NextAction handleClose(FilterChainContext ctx) throws IOException {
         return this.wrappedFilter.handleClose(ctx);
      }

      public void onAdded(FilterChain filterChain) {
         this.wrappedFilter.onAdded(filterChain);
      }

      public void onFilterChainChanged(FilterChain filterChain) {
         this.wrappedFilter.onFilterChainChanged(filterChain);
      }

      public void onRemoved(FilterChain filterChain) {
         this.wrappedFilter.onRemoved(filterChain);
      }

      public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
         this.wrappedFilter.exceptionOccurred(ctx, error);
      }

      public FilterChainContext createContext(Connection connection, FilterChainContext.Operation operation) {
         return this.wrappedFilter.createContext(connection, operation);
      }
   }

   public interface HandshakeListener {
      void onStart(Connection var1);

      void onComplete(Connection var1);

      void onFailure(Connection var1, Throwable var2);
   }

   private static class InternalProcessingHandler extends IOEventLifeCycleListener.Adapter {
      private final FilterChainContext parentContext;

      private InternalProcessingHandler(FilterChainContext parentContext) {
         this.parentContext = parentContext;
      }

      public void onComplete(Context context, Object data) throws IOException {
         this.parentContext.resume(this.parentContext.getStopAction());
      }

      // $FF: synthetic method
      InternalProcessingHandler(FilterChainContext x0, Object x1) {
         this(x0);
      }
   }

   public static class CertificateEvent implements FilterChainEvent {
      static final String TYPE = "CERT_EVENT";
      final FutureImpl certsFuture;
      final boolean needClientAuth;

      public CertificateEvent(boolean needClientAuth) {
         this.needClientAuth = needClientAuth;
         this.certsFuture = Futures.createSafeFuture();
      }

      public final Object type() {
         return "CERT_EVENT";
      }

      public GrizzlyFuture trigger(FilterChainContext ctx) {
         ctx.getFilterChain().fireEventDownstream(ctx.getConnection(), this, (CompletionHandler)null);
         return this.certsFuture;
      }
   }
}
