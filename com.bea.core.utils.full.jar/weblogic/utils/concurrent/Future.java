package weblogic.utils.concurrent;

public interface Future {
   boolean cancel(boolean var1);

   boolean isCancelled();

   boolean isDone();

   Object get() throws CancellationException, ExecutionException, InterruptedException;

   Object get(long var1) throws CancellationException, ExecutionException, InterruptedException, TimeoutException;
}
