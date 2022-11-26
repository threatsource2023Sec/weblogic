package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

class GroupImpl implements Group {
   protected static final boolean DEBUG;
   protected final Set configurationSet;
   protected final TreeSet members = new TreeSet();
   protected final Set unreliableSet = new HashSet();
   protected Object handle;
   protected final String groupName;

   private static boolean initProperty(String s) {
      try {
         return Boolean.getBoolean(s);
      } catch (SecurityException var2) {
         return false;
      }
   }

   GroupImpl(String groupName) {
      this.configurationSet = new HashSet();
      this.groupName = groupName;
   }

   public GroupImpl(Set set, String groupName) {
      this.configurationSet = set;
      this.groupName = groupName;
   }

   public synchronized GroupMember[] getMembers() {
      GroupMember[] mbrs = new GroupMember[this.members.size()];
      this.members.toArray(mbrs);
      return mbrs;
   }

   public synchronized GroupMember[] getUnReliableMembers() {
      GroupMember[] mbrs = new GroupMember[this.unreliableSet.size()];
      this.unreliableSet.toArray(mbrs);
      return mbrs;
   }

   protected synchronized ServerConfigurationInformation[] getConfigurations() {
      ServerConfigurationInformation[] cfgs = new ServerConfigurationInformation[this.configurationSet.size()];
      this.configurationSet.toArray(cfgs);
      return cfgs;
   }

   public synchronized void addServer(ServerConfigurationInformation info) {
      this.addToConfigurationSet(info);
      this.startDiscoveryIfNeeded();
   }

   public synchronized void removeServer(String serverName) {
      GroupMember member = this.findGroupMember(serverName);
      if (member != null) {
         this.removeFromRunningSet(member);
      }

      this.removeFromConfigurationSet(serverName);
   }

   public synchronized void addToConfigurationSet(ServerConfigurationInformation info) {
      this.configurationSet.add(info);
   }

   public synchronized void removeFromConfigurationSet(String serverName) {
      Iterator itr = this.configurationSet.iterator();

      while(itr.hasNext()) {
         ServerConfigurationInformation info = (ServerConfigurationInformation)itr.next();
         if (info.getServerName().equals(serverName)) {
            itr.remove();
            break;
         }
      }

   }

   synchronized void addToRunningSet(GroupMember member) {
      this.members.add(member);
      this.configurationSet.remove(member.getConfiguration());
      if (DEBUG) {
         this.debug("addToRunningSet: added " + member);
      }

   }

   protected void debug(String s) {
      Environment.getLogService().debug(this.toString() + ": " + s);
   }

   public synchronized GroupMember removeFromRunningSet(GroupMember member) {
      this.members.remove(member);
      this.configurationSet.add(member.getConfiguration());
      if (DEBUG) {
         this.debug("removeFromRunningSet: removed " + member);
      }

      this.startDiscoveryIfNeeded();
      return this.members.size() == 0 ? null : (GroupMember)this.members.first();
   }

   public synchronized void removeFromUnReliableSet(GroupMember member) {
      this.unreliableSet.remove(member);
      this.configurationSet.add(member.getConfiguration());
      if (DEBUG) {
         this.debug("removeFromUnReliableSet: removed " + member);
      }

      this.startDiscoveryIfNeeded();
   }

   private synchronized boolean isLocalServerSeniormost() {
      GroupMember localMember = GroupManagerImpl.getInstance().getLocalMember();
      return localMember.equals(this.members.first());
   }

   protected synchronized void startDiscoveryIfNeeded() {
      if (this.handle == null) {
         if (this.members.size() < 1) {
            throw new AssertionError("local group should have atleast one member!");
         } else {
            if (this.members.size() == 1 || this.isLocalServerSeniormost()) {
               this.handle = this.startTimer();
            }

         }
      }
   }

