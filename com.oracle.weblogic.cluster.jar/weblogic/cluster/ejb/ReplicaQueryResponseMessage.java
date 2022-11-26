package weblogic.cluster.ejb;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.cluster.replication.QuerySessionResponseMessage;
import weblogic.rmi.extensions.server.RemoteReference;

public class ReplicaQueryResponseMessage implements QuerySessionResponseMessage {
   private static final long serialVersionUID = 2891547381105837500L;
   private String uuid = null;
   private RemoteReference ref = null;

   public ReplicaQueryResponseMessage() {
   }

   public ReplicaQueryResponseMessage(String uuid, RemoteReference ref) {
      this.uuid = uuid;
      this.ref = ref;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.uuid);
      out.writeObject(this.ref);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.uuid = in.readUTF();
      this.ref = (RemoteReference)in.readObject();
   }

   public RemoteReference getRemoteRef() {
      return this.ref;
   }

   public String getID() {
      return this.uuid;
   }

   public String toString() {
      return "ReplicaQueryResponseMessage[uuid=" + this.uuid + "]ref=" + this.ref;
   }
}
