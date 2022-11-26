package weblogic.cluster.leasing.databaseless;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.work.WorkManagerFactory;

public final class ClusterGroupView implements Serializable {
   private ServerInformation leaderInformation;
   private TreeSet members;
   private transient GroupViewListener listener;
   private long versionNumber = 0L;
   private static final DebugCategory debugClusterGroupView = Debug.getCategory("weblogic.cluster.leasing.ClusterGroupView");
   private static final boolean DEBUG = debugEnabled();
   private static final long serialVersionUID = 8079240922100673698L;

   ClusterGroupView(ServerInformation leaderInformation, ServerInformation[] members) {
      this.leaderInformation = leaderInformation;
      this.members = new TreeSet();
      this.members.add(leaderInformation);
      if (members != null && members.length > 0) {
         this.members.addAll(Arrays.asList(members));
      }

   }

   ClusterGroupView(TreeSet members) {
      assert members.size() > 0;

      this.members = members;
      this.leaderInformation = this.getSeniorMost();
   }

   synchronized ServerInformation getLeaderInformation() {
      if (this.leaderInformation == null) {
         this.leaderInformation = this.getSeniorMost();
      }

      return this.leaderInformation;
   }

   Set getMembers() {
      return this.members;
   }

   public String toString() {
      String membersInfo;
      synchronized(this) {
         membersInfo = this.members.toString();
      }

      return "[GroupView with leader " + this.leaderInformation + " version " + this.versionNumber + " and members " + membersInfo + "]";
   }

   public synchronized boolean isSeniorMost(ServerInformation localServerInformation) {
      return localServerInformation.equals(this.getSeniorMost());
   }

   public synchronized ServerInformation getSeniorMost() {
      assert this.members != null;

      ServerInformation server = !this.members.isEmpty() ? (ServerInformation)this.members.first() : null;
      return server;
   }

   public synchronized void removeLeader() {
      this.removeMember(this.leaderInformation);
      this.leaderInformation = this.getSeniorMost();
   }

   public synchronized ServerInformation[] getRemoteMembers(ServerInformation local) {
      ArrayList list = new ArrayList();
      Iterator iter = this.members.iterator();

      while(iter.hasNext()) {
         ServerInformation info = (ServerInformation)iter.next();
         if (!local.equals(info)) {
            list.add(info);
         }
      }

      ServerInformation[] infos = new ServerInformation[list.size()];
      return (ServerInformation[])((ServerInformation[])list.toArray(infos));
   }

   public void addMember(final ServerInformation server) {
      synchronized(this) {
         this.members.add(server);
         if (DEBUG) {
            debug("Added Member - " + server + " to group " + this);
         }
      }

      if (this.listener != null) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               ClusterGroupView.this.listener.memberAdded(server);
            }
         });
      }

   }

   public void removeMember(final ServerInformation server) {
      synchronized(this) {
         if (!this.members.remove(server)) {
            return;
         }

         if (DEBUG) {
            debug("Removed Member - " + server + " from group " + this);
         }

         if (this.leaderInformation == null || this.leaderInformation.equals(server)) {
            this.leaderInformation = this.getSeniorMost();
         }
      }

      if (this.listener != null) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               ClusterGroupView.this.listener.memberRemoved(server);
            }
         });
      }

   }

   void setGroupViewListener(GroupViewListener listener) {
      this.listener = listener;
   }

   public synchronized long getVersionNumber() {
      return this.versionNumber;
   }

   synchronized long incrementVersionNumber() {
      return ++this.versionNumber;
   }

   public void processStateDump(ClusterGroupView groupView) {
      final List membersToBeRemoved = new ArrayList();
      synchronized(this) {
         if (groupView == null || this.versionNumber >= groupView.getVersionNumber()) {
            return;
         }

         TreeSet newMembers = (TreeSet)groupView.getMembers();
         Iterator iter = this.members.iterator();

         while(true) {
            if (!iter.hasNext()) {
               this.members = newMembers;
               this.versionNumber = groupView.getVersionNumber();
               break;
            }

            ServerInformation server = (ServerInformation)iter.next();
            if (!newMembers.contains(server)) {
               membersToBeRemoved.add(server);
            }
         }
      }

      if (this.listener != null) {
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               Iterator var1 = membersToBeRemoved.iterator();

               while(var1.hasNext()) {
                  ServerInformation server = (ServerInformation)var1.next();
                  ClusterGroupView.this.listener.memberRemoved(server);
               }

            }
         });
      }

   }

   synchronized ServerInformation getServerInformation(String serverName) {
      Iterator iter = this.members.iterator();

      ServerInformation serverInfo;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         serverInfo = (ServerInformation)iter.next();
      } while(!serverInfo.getServerName().equals(serverName));

      return serverInfo;
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[ClusterGroupView] " + s);
   }

   private static boolean debugEnabled() {
      return debugClusterGroupView.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }
}
