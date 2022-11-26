package weblogic.t3.srvr;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.logging.LoggingHelper;
import weblogic.management.provider.ManagementService;
import weblogic.platform.VM;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.RunningStateListener;
import weblogic.server.ServerService;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class SrvrUtilities {
   private static AuthenticatedSubject kernelId;
   private static long gracefulShutdownOverride = -1L;
   private static final List runningListeners = new CopyOnWriteArrayList();

   static void logThreadDump() {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      VM.getVM().threadDump(pw);
      pw.close();
      LoggingHelper.getServerLogger().severe(sw.toString());
   }

   private static void initializeKernelId() {
      if (kernelId == null) {
         kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      }
   }

   static long getStartupTimeout() {
      initializeKernelId();
      return (long)(ManagementService.getRuntimeAccess(kernelId).getServer().getStartupTimeout() * 1000);
   }

   static int getStartupTimeoutNumOfThreadDump() {
      initializeKernelId();
      return !ManagementService.isRuntimeAccessInitialized() ? 0 : ManagementService.getRuntimeAccess(kernelId).getServer().getServerDebug().getStartupTimeoutNumOfThreadDump();
   }

   static int getStartupTimeoutThreadDumpInterval() {
      initializeKernelId();
      return !ManagementService.isRuntimeAccessInitialized() ? 0 : ManagementService.getRuntimeAccess(kernelId).getServer().getServerDebug().getStartupTimeoutThreadDumpInterval();
   }

   static void setGracefulShutdownOverride(long gracefulShutdown) {
      gracefulShutdownOverride = gracefulShutdown;
   }

   static long getGracefulShutdownTimeout() {
      if (gracefulShutdownOverride > 0L) {
         return gracefulShutdownOverride;
      } else {
         initializeKernelId();
         return !ManagementService.isRuntimeAccessInitialized() ? 0L : (long)(ManagementService.getRuntimeAccess(kernelId).getServer().getGracefulShutdownTimeout() * 1000);
      }
   }

   static int getGracefulShutdownTimeoutNumOfThreadDump() {
      initializeKernelId();
      return ManagementService.getRuntimeAccess(kernelId).getServer().getServerDebug().getGracefulShutdownTimeoutNumOfThreadDump();
   }

   static int getGracefulShutdownTimeoutThreadDumpInterval() {
      initializeKernelId();
      return !ManagementService.isRuntimeAccessInitialized() ? 0 : ManagementService.getRuntimeAccess(kernelId).getServer().getServerDebug().getGracefulShutdownTimeoutThreadDumpInterval();
   }

   static long getForcedShutdownTimeout() {
      initializeKernelId();
      return (long)(ManagementService.getRuntimeAccess(kernelId).getServer().getServerLifeCycleTimeoutVal() * 1000);
   }

   static HashMap getVersionsOnline() {
      if (T3Srvr.getT3Srvr().getRunState() != 2) {
         throw new IllegalStateException("Cannot get ServerServices information till startup is complete");
      } else {
         HashMap map = new HashMap();
         ServiceLocator locator = GlobalServiceLocator.getServiceLocator();
         Collection coll = locator.getAllServices(ServerService.class, new Annotation[0]);
         Iterator var3 = coll.iterator();

         while(var3.hasNext()) {
            ServerService ss = (ServerService)var3.next();
            if (ss.getVersion() != null && ss.getVersion().trim().length() > 0) {
               map.put(ss.getName(), ss.getVersion());
            }
         }

         return map;
      }
   }

   private static boolean serverIsAlreadyRunning() {
      return T3Srvr.getT3Srvr().getRunState() == 2;
   }

   public static void addRunningStateListener(RunningStateListener listener) {
      if (serverIsAlreadyRunning()) {
         scheduleOnRunningCallback(listener);
      } else {
         runningListeners.add(listener);
      }

   }

   public static void removeRunningStateListener(RunningStateListener listener) {
      runningListeners.remove(listener);
   }

   static void invokeRunningStateListeners() {
      WorkManagerFactory.getInstance().getSystem().schedule(new WorkAdapter() {
         public void run() {
            Iterator var1 = SrvrUtilities.runningListeners.iterator();

            while(var1.hasNext()) {
               RunningStateListener rsl = (RunningStateListener)var1.next();
               rsl.onRunning();
            }

         }
      });
   }

   private static void scheduleOnRunningCallback(final RunningStateListener listener) {
      WorkManagerFactory.getInstance().getSystem().schedule(new WorkAdapter() {
         public void run() {
            listener.onRunning();
         }
      });
   }
}
