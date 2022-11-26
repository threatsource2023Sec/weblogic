package weblogic.management.utils.jmsdlb;

import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import weblogic.cluster.migration.DynamicLoadbalancer;
import weblogic.cluster.migration.DynamicLoadbalancer.CRITICAL;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DLCluster {
   private static int STEADY_STATE_SEC = Integer.getInteger("weblogic.management.utils.jmsdlb.cluster.steadySecs", 120);
   private boolean steadyState = false;
   private long clusterStartTime = 0L;
   private int expectedClusterSize = 0;
   private final DLContext context;
   private DLUtil.DLLogger logger;
   private String myServerName;
   private Set currentCoreRunningServers;
   private Map configMap;
   private Map managedServers;
   private Map managedGroups;
   private final DLCoreInfo coreinfo;
   private Map serverToServices;
   SortedSet times = new TreeSet();

   public DLCluster(DLContext context) {
      this.context = context;
      this.logger = context.getDMUtil().getLogger();
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Initializing DLCluster state");
      }

      this.coreinfo = new DLCoreInfo(this.logger, this.context);
      this.configMap = new HashMap();
      this.managedServers = new HashMap();
      this.managedGroups = new HashMap();
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      this.myServerName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
   }

   public String getLocalServerName() {
      return this.myServerName;
   }

   public DLServerStatus getLocalServer() {
      return this.getServerTarget(this.getLocalServerName());
   }

   public String getClusterName() {
      return this.context.getClusterName();
   }

   public DLServerStatus getServerTarget(String serverName) {
      return (DLServerStatus)this.managedServers.get(serverName);
   }

   public boolean isSteadyState() {
      if (!this.steadyState) {
         if (System.currentTimeMillis() >= this.timeToSteadyState()) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Steady State reached: Timeout");
            }

            this.steadyState = true;
         } else if (this.managedServers.size() >= this.expectedClusterSize) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Steady State reached: Cluster Size");
            }

            this.steadyState = true;
         } else if (this.logger.isDebugEnabled()) {
            this.logger.debug("Time to SteadyState: " + (this.timeToSteadyState() - System.currentTimeMillis()) / 1000L);
         }
      }

      return this.steadyState;
   }

   public boolean isClusterUp() {
      return this.managedServers.size() >= this.expectedClusterSize;
   }

   public int getExpectedClusterSize() {
      return this.expectedClusterSize;
   }

   public long getClusterStartTime() {
      return this.clusterStartTime;
   }

   public long timeToSteadyState() {
      return STEADY_STATE_SEC == -1 ? System.currentTimeMillis() - 100L : this.clusterStartTime + (long)(1000 * STEADY_STATE_SEC);
   }

   public static final void setSteadyStateSec(int sec) {
      STEADY_STATE_SEC = sec;
   }

   public boolean checkGroupSteadyState(long stateSecs) {
      boolean steady = false;
      if (this.managedServers.size() >= this.expectedClusterSize) {
         steady = true;
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Steady State Config reached: Expected Cluster Size");
         }
      } else if (stateSecs <= 0L) {
         steady = true;
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Steady State Config reached: No Timeout");
         }
      } else if (System.currentTimeMillis() > this.clusterStartTime + stateSecs * 1000L) {
         steady = true;
      } else if (this.logger.isDebugEnabled()) {
         this.logger.debug("Time to Config SteadyState: " + (this.clusterStartTime + stateSecs * 1000L - System.currentTimeMillis()) / 1000L);
      }

      return steady;
   }

   public void startingCluster(int expectedClusterSize) {
      this.clusterStartTime = System.currentTimeMillis();
      this.expectedClusterSize = expectedClusterSize;
      this.steadyState = false;
   }

   public void overrideStartTime(long startTime) {
      this.clusterStartTime = startTime;
   }

   public void initializeRunningServers(Map servers, int proposedClusterSize) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("DLCluster: Initializing RunningServers: [size =" + proposedClusterSize + "] " + servers);
      }

      this.startingCluster(proposedClusterSize);
      Iterator var3 = servers.keySet().iterator();

      while(var3.hasNext()) {
         String s = (String)var3.next();
         if (this.logger.isDebugFineEnabled()) {
            this.logger.fine("DLCluster: Initializing server: " + s);
         }

         DLServerStatus target = new DLServerStatus(s, this.context);
         target.setState(DLServerStatus.State.RUNNING);
         target.setServerIdentity((String)servers.get(s));
         this.managedServers.put(s, target);
      }

      if (this.logger.isDebugFineEnabled()) {
         this.logger.fine("DLCluster: Initializing coreRunningServers: servers");
      }

      this.currentCoreRunningServers = new HashSet(servers.keySet());
   }

   public boolean changeRunningServers(Map servers) {
      boolean changed = true;
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("DLCluster: In changeRunningServers: " + servers);
      }

      boolean serversChanged = this.currentCoreRunningServers.size() != servers.size() || !this.currentCoreRunningServers.containsAll(servers.keySet()) || !servers.keySet().containsAll(this.currentCoreRunningServers);
      String s;
      if (serversChanged) {
         if (!this.steadyState && servers.size() >= this.expectedClusterSize) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Steady State reached: cluster is up");
            }

            this.steadyState = true;
         }

         Set newServers = new HashSet(servers.keySet());
         newServers.removeAll(this.currentCoreRunningServers);
         Iterator var5 = newServers.iterator();

         DLServerStatus st;
         while(var5.hasNext()) {
            s = (String)var5.next();
            st = (DLServerStatus)this.managedServers.get(s);
            if (st == null) {
               st = new DLServerStatus(s, this.context);
               this.managedServers.put(s, st);
            }

            st.setState(DLServerStatus.State.RUNNING);
            st.setServerIdentity((String)servers.get(s));
         }

         newServers.addAll(this.currentCoreRunningServers);
         newServers.removeAll(servers.keySet());

         for(var5 = newServers.iterator(); var5.hasNext(); st.setState(DLServerStatus.State.DOWN_UNKNOWN)) {
            s = (String)var5.next();
            st = (DLServerStatus)this.managedServers.get(s);
            if (st == null) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Internal Error: managed server does not exist [" + s + "]");
               }

               st = new DLServerStatus(s, this.context);
               this.managedServers.put(s, st);
            }
         }

         this.currentCoreRunningServers = new HashSet(servers.keySet());
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("DLCluster: complete changeRunningServers: " + this.currentCoreRunningServers);
         }
      } else {
         changed = false;
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("DLCluster: complete changeRunningServers: unchanged");
         }
      }

      Iterator iterator = this.managedServers.values().iterator();

      while(iterator.hasNext()) {
         DLServerStatus thisServerStatus = (DLServerStatus)iterator.next();
         if (thisServerStatus.isRunning()) {
            if (!servers.keySet().contains(thisServerStatus.getName())) {
               thisServerStatus.setState(DLServerStatus.State.DOWN_UNKNOWN);
               changed = true;
            } else {
               s = (String)servers.get(thisServerStatus.getName());
               if (!thisServerStatus.getServerIdentity().equals(s)) {
                  thisServerStatus.setState(DLServerStatus.State.REMOVED);
                  thisServerStatus.setState(DLServerStatus.State.RUNNING);
                  thisServerStatus.setServerIdentity(s);
                  changed = true;
               }
            }
         }
      }

      return changed;
   }

   public void markFailure(String server, String target, DynamicLoadbalancer.CRITICAL critical) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("DLCluster: Mark Server Failure: " + server + ":" + target + ":" + critical);
      }

      if (critical != CRITICAL.NOTREADY) {
         DLServerStatus serverTarget = (DLServerStatus)this.managedServers.get(server);
         DLMigratableGroup instanceTarget = (DLMigratableGroup)this.managedGroups.get(target);
         if (serverTarget != null) {
            serverTarget.fail(instanceTarget.getName());
         }

         if (instanceTarget == null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Error: DMCLusterState: UnknownTarget " + target);
            }

         } else {
            if (critical == CRITICAL.YES) {
               instanceTarget.setStatus(DLMigratableGroup.STATUS.FAILED_CRITICAL);
            } else if (critical == CRITICAL.NO) {
               instanceTarget.setStatus(DLMigratableGroup.STATUS.FAILED);
            }

            if (instanceTarget.getCurrentServer() != serverTarget) {
               instanceTarget.setCurrentServer(serverTarget);
            }

         }
      }
   }

   public boolean changeServiceStatus(Map currentServiceStatus) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("DLCluster: In ChangeServiceStatus " + currentServiceStatus);
      }

      boolean flag = this.coreinfo.updateServiceStatus(this.managedServers, this.managedGroups, currentServiceStatus);
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("DLCluster: Complete: ChangeServiceStatus servers:" + this.managedServers);
         this.logger.debug("DLCluster: Complete: ChangeServiceStatus services:" + this.managedGroups);
      }

      return flag;
   }

   public DLStoreConfig getConfig(String idname) {
      return (DLStoreConfig)this.configMap.get(idname);
   }

   public DLMigratableGroupConfigImpl registerInstance(String prefix, String idname, String userPreferredServerName) {
      DLStoreConfig config = null;
      config = this.getConfig(prefix);
      if (config == null) {
         throw new IllegalStateException("Unregistered Deployment Bean " + prefix + ":" + this.configMap);
      } else {
         DLMigratableGroupConfigImpl instance = new DLMigratableGroupConfigImpl(this.context, idname, config, userPreferredServerName);
         this.managedGroups.put(instance.getName(), instance);
         return instance;
      }
   }

   public DLMigratableGroupConfigImpl getManagedGroup(String instanceName) {
      return (DLMigratableGroupConfigImpl)this.managedGroups.get(instanceName);
   }

   public void registerConfig(String name, DLStoreConfig group) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Registering template: " + name);
      }

      this.configMap.put(name, group);
   }

   public void dumpDebugClusterState() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("------------------------------------------");
         this.logger.debug("------------------------------------------");
         this.logger.debug("Dumping Debug Cluster State");
         this.logger.debug("------------------------------------------");
         this.prettySetDump("CurrentCodeRunningServers", "   ", this.currentCoreRunningServers);
         this.logger.debug("------------------------------------------");
         this.prettyMapDump("lastCoreServiceStatus", "   ", this.coreinfo.getLastStatus());
         this.logger.debug("------------------------------------------");
         this.prettyMapDump("configMap", "   ", this.configMap);
         this.logger.debug("------------------------------------------");
         this.prettyMapDump("managedServers", "   ", this.managedServers);
         this.logger.debug("------------------------------------------");
         this.prettyMapDump("managedGroups", "   ", this.managedGroups);
         this.logger.debug("------------------------------------------");
         this.logger.debug("------------------------------------------");
      }

   }

   private void prettySetDump(String name, String prefix, Set s) {
      this.logger.debug(prefix + name);
      Iterator var4 = s.iterator();

      while(var4.hasNext()) {
         Object o = var4.next();
         this.logger.debug(prefix + "  " + o);
      }

   }

   private void prettyMapDump(String name, String prefix, Map m) {
      this.logger.debug(prefix + name);
      Set s = m.entrySet();
      Iterator var5 = s.iterator();

      while(var5.hasNext()) {
         Map.Entry e = (Map.Entry)var5.next();
         this.logger.debug(prefix + "  [key=" + e.getKey() + ", " + e.getValue());
      }

   }

   public Set getMigrationInstances() {
      return new HashSet(this.managedGroups.values());
   }

   public Set getServers() {
      return new HashSet(this.managedServers.values());
   }

   public void addTimeout(long ttl) {
      this.times.add(ttl);
   }

   public boolean hasTimedOut() {
      Iterator itr = this.times.iterator();
      long curTime = System.currentTimeMillis();
      boolean timedOut = false;

      while(itr.hasNext()) {
         long time = (Long)itr.next();
         if (time >= curTime) {
            break;
         }

         timedOut = true;
         itr.remove();
      }

      return timedOut;
   }
}
