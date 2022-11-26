package weblogic.cacheprovider.coherence;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.Module;
import weblogic.application.internal.SingleModuleDeployment;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.SystemResourceMBean;

public class CoherenceClusterSystemResourceDeployment extends SingleModuleDeployment implements Deployment {
   public CoherenceClusterSystemResourceDeployment(SystemResourceMBean sbean, File f) throws DeploymentException {
      super(sbean, createModule(sbean), f);
   }

   private static Module createModule(SystemResourceMBean sbean) throws DeploymentException {
      String uri = sbean.getDescriptorFileName();
      if (uri == null) {
         throw new DeploymentException("CoherenceClusterSystemResource " + ApplicationVersionUtils.getDisplayName(sbean) + " does not have a descriptor file name");
      } else {
         return new CoherenceClusterModule(uri, (CoherenceClusterSystemResourceMBean)sbean);
      }
   }
}
