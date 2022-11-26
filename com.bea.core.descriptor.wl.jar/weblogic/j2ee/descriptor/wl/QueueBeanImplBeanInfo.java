package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class QueueBeanImplBeanInfo extends DestinationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = QueueBean.class;

   public QueueBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public QueueBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.QueueBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Queues are used for asynchronous peer communications.  A message delivered to a queue will be distributed to one consumer.  Several aspects of a queues behavior can be configured with a queue bean.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.QueueBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ForwardDelay")) {
         getterName = "getForwardDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForwardDelay";
         }

         currentResult = new PropertyDescriptor("ForwardDelay", QueueBean.class, getterName, setterName);
         descriptors.put("ForwardDelay", currentResult);
         currentResult.setValue("description", "<p>The number of seconds after which a uniform distributed queue member with no consumers will wait before forwarding its messages to other uniform distributed queue members that do have consumers.</p>  <p>The default value of -1 disables this feature so that no messages are forwarded to other uniform distributed queue members.</p>  <p> Note: This attribute is ignored by standalone/singleton Queues, it only applies to distributed queues. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("secureValue", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResetDeliveryCountOnForward")) {
         getterName = "getResetDeliveryCountOnForward";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResetDeliveryCountOnForward";
         }

         currentResult = new PropertyDescriptor("ResetDeliveryCountOnForward", QueueBean.class, getterName, setterName);
         descriptors.put("ResetDeliveryCountOnForward", currentResult);
         currentResult.setValue("description", "<p>Determines whether or not the delivery count is reset during message forwarding between distributed queue members.</p>  <p>The default value of true resets the delivery counts on messages when they are forwarded to another distributed queue member.</p>  <p> Note: This attribute is ignored by standalone/singleton Queues, it only applies to distributed queues. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
