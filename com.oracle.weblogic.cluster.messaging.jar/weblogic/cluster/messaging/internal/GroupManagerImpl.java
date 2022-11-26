package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class GroupManagerImpl implements GroupManager {
   private static final boolean DEBUG;
   private static final int MAX_GROUP_NUM;
   private static final int DEFAULT_MAX_GROUP_NUM = 10;
   private final GroupMemberImpl localMember;
   private final ServerConfigurationInformation serverConfigForWire;
   private final GroupImpl localGroup;
   private final int localGroupNo;
   private final Set remoteGroups;
   private final List groups;
   private MessageListener listener;

   public static GroupManager getInstance() {
      return GroupManagerImpl.Factory.THE_ONE;
   }

   private GroupManagerImpl() {
      this.remoteGroups = new HashSet();
      this.groups = new ArrayList(MAX_GROUP_NUM);
      this.localMember = new GroupMemberImpl(Environment.getConfiguredServersMonitor().getLocalServerConfiguration(), 1L);
      ServerConfigurationInformation localConfig = this.localMember.getConfiguration();
      InetAddress lcAddress = null;

      try {
         lcAddress = localConfig.getAddress();
      } catch (IOException var6) {
      }

      this.localGroupNo = mapServerNameToGroupNo(localConfig);
      this.serverConfigForWire = new ServerConfigurationInformationImpl(lcAddress, localConfig.getPort(), localConfig.getServerName(), System.currentTimeMillis(), localConfig.isUsingSSL(), localConfig.getAddressHostname(), localConfig.getClusterName());
      this.initializeGroups(Environment.getConfiguredServersMonitor().getConfiguredServers());
      this.localGroup = (GroupImpl)this.groups.get(this.localGroupNo);
      this.localGroup.addToRunningSet(this.localMember);
      if (DEBUG) {
         this.debug("initialized all groups with MAX_GROUP_NUM: " + MAX_GROUP_NUM);
         int i = 0;

         for(Iterator var4 = this.groups.iterator(); var4.hasNext(); ++i) {
            Group g = (Group)var4.next();
            this.debug("    [" + i + "]: " + g);
         }

         this.debug("localConfig: " + localConfig);
         this.debug("serverConfigForWire: " + this.serverConfigForWire);
      }

      this.localGroup.start();
   }

   public ServerConfigurationInformation getServerConfigForWire() {
      return this.serverConfigForWire;
   }

   public void startRemoteGroups() {
      Iterator iter = this.remoteGroups.iterator();

      while(iter.hasNext()) {
         Group group = (Group)iter.next();
         group.start();
      }

   }

   public void stopRemoteGroups() {
      Iterator iter = this.remoteGroups.iterator();

      while(iter.hasNext()) {
         Group group = (Group)iter.next();
         group.stop();
      }

   }

   public void sendRemoteGroups(final Message message) {
      Environment.executeForwardMessage(new Runnable() {
         public void run() {
            Iterator iter = GroupManagerImpl.this.remoteGroups.iterator();

            while(iter.hasNext()) {
               Group group = (Group)iter.next();
               group.send(message);
            }

         }
      });
   }

   public GroupMember getLocalMember() {
      return this.localMember;
   }

   public Group getLocalGroup() {
      return this.localGroup;
   }

   public synchronized Group[] getRemoteGroups() {
      Group[] groups = new Group[this.remoteGroups.size()];
      this.remoteGroups.toArray(groups);
      return groups;
   }

   private void initializeGroups(SortedSet configuredServers) {
      for(int i = 0; i < MAX_GROUP_NUM; ++i) {
         if (i == this.localGroupNo) {
            this.groups.add(new GroupImpl("LocalGroup[" + i + "]"));
         } else {
            GroupImpl group = new RemoteGroupImpl("RemoteGroup[" + i + "]");
            this.groups.add(group);
            this.remoteGroups.add(group);
         }
      }

      Iterator iter = configuredServers.iterator();

      while(iter.hasNext()) {
         ServerConfigurationInformation info = (ServerConfigurationInformation)iter.next();
         int groupNo = mapServerNameToGroupNo(info);
         ((GroupImpl)this.groups.get(groupNo)).addToConfigurationSet(info);
      }

   }

   private static int mapServerNameToGroupNo(ServerConfigurationInformation info) {
      return mapServerNameToGroupNo(info, MAX_GROUP_NUM);
   }

   static int mapServerNameToGroupNo(ServerConfigurationInformation info, int maxGroupNum) {
      return mapServerNameToGroupNo(info.getNameComponents(), maxGroupNum);
   }

   private static int mapServerNameToGroupNo(String serverName) {
      return mapServerNameToGroupNo(ServerNameComponents.parseServername(serverName), MAX_GROUP_NUM);
   }

   static int mapServerNameToGroupNo(ServerNameComponents serverNameComponent, int maxGroupNum) {
      return serverNameComponent.getSeqNo() % maxGroupNum;
   }

   private void debug(String s) {
      Environment.getLogService().debug("[GroupManager] " + s);
   }

   public void handleMessage(Message message, Connection connection) {
      if (this.localMember.getConfiguration().equals(message.getSenderConfiguration())) {
         if (DEBUG) {
            this.debug("squelching local message:" + message);
         }

      } else {
         if (DEBUG) {
            this.debug("received for dispatch:" + message + " from " + connection);
         }

         GroupMember member = this.findOrCreateGroupMember(message);
         if (member != null) {
            member.setLastMessageArrivalTime(System.currentTimeMillis());
            if (DEBUG) {
               this.debug("updated LAT for " + member.getConfiguration().getServerName());
            }
         }

         GroupMember connMember = this.findOrCreateGroupMember(connection);
         if (connMember != null) {
            connMember.setLastMessageArrivalTime(System.currentTimeMillis());
            if (DEBUG) {
               this.debug("updated LAT for " + connMember.getConfiguration().getServerName());
            }

            connMember.addConnection(connection);
         }

         this.localMember.receive(message, connection);
         this.listener.onMessage(message);
      }
   }

   public void setMessageListener(MessageListener listener) {
      this.listener = listener;
   }

   public void addToGroup(ServerConfigurationInformation serverConfigurationInformation) {
      int groupNo = mapServerNameToGroupNo(serverConfigurationInformation);
      Group group = (Group)this.groups.get(groupNo);
      group.addServer(serverConfigurationInformation);
      if (DEBUG) {
         this.debug("GroupManagerImpl.addToConfigurationSet group: " + group);
      }

   }

   public void removeFromGroup(String serverName) {
      int groupNo = mapServerNameToGroupNo(serverName);
      Group group = (Group)this.groups.get(groupNo);
      group.removeServer(serverName);
      if (DEBUG) {
         this.debug("GroupManagerImpl.removeFromConfigurationSet group: " + group);
      }

   }

   private GroupMember findOrCreateGroupMember(Message message) {
      String serverName = message.getServerName();
      ServerConfigurationInformation senderInfo = message.getSenderConfiguration();
      if (senderInfo == null) {
         throw new RuntimeException("message's SenderConfiguration is null for " + serverName);
      } else {
         int groupNo = mapServerNameToGroupNo(senderInfo);
         Group group = (Group)this.groups.get(groupNo);
         ServerConfigurationInformation info = group.getConfigInformation(serverName);
         if (info == null) {
            this.debug("cannot find ServerConfigurationInformation for " + serverName + " in group[" + groupNo + "] " + group + "; use message's SenderConfiguration " + senderInfo);
            info = senderInfo;
         }

         return ((GroupImpl)this.groups.get(groupNo)).findOrCreateGroupMember(info, message.getServerStartTime());
      }
   }

   private GroupMember findOrCreateGroupMember(Connection connection) {
      ServerConfigurationInformation connInfo = connection.getConfiguration();
      String serverName = connInfo.getServerName();
      int groupNo = mapServerNameToGroupNo(connInfo);
      Group group = (Group)this.groups.get(groupNo);
      ServerConfigurationInformation info = group.getConfigInformation(serverName);
      if (info == null) {
         info = connection.getConfiguration();
         this.debug("cannot find ServerConfigurationInformation for " + serverName + " in group[" + groupNo + "] " + group + "; use connection's info " + info);
         info = connInfo;
      }

      return group.findOrCreateGroupMember(info, connection.getConfiguration().getCreationTime());
   }

   // $FF: synthetic method
   GroupManagerImpl(Object x0) {
      this();
   }

   static {
      DEBUG = Environment.DEBUG;
      int max = Integer.getInteger("weblogic.unicast.maxGroupNum", 10);
      if (max < 1) {
         max = 10;
      }

      MAX_GROUP_NUM = max;
   }

   private static final class Factory {
      static final GroupManager THE_ONE = new GroupManagerImpl();
   }
}
