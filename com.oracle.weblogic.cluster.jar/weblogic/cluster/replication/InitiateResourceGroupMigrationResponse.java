package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.cluster.GroupMessage;
import weblogic.rmi.spi.HostID;

public class InitiateResourceGroupMigrationResponse implements GroupMessage, Externalizable {
   private HostID destination;
   private Status status;

   public InitiateResourceGroupMigrationResponse() {
   }

   public InitiateResourceGroupMigrationResponse(HostID destination, Status status) {
      this.destination = destination;
      this.status = status;
   }

   public void execute(HostID sender) {
      if (this.destination.isLocal()) {
         ResourceGroupMigrationHandler.getInstance().handleResponse(sender, this);
      }

   }

   public HostID getDestination() {
      return this.destination;
   }

   public Status getStatus() {
      return this.status;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeObject(this.destination);
      oo.writeObject(this.status);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.destination = (HostID)oi.readObject();
      this.status = (Status)oi.readObject();
   }
}
