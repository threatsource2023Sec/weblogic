package org.glassfish.grizzly.filterchain;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ShutdownEvent implements FilterChainEvent {
   public static final Object TYPE = ShutdownEvent.class.getName();
   private Set shutdownFutures;
   private long gracePeriod;
   private TimeUnit timeUnit;

   public ShutdownEvent(long gracePeriod, TimeUnit timeUnit) {
      this.gracePeriod = gracePeriod;
      this.timeUnit = timeUnit;
   }

   public Object type() {
      return TYPE;
   }

   public void addShutdownTask(Callable future) {
      if (future != null) {
         if (this.shutdownFutures == null) {
            this.shutdownFutures = new LinkedHashSet(4);
         }

         this.shutdownFutures.add(future);
      }
   }

   public Set getShutdownTasks() {
      return this.shutdownFutures != null ? this.shutdownFutures : Collections.emptySet();
   }

   public long getGracePeriod() {
      return this.gracePeriod;
   }

   public TimeUnit getTimeUnit() {
      return this.timeUnit;
   }
}
