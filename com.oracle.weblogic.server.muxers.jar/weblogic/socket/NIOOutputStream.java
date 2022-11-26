package weblogic.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import weblogic.kernel.Kernel;
import weblogic.security.SSL.WLSSSLNioSocket;

class NIOOutputStream extends OutputStream implements GatheringByteChannel, NIOConnection, NIOOutputSink {
   private static ConcurrentHashMap pools = new ConcurrentHashMap();
   private final GatheringByteChannel wc;
   private final NIOSocketMuxer nioSocketMuxer;
   private ByteBuffer lastByteBuffer;
   private byte[] lastByteArray;
   private NetworkInterfaceInfo nwInfo;
   private final SocketChannel sockChannel;
   private boolean isBlocking = true;
   private final BlockingWriter blockingWriter;
   private NonBlockingWriter nonBlockingWriter;

   NIOOutputStream(NIOSocketMuxer nioSocketMuxer, SocketChannel sc, NetworkInterfaceInfo info) {
      this.nioSocketMuxer = nioSocketMuxer;
      this.sockChannel = sc;
      this.nwInfo = info;
      Socket sock = sc.socket();
      if (sock instanceof WLSSSLNioSocket) {
         WritableByteChannel channel = ((WLSSSLNioSocket)sock).getWritableByteChannel();
         if (channel instanceof GatheringByteChannel) {
            this.wc = (GatheringByteChannel)channel;
         } else {
            this.wc = this.getGatheringByteChannel(channel);
         }

         SocketLogger.logDebug("NIOOutputStream constructed with writableByteChannel: " + this.wc);
      } else {
         this.wc = sc;
      }

      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
         SocketLogger.logDebug("NIOOutputStream created");
      }

