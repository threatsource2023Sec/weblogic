package weblogic.servlet.internal.session;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.cluster.replication.QuerySessionResponseMessage;
import weblogic.rmi.spi.HostID;

public class HttpQuerySessionResponseMessage implements QuerySessionResponseMessage {
   private HostID primary;
   private HostID secondary;
   private String id;

   public HttpQuerySessionResponseMessage() {
   }

   public HttpQuerySessionResponseMessage(String id, HostID primary, HostID secondary) {
      this.id = id;
      this.primary = primary;
      this.secondary = secondary;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.id);
      out.writeObject(this.primary);
      out.writeObject(this.secondary);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.id = in.readUTF();
      this.primary = (HostID)in.readObject();
      this.secondary = (HostID)in.readObject();
   }

   public HostID getPrimary() {
      return this.primary;
   }

   public HostID getSecondary() {
      return this.secondary;
   }

   public String getID() {
      return this.id;
   }

   public String toString() {
      return "HttpQuerySessionResponseMessage id: " + this.id + ", primary: " + this.primary + ", secondary: " + this.secondary;
   }
}
