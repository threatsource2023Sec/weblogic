package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class InterceptorsMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = InterceptorsMBean.class;

   public InterceptorsMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public InterceptorsMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.InterceptorsMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.InterceptorsMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Interceptors")) {
         getterName = "getInterceptors";
         setterName = null;
         currentResult = new PropertyDescriptor("Interceptors", InterceptorsMBean.class, getterName, setterName);
         descriptors.put("Interceptors", currentResult);
         currentResult.setValue("description", "Get the InterceptorMBeans for this domain ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator.DatasourceInterceptorMBean", "createDatasourceInterceptor");
         currentResult.setValue("creator.ScriptInterceptorMBean", "createScriptInterceptor");
         currentResult.setValue("creator", "createInterceptor");
         currentResult.setValue("destroyer", "destroyInterceptor");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WhiteListingEnabled")) {
         getterName = "isWhiteListingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWhiteListingEnabled";
         }

         currentResult = new PropertyDescriptor("WhiteListingEnabled", InterceptorsMBean.class, getterName, setterName);
         descriptors.put("WhiteListingEnabled", currentResult);
         currentResult.setValue("description", "Returns true if white listing feature is enabled. When white listing is enabled, only those Interceptors returned by getInterceptors() are enabled. If white listing is disabled, then the values returned by getInterceptor() overrides the values specified through annotations. For example the rank specified by an InterceptorMethodMBean overrides the rank specified using the ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = InterceptorsMBean.class.getMethod("createInterceptor", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get the named InterceptorMBean for this domain ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Interceptors");
      }

      mth = InterceptorsMBean.class.getMethod("createScriptInterceptor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get the named ScriptInterceptorMBean for this domain ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Interceptors");
      }

      mth = InterceptorsMBean.class.getMethod("createDatasourceInterceptor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get the named DatasourceInterceptorMBean for this domain ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Interceptors");
      }

      mth = InterceptorsMBean.class.getMethod("destroyInterceptor", InterceptorMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("interceptor", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get the named InterceptorMBean for this domain ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Interceptors");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = InterceptorsMBean.class.getMethod("lookupInterceptor", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get the named InterceptorMBean for this domain ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Interceptors");
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
