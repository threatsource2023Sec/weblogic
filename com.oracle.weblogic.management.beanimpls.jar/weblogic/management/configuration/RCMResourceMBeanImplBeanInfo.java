package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class RCMResourceMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = RCMResourceMBean.class;

   public RCMResourceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RCMResourceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.RCMResourceMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p> This mbean is used to create Trigger (Usage limit) based policies for a resource type. </p> <p> Trigger (Usage limit) based policies allows a system administrator to establish usage limits, and define recourse actions that must be executed when those usage limits are breached. </p> <p> A trigger is a static upper-limit of usage of a resource. When the consumption of that resource crosses the specified limit, the specified recourse action is performed. This policy type is best suited for environments where the resource usage by Domain Partitions are predictable. </p> <p> As an example, a system administrator may limit the \"Bayland\" Partition to not use more than 100 open files, by setting a Trigger for 100 units of the \"File Open\" resource type in the resource-manager for the \"Bayland\" Partition. </p> <p> <b>Recourse Actions</b> </p> <p> The following recourse actions may be configured when a resource's trigger value is breached: </p> <ul> <li><b>notify</b>: A Notification is provided to system administrators as an informational update of the trigger being breached.</li> <li><b>slow</b>: Throttle (typically slow-down) the rate at which the resource is consumed.</li> <li><b>fail</b>: Fail one or more resource consumption requests, usually until usage is below the desired limit.</li> <li><b>shutdown</b>: Attempts to stop resource consumption by initiating the shutdown sequence of the Domain Partition while allowing cleanup.</li> </ul> <p> The following requirements are validated during trigger creation: </p> <ol> <li>The set of recourse actions listed above are not valid for all resource types. The resource type MBean's class document lists the subset of valid recourse actions that may be specified for that resource type. The specified recouse action for a resource type must be within that valid subset of recourse actions.</li> <li>Atmost one <i>shutdown</i> recourse action may be specified for a resource type.</li> <li>Atmost one <i>slow</i> recourse action may be specified for a resource type.</li> <li>Atmost one <i>fail</i> recourse action may be specified for a resource type.</li> <li>A <i>slow</i> recourse action cannot be specified for a resource type if a fair share policy is also defined for that resource type.</li> </ol> <p> Trigger creation is failed if any of these requirements fail. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.RCMResourceMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("Triggers")) {
         String getterName = "getTriggers";
         String setterName = null;
         currentResult = new PropertyDescriptor("Triggers", RCMResourceMBean.class, getterName, (String)setterName);
         descriptors.put("Triggers", currentResult);
         currentResult.setValue("description", "Gets the list of configured trigger based policies for the current resource type. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyTrigger");
         currentResult.setValue("creator", "createTrigger");
         currentResult.setValue("creator", "createTrigger");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = RCMResourceMBean.class.getMethod("createTrigger", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the trigger configuration ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for trigger configurations for this resource type. <p> The new {@code TriggerMBean} that is created will have the resource type MBean as its parent and must be destroyed with the {@link #destroyTrigger(TriggerMBean)} method. </p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "Triggers");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
         }
      }

      mth = RCMResourceMBean.class.getMethod("createTrigger", String.class, Long.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the trigger configuration "), createParameterDescriptor("value", "The upper-limit of usage of the resource, beyond which the specified action must be taken. "), createParameterDescriptor("action", "The recourse action to be taken when the usage of resoure crosses the value specified in the value parameter. See the class documentation of RCMResourceMBean for a set of allowed recourse actions types. Refer the Resource type MBean to obtain the subset of valid recourse action types for the Resource type. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This is the factory method for trigger configurations for this resource type. <p> The new {@code TriggerMBean} that is created will have the resource type MBean as its parent and must be destroyed with the {@link #destroyTrigger(TriggerMBean)} method. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Triggers");
      }

      mth = RCMResourceMBean.class.getMethod("destroyTrigger", TriggerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("triggerMBean", "The TriggerMBean to be removed from this resource type MBean. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a trigger configuration corresponding to the {code triggerMBean} parameter, which is a child of this resource type MBean. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Triggers");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = RCMResourceMBean.class.getMethod("lookupTrigger", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the trigger configuration to lookup. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up a trigger configuration from the list of triggers configured in this resource type. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Triggers");
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
