package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class DetachOptionsAllBeanImplBeanInfo extends DetachStateBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DetachOptionsAllBean.class;

   public DetachOptionsAllBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DetachOptionsAllBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.DetachOptionsAllBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String("Detach all fields. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.DetachOptionsAllBean");
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

         currentResult = new PropertyDescriptor("AccessUnloaded", DetachOptionsAllBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("DetachedStateField", DetachOptionsAllBean.class, getterName, setterName);
         descriptors.put("DetachedStateField", currentResult);
         currentResult.setValue("description", "The field to store detached state. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachedStateManager")) {
         getterName = "getDetachedStateManager";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDetachedStateManager";
         }

         currentResult = new PropertyDescriptor("DetachedStateManager", DetachOptionsAllBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("DetachedStateTransient", DetachOptionsAllBean.class, getterName, setterName);
         descriptors.put("DetachedStateTransient", currentResult);
         currentResult.setValue("description", "Return whether detached states are transient. ");
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
