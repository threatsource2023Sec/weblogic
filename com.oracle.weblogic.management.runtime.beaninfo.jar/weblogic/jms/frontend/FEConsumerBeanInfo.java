package weblogic.jms.frontend;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.JMSConsumerRuntimeMBean;

public class FEConsumerBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSConsumerRuntimeMBean.class;

   public FEConsumerBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public FEConsumerBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.frontend.FEConsumer");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jms.frontend");
      String description = (new String("This class is used for monitoring a WebLogic JMS consumer. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSConsumerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("BytesPendingCount")) {
         getterName = "getBytesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPendingCount", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes pending (uncommitted and unacknowledged) by this consumer.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesReceivedCount")) {
         getterName = "getBytesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesReceivedCount", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes received by this consumer since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientID")) {
         getterName = "getClientID";
         setterName = null;
         currentResult = new PropertyDescriptor("ClientID", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClientID", currentResult);
         currentResult.setValue("description", "<p>The client ID for this connection.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientIDPolicy")) {
         getterName = "getClientIDPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("ClientIDPolicy", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClientIDPolicy", currentResult);
         currentResult.setValue("description", "<p>The ClientIDPolicy on this connection or durable subscriber.</p> Valid values are: <ul><li> <code>weblogic.management.configuration.JMSConstants.CLIENT_ID_POLICY_RESTRICTED</code>: Only one connection that uses this policy exists in a cluster at any given time for a particular <code>ClientID</code>.</li> <li><code>weblogic.management.configuration.JMSConstants.CLIENT_ID_POLICY_UNRESTRICTED</code>:  Connections created using this policy can specify any <code>ClientID</code>, even when other restricted or unrestricted connections already use the same <code>ClientID</code>.</li> </ul> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationName")) {
         getterName = "getDestinationName";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationName", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationName", currentResult);
         currentResult.setValue("description", "<p>The name of the destination for this consumer. In case of a distributed destination, it is the name of the distributed destination, instead of the member destination.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MemberDestinationName")) {
         getterName = "getMemberDestinationName";
         setterName = null;
         currentResult = new PropertyDescriptor("MemberDestinationName", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MemberDestinationName", currentResult);
         currentResult.setValue("description", "<p>The name of the destination for this consumer. In case of a distributed destination, it is the name of the member destination.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesPendingCount")) {
         getterName = "getMessagesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPendingCount", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages pending (uncommitted and unacknowledged) by this consumer.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesReceivedCount")) {
         getterName = "getMessagesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesReceivedCount", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages received by this consumer since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Selector")) {
         getterName = "getSelector";
         setterName = null;
         currentResult = new PropertyDescriptor("Selector", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Selector", currentResult);
         currentResult.setValue("description", "<p>The selector associated with this consumer, if any.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscriptionSharingPolicy")) {
         getterName = "getSubscriptionSharingPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("SubscriptionSharingPolicy", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubscriptionSharingPolicy", currentResult);
         currentResult.setValue("description", "<p>The Subscription Sharing Policy on this subscriber.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Active")) {
         getterName = "isActive";
         setterName = null;
         currentResult = new PropertyDescriptor("Active", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Active", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the consumer active. A consumer is active if it has a message listener set up or a synchronous receive in progress.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Durable")) {
         getterName = "isDurable";
         setterName = null;
         currentResult = new PropertyDescriptor("Durable", JMSConsumerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Durable", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the consumer is durable.</p> ");
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
