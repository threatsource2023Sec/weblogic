package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class MessageDrivenDescriptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = MessageDrivenDescriptorBean.class;

   public MessageDrivenDescriptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MessageDrivenDescriptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.MessageDrivenDescriptorBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.MessageDrivenDescriptorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionFactoryJNDIName")) {
         getterName = "getConnectionFactoryJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactoryJNDIName";
         }

         currentResult = new PropertyDescriptor("ConnectionFactoryJNDIName", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("ConnectionFactoryJNDIName", currentResult);
         currentResult.setValue("description", "Specifies the JNDI name of the JMS Connection Factory that a message-driven EJB looks up to create its queues and topics. ");
         setPropertyDescriptorDefault(currentResult, "weblogic.jms.MessageDrivenBeanConnectionFactory");
         currentResult.setValue("dependency", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactoryResourceLink")) {
         getterName = "getConnectionFactoryResourceLink";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactoryResourceLink";
         }

         currentResult = new PropertyDescriptor("ConnectionFactoryResourceLink", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("ConnectionFactoryResourceLink", currentResult);
         currentResult.setValue("description", "Maps to a resource within a JMS module defined in ejb-jar.xml to an actual JMS Module Reference in WebLogic Server. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationJNDIName")) {
         getterName = "getDestinationJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationJNDIName";
         }

         currentResult = new PropertyDescriptor("DestinationJNDIName", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("DestinationJNDIName", currentResult);
         currentResult.setValue("description", "Specifies the JNDI name used to associate a message-driven bean with an actual JMS Queue or Topic deployed in the WebLogic Server JNDI tree. ");
         currentResult.setValue("dependency", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationResourceLink")) {
         getterName = "getDestinationResourceLink";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationResourceLink";
         }

         currentResult = new PropertyDescriptor("DestinationResourceLink", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("DestinationResourceLink", currentResult);
         currentResult.setValue("description", "Maps to a resource within a JMS module defined in ejb-jar.xml to an actual JMS Module Reference in WebLogic Server. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DistributedDestinationConnection")) {
         getterName = "getDistributedDestinationConnection";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDistributedDestinationConnection";
         }

         currentResult = new PropertyDescriptor("DistributedDestinationConnection", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("DistributedDestinationConnection", currentResult);
         currentResult.setValue("description", "<p>Specifies whether an MDB that accesses a WebLogic JMS distributed queue in the same cluster consumes from all distributed destination members or only those members local to the current Weblogic Server.</p> <p>Valid values include: <ul><li><b>LocalOnly</b>Deployment descriptor and message-driven bean are in the same cluster.</li> <li><b>EveryMember</b>Deployment descriptor is on a remote server.</li> </ul></p> ");
         setPropertyDescriptorDefault(currentResult, "LocalOnly");
         currentResult.setValue("legalValues", new Object[]{"LocalOnly", "EveryMember"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "The bean Id. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitSuspendSeconds")) {
         getterName = "getInitSuspendSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitSuspendSeconds";
         }

         currentResult = new PropertyDescriptor("InitSuspendSeconds", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("InitSuspendSeconds", currentResult);
         currentResult.setValue("description", "Specifies the initial number of seconds to suspend an MDB's JMS connection when the EJB container detects a JMS resource outage. ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialContextFactory")) {
         getterName = "getInitialContextFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialContextFactory";
         }

         currentResult = new PropertyDescriptor("InitialContextFactory", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("InitialContextFactory", currentResult);
         currentResult.setValue("description", "Specifies the initial context factory used by the JMS provider to create initial context. ");
         setPropertyDescriptorDefault(currentResult, "weblogic.jndi.WLInitialContextFactory");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsClientId")) {
         getterName = "getJmsClientId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJmsClientId";
         }

         currentResult = new PropertyDescriptor("JmsClientId", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("JmsClientId", currentResult);
         currentResult.setValue("description", "<p>Specifies a client ID for the MDB when it connects to a JMS destination. Required for durable subscriptions to JMS topics.</p> <p>If you specify the connection factory that the MDB uses in connection-factory-jndi-name, the client ID can be defined in the ClientID element of the associated JMSConnectionFactory element in config.xml.</p> <p>If JMSConnectionFactory in config.xml does not specify a ClientID, or if you use the default connection factory, (you do not specify connection-factory-jndi-name) the message-driven bean uses the jms-client-id value as its client id. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsPollingIntervalSeconds")) {
         getterName = "getJmsPollingIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJmsPollingIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("JmsPollingIntervalSeconds", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("JmsPollingIntervalSeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the number of seconds between each attempt to reconnect to the JMS destination. </p> <p>Each message-driven bean listens on an associated JMS destination. If the JMS destination is located on another WebLogic Server instance or a foreign JMS provider, then the JMS destination may become unreachable. In this case, the EJB container automatically attempts to reconnect to the JMS Server. Once the JMS Server is up again, the message-driven bean can again receive messages.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxMessagesInTransaction")) {
         getterName = "getMaxMessagesInTransaction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxMessagesInTransaction";
         }

         currentResult = new PropertyDescriptor("MaxMessagesInTransaction", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("MaxMessagesInTransaction", currentResult);
         currentResult.setValue("description", "Specifies the maximum number of messages that can be in a transaction for this MDB. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxSuspendSeconds")) {
         getterName = "getMaxSuspendSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxSuspendSeconds";
         }

         currentResult = new PropertyDescriptor("MaxSuspendSeconds", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("MaxSuspendSeconds", currentResult);
         currentResult.setValue("description", "Specifies the maximum number of seconds to suspend an MDB's JMS connection when the EJB container detects a JMS resource outage. To disable JMS connection suspension when the EJB container detects a JMS resource outage, set the value of this element to 0. ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Pool")) {
         getterName = "getPool";
         setterName = null;
         currentResult = new PropertyDescriptor("Pool", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("Pool", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProviderUrl")) {
         getterName = "getProviderUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProviderUrl";
         }

         currentResult = new PropertyDescriptor("ProviderUrl", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("ProviderUrl", currentResult);
         currentResult.setValue("description", "Specifies the URL to be used by the InitialContext. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceAdapterJNDIName")) {
         getterName = "getResourceAdapterJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceAdapterJNDIName";
         }

         currentResult = new PropertyDescriptor("ResourceAdapterJNDIName", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("ResourceAdapterJNDIName", currentResult);
         currentResult.setValue("description", "Identifies the resource adapter that this MDB receives messages from. ");
         currentResult.setValue("dependency", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityPlugin")) {
         getterName = "getSecurityPlugin";
         setterName = null;
         currentResult = new PropertyDescriptor("SecurityPlugin", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("SecurityPlugin", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimerDescriptor")) {
         getterName = "getTimerDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("TimerDescriptor", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("TimerDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DurableSubscriptionDeletion")) {
         getterName = "isDurableSubscriptionDeletion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDurableSubscriptionDeletion";
         }

         currentResult = new PropertyDescriptor("DurableSubscriptionDeletion", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("DurableSubscriptionDeletion", currentResult);
         currentResult.setValue("description", "Indicates whether you want durable topic subscriptions to be automatically deleted when an MDB is undeployed or removed. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GenerateUniqueJmsClientId")) {
         getterName = "isGenerateUniqueJmsClientId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGenerateUniqueJmsClientId";
         }

         currentResult = new PropertyDescriptor("GenerateUniqueJmsClientId", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("GenerateUniqueJmsClientId", currentResult);
         currentResult.setValue("description", "Indicates whether or not you want the EJB container to generate a unique client-id for every instance of an MDB. Enabling this flag makes it easier to deploy durable MDBs to multiple server instances in a WebLogic Server cluster. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Use81StylePolling")) {
         getterName = "isUse81StylePolling";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUse81StylePolling";
         }

         currentResult = new PropertyDescriptor("Use81StylePolling", MessageDrivenDescriptorBean.class, getterName, setterName);
         descriptors.put("Use81StylePolling", currentResult);
         currentResult.setValue("description", "<p>Enables backwards compatibility for WebLogic Server Version 8.1-style polling.</p> <p>In WLS version 8.1 and earlier, transactional MDBs with batching enabled created a dedicated polling thread for each deployed MDB. This polling thread was not allocated from the pool specified by dispatch-policy, it was an entirely new thread in addition to the all other threads running on the system.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
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
