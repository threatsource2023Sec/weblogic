package weblogic.cluster.messaging.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.protocol.ServerIdentity;
import weblogic.rjvm.JVMID;

public class SuspectedMemberInfoImpl implements SuspectedMemberInfo {
   private ClusterMemberInfo clusterMemberInfo;
   private ServerInformation serverInfo;
   private boolean hasVoidedSingletonServices = false;
   private long suspectedStartTime;

   SuspectedMemberInfoImpl(ClusterMemberInfo memberInfo) {
      this.clusterMemberInfo = memberInfo;
      this.serverInfo = new ServerInformationImpl(this.clusterMemberInfo);
   }

   public String getServerName() {
      return this.clusterMemberInfo.serverName();
   }

   public String getMachineName() {
      return this.clusterMemberInfo.machineName();
   }

   public ServerInformation getServerInformation() {
      return this.serverInfo;
   }

   public ServerConfigurationInformation getServerConfigurationInformation() {
      try {
         return new ServerConfigurationInformationImpl(InetAddress.getByName(this.clusterMemberInfo.hostAddress()), ((JVMID)this.clusterMemberInfo.identity()).getPublicPort(), this.clusterMemberInfo.serverName(), System.currentTimeMillis(), this.clusterMemberInfo.clusterName());
      } catch (UnknownHostException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public int getPort() {
      return ((JVMID)this.getServerIdentity()).getPublicPort();
   }

   public ServerIdentity getServerIdentity() {
      return this.clusterMemberInfo.identity();
   }

   public boolean hasVoidedSingletonServices() {
      return this.hasVoidedSingletonServices;
   }

   public void voidedSingletonServices() {
      this.hasVoidedSingletonServices = true;
   }

   public void setSuspectedStartTime(long startTime) {
      this.suspectedStartTime = startTime;
   }

   public long getSuspectedStartTime() {
      return this.suspectedStartTime;
   }
}
