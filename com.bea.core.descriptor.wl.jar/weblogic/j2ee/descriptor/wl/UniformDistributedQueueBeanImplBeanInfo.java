package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class UniformDistributedQueueBeanImplBeanInfo extends UniformDistributedDestinationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UniformDistributedQueueBean.class;

   public UniformDistributedQueueBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UniformDistributedQueueBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.UniformDistributedQueueBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>A uniform distributed queue is a distributed queue whose members are configured as part of its configuration; since members share the same configuration, they are uniform.  There is one member on each JMS Server that conforms to the targeting criteria of the uniform distributed queue.  For example, if a uniform distributed queue is targeted to a cluster, then one member will be created on each JMS server in the cluster. Members are created and destroyed as the targeting is changed.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean");
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

         currentResult = new PropertyDescriptor("ForwardDelay", UniformDistributedQueueBean.class, getterName, setterName);
         descriptors.put("ForwardDelay", currentResult);
         currentResult.setValue("description", "<p>The number of seconds after which a uniform distributed queue member with no consumers will wait before forwarding its messages to other uniform distributed queue members that do have consumers.</p>  <p>The default value of -1 disables this feature so that no messages are forwarded to other uniform distributed queue members.</p>  <p> Note: This attribute is ignored by standalone/singleton Queues, it only applies to distributed queues. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("secureValue", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ResetDeliveryCountOnForward")) {
         getterName = "getResetDeliveryCountOnForward";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResetDeliveryCountOnForward";
         }

         currentResult = new PropertyDescriptor("ResetDeliveryCountOnForward", UniformDistributedQueueBean.class, getterName, setterName);
         descriptors.put("ResetDeliveryCountOnForward", currentResult);
         currentResult.setValue("description", "<p>Determines whether or not the delivery count is reset during message forwarding between distributed queue members.</p>  <p>The default value of true resets the delivery counts on messages when they are forwarded to another distributed queue member.</p>  <p> Note: This attribute is ignored by standalone/singleton Queues, it only applies to distributed queues. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
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
