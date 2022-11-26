package weblogic.management.deploy.status;

import java.util.Set;
import weblogic.management.configuration.ServerMBean;
import weblogic.utils.Debug;

/** @deprecated */
@Deprecated
public class ServerTargetAvailabilityStatus extends BaseTargetAvailabilityStatus {
   private static final long serialVersionUID = -2705317822110632531L;

   public ServerTargetAvailabilityStatus(ServerMBean serverMBean, boolean isStaged) {
      super(serverMBean.getName(), 1, isStaged);
      if (isStaged) {
         this.unavailableServersAvailabilityMap.put(serverMBean.getName(), new Integer(2));
      } else {
         this.unavailableServersAvailabilityMap.put(serverMBean.getName(), new Integer(0));
      }

   }

   public ServerTargetAvailabilityStatus(String serverName, boolean isStaged, boolean available, int unavailabilityStatus) {
      super(serverName, 1, isStaged);
      if (available) {
         this.availableServers.add(serverName);
      } else {
         this.unavailableServersAvailabilityMap.put(serverName, new Integer(unavailabilityStatus));
      }

   }

   public int getAvailabilityStatus() {
      int status = false;
      int depStatus = this.getDeploymentStatus();
      int status;
      if (depStatus == 1) {
         status = 1;
      } else {
         Integer statusValue = (Integer)this.unavailableServersAvailabilityMap.get(this.getTargetName());
         status = statusValue;
      }

      return status;
   }

   public Set getServersAvailabilityStatus() {
      Debug.assertion(false, "This is not a valid call");
      return null;
   }

   public Set getClustersAvailabilityStatus() {
      Debug.assertion(false, "This is not a valid call");
      return null;
   }
}
