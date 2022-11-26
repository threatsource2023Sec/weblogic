package weblogic.management.utils.jmsdlb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.comparators.ComparatorChain;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.management.utils.GenericManagedDeployment;

public class DLRulesLayout implements DLLayout {
   private final boolean supportSuspect;
   private final DLUtil util;
   private final DLUtil.DLLogger logger;
   private int maxInstancesPerNode = -1;
   private final DLContext context;
   RulesProcessor rp;

   public DLRulesLayout(DLContext context, DLCluster dlCluster, int maxServicePerNode) {
      this.context = context;
      this.util = context.getUtil();
      this.supportSuspect = context.getSupportSuspect();
      this.logger = this.util.getLogger();
      this.maxInstancesPerNode = maxServicePerNode;
      this.rp = new RulesProcessor(dlCluster);
   }

   public void setMaxInstancesPerNode(int number) {
      this.maxInstancesPerNode = number;
   }

   public Map balance(DLCluster state) {
      return this.rp.process();
   }

   class RulesProcessor {
      private static final int NOT_AVAILABLE = 0;
      private static final int NO_OP = 100;
      private static final int REQUIRED = 500;
      private static final int LOW = 20;
      Map sharedContext = new HashMap();
      final Rule[] rules;
      final DLCluster cluster;

      public RulesProcessor(DLCluster cluster) {
         this.rules = new Rule[]{new ValidityCheckRule(this.sharedContext), new ActivelyStartingRule(this.sharedContext), new RunningOnServerRule(this.sharedContext), new AdminShutdownRule(this.sharedContext), new FailbackRule(this.sharedContext), new NotOnPreferredRule(this.sharedContext), new RestartRule(this.sharedContext), new SuspectServerRule(this.sharedContext), new ApplyBootDelay(this.sharedContext), new ExistingServerLoadRule(this.sharedContext), new ApplyLoadRule(this.sharedContext), new FailOverLimitRule(this.sharedContext), new MaxInstanceRule(this.sharedContext), new ClusterIsReadyDistributedRule(this.sharedContext), new ClusterIsReadySingletonRule(this.sharedContext), new MaxConcurrentPlacement(this.sharedContext)};
         this.cluster = cluster;
         Rule[] var3 = this.rules;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Rule r = var3[var5];
            r.init(cluster);
         }

      }

      public Map process() {
         long timestart = System.currentTimeMillis();
         DebugInfo debuginfo = null;
         if (DLRulesLayout.this.logger.isDebugFinestEnabled()) {
            debuginfo = new DebugInfo();
         }

         ServerAssignment assignment = new ServerAssignment(0);
         DLServerStatus[] servers = this.getOperatingServers(this.cluster);
         this.sharedContext.clear();
         Rule[] var6 = this.rules;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Rule r = var6[var8];
            r.reset(servers);
         }

         DLMigratableGroup[] activeGroups = (DLMigratableGroup[])this.cluster.getMigrationInstances().toArray(new DLMigratableGroup[0]);
         ComparatorChain chainSorter = new ComparatorChain();
         chainSorter.addComparator(new DistFirstSort());
         chainSorter.addComparator(new TTLSort());
         Arrays.sort(activeGroups, chainSorter);
         int[][] results = new int[activeGroups.length][];
         if (DLRulesLayout.this.logger.isDebugEnabled()) {
            DLRulesLayout.this.logger.debug("Beginning to place instances: " + activeGroups.length + " on " + servers.length + " servers");
         }

