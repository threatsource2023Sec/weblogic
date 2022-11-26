package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ResourceDeploymentPlanBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ResourceDeploymentPlanBean.class;

   public ResourceDeploymentPlanBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ResourceDeploymentPlanBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML resource-deployment-planType(@http://xmlns.oracle.com/weblogic/resource-deployment-plan). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConfigResourceOverrides")) {
         getterName = "getConfigResourceOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigResourceOverrides", ResourceDeploymentPlanBean.class, getterName, setterName);
         descriptors.put("ConfigResourceOverrides", currentResult);
         currentResult.setValue("description", "Gets array of all \"config-resource-override\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createConfigResourceOverride");
         currentResult.setValue("destroyer", "destroyConfigResourceOverride");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", ResourceDeploymentPlanBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "Gets the \"description\" element ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExternalResourceOverrides")) {
         getterName = "getExternalResourceOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("ExternalResourceOverrides", ResourceDeploymentPlanBean.class, getterName, setterName);
         descriptors.put("ExternalResourceOverrides", currentResult);
         currentResult.setValue("description", "Gets array of all \"external-resource-override\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createExternalResourceOverride");
         currentResult.setValue("destroyer", "destroyExternalResourceOverride");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VariableDefinition")) {
         getterName = "getVariableDefinition";
         setterName = null;
         currentResult = new PropertyDescriptor("VariableDefinition", ResourceDeploymentPlanBean.class, getterName, setterName);
         descriptors.put("VariableDefinition", currentResult);
         currentResult.setValue("description", "Gets the \"variable-definition\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GlobalVariables")) {
         getterName = "isGlobalVariables";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGlobalVariables";
         }

         currentResult = new PropertyDescriptor("GlobalVariables", ResourceDeploymentPlanBean.class, getterName, setterName);
         descriptors.put("GlobalVariables", currentResult);
         currentResult.setValue("description", "Whether to use global variable naming. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceDeploymentPlanBean.class.getMethod("createExternalResourceOverride");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create new \"external-resource-override\" element ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExternalResourceOverrides");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("destroyExternalResourceOverride", ExternalResourceOverrideBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Remove \"external-resource-override\" element ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExternalResourceOverrides");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("createConfigResourceOverride");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create new \"config-resource-override\" element ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigResourceOverrides");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("destroyConfigResourceOverride", ConfigResourceOverrideBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Remove \"config-resource-override\" element ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigResourceOverrides");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceDeploymentPlanBean.class.getMethod("findConfigResourceOverride", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get config resource overrides for any module in plan. ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("findExternalResourceOverride", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get config/external resource overrides for any module in plan. ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("hasVariable", ConfigResourceOverrideBean.class, DescriptorBean.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("findVariable", ConfigResourceOverrideBean.class, DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("findVariableAssignments", VariableBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("var", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("findVariableAssignment", ConfigResourceOverrideBean.class, DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("findOrCreateVariable", ConfigResourceOverrideBean.class, DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("findOrCreateVariable", ConfigResourceOverrideBean.class, DescriptorBean.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null), createParameterDescriptor("planBased", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("findOrCreateVariable", ConfigResourceOverrideBean.class, DescriptorBean.class, String.class, Boolean.TYPE, Object.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null), createParameterDescriptor("planBased", (String)null), createParameterDescriptor("oldKeyValue", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("valueOf", VariableBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("var", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("assignVariable", VariableBean.class, ConfigResourceOverrideBean.class, DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("var", (String)null), createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create new variable assignment from existing variable. ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceDeploymentPlanBean.class.getMethod("isRemovable", DescriptorBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

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
