package org.python.netty.handler.traffic;

import java.util.Iterator;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GlobalChannelTrafficCounter extends TrafficCounter {
   public GlobalChannelTrafficCounter(GlobalChannelTrafficShapingHandler trafficShapingHandler, ScheduledExecutorService executor, String name, long checkInterval) {
      super(trafficShapingHandler, executor, name, checkInterval);
      if (executor == null) {
         throw new IllegalArgumentException("Executor must not be null");
      }
   }

   public synchronized void start() {
      if (!this.monitorActive) {
         this.lastTime.set(milliSecondFromNano());
         long localCheckInterval = this.checkInterval.get();
         if (localCheckInterval > 0L) {
            this.monitorActive = true;
            this.monitor = new MixedTrafficMonitoringTask((GlobalChannelTrafficShapingHandler)this.trafficShapingHandler, this);
            this.scheduledFuture = this.executor.schedule(this.monitor, localCheckInterval, TimeUnit.MILLISECONDS);
         }

      }
   }

   public synchronized void stop() {
      if (this.monitorActive) {
         this.monitorActive = false;
         this.resetAccounting(milliSecondFromNano());
         this.trafficShapingHandler.doAccounting(this);
         if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
         }

      }
   }

   public void resetCumulativeTime() {
      Iterator var1 = ((GlobalChannelTrafficShapingHandler)this.trafficShapingHandler).channelQueues.values().iterator();

      while(var1.hasNext()) {
         GlobalChannelTrafficShapingHandler.PerChannel perChannel = (GlobalChannelTrafficShapingHandler.PerChannel)var1.next();
         perChannel.channelTrafficCounter.resetCumulativeTime();
      }

      super.resetCumulativeTime();
   }

   private static class MixedTrafficMonitoringTask implements Runnable {
      private final GlobalChannelTrafficShapingHandler trafficShapingHandler1;
      private final TrafficCounter counter;

      MixedTrafficMonitoringTask(GlobalChannelTrafficShapingHandler trafficShapingHandler, TrafficCounter counter) {
         this.trafficShapingHandler1 = trafficShapingHandler;
         this.counter = counter;
      }

      public void run() {
         if (this.counter.monitorActive) {
            long newLastTime = TrafficCounter.milliSecondFromNano();
            this.counter.resetAccounting(newLastTime);
            Iterator var3 = this.trafficShapingHandler1.channelQueues.values().iterator();

            while(var3.hasNext()) {
               GlobalChannelTrafficShapingHandler.PerChannel perChannel = (GlobalChannelTrafficShapingHandler.PerChannel)var3.next();
               perChannel.channelTrafficCounter.resetAccounting(newLastTime);
            }

            this.trafficShapingHandler1.doAccounting(this.counter);
            this.counter.scheduledFuture = this.counter.executor.schedule(this, this.counter.checkInterval.get(), TimeUnit.MILLISECONDS);
         }
      }
   }
}
