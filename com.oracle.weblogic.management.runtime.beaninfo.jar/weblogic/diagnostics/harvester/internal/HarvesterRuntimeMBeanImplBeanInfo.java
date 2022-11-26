package weblogic.diagnostics.harvester.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFHarvesterRuntimeMBean;

public class HarvesterRuntimeMBeanImplBeanInfo extends PartitionHarvesterRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFHarvesterRuntimeMBean.class;

   public HarvesterRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public HarvesterRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.harvester.internal.HarvesterRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("notificationTranslator", "weblogic.diagnostics.harvester.internal.RuntimeMBeanNotificationTranslator");
      beanDescriptor.setValue("package", "weblogic.diagnostics.harvester.internal");
      String description = (new String("<p>Provides aggregated information about all active Harvester configurations, as well as metadata about harvestable and harvested attributes, types, and instances. Harvestable means potentially available for harvesting; harvested means explicitly designated for harvesting. These terms apply to types, instances, and the attributes within those types. In addition, the interface provides access to sampling and snapshot statistics. All statistics are base on data collected during the current server session.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFHarvesterRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AttributeInfoForAllTypes")) {
         getterName = "getAttributeInfoForAllTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("AttributeInfoForAllTypes", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AttributeInfoForAllTypes", currentResult);
         currentResult.setValue("description", "<p> This method returns all Harvester types and their MBeanAttributeInfos. </p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map<String,MBeanAttributeInfo[]>");
      }

      if (!descriptors.containsKey("AverageSamplingTime")) {
         getterName = "getAverageSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageSamplingTime", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageSamplingTime", currentResult);
         currentResult.setValue("description", "<p> The average amount of time, in nanoseconds, spent in sampling cycles. </p> ");
      }

      if (!descriptors.containsKey("ConfiguredNamespaces")) {
         getterName = "getConfiguredNamespaces";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfiguredNamespaces", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConfiguredNamespaces", currentResult);
         currentResult.setValue("description", "<p>Returns the set of MBean namespaces currently configured within the WLDF Harvester.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentDataSampleCount")) {
         getterName = "getCurrentDataSampleCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentDataSampleCount", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentDataSampleCount", currentResult);
         currentResult.setValue("description", "<p> The number of collected data samples in the current snapshot. </p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentSnapshotElapsedTime")) {
         getterName = "getCurrentSnapshotElapsedTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentSnapshotElapsedTime", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentSnapshotElapsedTime", currentResult);
         currentResult.setValue("description", "<p> The elapsed time, in nanoseconds, of a snapshot. </p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentSnapshotStartTime")) {
         getterName = "getCurrentSnapshotStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentSnapshotStartTime", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentSnapshotStartTime", currentResult);
         currentResult.setValue("description", "<p> The start time, in nanoseconds, of a snapshot. </p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultNamespace")) {
         getterName = "getDefaultNamespace";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultNamespace", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DefaultNamespace", currentResult);
         currentResult.setValue("description", "<p> Returns the default MBean namespace within the WLDF Harvester.  This is the namespace used if none is provided for a configured MBean metric. </p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InstancesForAllTypes")) {
         getterName = "getInstancesForAllTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("InstancesForAllTypes", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InstancesForAllTypes", currentResult);
         currentResult.setValue("description", "<p> This method returns all Harvester types and their instances </p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map<String,ObjectName[]>");
      }

      if (!descriptors.containsKey("KnownHarvestableTypes")) {
         getterName = "getKnownHarvestableTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("KnownHarvestableTypes", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("KnownHarvestableTypes", currentResult);
         currentResult.setValue("description", "<p>The set of all known types, regardless of whether the types are currently configured for harvesting. The set includes the WebLogic Server MBeans, which are always present, plus any other types that can be discovered. MBeans that are not WebLogic Server MBeans will require instances to exist in order to discover the type.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("MaximumSamplingTime")) {
         getterName = "getMaximumSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumSamplingTime", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumSamplingTime", currentResult);
         currentResult.setValue("description", "<p> The maximum sampling time, in nanoseconds. </p> ");
      }

      if (!descriptors.containsKey("MinimumSamplingTime")) {
         getterName = "getMinimumSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimumSamplingTime", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimumSamplingTime", currentResult);
         currentResult.setValue("description", "<p> The minimum sampling time, in nanoseconds. </p> ");
      }

      if (!descriptors.containsKey("OutlierDetectionFactor")) {
         getterName = "getOutlierDetectionFactor";
         setterName = null;
         currentResult = new PropertyDescriptor("OutlierDetectionFactor", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OutlierDetectionFactor", currentResult);
         currentResult.setValue("description", "<p>The multiplicative factor used to determine a statistical outlier. If the actual sampling time exceeds this the session average multiplied by the outlier detection factor, then the sampling time is considered to be a statistical outlier.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SamplePeriod")) {
         getterName = "getSamplePeriod";
         setterName = null;
         currentResult = new PropertyDescriptor("SamplePeriod", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SamplePeriod", currentResult);
         currentResult.setValue("description", "<p>The current global sample period, in nanoseconds.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalDataSampleCount")) {
         getterName = "getTotalDataSampleCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalDataSampleCount", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalDataSampleCount", currentResult);
         currentResult.setValue("description", "<p>The number of configured data samples that have been collected so far in this server session.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalSamplingCycles")) {
         getterName = "getTotalSamplingCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSamplingCycles", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSamplingCycles", currentResult);
         currentResult.setValue("description", "<p> The total number of sampling cycles taken thus far. </p> ");
      }

      if (!descriptors.containsKey("TotalSamplingTime")) {
         getterName = "getTotalSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSamplingTime", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSamplingTime", currentResult);
         currentResult.setValue("description", "<p> The total amount of time, in nanoseconds, spent in sampling cycles. </p> ");
      }

      if (!descriptors.containsKey("TotalSamplingTimeOutlierCount")) {
         getterName = "getTotalSamplingTimeOutlierCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSamplingTimeOutlierCount", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSamplingTimeOutlierCount", currentResult);
         currentResult.setValue("description", "<p>The number of times within this server session that the sampling time differed significantly enough from the average to be considered a statistical outlier.  The Harvester removes these values form the ongoing averages.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentSampleTimeAnOutlier")) {
         getterName = "isCurrentSampleTimeAnOutlier";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentSampleTimeAnOutlier", WLDFHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentSampleTimeAnOutlier", currentResult);
         currentResult.setValue("description", "<p>Whether or not the sampling time for the most recent data sample differed significantly enough from the average to be considered a statistical outlier.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFHarvesterRuntimeMBean.class.getMethod("getHarvestableAttributes", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "the name of the type to get the attributes for ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("HarvesterException.HarvestableTypesNotFoundException if the type          name is valid but could not be located"), BeanInfoHelper.encodeEntities("HarvesterException.AmbiguousTypeName if the          type name requires qualification to resolve"), BeanInfoHelper.encodeEntities("HarvesterException.TypeNotHarvestableException if the          type could never be harvested")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The set of attributes that are eligible for harvesting for the specified type. The specified type does not need to be currently configured for harvesting. For MBeans other than WebLogic Server MBeans, returns null until at least one instance has been created.</p>  <p>The returned array represents a list of pairs. The first element in each pair is the attribute name and the second element is the class name of the attribute's type.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFHarvesterRuntimeMBean.class.getMethod("getHarvestableAttributes", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "The name of the type to get the attributes for "), createParameterDescriptor("forPartition", "The name of the domain partition the query is being made for ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> Same as {@link #getHarvestableAttributes(String)}, except scoped to the specified domain partition. </p> ");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getHarvestableAttributes(String)")};
            currentResult.setValue("see", throwsObjectArray);
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      mth = WLDFHarvesterRuntimeMBean.class.getMethod("getHarvestableAttributesForInstance", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("instancePattern", "the ObjectName or ObjectName pattern of the type to get the attributes for ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("HarvesterException.HarvestableTypesNotFoundException            if the type name is valid but could not be located"), BeanInfoHelper.encodeEntities("HarvesterException.AmbiguousTypeName            if the type name requires qualification to resolve"), BeanInfoHelper.encodeEntities("HarvesterException.TypeNotHarvestableException            if the type could never be harvested")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The set of attributes that are eligible for harvesting for the specified instance name. The type of the specified instance does not need to be currently configured for harvesting. For MBeans other than WebLogic Server MBeans, returns null until at least one instance has been created.</p>  <p>Note that in the case where an ObjectName pattern is specified for a WebLogic Server MBean, the ObjectName's property list must contain the &quot;Type&quot; property (e.g., &quot;com.bea:Type=ServerRuntime,*&quot;). Otherwise, as is the case for any non-WebLogic Server MBean, an instance must exist in order for the set of harvestable attributes to be known.</p>  <p> The returned array represents a list of pairs. The first element in each pair is the attribute name and the second element is the class name of the attribute's type. </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFHarvesterRuntimeMBean.class.getMethod("getHarvestableAttributesForInstance", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("instancePattern", "the ObjectName or ObjectName pattern of the type to get the attributes for "), createParameterDescriptor("forPartition", "The name of the domain partition the query is being made for ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> Same as {@link #getHarvestableAttributesForInstance(String)}, except scoped to the specified domain partition. </p> ");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getHarvestableAttributesForInstance(String)")};
            currentResult.setValue("see", throwsObjectArray);
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      mth = WLDFHarvesterRuntimeMBean.class.getMethod("getCurrentlyHarvestedAttributes", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "the name of the type to get the attributes for ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("HarvesterException.MissingConfigurationType if the provided type          is not configured for harvesting"), BeanInfoHelper.encodeEntities("HarvesterException.HarvestingNotEnabled if the Harvester is not deployed"), BeanInfoHelper.encodeEntities("HarvesterException.HarvesterException.AmbiguousTypeName if the          type name requires qualification to resolve")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The set of attributes that are currently being harvested for the specified type. The specified type must be explicitly configured for harvesting.</p>  <p>The returned set of attributes usually matches the corresponding set of attributes configured for harvesting; but if an error occurs when harvesting an attribute, that attribute will be omitted from the returned set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFHarvesterRuntimeMBean.class.getMethod("getKnownHarvestableTypes", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> The set of all known types within a particular MBean namespace, regardless of whether the types are currently configured for harvesting. An MBean namespace loosely corresponds to those MBeans that can be found within a particular MBeanServer, although there may be multiple Harvester delegates that service a particular MBean namespace. The returned set includes the WebLogic Server MBeans, which are always present, plus any other types that can be discovered. MBeans that are not WebLogic Server MBeans will require instances to exist in order to discover the type. </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFHarvesterRuntimeMBean.class.getMethod("getKnownHarvestableTypes", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("namespace", "the MBean namespace to query "), createParameterDescriptor("forPartition", "The name of the domain partition the query is being made for ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> The same as {@link #getKnownHarvestableTypes(String)}, except scoped to the specified domain partition. </p> ");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getKnownHarvestableTypes(String)")};
            currentResult.setValue("see", throwsObjectArray);
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      mth = WLDFHarvesterRuntimeMBean.class.getMethod("getKnownHarvestableInstances", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "the name of the type to get the attributes for ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("HarvesterException.HarvestableTypesNotFoundException if the type          name is valid but could not be located"), BeanInfoHelper.encodeEntities("HarvesterException.AmbiguousTypeName if the          type name requires qualification to resolve"), BeanInfoHelper.encodeEntities("HarvesterException.TypeNotHarvestableException if the          type could never be harvested")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The set of instances that are eligible for harvesting for the specified type at the time of the call.  The type does not need to be currently configured for harvesting.</p>  <p>The caller should be aware that instances come and go. This method returns only those instances that exist at the time of the call.</p>  <dl><dt>Note</dt><dd><p>For MBeans that are not WebLogic Server MBeans, returns null until at least one instance has been created.</p></dd></dl> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
      }

      mth = WLDFHarvesterRuntimeMBean.class.getMethod("getKnownHarvestableInstances", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("namespace", "the MBean namespace to query "), createParameterDescriptor("type", "the name of the type to get the attributes for ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("HarvesterException.HarvestableTypesNotFoundException if the type          name is valid but could not be located"), BeanInfoHelper.encodeEntities("HarvesterException.AmbiguousTypeName if the          type name requires qualification to resolve"), BeanInfoHelper.encodeEntities("HarvesterException.TypeNotHarvestableException if the          type could never be harvested")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The set of instances that are eligible for harvesting for the specified type at the time of the call within a particular MBean namespace.  An MBean namespace loosely corresponds to those MBeans that can be found within a particular MBeanServer, although there may be multiple Harvester delegates that service a particular MBean namespace.  The type parameter provided to this call does not need to be currently configured for harvesting.</p>  <p>The caller should be aware that instances come and go. This method returns only those instances that exist at the time of the call.</p>  <dl><dt>Note</dt><dd><p>For MBeans that are not WebLogic Server MBeans, returns null until at least one instance has been created.</p></dd></dl> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = WLDFHarvesterRuntimeMBean.class.getMethod("getKnownHarvestableInstances", String.class, String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("namespace", "the MBean namespace to query "), createParameterDescriptor("type", "the name of the type to get the attributes for "), createParameterDescriptor("forPartition", "The name of the domain partition the query is being made for ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Same as {@link #getKnownHarvestableInstances(String, String)}, except scoped to the specified partition.  All results will be filtered based on the visibility of each instance to the specified partition. ");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getKnownHarvestableInstances(String, String)")};
            currentResult.setValue("see", throwsObjectArray);
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      mth = WLDFHarvesterRuntimeMBean.class.getMethod("getCurrentlyHarvestedInstances", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "the name of the type to get the instances for ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("HarvesterException.MissingConfigurationType if the provided type          is not configured for harvesting"), BeanInfoHelper.encodeEntities("HarvesterException.HarvestingNotEnabled if the Harvester is not          deployed"), BeanInfoHelper.encodeEntities("HarvesterException.AmbiguousTypeName if the          type name requires qualification to resolve")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The set of instances that are currently being harvested for the specified type. The type must be configured for harvesting.</p>  <p>The returned set of instances usually matches the corresponding set of instances configured for harvesting; but if an error occurs when harvesting an instance, that instance will be omitted from the returned set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFHarvesterRuntimeMBean.class.getMethod("getHarvestableType", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("instanceName", "the name of the instance to get the type for ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("HarvesterException.HarvestableInstancesNotFoundException if          the provided instance does not currently exist"), BeanInfoHelper.encodeEntities("HarvesterException.AmbiguousInstanceName if the          instance name requires qualification to resolve")};
         currentResult.setValue("throws", throwsObjectArray);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The type associated with a particular harvestable instance.</p> ");
         currentResult.setValue("role", "operation");
      }

   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
