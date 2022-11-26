package weblogic.deploy.compatibility;

import java.security.AccessController;
import weblogic.application.ModuleListener;
import weblogic.deploy.internal.targetserver.state.AppTransition;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.internal.targetserver.state.ModuleTransition;
import weblogic.deploy.internal.targetserver.state.TargetModuleState;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class NotificationBroadcaster {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void sendNotificationsFromManagedServer(DeploymentState state, DeploymentTaskRuntimeMBean task, DebugLogger dl) {
      if (null != state) {
         ApplicationMBean appmbean = task.getDeploymentObject();
         if (appmbean == null) {
            if (dl.isDebugEnabled()) {
               dl.debug("AppMBean from Task is NULL. No notifs sent.");
            }

         } else {
            Object[] transitions = state.getTransitions();

            for(int i = 0; transitions != null && i < transitions.length; ++i) {
               sendJMXNotification(task, appmbean, transitions[i]);
            }

         }
      }
   }

   private static void sendJMXNotification(DeploymentTaskRuntimeMBean task, ApplicationMBean appmbean, Object transition) {
      if (transition instanceof ModuleTransition && task.getNotificationLevel() >= 2) {
         ModuleTransition xition = (ModuleTransition)transition;
         TargetModuleState tm = xition.getModule();
         appmbean.sendModuleNotification(tm.getServerName(), tm.getModuleId(), xition.getName(), xition.getCurrentState(), xition.getNewState(), task.getId(), xition.getGenerationTime());
      }

      if (transition instanceof AppTransition && task.getNotificationLevel() >= 1) {
         AppTransition xition = (AppTransition)transition;
         appmbean.sendAppLevelNotification(xition.getServerName(), xition.getXition(), (String)null);
      }

   }

   public static void sendAppNotification(String phase, String applicationName, String taskID) {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      ApplicationMBean appmbean = runtimeAccess.getDomain().lookupApplication(applicationName);
      if (appmbean != null) {
         appmbean.sendAppLevelNotification(ManagementService.getRuntimeAccess(kernelId).getServerName(), phase, taskID);
      }

   }

   public static boolean isRelevantToWLS81(String curState, String newstate) {
      boolean newToPrepared = curState.equals(ModuleListener.STATE_NEW.toString()) && newstate.equals(ModuleListener.STATE_PREPARED.toString());
      boolean preparedToNew = curState.equals(ModuleListener.STATE_PREPARED.toString()) && newstate.equals(ModuleListener.STATE_NEW.toString());
      return !newToPrepared && !preparedToNew;
   }
}