   public void send(Message message) {
      GroupMember localMember = GroupManagerImpl.getInstance().getLocalMember();
      GroupMember[] activeMembers = this.getMembers();
      if (activeMembers.length == 0) {
         throw new AssertionError("LocalGroup should atleast have the local server!");
      } else {
         if (localMember.equals(activeMembers[0])) {
            if (DEBUG) {
               this.debug("we are the seniormost. Send message to group");
            }

            this.performLeaderActions(message);
         } else {
            GroupMember leader = activeMembers[0];
            this.sendToLeader(leader, message);
         }

      }
   }

   protected void sendToLeader(GroupMember leader, Message message) {
      GroupMember localMember = GroupManagerImpl.getInstance().getLocalMember();

      while(true) {
         try {
            if (localMember.equals(leader)) {
               this.performLeaderActions(message);
               return;
            }

            if (DEBUG) {
               this.debug("Send [" + message + "] to leader -> " + leader);
            }

            if (!message.getServerName().equals(leader.getConfiguration().getServerName())) {
               leader.send(message);
               if (leader.getLastArrivalTime() > 0L && System.currentTimeMillis() - leader.getLastArrivalTime() > (long)Environment.getPropertyService().getHeartbeatTimeoutMillis()) {
                  synchronized(this) {
                     this.unreliableSet.add(leader);
                  }

                  if (DEBUG) {
                     this.debug("added Leader " + leader + " to unreliableSet due to missed heartbeats");
                  }

                  throw new UnReliableServerException(leader.getConfiguration().getServerName() + " missed heartbeats !");
               }
            }

            if (DEBUG) {
               this.debug("send ok to " + leader);
            }

            return;
         } catch (IOException var7) {
            if (DEBUG) {
               this.debug("send failed to " + leader + " due to " + var7);
            }

            leader = this.handleIOExceptionRemoveFromRunningSet(leader, var7);
            if (leader == null) {
               return;
            }
         } catch (UnReliableServerException var8) {
            if (DEBUG) {
               this.debug("send failed to " + leader + " due to " + var8);
            }

            leader = this.removeFromRunningSet(leader);
            if (leader == null) {
               return;
            }

            leader.setLastMessageArrivalTime(System.currentTimeMillis() + (long)Environment.getPropertyService().getHeartbeatTimeoutMillis());
         }
      }
   }

   protected void performLeaderActions(Message message) {
      if (DEBUG) {
         this.debug("we are the seniormost. Send message to group");
      }

      GroupMember localMember = GroupManagerImpl.getInstance().getLocalMember();
      GroupMember[] activeMembers = this.getMembers();
      long currentTime = System.currentTimeMillis();

      for(int i = 0; i < activeMembers.length; ++i) {
         GroupMember member = activeMembers[i];
         if (!localMember.equals(member)) {
            if (member.getLastArrivalTime() > 0L && currentTime - member.getLastArrivalTime() > (long)Environment.getPropertyService().getHeartbeatTimeoutMillis()) {
               if (DEBUG) {
                  this.debug("will remove member " + member + " from runningSet due to missed heartbeats");
               }

               this.removeFromRunningSet(member);
            } else {
               try {
                  member.send(message);
                  if (DEBUG) {
                     this.debug(message + " send ok to " + member);
                  }
               } catch (IOException var11) {
                  currentTime = System.currentTimeMillis();
                  this.handleIOExceptionRemoveFromRunningSet(member, var11);
                  if (DEBUG) {
                     this.debug(message + " send failed to " + member);
                  }
               }
            }
         }
      }

      GroupMember[] unreliableMembers = this.getUnReliableMembers();

      for(int i = 0; i < unreliableMembers.length; ++i) {
         GroupMember member = unreliableMembers[i];

         try {
            member.send(message);
            if (DEBUG) {
               this.debug(message + " send ok to unreliable member " + member);
            }
         } catch (IOException var10) {
            this.handleIOExceptionRemoveFromUnreliableSet(member, var10);
            if (DEBUG) {
               this.debug(message + " send failed to " + member);
            }
         }
      }

      GroupManagerImpl.getInstance().sendRemoteGroups(message);
   }

