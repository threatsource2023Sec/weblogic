package weblogic.work.commonj;

import commonj.work.Work;
import commonj.work.WorkEvent;
import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkListener;
import commonj.work.WorkManager;
import commonj.work.WorkRejectedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import weblogic.work.WorkManagerImpl;

public class CommonjWorkManagerImpl implements WorkManager {
   protected final weblogic.work.WorkManager workManager;
   private static final int MILLI_TO_NANO_FACTOR = 1000000;

   public CommonjWorkManagerImpl(weblogic.work.WorkManager workManager) {
      this.workManager = workManager;
   }

   public WorkItem schedule(Work e) {
      return this.schedule(e, (WorkListener)null);
   }

   public WorkItem schedule(Work e, WorkListener wl) {
      if (e == null) {
         throw new IllegalArgumentException("null work instance");
      } else {
         WorkStatus status = new WorkStatus(e);
         if (wl != null) {
            wl.workAccepted(status);
         }

         if (e.isDaemon()) {
            WorkManagerImpl.executeDaemonTask(e.toString(), 10, e);
         } else {
            this.workManager.schedule(new WorkWithListener(e, wl, status));
         }

         return status;
      }
   }

   public boolean waitForAll(Collection workItems, long timeoutMillis) throws InterruptedException {
      if (workItems != null && workItems.size() != 0) {
         if (timeoutMillis < 0L) {
            throw new IllegalArgumentException("timeoutMillis < 0");
         } else {
            return 0L == timeoutMillis ? createCallback(workItems).waitForAll(1L) : createCallback(workItems).waitForAll(timeoutMillis);
         }
      } else {
         return true;
      }
   }

