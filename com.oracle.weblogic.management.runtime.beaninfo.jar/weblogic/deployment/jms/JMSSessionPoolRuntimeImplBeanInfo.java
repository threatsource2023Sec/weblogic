package weblogic.deployment.jms;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.JMSPooledConnectionRuntimeMBean;

public class JMSSessionPoolRuntimeImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSPooledConnectionRuntimeMBean.class;

   public JMSSessionPoolRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSSessionPoolRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.deployment.jms.JMSSessionPoolRuntimeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.deployment.jms");
      String description = (new String("This class is used for monitoring pooled JMS connections. A pooled JMS connection is a session pool used by EJBs and servlets that use a resource-reference element in their EJB or Servlet deployment descriptor to define their JMS connection factories. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSPooledConnectionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AverageReserved")) {
         getterName = "getAverageReserved";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageReserved", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageReserved", currentResult);
         currentResult.setValue("description", "<p>The average number of JMS sessions that have been in use in this instance of the session pool since it was instantiated. This generally happens when an EJB or servlet is deployed that requires the session pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CreationDelayTime")) {
         getterName = "getCreationDelayTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CreationDelayTime", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CreationDelayTime", currentResult);
         currentResult.setValue("description", "<p>The average amount of time that it takes to create each JMS session in the session pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrCapacity")) {
         getterName = "getCurrCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrCapacity", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrCapacity", currentResult);
         currentResult.setValue("description", "<p>The current capacity of the session pool, which is always less than or equal to the maximum capacity of JMS sessions.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumAvailable")) {
         getterName = "getHighestNumAvailable";
         setterName = null;
         currentResult = new PropertyDescriptor("HighestNumAvailable", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HighestNumAvailable", currentResult);
         currentResult.setValue("description", "<p>The highest number of available JMS sessions in this instance of the session pool since it was instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumReserved")) {
         getterName = "getHighestNumReserved";
         setterName = null;
         currentResult = new PropertyDescriptor("HighestNumReserved", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HighestNumReserved", currentResult);
         currentResult.setValue("description", "<p>The highest number of concurrent JMS sessions reserved for this instance of the session pool since it was instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumUnavailable")) {
         getterName = "getHighestNumUnavailable";
         setterName = null;
         currentResult = new PropertyDescriptor("HighestNumUnavailable", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HighestNumUnavailable", currentResult);
         currentResult.setValue("description", "<p>The highest number of unusable JMS sessions in this instance of the session pool since it was instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumWaiters")) {
         getterName = "getHighestNumWaiters";
         setterName = null;
         currentResult = new PropertyDescriptor("HighestNumWaiters", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HighestNumWaiters", currentResult);
         currentResult.setValue("description", "<p>The highest number of threads waiting to retrieve a JMS session in this instance of the session pool since it was instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestWaitSeconds")) {
         getterName = "getHighestWaitSeconds";
         setterName = null;
         currentResult = new PropertyDescriptor("HighestWaitSeconds", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HighestWaitSeconds", currentResult);
         currentResult.setValue("description", "<p>The highest number of seconds that an application waited to retrieve a JMS session in this instance of the session pool since it was instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCapacity")) {
         getterName = "getMaxCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxCapacity", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxCapacity", currentResult);
         currentResult.setValue("description", "<p>The maximum number of JMS sessions that can be allocated using the session pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumAvailable")) {
         getterName = "getNumAvailable";
         setterName = null;
         currentResult = new PropertyDescriptor("NumAvailable", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumAvailable", currentResult);
         currentResult.setValue("description", "<p>The number of available JMS sessions in the session pool that are not currently in use.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumConnectionObjects")) {
         getterName = "getNumConnectionObjects";
         setterName = null;
         currentResult = new PropertyDescriptor("NumConnectionObjects", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumConnectionObjects", currentResult);
         currentResult.setValue("description", "<p>The number of JMS connections that back this session pool. This value may be greater than one if different sessions were created using different combinations of a username and password to contact the JMS server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumFailuresToRefresh")) {
         getterName = "getNumFailuresToRefresh";
         setterName = null;
         currentResult = new PropertyDescriptor("NumFailuresToRefresh", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumFailuresToRefresh", currentResult);
         currentResult.setValue("description", "<p>The number of failed attempts to create a JMS session in the session pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumLeaked")) {
         getterName = "getNumLeaked";
         setterName = null;
         currentResult = new PropertyDescriptor("NumLeaked", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumLeaked", currentResult);
         currentResult.setValue("description", "<p>The number of JMS sessions that were removed from the session pool, but were not returned.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumReserved")) {
         getterName = "getNumReserved";
         setterName = null;
         currentResult = new PropertyDescriptor("NumReserved", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumReserved", currentResult);
         currentResult.setValue("description", "<p>The number of JMS sessions that are currently in use.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumUnavailable")) {
         getterName = "getNumUnavailable";
         setterName = null;
         currentResult = new PropertyDescriptor("NumUnavailable", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumUnavailable", currentResult);
         currentResult.setValue("description", "<p>The number of JMS sessions in the session pool that are not currently usable for some reason.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumWaiters")) {
         getterName = "getNumWaiters";
         setterName = null;
         currentResult = new PropertyDescriptor("NumWaiters", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumWaiters", currentResult);
         currentResult.setValue("description", "<p>The number of threads waiting to retrieve a JMS session from the session pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalNumAllocated")) {
         getterName = "getTotalNumAllocated";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNumAllocated", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalNumAllocated", currentResult);
         currentResult.setValue("description", "<p>The total number of JMS sessions allocated by this session pool in this instance of the session pool since it was instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalNumDestroyed")) {
         getterName = "getTotalNumDestroyed";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNumDestroyed", JMSPooledConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalNumDestroyed", currentResult);
         currentResult.setValue("description", "<p>The total number of JMS sessions that were created and then destroyed in this instance of the session pool since it was instantiated.</p> ");
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
