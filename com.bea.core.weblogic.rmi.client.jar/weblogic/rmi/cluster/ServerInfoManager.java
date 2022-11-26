package weblogic.rmi.cluster;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.spi.HostID;

public final class ServerInfoManager {
   private static final Object LOCK = new Object() {
   };
   private Map serverNameMap;
   private Map serverIdMap;

   public static ServerInfoManager theOne() {
      return ServerInfoManager.SingletonMaker.SINGLETON;
   }

   private ServerInfoManager() {
      this.serverNameMap = null;
      this.serverIdMap = null;
      this.serverNameMap = new HashMap();
      this.serverIdMap = new HashMap();
   }

   public ServerInfo getServerInfo(String name) {
      return (ServerInfo)this.serverNameMap.get(name);
   }

   public ServerInfo getServerInfo(HostID id) {
      return (ServerInfo)this.serverIdMap.get(id);
   }

   public ServerInfo getServerInfo(Object remoteObj) {
      return this.getServerInfo(RemoteHelper.getHostID(remoteObj));
   }

   public ServerInfo getServerInfo(RemoteReference ror) {
      return this.getServerInfo(ror.getHostID());
   }

   public void addServer(String name, HostID id, int loadWeight) {
      ServerInfo newServer = new ServerInfo(name, id, loadWeight);
      synchronized(LOCK) {
         this.serverNameMap.put(name, newServer);
         this.serverIdMap.put(id, newServer);
      }
   }

   public void removeServer(HostID id) {
      synchronized(LOCK) {
         this.serverNameMap.remove(this.getServerInfo(id).getName());
         this.serverIdMap.remove(id);
      }
   }

   public Object writeLocalInfoUpdate() throws IOException {
      return new Object[]{this.getServerInfo((HostID)LocalServerIdentity.getIdentity())};
   }

   Object writeUpdate() {
      return this.serverNameMap == null ? null : this.serverNameMap.values().toArray();
   }

   public void readUpdate(Object in) throws IOException {
      Object[] infoList = (Object[])((Object[])in);
      if (infoList != null) {
         for(int i = 0; i < infoList.length; ++i) {
            this.updateServerInfo((ServerInfo)infoList[i]);
         }
      }

   }

   private void updateServerInfo(ServerInfo server) throws IOException {
      if (server == null) {
         throw new IOException("Reference to server no longer exists.\n Possible reasons for failure include having servers with duplicate names running on a cluster.  Please check your configuration for this error.");
      } else {
         String name = server.getName();
         HostID id = server.getID();
         synchronized(LOCK) {
            ServerInfo old = (ServerInfo)this.serverNameMap.get(name);
            if (old != null && old.getID() != id) {
               if (old.getID().isLocal() && !id.isLocal()) {
                  return;
               }

               this.serverIdMap.remove(old.getID());
            }

            this.serverNameMap.put(name, server);
            this.serverIdMap.put(server.getID(), server);
         }
      }
   }

   ServerInfo[] getServerInfos() {
      synchronized(LOCK) {
         Collection c = this.serverIdMap.values();
         return (ServerInfo[])((ServerInfo[])c.toArray(new ServerInfo[c.size()]));
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("Server Info\n");
      buf.append("-----------\n");
      Iterator it = this.serverIdMap.values().iterator();

      while(it.hasNext()) {
         buf.append(it.next().toString() + "\n");
      }

      return buf.toString();
   }

   // $FF: synthetic method
   ServerInfoManager(Object x0) {
      this();
   }

   private static final class SingletonMaker {
      private static final ServerInfoManager SINGLETON = new ServerInfoManager();
   }
}
