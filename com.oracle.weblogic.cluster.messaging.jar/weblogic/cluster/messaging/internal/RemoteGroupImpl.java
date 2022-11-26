package weblogic.cluster.messaging.internal;

import java.util.Set;

final class RemoteGroupImpl extends GroupImpl {
   public RemoteGroupImpl(String groupName) {
      super(groupName);
   }

   public RemoteGroupImpl(Set set, String groupName) {
      super(set, groupName);
   }

   public synchronized void start() {
      if (this.handle == null) {
         if (DEBUG) {
            this.debug("group monitor timer: starting remote group timer now.");
         }

         this.handle = Environment.startTimer(new RemoteGroupMonitor(), 0, Environment.getPropertyService().getDiscoveryPeriodMillis());
      }
   }

   public void send(Message message) {
      GroupMember[] activeMembers = this.getMembers();
      if (activeMembers.length == 0) {
         if (DEBUG) {
            this.debug("no server in remote group is alive!");
         }

         this.startDiscoveryIfNeeded();
      } else {
         GroupMember leader = activeMembers[0];
         this.sendToLeader(leader, message);
      }

   }

   protected void performLeaderActions(Message message) {
      throw new AssertionError("local server can never perform leader actions on a remote group !");
   }

   public void forward(Message message, Connection connection) {
      throw new AssertionError("forward cannot be called on remote groups !");
   }

   protected synchronized void startDiscoveryIfNeeded() {
      if (this.handle == null) {
         if (this.members.size() == 0) {
            if (DEBUG) {
               this.debug("group monitor timer: starting remote group timer as needed.");
            }

            this.handle = Environment.startTimer(new RemoteGroupMonitor(), 0, Environment.getPropertyService().getDiscoveryPeriodMillis());
         }

      }
   }

   public boolean isLocal() {
      return false;
   }

   private class RemoteGroupMonitor implements Runnable {
      private RemoteGroupMonitor() {
      }

      public void run() {
         ServerConfigurationInformation[] infos = RemoteGroupImpl.this.getConfigurations();
         if (infos != null && infos.length != 0) {
            for(int i = 0; i < infos.length; ++i) {
               PingRoutine pinger = Environment.getPingRoutine();
               long startTime = pinger.ping(infos[i]);
               if (startTime > 0L) {
                  if (GroupImpl.DEBUG) {
                     RemoteGroupImpl.this.debug("discovered " + infos[i]);
                  }

                  GroupMember member = new GroupMemberImpl(infos[i], startTime);
                  member.setLastMessageArrivalTime(System.currentTimeMillis());
                  RemoteGroupImpl.this.addToRunningSet(member);
               }
            }

            if (RemoteGroupImpl.this.members.size() > 0) {
               RemoteGroupImpl.this.stopTimerIfNeeded();
            }

         }
      }

      // $FF: synthetic method
      RemoteGroupMonitor(Object x1) {
         this();
      }
   }
}
