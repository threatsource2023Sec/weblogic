package weblogic.application.internal.flow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.application.internal.FlowContext;
import weblogic.application.utils.StateChange;
import weblogic.application.utils.StateChangeException;
import weblogic.application.utils.StateMachineDriver;
import weblogic.j2ee.J2EELogger;
import weblogic.management.DeploymentException;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StackTraceUtils;

public final class DeploymentCallbackFlow extends BaseFlow {
   private static final String parallelPrepareDefault = "true";
   private static final boolean parallelPrepareEnabled = Boolean.valueOf(System.getProperty("weblogic.application.ParallelPrepare", "true"));
   private final ModuleStateDriver modDriver;
   private final StateMachineDriver puDriver = new StateMachineDriver();
   private final StateChange prepareUpdateChange = new StateChange() {
      public String toString() {
         return "prepareUpdate";
      }

      public void next(PendingUpdate p) throws Exception {
         p.listener.prepareUpdate(p.uri);
      }

      public void previous(PendingUpdate p) throws Exception {
         p.listener.rollbackUpdate(p.uri);
      }

      public void logRollbackError(StateChangeException e) {
         if (DeploymentCallbackFlow.this.isDebugEnabled()) {
            DeploymentCallbackFlow.this.debug("Ignoring errors while rolling back update " + StackTraceUtils.throwable2StackTrace(e.getCause()));
         }

      }
   };

   public DeploymentCallbackFlow(FlowContext appCtx) {
      super(appCtx);
      this.modDriver = new ModuleStateDriver(appCtx);
   }

   public void prepare() throws DeploymentException {
      this.prepare(this.appCtx.getApplicationModules());
      this.appCtx.addUpdateListener(new PartialRedeployUpdateListener(this.appCtx));
   }

   private void prepare(Module[] modules) throws DeploymentException {
      if (parallelPrepareEnabled) {
         List moduleGroups = this.partitionModules(modules);
         boolean isFirstListEmpty = ((Module[])moduleGroups.get(0)).length == 0;
         boolean isPartitionConcurrent = isFirstListEmpty;
         int startingIndex = isFirstListEmpty ? 1 : 0;

         for(int index = startingIndex; index < moduleGroups.size(); ++index) {
            Module[] moduleGroup = (Module[])moduleGroups.get(index);
            if (isPartitionConcurrent) {
               this.modDriver.parallelPrepare(moduleGroup);
            } else {
               this.modDriver.prepare(moduleGroup);
            }

            isPartitionConcurrent = !isPartitionConcurrent;
         }
      } else {
         this.modDriver.prepare(modules);
      }

   }

   public void activate() throws DeploymentException {
      Module[] modules = this.appCtx.getApplicationModules();
      if (this.isParallelActivateEnabled()) {
         List moduleGroups = this.partitionModules(modules);
         boolean isFirstListEmpty = ((Module[])moduleGroups.get(0)).length == 0;
         boolean isPartitionConcurrent = isFirstListEmpty;
         int startingIndex = isFirstListEmpty ? 1 : 0;

         for(int index = startingIndex; index < moduleGroups.size(); ++index) {
            Module[] moduleGroup = (Module[])moduleGroups.get(index);
            if (isPartitionConcurrent) {
               this.modDriver.parallelActivate(moduleGroup);
            } else {
               this.modDriver.activate(moduleGroup);
            }

            isPartitionConcurrent = !isPartitionConcurrent;
         }
      } else {
         this.modDriver.activate(modules);
      }

   }

   public void deactivate() throws DeploymentException {
      this.modDriver.deactivate(this.appCtx.getApplicationModules());
   }

   public void unprepare() throws DeploymentException {
      try {
         this.modDriver.unprepare(this.appCtx.getApplicationModules());
      } finally {
         invokeStaticMethodEasy(ResourceBundle.class, "clearCache", new Class[]{ClassLoader.class}, new Object[]{this.appCtx.getAppClassLoader()});
      }

   }

   public static void invokeStaticMethodEasy(Class clz, String methodName, Class[] paramTypes, Object[] paramValues) {
      try {
         Method clearCache = clz.getDeclaredMethod(methodName, paramTypes);
         clearCache.invoke((Object)null, paramValues);
      } catch (SecurityException var5) {
      } catch (NoSuchMethodException var6) {
      } catch (IllegalArgumentException var7) {
      } catch (IllegalAccessException var8) {
      } catch (InvocationTargetException var9) {
      }

   }

   public void remove() throws DeploymentException {
      this.modDriver.remove(this.appCtx.getApplicationModules());
   }

