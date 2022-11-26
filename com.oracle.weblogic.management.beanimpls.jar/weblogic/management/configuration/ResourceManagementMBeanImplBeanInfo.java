package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ResourceManagementMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ResourceManagementMBean.class;

   public ResourceManagementMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ResourceManagementMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ResourceManagementMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionMBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean is used to create and edit resource management policy configurations (Resource Managers) for the domain.  Partition-scoped resource managers can be created under {@code PartitionMBean}. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ResourceManagementMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("ResourceManagers")) {
         String getterName = "getResourceManagers";
         String setterName = null;
         currentResult = new PropertyDescriptor("ResourceManagers", ResourceManagementMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceManagers", currentResult);
         currentResult.setValue("description", "Gets the list of configured resource managers in the Domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResourceManager");
         currentResult.setValue("destroyer", "destroyResourceManager");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceManagementMBean.class.getMethod("createResourceManager", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the resource manager configuration ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This is the factory method for resource manager configurations scoped at the domain level. The specified {@code name} parameter must be unique among all object instances of type {@code ResourceManagerMBean}. The new {@code ResourceManager} which is created will have the Domain as its parent and must be destroyed with the {@link #destroyResourceManager(ResourceManagerMBean)} method. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceManagers");
         currentResult.setValue("excludeFromRest", "");
      }

      mth = ResourceManagementMBean.class.getMethod("destroyResourceManager", ResourceManagerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resMgrMBean", "The ResourceManagerMBean to be removed from the domain. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a resource manager configuration corresponding to the {code resMgrMBean} parameter, which is a child of this Domain. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceManagers");
         currentResult.setValue("excludeFromRest", "");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceManagementMBean.class.getMethod("lookupResourceManager", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the resource manager to lookup. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up a resource manager configuration from the list of resource managers configured in the domain ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ResourceManagers");
         currentResult.setValue("excludeFromRest", "");
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
