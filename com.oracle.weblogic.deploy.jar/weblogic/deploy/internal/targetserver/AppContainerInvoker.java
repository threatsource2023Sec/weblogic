package weblogic.deploy.internal.targetserver;

import weblogic.application.ApplicationContext;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.application.ModuleListener;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.common.Debug;
import weblogic.deploy.compatibility.NotificationBroadcaster;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.StringUtils;

public class AppContainerInvoker implements Deployment {
   private final Deployment delegate;
   private DeploymentState state;
   private final String id;
   private boolean removed = false;
   private static final AppRuntimeStateManager appRTStateMgr = AppRuntimeStateManager.getManager();

   AppContainerInvoker(Deployment d, BasicDeploymentMBean b, DeploymentState s) {
      this.delegate = d;
      this.state = s;
      this.id = b.getName();
   }

   private void debug(String s) {
      Debug.deploymentDebug(s);
   }

   private boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   public void setStateRef(DeploymentState s) {
      this.state = s;
   }

   public Deployment getDelegate() {
      return this.delegate;
   }

   public void prepare(DeploymentContext deploymentContext) throws DeploymentException {
      this.sendJMXNotification("preparing");
      long stateStartTime = System.nanoTime();

      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling app container 'prepare' for '" + this.id + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.prepare(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_PREPARED.toString());
         }
      } catch (Throwable var5) {
         this.sendJMXNotification("failed");
         throw this.getOrCreateDeploymentException(var5);
      }

      if (Debug.isDeploymentPerformanceDebugEnabled) {
         Debug.logPerformanceData("Application " + this.id + " transitioned to state PREPARED in " + StringUtils.formatCurrentTimeFromNanoToMillis(stateStartTime) + " ms.");
      }

      this.sendJMXNotification("prepared");
   }

   public void activate(DeploymentContext deploymentContext) throws DeploymentException {
      this.sendJMXNotification("activating");
      long stateStartTime = System.nanoTime();

      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling app container 'activate' for '" + this.id + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.activate(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_ADMIN.toString());
         }
      } catch (Throwable var5) {
         this.sendJMXNotification("failed");
         throw this.getOrCreateDeploymentException(var5);
      }

      if (Debug.isDeploymentPerformanceDebugEnabled) {
         Debug.logPerformanceData("Application " + this.id + " transitioned to state ACTIVATE in " + StringUtils.formatCurrentTimeFromNanoToMillis(stateStartTime) + " ms.");
      }

   }

   public void deactivate(DeploymentContext deploymentContext) throws DeploymentException {
      this.sendJMXNotification("deactivating");

      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling app container 'deactivate' for '" + this.id + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.deactivate(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_PREPARED.toString());
         }
      } catch (Throwable var3) {
         this.sendJMXNotification("failed");
         throw this.getOrCreateDeploymentException(var3);
      }

      this.sendJMXNotification("deactivated");
   }

   public void unprepare(DeploymentContext deploymentContext) throws DeploymentException {
      this.sendJMXNotification("unpreparing");

      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling app container 'unprepare' for '" + this.id + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.unprepare(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_NEW.toString());
         }
      } catch (Throwable var3) {
         this.sendJMXNotification("failed");
         throw this.getOrCreateDeploymentException(var3);
      }

      this.sendJMXNotification("unprepared");
   }

   public void remove(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Calling app container 'remove' for '" + this.id + "'";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      try {
         this.removed = true;
         this.delegate.remove(deploymentContext);
      } catch (Throwable var3) {
         throw this.getOrCreateDeploymentException(var3);
      }
   }

   public void prepareUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Calling app container 'prepareUpdate' for '" + this.id + "'";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      try {
         this.delegate.prepareUpdate(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_UPDATE_PENDING.toString());
         }

      } catch (Throwable var3) {
         throw this.getOrCreateDeploymentException(var3);
      }
   }

   public void activateUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Calling app container 'activateUpdate' for '" + this.id + "'";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      try {
         this.delegate.activateUpdate(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_ACTIVE.toString());
         }

      } catch (Throwable var3) {
         throw this.getOrCreateDeploymentException(var3);
      }
   }

   public void rollbackUpdate(DeploymentContext deploymentContext) {
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Calling app container 'rollbackUpdate' for '" + this.id + "'";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      try {
         this.delegate.rollbackUpdate(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_ACTIVE.toString());
         }
      } catch (Throwable var4) {
         if (this.isDebugEnabled() && (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled())) {
            String debugMsg = "App container 'rollbackUpdate' for id '" + this.id + "' failed - " + StackTraceUtils.throwable2StackTrace(var4);
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }
      }

   }

   public void adminToProduction(DeploymentContext deploymentContext) throws DeploymentException {
      try {
         if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Calling app container 'Admin To Production' state change for '" + this.id + "'";
            if (this.isDebugEnabled()) {
               this.debug(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         this.delegate.adminToProduction(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_ACTIVE.toString());
         }

         this.setActiveAppVersionIfNeeded();
      } catch (Throwable var3) {
         this.sendJMXNotification("failed");
         throw this.getOrCreateDeploymentException(var3);
      }

      this.sendJMXNotification("activated");
   }

   public void gracefulProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Calling app container 'Graceful Production To Admin' state change for '" + this.id + "'";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      try {
         this.delegate.gracefulProductionToAdmin(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_ADMIN.toString());
         }

      } catch (Throwable var3) {
         throw this.getOrCreateDeploymentException(var3);
      }
   }

   public void forceProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Calling app container 'Force Production To Admin' state change for '" + this.id + "'";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      try {
         this.delegate.forceProductionToAdmin(deploymentContext);
         if (this.state != null) {
            this.state.setCurrentState(ModuleListener.STATE_ADMIN.toString());
         }

      } catch (Throwable var3) {
         throw this.getOrCreateDeploymentException(var3);
      }
   }

   public void stop(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Calling app container 'stop' for '" + this.id + "'";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      try {
         this.delegate.stop(deploymentContext);
      } catch (Throwable var3) {
         throw this.getOrCreateDeploymentException(var3);
      }
   }

   public void start(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Calling app container 'start' for '" + this.id + "'";
         if (this.isDebugEnabled()) {
            this.debug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      try {
         this.delegate.start(deploymentContext);
      } catch (Throwable var3) {
         throw this.getOrCreateDeploymentException(var3);
      }
   }

   public ApplicationContext getApplicationContext() {
      return this.delegate.getApplicationContext();
   }

   private void sendJMXNotification(String phase) {
      if (this.state != null) {
         NotificationBroadcaster.sendAppNotification(phase, this.id, (String)null);
         this.state.addAppXition(phase);
      }

   }

   private void setActiveAppVersionIfNeeded() {
      if (ApplicationVersionUtils.getVersionId(this.id) != null) {
         try {
            appRTStateMgr.setActiveVersion(this.id);
         } catch (Exception var2) {
         }

      }
   }

   private DeploymentException getOrCreateDeploymentException(Throwable t) {
      return t instanceof DeploymentException ? (DeploymentException)t : new DeploymentException("Unexpected error encountered", t);
   }

   public void assertUndeployable() throws DeploymentException {
      this.delegate.assertUndeployable();
   }

   boolean isRemoved() {
      return this.removed;
   }
}
