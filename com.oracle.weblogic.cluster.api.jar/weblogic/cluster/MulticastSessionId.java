package weblogic.cluster;

import java.io.Serializable;

public class MulticastSessionId implements Serializable {
   private String partitionID;
   private String resourceGroupName;
   private String name;

   public MulticastSessionId(String partitionID, String resourceGroupName) {
      this(partitionID, resourceGroupName, MulticastSessionIDConstants.ANNOUNCEMENT_MANAGER_ID.name);
   }

   public MulticastSessionId(String partitionID, String resourceGroupName, String name) {
      this.partitionID = partitionID == null ? "0" : partitionID;
      this.resourceGroupName = resourceGroupName == null ? "NO_RESOURCE_GROUP" : resourceGroupName;
      this.name = name == null ? MulticastSessionIDConstants.ANNOUNCEMENT_MANAGER_ID.name : name;
   }

   public String getPartitionID() {
      return this.partitionID;
   }

   public String getResourceGroupName() {
      return this.resourceGroupName;
   }

   public String getName() {
      return this.name;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MulticastSessionId that = (MulticastSessionId)o;
         return this.partitionID.equals(that.partitionID) && this.resourceGroupName.equals(that.resourceGroupName) && this.name.equals(that.name);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.partitionID.hashCode();
      result = 31 * result + this.resourceGroupName.hashCode();
      result = 31 * result + this.name.hashCode();
      return result;
   }

   public String toString() {
      return "MulticastSessionId{" + this.partitionID + ':' + this.resourceGroupName + ':' + this.name + "}";
   }

   public boolean isCustomMulticastSession() {
      if (!this.name.contains("-")) {
         return false;
      } else {
         String[] components = this.name.split("-");
         return components.length == 5;
      }
   }
}