   public void start(String[] uris) throws DeploymentException {
      Module[] startingModules = this.appCtx.getStartingModules();

      try {
         this.prepare(startingModules);

         try {
            this.modDriver.activate(startingModules);
         } catch (Throwable var7) {
            try {
               this.modDriver.unprepare(startingModules);
            } catch (DeploymentException var6) {
               J2EELogger.logIgnoringUndeploymentError(var6);
            }

            throw var7;
         }
      } catch (Throwable var8) {
         try {
            this.destroy(startingModules);
         } catch (Throwable var5) {
            J2EELogger.logIgnoringUndeploymentError(var5);
         }

         this.throwAppException(var8);
      }

   }

   private void destroy(Module[] modules) throws DeploymentException {
      ErrorCollectionException e = null;
      int n = modules.length;

      for(int i = n - 1; i >= 0; --i) {
         try {
            modules[i].destroy(this.appCtx);
         } catch (Throwable var6) {
            if (e == null) {
               e = new ErrorCollectionException();
            }

            e.addError(var6);
         }
      }

      if (e != null) {
         this.throwAppException(e);
      }

   }

   public void stop(String[] uris) throws DeploymentException {
      Module[] stoppingModules = this.appCtx.getStoppingModules();

      try {
         this.modDriver.deactivate(stoppingModules);
      } catch (DeploymentException var5) {
         if (this.isDebugEnabled()) {
            this.debug("Ignoring deactivate error ", var5);
         }
      }

      try {
         this.modDriver.unprepare(stoppingModules);
      } catch (DeploymentException var4) {
         if (this.isDebugEnabled()) {
            this.debug("Ignoring unprepare error ", var4);
         }
      }

   }

   private void addPendingUpdates(List pendingList, String uri) throws DeploymentException {
      Iterator it = this.appCtx.getUpdateListeners().iterator();
      boolean found = false;

      while(it.hasNext()) {
         UpdateListener ul = (UpdateListener)it.next();
         if (ul.acceptURI(uri)) {
            found = true;
            pendingList.add(new PendingUpdate(uri, ul));
         }
      }

      if (!found) {
         if (this.isDebugEnabled()) {
            this.debug("No UpdateListener found or none of the found UpdateListeners accepts URI");
         }

         throw new DeploymentException("\n The application " + this.appCtx.getApplicationId() + " cannot have the resource " + uri + " updated dynamically. Either:\n1.) The resource does not exist. \n or \n2) The resource cannot be changed dynamically. \nPlease ensure the resource uri is correct, and redeploy the entire application for this change to take effect.");
      }
   }

   private PendingUpdate[] makePendingUpdates(String[] uris) throws DeploymentException {
      List pendingList = new ArrayList();

      for(int i = 0; i < uris.length; ++i) {
         this.addPendingUpdates(pendingList, uris[i]);
      }

      return (PendingUpdate[])((PendingUpdate[])pendingList.toArray(new PendingUpdate[pendingList.size()]));
   }

   public void prepareUpdate(String[] uris) throws DeploymentException {
      PendingUpdate[] pendingUpdates = this.makePendingUpdates(uris);

      try {
         this.puDriver.nextState(this.prepareUpdateChange, pendingUpdates);
      } catch (StateChangeException var4) {
         this.throwAppException(var4.getCause());
      }

   }

   public void rollbackUpdate(String[] uris) throws DeploymentException {
      PendingUpdate[] pendingUpdates = this.makePendingUpdates(uris);

      try {
         this.puDriver.previousState(this.prepareUpdateChange, pendingUpdates);
      } catch (StateChangeException var4) {
         this.throwAppException(var4.getCause());
      }

   }

   public void activateUpdate(String[] uris) throws DeploymentException {
      PendingUpdate[] pendingUpdates = this.makePendingUpdates(uris);

      try {
         for(int i = 0; i < pendingUpdates.length; ++i) {
            PendingUpdate p = pendingUpdates[i];

            try {
               p.listener.activateUpdate(p.uri);
            } catch (ModuleException var9) {
               throw new DeploymentException(var9);
            }
         }
      } finally {
         pendingUpdates = null;
      }

   }

   public void adminToProduction() throws DeploymentException {
      this.modDriver.adminToProduction(this.appCtx.getApplicationModules());
   }

   public void forceProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      this.modDriver.forceProductionToAdmin(this.appCtx.getApplicationModules());
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      this.modDriver.gracefulProductionToAdmin(barrier, this.appCtx.getApplicationModules());
   }

   private static class PendingUpdate {
      private final String uri;
      private final UpdateListener listener;

      PendingUpdate(String uri, UpdateListener listener) {
         this.uri = uri;
         this.listener = listener;
      }
   }
}
