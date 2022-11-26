package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Partition implements Target, Serializable {
   private String partitionName;
   private boolean isTargeted = false;
   private Map resourceGroups;
   private List virtualTargetList;

   public Partition(String partitionName) {
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
}
