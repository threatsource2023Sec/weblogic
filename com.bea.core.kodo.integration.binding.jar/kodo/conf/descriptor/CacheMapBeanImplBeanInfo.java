package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class CacheMapBeanImplBeanInfo extends QueryCompilationCacheBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CacheMapBean.class;

   public CacheMapBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CacheMapBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.CacheMapBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String("The basic query cache. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.CacheMapBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CacheSize")) {
         getterName = "getCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheSize";
         }

         currentResult = new PropertyDescriptor("CacheSize", CacheMapBean.class, getterName, setterName);
         descriptors.put("CacheSize", currentResult);
         currentResult.setValue("description", "The number of objects to hold in cache. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SoftReferenceSize")) {
         getterName = "getSoftReferenceSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSoftReferenceSize";
         }

         currentResult = new PropertyDescriptor("SoftReferenceSize", CacheMapBean.class, getterName, setterName);
         descriptors.put("SoftReferenceSize", currentResult);
         currentResult.setValue("description", "The number of soft references to hold in cache. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
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
