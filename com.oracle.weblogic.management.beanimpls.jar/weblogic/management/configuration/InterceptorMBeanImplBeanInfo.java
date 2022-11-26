package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class InterceptorMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = InterceptorMBean.class;

   public InterceptorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public InterceptorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.InterceptorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.InterceptorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DependsOn")) {
         getterName = "getDependsOn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDependsOn";
         }

         currentResult = new PropertyDescriptor("DependsOn", InterceptorMBean.class, getterName, setterName);
         descriptors.put("DependsOn", currentResult);
         currentResult.setValue("description", "<p>The set of interceptor names this interceptor depends on. The specified interceptors will appear before this interceptor in the invocation chain.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterceptedOperationNames")) {
         getterName = "getInterceptedOperationNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterceptedOperationNames";
         }

         currentResult = new PropertyDescriptor("InterceptedOperationNames", InterceptorMBean.class, getterName, setterName);
         descriptors.put("InterceptedOperationNames", currentResult);
         currentResult.setValue("description", "<p>The name of the operation / method that needs to intercepted by this interceptor's method. If the return value is null or \"*\" then all operations are intercepted by this interceptor.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterceptedTargetKey")) {
         getterName = "getInterceptedTargetKey";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterceptedTargetKey";
         }

         currentResult = new PropertyDescriptor("InterceptedTargetKey", InterceptorMBean.class, getterName, setterName);
         descriptors.put("InterceptedTargetKey", currentResult);
         currentResult.setValue("description", "<p>The key associated with the intercepted target. This is the value that would have been specified in the @InterceptedTargetKey annotation. If this is null then this Interceptor is ignored.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterceptorTypeName")) {
         getterName = "getInterceptorTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterceptorTypeName";
         }

         currentResult = new PropertyDescriptor("InterceptorTypeName", InterceptorMBean.class, getterName, setterName);
         descriptors.put("InterceptorTypeName", currentResult);
         currentResult.setValue("description", "<p>The HK2 name of the interceptor class.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Priority")) {
         getterName = "getPriority";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPriority";
         }

         currentResult = new PropertyDescriptor("Priority", InterceptorMBean.class, getterName, setterName);
         descriptors.put("Priority", currentResult);
         currentResult.setValue("description", "<p>The priority of this intercepted method in the interceptor chain. An interceptor with a higher priority will be executed earlier than an interceptor with a lower priority.</p> <p>Note:  1073741823 is actually Integer.MAX_VALUE / 2 as defined in InterceptorPriorities.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1073741823));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProperties";
         }

         currentResult = new PropertyDescriptor("Properties", InterceptorMBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "<p> A set of &lt;name, value&gt; pairs that needs to be passed to the interceptor.</p> ");
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