      this.blockingWriter = new BlockingWriter(this);
   }

   boolean onWritable() throws IOException {
      assert !this.isBlocking;

      return this.nonBlockingWriter.onWritable();
   }

   public boolean canWrite() {
      return this.getWriter().canWrite();
   }

   public void notifyWritePossible(WriteHandler writeHandler) {
      this.getWriter().notifyWritePossible(writeHandler);
   }

   public synchronized boolean isBlocking() {
      return this.isBlocking;
   }

   public synchronized void configureBlocking() throws InterruptedException {
      try {
         this.configureBlocking(-1L, TimeUnit.MILLISECONDS);
      } catch (TimeoutException var2) {
      }

   }

   public synchronized void configureBlocking(long timeout, TimeUnit timeUnit) throws TimeoutException, InterruptedException {
      if (!this.isBlocking) {
         this.nonBlockingWriter.awaitCompletion(timeout, timeUnit);
         this.isBlocking = true;
      }
   }

   public synchronized void configureNonBlocking(MuxableSocket ms) {
      if (this.isBlocking) {
         if (this.nonBlockingWriter == null) {
            this.nonBlockingWriter = new NonBlockingWriter(this);
         }

         this.nonBlockingWriter.muxableSocket = ms;
         this.nonBlockingWriter.socketInfo = (NIOSocketInfo)ms.getSocketInfo();
         this.isBlocking = false;
      }
   }

   public void close() throws IOException {
      if (this.wc.isOpen()) {
         this.wc.close();
      }

      this.blockingWriter.close();
      if (this.nonBlockingWriter != null) {
         this.nonBlockingWriter.close();
      }

      this.lastByteBuffer = null;
      this.lastByteArray = null;
   }

   private GatheringByteChannel getGatheringByteChannel(final WritableByteChannel channel) {
      return new GatheringByteChannel() {
         public boolean isOpen() {
            return channel.isOpen();
         }

         public void close() throws IOException {
            channel.close();
         }

         public int write(ByteBuffer src) throws IOException {
            return channel.write(src);
         }

         public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
            long sz = 0L;
            int end = offset + length;
            if (end > srcs.length) {
               throw new IndexOutOfBoundsException();
            } else {
               int i = offset;

               while(i < end) {
                  sz += (long)channel.write(srcs[i]);
                  if (!srcs[i].hasRemaining()) {
                     ++i;
                  }
               }

               return sz;
            }
         }

         public long write(ByteBuffer[] srcs) throws IOException {
            return this.write(srcs, 0, srcs.length);
         }
      };
   }

   private ByteBuffer getByteBuffer(byte[] b, int off, int len) {
      if (b != this.lastByteArray) {
         this.lastByteArray = b;
         this.lastByteBuffer = ByteBuffer.wrap(b);
      }

      return (ByteBuffer)this.lastByteBuffer.position(off).limit(off + len);
   }

   public void write(int b) throws IOException {
      this.write((byte[])(new byte[]{(byte)b}), 0, 1);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      ByteBuffer buf = this.getByteBuffer(b, off, len);
      this.getWriter().write(buf);
      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
         SocketLogger.logDebug("NIOOutputStream.write: expected to write " + len + " bytes");
      }

   }

   public long write(ByteBuffer[] srcs) throws IOException {
      return this.write((ByteBuffer[])srcs, 0, srcs.length);
   }

   public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
      return this.getWriter().write(srcs, offset, length);
   }

   public int write(ByteBuffer src) throws IOException {
      int len = src.remaining();
      this.getWriter().write(src);
      return len;
   }

   private Writer getWriter() {
      return (Writer)(this.isBlocking ? this.blockingWriter : this.nonBlockingWriter);
   }

   private static void convertToSocketException(Exception e) throws IOException {
      SocketException se = new SocketException("Socket closed");
      se.initCause(e);
      throw se;
   }

   private static void notifyWriteHandler(WriteHandler writeHandler) {
      try {
         writeHandler.onWritable();
      } catch (Exception var2) {
         writeHandler.onError(var2);
      }

   }

   public boolean isOpen() {
      return this.wc.isOpen();
   }

   public InetAddress getLocalInetAddress() {
      return this.nwInfo.getLocalInetAddress();
   }

   public int getMTU() {
      return this.nwInfo.getMTU();
   }

   private int getDirectBufferSize() {
      return this.nwInfo.getDirectBufferSize();
   }

   public int getOptimalNumberOfBuffers() {
      return this.nwInfo.getOptimalNumberOfBuffers();
   }

   public boolean supportsGatheredWrites() {
      return this.nwInfo.supportsGatheredWrites();
   }

   public GatheringByteChannel getGatheringByteChannel() {
      return this;
   }

   public boolean supportsScatteredReads() {
      return false;
   }

   public ScatteringByteChannel getScatteringByteChannel() {
      throw new UnsupportedOperationException();
   }

   private static class DirectBufferPool {
      private volatile int r;
      private volatile int w;
      private static final AtomicIntegerFieldUpdater rPos = AtomicIntegerFieldUpdater.newUpdater(DirectBufferPool.class, "r");
      private static final int MAX_POOL_SIZE = Integer.getInteger("MAX_DIRECT_POOL_SIZE", 10240);
      private ByteBuffer[] bufferArray;

      private DirectBufferPool() {
         this.r = 0;
         this.w = 0;
         this.bufferArray = new ByteBuffer[16];
      }

      private static int pow2(int v) {
         assert v <= MAX_POOL_SIZE;

         return Integer.highestOneBit(v - 1) << 1;
      }

      public ByteBuffer borrow() {
         ByteBuffer bb = null;

         for(int rp = this.r; rp != this.w; rp = this.r) {
            ByteBuffer b = this.bufferArray[this.indexOf(rp)];
            if (rPos.compareAndSet(this, rp, rp + 1)) {
               bb = b;
               break;
            }
         }

         return bb;
      }

      private int indexOf(int rp) {
         return rp & this.bufferArray.length - 1;
      }

      public void borrow(ByteBuffer[] dest, int offset, int length) {
         if (length == 1) {
            dest[offset] = this.borrow();
         } else {
            synchronized(this) {
               int max;
               int rp;
               do {
                  rp = this.r;
                  max = Math.min(length, this.w - rp);
               } while(max > 0 && !rPos.compareAndSet(this, rp, rp + max));

               if (max != 0) {
                  rp = this.indexOf(rp);
                  int c = rp + max >= this.bufferArray.length ? this.bufferArray.length - rp : max;
                  System.arraycopy(this.bufferArray, rp, dest, offset, c);
                  offset += c;
                  max -= c;
                  if (max != 0) {
                     System.arraycopy(this.bufferArray, 0, dest, offset, max);
                  }
               }
            }
         }
      }

      public void release(ByteBuffer[] src, int offset, int length) {
         synchronized(this) {
            int wp = this.w;
            int max = this.r + this.bufferArray.length - wp;
            int i;
            if (length > max && this.bufferArray.length < MAX_POOL_SIZE) {
               for(i = this.r; i != wp && !rPos.compareAndSet(this, i, wp); i = this.r) {
               }

               ByteBuffer[] dd = new ByteBuffer[pow2(this.bufferArray.length + length)];
               System.arraycopy(this.bufferArray, 0, dd, 0, this.bufferArray.length);
               this.bufferArray = dd;
               max = length;
               this.r = i;
            }

            for(i = offset; i < length && max > 0; ++i) {
               if (src[i] != null) {
                  src[i].clear();
                  this.bufferArray[this.indexOf(wp)] = src[i];
                  ++wp;
                  --max;
               }
            }

            this.w = wp;
         }
      }

      // $FF: synthetic method
      DirectBufferPool(Object x0) {
         this();
      }
   }

   private static class MultiBuffersWrite implements WriteBuffer {
      private final ByteBuffer[] buffers;
      private int offset;
      private int length;

      public MultiBuffersWrite(ByteBuffer[] buffers, int offset, int length) {
         this.buffers = buffers;
         this.offset = offset;
         this.length = length;
      }

      public int getOffset() {
         return this.offset;
      }

      public int getLength() {
         return this.length;
      }

      public boolean hasRemaining() {
         return this.length > 0;
      }

      public long writeTo(GatheringByteChannel wc) throws IOException {
         long written = wc.write(this.buffers, this.offset, this.length);
         if (written > 0L) {
            while(this.length > 0 && !this.buffers[this.offset].hasRemaining()) {
               ++this.offset;
               --this.length;
            }
         }

         return written;
      }
   }

   private static class SingleBufferWrite implements WriteBuffer {
      private final ByteBuffer buffer;

      public SingleBufferWrite(ByteBuffer buffer) {
         this.buffer = buffer;
      }

      public boolean hasRemaining() {
         return this.buffer.hasRemaining();
      }

      public long writeTo(GatheringByteChannel wc) throws IOException {
         return (long)wc.write(this.buffer);
      }
   }

   private interface Writer {
      void write(ByteBuffer var1) throws IOException;

      long write(ByteBuffer[] var1, int var2, int var3) throws IOException;

      boolean canWrite();

      void notifyWritePossible(WriteHandler var1);

      void close();
   }

   private static class NonBlockingWriter implements Writer {
      private volatile int queueSize;
      private static final AtomicIntegerFieldUpdater queueSizeUpdater = AtomicIntegerFieldUpdater.newUpdater(NonBlockingWriter.class, "queueSize");
      private final BlockingQueue queue;
      private final AtomicReference writeHandlerRef;
      private final NIOSocketMuxer nioSocketMuxer;
      private final GatheringByteChannel wc;
      private MuxableSocket muxableSocket;
      private NIOSocketInfo socketInfo;
      private volatile boolean isClosed;

      private NonBlockingWriter(NIOOutputStream nioOutputStream) {
         this.queue = new LinkedBlockingQueue();
         this.writeHandlerRef = new AtomicReference();
         this.nioSocketMuxer = nioOutputStream.nioSocketMuxer;
         this.wc = nioOutputStream.wc;
      }

      public void write(ByteBuffer buffer) throws IOException {
         if (this.isClosed) {
            throw new IOException("The stream is closed");
         } else if (buffer.hasRemaining()) {
            try {
               boolean isFirstQueueItem = queueSizeUpdater.getAndIncrement(this) == 0;
               if (isFirstQueueItem) {
                  this.writeNonBlocking(buffer);
                  if (!buffer.hasRemaining()) {
                     int remainingItems = queueSizeUpdater.decrementAndGet(this);
                     if (remainingItems > 0) {
                        this.nioSocketMuxer.enableSelectionKeyInterest(this.muxableSocket, this.socketInfo, 4);
                     }

                     return;
                  }
               }

               if (!this.queue.add(this.cloneAndWrapBuffer(buffer))) {
                  assert !isFirstQueueItem;

                  queueSizeUpdater.decrementAndGet(this);
                  throw new IOException("The Write queue is overflowed. Please use properly OutputSink canWrite/notifyWritePossible methods");
               } else {
                  if (isFirstQueueItem) {
                     this.nioSocketMuxer.enableSelectionKeyInterest(this.muxableSocket, this.socketInfo, 4);
                  }

               }
            } catch (IOException var4) {
               WriteHandler writeHandler = (WriteHandler)this.writeHandlerRef.getAndSet((Object)null);
               if (writeHandler != null) {
                  writeHandler.onError(var4);
               }

               throw var4;
            }
         }
      }

      public long write(ByteBuffer[] buffers, int offset, int length) throws IOException {
         if (this.isClosed) {
            throw new IOException("The stream is closed");
         } else {
            long buffersSize = 0L;

            for(int i = 0; i < length; ++i) {
               buffersSize += (long)buffers[i + offset].remaining();
            }

            if (buffersSize == 0L) {
               return 0L;
            } else {
               try {
                  long written = 0L;
                  boolean isFirstQueueItem = queueSizeUpdater.getAndIncrement(this) == 0;
                  if (isFirstQueueItem) {
                     written = this.writeNonBlocking(buffers, offset, length, buffersSize);
                     if (buffersSize == written) {
                        int remainingItems = queueSizeUpdater.decrementAndGet(this);
                        if (remainingItems > 0) {
                           this.nioSocketMuxer.enableSelectionKeyInterest(this.muxableSocket, this.socketInfo, 4);
                        }

                        return buffersSize;
                     }
                  }

                  if (!this.queue.add(this.cloneAndWrapBuffer(buffers, offset, length, buffersSize - written))) {
                     assert !isFirstQueueItem;

                     queueSizeUpdater.decrementAndGet(this);
                     throw new IOException("The Write queue is overflowed. Please use properly OutputSink canWrite/notifyWritePossible methods");
                  } else {
                     if (isFirstQueueItem) {
                        this.nioSocketMuxer.enableSelectionKeyInterest(this.muxableSocket, this.socketInfo, 4);
                     }

                     return buffersSize;
                  }
               } catch (IOException var10) {
                  WriteHandler writeHandler = (WriteHandler)this.writeHandlerRef.getAndSet((Object)null);
                  if (writeHandler != null) {
                     writeHandler.onError(var10);
                  }

                  throw var10;
               }
            }
         }
      }

      public boolean canWrite() {
         return this.isClosed || this.queueSize == 0;
      }

      public void notifyWritePossible(WriteHandler writeHandler) {
         if (this.canWrite()) {
            NIOOutputStream.notifyWriteHandler(writeHandler);
         } else if (!this.writeHandlerRef.compareAndSet((Object)null, writeHandler)) {
            throw new IllegalStateException("WriteHandler is already set");
         } else {
            if (this.canWrite() && this.writeHandlerRef.compareAndSet(writeHandler, (Object)null)) {
               NIOOutputStream.notifyWriteHandler(writeHandler);
            }

         }
      }

      public void awaitCompletion(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
         if (!this.canWrite()) {
            final CountDownLatch latch = new CountDownLatch(1);
            this.notifyWritePossible(new WriteHandler() {
               public void onWritable() throws Exception {
                  latch.countDown();
               }

               public void onError(Throwable t) {
                  latch.countDown();
               }
            });
            if (timeout >= 0L) {
               if (!latch.await(timeout, timeUnit)) {
                  throw new TimeoutException();
               }
            } else {
               latch.await();
            }

         }
      }

      public void close() {
         this.isClosed = true;
         WriteHandler writeHandler = (WriteHandler)this.writeHandlerRef.getAndSet((Object)null);
         if (writeHandler != null) {
            writeHandler.onError(new IOException("The stream is closed"));
         }

      }

      boolean onWritable() throws IOException {
         try {
            boolean isReregister = false;
            int size = this.queueSize;

            for(int i = 0; i < size; ++i) {
               WriteBuffer buffer = (WriteBuffer)this.queue.peek();
               if (buffer == null) {
                  isReregister = true;
                  break;
               }

               buffer.writeTo(this.wc);
               if (buffer.hasRemaining()) {
                  isReregister = true;
                  break;
               }

               this.queue.remove();
               queueSizeUpdater.decrementAndGet(this);
            }

            if (!isReregister && this.queueSize <= 0) {
               WriteHandler writeHandler = (WriteHandler)this.writeHandlerRef.getAndSet((Object)null);
               if (writeHandler != null) {
                  NIOOutputStream.notifyWriteHandler(writeHandler);
               }

               return true;
            } else {
               this.nioSocketMuxer.enableSelectionKeyInterest(this.muxableSocket, this.socketInfo, 4);
               return false;
            }
         } catch (IOException var5) {
            WriteHandler writeHandler = (WriteHandler)this.writeHandlerRef.getAndSet((Object)null);
            if (writeHandler != null) {
               writeHandler.onError(var5);
            }

            throw var5;
         }
      }

      private int writeNonBlocking(ByteBuffer byteBuffer) throws IOException {
         int written = 0;

         try {
            while(byteBuffer.hasRemaining()) {
               int writtenNow = this.wc.write(byteBuffer);
               if (writtenNow == 0) {
                  break;
               }

               written += writtenNow;
            }
         } catch (ClosedChannelException var4) {
            NIOOutputStream.convertToSocketException(var4);
         }

         return written;
      }

      private long writeNonBlocking(ByteBuffer[] byteBuffers, int offset, int length, long buffersSize) throws IOException {
         long written = 0L;

         try {
            while(written < buffersSize) {
               long writtenNow = this.wc.write(byteBuffers, offset, length);
               if (writtenNow == 0L) {
                  break;
               }

               written += writtenNow;
            }
         } catch (ClosedChannelException var10) {
            NIOOutputStream.convertToSocketException(var10);
         }

         return written;
      }

      private WriteBuffer cloneAndWrapBuffer(ByteBuffer byteBuffer) {
         ByteBuffer copy = ByteBuffer.allocate(byteBuffer.remaining());
         copy.put(byteBuffer);
         copy.flip();
         return new SingleBufferWrite(copy);
      }

      private WriteBuffer cloneAndWrapBuffer(ByteBuffer[] buffers, int offset, int length, long buffersSize) {
         int i;
         if (buffersSize > 2147483647L) {
            ByteBuffer[] copy = new ByteBuffer[length];

            for(i = 0; i < length; ++i) {
               ByteBuffer srcBuf = buffers[i + offset];
               ByteBuffer dstBuf = ByteBuffer.allocate(srcBuf.remaining());
               dstBuf.put(srcBuf);

               assert !dstBuf.hasRemaining();

               dstBuf.flip();
               copy[i] = dstBuf;
            }

            return new MultiBuffersWrite(copy, 0, length);
         } else {
            ByteBuffer singleBuffer = ByteBuffer.allocate((int)buffersSize);

            for(i = 0; i < length; ++i) {
               singleBuffer.put(buffers[i + offset]);
            }

            assert !singleBuffer.hasRemaining();

            singleBuffer.flip();
            return new SingleBufferWrite(singleBuffer);
         }
      }

      // $FF: synthetic method
      NonBlockingWriter(NIOOutputStream x0, Object x1) {
         this(x0);
      }
   }

   private static class BlockingWriter implements Writer {
      private final DirectBufferPool pool;
      private final NIOOutputStream nioOutputStream;
      private final NIOSocketMuxer nioSocketMuxer;
      private final GatheringByteChannel wc;
      private final SocketChannel sockChannel;

      public BlockingWriter(NIOOutputStream nioOutputStream) {
         this.nioOutputStream = nioOutputStream;
         this.nioSocketMuxer = nioOutputStream.nioSocketMuxer;
         this.wc = nioOutputStream.wc;
         this.sockChannel = nioOutputStream.sockChannel;
         this.pool = this.initPool();
      }

      public void write(ByteBuffer buffer) throws IOException {
         while(true) {
            try {
               if (buffer.hasRemaining() && this.wc.write(buffer) != 0) {
                  continue;
               }
            } catch (ClosedChannelException var3) {
               NIOOutputStream.convertToSocketException(var3);
            }

            this.flush(new SingleBufferWrite(buffer));
            return;
         }
      }

      public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
         long sz = 0L;
         int end = offset + length;

         int directBufferSize;
         for(directBufferSize = offset; directBufferSize < end; ++directBufferSize) {
            sz += (long)srcs[directBufferSize].remaining();
         }

         directBufferSize = this.nioOutputStream.getDirectBufferSize();
         ByteBuffer[] directBuffers = new ByteBuffer[(int)((sz + (long)directBufferSize - 1L) / (long)directBufferSize)];
         this.pool.borrow(directBuffers, 0, directBuffers.length);
         int currPos = 0;
         int i = offset;

         while(i < end) {
            if (!srcs[i].hasRemaining()) {
               ++i;
            } else {
               if (directBuffers[currPos] == null) {
                  directBuffers[currPos] = this.pool.borrow();
                  if (directBuffers[currPos] == null) {
                     directBuffers[currPos] = ByteBuffer.allocateDirect(directBufferSize);
                  }
               }

               int r = directBuffers[currPos].remaining();
               if (r <= srcs[i].remaining()) {
                  int oldlim = srcs[i].limit();
                  srcs[i].limit(srcs[i].position() + r);
                  directBuffers[currPos].put(srcs[i]);
                  directBuffers[currPos++].flip();
                  srcs[i].limit(oldlim);
               } else {
                  directBuffers[currPos].put(srcs[i]);
                  ++i;
               }
            }
         }

         if (currPos < directBuffers.length && directBuffers[currPos].position() > 0) {
            directBuffers[currPos++].flip();
         }

         try {
            this.writeAsWriteBuffer(directBuffers, 0, currPos);
         } finally {
            this.pool.release(directBuffers, 0, directBuffers.length);
         }

         return sz;
      }

      public void close() {
      }

      public boolean canWrite() {
         return true;
      }

      public void notifyWritePossible(WriteHandler writeHandler) {
         NIOOutputStream.notifyWriteHandler(writeHandler);
      }

      private void writeAsWriteBuffer(ByteBuffer[] buffers, int offset, int length) throws IOException {
         MultiBuffersWrite multiBuffersWrite = new MultiBuffersWrite(buffers, offset, length);

         try {
            while(multiBuffersWrite.hasRemaining() && multiBuffersWrite.writeTo(this.wc) != 0L) {
            }
         } catch (ClosedChannelException var6) {
            NIOOutputStream.convertToSocketException(var6);
         }

         this.flush(multiBuffersWrite);
      }

      private void flush(WriteBuffer buffer) throws IOException {
         if (buffer.hasRemaining()) {
            Selector writeSelector = this.nioSocketMuxer.findOrCreateSelector();

            assert this.sockChannel.keyFor(writeSelector) == null;

            try {
               SelectionKey key = this.sockChannel.register(writeSelector, 4);
               long n = 500L;

               while(buffer.hasRemaining()) {
                  if (writeSelector.select(n) > 0) {
                     writeSelector.selectedKeys().clear();
                     buffer.writeTo(this.wc);
                  } else if (writeSelector.keys().size() == 0) {
                     if (key.isValid()) {
                        key.interestOps(4);
                        n = 0L;
                     } else {
                        key = this.sockChannel.register(writeSelector, 4);
                     }
                  }

                  if (Thread.interrupted() && Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
                     SocketLogger.logDebug("NIOOutputStream [" + this + "] has been interrupted.");
                  }
               }
            } catch (ClosedChannelException var9) {
               NIOOutputStream.convertToSocketException(var9);
            } finally {
               this.nioSocketMuxer.release(writeSelector);
            }
         }

      }

      private DirectBufferPool initPool() {
         int directBufferSize = this.nioOutputStream.getDirectBufferSize();
         DirectBufferPool temp = (DirectBufferPool)NIOOutputStream.pools.get(directBufferSize);
         if (temp == null) {
            DirectBufferPool t = new DirectBufferPool();
            temp = (DirectBufferPool)NIOOutputStream.pools.putIfAbsent(directBufferSize, t);
            if (temp == null) {
               temp = t;
            }
         }

         return temp;
      }
   }

   private interface WriteBuffer {
      boolean hasRemaining();

      long writeTo(GatheringByteChannel var1) throws IOException;
   }
}
