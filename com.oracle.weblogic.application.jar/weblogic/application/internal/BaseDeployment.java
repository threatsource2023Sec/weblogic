package weblogic.application.internal;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import weblogic.application.AdminModeCallback;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.application.Module;
import weblogic.application.ModuleLocationInfo;
import weblogic.application.ModuleManager;
import weblogic.application.ModuleWrapper;
import weblogic.application.NonFatalDeploymentException;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.io.AA;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.CachedApplicationArchiveFactory;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.PathUtils;
import weblogic.application.utils.StateChange;
import weblogic.application.utils.StateChangeException;
import weblogic.application.utils.StateMachineDriver;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.J2EELogger;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StringUtils;

abstract class BaseDeployment implements Deployment {
   private final StateMachineDriver driver = new StateMachineDriver();
   String asString = null;
   protected final ApplicationContextImpl appCtx;
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final StateChange prepareStateChange = new StateChange() {
      public String toString() {
         return "prepare";
      }

      public void next(Object obj) throws Exception {
         ((Flow)obj).prepare();
      }

      public void previous(Object obj) throws Exception {
         ((Flow)obj).unprepare();
      }

      public void logRollbackError(StateChangeException e) {
         J2EELogger.logIgnoringUndeploymentError(e.getCause());
      }
   };
   private static final StateChange activateStateChange = new StateChange() {
      public String toString() {
         return "activate";
      }

      public void next(Object obj) throws Exception {
         ((Flow)obj).activate();
      }

      public void previous(Object obj) throws Exception {
         ((Flow)obj).deactivate();
      }

      public void logRollbackError(StateChangeException e) {
         J2EELogger.logIgnoringUndeploymentError(e.getCause());
      }
   };
   private static final StateChange removeStateChange = new StateChange() {
      public String toString() {
         return "remove";
      }

      public void next(Object obj) throws Exception {
         throw new AssertionError("someone is transitioning up to remove!");
      }

      public void previous(Object obj) throws Exception {
         ((Flow)obj).remove();
      }

      public void logRollbackError(StateChangeException e) {
         J2EELogger.logIgnoringUndeploymentError(e.getCause());
      }
   };
   private static final StateChange adminStateChange = new StateChange() {
      public String toString() {
         return "admin";
      }

      public void next(Object obj) throws Exception {
         ((Flow)obj).adminToProduction();
      }

      public void previous(Object obj) throws Exception {
         ((Flow)obj).forceProductionToAdmin(new AdminModeCompletionBarrier(Deployment.noopAdminModeCallback));
      }

      public void logRollbackError(StateChangeException e) {
         J2EELogger.logIgnoringAdminModeErrro(e.getCause());
      }
   };

