package weblogic.messaging.kernel;

import java.util.LinkedList;
import weblogic.utils.collections.AbstractEmbeddedListElement;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class KernelRequest extends AbstractEmbeddedListElement implements Runnable {
   private Object result;
   private int numWaiters;
   private LinkedList listeners;
   private boolean running;
   private int state;
   private KernelRequest childRequest;
   private WorkManager workManager;

   public KernelRequest() {
      this.result = this;
   }

   public KernelRequest(Object result) {
      if (result instanceof Throwable && !(result instanceof KernelException) && !(result instanceof RuntimeException)) {
         result = new KernelException(((Throwable)result).getMessage(), (Throwable)result);
      }

      this.result = result;
   }

   public void setResult(Object result) {
      this.setResult(result, true);
   }

   public void setResult(Object result, boolean dispatchRequired) {
      WorkManager manager;
      synchronized(this) {
         if (this.hasResult()) {
            throw new java.lang.IllegalStateException("Result has already been set");
         }

         this.result = result;
         if (result instanceof Throwable && !(result instanceof KernelException) && !(result instanceof RuntimeException)) {
            this.result = new KernelException(((Throwable)result).getMessage(), (Throwable)result);
         }

         if (this.numWaiters > 0) {
            this.notifyAll();
         }

         if (this.listeners == null || this.listeners.isEmpty()) {
            return;
         }

         this.running = true;
         manager = this.workManager;
      }

      if (dispatchRequired) {
         manager.schedule(this);
      } else {
         this.run();
      }

   }

   public synchronized Object getResult() throws KernelException {
      while(!this.hasResult()) {
         try {
            ++this.numWaiters;
            this.wait();
         } catch (InterruptedException var5) {
         } finally {
            --this.numWaiters;
         }
      }

      if (this.result instanceof KernelException) {
         throw (KernelException)this.result;
      } else if (this.result instanceof RuntimeException) {
         throw (RuntimeException)this.result;
      } else {
         return this.result;
      }
   }

   public void addListener(KernelListener listener) {
      this.addListener(listener, WorkManagerFactory.getInstance().getSystem());
   }

   public void addListener(KernelListener listener, WorkManager workManager) {
      if (listener != null && workManager != null) {
         synchronized(this) {
            if (this.listeners == null) {
               this.listeners = new LinkedList();
            }

            this.workManager = workManager;
            this.listeners.addLast(listener);
            if (!this.hasResult() || this.running) {
               return;
            }

            this.running = true;
         }

         workManager.schedule(this);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public synchronized boolean removeListener(KernelListener listener) {
      return this.listeners == null ? false : this.listeners.remove(listener);
   }

   public synchronized void reset() {
      if (!this.running && this.numWaiters <= 0 && (this.listeners == null || this.listeners.size() == 0)) {
         this.result = this;
      } else {
         throw new java.lang.IllegalStateException();
      }
   }

   public void run() {
      while(true) {
         boolean var13 = false;

         try {
            label106: {
               var13 = true;
               Object result;
               KernelListener listener;
               synchronized(this) {
                  if (this.listeners == null) {
                     var13 = false;
                     break label106;
                  }

                  if (this.listeners.isEmpty()) {
                     var13 = false;
                     break label106;
                  }

                  listener = (KernelListener)this.listeners.removeFirst();
                  result = this.result;
               }

               if (result instanceof Throwable) {
                  listener.onException(this, (Throwable)result);
                  continue;
               }

               listener.onCompletion(this, result);
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

   public synchronized boolean hasResult() {
      return this.result != this;
   }

   public int getState() {
      return this.state;
   }

   public void setState(int state) {
      this.state = state;
   }

   public void setChildRequest(KernelRequest childRequest) {
      this.childRequest = childRequest;
   }

   public KernelRequest getChildRequest() {
      return this.childRequest;
   }
}
