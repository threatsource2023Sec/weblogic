package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DynamicDeploymentMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DynamicDeploymentMBean.class;

   public DynamicDeploymentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DynamicDeploymentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.DynamicDeploymentMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Definition of a dynamic deployment interface ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DynamicDeploymentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (!descriptors.containsKey("DistributionPolicy")) {
         getterName = "getDistributionPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDistributionPolicy";
         }

         currentResult = new PropertyDescriptor("DistributionPolicy", DynamicDeploymentMBean.class, getterName, setterName);
         descriptors.put("DistributionPolicy", currentResult);
         currentResult.setValue("description", "<p>Specifies how the instances of a configured JMS artifact are named and distributed when cluster-targeted.  A JMS artifact is cluster-targeted when its target is directly set to a cluster, or when it is scoped to a resource group and the resource group is in turn targeted to a cluster.  When this setting is configured on a store, it applies to all JMS artifacts that reference the store.  Valid options:</p> <ul> <li> <code>Distributed</code> Creates an instance on each server JVM in a cluster. Required for all SAF agents and for cluster-targeted or resource-group-scoped JMS servers that host distributed destinations. </li>  <li> <code>Singleton</code> Creates a single instance on a single server JVM within a cluster. Required for cluster-targeted or resource-group-scoped JMS servers that host standalone (non-distributed) destinations and for cluster-targeted or resource-group-scoped path services.  The <code>Migration Policy</code> must be <code>On-Failure</code> or <code>Always</code> when using this option with a JMS server, <code>On-Failure</code> when using this option with a messaging bridge, and <code>Always</code> when using this option with a path service. </li> </ul>  <p><b>Instance Naming Note:</b></p> <ul> <li> The <code>DistributionPolicy</code> determines the instance name suffix for cluster-targeted JMS artifacts.  The suffix for a cluster-targeted <code>Singleton</code> is <code>-01</code> and for a cluster-targeted <code>Distributed</code> is <code>@ClusterMemberName</code>. </li> </ul>  <p><b>Messaging Bridge Notes:</b></p> <ul> <li> When an instance per server is desired for a cluster-targeted messaging bridge, Oracle recommends setting the bridge <code>Distributed Policy</code> and <code>Migration Policy</code> to <code>Distributed/Off</code>, respectively; these are the defaults. </li> <li> When a single instance per cluster is desired for a cluster-targeted bridge, Oracle recommends setting the bridge <code>Distributed Policy</code> and <code>Migration Policy</code> to <code>Singleton/On-Failure</code>, respectively. </li> <li> If you cannot cluster-target a bridge and still need singleton behavior in a configured cluster, you can target the bridge to a migratable target and configure the <code>Migration Policy</code> on the migratable target to <code>Exactly-Once</code>. </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMigrationPolicy"), BeanInfoHelper.encodeEntities("#setDistributionPolicy")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "Distributed");
         currentResult.setValue("legalValues", new Object[]{"Distributed", "Singleton"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailOverLimit")) {
         getterName = "getFailOverLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailOverLimit";
         }

         currentResult = new PropertyDescriptor("FailOverLimit", DynamicDeploymentMBean.class, getterName, setterName);
         descriptors.put("FailOverLimit", currentResult);
         currentResult.setValue("description", "<p>Specify a limit for the number of cluster-targeted JMS artifact instances that can fail over to a particular JVM.</p>  <p>This can be used to prevent too many instances from starting on a server, avoiding a system failure when starting too few servers of a formerly large cluster.</p>  <p>A typical limit value should allow all instances to run in the smallest desired cluster size, which means (smallest-cluster-size * (limit + 1)) should equal or exceed the total number of instances. </p>  <ul> <li>A value of <code>-1</code> means there is no fail over limit (unlimited).</li> <li>A value of <code>0</code> prevents any fail overs of cluster-targeted JMS artifact instances, so no more than 1 instance will run per server (this is an instance that has not failed over).</li> <li>A value of <code>1</code> allows one fail-over instance on each server, so no more than two instances will run per server (one failed over instance plus an instance that has not failed over).</li> </ul>  <b>Note:</b> This setting only applies when the JMS artifact is cluster-targeted and the Migration Policy is set to <code>On-Failure</code> or <code>Always</code>. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailbackDelaySeconds")) {
         getterName = "getFailbackDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailbackDelaySeconds";
         }

         currentResult = new PropertyDescriptor("FailbackDelaySeconds", DynamicDeploymentMBean.class, getterName, setterName);
         descriptors.put("FailbackDelaySeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the amount of time, in seconds, to delay before failing a cluster-targeted JMS artifact instance back to its preferred server after the preferred server failed and was restarted.</p>  <p>This delay allows time for the system to stabilize and dependent services to be restarted, preventing a system failure during a reboot.</p>  <ul> <li>A value > <code>0</code> specifies the time, in seconds, to delay before failing a JMS artifact back to its user preferred server.</li> <li>A value of <code>0</code> indicates that the instance would never failback.</li> <li>A value of <code>-1</code> indicates that there is no delay and the instance would failback immediately.</li> </ul>  <b>Note:</b> This setting only applies when the JMS artifact is cluster-targeted and the Migration Policy is set to <code>On-Failure</code> or <code>Always</code>. ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialBootDelaySeconds")) {
         getterName = "getInitialBootDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialBootDelaySeconds";
         }

         currentResult = new PropertyDescriptor("InitialBootDelaySeconds", DynamicDeploymentMBean.class, getterName, setterName);
         descriptors.put("InitialBootDelaySeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the amount of time, in seconds, to delay before starting a cluster-targeted JMS instance on a newly booted WebLogic Server instance. When this setting is configured on a store, it applies to all JMS artifacts that reference the store. </p> <p>This allows time for the system to stabilize and dependent services to be restarted, preventing a system failure during a reboot.</p>  <ul> <li>A value > <code>0</code> is the time, in seconds, to delay before before loading resources after a failure and restart.</li> <li>A value of <code>0</code> specifies no delay.</li> </ul> <b>Note:</b> This setting only applies when the JMS artifact is cluster-targeted and the Migration Policy is set to <code>On-Failure</code> or <code>Always</code>. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#PartialClusterStabilityDelaySeconds")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(60L));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigrationPolicy")) {
         getterName = "getMigrationPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMigrationPolicy";
         }

         currentResult = new PropertyDescriptor("MigrationPolicy", DynamicDeploymentMBean.class, getterName, setterName);
         descriptors.put("MigrationPolicy", currentResult);
         currentResult.setValue("description", "<p>Controls migration and restart behavior of cluster-targeted JMS service artifact instances. When this setting is configured on a cluster-targeted store, it applies to all JMS artifacts that reference the store. See the migratable target settings for enabling migration and restart on migratable-targeted JMS artifacts.</p> <ul> <li><code>Off</code> Disables migration support for cluster-targeted JMS service objects, and changes the default for Restart In Place to false. If you want a restart to be enabled when the Migration Policy is Off, then Restart In Place must be explicitly configured to true. This policy cannot be combined with the <code>Singleton</code> Migration Policy. </li> <li><code>On-Failure</code> Enables automatic migration and restart of instances on the failure of a subsystem Service or WebLogic Server instance, including automatic fail-back and load balancing of instances. </li>  <li><code>Always</code> Provides the same behavior as <code>On-Failure</code> and automatically migrates instances even in the event of a graceful shutdown or a partial cluster start. </li> </ul> <b>Note:</b> Cluster leasing must be configured for <code>On-Failure</code> and <code>Always</code>.  <p><b>Messaging Bridge Notes:</b></p> <ul> <li> When an instance per server is desired for a cluster-targeted messaging bridge, Oracle recommends setting the bridge <code>Distributed Policy</code> and <code>Migration Policy</code> to <code>Distributed/Off</code>, respectively; these are the defaults. </li> <li> When a single instance per cluster is desired for a cluster-targeted bridge, Oracle recommends setting the bridge <code>Distributed Policy</code> and <code>Migration Policy</code> to <code>Singleton/On-Failure</code>, respectively. </li> <li> A <code>Migration Policy</code> of <code>Always</code> is not recommended for bridges. </li> <li> If you cannot cluster-target a bridge and still need singleton behavior in a configured cluster, you can target the bridge to a migratable target and configure the <code>Migration Policy</code> on the migratable target to <code>Exactly-Once</code>. </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMigrationPolicy")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "Off");
         currentResult.setValue("legalValues", new Object[]{"Off", "On-Failure", "Always"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumberOfRestartAttempts")) {
         getterName = "getNumberOfRestartAttempts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumberOfRestartAttempts";
         }

         currentResult = new PropertyDescriptor("NumberOfRestartAttempts", DynamicDeploymentMBean.class, getterName, setterName);
         descriptors.put("NumberOfRestartAttempts", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum number of restart attempts.</p>  <ul> <li>A value > <code>0</code> specifies the maximum number of restart attempts.</li> <li>A value of <code>0</code> specifies the same behavior as setting {@link #getRestartInPlace} to <code>false</code>.</li> <li>A value of <code>-1</code> means infinite retry restart until it either starts or the server instance shuts down.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(6));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PartialClusterStabilityDelaySeconds")) {
         getterName = "getPartialClusterStabilityDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPartialClusterStabilityDelaySeconds";
         }

         currentResult = new PropertyDescriptor("PartialClusterStabilityDelaySeconds", DynamicDeploymentMBean.class, getterName, setterName);
         descriptors.put("PartialClusterStabilityDelaySeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the amount of time, in seconds, to delay before a partially started cluster starts all cluster-targeted JMS artifact instances that are configured with a Migration Policy of <code>Always</code> or <code>On-Failure</code>. </p>  <p>Before this timeout expires or all servers are running, a cluster starts a subset of such instances based on the total number of servers running and the configured cluster size. Once the timeout expires or all servers have started, the system considers the cluster stable and starts any remaining services.</p>  <p>This delay ensures that services are balanced across a cluster even if the servers are started sequentially. It is ignored after a cluster is fully started (stable) or when individual servers are started.</p>  <ul> <li>A value > <code>0</code> specifies the time, in seconds, to delay before a partially started cluster starts dynamically configured services.</li> <li>A value of <code>0</code> specifies no delay.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getInitialBootDelaySeconds")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(240L));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestartInPlace")) {
         getterName = "getRestartInPlace";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartInPlace";
         }

         currentResult = new PropertyDescriptor("RestartInPlace", DynamicDeploymentMBean.class, getterName, setterName);
         descriptors.put("RestartInPlace", currentResult);
         currentResult.setValue("description", "<p> Enables a periodic automatic in-place restart of failed cluster-targeted or standalone-server-targeted JMS artifact instance(s) running on healthy WebLogic Server instances. See the migratable target settings for in-place restarts of migratable-targeted JMS artifacts. When the Restart In Place setting is configured on a store, it applies to all JMS artifacts that reference the store.</p>  <ul>  <li>If the Migration Policy of the JMS artifact is set to <code>Off</code>, Restart In Place is disabled by default.</li>  <li>If the Migration Policy of the JMS artifact is set to <code>On-Failure</code> or <code>Always</code>, Restart In Place is enabled by default.</li>  <li>This attribute is not used by WebLogic messaging bridges which automatically restart internal connections as needed.</li>  <li>For a JMS artifact that is cluster-targeted and the Migration Policy is set to <code>On-Failure</code> or <code>Always</code>, if restart fails after the configured maximum retry attempts, it will migrate to a different server within the cluster. </li>  </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setRestartInPlace")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondsBetweenRestarts")) {
         getterName = "getSecondsBetweenRestarts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecondsBetweenRestarts";
         }

         currentResult = new PropertyDescriptor("SecondsBetweenRestarts", DynamicDeploymentMBean.class, getterName, setterName);
         descriptors.put("SecondsBetweenRestarts", currentResult);
         currentResult.setValue("description", "<p>Specifies the amount of time, in seconds, to wait in between attempts to restart a failed service instance.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMin", new Integer(1));
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
