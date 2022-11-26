package weblogic.store.admin;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.PersistentStoreConnectionRuntimeMBean;

public class PersistentStoreConnectionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PersistentStoreConnectionRuntimeMBean.class;

   public PersistentStoreConnectionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PersistentStoreConnectionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.store.admin.PersistentStoreConnectionRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.store.admin");
      String description = (new String("This class is used for monitoring a Persistent Store Connection. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PersistentStoreConnectionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CreateCount")) {
         getterName = "getCreateCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CreateCount", PersistentStoreConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CreateCount", currentResult);
         currentResult.setValue("description", "<p>Number of create requests issued by this connection.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeleteCount")) {
         getterName = "getDeleteCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DeleteCount", PersistentStoreConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeleteCount", currentResult);
         currentResult.setValue("description", "<p>Number of delete requests issued by this connection.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ObjectCount")) {
         getterName = "getObjectCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ObjectCount", PersistentStoreConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ObjectCount", currentResult);
         currentResult.setValue("description", "<p>Number of objects contained in the connection.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReadCount")) {
         getterName = "getReadCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReadCount", PersistentStoreConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReadCount", currentResult);
         currentResult.setValue("description", "<p>Number of read requests issued by this connection, including requests that occur during store initialization.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UpdateCount")) {
         getterName = "getUpdateCount";
         setterName = null;
         currentResult = new PropertyDescriptor("UpdateCount", PersistentStoreConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UpdateCount", currentResult);
         currentResult.setValue("description", "<p>Number of update requests issued by this connection.</p> ");
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
