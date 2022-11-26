package org.python.netty.handler.traffic;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class TrafficCounter {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(TrafficCounter.class);
   private final AtomicLong currentWrittenBytes = new AtomicLong();
   private final AtomicLong currentReadBytes = new AtomicLong();
   private long writingTime;
   private long readingTime;
   private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
   private final AtomicLong cumulativeReadBytes = new AtomicLong();
   private long lastCumulativeTime;
   private long lastWriteThroughput;
   private long lastReadThroughput;
   final AtomicLong lastTime = new AtomicLong();
   private volatile long lastWrittenBytes;
   private volatile long lastReadBytes;
   private volatile long lastWritingTime;
   private volatile long lastReadingTime;
   private final AtomicLong realWrittenBytes = new AtomicLong();
   private long realWriteThroughput;
   final AtomicLong checkInterval = new AtomicLong(1000L);
   final String name;
   final AbstractTrafficShapingHandler trafficShapingHandler;
   final ScheduledExecutorService executor;
   Runnable monitor;
   volatile ScheduledFuture scheduledFuture;
   volatile boolean monitorActive;

   public static long milliSecondFromNano() {
      return System.nanoTime() / 1000000L;
   }

   public synchronized void start() {
      if (!this.monitorActive) {
         this.lastTime.set(milliSecondFromNano());
         long localCheckInterval = this.checkInterval.get();
         if (localCheckInterval > 0L && this.executor != null) {
            this.monitorActive = true;
            this.monitor = new TrafficMonitoringTask();
            this.scheduledFuture = this.executor.schedule(this.monitor, localCheckInterval, TimeUnit.MILLISECONDS);
         }

      }
   }

   public synchronized void stop() {
      if (this.monitorActive) {
         this.monitorActive = false;
         this.resetAccounting(milliSecondFromNano());
         if (this.trafficShapingHandler != null) {
            this.trafficShapingHandler.doAccounting(this);
         }

         if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
         }

      }
   }

   synchronized void resetAccounting(long newLastTime) {
      long interval = newLastTime - this.lastTime.getAndSet(newLastTime);
      if (interval != 0L) {
         if (logger.isDebugEnabled() && interval > this.checkInterval() << 1) {
            logger.debug("Acct schedule not ok: " + interval + " > 2*" + this.checkInterval() + " from " + this.name);
         }

         this.lastReadBytes = this.currentReadBytes.getAndSet(0L);
         this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0L);
         this.lastReadThroughput = this.lastReadBytes * 1000L / interval;
         this.lastWriteThroughput = this.lastWrittenBytes * 1000L / interval;
         this.realWriteThroughput = this.realWrittenBytes.getAndSet(0L) * 1000L / interval;
         this.lastWritingTime = Math.max(this.lastWritingTime, this.writingTime);
         this.lastReadingTime = Math.max(this.lastReadingTime, this.readingTime);
      }
   }

   public TrafficCounter(ScheduledExecutorService executor, String name, long checkInterval) {
      if (name == null) {
         throw new NullPointerException("name");
      } else {
         this.trafficShapingHandler = null;
         this.executor = executor;
         this.name = name;
         this.init(checkInterval);
      }
   }

   public TrafficCounter(AbstractTrafficShapingHandler trafficShapingHandler, ScheduledExecutorService executor, String name, long checkInterval) {
      if (trafficShapingHandler == null) {
         throw new IllegalArgumentException("trafficShapingHandler");
      } else if (name == null) {
         throw new NullPointerException("name");
      } else {
         this.trafficShapingHandler = trafficShapingHandler;
         this.executor = executor;
         this.name = name;
         this.init(checkInterval);
      }
   }

   private void init(long checkInterval) {
      this.lastCumulativeTime = System.currentTimeMillis();
      this.writingTime = milliSecondFromNano();
      this.readingTime = this.writingTime;
      this.lastWritingTime = this.writingTime;
      this.lastReadingTime = this.writingTime;
      this.configure(checkInterval);
   }

   public void configure(long newCheckInterval) {
      long newInterval = newCheckInterval / 10L * 10L;
      if (this.checkInterval.getAndSet(newInterval) != newInterval) {
         if (newInterval <= 0L) {
            this.stop();
            this.lastTime.set(milliSecondFromNano());
         } else {
            this.start();
         }
      }

   }

   void bytesRecvFlowControl(long recv) {
      this.currentReadBytes.addAndGet(recv);
      this.cumulativeReadBytes.addAndGet(recv);
   }

   void bytesWriteFlowControl(long write) {
      this.currentWrittenBytes.addAndGet(write);
      this.cumulativeWrittenBytes.addAndGet(write);
   }

   void bytesRealWriteFlowControl(long write) {
      this.realWrittenBytes.addAndGet(write);
   }

   public long checkInterval() {
      return this.checkInterval.get();
   }

   public long lastReadThroughput() {
      return this.lastReadThroughput;
   }

   public long lastWriteThroughput() {
      return this.lastWriteThroughput;
   }

   public long lastReadBytes() {
      return this.lastReadBytes;
   }

   public long lastWrittenBytes() {
      return this.lastWrittenBytes;
   }

   public long currentReadBytes() {
      return this.currentReadBytes.get();
   }

   public long currentWrittenBytes() {
      return this.currentWrittenBytes.get();
   }

   public long lastTime() {
      return this.lastTime.get();
   }

   public long cumulativeWrittenBytes() {
      return this.cumulativeWrittenBytes.get();
   }

   public long cumulativeReadBytes() {
      return this.cumulativeReadBytes.get();
   }

   public long lastCumulativeTime() {
      return this.lastCumulativeTime;
   }

   public AtomicLong getRealWrittenBytes() {
      return this.realWrittenBytes;
   }

   public long getRealWriteThroughput() {
      return this.realWriteThroughput;
   }

   public void resetCumulativeTime() {
      this.lastCumulativeTime = System.currentTimeMillis();
      this.cumulativeReadBytes.set(0L);
      this.cumulativeWrittenBytes.set(0L);
   }

   public String name() {
      return this.name;
   }

   /** @deprecated */
   @Deprecated
   public long readTimeToWait(long size, long limitTraffic, long maxTime) {
      return this.readTimeToWait(size, limitTraffic, maxTime, milliSecondFromNano());
   }

   public long readTimeToWait(long size, long limitTraffic, long maxTime, long now) {
      this.bytesRecvFlowControl(size);
      if (size != 0L && limitTraffic != 0L) {
         long lastTimeCheck = this.lastTime.get();
         long sum = this.currentReadBytes.get();
         long localReadingTime = this.readingTime;
         long lastRB = this.lastReadBytes;
         long interval = now - lastTimeCheck;
         long pastDelay = Math.max(this.lastReadingTime - lastTimeCheck, 0L);
         long time;
         if (interval > 10L) {
            time = sum * 1000L / limitTraffic - interval + pastDelay;
            if (time > 10L) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Time: " + time + ':' + sum + ':' + interval + ':' + pastDelay);
               }

               if (time > maxTime && now + time - localReadingTime > maxTime) {
                  time = maxTime;
               }

               this.readingTime = Math.max(localReadingTime, now + time);
               return time;
            } else {
               this.readingTime = Math.max(localReadingTime, now);
               return 0L;
            }
         } else {
            time = sum + lastRB;
            long lastinterval = interval + this.checkInterval.get();
            long time = time * 1000L / limitTraffic - lastinterval + pastDelay;
            if (time > 10L) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Time: " + time + ':' + time + ':' + lastinterval + ':' + pastDelay);
               }

               if (time > maxTime && now + time - localReadingTime > maxTime) {
                  time = maxTime;
               }

               this.readingTime = Math.max(localReadingTime, now + time);
               return time;
            } else {
               this.readingTime = Math.max(localReadingTime, now);
               return 0L;
            }
         }
      } else {
         return 0L;
      }
   }

   /** @deprecated */
   @Deprecated
   public long writeTimeToWait(long size, long limitTraffic, long maxTime) {
      return this.writeTimeToWait(size, limitTraffic, maxTime, milliSecondFromNano());
   }

   public long writeTimeToWait(long size, long limitTraffic, long maxTime, long now) {
      this.bytesWriteFlowControl(size);
      if (size != 0L && limitTraffic != 0L) {
         long lastTimeCheck = this.lastTime.get();
         long sum = this.currentWrittenBytes.get();
         long lastWB = this.lastWrittenBytes;
         long localWritingTime = this.writingTime;
         long pastDelay = Math.max(this.lastWritingTime - lastTimeCheck, 0L);
         long interval = now - lastTimeCheck;
         long time;
         if (interval > 10L) {
            time = sum * 1000L / limitTraffic - interval + pastDelay;
            if (time > 10L) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Time: " + time + ':' + sum + ':' + interval + ':' + pastDelay);
               }

               if (time > maxTime && now + time - localWritingTime > maxTime) {
                  time = maxTime;
               }

               this.writingTime = Math.max(localWritingTime, now + time);
               return time;
            } else {
               this.writingTime = Math.max(localWritingTime, now);
               return 0L;
            }
         } else {
            time = sum + lastWB;
            long lastinterval = interval + this.checkInterval.get();
            long time = time * 1000L / limitTraffic - lastinterval + pastDelay;
            if (time > 10L) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Time: " + time + ':' + time + ':' + lastinterval + ':' + pastDelay);
               }

               if (time > maxTime && now + time - localWritingTime > maxTime) {
                  time = maxTime;
               }

               this.writingTime = Math.max(localWritingTime, now + time);
               return time;
            } else {
               this.writingTime = Math.max(localWritingTime, now);
               return 0L;
            }
         }
      } else {
         return 0L;
      }
   }

   public String toString() {
      return (new StringBuilder(165)).append("Monitor ").append(this.name).append(" Current Speed Read: ").append(this.lastReadThroughput >> 10).append(" KB/s, ").append("Asked Write: ").append(this.lastWriteThroughput >> 10).append(" KB/s, ").append("Real Write: ").append(this.realWriteThroughput >> 10).append(" KB/s, ").append("Current Read: ").append(this.currentReadBytes.get() >> 10).append(" KB, ").append("Current asked Write: ").append(this.currentWrittenBytes.get() >> 10).append(" KB, ").append("Current real Write: ").append(this.realWrittenBytes.get() >> 10).append(" KB").toString();
   }

   private final class TrafficMonitoringTask implements Runnable {
      private TrafficMonitoringTask() {
      }

      public void run() {
         if (TrafficCounter.this.monitorActive) {
            TrafficCounter.this.resetAccounting(TrafficCounter.milliSecondFromNano());
            if (TrafficCounter.this.trafficShapingHandler != null) {
               TrafficCounter.this.trafficShapingHandler.doAccounting(TrafficCounter.this);
            }

            TrafficCounter.this.scheduledFuture = TrafficCounter.this.executor.schedule(this, TrafficCounter.this.checkInterval.get(), TimeUnit.MILLISECONDS);
         }
      }

      // $FF: synthetic method
      TrafficMonitoringTask(Object x1) {
         this();
      }
   }
}
