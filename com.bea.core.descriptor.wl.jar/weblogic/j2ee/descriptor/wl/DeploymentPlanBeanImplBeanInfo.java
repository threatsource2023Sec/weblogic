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

public class DeploymentPlanBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = DeploymentPlanBean.class;

   public DeploymentPlanBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeploymentPlanBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DeploymentPlanBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML deployment-planType(@http://xmlns.oracle.com/weblogic/deployment-plan). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DeploymentPlanBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationName";
         }

         currentResult = new PropertyDescriptor("ApplicationName", DeploymentPlanBean.class, getterName, setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "Gets the \"application-name\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigRoot")) {
         getterName = "getConfigRoot";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfigRoot";
         }

         currentResult = new PropertyDescriptor("ConfigRoot", DeploymentPlanBean.class, getterName, setterName);
         descriptors.put("ConfigRoot", currentResult);
         currentResult.setValue("description", "Gets the \"install-root\" element ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", DeploymentPlanBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "Gets the \"description\" element ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModuleOverrides")) {
         getterName = "getModuleOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleOverrides", DeploymentPlanBean.class, getterName, setterName);
         descriptors.put("ModuleOverrides", currentResult);
         currentResult.setValue("description", "Gets array of all \"module-override\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyModuleOverride");
         currentResult.setValue("creator", "createModuleOverride");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VariableDefinition")) {
         getterName = "getVariableDefinition";
         setterName = null;
         currentResult = new PropertyDescriptor("VariableDefinition", DeploymentPlanBean.class, getterName, setterName);
         descriptors.put("VariableDefinition", currentResult);
         currentResult.setValue("description", "Gets the \"variable-definition\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", DeploymentPlanBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "Gets the \"version\" element ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GlobalVariables")) {
         getterName = "isGlobalVariables";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGlobalVariables";
         }

         currentResult = new PropertyDescriptor("GlobalVariables", DeploymentPlanBean.class, getterName, setterName);
         descriptors.put("GlobalVariables", currentResult);
         currentResult.setValue("description", "Global variable names are of the form BeanName_[KeyValue_]PropertyName. If false then the name is appended with a unique number. Global variables are useful for most descriptors since this allows a single variable value to affect all like references to it. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DeploymentPlanBean.class.getMethod("createModuleOverride");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ModuleOverrides");
      }

      mth = DeploymentPlanBean.class.getMethod("destroyModuleOverride", ModuleOverrideBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ModuleOverrides");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DeploymentPlanBean.class.getMethod("findModuleOverride", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get descriptor overrides  for any  module in plan. ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("findModuleDescriptor", String.class, String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get descriptor for  module in plan. ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("rootModule", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("moduleName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("hasVariable", ModuleDescriptorBean.class, DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("findVariable", ModuleDescriptorBean.class, DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("findOrCreateVariable", ModuleDescriptorBean.class, DescriptorBean.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null), createParameterDescriptor("planBased", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the property is transient")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("findOrCreateVariable", ModuleDescriptorBean.class, DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the property is transient")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("findOrCreateVariable", ModuleDescriptorBean.class, DescriptorBean.class, String.class, Boolean.TYPE, Object.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null), createParameterDescriptor("planBased", (String)null), createParameterDescriptor("oldKeyValue", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the property is transient")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("findVariableAssignments", VariableBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("var", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("findVariableAssignment", ModuleDescriptorBean.class, DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the desc is null")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("valueOf", VariableBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("var", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("assignVariable", VariableBean.class, ModuleDescriptorBean.class, DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("var", (String)null), createParameterDescriptor("desc", (String)null), createParameterDescriptor("bean", (String)null), createParameterDescriptor("prop", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create new variable assignment from existing variable. ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("findRootModule");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "A plan can have at most one ModuleOverride that represents the root module of the application. ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("findAndRemoveAllBeanVariables", ModuleDescriptorBean.class, DescriptorBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentPlanBean.class.getMethod("isRemovable", DescriptorBean.class);
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
