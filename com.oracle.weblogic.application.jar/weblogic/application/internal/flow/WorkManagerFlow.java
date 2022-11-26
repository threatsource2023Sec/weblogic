package weblogic.application.internal.flow;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import sun.misc.Unsafe;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.internal.ApplicationRuntimeMBeanImpl;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.management.DeploymentException;
import weblogic.work.RMIGracePeriodManager;
import weblogic.work.ShutdownCallback;
import weblogic.work.WorkManagerCollection;
import weblogic.work.WorkManagerLogger;
import weblogic.work.WorkManagerService;

public final class WorkManagerFlow extends BaseFlow {
   private static final Unsafe U;
   private static final long CATALOG;
   private static final Class odlLoggerClass;
   private static final long MLOGGER;

   public WorkManagerFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      try {
         if (this.isDebugEnabled()) {
            J2EELogger.logDebug("-- wmflow -- calling prepare on - " + this.appCtx.getApplicationId());
         }

         WorkManagerCollection collection = this.appCtx.getWorkManagerCollection();
         ApplicationRuntimeMBeanImpl runtime = this.appCtx.getRuntime();
         collection.setApplicationRuntime(runtime, runtime);
         collection.initialize(this.appCtx.getWLApplicationDD());
      } catch (DeploymentException var3) {
         throw new DeploymentException(var3);
      }
   }

   public void activate() throws DeploymentException {
      if (this.isDebugEnabled()) {
         J2EELogger.logDebug("-- wmflow -- calling activate on - " + this.appCtx.getApplicationId());
      }

      WorkManagerCollection collection = this.appCtx.getWorkManagerCollection();
      collection.setState(1);

      WorkManagerService wmService;
      for(Iterator iter = collection.iterator(); iter.hasNext(); wmService.start()) {
         wmService = (WorkManagerService)iter.next();
         if (this.isDebugEnabled()) {
            J2EELogger.logDebug("-- wmflow -- starting - " + wmService.toString());
         }
      }

   }

   public void adminToProduction() throws DeploymentException {
      if (this.isDebugEnabled()) {
         J2EELogger.logDebug("-- wmflow -- calling adminToProduction on - " + this.appCtx.getApplicationId());
      }

      WorkManagerCollection collection = this.appCtx.getWorkManagerCollection();
      if (collection.getState() != 1) {
         collection.setState(1);
         Iterator iter = collection.iterator();

         while(iter.hasNext()) {
            WorkManagerService wmService = (WorkManagerService)iter.next();
            if (wmService.isShutdown()) {
               if (this.isDebugEnabled()) {
                  J2EELogger.logDebug("-- wmflow -- starting - " + wmService.toString());
               }

               wmService.start();
            }
         }
      }

   }

   public void forceProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      if (this.isDebugEnabled()) {
         J2EELogger.logDebug("-- wmflow -- calling forceProductionToAdmin on - " + this.appCtx.getApplicationId());
      }

      WorkManagerCollection collection = this.appCtx.getWorkManagerCollection();
      collection.setState(0);

      WorkManagerService wmService;
      for(Iterator iter = collection.iterator(); iter.hasNext(); wmService.shutdown(barrier.registerWMShutdown())) {
         wmService = (WorkManagerService)iter.next();
         if (this.isDebugEnabled()) {
            J2EELogger.logDebug("-- wmflow -- shutdown no callback - " + wmService.toString());
         }
      }

   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      if (this.isDebugEnabled()) {
         J2EELogger.logDebug("-- wmflow -- calling gracefulProductionToAdmin on - " + this.appCtx.getApplicationId());
      }

      WorkManagerCollection collection = this.appCtx.getWorkManagerCollection();
      collection.setState(0);
      Iterator iter = collection.iterator();
      int rmiGracePeriod = ApplicationVersionUtils.getRMIGracePeriodAppCtxParam(this.appCtx);
      RMIGracePeriodManager rmiManager = null;
      if (rmiGracePeriod > 0) {
         rmiManager = new RMIGracePeriodManager(collection, rmiGracePeriod);
      }

      WorkManagerService wmService;
      for(; iter.hasNext(); wmService.shutdown(barrier.registerWMShutdown())) {
         wmService = (WorkManagerService)iter.next();
         if (this.isDebugEnabled()) {
            J2EELogger.logDebug("-- wmflow -- shutdown with callback - " + wmService.toString());
         }

         if (rmiGracePeriod > 0) {
            wmService.startRMIGracePeriod(rmiManager);
         }
      }

   }

   public void deactivate() throws DeploymentException {
      if (this.isDebugEnabled()) {
         J2EELogger.logDebug("-- wmflow -- calling deactivate on - " + this.appCtx.getApplicationId());
      }

      WorkManagerCollection collection = this.appCtx.getWorkManagerCollection();
      if (collection.getState() != 0) {
         collection.setState(0);
         ShutdownCallback callback = new ShutdownCallback() {
            public void completed() {
            }
         };

         WorkManagerService wmService;
         for(Iterator iter = collection.iterator(); iter.hasNext(); wmService.shutdown(callback)) {
            wmService = (WorkManagerService)iter.next();
            if (this.isDebugEnabled()) {
               J2EELogger.logDebug("-- wmflow -- shutdown with noop callback - " + wmService.toString());
            }
         }
      }

   }

   public void unprepare() throws DeploymentException {
      WorkManagerCollection collection = this.appCtx.getWorkManagerCollection();
      if (collection != null) {
         collection.close();
      }

      this.eraseLoggerCatalogs();
   }

   private final void eraseLoggerCatalogs() {
      if (U != null) {
         LogManager manager = LogManager.getLogManager();
         Enumeration loggerNames = manager.getLoggerNames();

         while(loggerNames.hasMoreElements()) {
            String name = (String)loggerNames.nextElement();
            Logger l = manager.getLogger(name);
            if (l != null) {
               this.eraseLogger(l);
               if (odlLoggerClass != null && odlLoggerClass.isAssignableFrom(l.getClass())) {
                  Object ml = U.getObject(l, MLOGGER);
                  if (ml != null) {
                     this.eraseLogger(ml);
                  }
               }
            }
         }

      }
   }

   private final void eraseLogger(Object l) {
      Object catalog = U.getObject(l, CATALOG);
      if (catalog != null && this.isAncestorLoader(this.appCtx.getAppClassLoader(), catalog.getClass().getClassLoader())) {
         synchronized(l) {
            U.putObject(l, CATALOG, (Object)null);
         }
      }

   }

   private final boolean isAncestorLoader(ClassLoader ancestor, ClassLoader loader) {
      if (ancestor == loader) {
         return true;
      } else {
         return loader != null ? this.isAncestorLoader(ancestor, loader.getParent()) : false;
      }
   }

   static {
      Unsafe unsafe = null;
      long catalog = 0L;
      long m_logger = 0L;

      try {
         Field f = Unsafe.class.getDeclaredField("theUnsafe");
         f.setAccessible(true);
         unsafe = (Unsafe)f.get((Object)null);
         Class tk = Logger.class;
         catalog = unsafe.objectFieldOffset(tk.getDeclaredField("catalog"));
      } catch (Throwable var19) {
         WorkManagerLogger.logThreadLocalCleanupDisabled(var19);
      } finally {
         U = unsafe;
         CATALOG = catalog;
      }

      Class olc = null;

      try {
         olc = Class.forName("oracle.core.ojdl.logging.ODLLogger");
         m_logger = unsafe.objectFieldOffset(olc.getDeclaredField("m_logger"));
      } catch (Throwable var17) {
         olc = null;
      } finally {
         odlLoggerClass = olc;
         MLOGGER = m_logger;
      }

   }
}
