package weblogic.management.partition.admin;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.AccessController;
import java.util.Arrays;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.collections.PartitionMatchMap;

public final class PartitionTableImpl extends PartitionTable {
   static final boolean USE_MATCH_MAP_SINGLETON = Boolean.parseBoolean(System.getProperty("partitiontable.useMatchMapSingleton", "true"));
   private static final DebugLogger debugPartitionTable = DebugLogger.getDebugLogger("DebugPartitionTable");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String GLOBAL_PARTITION_ID = "0";
   private static final TargetMBean[] NO_TARGETS = new TargetMBean[0];
   private static final PartitionMBean[] NO_PARTITIONS = new PartitionMBean[0];

   public PartitionTableEntry lookup(String urlString) throws URISyntaxException {
      if (debugPartitionTable.isDebugEnabled()) {
         debugPartitionTable.debug("PartitionTable lookup(String) called for this URL: " + urlString);
      }

      return this.lookup(new URI(urlString));
   }

   public PartitionTableEntry lookup(URI uri) {
      if (debugPartitionTable.isDebugEnabled()) {
         debugPartitionTable.debug("PartitionTable lookup(URI) called for this URL: " + uri.toString());
      }

      DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      PartitionMBean partition = this.getPartitionFromHostAndPath(domainMBean, uri);
      if (partition == null) {
         partition = this.getPartitionFromReservedUriPath(domainMBean, uri);
      }

      if (partition == null) {
         partition = this.getPartitionFromQuery(domainMBean, uri);
      }

      if (debugPartitionTable.isDebugEnabled()) {
         if (partition != null) {
            debugPartitionTable.debug("PartitionTable lookup(URI) returned partition: " + partition.getName());
         } else {
            debugPartitionTable.debug("PartitionTable lookup(URI) returned partition: null");
         }
      }

      return partition != null ? this.createPartitionTableEntry(partition) : this.getGlobalPartitionTableEntry();
   }

   private PartitionMBean getPartitionFromHostAndPath(DomainMBean domainMBean, URI uri) {
      return this.getBestMatch(uri, createMatchMap(domainMBean));
   }

   private PartitionMBean getBestMatch(URI uri, PartitionMatchMap matchMap) {
      return (PartitionMBean)matchMap.match(uri.getHost(), uri.getPort(), uri.getPath());
   }

