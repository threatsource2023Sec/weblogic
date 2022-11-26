package weblogic.deploy.event;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import weblogic.application.ApplicationException;
import weblogic.application.ApplicationVersionLifecycleEvent;
import weblogic.application.ApplicationVersionLifecycleListener;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class ApplicationVersionLifecycleListenerAdapter implements VetoableDeploymentListener, DeploymentEventListener {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final AuthenticatedSubject ANONYMOUS_ID = SubjectUtils.getAnonymousSubject();
   private String appId;
   private ApplicationVersionLifecycleListener[] appListeners;

   public ApplicationVersionLifecycleListenerAdapter(String appId, ApplicationVersionLifecycleListener[] listeners) throws DeploymentException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("AppVersionLifecycleAdapter create for " + appId + ", listeners=" + Arrays.asList(listeners));
      }

      this.appId = appId;
      this.appListeners = listeners;
      if (listeners.length > 0) {
         this.registerDeploymentEventListeners(ApplicationVersionUtils.getApplicationName(appId));
      } else if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("AppVersionLifecycleAdapter no listeners for " + appId);
      }

   }

   private void registerDeploymentEventListeners(String appName) throws DeploymentException {
      if (appName != null) {
         DeploymentEventManager.addVetoableDeploymentListener(appName, this);
         DeploymentEventManager.addDeploymentEventListener(appName, this);
      } else if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("AppVersionLifecycleAdapter null appName for " + this.appId);
      }

   }

   public String toString() {
      return "ApplicationVersionLifecycleListenerAdapter[" + this.appId + "]";
   }

   public void cleanup() {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("AppVersionLifecycleAdapter cleanup for " + this.appId);
      }

      this.unregisterDeploymentEventListeners();
   }

   private void unregisterDeploymentEventListeners() {
      DeploymentEventManager.removeVetoableDeploymentListener(this);
      DeploymentEventManager.removeDeploymentEventListener(this);
   }

   public void vetoableApplicationActivate(VetoableDeploymentEvent evt) throws DeploymentVetoException {
   }

   public void vetoableApplicationDeploy(VetoableDeploymentEvent evt) throws DeploymentVetoException {
      this.invokeAppLifecycleListeners(ApplicationVersionLifecycleListenerAdapter.ApplicationLifecycleAction.PRE_DEPLOY, evt, true, false);
   }

   public void vetoableApplicationUndeploy(VetoableDeploymentEvent evt) throws DeploymentVetoException {
      this.invokeAppLifecycleListeners(ApplicationVersionLifecycleListenerAdapter.ApplicationLifecycleAction.PRE_UNDEPLOY, evt, true, true);
   }

   public void applicationActivated(DeploymentEvent evt) {
   }

   public void applicationDeployed(DeploymentEvent evt) {
      try {
         this.invokeAppLifecycleListeners(ApplicationVersionLifecycleListenerAdapter.ApplicationLifecycleAction.POST_DEPLOY, evt, false, false);
      } catch (DeploymentVetoException var3) {
      }

   }

   public void applicationRedeployed(DeploymentEvent evt) {
      try {
         this.invokeAppLifecycleListeners(ApplicationVersionLifecycleListenerAdapter.ApplicationLifecycleAction.POST_DEPLOY, evt, false, false);
      } catch (DeploymentVetoException var3) {
      }

   }

   public void applicationDeleted(DeploymentEvent evt) {
      try {
         this.invokeAppLifecycleListeners(ApplicationVersionLifecycleListenerAdapter.ApplicationLifecycleAction.POST_DELETE, evt, false, true);
      } catch (DeploymentVetoException var3) {
      }

   }

   private void invokeAppLifecycleListeners(final ApplicationLifecycleAction action, final BaseDeploymentEvent evt, final boolean vetoable, final boolean reverse) throws DeploymentVetoException {
      Object obj = SecurityServiceManager.runAsForUserCode(KERNEL_ID, ANONYMOUS_ID, new PrivilegedAction() {
         public Object run() {
            Throwable curThs = null;
            Iterator iter = ApplicationVersionLifecycleListenerAdapter.this.getIterator(reverse);

            while(iter.hasNext()) {
               try {
                  ApplicationVersionLifecycleListener listener = (ApplicationVersionLifecycleListener)iter.next();
                  ApplicationVersionLifecycleEvent event = ApplicationVersionLifecycleListenerAdapter.this.getAppLifecycleEvent(evt);
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("AppVersionLifecycleAdapter invoke " + action + " for " + listener + ", " + evt);
                  }

                  if (listener != null && evt != null) {
                     action.invoke(listener, event);
                  }
               } catch (Throwable var5) {
                  if (vetoable) {
                     return var5;
                  }

                  Throwable curTh = null;
                  if (curThs != null && var5.getCause() != null) {
                     curTh = new Throwable(var5.getMessage());
                     curTh.initCause(curThs);
                  } else {
                     curTh = new Throwable(var5);
                  }

                  curThs = curTh;
               }
            }

            return curThs;
         }
      });
      if (obj instanceof Throwable) {
         if (vetoable) {
            throw new DeploymentVetoException((Throwable)obj);
         } else {
            throw new ApplicationLifecycleException((Throwable)obj);
         }
      }
   }

   private Iterator getIterator(boolean reverse) {
      return reverse ? new Iterator() {
         private ListIterator listIter;

         {
            this.listIter = Arrays.asList(ApplicationVersionLifecycleListenerAdapter.this.appListeners).listIterator(ApplicationVersionLifecycleListenerAdapter.this.appListeners.length);
         }

         public boolean hasNext() {
            return this.listIter.hasPrevious();
         }

         public Object next() {
            return this.listIter.previous();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      } : Arrays.asList(this.appListeners).iterator();
   }

   private ApplicationVersionLifecycleEvent getAppLifecycleEvent(BaseDeploymentEvent evt) {
      AppDeploymentMBean appMBean = evt.getAppDeployment();
      return new ApplicationVersionLifecycleEvent(this.appId, ApplicationUtils.isActiveVersion(this.appId), appMBean.getName(), ApplicationUtils.isActiveVersion(appMBean.getName()));
   }

   public class ApplicationLifecycleException extends RuntimeException {
      public ApplicationLifecycleException(Throwable t) {
         super(DeploymentServiceLogger.logAppListenerExceptionLoggable().getMessage(), t);
      }
   }

   private abstract static class ApplicationLifecycleAction {
      static final ApplicationLifecycleAction PRE_DEPLOY = new ApplicationLifecycleAction("preDeploy") {
         void invoke(ApplicationVersionLifecycleListener listener, ApplicationVersionLifecycleEvent event) throws ApplicationException {
            listener.preDeploy(event);
         }
      };
      static final ApplicationLifecycleAction POST_DEPLOY = new ApplicationLifecycleAction("postDeploy") {
         void invoke(ApplicationVersionLifecycleListener listener, ApplicationVersionLifecycleEvent event) throws ApplicationException {
            listener.postDeploy(event);
         }
      };
      static final ApplicationLifecycleAction PRE_UNDEPLOY = new ApplicationLifecycleAction("preUndeploy") {
         void invoke(ApplicationVersionLifecycleListener listener, ApplicationVersionLifecycleEvent event) throws ApplicationException {
            listener.preUndeploy(event);
         }
      };
      static final ApplicationLifecycleAction POST_DELETE = new ApplicationLifecycleAction("postDelete") {
         void invoke(ApplicationVersionLifecycleListener listener, ApplicationVersionLifecycleEvent event) throws ApplicationException {
            listener.postDelete(event);
         }
      };
      private String name;

      ApplicationLifecycleAction(String name) {
         this.name = name;
      }

      public String toString() {
         return this.name;
      }

      String getName() {
         return this.name;
      }

      abstract void invoke(ApplicationVersionLifecycleListener var1, ApplicationVersionLifecycleEvent var2) throws ApplicationException;
   }
}
