package weblogic.rjvm;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;

public class ClusterInfo {
   static final long serialVersionUID = 6364840763030891921L;
   private String domainName;
   private String clusterAddress;
   private String channelName;
   private int port;
   private int sslPort;
   private int adminPort;
   private int protocolMask;
   private boolean isAdmin;
   private transient String protocolName;

   ClusterInfo() {
   }

   ClusterInfo(String domainName, String clusterAddress, String channelName, int port, int sslPort, int adminPort, int protocolMask, String protocolName, boolean isAdmin) {
      if ((this.domainName = domainName) == null) {
         throw new AssertionError("Unexpected null.");
      } else if ((this.clusterAddress = clusterAddress) == null) {
         throw new AssertionError("Unexpected null.");
      } else if ((this.channelName = channelName) == null) {
         throw new AssertionError("Unexpected null.");
      } else {
         this.port = port;
         this.sslPort = sslPort;
         this.adminPort = adminPort;
         this.protocolMask = protocolMask;
         this.protocolName = protocolName;
         this.isAdmin = isAdmin;
      }
   }

   public String toString() {
      return "ClusterInfo(" + this.domainName + ',' + this.channelName + ',' + this.clusterAddress + ':' + this.port + ':' + this.sslPort + ':' + this.adminPort + ',' + this.protocolMask + ')' + '(' + this.protocolName + ',' + this.isAdmin + ')';
   }

   public String getDomainName() {
      return this.domainName;
   }

   public String getClusterAddress() {
      return this.clusterAddress;
   }

   public String getChannelName() {
      return this.channelName;
   }

   public int getPort() {
      return this.port;
   }

   public int getSSLPort() {
      return this.sslPort;
   }

   public int getAdminPort() {
      return this.adminPort;
   }

   public String getProtocolName() {
      return this.protocolName;
   }

   public boolean isAdminOnly() {
      return this.isAdmin;
   }

   public void setProtocolName(String n) {
      this.protocolName = n;
   }

   public boolean isProtocolEnabled(int pNum) {
      return (this.protocolMask & 1 << pNum) != 0;
   }

   public void writeExternal(ObjectOutput oo, PeerInfo pi) throws IOException {
      oo.writeUTF(this.domainName);
      oo.writeUTF(this.clusterAddress);
      oo.writeUTF(this.channelName);
      oo.writeInt(this.port);
      oo.writeInt(this.sslPort);
      oo.writeInt(this.protocolMask);
      oo.writeInt(this.adminPort);
      oo.writeBoolean(this.isAdmin);
   }

   public void readExternal(ObjectInput oi, PeerInfo pi) throws IOException {
      this.domainName = oi.readUTF();
      this.clusterAddress = oi.readUTF();
      this.channelName = oi.readUTF();
      this.port = oi.readInt();
      this.sslPort = oi.readInt();
      this.protocolMask = oi.readInt();
      this.adminPort = oi.readInt();
      this.isAdmin = oi.readBoolean();
   }
}