   private static PartitionMatchMap createMatchMap(DomainMBean domainMBean) {
      if (USE_MATCH_MAP_SINGLETON) {
         PartitionMatchMapSingleton.getInstance();
         return PartitionMatchMapSingleton.getPartitionMatchMap();
      } else {
         PartitionMatchMap matchMap = new PartitionMatchMap(true);
         PartitionMBean[] var2 = getPartitions(domainMBean);
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PartitionMBean partition = var2[var4];
            addPartitionTargetsToMap(matchMap, partition);
         }

         return matchMap;
      }
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
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
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
      String[] var3 = target.getHostNames();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String hostname = var3[var5];
         matchMap.put(hostname, PartitionUtils.getPortNumber(target), target.getUriPrefix(), partition);
      }

      if (target.getHostNames().length == 0) {
         matchMap.put((String)null, PartitionUtils.getPortNumber(target), target.getUriPrefix(), partition);
      }

   }

   private PartitionTableEntry createPartitionTableEntry(PartitionMBean partition) {
      return new PartitionTableEntry(partition.getName(), partition.getPartitionID(), partition.getSystemFileSystem().getRoot(), partition.getUserFileSystem().getRoot());
   }

   private PartitionMBean getPartitionFromReservedUriPath(DomainMBean domain, URI uri) {
      String partitionName = this.getPartitionNameFromReservedUriPath(uri.getPath());
      return partitionName == null ? null : this.getPartitionBean(domain, partitionName);
   }

   private PartitionMBean getPartitionFromQuery(DomainMBean domain, URI uri) {
      String partitionName = this.getPartitionNameFromQuery(uri.getQuery());
      return partitionName == null ? null : domain.lookupPartition(partitionName);
   }

   private PartitionMBean getPartitionBean(DomainMBean domain, String partitionName) {
      PartitionMBean partition = domain.lookupPartition(partitionName);
      if (partition == null) {
         throw new IllegalArgumentException("URL points to non-existing partition: " + partitionName);
      } else {
         return partition;
      }
   }

   private String getPartitionNameFromReservedUriPath(String path) {
      if (path != null && path.startsWith("/partitions/")) {
         int startIndex = "/partitions/".length();
         int endIndex = this.getEndIndexForPathSegment(path, startIndex);
         return startIndex == endIndex ? null : path.substring(startIndex, endIndex);
      } else {
         return null;
      }
   }

   private int getEndIndexForPathSegment(String path, int startIndex) {
      int slashIndex = path.indexOf("/", startIndex);
      return slashIndex == -1 ? path.length() : slashIndex;
   }

   private String getPartitionNameFromQuery(String query) {
      if (query != null && query.contains("partitionName=")) {
         int startIndex = query.indexOf("partitionName=") + "partitionName=".length();
         int endIndex = this.getEndIndexForQueryParameter(query, startIndex);
         return query.substring(startIndex, endIndex);
      } else {
         return null;
      }
   }

   private int getEndIndexForQueryParameter(String query, int startIndex) {
      int ampersandIndex = query.indexOf("&", startIndex);
      return ampersandIndex == -1 ? query.length() : ampersandIndex;
   }

   public PartitionTableEntry lookup(InetSocketAddress socketAddress) {
      PartitionMBean partition = this.getPartitionFromAddress(ManagementService.getRuntimeAccess(kernelId).getDomain(), socketAddress);
      return partition == null ? this.getGlobalPartitionTableEntry() : this.createPartitionTableEntry(partition);
   }

   private PartitionMBean getPartitionFromAddress(DomainMBean domainMBean, InetSocketAddress address) {
      PartitionMBean[] var3 = getPartitions(domainMBean);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PartitionMBean partition = var3[var5];
         if (this.hasMatchingAddress(partition, address)) {
            return partition;
         }
      }

      return null;
   }

   private boolean hasMatchingAddress(PartitionMBean partition, InetSocketAddress address) {
      TargetMBean[] var3 = getTargets(partition);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TargetMBean target = var3[var5];
         if (this.hasMatchingAddress(target, address)) {
            return true;
         }
      }

      return false;
   }

   private boolean hasMatchingAddress(TargetMBean target, InetSocketAddress address) {
      return target instanceof VirtualTargetMBean && this.hasMatchingAddress((VirtualTargetMBean)target, address);
   }

   private boolean hasMatchingAddress(VirtualTargetMBean virtualTarget, InetSocketAddress address) {
      return PartitionUtils.getPortNumber(virtualTarget) == address.getPort() && this.hasHostName(virtualTarget, address.getHostString());
   }

   private boolean hasHostName(VirtualTargetMBean virtualTarget, String hostString) {
      return this.matchesAnyHost(virtualTarget) || Arrays.asList(virtualTarget.getHostNames()).contains(hostString);
   }

   private boolean matchesAnyHost(VirtualTargetMBean virtualTarget) {
      return virtualTarget.getHostNames().length == 0;
   }

   public PartitionTableEntry lookupByName(String name) {
      if (this.getGlobalPartitionName().equals(name)) {
         return this.getGlobalPartitionTableEntry();
      } else {
         DomainMBean domainBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
         PartitionMBean[] var3 = getPartitions(domainBean);
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PartitionMBean partition = var3[var5];
            if (partition.getName().equals(name)) {
               return this.createPartitionTableEntry(partition);
            }
         }

         throw new IllegalArgumentException("There is no partition for name: " + name);
      }
   }

   public PartitionTableEntry lookupByID(String id) {
      if (this.getGlobalPartitionId().equals(id)) {
         return this.getGlobalPartitionTableEntry();
      } else {
         DomainMBean domainBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
         PartitionMBean[] var3 = getPartitions(domainBean);
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PartitionMBean partition = var3[var5];
            if (partition.getPartitionID().equals(id)) {
               return this.createPartitionTableEntry(partition);
            }
         }

         throw new IllegalArgumentException("There is no partition for ID: " + id);
      }
   }

   public String getGlobalPartitionId() {
      return "0";
   }

   private PartitionTableEntry getGlobalPartitionTableEntry() {
      return new PartitionTableEntry("DOMAIN", "0", (String)null, (String)null);
   }
}
