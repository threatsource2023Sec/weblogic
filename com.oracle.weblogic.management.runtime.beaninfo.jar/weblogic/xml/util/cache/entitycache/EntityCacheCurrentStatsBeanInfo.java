package weblogic.xml.util.cache.entitycache;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.EntityCacheCurrentStateRuntimeMBean;

public class EntityCacheCurrentStatsBeanInfo extends EntityCacheStatsBeanInfo {
   public static final Class INTERFACE_CLASS = EntityCacheCurrentStateRuntimeMBean.class;

   public EntityCacheCurrentStatsBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EntityCacheCurrentStatsBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.xml.util.cache.entitycache.EntityCacheCurrentStats");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.xml.util.cache.entitycache");
      String description = (new String("<p>This class is used for monitoring the size and usage of an XML Cache.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EntityCacheCurrentStateRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AvgPerEntryDiskSize")) {
         getterName = "getAvgPerEntryDiskSize";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgPerEntryDiskSize", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgPerEntryDiskSize", currentResult);
         currentResult.setValue("description", "<p>Provides the current average size of the entries in the entity disk cache.</p>  <p>Returns the current average size of the entries in the entity disk cache.</p> ");
      }

      if (!descriptors.containsKey("AvgPerEntryMemorySize")) {
         getterName = "getAvgPerEntryMemorySize";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgPerEntryMemorySize", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgPerEntryMemorySize", currentResult);
         currentResult.setValue("description", "<p>Provides the current average size of the entries in the entity memory cache.</p>  <p>Returns the current average size of the entries in the entity memory cache.</p> ");
      }

      if (!descriptors.containsKey("AvgPercentPersistent")) {
         getterName = "getAvgPercentPersistent";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgPercentPersistent", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgPercentPersistent", currentResult);
         currentResult.setValue("description", "<p>Provides the current average percentage of entries in the entity cache that have been persisted to the disk cache.</p>  <p>Returns current average percentage of entries in the entity cache that have been persisted to the disk cache.</p> ");
      }

      if (!descriptors.containsKey("AvgPercentTransient")) {
         getterName = "getAvgPercentTransient";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgPercentTransient", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgPercentTransient", currentResult);
         currentResult.setValue("description", "<p>Provides the current average percentage of entries in the entity cache that are transient, or have not been persisted.</p>  <p>Returns current average percentage of entries in the entity cache that are transient, or have not been persisted.</p> ");
      }

      if (!descriptors.containsKey("AvgTimeout")) {
         getterName = "getAvgTimeout";
         setterName = null;
         currentResult = new PropertyDescriptor("AvgTimeout", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AvgTimeout", currentResult);
         currentResult.setValue("description", "<p>Provides the average amount of time that the entity cache has timed out when trying to retrieve an entity.</p>  <p>Returns the average amount of time that the entity cache has timed out when trying to retrieve an entity.</p> ");
      }

      if (!descriptors.containsKey("DiskUsage")) {
         getterName = "getDiskUsage";
         setterName = null;
         currentResult = new PropertyDescriptor("DiskUsage", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DiskUsage", currentResult);
         currentResult.setValue("description", "<p>Provides the current size of the entity disk cache.</p>  <p>Returns the current size of the entity disk cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxEntryMemorySize")) {
         getterName = "getMaxEntryMemorySize";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxEntryMemorySize", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxEntryMemorySize", currentResult);
         currentResult.setValue("description", "<p>Provides the current maximum size of the entries in the entity memory cache.</p>  <p>Returns the current maximum size of the entries in the entity memory cache.</p> ");
      }

      if (!descriptors.containsKey("MaxEntryTimeout")) {
         getterName = "getMaxEntryTimeout";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxEntryTimeout", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxEntryTimeout", currentResult);
         currentResult.setValue("description", "<p>Provides the largest timeout value for any current entry in the entity cache.</p>  <p>Returns the largest timeout value for any current entry in the entity cache.</p> ");
      }

      if (!descriptors.containsKey("MemoryUsage")) {
         getterName = "getMemoryUsage";
         setterName = null;
         currentResult = new PropertyDescriptor("MemoryUsage", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MemoryUsage", currentResult);
         currentResult.setValue("description", "<p>Provides the current size of the entity memory cache.</p>  <p>Returns current size of the entity memory cache.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinEntryMemorySize")) {
         getterName = "getMinEntryMemorySize";
         setterName = null;
         currentResult = new PropertyDescriptor("MinEntryMemorySize", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinEntryMemorySize", currentResult);
         currentResult.setValue("description", "<p>Provides the current minimum size of the entries in the entity memory cache.</p>  <p>Returns the current minimum size of the entries in the entity memory cache.</p> ");
      }

      if (!descriptors.containsKey("MinEntryTimeout")) {
         getterName = "getMinEntryTimeout";
         setterName = null;
         currentResult = new PropertyDescriptor("MinEntryTimeout", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinEntryTimeout", currentResult);
         currentResult.setValue("description", "<p>Provides the smallest timeout value for any current entry in the entity cache.</p>  <p>Returns the smallest timeout value for any current entry in the entity cache.</p> ");
      }

      if (!descriptors.containsKey("TotalCurrentEntries")) {
         getterName = "getTotalCurrentEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalCurrentEntries", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalCurrentEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total current number of entries in the entity cache.</p>  <p>Returns the total current number of entries in the entity cache.</p> ");
      }

      if (!descriptors.containsKey("TotalPersistentCurrentEntries")) {
         getterName = "getTotalPersistentCurrentEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalPersistentCurrentEntries", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalPersistentCurrentEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total current number of entries in the cache that have been persisted to disk.</p>  <p>Returns the total current number of entries in the cache that have been persisted to disk.</p> ");
      }

      if (!descriptors.containsKey("TotalTransientCurrentEntries")) {
         getterName = "getTotalTransientCurrentEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalTransientCurrentEntries", EntityCacheCurrentStateRuntimeMBean.class, getterName, (String)setterName);
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
