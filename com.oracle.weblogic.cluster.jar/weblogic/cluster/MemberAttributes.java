package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.cluster.ServerInfoManager;
import weblogic.server.channels.ChannelService;

final class MemberAttributes implements ClusterMemberInfo, Externalizable {
   private static final long serialVersionUID = -2297055762635480163L;
   private ServerIdentity jvmid;
   private String hostAddress;
   private String machineName;
   private String version;
   private long joinTime;
   private int loadWeight;
   private String replicationGroup;
   private String preferredSecondaryGroup;
   private String clusterName;
   private boolean isMigratableServer;
   private String replicationChannel;
   private PeerInfo info;

   public MemberAttributes() {
   }

   public MemberAttributes(String hostAddress, String machineName, String version, long joinTime, int loadWeight, String replicationGroup, String preferredSecondaryGroup, String clusterName, boolean isMigratableServer, String replicationChannel, PeerInfo info) {
      this.jvmid = LocalServerIdentity.getIdentity();
      this.hostAddress = hostAddress == null ? "" : hostAddress;
      this.machineName = machineName;
      this.version = version;
      this.joinTime = joinTime;
      this.loadWeight = loadWeight;
      this.replicationGroup = replicationGroup == null ? "" : replicationGroup;
      this.preferredSecondaryGroup = preferredSecondaryGroup == null ? "" : preferredSecondaryGroup;
      this.clusterName = clusterName;
      this.isMigratableServer = isMigratableServer;
      this.replicationChannel = replicationChannel == null ? "" : replicationChannel;
      this.info = PeerInfo.getPeerInfoForWire();
   }

   public String toString() {
      return this.jvmid.getServerName() + " jvmid:" + this.jvmid;
   }

   public int hashCode() {
      return this.jvmid.hashCode();
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof MemberAttributes)) {
         return false;
      } else {
         MemberAttributes other = (MemberAttributes)object;
         return this.jvmid.equals(other.jvmid) && this.serverName().equals(other.serverName());
      }
   }

   public ServerIdentity identity() {
      return this.jvmid;
   }

   public String serverName() {
      return this.jvmid.getServerName();
   }

   public String hostAddress() {
      return this.hostAddress;
   }

   public String machineName() {
      return this.machineName;
   }

   public String version() {
      return this.version;
   }

   public long joinTime() {
      return this.joinTime;
   }

   public int loadWeight() {
      return this.loadWeight;
   }

   public String replicationGroup() {
      return this.replicationGroup;
   }

   public String preferredSecondaryGroup() {
      return this.preferredSecondaryGroup;
   }

   public String domainName() {
      return this.jvmid.getDomainName();
   }

   public String clusterName() {
      return this.clusterName;
   }

   public boolean isMigratableServer() {
      return this.isMigratableServer;
   }

   public String replicationChannel() {
      return this.replicationChannel;
   }

   public PeerInfo peerInfo() {
      return this.info;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.info);
      out.writeObject(this.jvmid);
      out.writeUTF(this.hostAddress);
      out.writeUTF(this.machineName);
      out.writeUTF(this.version);
      out.writeLong(this.joinTime);
      out.writeObject(ServerInfoManager.theOne().writeLocalInfoUpdate());
      out.writeInt(this.loadWeight);
      out.writeUTF(this.replicationGroup);
      out.writeUTF(this.preferredSecondaryGroup);
      out.writeUTF(this.clusterName);
      out.writeBoolean(this.isMigratableServer);
      out.writeUTF(this.replicationChannel);
      ChannelService.exportServerChannels(this.jvmid, out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.info = (PeerInfo)in.readObject();
      this.jvmid = (ServerIdentity)in.readObject();
      this.hostAddress = in.readUTF();
      this.machineName = in.readUTF();
      this.version = in.readUTF();
      this.joinTime = in.readLong();
      ServerInfoManager.theOne().readUpdate(in.readObject());
      this.loadWeight = in.readInt();
      this.replicationGroup = in.readUTF();
      this.preferredSecondaryGroup = in.readUTF();
      this.clusterName = in.readUTF();
      this.isMigratableServer = in.readBoolean();
      this.replicationChannel = in.readUTF();
      ChannelService.importServerChannels(this.jvmid, in);
   }
}
