package com.bea.adaptive.harvester.jmx;

import com.bea.adaptive.mbean.typing.MBeanCategorizer;
import com.bea.adaptive.mbean.typing.MBeanTypeUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;

class MBeanRegistrationManager extends RegistrationManager implements MBeanTypeUtil.RegHandler {
   private static final String UNHARVESTABLE_TAG_PREFIX = "com.bea.";
   private static final String UNHARVESTABLE_MBI_TAG_NAME = "com.bea." + getUnharvestableDescriptorTag();
   private MBeanServerConnection mBeanServer;
   private MBeanCategorizer categorizer;
   Object regID;
   MBeanTypeUtil typeUtil;

   MBeanRegistrationManager(String harvesterName, MBeanServerConnection mBeanServer, MBeanCategorizer categorizer, MetricCacheListener cacheListener, HarvesterDebugLogger logger, String[] priorityMBeanPatterns, Executor executor) throws IOException, JMException {
      super(logger, cacheListener, new MBeanTypeResolver(logger, categorizer, mBeanServer, getUnharvestableAttributeDescriptors()));
      this.mBeanServer = mBeanServer;
      this.categorizer = categorizer;
      this.typeUtil = new MBeanTypeUtil(mBeanServer, this.categorizer, harvesterName, priorityMBeanPatterns, executor);
      this.regID = this.typeUtil.addRegistrationHandler(this);
   }

   MBeanRegistrationManager() {
   }

   public void shutdown(boolean forceShutdown) {
      try {
         if (this.regID != null) {
            this.typeUtil.shutdown(this.regID, forceShutdown);
         }
      } catch (Exception var3) {
         if (this.dbg.isDebugCEnabled()) {
            this.dbg.dbgC("An exception occurred whilst shutting down the RegManager.  Ignoring...   Exception: " + var3.getMessage());
            var3.printStackTrace();
         }
      }

      this.regID = null;
      this.typeUtil = null;
   }

   public String getDescriptionForType(String typeName) {
      String descr = mtf_base.getUnknownLabel();
      MetricTypeInfo typeInfo = super.lookupTypeInfo(typeName);
      if (typeInfo != null) {
         Set instances = typeInfo.getInstances();
         if (instances.size() != 0) {
            Iterator iterator = instances.iterator();

            while(iterator.hasNext()) {
               MBeanInfo mbi = null;
               String instName = (String)iterator.next();

               try {
                  ObjectName objectName = new ObjectName(instName);
                  mbi = this.mBeanServer.getMBeanInfo(objectName);
                  if (mbi != null) {
                     descr = mbi.getDescription();
                     break;
                  }
               } catch (Exception var9) {
                  if (this.dbg.isDebugHEnabled()) {
                     this.dbg.dbgH("Caught exception for instance " + instName, var9);
                  }
               }
            }
         }
      }

      return descr;
   }

   private static class MBeanTypeResolver implements MetricMetaDataResolver {
      private HarvesterDebugLogger dbg;
      private MBeanCategorizer categorizer;
      private MBeanServerConnection mbeanServer;
      private String[] unharvestableAttrDescriptors;

      public MBeanTypeResolver(HarvesterDebugLogger debug, MBeanCategorizer categorizer, MBeanServerConnection mbs, String[] unharvestableDescriptorTags) {
         this.dbg = debug;
         this.categorizer = categorizer;
         this.mbeanServer = mbs;
         this.unharvestableAttrDescriptors = unharvestableDescriptorTags;
      }

      public List getHarvestableAttributesForInstance(final String instName, final String type) {
         return (List)RegistrationManager.runAsDomainPartition(new Callable() {
            public List call() throws Exception {
               return MBeanTypeResolver.this.getHarvestableAttributesSpecs(instName, type, (String)null);
            }
         });
      }

      public String getTypeForInstance(final String instanceName) {
         return (String)RegistrationManager.runAsDomainPartition(new Callable() {
            public String call() throws Exception {
               String typeName = null;

               try {
                  MBeanCategorizer.TypeInfo typeInfo = MBeanTypeResolver.this.categorizer.getTypeInfo(MBeanTypeResolver.this.mbeanServer, new ObjectName(instanceName));
                  if (typeInfo != null) {
                     typeName = typeInfo.getTypeName();
                  }

                  return typeName;
               } catch (MalformedObjectNameException var3) {
                  throw new RuntimeException(var3);
               }
            }
         });
      }

