package weblogic.cluster.replication;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ResourceGroupMigrationAsyncResult implements Future, Serializable {
   private final Object resultValue;

   public ResourceGroupMigrationAsyncResult(Object result) {
      this.resultValue = result;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      throw new IllegalStateException("Object does not represent an acutal Future");
   }

   public boolean isCancelled() {
      throw new IllegalStateException("Object does not represent an acutal Future");
   }

   public boolean isDone() {
      throw new IllegalStateException("Object does not represent an acutal Future");
   }

   public Object get() throws InterruptedException, ExecutionException {
      return this.resultValue;
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      throw new IllegalStateException("Object does not represent an acutal Future");
   }
}