   private GroupMember handleIOExceptionRemoveFromRunningSet(GroupMember member, IOException ioe) {
      if (ioe instanceof RejectConnectionException) {
         this.removeServer(member.getConfiguration().getServerName());
         return this.members.size() == 0 ? null : (GroupMember)this.members.first();
      } else {
         return this.removeFromRunningSet(member);
      }
   }

   private void handleIOExceptionRemoveFromUnreliableSet(GroupMember member, IOException ioe) {
      if (ioe instanceof RejectConnectionException) {
         this.unreliableSet.remove(member);
         this.removeServer(member.getConfiguration().getServerName());
      }

      this.removeFromUnReliableSet(member);
   }

   public boolean isLocal() {
      return true;
   }

   public synchronized void start() {
      if (this.handle == null) {
         this.handle = this.startTimer();
      }
   }

   public void stop() {
      this.stopTimerIfNeeded();
   }

   protected synchronized void stopTimerIfNeeded() {
      if (this.handle != null) {
         if (DEBUG) {
            this.debug("group monitor timer: stopping timer!");
         }

         Environment.stopTimer(this.handle);
         this.handle = null;
      }
   }

   private Object startTimer() {
      if (DEBUG) {
         this.debug("group monitor timer: starting timer!");
      }

      int period = Environment.getPropertyService().getDiscoveryPeriodMillis();
      return Environment.startTimer(new LocalGroupMonitor(), 0, period);
   }

   public synchronized ServerConfigurationInformation getConfigInformation(String serverName) {
      Iterator iter = this.configurationSet.iterator();

      ServerConfigurationInformation info;
      do {
         if (!iter.hasNext()) {
            iter = this.members.iterator();

            ServerConfigurationInformation info;
            do {
               GroupMember member;
               if (!iter.hasNext()) {
                  iter = this.unreliableSet.iterator();

                  do {
                     if (!iter.hasNext()) {
                        return null;
                     }

                     member = (GroupMember)iter.next();
                     info = member.getConfiguration();
                  } while(!info.getServerName().equals(serverName));

                  return info;
               }

               member = (GroupMember)iter.next();
               info = member.getConfiguration();
            } while(!info.getServerName().equals(serverName));

            return info;
         }

         info = (ServerConfigurationInformation)iter.next();
      } while(!info.getServerName().equals(serverName));

      return info;
   }

   public synchronized GroupMember findOrCreateGroupMember(ServerConfigurationInformation info, long serverStartTime) {
      Iterator iter = this.members.iterator();

      GroupMember member;
      do {
         if (!iter.hasNext()) {
            GroupMember member = new GroupMemberImpl(info, serverStartTime);
            if (this.unreliableSet.contains(member)) {
               this.removeFromUnReliableSet(member);
            }

            this.addToRunningSet(member);
            return member;
         }

         member = (GroupMember)iter.next();
      } while(!member.getConfiguration().equals(info));

      return member;
   }

   public synchronized GroupMember findGroupMember(String serverName) {
      Iterator iter = this.members.iterator();

      GroupMember member;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         member = (GroupMember)iter.next();
      } while(!member.getConfiguration().getServerName().equals(serverName));

