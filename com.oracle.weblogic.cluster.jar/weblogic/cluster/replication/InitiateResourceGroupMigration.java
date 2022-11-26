package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.cluster.GroupMessage;
import weblogic.rmi.spi.HostID;

public class InitiateResourceGroupMigration implements GroupMessage, Externalizable {
   private String partitionName;
   private String resourceGroupName;
   private String targetClusterName;

   public InitiateResourceGroupMigration() {
   }

   public InitiateResourceGroupMigration(String partitionName, String resourceGroupName, String targetClusterName) {
      this.partitionName = partitionName;
      this.resourceGroupName = resourceGroupName;
      this.targetClusterName = targetClusterName;
   }

   public void execute(HostID sender) {
      if (ResourceGroupMigrationHandler.getInstance().getLocalMigrationStatus() != ResourceGroupMigrationHandler.MigrationStatus.IN_PROGRESS) {
         ResourceGroupMigrationHandler.getInstance().initiateMigration(sender, this.partitionName, this.resourceGroupName, this.targetClusterName);
      }

   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeUTF(this.partitionName);
      oo.writeUTF(this.resourceGroupName);
      oo.writeUTF(this.targetClusterName);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.partitionName = oi.readUTF();
      this.resourceGroupName = oi.readUTF();
      this.targetClusterName = oi.readUTF();
   }
}
