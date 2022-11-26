package weblogic.cacheprovider.coherence.management;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.CoherenceClusterMetricsRuntimeMBean;

public class CoherenceClusterMetricsRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceClusterMetricsRuntimeMBean.class;

   public CoherenceClusterMetricsRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceClusterMetricsRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cacheprovider.coherence.management.CoherenceClusterMetricsRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.3");
      beanDescriptor.setValue("package", "weblogic.cacheprovider.coherence.management");
      String description = (new String("<p>A CoherenceClusterRuntimeMetricsMBean gathers metrics from nodes in the cluster according to the specified report group xml file, and then provides a way to query for subsets of the group file by nodes and by table name, which refers to individual file names within the report group file.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.CoherenceClusterMetricsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CoherenceClusterSystemResource")) {
         getterName = "getCoherenceClusterSystemResource";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterSystemResource", CoherenceClusterMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CoherenceClusterSystemResource", currentResult);
         currentResult.setValue("description", "<p>Get the CCSR associated with this Coherence Metrics Runtime MBean if there is one.</p>  <p>The Coherence Metrics MBean can either be associated with a CCSR or it can be outside of WLS domain and simply have a list of CoherenceManagementJMXAddresses.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restRelationship", "reference");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceManagementCluster")) {
         getterName = "getCoherenceManagementCluster";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceManagementCluster", CoherenceClusterMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CoherenceManagementCluster", currentResult);
         currentResult.setValue("description", "<p>Get the CoherenceManagementClusterMBean associated with this Coherence Metrics Runtime MBean if there is one.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("restRelationship", "reference");
      }

      if (!descriptors.containsKey("Instances")) {
         getterName = "getInstances";
         setterName = null;
         currentResult = new PropertyDescriptor("Instances", CoherenceClusterMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Instances", currentResult);
         currentResult.setValue("description", "<p>Get the Management nodes names.</p> ");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("NameServiceAddresses")) {
         getterName = "getNameServiceAddresses";
         setterName = null;
         currentResult = new PropertyDescriptor("NameServiceAddresses", CoherenceClusterMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NameServiceAddresses", currentResult);
         currentResult.setValue("description", "<p>Get the list of socket addresses for connecting to the NameService. It can be used to lookup JMXServiceURL for the MBean Connector.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for ListSocketAddress");
      }

      if (!descriptors.containsKey("ReportGroupFile")) {
         getterName = "getReportGroupFile";
         setterName = null;
         currentResult = new PropertyDescriptor("ReportGroupFile", CoherenceClusterMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReportGroupFile", currentResult);
         currentResult.setValue("description", "<p>The report group file representing the superset of metrics this bean will gather</p> ");
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
      Method mth = CoherenceClusterMetricsRuntimeMBean.class.getMethod("getSchema", String[].class, String[].class, Properties.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("asTables", "an array of table names to get a schema for. If null, get all tables possible "), createParameterDescriptor("asNodeId", "an array of nodeIds specifying which nodes to get schemas from, e.g. {\"2\", \"3\"} If null, query all nodes possible. "), createParameterDescriptor("properties", "the property dms.use.cache will tell us whether to use the cache (true) or not (false) ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Acquire the superset of tabularTypes available from running the given table names on the specified nodes.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
      }

      mth = CoherenceClusterMetricsRuntimeMBean.class.getMethod("getMetrics", String[].class, String[].class, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("asTables", "an array of table names to return metrics for. If null, get all tables possible. "), createParameterDescriptor("asNodeId", "an array of nodeIds specifying which nodes to get schemas from, e.g. {\"2\", \"3\"} If null, query all nodes possible. "), createParameterDescriptor("properties", "the property dms.use.cache will tell us whether to use the cache (true) or not (false) ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Get the requested table metrics from the requested target nodes.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
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