         int highestWeight;
         int k;
         int newweight;
         for(int i = 0; i < activeGroups.length; ++i) {
            DLMigratableGroup dgx = activeGroups[i];
            if (debuginfo != null) {
               debuginfo.debug(i, "Group is " + dgx.getName() + dgx.getStateInfo());
               debuginfo.debug(i, "\tinfo=" + dgx.isRunning() + "-" + dgx.getTargetServer() + " - " + dgx.getCurrentServer());
            }

            if (dgx.getConfig().isSingleton()) {
               assignment.incrementTotalSingletonCnt();
            }

            if (dgx.isRunning()) {
               if (debuginfo != null) {
                  debuginfo.debug(i, "\t... running");
               }

               DLServerStatus ss = dgx.getCurrentServer();
               DLId id = ss.getServerID();
               assignment.assignToServer(id, dgx, dgx.getConfig().getStoreID(), dgx.getConfig().isSingleton(), false);
            }

            boolean valid = true;
            if (debuginfo != null) {
               debuginfo.debug(i, "\tChecking Validity: ");
            }

            int j;
            for(j = 0; j < this.rules.length && valid; ++j) {
               valid = this.rules[j].validateCanPlaceGroup(dgx);
               if (debuginfo != null) {
                  debuginfo.debug(i, "\t\t" + this.rules[j].getClass().getSimpleName() + " isValid=" + valid);
               }
            }

            if (!valid) {
               results[i] = null;
            } else {
               results[i] = new int[servers.length];
               if (debuginfo != null) {
                  debuginfo.debug(i, "\tCalculating Weight: ");
               }

               for(j = 0; j < servers.length; ++j) {
                  highestWeight = 100;

                  for(k = 0; k < this.rules.length && highestWeight > 0; ++k) {
                     newweight = this.rules[k].calcServerRating(dgx, servers[j]);
                     highestWeight = newweight != 0 && highestWeight != 0 ? (highestWeight * newweight + 100) / 100 : 0;
                     if (debuginfo != null) {
                        debuginfo.debug(i, "\t\tServer: " + servers[j].getName() + ", rule= " + this.rules[k].getClass().getSimpleName() + " weight=" + newweight);
                     }
                  }

                  results[i][j] = highestWeight;
               }
            }
         }

         Map m = new HashMap();

         for(int ix = 0; ix < results.length; ++ix) {
            DLMigratableGroup dg = activeGroups[ix];
            if (DLRulesLayout.this.logger.isDebugFineEnabled()) {
               DLRulesLayout.this.logger.fine("Processing: " + dg.getName());
            }

            if (results[ix] != null && DLUtil.BitUtility.sum(results[ix]) != 0) {
               int[] weightAdj = Arrays.copyOf(results[ix], results[ix].length);
               if (debuginfo != null) {
                  debuginfo.debug(ix, "\tAdjusting Weight");
               }

               Rule[] var30 = this.rules;
               k = var30.length;

               for(newweight = 0; newweight < k; ++newweight) {
                  Rule rx = var30[newweight];
                  int[] weights = rx.getWeightAdjustment(dg, assignment);
                  if (weights != null) {
                     if (debuginfo != null) {
                        debuginfo.debug(ix, "\t\trule= " + rx.getClass().getSimpleName() + " Adjustment=" + Arrays.toString(weights));
                     }

                     for(int iw = 0; iw < weightAdj.length; ++iw) {
                        weightAdj[iw] = weights[iw] != 0 && weightAdj[iw] != 0 ? (weights[iw] * weightAdj[iw] + 100) / 100 : 0;
                     }
                  }
               }

               results[ix] = weightAdj;
               highestWeight = 0;
               k = -1;

               for(newweight = 0; newweight < servers.length; ++newweight) {
                  int weight = weightAdj[newweight];
                  if (weight > highestWeight) {
                     highestWeight = weight;
                     k = newweight;
                  }
               }

               if (highestWeight > 0 && k >= 0) {
                  DLServerStatus assigned = servers[k];
                  if (DLRulesLayout.this.logger.isDebugFineEnabled()) {
                     DLRulesLayout.this.logger.fine("PLACED: " + dg.getName() + "-" + dg.getConfig() + " on " + assigned.getName() + " index = " + k + " weight = " + highestWeight + "\n\t .. " + Arrays.toString(servers) + " ... " + Arrays.toString(weightAdj));
                  }

                  if (dg.isRunning() && assigned == dg.getCurrentServer()) {
                     if (DLRulesLayout.this.logger.isDebugFineEnabled()) {
                        DLRulesLayout.this.logger.fine("Ingoring: " + dg.getName() + " already running");
                     }
                     continue;
                  }

                  m.put(dg, assigned);
                  assignment.assignToServer(assigned.getServerID(), dg, dg.getConfig().getStoreID(), dg.getConfig().isSingleton(), true);
               } else if (DLRulesLayout.this.logger.isDebugFineEnabled()) {
                  DLRulesLayout.this.logger.fine("DID NOT PLACE: " + dg.getName());
               }

               if (debuginfo != null && DLRulesLayout.this.logger.isDebugFinestEnabled()) {
                  DLRulesLayout.this.logger.finest(debuginfo.toString());
               }
            } else {
               if (DLRulesLayout.this.logger.isDebugFineEnabled()) {
                  DLRulesLayout.this.logger.fine("Nothing to do: " + Arrays.toString(results[ix]));
               }

               if (debuginfo != null) {
                  debuginfo.debug(ix, "\tNothing to do");
               }
            }
         }

