package org.python.netty.channel;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.util.internal.ObjectUtil;

public class DefaultChannelConfig implements ChannelConfig {
   private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR;
   private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
   private static final AtomicIntegerFieldUpdater AUTOREAD_UPDATER;
   private static final AtomicReferenceFieldUpdater WATERMARK_UPDATER;
   protected final Channel channel;
   private volatile ByteBufAllocator allocator;
   private volatile RecvByteBufAllocator rcvBufAllocator;
   private volatile MessageSizeEstimator msgSizeEstimator;
   private volatile int connectTimeoutMillis;
   private volatile int writeSpinCount;
   private volatile int autoRead;
   private volatile boolean autoClose;
   private volatile WriteBufferWaterMark writeBufferWaterMark;
   private volatile boolean pinEventExecutor;

   public DefaultChannelConfig(Channel channel) {
      this(channel, new AdaptiveRecvByteBufAllocator());
   }

   protected DefaultChannelConfig(Channel channel, RecvByteBufAllocator allocator) {
      this.allocator = ByteBufAllocator.DEFAULT;
      this.msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
      this.connectTimeoutMillis = 30000;
      this.writeSpinCount = 16;
      this.autoRead = 1;
      this.autoClose = true;
      this.writeBufferWaterMark = WriteBufferWaterMark.DEFAULT;
      this.pinEventExecutor = true;
      this.setRecvByteBufAllocator(allocator, channel.metadata());
      this.channel = channel;
   }

   public Map getOptions() {
      return this.getOptions((Map)null, ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.AUTO_CLOSE, ChannelOption.RCVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.WRITE_BUFFER_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR, ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP);
   }

   protected Map getOptions(Map result, ChannelOption... options) {
      if (result == null) {
         result = new IdentityHashMap();
      }

      ChannelOption[] var3 = options;
      int var4 = options.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ChannelOption o = var3[var5];
         ((Map)result).put(o, this.getOption(o));
      }

