package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ResourceGroupMBeanImplBeanInfo extends ResourceGroupTemplateMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ResourceGroupMBean.class;

   public ResourceGroupMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ResourceGroupMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ResourceGroupMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("A resource group is a named collection of deployable resources. Typically the resources in a given resource group are related in some way, for example in that they make up a single application suite. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ResourceGroupMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ResourceGroupTemplate")) {
         getterName = "getResourceGroupTemplate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceGroupTemplate";
         }

         currentResult = new PropertyDescriptor("ResourceGroupTemplate", ResourceGroupMBean.class, getterName, setterName);
         descriptors.put("ResourceGroupTemplate", currentResult);
         currentResult.setValue("description", "The resource group template referenced by this resource group. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", ResourceGroupMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "A list of all the targets. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UploadDirectoryName")) {
         getterName = "getUploadDirectoryName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUploadDirectoryName";
         }

         currentResult = new PropertyDescriptor("UploadDirectoryName", ResourceGroupMBean.class, getterName, setterName);
         descriptors.put("UploadDirectoryName", currentResult);
         currentResult.setValue("description", "<p>The directory path on the Administration Server where the uploaded applications for this resource group template are placed.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("Administrative")) {
         getterName = "isAdministrative";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdministrative";
         }

         currentResult = new PropertyDescriptor("Administrative", ResourceGroupMBean.class, getterName, setterName);
         descriptors.put("Administrative", currentResult);
         currentResult.setValue("description", "<p>Does this resource group contain administrative applications and resources? If true, then this is considered an administrative resource group and will be handled differently by the partition lifecycle. Specifically, an administrative resource group will be left running when a partition is shut down (it will be shut down only when the partition is halted).</p>  <p>Note that this Boolean is independent of the targeting of the resource group and works independently of the <code>autoTargetAdminServer</code> Boolean.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AutoTargetAdminServer")) {
         getterName = "isAutoTargetAdminServer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoTargetAdminServer";
         }

         currentResult = new PropertyDescriptor("AutoTargetAdminServer", ResourceGroupMBean.class, getterName, setterName);
         descriptors.put("AutoTargetAdminServer", currentResult);
         currentResult.setValue("description", "Should this resource group always be targeted to the Administration Server? If true, then this resource group will be targeted to the domain's Administration Server using the partition's administrative virtual target (AdminVT) in addition to any targets explicitly set on the resource group. Functionally, this is similar to getting the partition's administrative virtual target and explicitly adding it to the list of resource group targets. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("UseDefaultTarget")) {
         getterName = "isUseDefaultTarget";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseDefaultTarget";
         }

         currentResult = new PropertyDescriptor("UseDefaultTarget", ResourceGroupMBean.class, getterName, setterName);
         descriptors.put("UseDefaultTarget", currentResult);
         currentResult.setValue("description", "Checks whether this resource group uses the default target from the partition that contains the resource group. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceGroupMBean.class.getMethod("addTarget", TargetMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "the target to add ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Add an existing target to the list of targets. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = ResourceGroupMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "the target to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Remove the given target from the list of targets. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceGroupMBean.class.getMethod("findEffectiveTargets");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the targets actually used by this resource group, accounting for partition-level defaulting. ");
         currentResult.setValue("role", "operation");
         String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = ResourceGroupMBean.class.getMethod("lookupTarget", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the target to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Find a particular target with a given name. ");
         currentResult.setValue("role", "operation");
      }

      mth = ResourceGroupMBean.class.getMethod("areDefinedInTemplate", ConfigurationMBean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cbs", "an array of ConfigurationMBean parented by this resource group ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if any of the passed config beans is not  parented under a resource group or resource group template."), BeanInfoHelper.encodeEntities("IllegalArgumentException if any of the passed config beans is not  parented by this resource group or not parented by the resource group  template referenced by this resource group")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Indicates, for each of the passed configuration beans, if they are inherited from the resource group template referenced by this resource group. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
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
