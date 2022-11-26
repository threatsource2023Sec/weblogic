package weblogic.work;

import java.util.Map;
import weblogic.utils.UnsyncCircularQueue;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public final class IDBasedConstraintEnforcement {
   private static final IDBasedConstraintEnforcement THE_ONE = new IDBasedConstraintEnforcement();
   private final Map wrappers = new ConcurrentWeakHashMap();

   public static IDBasedConstraintEnforcement getInstance() {
      return THE_ONE;
   }

   public synchronized void schedule(WorkManager wm, Runnable runnable, int id) {
      RunnableWrapper wrapper = this.get(id);
      if (wrapper == null) {
         wrapper = this.create(id, runnable);
         wrapper.submitted();
         wm.schedule(wrapper);
      } else {
         boolean submitted = wrapper.add(runnable);
         if (!submitted) {
            wrapper.submitted();
            wm.schedule(wrapper);
         }
      }

   }

   public synchronized int getExecutingCount(int id) {
      RunnableWrapper wrapper = this.get(id);
      return wrapper != null ? wrapper.getExecutingCount() : 0;
   }

   public synchronized int getPendingCount(int id) {
      RunnableWrapper wrapper = this.get(id);
      return wrapper != null ? wrapper.getPendingCount() : 0;
   }

   private RunnableWrapper create(int id, Runnable initial) {
      RunnableWrapper wrapper = new RunnableWrapper(id, initial);
      this.wrappers.put(id, wrapper);
      return wrapper;
   }

   private RunnableWrapper get(int id) {
      return (RunnableWrapper)this.wrappers.get(id);
   }

   private static final class RunnableWrapper extends WorkAdapter {
      private int id;
      private boolean running;
      private boolean submitted;
      private Runnable initialRunnable;
      private UnsyncCircularQueue queue = null;

      RunnableWrapper(int id, Runnable initialRunnable) {
         this.id = id;
         this.initialRunnable = initialRunnable;
      }

      public void run() {
         synchronized(this) {
            if (this.running) {
               return;
            }

            this.running = true;
         }

         if (this.initialRunnable != null) {
            this.initialRunnable.run();
         }

         Runnable runnable = null;

         while((runnable = this.get()) != null) {
            runnable.run();
         }

      }

      private synchronized Runnable get() {
         if (this.queue == null) {
            this.reset();
            return null;
         } else {
            Runnable runnable = (Runnable)this.queue.get();
            if (runnable == null) {
               this.reset();
            }

            return runnable;
         }
      }

      private void reset() {
         this.running = false;
         this.submitted = false;
         this.initialRunnable = null;
         this.queue = null;
      }

      synchronized boolean add(Runnable runnable) {
         if (this.initialRunnable == null) {
            this.initialRunnable = runnable;
            return this.submitted;
         } else {
            if (this.queue == null) {
               this.queue = new UnsyncCircularQueue();
            }

            this.queue.put(runnable);
            return this.submitted;
         }
      }

      synchronized int getPendingCount() {
         return this.queue != null ? this.queue.size() : 0;
      }

      synchronized int getExecutingCount() {
         return this.running ? 1 : 0;
      }

      public final int hashCode() {
         return this.id;
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof RunnableWrapper)) {
            return false;
         } else {
            return ((RunnableWrapper)obj).id == this.id;
         }
      }

      synchronized void submitted() {
         this.submitted = true;
      }
   }
}
