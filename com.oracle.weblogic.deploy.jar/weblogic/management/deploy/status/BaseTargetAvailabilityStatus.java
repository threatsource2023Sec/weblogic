package weblogic.management.deploy.status;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.management.TargetAvailabilityStatus;
import weblogic.management.deploy.internal.ComponentTarget;
import weblogic.utils.Debug;

/** @deprecated */
@Deprecated
public abstract class BaseTargetAvailabilityStatus implements Serializable, TargetAvailabilityStatus {
   private static final long serialVersionUID = 346425021436708210L;
   protected String componentTargetName;
   protected int componentTargetType;
   protected boolean isStaged = false;
   protected HashSet availableServers = null;
   protected HashMap unavailableServersAvailabilityMap = null;

   public BaseTargetAvailabilityStatus(String componentTargetName, int componentTargetType, boolean isStaged) {
      this.componentTargetName = componentTargetName;
      this.componentTargetType = componentTargetType;
      this.isStaged = isStaged;
      this.availableServers = new HashSet();
      this.unavailableServersAvailabilityMap = new HashMap();
   }

   public String getTargetName() {
      return this.componentTargetName;
   }

   public int getTargetType() {
      return this.componentTargetType;
   }

   public int getDeploymentStatus() {
      int status = 0;
      if (this.unavailableServersAvailabilityMap.size() == 0 && this.availableServers.size() > 0) {
         status = 1;
      } else if (this.unavailableServersAvailabilityMap.size() > 0 && this.availableServers.size() > 0) {
         status = 2;
      }

      return status;
   }

   public int getAvailabilityStatus() {
      Debug.assertion(false, "This is not a valid call");
      return -1;
   }

   public Set getServersAvailabilityStatus() {
      Iterator itr = this.availableServers.iterator();
      HashSet servers = new HashSet();

      while(itr.hasNext()) {
         String serverName = (String)itr.next();
         ServerTargetAvailabilityStatus status = new ServerTargetAvailabilityStatus(serverName, this.isStaged, true, 1);
         servers.add(status);
      }

      Iterator itr1 = this.unavailableServersAvailabilityMap.keySet().iterator();

      while(itr1.hasNext()) {
         String serverName = (String)itr1.next();
         int availabilityStatus = (Integer)this.unavailableServersAvailabilityMap.get(serverName);
         ServerTargetAvailabilityStatus status = new ServerTargetAvailabilityStatus(serverName, this.isStaged, false, availabilityStatus);
         servers.add(status);
      }

      return servers;
   }

   public Set getClustersAvailabilityStatus() {
      Debug.assertion(false, "This is not a valid call");
      return null;
   }

   public void updateAvailabilityStatus(ComponentTarget compTarget) {
      String physicalServer = compTarget.getPhysicalTarget();
      if (this.unavailableServersAvailabilityMap.keySet().contains(physicalServer)) {
         this.unavailableServersAvailabilityMap.remove(physicalServer);
         this.availableServers.add(physicalServer);
      } else {
         String mesg = compTarget.getComponentTarget() + " not targeted to " + physicalServer;
         Debug.assertion(false, mesg);
      }

   }

   public void updateUnavailabilityStatus(List stagedServersList) {
      if (this.isStaged) {
         Iterator itr = this.unavailableServersAvailabilityMap.keySet().iterator();

         while(itr.hasNext()) {
            String serverName = (String)itr.next();
            if (!stagedServersList.contains(serverName)) {
               this.unavailableServersAvailabilityMap.put(serverName, new Integer(3));
            }
         }
      } else {
         Debug.assertion(false, "updateUnavailabilityStatus applicable only for staged applications.");
      }

   }
}
