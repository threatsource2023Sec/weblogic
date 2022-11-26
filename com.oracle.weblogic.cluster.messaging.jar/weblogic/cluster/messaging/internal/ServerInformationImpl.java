package weblogic.cluster.messaging.internal;

import weblogic.cluster.ClusterMemberInfo;

public class ServerInformationImpl implements ServerInformation {
   static final long serialVersionUID = 5448440092079264186L;
   private final long joinTime;
   private final String serverName;

   public ServerInformationImpl(ClusterMemberInfo info) {
      this.joinTime = info.joinTime();
      this.serverName = info.serverName();
   }

   public ServerInformationImpl(String serverName) {
      this.joinTime = 0L;
      this.serverName = serverName;
   }

   public String getServerName() {
      return this.serverName;
   }

   public long getStartupTime() {
      return this.joinTime;
   }

   public String toString() {
      return "[" + this.serverName + "," + this.joinTime + "]";
   }

   public int compareTo(Object other) {
      assert other instanceof ServerInformation;

      ServerInformation otherInfo = (ServerInformation)other;
      long diff = this.joinTime - otherInfo.getStartupTime();
      if (diff < 0L) {
         return -1;
      } else {
         return diff > 0L ? 1 : this.serverName.compareTo(otherInfo.getServerName());
      }
   }

   public boolean equals(Object other) {
      if (!(other instanceof ServerInformation)) {
         return false;
      } else {
         ServerInformation otherInfo = (ServerInformation)other;
         return this.serverName.equals(otherInfo.getServerName()) && this.joinTime == otherInfo.getStartupTime();
      }
   }

   public int hashCode() {
      return (int)((long)this.serverName.hashCode() ^ this.joinTime);
   }
}
