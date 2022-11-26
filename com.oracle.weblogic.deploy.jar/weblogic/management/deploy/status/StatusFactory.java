package weblogic.management.deploy.status;

import weblogic.management.TargetAvailabilityStatus;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.utils.Debug;

/** @deprecated */
@Deprecated
public class StatusFactory {
   private static StatusFactory instance = new StatusFactory();

   public static StatusFactory getInstance() {
      return instance;
   }

   private StatusFactory() {
   }

   public TargetAvailabilityStatus createStatus(ApplicationMBean appMBean, ConfigurationMBean compTarget) {
      TargetAvailabilityStatus status = null;
      boolean isStaged = appMBean.getStagingMode().equals("stage");
      if (compTarget instanceof ServerMBean) {
         status = new ServerTargetAvailabilityStatus((ServerMBean)compTarget, isStaged);
      } else if (compTarget instanceof ClusterMBean) {
         status = new ClusterTargetAvailabilityStatus((ClusterMBean)compTarget, isStaged);
      } else if (compTarget instanceof VirtualHostMBean) {
         status = new VirtualHostTargetAvailabilityStatus((VirtualHostMBean)compTarget, isStaged);
      } else {
         Debug.assertion(false, "Invalid ConfigurationMBean");
      }

      return (TargetAvailabilityStatus)status;
   }
}
