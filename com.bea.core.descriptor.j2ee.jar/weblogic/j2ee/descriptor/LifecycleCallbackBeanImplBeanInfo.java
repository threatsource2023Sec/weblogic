package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class LifecycleCallbackBeanImplBeanInfo extends SourceTrackerBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LifecycleCallbackBean.class;

   public LifecycleCallbackBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LifecycleCallbackBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.LifecycleCallbackBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.LifecycleCallbackBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("LifecycleCallbackClass")) {
         getterName = "getLifecycleCallbackClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLifecycleCallbackClass";
         }

         currentResult = new PropertyDescriptor("LifecycleCallbackClass", LifecycleCallbackBean.class, getterName, setterName);
         descriptors.put("LifecycleCallbackClass", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LifecycleCallbackMethod")) {
         getterName = "getLifecycleCallbackMethod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLifecycleCallbackMethod";
         }

         currentResult = new PropertyDescriptor("LifecycleCallbackMethod", LifecycleCallbackBean.class, getterName, setterName);
         descriptors.put("LifecycleCallbackMethod", currentResult);
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
