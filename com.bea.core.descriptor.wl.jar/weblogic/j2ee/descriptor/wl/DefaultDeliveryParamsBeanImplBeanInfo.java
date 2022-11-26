package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DefaultDeliveryParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DefaultDeliveryParamsBean.class;

   public DefaultDeliveryParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DefaultDeliveryParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>If a client does not specify certain parameters then the values that those parameters will take can be controlled with a default delivery parameters bean.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DefaultCompressionThreshold")) {
         getterName = "getDefaultCompressionThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultCompressionThreshold";
         }

         currentResult = new PropertyDescriptor("DefaultCompressionThreshold", DefaultDeliveryParamsBean.class, getterName, setterName);
         descriptors.put("DefaultCompressionThreshold", currentResult);
         currentResult.setValue("description", "<p>The number of bytes for the serialized message body so any message exceeds this limit will trigger message compression when the message is sent or received by the JMS message producer or consumer.</p>  <p>The compression will occur either:</p> <ul> <li> On the JMS message producer's JVM if the JMS message producer's JVM is not collocated with the JMS provider's JVM and the message body size exceeds the threshold limit. </li> <li> On the JMS provider's JVM when the JMS message consumer's JVM is not collocated with the JMS provider's JVM and the message body size exceeds the threshold limit. </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultDeliveryMode")) {
         getterName = "getDefaultDeliveryMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultDeliveryMode";
         }

         currentResult = new PropertyDescriptor("DefaultDeliveryMode", DefaultDeliveryParamsBean.class, getterName, setterName);
         descriptors.put("DefaultDeliveryMode", currentResult);
         currentResult.setValue("description", "<p>The default delivery mode used for messages when a delivery mode is not explicitly defined. </p>  <p>All messages with a DefaultDeliveryMode of <code>null</code> that are produced on a connection created with this factory will receive this value. Message producers can get the delivery mode explicitly by calling the <code> javax.jms.MessageProducer.getDeliveryMode()</code> method.</p>  <p>This attribute is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, "Persistent");
         currentResult.setValue("legalValues", new Object[]{"Persistent", "Non-Persistent"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultPriority")) {
         getterName = "getDefaultPriority";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultPriority";
         }

         currentResult = new PropertyDescriptor("DefaultPriority", DefaultDeliveryParamsBean.class, getterName, setterName);
         descriptors.put("DefaultPriority", currentResult);
         currentResult.setValue("description", "<p>The default priority used for messages when a priority is not explicitly defined.</p>  <p>All messages with a DefaultPriority of -1 that are produced on a connection created with this factory will receive this value. Message producers can get the priority explicitly by calling the <code>javax.jms.MessageProducer.getPriority()</code> method.</p>  <p>This attribute is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(4));
         currentResult.setValue("legalMax", new Integer(9));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultRedeliveryDelay")) {
         getterName = "getDefaultRedeliveryDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRedeliveryDelay";
         }

         currentResult = new PropertyDescriptor("DefaultRedeliveryDelay", DefaultDeliveryParamsBean.class, getterName, setterName);
         descriptors.put("DefaultRedeliveryDelay", currentResult);
         currentResult.setValue("description", "<p>The delay time, in milliseconds, before rolled back or recovered messages are redelivered.</p>  <p>All messages consumed by a consumer created with this factory that have a DefaultRedeliveryDelay of -1 will use this value.</p>  <p>Message consumers can get the redelivery delay explicitly by calling the <code> weblogic.jms.extensions.WLSession.getRedliveryDelay()</code> method. </p>  <p>This attribute is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTimeToDeliver")) {
         getterName = "getDefaultTimeToDeliver";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTimeToDeliver";
         }

         currentResult = new PropertyDescriptor("DefaultTimeToDeliver", DefaultDeliveryParamsBean.class, getterName, setterName);
         descriptors.put("DefaultTimeToDeliver", currentResult);
         currentResult.setValue("description", "<p>The delay time, in milliseconds, between when a message is produced and when it is made visible on its destination.</p>  <p>All messages produced by a producer created with this factory that have a DefaultTimeToDeliver of -1 will use this value. Message producers can get the Time-to-Deliver explicitly by calling the <code> weblogic.jms.extensions.WLMessageProducer.getTimeToDeliver()</code> method.</p>  <p>This attribute is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, "0");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTimeToLive")) {
         getterName = "getDefaultTimeToLive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTimeToLive";
         }

         currentResult = new PropertyDescriptor("DefaultTimeToLive", DefaultDeliveryParamsBean.class, getterName, setterName);
         descriptors.put("DefaultTimeToLive", currentResult);
         currentResult.setValue("description", "<p>The maximum length of time, in milliseconds, that a message exists. This value is used for messages when both the <code>time-to-live</code> is not explicitly set by the sender application that is using this connection factory and the <code>time-to-live</code> is not explicitly overridden by the destination's <code>TimeToLiveOverride</code> attribute . A value of 0 indicates that the message has an infinite amount time to live. </p>  <p>Message producer applications can set the <code>time-to-live</code> explicitly by calling the standard JMS <code>javax.jms.MessageProducer.setTimeToLive(long)</code> method. Note that programmatically setting <code>time-to-live</code> using <code>javax.jms.Message.setJMSExpiration()</code> has no effect - the message setter is ignored by  the JMS send call as required by the JMS specification. </p>  <p>This attribute is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultUnitOfOrder")) {
         getterName = "getDefaultUnitOfOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultUnitOfOrder";
         }

         currentResult = new PropertyDescriptor("DefaultUnitOfOrder", DefaultDeliveryParamsBean.class, getterName, setterName);
         descriptors.put("DefaultUnitOfOrder", currentResult);
         currentResult.setValue("description", "<p>The default Unit-of-Order name for producers that connect using this connection factory. A Unit-of-Order allows for messages to be processed in a certain order, even among multiple recipients.</p>  <p><code>System-generated</code> indicates that WebLogic Server will automatically generate a Unit-of-Order name. <code>User-Generated</code> indicates that the Unit-of-Order name will come from the name specified name in the <code>Unit-of-Order Name</code> field. If <code>None</code> is selected, no message ordering is enforced.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SendTimeout")) {
         getterName = "getSendTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSendTimeout";
         }

         currentResult = new PropertyDescriptor("SendTimeout", DefaultDeliveryParamsBean.class, getterName, setterName);
         descriptors.put("SendTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum length of time, in milliseconds, that a sender will wait when there isn't enough available space (no quota) on a destination to accommodate the message being sent.</p>  <p>The default time is 10 milliseconds. A value of 0 indicates that the sender does not want to wait for space. </p>  <p>This attribute is dynamic. It can be changed at any time. However, changing the value does not affect existing connections or their producers. It only affects new connections made with this connection factory. Producers inherit the setting from the connection factory used to create their session and connection. The value can then be overridden at run time by setting the value on the producer. </p> ");
         setPropertyDescriptorDefault(currentResult, new Long(10L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
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
