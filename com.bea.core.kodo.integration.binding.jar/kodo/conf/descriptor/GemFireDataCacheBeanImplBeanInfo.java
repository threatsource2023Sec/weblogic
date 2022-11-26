package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class GemFireDataCacheBeanImplBeanInfo extends DataCacheBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = GemFireDataCacheBean.class;

   public GemFireDataCacheBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public GemFireDataCacheBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.GemFireDataCacheBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String("GemFire datacache implementation. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.GemFireDataCacheBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("EvictionSchedule")) {
         getterName = "getEvictionSchedule";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEvictionSchedule";
         }

         currentResult = new PropertyDescriptor("EvictionSchedule", GemFireDataCacheBean.class, getterName, setterName);
         descriptors.put("EvictionSchedule", currentResult);
         currentResult.setValue("description", "A cron-style eviction schedule. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GemFireCacheName")) {
         getterName = "getGemFireCacheName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGemFireCacheName";
         }

         currentResult = new PropertyDescriptor("GemFireCacheName", GemFireDataCacheBean.class, getterName, setterName);
         descriptors.put("GemFireCacheName", currentResult);
         currentResult.setValue("description", "The name of the corresponding GemFire cache to use. ");
         setPropertyDescriptorDefault(currentResult, "root/kodo-data-cache");
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
