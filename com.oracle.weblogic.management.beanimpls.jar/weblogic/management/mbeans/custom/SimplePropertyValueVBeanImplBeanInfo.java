package weblogic.management.mbeans.custom;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.configuration.SimplePropertyValueVBean;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class SimplePropertyValueVBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = SimplePropertyValueVBean.class;

   public SimplePropertyValueVBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SimplePropertyValueVBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeans.custom.SimplePropertyValueVBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("valueObject", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.mbeans.custom");
      String description = (new String("Allows admin clients to discover the effective value for a config bean after any relevant overrides have been applied. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SimplePropertyValueVBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("EffectiveValue")) {
         getterName = "getEffectiveValue";
         setterName = null;
         currentResult = new PropertyDescriptor("EffectiveValue", SimplePropertyValueVBean.class, getterName, setterName);
         descriptors.put("EffectiveValue", currentResult);
         currentResult.setValue("description", "Returns the effective value of the property after all (if any) overrides have been applied. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (!descriptors.containsKey("PropertyName")) {
         getterName = "getPropertyName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPropertyName";
         }

         currentResult = new PropertyDescriptor("PropertyName", SimplePropertyValueVBean.class, getterName, setterName);
         descriptors.put("PropertyName", currentResult);
         currentResult.setValue("description", "Returns the property name this PropertyValueVBean describes. ");
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
