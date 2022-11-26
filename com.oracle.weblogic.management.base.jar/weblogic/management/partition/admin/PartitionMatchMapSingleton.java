package weblogic.management.partition.admin;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Arrays;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.collections.PartitionMatchMap;

public final class PartitionMatchMapSingleton {
   private static final TargetMBean[] NO_TARGETS = new TargetMBean[0];
   private static final PartitionMBean[] NO_PARTITIONS = new PartitionMBean[0];
   private static final DebugLogger debugPartitionTable = DebugLogger.getDebugLogger("DebugPartitionTable");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static boolean isAdminServer;
   private static ServerMBean server;
   private static PartitionMatchMap matchMap;

   public static PartitionMatchMapSingleton getInstance() {
      return PartitionMatchMapSingleton.Initializer.SINGLETON;
   }

   public static PartitionMatchMap getPartitionMatchMap() {
      return matchMap;
   }

   private static void setPartitionMatchMap(PartitionMatchMap newMatchMap) {
      matchMap = newMatchMap;
   }

   public static void createMatchMap(DomainMBean domainMBean) {
      PartitionMatchMap newMatchMap = new PartitionMatchMap(true);
      PartitionMBean[] var2 = getPartitions(domainMBean);
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PartitionMBean partition = var2[var4];
         addPartitionTargetsToMap(newMatchMap, partition);
      }

      setPartitionMatchMap(newMatchMap);
   }

   private static PartitionMBean[] getPartitions(DomainMBean domainMBean) {
      return NO_PARTITIONS;
   }

   private static void addPartitionTargetsToMap(PartitionMatchMap matchMap, PartitionMBean partition) {
      TargetMBean[] var2 = getTargets(partition);
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean target = var2[var4];
         if (target instanceof VirtualHostMBean) {
            addToMatchMap(matchMap, partition, (VirtualHostMBean)target);
         } else if (target instanceof VirtualTargetMBean) {
            addToMatchMap(matchMap, partition, (VirtualTargetMBean)target);
         }
      }

   }

   private static TargetMBean[] getTargets(PartitionMBean partition) {
      TargetMBean[] targets;
      if (isAdminServer) {
         targets = partition.findEffectiveAvailableTargets();
      } else {
         targets = partition.findEffectiveTargets();
      }

      return targets != null ? targets : NO_TARGETS;
   }

   private static void addToMatchMap(PartitionMatchMap matchMap, PartitionMBean partition, VirtualHostMBean target) {
      String[] var3 = target.getVirtualHostNames();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String hostname = var3[var5];
         matchMap.put(hostname, 0, target.getUriPath(), partition);
      }

   }

   private static void addToMatchMap(PartitionMatchMap matchMap, PartitionMBean partition, VirtualTargetMBean target) {
      WorkingVirtualTargetManager workingVirtualTargetManager = (WorkingVirtualTargetManager)GlobalServiceLocator.getServiceLocator().getService(WorkingVirtualTargetManager.class, new Annotation[0]);
      VirtualTargetMBean workingTarget = target;
      if (workingVirtualTargetManager != null) {
         workingTarget = workingVirtualTargetManager.lookupWorkingVirtualTarget(target);
      }

      if (debugPartitionTable.isDebugEnabled()) {
         debugPartitionTable.debug("PartitionMatchMapSingleton addToMatchMap called for partition " + partition.getName() + " and VirtualTarget " + workingTarget.getName());
         debugPartitionTable.debug("VirtualTarget is targeted to servers: " + Arrays.toString(workingTarget.getServerNames().toArray()));
         debugPartitionTable.debug("Current server name is: " + server.getName());
      }

      if (isAdminServer || workingTarget.getServerNames().contains(server.getName())) {
         String[] var5 = workingTarget.getHostNames();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String hostname = var5[var7];
            if (debugPartitionTable.isDebugEnabled()) {
               debugPartitionTable.debug("Adding entry to PartitionMatchMap. Hostname:" + hostname + " URI prefix:" + workingTarget.getUriPrefix());
            }

            matchMap.put(hostname, PartitionUtils.getPortNumber(workingTarget), workingTarget.getUriPrefix(), partition);
         }

         if (workingTarget.getHostNames().length == 0) {
            if (debugPartitionTable.isDebugEnabled()) {
               debugPartitionTable.debug("Adding entry to PartitionMatchMap. Hostname:null URI prefix:" + workingTarget.getUriPrefix());
            }

            matchMap.put((String)null, PartitionUtils.getPortNumber(workingTarget), workingTarget.getUriPrefix(), partition);
         }

      }
   }

   static {
      isAdminServer = ManagementService.getRuntimeAccess(kernelId).isAdminServer();
      server = ManagementService.getRuntimeAccess(kernelId).getServer();
      matchMap = new PartitionMatchMap(true);
   }

   private static class Initializer {
      static final PartitionMatchMapSingleton SINGLETON = new PartitionMatchMapSingleton();
   }
}