         long deltaTime = System.currentTimeMillis() - timestart;
         if (DLRulesLayout.this.logger.isDebugEnabled()) {
            DLRulesLayout.this.logger.debug(deltaTime + "ms :Returning plan: " + m.size());
         }

         return m;
      }

      private DLServerStatus[] getOperatingServers(DLCluster cluster) {
         Set s = new HashSet(cluster.getServers());
         Iterator itr = s.iterator();

         while(itr.hasNext()) {
            DLServerStatus ss = (DLServerStatus)itr.next();
            if (!ss.isRunning()) {
               itr.remove();
            }
         }

         return (DLServerStatus[])s.toArray(new DLServerStatus[0]);
      }

      private class DebugInfo {
         ArrayList strings;

         private DebugInfo() {
            this.strings = new ArrayList();
         }

         public void debug(int i, String s) {
            String old = "";
            if (this.strings.size() < i) {
               old = (String)this.strings.get(i);
            }

            old = "\n" + s;
            if (i <= this.strings.size()) {
               this.strings.add(old);
            } else {
               this.strings.set(i, old);
            }

         }

         public String toString() {
            String ret = "";

            for(int i = 0; i < this.strings.size(); ++i) {
               ret = ret + (String)this.strings.get(i);
            }

            return ret;
         }

         // $FF: synthetic method
         DebugInfo(Object x1) {
            this();
         }
      }

      private class FailOverLimitRule extends Rule {
         private static final int FAIL_OVER_UNLIMITED = -1;
         private static final int FAIL_OVER_NONE = 0;

         public FailOverLimitRule(Map context) {
            super(context);
         }

         int[] getWeightAdjustment(DLMigratableGroup group, ServerAssignment assignment) {
            if (group.getConfig().getFailOverLimit() == -1) {
               return null;
            } else {
               int[] load = new int[this.servers.length];

               for(int i = 0; i < load.length; ++i) {
                  load[i] = this.calcServerRating(group, this.servers[i], assignment);
               }

               return load;
            }
         }

         private int calcServerRating(DLMigratableGroup group, DLServerStatus server, ServerAssignment assignment) {
            DLStoreConfig config = group.getConfig();
            if (config.isDistributed() && config.validPolicy()) {
               if (server.equals(this.cluster.getServerTarget(group.getTargetServer()))) {
                  return 100;
               } else if (config.getFailOverLimit() == 0) {
                  return 0;
               } else {
                  return this.getFailOverCnt(group, server, assignment) < config.getFailOverLimit() ? 100 : 0;
               }
            } else {
               return 100;
            }
         }

         private int getFailOverCnt(DLMigratableGroup group, DLServerStatus server, ServerAssignment assignment) {
            DLStoreConfig config = group.getConfig();
            int cnt = 0;
            DLMigratableGroup[] activeGroups = (DLMigratableGroup[])this.cluster.getMigrationInstances().toArray(new DLMigratableGroup[0]);

            for(int i = 0; i < activeGroups.length; ++i) {
               DLServerStatus targetServer = this.cluster.getServerTarget(activeGroups[i].getTargetServer());
               if (config.equals(activeGroups[i].getConfig()) && !server.equals(targetServer)) {
                  if (activeGroups[i].isRunning()) {
                     if (server.equals(activeGroups[i].getCurrentServer()) && !assignment.isPlacedOnServer(targetServer.getServerID().getIndex(), activeGroups[i].getGroupID())) {
                        ++cnt;
                     }
                  } else if (assignment.isPlacedOnServer(server.getServerID().getIndex(), activeGroups[i].getGroupID())) {
                     ++cnt;
                  }
               }
            }

            return cnt;
         }
      }

      class MaxConcurrentPlacement extends Rule {
         public MaxConcurrentPlacement(Map context) {
            super(context);
         }

         int[] getWeightAdjustment(DLMigratableGroup group, ServerAssignment assignment) {
            int maxPlace = DLRulesLayout.this.context.getMaxConcurrentPlacement();
            int[] weights = null;
            if (maxPlace != -1) {
               weights = new int[this.servers.length];

               for(int i = 0; i < weights.length; ++i) {
                  int cnt = assignment.getPlacedOnServerCnt(this.servers[i].getServerID().getIndex());
                  if (cnt >= maxPlace) {
                     weights[i] = 0;
                  } else {
                     weights[i] = 100;
                  }
               }
            }

            return weights;
         }
      }

      private class ExistingServerLoadRule extends Rule {
         public ExistingServerLoadRule(Map context) {
            super(context);
         }

         private int getLoad(int max, int min, int cnt) {
            int load = 100 / (max - min + 1) * (max - cnt + 1);
            return load;
         }

         int[] getWeightAdjustment(DLMigratableGroup group, ServerAssignment assignment) {
            int max = assignment.getMax();
            int min = assignment.getMin();
            int[] weights = new int[this.servers.length];

            for(int i = 0; i < weights.length; ++i) {
               int cnt = assignment.getAssignedToServerCnt(this.servers[i].getServerID().getIndex());
               weights[i] = this.getLoad(max, min, cnt);
            }

            return weights;
         }
      }

      private class ApplyBootDelay extends Rule {
         boolean applyFirst = false;

         public ApplyBootDelay(Map context) {
            super(context);
         }

         public void reset(DLServerStatus[] servers) {
            super.reset(servers);
            this.applyFirst = DLRulesLayout.this.context.isApplyFirstDelay();
         }

         public int calcServerRating(DLMigratableGroup g, DLServerStatus server) {
            int weight = 100;
            long delay = g.getConfig().getBootDelay();
            if (delay > 0L && server.isRunning()) {
               long ttl = delay * 1000L;
               long lastPlacementTime = server.getLastPlacementTime(g.getName());
               boolean checkTTL = this.applyFirst || lastPlacementTime > 0L;
               if (checkTTL) {
                  long exptime = System.currentTimeMillis() - (lastPlacementTime == -1L ? server.getStartTime() : lastPlacementTime);
                  if (exptime < ttl) {
                     weight = 0;
                     this.cluster.addTimeout(server.getStartTime() + ttl);
                  }
               }
            }

            return weight;
         }

         int[] getWeightAdjustment(DLMigratableGroup group, ServerAssignment assignment) {
            long bootDelay = group.getConfig().getBootDelay();
            if (bootDelay <= 0L) {
               return null;
            } else {
               int[] weight = assignment.getCntPlacedInGroup(group.getConfig().getStoreID());

               for(int i = 0; i < weight.length; ++i) {
                  weight[i] = weight[i] == 0 ? 100 : 0;
               }

               return weight;
            }
         }
      }

      private class SuspectServerRule extends Rule {
         public SuspectServerRule(Map context) {
            super(context);
         }

         public int calcServerRating(DLMigratableGroup g, DLServerStatus server) {
            int weight = 100;
            if (DLRulesLayout.this.supportSuspect) {
               weight = server.isSuspect() ? 0 : 100;
            }

            return weight;
         }
      }

      private class ApplyLoadRule extends Rule {
         int[] load = null;

         public ApplyLoadRule(Map context) {
            super(context);
         }

         public void reset(DLServerStatus[] servers) {
            super.reset(servers);
            this.load = new int[servers.length];
            int i = 0;
            DLServerStatus[] var3 = servers;
            int var4 = servers.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               DLServerStatus s = var3[var5];
               this.load[i++] = s.getLoad().getLoad();
            }

         }

         int[] getWeightAdjustment(DLMigratableGroup group, ServerAssignment assignment) {
            return this.load;
         }
      }

      private class RestartRule extends Rule {
         public RestartRule(Map context) {
            super(context);
         }

         public int calcServerRating(DLMigratableGroup g, DLServerStatus server) {
            DLStoreConfig config = g.getConfig();
            DLServerStatus s = g.getCurrentServer();
            boolean restart = config.isRestartInPlace();
            int restartAttempts = config.getRestartAttempts();
            int tries = g.getRestartCnt();
            long lastRestart = g.getLastRestartTime();
            long restartSec = config.getRestartInterval();
            int weight = 100;
            if (s == null) {
               boolean var10000 = true;
            } else {
               g.lastVersionOfServer(s.getName());
            }

            if (this.getSharedContext().containsKey("failback.assigned")) {
               weight = 100;
            } else if (restart && s != null && s.isRunning()) {
               if (tries >= restartAttempts) {
                  weight = server == s ? 0 : 500;
               } else if (server == s) {
                  long targetRestart = lastRestart + restartSec * 1000L;
                  if (System.currentTimeMillis() > targetRestart) {
                     weight = 500;
                     g.incrementRestart();
                  } else {
                     weight = 0;
                  }
               } else {
                  weight = 0;
               }
            } else if (s == server) {
               weight = 20;
            }

            return weight;
         }
      }

      private class MaxInstanceRule extends Rule {
         public MaxInstanceRule(Map context) {
            super(context);
         }

         int[] getWeightAdjustment(DLMigratableGroup group, ServerAssignment assignment) {
            if (DLRulesLayout.this.maxInstancesPerNode == -1) {
               return null;
            } else {
               int[] load = new int[this.servers.length];

               for(int i = 0; i < load.length; ++i) {
                  int cnt = assignment.getAssignedToServerCnt(this.servers[i].getServerID().getIndex());
                  if (cnt >= DLRulesLayout.this.maxInstancesPerNode) {
                     load[i] = 0;
                  } else {
                     load[i] = 100;
                  }
               }

               return load;
            }
         }
      }

      private class ClusterIsReadySingletonRule extends ClusterReadyRule {
         public ClusterIsReadySingletonRule(Map context) {
            super(context);
         }

         int[] getWeightAdjustment(DLMigratableGroup group, ServerAssignment assignment) {
            int[] load = null;
            if ((!this.isClusterReady() || !this.isGroupReady(group)) && group.getConfig().isSingleton()) {
               int placedSingletonCnt = assignment.getPlacedSingletonCnt();
               int runningServersCnt = assignment.getServerCnt();
               int configServerCnt = this.cluster.getExpectedClusterSize();
               if (configServerCnt < runningServersCnt) {
                  configServerCnt = runningServersCnt;
               }

               int bareMinToPlace = assignment.getTotalSingletonCnt() / configServerCnt;
               int maxToPlacePerServer = bareMinToPlace < 1 ? 1 : bareMinToPlace;
               int totalMaxToPlace = maxToPlacePerServer * runningServersCnt;
               if (placedSingletonCnt >= totalMaxToPlace) {
                  load = new int[this.servers.length];
                  this.cluster.addTimeout(this.getClusterSleepTime());
               }
            }

            return load;
         }
      }

      private class ClusterIsReadyDistributedRule extends ClusterReadyRule {
         public ClusterIsReadyDistributedRule(Map context) {
            super(context);
         }

         public int calcServerRating(DLMigratableGroup g, DLServerStatus server) {
            int weight = 100;
            boolean clusterReady = this.isClusterReady();
            boolean groupReady = this.isGroupReady(g);
            if (!g.getConfig().isSingleton() && (!clusterReady || !groupReady) && !server.getName().equals(g.getTargetServer())) {
               weight = 0;
               if (!clusterReady) {
                  this.cluster.addTimeout(this.getClusterSleepTime());
               }

               if (!groupReady) {
                  this.cluster.addTimeout(this.getGroupClusterSleepTime(g));
               }
            }

            return weight;
         }
      }

      private abstract class ClusterReadyRule extends Rule {
         public ClusterReadyRule(Map context) {
            super(context);
         }

         protected boolean isClusterReady() {
            return this.cluster.isSteadyState();
         }

         protected long getSteadyStateTime(DLMigratableGroup group) {
            return group.getConfig().getSteadyStateTime();
         }

         protected boolean isGroupReady(DLMigratableGroup group) {
            boolean steady = true;
            long ttl = this.getSteadyStateTime(group);
            if (ttl > 0L) {
               steady = this.cluster.isClusterUp() || this.cluster.checkGroupSteadyState(ttl);
            }

            return steady;
         }

         protected long getGroupClusterSleepTime(DLMigratableGroup group) {
            long ttl = this.getSteadyStateTime(group);
            long time = ttl <= 0L ? System.currentTimeMillis() - 1000L : System.currentTimeMillis() + 1000L * ttl;
            return time;
         }

         protected long getClusterSleepTime() {
            return this.cluster.timeToSteadyState();
         }
      }

      private class FailbackRule extends Rule {
         public FailbackRule(Map context) {
            super(context);
         }

         public int calcServerRating(DLMigratableGroup g, DLServerStatus server) {
            int weight = 100;
            long failback = g.getConfig().getFailSeconds();
            if (g.getConfig().isDistributed()) {
               DLServerStatus pserver = this.cluster.getServerTarget(g.getTargetServer());
               boolean serverAvailable = pserver != null && pserver.isRunning();
               int version = pserver == null ? -1 : g.lastVersionOfServer(pserver.getName());
               serverAvailable &= version == -1 || version != pserver.getVersion();
               if (serverAvailable && failback != 0L) {
                  this.getSharedContext().put("failback.assigned", true);
               }

               boolean availableToStart = serverAvailable && (failback < 0L || version == -1 || System.currentTimeMillis() >= pserver.getStartTime() + (failback == 0L ? 2147483647L : failback) * 1000L);
               if (g.isRunning() && g.getCurrentServer() == pserver && serverAvailable) {
                  return 100;
               }

               if (!serverAvailable) {
                  weight = g.isRunning() ? 0 : 100;
               } else if (availableToStart) {
                  weight = pserver == server ? 500 : 100;
               } else {
                  weight = 0;
               }
            }

            return weight;
         }
      }

      private class NotOnPreferredRule extends Rule {
         public NotOnPreferredRule(Map context) {
            super(context);
         }

         public int calcServerRating(DLMigratableGroup g, DLServerStatus server) {
            if (!g.getConfig().isDistributed()) {
               return 100;
            } else if (g.isRunning()) {
               return 100;
            } else {
               DLServerStatus preferredServer = this.cluster.getServerTarget(g.getTargetServer());
               if (server == preferredServer) {
                  return 100;
               } else {
                  DLDynamicPlacement dlb = DLDynamicPlacement.getSingleton(GenericDeploymentManager.getMyPartitionName(), this.cluster.getClusterName());
                  GenericManagedDeployment gmd = dlb.getGMD(g.getName());
                  if (gmd == null) {
                     return 0;
                  } else {
                     return gmd.getNewStatus() != GenericManagedDeployment.NewStatus.OLD && gmd.getNewStatus() != GenericManagedDeployment.NewStatus.UNKNOWN_ASSUME_OLD ? 0 : 100;
                  }
               }
            }
         }
      }

      private class AdminShutdownRule extends Rule {
         public AdminShutdownRule(Map context) {
            super(context);
         }

         public int calcServerRating(DLMigratableGroup g, DLServerStatus server) {
            DLServerStatus last = g.getLastServer() == null ? this.cluster.getLocalServer() : g.getLastServer();
            int weight = 100;
            if (g.getConfig().isOnFailure() && g.isAdminDown()) {
               if (last == server && server.isRunning()) {
                  if (DLRulesLayout.this.logger.isDebugEnabled()) {
                     DLRulesLayout.this.logger.debug("CAN NOT START: " + g + " because " + last);
                  }

                  weight = 100;
               } else {
                  if (DLRulesLayout.this.logger.isDebugEnabled()) {
                     DLRulesLayout.this.logger.debug("AdminDOWN " + g);
                  }

                  weight = 0;
               }
            }

            return weight;
         }
      }

      private class RunningOnServerRule extends Rule {
         public RunningOnServerRule(Map context) {
            super(context);
         }

         public boolean validateCanPlaceGroup(DLMigratableGroup di) {
            DLStoreConfig sc = di.getConfig();
            String preferredServerName = di.getTargetServer();
            boolean avail = true;
            if (di.isRunning() && (sc.isSingleton() || di.getCurrentServer() != null && di.getCurrentServer().matches(preferredServerName))) {
               avail = false;
            }

            return avail;
         }
      }

      private class ActivelyStartingRule extends Rule {
         public ActivelyStartingRule(Map context) {
            super(context);
         }

         public boolean validateCanPlaceGroup(DLMigratableGroup di) {
            return !di.isPending();
         }
      }

      private class ValidityCheckRule extends Rule {
         public ValidityCheckRule(Map context) {
            super(context);
         }

         public boolean validateCanPlaceGroup(DLMigratableGroup di) {
            boolean valid = true;
            DLStoreConfig sc = di.getConfig();
            if (!sc.validPolicy()) {
               valid = false;
            } else if (di.isRunning() && di.getCurrentServer() == null) {
               valid = false;
            }

            return valid;
         }
      }

      abstract class Rule {
         DLCluster cluster;
         DLServerStatus[] servers;
         protected Map shared;

         public Rule(Map shared) {
            this.shared = shared;
         }

         public Map getSharedContext() {
            return this.shared;
         }

         public void init(DLCluster cluster) {
            this.cluster = cluster;
         }

         public void reset(DLServerStatus[] servers) {
            this.servers = servers;
         }

         protected boolean validateCanPlaceGroup(DLMigratableGroup di) {
            return true;
         }

         protected int calcServerRating(DLMigratableGroup g, DLServerStatus server) {
            return 100;
         }

         int[] getWeightAdjustment(DLMigratableGroup group, ServerAssignment assignment) {
            return null;
         }
      }

      class TTLSort implements Comparator {
         public int compare(DLMigratableGroup o1, DLMigratableGroup o2) {
            DLStoreConfig sc1 = o1.getConfig();
            DLStoreConfig sc2 = o2.getConfig();
            long ttl1 = sc1.getSteadyStateTime() <= 0L ? 0L : sc1.getSteadyStateTime();
            long ttl2 = sc2.getSteadyStateTime() <= 0L ? 0L : sc2.getSteadyStateTime();
            return (int)(ttl1 - ttl2);
         }
      }

      class DistFirstSort implements Comparator {
         public int compare(DLMigratableGroup o1, DLMigratableGroup o2) {
            DLStoreConfig sc1 = o1.getConfig();
            DLStoreConfig sc2 = o2.getConfig();
            if (sc1.isDistributed() && !sc2.isDistributed()) {
               return -1;
            } else {
               return !sc1.isDistributed() && sc2.isDistributed() ? 1 : 0;
            }
         }
      }

      private class ServerAssignment {
         int configSize = 0;
         int totalSingletonCnt = 0;
         int[] singleton = new int[1];
         int[][] servers = (int[][])null;
         int[][] justPlaced = (int[][])null;
         int[][] groups = (int[][])null;
         int[] count = null;

         public ServerAssignment(int configSize) {
            this.servers = new int[DLRulesLayout.this.context.getServerIDSize()][1];
            this.justPlaced = new int[DLRulesLayout.this.context.getServerIDSize()][1];
            this.groups = new int[DLRulesLayout.this.context.getGroupIDSize()][1];
            this.count = new int[DLRulesLayout.this.context.getServerIDSize()];
            this.configSize = configSize;
         }

         public int[] getCntPlacedInGroup(DLId storecfgId) {
            int[] cnts = new int[this.servers.length];

            for(int i = 0; i < this.servers.length; ++i) {
               cnts[i] = DLUtil.BitUtility.countOfSetBits(DLUtil.BitUtility.and(this.servers[i], this.groups[storecfgId.getIndex()]));
            }

            return cnts;
         }

         public void assignToServer(DLId serverID, DLMigratableGroup group, DLId storecfgId, boolean isSingleton, boolean newPlacement) {
            DLId groupid = group.getGroupID();
            if (isSingleton) {
               this.singleton = DLUtil.BitUtility.or(this.singleton, groupid.getMask());
            }

            int id = serverID.getIndex();
            this.servers[id] = DLUtil.BitUtility.or(this.servers[id], groupid.getMask());
            int var10002 = this.count[id]++;
            if (newPlacement) {
               this.justPlaced[id] = DLUtil.BitUtility.or(this.justPlaced[id], groupid.getMask());
               int groupindx = storecfgId.getIndex();
               this.groups[groupindx] = DLUtil.BitUtility.or(this.groups[groupindx], groupid.getMask());
            }

            if (group.isRunning()) {
               DLId currentServerID = group.getCurrentServer().getServerID();
               if (!serverID.equals(currentServerID)) {
                  int sid = currentServerID.getIndex();
                  this.servers[sid] = DLUtil.BitUtility.and(this.servers[sid], DLUtil.BitUtility.not(groupid.getMask()));
                  var10002 = this.count[sid]--;
               }
            }

         }

         public int getServerCnt() {
            return this.servers.length;
         }

         public int getConfigClusterSize() {
            return this.configSize;
         }

         public int getAssignedToServerCnt(int id) {
            return this.count[id];
         }

         public int getPlacedOnServerCnt(int id) {
            return DLUtil.BitUtility.countOfSetBits(this.justPlaced[id]);
         }

         public boolean isPlacedOnServer(int sid, DLId gid) {
            return DLUtil.BitUtility.isMaskSet(this.justPlaced[sid], gid.getMask());
         }

         public int getMin() {
            return DLUtil.BitUtility.min(this.count);
         }

         public int getMax() {
            return DLUtil.BitUtility.max(this.count);
         }

         public int getPlacedSingletonCnt() {
            return DLUtil.BitUtility.countOfSetBits(this.singleton);
         }

         public void incrementTotalSingletonCnt() {
            ++this.totalSingletonCnt;
         }

         public int getTotalSingletonCnt() {
            return this.totalSingletonCnt;
         }
      }
   }
}
