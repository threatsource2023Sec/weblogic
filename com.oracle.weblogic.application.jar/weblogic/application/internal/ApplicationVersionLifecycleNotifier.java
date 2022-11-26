package weblogic.application.internal;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationException;
import weblogic.application.ApplicationVersionLifecycleEvent;
import weblogic.application.ApplicationVersionLifecycleListener;
import weblogic.application.Deployment;
import weblogic.application.DeploymentManager;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;

public class ApplicationVersionLifecycleNotifier {
   private String ownAppId;
   private List appListeners;
   private static final boolean VETOABLE = true;
   private static final boolean REVERSE = true;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   public static void sendLifecycleEventNotification(String appId, ApplicationLifecycleAction action) throws DeploymentException {
      DeploymentManager dm = DeploymentManager.getDeploymentManager();
      Deployment d = (Deployment)dm.findDeployment(appId);
      ApplicationContext ac = d == null ? null : d.getApplicationContext();
      if (ac == null || ac instanceof FlowContext) {
         FlowContext appCtx = (FlowContext)ac;
         if (action == ApplicationVersionLifecycleNotifier.ApplicationLifecycleAction.POST_DEPLOY && appCtx != null && appCtx.getDeploymentOperation() == 7) {
            return;
         }

         boolean isAdmin = appCtx == null ? false : appCtx.isAdminModeSpecified();
         String unversionedAppName = ApplicationVersionUtils.getApplicationName(appId);
         Iterator deployments = ((DeploymentManagerImpl)dm).getApplicationDeployments(unversionedAppName);

         while(deployments.hasNext()) {
            Deployment deployment = (Deployment)deployments.next();
            ac = deployment.getApplicationContext();
            if (ac instanceof FlowContext) {
               FlowContext ownAppCtx = (FlowContext)ac;
               ApplicationVersionLifecycleNotifier notifier = ownAppCtx.getApplicationVersionNotifier();
               boolean shouldNotify = true;
               if (action == ApplicationVersionLifecycleNotifier.ApplicationLifecycleAction.POST_DEPLOY && appCtx != ownAppCtx && !isAdmin) {
                  shouldNotify = ownAppCtx.isActive();
                  ownAppCtx.setIsActive(false);
               }

               if (notifier != null && shouldNotify) {
                  notifier.invokeAppLifecycleListeners(ownAppCtx, appCtx, appId, action);
               }
            }
         }
      }

   }

   public ApplicationVersionLifecycleNotifier(String applicationId, List listeners) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("AppVersionLifecycleAdapter create for " + applicationId + ", listeners=" + listeners);
      }

      this.ownAppId = applicationId;
      this.appListeners = listeners;
   }

   private void invokeAppLifecycleListeners(final FlowContext ownAppCtx, final FlowContext appCtx, final String appId, final ApplicationLifecycleAction action) throws DeploymentException {
      try {
         ownAppCtx.getSecurityProvider().invokePrivilegedActionAsAnonymous(new PrivilegedExceptionAction() {
            public Void run() throws ApplicationException {
               Iterator iter = ApplicationVersionLifecycleNotifier.this.getIterator(action.reversed);

               while(iter.hasNext()) {
                  ApplicationVersionLifecycleListener listener = (ApplicationVersionLifecycleListener)iter.next();
                  ApplicationVersionLifecycleEvent event = ApplicationVersionLifecycleNotifier.this.getAppLifecycleEvent(ownAppCtx, appCtx, appId);
                  if (ApplicationVersionLifecycleNotifier.debugLogger.isDebugEnabled()) {
                     ApplicationVersionLifecycleNotifier.debugLogger.debug("AppVersionLifecycleAdapter invoke " + action + " for " + listener + ", " + event);
                  }

                  if (listener != null) {
                     action.invoke(listener, event);
                  }
               }

               return null;
            }
         });
      } catch (PrivilegedActionException var8) {
         Throwable cause = var8.getCause();
         if (cause instanceof ApplicationException && action.vetoable) {
            throw new DeploymentException(cause);
         }

         if (cause instanceof Error) {
            throw (Error)cause;
         }

         String appDisplayName = ApplicationVersionUtils.getDisplayName((BasicDeploymentMBean)ownAppCtx.getAppDeploymentMBean());
         if (action.vetoable) {
            J2EELogger.logSendVetoableDeployEventError(appDisplayName, cause);
         } else {
            J2EELogger.logSendDeploymentEventError(appDisplayName, action.getName(), cause);
         }
      }

   }

   private Iterator getIterator(boolean reverse) {
      return reverse ? new Iterator() {
         private ListIterator listIter;

         {
            this.listIter = ApplicationVersionLifecycleNotifier.this.appListeners.listIterator(ApplicationVersionLifecycleNotifier.this.appListeners.size());
         }

         public boolean hasNext() {
            return this.listIter.hasPrevious();
         }

         public ApplicationVersionLifecycleListener next() {
            return (ApplicationVersionLifecycleListener)this.listIter.previous();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      } : this.appListeners.iterator();
   }

   private ApplicationVersionLifecycleEvent getAppLifecycleEvent(FlowContext ownAppCtx, FlowContext appCtx, String appId) {
      return new ApplicationVersionLifecycleEvent(this.ownAppId, ownAppCtx.isActive(), appId, appCtx == null ? false : appCtx.isActive()) {
         public boolean isAdminMode() {
            return false;
         }
      };
   }

   public interface VersionActiveAssessable {
      boolean isActiveVersion(String var1);
   }

   public abstract static class ApplicationLifecycleAction {
      public static final ApplicationLifecycleAction PRE_DEPLOY = new ApplicationLifecycleAction("preDeploy", true, false) {
         void invoke(ApplicationVersionLifecycleListener listener, ApplicationVersionLifecycleEvent event) throws ApplicationException {
            listener.preDeploy(event);
         }
      };
      public static final ApplicationLifecycleAction POST_DEPLOY = new ApplicationLifecycleAction("postDeploy", false, false) {
         void invoke(ApplicationVersionLifecycleListener listener, ApplicationVersionLifecycleEvent event) throws ApplicationException {
            listener.postDeploy(event);
         }
      };
      public static final ApplicationLifecycleAction PRE_UNDEPLOY = new ApplicationLifecycleAction("preUndeploy", true, true) {
         void invoke(ApplicationVersionLifecycleListener listener, ApplicationVersionLifecycleEvent event) throws ApplicationException {
            listener.preUndeploy(event);
         }
      };
      public static final ApplicationLifecycleAction POST_DELETE = new ApplicationLifecycleAction("postDelete", false, true) {
         void invoke(ApplicationVersionLifecycleListener listener, ApplicationVersionLifecycleEvent event) throws ApplicationException {
            listener.postDelete(event);
         }
      };
      private final String name;
      private final boolean vetoable;
      private final boolean reversed;

      ApplicationLifecycleAction(String name, boolean vetoable, boolean reversed) {
         this.name = name;
         this.vetoable = vetoable;
         this.reversed = reversed;
      }

      public String toString() {
         return this.name + ", vetoable=" + this.vetoable + ", reversed=" + this.reversed;
      }

      String getName() {
         return this.name;
      }

      abstract void invoke(ApplicationVersionLifecycleListener var1, ApplicationVersionLifecycleEvent var2) throws ApplicationException;
   }
}
