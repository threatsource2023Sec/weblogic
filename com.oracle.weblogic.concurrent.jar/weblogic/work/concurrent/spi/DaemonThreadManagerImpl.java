package weblogic.work.concurrent.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.management.ManagementException;
import weblogic.work.concurrent.AbstractManagedThread;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.ExecutorDaemonThread;
import weblogic.work.concurrent.ManagedThread;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectsRuntimeMBeanImpl;
import weblogic.work.concurrent.runtime.RuntimeAccessUtils;
import weblogic.work.concurrent.utils.LogUtils;

public class DaemonThreadManagerImpl implements DaemonThreadManager {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private final List threads = new ArrayList();
   private String name;
   private AtomicLong sequence = new AtomicLong();
   private final AtomicLong rejectedThreads = new AtomicLong();
   private final AtomicLong completedThreads = new AtomicLong();
   private final ThreadNumberConstraints localNumberConstraints;
   private final ServerStatusChecker serverStatusChecker = new ServerStatusChecker();
   private List checkers;
   protected String partitionName;

   public DaemonThreadManagerImpl(int maxThreads, String name, String partitionName) {
      this.localNumberConstraints = new ThreadNumberConstraints(maxThreads, "local");
      this.name = name;
      this.partitionName = partitionName;
      this.checkers = new ArrayList();
      this.checkers.add(this.serverStatusChecker);
      this.checkers.add(this.localNumberConstraints);
   }

   public final int getRunningThreadCount() {
      synchronized(this) {
         return this.getThreads().size();
      }
   }

   protected final String getName() {
      return this.name;
   }

   protected Collection getThreads() {
      return this.threads;
   }

   protected void addThread(AbstractManagedThread thread) {
      this.threads.add(thread);
   }

   protected boolean removeThread(AbstractManagedThread thread) {
      return this.threads.remove(thread);
   }

   public int getLimit() {
      return this.localNumberConstraints.getLimit();
   }

   public final boolean threadTerminate(AbstractManagedThread thread) {
      boolean contained;
      synchronized(this) {
         contained = this.removeThread(thread);
      }

      if (contained) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("removed thread " + thread.getName());
         }

         for(int i = this.checkers.size() - 1; i >= 0; --i) {
            ((ThreadCreationChecker)this.checkers.get(i)).undo();
         }

         this.completedThreads.incrementAndGet();
      }

      return contained;
   }

   public final boolean start() {
      ArrayList copyList;
      synchronized(this) {
         if (!this.serverStatusChecker.start()) {
            return false;
         }

         copyList = new ArrayList(this.getThreads());
      }

      Iterator var2 = copyList.iterator();

      while(var2.hasNext()) {
         AbstractManagedThread thread = (AbstractManagedThread)var2.next();
         ConcurrencyLogger.logOutofDateThreadLeft(this.name, thread.getName());
      }

      copyList.clear();
      return true;
   }

   public final boolean stop() {
      ArrayList copyList;
      synchronized(this) {
         if (!this.serverStatusChecker.stop()) {
            return false;
         }

         copyList = new ArrayList(this.getThreads());
      }

      AbstractManagedThread thread;
      for(Iterator var2 = copyList.iterator(); var2.hasNext(); thread.shutdown(LogUtils.getMessageCancelForStop(this.name))) {
         thread = (AbstractManagedThread)var2.next();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(String.format("thread %s is still running while executor %s is shutdown.", thread.getName(), this.name));
         }
      }

      copyList.clear();
      return true;
   }

   public void threadStart(AbstractManagedThread thread) {
      synchronized(this) {
         if (!this.serverStatusChecker.isStarted()) {
            thread.shutdown(LogUtils.getMessageCancelForStop(this.name));
         }

         this.addThread(thread);
      }
   }

   public boolean isStarted() {
      return this.serverStatusChecker.isStarted();
   }

   public long getCompletedThreads() {
      return this.completedThreads.get();
   }

   public long getRejectedThreads() {
      return this.rejectedThreads.get();
   }

   private String nextThreadName() {
      return String.format("DaemonConcurrentThread: '%d' of '%s'", this.sequence.incrementAndGet(), this.name);
   }

   public final ExecutorDaemonThread createAndStart(DaemonThreadTask task) throws RejectException {
      if (task.getThread() != null) {
         IllegalStateException ex = new IllegalStateException(String.format("long running thread %s for task %s has already been started", task.getThread().getName(), this.name));
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(ex.getMessage(), ex);
         }

         throw ex;
      } else {
         this.checkThreadCreation();
         ExecutorDaemonThread thread = new ExecutorDaemonThread(task, this);
         thread.setName(this.nextThreadName());
         thread.setPriority(task.getPriority());
         thread.setDaemon(true);
         task.setThread(thread);
         thread.start();
         return thread;
      }
   }

   protected void checkThreadCreation() throws RejectException {
      int i = 0;

      try {
         for(i = 0; i < this.checkers.size(); ++i) {
            ((ThreadCreationChecker)this.checkers.get(i)).acquire();
         }

      } catch (RejectException var4) {
         for(int j = i - 1; j >= 0; --j) {
            ((ThreadCreationChecker)this.checkers.get(j)).undo();
         }

         this.rejectedThreads.incrementAndGet();
         throw var4;
      }
   }

   public final ManagedThread create(Runnable target, ContextProvider contextSetupProcessor, int priority) throws RejectException {
      this.checkThreadCreation();
      ManagedThread thread = new ManagedThread(target, contextSetupProcessor, this);
      thread.setName(this.nextThreadName());
      thread.setPriority(priority);
      thread.setDaemon(true);
      this.threadStart(thread);
      return thread;
   }

   protected final void addThreadCreationChecker(ThreadCreationChecker checker) {
      this.checkers.add(checker);
   }

   protected ConcurrentManagedObjectsRuntimeMBeanImpl getConcurrentManagedObjectsRuntimeMBean() {
      try {
         return RuntimeAccessUtils.getConcurrentManagedObjectsRuntime(this.partitionName);
      } catch (ManagementException var2) {
         return null;
      }
   }

   public void shutdownThreadsSubmittedBy(String applicationId) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(String.format("shutdown threads because its submitting component %s is shutdown.", applicationId));
      }

      ArrayList copyList;
      synchronized(this) {
         copyList = new ArrayList(this.getThreads());
      }

      Iterator var3 = copyList.iterator();

      while(var3.hasNext()) {
         AbstractManagedThread thread = (AbstractManagedThread)var3.next();
         ComponentInvocationContext submittingCIC = thread.getSubmittingCICInSharing();
         if (submittingCIC != null && applicationId.equals(submittingCIC.getApplicationId())) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(String.format("shutdown thread %s because its submitting component %s is shutdown.", thread.getName(), applicationId));
            }

            thread.shutdown(ConcurrencyLogger.logCancelForSubmittingCompStopLoggable(applicationId).getMessage());
         }
      }

      copyList.clear();
   }
}
