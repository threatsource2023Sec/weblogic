package weblogic.store.admin;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.PersistentStoreRuntimeMBean;

public class PersistentStoreRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PersistentStoreRuntimeMBean.class;

   public PersistentStoreRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PersistentStoreRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.store.admin.PersistentStoreRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.store.admin");
      String description = (new String("<p>This class is used for monitoring a Persistent Store.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PersistentStoreRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AllocatedIoBufferBytes")) {
         getterName = "getAllocatedIoBufferBytes";
         setterName = null;
         currentResult = new PropertyDescriptor("AllocatedIoBufferBytes", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AllocatedIoBufferBytes", currentResult);
         currentResult.setValue("description", "<p>The amount of off-heap (native) memory, in bytes, reserved for file store use.</p>  <p>When applicable, this is a multiple of the file store configurable attribute <code>IOBufferSize</code>. Applies to synchronous write policies <code>Direct-Write</code> and <code>Cache-Flush policies</code>.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllocatedWindowBufferBytes")) {
         getterName = "getAllocatedWindowBufferBytes";
         setterName = null;
         currentResult = new PropertyDescriptor("AllocatedWindowBufferBytes", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AllocatedWindowBufferBytes", currentResult);
         currentResult.setValue("description", "<p>The amount of off-heap (native) memory, in bytes, reserved for file store window buffer use.</p>  <p>Applies to synchronous write policies <code>Direct-Write-With-Cache</code> and <code>Disabled</code> but only when the native <code>wlfileio</code> library is loaded. See file store configurable attribute <code>MaxWindowBufferSize</code> for more information</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Connections")) {
         getterName = "getConnections";
         setterName = null;
         currentResult = new PropertyDescriptor("Connections", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Connections", currentResult);
         currentResult.setValue("description", "<p>The connections contained in the store.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CreateCount")) {
         getterName = "getCreateCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CreateCount", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CreateCount", currentResult);
         currentResult.setValue("description", "<p>Number of create requests issued by this store.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeleteCount")) {
         getterName = "getDeleteCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DeleteCount", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeleteCount", currentResult);
         currentResult.setValue("description", "<p>Number of delete requests issued by this store.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>Implement the HealthFeedback interface by returning the health state of this store.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ObjectCount")) {
         getterName = "getObjectCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ObjectCount", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ObjectCount", currentResult);
         currentResult.setValue("description", "<p>Number of objects contained in the store.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PhysicalWriteCount")) {
         getterName = "getPhysicalWriteCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PhysicalWriteCount", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PhysicalWriteCount", currentResult);
         currentResult.setValue("description", "<p>Number of times the store flushed its data to durable storage.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReadCount")) {
         getterName = "getReadCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReadCount", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReadCount", currentResult);
         currentResult.setValue("description", "<p>Number of read requests issued by this store, including requests that occur during store initialization.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UpdateCount")) {
         getterName = "getUpdateCount";
         setterName = null;
         currentResult = new PropertyDescriptor("UpdateCount", PersistentStoreRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UpdateCount", currentResult);
         currentResult.setValue("description", "<p>Number of update requests issued by this store.</p> ");
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
