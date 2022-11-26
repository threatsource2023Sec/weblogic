package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class DetachOptionsLoadedBeanImplBeanInfo extends DetachStateBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DetachOptionsLoadedBean.class;

   public DetachOptionsLoadedBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DetachOptionsLoadedBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.DetachOptionsLoadedBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String("Detach loaded fields. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.DetachOptionsLoadedBean");
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

         currentResult = new PropertyDescriptor("AccessUnloaded", DetachOptionsLoadedBean.class, getterName, setterName);
         descriptors.put("AccessUnloaded", currentResult);
         currentResult.setValue("description", "Whether to access unlaoded fields. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachedStateField")) {
         getterName = "getDetachedStateField";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDetachedStateField";
         }

         currentResult = new PropertyDescriptor("DetachedStateField", DetachOptionsLoadedBean.class, getterName, setterName);
         descriptors.put("DetachedStateField", currentResult);
         currentResult.setValue("description", "Whether to use a detached state field. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachedStateManager")) {
         getterName = "getDetachedStateManager";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDetachedStateManager";
         }

         currentResult = new PropertyDescriptor("DetachedStateManager", DetachOptionsLoadedBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("DetachedStateTransient", DetachOptionsLoadedBean.class, getterName, setterName);
         descriptors.put("DetachedStateTransient", currentResult);
         currentResult.setValue("description", "Whether detached state field is transint. ");
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
