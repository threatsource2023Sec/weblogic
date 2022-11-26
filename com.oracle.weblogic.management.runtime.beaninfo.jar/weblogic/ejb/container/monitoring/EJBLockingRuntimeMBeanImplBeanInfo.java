package weblogic.ejb.container.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.EJBLockingRuntimeMBean;

public class EJBLockingRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EJBLockingRuntimeMBean.class;

   public EJBLockingRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EJBLockingRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.monitoring.EJBLockingRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.ejb.container.monitoring");
      String description = (new String("This interface contains accessor methods for all lock manager runtime information collected for an EJB. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EJBLockingRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("LockEntriesCurrentCount")) {
         getterName = "getLockEntriesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LockEntriesCurrentCount", EJBLockingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LockEntriesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the number of beans currently locked.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockManagerAccessCount")) {
         getterName = "getLockManagerAccessCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LockManagerAccessCount", EJBLockingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LockManagerAccessCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total number of attempts to obtain a lock on a bean. This includes attempts to obtain a lock on a bean that is already locked on behalf of the client.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeoutTotalCount")) {
         getterName = "getTimeoutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TimeoutTotalCount", EJBLockingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TimeoutTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides the current number of Threads that have timed out waiting for a lock on a bean.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WaiterCurrentCount")) {
         getterName = "getWaiterCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WaiterCurrentCount", EJBLockingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaiterCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Provides the current number of Threads that have waited for a lock on a bean.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WaiterTotalCount")) {
         getterName = "getWaiterTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WaiterTotalCount", EJBLockingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaiterTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total number of Threads that have waited for a lock on a bean.</p>  <p>Returns the total number of Threads that have waited for a lock on a bean.</p> ");
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