   public Collection waitForAny(Collection workItems, long timeoutMillis) throws InterruptedException {
      if (workItems != null && workItems.size() != 0) {
         if (timeoutMillis < 0L) {
            throw new IllegalArgumentException("timeoutMillis < 0");
         } else {
            return 0L == timeoutMillis ? createCallback(workItems).waitForAny(1L) : createCallback(workItems).waitForAny(timeoutMillis);
         }
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   private static Callback createCallback(Collection workItems) {
      Object[] items = workItems.toArray();
      Callback callback = new Callback();

      for(int count = 0; count < items.length; ++count) {
         if (items[count] instanceof WorkStatus) {
            WorkStatus status = (WorkStatus)items[count];
            callback.add(status);
         }
      }

      return callback;
   }

   public weblogic.work.WorkManager getDelegate() {
      return this.workManager;
   }

   public boolean equals(Object other) {
      if (!(other instanceof CommonjWorkManagerImpl)) {
         return false;
      } else {
         return this.workManager == ((CommonjWorkManagerImpl)other).workManager;
      }
   }

   public int hashCode() {
      return this.workManager != null ? this.workManager.hashCode() : super.hashCode();
   }

   protected static class WorkStatus implements WorkEvent, WorkItem {
      private static long globalCount;
      private int type = 1;
      protected final Work work;
      private WorkException workException;
      private ArrayList callbacks;
      protected long counter;

      protected WorkStatus(Work work) {
         this.work = work;
         this.counter = getCounter();
      }

      private static synchronized long getCounter() {
         return (long)(globalCount++);
      }

      public final synchronized void setType(int type) {
         this.type = type;
         if (this.isCompleted() && this.callbacks != null && this.callbacks.size() > 0) {
            Iterator iter = this.callbacks.iterator();

            while(iter.hasNext()) {
               ((Callback)iter.next()).completed(this);
            }

            this.callbacks = null;
         }

      }

      public final int getType() {
         return this.type;
      }

      public final WorkItem getWorkItem() {
         return this;
      }

      public final Work getWork() {
         return this.work;
      }

      public final void setThrowable(Throwable th) {
         this.workException = new WorkException(th);
      }

      public final WorkException getException() {
         return this.workException;
      }

      public final int getStatus() {
         return this.type;
      }

      public final void release() throws WorkException {
         if (this.isCompleted()) {
            throw new WorkException("release called on already completed work");
         } else {
            this.work.release();
         }
      }

      public final Work getResult() throws WorkException {
         if (this.workException != null) {
            throw this.workException;
         } else {
            return this.isCompleted() ? this.work : null;
         }
      }

      public final synchronized void register(Callback callback) {
         if (this.isCompleted()) {
            callback.completed(this);
         } else {
            if (this.callbacks == null) {
               this.callbacks = new ArrayList();
            }

            this.callbacks.add(callback);
         }

      }

      private synchronized boolean isCompleted() {
         return this.type == 4 || this.type == 2;
      }

      public String toString() {
         return "[" + this.counter + "] executing: " + this.work;
      }

      public int hashCode() {
         return (int)this.counter;
      }

      public int compareTo(Object o) {
         try {
            return this.compare((WorkStatus)o);
         } catch (ClassCastException var3) {
            return -1;
         }
      }

      public int compare(WorkStatus id) {
         if (this.counter > id.counter) {
            return 1;
         } else {
            return this.counter < id.counter ? -1 : 0;
         }
      }

      public boolean equals(Object o) {
         if (!(o instanceof WorkStatus)) {
            return false;
         } else {
            WorkStatus id = (WorkStatus)o;
            return this.counter == id.counter;
         }
      }
   }

   private static final class WorkWithListener implements weblogic.work.Work, Work {
      private final Work work;
      private final WorkListener listener;
      private final WorkStatus status;

      private WorkWithListener(Work work, WorkListener listener, WorkStatus status) {
         this.work = work;
         this.listener = listener;
         this.status = status;
      }

      public Runnable overloadAction(final String reason) {
         return new Runnable() {
            public void run() {
               WorkWithListener.this.status.setType(2);
               WorkWithListener.this.status.setThrowable(new WorkRejectedException(reason));

               try {
                  if (WorkWithListener.this.listener != null) {
                     WorkWithListener.this.listener.workRejected(WorkWithListener.this.status);
                  }
               } catch (Throwable var2) {
               }

            }
         };
      }

      public Runnable cancel(String reason) {
         return this.status.getStatus() != 1 ? null : this.overloadAction(reason);
      }

      public void run() {
         try {
            this.status.setType(3);

            try {
               if (this.listener != null) {
                  this.listener.workStarted(this.status);
               }
            } catch (Throwable var11) {
            }

            try {
               this.work.run();
            } catch (Throwable var10) {
               this.status.setThrowable(var10);
            }
         } finally {
            this.status.setType(4);

            try {
               if (this.listener != null) {
                  this.listener.workCompleted(this.status);
               }
            } catch (Throwable var9) {
            }

         }

      }

      public boolean isDaemon() {
         return this.work.isDaemon();
      }

      public void release() {
         this.work.release();
      }

      // $FF: synthetic method
      WorkWithListener(Work x0, WorkListener x1, WorkStatus x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static final class Callback {
      int count;
      boolean notifyWaitForAll;
      boolean notifyWaitForAny;
      ArrayList completedItems;

      private Callback() {
         this.completedItems = new ArrayList();
      }

      synchronized void add(WorkStatus status) {
         ++this.count;
         status.register(this);
      }

      boolean waitForAll(long timeoutMillis) throws InterruptedException {
         if (this.count == 0) {
            return true;
         } else {
            long timeoutTime = this.calculateTimeout(System.nanoTime(), timeoutMillis);
            synchronized(this) {
               if (this.count == 0) {
                  return true;
               } else {
                  this.notifyWaitForAll = true;
                  this.completedItems = null;

                  do {
                     if (System.nanoTime() >= timeoutTime) {
                        return false;
                     }

                     long waitTime = (timeoutTime - System.nanoTime()) / 1000000L;
                     if (waitTime > 0L) {
                        this.wait(waitTime);
                     }
                  } while(this.count != 0);

                  return true;
               }
            }
         }
      }

      synchronized void completed(WorkStatus status) {
         --this.count;
         if (this.completedItems != null) {
            this.completedItems.add(status);
         }

         if (this.notifyWaitForAll && this.count == 0) {
            this.notify();
         }

         if (this.notifyWaitForAny) {
            this.notify();
         }

      }

      synchronized Collection waitForAny(long timeoutMillis) throws InterruptedException {
         if (this.count == 0) {
            return this.completedItems;
         } else if (this.completedItems.size() > 0) {
            Collection temp = this.completedItems;
            this.completedItems = null;
            return temp;
         } else {
            this.notifyWaitForAny = true;
            long timeoutTime = this.calculateTimeout(System.nanoTime(), timeoutMillis);

            while(System.nanoTime() < timeoutTime && this.completedItems.isEmpty()) {
               long waitTime = (timeoutTime - System.nanoTime()) / 1000000L;
               if (waitTime > 0L) {
                  this.wait(waitTime);
               }
            }

            this.notifyWaitForAny = false;
            Collection temp = this.completedItems;
            this.completedItems = null;
            return temp;
         }
      }

      private long calculateTimeout(long nowInNs, long timeoutInMs) {
         long timeoutInNs = timeoutInMs * 1000000L;
         if (timeoutInNs < 0L) {
            return Long.MAX_VALUE;
         } else {
            long timeoutTime = nowInNs + timeoutInNs;
            return timeoutTime < 0L ? Long.MAX_VALUE : timeoutTime;
         }
      }

      // $FF: synthetic method
      Callback(Object x0) {
         this();
      }
   }
}
