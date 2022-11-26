package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class TopicBeanImplBeanInfo extends DestinationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TopicBean.class;

   public TopicBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TopicBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.TopicBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Topics are used for asynchronous peer communications.  A message delivered to a topic will be distributed to all topic consumers.  Several aspects of a topics behavior can be configured with a topic bean.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.TopicBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ForwardingPolicy")) {
         getterName = "getForwardingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForwardingPolicy";
         }

         currentResult = new PropertyDescriptor("ForwardingPolicy", TopicBean.class, getterName, setterName);
         descriptors.put("ForwardingPolicy", currentResult);
         currentResult.setValue("description", "<p> The uniform distributed topic message Forwarding Policy specifies whether a sent message is forwarded to all members. </p>  <p>The valid values are:</p> <ul> <li><b>Replicated</b> - The default. All physical topic members receive each sent message. If a message arrives at one of the physical topic members, a copy of this message is forwarded to the other members of that uniform distributed topic. A subscription on any one particular member will get a copy of any message sent to the uniform distributed topic logical name or to any particular uniform distributed topic member.</li>  <li><b>Partitioned</b> - The physical member receiving the message is the only member of the uniform distributed topic that is aware of the message. When a message is published to the logical name of a Partitioned uniform distributed topic, it will only arrive on one particular physical topic member. Once a message arrives on a physical topic member, the message is not forwarded to the rest of the members of the uniform distributed destination, and subscribers on other physical topic members do not get a copy of that message. The Partitioned capability was added in WebLogic 10.3.4 (11gR1PS3).</li> </ul> <p>A publisher that uses a logical JNDI name of a Replicated distributed topic is created on one member and every send call  publishes messages to the same member regardless the value of the <code>Load Balancing Enabled</code> attribute. This is behavior is backward compatible with previous WebLogic Server releases when using a uniform distributed topic. Under the same conditions, a Partitioned distributed topic publishes to the same member only when the value of the <code>Load Balancing Enabled</code> attribute is set to false. If the value of the <code>Load Balancing Enabled</code> attribute is to true, a publisher to a Partitioned distributed topic publishes messages that are load balanced accross all the members of the uniform distributed topic. </p>  <p>Most new applications will use the new Partitioned forwarding policy in combination with a logical subscription topology on a uniform distributed topic that consists of: (1) a same named physical subscription created directly on each physical member, (2) a Client ID Policy of Unrestricted, and (3) a Subscription Sharing Policy of <code>Sharable</code>. WL 10.3.4 Message Driven Beans (MDBs) provides a Topic Messages Distribution Mode option to automatically setup this kind of topology.</p>  <p> Note: This attribute is ignored by standalone/singleton Topics, it only applies to distributed topics. </p> ");
         setPropertyDescriptorDefault(currentResult, JMSConstants.FORWARDING_POLICY_REPLICATED);
         currentResult.setValue("legalValues", new Object[]{JMSConstants.FORWARDING_POLICY_PARTITIONED, JMSConstants.FORWARDING_POLICY_REPLICATED});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Multicast")) {
         getterName = "getMulticast";
         setterName = null;
         currentResult = new PropertyDescriptor("Multicast", TopicBean.class, getterName, setterName);
         descriptors.put("Multicast", currentResult);
         currentResult.setValue("description", "<p>Gets the multicast parameters for this destination.</p>  <p> Topics with certain quality of service allowments can receive a signifigant performance boost by using multicast to receive messages rather than using a connection oriented protocol like TCP.  These parameters can be configured with the bean returned. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TopicSubscriptionParams")) {
         getterName = "getTopicSubscriptionParams";
         setterName = null;
         currentResult = new PropertyDescriptor("TopicSubscriptionParams", TopicBean.class, getterName, setterName);
         descriptors.put("TopicSubscriptionParams", currentResult);
         currentResult.setValue("description", "<p>Gets the subscription parameters for this topic.</p>  <p><code>TopicSubscriptionParamsBean</code> specifies topic subscription parameters.</p> ");
         currentResult.setValue("relationship", "containment");
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
