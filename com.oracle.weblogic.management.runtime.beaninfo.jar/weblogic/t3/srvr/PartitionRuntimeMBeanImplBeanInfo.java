package weblogic.t3.srvr;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.PartitionRuntimeMBean;

public class PartitionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PartitionRuntimeMBean.class;

   public PartitionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.t3.srvr.PartitionRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.t3.srvr");
      String description = (new String("<p>Partition Runtime information.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PartitionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ApplicationRuntimes")) {
         getterName = "getApplicationRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationRuntimes", currentResult);
         currentResult.setValue("description", "<p>Currently running Applications of this partition. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("BatchJobRepositoryRuntime")) {
         getterName = "getBatchJobRepositoryRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("BatchJobRepositoryRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BatchJobRepositoryRuntime", currentResult);
         currentResult.setValue("description", "<p>The partition BatchJobRepositoryPartitionRuntimeMBean</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ConcurrentManagedObjectsRuntime")) {
         getterName = "getConcurrentManagedObjectsRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ConcurrentManagedObjectsRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConcurrentManagedObjectsRuntime", currentResult);
         currentResult.setValue("description", "<p>The ConcurrentManagedObjectsRuntimeMBean for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ConnectorServiceRuntime")) {
         getterName = "getConnectorServiceRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectorServiceRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectorServiceRuntime", currentResult);
         currentResult.setValue("description", "<p>The access point for partition specific control and monitoring of the Connector Container.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JDBCPartitionRuntime")) {
         getterName = "getJDBCPartitionRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCPartitionRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JDBCPartitionRuntime", currentResult);
         currentResult.setValue("description", "<p>The JDBCPartitionRuntimeMBean for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JMSRuntime")) {
         getterName = "getJMSRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JMSRuntime", currentResult);
         currentResult.setValue("description", "<p>The JMSRuntimeMBean for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JTAPartitionRuntime")) {
         getterName = "getJTAPartitionRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JTAPartitionRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JTAPartitionRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean which provides access to all JTA runtime MBeans for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("LibraryRuntimes")) {
         getterName = "getLibraryRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LibraryRuntimes", currentResult);
         currentResult.setValue("description", "<p>Deployed Libraries of this partition. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("MailSessionRuntimes")) {
         getterName = "getMailSessionRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MailSessionRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MailSessionRuntimes", currentResult);
         currentResult.setValue("description", "<p>The JavaMail Mail RuntimeMBeans for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("MaxThreadsConstraintRuntimes")) {
         getterName = "getMaxThreadsConstraintRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxThreadsConstraintRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxThreadsConstraintRuntimes", currentResult);
         currentResult.setValue("description", "<p>RuntimeMBeans which expose this partition's globally defined MaxThreadsConstraints.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("MessagingBridgeRuntimes")) {
         getterName = "getMessagingBridgeRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagingBridgeRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagingBridgeRuntimes", currentResult);
         currentResult.setValue("description", "<p>The MessagingBridgeRuntimeMBeans for this partition. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("MinThreadsConstraintRuntimes")) {
         getterName = "getMinThreadsConstraintRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MinThreadsConstraintRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinThreadsConstraintRuntimes", currentResult);
         currentResult.setValue("description", "<p>RuntimeMBeans which exposes this partition's globally defined MinThreadsConstraints.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The name of this configuration. WebLogic Server uses an MBean to implement and persist the configuration.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("OverallHealthState")) {
         getterName = "getOverallHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("OverallHealthState", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OverallHealthState", currentResult);
         currentResult.setValue("description", "<p>The aggregate health state of the partition as reported by components within the partition </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("OverallHealthStateJMX")) {
         getterName = "getOverallHealthStateJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("OverallHealthStateJMX", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OverallHealthStateJMX", currentResult);
         currentResult.setValue("description", "The overall health state of this partition returned as a CompositeData type. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionRuntimeMBean#getOverallHealthState()"), BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PartitionID")) {
         getterName = "getPartitionID";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionID", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionID", currentResult);
         currentResult.setValue("description", "<p>Partition ID for this partition.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PartitionResourceMetricsRuntime")) {
         getterName = "getPartitionResourceMetricsRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionResourceMetricsRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionResourceMetricsRuntime", currentResult);
         currentResult.setValue("description", "<p>The partition resource consumption metrics for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PartitionWorkManagerRuntime")) {
         getterName = "getPartitionWorkManagerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionWorkManagerRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionWorkManagerRuntime", currentResult);
         currentResult.setValue("description", "<p>The PartitionWorkManagerRuntimeMBean for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PathServiceRuntimes")) {
         getterName = "getPathServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("PathServiceRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PathServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Array of all PathServiceRuntimeMBean instances for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("setterDeprecated", "12.2.1.0.0 Use {@link #addPathServiceRuntime} ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PendingRestartResourceMBeans")) {
         getterName = "getPendingRestartResourceMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRestartResourceMBeans", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingRestartResourceMBeans", currentResult);
         currentResult.setValue("description", "<p> Returns all the partition-scoped-resources that need to be restarted </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PendingRestartResources")) {
         getterName = "getPendingRestartResources";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRestartResources", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingRestartResources", currentResult);
         currentResult.setValue("description", "<p> Returns all the partition-scoped-resources that need to be restarted </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PendingRestartSystemResources")) {
         getterName = "getPendingRestartSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRestartSystemResources", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingRestartSystemResources", currentResult);
         currentResult.setValue("description", "<p> Returns all the partition-system-resource that need to be restarted </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PersistentStoreRuntimes")) {
         getterName = "getPersistentStoreRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistentStoreRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PersistentStoreRuntimes", currentResult);
         currentResult.setValue("description", "<p>The mbeans that provide runtime information for each PersistentStore.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("RequestClassRuntimes")) {
         getterName = "getRequestClassRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestClassRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestClassRuntimes", currentResult);
         currentResult.setValue("description", "<p>RuntimeMBeans which exposes this partition's globally defined Request Classes.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("SAFRuntime")) {
         getterName = "getSAFRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SAFRuntime", currentResult);
         currentResult.setValue("description", "<p>The SAFRuntimeMBean for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ServerName")) {
         getterName = "getServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerName", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerName", currentResult);
         currentResult.setValue("description", "<p>The server associated with this configuration. </p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The current state of the partition runtime MBean</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SubState")) {
         getterName = "getSubState";
         setterName = null;
         currentResult = new PropertyDescriptor("SubState", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubState", currentResult);
         currentResult.setValue("description", "<p>The current substate of the partition runtime MBean</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("SubsystemHealthStates")) {
         getterName = "getSubsystemHealthStates";
         setterName = null;
         currentResult = new PropertyDescriptor("SubsystemHealthStates", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubsystemHealthStates", currentResult);
         currentResult.setValue("description", "An array of health states for major subsystems in the partition. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("SubsystemHealthStatesJMX")) {
         getterName = "getSubsystemHealthStatesJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("SubsystemHealthStatesJMX", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubsystemHealthStatesJMX", currentResult);
         currentResult.setValue("description", "The health states for major subsystems in the partition as an array of CompositeData type. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SystemFileSystemRoot")) {
         getterName = "getSystemFileSystemRoot";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemFileSystemRoot", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SystemFileSystemRoot", currentResult);
         currentResult.setValue("description", "<p>System file system root directory for this partition.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("UserFileSystemRoot")) {
         getterName = "getUserFileSystemRoot";
         setterName = null;
         currentResult = new PropertyDescriptor("UserFileSystemRoot", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UserFileSystemRoot", currentResult);
         currentResult.setValue("description", "<p>User file system root directory for this partition.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("WLDFPartitionRuntime")) {
         getterName = "getWLDFPartitionRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFPartitionRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFPartitionRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean which provides access to all Diagnostic runtime MBeans for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("WorkManagerRuntimes")) {
         getterName = "getWorkManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntimes", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>An array of MBeans which expose this partition's active internal WorkManagers.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("WseeClusterFrontEndRuntime")) {
         getterName = "getWseeClusterFrontEndRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeClusterFrontEndRuntime", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseeClusterFrontEndRuntime", currentResult);
         currentResult.setValue("description", "A non-null value only when this partition of the server is running as a host to a front-end proxy (HttpClusterServlet) instance. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("RestartRequired")) {
         getterName = "isRestartRequired";
         setterName = null;
         currentResult = new PropertyDescriptor("RestartRequired", PartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RestartRequired", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the partition must be restarted in order to activate configuration changes.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PartitionRuntimeMBean.class.getMethod("lookupApplicationRuntime", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ApplicationRuntime MBean to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The name of the ApplicationRuntimeMBean requested. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ApplicationRuntimes");
         String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = PartitionRuntimeMBean.class.getMethod("lookupLibraryRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the LibraryRuntimeMBean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The name of the LibraryRuntimeMBean requested. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "LibraryRuntimes");
      }

      mth = PartitionRuntimeMBean.class.getMethod("lookupMinThreadsConstraintRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MinThreadsConstraintRuntimes");
      }

      mth = PartitionRuntimeMBean.class.getMethod("lookupRequestClassRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "RequestClassRuntimes");
      }

      mth = PartitionRuntimeMBean.class.getMethod("lookupMaxThreadsConstraintRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MaxThreadsConstraintRuntimes");
      }

      mth = PartitionRuntimeMBean.class.getMethod("lookupMessagingBridgeRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the messagingBridgeRuntime to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MessagingBridgeRuntimes");
      }

      mth = PartitionRuntimeMBean.class.getMethod("lookupPersistentStoreRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the persistent store Runtime mbean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The Runtime mbean for the persistent store with the specified short name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "PersistentStoreRuntimes");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PartitionRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Boolean.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Seconds to wait for partition to transition gracefully. The {@link #forceSuspend()} is called after timeout. "), createParameterDescriptor("ignoreSessions", "drop inflight HTTP sessions during graceful suspend ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] roleObjectArray;
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.PartitionLifeCycleException if partition failed to suspend gracefully.  A {@link #forceSuspend()} or a {@link #forceShutdown()} operation can be  invoked.")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Transitions the partition from <code>RUNNING</code> to <code>ADMIN</code> state gracefully.</p>  <p>Applications are in admin mode. Inflight work is completed. Applications and resources are fully available to administrators in <code>ADMIN</code> state. Non-admin users are denied access to applications and resources</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionRuntimeMBean.class.getMethod("suspend");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends the partition. Denies new requests except by privileged users. Allows pending requests to complete. This operation transitions the partition into <code>ADMIN</code> state. Applications and resources are fully available to administrators in <code>ADMIN</code> state. Non-admin users are denied access to applications and resources</p> ");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspend(int, boolean)")};
         currentResult.setValue("see", roleObjectArray);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionRuntimeMBean.class.getMethod("forceSuspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.PartitionLifeCycleException partition failed to force suspend.  A {@link #forceShutdown()} operation can be invoked.")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Transitions the partition from <code>RUNNING</code> to <code>ADMIN</code> state forcefully cancelling inflight work.</p>  <p>Work that cannot be cancelled is dropped. Applications are transitioned to admin mode. This forcefully suspends the partition and transitions it to <code>ADMIN</code> state.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionRuntimeMBean.class.getMethod("resume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resume a suspended partition. Allow new requests. This operation transitions the partition into <code>RUNNING</code> state.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the partition. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "<code>true</code> indicates waiting for all HTTP sessions during inflight work handling; <code>false</code> indicates waiting for non-persisted HTTP sessions only. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully shuts down the partition after handling inflight work; optionally ignores pending HTTP sessions while handling inflight work.</p>  <p>The following inflight work is allowed to complete before shutdown:</p>  <ul> <li> <p>Pending transaction's and TLOG checkpoint</p> </li>  <li> <p>Pending HTTP sessions</p> </li>  <li> <p>Pending JMS work</p> </li>  <li> <p>Pending work in the execute queues</p> </li>  <li> <p>RMI requests with transaction context</p> </li> </ul>  <p>Further administrative calls are accepted while the partition is completing inflight work. For example, a forceShutdown command can be issued to quickly shutdown the partition if graceful shutdown is taking too long to complete.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", (String)null), createParameterDescriptor("ignoreSessions", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully shuts down the partition after handling inflight work.</p>  <p>This method is same to call: <code>shutdown(timeout, ignoreSessions, false);</code></p> ");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("#shutdown(int, boolean, boolean)")};
         currentResult.setValue("see", roleObjectArray);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionRuntimeMBean.class.getMethod("shutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully shuts down the partition after handling inflight work.</p> ");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("#shutdown(int, boolean, boolean)")};
         currentResult.setValue("see", roleObjectArray);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionRuntimeMBean.class.getMethod("halt");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Halts the partition. </p> ");
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("owner", "Context");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      mth = PartitionRuntimeMBean.class.getMethod("forceShutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Force shutdown the partition. Causes the partition to reject new requests and fail pending requests.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionRuntimeMBean.class.getMethod("startResourceGroup", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", "The resource group name ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.ResourceGroupLifecycleException resource group failed to start.")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>starts the resource group.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("startResourceGroupInAdmin", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.ResourceGroupLifecycleException resource group failed to start in admin mode.")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts the resource group in ADMIN state. Applications and resources are fully available to administrators in <code>ADMIN</code> state. Non-admin users are denied access to applications and resources</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("suspendResourceGroup", String.class, Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null), createParameterDescriptor("timeout", (String)null), createParameterDescriptor("ignoreSessions", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends resource group. Denies new requests except by privileged users. Allows pending requests to complete. This operation transitions the partition into <code>ADMIN</code> state. Applications and resources are fully available to administrators in <code>ADMIN</code> state. Non-admin users are denied access to applications and resources</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("suspendResourceGroup", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends resource group. Denies new requests except by privileged users. Allows pending requests to complete. This operation transitions the partition into <code>ADMIN</code> state. Applications and resources are fully available to administrators in <code>ADMIN</code> state. Non-admin users are denied access to applications and resources</p> ");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspendResourceGroup(String, int, boolean)")};
         currentResult.setValue("see", roleObjectArray);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("forceSuspendResourceGroup", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.ResourceGroupLifecycleException partition failed to force suspend.  A {@link #forceShutdown()} operation can be invoked.")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Transitions the resource group from <code>RUNNING</code> to <code>ADMIN</code> state forcefully cancelling inflight work.</p>  <p>Work that cannot be cancelled is dropped. Applications are brought into the admin mode. This forcefully suspends the resource group and transitions it <code>ADMIN</code> state.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("resumeResourceGroup", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resume suspended resource group. Allow new requests. This operation transitions the resource group into <code>RUNNING</code> state.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("forceShutdownResourceGroup", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Force shutdown the resource group. Causes the resource group to reject new requests and fail pending requests.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("shutdownResourceGroup", String.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null), createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the partition. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "<code>true</code> indicates waiting for all HTTP sessions during inflight work handling; <code>false</code> indicates waiting for non-persisted HTTP sessions only. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully shuts down the partition after handling inflight work. Optionally, ignores pending HTTP sessions while handling inflight work.</p>  <p>The following inflight work is allowed to complete before shutdown:</p>  <ul> <li>Pending transaction's and TLOG checkpoint</li> <li>Pending HTTP sessions</li> <li>Pending JMS work</li> <li>Pending work in the execute queues</li> <li>RMI requests with transaction context</li> </ul>  <p>Further administrative calls are accepted while the server is completing inflight work. For example a forceShutdown command can be issued to quickly shutdown the partition if graceful shutdown takes a long time.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("shutdownResourceGroup", String.class, Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null), createParameterDescriptor("timeout", (String)null), createParameterDescriptor("ignoreSessions", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully shuts down the partition after handling inflight work.</p>  <p>This method is same to call: <code>shutdown(timeout, ignoreSessions, false);</code></p> ");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("#shutdownResourceGroup(String, int, boolean, boolean)")};
         currentResult.setValue("see", roleObjectArray);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("shutdownResourceGroup", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully shuts down the resource group after handling inflight work.</p> ");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("#shutdownResourceGroup(String, int, boolean, boolean)")};
         currentResult.setValue("see", roleObjectArray);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("getRgState", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", "the resource group name ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The current state of the named resource group MBean</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionRuntimeMBean.class.getMethod("isRestartPendingForResourceMBean", ConfigurationMBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Returns if a partition-scoped-resource has pending restart or not. </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = PartitionRuntimeMBean.class.getMethod("isRestartPendingForResource", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Returns if a partition-scoped-resource has pending restart or not. </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = PartitionRuntimeMBean.class.getMethod("isRestartPendingForSystemResource", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Returns if a partition-system-resource has pending restart or not. </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionRuntimeMBean.class.getMethod("urlMappingForVT", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("vtName", (String)null), createParameterDescriptor("protocol", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns all the urls for virtual target provided by the user. The url contains <physical host address>:port of all the managed server(s) targeted to the cluster (which in turn is targeted to the virtual target). If the explicit port of virtual target is set, that is used instead of physical port of the server. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("owner", "Context");
            currentResult.setValue("since", "12.2.1.1.0");
         }
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
