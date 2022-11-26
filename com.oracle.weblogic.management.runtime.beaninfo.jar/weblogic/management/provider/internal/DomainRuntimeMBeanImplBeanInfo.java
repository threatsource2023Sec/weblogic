package weblogic.management.provider.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.DomainRuntimeMBean;

public class DomainRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DomainRuntimeMBean.class;

   public DomainRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DomainRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.provider.internal.DomainRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.provider.internal");
      String description = (new String("<p>This class is used for monitoring a WebLogic domain. A domain may contain zero or more clusters. A cluster may be looked up by a logical name.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.DomainRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActivationTime")) {
         getterName = "getActivationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("ActivationTime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActivationTime", currentResult);
         currentResult.setValue("description", "<p>The time when the domain became active.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AppRuntimeStateRuntime")) {
         getterName = "getAppRuntimeStateRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("AppRuntimeStateRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AppRuntimeStateRuntime", currentResult);
         currentResult.setValue("description", "<p>Returns a service from which it is possible to determine the state applications throughout the domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("BatchJobRepositoryRuntime")) {
         getterName = "getBatchJobRepositoryRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("BatchJobRepositoryRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BatchJobRepositoryRuntime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceServerLifeCycleRuntimes")) {
         getterName = "getCoherenceServerLifeCycleRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceServerLifeCycleRuntimes", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CoherenceServerLifeCycleRuntimes", currentResult);
         currentResult.setValue("description", "<p>The <code>CoherenceServerLifecycleRuntimeMBean</code> for all configured Coherence servers in the domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "12.2.1.0.0 CoherenceServerLifeCycleRuntimeMBean has been deprecated ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConsoleRuntime")) {
         getterName = "getConsoleRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsoleRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsoleRuntime", currentResult);
         currentResult.setValue("description", "<p>Return the MBean which provides access to console runtime services.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CurrentDomainPartitionRuntime")) {
         getterName = "getCurrentDomainPartitionRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentDomainPartitionRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentDomainPartitionRuntime", currentResult);
         currentResult.setValue("description", "<p>Returns the domain partition runtime MBean for the \"current\" partition</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("DeployerRuntime")) {
         getterName = "getDeployerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("DeployerRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeployerRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides access to the service interface to the interface that is used to deploy new customer applications or modules into this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentManager")) {
         getterName = "getDeploymentManager";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentManager", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentManager", currentResult);
         currentResult.setValue("description", "<p>Provides access to the service interface to the interface that is used to deploy new customer applications or modules into this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DomainPartitionRuntimes")) {
         getterName = "getDomainPartitionRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("DomainPartitionRuntimes", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DomainPartitionRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns domain partition runtimes.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("EditSessionConfigurationManager")) {
         getterName = "getEditSessionConfigurationManager";
         setterName = null;
         currentResult = new PropertyDescriptor("EditSessionConfigurationManager", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EditSessionConfigurationManager", currentResult);
         currentResult.setValue("description", "<p>Provides access to the service interface used to manage named edit sessions in this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ElasticServiceManagerRuntime")) {
         getterName = "getElasticServiceManagerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ElasticServiceManagerRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ElasticServiceManagerRuntime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogRuntime")) {
         getterName = "getLogRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("LogRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogRuntime", currentResult);
         currentResult.setValue("description", "<p>Return the MBean which provides access to the control interface for WLS server logging.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0", (String)null, this.targetVersion) && !descriptors.containsKey("MessageDrivenControlEJBRuntime")) {
         getterName = "getMessageDrivenControlEJBRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDrivenControlEJBRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessageDrivenControlEJBRuntime", currentResult);
         currentResult.setValue("description", "<p>The MessageDrivenControlEJBRuntimeMBean for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0");
      }

      if (!descriptors.containsKey("MigratableServiceCoordinatorRuntime")) {
         getterName = "getMigratableServiceCoordinatorRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("MigratableServiceCoordinatorRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigratableServiceCoordinatorRuntime", currentResult);
         currentResult.setValue("description", "<p>Returns the service used for coordinating the migraiton of migratable services.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigrationDataRuntimes")) {
         getterName = "getMigrationDataRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MigrationDataRuntimes", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigrationDataRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns a history of server migrations. Each array element represents a past or an ongoing migration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("NodeManagerRuntimes")) {
         getterName = "getNodeManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("NodeManagerRuntimes", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NodeManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Provides access to the NodeManagerRuntimeMBeans useful for checking NodeManager reachability</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("PolicySubjectManagerRuntime")) {
         getterName = "getPolicySubjectManagerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectManagerRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectManagerRuntime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResourceGroupLifeCycleRuntimes")) {
         getterName = "getResourceGroupLifeCycleRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceGroupLifeCycleRuntimes", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceGroupLifeCycleRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns life cycle runtimes for domain-level resource groups.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RolloutService")) {
         getterName = "getRolloutService";
         setterName = null;
         currentResult = new PropertyDescriptor("RolloutService", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RolloutService", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0", (String)null, this.targetVersion) && !descriptors.containsKey("SNMPAgentRuntime")) {
         getterName = "getSNMPAgentRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPAgentRuntime", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SNMPAgentRuntime", currentResult);
         currentResult.setValue("description", "<p>Return the MBean which provides access to the monitoring statistics for WLS SNMP Agent.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerLifeCycleRuntimes")) {
         getterName = "getServerLifeCycleRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerLifeCycleRuntimes", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerLifeCycleRuntimes", currentResult);
         currentResult.setValue("description", "<p>The <code>ServerLifecycleRuntimeMBean</code> for all configured servers in the domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("ServiceMigrationDataRuntimes")) {
         getterName = "getServiceMigrationDataRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceMigrationDataRuntimes", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServiceMigrationDataRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns all the service migrations done in the domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("SystemComponentLifeCycleRuntimes")) {
         getterName = "getSystemComponentLifeCycleRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemComponentLifeCycleRuntimes", DomainRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SystemComponentLifeCycleRuntimes", currentResult);
         currentResult.setValue("description", "<p>The <code>SystemComponentLifecycleRuntimeMBean</code> for all configured System Components in the domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeMBean.class.getMethod("lookupServerLifeCycleRuntime", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the server life cycle run-time MBean for the specified server.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ServerLifeCycleRuntimes");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeMBean.class.getMethod("lookupCoherenceServerLifeCycleRuntime", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.4.0");
            currentResult.setValue("deprecated", "12.2.1.0.0 CoherenceServerLifeCycleRuntimeMBean has been deprecated ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the Coherence server life cycle run-time MBean for the specified server.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "CoherenceServerLifeCycleRuntimes");
            currentResult.setValue("since", "10.3.4.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeMBean.class.getMethod("lookupSystemComponentLifeCycleRuntime", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the SystemComponent life cycle run-time MBean for the specified server.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SystemComponentLifeCycleRuntimes");
            currentResult.setValue("since", "12.1.2.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeMBean.class.getMethod("lookupResourceGroupLifeCycleRuntime", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the resource group life cycle run-time MBean for the specified domain-level resource group.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ResourceGroupLifeCycleRuntimes");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeMBean.class.getMethod("lookupDomainPartitionRuntime", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the domain partition runtime MBean for the specified partition</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "DomainPartitionRuntimes");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeMBean.class.getMethod("restartSystemResource", SystemResourceMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resource", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Restarts a system resource on all nodes to which it is deployed.</p> ");
            currentResult.setValue("role", "operation");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", throwsObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeMBean.class.getMethod("startPartitionWait", PartitionMBean.class, String.class, Integer.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionMBean", (String)null), createParameterDescriptor("initialState", "can be ADMIN or RUNNING, Default is RUNNING "), createParameterDescriptor("timeOut", "Specifies the number of milliseconds to start a Partition. Throws InterruptedException if Partition is unable to start during that duration and leaves the Partition in UNKNOWN state. Default is 60000ms ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException"), BeanInfoHelper.encodeEntities("InterruptedException"), BeanInfoHelper.encodeEntities("PartitionLifeCycleException"), BeanInfoHelper.encodeEntities("IllegalArgumentException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> Starts the Partition with initialState and within timeOut msec. This is synchronous operation, where the function will not return untill the partition is started or an exception is thrown </p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeMBean.class.getMethod("forceShutdownPartitionWait", PartitionMBean.class, Integer.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionMBean", (String)null), createParameterDescriptor("timeout", "Specifies the number of milliseconds to stop a Partition. Throws InterruptedException if Partition is unable to stop during that duration and leaves the Partition in UNKNOWN state. Default is 60000ms ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException"), BeanInfoHelper.encodeEntities("PartitionLifeCycleException"), BeanInfoHelper.encodeEntities("InterruptedException"), BeanInfoHelper.encodeEntities("IllegalArgumentException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
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
