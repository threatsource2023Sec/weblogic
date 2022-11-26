package weblogic.messaging.interception.module;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.Module;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;

public final class InterceptionDeployment extends SingleModuleDeployment implements Deployment {
   public InterceptionDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      super(mbean, createModule(mbean), f);
   }

   private static Module createModule(AppDeploymentMBean mbean) throws DeploymentException {
      ComponentMBean[] c = mbean.getAppMBean().getComponents();
      StringBuffer sb = new StringBuffer();
      sb.append("Components: ");

      for(int i = 0; i < c.length; ++i) {
         sb.append(c[i].getName());
         if (i < c.length - 1) {
            sb.append(",");
         }
      }

      if (c != null && c.length != 0) {
         if (c.length > 1) {
            throw new DeploymentException("Application" + mbean.getName() + " is a MAR file, but it contains > 1 component.");
         } else {
            return new InterceptionModule(c[0].getURI());
         }
      } else {
         throw new DeploymentException("Application " + mbean.getName() + " does not have any Components in it.");
      }
   }
}
