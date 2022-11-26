package weblogic.management.mbeans.custom;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.configuration.PropertyValueVBean;

public class PropertyValueVBeanImplBeanInfo extends SimplePropertyValueVBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PropertyValueVBean.class;

   public PropertyValueVBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PropertyValueVBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeans.custom.PropertyValueVBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("valueObject", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.mbeans.custom");
      String description = (new String("Allows admin clients to discover whether and how a config bean's properties were overridden by various supported techniques. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.PropertyValueVBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DefaultValue")) {
         getterName = "getDefaultValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultValue";
         }

         currentResult = new PropertyDescriptor("DefaultValue", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("DefaultValue", currentResult);
         currentResult.setValue("description", "Returns the default value for this property. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (!descriptors.containsKey("EffectiveValue")) {
         getterName = "getEffectiveValue";
         setterName = null;
         currentResult = new PropertyDescriptor("EffectiveValue", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("EffectiveValue", currentResult);
         currentResult.setValue("description", "Returns the effective value of the property after all (if any) overrides have been applied. ");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (!descriptors.containsKey("EffectiveValueName")) {
         getterName = "getEffectiveValueName";
         setterName = null;
         currentResult = new PropertyDescriptor("EffectiveValueName", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("EffectiveValueName", currentResult);
         currentResult.setValue("description", "Reports which of the values is the effective value. ");
         setPropertyDescriptorDefault(currentResult, "DEFAULT");
         currentResult.setValue("legalValues", new Object[]{"DEFAULT", "ORIGINAL", "OVERRIDING_CONFIG_BEAN", "RESOURCE_DEPLOYMENT_PLAN"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OriginalValue")) {
         getterName = "getOriginalValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOriginalValue";
         }

         currentResult = new PropertyDescriptor("OriginalValue", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("OriginalValue", currentResult);
         currentResult.setValue("description", "Returns the original value of the property from the bean in the config tree corresponding to config.xml. The value returned will be from either an actual child of a ResourceGroupMBean or from the delegate in the resource group template (if the resource group points to a resource group template). ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (!descriptors.containsKey("OverridingConfigBeanValue")) {
         getterName = "getOverridingConfigBeanValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOverridingConfigBeanValue";
         }

         currentResult = new PropertyDescriptor("OverridingConfigBeanValue", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("OverridingConfigBeanValue", currentResult);
         currentResult.setValue("description", "Returns the value from the corresponding overriding config bean (if one applies to the config bean passed to the getPropertyValues method. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (!descriptors.containsKey("PropertyName")) {
         getterName = "getPropertyName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPropertyName";
         }

         currentResult = new PropertyDescriptor("PropertyName", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("PropertyName", currentResult);
         currentResult.setValue("description", "Returns the property name this PropertyValueVBean describes. ");
      }

      if (!descriptors.containsKey("ResourceDeploymentPlanValue")) {
         getterName = "getResourceDeploymentPlanValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceDeploymentPlanValue";
         }

         currentResult = new PropertyDescriptor("ResourceDeploymentPlanValue", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("ResourceDeploymentPlanValue", currentResult);
         currentResult.setValue("description", "Returns the value from the containing partition's resource deployment plan.  The value will be non-null if all three of these conditions are true: 1. the config bean is within a partition 2. the partition specifies a resource deployment plan 3. the resource deployment plan affects this property  Otherwise the method returns null. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (!descriptors.containsKey("OriginalValueAssigned")) {
         getterName = "isOriginalValueAssigned";
         setterName = null;
         currentResult = new PropertyDescriptor("OriginalValueAssigned", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("OriginalValueAssigned", currentResult);
         currentResult.setValue("description", "Returns whether the original value was actually set. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OverridingConfigBeanValueAssigned")) {
         getterName = "isOverridingConfigBeanValueAssigned";
         setterName = null;
         currentResult = new PropertyDescriptor("OverridingConfigBeanValueAssigned", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("OverridingConfigBeanValueAssigned", currentResult);
         currentResult.setValue("description", "Returns whether the overriding config bean value was actually set. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceDeploymentPlanValueAssigned")) {
         getterName = "isResourceDeploymentPlanValueAssigned";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceDeploymentPlanValueAssigned", PropertyValueVBean.class, getterName, setterName);
         descriptors.put("ResourceDeploymentPlanValueAssigned", currentResult);
         currentResult.setValue("description", " ");
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
