package com.bea.core.repackaged.springframework.scheduling;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

public interface TaskScheduler {
   @Nullable
   ScheduledFuture schedule(Runnable var1, Trigger var2);

   default ScheduledFuture schedule(Runnable task, Instant startTime) {
      return this.schedule(task, Date.from(startTime));
   }

   ScheduledFuture schedule(Runnable var1, Date var2);

   default ScheduledFuture scheduleAtFixedRate(Runnable task, Instant startTime, Duration period) {
      return this.scheduleAtFixedRate(task, Date.from(startTime), period.toMillis());
   }

   ScheduledFuture scheduleAtFixedRate(Runnable var1, Date var2, long var3);

   default ScheduledFuture scheduleAtFixedRate(Runnable task, Duration period) {
      return this.scheduleAtFixedRate(task, period.toMillis());
   }

   ScheduledFuture scheduleAtFixedRate(Runnable var1, long var2);

   default ScheduledFuture scheduleWithFixedDelay(Runnable task, Instant startTime, Duration delay) {
      return this.scheduleWithFixedDelay(task, Date.from(startTime), delay.toMillis());
   }

   ScheduledFuture scheduleWithFixedDelay(Runnable var1, Date var2, long var3);

   default ScheduledFuture scheduleWithFixedDelay(Runnable task, Duration delay) {
      return this.scheduleWithFixedDelay(task, delay.toMillis());
   }

   ScheduledFuture scheduleWithFixedDelay(Runnable var1, long var2);
}
