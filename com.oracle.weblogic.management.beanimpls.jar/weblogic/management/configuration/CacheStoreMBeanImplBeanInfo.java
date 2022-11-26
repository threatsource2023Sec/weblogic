package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class CacheStoreMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CacheStoreMBean.class;

   public CacheStoreMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CacheStoreMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CacheStoreMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CacheStoreMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BufferMaxSize")) {
         getterName = "getBufferMaxSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBufferMaxSize";
         }

         currentResult = new PropertyDescriptor("BufferMaxSize", CacheStoreMBean.class, getterName, setterName);
         descriptors.put("BufferMaxSize", currentResult);
         currentResult.setValue("description", "Sets the upper limit for the store buffer that's used to write out updates to the store. A value of 0 indicates no limit ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BufferWriteAttempts")) {
         getterName = "getBufferWriteAttempts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBufferWriteAttempts";
         }

         currentResult = new PropertyDescriptor("BufferWriteAttempts", CacheStoreMBean.class, getterName, setterName);
         descriptors.put("BufferWriteAttempts", currentResult);
         currentResult.setValue("description", "Sets the number of attempts that the user thread will make to write to the store buffer. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BufferWriteTimeout")) {
         getterName = "getBufferWriteTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBufferWriteTimeout";
         }

         currentResult = new PropertyDescriptor("BufferWriteTimeout", CacheStoreMBean.class, getterName, setterName);
         descriptors.put("BufferWriteTimeout", currentResult);
         currentResult.setValue("description", "Sets the time in milliseconds that the user thread will wait before aborting an attempt to write to the buffer. The attempt to write to the store buffer fails only in case the buffer is full. After the timeout, futher attempts may be made to write to the buffer based on the value of StoreBufferWriteAttempts ");
         setPropertyDescriptorDefault(currentResult, new Long(100L));
         currentResult.setValue("legalMin", new Long(1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomStore")) {
         getterName = "getCustomStore";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomStore";
         }

         currentResult = new PropertyDescriptor("CustomStore", CacheStoreMBean.class, getterName, setterName);
         descriptors.put("CustomStore", currentResult);
         currentResult.setValue("description", "The cache store to be used for store backed caches ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreBatchSize")) {
         getterName = "getStoreBatchSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreBatchSize";
         }

         currentResult = new PropertyDescriptor("StoreBatchSize", CacheStoreMBean.class, getterName, setterName);
         descriptors.put("StoreBatchSize", currentResult);
         currentResult.setValue("description", "Sets the number of user updates that are picked up from the store buffer to write back to the backing store ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManager")) {
         getterName = "getWorkManager";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkManager";
         }

         currentResult = new PropertyDescriptor("WorkManager", CacheStoreMBean.class, getterName, setterName);
         descriptors.put("WorkManager", currentResult);
         currentResult.setValue("description", "Sets the work manager that schedules the thread that writes to the backing store asynchronously ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WritePolicy")) {
         getterName = "getWritePolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWritePolicy";
         }

         currentResult = new PropertyDescriptor("WritePolicy", CacheStoreMBean.class, getterName, setterName);
         descriptors.put("WritePolicy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"None", "WriteThrough", "WriteBehind"});
         currentResult.setValue("dynamic", Boolean.TRUE);
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
