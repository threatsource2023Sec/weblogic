package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class LRUDataCacheBeanImplBeanInfo extends DataCacheBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LRUDataCacheBean.class;

   public LRUDataCacheBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LRUDataCacheBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.LRUDataCacheBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.LRUDataCacheBean");
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

         currentResult = new PropertyDescriptor("CacheSize", LRUDataCacheBean.class, getterName, setterName);
         descriptors.put("CacheSize", currentResult);
         currentResult.setValue("description", "The maximum number of unpinned objects to keep hard references to. If the hard reference cache is full, then adding new entries forces the oldest entries to move to the soft cache.  Entries that have outlived their lifetime in the hard reference cache are not moved to the soft reference cache when they are evicted. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EvictionSchedule")) {
         getterName = "getEvictionSchedule";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEvictionSchedule";
         }

         currentResult = new PropertyDescriptor("EvictionSchedule", LRUDataCacheBean.class, getterName, setterName);
         descriptors.put("EvictionSchedule", currentResult);
         currentResult.setValue("description", "The eviction schedule, if any, for the cache. Accepts a cron style eviction schedule. The format of this property is a whitespace-separated list of five tokens, where the * symbol (asterisk), indicates match-all. Multiple entries for each token can be entered by separating them with commas.  The tokens are, in order: <ol> <li>Minute</li> <li>Hour of Day</li> <li>Day of Month</li> <li>Month</li> <li>Day of Week</li> </ol> For example, the following eviction schedule evicts values from the cache at 15 and 45 minutes past 3 PM on Sundays: \"15,45 15 * * 1\". <p> Both the hard reference cache and the soft reference cache are evicted at the same time. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SoftReferenceSize")) {
         getterName = "getSoftReferenceSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSoftReferenceSize";
         }

         currentResult = new PropertyDescriptor("SoftReferenceSize", LRUDataCacheBean.class, getterName, setterName);
         descriptors.put("SoftReferenceSize", currentResult);
         currentResult.setValue("description", "The maximum number of unpinned objects to keep soft references to. Entries in the hard reference cache, when forced out due to space limitations before their cache lifetimes have expired, are moved to the soft reference cache, if it is enabled. If the soft reference cache is full, adding new entries will force the soft reference cache to drop existing entries. <p> If the SoftReferenceSize is set to -1 (its default size), then its size is unlimited. The soft reference cache may be disabled by setting its size to 0. <p> The Java garbage collector may exercise its option to clear the soft reference cache at any time. <p> ");
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
