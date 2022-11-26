package weblogic.coherence.container.server;

import java.io.File;
import weblogic.application.Module;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;

public class CoherenceDeployment extends SingleModuleDeployment {
   public CoherenceDeployment(AppDeploymentMBean appDeploymentMBean, File file) throws DeploymentException {
      super(appDeploymentMBean, createModule(appDeploymentMBean, file), file);
   }

   private static Module createModule(AppDeploymentMBean mbean, File file) throws DeploymentException {
      String name = mbean.getApplicationName();
      return new CoherenceModule(name, name, file.isDirectory());
   }
}