      private List getHarvestableAttributesSpecs(String instName, String typeName, String attrNameRegex) {
         List attributeList = new ArrayList();
         if (instName != null && typeName != null) {
            MBeanInfo mbi = null;

            try {
               ObjectName instanceObjectName = new ObjectName(instName);
               mbi = this.mbeanServer.getMBeanInfo(instanceObjectName);
               if (this.dbg.isDebugREnabled()) {
                  this.dbg.dbgR("Attibute types for " + typeName + " " + instName);
               }

               MBeanAttributeInfo[] attrInfos = mbi.getAttributes();

               for(int i = 0; i < attrInfos.length; ++i) {
                  MBeanAttributeInfo attrInfo = attrInfos[i];
                  String attrTypeName = attrInfo.getType();

                  try {
                     String attributeName = attrInfo.getName();
                     boolean isHarvestable = !this.isAttributeUnharvestable(mbi, attributeName);
                     if (isHarvestable) {
                        if (this.dbg.isDebugREnabled()) {
                           this.dbg.dbgR("  " + attributeName + "  " + attrTypeName);
                        }

                        boolean isAttributeVisibleToPartitions = this.isVisibleToPartitions(typeName, mbi, attributeName);
                        AttributeSpec spec = new AttributeSpec(attributeName, typeName, attrTypeName, isAttributeVisibleToPartitions, new AttributeTerm.SimpleTerm(attributeName, (AttributeTerm)null));
                        attributeList.add(spec);
                     } else if (this.dbg.isDebugREnabled()) {
                        this.dbg.dbgR("Attribute " + attributeName + " has been tagged as unharvestable, ignoring.");
                     }
                  } catch (Exception var15) {
                     LogSupport.logUnexpectedException("MBeanRegistrationManager", RegistrationManager.mtf_base.getCantHandleAttrTypeMessage(attrTypeName), var15);
                  }
               }
            } catch (InstanceNotFoundException var16) {
               if (this.dbg.isDebugREnabled()) {
                  this.dbg.dbgR("Caught instance not found: ", var16);
               }
            } catch (Exception var17) {
               throw new RuntimeException(var17);
            }
         }

         return attributeList;
      }

      private boolean isVisibleToPartitions(String typeName, MBeanInfo mbi, String attrName) {
         boolean isVisible = false;
         if (mbi instanceof ModelMBeanInfo) {
            boolean typeImplicitlyVisible = MetricTypeInfo.isTypeImplicitlyPartitionVisible(typeName);
            ModelMBeanInfo mmbi = (ModelMBeanInfo)mbi;

            try {
               String typeVisibilityTag = typeImplicitlyVisible ? "ALWAYS" : this.getPartitionVisibilityTag(mbi.getDescriptor());
               ModelMBeanAttributeInfo mmAttrInfo = mmbi.getAttribute(attrName);
               if (this.dbg.isDebugREnabled()) {
                  this.dbg.dbgR("partition-visibility tag for type " + typeName + ": " + typeVisibilityTag);
               }

               String attributePartitionVisbilityTag = this.getPartitionVisibilityTag(mmAttrInfo.getDescriptor());
               if (this.dbg.isDebugREnabled()) {
                  this.dbg.dbgR("partition-visibility tag for attribute " + attrName + " on type " + typeName + ": " + attributePartitionVisbilityTag);
               }

               isVisible = RegistrationManager.isAttributeVisibleToPartitions(typeVisibilityTag, attributePartitionVisbilityTag);
               if (this.dbg.isDebugREnabled() && isVisible) {
                  this.dbg.dbgR("Attribute " + attrName + " on type " + typeName + " is partition-visible");
               }
            } catch (RuntimeOperationsException var10) {
               if (this.dbg.isDebugREnabled()) {
                  this.dbg.dbgR("Exception occurred examining MBeanInfo for VisibleToPartitions tag, ignoring.", var10);
               }
            } catch (MBeanException var11) {
               if (this.dbg.isDebugREnabled()) {
                  this.dbg.dbgR("Exception occurred examining MBeanInfo for VisibleToPartitions tag, ignoring.", var11);
               }
            }
         }

         return isVisible;
      }

      private String getPartitionVisibilityTag(Descriptor descriptor) {
         String partitionTagName = "com.bea.VisibleToPartitions";
         String tag = "NONE";
         Object typeVisibilityTag = descriptor.getFieldValue(partitionTagName);
         if (typeVisibilityTag != null && typeVisibilityTag instanceof String) {
            tag = (String)typeVisibilityTag;
         }

         return tag;
      }

      private boolean isAttributeUnharvestable(MBeanInfo mbi, String name) {
         boolean isUnHarvestable = false;
         if (mbi instanceof ModelMBeanInfo) {
            ModelMBeanInfo mmbi = (ModelMBeanInfo)mbi;

            try {
               ModelMBeanAttributeInfo mmAttrInfo = mmbi.getAttribute(name);
               String tagName = null;

               for(int i = 0; i < this.unharvestableAttrDescriptors.length; ++i) {
                  tagName = "com.bea." + this.unharvestableAttrDescriptors[i];
                  Object fieldValue = mmAttrInfo.getDescriptor().getFieldValue(tagName);
                  if (fieldValue != null) {
                     if (tagName.equals(MBeanRegistrationManager.UNHARVESTABLE_MBI_TAG_NAME)) {
                        isUnHarvestable = true;
                        break;
                     }

                     Boolean tagVal = (Boolean)fieldValue;
                     if (tagVal) {
                        isUnHarvestable = tagVal;
                        break;
                     }
                  }
               }

               if (isUnHarvestable && this.dbg.isDebugREnabled()) {
                  this.dbg.dbgR("Attribute " + name + " is unharvestable, tagged with @" + tagName);
               }
            } catch (RuntimeOperationsException var10) {
               if (this.dbg.isDebugREnabled()) {
                  this.dbg.dbgR("Exception occurred examining MBeanInfo for @unharvestable tag, ignoring.", var10);
               }
            } catch (MBeanException var11) {
               if (this.dbg.isDebugREnabled()) {
                  this.dbg.dbgR("Exception occurred examining MBeanInfo for @unharvestable tag, ignoring.", var11);
               }
            }
         }

         return isUnHarvestable;
      }
   }
}
