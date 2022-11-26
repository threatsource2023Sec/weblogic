package weblogic.cluster.messaging.internal;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.work.WorkManagerFactory;

public class ServerReachabilityMajorityServiceImpl implements ServerReachabilityMajorityService {
   private static final DebugCategory debugSRM = Debug.getCategory("weblogic.cluster.leasing.SRMService");
   private static final boolean DEBUG = debugEnabled();
   private SRMResult lastResult;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public SRMResult performSRMCheck(ServerInformation leader, String clusterName, String machineToIgnore, boolean useNMConnectionTimeout) {
      ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupCluster(clusterName);

      assert cluster != null;

      ServerMBean[] servers = cluster.getServers();

      assert servers != null;

      if (DEBUG) {
         debug("There are " + servers.length + " in the cluster");
      }

      SRMResultImpl result = new SRMResultImpl(leader, servers.length);
      HashMap machineStateMap = new HashMap();

      for(int count = 0; count < servers.length; ++count) {
         MachineMBean machine = servers[count].getMachine();
         MachineState machineState = (MachineState)machineStateMap.get(machine.getName());
         if (machineState == null) {
            if (DEBUG) {
               debug("created new MachineState for " + machine.getName());
            }

            machineState = new MachineState(machine, result, useNMConnectionTimeout);
            machineStateMap.put(machine.getName(), machineState);
         }
      }

      result.setMachinesExpectedToReportStates(machineStateMap.size());
      if (DEBUG) {
         debug("machine state map has " + machineStateMap.size() + " entries");
      }

      MachineState machineState;
      for(Iterator iter = machineStateMap.values().iterator(); iter.hasNext(); WorkManagerFactory.getInstance().getSystem().schedule(machineState)) {
         machineState = (MachineState)iter.next();
         if (machineState.getMachineName().equals(machineToIgnore)) {
            machineState.nullifyStates();
         }
      }

      result.blockTillCompletion();
      this.lastResult = result;
      return result;
   }

   public SRMResult getLastSRMResult() {
      return this.lastResult;
   }

   public SRMResult performSRMCheck(ServerInformation leader, String clusterName) {
      return this.performSRMCheck(leader, clusterName, (String)null, true);
   }

   public SRMResult performSRMCheck(ServerInformation leader, String clusterName, String machineNameToIgnore) {
      return this.performSRMCheck(leader, clusterName, machineNameToIgnore, true);
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[SRMService] " + s);
   }

   public static ServerReachabilityMajorityService getInstance() {
      return ServerReachabilityMajorityServiceImpl.Factory.THE_ONE;
   }

   private static boolean debugEnabled() {
      return debugSRM.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static final class Factory {
      static final ServerReachabilityMajorityServiceImpl THE_ONE = new ServerReachabilityMajorityServiceImpl();
   }
}
