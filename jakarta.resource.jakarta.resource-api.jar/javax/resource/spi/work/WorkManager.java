package javax.resource.spi.work;

public interface WorkManager {
   long IMMEDIATE = 0L;
   long INDEFINITE = Long.MAX_VALUE;
   long UNKNOWN = -1L;

   void doWork(Work var1) throws WorkException;

   void doWork(Work var1, long var2, ExecutionContext var4, WorkListener var5) throws WorkException;

   long startWork(Work var1) throws WorkException;

   long startWork(Work var1, long var2, ExecutionContext var4, WorkListener var5) throws WorkException;

   void scheduleWork(Work var1) throws WorkException;

   void scheduleWork(Work var1, long var2, ExecutionContext var4, WorkListener var5) throws WorkException;
}