   public BaseDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      this.initString(mbean);
      if (!AA.useApplicationArchive(f)) {
         this.appCtx = new ApplicationContextImpl(mbean, f);
      } else {
         try {
            this.appCtx = new ApplicationContextImpl(mbean, CachedApplicationArchiveFactory.instance.create(f, PathUtils.getTempDirForAppArchive(mbean.getApplicationIdentifier())));
         } catch (IOException var4) {
            throw new DeploymentException(var4);
         }
      }

   }

   public BaseDeployment(AppDeploymentMBean mbean, ApplicationArchive f) throws DeploymentException {
      this.initString(mbean);
      this.appCtx = this.createApplicationContextImpl(mbean, f);
   }

   public BaseDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      this.initString(mbean);
      if (!AA.useApplicationArchive(f)) {
         this.appCtx = new ApplicationContextImpl(mbean, f);
      } else {
         try {
            this.appCtx = new ApplicationContextImpl(mbean, CachedApplicationArchiveFactory.instance.create(f, PathUtils.getTempDirForAppArchive(mbean.getName())));
         } catch (IOException var4) {
            throw new DeploymentException(var4);
         }
      }

   }

   public BaseDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      this.initString(mbean);
      this.appCtx = new ApplicationContextImpl(mbean, f);
   }

   private void initString(BasicDeploymentMBean mbean) {
      this.asString = "[" + this.getClass().getName() + "] name: " + mbean.getName() + " path: " + mbean.getSourcePath();
   }

   public String toString() {
      return this.asString;
   }

   protected void throwAppException(Throwable th) throws DeploymentException {
      if (th instanceof DeploymentException) {
         throw (DeploymentException)th;
      } else {
         if (th instanceof ErrorCollectionException) {
            ErrorCollectionException ece = (ErrorCollectionException)th;
            if (ece.size() == 1) {
               this.throwAppException((Throwable)ece.getErrors().next());
            }
         }

         throw new DeploymentException(th);
      }
   }

   protected abstract Flow[] getFlow();

   private ClassLoader pushLoader() {
      Thread th = Thread.currentThread();
      ClassLoader cl = th.getContextClassLoader();
      th.setContextClassLoader(this.appCtx.getAppClassLoader());
      return cl;
   }

   private void popLoader(ClassLoader cl) {
      Thread.currentThread().setContextClassLoader(cl);
   }

   private void propagateDeploymentContext(DeploymentContext deploymentContext) {
      if (deploymentContext != null) {
         this.appCtx.setProposedDomain(deploymentContext.getProposedDomain());
         this.appCtx.setDeploymentInitiator(deploymentContext.getInitiator());
         this.appCtx.setRequiresRestart(deploymentContext.requiresRestart());
         this.appCtx.setDeploymentOperation(deploymentContext.getDeploymentOperation());
         this.appCtx.setStaticDeploymentOperation(deploymentContext.isStaticDeploymentOperation());
         this.appCtx.setAdminModeSpecified(deploymentContext.isAdminModeSpecified());
         this.appCtx.setSpecifiedTargetsOnly(deploymentContext.isSpecifiedTargetsOnly());
         ApplicationVersionUtils.setAdminModeAppCtxParam(this.appCtx, deploymentContext.isAdminModeTransition());
         ApplicationVersionUtils.setIgnoreSessionsAppCtxParam(this.appCtx, deploymentContext.isIgnoreSessionsEnabled());
         ApplicationVersionUtils.setRMIGracePeriodAppCtxParam(this.appCtx, deploymentContext.getRMIGracePeriodSecs());
      }
   }

   private void resetDeploymentContext() {
      this.appCtx.setProposedDomain((DomainMBean)null);
      this.appCtx.setDeploymentInitiator((AuthenticatedSubject)null);
      this.appCtx.setDeploymentOperation(-1);
      this.appCtx.setAdminModeSpecified(false);
      ApplicationVersionUtils.unsetAdminModeAppCtxParam(this.appCtx);
      ApplicationVersionUtils.unsetIgnoreSessionsAppCtxParam(this.appCtx);
      ApplicationVersionUtils.unsetRMIGracePeriodAppCtxParam(this.appCtx);
   }

   private String[] getUpdatedResourceURIs(DeploymentContext depCtx) {
      String[] uris = new String[0];
      if (depCtx != null && depCtx.getUpdatedResourceURIs() != null) {
         uris = EarUtils.toModuleIds(this.appCtx, depCtx.getUpdatedResourceURIs());
      }

      return uris;
   }

   public void prepare(DeploymentContext deploymentContext) throws DeploymentException {
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("prepare " + this.appCtx.getApplicationId());
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var4 = null;

         try {
            this.driver.nextState(prepareStateChange, this.getFlow());
         } catch (Throwable var22) {
            var4 = var22;
            throw var22;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var21) {
                     var4.addSuppressed(var21);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var24) {
         this.throwAppException(var24.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void activate(DeploymentContext deploymentContext) throws DeploymentException {
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("activate " + this.appCtx.getApplicationId());
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var4 = null;

         try {
            this.driver.nextState(activateStateChange, this.getFlow());
         } catch (Throwable var22) {
            var4 = var22;
            throw var22;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var21) {
                     var4.addSuppressed(var21);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var24) {
         this.throwAppException(var24.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void deactivate(DeploymentContext deploymentContext) throws DeploymentException {
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("deactivate " + this.appCtx.getApplicationId());
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var4 = null;

         try {
            this.driver.previousState(activateStateChange, this.getFlow());
         } catch (Throwable var22) {
            var4 = var22;
            throw var22;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var21) {
                     var4.addSuppressed(var21);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var24) {
         this.throwAppException(var24.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void unprepare(DeploymentContext deploymentContext) throws DeploymentException {
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("unprepare " + this.appCtx.getApplicationId());
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var4 = null;

         try {
            this.driver.previousState(prepareStateChange, this.getFlow());
         } catch (Throwable var22) {
            var4 = var22;
            throw var22;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var21) {
                     var4.addSuppressed(var21);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var24) {
         this.throwAppException(var24.getCause());
      } finally {
         this.appCtx.clear();
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void remove(DeploymentContext deploymentContext) throws DeploymentException {
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("remove " + this.appCtx.getApplicationId());
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var4 = null;

         try {
            this.driver.previousState(removeStateChange, this.getFlow());
         } catch (Throwable var28) {
            var4 = var28;
            throw var28;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var27) {
                     var4.addSuppressed(var27);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var30) {
         this.throwAppException(var30.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);

         try {
            this.appCtx.reset();
         } catch (IOException var26) {
            throw new DeploymentException(var26);
         }
      }

   }

   private void validateRedeploy(DeploymentContext deplContext) throws DeploymentException {
      String[] uris = this.getUpdatedResourceURIs(deplContext);
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("validateRedeploy " + this.appCtx.getApplicationId() + " uris --> " + StringUtils.join(uris, ","));
      }

      if (uris.length != 0) {
         ClassLoader oldCL = this.pushLoader();

         try {
            ManagedInvocationContext mic = this.setInvocationContext();
            Throwable var5 = null;

            try {
               this.driver.nextState(new ValidateRedeployStateChange(deplContext), this.getFlow());
            } catch (Throwable var23) {
               var5 = var23;
               throw var23;
            } finally {
               if (mic != null) {
                  if (var5 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var22) {
                        var5.addSuppressed(var22);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } catch (StateChangeException var25) {
            this.throwAppException(var25.getCause());
         } finally {
            this.popLoader(oldCL);
            this.appCtx.setAdditionalModuleUris(Collections.EMPTY_MAP);
            this.appCtx.setProposedPartialRedeployDDs((AppDDHolder)null);
         }

      }
   }

   public void start(DeploymentContext deploymentContext) throws DeploymentException {
      String[] uris = this.getUpdatedResourceURIs(deploymentContext);
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("start " + this.appCtx.getApplicationId() + " uris --> " + StringUtils.join(uris, ","));
      }

      if (!this.appCtx.isRedeployOperation()) {
         ModuleManager mm = this.appCtx.getModuleManager();
         if (mm.validateModuleIds(uris)) {
            throw new NonFatalDeploymentException(J2EELogger.logModulesAlreadyRunningErrorLoggable(StringUtils.join(mm.getValidModuleIds(uris), ",")).getMessage());
         }
      }

      ClassLoader oldCL = this.pushLoader();
      if (this.appCtx.getPartialRedeployURIs() != null) {
         uris = this.appCtx.getPartialRedeployURIs();
      }

      this.appCtx.setPartialRedeployURIs(uris);

      try {
         this.propagateDeploymentContext(deploymentContext);
         String[] uris1 = uris;
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var6 = null;

         try {
            ErrorCollectionException e = null;
            Flow[] flow = this.getFlow();

            for(int i = 0; i < flow.length; ++i) {
               flow[i].start(uris1);
            }

            if (e != null) {
               this.throwAppException((Throwable)e);
            }
         } catch (Throwable var24) {
            var6 = var24;
            throw var24;
         } finally {
            if (mic != null) {
               if (var6 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var23) {
                     var6.addSuppressed(var23);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } finally {
         if (this.appCtx.getStartingModules() != null && this.appCtx.getStartingModules().length > 0) {
            this.appCtx.setStartingModules(new Module[0]);
         }

         this.appCtx.setPartialRedeployURIs((String[])null);
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void stop(DeploymentContext deploymentContext) throws DeploymentException {
      this.setModuleURItoModuleIdMap();
      String[] uris = this.getUpdatedResourceURIs(deploymentContext);
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("stop " + this.appCtx.getApplicationId() + " uris --> " + StringUtils.join(uris, ","));
      }

      this.propagateDeploymentContext(deploymentContext);
      this.validateRedeploy(deploymentContext);
      Flow[] flow = this.getFlow();
      ClassLoader oldCL = this.pushLoader();

      try {
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var6 = null;

         try {
            ErrorCollectionException e = null;
            if (this.appCtx.getPartialRedeployURIs() != null) {
               uris = this.appCtx.getPartialRedeployURIs();
            }

            for(int i = flow.length - 1; i >= 0; --i) {
               try {
                  flow[i].stop(uris);
               } catch (Throwable var26) {
                  if (e == null) {
                     e = new ErrorCollectionException();
                  }

                  e.addError(var26);
               }
            }

            if (e != null) {
               this.throwAppException(e);
            }
         } catch (Throwable var27) {
            var6 = var27;
            throw var27;
         } finally {
            if (mic != null) {
               if (var6 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var25) {
                     var6.addSuppressed(var25);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } finally {
         if (this.appCtx.isStopOperation()) {
            if (this.appCtx.getStoppingModules() != null && this.appCtx.getStoppingModules().length > 0) {
               this.appCtx.setStoppingModules(new Module[0]);
            }

            this.appCtx.setPartialRedeployURIs((String[])null);
         }

         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void prepareUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      String[] uris = this.getUpdatedResourceURIs(deploymentContext);
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("prepareUpdate " + this.appCtx.getApplicationId() + " uris --> " + StringUtils.join(uris, ","));
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var5 = null;

         try {
            this.driver.nextState(new PrepareUpdateStateChange(uris), this.getFlow());
         } catch (Throwable var23) {
            var5 = var23;
            throw var23;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var22) {
                     var5.addSuppressed(var22);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var25) {
         this.throwAppException(var25.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void activateUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      String[] uris = this.getUpdatedResourceURIs(deploymentContext);
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("activateUpdate " + this.appCtx.getApplicationId() + " uris --> " + StringUtils.join(uris, ","));
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var5 = null;

         try {
            this.driver.nextState(new ActivateUpdateStateChange(uris), this.getFlow());
         } catch (Throwable var23) {
            var5 = var23;
            throw var23;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var22) {
                     var5.addSuppressed(var22);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var25) {
         this.throwAppException(var25.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

      this.updateAppDeploymentBean(deploymentContext);
   }

   private void updateAppDeploymentBean(DeploymentContext deploymentContext) {
      if (deploymentContext != null && deploymentContext.getProposedDomain() != null) {
         AppDeploymentMBean mbean = deploymentContext.getProposedDomain().lookupAppDeployment(this.appCtx.getApplicationId());
         this.appCtx.setUpdatedAppDeploymentMBean(mbean);
      }

   }

   public void rollbackUpdate(DeploymentContext deploymentContext) {
      String[] uris = this.getUpdatedResourceURIs(deploymentContext);
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("rollbackUpdate " + this.appCtx.getApplicationId() + " uris --> " + StringUtils.join(uris, ","));
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var5 = null;

         try {
            this.driver.previousState(new PrepareUpdateStateChange(uris), this.getFlow());
         } catch (Throwable var23) {
            var5 = var23;
            throw var23;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var22) {
                     var5.addSuppressed(var22);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var25) {
         J2EELogger.logIgnoringRollbackUpdateError(this.appCtx.getApplicationId(), var25.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void adminToProduction(DeploymentContext deploymentContext) throws DeploymentException {
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("adminToProduction " + this.appCtx.getApplicationId());
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var4 = null;

         try {
            this.driver.nextState(adminStateChange, this.getFlow());
         } catch (Throwable var22) {
            var4 = var22;
            throw var22;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var21) {
                     var4.addSuppressed(var21);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var24) {
         this.throwAppException(var24.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void forceProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("forceProductionToAdmin " + this.appCtx.getApplicationId());
      }

      AdminModeCallback callback = null;
      if (deploymentContext != null) {
         callback = deploymentContext.getAdminModeCallback();
      }

      ClassLoader oldCL = this.pushLoader();
      AdminModeCallback callback1 = callback;

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var6 = null;

         try {
            this.driver.previousState(new ForceProdToAdminStateChange(callback1), this.getFlow());
         } catch (Throwable var24) {
            var6 = var24;
            throw var24;
         } finally {
            if (mic != null) {
               if (var6 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var23) {
                     var6.addSuppressed(var23);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var26) {
         this.throwAppException(var26.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void gracefulProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      if (EarUtils.isDebugOn()) {
         EarUtils.debug("gracefulProductionToAdmin " + this.appCtx.getApplicationId());
      }

      AdminModeCallback callback = null;
      if (deploymentContext != null) {
         callback = deploymentContext.getAdminModeCallback();
      }

      ClassLoader oldCL = this.pushLoader();

      try {
         this.propagateDeploymentContext(deploymentContext);
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var5 = null;

         try {
            this.driver.previousState(new GracefulProdToAdminStateChange(callback), this.getFlow());
         } catch (Throwable var23) {
            var5 = var23;
            throw var23;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var22) {
                     var5.addSuppressed(var22);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (StateChangeException var25) {
         this.throwAppException(var25.getCause());
      } finally {
         this.resetDeploymentContext();
         this.popLoader(oldCL);
      }

   }

   public void assertUndeployable() throws DeploymentException {
      Flow[] var1 = this.getFlow();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Flow flow = var1[var3];
         flow.assertUndeployable();
      }

   }

   protected ApplicationContextImpl createApplicationContextImpl(AppDeploymentMBean mbean, ApplicationArchive f) {
      return new ApplicationContextImpl(mbean, f);
   }

   private void setModuleURItoModuleIdMap() {
      HashMap moduleURItoIdMap = new HashMap();
      Module[] var2 = this.appCtx.getApplicationModules();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module m = var2[var4];
         if (m instanceof ModuleWrapper) {
            m = ((ModuleWrapper)m).unwrap();
         }

         String mId = m.getId();
         if (m instanceof ModuleLocationInfo) {
            moduleURItoIdMap.put(((ModuleLocationInfo)m).getModuleURI(), mId);
         }

         moduleURItoIdMap.put(mId, mId);
      }

      this.appCtx.setModuleURItoIdMap(moduleURItoIdMap);
   }

   public ApplicationContext getApplicationContext() {
      return this.appCtx;
   }

   private ManagedInvocationContext setInvocationContext() {
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      ComponentInvocationContext cic = this.appCtx.getInvocationContext();
      return cicm.setCurrentComponentInvocationContext(cic);
   }

   private static final class ValidateRedeployStateChange implements StateChange {
      private final DeploymentContext deplCtx;

      ValidateRedeployStateChange(DeploymentContext deplCtx) {
         this.deplCtx = deplCtx;
      }

      public String toString() {
         return "validateRedeploy";
      }

      public void next(Object obj) throws DeploymentException {
         ((Flow)obj).validateRedeploy(this.deplCtx);
      }

      public void previous(Object obj) {
      }

      public void logRollbackError(StateChangeException e) {
      }
   }

   private static final class ForceProdToAdminStateChange extends AdminCallbackStateChange implements StateChange {
      ForceProdToAdminStateChange(AdminModeCallback callback) {
         super(callback);
      }

      public String toString() {
         return "forceProdToAdmin";
      }

      public void previous(Object obj) throws Exception {
         ((Flow)obj).forceProductionToAdmin(this.barrier);
      }
   }

   private static final class GracefulProdToAdminStateChange extends AdminCallbackStateChange implements StateChange {
      public String toString() {
         return "gracefulProdToAdmin";
      }

      GracefulProdToAdminStateChange(AdminModeCallback callback) {
         super(callback);
      }

      public void previous(Object obj) throws Exception {
         ((Flow)obj).gracefulProductionToAdmin(this.barrier);
      }
   }

   private abstract static class AdminCallbackStateChange implements StateChange {
      protected final AdminModeCompletionBarrier barrier;

      AdminCallbackStateChange(AdminModeCallback callback) {
         this.barrier = new AdminModeCompletionBarrier(callback);
      }

      public String toString() {
         return "adminCallback";
      }

      public void next(Object obj) throws Exception {
         throw new AssertionError("should not be called");
      }

      public abstract void previous(Object var1) throws Exception;

      public void logRollbackError(StateChangeException e) {
         J2EELogger.logIgnoringAdminModeErrro(e.getCause());
      }
   }

   private static final class ActivateUpdateStateChange implements StateChange {
      private String[] updateURIs;

      ActivateUpdateStateChange(String[] uris) {
         this.updateURIs = uris;
      }

      public String toString() {
         return "activateUpdate";
      }

      public void next(Object obj) throws Exception {
         ((Flow)obj).activateUpdate(this.updateURIs);
      }

      public void previous(Object obj) throws Exception {
      }

      public void logRollbackError(StateChangeException e) {
      }
   }

   private static final class PrepareUpdateStateChange implements StateChange {
      private String[] updateURIs;

      PrepareUpdateStateChange(String[] uris) {
         this.updateURIs = uris;
      }

      public String toString() {
         return "prepareUpdate";
      }

      public void next(Object obj) throws Exception {
         ((Flow)obj).prepareUpdate(this.updateURIs);
      }

      public void previous(Object obj) throws Exception {
         ((Flow)obj).rollbackUpdate(this.updateURIs);
      }

      public void logRollbackError(StateChangeException e) {
         J2EELogger.logIgnoringUndeploymentError(e.getCause());
      }
   }
}
