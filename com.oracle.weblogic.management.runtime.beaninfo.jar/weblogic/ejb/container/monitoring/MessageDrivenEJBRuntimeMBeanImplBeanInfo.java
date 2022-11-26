package weblogic.ejb.container.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;

public class MessageDrivenEJBRuntimeMBeanImplBeanInfo extends EJBRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MessageDrivenEJBRuntimeMBean.class;

   public MessageDrivenEJBRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MessageDrivenEJBRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.monitoring.MessageDrivenEJBRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.ejb.container.monitoring");
      String description = (new String("This interface contains accessor methods for all EJB runtime information collected for a Message Driven Bean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.MessageDrivenEJBRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ConnectionStatus")) {
         getterName = "getConnectionStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionStatus", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionStatus", currentResult);
         currentResult.setValue("description", "<p>Provides the connection status for the Message Driven Bean. ConnectionStatus can be Connected or Reconnecting.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Destination")) {
         getterName = "getDestination";
         setterName = null;
         currentResult = new PropertyDescriptor("Destination", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Destination", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the Message Driven Bean destination</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EJBName")) {
         getterName = "getEJBName";
         setterName = null;
         currentResult = new PropertyDescriptor("EJBName", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EJBName", currentResult);
         currentResult.setValue("description", "<p>Provides the name for this EJB as defined in the javax.ejb.EJB annotation, or the ejb-name when * using the ejb-jar.xml deployment descriptor.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The health state of this MDB.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsClientID")) {
         getterName = "getJmsClientID";
         setterName = null;
         currentResult = new PropertyDescriptor("JmsClientID", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JmsClientID", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the Message Driven Bean jmsClientID</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastException")) {
         getterName = "getLastException";
         setterName = null;
         currentResult = new PropertyDescriptor("LastException", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastException", currentResult);
         currentResult.setValue("description", "<p>Provides the last exception this MDB encountered</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastExceptionAsString")) {
         getterName = "getLastExceptionAsString";
         setterName = null;
         currentResult = new PropertyDescriptor("LastExceptionAsString", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastExceptionAsString", currentResult);
         currentResult.setValue("description", "<p>Provides the last exception as String this MDB encountered</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MDBStatus")) {
         getterName = "getMDBStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("MDBStatus", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MDBStatus", currentResult);
         currentResult.setValue("description", "<p>Provides the Message Driven Bean status.  MDBStatus is used after the MDB is connected to the destination. MDBStatus can be Running or Suspended.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolRuntime")) {
         getterName = "getPoolRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolRuntime", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides runtime information about the free pool for this EJB.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProcessedMessageCount")) {
         getterName = "getProcessedMessageCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ProcessedMessageCount", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProcessedMessageCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of messages processed by this Message Driven Bean.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Resources")) {
         getterName = "getResources";
         setterName = null;
         currentResult = new PropertyDescriptor("Resources", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Resources", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the RuntimeMBeans for the resources used by this EJB. This will always include an ExecuteQueueRuntimeMBean. It will also include a JMSDestinationRuntimeMBean for message-driven beans and a JDBCConnectionPoolMBean for CMP entity beans.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("SuspendCount")) {
         getterName = "getSuspendCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SuspendCount", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuspendCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of times this MDB is suspended by the user or the EJB container.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimerRuntime")) {
         getterName = "getTimerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("TimerRuntime", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TimerRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides runtime information about any EJB Timers created, for this EJB. If the bean class for this EJB does not implement javax.ejb.TimedObject, null will be returned.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionRuntime")) {
         getterName = "getTransactionRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRuntime", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides the EJBTransactionRuntimeMBean, containing the run-time transaction counts for this EJB.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("JMSConnectionAlive")) {
         getterName = "isJMSConnectionAlive";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSConnectionAlive", MessageDrivenEJBRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JMSConnectionAlive", currentResult);
         currentResult.setValue("description", "<p>Provides information about whether the Message Driven Bean is currently connected to the JMS destination it is mapped to.</p>  <p>Returns whether the Message Driven Bean is currently connected to the JMS destination it is mapped to.</p> ");
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
      Method mth = MessageDrivenEJBRuntimeMBean.class.getMethod("suspend");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends the specific type of MDB by calling stop on the JMS Connection.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MessageDrivenEJBRuntimeMBean.class.getMethod("scheduleSuspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends the specific type of MDB asynchronously by calling stop on the JMS Connection. check MDBStatus to ensure mdb is suspended</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MessageDrivenEJBRuntimeMBean.class.getMethod("resume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the specific type of MDB by calling start on the JMS Connection.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MessageDrivenEJBRuntimeMBean.class.getMethod("scheduleResume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the specific type of MDB asynchronously by calling start on the JMS Connection. check MDBStatus to ensure mdb is resumed</p> ");
         currentResult.setValue("role", "operation");
      }

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
