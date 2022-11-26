package weblogic.rmi.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.spi.HostID;

public final class ServerInfo implements Externalizable {
   private static final long serialVersionUID = -2369726782100305637L;
   private long avgRequestWaitMillis = 0L;
   private int loadWeight;
   private HostID id;
   private String name;
   private int normalizedWeight;

   ServerInfo(String name, HostID id, int loadWeight) {
      this.id = id;
      this.name = name;
      this.loadWeight = loadWeight;
   }

   public String getName() {
      return this.name;
   }

   public HostID getID() {
      return this.id;
   }

   public int getLoadWeight() {
      return this.loadWeight;
   }

   public long getAvgRequestWaitMillis() {
      return this.avgRequestWaitMillis;
   }

   public String toString() {
      return this.name + "(on " + this.id + "):\n  avgRequestWaitMillis: " + this.avgRequestWaitMillis + "\n  loadWeight:           " + this.loadWeight;
   }

   public void setAvgRequestWaitMillis(long millis) {
      this.avgRequestWaitMillis = millis;
   }

   void setNormalizedWeight(int normalizedWeight) {
      this.normalizedWeight = normalizedWeight;
   }

   int getNormalizedWeight() {
      return this.normalizedWeight;
   }

   public ServerInfo() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         WLObjectOutput wlOut = (WLObjectOutput)out;
         wlOut.writeLong(this.avgRequestWaitMillis);
         wlOut.writeInt(this.loadWeight);
         wlOut.writeObjectWL(this.id);
         wlOut.writeString(this.name);
      } else {
         out.writeLong(this.avgRequestWaitMillis);
         out.writeInt(this.loadWeight);
         out.writeObject(this.id);
         out.writeObject(this.name);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      if (in instanceof WLObjectInput) {
         WLObjectInput wlIn = (WLObjectInput)in;
         this.avgRequestWaitMillis = wlIn.readLong();
         this.loadWeight = wlIn.readInt();
         this.id = (HostID)wlIn.readObjectWL();
         this.name = wlIn.readString();
      } else {
         this.avgRequestWaitMillis = in.readLong();
         this.loadWeight = in.readInt();
         this.id = (HostID)in.readObject();
         this.name = (String)in.readObject();
      }

   }
}
