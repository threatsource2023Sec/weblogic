package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class DetachOptionsFetchGroupsBeanImplBeanInfo extends DetachStateBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DetachOptionsFetchGroupsBean.class;

   public DetachOptionsFetchGroupsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DetachOptionsFetchGroupsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.DetachOptionsFetchGroupsBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String("Detach fetch-groups. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.DetachOptionsFetchGroupsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AccessUnloaded")) {
         getterName = "getAccessUnloaded";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAccessUnloaded";
         }

         currentResult = new PropertyDescriptor("AccessUnloaded", DetachOptionsFetchGroupsBean.class, getterName, setterName);
         descriptors.put("AccessUnloaded", currentResult);
         currentResult.setValue("description", "Whether to access unloaded fields. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachedStateField")) {
         getterName = "getDetachedStateField";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDetachedStateField";
         }

         currentResult = new PropertyDescriptor("DetachedStateField", DetachOptionsFetchGroupsBean.class, getterName, setterName);
         descriptors.put("DetachedStateField", currentResult);
         currentResult.setValue("description", "Whhether to use a detached state field. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachedStateManager")) {
         getterName = "getDetachedStateManager";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDetachedStateManager";
         }

         currentResult = new PropertyDescriptor("DetachedStateManager", DetachOptionsFetchGroupsBean.class, getterName, setterName);
         descriptors.put("DetachedStateManager", currentResult);
         currentResult.setValue("description", "Whether to use detached state manager. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachedStateTransient")) {
         getterName = "getDetachedStateTransient";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDetachedStateTransient";
         }

         currentResult = new PropertyDescriptor("DetachedStateTransient", DetachOptionsFetchGroupsBean.class, getterName, setterName);
         descriptors.put("DetachedStateTransient", currentResult);
         currentResult.setValue("description", "Whether detached state fields are transient. ");
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
