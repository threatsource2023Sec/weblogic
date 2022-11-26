package weblogic.t3.srvr;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import org.glassfish.hk2.api.MultiException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.NodeManagerRuntimeService;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.filelock.FileLockService;

final class ServerLifeCycleTimerThread extends Thread {
   private static ServerLifeCycleTimerThread THE_ONE;
   private static final DebugLogger debugSUT = DebugLogger.getDebugLogger("DebugServerShutdownTimer");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private int timeout;
   private int numOfThreadDump;
   private int threadDumpInterval;
   private final int numOfTermThreadDump = 3;
   private final int termThreadDumpInterval = 30000;

   private ServerLifeCycleTimerThread() {
      if (ManagementService.isRuntimeAccessInitialized() && ManagementService.getRuntimeAccess(kernelId).getServer() != null) {
         this.timeout = ManagementService.getRuntimeAccess(kernelId).getServer().getServerLifeCycleTimeoutVal();
         this.numOfThreadDump = ManagementService.getRuntimeAccess(kernelId).getServer().getServerDebug().getForceShutdownTimeoutNumOfThreadDump();
         this.threadDumpInterval = ManagementService.getRuntimeAccess(kernelId).getServer().getServerDebug().getForceShutdownTimeoutThreadDumpInterval();
      } else {
         this.timeout = 30;
         this.numOfThreadDump = 0;
         this.threadDumpInterval = 0;
      }

   }

   static synchronized boolean isTimeBombStarted() {
      return THE_ONE != null;
   }

   static synchronized void startTimeBomb() {
      if (THE_ONE == null) {
         try {
            THE_ONE = new ServerLifeCycleTimerThread();
            THE_ONE.setDaemon(true);
            THE_ONE.start();
         } catch (Throwable var1) {
            var1.printStackTrace();
         }

      }
   }

   static void waitForForceShutdownCompletionIfNeeded(Exception e) {
      if (isTimeBombStarted()) {
         if (debugSUT.isDebugEnabled()) {
            debugSUT.debug("Once force shutdown process has been started, another shutdown thread needs to wait for force shutdown completion.");
            if (e != null) {
               debugSUT.debug("caught exception during graceful shutdown process. : " + e);
            }
         }

         Object syncObj = new Object();
         synchronized(syncObj){}

         try {
            while(true) {
               while(true) {
                  try {
                     syncObj.wait();
                  } catch (InterruptedException var7) {
                  }
               }
            }
         } finally {
            ;
         }
      }
   }

   public void run() {
      int sleepInterval = 10000;
      int remaintime;
      if (this.timeout == 0) {
         remaintime = Integer.MAX_VALUE;
      } else if (this.timeout < 2147483) {
         remaintime = this.timeout * 1000;
      } else {
         remaintime = Integer.MAX_VALUE;
      }

      if (sleepInterval > remaintime) {
         sleepInterval = remaintime;
      }

      while(sleepInterval > 0) {
         try {
            Thread.sleep((long)sleepInterval);
            remaintime -= sleepInterval;
            if (remaintime <= 0) {
               break;
            }

            if (remaintime < sleepInterval) {
               sleepInterval = remaintime;
            }
         } catch (InterruptedException var10) {
         }
      }

      for(int count = 0; count < this.numOfThreadDump; ++count) {
         if (debugSUT.isDebugEnabled()) {
            debugSUT.debug("force shutdown diagnostic is kicked. num of diag dumps:" + (this.numOfThreadDump - count));
         }

         SrvrUtilities.logThreadDump();

         try {
            Thread.sleep((long)this.threadDumpInterval);
         } catch (InterruptedException var9) {
         }
      }

      T3SrvrLogger.logShutdownTimedOut(this.timeout);
      Thread t = new Thread() {
         public void run() {
            for(int count = 0; count < 3; ++count) {
               try {
                  Thread.sleep(30000L);
               } catch (InterruptedException var3) {
               }

               SrvrUtilities.logThreadDump();
            }

         }
      };
      t.setDaemon(true);
      t.start();
      FileLockService fls = (FileLockService)LocatorUtilities.getService(FileLockService.class);
      if (fls != null) {
         fls.removeLockFiles();
      }

      SrvrUtilities.logThreadDump();

      try {
         NodeManagerRuntimeService nms = (NodeManagerRuntimeService)GlobalServiceLocator.getServiceLocator().getService(NodeManagerRuntimeService.class, new Annotation[0]);
         if (nms != null) {
            try {
               nms.hardShutdown();
            } catch (ServiceFailureException var7) {
            }
         }
      } catch (IllegalStateException | MultiException var8) {
         var8.printStackTrace();
      }

      Runtime.getRuntime().halt(-1);
   }
}
