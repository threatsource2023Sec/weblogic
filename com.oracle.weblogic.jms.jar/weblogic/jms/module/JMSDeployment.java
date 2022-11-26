package weblogic.jms.module;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.Module;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public final class JMSDeployment extends SingleModuleDeployment implements Deployment {
   public JMSDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      super(mbean, createModule(mbean), f);
   }

   public JMSDeployment(SystemResourceMBean sbean, File f) throws DeploymentException {
      super(sbean, createModule(sbean), f);
   }

   private static Module createModule(AppDeploymentMBean mbean) throws DeploymentException {
      if (mbean == null) {
         throw new DeploymentException("AppDepoymentMBean cannot be null. Please check the server log related error messages");
      } else {
         SubDeploymentMBean[] subDeployments = mbean.getSubDeployments();
         if (subDeployments != null) {
            for(int i = 0; i < subDeployments.length; ++i) {
               SubDeploymentMBean[] subSubDeployments = subDeployments[i].getSubDeployments();
               if (subSubDeployments != null && subSubDeployments.length > 0) {
                  throw new DeploymentException("Application " + ApplicationVersionUtils.getDisplayName(mbean) + " is a not a stand alone JMS deployment, it contains nested sub deployments.");
               }
            }
         }

         return new JMSModule(mbean.getSourcePath());
      }
   }

   private static Module createModule(SystemResourceMBean sbean) throws DeploymentException {
      if (sbean == null) {
         throw new DeploymentException("SystemResourceMBean cannot be null. Please check the server log related error messages");
      } else {
         String uri = sbean.getDescriptorFileName();
         if (uri == null) {
            throw new DeploymentException("JMSSystemResource " + ApplicationVersionUtils.getDisplayName(sbean) + " does not have a descriptor file name");
         } else {
            return new JMSModule(uri);
         }
      }
   }
}
