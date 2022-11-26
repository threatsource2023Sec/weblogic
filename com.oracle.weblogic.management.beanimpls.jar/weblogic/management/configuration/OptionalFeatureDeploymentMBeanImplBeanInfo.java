package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class OptionalFeatureDeploymentMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = OptionalFeatureDeploymentMBean.class;

   public OptionalFeatureDeploymentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public OptionalFeatureDeploymentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.OptionalFeatureDeploymentMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Control panel for all optional features, e.g. WSAT, ASYNC_RESPONSE etc. The mbean is designed to be managed by domain and to extend for partition if needed in future. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.OptionalFeatureDeploymentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("OptionalFeatures")) {
         String getterName = "getOptionalFeatures";
         String setterName = null;
         currentResult = new PropertyDescriptor("OptionalFeatures", OptionalFeatureDeploymentMBean.class, getterName, (String)setterName);
         descriptors.put("OptionalFeatures", currentResult);
         currentResult.setValue("description", "<p>get all OptionalFeatureMBean in the current OptionalFeatureDeploymentMBean.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOptionalFeature");
         currentResult.setValue("creator", "createOptionalFeature");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = OptionalFeatureDeploymentMBean.class.getMethod("createOptionalFeature", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory for creating OptionalFeatureMBean.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OptionalFeatures");
      }

      mth = OptionalFeatureDeploymentMBean.class.getMethod("destroyOptionalFeature", OptionalFeatureMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("optionalFeatureMBean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>remove an OptionalFeatureMBean.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OptionalFeatures");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = OptionalFeatureDeploymentMBean.class.getMethod("lookupOptionalFeature", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>find an OptionalFeatureMBean by name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "OptionalFeatures");
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