      return (Map)result;
   }

   public boolean setOptions(Map options) {
      if (options == null) {
         throw new NullPointerException("options");
      } else {
         boolean setAllOptions = true;
         Iterator var3 = options.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry e = (Map.Entry)var3.next();
            if (!this.setOption((ChannelOption)e.getKey(), e.getValue())) {
               setAllOptions = false;
            }
         }

         return setAllOptions;
      }
   }

   public Object getOption(ChannelOption option) {
      if (option == null) {
         throw new NullPointerException("option");
      } else if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
         return this.getConnectTimeoutMillis();
      } else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
         return this.getMaxMessagesPerRead();
      } else if (option == ChannelOption.WRITE_SPIN_COUNT) {
         return this.getWriteSpinCount();
      } else if (option == ChannelOption.ALLOCATOR) {
         return this.getAllocator();
      } else if (option == ChannelOption.RCVBUF_ALLOCATOR) {
         return this.getRecvByteBufAllocator();
      } else if (option == ChannelOption.AUTO_READ) {
         return this.isAutoRead();
      } else if (option == ChannelOption.AUTO_CLOSE) {
         return this.isAutoClose();
      } else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
         return this.getWriteBufferHighWaterMark();
      } else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
         return this.getWriteBufferLowWaterMark();
      } else if (option == ChannelOption.WRITE_BUFFER_WATER_MARK) {
         return this.getWriteBufferWaterMark();
      } else if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
         return this.getMessageSizeEstimator();
      } else {
         return option == ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP ? this.getPinEventExecutorPerGroup() : null;
      }
   }

   public boolean setOption(ChannelOption option, Object value) {
      this.validate(option, value);
      if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
         this.setConnectTimeoutMillis((Integer)value);
      } else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
         this.setMaxMessagesPerRead((Integer)value);
      } else if (option == ChannelOption.WRITE_SPIN_COUNT) {
         this.setWriteSpinCount((Integer)value);
      } else if (option == ChannelOption.ALLOCATOR) {
         this.setAllocator((ByteBufAllocator)value);
      } else if (option == ChannelOption.RCVBUF_ALLOCATOR) {
         this.setRecvByteBufAllocator((RecvByteBufAllocator)value);
      } else if (option == ChannelOption.AUTO_READ) {
         this.setAutoRead((Boolean)value);
      } else if (option == ChannelOption.AUTO_CLOSE) {
         this.setAutoClose((Boolean)value);
      } else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
         this.setWriteBufferHighWaterMark((Integer)value);
      } else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
         this.setWriteBufferLowWaterMark((Integer)value);
      } else if (option == ChannelOption.WRITE_BUFFER_WATER_MARK) {
         this.setWriteBufferWaterMark((WriteBufferWaterMark)value);
      } else if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
         this.setMessageSizeEstimator((MessageSizeEstimator)value);
      } else {
         if (option != ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
            return false;
         }

         this.setPinEventExecutorPerGroup((Boolean)value);
      }

      return true;
   }

   protected void validate(ChannelOption option, Object value) {
      if (option == null) {
         throw new NullPointerException("option");
      } else {
         option.validate(value);
      }
   }

   public int getConnectTimeoutMillis() {
      return this.connectTimeoutMillis;
   }

   public ChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
      if (connectTimeoutMillis < 0) {
         throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", connectTimeoutMillis));
      } else {
         this.connectTimeoutMillis = connectTimeoutMillis;
         return this;
      }
   }

   /** @deprecated */
   @Deprecated
   public int getMaxMessagesPerRead() {
      try {
         MaxMessagesRecvByteBufAllocator allocator = (MaxMessagesRecvByteBufAllocator)this.getRecvByteBufAllocator();
         return allocator.maxMessagesPerRead();
      } catch (ClassCastException var2) {
         throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", var2);
      }
   }

   /** @deprecated */
   @Deprecated
   public ChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
      try {
         MaxMessagesRecvByteBufAllocator allocator = (MaxMessagesRecvByteBufAllocator)this.getRecvByteBufAllocator();
         allocator.maxMessagesPerRead(maxMessagesPerRead);
         return this;
      } catch (ClassCastException var3) {
         throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", var3);
      }
   }

   public int getWriteSpinCount() {
      return this.writeSpinCount;
   }

   public ChannelConfig setWriteSpinCount(int writeSpinCount) {
      if (writeSpinCount <= 0) {
         throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
      } else {
         this.writeSpinCount = writeSpinCount;
         return this;
      }
   }

   public ByteBufAllocator getAllocator() {
      return this.allocator;
   }

   public ChannelConfig setAllocator(ByteBufAllocator allocator) {
      if (allocator == null) {
         throw new NullPointerException("allocator");
      } else {
         this.allocator = allocator;
         return this;
      }
   }

   public RecvByteBufAllocator getRecvByteBufAllocator() {
      return this.rcvBufAllocator;
   }

   public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
      this.rcvBufAllocator = (RecvByteBufAllocator)ObjectUtil.checkNotNull(allocator, "allocator");
      return this;
   }

   private void setRecvByteBufAllocator(RecvByteBufAllocator allocator, ChannelMetadata metadata) {
      if (allocator instanceof MaxMessagesRecvByteBufAllocator) {
         ((MaxMessagesRecvByteBufAllocator)allocator).maxMessagesPerRead(metadata.defaultMaxMessagesPerRead());
      } else if (allocator == null) {
         throw new NullPointerException("allocator");
      }

      this.setRecvByteBufAllocator(allocator);
   }

   public boolean isAutoRead() {
      return this.autoRead == 1;
   }

   public ChannelConfig setAutoRead(boolean autoRead) {
      boolean oldAutoRead = AUTOREAD_UPDATER.getAndSet(this, autoRead ? 1 : 0) == 1;
      if (autoRead && !oldAutoRead) {
         this.channel.read();
      } else if (!autoRead && oldAutoRead) {
         this.autoReadCleared();
      }

      return this;
   }

   protected void autoReadCleared() {
   }

   public boolean isAutoClose() {
      return this.autoClose;
   }

   public ChannelConfig setAutoClose(boolean autoClose) {
      this.autoClose = autoClose;
      return this;
   }

   public int getWriteBufferHighWaterMark() {
      return this.writeBufferWaterMark.high();
   }

   public ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
      if (writeBufferHighWaterMark < 0) {
         throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
      } else {
         WriteBufferWaterMark waterMark;
         do {
            waterMark = this.writeBufferWaterMark;
            if (writeBufferHighWaterMark < waterMark.low()) {
               throw new IllegalArgumentException("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + waterMark.low() + "): " + writeBufferHighWaterMark);
            }
         } while(!WATERMARK_UPDATER.compareAndSet(this, waterMark, new WriteBufferWaterMark(waterMark.low(), writeBufferHighWaterMark, false)));

         return this;
      }
   }

   public int getWriteBufferLowWaterMark() {
      return this.writeBufferWaterMark.low();
   }

   public ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
      if (writeBufferLowWaterMark < 0) {
         throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
      } else {
         WriteBufferWaterMark waterMark;
         do {
            waterMark = this.writeBufferWaterMark;
            if (writeBufferLowWaterMark > waterMark.high()) {
               throw new IllegalArgumentException("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + waterMark.high() + "): " + writeBufferLowWaterMark);
            }
         } while(!WATERMARK_UPDATER.compareAndSet(this, waterMark, new WriteBufferWaterMark(writeBufferLowWaterMark, waterMark.high(), false)));

         return this;
      }
   }

   public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
      this.writeBufferWaterMark = (WriteBufferWaterMark)ObjectUtil.checkNotNull(writeBufferWaterMark, "writeBufferWaterMark");
      return this;
   }

   public WriteBufferWaterMark getWriteBufferWaterMark() {
      return this.writeBufferWaterMark;
   }

   public MessageSizeEstimator getMessageSizeEstimator() {
      return this.msgSizeEstimator;
   }

   public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
      if (estimator == null) {
         throw new NullPointerException("estimator");
      } else {
         this.msgSizeEstimator = estimator;
         return this;
      }
   }

   private ChannelConfig setPinEventExecutorPerGroup(boolean pinEventExecutor) {
      this.pinEventExecutor = pinEventExecutor;
      return this;
   }

   private boolean getPinEventExecutorPerGroup() {
      return this.pinEventExecutor;
   }

   static {
      DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
      AUTOREAD_UPDATER = AtomicIntegerFieldUpdater.newUpdater(DefaultChannelConfig.class, "autoRead");
      WATERMARK_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelConfig.class, WriteBufferWaterMark.class, "writeBufferWaterMark");
   }
}
