package weblogic.connector.deploy;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.Module;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.connector.common.Debug;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;

public final class ConnectorDeployment extends SingleModuleDeployment implements Deployment {
   public ConnectorDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      super(mbean, createModule(mbean), f);
   }

   private static Module createModule(AppDeploymentMBean mbean) throws DeploymentException {
      ComponentMBean[] c = mbean.getAppMBean().getComponents();
      String exMsg;
      if (c != null && c.length != 0) {
         if (c.length > 1) {
            exMsg = Debug.getExceptionMoreThanOneComponent(ApplicationVersionUtils.getDisplayName(mbean));
            throw new DeploymentException(exMsg);
         } else {
            return ConnectorModule.createStandaloneConnectorModule(c[0].getURI());
         }
      } else {
         exMsg = Debug.getExceptionNoComponents(ApplicationVersionUtils.getDisplayName(mbean));
         throw new DeploymentException(exMsg);
      }
   }
}
