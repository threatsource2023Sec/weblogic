package weblogic.common;

import java.util.LinkedList;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class CompletionRequest implements Runnable {
   private Object result = this;
   private int numWaiters = 0;
   private LinkedList listeners = new LinkedList();
   private boolean running;
   private boolean runListenersInSetResult;
   private WorkManager workManager;

   public void setResult(Object result) {
      synchronized(this) {
         if (this.hasResult()) {
            throw new IllegalStateException("Result has already been set");
         }

         this.result = result;
         if (this.numWaiters > 0) {
            this.notifyAll();
         }

         if (this.listeners.isEmpty()) {
            return;
         }

         this.running = true;
      }

      if (this.runListenersInSetResult) {
         this.run();
      } else {
         this.workManager.schedule(this);
      }

   }

   public synchronized boolean runListenersInSetResult(boolean rlisr) {
      boolean oldValue = this.runListenersInSetResult;
      this.runListenersInSetResult = rlisr;
      return oldValue;
   }

   public synchronized Object getResult() throws Throwable {
      while(!this.hasResult()) {
         try {
            ++this.numWaiters;
            this.wait();
         } catch (InterruptedException var5) {
         } finally {
            --this.numWaiters;
         }
      }

      if (this.result instanceof Throwable) {
         throw (Throwable)this.result;
      } else {
         return this.result;
      }
   }

   public void addListener(CompletionListener listener) {
      this.addListener(listener, WorkManagerFactory.getInstance().getSystem());
   }

   public void addListener(CompletionListener listener, WorkManager workManager) {
      if (listener != null && workManager != null) {
         synchronized(this) {
            this.workManager = workManager;
            this.listeners.addLast(listener);
            if (!this.hasResult() || this.running) {
               return;
            }

            this.running = true;
         }

         if (this.runListenersInSetResult) {
            this.run();
         } else {
            workManager.schedule(this);
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   public void addFirstListener(CompletionListener listener) {
      this.addFirstListener(listener, WorkManagerFactory.getInstance().getSystem());
   }

   public void addFirstListener(CompletionListener listener, WorkManager workManager) {
      if (listener != null && workManager != null) {
         synchronized(this) {
            this.workManager = workManager;
            this.listeners.addFirst(listener);
            if (!this.hasResult() || this.running) {
               return;
            }

            this.running = true;
         }

         if (this.runListenersInSetResult) {
            this.run();
         } else {
            workManager.schedule(this);
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   public synchronized boolean removeListener(CompletionListener listener) {
      return this.listeners.remove(listener);
   }

   public synchronized void reset() {
      this.runListenersInSetResult = false;
      if (!this.running && this.numWaiters <= 0 && this.listeners.size() == 0) {
         this.result = this;
      } else {
         throw new IllegalStateException();
      }
   }

   public void run() {
      while(true) {
         boolean var13 = false;

         try {
            label98: {
               var13 = true;
               boolean isThrowable;
               CompletionListener listener;
               synchronized(this) {
                  if (this.listeners.isEmpty()) {
                     this.running = false;
                     var13 = false;
                     break label98;
                  }

                  listener = (CompletionListener)this.listeners.removeFirst();
                  isThrowable = this.result instanceof Throwable;
               }

               if (isThrowable) {
                  listener.onException(this, (Throwable)this.result);
                  continue;
               }

               listener.onCompletion(this, this.result);
               continue;
            }
         } finally {
            if (var13) {
               synchronized(this) {
                  this.running = false;
               }
            }
         }

         synchronized(this) {
            this.running = false;
            return;
         }
      }
   }

   public boolean hasResult() {
      return this.result != this;
   }
}
