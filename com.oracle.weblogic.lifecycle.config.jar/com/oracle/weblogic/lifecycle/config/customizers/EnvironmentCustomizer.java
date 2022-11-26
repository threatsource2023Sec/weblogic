package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Association;
import com.oracle.weblogic.lifecycle.config.Associations;
import com.oracle.weblogic.lifecycle.config.Environment;
import com.oracle.weblogic.lifecycle.config.Partition;
import com.oracle.weblogic.lifecycle.config.PartitionRef;
import com.oracle.weblogic.lifecycle.config.PropertyBean;
import com.oracle.weblogic.lifecycle.config.Runtime;
import java.beans.PropertyVetoException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class EnvironmentCustomizer {
   @Inject
   private XmlService xmlService;
   @Inject
   private ServiceLocator locator;

   public List getPartitions(Environment environment) {
      List partitionRefs = environment.getPartitionRefs();
      List partitions = new ArrayList(partitionRefs.size());
      Iterator var4 = partitionRefs.iterator();

      while(var4.hasNext()) {
         PartitionRef partitionRef = (PartitionRef)var4.next();
         partitions.add(this.getPartitionById(environment, partitionRef.getId()));
      }

      return partitions;
   }

   public Partition getPartitionById(Environment environment, String id) {
      return (Partition)this.locator.getService(Partition.class, id, new Annotation[0]);
   }

   public PartitionRef getPartitionRefById(Environment environment, String id) {
      return (PartitionRef)this.locator.getService(PartitionRef.class, id, new Annotation[0]);
   }

   public PartitionRef getPartitionRefByTypeAndName(Environment environment, String type, String name) {
      List partitionRefs = environment.getPartitionRefs();
      Iterator var5 = partitionRefs.iterator();

      while(var5.hasNext()) {
         PartitionRef partitionRef = (PartitionRef)var5.next();
         Runtime runtime = partitionRef.getRuntime();
         if (type.equals(runtime.getType())) {
            Partition partition = runtime.getPartitionByName(name);
            if (partition != null && partition.getId().equals(partitionRef.getId())) {
               return partitionRef;
            }
         }
      }

      return null;
   }

   public PartitionRef createPartitionRef(Environment environment, Partition partition, Properties properties) {
      PartitionRef retVal = (PartitionRef)this.xmlService.createBean(PartitionRef.class);

      try {
         retVal.setId(partition.getId());
         retVal.setRuntimeRef(partition.getRuntime().getName());
      } catch (PropertyVetoException var10) {
         throw new RuntimeException(var10);
      }

      PropertyBean property;
      for(Iterator var5 = properties.stringPropertyNames().iterator(); var5.hasNext(); retVal.addProperty(property)) {
         String propertyName = (String)var5.next();
         property = (PropertyBean)this.xmlService.createBean(PropertyBean.class);

         try {
            property.setName(propertyName);
            property.setValue(properties.getProperty(propertyName));
         } catch (Exception var9) {
            throw new RuntimeException(var9);
         }
      }

      return environment.addPartitionRef(retVal);
   }

   public PartitionRef deletePartitionRef(Environment environment, PartitionRef partitionRef) {
      return partitionRef == null ? null : environment.removePartitionRef(partitionRef);
   }

   private static void validateEnvironmentPartition(Environment environment, String partitionId) throws IllegalArgumentException {
      if (environment.getPartitionRefById(partitionId) == null) {
         throw new IllegalArgumentException("Partition " + partitionId + " is not added to the environment.");
      }
   }

   public Association createAssociation(Environment environment, Partition partition1, Partition partition2) {
      validateEnvironmentPartition(environment, partition1.getId());
      validateEnvironmentPartition(environment, partition2.getId());
      List existingAssociations = this.findAssociations(environment, partition1);
      String[] partitionIds = new String[]{partition1.getId(), partition2.getId()};
      Arrays.sort(partitionIds);
      Iterator var6 = existingAssociations.iterator();

      String[] existingPartitionIds;
      do {
         Association association;
         if (!var6.hasNext()) {
            Associations associations = environment.getAssociations();
            association = (Association)this.xmlService.createBean(Association.class);
            association.setPartition1(partition1);
            association.setPartition2(partition2);
            return associations.addAssociation(association);
         }

         association = (Association)var6.next();
         existingPartitionIds = new String[]{association.getPartition1().getId(), association.getPartition2().getId()};
         Arrays.sort(existingPartitionIds);
      } while(!Arrays.equals(existingPartitionIds, partitionIds));

      throw new IllegalArgumentException("Partitions " + partition1.getId() + " and " + partition2.getId() + " are already associated.");
   }

   public Association createAssociation(Environment environment, PartitionRef partition1, PartitionRef partition2) {
      Partition p1 = this.getPartitionById(environment, partition1.getId());
      Partition p2 = this.getPartitionById(environment, partition2.getId());
      return this.createAssociation(environment, p1, p2);
   }

   public Association createAssociation(Environment environment, String partition1, String partition2) {
      Partition p1 = this.getPartitionById(environment, partition1);
      Partition p2 = this.getPartitionById(environment, partition2);
      return this.createAssociation(environment, p1, p2);
   }

   public Association removeAssociation(Environment environment, Partition partition1, Partition partition2) {
      Associations associations = environment.getAssociations();
      if (associations == null) {
         return null;
      } else {
         List existingAssocations = this.findAssociations(environment, partition1);
         if (existingAssocations == null) {
            return null;
         } else {
            String[] partitionIds = new String[]{partition1.getId(), partition2.getId()};
            Arrays.sort(partitionIds);
            Iterator var7 = existingAssocations.iterator();

            Association association;
            String[] existingPartitionIds;
            do {
               if (!var7.hasNext()) {
                  return null;
               }

               association = (Association)var7.next();
               existingPartitionIds = new String[]{association.getPartition1().getId(), association.getPartition2().getId()};
               Arrays.sort(existingPartitionIds);
            } while(!Arrays.equals(existingPartitionIds, partitionIds));

            return associations.removeAssociation(association);
         }
      }
   }

   public Association removeAssociation(Environment environment, PartitionRef partition1, PartitionRef partition2) {
      Partition p1 = this.getPartitionById(environment, partition1.getId());
      Partition p2 = this.getPartitionById(environment, partition2.getId());
      return p1 != null && p2 != null ? this.removeAssociation(environment, p1, p2) : null;
   }

   public Association removeAssociation(Environment environment, String partition1, String partition2) {
      Partition p1 = this.getPartitionById(environment, partition1);
      Partition p2 = this.getPartitionById(environment, partition2);
      return p1 != null && p2 != null ? this.removeAssociation(environment, p1, p2) : null;
   }

   public List findAssociations(Environment environment, Partition partition) {
      Associations associations = environment.getAssociations();
      List associationList = associations.getAssociations();
      List result = new ArrayList(associationList.size());
      String partitionId = partition.getId();
      Iterator var7 = associationList.iterator();

      while(true) {
         Association association;
         do {
            if (!var7.hasNext()) {
               return result;
            }

            association = (Association)var7.next();
         } while(!association.getPartition1().getId().equals(partitionId) && !association.getPartition2().getId().equals(partitionId));

         result.add(association);
      }
   }

   public List findAssociations(Environment environment, PartitionRef partition) {
      Partition p = this.getPartitionById(environment, partition.getId());
      return p != null ? this.findAssociations(environment, p) : null;
   }

   public List removeAssociations(Environment environment, Partition partition) {
      List existingAssociations = this.findAssociations(environment, partition);
      if (existingAssociations == null) {
         return null;
      } else {
         Associations associations = environment.getAssociations();
         Iterator var5 = existingAssociations.iterator();

         while(var5.hasNext()) {
            Association association = (Association)var5.next();
            associations.removeAssociation(association);
         }

         return existingAssociations;
      }
   }

   public List removeAssociations(Environment environment, PartitionRef partition) {
      return this.removeAssociations(environment, partition.getId());
   }

   public List removeAssociations(Environment environment, String partition) {
      Partition p = this.getPartitionById(environment, partition);
      return p == null ? Collections.emptyList() : this.removeAssociations(environment, p);
   }
}
