package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DatabaseLessLeasingBasisMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DatabaseLessLeasingBasisMBean.class;

   public DatabaseLessLeasingBasisMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DatabaseLessLeasingBasisMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.DatabaseLessLeasingBasisMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("DatabaseLessLeasingBasisMBean defines attributes related to the functioning of singleton services and server migration without the use of a HA database. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DatabaseLessLeasingBasisMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("FenceTimeout")) {
         getterName = "getFenceTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFenceTimeout";
         }

         currentResult = new PropertyDescriptor("FenceTimeout", DatabaseLessLeasingBasisMBean.class, getterName, setterName);
         descriptors.put("FenceTimeout", currentResult);
         currentResult.setValue("description", "The timeout to wait and retry getting the server state when the NodeManager is unreachable. We try once more after waiting for the fence timeout period to make sure that the machine is really unavailable as opposed to heavy disk swapping. ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LeaderHeartbeatPeriod")) {
         getterName = "getLeaderHeartbeatPeriod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLeaderHeartbeatPeriod";
         }

         currentResult = new PropertyDescriptor("LeaderHeartbeatPeriod", DatabaseLessLeasingBasisMBean.class, getterName, setterName);
         descriptors.put("LeaderHeartbeatPeriod", currentResult);
         currentResult.setValue("description", "Gets the LeaderHeartbeatPeriod value. The cluster leader heartbeats a special leader heartbeat every period seconds to publish group view version and other cluster information. Members look at this heartbeat and perform any sync up operations if required. ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MemberDiscoveryTimeout")) {
         getterName = "getMemberDiscoveryTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemberDiscoveryTimeout";
         }

         currentResult = new PropertyDescriptor("MemberDiscoveryTimeout", DatabaseLessLeasingBasisMBean.class, getterName, setterName);
         descriptors.put("MemberDiscoveryTimeout", currentResult);
         currentResult.setValue("description", "Gets the MemberDiscoveryTimeout value. This value defines the amount of time a server waits during or after startup to discover members that belong to the same cluster. This information is used to join or form a new cluster. ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMemberDiscoveryTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMin", new Integer(10));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageDeliveryTimeout")) {
         getterName = "getMessageDeliveryTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageDeliveryTimeout";
         }

         currentResult = new PropertyDescriptor("MessageDeliveryTimeout", DatabaseLessLeasingBasisMBean.class, getterName, setterName);
         descriptors.put("MessageDeliveryTimeout", currentResult);
         currentResult.setValue("description", "Gets the message delivery timeout value. This is the amount of time a server waits to get a response from the remote peer before taking recovery actions. ");
         setPropertyDescriptorDefault(currentResult, new Integer(5000));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NodeManagerTimeoutMillis")) {
         getterName = "getNodeManagerTimeoutMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNodeManagerTimeoutMillis";
         }

         currentResult = new PropertyDescriptor("NodeManagerTimeoutMillis", DatabaseLessLeasingBasisMBean.class, getterName, setterName);
         descriptors.put("NodeManagerTimeoutMillis", currentResult);
         currentResult.setValue("description", "<p> NodeManager timeout. Amount of time to wait for a response from NodeManager. NodeManager is used to get server state and decide if a server is really dead. Note that the server automatically calculates a timeout value and this mbean attribute is used as a upper bound. </p> <p> The NodeManager timeout is dependent on the type of the NodeManager used. For SSH nodemanager, the timeout is on the larger side due to the nature of the SSH connection establishment. The default timeout value might appear very large for some installations. Please set the timeout to a value that is representative of SSH performance in your environment. </p> <p> Note that if the NodeManager does not respond back within the timeout period, the machine is considered 'dead'. One retry attempt is provided by the server if the NodeManager timesout. On timeout, the server will wait for a FenceTimeout period and retry connecting to the NodeManager. If that call times out as well, the machine is deemed unavailable and taken out of the cluster view. </p> <p> A value of 0 means that the timeout will not be applied at all. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(180000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PeriodicSRMCheckEnabled")) {
         getterName = "isPeriodicSRMCheckEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPeriodicSRMCheckEnabled";
         }

         currentResult = new PropertyDescriptor("PeriodicSRMCheckEnabled", DatabaseLessLeasingBasisMBean.class, getterName, setterName);
         descriptors.put("PeriodicSRMCheckEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the cluster leader needs to periodically check if it is still in the right network partition using NodeManager state query. By default the cluster leader or the seniormost member ensures that it is in the right partition by periodically checking with all NodeManagers. This is used to cover a case where the cluster leader was elected with most of the servers shutdown but subsequently there is a network partition with the leader in the minority partition. This is just done on the cluster leader instance and not on other instances.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
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
