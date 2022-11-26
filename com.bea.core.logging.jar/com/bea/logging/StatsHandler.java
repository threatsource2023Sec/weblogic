package com.bea.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class StatsHandler extends Handler {
   private static final Map countsBySeverity = new ConcurrentHashMap() {
      private static final long serialVersionUID = 1L;

      {
         this.put(256, new AtomicInteger());
         this.put(128, new AtomicInteger());
         this.put(64, new AtomicInteger());
         this.put(32, new AtomicInteger());
         this.put(16, new AtomicInteger());
         this.put(8, new AtomicInteger());
         this.put(4, new AtomicInteger());
         this.put(2, new AtomicInteger());
         this.put(1, new AtomicInteger());
         this.put(0, new AtomicInteger());
      }
   };

   public static Map getCountsBySeverity() {
      return countsBySeverity;
   }

   public StatsHandler() {
      this.setLevel(Level.INFO);
   }

   public void close() {
   }

   public void flush() {
   }

   public void publish(LogRecord rec) {
      if (this.isLoggable(rec)) {
         Level level = rec.getLevel();
         int severity = LogLevel.getSeverity(level);
         AtomicInteger counter = (AtomicInteger)countsBySeverity.get(severity);
         counter.getAndIncrement();
      }

   }
}
