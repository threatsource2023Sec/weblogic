package commonj.work;

import java.util.Collection;

public interface WorkManager {
   long IMMEDIATE = 0L;
   long INDEFINITE = Long.MAX_VALUE;

   WorkItem schedule(Work var1) throws WorkException, IllegalArgumentException;

   WorkItem schedule(Work var1, WorkListener var2) throws WorkException, IllegalArgumentException;

   boolean waitForAll(Collection var1, long var2) throws InterruptedException, IllegalArgumentException;

   Collection waitForAny(Collection var1, long var2) throws InterruptedException, IllegalArgumentException;
}
