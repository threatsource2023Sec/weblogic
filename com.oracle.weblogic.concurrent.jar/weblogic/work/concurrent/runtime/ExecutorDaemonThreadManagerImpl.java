package weblogic.work.concurrent.runtime;

import weblogic.logging.Loggable;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.spi.DaemonThreadManagerImpl;
import weblogic.work.concurrent.spi.ExceedThreadLimitException;
import weblogic.work.concurrent.spi.RejectException;
import weblogic.work.concurrent.spi.ServiceShutdownException;
import weblogic.work.concurrent.spi.ThreadCreationChecker;

public class ExecutorDaemonThreadManagerImpl extends DaemonThreadManagerImpl {
   public ExecutorDaemonThreadManagerImpl(String name, int maxThreads, String partitionName) {
      super(maxThreads, name, partitionName);
      GlobalConstraints globalConstraints = GlobalConstraints.getExecutorConstraints();
      this.addThreadCreationChecker(globalConstraints.getServerLimit());
      this.addThreadCreationChecker(globalConstraints.getPartitionLimit(partitionName));
      this.addThreadCreationChecker(new ThreadCreationChecker() {
         public void undo() {
            ConcurrentManagedObjectsRuntimeMBeanImpl globalRuntimeMbean = ExecutorDaemonThreadManagerImpl.this.getConcurrentManagedObjectsRuntimeMBean();
            if (globalRuntimeMbean != null) {
               globalRuntimeMbean.releaseLongRunning();
            }

         }

         public void acquire() throws RejectException {
            ConcurrentManagedObjectsRuntimeMBeanImpl globalRuntimeMbean = ExecutorDaemonThreadManagerImpl.this.getConcurrentManagedObjectsRuntimeMBean();
            if (globalRuntimeMbean != null) {
               globalRuntimeMbean.addLongRunning();
            }

         }
      });
   }

   protected void checkThreadCreation() throws RejectException {
      boolean failed = true;
      boolean var9 = false;

      try {
         Loggable loggable;
         try {
            var9 = true;
            super.checkThreadCreation();
            failed = false;
            var9 = false;
         } catch (ServiceShutdownException var10) {
            loggable = ConcurrencyLogger.logNewLongRunningThreadStateErrorLoggable(this.getName());
            loggable.log();
            var10.setMessage(loggable.getMessage());
            throw var10;
         } catch (ExceedThreadLimitException var11) {
            loggable = ConcurrencyLogger.logLongRunningThreadRejectedLoggable(this.getName(), var11.getLimit(), var11.getLimitType());
            var11.setMessage(loggable.getMessage());
            loggable.log();
            throw var11;
         }
      } finally {
         if (var9) {
            if (failed) {
               ConcurrentManagedObjectsRuntimeMBeanImpl globalRuntimeMbean = this.getConcurrentManagedObjectsRuntimeMBean();
               if (globalRuntimeMbean != null) {
                  globalRuntimeMbean.incrementRejectedLongRunning();
               }
            }

         }
      }

      if (failed) {
         ConcurrentManagedObjectsRuntimeMBeanImpl globalRuntimeMbean = this.getConcurrentManagedObjectsRuntimeMBean();
         if (globalRuntimeMbean != null) {
            globalRuntimeMbean.incrementRejectedLongRunning();
         }
      }

   }
}
