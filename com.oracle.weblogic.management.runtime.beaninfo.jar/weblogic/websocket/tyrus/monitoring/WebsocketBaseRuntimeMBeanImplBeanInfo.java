package weblogic.websocket.tyrus.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WebsocketBaseRuntimeMBean;

public class WebsocketBaseRuntimeMBeanImplBeanInfo extends WebsocketMessageStatisticsRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebsocketBaseRuntimeMBean.class;

   public WebsocketBaseRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebsocketBaseRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.websocket.tyrus.monitoring.WebsocketBaseRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.websocket.tyrus.monitoring");
      String description = (new String("MBean containing properties shared between application and endpoint MBeans. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WebsocketBaseRuntimeMBean");
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
         currentResult = new PropertyDescriptor("AverageReceivedMessageSize", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageReceivedMessageSize", currentResult);
         currentResult.setValue("description", "Get the average size of all of the messages received since the start of monitoring. ");
      }

      if (!descriptors.containsKey("AverageSentMessageSize")) {
         getterName = "getAverageSentMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageSentMessageSize", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageSentMessageSize", currentResult);
         currentResult.setValue("description", "Get the average size of all of the messages sent since the start of monitoring. ");
      }

      if (!descriptors.containsKey("BinaryMessageStatisticsRuntimeMBean")) {
         getterName = "getBinaryMessageStatisticsRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("BinaryMessageStatisticsRuntimeMBean", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BinaryMessageStatisticsRuntimeMBean", currentResult);
         currentResult.setValue("description", "Get an MBean containing binary message statistics. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ControlMessageStatisticsRuntimeMBean")) {
         getterName = "getControlMessageStatisticsRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("ControlMessageStatisticsRuntimeMBean", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ControlMessageStatisticsRuntimeMBean", currentResult);
         currentResult.setValue("description", "Get an MBean containing control message statistics. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ErrorCounts")) {
         getterName = "getErrorCounts";
         setterName = null;
         currentResult = new PropertyDescriptor("ErrorCounts", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ErrorCounts", currentResult);
         currentResult.setValue("description", "Get the list of throwable class name and count pairs. These pairs represent errors and the number of times they have occurred. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for WebsocketErrorCount");
      }

      if (!descriptors.containsKey("MaximalOpenSessionsCount")) {
         getterName = "getMaximalOpenSessionsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximalOpenSessionsCount", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximalOpenSessionsCount", currentResult);
         currentResult.setValue("description", "Get the maximal number of open sessions since the start of monitoring. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximalReceivedMessageSize")) {
         getterName = "getMaximalReceivedMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximalReceivedMessageSize", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximalReceivedMessageSize", currentResult);
         currentResult.setValue("description", "Get the size of the largest message received since the start of monitoring. ");
      }

      if (!descriptors.containsKey("MaximalSentMessageSize")) {
         getterName = "getMaximalSentMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximalSentMessageSize", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximalSentMessageSize", currentResult);
         currentResult.setValue("description", "Get the size of the largest message sent since the start of monitoring. ");
      }

      if (!descriptors.containsKey("MinimalReceivedMessageSize")) {
         getterName = "getMinimalReceivedMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimalReceivedMessageSize", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimalReceivedMessageSize", currentResult);
         currentResult.setValue("description", "Get the size of the smallest message received since the start of monitoring. ");
      }

      if (!descriptors.containsKey("MinimalSentMessageSize")) {
         getterName = "getMinimalSentMessageSize";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimalSentMessageSize", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimalSentMessageSize", currentResult);
         currentResult.setValue("description", "Get the size of the smallest message sent since the start of monitoring. ");
      }

      if (!descriptors.containsKey("OpenSessionsCount")) {
         getterName = "getOpenSessionsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("OpenSessionsCount", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OpenSessionsCount", currentResult);
         currentResult.setValue("description", "Get the number of currently open sessions. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReceivedMessagesCount")) {
         getterName = "getReceivedMessagesCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReceivedMessagesCount", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReceivedMessagesCount", currentResult);
         currentResult.setValue("description", "Get the total number of messages received since the start of monitoring. ");
      }

      if (!descriptors.containsKey("ReceivedMessagesCountPerSecond")) {
         getterName = "getReceivedMessagesCountPerSecond";
         setterName = null;
         currentResult = new PropertyDescriptor("ReceivedMessagesCountPerSecond", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReceivedMessagesCountPerSecond", currentResult);
         currentResult.setValue("description", "Get the average number of received messages per second. ");
      }

      if (!descriptors.containsKey("SentMessagesCount")) {
         getterName = "getSentMessagesCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SentMessagesCount", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SentMessagesCount", currentResult);
         currentResult.setValue("description", "Get the total number of messages sent since the start of monitoring. ");
      }

      if (!descriptors.containsKey("SentMessagesCountPerSecond")) {
         getterName = "getSentMessagesCountPerSecond";
         setterName = null;
         currentResult = new PropertyDescriptor("SentMessagesCountPerSecond", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SentMessagesCountPerSecond", currentResult);
         currentResult.setValue("description", "Get the average number of sent messages per second. ");
      }

      if (!descriptors.containsKey("TextMessageStatisticsRuntimeMBean")) {
         getterName = "getTextMessageStatisticsRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("TextMessageStatisticsRuntimeMBean", WebsocketBaseRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TextMessageStatisticsRuntimeMBean", currentResult);
         currentResult.setValue("description", "Get an MBean containing text message statistics. ");
         currentResult.setValue("relationship", "containment");
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
