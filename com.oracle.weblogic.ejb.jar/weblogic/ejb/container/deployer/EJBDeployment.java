package weblogic.ejb.container.deployer;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.Module;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.coherence.api.internal.CoherenceService;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;

public final class EJBDeployment extends SingleModuleDeployment implements Deployment {
   public EJBDeployment(AppDeploymentMBean mbean, File f, CoherenceService cs) throws DeploymentException {
      super(mbean, createModule(mbean, cs), f);
   }

   private static Module createModule(AppDeploymentMBean mbean, CoherenceService cs) throws DeploymentException {
      ComponentMBean[] c = mbean.getAppMBean().getComponents();
      if (c != null && c.length != 0) {
         if (c.length > 1) {
            throw new DeploymentException("Application " + ApplicationVersionUtils.getDisplayName(mbean) + " is a JAR file, but it contains > 1 component.");
         } else {
            return new EJBModule(c[0].getURI(), cs);
         }
      } else {
         throw new DeploymentException("Application " + ApplicationVersionUtils.getDisplayName(mbean) + " does not have any Components in it.");
      }
   }
}
