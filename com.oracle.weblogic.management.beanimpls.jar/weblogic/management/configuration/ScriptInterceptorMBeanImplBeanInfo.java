package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class ScriptInterceptorMBeanImplBeanInfo extends InterceptorMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ScriptInterceptorMBean.class;

   public ScriptInterceptorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ScriptInterceptorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ScriptInterceptorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This interface provides configuration for a script interceptor. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ScriptInterceptorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ApplicableClusterNames")) {
         getterName = "getApplicableClusterNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicableClusterNames";
         }

         currentResult = new PropertyDescriptor("ApplicableClusterNames", ScriptInterceptorMBean.class, getterName, setterName);
         descriptors.put("ApplicableClusterNames", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterceptorTypeName")) {
         getterName = "getInterceptorTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterceptorTypeName";
         }

         currentResult = new PropertyDescriptor("InterceptorTypeName", ScriptInterceptorMBean.class, getterName, setterName);
         descriptors.put("InterceptorTypeName", currentResult);
         currentResult.setValue("description", "The HK2 name of the interceptor class. ");
         setPropertyDescriptorDefault(currentResult, "ScriptInterceptor");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostProcessor")) {
         getterName = "getPostProcessor";
         setterName = null;
         currentResult = new PropertyDescriptor("PostProcessor", ScriptInterceptorMBean.class, getterName, setterName);
         descriptors.put("PostProcessor", currentResult);
         currentResult.setValue("description", "Returns the configuration for the post-processor script which will be invoked after executing the intercepted operation. Note that if the command specified by the returned configuration is not set or empty, this interceptor will not perform any action after execution of the intercepted operation.  If the pre-processor script (if specified) failed, or the intercepted operation or another interceptor causes an exception, the post-processor script (even if specified) will not be executed.  If the post-processor script (if specified) returns a non-zero status, the error handler script (if specified) will be executed and a PostProcessingException will occur. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreProcessor")) {
         getterName = "getPreProcessor";
         setterName = null;
         currentResult = new PropertyDescriptor("PreProcessor", ScriptInterceptorMBean.class, getterName, setterName);
         descriptors.put("PreProcessor", currentResult);
         currentResult.setValue("description", "Returns the configuration for the pre-processor script which will be invoked before executing the interceptor operation. Note that if the command specified by the returned configuration is not set or empty, this interceptor will not perform any action before execution of the intercepted operation.  If the pre-processor script (if specified) returns a non-zero status, the error handler script (if specified) will be executed and a PreProcessingException will occur. ");
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
