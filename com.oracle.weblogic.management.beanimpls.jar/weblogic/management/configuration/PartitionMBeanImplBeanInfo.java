package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class PartitionMBeanImplBeanInfo extends ConfigurationPropertiesMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PartitionMBean.class;

   public PartitionMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.PartitionMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("A domain partition. A configuration may define zero or more partitions. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.PartitionMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AdminVirtualTarget")) {
         getterName = "getAdminVirtualTarget";
         setterName = null;
         currentResult = new PropertyDescriptor("AdminVirtualTarget", PartitionMBean.class, getterName, setterName);
         descriptors.put("AdminVirtualTarget", currentResult);
         currentResult.setValue("description", "<p> The administrative virtual target that is automatically created for this partition that has the domain's admin server as its physical target. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("AvailableTargets")) {
         getterName = "getAvailableTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAvailableTargets";
         }

         currentResult = new PropertyDescriptor("AvailableTargets", PartitionMBean.class, getterName, setterName);
         descriptors.put("AvailableTargets", currentResult);
         currentResult.setValue("description", "All the available targets for this partition. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addAvailableTarget");
         currentResult.setValue("remover", "removeAvailableTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("BatchJobsDataSourceJndiName")) {
         getterName = "getBatchJobsDataSourceJndiName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBatchJobsDataSourceJndiName";
         }

         currentResult = new PropertyDescriptor("BatchJobsDataSourceJndiName", PartitionMBean.class, getterName, setterName);
         descriptors.put("BatchJobsDataSourceJndiName", currentResult);
         currentResult.setValue("description", "<p>The JNDI name of the Batch runtime's JobRepository data source, which will be used to store data for Batch jobs submitted from applications deployed to the partition. When a Java EE component submits a Batch job, the Batch runtime updates the JobRepository tables using this data source, which is obtained by looking up the data source's JNDI name. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BatchJobsExecutorServiceName")) {
         getterName = "getBatchJobsExecutorServiceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBatchJobsExecutorServiceName";
         }

         currentResult = new PropertyDescriptor("BatchJobsExecutorServiceName", PartitionMBean.class, getterName, setterName);
         descriptors.put("BatchJobsExecutorServiceName", currentResult);
         currentResult.setValue("description", "<p>The name of the application-scoped Managed Executor Service instance that will be used to run Batch jobs that are submitted from applications deployed to the partition. A Managed Executor Service Template by the same name must exist for the domain when a Batch job is submitted in the partition. If this name returns null, then the Batch runtime will use the default Java EE Managed Executor Service that is bound to the default JNDI name of: <code>java:comp/DefaultManagedExecutorService</code>. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("CoherencePartitionCacheConfigs")) {
         getterName = "getCoherencePartitionCacheConfigs";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherencePartitionCacheConfigs", PartitionMBean.class, getterName, setterName);
         descriptors.put("CoherencePartitionCacheConfigs", currentResult);
         currentResult.setValue("description", "<p>The list of all the Coherence partition cache config beans in this partition. These beans allow definition of shared caches and cache properties.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCoherencePartitionCacheConfig");
         currentResult.setValue("destroyer", "destroyCoherencePartitionCacheConfig");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("DataSourceForJobScheduler")) {
         getterName = "getDataSourceForJobScheduler";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceForJobScheduler";
         }

         currentResult = new PropertyDescriptor("DataSourceForJobScheduler", PartitionMBean.class, getterName, setterName);
         descriptors.put("DataSourceForJobScheduler", currentResult);
         currentResult.setValue("description", "<p>The data source required to support the persistence of jobs scheduled with the job scheduler.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("DataSourcePartition")) {
         getterName = "getDataSourcePartition";
         setterName = null;
         currentResult = new PropertyDescriptor("DataSourcePartition", PartitionMBean.class, getterName, setterName);
         descriptors.put("DataSourcePartition", currentResult);
         currentResult.setValue("description", "<p>The data source partition configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("DefaultTargets")) {
         getterName = "getDefaultTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTargets";
         }

         currentResult = new PropertyDescriptor("DefaultTargets", PartitionMBean.class, getterName, setterName);
         descriptors.put("DefaultTargets", currentResult);
         currentResult.setValue("description", "A list of default targets for the partition (if any). ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addDefaultTarget");
         currentResult.setValue("remover", "removeDefaultTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("ForeignJNDIProviderOverrides")) {
         getterName = "getForeignJNDIProviderOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignJNDIProviderOverrides", PartitionMBean.class, getterName, setterName);
         descriptors.put("ForeignJNDIProviderOverrides", currentResult);
         currentResult.setValue("description", "All the ForeignJNDIProviderOverride MBeans in this partition. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyForeignJNDIProviderOverride");
         currentResult.setValue("creator", "createForeignJNDIProviderOverride");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
      }

      String[] roleObjectArrayGet;
      if (!descriptors.containsKey("GracefulShutdownTimeout")) {
         getterName = "getGracefulShutdownTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGracefulShutdownTimeout";
         }

         currentResult = new PropertyDescriptor("GracefulShutdownTimeout", PartitionMBean.class, getterName, setterName);
         descriptors.put("GracefulShutdownTimeout", currentResult);
         currentResult.setValue("description", "<p>The number of seconds a graceful shutdown operation waits before forcing a shut down. A graceful shutdown gives WebLogic Server subsystems time to complete certain application processing currently in progress. If subsystems are unable to complete processing within the number of seconds that you specify here, the partition will force shutdown automatically.</p> <p/> <p>A value of <code>0</code> means that the partition will wait indefinitely for a graceful shutdown to complete.</p> <p/> <p>The graceful shutdown timeout applies only to graceful shutdown operations.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("#getPartitionLifeCycleTimeoutVal")};
         currentResult.setValue("see", roleObjectArrayGet);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("InternalAppDeployments")) {
         getterName = "getInternalAppDeployments";
         setterName = null;
         currentResult = new PropertyDescriptor("InternalAppDeployments", PartitionMBean.class, getterName, setterName);
         descriptors.put("InternalAppDeployments", currentResult);
         currentResult.setValue("description", "<p>The collection of internal application deployments in this partition.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("restRelationship", "containment");
      }

      if (!descriptors.containsKey("InternalLibraries")) {
         getterName = "getInternalLibraries";
         setterName = null;
         currentResult = new PropertyDescriptor("InternalLibraries", PartitionMBean.class, getterName, setterName);
         descriptors.put("InternalLibraries", currentResult);
         currentResult.setValue("description", "<p>The collection of internal libraries in this partition.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("restRelationship", "containment");
      }

      if (!descriptors.containsKey("JDBCSystemResourceOverrides")) {
         getterName = "getJDBCSystemResourceOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCSystemResourceOverrides", PartitionMBean.class, getterName, setterName);
         descriptors.put("JDBCSystemResourceOverrides", currentResult);
         currentResult.setValue("description", "All the JDBCSystemResourceOverride MBeans in this partition. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJDBCSystemResourceOverride");
         currentResult.setValue("creator", "createJDBCSystemResourceOverride");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JMSSystemResourceOverrides")) {
         getterName = "getJMSSystemResourceOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSSystemResourceOverrides", PartitionMBean.class, getterName, setterName);
         descriptors.put("JMSSystemResourceOverrides", currentResult);
         currentResult.setValue("description", "All the JMS system resource overrides in this partition. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSSystemResourceOverride");
         currentResult.setValue("destroyer", "destroyJMSSystemResourceOverride");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JTAPartition")) {
         getterName = "getJTAPartition";
         setterName = null;
         currentResult = new PropertyDescriptor("JTAPartition", PartitionMBean.class, getterName, setterName);
         descriptors.put("JTAPartition", currentResult);
         currentResult.setValue("description", "<p>The JTA partition configuration for this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JobSchedulerTableName")) {
         getterName = "getJobSchedulerTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJobSchedulerTableName";
         }

         currentResult = new PropertyDescriptor("JobSchedulerTableName", PartitionMBean.class, getterName, setterName);
         descriptors.put("JobSchedulerTableName", currentResult);
         currentResult.setValue("description", "<p>The table name to use for storing timers active with the Job Scheduler.</p> ");
         setPropertyDescriptorDefault(currentResult, "WEBLOGIC_TIMERS");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("MailSessionOverrides")) {
         getterName = "getMailSessionOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("MailSessionOverrides", PartitionMBean.class, getterName, setterName);
         descriptors.put("MailSessionOverrides", currentResult);
         currentResult.setValue("description", "All the MailSessionOverride MBeans in this partition. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMailSessionOverride");
         currentResult.setValue("destroyer", "destroyMailSessionOverride");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ManagedExecutorServiceTemplates")) {
         getterName = "getManagedExecutorServiceTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServiceTemplates", PartitionMBean.class, getterName, setterName);
         descriptors.put("ManagedExecutorServiceTemplates", currentResult);
         currentResult.setValue("description", "All the managed executor service templates. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedExecutorServiceTemplate");
         currentResult.setValue("destroyer", "destroyManagedExecutorServiceTemplate");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ManagedScheduledExecutorServiceTemplates")) {
         getterName = "getManagedScheduledExecutorServiceTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServiceTemplates", PartitionMBean.class, getterName, setterName);
         descriptors.put("ManagedScheduledExecutorServiceTemplates", currentResult);
         currentResult.setValue("description", "All the ManagedScheduledExecutorServiceTemplateMBeans. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedScheduledExecutorServiceTemplate");
         currentResult.setValue("destroyer", "destroyManagedScheduledExecutorServiceTemplate");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ManagedThreadFactoryTemplates")) {
         getterName = "getManagedThreadFactoryTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactoryTemplates", PartitionMBean.class, getterName, setterName);
         descriptors.put("ManagedThreadFactoryTemplates", currentResult);
         currentResult.setValue("description", "List of the ManagedThreadFactory templates. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedThreadFactoryTemplate");
         currentResult.setValue("destroyer", "destroyManagedThreadFactoryTemplate");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("MaxConcurrentLongRunningRequests")) {
         getterName = "getMaxConcurrentLongRunningRequests";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConcurrentLongRunningRequests";
         }

         currentResult = new PropertyDescriptor("MaxConcurrentLongRunningRequests", PartitionMBean.class, getterName, setterName);
         descriptors.put("MaxConcurrentLongRunningRequests", currentResult);
         currentResult.setValue("description", "The maximum number of running long-running requests that can be submitted to all the Managed Executor Services or Managed Scheduled Executor Services in the partition on the current server. ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("MaxConcurrentNewThreads")) {
         getterName = "getMaxConcurrentNewThreads";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConcurrentNewThreads";
         }

         currentResult = new PropertyDescriptor("MaxConcurrentNewThreads", PartitionMBean.class, getterName, setterName);
         descriptors.put("MaxConcurrentNewThreads", currentResult);
         currentResult.setValue("description", "The maximum number of running threads that can be created by all the Managed Thread Factories in the partition on the current server. ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", PartitionMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p> <p/> <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p> <p/> <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PartitionID")) {
         getterName = "getPartitionID";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionID", PartitionMBean.class, getterName, setterName);
         descriptors.put("PartitionID", currentResult);
         currentResult.setValue("description", "The ID for this partition. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PartitionLifeCycleTimeoutVal")) {
         getterName = "getPartitionLifeCycleTimeoutVal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPartitionLifeCycleTimeoutVal";
         }

         currentResult = new PropertyDescriptor("PartitionLifeCycleTimeoutVal", PartitionMBean.class, getterName, setterName);
         descriptors.put("PartitionLifeCycleTimeoutVal", currentResult);
         currentResult.setValue("description", "<p>The number of seconds a force shutdown operation waits before timing out. If the operation does not complete within the configured timeout period, the partition will shutdown automatically if the state of the server at that time was <code>SHUTTING_DOWN</code>.</p> <p/> <p>A value of <code>0</code> means that the partition will wait indefinitely for the life cycle operation to complete.</p> ");
         currentResult.setValue("restProductionModeDefault", new Integer(120));
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("secureValue", new Integer(120));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("PartitionLog")) {
         getterName = "getPartitionLog";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionLog", PartitionMBean.class, getterName, setterName);
         descriptors.put("PartitionLog", currentResult);
         currentResult.setValue("description", "The partition-specific logging configuration. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PartitionWorkManager")) {
         getterName = "getPartitionWorkManager";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionWorkManager", PartitionMBean.class, getterName, setterName);
         descriptors.put("PartitionWorkManager", currentResult);
         currentResult.setValue("description", "<p>The partition-level work manager policy set by the system administrator for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPartitionWorkManager");
         currentResult.setValue("destroyer", "destroyPartitionWorkManager");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PartitionWorkManagerRef")) {
         getterName = "getPartitionWorkManagerRef";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPartitionWorkManagerRef";
         }

         currentResult = new PropertyDescriptor("PartitionWorkManagerRef", PartitionMBean.class, getterName, setterName);
         descriptors.put("PartitionWorkManagerRef", currentResult);
         currentResult.setValue("description", "<p>A reference to a partition-level work manager policy set by the system administrator.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PrimaryIdentityDomain")) {
         getterName = "getPrimaryIdentityDomain";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrimaryIdentityDomain";
         }

         currentResult = new PropertyDescriptor("PrimaryIdentityDomain", PartitionMBean.class, getterName, setterName);
         descriptors.put("PrimaryIdentityDomain", currentResult);
         currentResult.setValue("description", "The partition's primary identity domain. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("VisibleToPartitionsOnSetter", "NEVER");
      }

      if (!descriptors.containsKey("RCMHistoricalDataBufferLimit")) {
         getterName = "getRCMHistoricalDataBufferLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRCMHistoricalDataBufferLimit";
         }

         currentResult = new PropertyDescriptor("RCMHistoricalDataBufferLimit", PartitionMBean.class, getterName, setterName);
         descriptors.put("RCMHistoricalDataBufferLimit", currentResult);
         currentResult.setValue("description", "The maximum number of elements retained for monitoring RCM usage requests over time. ");
         setPropertyDescriptorDefault(currentResult, new Integer(250));
         currentResult.setValue("legalMax", new Integer(5000));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("Realm")) {
         getterName = "getRealm";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRealm";
         }

         currentResult = new PropertyDescriptor("Realm", PartitionMBean.class, getterName, setterName);
         descriptors.put("Realm", currentResult);
         currentResult.setValue("description", "The security realm for this partition. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("VisibleToPartitionsOnSetter", "NEVER");
      }

      if (!descriptors.containsKey("ResourceDeploymentPlan")) {
         getterName = "getResourceDeploymentPlan";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceDeploymentPlan", PartitionMBean.class, getterName, setterName);
         descriptors.put("ResourceDeploymentPlan", currentResult);
         currentResult.setValue("description", "The contents of this resource's deployment plan, returned as a byte[] containing the XML. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("sensitive", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
      }

      if (!descriptors.containsKey("ResourceDeploymentPlanExternalDescriptors")) {
         getterName = "getResourceDeploymentPlanExternalDescriptors";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceDeploymentPlanExternalDescriptors", PartitionMBean.class, getterName, setterName);
         descriptors.put("ResourceDeploymentPlanExternalDescriptors", currentResult);
         currentResult.setValue("description", "A zip file containing the external descriptors referenced in the deployment plan. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("sensitive", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
      }

      if (!descriptors.containsKey("ResourceDeploymentPlanPath")) {
         getterName = "getResourceDeploymentPlanPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceDeploymentPlanPath";
         }

         currentResult = new PropertyDescriptor("ResourceDeploymentPlanPath", PartitionMBean.class, getterName, setterName);
         descriptors.put("ResourceDeploymentPlanPath", currentResult);
         currentResult.setValue("description", "<p>The resource deployment plan path.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("ResourceGroups")) {
         getterName = "getResourceGroups";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceGroups", PartitionMBean.class, getterName, setterName);
         descriptors.put("ResourceGroups", currentResult);
         currentResult.setValue("description", "All the resource groups in this partition. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResourceGroup");
         currentResult.setValue("destroyer", "destroyResourceGroup");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ResourceManager")) {
         getterName = "getResourceManager";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceManager", PartitionMBean.class, getterName, setterName);
         descriptors.put("ResourceManager", currentResult);
         currentResult.setValue("description", "<p>The resource manager policy assigned to the partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResourceManager");
         currentResult.setValue("destroyer", "destroyResourceManager");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ResourceManagerRef")) {
         getterName = "getResourceManagerRef";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceManagerRef";
         }

         currentResult = new PropertyDescriptor("ResourceManagerRef", PartitionMBean.class, getterName, setterName);
         descriptors.put("ResourceManagerRef", currentResult);
         currentResult.setValue("description", "<p>A resource manager reference from the resource management.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("SelfTuning")) {
         getterName = "getSelfTuning";
         setterName = null;
         currentResult = new PropertyDescriptor("SelfTuning", PartitionMBean.class, getterName, setterName);
         descriptors.put("SelfTuning", currentResult);
         currentResult.setValue("description", "The Work Manager configuration for this partition. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("StartupTimeout")) {
         getterName = "getStartupTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStartupTimeout";
         }

         currentResult = new PropertyDescriptor("StartupTimeout", PartitionMBean.class, getterName, setterName);
         descriptors.put("StartupTimeout", currentResult);
         currentResult.setValue("description", "<p>The timeout value for a partition's start and resume operations. If the partition fails to start within the timeout period, it will force a shutdown.</p> <p/> <p>A value of <code>0</code> means that the server will wait indefinitely for the operation to complete.</p> ");
         currentResult.setValue("restProductionModeDefault", new Integer(0));
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("SystemFileSystem")) {
         getterName = "getSystemFileSystem";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemFileSystem", PartitionMBean.class, getterName, setterName);
         descriptors.put("SystemFileSystem", currentResult);
         currentResult.setValue("description", "<p>The file system for the partition's system files. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", PartitionMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("UploadDirectoryName")) {
         getterName = "getUploadDirectoryName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUploadDirectoryName";
         }

         currentResult = new PropertyDescriptor("UploadDirectoryName", PartitionMBean.class, getterName, setterName);
         descriptors.put("UploadDirectoryName", currentResult);
         currentResult.setValue("description", "<p>The directory path on the Administration Server where the uploaded applications for this partition are placed.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("UserFileSystem")) {
         getterName = "getUserFileSystem";
         setterName = null;
         currentResult = new PropertyDescriptor("UserFileSystem", PartitionMBean.class, getterName, setterName);
         descriptors.put("UserFileSystem", currentResult);
         currentResult.setValue("description", "<p>The file system for the partition's user files. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("WebService")) {
         getterName = "getWebService";
         setterName = null;
         currentResult = new PropertyDescriptor("WebService", PartitionMBean.class, getterName, setterName);
         descriptors.put("WebService", currentResult);
         currentResult.setValue("description", "Configures an unique WebServiceMBean for all targets of the partition. Return the WebServiceMBean configuration for this partition ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", PartitionMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("EagerTrackingOfResourceMetricsEnabled")) {
         getterName = "isEagerTrackingOfResourceMetricsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEagerTrackingOfResourceMetricsEnabled";
         }

         currentResult = new PropertyDescriptor("EagerTrackingOfResourceMetricsEnabled", PartitionMBean.class, getterName, setterName);
         descriptors.put("EagerTrackingOfResourceMetricsEnabled", currentResult);
         currentResult.setValue("description", "Determines if tracking of resource consumption metrics of this Partition is initiated eagerly from the time the Partition is started, or if it is initiated lazily on first access of PartitionResourceMetricsRuntimeMBean. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("IgnoreSessionsDuringShutdown")) {
         getterName = "isIgnoreSessionsDuringShutdown";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreSessionsDuringShutdown";
         }

         currentResult = new PropertyDescriptor("IgnoreSessionsDuringShutdown", PartitionMBean.class, getterName, setterName);
         descriptors.put("IgnoreSessionsDuringShutdown", currentResult);
         currentResult.setValue("description", "<p>Indicates whether a graceful shutdown operation drops all HTTP sessions immediately.</p> <p>If this is set to <code>false</code>, a graceful shutdown operation waits for HTTP sessions to complete or timeout.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("ParallelDeployApplicationModules")) {
         getterName = "isParallelDeployApplicationModules";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParallelDeployApplicationModules";
         }

         currentResult = new PropertyDescriptor("ParallelDeployApplicationModules", PartitionMBean.class, getterName, setterName);
         descriptors.put("ParallelDeployApplicationModules", currentResult);
         currentResult.setValue("description", "Determines if the modules of applications will be deployed in parallel.  This setting can be overridden at the per-application level. See {@link AppDeploymentMBean#isParallelDeployModules()} ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("ParallelDeployApplications")) {
         getterName = "isParallelDeployApplications";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParallelDeployApplications";
         }

         currentResult = new PropertyDescriptor("ParallelDeployApplications", PartitionMBean.class, getterName, setterName);
         descriptors.put("ParallelDeployApplications", currentResult);
         currentResult.setValue("description", "Determines if applications will be deployed in parallel. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PartitionMBean.class.getMethod("createResourceGroup", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the resource group to create ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a resource group with the specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceGroups");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyResourceGroup", ResourceGroupMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("group", "the name of the group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes the specified resource group. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceGroups");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("createJDBCSystemResourceOverride", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JDBCSystemResourceOverride MBean to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a JDBCSystemResourceOverride MBeans with the specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCSystemResourceOverrides");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyJDBCSystemResourceOverride", JDBCSystemResourceOverrideMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("group", "the name of the JDBCSystemResourceOverride MBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes the specified JDBCSystemResourceOverride MBean. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCSystemResourceOverrides");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("createMailSessionOverride", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the MailSessionOverride MBean to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a MailSessionOverride MBeans with the name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MailSessionOverrides");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyMailSessionOverride", MailSessionOverrideMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("group", "the name of the MailSessionOverride MBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes the specified MailSessionOverride MBean. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MailSessionOverrides");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("createForeignJNDIProviderOverride", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ForeignJNDIProviderOverride MBean to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a ForeignJNDIProviderOverride MBean with the name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJNDIProviderOverrides");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyForeignJNDIProviderOverride", ForeignJNDIProviderOverrideMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("group", "the name of the ForeignJNDIProviderOverrideMBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes the ForeignJNDIProviderOverride MBean. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJNDIProviderOverrides");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("createManagedExecutorServiceTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedExecutorServiceTemplate to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates ManagedExecutorServiceTemplate with the specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedExecutorServiceTemplates");
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionMBean.class.getMethod("destroyManagedExecutorServiceTemplate", ManagedExecutorServiceTemplateMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "the ManagedExecutorServiceTemplate to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a ManagedExecutorServiceTemplate with the specified short name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedExecutorServiceTemplates");
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionMBean.class.getMethod("createManagedScheduledExecutorServiceTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedScheduledExecutorServiceTemplate to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a ManagedScheduledExecutorServiceTemplate with the specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedScheduledExecutorServiceTemplates");
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionMBean.class.getMethod("destroyManagedScheduledExecutorServiceTemplate", ManagedScheduledExecutorServiceTemplateMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "the ManagedScheduledExecutorServiceTemplate Mbean to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a ManagedScheduledExecutorServiceTemplate. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedScheduledExecutorServiceTemplates");
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionMBean.class.getMethod("createManagedThreadFactoryTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedThreadFactoryTemplate to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a ManagedThreadFactoryTemplate with the specified name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedThreadFactoryTemplates");
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionMBean.class.getMethod("destroyManagedThreadFactoryTemplate", ManagedThreadFactoryTemplateMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "the ManagedThreadFactoryTemplate to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a ManagedThreadFactory template with the specified short name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManagedThreadFactoryTemplates");
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionMBean.class.getMethod("createResourceManager", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates the resource manager policy for the partition.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceManager");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyResourceManager", ResourceManagerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceManagerMBean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys the resource manager policy assigned to the partition.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceManager");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyResourceManagerRef", ResourceManagerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceManagerRef", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the resource manager reference from te resource management.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceManagerRef");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("createCoherencePartitionCacheConfig", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the cache to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Factory method for creating Coherence Partition Cache Config bean. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherencePartitionCacheConfigs");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyCoherencePartitionCacheConfig", CoherencePartitionCacheConfigMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cache", "the name of the Coherence Partition Cache bean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes the Coherence Partition Cache Config bean. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherencePartitionCacheConfigs");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("createJMSSystemResourceOverride", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMSSystemResourceOverride MBean to create ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates JMS System Resource Override MBeans. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSSystemResourceOverrides");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyJMSSystemResourceOverride", JMSSystemResourceOverrideMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jmsSystemResourceOverride", "the JMSSystemResourceOverride MBean to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes JMS System Resource Override MBeans. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSSystemResourceOverrides");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("createPartitionWorkManager", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a partition-level work manager policy set by the system administrator for this partition.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PartitionWorkManager");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyPartitionWorkManager", PartitionWorkManagerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionWorkManager", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the partition-level work manager policy set by the system administrator for this partition.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PartitionWorkManager");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("destroyPartitionWorkManagerRef", PartitionWorkManagerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionWorkManagerRef", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a reference to a partition-level work manager policy set by the system administrator.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PartitionWorkManagerRef");
         currentResult.setValue("excludeFromRest", "");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = PartitionMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = PartitionMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = PartitionMBean.class.getMethod("addDefaultTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("defaultTarget", "target to add ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds an existing target to the list of default targets for this partition. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DefaultTargets");
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionMBean.class.getMethod("removeDefaultTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "the target to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the specified target from the list of default targets for this partition. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DefaultTargets");
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionMBean.class.getMethod("addAvailableTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("availableTarget", "the available target to add ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds an existing target to the list of available targets for this partition. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "AvailableTargets");
      }

      mth = PartitionMBean.class.getMethod("removeAvailableTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "the available target to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the specified target from the list of available targets for this partition. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "AvailableTargets");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PartitionMBean.class.getMethod("lookupResourceGroup", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the resource group to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Look up the named resource group. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ResourceGroups");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("lookupJDBCSystemResourceOverride", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JDBCSystemResourceOverride MBean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up the named JDBCSystemResourceOverride MBean. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JDBCSystemResourceOverrides");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("lookupMailSessionOverride", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the MailSessionOverride MBean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up the named MailSessionOverride MBean. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MailSessionOverrides");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("lookupForeignJNDIProviderOverride", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ForeignJNDIProviderOverride MBean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up the named ForeignJNDIProviderOverride MBean. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignJNDIProviderOverrides");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("lookupManagedExecutorServiceTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedExecutorServiceTemplate MBean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up a particular ManagedExecutorServiceTemplate from the list. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ManagedExecutorServiceTemplates");
      }

      mth = PartitionMBean.class.getMethod("lookupManagedScheduledExecutorServiceTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedScheduledExecutorServiceTemplate to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up a particular ManagedScheduledExecutorServiceTemplate from the list. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ManagedScheduledExecutorServiceTemplates");
      }

      mth = PartitionMBean.class.getMethod("lookupManagedThreadFactoryTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the ManagedThreadFactoryTemplate to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up a particular ManagedThreadFactory template from the list. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ManagedThreadFactoryTemplates");
      }

      mth = PartitionMBean.class.getMethod("lookupCoherencePartitionCacheConfig", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the cache name to lookup ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up the named Coherence Partition Cache Config. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CoherencePartitionCacheConfigs");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("lookupJMSSystemResourceOverride", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the JMSSystemResourceOverride MBean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up the named JMS System Resource Override. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSSystemResourceOverrides");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = PartitionMBean.class.getMethod("lookupInternalAppDeployment", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of the internal application deployment ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "InternalAppDeployments");
      }

      mth = PartitionMBean.class.getMethod("lookupInternalLibrary", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of the internal library ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "InternalLibraries");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PartitionMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = PartitionMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
      }

      mth = PartitionMBean.class.getMethod("findEffectiveTargets");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns list of targets actually used by the resource groups of this partition. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = PartitionMBean.class.getMethod("findEffectiveServerNames");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the names of servers for the partition's effective targets, with no duplicates. ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionMBean.class.getMethod("findEffectiveAdminTargets");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns the list of targets actually used by the administrative resource groups and the AdminVirtualTarget of this partition. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("owner", "Context");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = PartitionMBean.class.getMethod("findAdminResourceGroupsTargeted", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", "the server of interest ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "");
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns the administrative resource groups in the partition targeted to a given server. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("owner", "Context");
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("excludeFromRest", "");
         }
      }

      mth = PartitionMBean.class.getMethod("lookupAvailableTarget", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the target to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up the named available target. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
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
