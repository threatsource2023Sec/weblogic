package weblogic.j2ee;

import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.MailSessionMBean;
import weblogic.management.internal.DeploymentHandler;
import weblogic.management.internal.DeploymentHandlerContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class RMCFactoryDeployer implements DeploymentHandler {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void prepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
   }

   public void activateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
      if (deployment instanceof MailSessionMBean) {
         final MailSessionMBean mbean = (MailSessionMBean)deployment;
         Object thrown = SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               Object thrown = null;

               try {
                  MailSessionUtils.deployMailSession(mbean);
               } catch (DeploymentException var3) {
                  thrown = var3;
               }

               return thrown;
            }
         });
         if (thrown != null && thrown instanceof DeploymentException) {
            throw (DeploymentException)thrown;
         }
      }

   }

   public void deactivateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) {
      if (deployment instanceof MailSessionMBean) {
         final MailSessionMBean mbean = (MailSessionMBean)deployment;
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               try {
                  MailSessionUtils.undeployMailSession(mbean);
               } catch (DeploymentException var2) {
                  J2EELogger.logFailedToUndeployMailSession(var2);
               }

               return null;
            }
         });
      }

   }

   public void unprepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
   }
}
