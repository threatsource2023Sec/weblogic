package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class RestartLoopProtectionMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = RestartLoopProtectionMBean.class;

   public RestartLoopProtectionMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RestartLoopProtectionMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.RestartLoopProtectionMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.1.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p> {@code RestartLoopProtectionMBean} defines the configuration to stop the partition from entering an endless restart loop. It is an optional configuration and when enabled it is a resource manager wide configuration. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.RestartLoopProtectionMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MaxRestartAllowed")) {
         getterName = "getMaxRestartAllowed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRestartAllowed";
         }

         currentResult = new PropertyDescriptor("MaxRestartAllowed", RestartLoopProtectionMBean.class, getterName, setterName);
         descriptors.put("MaxRestartAllowed", currentResult);
         currentResult.setValue("description", "Gets the value of max-restart-allowed. max-restart-allowed is defined as the maximum number of RCM initiated partition restarts allowed in the specified time interval(as specified by max-restart-allowed-interval), after which the partition will be halted upon an RCM initiated request to restart the partition. ");
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRestartAllowedInterval")) {
         getterName = "getMaxRestartAllowedInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRestartAllowedInterval";
         }

         currentResult = new PropertyDescriptor("MaxRestartAllowedInterval", RestartLoopProtectionMBean.class, getterName, setterName);
         descriptors.put("MaxRestartAllowedInterval", currentResult);
         currentResult.setValue("description", "Gets the value of max-restart-allowed-interval. The max-restart-allowed-interval is defined as a fixed-width, sliding-window time interval (in seconds) within which the specified number (as specified by max-restart-allowed) of RCM initiated partition restarts are permitted. A RCM initiated request to restart the partition that exceeds the given max-restart-allowed number in the given max-restart-allowed-interval will lead to the partition being halted than being restarted. ");
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestartDelay")) {
         getterName = "getRestartDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartDelay";
         }

         currentResult = new PropertyDescriptor("RestartDelay", RestartLoopProtectionMBean.class, getterName, setterName);
         descriptors.put("RestartDelay", currentResult);
         currentResult.setValue("description", "The restart delay. A restart delay introduces a delay before starting the partition, as part of the RCM initiated restart action. ");
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestartLoopProtectionEnabled")) {
         getterName = "isRestartLoopProtectionEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartLoopProtectionEnabled";
         }

         currentResult = new PropertyDescriptor("RestartLoopProtectionEnabled", RestartLoopProtectionMBean.class, getterName, setterName);
         descriptors.put("RestartLoopProtectionEnabled", currentResult);
         currentResult.setValue("description", "Checks if restart loop protection is enabled. ");
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
