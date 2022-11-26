package weblogic.cluster;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.ReplicationRuntimeBeanInfo;

public class ClusterRuntimeBeanInfo extends ReplicationRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = ClusterRuntimeMBean.class;

   public ClusterRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ClusterRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cluster.ClusterRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.cluster");
      String description = (new String("This class is used for monitoring a server's view of the members of a WebLogic cluster within a WebLogic domain. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ClusterRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveSingletonServices")) {
         getterName = "getActiveSingletonServices";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveSingletonServices", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveSingletonServices", currentResult);
         currentResult.setValue("description", "<p>Returns an array of the names of the singleton services that are active on this server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AliveServerCount")) {
         getterName = "getAliveServerCount";
         setterName = null;
         currentResult = new PropertyDescriptor("AliveServerCount", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AliveServerCount", currentResult);
         currentResult.setValue("description", "<p>Provides the current total number of alive servers in this cluster.</p>  <p>Returns the current total number of alive servers in this cluster.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentMachine")) {
         getterName = "getCurrentMachine";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentMachine", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentMachine", currentResult);
         currentResult.setValue("description", "<p>Provides the current MachineMBean of the server. In most cases this is the MachineMBean that the server is configured to run on. The only time when this will not be true is if auto-migration is enabled. This method will report the current host machine for the server, in that case. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restRelationship", "reference");
         currentResult.setValue("excludeFromRest", "REST does not support references from server runtime mbeans to config mbeans when delegating from the admin server to a managed server");
      }

      if (!descriptors.containsKey("CurrentSecondaryServer")) {
         getterName = "getCurrentSecondaryServer";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentSecondaryServer", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentSecondaryServer", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", "10.3.0.0. deprecated in favor of getSecondaryServerDetails ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetailedSecondariesDistribution")) {
         getterName = "getDetailedSecondariesDistribution";
         setterName = null;
         currentResult = new PropertyDescriptor("DetailedSecondariesDistribution", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DetailedSecondariesDistribution", currentResult);
         currentResult.setValue("description", "<p>Provides the names of the remote servers (such as myserver) for which the local server is hosting secondary objects. The name is appended with a number to indicate the number of secondaries hosted on behalf of that server.</p> ");
      }

      if (!descriptors.containsKey("ForeignFragmentsDroppedCount")) {
         getterName = "getForeignFragmentsDroppedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignFragmentsDroppedCount", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ForeignFragmentsDroppedCount", currentResult);
         currentResult.setValue("description", "<p>Provides the number of fragments that originated in foreign domains or clusters which use the same multicast address.</p>  <p>Answer the number of fragments that originated in foreign domains/cluster that use the same multicast address.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FragmentsReceivedCount")) {
         getterName = "getFragmentsReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FragmentsReceivedCount", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FragmentsReceivedCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total number of messages received on this server from the cluster. This is applicable to both multicast and unicast message types.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FragmentsSentCount")) {
         getterName = "getFragmentsSentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FragmentsSentCount", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FragmentsSentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the total number of message fragments sent from this server into the cluster. This is applicable to both multicast and unicast message types.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>Provides health information returned by the server self-health monitor service.</p> <ul> <li>HEALTH_OK = 0, Server service is healthy.</li>  <li>HEALTH_WARN = 1, Service could have problems in the future. Check the server logs and the corresponding RuntimeMBean for more details.</li>  <li>HEALTH_CRITICAL = 2, Something must be done now to prevent service failure. Check the server logs and the corresponding RuntimeMBean for more details.</li>  <li>HEALTH_FAILED = 3, Service has failed - must be restarted. Check the server logs and the corresponding RuntimeMBean for more details.</li>  <li>HEALTH_OVERLOADED = 4, Service is functioning normally but there is too much work in it. CRITICAL and OVERLOADED are different. A subsystem is in the critical state when a part of it is malfunctioning, for example, stuck threads. An overloaded state means that there is more work assigned to the service than the configured threshold. A service might refuse more work in this state.</li>  <li>LOW_MEMORY_REASON = \"server is low on memory\", Reason code that indicates that the server is low on memory. Administrators can configure low and high thresholds for memory usage. The server health changes to <code>OVERLOADED</code> with this reason code if the low threshold is reached. </li></ul> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.OverloadProtectionMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JobSchedulerRuntime")) {
         getterName = "getJobSchedulerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JobSchedulerRuntime", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JobSchedulerRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides information about past jobs executed on this server. Jobs must have been submitted to the Job Scheduler and should have been executed at least once on this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastMessagesLostCount")) {
         getterName = "getMulticastMessagesLostCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MulticastMessagesLostCount", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MulticastMessagesLostCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total number of in-coming multicast messages that were lost according to this server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrimaryCount")) {
         getterName = "getPrimaryCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrimaryCount", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrimaryCount", currentResult);
         currentResult.setValue("description", "<p>Provides the number of object that the local server hosts as primaries.</p>  <p>Answer the number of object that the local server hosts as primaries.</p> ");
      }

      if (!descriptors.containsKey("ResendRequestsCount")) {
         getterName = "getResendRequestsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResendRequestsCount", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResendRequestsCount", currentResult);
         currentResult.setValue("description", "<p>Provides the number of state-delta messages that had to be resent because a receiving server in the cluster missed a message.</p>  <p>Returns the number of state-delta messages that had to be resent because a receiving server in the cluster missed a message.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondaryCount")) {
         getterName = "getSecondaryCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryCount", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryCount", currentResult);
         currentResult.setValue("description", "<p>Answer the number of object that the local server hosts as secondaries.</p> ");
      }

      if (!descriptors.containsKey("SecondaryDistributionNames")) {
         getterName = "getSecondaryDistributionNames";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryDistributionNames", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryDistributionNames", currentResult);
         currentResult.setValue("description", "<p>Provides the names of the remote servers (such as myserver) for which the local server is hosting secondary objects. The name is appended with a number to indicate the number of secondaries hosted on behalf of that server.</p> ");
         currentResult.setValue("deprecated", "10.3.0.0 deprecated in favor of getDetailedSecondariesDistribution ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondaryServerDetails")) {
         getterName = "getSecondaryServerDetails";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryServerDetails", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryServerDetails", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("ServerMigrationRuntime")) {
         getterName = "getServerMigrationRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerMigrationRuntime", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerMigrationRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides information about server migrations performed by this server. If the current server is not responsible for migrations, it points to the server that is responsible for them.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerNames")) {
         getterName = "getServerNames";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerNames", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerNames", currentResult);
         currentResult.setValue("description", "<p>Provides the names of the servers in the cluster.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnicastMessaging")) {
         getterName = "getUnicastMessaging";
         setterName = null;
         currentResult = new PropertyDescriptor("UnicastMessaging", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UnicastMessaging", currentResult);
         currentResult.setValue("description", "<p>Provides information about unicast messaging mode if enabled.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnreliableServers")) {
         getterName = "getUnreliableServers";
         setterName = null;
         currentResult = new PropertyDescriptor("UnreliableServers", ClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UnreliableServers", currentResult);
         currentResult.setValue("description", "<p>Provides a HashMap of the servers that, from this server's perspective, have dropped out of the cluster at some point during this server's current lifetime. The key is the server name, and the value is an Integer of the number of times the server has disconnected.</p>  <p> This view may not be consistent with the views of other servers. Servers that have never disconnected will not be in the HashMap. The view is not maintained across server restarts. No distinction is made between the possible causes of the disconnect; proper shutdowns cause the disconnect count to go up just like a network outage or a crash would.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         Method mth = ClusterRuntimeMBean.class.getMethod("initiateResourceGroupMigration", String.class, String.class, String.class, Integer.TYPE);
         ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", (String)null), createParameterDescriptor("resourceGroupName", (String)null), createParameterDescriptor("targetCluster", (String)null), createParameterDescriptor("timeout", (String)null)};
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Provides a synchronous method for initiating resource group migration.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
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
