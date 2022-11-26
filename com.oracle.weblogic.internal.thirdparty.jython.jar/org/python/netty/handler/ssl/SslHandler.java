package org.python.netty.handler.ssl;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.buffer.ByteBufUtil;
import org.python.netty.buffer.CompositeByteBuf;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.ChannelException;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelOutboundHandler;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.ChannelPromiseNotifier;
import org.python.netty.channel.PendingWriteQueue;
import org.python.netty.handler.codec.ByteToMessageDecoder;
import org.python.netty.handler.codec.UnsupportedMessageTypeException;
import org.python.netty.util.concurrent.DefaultPromise;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.FutureListener;
import org.python.netty.util.concurrent.ImmediateExecutor;
import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.ThrowableUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class SslHandler extends ByteToMessageDecoder implements ChannelOutboundHandler {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SslHandler.class);
   private static final Pattern IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
   private static final Pattern IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
   private static final SSLException SSLENGINE_CLOSED = (SSLException)ThrowableUtil.unknownStackTrace(new SSLException("SSLEngine closed already"), SslHandler.class, "wrap(...)");
   private static final SSLException HANDSHAKE_TIMED_OUT = (SSLException)ThrowableUtil.unknownStackTrace(new SSLException("handshake timed out"), SslHandler.class, "handshake(...)");
   private static final ClosedChannelException CHANNEL_CLOSED = (ClosedChannelException)ThrowableUtil.unknownStackTrace(new ClosedChannelException(), SslHandler.class, "channelInactive(...)");
   private volatile ChannelHandlerContext ctx;
   private final SSLEngine engine;
   private final SslEngineType engineType;
   private final int maxPacketBufferSize;
   private final Executor delegatedTaskExecutor;
   private final ByteBuffer[] singleBuffer;
   private final boolean startTls;
   private boolean sentFirstMessage;
   private boolean flushedBeforeHandshake;
   private boolean readDuringHandshake;
   private PendingWriteQueue pendingUnencryptedWrites;
   private Promise handshakePromise;
   private final LazyChannelPromise sslClosePromise;
   private boolean needsFlush;
   private boolean outboundClosed;
   private int packetLength;
   private boolean firedChannelRead;
   private volatile long handshakeTimeoutMillis;
   private volatile long closeNotifyFlushTimeoutMillis;
   private volatile long closeNotifyReadTimeoutMillis;

   public SslHandler(SSLEngine engine) {
      this(engine, false);
   }

   public SslHandler(SSLEngine engine, boolean startTls) {
      this(engine, startTls, ImmediateExecutor.INSTANCE);
   }

   /** @deprecated */
   @Deprecated
   public SslHandler(SSLEngine engine, Executor delegatedTaskExecutor) {
      this(engine, false, delegatedTaskExecutor);
   }

   /** @deprecated */
   @Deprecated
   public SslHandler(SSLEngine engine, boolean startTls, Executor delegatedTaskExecutor) {
      this.singleBuffer = new ByteBuffer[1];
      this.handshakePromise = new LazyChannelPromise();
      this.sslClosePromise = new LazyChannelPromise();
      this.handshakeTimeoutMillis = 10000L;
      this.closeNotifyFlushTimeoutMillis = 3000L;
      if (engine == null) {
         throw new NullPointerException("engine");
      } else if (delegatedTaskExecutor == null) {
         throw new NullPointerException("delegatedTaskExecutor");
      } else {
         this.engine = engine;
         this.engineType = SslHandler.SslEngineType.forEngine(engine);
         this.delegatedTaskExecutor = delegatedTaskExecutor;
         this.startTls = startTls;
         this.maxPacketBufferSize = engine.getSession().getPacketBufferSize();
         this.setCumulator(this.engineType.cumulator);
      }
   }

   public long getHandshakeTimeoutMillis() {
      return this.handshakeTimeoutMillis;
   }

   public void setHandshakeTimeout(long handshakeTimeout, TimeUnit unit) {
      if (unit == null) {
         throw new NullPointerException("unit");
      } else {
         this.setHandshakeTimeoutMillis(unit.toMillis(handshakeTimeout));
      }
   }

   public void setHandshakeTimeoutMillis(long handshakeTimeoutMillis) {
      if (handshakeTimeoutMillis < 0L) {
         throw new IllegalArgumentException("handshakeTimeoutMillis: " + handshakeTimeoutMillis + " (expected: >= 0)");
      } else {
         this.handshakeTimeoutMillis = handshakeTimeoutMillis;
      }
   }

   /** @deprecated */
   @Deprecated
   public long getCloseNotifyTimeoutMillis() {
      return this.getCloseNotifyFlushTimeoutMillis();
   }

   /** @deprecated */
   @Deprecated
   public void setCloseNotifyTimeout(long closeNotifyTimeout, TimeUnit unit) {
      this.setCloseNotifyFlushTimeout(closeNotifyTimeout, unit);
   }

   /** @deprecated */
   @Deprecated
   public void setCloseNotifyTimeoutMillis(long closeNotifyFlushTimeoutMillis) {
      this.setCloseNotifyFlushTimeoutMillis(closeNotifyFlushTimeoutMillis);
   }

   public final long getCloseNotifyFlushTimeoutMillis() {
      return this.closeNotifyFlushTimeoutMillis;
   }

   public final void setCloseNotifyFlushTimeout(long closeNotifyFlushTimeout, TimeUnit unit) {
      this.setCloseNotifyFlushTimeoutMillis(unit.toMillis(closeNotifyFlushTimeout));
   }

   public final void setCloseNotifyFlushTimeoutMillis(long closeNotifyFlushTimeoutMillis) {
      if (closeNotifyFlushTimeoutMillis < 0L) {
         throw new IllegalArgumentException("closeNotifyFlushTimeoutMillis: " + closeNotifyFlushTimeoutMillis + " (expected: >= 0)");
      } else {
         this.closeNotifyFlushTimeoutMillis = closeNotifyFlushTimeoutMillis;
      }
   }

   public final long getCloseNotifyReadTimeoutMillis() {
      return this.closeNotifyReadTimeoutMillis;
   }

   public final void setCloseNotifyReadTimeout(long closeNotifyReadTimeout, TimeUnit unit) {
      this.setCloseNotifyReadTimeoutMillis(unit.toMillis(closeNotifyReadTimeout));
   }

   public final void setCloseNotifyReadTimeoutMillis(long closeNotifyReadTimeoutMillis) {
      if (closeNotifyReadTimeoutMillis < 0L) {
         throw new IllegalArgumentException("closeNotifyReadTimeoutMillis: " + closeNotifyReadTimeoutMillis + " (expected: >= 0)");
      } else {
         this.closeNotifyReadTimeoutMillis = closeNotifyReadTimeoutMillis;
      }
   }

   public SSLEngine engine() {
      return this.engine;
   }

   public String applicationProtocol() {
      SSLSession sess = this.engine().getSession();
      return !(sess instanceof ApplicationProtocolAccessor) ? null : ((ApplicationProtocolAccessor)sess).getApplicationProtocol();
   }

   public Future handshakeFuture() {
      return this.handshakePromise;
   }

   /** @deprecated */
   @Deprecated
   public ChannelFuture close() {
      return this.close(this.ctx.newPromise());
   }

   /** @deprecated */
   @Deprecated
   public ChannelFuture close(final ChannelPromise promise) {
      final ChannelHandlerContext ctx = this.ctx;
      ctx.executor().execute(new Runnable() {
         public void run() {
            SslHandler.this.outboundClosed = true;
            SslHandler.this.engine.closeOutbound();

            try {
               SslHandler.this.flush(ctx, promise);
            } catch (Exception var2) {
               if (!promise.tryFailure(var2)) {
                  SslHandler.logger.warn("{} flush() raised a masked exception.", ctx.channel(), var2);
               }
            }

         }
      });
      return promise;
   }

   public Future sslCloseFuture() {
      return this.sslClosePromise;
   }

   public void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
      if (!this.pendingUnencryptedWrites.isEmpty()) {
         this.pendingUnencryptedWrites.removeAndFailAll(new ChannelException("Pending write on removal of SslHandler"));
      }

      if (this.engine instanceof ReferenceCountedOpenSslEngine) {
         ((ReferenceCountedOpenSslEngine)this.engine).release();
      }

   }

   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      ctx.bind(localAddress, promise);
   }

   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      ctx.connect(remoteAddress, localAddress, promise);
   }

   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.deregister(promise);
   }

   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      this.closeOutboundAndChannel(ctx, promise, true);
   }

   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      this.closeOutboundAndChannel(ctx, promise, false);
   }

   public void read(ChannelHandlerContext ctx) throws Exception {
      if (!this.handshakePromise.isDone()) {
         this.readDuringHandshake = true;
      }

      ctx.read();
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      if (!(msg instanceof ByteBuf)) {
         promise.setFailure(new UnsupportedMessageTypeException(msg, new Class[]{ByteBuf.class}));
      } else {
         this.pendingUnencryptedWrites.add(msg, promise);
      }
   }

   public void flush(ChannelHandlerContext ctx) throws Exception {
      if (this.startTls && !this.sentFirstMessage) {
         this.sentFirstMessage = true;
         this.pendingUnencryptedWrites.removeAndWriteAll();
         this.forceFlush(ctx);
      } else {
         try {
            this.wrapAndFlush(ctx);
         } catch (Throwable var3) {
            this.setHandshakeFailure(ctx, var3);
            PlatformDependent.throwException(var3);
         }

      }
   }

   private void wrapAndFlush(ChannelHandlerContext ctx) throws SSLException {
      if (this.pendingUnencryptedWrites.isEmpty()) {
         this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, ctx.newPromise());
      }

      if (!this.handshakePromise.isDone()) {
         this.flushedBeforeHandshake = true;
      }

      try {
         this.wrap(ctx, false);
      } finally {
         this.forceFlush(ctx);
      }

   }

   private void wrap(ChannelHandlerContext ctx, boolean inUnwrap) throws SSLException {
      ByteBuf out = null;
      ChannelPromise promise = null;
      ByteBufAllocator alloc = ctx.alloc();
      boolean needUnwrap = false;

      try {
         while(!ctx.isRemoved()) {
            Object msg = this.pendingUnencryptedWrites.current();
            if (msg == null) {
               break;
            }

            ByteBuf buf = (ByteBuf)msg;
            if (out == null) {
               out = this.allocateOutNetBuf(ctx, buf.readableBytes(), buf.nioBufferCount());
            }

            SSLEngineResult result = this.wrap(alloc, this.engine, buf, out);
            if (result.getStatus() == Status.CLOSED) {
               this.pendingUnencryptedWrites.removeAndFailAll(SSLENGINE_CLOSED);
               return;
            }

            if (!buf.isReadable()) {
               promise = this.pendingUnencryptedWrites.remove();
            } else {
               promise = null;
            }

            switch (result.getHandshakeStatus()) {
               case NEED_TASK:
                  this.runDelegatedTasks();
                  break;
               case FINISHED:
                  this.setHandshakeSuccess();
               case NOT_HANDSHAKING:
                  this.setHandshakeSuccessIfStillHandshaking();
               case NEED_WRAP:
                  this.finishWrap(ctx, out, promise, inUnwrap, false);
                  promise = null;
                  out = null;
                  break;
               case NEED_UNWRAP:
                  needUnwrap = true;
                  return;
               default:
                  throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
            }
         }
      } finally {
         this.finishWrap(ctx, out, promise, inUnwrap, needUnwrap);
      }

   }

   private void finishWrap(ChannelHandlerContext ctx, ByteBuf out, ChannelPromise promise, boolean inUnwrap, boolean needUnwrap) {
      if (out == null) {
         out = Unpooled.EMPTY_BUFFER;
      } else if (!out.isReadable()) {
         out.release();
         out = Unpooled.EMPTY_BUFFER;
      }

      if (promise != null) {
         ctx.write(out, promise);
      } else {
         ctx.write(out);
      }

      if (inUnwrap) {
         this.needsFlush = true;
      }

      if (needUnwrap) {
         this.readIfNeeded(ctx);
      }

   }

   private void wrapNonAppData(ChannelHandlerContext ctx, boolean inUnwrap) throws SSLException {
      ByteBuf out = null;
      ByteBufAllocator alloc = ctx.alloc();

      try {
         while(!ctx.isRemoved()) {
            if (out == null) {
               out = this.allocateOutNetBuf(ctx, 2048, 1);
            }

            SSLEngineResult result = this.wrap(alloc, this.engine, Unpooled.EMPTY_BUFFER, out);
            if (result.bytesProduced() > 0) {
               ctx.write(out);
               if (inUnwrap) {
                  this.needsFlush = true;
               }

               out = null;
            }

            switch (result.getHandshakeStatus()) {
               case NEED_TASK:
                  this.runDelegatedTasks();
                  break;
               case FINISHED:
                  this.setHandshakeSuccess();
                  break;
               case NOT_HANDSHAKING:
                  this.setHandshakeSuccessIfStillHandshaking();
                  if (!inUnwrap) {
                     this.unwrapNonAppData(ctx);
                  }
               case NEED_WRAP:
                  break;
               case NEED_UNWRAP:
                  if (!inUnwrap) {
                     this.unwrapNonAppData(ctx);
                  }
                  break;
               default:
                  throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
            }

            if (result.bytesProduced() == 0 || result.bytesConsumed() == 0 && result.getHandshakeStatus() == HandshakeStatus.NOT_HANDSHAKING) {
               break;
            }
         }
      } finally {
         if (out != null) {
            out.release();
         }

      }

   }

   private SSLEngineResult wrap(ByteBufAllocator alloc, SSLEngine engine, ByteBuf in, ByteBuf out) throws SSLException {
      ByteBuf newDirectIn = null;

      try {
         int readerIndex = in.readerIndex();
         int readableBytes = in.readableBytes();
         ByteBuffer[] in0;
         if (!in.isDirect() && this.engineType.wantsDirectBuffer) {
            newDirectIn = alloc.directBuffer(readableBytes);
            newDirectIn.writeBytes(in, readerIndex, readableBytes);
            in0 = this.singleBuffer;
            in0[0] = newDirectIn.internalNioBuffer(newDirectIn.readerIndex(), readableBytes);
         } else if (!(in instanceof CompositeByteBuf) && in.nioBufferCount() == 1) {
            in0 = this.singleBuffer;
            in0[0] = in.internalNioBuffer(readerIndex, readableBytes);
         } else {
            in0 = in.nioBuffers();
         }

         while(true) {
            ByteBuffer out0 = out.nioBuffer(out.writerIndex(), out.writableBytes());
            SSLEngineResult result = engine.wrap(in0, out0);
            in.skipBytes(result.bytesConsumed());
            out.writerIndex(out.writerIndex() + result.bytesProduced());
            switch (result.getStatus()) {
               case BUFFER_OVERFLOW:
                  out.ensureWritable(this.maxPacketBufferSize);
                  break;
               default:
                  SSLEngineResult var11 = result;
                  return var11;
            }
         }
      } finally {
         this.singleBuffer[0] = null;
         if (newDirectIn != null) {
            newDirectIn.release();
         }

      }
   }

   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      this.setHandshakeFailure(ctx, CHANNEL_CLOSED, !this.outboundClosed);
      this.notifyClosePromise(CHANNEL_CLOSED);
      super.channelInactive(ctx);
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (this.ignoreException(cause)) {
         if (logger.isDebugEnabled()) {
            logger.debug("{} Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", ctx.channel(), cause);
         }

         if (ctx.channel().isActive()) {
            ctx.close();
         }
      } else {
         ctx.fireExceptionCaught(cause);
      }

   }

   private boolean ignoreException(Throwable t) {
      if (!(t instanceof SSLException) && t instanceof IOException && this.sslClosePromise.isDone()) {
         String message = t.getMessage();
         if (message != null && IGNORABLE_ERROR_MESSAGE.matcher(message).matches()) {
            return true;
         }

         StackTraceElement[] elements = t.getStackTrace();
         StackTraceElement[] var4 = elements;
         int var5 = elements.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            StackTraceElement element = var4[var6];
            String classname = element.getClassName();
            String methodname = element.getMethodName();
            if (!classname.startsWith("org.python.netty.") && "read".equals(methodname)) {
               if (IGNORABLE_CLASS_IN_STACK.matcher(classname).matches()) {
                  return true;
               }

               try {
                  Class clazz = PlatformDependent.getClassLoader(this.getClass()).loadClass(classname);
                  if (SocketChannel.class.isAssignableFrom(clazz) || DatagramChannel.class.isAssignableFrom(clazz)) {
                     return true;
                  }

                  if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(clazz.getSuperclass().getName())) {
                     return true;
                  }
               } catch (Throwable var11) {
                  logger.debug("Unexpected exception while loading class {} classname {}", this.getClass(), classname, var11);
               }
            }
         }
      }

      return false;
   }

   public static boolean isEncrypted(ByteBuf buffer) {
      if (buffer.readableBytes() < 5) {
         throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
      } else {
         return SslUtils.getEncryptedPacketLength(buffer, buffer.readerIndex()) != -2;
      }
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws SSLException {
      int startOffset = in.readerIndex();
      int endOffset = in.writerIndex();
      int offset = startOffset;
      int totalLength = 0;
      if (this.packetLength > 0) {
         if (endOffset - startOffset < this.packetLength) {
            return;
         }

         offset = startOffset + this.packetLength;
         totalLength = this.packetLength;
         this.packetLength = 0;
      }

      boolean nonSslRecord;
      int newTotalLength;
      for(nonSslRecord = false; totalLength < 16474; totalLength = newTotalLength) {
         int readableBytes = endOffset - offset;
         if (readableBytes < 5) {
            break;
         }

         int packetLength = SslUtils.getEncryptedPacketLength(in, offset);
         if (packetLength == -2) {
            nonSslRecord = true;
            break;
         }

         assert packetLength > 0;

         if (packetLength > readableBytes) {
            this.packetLength = packetLength;
            break;
         }

         newTotalLength = totalLength + packetLength;
         if (newTotalLength > 16474) {
            break;
         }

         offset += packetLength;
      }

      if (totalLength > 0) {
         in.skipBytes(totalLength);

         try {
            this.firedChannelRead = this.unwrap(ctx, in, startOffset, totalLength) || this.firedChannelRead;
         } catch (Throwable var18) {
            try {
               this.wrapAndFlush(ctx);
            } catch (SSLException var16) {
               logger.debug("SSLException during trying to call SSLEngine.wrap(...) because of an previous SSLException, ignoring...", (Throwable)var16);
            } finally {
               this.setHandshakeFailure(ctx, var18);
            }

            PlatformDependent.throwException(var18);
         }
      }

      if (nonSslRecord) {
         NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
         in.skipBytes(in.readableBytes());
         this.setHandshakeFailure(ctx, e);
         throw e;
      }
   }

   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      this.discardSomeReadBytes();
      this.flushIfNeeded(ctx);
      this.readIfNeeded(ctx);
      this.firedChannelRead = false;
      ctx.fireChannelReadComplete();
   }

   private void readIfNeeded(ChannelHandlerContext ctx) {
      if (!ctx.channel().config().isAutoRead() && (!this.firedChannelRead || !this.handshakePromise.isDone())) {
         ctx.read();
      }

   }

   private void flushIfNeeded(ChannelHandlerContext ctx) {
      if (this.needsFlush) {
         this.forceFlush(ctx);
      }

   }

   private void unwrapNonAppData(ChannelHandlerContext ctx) throws SSLException {
      this.unwrap(ctx, Unpooled.EMPTY_BUFFER, 0, 0);
   }

   private boolean unwrap(ChannelHandlerContext ctx, ByteBuf packet, int offset, int length) throws SSLException {
      boolean decoded = false;
      boolean wrapLater = false;
      boolean notifyClosure = false;
      ByteBuf decodeOut = this.allocate(ctx, length);

      try {
         label151:
         while(!ctx.isRemoved()) {
            SSLEngineResult result = this.engineType.unwrap(this, packet, offset, length, decodeOut);
            SSLEngineResult.Status status = result.getStatus();
            SSLEngineResult.HandshakeStatus handshakeStatus = result.getHandshakeStatus();
            int produced = result.bytesProduced();
            int consumed = result.bytesConsumed();
            offset += consumed;
            length -= consumed;
            switch (status) {
               case BUFFER_OVERFLOW:
                  int readableBytes = decodeOut.readableBytes();
                  int bufferSize = this.engine.getSession().getApplicationBufferSize() - readableBytes;
                  if (readableBytes > 0) {
                     decoded = true;
                     ctx.fireChannelRead(decodeOut);
                     decodeOut = null;
                     if (bufferSize <= 0) {
                        bufferSize = this.engine.getSession().getApplicationBufferSize();
                     }
                  } else {
                     decodeOut.release();
                     decodeOut = null;
                  }

                  decodeOut = this.allocate(ctx, bufferSize);
                  break;
               case CLOSED:
                  notifyClosure = true;
               default:
                  switch (handshakeStatus) {
                     case NEED_TASK:
                        this.runDelegatedTasks();
                        break;
                     case FINISHED:
                        this.setHandshakeSuccess();
                        wrapLater = true;
                        break;
                     case NOT_HANDSHAKING:
                        if (this.setHandshakeSuccessIfStillHandshaking()) {
                           wrapLater = true;
                           continue;
                        }

                        if (this.flushedBeforeHandshake) {
                           this.flushedBeforeHandshake = false;
                           wrapLater = true;
                        }
                        break;
                     case NEED_WRAP:
                        this.wrapNonAppData(ctx, true);
                     case NEED_UNWRAP:
                        break;
                     default:
                        throw new IllegalStateException("unknown handshake status: " + handshakeStatus);
                  }

                  if (status == Status.BUFFER_UNDERFLOW || consumed == 0 && produced == 0) {
                     if (handshakeStatus == HandshakeStatus.NEED_UNWRAP) {
                        this.readIfNeeded(ctx);
                     }
                     break label151;
                  }
            }
         }

         if (wrapLater) {
            this.wrap(ctx, true);
         }

         if (notifyClosure) {
            this.notifyClosePromise((Throwable)null);
         }
      } finally {
         if (decodeOut != null) {
            if (decodeOut.isReadable()) {
               decoded = true;
               ctx.fireChannelRead(decodeOut);
            } else {
               decodeOut.release();
            }
         }

      }

      return decoded;
   }

   private static ByteBuffer toByteBuffer(ByteBuf out, int index, int len) {
      return out.nioBufferCount() == 1 ? out.internalNioBuffer(index, len) : out.nioBuffer(index, len);
   }

   private void runDelegatedTasks() {
      if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE) {
         while(true) {
            Runnable task = this.engine.getDelegatedTask();
            if (task == null) {
               break;
            }

            task.run();
         }
      } else {
         final List tasks = new ArrayList(2);

         while(true) {
            Runnable task = this.engine.getDelegatedTask();
            if (task == null) {
               if (tasks.isEmpty()) {
                  return;
               }

               final CountDownLatch latch = new CountDownLatch(1);
               this.delegatedTaskExecutor.execute(new Runnable() {
                  public void run() {
                     try {
                        Iterator var1 = tasks.iterator();

                        while(var1.hasNext()) {
                           Runnable task = (Runnable)var1.next();
                           task.run();
                        }
                     } catch (Exception var6) {
                        SslHandler.this.ctx.fireExceptionCaught(var6);
                     } finally {
                        latch.countDown();
                     }

                  }
               });
               boolean interrupted = false;

               while(latch.getCount() != 0L) {
                  try {
                     latch.await();
                  } catch (InterruptedException var5) {
                     interrupted = true;
                  }
               }

               if (interrupted) {
                  Thread.currentThread().interrupt();
               }
               break;
            }

            tasks.add(task);
         }
      }

   }

   private boolean setHandshakeSuccessIfStillHandshaking() {
      if (!this.handshakePromise.isDone()) {
         this.setHandshakeSuccess();
         return true;
      } else {
         return false;
      }
   }

   private void setHandshakeSuccess() {
      this.handshakePromise.trySuccess(this.ctx.channel());
      if (logger.isDebugEnabled()) {
         logger.debug("{} HANDSHAKEN: {}", this.ctx.channel(), this.engine.getSession().getCipherSuite());
      }

      this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
      if (this.readDuringHandshake && !this.ctx.channel().config().isAutoRead()) {
         this.readDuringHandshake = false;
         this.ctx.read();
      }

   }

   private void setHandshakeFailure(ChannelHandlerContext ctx, Throwable cause) {
      this.setHandshakeFailure(ctx, cause, true);
   }

   private void setHandshakeFailure(ChannelHandlerContext ctx, Throwable cause, boolean closeInbound) {
      try {
         this.engine.closeOutbound();
         if (closeInbound) {
            try {
               this.engine.closeInbound();
            } catch (SSLException var9) {
               String msg = var9.getMessage();
               if (msg == null || !msg.contains("possible truncation attack")) {
                  logger.debug("{} SSLEngine.closeInbound() raised an exception.", ctx.channel(), var9);
               }
            }
         }

         this.notifyHandshakeFailure(cause);
      } finally {
         this.pendingUnencryptedWrites.removeAndFailAll(cause);
      }

   }

   private void notifyHandshakeFailure(Throwable cause) {
      if (this.handshakePromise.tryFailure(cause)) {
         SslUtils.notifyHandshakeFailure(this.ctx, cause);
      }

   }

   private void notifyClosePromise(Throwable cause) {
      if (cause == null) {
         if (this.sslClosePromise.trySuccess(this.ctx.channel())) {
            this.ctx.fireUserEventTriggered(SslCloseCompletionEvent.SUCCESS);
         }
      } else if (this.sslClosePromise.tryFailure(cause)) {
         this.ctx.fireUserEventTriggered(new SslCloseCompletionEvent(cause));
      }

   }

   private void closeOutboundAndChannel(ChannelHandlerContext ctx, ChannelPromise promise, boolean disconnect) throws Exception {
      if (!ctx.channel().isActive()) {
         if (disconnect) {
            ctx.disconnect(promise);
         } else {
            ctx.close(promise);
         }

      } else {
         this.outboundClosed = true;
         this.engine.closeOutbound();
         ChannelPromise closeNotifyPromise = ctx.newPromise();

         try {
            this.flush(ctx, closeNotifyPromise);
         } finally {
            this.safeClose(ctx, closeNotifyPromise, ctx.newPromise().addListener(new ChannelPromiseNotifier(false, new ChannelPromise[]{promise})));
         }

      }
   }

   private void flush(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, promise);
      this.flush(ctx);
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      this.ctx = ctx;
      this.pendingUnencryptedWrites = new PendingWriteQueue(ctx);
      if (ctx.channel().isActive() && this.engine.getUseClientMode()) {
         this.handshake((Promise)null);
      }

   }

   public Future renegotiate() {
      ChannelHandlerContext ctx = this.ctx;
      if (ctx == null) {
         throw new IllegalStateException();
      } else {
         return this.renegotiate(ctx.executor().newPromise());
      }
   }

   public Future renegotiate(final Promise promise) {
      if (promise == null) {
         throw new NullPointerException("promise");
      } else {
         ChannelHandlerContext ctx = this.ctx;
         if (ctx == null) {
            throw new IllegalStateException();
         } else {
            EventExecutor executor = ctx.executor();
            if (!executor.inEventLoop()) {
               executor.execute(new Runnable() {
                  public void run() {
                     SslHandler.this.handshake(promise);
                  }
               });
               return promise;
            } else {
               this.handshake(promise);
               return promise;
            }
         }
      }
   }

   private void handshake(final Promise newHandshakePromise) {
      final Promise p;
      if (newHandshakePromise != null) {
         Promise oldHandshakePromise = this.handshakePromise;
         if (!oldHandshakePromise.isDone()) {
            oldHandshakePromise.addListener(new FutureListener() {
               public void operationComplete(Future future) throws Exception {
                  if (future.isSuccess()) {
                     newHandshakePromise.setSuccess(future.getNow());
                  } else {
                     newHandshakePromise.setFailure(future.cause());
                  }

               }
            });
            return;
         }

         p = newHandshakePromise;
         this.handshakePromise = newHandshakePromise;
      } else {
         if (this.engine.getHandshakeStatus() != HandshakeStatus.NOT_HANDSHAKING) {
            return;
         }

         p = this.handshakePromise;

         assert !p.isDone();
      }

      ChannelHandlerContext ctx = this.ctx;

      try {
         this.engine.beginHandshake();
         this.wrapNonAppData(ctx, false);
      } catch (Throwable var11) {
         this.setHandshakeFailure(ctx, var11);
      } finally {
         this.forceFlush(ctx);
      }

      long handshakeTimeoutMillis = this.handshakeTimeoutMillis;
      if (handshakeTimeoutMillis > 0L && !p.isDone()) {
         final ScheduledFuture timeoutFuture = ctx.executor().schedule(new Runnable() {
            public void run() {
               if (!p.isDone()) {
                  SslHandler.this.notifyHandshakeFailure(SslHandler.HANDSHAKE_TIMED_OUT);
               }
            }
         }, handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
         p.addListener(new FutureListener() {
            public void operationComplete(Future f) throws Exception {
               timeoutFuture.cancel(false);
            }
         });
      }
   }

   private void forceFlush(ChannelHandlerContext ctx) {
      this.needsFlush = false;
      ctx.flush();
   }

   public void channelActive(ChannelHandlerContext ctx) throws Exception {
      if (!this.startTls && this.engine.getUseClientMode()) {
         this.handshake((Promise)null);
      }

      ctx.fireChannelActive();
   }

   private void safeClose(final ChannelHandlerContext ctx, final ChannelFuture flushFuture, final ChannelPromise promise) {
      if (!ctx.channel().isActive()) {
         ctx.close(promise);
      } else {
         final org.python.netty.util.concurrent.ScheduledFuture timeoutFuture;
         if (!flushFuture.isDone()) {
            long closeNotifyTimeout = this.closeNotifyFlushTimeoutMillis;
            if (closeNotifyTimeout > 0L) {
               timeoutFuture = ctx.executor().schedule(new Runnable() {
                  public void run() {
                     if (!flushFuture.isDone()) {
                        SslHandler.logger.warn("{} Last write attempt timed out; force-closing the connection.", (Object)ctx.channel());
                        SslHandler.addCloseListener(ctx.close(ctx.newPromise()), promise);
                     }

                  }
               }, closeNotifyTimeout, TimeUnit.MILLISECONDS);
            } else {
               timeoutFuture = null;
            }
         } else {
            timeoutFuture = null;
         }

         flushFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) throws Exception {
               if (timeoutFuture != null) {
                  timeoutFuture.cancel(false);
               }

               final long closeNotifyReadTimeout = SslHandler.this.closeNotifyReadTimeoutMillis;
               if (closeNotifyReadTimeout <= 0L) {
                  SslHandler.addCloseListener(ctx.close(ctx.newPromise()), promise);
               } else {
                  final org.python.netty.util.concurrent.ScheduledFuture closeNotifyReadTimeoutFuture;
                  if (!SslHandler.this.sslClosePromise.isDone()) {
                     closeNotifyReadTimeoutFuture = ctx.executor().schedule(new Runnable() {
                        public void run() {
                           if (!SslHandler.this.sslClosePromise.isDone()) {
                              SslHandler.logger.debug("{} did not receive close_notify in {}ms; force-closing the connection.", ctx.channel(), closeNotifyReadTimeout);
                              SslHandler.addCloseListener(ctx.close(ctx.newPromise()), promise);
                           }

                        }
                     }, closeNotifyReadTimeout, TimeUnit.MILLISECONDS);
                  } else {
                     closeNotifyReadTimeoutFuture = null;
                  }

                  SslHandler.this.sslClosePromise.addListener(new FutureListener() {
                     public void operationComplete(Future future) throws Exception {
                        if (closeNotifyReadTimeoutFuture != null) {
                           closeNotifyReadTimeoutFuture.cancel(false);
                        }

                        SslHandler.addCloseListener(ctx.close(ctx.newPromise()), promise);
                     }
                  });
               }

            }
         });
      }
   }

   private static void addCloseListener(ChannelFuture future, ChannelPromise promise) {
      future.addListener(new ChannelPromiseNotifier(false, new ChannelPromise[]{promise}));
   }

   private ByteBuf allocate(ChannelHandlerContext ctx, int capacity) {
      ByteBufAllocator alloc = ctx.alloc();
      return this.engineType.wantsDirectBuffer ? alloc.directBuffer(capacity) : alloc.buffer(capacity);
   }

   private ByteBuf allocateOutNetBuf(ChannelHandlerContext ctx, int pendingBytes, int numComponents) {
      return this.allocate(ctx, this.engineType.calculateWrapBufferCapacity(this, pendingBytes, numComponents));
   }

   private final class LazyChannelPromise extends DefaultPromise {
      private LazyChannelPromise() {
      }

      protected EventExecutor executor() {
         if (SslHandler.this.ctx == null) {
            throw new IllegalStateException();
         } else {
            return SslHandler.this.ctx.executor();
         }
      }

      protected void checkDeadLock() {
         if (SslHandler.this.ctx != null) {
            super.checkDeadLock();
         }
      }

      // $FF: synthetic method
      LazyChannelPromise(Object x1) {
         this();
      }
   }

   private static enum SslEngineType {
      TCNATIVE(true, ByteToMessageDecoder.COMPOSITE_CUMULATOR) {
         SSLEngineResult unwrap(SslHandler handler, ByteBuf in, int readerIndex, int len, ByteBuf out) throws SSLException {
            int nioBufferCount = in.nioBufferCount();
            int writerIndex = out.writerIndex();
            SSLEngineResult result;
            if (nioBufferCount > 1) {
               ReferenceCountedOpenSslEngine opensslEngine = (ReferenceCountedOpenSslEngine)handler.engine;

               try {
                  handler.singleBuffer[0] = SslHandler.toByteBuffer(out, writerIndex, out.writableBytes());
                  result = opensslEngine.unwrap(in.nioBuffers(readerIndex, len), handler.singleBuffer);
               } finally {
                  handler.singleBuffer[0] = null;
               }
            } else {
               result = handler.engine.unwrap(SslHandler.toByteBuffer(in, readerIndex, len), SslHandler.toByteBuffer(out, writerIndex, out.writableBytes()));
            }

            out.writerIndex(writerIndex + result.bytesProduced());
            return result;
         }

         int calculateWrapBufferCapacity(SslHandler handler, int pendingBytes, int numComponents) {
            return ReferenceCountedOpenSslEngine.calculateOutNetBufSize(pendingBytes, numComponents);
         }
      },
      CONSCRYPT(true, ByteToMessageDecoder.COMPOSITE_CUMULATOR) {
         SSLEngineResult unwrap(SslHandler handler, ByteBuf in, int readerIndex, int len, ByteBuf out) throws SSLException {
            int nioBufferCount = in.nioBufferCount();
            int writerIndex = out.writerIndex();
            SSLEngineResult result;
            if (nioBufferCount > 1) {
               try {
                  handler.singleBuffer[0] = SslHandler.toByteBuffer(out, writerIndex, out.writableBytes());
                  result = ((ConscryptAlpnSslEngine)handler.engine).unwrap(in.nioBuffers(readerIndex, len), handler.singleBuffer);
               } finally {
                  handler.singleBuffer[0] = null;
               }
            } else {
               result = handler.engine.unwrap(SslHandler.toByteBuffer(in, readerIndex, len), SslHandler.toByteBuffer(out, writerIndex, out.writableBytes()));
            }

            out.writerIndex(writerIndex + result.bytesProduced());
            return result;
         }

         int calculateWrapBufferCapacity(SslHandler handler, int pendingBytes, int numComponents) {
            return ((ConscryptAlpnSslEngine)handler.engine).calculateOutNetBufSize(pendingBytes, numComponents);
         }
      },
      JDK(false, ByteToMessageDecoder.MERGE_CUMULATOR) {
         SSLEngineResult unwrap(SslHandler handler, ByteBuf in, int readerIndex, int len, ByteBuf out) throws SSLException {
            int writerIndex = out.writerIndex();
            SSLEngineResult result = handler.engine.unwrap(SslHandler.toByteBuffer(in, readerIndex, len), SslHandler.toByteBuffer(out, writerIndex, out.writableBytes()));
            out.writerIndex(writerIndex + result.bytesProduced());
            return result;
         }

         int calculateWrapBufferCapacity(SslHandler handler, int pendingBytes, int numComponents) {
            return handler.maxPacketBufferSize;
         }
      };

      final boolean wantsDirectBuffer;
      final ByteToMessageDecoder.Cumulator cumulator;

      static SslEngineType forEngine(SSLEngine engine) {
         if (engine instanceof ReferenceCountedOpenSslEngine) {
            return TCNATIVE;
         } else {
            return engine instanceof ConscryptAlpnSslEngine ? CONSCRYPT : JDK;
         }
      }

      private SslEngineType(boolean wantsDirectBuffer, ByteToMessageDecoder.Cumulator cumulator) {
         this.wantsDirectBuffer = wantsDirectBuffer;
         this.cumulator = cumulator;
      }

      abstract SSLEngineResult unwrap(SslHandler var1, ByteBuf var2, int var3, int var4, ByteBuf var5) throws SSLException;

      abstract int calculateWrapBufferCapacity(SslHandler var1, int var2, int var3);

      // $FF: synthetic method
      SslEngineType(boolean x2, ByteToMessageDecoder.Cumulator x3, Object x4) {
         this(x2, x3);
      }
   }
}
