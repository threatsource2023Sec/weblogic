package weblogic.ejb.container.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.EntityEJBRuntimeMBean;

public class EntityEJBRuntimeMBeanImplBeanInfo extends EJBRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EntityEJBRuntimeMBean.class;

   public EntityEJBRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EntityEJBRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.monitoring.EntityEJBRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.ejb.container.monitoring");
      String description = (new String("This interface contains accessor methods for all EJB runtime information collected for an Entity Bean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EntityEJBRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CacheRuntime")) {
         getterName = "getCacheRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheRuntime", EntityEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CacheRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides runtime information about the cache, for this EJB.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EJBName")) {
         getterName = "getEJBName";
         setterName = null;
         currentResult = new PropertyDescriptor("EJBName", EntityEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EJBName", currentResult);
         currentResult.setValue("description", "<p>Provides the name for this EJB as defined in the javax.ejb.EJB annotation, or the ejb-name when * using the ejb-jar.xml deployment descriptor.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("LockingRuntime")) {
         getterName = "getLockingRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("LockingRuntime", EntityEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LockingRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides runtime information about the lock manager, for this EJB. If this Entity bean does not use an exclusive concurrency strategy, null will be returned.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolRuntime")) {
         getterName = "getPoolRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolRuntime", EntityEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides runtime information about the free pool, for this EJB.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QueryCacheRuntime")) {
         getterName = "getQueryCacheRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("QueryCacheRuntime", EntityEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("QueryCacheRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides runtime information about the query cache for read-only EJBs. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Resources")) {
         getterName = "getResources";
         setterName = null;
         currentResult = new PropertyDescriptor("Resources", EntityEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Resources", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the RuntimeMBeans for the resources used by this EJB. This will always include an ExecuteQueueRuntimeMBean. It will also include a JMSDestinationRuntimeMBean for message-driven beans and a JDBCConnectionPoolMBean for CMP entity beans.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("TimerRuntime")) {
         getterName = "getTimerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("TimerRuntime", EntityEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TimerRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides runtime information about any EJB Timers created, for this EJB. If the bean class for this EJB does not implement javax.ejb.TimedObject, null will be returned.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionRuntime")) {
         getterName = "getTransactionRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRuntime", EntityEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides the EJBTransactionRuntimeMBean, containing the run-time transaction counts for this EJB.</p> ");
         currentResult.setValue("relationship", "containment");
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
