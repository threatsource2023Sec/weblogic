package com.bea.adaptive.harvester.jmx;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class RegistrationManager {
   private static final String PARTITION_KEY = "Partition=";
   private static final String PARTITION_RUNTIME_KEY = "PartitionRuntime=";
   public static final String VISIBLE_TO_PARTITIONS_TAG = "VisibleToPartitions";
   public static final String UNHARVESTABLE_DESCRIPTOR_TAG = "unharvestable";
   public static final String[] UNHARVESTABLE_ATTRIBUTE_DESCRIPTORS = new String[]{"unharvestable", "encrypted", "internal"};
   protected HarvesterDebugLogger dbg;
   private MetricCacheListener listener;
   private MetricMetaDataResolver typeResolver;
   protected static final HarvesterJMXTextTextFormatter mtf_base = HarvesterJMXTextTextFormatter.getInstance();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ConcurrentHashMap typeNameCache = new ConcurrentHashMap();
   private static ComponentInvocationContextManager cicManager;

   public static String getUnharvestableDescriptorTag() {
      return "unharvestable";
   }

   public static String[] getUnharvestableAttributeDescriptors() {
      return (String[])Arrays.copyOf(UNHARVESTABLE_ATTRIBUTE_DESCRIPTORS, UNHARVESTABLE_ATTRIBUTE_DESCRIPTORS.length);
   }

   boolean doesTypeExist(String typeName) {
      return this.typeNameCache.get(typeName) != null;
   }

   protected RegistrationManager(HarvesterDebugLogger debugLogger, MetricCacheListener listener, MetricMetaDataResolver resolver) {
      this.dbg = debugLogger;
      this.listener = listener;
      this.typeResolver = resolver;
   }

   RegistrationManager() {
   }

   public abstract void shutdown(boolean var1);

   Collection getAllCurrentInstanceNames() {
      Set names = new HashSet();
      Iterator it = this.typeNameCache.values().iterator();

      while(it.hasNext()) {
         names.addAll(((MetricTypeInfo)it.next()).getInstances());
      }

      return names;
   }

   String getTypeName(String instName) {
      String typeForInstance = null;
      MetricTypeInfo typeInfo = this.lookupTypeInfoByInstance(instName);
      if (typeInfo != null) {
         typeForInstance = typeInfo.getType();
      } else {
         typeForInstance = this.typeResolver.getTypeForInstance(instName);
         if (typeForInstance != null) {
            this.addToTypeCache(typeForInstance, instName);
         } else if (this.dbg.isDebugREnabled()) {
            this.dbg.dbgR("Unable to find type for instance " + instName);
         }
      }

      return typeForInstance;
   }

   protected MetricTypeInfo lookupTypeInfoByInstance(String instanceName) {
      MetricTypeInfo ti = null;
      Iterator it = this.typeNameCache.values().iterator();

      while(it.hasNext()) {
         MetricTypeInfo nextType = (MetricTypeInfo)it.next();
         if (nextType.getInstances().contains(instanceName)) {
            ti = nextType;
            break;
         }
      }

      return ti;
   }

   protected MetricTypeInfo lookupTypeInfo(String typeName) {
      return (MetricTypeInfo)this.typeNameCache.get(typeName);
   }

   List getAllCurrentInstanceNames(String typeName, String instNameRegex) throws IOException {
      Pattern pat = instNameRegex != null ? Pattern.compile(instNameRegex) : null;
      MetricTypeInfo typeInfo = (MetricTypeInfo)this.typeNameCache.get(typeName);
      if (typeInfo == null) {
         return Collections.EMPTY_LIST;
      } else {
         boolean isGlobalRuntime = this.isGlobalRuntime();
         if (!isGlobalRuntime && !typeInfo.isTypeImplicitlyPartitionVisible() && !typeInfo.hasPartitionVisibleAttributes()) {
            return Collections.EMPTY_LIST;
         } else {
            List insts = new ArrayList(typeInfo.getInstances());
            Iterator it = insts.iterator();

            while(true) {
               while(it.hasNext()) {
                  String instName = (String)it.next();
                  if (!isGlobalRuntime && this.partitionFiltered(typeInfo, instName)) {
                     it.remove();
                  } else if (pat != null && !pat.matcher(instName).matches()) {
                     it.remove();
                  }
               }

               Collections.sort(insts);
               return insts;
            }
         }
      }
   }

   private boolean partitionFiltered(MetricTypeInfo typeInfo, String instName) {
      boolean filtered = false;
      String currentPartitionName = this.getCurrentPartitionName();
      if (typeInfo.isTypeImplicitlyPartitionVisible() && !this.matchesCurrentPartition(instName, currentPartitionName)) {
         filtered = true;
      } else if (!typeInfo.hasPartitionVisibleAttributes()) {
         filtered = true;
      }

      return filtered;
   }

   protected boolean matchesCurrentPartition(String instName, String currentPartitionName) {
      return instName.contains("PartitionRuntime=" + currentPartitionName) || instName.contains("Partition=" + currentPartitionName);
   }

   protected String getCurrentPartitionName() {
      return getCICManager().getCurrentComponentInvocationContext().getPartitionName();
   }

   public static boolean isAttributeVisibleToPartitions(String typeVisibilityTag, String attributeVisibilityTag) {
      boolean isVisible = false;
      if (attributeVisibilityTag != null && !attributeVisibilityTag.isEmpty()) {
         switch (attributeVisibilityTag) {
            case "NEVER":
               break;
            case "ALWAYS":
               isVisible = true;
               break;
            case "NONE":
            default:
               isVisible = typeVisibilityTag.equals("ALWAYS");
         }
      }

      return isVisible;
   }

   public void newInstance(String typeName, String instName, String categoryName) throws Exception {
      if (this.dbg.isDebugREnabled()) {
         this.dbg.dbgR("Got MBean registration for: " + instName + " (" + typeName + ").");
      }

      String resolvedTypeName = typeName;
      if (typeName == null) {
         resolvedTypeName = this.typeResolver.getTypeForInstance(instName);
      }

      if (resolvedTypeName != null) {
         this.addToTypeCache(resolvedTypeName, instName);
         if (this.listener != null) {
            this.listener.newInstance(instName, resolvedTypeName);
         }
      } else if (this.dbg.isDebugREnabled()) {
         this.dbg.dbgR("Unable to resolve type type name for " + instName);
      }

   }

   protected void addKnownTypeToCache(String typeName) {
      this.addToTypeCache(typeName, (String)null);
   }

   protected void addToTypeCache(String typeName, String instName) {
      MetricTypeInfo proposedMTI = new MetricTypeInfo(typeName, this.typeResolver);
      MetricTypeInfo actualTypeInfo = (MetricTypeInfo)this.typeNameCache.putIfAbsent(typeName, proposedMTI);
      actualTypeInfo = actualTypeInfo == null ? proposedMTI : actualTypeInfo;
      if (instName != null) {
         actualTypeInfo.getInstances().add(instName);
      }

   }

   public void instanceDeleted(String instName) throws Exception {
      this.instanceDeletedInternal(instName);
   }

   protected void showError(String instName, Exception e) {
      if (this.dbg.isDebugREnabled()) {
         this.dbg.dbgR("Exception deleting instance: " + instName + " " + e.getMessage());
      }

   }

   protected void instanceDeletedInternal(String instName) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
      if (this.dbg.isDebugHEnabled()) {
         this.dbg.dbgH("Got MBean deregistration for: " + instName);
      }

      String typeName = null;
      MetricTypeInfo info = this.lookupTypeInfoByInstance(instName);
      if (info != null) {
         info.getInstances().remove(instName);
         typeName = info.getType();
      } else if (this.dbg.isDebugHEnabled()) {
         this.dbg.dbgH("+++++ TYPEINFO name was NULL for instance " + instName);
      }

      if (this.listener != null) {
         this.listener.instanceDeleted(instName, typeName);
      }

   }

   public String[][] getKnownHarvestableTypes(String typeNameRegex) throws IOException {
      List retList = new ArrayList();
      Pattern pat = typeNameRegex != null ? Pattern.compile(typeNameRegex) : null;
      Iterator var4 = this.typeNameCache.entrySet().iterator();

      while(true) {
         String typeName;
         MetricTypeInfo typeInfo;
         do {
            Map.Entry entry;
            do {
               if (!var4.hasNext()) {
                  String[][] ret = (String[][])retList.toArray(new String[retList.size()][2]);
                  return ret;
               }

               entry = (Map.Entry)var4.next();
               typeName = (String)entry.getKey();
            } while(pat != null && !pat.matcher(typeName).matches());

            typeInfo = (MetricTypeInfo)entry.getValue();
         } while(!this.isGlobalRuntime() && !typeInfo.hasPartitionVisibleAttributes() && !typeInfo.isTypeImplicitlyPartitionVisible());

         String descr = this.getDescriptionForType(typeName);
         retList.add(new String[]{typeName, descr});
      }
   }

   protected boolean isGlobalRuntime() {
      ComponentInvocationContext currentContext = getCICManager().getCurrentComponentInvocationContext();
      return currentContext != null ? currentContext.isGlobalRuntime() : true;
   }

   protected static synchronized ComponentInvocationContextManager getCICManager() {
      if (cicManager == null) {
         cicManager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      }

      return cicManager;
   }

   protected static Object runAsPartition(String forPartition, Callable callable) {
      ComponentInvocationContextManager mgr = getCICManager();
      ComponentInvocationContext cic = mgr.createComponentInvocationContext(forPartition);

      try {
         return ComponentInvocationContextManager.runAs(KERNEL_ID, cic, callable);
      } catch (ExecutionException var5) {
         throw new RuntimeException(var5);
      }
   }

   protected static Object runAsDomainPartition(Callable callable) {
      return runAsPartition("DOMAIN", callable);
   }

   protected abstract String getDescriptionForType(String var1);

   public String[][] getHarvestableAttributes(String typeName, String attrNameRegex) throws IOException {
      ArrayList matchedAttrs = new ArrayList();
      MetricTypeInfo typeInfo = (MetricTypeInfo)this.typeNameCache.get(typeName);
      if (typeInfo != null && (this.isGlobalRuntime() || typeInfo.isTypeImplicitlyPartitionVisible() || typeInfo.hasPartitionVisibleAttributes())) {
         List specs = typeInfo.getAttributes();
         Pattern pat = attrNameRegex != null ? Pattern.compile(attrNameRegex) : null;
         Iterator var7 = specs.iterator();

         while(true) {
            AttributeSpec spec;
            do {
               do {
                  if (!var7.hasNext()) {
                     return (String[][])matchedAttrs.toArray(new String[matchedAttrs.size()][]);
                  }

                  spec = (AttributeSpec)var7.next();
               } while(pat != null && !pat.matcher(spec.getName()).matches());
            } while(!this.isGlobalRuntime() && !spec.isVisibleToPartitions());

            matchedAttrs.add(new String[]{spec.getName(), spec.getDataType(), spec.getAttributeDescription()});
         }
      } else {
         return (String[][])matchedAttrs.toArray(new String[matchedAttrs.size()][]);
      }
   }

   AttributeSpec[] getAttributeSpecs(String typeName, String attrName) {
      AttributeSpec[] attributeSpecs = new AttributeSpec[0];
      if (typeName != null) {
         if (attrName == null) {
            MetricTypeInfo typeInfo = (MetricTypeInfo)this.typeNameCache.get(typeName);
            if (typeInfo != null) {
               attributeSpecs = (AttributeSpec[])typeInfo.getAttributes().toArray(attributeSpecs);
            }
         } else {
            AttributeSpec spec = this.getAttributeSpec(typeName, attrName);
            attributeSpecs = new AttributeSpec[]{spec};
         }
      }

      return attributeSpecs;
   }

   AttributeSpec getAttributeSpec(String typeName, String attrName) {
      AttributeSpec attributeSpec = null;
      if (typeName != null) {
         MetricTypeInfo typeInfo = (MetricTypeInfo)this.typeNameCache.get(typeName);
         if (typeInfo != null) {
            attributeSpec = typeInfo.lookupAttributeSpec(attrName);
         }
      }

      if (this.dbg.isDebugREnabled() && attributeSpec == null) {
         this.dbg.dbgR("Attribute not found --> [" + attrName + ", " + typeName + "]");
      }

      return attributeSpec;
   }
}
