package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DistributedQueueBeanImplBeanInfo extends DistributedDestinationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DistributedQueueBean.class;

   public DistributedQueueBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DistributedQueueBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DistributedQueueBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("A distributed queue is one that can be load-balanced across a cluster. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DistributedQueueBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DistributedQueueMembers")) {
         getterName = "getDistributedQueueMembers";
         setterName = null;
         currentResult = new PropertyDescriptor("DistributedQueueMembers", DistributedQueueBean.class, getterName, setterName);
         descriptors.put("DistributedQueueMembers", currentResult);
         currentResult.setValue("description", "<p>The list of all queue members that make up the aggregate distributed queue.</p> <p> The name of the specific queue and any configuration information about that queue (such as its relative weight) can be configured in the distributed queue member. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDistributedQueueMember");
         currentResult.setValue("destroyer", "destroyDistributedQueueMember");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForwardDelay")) {
         getterName = "getForwardDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForwardDelay";
         }

         currentResult = new PropertyDescriptor("ForwardDelay", DistributedQueueBean.class, getterName, setterName);
         descriptors.put("ForwardDelay", currentResult);
         currentResult.setValue("description", "<p>The number of seconds after which a distributed queue member with no consumers will wait before forwarding its messages to other distributed queue members that do have consumers.</p>  <p>The default value of -1 disables this feature so that no messages are forwarded to other distributed queue members.</p> ");
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

         currentResult = new PropertyDescriptor("ResetDeliveryCountOnForward", DistributedQueueBean.class, getterName, setterName);
         descriptors.put("ResetDeliveryCountOnForward", currentResult);
         currentResult.setValue("description", "<p>Determines whether or not the delivery count is reset during message forwarding between distributed queue members.</p>  <p>The default value of true resets the delivery counts on messages when they are forwarded to another distributed queue member.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DistributedQueueBean.class.getMethod("createDistributedQueueMember", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the distributed queue member bean to add to this distributed queue ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a distributed queue member and adds it to this distributed queue.</p> <p> The name of the specific queue and any configuration information about that queue (such as its relative weight) can be configured in the distributed queue member. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DistributedQueueMembers");
      }

      mth = DistributedQueueBean.class.getMethod("destroyDistributedQueueMember", DistributedDestinationMemberBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("distributedDestinationMember", "The particular distributed queue member to remove from this distributed queue ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a distributed queue member from this distributed queue.</p> <p> The name of the specific queue and any configuration information about that queue (such as its relative weight) can be configured in the distributed queue member. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DistributedQueueMembers");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DistributedQueueBean.class.getMethod("lookupDistributedQueueMember", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the distributed queue member to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Finds a distributed queue member bean with the given name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "DistributedQueueMembers");
      }

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
