package weblogic.cluster.messaging.internal.server;

import java.util.ArrayList;
import weblogic.cluster.messaging.internal.Environment;
import weblogic.cluster.messaging.internal.Group;
import weblogic.cluster.messaging.internal.GroupMember;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.UnicastMessagingRuntimeMBean;

public class UnicastMessagingRuntimeMBeanImpl extends RuntimeMBeanDelegate implements UnicastMessagingRuntimeMBean {
   private static UnicastMessagingRuntimeMBeanImpl THE_ONE;

   private static void setTheOne(UnicastMessagingRuntimeMBeanImpl oneOnly) {
      THE_ONE = oneOnly;
   }

   UnicastMessagingRuntimeMBeanImpl(RuntimeMBean parent) throws ManagementException {
      super("UnicastMessagingRuntime", parent, true);
      setTheOne(this);
   }

   public static UnicastMessagingRuntimeMBeanImpl getInstance() {
      return THE_ONE;
   }

   public int getRemoteGroupsDiscoveredCount() {
      return this.getDiscoveredGroupLeaders().length - 1;
   }

   public String getLocalGroupLeaderName() {
      Group group = Environment.getGroupManager().getLocalGroup();
      GroupMember[] members = group.getMembers();
      return members[0].getConfiguration().getServerName();
   }

   public int getTotalGroupsCount() {
      String[] groupLeaders = this.getDiscoveredGroupLeaders();
      return groupLeaders.length;
   }

   public String[] getDiscoveredGroupLeaders() {
      Group[] groups = Environment.getGroupManager().getRemoteGroups();
      ArrayList list = new ArrayList();
      list.add(this.getLocalGroupLeaderName());

      for(int i = 0; i < groups.length; ++i) {
         GroupMember[] members = groups[i].getMembers();
         if (members != null && members.length > 0) {
            list.add(members[0].getConfiguration().getServerName());
         }
      }

      String[] names = new String[list.size()];
      list.toArray(names);
      return names;
   }

   public String getGroups() {
      StringBuffer buf = new StringBuffer();
      Group group = Environment.getGroupManager().getLocalGroup();
      printGroup(group, buf);
      Group[] groups = Environment.getGroupManager().getRemoteGroups();

      for(int i = 0; i < groups.length; ++i) {
         printGroup(groups[i], buf);
      }

      return buf.toString();
   }

   private static void printGroup(Group group, StringBuffer list) {
      GroupMember[] members = group.getMembers();
      if (members.length != 0) {
         list.append("{");

         for(int i = 0; i < members.length; ++i) {
            list.append(members[i].getConfiguration().getServerName());
            if (i < members.length - 1) {
               list.append(",");
            }
         }

         list.append("}");
      }
   }
}
