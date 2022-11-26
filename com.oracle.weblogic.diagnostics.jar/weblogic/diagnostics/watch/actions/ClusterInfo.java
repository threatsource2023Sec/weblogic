package weblogic.diagnostics.watch.actions;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.elasticity.ServerStateInspector;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.collections.ConcurrentHashMap;

public class ClusterInfo {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugWatchScalingActions");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String clusterName;
   private String serverNamePrefix;
   private int maxServers;
   private Map membersMap = new ConcurrentHashMap();

   private ClusterInfo() {
   }

   private ClusterInfo(String clusterName) {
      this.clusterName = clusterName;
   }

   public String getClusterName() {
      return this.clusterName;
   }

   private void copyGroups(ArrayList groupsToCopy, ArrayList copy) {
      copy.clear();
      Iterator var3 = groupsToCopy.iterator();

      while(var3.hasNext()) {
         ArrayList group = (ArrayList)var3.next();
         ArrayList copiedGroup = new ArrayList();
         copiedGroup.addAll(group);
         copy.add(copiedGroup);
      }

   }

   public String getServerNamePrefix() {
      return this.serverNamePrefix;
   }

   public void setServerNamePrefix(String serverNamePrefix) {
      this.serverNamePrefix = serverNamePrefix;
   }

   public int getMaxServers() {
      return this.maxServers;
   }

   public void setMaxServers(int maxServers) {
      this.maxServers = maxServers;
   }

   public synchronized void addMember(ClusterMember member) {
      this.membersMap.put(member.getName(), member);
   }

   public synchronized void removeMember(ClusterMember member) {
      this.membersMap.remove(member.getName());
   }

   public ClusterMember getMember(String name) {
      return (ClusterMember)this.membersMap.get(name);
   }

   public int size() {
      return this.membersMap.size();
   }

   public String[] getMemberNames() {
      String[] names = this.getMemberNames(false);
      return names;
   }

   public String[] getMemberNamesReverseOrder() {
      String[] names = this.getMemberNames(true);
      return names;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("Cluster[").append(this.clusterName).append("] {").append(this.serverNamePrefix).append(", ").append(this.maxServers).append("}");
      Iterator var2 = this.membersMap.values().iterator();

      while(var2.hasNext()) {
         ClusterMember m = (ClusterMember)var2.next();
         buf.append("\n  ").append(m);
      }

      return buf.toString();
   }

   public static ClusterInfo getClusterInfo(String clusterName) {
      ClusterInfo clusterInfo = null;
      DomainAccess domainAccess = ManagementService.getDomainAccess(KERNEL_ID);
      ClusterMBean cluster = domainAccess.getDomainRuntimeService().getDomainConfiguration().lookupCluster(clusterName);
      if (cluster != null) {
         DynamicServersMBean dynamicServers = cluster.getDynamicServers();
         if (dynamicServers.getServerTemplate() == null) {
            return null;
         } else {
            String serverNamePrefix = dynamicServers.getServerNamePrefix();
            int maxServers = dynamicServers.getDynamicClusterSize();
            clusterInfo = new ClusterInfo(clusterName);
            clusterInfo.setMaxServers(maxServers);
            clusterInfo.setServerNamePrefix(serverNamePrefix);
            findClusterMembers(clusterInfo);
            return clusterInfo;
         }
      } else {
         return null;
      }
   }

   static void findClusterMembers(ClusterInfo clusterInfo) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Finding cluster members for " + clusterInfo.getClusterName());
      }

      String serverNamePrefix = clusterInfo.getServerNamePrefix();
      DomainAccess domainAccess = ManagementService.getDomainAccess(KERNEL_ID);
      ServerStateInspector serverStateInspector = (ServerStateInspector)GlobalServiceLocator.getServiceLocator().getService(ServerStateInspector.class, new Annotation[0]);
      ServerLifeCycleRuntimeMBean[] var4 = domainAccess.getServerLifecycleRuntimes();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ServerLifeCycleRuntimeMBean slcrt = var4[var6];
         String serverName = slcrt.getName();
         if (serverName.startsWith(serverNamePrefix)) {
            ClusterMember member = new ClusterMember();
            member.setName(serverName);
            if (serverStateInspector.isNodemanagerForServerReachable(serverName)) {
               try {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Finding state for server: " + serverName);
                  }

                  String state = slcrt.getState();
                  member.setState(state);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Current state for server " + serverName + " is " + state);
                  }

                  if (state.equals("RUNNING")) {
                     ServerRuntimeMBean serverRuntime = domainAccess.getDomainRuntimeService().lookupServerRuntime(serverName);
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("serverRuntime for server " + serverName + " is " + serverRuntime);
                     }

                     if (serverRuntime != null) {
                        String listenAddr = serverRuntime.getListenAddress();
                        int listenPort = serverRuntime.getListenPort();
                        member.setListenAddr(listenAddr);
                        member.setListenPort(listenPort);
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("server=" + serverName + " listenAddr=" + listenAddr + " listenPort=" + listenPort);
                        }
                     } else {
                        member.setState("UNKNOWN");
                     }
                  }
               } catch (Throwable var14) {
                  member.setState("UNKNOWN");
               }
            } else {
               member.setState("UNKNOWN");
            }

            clusterInfo.addMember(member);
         }
      }

   }

   private String[] getMemberNames(final boolean reverseOrder) {
      String[] names = new String[this.membersMap.size()];
      int i = 0;

      String name;
      for(Iterator var4 = this.membersMap.keySet().iterator(); var4.hasNext(); names[i++] = name) {
         name = (String)var4.next();
      }

      final int prefixLength = this.serverNamePrefix.length();
      Arrays.sort(names, new Comparator() {
         public int compare(Object o1, Object o2) {
            try {
               String s1 = (String)o1;
               String s2 = (String)o2;
               int i1 = Integer.parseInt(s1.substring(prefixLength));
               int i2 = Integer.parseInt(s2.substring(prefixLength));
               return !reverseOrder ? i1 - i2 : i2 - i1;
            } catch (Exception var7) {
               var7.printStackTrace();
               return 0;
            }
         }
      });
      return names;
   }
}
