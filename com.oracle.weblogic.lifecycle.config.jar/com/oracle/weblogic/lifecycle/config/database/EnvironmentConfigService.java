package com.oracle.weblogic.lifecycle.config.database;

import com.oracle.weblogic.lifecycle.LifecycleException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.inject.Inject;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.configuration.api.ChildInject;
import org.glassfish.hk2.configuration.api.ChildIterable;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/environments/environment")
public class EnvironmentConfigService extends LifecycleConfigService {
   static final String PATH_KEY = "/lifecycle-config/environments/environment";
   private static final String PATH_NAME_PREFIX = "lifecycle-config.environments";
   static final String NAME = "name";
   @Configured
   private String name;
   @Inject
   private IterableProvider allEnvironmentServices;
   @Inject
   private IterableProvider allPartitionServices;
   @ChildInject
   private ChildIterable partitionRefs;
   @ChildInject
   private ChildIterable associations;

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void createPartitionRef(Map map) {
      this.add("/lifecycle-config/environments/environment/partition-ref", PartitionRefConfigService.getInstanceId(this, map), map);
   }

   public PartitionRefConfigService createPartitionRef(PartitionConfigService ps, Properties props) {
      Map map = new HashMap();
      String id = ps.getId();
      String runtime_ref = this.getPartitionFromAllPartitions(ps.getId()).getRuntime().getName();
      map.put("id", id);
      map.put("runtimeRef", runtime_ref);
      map.put("properties", props);
      this.createPartitionRef(map);
      return this.getPartitionRefById(id);
   }

   public void deletePartitionRef(PartitionRefConfigService ref) {
      this.delete("/lifecycle-config/environments/environment/partition-ref", ref.getInstanceId());
   }

   public List getPartitionRefs() {
      return ConfigUtil.toList(this.partitionRefs);
   }

   public PartitionRefConfigService getPartitionRefById(String id) {
      return (PartitionRefConfigService)this.partitionRefs.byKey(id);
   }

   public PartitionConfigService getPartitionByName(String name) {
      Iterator var2 = this.allPartitionServices.iterator();

      PartitionConfigService ps;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ps = (PartitionConfigService)var2.next();
      } while(!name.equals(ps.getName()));

      return ps;
   }

   public PartitionRefConfigService getPartitionRefByName(String name) {
      PartitionConfigService ps = this.getPartitionByName(name);
      Iterator var3 = this.partitionRefs.iterator();

      PartitionRefConfigService ref;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         ref = (PartitionRefConfigService)var3.next();
      } while(!ps.getId().equals(ref.getId()));

      return ref;
   }

   public List getPartitions() {
      List list = new ArrayList();
      Iterator var2 = this.partitionRefs.iterator();

      while(var2.hasNext()) {
         PartitionRefConfigService ref = (PartitionRefConfigService)var2.next();
         list.add(ref.getRuntime().getPartitionById(ref.getId()));
      }

      return list;
   }

   public PartitionConfigService getPartitionById(String id) {
      PartitionRefConfigService prcs = (PartitionRefConfigService)this.partitionRefs.byKey(id);
      return prcs.getRuntime().getPartitionById(id);
   }

   public void createAssociation(Map map) throws LifecycleException {
      String partition1 = (String)map.get("partition1");
      String partition2 = (String)map.get("partition2");
      if (this.isPartitionValid(partition1) && this.isPartitionValid(partition2)) {
         this.add("/lifecycle-config/environments/environment/association", AssociationConfigService.getInstanceId(this, map), map);
      } else {
         throw new LifecycleException(String.format("Invalid partition for association : %s,%s", partition1, partition2));
      }
   }

   public AssociationConfigService createAssociation(PartitionConfigService partition1, PartitionConfigService partition2) throws LifecycleException {
      return this.createAssociation(partition1.getId(), partition2.getId());
   }

   public AssociationConfigService createAssociation(PartitionRefConfigService partition1, PartitionRefConfigService partition2) throws LifecycleException {
      return this.createAssociation(partition1.getId(), partition2.getId());
   }

   public AssociationConfigService createAssociation(String partition1Id, String partition2Id) throws LifecycleException {
      Map map = new HashMap();
      map.put("partition1", partition1Id);
      map.put("partition2", partition2Id);
      this.createAssociation(map);
      return this.getAssociation(partition1Id, partition2Id);
   }

   public AssociationConfigService getAssociation(String partition1Id, String partition2Id) {
      Iterator var3 = this.associations.iterator();

      AssociationConfigService association;
      String id1;
      String id2;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         association = (AssociationConfigService)var3.next();
         id1 = association.getPartition1().getId();
         id2 = association.getPartition2().getId();
         if (id1.equals(partition1Id) && id2.equals(partition2Id)) {
            return association;
         }
      } while(!id1.equals(partition2Id) || !id2.equals(partition1Id));

      return association;
   }

   public List findAssociations(PartitionConfigService partition) {
      List list = new ArrayList();
      Iterator var3 = this.associations.iterator();

      while(true) {
         AssociationConfigService association;
         do {
            if (!var3.hasNext()) {
               return list;
            }

            association = (AssociationConfigService)var3.next();
         } while(!association.getPartition1().getId().equals(partition.getId()) && !association.getPartition2().getId().equals(partition.getId()));

         list.add(association);
      }
   }

   public void removeAssociation(AssociationConfigService association) {
      this.delete("/lifecycle-config/environments/environment/association", association.getInstanceId());
   }

   public AssociationConfigService removeAssociation(String id1, String id2) {
      AssociationConfigService association = this.getAssociation(id1, id2);
      if (association != null) {
         this.removeAssociation(association);
      }

      return association;
   }

   public List getAssociations() {
      return ConfigUtil.toList(this.associations);
   }

   static String getInstanceId(Map map) {
      return ConfigUtil.addWithSeparator("lifecycle-config.environments", (String)map.get("name"));
   }

   private boolean isPartitionValid(String partitionId) {
      return partitionId != null && this.partitionRefs.byKey(partitionId) != null;
   }

   private PartitionConfigService getPartitionFromAllPartitions(String id) {
      Iterator var2 = this.allPartitionServices.iterator();

      PartitionConfigService ps;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ps = (PartitionConfigService)var2.next();
      } while(!ps.getId().equals(id));

      return ps;
   }

   public List removeAssociations(PartitionConfigService ps) {
      List associations = this.findAssociations(ps);
      Iterator var3 = associations.iterator();

      while(var3.hasNext()) {
         AssociationConfigService association = (AssociationConfigService)var3.next();
         this.removeAssociation(association);
      }

      return associations;
   }
}
