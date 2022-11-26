package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ReplicatedStoreMBeanImplBeanInfo extends PersistentStoreMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ReplicatedStoreMBean.class;

   public ReplicatedStoreMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ReplicatedStoreMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ReplicatedStoreMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.3.0");
      beanDescriptor.setValue("deprecated", "12.2.1.3.0 Replace with custom file store or JDBC store. ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Defines an instance of a Replicated Store.</p>  <p>A WebLogic Replicated Store is a high performance storage option for Exalogic hosted Weblogic Messaging Services and is an alternative to WebLogic Server's File and JDBC persistent storage options. Replicated stores depend on Exalogic replicated memory that is managed by a separately administered Daemon Cluster that must be started before a WebLogic Replicated Store can boot.</p>  <p> See <a href=\"http://www.oracle.com/pls/topic/lookup?ctx=wls14110&amp;id=WLEXA221\" rel=\"noopener noreferrer\" target=\"_blank\">Using the WebLogic Replicated Store</a> in Administering WebLogic Server for Oracle Exalogic Elastic Cloud.</p>  <p>This feature should be used only in Oracle Exalogic Elastic Cloud environments.</p>  <p>DEPRECATION NOTICE: With WebLogic Server 12.2.1.3 and with the Exalogic X6 initial release, the WebLogic Replicated Store for JMS messages is deprecated. It will be removed in a future release. Oracle recommends that you use either a custom file store or a JDBC store for JMS message storage. See &quot;Administering the WebLogic Persistent Store&quot; in the documentation for more information about these options.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ReplicatedStoreMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (!descriptors.containsKey("Address")) {
         getterName = "getAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAddress";
         }

         currentResult = new PropertyDescriptor("Address", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("Address", currentResult);
         currentResult.setValue("description", "<p>IP address of a local Daemon. This field is not commonly set - use a 'local index' instead. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setLocalIndex")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BlockSize")) {
         getterName = "getBlockSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlockSize";
         }

         currentResult = new PropertyDescriptor("BlockSize", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("BlockSize", currentResult);
         currentResult.setValue("description", "<p>The smallest addressable block, in bytes, as a power of 2.</p> <p>Shutdown your entire Daemon cluster prior to changing this setting.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(8192));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BusyWaitMicroSeconds")) {
         getterName = "getBusyWaitMicroSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBusyWaitMicroSeconds";
         }

         currentResult = new PropertyDescriptor("BusyWaitMicroSeconds", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("BusyWaitMicroSeconds", currentResult);
         currentResult.setValue("description", "<p>Busy wait microseconds.  See setBusyWaitMicroSeconds for more information. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setBusyWaitMicroSeconds")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Directory")) {
         getterName = "getDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDirectory";
         }

         currentResult = new PropertyDescriptor("Directory", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("Directory", currentResult);
         currentResult.setValue("description", "<p>Specifies the path of the Replicated Store Global Directory.</p> <p>This must be the same directory that is used to store the Daemon Cluster <code>rs_daemons.cfg</code> configuration file and requires a specially tuned NFS mount. Oracle recommends using an absolute directory path on a shared NFS mount.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IoBufferSize")) {
         getterName = "getIoBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIoBufferSize";
         }

         currentResult = new PropertyDescriptor("IoBufferSize", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("IoBufferSize", currentResult);
         currentResult.setValue("description", "<p>The I/O buffer size, in bytes, automatically rounded down to the nearest power of 2.</p> <ul> <li>Controls the amount of WebLogic Server JVM off-heap (native) memory that is allocated for a replicated store.</li>  <li>For the best runtime performance, Oracle recommends setting <code>IOBufferSize</code> so that it is larger than the largest write (multiple concurrent store requests may be combined into a single write).</li>  <li>For the best boot recovery time performance of large stores, Oracle recommends setting <code>IOBufferSize</code> to at least 2 megabytes.</li>  </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(67108864));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalIndex")) {
         getterName = "getLocalIndex";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalIndex";
         }

         currentResult = new PropertyDescriptor("LocalIndex", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("LocalIndex", currentResult);
         currentResult.setValue("description", "<p>Specifies a local Daemon to attach to when a Daemon Cluster has multiple Daemons configured to run on the current node.</p>  <p>The local Daemon is chosen using the formula: ((localindex) modulo (number-of-local-daemons)). When 0, it always resolves to the first available Daemon entry in the <code>rs_daemons.cfg </code> file that has an address on the current node. The default value is 0.</p>  This setting is not applicable to production environments. To ensure high availability, a Daemon Cluster in a production environment should have multiple nodes with one Daemon on each node. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxReplicaCount")) {
         getterName = "getMaxReplicaCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxReplicaCount";
         }

         currentResult = new PropertyDescriptor("MaxReplicaCount", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("MaxReplicaCount", currentResult);
         currentResult.setValue("description", "<p>Maximum number of replicas per Region. Must be equal to or greater than MinReplicaCount. Setting this to a different value than the default is not supported.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMaxReplicaCount")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(32));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumMessageSizePercent")) {
         getterName = "getMaximumMessageSizePercent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumMessageSizePercent";
         }

         currentResult = new PropertyDescriptor("MaximumMessageSizePercent", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("MaximumMessageSizePercent", currentResult);
         currentResult.setValue("description", "<p>The maximum message size for a JMS destination that is backed by a replicated store, specified as a percentage of the store region size. New messages that exceed this size throw a <code>ResourceAllocationException</code>. The total size of all concurrently written replicated store messages must be less than the Region size or failures can result. See related settings for the \"JMS Server or Destination Maximum Message Size\".</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.JMSServerMBean#getMaximumMessageSize()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.JMSDestCommonMBean#getMaximumMessageSize()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinReplicaCount")) {
         getterName = "getMinReplicaCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinReplicaCount";
         }

         currentResult = new PropertyDescriptor("MinReplicaCount", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("MinReplicaCount", currentResult);
         currentResult.setValue("description", "<p>Minimum number of replicas per Region. Must be equal to or lesser than MaxReplicaCount. Setting this to a different value than the default is not supported.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMinReplicaCount")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(32));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Port")) {
         getterName = "getPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPort";
         }

         currentResult = new PropertyDescriptor("Port", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("Port", currentResult);
         currentResult.setValue("description", "<p>Port of a local Daemon. This field is not commonly set - use a 'local index' instead.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setLocalIndex")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RegionSize")) {
         getterName = "getRegionSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRegionSize";
         }

         currentResult = new PropertyDescriptor("RegionSize", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("RegionSize", currentResult);
         currentResult.setValue("description", "<p>The region size, in bytes.</p> <p>Data is stored in one or more uniquely named Regions within a Daemon Cluster where each region is created with this size. Oracle recommends that regions sizes should be tuned to be a small fraction of the available local Daemon memory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(134217728));
         currentResult.setValue("legalMax", new Integer(1073741824));
         currentResult.setValue("legalMin", new Integer(33554432));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SleepWaitMilliSeconds")) {
         getterName = "getSleepWaitMilliSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSleepWaitMilliSeconds";
         }

         currentResult = new PropertyDescriptor("SleepWaitMilliSeconds", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("SleepWaitMilliSeconds", currentResult);
         currentResult.setValue("description", "<p>Sleep wait milli seconds.  See setSleepWaitMilliSeconds for more information. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setSleepWaitMilliSeconds")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SpaceLoggingDeltaPercent")) {
         getterName = "getSpaceLoggingDeltaPercent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSpaceLoggingDeltaPercent";
         }

         currentResult = new PropertyDescriptor("SpaceLoggingDeltaPercent", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("SpaceLoggingDeltaPercent", currentResult);
         currentResult.setValue("description", "<p>Replicated store daemon space usage logging delta. The default is 10 percent, which means the store will log every 10% of space usage change.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SpaceLoggingStartPercent")) {
         getterName = "getSpaceLoggingStartPercent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSpaceLoggingStartPercent";
         }

         currentResult = new PropertyDescriptor("SpaceLoggingStartPercent", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("SpaceLoggingStartPercent", currentResult);
         currentResult.setValue("description", "<p>The percentage of the Daemon Shared Memory Limit when a replicated store will start logging Daemon shared memory usage.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(70));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SpaceOverloadRedPercent")) {
         getterName = "getSpaceOverloadRedPercent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSpaceOverloadRedPercent";
         }

         currentResult = new PropertyDescriptor("SpaceOverloadRedPercent", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("SpaceOverloadRedPercent", currentResult);
         currentResult.setValue("description", "<p>Controls when a Replicated Store reaches its memory error condition as a percentage of the Daemon Shared Memory Limit. When this threshold is reached, the WebLogic server instance starts rejecting new stores that attempt to open on, or migrate to, the underlying daemon.</p> <p>The Space Overload Red threshold should be set higher than the Overload Yellow threshold.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(90));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SpaceOverloadYellowPercent")) {
         getterName = "getSpaceOverloadYellowPercent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSpaceOverloadYellowPercent";
         }

         currentResult = new PropertyDescriptor("SpaceOverloadYellowPercent", ReplicatedStoreMBean.class, getterName, setterName);
         descriptors.put("SpaceOverloadYellowPercent", currentResult);
         currentResult.setValue("description", "<p>Controls when a Replicated Store reaches its memory overload condition. If the memory used by all Replicated Stores on a Daemon exceeds this percentage of the Daemon Shared Memory Limit, and also the data stored in the store exceeds this percentage of the total region memory allocated for the particular store, then a JMS server will reject new messages with a ResourceAllocationException.</p> <p>The space usage overload threshold should be set lower than the Space Overload Red percent.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(80));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
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
