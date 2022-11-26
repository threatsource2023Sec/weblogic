package weblogic.websocket.tyrus.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WebsocketMessageStatisticsRuntimeMBean;

public class WebsocketMessageStatisticsRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebsocketMessageStatisticsRuntimeMBean.class;

   public WebsocketMessageStatisticsRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebsocketMessageStatisticsRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.websocket.tyrus.monitoring.WebsocketMessageStatisticsRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.websocket.tyrus.monitoring");
      String description = (new String("MBean used for exposing message-level statistics. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WebsocketMessageStatisticsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AverageReceivedMessageSize")) {
         getterName = "getAverageReceivedMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageReceivedMessageSize", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageReceivedMessageSize", currentResult);
         currentResult.setValue("description", "Get the average size of all of the messages received since the start of monitoring. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AverageSentMessageSize")) {
         getterName = "getAverageSentMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageSentMessageSize", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageSentMessageSize", currentResult);
         currentResult.setValue("description", "Get the average size of all of the messages sent since the start of monitoring. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximalReceivedMessageSize")) {
         getterName = "getMaximalReceivedMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximalReceivedMessageSize", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximalReceivedMessageSize", currentResult);
         currentResult.setValue("description", "Get the size of the largest message received since the start of monitoring. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximalSentMessageSize")) {
         getterName = "getMaximalSentMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximalSentMessageSize", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximalSentMessageSize", currentResult);
         currentResult.setValue("description", "Get the size of the largest message sent since the start of monitoring. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimalReceivedMessageSize")) {
         getterName = "getMinimalReceivedMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimalReceivedMessageSize", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimalReceivedMessageSize", currentResult);
         currentResult.setValue("description", "Get the size of the smallest message received since the start of monitoring. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimalSentMessageSize")) {
         getterName = "getMinimalSentMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimalSentMessageSize", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimalSentMessageSize", currentResult);
         currentResult.setValue("description", "Get the size of the smallest message sent since the start of monitoring. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReceivedMessagesCount")) {
         getterName = "getReceivedMessagesCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReceivedMessagesCount", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReceivedMessagesCount", currentResult);
         currentResult.setValue("description", "Get the total number of messages received since the start of monitoring. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReceivedMessagesCountPerSecond")) {
         getterName = "getReceivedMessagesCountPerSecond";
         setterName = null;
         currentResult = new PropertyDescriptor("ReceivedMessagesCountPerSecond", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReceivedMessagesCountPerSecond", currentResult);
         currentResult.setValue("description", "Get the average number of received messages per second. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SentMessagesCount")) {
         getterName = "getSentMessagesCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SentMessagesCount", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SentMessagesCount", currentResult);
         currentResult.setValue("description", "Get the total number of messages sent since the start of monitoring. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SentMessagesCountPerSecond")) {
         getterName = "getSentMessagesCountPerSecond";
         setterName = null;
         currentResult = new PropertyDescriptor("SentMessagesCountPerSecond", WebsocketMessageStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SentMessagesCountPerSecond", currentResult);
         currentResult.setValue("description", "Get the average number of sent messages per second. ");
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
