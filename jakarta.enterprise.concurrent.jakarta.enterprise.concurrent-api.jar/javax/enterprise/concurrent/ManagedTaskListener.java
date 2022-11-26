package javax.enterprise.concurrent;

import java.util.concurrent.Future;

public interface ManagedTaskListener {
   void taskSubmitted(Future var1, ManagedExecutorService var2, Object var3);

   void taskAborted(Future var1, ManagedExecutorService var2, Object var3, Throwable var4);

   void taskDone(Future var1, ManagedExecutorService var2, Object var3, Throwable var4);

   void taskStarting(Future var1, ManagedExecutorService var2, Object var3);
}
