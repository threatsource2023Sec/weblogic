package weblogic.diagnostics.harvester.internal;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;
import weblogic.diagnostics.harvester.HarvesterException;
import weblogic.diagnostics.i18n.DiagnosticsTextHarvesterTextFormatter;
import weblogic.diagnostics.utils.SecurityHelper;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.ManagementRuntimeException;
import weblogic.management.runtime.WLDFHarvesterRuntimeMBean;
import weblogic.management.runtime.WLDFRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class HarvesterRuntimeMBeanImpl extends PartitionHarvesterRuntime implements WLDFHarvesterRuntimeMBean {
   private static final DiagnosticsTextHarvesterTextFormatter txtFormatter = DiagnosticsTextHarvesterTextFormatter.getInstance();
   private static final int HARVESTER_INIT_DELAY = 10000;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Map attributeInfoMap = new HashMap();
   private ComponentInvocationContextManager cicManager;

   public HarvesterRuntimeMBeanImpl(MetricArchiver archiver, WLDFRuntimeMBean parent) throws ManagementException {
      super(archiver, "WLDFHarvesterRuntime", parent);
      this.cicManager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      parent.setWLDFHarvesterRuntime(this);
   }

   public long getTotalDataSampleCount() throws HarvesterException.HarvestingNotEnabled {
      return this.archiver.isEnabled() ? this.archiver.getTotalConfiguredDataSampleCount() : 0L;
   }

   public String[] getConfiguredNamespaces() throws HarvesterException.HarvestingNotEnabled {
      return this.archiver.getConfiguredNamespaces();
   }

   public String getDefaultNamespace() throws HarvesterException.HarvestingNotEnabled {
      String defNamespace = this.archiver.getDefaultNamespace();
      return defNamespace;
   }

   public long getCurrentDataSampleCount() throws HarvesterException.HarvestingNotEnabled {
      return this.archiver.isEnabled() ? (long)this.archiver.getCurrentConfiguredDataSampleCount() : 0L;
   }

   public long getCurrentImplicitDataSampleCount() throws HarvesterException.HarvestingNotEnabled {
      return this.archiver.isEnabled() ? this.archiver.getCurrentImplicitDataSampleCount() : 0L;
   }

   public long getCurrentSnapshotStartTime() throws HarvesterException.HarvestingNotEnabled {
      return this.archiver.isEnabled() ? this.archiver.getCurrentSnapshotStartTime() : -1L;
   }

   public long getCurrentSnapshotElapsedTime() throws HarvesterException.HarvestingNotEnabled {
      return this.archiver.isEnabled() ? this.archiver.getCurrentSnapshotElapsedTime() : 0L;
   }

   public long getSamplePeriod() throws HarvesterException.HarvestingNotEnabled {
      return this.archiver.isEnabled() ? this.archiver.getSamplePeriod() : 0L;
   }

   public String getHarvestableType(String instanceName) throws HarvesterException.HarvestableInstancesNotFoundException, HarvesterException.AmbiguousInstanceName {
      String result = this.archiver.getHarvestableType(instanceName);
      return result;
   }

   public String[] getCurrentlyHarvestedAttributes(String type) throws HarvesterException.AmbiguousTypeName, HarvesterException.HarvestingNotEnabled, HarvesterException.MissingConfigurationType {
      String[] result = new String[0];
      if (this.archiver.isEnabled()) {
         result = this.archiver.getCurrentlyHarvestedAttributes(type);
      }

      return result;
   }

   public String[] getCurrentlyHarvestedInstances(String type) throws HarvesterException.MissingConfigurationType, HarvesterException.HarvestingNotEnabled {
      String[] result = new String[0];
      if (this.archiver.isEnabled()) {
         result = this.archiver.getCurrentlyHarvestedInstances(type);
      }

      return result;
   }

   public String[][] getHarvestableAttributes(String type) throws HarvesterException.AmbiguousTypeName, HarvesterException.HarvestableTypesNotFoundException, HarvesterException.TypeNotHarvestable {
      return this.getHarvestableAttributes(type, this.getCurrentPartitionName());
   }

   public String[][] getHarvestableAttributes(final String type, String forPartition) {
      Callable callable = new Callable() {
         public String[][] call() throws Exception {
            return HarvesterRuntimeMBeanImpl.this.archiver.getHarvestableAttributes(type);
         }
      };
      return (String[][])this.runAsPartition(forPartition, callable);
   }

   public String[][] getHarvestableAttributesForInstance(String instanceName) throws HarvesterException.AmbiguousTypeName, HarvesterException.HarvestableTypesNotFoundException, HarvesterException.TypeNotHarvestable {
      return this.getHarvestableAttributesForInstance(instanceName, this.getCurrentPartitionName());
   }

   public String[][] getHarvestableAttributesForInstance(final String instancePattern, String forPartition) {
      Callable callable = new Callable() {
         public String[][] call() throws Exception {
            String[][] harvestableAttributes = (String[][])null;
            String derivedTypeName = HarvesterRuntimeMBeanImpl.this.archiver.getHarvestableType(instancePattern);
            if (derivedTypeName != null) {
               harvestableAttributes = HarvesterRuntimeMBeanImpl.this.getHarvestableAttributes(derivedTypeName);
            }

            return harvestableAttributes;
         }
      };
      return (String[][])this.runAsPartition(forPartition, callable);
   }

   public String[] getKnownHarvestableTypes() {
      return this.getKnownHarvestableTypes((String)null);
   }

   public Map getInstancesForAllTypes() {
      Map result = new HashMap();
      String[] types = this.getKnownHarvestableTypes();
      String[] var3 = types;
      int var4 = types.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String type = var3[var5];

         try {
            String[] instances = this.getKnownHarvestableInstances(type);
            ObjectName[] instanceNames = instances == null ? new ObjectName[0] : new ObjectName[instances.length];

            for(int i = 0; i < instanceNames.length; ++i) {
               instanceNames[i] = new ObjectName(instances[i]);
            }

            if (dbg.isDebugEnabled()) {
               dbg.debug("getInstancesForAllTypes(): Adding type " + type + " to result set with instances " + Arrays.toString(instanceNames));
            }

            result.put(type, instanceNames);
         } catch (Exception var10) {
            if (dbg.isDebugEnabled()) {
               dbg.debug("Could not obtain the set of harvestable instances for " + type, var10);
            }
         }
      }

      if (dbg.isDebugEnabled()) {
         dbg.debug("getInstancesForAllTypes(): total types in result set: " + result.size());
      }

      return result;
   }

   public Map getAttributeInfoForAllTypes() {
      String[] types = this.getKnownHarvestableTypes();
      synchronized(this) {
         if (types.length != this.attributeInfoMap.size()) {
            this.rebuildAttributeInfoMap(types);
         }
      }

      return this.attributeInfoMap;
   }

   public String[] getKnownHarvestableTypes(String namespace) {
      return this.getKnownHarvestableTypes(namespace, this.getCurrentPartitionName());
   }

   public String[] getKnownHarvestableTypes(final String namespace, String forPartition) {
      Callable callable = new Callable() {
         public String[] call() throws Exception {
            return namespace == null ? HarvesterRuntimeMBeanImpl.this.archiver.getKnownHarvestableTypes() : HarvesterRuntimeMBeanImpl.this.archiver.getKnownHarvestableTypes(namespace);
         }
      };
      String[] result = (String[])this.runAsPartition(forPartition, callable);
      if (result == null || result.length == 0) {
         if (dbg.isDebugEnabled()) {
            dbg.debug("getKnownHarvestableTypes(): result set empty, waiting and retrying");
         }

         try {
            Thread.sleep(10000L);
         } catch (InterruptedException var6) {
         }

         result = (String[])this.runAsPartition(forPartition, callable);
      }

      return result;
   }

   public String[] getKnownHarvestableInstances(String type) throws HarvesterException.AmbiguousTypeName, HarvesterException.HarvestableTypesNotFoundException, HarvesterException.TypeNotHarvestable {
      return this.getKnownHarvestableInstances(this.archiver.getDefaultNamespace(), type);
   }

   public String[] getKnownHarvestableInstances(String namespace, String type) throws HarvesterException.HarvestableTypesNotFoundException, HarvesterException.AmbiguousTypeName, HarvesterException.TypeNotHarvestable {
      return this.getKnownHarvestableInstances(namespace, type, this.getCurrentPartitionName());
   }

   public String[] getKnownHarvestableInstances(final String namespace, final String type, String forPartition) {
      Callable callable = new Callable() {
         public String[] call() throws Exception {
            return HarvesterRuntimeMBeanImpl.this.archiver.getKnownHarvestableInstances(namespace, type);
         }
      };
      return (String[])this.runAsPartition(forPartition, callable);
   }

   public long getTotalSamplingTimeOutlierCount() {
      return this.archiver.isEnabled() ? this.archiver.getTotalSamplingTimeOutlierCount() : 0L;
   }

   public boolean isCurrentSampleTimeAnOutlier() {
      return this.archiver.isEnabled() ? this.archiver.isCurrentSampleTimeAnOutlier() : false;
   }

   public float getOutlierDetectionFactor() {
      return this.archiver != null ? this.archiver.getOutlierDetectionFactor() : 0.0F;
   }

   private void rebuildAttributeInfoMap(String[] types) {
      if (dbg.isDebugEnabled()) {
         dbg.debug("getAttributeInfoForAllTypes(): Type set has changed, rebuilding attribute info map");
      }

      String[] var2 = types;
      int var3 = types.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String type = var2[var4];
         if (!this.attributeInfoMap.containsKey(type)) {
            try {
               MBeanAttributeInfo[] attributeInfos = this.getAttributeInfos(type);
               if (dbg.isDebugEnabled()) {
                  dbg.debug("getAttributeInfoForAllTypes(): Adding type " + type + " to result set with attributes " + Arrays.toString(attributeInfos));
               }

               this.attributeInfoMap.put(type, attributeInfos);
            } catch (Exception var7) {
               if (dbg.isDebugEnabled()) {
                  dbg.debug("Could not obtain the set of harvestable attributes for " + type, var7);
               }
            }
         }
      }

      if (dbg.isDebugEnabled()) {
         dbg.debug("getAttributeInfoForAllTypes(): total types in result set: " + this.attributeInfoMap.size());
      }

   }

   private MBeanAttributeInfo[] getAttributeInfos(String type) throws Exception {
      String[][] attributes = this.getHarvestableAttributes(type);
      if (attributes == null) {
         attributes = new String[0][0];
      }

      MBeanAttributeInfo[] attributeInfos = new MBeanAttributeInfo[attributes.length];
      int i = 0;
      String[][] var5 = attributes;
      int var6 = attributes.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String[] attributeData = var5[var7];
         String attrName = attributeData[0];
         String attrType = attributeData[1];
         String attrDesc = attributeData[2];
         Class typeClass = Class.forName(type);
         Method setter = null;
         boolean isIs = false;

         Method getter;
         try {
            getter = typeClass.getMethod("get" + attrName, (Class[])null);
         } catch (NoSuchMethodException var17) {
            getter = typeClass.getMethod("is" + attrName, (Class[])null);
            isIs = true;
         }

         try {
            setter = typeClass.getMethod("set" + attrName, (Class[])null);
         } catch (Throwable var18) {
            if (dbg.isDebugEnabled()) {
               dbg.debug("No setter for attribute " + attrName + " on type " + type);
            }
         }

         attributeInfos[i++] = new MBeanAttributeInfo(attrName, attrType, attrDesc, getter != null, setter != null, isIs);
      }

      return attributeInfos;
   }

   private Object runAsPartition(String forPartition, Callable callable) {
      SecurityHelper.checkForAdminRole();
      Object result = null;

      try {
         ComponentInvocationContext currentContext = this.cicManager.getCurrentComponentInvocationContext();
         if (forPartition != null && !forPartition.isEmpty()) {
            String currentPartitionName = currentContext.getPartitionName();
            if (currentPartitionName.equals(forPartition)) {
               result = callable.call();
            } else {
               if (!currentContext.isGlobalRuntime()) {
                  throw new IllegalArgumentException(txtFormatter.getHarvesterRuntimeIllegalPartitionNameSpecified(currentPartitionName, forPartition));
               }

               ComponentInvocationContext partitionCic = this.cicManager.createComponentInvocationContext(forPartition);
               result = ComponentInvocationContextManager.runAs(KERNEL_ID, partitionCic, callable);
            }
         } else {
            if (!currentContext.isGlobalRuntime()) {
               throw new IllegalArgumentException(txtFormatter.getPartitionNameMissingForPartitionUser());
            }

            result = callable.call();
         }

         return result;
      } catch (Exception var7) {
         ManagementRuntimeException mex = new ManagementRuntimeException(var7.getMessage());
         mex.setStackTrace(var7.getStackTrace());
         throw mex;
      }
   }

   private String getCurrentPartitionName() {
      return this.cicManager.getCurrentComponentInvocationContext().getPartitionName();
   }
}
