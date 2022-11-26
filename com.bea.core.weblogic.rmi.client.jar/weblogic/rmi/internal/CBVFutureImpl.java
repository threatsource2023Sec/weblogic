package weblogic.rmi.internal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class CBVFutureImpl implements Future {
   private volatile Future del;
   private volatile boolean resultCaptured = false;
   private volatile Object copiedResult;
   private volatile CBVWrapper cbvWrapper;

   public CBVFutureImpl(Future f, CBVWrapper wrapper) {
      this.del = f;
      this.cbvWrapper = wrapper;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return this.del.cancel(mayInterruptIfRunning);
   }

   public boolean isCancelled() {
      return this.del.isCancelled();
   }

   public boolean isDone() {
      return this.del.isDone();
   }

   public Object get() throws InterruptedException, ExecutionException {
      if (!this.resultCaptured) {
         this.getResultInternal(this.del.get());
      }

      return this.copiedResult;
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      if (!this.resultCaptured) {
         this.getResultInternal(this.del.get(timeout, unit));
      }

      return this.copiedResult;
   }

   private synchronized void getResultInternal(Object v) {
      if (!this.resultCaptured) {
         if (v != null) {
            if (this.shouldCopy(v.getClass())) {
               this.copiedResult = this.cbvWrapper.copy(v);
            } else {
               this.copiedResult = v;
            }
         }

         this.resultCaptured = true;
      }
   }

   private boolean shouldCopy(Class c) {
      if (c.isPrimitive()) {
         return false;
      } else if (c == String.class) {
         return false;
      } else if (c == Boolean.class) {
         return false;
      } else if (c == Byte.class) {
         return false;
      } else if (c == Character.class) {
         return false;
      } else if (c == Double.class) {
         return false;
      } else if (c == Float.class) {
         return false;
      } else if (c == Integer.class) {
         return false;
      } else if (c == Long.class) {
         return false;
      } else if (c == Short.class) {
         return false;
      } else {
         return c != Void.class;
      }
   }
}
