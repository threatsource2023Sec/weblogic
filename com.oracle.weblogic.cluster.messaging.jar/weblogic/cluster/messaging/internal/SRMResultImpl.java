package weblogic.cluster.messaging.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.collections.ConcurrentHashMap;

public class SRMResultImpl implements SRMResult {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugCategory debugSRMResult = Debug.getCategory("weblogic.cluster.leasing.SRMResult");
   private static final boolean DEBUG = debugEnabled();
   private final HashMap serverStates;
   private final String leaderName;
   private int reachabilityCount;
   private final int expectedCount;
   private int machinesExpectedToReportStates;
   private final HashSet machinesReportingState = new HashSet();
   private ConcurrentHashMap serverMachineNameMap = new ConcurrentHashMap();

   SRMResultImpl() {
      this.leaderName = null;
      this.serverStates = new HashMap();
      this.reachabilityCount = 0;
      this.expectedCount = Integer.MAX_VALUE;
   }

   public SRMResultImpl(ServerInformation leader, int length) {
      this.leaderName = leader != null ? leader.getServerName() : null;
      this.serverStates = new HashMap();
      this.reachabilityCount = 0;
      this.expectedCount = length;
   }

   public boolean hasReachabilityMajority() {
      String localServerName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      if ("UNKNOWN".equals(this.serverStates.get(localServerName))) {
         if (DEBUG) {
            debug("local server state is UNKNOWN ! Local NodeManager is not reporting correct state?!");
         }

         return false;
      } else {
         if (DEBUG) {
            debug("hasReachabilityMajority called. reachabilityCount=" + this.reachabilityCount + ", server states=" + this.serverStates.size());
         }

         if (2 * this.reachabilityCount > this.serverStates.size()) {
            if (DEBUG) {
               debug("hasReachabilityMajority returns true as majority is reachable");
            }

            return true;
         } else if (2 * this.reachabilityCount == this.serverStates.size() && this.leaderName != null && this.serverStates.get(this.leaderName) != null) {
            if (DEBUG) {
               debug("hasReachabilityMajority returns true as half the servers are reachable along with the leader");
            }

            return true;
         } else {
            if (DEBUG) {
               debug("hasReachabilityMajority returns FALSE !");
            }

            return false;
         }
      }
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[SRMResult] " + s);
   }

   public String getServerState(String serverName) {
      return (String)this.serverStates.get(serverName);
   }

   public String getCurrentMachine(String serverName) {
      return (String)this.serverMachineNameMap.get(serverName);
   }

   public synchronized void blockTillCompletion() {
      assert this.expectedCount != Integer.MAX_VALUE;

      while(this.serverStates.size() < this.expectedCount || this.machinesReportingState.size() < this.machinesExpectedToReportStates) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   public synchronized void doneReporting(String machine) {
      this.machinesReportingState.add(machine);
      if (this.serverStates.size() == this.expectedCount && this.machinesReportingState.size() == this.machinesExpectedToReportStates) {
         Iterator itr = this.serverStates.values().iterator();

         while(itr.hasNext()) {
            String state = (String)itr.next();
            if (state != null) {
               ++this.reachabilityCount;
            }
         }

         this.notify();
      }

   }

   public synchronized void setServerState(String server, String machine, String newState) {
      String storedState = (String)this.serverStates.get(server);
      if (acceptState(storedState, newState)) {
         this.serverStates.put(server, newState);
         this.serverMachineNameMap.put(server, machine);

         assert this.serverStates.size() <= this.expectedCount;

      }
   }

   private static boolean acceptState(String storedState, String newState) {
      if (storedState != null && !"UNKNOWN".equals(storedState)) {
         return newState != null && !"UNKNOWN".equals(newState) && !"FAILED_NOT_RESTARTABLE".equals(newState);
      } else {
         return true;
      }
   }

   public String toString() {
      return "SRMResult [" + this.serverStates + "]";
   }

   private static boolean debugEnabled() {
      return debugSRMResult.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   public void setMachinesExpectedToReportStates(int machinesExpectedToReportStates) {
      this.machinesExpectedToReportStates = machinesExpectedToReportStates;
   }

   public List getServersInState(String state) {
      ArrayList servers = new ArrayList();
      if (this.serverStates == null) {
         return servers;
      } else {
         Iterator allServers = this.serverStates.entrySet().iterator();

         while(allServers.hasNext()) {
            Map.Entry tmp = (Map.Entry)allServers.next();
            String tmpState = (String)tmp.getValue();
            if (tmpState != null && tmpState.equals(state)) {
               servers.add(tmp.getKey());
            }
         }

         return servers;
      }
   }
}
