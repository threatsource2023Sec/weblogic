package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DeploymentValidationPluginMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeploymentValidationPluginMBean.class;

   public DeploymentValidationPluginMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeploymentValidationPluginMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.DeploymentValidationPluginMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Specifies the deployment validation plug-in configuration attributes. The plug-in factory must have a no-argument constructor and must implement this interface: weblogic.deployment.configuration.DeploymentValidationPluginFactory ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DeploymentValidationPluginMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("FactoryClassname")) {
         getterName = "getFactoryClassname";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFactoryClassname";
         }

         currentResult = new PropertyDescriptor("FactoryClassname", DeploymentValidationPluginMBean.class, getterName, setterName);
         descriptors.put("FactoryClassname", currentResult);
         currentResult.setValue("description", "<p>Gets the classname of the plug-in factory used for validation. This is a fully qualified name. The class must be in the Administration Server classpath. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Parameters")) {
         getterName = "getParameters";
         setterName = null;
         currentResult = new PropertyDescriptor("Parameters", DeploymentValidationPluginMBean.class, getterName, setterName);
         descriptors.put("Parameters", currentResult);
         currentResult.setValue("description", "<p>Gets the parameters used to configure the plug-in factory used for validation. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyParameter");
         currentResult.setValue("creator", "createParameter");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DeploymentValidationPluginMBean.class.getMethod("destroyParameter", ParameterMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("mBean", "The parameter to remove ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a parameter used to configure the plug-in factory used for validation. <p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Parameters");
      }

      mth = DeploymentValidationPluginMBean.class.getMethod("createParameter", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the parameter ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a parameter used to configure the plug-in factory used for validation. <p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Parameters");
      }

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
