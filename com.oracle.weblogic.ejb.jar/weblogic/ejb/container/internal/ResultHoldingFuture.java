package weblogic.ejb.container.internal;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

final class ResultHoldingFuture implements Future, Serializable {
   private static final long serialVersionUID = 1L;
   private final ExecutionException resultedException;
   private final Object result;

   ResultHoldingFuture(Throwable th) {
      this.resultedException = new ExecutionException(th);
      this.result = null;
   }

   ResultHoldingFuture(Object resultObj) {
      this.result = resultObj;
      this.resultedException = null;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return false;
   }

   public Object get() throws ExecutionException {
      if (this.resultedException != null) {
         throw this.resultedException;
      } else {
         return this.result;
      }
   }

   public Object get(long timeout, TimeUnit unit) throws ExecutionException {
      return this.get();
   }

   public boolean isCancelled() {
      return false;
   }

   public boolean isDone() {
      return true;
   }
}
