package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class TangosolQueryCacheBeanImplBeanInfo extends QueryCacheBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TangosolQueryCacheBean.class;

   public TangosolQueryCacheBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TangosolQueryCacheBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.TangosolQueryCacheBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String("Query cache which delegates to Tangosol. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.TangosolQueryCacheBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClearOnClose")) {
         getterName = "getClearOnClose";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClearOnClose";
         }

         currentResult = new PropertyDescriptor("ClearOnClose", TangosolQueryCacheBean.class, getterName, setterName);
         descriptors.put("ClearOnClose", currentResult);
         currentResult.setValue("description", "Whether to clear tangosol cache on close. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TangosolCacheName")) {
         getterName = "getTangosolCacheName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTangosolCacheName";
         }

         currentResult = new PropertyDescriptor("TangosolCacheName", TangosolQueryCacheBean.class, getterName, setterName);
         descriptors.put("TangosolCacheName", currentResult);
         currentResult.setValue("description", "The name of the corresponding tangosol cache. ");
         setPropertyDescriptorDefault(currentResult, "kodo-query");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TangosolCacheType")) {
         getterName = "getTangosolCacheType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTangosolCacheType";
         }

         currentResult = new PropertyDescriptor("TangosolCacheType", TangosolQueryCacheBean.class, getterName, setterName);
         descriptors.put("TangosolCacheType", currentResult);
         currentResult.setValue("description", "Tangosol cache type. ");
         setPropertyDescriptorDefault(currentResult, "named");
         currentResult.setValue("legalValues", new Object[]{"distributed", "replicated", "named"});
         currentResult.setValue("configurable", Boolean.TRUE);
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
