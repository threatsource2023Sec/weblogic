package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class LRUQueryCacheBeanImplBeanInfo extends QueryCacheBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LRUQueryCacheBean.class;

   public LRUQueryCacheBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LRUQueryCacheBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.LRUQueryCacheBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.LRUQueryCacheBean");
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

         currentResult = new PropertyDescriptor("CacheSize", LRUQueryCacheBean.class, getterName, setterName);
         descriptors.put("CacheSize", currentResult);
         currentResult.setValue("description", "The maximum number of query results to keep hard references to. If the hard reference cache is full, then adding new entries forces the oldest entries to move to the soft cache. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SoftReferenceSize")) {
         getterName = "getSoftReferenceSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSoftReferenceSize";
         }

         currentResult = new PropertyDescriptor("SoftReferenceSize", LRUQueryCacheBean.class, getterName, setterName);
         descriptors.put("SoftReferenceSize", currentResult);
         currentResult.setValue("description", "The maximum number of query results to keep soft references to. Entries in the hard reference cache, when forced out due to space limitations, are moved to the soft reference cache, if it is enabled. If the soft reference cache is full, adding new entries will force the soft reference cache to drop existing entries. <p> If the SoftReferenceSize is set to -1 (its default value), then its size is unlimited. The soft reference cache may be disabled by setting its size to 0. <p> The Java garbage collector may exercise its option to clear the soft reference cache at any time. <p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
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
