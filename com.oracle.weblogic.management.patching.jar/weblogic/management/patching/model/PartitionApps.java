package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PartitionApps implements Target, Serializable {
   private String partitionName;
   private boolean isTargeted = false;
   private Map resourceGroups;
   private List virtualTargetList;
   private Partition partition;
   private Server server;

   public Partition getPartition() {
      return this.partition;
   }

   public void setPartition(Partition partition) {
      this.partition = partition;
   }

   public Server getServer() {
      return this.server;
   }

   public void setServer(Server server) {
      this.server = server;
   }

   public PartitionApps(String partitionName) {
      this.partitionName = partitionName;
      this.resourceGroups = new HashMap();
      this.virtualTargetList = new ArrayList();
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setTargeted(boolean isTargeted) {
      this.isTargeted = isTargeted;
   }

   public boolean isTargeted() {
      return this.isTargeted;
   }

   public void applyTargetedAndPropagateValue() {
      this.setTargeted(true);
      this.server.applyTargetedAndPropagateValue();
      if (this.partition != null) {
         this.partition.applyTargetedAndPropagateValue();
      }

   }

   public Collection getResourceGroups() {
      return this.resourceGroups.values();
   }

   public List getVirtualTargets() {
      return this.virtualTargetList;
   }

   public void addVirtualTarget(VirtualTarget virtualTarget) {
      this.virtualTargetList.add(virtualTarget);
   }

   public void addResourceGroup(ResourceGroup resourceGroup) {
      this.resourceGroups.put(resourceGroup.getResourceGroupName(), resourceGroup);
   }

   public ResourceGroup getResourceGroup(String resourceGroupName) {
      return (ResourceGroup)this.resourceGroups.get(resourceGroupName);
   }

   public boolean hasResourceGroup(String resourceGroupName) {
      return this.resourceGroups.containsKey(resourceGroupName);
   }

   public List getApplicationPropertyList() {
      List applicationPropertyList = new ArrayList();
      Iterator var2 = this.resourceGroups.values().iterator();

      while(var2.hasNext()) {
         ResourceGroup rg = (ResourceGroup)var2.next();
         applicationPropertyList.addAll(rg.getApplicationPropertyList());
      }

      return applicationPropertyList;
   }
}
