package weblogic.rmi.cluster;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.spi.HostID;

public final class RichReplicaList extends BasicReplicaList {
   private static final long serialVersionUID = -5818437427224059485L;
   private int maxWeight;
   private int previousSize;
   private ServerInfo[] serverInfoArray;
   private int loopIter;
   private Object localServerInfo;

   public RichReplicaList(RemoteReference primary) {
      super(primary);
      this.maxWeight = -1;
      this.previousSize = 1;
      this.serverInfoArray = null;
      this.loopIter = 1;
      this.maxWeight = -1;
      this.loopIter = 1;
   }

   private RichReplicaList(RemoteReference primary, Object localServerInfo) {
      this(primary);
      this.localServerInfo = localServerInfo;
   }

   public RemoteReference findReplicaHostedBy(String serverName) {
      HostID serverId = null;
      ServerInfo info = ServerInfoManager.theOne().getServerInfo(serverName);
      if (info != null) {
         serverId = info.getID();
      }

      return serverId == null ? null : this.findReplicaHostedBy(serverId);
   }

   public boolean isChanged() {
      int currentSize = this.size();
      if (this.previousSize != currentSize) {
         this.previousSize = currentSize;
         return true;
      } else {
         return false;
      }
   }

   public int getMaxServerWeight() {
      if (this.isChanged()) {
         this.maxWeight = -1;
      }

      if (this.maxWeight == -1) {
         ServerInfoManager infoManager = ServerInfoManager.theOne();
         synchronized(this) {
            Iterator it = this.iterator();

            while(it.hasNext()) {
               RemoteReference ror = (RemoteReference)it.next();
               ServerInfo serverInfo = infoManager.getServerInfo(ror);
               if (serverInfo != null) {
                  int weight = serverInfo.getLoadWeight();
                  if (weight > this.maxWeight) {
                     this.maxWeight = weight;
                  }
               }
            }
         }
      }

      return this.maxWeight;
   }

   public void reset(ReplicaList newList) {
      super.reset(newList);
      this.maxWeight = -1;
      this.loopIter = 1;
      this.resetAndNormalizeWeights();
   }

   public RichReplicaList() {
      this.maxWeight = -1;
      this.previousSize = 1;
      this.serverInfoArray = null;
      this.loopIter = 1;
      this.loopIter = 1;
   }

   public ReplicaList getListWithRefHostedBy(HostID hostID) {
      return new RichReplicaList(this.findReplicaHostedBy(hostID), new Object[]{ServerInfoManager.theOne().getServerInfo(hostID)});
   }

   void resetAndNormalizeWeights() {
      synchronized(this) {
         this.serverInfoArray = ServerInfoManager.theOne().getServerInfos();
         int tmpGds = this.serverInfoArray[0].getLoadWeight();

         for(int i = 1; i < this.serverInfoArray.length; ++i) {
            tmpGds = gcd(tmpGds, this.serverInfoArray[i].getLoadWeight());
         }

         this.normalizeWeights(tmpGds, this.serverInfoArray);
      }
   }

   private void normalizeWeights(int norWeight, ServerInfo[] infoArray) {
      this.loopIter = 1;

      for(int i = 0; i < infoArray.length; ++i) {
         infoArray[i].setNormalizedWeight(infoArray[i].getLoadWeight() / norWeight);
         if (infoArray[i].getNormalizedWeight() > this.loopIter) {
            this.loopIter = infoArray[i].getNormalizedWeight();
         }
      }

   }

   int getLoopIter() {
      return this.loopIter;
   }

   ServerInfo[] getServerInfos() {
      return this.serverInfoArray;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (this.localServerInfo != null) {
         out.writeObject(this.localServerInfo);
      } else {
         out.writeObject(ServerInfoManager.theOne().writeUpdate());
      }

      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      ServerInfoManager.theOne().readUpdate(in.readObject());
      super.readExternal(in);
   }

   private static int gcd(int a, int b) {
      while(b != 0) {
         int z = a % b;
         a = b;
         b = z;
      }

      return a;
   }
}
