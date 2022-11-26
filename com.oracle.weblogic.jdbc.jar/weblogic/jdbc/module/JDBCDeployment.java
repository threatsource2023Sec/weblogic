package weblogic.jdbc.module;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.Module;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public final class JDBCDeployment extends SingleModuleDeployment implements Deployment {
   public JDBCDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      super(mbean, createModule(mbean), f);
   }

   public JDBCDeployment(SystemResourceMBean sbean, File f) throws DeploymentException {
      super(sbean, createModule(sbean), f);
   }

   private static Module createModule(AppDeploymentMBean mbean) throws DeploymentException {
      ComponentMBean[] c = mbean.getAppMBean().getComponents();
      if (c != null && c.length != 0) {
         if (c.length > 1) {
            throw new DeploymentException("Application" + ApplicationVersionUtils.getDisplayName(mbean) + " is a DAR file, but it contains > 1 component.");
         } else {
            return new JDBCModule(c[0].getURI());
         }
      } else {
         throw new DeploymentException("Application " + ApplicationVersionUtils.getDisplayName(mbean) + " does not have any Components in it.");
      }
   }

   private static Module createModule(SystemResourceMBean sbean) throws DeploymentException {
      return new JDBCModule(sbean);
   }
}
