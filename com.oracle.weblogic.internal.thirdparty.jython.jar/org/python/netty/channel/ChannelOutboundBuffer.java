package org.python.netty.channel;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufHolder;
import org.python.netty.buffer.Unpooled;
import org.python.netty.util.Recycler;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.concurrent.FastThreadLocal;
import org.python.netty.util.internal.InternalThreadLocalMap;
import org.python.netty.util.internal.PromiseNotificationUtil;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class ChannelOutboundBuffer {
   static final int CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD = SystemPropertyUtil.getInt("org.python.netty.transport.outboundBufferEntrySizeOverhead", 96);
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);
   private static final FastThreadLocal NIO_BUFFERS = new FastThreadLocal() {
      protected ByteBuffer[] initialValue() throws Exception {
         return new ByteBuffer[1024];
      }
   };
   private final Channel channel;
   private Entry flushedEntry;
   private Entry unflushedEntry;
   private Entry tailEntry;
   private int flushed;
   private int nioBufferCount;
   private long nioBufferSize;
   private boolean inFail;
   private static final AtomicLongFieldUpdater TOTAL_PENDING_SIZE_UPDATER = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
   private volatile long totalPendingSize;
   private static final AtomicIntegerFieldUpdater UNWRITABLE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "unwritable");
   private volatile int unwritable;
   private volatile Runnable fireChannelWritabilityChangedTask;

   ChannelOutboundBuffer(AbstractChannel channel) {
      this.channel = channel;
   }

   public void addMessage(Object msg, int size, ChannelPromise promise) {
      Entry entry = ChannelOutboundBuffer.Entry.newInstance(msg, size, total(msg), promise);
      if (this.tailEntry == null) {
         this.flushedEntry = null;
         this.tailEntry = entry;
      } else {
         Entry tail = this.tailEntry;
         tail.next = entry;
         this.tailEntry = entry;
      }

      if (this.unflushedEntry == null) {
         this.unflushedEntry = entry;
      }

      this.incrementPendingOutboundBytes((long)entry.pendingSize, false);
   }

   public void addFlush() {
      Entry entry = this.unflushedEntry;
      if (entry != null) {
         if (this.flushedEntry == null) {
            this.flushedEntry = entry;
         }

         do {
            ++this.flushed;
            if (!entry.promise.setUncancellable()) {
               int pending = entry.cancel();
               this.decrementPendingOutboundBytes((long)pending, false, true);
            }

            entry = entry.next;
         } while(entry != null);

         this.unflushedEntry = null;
      }

   }

   void incrementPendingOutboundBytes(long size) {
      this.incrementPendingOutboundBytes(size, true);
   }

   private void incrementPendingOutboundBytes(long size, boolean invokeLater) {
      if (size != 0L) {
         long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, size);
         if (newWriteBufferSize > (long)this.channel.config().getWriteBufferHighWaterMark()) {
            this.setUnwritable(invokeLater);
         }

      }
   }

   void decrementPendingOutboundBytes(long size) {
      this.decrementPendingOutboundBytes(size, true, true);
   }

   private void decrementPendingOutboundBytes(long size, boolean invokeLater, boolean notifyWritability) {
      if (size != 0L) {
         long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -size);
         if (notifyWritability && newWriteBufferSize < (long)this.channel.config().getWriteBufferLowWaterMark()) {
            this.setWritable(invokeLater);
         }

      }
   }

   private static long total(Object msg) {
      if (msg instanceof ByteBuf) {
         return (long)((ByteBuf)msg).readableBytes();
      } else if (msg instanceof FileRegion) {
         return ((FileRegion)msg).count();
      } else {
         return msg instanceof ByteBufHolder ? (long)((ByteBufHolder)msg).content().readableBytes() : -1L;
      }
   }

   public Object current() {
      Entry entry = this.flushedEntry;
      return entry == null ? null : entry.msg;
   }

   public void progress(long amount) {
      Entry e = this.flushedEntry;

      assert e != null;

      ChannelPromise p = e.promise;
      if (p instanceof ChannelProgressivePromise) {
         long progress = e.progress + amount;
         e.progress = progress;
         ((ChannelProgressivePromise)p).tryProgress(progress, e.total);
      }

   }

   public boolean remove() {
      Entry e = this.flushedEntry;
      if (e == null) {
         this.clearNioBuffers();
         return false;
      } else {
         Object msg = e.msg;
         ChannelPromise promise = e.promise;
         int size = e.pendingSize;
         this.removeEntry(e);
         if (!e.cancelled) {
            ReferenceCountUtil.safeRelease(msg);
            safeSuccess(promise);
            this.decrementPendingOutboundBytes((long)size, false, true);
         }

         e.recycle();
         return true;
      }
   }

   public boolean remove(Throwable cause) {
      return this.remove0(cause, true);
   }

   private boolean remove0(Throwable cause, boolean notifyWritability) {
      Entry e = this.flushedEntry;
      if (e == null) {
         this.clearNioBuffers();
         return false;
      } else {
         Object msg = e.msg;
         ChannelPromise promise = e.promise;
         int size = e.pendingSize;
         this.removeEntry(e);
         if (!e.cancelled) {
            ReferenceCountUtil.safeRelease(msg);
            safeFail(promise, cause);
            this.decrementPendingOutboundBytes((long)size, false, notifyWritability);
         }

         e.recycle();
         return true;
      }
   }

   private void removeEntry(Entry e) {
      if (--this.flushed == 0) {
         this.flushedEntry = null;
         if (e == this.tailEntry) {
            this.tailEntry = null;
            this.unflushedEntry = null;
         }
      } else {
         this.flushedEntry = e.next;
      }

   }

   public void removeBytes(long writtenBytes) {
      while(true) {
         Object msg = this.current();
         if (!(msg instanceof ByteBuf)) {
            assert writtenBytes == 0L;
         } else {
            ByteBuf buf = (ByteBuf)msg;
            int readerIndex = buf.readerIndex();
            int readableBytes = buf.writerIndex() - readerIndex;
            if ((long)readableBytes <= writtenBytes) {
               if (writtenBytes != 0L) {
                  this.progress((long)readableBytes);
                  writtenBytes -= (long)readableBytes;
               }

               this.remove();
               continue;
            }

            if (writtenBytes != 0L) {
               buf.readerIndex(readerIndex + (int)writtenBytes);
               this.progress(writtenBytes);
            }
         }

         this.clearNioBuffers();
         return;
      }
   }

   private void clearNioBuffers() {
      int count = this.nioBufferCount;
      if (count > 0) {
         this.nioBufferCount = 0;
         Arrays.fill((Object[])NIO_BUFFERS.get(), 0, count, (Object)null);
      }

   }

   public ByteBuffer[] nioBuffers() {
      long nioBufferSize = 0L;
      int nioBufferCount = 0;
      InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
      ByteBuffer[] nioBuffers = (ByteBuffer[])NIO_BUFFERS.get(threadLocalMap);

      for(Entry entry = this.flushedEntry; this.isFlushedEntry(entry) && entry.msg instanceof ByteBuf; entry = entry.next) {
         if (!entry.cancelled) {
            ByteBuf buf = (ByteBuf)entry.msg;
            int readerIndex = buf.readerIndex();
            int readableBytes = buf.writerIndex() - readerIndex;
            if (readableBytes > 0) {
               if ((long)(Integer.MAX_VALUE - readableBytes) < nioBufferSize) {
                  break;
               }

               nioBufferSize += (long)readableBytes;
               int count = entry.count;
               if (count == -1) {
                  entry.count = count = buf.nioBufferCount();
               }

               int neededSpace = nioBufferCount + count;
               if (neededSpace > nioBuffers.length) {
                  nioBuffers = expandNioBufferArray(nioBuffers, neededSpace, nioBufferCount);
                  NIO_BUFFERS.set(threadLocalMap, nioBuffers);
               }

               if (count == 1) {
                  ByteBuffer nioBuf = entry.buf;
                  if (nioBuf == null) {
                     entry.buf = nioBuf = buf.internalNioBuffer(readerIndex, readableBytes);
                  }

                  nioBuffers[nioBufferCount++] = nioBuf;
               } else {
                  ByteBuffer[] nioBufs = entry.bufs;
                  if (nioBufs == null) {
                     entry.bufs = nioBufs = buf.nioBuffers();
                  }

                  nioBufferCount = fillBufferArray(nioBufs, nioBuffers, nioBufferCount);
               }
            }
         }
      }

      this.nioBufferCount = nioBufferCount;
      this.nioBufferSize = nioBufferSize;
      return nioBuffers;
   }

   private static int fillBufferArray(ByteBuffer[] nioBufs, ByteBuffer[] nioBuffers, int nioBufferCount) {
      ByteBuffer[] var3 = nioBufs;
      int var4 = nioBufs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ByteBuffer nioBuf = var3[var5];
         if (nioBuf == null) {
            break;
         }

         nioBuffers[nioBufferCount++] = nioBuf;
      }

      return nioBufferCount;
   }

   private static ByteBuffer[] expandNioBufferArray(ByteBuffer[] array, int neededSpace, int size) {
      int newCapacity = array.length;

      do {
         newCapacity <<= 1;
         if (newCapacity < 0) {
            throw new IllegalStateException();
         }
      } while(neededSpace > newCapacity);

      ByteBuffer[] newArray = new ByteBuffer[newCapacity];
      System.arraycopy(array, 0, newArray, 0, size);
      return newArray;
   }

   public int nioBufferCount() {
      return this.nioBufferCount;
   }

   public long nioBufferSize() {
      return this.nioBufferSize;
   }

   public boolean isWritable() {
      return this.unwritable == 0;
   }

   public boolean getUserDefinedWritability(int index) {
      return (this.unwritable & writabilityMask(index)) == 0;
   }

   public void setUserDefinedWritability(int index, boolean writable) {
      if (writable) {
         this.setUserDefinedWritability(index);
      } else {
         this.clearUserDefinedWritability(index);
      }

   }

   private void setUserDefinedWritability(int index) {
      int mask = ~writabilityMask(index);

      int oldValue;
      int newValue;
      do {
         oldValue = this.unwritable;
         newValue = oldValue & mask;
      } while(!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));

      if (oldValue != 0 && newValue == 0) {
         this.fireChannelWritabilityChanged(true);
      }

   }

   private void clearUserDefinedWritability(int index) {
      int mask = writabilityMask(index);

      int oldValue;
      int newValue;
      do {
         oldValue = this.unwritable;
         newValue = oldValue | mask;
      } while(!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));

      if (oldValue == 0 && newValue != 0) {
         this.fireChannelWritabilityChanged(true);
      }

   }

   private static int writabilityMask(int index) {
      if (index >= 1 && index <= 31) {
         return 1 << index;
      } else {
         throw new IllegalArgumentException("index: " + index + " (expected: 1~31)");
      }
   }

   private void setWritable(boolean invokeLater) {
      int oldValue;
      int newValue;
      do {
         oldValue = this.unwritable;
         newValue = oldValue & -2;
      } while(!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));

      if (oldValue != 0 && newValue == 0) {
         this.fireChannelWritabilityChanged(invokeLater);
      }

   }

   private void setUnwritable(boolean invokeLater) {
      int oldValue;
      int newValue;
      do {
         oldValue = this.unwritable;
         newValue = oldValue | 1;
      } while(!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));

      if (oldValue == 0 && newValue != 0) {
         this.fireChannelWritabilityChanged(invokeLater);
      }

   }

   private void fireChannelWritabilityChanged(boolean invokeLater) {
      final ChannelPipeline pipeline = this.channel.pipeline();
      if (invokeLater) {
         Runnable task = this.fireChannelWritabilityChangedTask;
         if (task == null) {
            this.fireChannelWritabilityChangedTask = task = new Runnable() {
               public void run() {
                  pipeline.fireChannelWritabilityChanged();
               }
            };
         }

         this.channel.eventLoop().execute(task);
      } else {
         pipeline.fireChannelWritabilityChanged();
      }

   }

   public int size() {
      return this.flushed;
   }

   public boolean isEmpty() {
      return this.flushed == 0;
   }

   void failFlushed(Throwable cause, boolean notify) {
      if (!this.inFail) {
         try {
            this.inFail = true;

            while(this.remove0(cause, notify)) {
            }
         } finally {
            this.inFail = false;
         }

      }
   }

   void close(final ClosedChannelException cause) {
      if (this.inFail) {
         this.channel.eventLoop().execute(new Runnable() {
            public void run() {
               ChannelOutboundBuffer.this.close(cause);
            }
         });
      } else {
         this.inFail = true;
         if (this.channel.isOpen()) {
            throw new IllegalStateException("close() must be invoked after the channel is closed.");
         } else if (!this.isEmpty()) {
            throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
         } else {
            try {
               for(Entry e = this.unflushedEntry; e != null; e = e.recycleAndGetNext()) {
                  int size = e.pendingSize;
                  TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, (long)(-size));
                  if (!e.cancelled) {
                     ReferenceCountUtil.safeRelease(e.msg);
                     safeFail(e.promise, cause);
                  }
               }
            } finally {
               this.inFail = false;
            }

            this.clearNioBuffers();
         }
      }
   }

   private static void safeSuccess(ChannelPromise promise) {
      PromiseNotificationUtil.trySuccess(promise, (Object)null, promise instanceof VoidChannelPromise ? null : logger);
   }

   private static void safeFail(ChannelPromise promise, Throwable cause) {
      PromiseNotificationUtil.tryFailure(promise, cause, promise instanceof VoidChannelPromise ? null : logger);
   }

   /** @deprecated */
   @Deprecated
   public void recycle() {
   }

   public long totalPendingWriteBytes() {
      return this.totalPendingSize;
   }

   public long bytesBeforeUnwritable() {
      long bytes = (long)this.channel.config().getWriteBufferHighWaterMark() - this.totalPendingSize;
      if (bytes > 0L) {
         return this.isWritable() ? bytes : 0L;
      } else {
         return 0L;
      }
   }

   public long bytesBeforeWritable() {
      long bytes = this.totalPendingSize - (long)this.channel.config().getWriteBufferLowWaterMark();
      if (bytes > 0L) {
         return this.isWritable() ? 0L : bytes;
      } else {
         return 0L;
      }
   }

   public void forEachFlushedMessage(MessageProcessor processor) throws Exception {
      if (processor == null) {
         throw new NullPointerException("processor");
      } else {
         Entry entry = this.flushedEntry;
         if (entry != null) {
            do {
               if (!entry.cancelled && !processor.processMessage(entry.msg)) {
                  return;
               }

               entry = entry.next;
            } while(this.isFlushedEntry(entry));

         }
      }
   }

   private boolean isFlushedEntry(Entry e) {
      return e != null && e != this.unflushedEntry;
   }

   static final class Entry {
      private static final Recycler RECYCLER = new Recycler() {
         protected Entry newObject(Recycler.Handle handle) {
            return new Entry(handle);
         }
      };
      private final Recycler.Handle handle;
      Entry next;
      Object msg;
      ByteBuffer[] bufs;
      ByteBuffer buf;
      ChannelPromise promise;
      long progress;
      long total;
      int pendingSize;
      int count;
      boolean cancelled;

      private Entry(Recycler.Handle handle) {
         this.count = -1;
         this.handle = handle;
      }

      static Entry newInstance(Object msg, int size, long total, ChannelPromise promise) {
         Entry entry = (Entry)RECYCLER.get();
         entry.msg = msg;
         entry.pendingSize = size + ChannelOutboundBuffer.CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD;
         entry.total = total;
         entry.promise = promise;
         return entry;
      }

      int cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            int pSize = this.pendingSize;
            ReferenceCountUtil.safeRelease(this.msg);
            this.msg = Unpooled.EMPTY_BUFFER;
            this.pendingSize = 0;
            this.total = 0L;
            this.progress = 0L;
            this.bufs = null;
            this.buf = null;
            return pSize;
         } else {
            return 0;
         }
      }

      void recycle() {
         this.next = null;
         this.bufs = null;
         this.buf = null;
         this.msg = null;
         this.promise = null;
         this.progress = 0L;
         this.total = 0L;
         this.pendingSize = 0;
         this.count = -1;
         this.cancelled = false;
         this.handle.recycle(this);
      }

      Entry recycleAndGetNext() {
         Entry next = this.next;
         this.recycle();
         return next;
      }

      // $FF: synthetic method
      Entry(Recycler.Handle x0, Object x1) {
         this(x0);
      }
   }

   public interface MessageProcessor {
      boolean processMessage(Object var1) throws Exception;
   }
}
