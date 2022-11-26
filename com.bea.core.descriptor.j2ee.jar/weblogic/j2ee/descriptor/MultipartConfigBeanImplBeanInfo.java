package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class MultipartConfigBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = MultipartConfigBean.class;

   public MultipartConfigBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MultipartConfigBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.MultipartConfigBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.MultipartConfigBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("FileSizeThreshold")) {
         getterName = "getFileSizeThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileSizeThreshold";
         }

         currentResult = new PropertyDescriptor("FileSizeThreshold", MultipartConfigBean.class, getterName, setterName);
         descriptors.put("FileSizeThreshold", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Location")) {
         getterName = "getLocation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocation";
         }

         currentResult = new PropertyDescriptor("Location", MultipartConfigBean.class, getterName, setterName);
         descriptors.put("Location", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxFileSize")) {
         getterName = "getMaxFileSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxFileSize";
         }

         currentResult = new PropertyDescriptor("MaxFileSize", MultipartConfigBean.class, getterName, setterName);
         descriptors.put("MaxFileSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRequestSize")) {
         getterName = "getMaxRequestSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRequestSize";
         }

         currentResult = new PropertyDescriptor("MaxRequestSize", MultipartConfigBean.class, getterName, setterName);
         descriptors.put("MaxRequestSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("exclude", Boolean.TRUE);
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
