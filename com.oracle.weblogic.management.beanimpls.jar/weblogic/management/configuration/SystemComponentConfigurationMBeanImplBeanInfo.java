package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SystemComponentConfigurationMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SystemComponentConfigurationMBean.class;

   public SystemComponentConfigurationMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SystemComponentConfigurationMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SystemComponentConfigurationMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("A System Component Configuration MBean allows configuration to be shared between one or more System Components. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SystemComponentConfigurationMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ComponentType")) {
         getterName = "getComponentType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setComponentType";
         }

         currentResult = new PropertyDescriptor("ComponentType", SystemComponentConfigurationMBean.class, getterName, setterName);
         descriptors.put("ComponentType", currentResult);
         currentResult.setValue("description", "Get the system component type Examples of types may include \"OHS\", \"Coherence\". New System Components may be added in the future so the list of types is not static. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourcePath")) {
         getterName = "getSourcePath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourcePath";
         }

         currentResult = new PropertyDescriptor("SourcePath", SystemComponentConfigurationMBean.class, getterName, setterName);
         descriptors.put("SourcePath", currentResult);
         currentResult.setValue("description", "Get the source path for configuration files Note: if you use a SystemComponentConfiguration, then the files for the SystemComponents directory are not used so there is no chance of conflict. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SystemComponents")) {
         getterName = "getSystemComponents";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSystemComponents";
         }

         currentResult = new PropertyDescriptor("SystemComponents", SystemComponentConfigurationMBean.class, getterName, setterName);
         descriptors.put("SystemComponents", currentResult);
         currentResult.setValue("description", "System Component Configurations are targeted to System Component instances. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addSystemComponent");
         currentResult.setValue("remover", "removeSystemComponent");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SystemComponentConfigurationMBean.class.getMethod("addSystemComponent", SystemComponentMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The instance to be added to the System Component attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "You can add a system component to specify additional instances on which the system component configuration can be targeted. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SystemComponents");
      }

      mth = SystemComponentConfigurationMBean.class.getMethod("removeSystemComponent", SystemComponentMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The system component to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the value of the System Component attribute.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addSystemComponent")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SystemComponents");
      }

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
