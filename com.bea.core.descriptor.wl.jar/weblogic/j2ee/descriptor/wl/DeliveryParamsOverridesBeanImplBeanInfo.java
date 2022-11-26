package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DeliveryParamsOverridesBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeliveryParamsOverridesBean.class;

   public DeliveryParamsOverridesBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeliveryParamsOverridesBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Many delivery parameters can be set by the JMS client.  These overrides, if set, will cause those specific parameters to be ignored and replaced by the value set here.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DeliveryMode")) {
         getterName = "getDeliveryMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeliveryMode";
         }

         currentResult = new PropertyDescriptor("DeliveryMode", DeliveryParamsOverridesBean.class, getterName, setterName);
         descriptors.put("DeliveryMode", currentResult);
         currentResult.setValue("description", "<p>The delivery mode assigned to all messages that arrive at the destination regardless of the DeliveryMode specified by the message producer.</p>  <p>A value of <code>No-Delivery</code> specifies that the DeliveryMode will not be overridden.</p>  <p><b>Note:</b> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "No-Delivery");
         currentResult.setValue("legalValues", new Object[]{"Persistent", "Non-Persistent", "No-Delivery"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Priority")) {
         getterName = "getPriority";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPriority";
         }

         currentResult = new PropertyDescriptor("Priority", DeliveryParamsOverridesBean.class, getterName, setterName);
         descriptors.put("Priority", currentResult);
         currentResult.setValue("description", "<p>The priority assigned to all messages that arrive at this destination, regardless of the Priority specified by the message producer. The default value (-1) specifies that the destination will not override the Priority set by the message producer.</p>  <p><b>Note:</b> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(9));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RedeliveryDelay")) {
         getterName = "getRedeliveryDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedeliveryDelay";
         }

         currentResult = new PropertyDescriptor("RedeliveryDelay", DeliveryParamsOverridesBean.class, getterName, setterName);
         descriptors.put("RedeliveryDelay", currentResult);
         currentResult.setValue("description", "<p>The delay, in milliseconds, before rolled back or recovered messages are redelivered, regardless of the RedeliveryDelay specified by the consumer and/or connection factory. Redelivered queue messages are put back into their originating destination; redelivered topic messages are put back into their originating subscription. The default value (-1) specifies that the destination will not override the RedeliveryDelay setting specified by the consumer and/or connection factory.</p>  <p><b>Note:</b> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p>  <p><b>Note:</b> Changing the RedeliveryDelay override only affects future rollbacks and recovers, it does not affect rollbacks and recovers that have already occurred.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TemplateBean")) {
         getterName = "getTemplateBean";
         setterName = null;
         currentResult = new PropertyDescriptor("TemplateBean", DeliveryParamsOverridesBean.class, getterName, setterName);
         descriptors.put("TemplateBean", currentResult);
         currentResult.setValue("description", "<p>This is used to find the template bean for this destination</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeToDeliver")) {
         getterName = "getTimeToDeliver";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeToDeliver";
         }

         currentResult = new PropertyDescriptor("TimeToDeliver", DeliveryParamsOverridesBean.class, getterName, setterName);
         descriptors.put("TimeToDeliver", currentResult);
         currentResult.setValue("description", "<p>The default delay, either in milliseconds or as a schedule, between when a message is produced and when it is made visible on its target destination, regardless of the delivery time specified by the producer and/or connection factory. The default value (<code>-1</code>) specifies that the destination will not override the TimeToDeliver setting specified by the producer and/or connection factory. The TimeToDeliver override can be specified either as a long or as a schedule.</p>  <p><b>Note:</b> Changing the TimeToDeliver override only affects future message delivery, it does not affect message delivery of already produced messages.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.jms.extensions.Schedule")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "-1");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeToLive")) {
         getterName = "getTimeToLive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeToLive";
         }

         currentResult = new PropertyDescriptor("TimeToLive", DeliveryParamsOverridesBean.class, getterName, setterName);
         descriptors.put("TimeToLive", currentResult);
         currentResult.setValue("description", "<p>The time-to-live assigned to all messages that arrive at this destination, regardless of the TimeToLive value specified by the message producer. The default value (<code>-1</code>) specifies that this setting will not override the TimeToLive setting specified by the message producer.</p>  <p>Range of Values: Between 0 and a positive 64-bit integer.</p>  <p><b>Note:</b> This attribute is dynamically configurable, but only incoming messages are impacted; stored messages are not impacted.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
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
