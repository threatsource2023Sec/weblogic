package com.bea.adaptive.harvester.jmx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.LocatorUtilities;

public class MetricTypeInfo {
   private static DebugLogger dbg = DebugLogger.getDebugLogger("DebugHarvesterTypeInfoCache");
   private static PartitionRuntimeMBeanSetManager partitionMBeanSet = (PartitionRuntimeMBeanSetManager)LocatorUtilities.getService(PartitionRuntimeMBeanSetManager.class);
   private MetricMetaDataResolver resolver;
   private String type;
   private List attributes;
   private List partitionVisibleAttributes;
   private Set instances = new ConcurrentSkipListSet();

   public MetricTypeInfo(String typeName, MetricMetaDataResolver resolver) {
      this.type = typeName;
      this.resolver = resolver;
   }

   public boolean equals(Object obj) {
      boolean equal = this == obj;
      if (!equal && obj instanceof MetricTypeInfo) {
         equal = this.type.equals(((MetricTypeInfo)obj).type);
      }

      return equal;
   }

   public int hashCode() {
      return this.type.hashCode();
   }

   Set getInstances() {
      return this.instances;
   }

   public List getAttributes() {
      String sampleInstance = null;
      synchronized(this) {
         if (this.attributes == null) {
            if (this.instances.size() > 0) {
               sampleInstance = (String)this.instances.iterator().next();
            }

            this.attributes = this.resolver.getHarvestableAttributesForInstance(sampleInstance, this.type);
            this.partitionVisibleAttributes = new ArrayList();
            if (this.attributes != null && this.attributes.size() > 0) {
               Iterator var3 = this.attributes.iterator();

               while(var3.hasNext()) {
                  AttributeSpec attribute = (AttributeSpec)var3.next();
                  if (attribute.isVisibleToPartitions()) {
                     this.partitionVisibleAttributes.add(attribute);
                  }
               }
            }
         }
      }

      if (dbg.isDebugEnabled() && sampleInstance != null) {
         dbg.debug("Loaded attribute cache using instance --> " + sampleInstance);
      }

      return this.attributes;
   }

   public AttributeSpec lookupAttributeSpec(String attributeName) {
      AttributeSpec returnSpec = this.attributeCacheLookup(attributeName);
      if (returnSpec == null) {
         if (dbg.isDebugEnabled()) {
            dbg.debug("Attribute cache miss [" + attributeName + ", " + this.type + "], reloading cache");
         }

         synchronized(this) {
            this.attributes = null;
            this.getAttributes();
         }

         returnSpec = this.attributeCacheLookup(attributeName);
      }

      if (returnSpec == null) {
         returnSpec = this.checkComplexAttribute(attributeName);
      }

      return returnSpec;
   }

   public String getType() {
      return this.type;
   }

   private AttributeSpec checkComplexAttribute(String attributeName) {
      AttributeTerm evalChain = AttributeTermFactory.parseAttributeChain(attributeName);
      AttributeSpec newSpec = null;
      if (evalChain.isComplex()) {
         String rootName = evalChain.getName();
         Iterator it = this.attributes.iterator();

         while(it.hasNext()) {
            AttributeSpec spec = (AttributeSpec)it.next();
            if (rootName.equals(spec.getName())) {
               newSpec = new AttributeSpec(attributeName, this.type, spec.getDataType(), false, evalChain);
               this.attributes.add(newSpec);
               if (dbg.isDebugEnabled()) {
                  dbg.debug("New complex attribute spec --> [" + attributeName + ", " + this.type);
               }
               break;
            }
         }
      }

      return newSpec;
   }

   private AttributeSpec attributeCacheLookup(String attributeName) {
      AttributeSpec spec = null;
      if (this.attributes != null) {
         Iterator it = this.attributes.iterator();

         while(it.hasNext()) {
            AttributeSpec currentSpec = (AttributeSpec)it.next();
            if (attributeName.equals(currentSpec.getName())) {
               spec = currentSpec;
               if (dbg.isDebugEnabled()) {
                  dbg.debug("Attribute cache hit --> [" + attributeName + ", " + this.type);
               }
               break;
            }
         }
      }

      return spec;
   }

   public boolean hasPartitionVisibleAttributes() {
      if (this.partitionVisibleAttributes == null) {
         this.getAttributes();
      }

      return this.partitionVisibleAttributes != null ? this.partitionVisibleAttributes.size() > 0 : false;
   }

   public List getPartitionVisibleAttributeSpecs() {
      return this.hasPartitionVisibleAttributes() ? new ArrayList(this.partitionVisibleAttributes) : new ArrayList();
   }

   public boolean isTypeImplicitlyPartitionVisible() {
      return partitionMBeanSet != null ? partitionMBeanSet.isTypeInSet(this.type) : false;
   }

   public static boolean isTypeImplicitlyPartitionVisible(String typeName) {
      return partitionMBeanSet != null ? partitionMBeanSet.isTypeInSet(typeName) : false;
   }
}
