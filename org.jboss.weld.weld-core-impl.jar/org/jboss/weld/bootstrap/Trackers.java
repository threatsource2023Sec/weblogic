package org.jboss.weld.bootstrap;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import org.jboss.weld.logging.BootstrapLogger;

final class Trackers {
   private static final Tracker NOOP_INSTANCE = new NoopTracker() {
   };

   private Trackers() {
   }

   static Tracker create(String startOperation) {
      return create().start(startOperation);
   }

   static Tracker create() {
      return (Tracker)(BootstrapLogger.TRACKER_LOG.isDebugEnabled() ? new LoggingTracker() : NOOP_INSTANCE);
   }

   private static class LoggingTracker implements Tracker {
      private final List operations = new LinkedList();

      LoggingTracker() {
      }

      public Tracker start(String operation) {
         if (!this.operations.isEmpty()) {
            operation = ((Operation)this.operations.get(this.operations.size() - 1)).name + " > " + operation;
         }

         this.operations.add(new Operation(operation));
         BootstrapLogger.TRACKER_LOG.debugf("START %s ", operation);
         return this;
      }

      public void split(String info) {
         Operation operation = (Operation)this.operations.get(this.operations.size() - 1);
         BootstrapLogger.TRACKER_LOG.debugf(" TIME %s:%s (%s ms)", operation.name, info, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - operation.start));
      }

      public Tracker end() {
         Operation operation = (Operation)this.operations.remove(this.operations.size() - 1);
         this.logEnd(operation.name, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - operation.start));
         return this;
      }

      public void close() {
         ListIterator iterator = this.operations.listIterator(this.operations.size());

         while(iterator.hasPrevious()) {
            Operation operation = (Operation)iterator.previous();
            this.logEnd(operation.name, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - operation.start));
            iterator.remove();
         }

      }

      private void logEnd(String info, long time) {
         BootstrapLogger.TRACKER_LOG.debugf("  END %s (%s ms)", info, time);
      }

      private static class Operation {
         private final String name;
         private final Long start;

         public Operation(String operation) {
            this.name = operation;
            this.start = System.nanoTime();
         }
      }
   }

   private static class NoopTracker implements Tracker {
      private NoopTracker() {
      }

      public Tracker start(String operation) {
         return this;
      }

      public Tracker end() {
         return this;
      }

      public void split(String info) {
      }

      public void close() {
      }

      // $FF: synthetic method
      NoopTracker(Object x0) {
         this();
      }
   }
}
