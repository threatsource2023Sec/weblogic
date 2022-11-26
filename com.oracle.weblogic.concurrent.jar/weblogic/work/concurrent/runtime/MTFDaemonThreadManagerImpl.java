package weblogic.work.concurrent.runtime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.logging.Loggable;
import weblogic.work.concurrent.AbstractManagedThread;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.spi.DaemonThreadManagerImpl;
import weblogic.work.concurrent.spi.ExceedThreadLimitException;
import weblogic.work.concurrent.spi.RejectException;
import weblogic.work.concurrent.spi.ServiceShutdownException;
import weblogic.work.concurrent.spi.ThreadCreationChecker;

public class MTFDaemonThreadManagerImpl extends DaemonThreadManagerImpl {
   private Map threads = new HashMap();

   public MTFDaemonThreadManagerImpl(String name, int maxThreads, String partitionName) {
      super(maxThreads, name, partitionName);
      GlobalConstraints globalConstraints = GlobalConstraints.getMTFConstraints();
      this.addThreadCreationChecker(globalConstraints.getServerLimit());
      this.addThreadCreationChecker(globalConstraints.getPartitionLimit(partitionName));
      this.addThreadCreationChecker(new ThreadCreationChecker() {
         public void undo() {
            ConcurrentManagedObjectsRuntimeMBeanImpl globalRuntimeMbean = MTFDaemonThreadManagerImpl.this.getConcurrentManagedObjectsRuntimeMBean();
            if (globalRuntimeMbean != null) {
               globalRuntimeMbean.releaseNewThread();
            }

         }

         public void acquire() throws RejectException {
            ConcurrentManagedObjectsRuntimeMBeanImpl globalRuntimeMbean = MTFDaemonThreadManagerImpl.this.getConcurrentManagedObjectsRuntimeMBean();
            if (globalRuntimeMbean != null) {
               globalRuntimeMbean.addNewThread();
            }

         }
      });
   }

   protected Collection getThreads() {
      List threadsList = new ArrayList();
      Iterator var2 = this.threads.values().iterator();

      while(var2.hasNext()) {
         WeakReference mtRef = (WeakReference)var2.next();
         AbstractManagedThread amt = (AbstractManagedThread)mtRef.get();
         if (amt != null) {
            threadsList.add(amt);
         }
      }

      return threadsList;
   }

   protected void addThread(AbstractManagedThread thread) {
      this.threads.put(thread.getName(), new WeakReference(thread));
   }

   protected boolean removeThread(AbstractManagedThread thread) {
      return this.threads.remove(thread.getName()) != null;
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
            loggable = ConcurrencyLogger.logNewThreadStateErrorLoggable(this.getName());
            loggable.log();
            var10.setMessage(loggable.getMessage());
            throw var10;
         } catch (ExceedThreadLimitException var11) {
            loggable = ConcurrencyLogger.logNewThreadRejectedLoggable(this.getName(), var11.getLimit(), var11.getLimitType());
            var11.setMessage(loggable.getMessage());
            loggable.log();
            throw var11;
         }
      } finally {
         if (var9) {
            if (failed) {
               ConcurrentManagedObjectsRuntimeMBeanImpl globalRuntimeMbean = this.getConcurrentManagedObjectsRuntimeMBean();
               if (globalRuntimeMbean != null) {
                  globalRuntimeMbean.incrementRejectedNewThread();
               }
            }

         }
      }

      if (failed) {
         ConcurrentManagedObjectsRuntimeMBeanImpl globalRuntimeMbean = this.getConcurrentManagedObjectsRuntimeMBean();
         if (globalRuntimeMbean != null) {
            globalRuntimeMbean.incrementRejectedNewThread();
         }
      }

   }
}
