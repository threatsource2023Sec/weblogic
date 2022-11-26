package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class CacheMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CacheMBean.class;

   public CacheMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CacheMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CacheMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CacheMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AsyncListeners")) {
         getterName = "getAsyncListeners";
         setterName = null;
         currentResult = new PropertyDescriptor("AsyncListeners", CacheMBean.class, getterName, setterName);
         descriptors.put("AsyncListeners", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EvictionPolicy")) {
         getterName = "getEvictionPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEvictionPolicy";
         }

         currentResult = new PropertyDescriptor("EvictionPolicy", CacheMBean.class, getterName, setterName);
         descriptors.put("EvictionPolicy", currentResult);
         currentResult.setValue("description", "The eviction policy to choose when the number of entries in cache hits the maximum ");
         setPropertyDescriptorDefault(currentResult, "LFU");
         currentResult.setValue("legalValues", new Object[]{"LRU", "NRU", "FIFO", "LFU"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Expiration")) {
         getterName = "getExpiration";
         setterName = null;
         currentResult = new PropertyDescriptor("Expiration", CacheMBean.class, getterName, setterName);
         descriptors.put("Expiration", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIName";
         }

         currentResult = new PropertyDescriptor("JNDIName", CacheMBean.class, getterName, setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Loader")) {
         getterName = "getLoader";
         setterName = null;
         currentResult = new PropertyDescriptor("Loader", CacheMBean.class, getterName, setterName);
         descriptors.put("Loader", currentResult);
         currentResult.setValue("description", "The configuration parameters for self-loading caches ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCacheUnits")) {
         getterName = "getMaxCacheUnits";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxCacheUnits";
         }

         currentResult = new PropertyDescriptor("MaxCacheUnits", CacheMBean.class, getterName, setterName);
         descriptors.put("MaxCacheUnits", currentResult);
         currentResult.setValue("description", "Maximum number of cache elements in memory after which eviction/paging occurs. This value is defined as an Integer. ");
         setPropertyDescriptorDefault(currentResult, new Integer(64));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Store")) {
         getterName = "getStore";
         setterName = null;
         currentResult = new PropertyDescriptor("Store", CacheMBean.class, getterName, setterName);
         descriptors.put("Store", currentResult);
         currentResult.setValue("description", "The configuraiton parameters for self-backing caches ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Transactional")) {
         getterName = "getTransactional";
         setterName = null;
         currentResult = new PropertyDescriptor("Transactional", CacheMBean.class, getterName, setterName);
         descriptors.put("Transactional", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManager")) {
         getterName = "getWorkManager";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkManager";
         }

         currentResult = new PropertyDescriptor("WorkManager", CacheMBean.class, getterName, setterName);
         descriptors.put("WorkManager", currentResult);
         currentResult.setValue("description", "Set the default work manager to use for all asynchronous caching tasks. If none of the specific work managers are specified, this work manager is used. This work manager may be overriden by other work managers configured for specific tasks like store backup, listeners etc ");
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
