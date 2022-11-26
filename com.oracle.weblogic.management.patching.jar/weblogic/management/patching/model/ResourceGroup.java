package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.management.patching.ApplicationProperties;

public class ResourceGroup implements Target, Serializable {
   private String resourceGroupName;
   private boolean isTargeted = false;
   private ResourceGroupTemplate rgt = null;
   private List applicationPropertyList;
   private List virtualTargetList;
   private Set partitionAppsSet;
   private Set serverSet;

   public ResourceGroup(String resourceGroupName) {
      this.resourceGroupName = resourceGroupName;
      this.applicationPropertyList = new ArrayList();
      this.virtualTargetList = new ArrayList();
      this.partitionAppsSet = new HashSet();
      this.serverSet = new HashSet();
   }

   public String getResourceGroupName() {
      return this.resourceGroupName;
   }

   public void setTargeted(boolean isTargeted) {
      this.isTargeted = isTargeted;
   }

   public boolean isTargeted() {
      return this.isTargeted;
   }

   public void applyTargetedAndPropagateValue() {
      this.setTargeted(true);
      if (this.rgt != null) {
         this.rgt.applyTargetedAndPropagateValue();
      }

      Iterator var1;
      if (this.partitionAppsSet != null && !this.partitionAppsSet.isEmpty()) {
         var1 = this.partitionAppsSet.iterator();

         while(var1.hasNext()) {
            PartitionApps partitionApps = (PartitionApps)var1.next();
            partitionApps.applyTargetedAndPropagateValue();
         }
      } else {
         var1 = this.serverSet.iterator();

         while(var1.hasNext()) {
            Server server = (Server)var1.next();
            server.applyTargetedAndPropagateValue();
         }
      }

   }

   public ResourceGroupTemplate getRgt() {
      return this.rgt;
   }

   public void setRgt(ResourceGroupTemplate rgt) {
      this.rgt = rgt;
   }

   public List getApplicationPropertyList() {
      return this.applicationPropertyList;
   }

   public void addApplicationProperties(ApplicationProperties applicationProperties) {
      this.applicationPropertyList.add(applicationProperties);
   }

   public List getVirtualTargets() {
      return this.virtualTargetList;
   }

   public void addVirtualTarget(VirtualTarget virtualTarget) {
      this.virtualTargetList.add(virtualTarget);
   }

   public void addPartitionApps(PartitionApps partitionApps) {
      this.partitionAppsSet.add(partitionApps);
   }

   public Set getPartitionApps() {
      return this.partitionAppsSet;
   }

   public void addServer(Server server) {
      this.serverSet.add(server);
   }

   public Set getServers() {
      return this.serverSet;
   }
}
