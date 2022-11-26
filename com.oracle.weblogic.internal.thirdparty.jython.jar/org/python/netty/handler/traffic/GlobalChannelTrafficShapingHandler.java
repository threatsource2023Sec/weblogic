package org.python.netty.handler.traffic;

import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.util.Attribute;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

@ChannelHandler.Sharable
public class GlobalChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalChannelTrafficShapingHandler.class);
   final ConcurrentMap channelQueues = PlatformDependent.newConcurrentHashMap();
   private final AtomicLong queuesSize = new AtomicLong();
   private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
   private final AtomicLong cumulativeReadBytes = new AtomicLong();
   volatile long maxGlobalWriteSize = 419430400L;
   private volatile long writeChannelLimit;
   private volatile long readChannelLimit;
   private static final float DEFAULT_DEVIATION = 0.1F;
   private static final float MAX_DEVIATION = 0.4F;
   private static final float DEFAULT_SLOWDOWN = 0.4F;
   private static final float DEFAULT_ACCELERATION = -0.1F;
   private volatile float maxDeviation;
   private volatile float accelerationFactor;
   private volatile float slowDownFactor;
   private volatile boolean readDeviationActive;
   private volatile boolean writeDeviationActive;

   void createGlobalTrafficCounter(ScheduledExecutorService executor) {
      this.setMaxDeviation(0.1F, 0.4F, -0.1F);
      if (executor == null) {
         throw new IllegalArgumentException("Executor must not be null");
      } else {
         TrafficCounter tc = new GlobalChannelTrafficCounter(this, executor, "GlobalChannelTC", this.checkInterval);
         this.setTrafficCounter(tc);
         tc.start();
      }
   }

   protected int userDefinedWritabilityIndex() {
      return 3;
   }

   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit, long checkInterval, long maxTime) {
      super(writeGlobalLimit, readGlobalLimit, checkInterval, maxTime);
      this.createGlobalTrafficCounter(executor);
      this.writeChannelLimit = writeChannelLimit;
      this.readChannelLimit = readChannelLimit;
   }

   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit, long checkInterval) {
      super(writeGlobalLimit, readGlobalLimit, checkInterval);
      this.writeChannelLimit = writeChannelLimit;
      this.readChannelLimit = readChannelLimit;
      this.createGlobalTrafficCounter(executor);
   }

   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit) {
      super(writeGlobalLimit, readGlobalLimit);
      this.writeChannelLimit = writeChannelLimit;
      this.readChannelLimit = readChannelLimit;
      this.createGlobalTrafficCounter(executor);
   }

   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long checkInterval) {
      super(checkInterval);
      this.createGlobalTrafficCounter(executor);
   }

   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor) {
      this.createGlobalTrafficCounter(executor);
   }

   public float maxDeviation() {
      return this.maxDeviation;
   }

   public float accelerationFactor() {
      return this.accelerationFactor;
   }

   public float slowDownFactor() {
      return this.slowDownFactor;
   }

   public void setMaxDeviation(float maxDeviation, float slowDownFactor, float accelerationFactor) {
      if (maxDeviation > 0.4F) {
         throw new IllegalArgumentException("maxDeviation must be <= 0.4");
      } else if (slowDownFactor < 0.0F) {
         throw new IllegalArgumentException("slowDownFactor must be >= 0");
      } else if (accelerationFactor > 0.0F) {
         throw new IllegalArgumentException("accelerationFactor must be <= 0");
      } else {
         this.maxDeviation = maxDeviation;
         this.accelerationFactor = 1.0F + accelerationFactor;
         this.slowDownFactor = 1.0F + slowDownFactor;
      }
   }

   private void computeDeviationCumulativeBytes() {
      long maxWrittenBytes = 0L;
      long maxReadBytes = 0L;
      long minWrittenBytes = Long.MAX_VALUE;
      long minReadBytes = Long.MAX_VALUE;
      Iterator var9 = this.channelQueues.values().iterator();

      while(var9.hasNext()) {
         PerChannel perChannel = (PerChannel)var9.next();
         long value = perChannel.channelTrafficCounter.cumulativeWrittenBytes();
         if (maxWrittenBytes < value) {
            maxWrittenBytes = value;
         }

         if (minWrittenBytes > value) {
            minWrittenBytes = value;
         }

         value = perChannel.channelTrafficCounter.cumulativeReadBytes();
         if (maxReadBytes < value) {
            maxReadBytes = value;
         }

         if (minReadBytes > value) {
            minReadBytes = value;
         }
      }

      boolean multiple = this.channelQueues.size() > 1;
      this.readDeviationActive = multiple && minReadBytes < maxReadBytes / 2L;
      this.writeDeviationActive = multiple && minWrittenBytes < maxWrittenBytes / 2L;
      this.cumulativeWrittenBytes.set(maxWrittenBytes);
      this.cumulativeReadBytes.set(maxReadBytes);
   }

   protected void doAccounting(TrafficCounter counter) {
      this.computeDeviationCumulativeBytes();
      super.doAccounting(counter);
   }

   private long computeBalancedWait(float maxLocal, float maxGlobal, long wait) {
      if (maxGlobal == 0.0F) {
         return wait;
      } else {
         float ratio = maxLocal / maxGlobal;
         if (ratio > this.maxDeviation) {
            if (ratio < 1.0F - this.maxDeviation) {
               return wait;
            }

            ratio = this.slowDownFactor;
            if (wait < 10L) {
               wait = 10L;
            }
         } else {
            ratio = this.accelerationFactor;
         }

         return (long)((float)wait * ratio);
      }
   }

   public long getMaxGlobalWriteSize() {
      return this.maxGlobalWriteSize;
   }

   public void setMaxGlobalWriteSize(long maxGlobalWriteSize) {
      if (maxGlobalWriteSize <= 0L) {
         throw new IllegalArgumentException("maxGlobalWriteSize must be positive");
      } else {
         this.maxGlobalWriteSize = maxGlobalWriteSize;
      }
   }

   public long queuesSize() {
      return this.queuesSize.get();
   }

   public void configureChannel(long newWriteLimit, long newReadLimit) {
      this.writeChannelLimit = newWriteLimit;
      this.readChannelLimit = newReadLimit;
      long now = TrafficCounter.milliSecondFromNano();
      Iterator var7 = this.channelQueues.values().iterator();

      while(var7.hasNext()) {
         PerChannel perChannel = (PerChannel)var7.next();
         perChannel.channelTrafficCounter.resetAccounting(now);
      }

   }

   public long getWriteChannelLimit() {
      return this.writeChannelLimit;
   }

   public void setWriteChannelLimit(long writeLimit) {
      this.writeChannelLimit = writeLimit;
      long now = TrafficCounter.milliSecondFromNano();
      Iterator var5 = this.channelQueues.values().iterator();

      while(var5.hasNext()) {
         PerChannel perChannel = (PerChannel)var5.next();
         perChannel.channelTrafficCounter.resetAccounting(now);
      }

   }

   public long getReadChannelLimit() {
      return this.readChannelLimit;
   }

   public void setReadChannelLimit(long readLimit) {
      this.readChannelLimit = readLimit;
      long now = TrafficCounter.milliSecondFromNano();
      Iterator var5 = this.channelQueues.values().iterator();

      while(var5.hasNext()) {
         PerChannel perChannel = (PerChannel)var5.next();
         perChannel.channelTrafficCounter.resetAccounting(now);
      }

   }

   public final void release() {
      this.trafficCounter.stop();
   }

   private PerChannel getOrSetPerChannel(ChannelHandlerContext ctx) {
      Channel channel = ctx.channel();
      Integer key = channel.hashCode();
      PerChannel perChannel = (PerChannel)this.channelQueues.get(key);
      if (perChannel == null) {
         perChannel = new PerChannel();
         perChannel.messagesQueue = new ArrayDeque();
         perChannel.channelTrafficCounter = new TrafficCounter(this, (ScheduledExecutorService)null, "ChannelTC" + ctx.channel().hashCode(), this.checkInterval);
         perChannel.queueSize = 0L;
         perChannel.lastReadTimestamp = TrafficCounter.milliSecondFromNano();
         perChannel.lastWriteTimestamp = perChannel.lastReadTimestamp;
         this.channelQueues.put(key, perChannel);
      }

      return perChannel;
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      this.getOrSetPerChannel(ctx);
      this.trafficCounter.resetCumulativeTime();
      super.handlerAdded(ctx);
   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      this.trafficCounter.resetCumulativeTime();
      Channel channel = ctx.channel();
      Integer key = channel.hashCode();
      PerChannel perChannel = (PerChannel)this.channelQueues.remove(key);
      if (perChannel != null) {
         synchronized(perChannel) {
            Iterator var6;
            ToSend toSend;
            if (channel.isActive()) {
               var6 = perChannel.messagesQueue.iterator();

               while(var6.hasNext()) {
                  toSend = (ToSend)var6.next();
                  long size = this.calculateSize(toSend.toSend);
                  this.trafficCounter.bytesRealWriteFlowControl(size);
                  perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
                  perChannel.queueSize -= size;
                  this.queuesSize.addAndGet(-size);
                  ctx.write(toSend.toSend, toSend.promise);
               }
            } else {
               this.queuesSize.addAndGet(-perChannel.queueSize);
               var6 = perChannel.messagesQueue.iterator();

               while(var6.hasNext()) {
                  toSend = (ToSend)var6.next();
                  if (toSend.toSend instanceof ByteBuf) {
                     ((ByteBuf)toSend.toSend).release();
                  }
               }
            }

            perChannel.messagesQueue.clear();
         }
      }

      this.releaseWriteSuspended(ctx);
      this.releaseReadSuspended(ctx);
      super.handlerRemoved(ctx);
   }

   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      long size = this.calculateSize(msg);
      long now = TrafficCounter.milliSecondFromNano();
      if (size > 0L) {
         long waitGlobal = this.trafficCounter.readTimeToWait(size, this.getReadLimit(), this.maxTime, now);
         Integer key = ctx.channel().hashCode();
         PerChannel perChannel = (PerChannel)this.channelQueues.get(key);
         long wait = 0L;
         if (perChannel != null) {
            wait = perChannel.channelTrafficCounter.readTimeToWait(size, this.readChannelLimit, this.maxTime, now);
            if (this.readDeviationActive) {
               long maxLocalRead = perChannel.channelTrafficCounter.cumulativeReadBytes();
               long maxGlobalRead = this.cumulativeReadBytes.get();
               if (maxLocalRead <= 0L) {
                  maxLocalRead = 0L;
               }

               if (maxGlobalRead < maxLocalRead) {
                  maxGlobalRead = maxLocalRead;
               }

               wait = this.computeBalancedWait((float)maxLocalRead, (float)maxGlobalRead, wait);
            }
         }

         if (wait < waitGlobal) {
            wait = waitGlobal;
         }

         wait = this.checkWaitReadTime(ctx, wait, now);
         if (wait >= 10L) {
            ChannelConfig config = ctx.channel().config();
            if (logger.isDebugEnabled()) {
               logger.debug("Read Suspend: " + wait + ':' + config.isAutoRead() + ':' + isHandlerActive(ctx));
            }

            if (config.isAutoRead() && isHandlerActive(ctx)) {
               config.setAutoRead(false);
               ctx.attr(READ_SUSPENDED).set(true);
               Attribute attr = ctx.attr(REOPEN_TASK);
               Runnable reopenTask = (Runnable)attr.get();
               if (reopenTask == null) {
                  reopenTask = new AbstractTrafficShapingHandler.ReopenReadTimerTask(ctx);
                  attr.set(reopenTask);
               }

               ctx.executor().schedule((Runnable)reopenTask, wait, TimeUnit.MILLISECONDS);
               if (logger.isDebugEnabled()) {
                  logger.debug("Suspend final status => " + config.isAutoRead() + ':' + isHandlerActive(ctx) + " will reopened at: " + wait);
               }
            }
         }
      }

      this.informReadOperation(ctx, now);
      ctx.fireChannelRead(msg);
   }

   protected long checkWaitReadTime(ChannelHandlerContext ctx, long wait, long now) {
      Integer key = ctx.channel().hashCode();
      PerChannel perChannel = (PerChannel)this.channelQueues.get(key);
      if (perChannel != null && wait > this.maxTime && now + wait - perChannel.lastReadTimestamp > this.maxTime) {
         wait = this.maxTime;
      }

      return wait;
   }

   protected void informReadOperation(ChannelHandlerContext ctx, long now) {
      Integer key = ctx.channel().hashCode();
      PerChannel perChannel = (PerChannel)this.channelQueues.get(key);
      if (perChannel != null) {
         perChannel.lastReadTimestamp = now;
      }

   }

   protected long maximumCumulativeWrittenBytes() {
      return this.cumulativeWrittenBytes.get();
   }

   protected long maximumCumulativeReadBytes() {
      return this.cumulativeReadBytes.get();
   }

   public Collection channelTrafficCounters() {
      return new AbstractCollection() {
         public Iterator iterator() {
            return new Iterator() {
               final Iterator iter;

               {
                  this.iter = GlobalChannelTrafficShapingHandler.this.channelQueues.values().iterator();
               }

               public boolean hasNext() {
                  return this.iter.hasNext();
               }

               public TrafficCounter next() {
                  return ((PerChannel)this.iter.next()).channelTrafficCounter;
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }

         public int size() {
            return GlobalChannelTrafficShapingHandler.this.channelQueues.size();
         }
      };
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      long size = this.calculateSize(msg);
      long now = TrafficCounter.milliSecondFromNano();
      if (size > 0L) {
         long waitGlobal = this.trafficCounter.writeTimeToWait(size, this.getWriteLimit(), this.maxTime, now);
         Integer key = ctx.channel().hashCode();
         PerChannel perChannel = (PerChannel)this.channelQueues.get(key);
         long wait = 0L;
         if (perChannel != null) {
            wait = perChannel.channelTrafficCounter.writeTimeToWait(size, this.writeChannelLimit, this.maxTime, now);
            if (this.writeDeviationActive) {
               long maxLocalWrite = perChannel.channelTrafficCounter.cumulativeWrittenBytes();
               long maxGlobalWrite = this.cumulativeWrittenBytes.get();
               if (maxLocalWrite <= 0L) {
                  maxLocalWrite = 0L;
               }

               if (maxGlobalWrite < maxLocalWrite) {
                  maxGlobalWrite = maxLocalWrite;
               }

               wait = this.computeBalancedWait((float)maxLocalWrite, (float)maxGlobalWrite, wait);
            }
         }

         if (wait < waitGlobal) {
            wait = waitGlobal;
         }

         if (wait >= 10L) {
            if (logger.isDebugEnabled()) {
               logger.debug("Write suspend: " + wait + ':' + ctx.channel().config().isAutoRead() + ':' + isHandlerActive(ctx));
            }

            this.submitWrite(ctx, msg, size, wait, now, promise);
            return;
         }
      }

      this.submitWrite(ctx, msg, size, 0L, now, promise);
   }

   protected void submitWrite(final ChannelHandlerContext ctx, Object msg, long size, long writedelay, long now, ChannelPromise promise) {
      Channel channel = ctx.channel();
      Integer key = channel.hashCode();
      final PerChannel perChannel = (PerChannel)this.channelQueues.get(key);
      if (perChannel == null) {
         perChannel = this.getOrSetPerChannel(ctx);
      }

      long delay = writedelay;
      boolean globalSizeExceeded = false;
      ToSend newToSend;
      synchronized(perChannel) {
         if (writedelay == 0L && perChannel.messagesQueue.isEmpty()) {
            this.trafficCounter.bytesRealWriteFlowControl(size);
            perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
            ctx.write(msg, promise);
            perChannel.lastWriteTimestamp = now;
            return;
         }

         if (delay > this.maxTime && now + delay - perChannel.lastWriteTimestamp > this.maxTime) {
            delay = this.maxTime;
         }

         newToSend = new ToSend(delay + now, msg, size, promise);
         perChannel.messagesQueue.addLast(newToSend);
         perChannel.queueSize += size;
         this.queuesSize.addAndGet(size);
         this.checkWriteSuspend(ctx, delay, perChannel.queueSize);
         if (this.queuesSize.get() > this.maxGlobalWriteSize) {
            globalSizeExceeded = true;
         }
      }

      if (globalSizeExceeded) {
         this.setUserDefinedWritability(ctx, false);
      }

      final long futureNow = newToSend.relativeTimeAction;
      ctx.executor().schedule(new Runnable() {
         public void run() {
            GlobalChannelTrafficShapingHandler.this.sendAllValid(ctx, perChannel, futureNow);
         }
      }, delay, TimeUnit.MILLISECONDS);
   }

   private void sendAllValid(ChannelHandlerContext ctx, PerChannel perChannel, long now) {
      synchronized(perChannel) {
         ToSend newToSend = (ToSend)perChannel.messagesQueue.pollFirst();

         while(true) {
            if (newToSend != null) {
               if (newToSend.relativeTimeAction <= now) {
                  long size = newToSend.size;
                  this.trafficCounter.bytesRealWriteFlowControl(size);
                  perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
                  perChannel.queueSize -= size;
                  this.queuesSize.addAndGet(-size);
                  ctx.write(newToSend.toSend, newToSend.promise);
                  perChannel.lastWriteTimestamp = now;
                  newToSend = (ToSend)perChannel.messagesQueue.pollFirst();
                  continue;
               }

               perChannel.messagesQueue.addFirst(newToSend);
            }

            if (perChannel.messagesQueue.isEmpty()) {
               this.releaseWriteSuspended(ctx);
            }
            break;
         }
      }

      ctx.flush();
   }

   public String toString() {
      return (new StringBuilder(340)).append(super.toString()).append(" Write Channel Limit: ").append(this.writeChannelLimit).append(" Read Channel Limit: ").append(this.readChannelLimit).toString();
   }

   private static final class ToSend {
      final long relativeTimeAction;
      final Object toSend;
      final ChannelPromise promise;
      final long size;

      private ToSend(long delay, Object toSend, long size, ChannelPromise promise) {
         this.relativeTimeAction = delay;
         this.toSend = toSend;
         this.size = size;
         this.promise = promise;
      }

      // $FF: synthetic method
      ToSend(long x0, Object x1, long x2, ChannelPromise x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   static final class PerChannel {
      ArrayDeque messagesQueue;
      TrafficCounter channelTrafficCounter;
      long queueSize;
      long lastWriteTimestamp;
      long lastReadTimestamp;
   }
}
