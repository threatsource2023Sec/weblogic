package weblogic.jms.frontend;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.JMSSessionRuntimeMBean;

public class FESessionBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSSessionRuntimeMBean.class;

   public FESessionBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public FESessionBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.frontend.FESession");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jms.frontend");
      String description = (new String("This class is used for monitoring a WebLogic JMS session. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSSessionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AcknowledgeMode")) {
         getterName = "getAcknowledgeMode";
         setterName = null;
         currentResult = new PropertyDescriptor("AcknowledgeMode", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AcknowledgeMode", currentResult);
         currentResult.setValue("description", "<p>The acknowledge mode as one of the following:</p>  <ul> <li><code>AUTO_ACKNOWLEDGE</code></li>  <li><code>CLIENT_ACKNOWLEDGE</code></li>  <li><code>DUPS_OK_ACKNOWLEDGE</code></li>  <li><code>NO_ACKNOWLEDGE</code></li> </ul> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesPendingCount")) {
         getterName = "getBytesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPendingCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes pending (uncommitted and unacknowledged) for this session.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesReceivedCount")) {
         getterName = "getBytesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesReceivedCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes received by this session since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesSentCount")) {
         getterName = "getBytesSentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesSentCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesSentCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes sent by this session since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Consumers")) {
         getterName = "getConsumers";
         setterName = null;
         currentResult = new PropertyDescriptor("Consumers", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Consumers", currentResult);
         currentResult.setValue("description", "<p>An array of consumers for this session.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumersCurrentCount")) {
         getterName = "getConsumersCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumersCurrentCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumersCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of consumers for this session.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumersHighCount")) {
         getterName = "getConsumersHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumersHighCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumersHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of consumers for this session since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumersTotalCount")) {
         getterName = "getConsumersTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumersTotalCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumersTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of consumers instantiated by this session since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesPendingCount")) {
         getterName = "getMessagesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPendingCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages pending (uncommitted and unacknowledged) for this session.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesReceivedCount")) {
         getterName = "getMessagesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesReceivedCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages received by this session since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesSentCount")) {
         getterName = "getMessagesSentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesSentCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesSentCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes sent by this session since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Producers")) {
         getterName = "getProducers";
         setterName = null;
         currentResult = new PropertyDescriptor("Producers", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Producers", currentResult);
         currentResult.setValue("description", "<p>An array of producers for this session.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProducersCurrentCount")) {
         getterName = "getProducersCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ProducersCurrentCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProducersCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of producers for this session.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProducersHighCount")) {
         getterName = "getProducersHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ProducersHighCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProducersHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of producers for this session since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProducersTotalCount")) {
         getterName = "getProducersTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ProducersTotalCount", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProducersTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of producers for this session since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Transacted")) {
         getterName = "isTransacted";
         setterName = null;
         currentResult = new PropertyDescriptor("Transacted", JMSSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Transacted", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the session is transacted.</p> ");
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
