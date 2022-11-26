package weblogic.management.deploy.internal;

import java.security.AccessController;
import java.util.ArrayList;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class AppMBeanUsages {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static DeploymentMBean[] getDeployments(TargetMBean target) {
      ArrayList result = new ArrayList();
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      DeploymentMBean[] deployments = domain.getDeployments();

      for(int i = 0; i < deployments.length; ++i) {
         if (isTargeted(deployments[i], target)) {
            result.add(deployments[i]);
         }
      }

      ApplicationMBean[] applications = domain.getApplications();

      for(int j = 0; j < applications.length; ++j) {
         ComponentMBean[] components = applications[j].getComponents();

         for(int k = 0; k < components.length; ++k) {
            if (isTargeted(components[k], target)) {
               result.add(deployments[k]);
            }
         }
      }

      DeploymentMBean[] finalResult = new DeploymentMBean[result.size()];
      return (DeploymentMBean[])((DeploymentMBean[])result.toArray(finalResult));
   }

   private static boolean isTargeted(DeploymentMBean deployment, TargetMBean target) {
      TargetMBean[] targets = deployment.getTargets();

      for(int i = 0; i < targets.length; ++i) {
         if (target.getName().equals(targets[i].getName())) {
            return true;
         }
      }

      return false;
   }
}
