package weblogic.servlet.internal;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.Module;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;

public final class WarDeployment extends SingleModuleDeployment implements Deployment {
   public WarDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      super(mbean, createModule(mbean), f);
   }

   private static Module createModule(AppDeploymentMBean mbean) throws DeploymentException {
      ComponentMBean[] c = mbean.getAppMBean().getComponents();
      if (c != null && c.length != 0) {
         if (c.length > 1) {
            throw new DeploymentException("Application" + ApplicationVersionUtils.getDisplayName(mbean) + " is a WAR file, but it contains > 1 component.");
         } else {
            return new WebAppModule(c[0].getURI(), (String)null);
         }
      } else {
         throw new DeploymentException("Application " + ApplicationVersionUtils.getDisplayName(mbean) + " does not have any Components in it.");
      }
   }
}
