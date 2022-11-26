package weblogic.t3.srvr;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jvnet.hk2.annotations.Service;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
public class WebLogicExecutor implements Executor {
   private static final Executor DEFAULT_EXECUTOR;
   private volatile Executor currentExecutor;

   public WebLogicExecutor() {
      this.currentExecutor = DEFAULT_EXECUTOR;
   }

   public void execute(Runnable command) {
      this.currentExecutor.execute(command);
   }

   public synchronized void serverWorkManagerInitialized() {
      this.currentExecutor = new WebLogicServerExecutor();
   }

   static {
      DEFAULT_EXECUTOR = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 1L, TimeUnit.MILLISECONDS, new SynchronousQueue(true), new WebLogicThreadFactory());
   }

   private static class WebLogicThreadFactory implements ThreadFactory {
      private WebLogicThreadFactory() {
      }

      public Thread newThread(Runnable runnable) {
         Thread activeThread = new Thread(runnable);
         activeThread.setDaemon(true);
         return activeThread;
      }

      // $FF: synthetic method
      WebLogicThreadFactory(Object x0) {
         this();
      }
   }

   private static class WebLogicServerExecutor implements Executor {
      private final WorkManager systemWorkManager;

      private WebLogicServerExecutor() {
         this.systemWorkManager = WorkManagerFactory.getInstance().getSystem();
      }

      public void execute(Runnable command) {
         this.systemWorkManager.schedule(command);
      }

      // $FF: synthetic method
      WebLogicServerExecutor(Object x0) {
         this();
      }
   }
}
