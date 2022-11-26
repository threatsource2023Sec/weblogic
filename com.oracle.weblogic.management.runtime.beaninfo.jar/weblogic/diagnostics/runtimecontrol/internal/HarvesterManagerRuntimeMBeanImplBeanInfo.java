package weblogic.diagnostics.runtimecontrol.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFHarvesterManagerRuntimeMBean;

public class HarvesterManagerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFHarvesterManagerRuntimeMBean.class;

   public HarvesterManagerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public HarvesterManagerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.runtimecontrol.internal.HarvesterManagerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.2.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.runtimecontrol.internal");
      String description = (new String("<p>Provides statistical information relative to a particular configured Harvester instance, and a means to retreive an on-demand snapshot of configured attribute values.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFHarvesterManagerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AverageSamplingTime")) {
         getterName = "getAverageSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageSamplingTime", WLDFHarvesterManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageSamplingTime", currentResult);
         currentResult.setValue("description", "<p>The average amount of time, in nanoseconds, spent in sampling cycles.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentDataSampleCount")) {
         getterName = "getCurrentDataSampleCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentDataSampleCount", WLDFHarvesterManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentDataSampleCount", currentResult);
         currentResult.setValue("description", "<p>The number of collected data samples in the current snapshot.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentSnapshotElapsedTime")) {
         getterName = "getCurrentSnapshotElapsedTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentSnapshotElapsedTime", WLDFHarvesterManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentSnapshotElapsedTime", currentResult);
         currentResult.setValue("description", "<p>The elapsed time, in nanoseconds, of a snapshot.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentSnapshotStartTime")) {
         getterName = "getCurrentSnapshotStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentSnapshotStartTime", WLDFHarvesterManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentSnapshotStartTime", currentResult);
         currentResult.setValue("description", "<p>The start time, in milliseconds, of the latest archived snapshot.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumSamplingTime")) {
         getterName = "getMaximumSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumSamplingTime", WLDFHarvesterManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumSamplingTime", currentResult);
         currentResult.setValue("description", "<p>The maximum observed sampling time, in nanoseconds.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimumSamplingTime")) {
         getterName = "getMinimumSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimumSamplingTime", WLDFHarvesterManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimumSamplingTime", currentResult);
         currentResult.setValue("description", "<p>The minimum observed sampling time, in nanoseconds.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalDataSampleCount")) {
         getterName = "getTotalDataSampleCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalDataSampleCount", WLDFHarvesterManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalDataSampleCount", currentResult);
         currentResult.setValue("description", "<p>The number of configured data samples that have been collected and archived so far in this server session.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalSamplingCycles")) {
         getterName = "getTotalSamplingCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSamplingCycles", WLDFHarvesterManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSamplingCycles", currentResult);
         currentResult.setValue("description", "<p>The total number of periodic sampling cycles taken thus far.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalSamplingTime")) {
         getterName = "getTotalSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSamplingTime", WLDFHarvesterManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSamplingTime", currentResult);
         currentResult.setValue("description", "<p>The total amount of observed time, in nanoseconds, spent in sampling cycles.</p> ");
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
      Method mth = WLDFHarvesterManagerRuntimeMBean.class.getMethod("retrieveSnapshot");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map<ObjectName,AttributeList>");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Retrieves a snapshot of values for attributes configured in this Harvester instance at the moment the request is made; these values are returned to the caller and not persisted in the WLDF archive. The Harvester instance does not need to be actively polling for this call to be made. </p> <p> The returned Map contains a list of instances collected from and the set of values harvested from each instance. The map key is the ObjectName of MBean instance that the values were retrieved from, and the value for each key is an AttributeList containing the attributes and values retrieved from that instance. </p>  <p> Each returned snapshot will contain an implicit entry accessed by the reserved key \"serverTimestamp\"; its AttributeList payload will consist of a single attribute of the name \"serverTimestamp\",  where the value for that &quot;attribute&quot; will be the timestamp in milliseconds, relative to the server, that the snapshot request was made. </p>  <p> NOTE that the attribute names in the returned AttributeList instances <b>may not</b> map to an actual attribute on the target MBean. For example, this would be the case where the attribute of a WLDFHarvestedTypeBean is actually an attribute expression, or in the case above where the snapshot timestamp is returned as an \"attribute\" of the WLDFHarvesterManagerRuntimeMBean instance it was requested from. </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map<ObjectName,AttributeList>");
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
