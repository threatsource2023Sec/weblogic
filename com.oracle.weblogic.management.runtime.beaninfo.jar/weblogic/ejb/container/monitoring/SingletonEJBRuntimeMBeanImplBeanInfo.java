package weblogic.ejb.container.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SingletonEJBRuntimeMBean;

public class SingletonEJBRuntimeMBeanImplBeanInfo extends EJBRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SingletonEJBRuntimeMBean.class;

   public SingletonEJBRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SingletonEJBRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.monitoring.SingletonEJBRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.ejb.container.monitoring");
      String description = (new String("This interface contains accessor methods for all EJB run-time information collected for a Singleton session bean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SingletonEJBRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("EJBName")) {
         getterName = "getEJBName";
         setterName = null;
         currentResult = new PropertyDescriptor("EJBName", SingletonEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EJBName", currentResult);
         currentResult.setValue("description", "<p>Provides the name for this EJB as defined in the javax.ejb.EJB annotation, or the ejb-name when * using the ejb-jar.xml deployment descriptor.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ReadLockTimeoutTotalCount")) {
         getterName = "getReadLockTimeoutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReadLockTimeoutTotalCount", SingletonEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReadLockTimeoutTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides the count of read lock requests that have timed out since deployment; -1 will be returned if bean-managed concurrency is in use.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReadLockTotalCount")) {
         getterName = "getReadLockTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReadLockTotalCount", SingletonEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReadLockTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total count of read locks requested since deployment; -1 will be returned if bean-managed concurrency is in use.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Resources")) {
         getterName = "getResources";
         setterName = null;
         currentResult = new PropertyDescriptor("Resources", SingletonEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Resources", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the RuntimeMBeans for the resources used by this EJB. This will always include an ExecuteQueueRuntimeMBean. It will also include a JMSDestinationRuntimeMBean for message-driven beans and a JDBCConnectionPoolMBean for CMP entity beans.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("TimerRuntime")) {
         getterName = "getTimerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("TimerRuntime", SingletonEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TimerRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides run-time information about any Timers created for the Singleton session bean.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionRuntime")) {
         getterName = "getTransactionRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRuntime", SingletonEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides the EJBTransactionRuntimeMBean, containing the run-time transaction counts for this EJB.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("WaiterCurrentCount")) {
         getterName = "getWaiterCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WaiterCurrentCount", SingletonEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaiterCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total count of threads waiting for the lock; -1 will be returned if bean-managed concurrency is in use.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WriteLockTimeoutTotalCount")) {
         getterName = "getWriteLockTimeoutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WriteLockTimeoutTotalCount", SingletonEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WriteLockTimeoutTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides the count of write lock requests that have timed out since deployment; -1 will be returned if bean-managed concurrency is in use.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WriteLockTotalCount")) {
         getterName = "getWriteLockTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WriteLockTotalCount", SingletonEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WriteLockTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total count of write locks requested since deployment; -1 will be returned if bean-managed concurrency is in use.</p> ");
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
