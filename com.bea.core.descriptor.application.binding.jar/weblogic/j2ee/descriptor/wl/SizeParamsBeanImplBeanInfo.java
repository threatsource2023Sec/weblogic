package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class SizeParamsBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = SizeParamsBean.class;

   public SizeParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SizeParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.SizeParamsBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.SizeParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CapacityIncrement")) {
         getterName = "getCapacityIncrement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCapacityIncrement";
         }

         currentResult = new PropertyDescriptor("CapacityIncrement", SizeParamsBean.class, getterName, setterName);
         descriptors.put("CapacityIncrement", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumUnavailable")) {
         getterName = "getHighestNumUnavailable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHighestNumUnavailable";
         }

         currentResult = new PropertyDescriptor("HighestNumUnavailable", SizeParamsBean.class, getterName, setterName);
         descriptors.put("HighestNumUnavailable", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumWaiters")) {
         getterName = "getHighestNumWaiters";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHighestNumWaiters";
         }

         currentResult = new PropertyDescriptor("HighestNumWaiters", SizeParamsBean.class, getterName, setterName);
         descriptors.put("HighestNumWaiters", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialCapacity")) {
         getterName = "getInitialCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialCapacity";
         }

         currentResult = new PropertyDescriptor("InitialCapacity", SizeParamsBean.class, getterName, setterName);
         descriptors.put("InitialCapacity", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCapacity")) {
         getterName = "getMaxCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxCapacity";
         }

         currentResult = new PropertyDescriptor("MaxCapacity", SizeParamsBean.class, getterName, setterName);
         descriptors.put("MaxCapacity", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShrinkFrequencySeconds")) {
         getterName = "getShrinkFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShrinkFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("ShrinkFrequencySeconds", SizeParamsBean.class, getterName, setterName);
         descriptors.put("ShrinkFrequencySeconds", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShrinkPeriodMinutes")) {
         getterName = "getShrinkPeriodMinutes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShrinkPeriodMinutes";
         }

         currentResult = new PropertyDescriptor("ShrinkPeriodMinutes", SizeParamsBean.class, getterName, setterName);
         descriptors.put("ShrinkPeriodMinutes", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShrinkingEnabled")) {
         getterName = "isShrinkingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShrinkingEnabled";
         }

         currentResult = new PropertyDescriptor("ShrinkingEnabled", SizeParamsBean.class, getterName, setterName);
         descriptors.put("ShrinkingEnabled", currentResult);
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
