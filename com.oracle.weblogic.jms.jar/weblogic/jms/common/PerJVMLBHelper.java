package weblogic.jms.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.jms.forwarder.dd.DDLBTable;
import weblogic.jms.forwarder.dd.DDMemberInfo;
import weblogic.utils.StackTraceUtilsClient;

public final class PerJVMLBHelper {
   private DDLBTable ddLBTable;
   private Map candidateMap;

   public PerJVMLBHelper(DDLBTable ddLBTable) {
      this.ddLBTable = ddLBTable;
      this.candidateMap = new HashMap();
   }

   protected Map createPerJVMLBDDMemberList(PerJVMLBAwareDDMember[] members) {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("createPerJVMLBDDMemberList members = " + members);

         for(int i = 0; i < members.length; ++i) {
            JMSDebug.JMSCommon.debug("createPerJVMLBDDMemberList member(" + i + " = " + members[i]);
         }
      }

      boolean clusterTargeted = false;
      Iterator var3 = this.candidateMap.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         if (entry.getValue() != null && !this.isInFailedList((PerJVMLBAwareDDMember)entry.getValue())) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("createPerJVMLBDDMemberList clear member  = " + ((PerJVMLBAwareDDMember)entry.getValue()).getMemberName() + " from the candidate map because it is NOT in the failed list");
            }

            entry.setValue((Object)null);
         }
      }

      PerJVMLBAwareDDMember member;
      int i;
      String serverName;
      for(i = 0; i < members.length; ++i) {
         serverName = members[i].getDispatcherName();
         member = members[i];
         if (JMSDebug.JMSCommon.isDebugEnabled() && serverName == null) {
            JMSDebug.JMSCommon.debug(StackTraceUtilsClient.throwable2StackTrace(new Exception("!!! GOT NULL DISPATCHERID in member" + member.getMemberName())));
         }

         if (member.isOnPreferredServer() && (this.candidateMap.get(serverName) == null || ((PerJVMLBAwareDDMember)this.candidateMap.get(serverName)).getMemberNameForSort().compareTo(member.getMemberNameForSort()) > 0)) {
            this.candidateMap.put(serverName, member);
         }

         if (member.isPossiblyClusterTargeted()) {
            clusterTargeted = true;
         }
      }

      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("createPerJVMLBDDMemberList clusterTargeted = " + clusterTargeted);
      }

      if (!clusterTargeted) {
         for(i = 0; i < members.length; ++i) {
            serverName = members[i].getDispatcherName();
            member = members[i];
            if (!member.isOnPreferredServer() && serverName != null && (this.candidateMap.get(serverName) == null || !((PerJVMLBAwareDDMember)this.candidateMap.get(serverName)).isOnPreferredServer() && ((PerJVMLBAwareDDMember)this.candidateMap.get(serverName)).getMemberNameForSort().compareTo(member.getMemberNameForSort()) > 0)) {
               this.candidateMap.put(serverName, member);
            }
         }
      }

      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("createPerJVMLBDDMemberList candidateMap = " + this.candidateMap);
      }

      Map newMap = new HashMap();
      Iterator itr = this.candidateMap.values().iterator();

      while(itr.hasNext()) {
         member = (PerJVMLBAwareDDMember)itr.next();
         if (member != null && member.getDispatcherName() != null && !this.isInFailedList(member)) {
            newMap.put(member.getDispatcherName(), member);
         }
      }

      return newMap;
   }

   private boolean isInFailedList(PerJVMLBAwareDDMember member) {
      List failedInfos = this.ddLBTable.getFailedDDMemberInfos();

      for(int i = 0; i < failedInfos.size(); ++i) {
         PerJVMLBAwareDDMember failedMember = (PerJVMLBAwareDDMember)failedInfos.get(i);
         if (member.getMemberName().equals(failedMember.getMemberName())) {
            return true;
         }
      }

      return false;
   }

   public DDMemberInfo[] createPerJVMLBDDMemberInfos(DDMemberInfo[] dests) {
      Map newMap = this.createPerJVMLBDDMemberList((PerJVMLBAwareDDMember[])dests);
      return (DDMemberInfo[])((DDMemberInfo[])newMap.values().toArray(new DDMemberInfo[newMap.size()]));
   }
}
