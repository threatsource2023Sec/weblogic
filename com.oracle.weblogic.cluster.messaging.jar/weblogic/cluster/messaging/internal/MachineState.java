package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.NumericValueHashMap;

public final class MachineState implements Runnable {
   private static final DebugCategory debugMachineState = Debug.getCategory("weblogic.cluster.leasing.MachineState");
   private static final boolean DEBUG = debugEnabled();
   public static final boolean USE_NM_CONNECTION_TIMEOUT = true;
   static final boolean IGNORE_NM_CONNECTION_TIMEOUT = false;
   private static final int TIMEOUT_MULTIPLIER = 15;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static NumericValueHashMap machineRoundTripTimes = new NumericValueHashMap();
   private final ArrayList configuredServerNamesInMachine = new ArrayList();
   private final HashSet configuredServerNamesInCluster = new HashSet();
   private final NodeManagerLifecycleService nodeManagerRuntime;
   private final SRMResultImpl result;
   private final MachineMBean machine;
   private boolean machineUnavailable;
   private boolean useConnectionTimeout;

   MachineState(MachineMBean machine, SRMResultImpl result, boolean useConnectionTimeout) {
      NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
      this.nodeManagerRuntime = generator.getInstance(machine);
      this.result = result;
      this.machine = machine;
      this.useConnectionTimeout = useConnectionTimeout;
      RuntimeAccess access = ManagementService.getRuntimeAccess(kernelId);
      ClusterMBean cluster = access.getServer().getCluster();

      assert cluster != null;

      ServerMBean[] servers = cluster.getServers();

      assert servers != null;

      for(int count = 0; count < servers.length; ++count) {
         this.configuredServerNamesInCluster.add(servers[count].getName());
         MachineMBean local = servers[count].getMachine();
         if (local != null && local.getName().equals(machine.getName())) {
            this.configuredServerNamesInMachine.add(servers[count].getName());
         }
      }

   }

   public static MachineState getMachineState(MachineMBean machine, boolean useConnectionTimeout) {
      MachineState state = new MachineState(machine, new SRMResultImpl(), useConnectionTimeout);
      state.run();
      return state;
   }

   public void run() {
      try {
         long startTime = System.currentTimeMillis();
         if (DEBUG) {
            debug("Invoking NodeManager.getStates() on machine " + this.machine.getName() + " with timeout of " + this.getTimeout(this.machine) + "ms");
         }

         String states = this.nodeManagerRuntime.getStates(true, this.getTimeout(this.machine));
         recordExecutionTime(startTime, System.currentTimeMillis(), this.machine);
         if (DEBUG) {
            debug("consolidated states for machine " + this.machine.getName() + " returned by NM " + states);
         }

         if (states != null && states.length() != 0) {
            String[] strs = StringUtils.split(states, '=');

            ArrayList availableList;
            String serverName;
            for(availableList = new ArrayList(); strs != null && strs.length >= 2 && strs[1].trim().length() != 0; strs = StringUtils.split(strs[1], '=')) {
               String serverName = strs[0].trim();
               strs = StringUtils.split(strs[1], ' ');
               if (strs == null || strs.length < 2) {
                  break;
               }

               serverName = strs[0].trim();
               if (this.configuredServerNamesInCluster.contains(serverName) && serverName.length() > 0) {
                  if (DEBUG) {
                     debug("Server state for " + serverName + " is " + serverName);
                  }

                  this.result.setServerState(serverName, this.machine.getName(), serverName);
                  if (this.configuredServerNamesInMachine.contains(serverName)) {
                     availableList.add(serverName);
                  }
               }
            }

            if (availableList.size() < this.configuredServerNamesInMachine.size()) {
               Iterator iter = this.configuredServerNamesInMachine.iterator();

               while(iter.hasNext()) {
                  serverName = (String)iter.next();
                  if (!availableList.contains(serverName)) {
                     if (DEBUG) {
                        debug("Server state for " + serverName + " is  set to UNKNOWN as the NM.getStates() did not return a result");
                     }

                     this.result.setServerState(serverName, this.machine.getName(), "UNKNOWN");
                  }
               }
            }

            return;
         }

         this.nullifyStates();
      } catch (IOException var11) {
         if (DEBUG) {
            debug("Invoking NodeManager.getStates() on machine " + this.machine.getName() + " got exception: " + var11);
         }

         this.nullifyStates();
         return;
      } finally {
         this.result.doneReporting(this.machine.getName());
      }

   }

   private static void recordExecutionTime(long startTime, long endTime, MachineMBean machine) {
      int recorded = (int)machineRoundTripTimes.get(machine.getName());
      int current = (int)(endTime - startTime);
      if (current > recorded) {
         machineRoundTripTimes.put(machine.getName(), (long)current);
      }

   }

   private int getTimeout(MachineMBean machine) {
      if (!this.useConnectionTimeout) {
         return 0;
      } else {
         RuntimeAccess access = ManagementService.getRuntimeAccess(kernelId);
         ClusterMBean cluster = access.getServer().getCluster();
         int configuredTimeout = cluster.getDatabaseLessLeasingBasis().getNodeManagerTimeoutMillis();
         if (configuredTimeout == 0) {
            return 0;
         } else {
            int calculatedTimeout = (int)(15L * machineRoundTripTimes.get(machine.getName()));
            return Math.min(configuredTimeout, calculatedTimeout);
         }
      }
   }

   public String getServerState(String serverName) {
      return this.result.getServerState(serverName);
   }

   public List getServersInState(String state) {
      return this.result.getServersInState(state);
   }

   public boolean isMachineUnavailable() {
      return this.machineUnavailable;
   }

   public List getServerNames() {
      return this.configuredServerNamesInMachine;
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[MachineState] " + s);
   }

   void nullifyStates() {
      for(int count = 0; count < this.configuredServerNamesInMachine.size(); ++count) {
         this.result.setServerState((String)this.configuredServerNamesInMachine.get(count), this.machine.getName(), (String)null);
      }

      this.machineUnavailable = true;
   }

   public String getMachineName() {
      return this.machine.getName();
   }

   public String toString() {
      return "MachineState for " + this.machine.getName() + " is " + this.result + " with machineUnavailable=" + this.machineUnavailable;
   }

   private static boolean debugEnabled() {
      return debugMachineState.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }
}
