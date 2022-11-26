package weblogic.server;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.utils.PartitionUtils;
import weblogic.management.utils.TargetingAnalyzer;

@PerLookup
@Service
public class TargetingAnalyzerImpl implements TargetingAnalyzer {
   private CrossReferenceAnalyzer partitionServerReferences;
   private CrossReferenceAnalyzer resourceGroupServerReferences;

   private static String extendedResourceGroupName(ResourceGroupMBean rg) {
      PartitionMBean parentPartition = rg.getParent() instanceof PartitionMBean ? (PartitionMBean)rg.getParent() : null;
      return (parentPartition != null ? parentPartition.getName() + '/' : "") + rg.getName();
   }

   public void init(DomainMBean current, DomainMBean proposed) {
      this.partitionServerReferences = this.preparePartitionServerReferences(current, proposed);
      this.resourceGroupServerReferences = this.prepareResourceGroupServerReferences(current, proposed);
   }

   private CrossReferenceAnalyzer preparePartitionServerReferences(DomainMBean current, DomainMBean proposed) {
      CrossReferenceAnalyzer result = new CrossReferenceAnalyzer((Iterator)null, (Iterator)null);
      return result;
   }

   private CrossReferenceAnalyzer prepareResourceGroupServerReferences(DomainMBean current, DomainMBean proposed) {
      ResourceGroupMBean[] currentRGs = buildRGCollection(current);
      ResourceGroupMBean[] proposedRGs = buildRGCollection(proposed);
      CrossReferenceAnalyzer result = new CrossReferenceAnalyzer(new ResourceGroupToServerIterator(currentRGs), new ResourceGroupToServerIterator(proposedRGs));
      return result;
   }

   private static ResourceGroupMBean[] buildRGCollection(DomainMBean root) {
      return new ResourceGroupMBean[0];
   }

   public boolean isAddedToServer(PartitionMBean partition, String serverName) {
      return this.partitionServerReferences.isReferenceAdded(partition.getName(), serverName);
   }

   public boolean isAddedToServer(ResourceGroupMBean rg, String serverName) {
      return this.resourceGroupServerReferences.isReferenceAdded(extendedResourceGroupName(rg), serverName);
   }

   public boolean isRemovedFromServer(PartitionMBean partition, String serverName) {
      return this.partitionServerReferences.isReferenceRemoved(partition.getName(), serverName);
   }

   public boolean isRemovedFromServer(ResourceGroupMBean rg, String serverName) {
      return this.resourceGroupServerReferences.isReferenceRemoved(extendedResourceGroupName(rg), serverName);
   }

   public boolean isAdded(PartitionMBean partition) {
      return this.partitionServerReferences.isOriginAdded(partition.getName());
   }

   public boolean isAdded(ResourceGroupMBean rg) {
      return this.resourceGroupServerReferences.isOriginAdded(extendedResourceGroupName(rg));
   }

   public boolean isRemoved(PartitionMBean partition) {
      return this.partitionServerReferences.isOriginRemoved(partition.getName());
   }

   public boolean isRemoved(ResourceGroupMBean rg) {
      return this.resourceGroupServerReferences.isOriginRemoved(extendedResourceGroupName(rg));
   }

   public Collection getResourceGroupNamesLeavingAndJoiningServers() {
      return this.resourceGroupServerReferences.getOriginsLosingAndGainingReferences();
   }

   private static class ResourceGroupToServerIterator extends ResourceCollectionToServerIterator {
      private ResourceGroupToServerIterator(ResourceGroupMBean[] partitions) {
         super(partitions, null);
      }

      public Set getServers(ResourceGroupMBean collection) {
         return PartitionUtils.getServers(collection);
      }

      public String getIdentifier(ResourceGroupMBean collection) {
         return TargetingAnalyzerImpl.extendedResourceGroupName(collection);
      }

      // $FF: synthetic method
      ResourceGroupToServerIterator(ResourceGroupMBean[] x0, Object x1) {
         this(x0);
      }
   }

   private static class PartitionToServerIterator extends ResourceCollectionToServerIterator {
      private PartitionToServerIterator(PartitionMBean[] partitions) {
         super(partitions, null);
      }

      public Set getServers(PartitionMBean collection) {
         return PartitionUtils.getServers(collection);
      }

      public String getIdentifier(PartitionMBean collection) {
         return collection.getName();
      }
   }

   private abstract static class ResourceCollectionToServerIterator implements Iterator {
      private int nextSlot;
      private final ConfigurationMBean[] serverCollections;

      private ResourceCollectionToServerIterator(ConfigurationMBean[] serverCollections) {
         this.nextSlot = 0;
         this.serverCollections = serverCollections;
      }

      public boolean hasNext() {
         return this.nextSlot < this.serverCollections.length;
      }

      public Map.Entry next() {
         ConfigurationMBean nextCollection = this.serverCollections[this.nextSlot++];
         return new AbstractMap.SimpleImmutableEntry(this.getIdentifier(nextCollection), this.getServers(nextCollection));
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public abstract Set getServers(ConfigurationMBean var1);

      public abstract Object getIdentifier(ConfigurationMBean var1);

      // $FF: synthetic method
      ResourceCollectionToServerIterator(ConfigurationMBean[] x0, Object x1) {
         this(x0);
      }
   }
}
