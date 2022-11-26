package weblogic.xml.util.cache.entitycache;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.EntityCacheCumulativeRuntimeMBean;

public class EntityCacheCumulativeStatsBeanInfo extends EntityCacheStatsBeanInfo {
   public static final Class INTERFACE_CLASS = EntityCacheCumulativeRuntimeMBean.class;

   public EntityCacheCumulativeStatsBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EntityCacheCumulativeStatsBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.xml.util.cache.entitycache.EntityCacheCumulativeStats");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.xml.util.cache.entitycache");
      String description = (new String("This class is used for monitoring an XML Cache. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EntityCacheCumulativeRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AvgEntrySizeDiskPurged")) {
         getterName = "getAvgEntrySizeDiskPurged";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgEntrySizeDiskPurged", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgEntrySizeDiskPurged", currentResult);
         currentResult.setValue("description", "<p>Provides the cumulative average size of entries that have been purged from the disk cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AvgEntrySizeMemoryPurged")) {
         getterName = "getAvgEntrySizeMemoryPurged";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgEntrySizeMemoryPurged", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgEntrySizeMemoryPurged", currentResult);
         currentResult.setValue("description", "<p>Provides the average size of the all the entries that have been purged from the memory.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AvgPerEntryDiskSize")) {
         getterName = "getAvgPerEntryDiskSize";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgPerEntryDiskSize", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgPerEntryDiskSize", currentResult);
         currentResult.setValue("description", "<p>Provides the current average size of the entries in the entity disk cache.</p>  <p>Returns the current average size of the entries in the entity disk cache.</p> ");
      }

      if (!descriptors.containsKey("AvgPerEntryMemorySize")) {
         getterName = "getAvgPerEntryMemorySize";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgPerEntryMemorySize", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgPerEntryMemorySize", currentResult);
         currentResult.setValue("description", "<p>Provides the current average size of the entries in the entity memory cache.</p>  <p>Returns the current average size of the entries in the entity memory cache.</p> ");
      }

      if (!descriptors.containsKey("AvgPercentPersistent")) {
         getterName = "getAvgPercentPersistent";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgPercentPersistent", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgPercentPersistent", currentResult);
         currentResult.setValue("description", "<p>Provides the current average percentage of entries in the entity cache that have been persisted to the disk cache.</p>  <p>Returns current average percentage of entries in the entity cache that have been persisted to the disk cache.</p> ");
      }

      if (!descriptors.containsKey("AvgPercentTransient")) {
         getterName = "getAvgPercentTransient";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgPercentTransient", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgPercentTransient", currentResult);
         currentResult.setValue("description", "<p>Provides the current average percentage of entries in the entity cache that are transient, or have not been persisted.</p>  <p>Returns current average percentage of entries in the entity cache that are transient, or have not been persisted.</p> ");
      }

      if (!descriptors.containsKey("AvgTimeout")) {
         getterName = "getAvgTimeout";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgTimeout", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgTimeout", currentResult);
         currentResult.setValue("description", "<p>Provides the average amount of time that the entity cache has timed out when trying to retrieve an entity.</p>  <p>Returns the average amount of time that the entity cache has timed out when trying to retrieve an entity.</p> ");
      }

      if (!descriptors.containsKey("DiskPurgesPerHour")) {
         getterName = "getDiskPurgesPerHour";
         setterName = null;
         currentResult = new PropertyDescriptor("DiskPurgesPerHour", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DiskPurgesPerHour", currentResult);
         currentResult.setValue("description", "<p>Provides cumulative average number of purges from the disk cache per hour.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxEntryMemorySize")) {
         getterName = "getMaxEntryMemorySize";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxEntryMemorySize", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxEntryMemorySize", currentResult);
         currentResult.setValue("description", "<p>Provides the current maximum size of the entries in the entity memory cache.</p>  <p>Returns the current maximum size of the entries in the entity memory cache.</p> ");
      }

      if (!descriptors.containsKey("MaxEntryTimeout")) {
         getterName = "getMaxEntryTimeout";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxEntryTimeout", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxEntryTimeout", currentResult);
         currentResult.setValue("description", "<p>Provides the largest timeout value for any current entry in the entity cache.</p>  <p>Returns the largest timeout value for any current entry in the entity cache.</p> ");
      }

      if (!descriptors.containsKey("MemoryPurgesPerHour")) {
         getterName = "getMemoryPurgesPerHour";
         setterName = null;
         currentResult = new PropertyDescriptor("MemoryPurgesPerHour", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MemoryPurgesPerHour", currentResult);
         currentResult.setValue("description", "<p>Provides the cumulative average number of entries that have been purged from the entity cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinEntryMemorySize")) {
         getterName = "getMinEntryMemorySize";
         setterName = null;
         currentResult = new PropertyDescriptor("MinEntryMemorySize", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinEntryMemorySize", currentResult);
         currentResult.setValue("description", "<p>Provides the current minimum size of the entries in the entity memory cache.</p>  <p>Returns the current minimum size of the entries in the entity memory cache.</p> ");
      }

      if (!descriptors.containsKey("MinEntryTimeout")) {
         getterName = "getMinEntryTimeout";
         setterName = null;
         currentResult = new PropertyDescriptor("MinEntryTimeout", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinEntryTimeout", currentResult);
         currentResult.setValue("description", "<p>Provides the smallest timeout value for any current entry in the entity cache.</p>  <p>Returns the smallest timeout value for any current entry in the entity cache.</p> ");
      }

      if (!descriptors.containsKey("MostRecentDiskPurge")) {
         getterName = "getMostRecentDiskPurge";
         setterName = null;
         currentResult = new PropertyDescriptor("MostRecentDiskPurge", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MostRecentDiskPurge", currentResult);
         currentResult.setValue("description", "<p>Provides the date of the most recent purge from the disk cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MostRecentMemoryPurge")) {
         getterName = "getMostRecentMemoryPurge";
         setterName = null;
         currentResult = new PropertyDescriptor("MostRecentMemoryPurge", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MostRecentMemoryPurge", currentResult);
         currentResult.setValue("description", "<p>Provides the date of the most recent purge of the entity cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PercentRejected")) {
         getterName = "getPercentRejected";
         setterName = null;
         currentResult = new PropertyDescriptor("PercentRejected", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PercentRejected", currentResult);
         currentResult.setValue("description", "<p>Provides the cumulative percent of the potential entries to the entity cache that have been rejected.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalCurrentEntries")) {
         getterName = "getTotalCurrentEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCurrentEntries", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCurrentEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total current number of entries in the entity cache.</p>  <p>Returns the total current number of entries in the entity cache.</p> ");
      }

      if (!descriptors.containsKey("TotalItemsDiskPurged")) {
         getterName = "getTotalItemsDiskPurged";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalItemsDiskPurged", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalItemsDiskPurged", currentResult);
         currentResult.setValue("description", "<p>Provides the total number of items that have been purged from the disk cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalItemsMemoryPurged")) {
         getterName = "getTotalItemsMemoryPurged";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalItemsMemoryPurged", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalItemsMemoryPurged", currentResult);
         currentResult.setValue("description", "<p>Provides the cumulative number of items that have been purged from the entity cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalNumberDiskPurges")) {
         getterName = "getTotalNumberDiskPurges";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNumberDiskPurges", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalNumberDiskPurges", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of entries that have been purged from the disk cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalNumberMemoryPurges")) {
         getterName = "getTotalNumberMemoryPurges";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNumberMemoryPurges", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalNumberMemoryPurges", currentResult);
         currentResult.setValue("description", "<p>Provides the cumulative number of entries that have been purged from the entity cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalNumberOfRejections")) {
         getterName = "getTotalNumberOfRejections";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNumberOfRejections", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalNumberOfRejections", currentResult);
         currentResult.setValue("description", "<p>Provides the cumulative total number of rejections of entries from the entity cache for the current session.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalNumberOfRenewals")) {
         getterName = "getTotalNumberOfRenewals";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNumberOfRenewals", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalNumberOfRenewals", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the cumulative number of entries that have been refreshed in the entity cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalPersistentCurrentEntries")) {
         getterName = "getTotalPersistentCurrentEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalPersistentCurrentEntries", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalPersistentCurrentEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total current number of entries in the cache that have been persisted to disk.</p>  <p>Returns the total current number of entries in the cache that have been persisted to disk.</p> ");
      }

      if (!descriptors.containsKey("TotalSizeOfRejections")) {
         getterName = "getTotalSizeOfRejections";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSizeOfRejections", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSizeOfRejections", currentResult);
         currentResult.setValue("description", "<p>Provides the cumulative total size of the rejections from the entity cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalTransientCurrentEntries")) {
         getterName = "getTotalTransientCurrentEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalTransientCurrentEntries", EntityCacheCumulativeRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalTransientCurrentEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total current number of transient (not yet persisted to disk) entries in the entity cache.</p>  <p>Returns the total current number of transient entries in the entity cache.</p> ");
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