      return member;
   }

   private boolean isMessageFromRemoteGroup(Connection connection) {
      return this.getConfigInformation(connection.getConfiguration().getServerName()) == null;
   }

   public void forward(Message message, Connection connection) {
      GroupMember localMember = GroupManagerImpl.getInstance().getLocalMember();
      GroupMember[] activeMembers = this.getMembers();
      if (activeMembers.length == 0) {
         throw new AssertionError("LocalGroup should atleast have the local server!");
      } else {
         if (localMember.equals(activeMembers[0]) || this.isMessageFromRemoteGroup(connection)) {
            if (DEBUG) {
               this.debug("we are the seniormost. Send message to group. connection.getConfiguration()=" + connection.getConfiguration().getServerName());
            }

            for(int i = 0; i < activeMembers.length; ++i) {
               GroupMember member = activeMembers[i];
               if (!localMember.equals(member) && !member.getConfiguration().equals(connection.getConfiguration())) {
                  try {
                     member.send(message);
                     if (DEBUG) {
                        this.debug(message + " forward ok to " + member);
                     }
                  } catch (IOException var10) {
                     this.handleIOExceptionRemoveFromRunningSet(member, var10);
                     if (DEBUG) {
                        this.debug(message + " forward failed to " + member);
                     }
                  }
               }
            }

            GroupMember[] unreliableMembers = this.getUnReliableMembers();

            for(int i = 0; i < unreliableMembers.length; ++i) {
               GroupMember member = unreliableMembers[i];
               if (!localMember.equals(member) && !member.getConfiguration().equals(connection.getConfiguration())) {
                  try {
                     member.send(message);
                     if (DEBUG) {
                        this.debug(message + " forward ok to unreliable member " + member);
                     }
                  } catch (IOException var9) {
                     this.handleIOExceptionRemoveFromUnreliableSet(member, var9);
                     if (DEBUG) {
                        this.debug(message + " send failed to " + member);
                     }
                  }
               }
            }
         }

         boolean isLocalGroupMessage = this.getConfigInformation(message.getServerName()) != null;
         boolean isLocalGroupConnection = this.getConfigInformation(connection.getConfiguration().getServerName()) != null;
         if (localMember.equals(activeMembers[0]) && isLocalGroupMessage && isLocalGroupConnection) {
            GroupManagerImpl.getInstance().sendRemoteGroups(message);
         }

      }
   }

   public String toString() {
      return this.groupName + " [" + this.members + "] [" + this.configurationSet + "]";
   }

   static {
      DEBUG = Environment.DEBUG;
   }

   private static class UnReliableServerException extends Exception {
      UnReliableServerException(String reason) {
         super(reason);
      }
   }

   private class LocalGroupMonitor implements Runnable {
      private int discoveryAttempts;
      private boolean remoteGroupMonitoring;

      private LocalGroupMonitor() {
      }

      public void run() {
         if (GroupImpl.this.isLocalServerSeniormost()) {
            if (this.discoveryAttempts > 2 && !this.remoteGroupMonitoring) {
               GroupManagerImpl.getInstance().startRemoteGroups();
               this.remoteGroupMonitoring = true;
               if (GroupImpl.DEBUG) {
                  GroupImpl.this.debug("starting remote group discovery ...");
               }
            }

            ServerConfigurationInformation[] infos = GroupImpl.this.getConfigurations();
            if (infos == null || infos.length == 0) {
               return;
            }

            for(int i = 0; i < infos.length; ++i) {
               PingRoutine pinger = Environment.getPingRoutine();
               long startTime = pinger.ping(infos[i]);
               if (startTime > 0L) {
                  if (GroupImpl.DEBUG) {
                     GroupImpl.this.debug("discovered " + infos[i]);
                  }

                  GroupMember member = new GroupMemberImpl(infos[i], startTime);
                  member.setLastMessageArrivalTime(System.currentTimeMillis());
                  synchronized(GroupImpl.this) {
                     if (!GroupImpl.this.unreliableSet.contains(member)) {
                        GroupImpl.this.addToRunningSet(member);
                     }
                  }
               }
            }

            ++this.discoveryAttempts;
         } else {
            if (GroupImpl.DEBUG) {
               GroupImpl.this.debug("we are not senior anymore ! senior is " + GroupImpl.this.getMembers()[0]);
            }

            GroupImpl.this.stopTimerIfNeeded();
            if (this.remoteGroupMonitoring) {
               if (GroupImpl.DEBUG) {
                  GroupImpl.this.debug("stopping remote group discovery ...");
               }

               Environment.getGroupManager().stopRemoteGroups();
               this.remoteGroupMonitoring = false;
            }
         }

      }

      // $FF: synthetic method
      LocalGroupMonitor(Object x1) {
         this();
      }
   }
}
