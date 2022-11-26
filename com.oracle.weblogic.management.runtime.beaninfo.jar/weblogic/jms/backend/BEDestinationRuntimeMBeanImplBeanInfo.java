package weblogic.jms.backend;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JMSDestinationRuntimeMBean;
import weblogic.management.runtime.JMSDurableSubscriberRuntimeMBean;

public class BEDestinationRuntimeMBeanImplBeanInfo extends BEMessageManagementRuntimeDelegateBeanInfo {
   public static final Class INTERFACE_CLASS = JMSDestinationRuntimeMBean.class;

   public BEDestinationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BEDestinationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.backend.BEDestinationRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jms.backend");
      String description = (new String("<p>This class is used for monitoring a WebLogic JMS destination (topic or queue).</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSDestinationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("BytesCurrentCount")) {
         getterName = "getBytesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesCurrentCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of bytes stored in the destination. This does not include the pending bytes.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesHighCount")) {
         getterName = "getBytesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesHighCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of bytes stored in the destination since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesPendingCount")) {
         getterName = "getBytesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPendingCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The number of pending bytes stored in the destination.</p>  <p>Pending bytes are over and above the current number of bytes.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesReceivedCount")) {
         getterName = "getBytesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesReceivedCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes received in this destination since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesThresholdTime")) {
         getterName = "getBytesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesThresholdTime", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>The amount of time in the threshold condition since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumersCurrentCount")) {
         getterName = "getConsumersCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumersCurrentCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumersCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of consumers accessing this destination.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumersHighCount")) {
         getterName = "getConsumersHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumersHighCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumersHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of consumers accessing this destination since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumersTotalCount")) {
         getterName = "getConsumersTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumersTotalCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumersTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of consumers accessing this destination since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumptionPausedState")) {
         getterName = "getConsumptionPausedState";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumptionPausedState", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumptionPausedState", currentResult);
         currentResult.setValue("description", "<p>The current consumption pause state of the destination.</p> ");
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("DestinationInfo")) {
         getterName = "getDestinationInfo";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationInfo", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationInfo", currentResult);
         currentResult.setValue("description", "<p> Returns information about this destination in JMX open data representation.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.jms.extensions.DestinationInfo")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      if (!descriptors.containsKey("DestinationType")) {
         getterName = "getDestinationType";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationType", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationType", currentResult);
         currentResult.setValue("description", "<p>The destination type, either <code>weblogic.management.configuration.JMSConstants.DESTINATION_TYPE_QUEUE</code> or <code>weblogic.management.configuration.JMSConstants.DESTINATION_TYPE_TOPIC</code> depending upon whether the destination is a queue or topic.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DurableSubscribers")) {
         getterName = "getDurableSubscribers";
         setterName = null;
         currentResult = new PropertyDescriptor("DurableSubscribers", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DurableSubscribers", currentResult);
         currentResult.setValue("description", "<p>An array of durable subscriber run-time MBeans for this destination.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InsertionPausedState")) {
         getterName = "getInsertionPausedState";
         setterName = null;
         currentResult = new PropertyDescriptor("InsertionPausedState", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InsertionPausedState", currentResult);
         currentResult.setValue("description", "<p>The current insertion pause state of the destination.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSDurableSubscriberRuntimes")) {
         getterName = "getJMSDurableSubscriberRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSDurableSubscriberRuntimes", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JMSDurableSubscriberRuntimes", currentResult);
         currentResult.setValue("description", "<p>An array of durable subscriber run-time MBeans for this destination.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesCurrentCount")) {
         getterName = "getMessagesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesCurrentCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of messages in the destination. This does not include the pending messages.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesDeletedCurrentCount")) {
         getterName = "getMessagesDeletedCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesDeletedCurrentCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesDeletedCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of messages that have been deleted from the destination.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#deleteMessages(String)")};
         currentResult.setValue("see", seeObjectArray);
      }

      if (!descriptors.containsKey("MessagesHighCount")) {
         getterName = "getMessagesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesHighCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of messages in the destination since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesMovedCurrentCount")) {
         getterName = "getMessagesMovedCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesMovedCurrentCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesMovedCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of messages that have been moved from the destination.</p> ");
      }

      if (!descriptors.containsKey("MessagesPendingCount")) {
         getterName = "getMessagesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPendingCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The number of pending messages in the destination.</p>  <p>Pending messages are over and above the current number of messages. A pending message is one that has either been sent in a transaction and not committed, or that has been received and not committed or acknowledged.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesReceivedCount")) {
         getterName = "getMessagesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesReceivedCount", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages received in this destination since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesThresholdTime")) {
         getterName = "getMessagesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesThresholdTime", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>The amount of time in the threshold condition since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProductionPausedState")) {
         getterName = "getProductionPausedState";
         setterName = null;
         currentResult = new PropertyDescriptor("ProductionPausedState", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProductionPausedState", currentResult);
         currentResult.setValue("description", "<p>The current production pause state of the destination.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The operational state of the destination as a String.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscriptionMessagesLimit")) {
         getterName = "getSubscriptionMessagesLimit";
         setterName = null;
         currentResult = new PropertyDescriptor("SubscriptionMessagesLimit", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubscriptionMessagesLimit", currentResult);
         currentResult.setValue("description", "<p>The effective messages limit on topic subscriptions (not applicable to queues). A '-1' indicates that no limit is configured or active.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumptionPaused")) {
         getterName = "isConsumptionPaused";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumptionPaused", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumptionPaused", currentResult);
         currentResult.setValue("description", "<p>Indicates the consumption Pause state of the destination.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InsertionPaused")) {
         getterName = "isInsertionPaused";
         setterName = null;
         currentResult = new PropertyDescriptor("InsertionPaused", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InsertionPaused", currentResult);
         currentResult.setValue("description", "<p>Indicates the InsertionPause state of the destination.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Paused")) {
         getterName = "isPaused";
         setterName = null;
         currentResult = new PropertyDescriptor("Paused", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Paused", currentResult);
         currentResult.setValue("description", "<p>Indicates whether or not the destination is paused at the current time.</p> ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.JMSDestinationRuntimeMBean#isProductionPaused} ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProductionPaused")) {
         getterName = "isProductionPaused";
         setterName = null;
         currentResult = new PropertyDescriptor("ProductionPaused", JMSDestinationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProductionPaused", currentResult);
         currentResult.setValue("description", "<p>Indicates the ProductionPause state of the destination.</p> ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSDestinationRuntimeMBean.class.getMethod("destroyJMSDurableSubscriberRuntime", JMSDurableSubscriberRuntimeMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "to destroy ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>destroy a durable subscriber</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSDurableSubscriberRuntimes");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSDestinationRuntimeMBean.class.getMethod("getCursorStartPosition", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor start position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getMessage", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The handle of the cursor. "), createParameterDescriptor("messageID", "The JMS message ID of the requested message. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] seeObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Given a JMS message ID this method returns the corresponding message from the queue. If no message with the specified message ID exists on the destination, a null value is returned.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.management.openmbean.CompositeData")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("sort", String.class, Long.class, String[].class, Boolean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The handle of the cursor. "), createParameterDescriptor("start", "The location of the message before the sort that will be the first message returned after the sort.  A value of -1 will place the  cursor start position at the head of the new sort order. "), createParameterDescriptor("fields", "The JMS header attributes on which to sort. "), createParameterDescriptor("ascending", "Determines whether the sort of the corresponding fields element is in ascending or descending order. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] seeObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException Thrown when an internal JMS error occurs while processing the request.")};
         currentResult.setValue("throws", seeObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Sorts the entire message result set managed by the cursor according to the JMS header attributes specified. The cursor position is set to the new position of the message corresponding to the \"start\" location before the sort is performed. The method returns the new cursor position.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.management.openmbean.CompositeData")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getCursorEndPosition", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor end position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getMessage", String.class, Long.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The handle of the cursor. "), createParameterDescriptor("messageHandle", "The handle of the message within the cursor. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException Thrown when an error occurs while performing the  operation.")};
         currentResult.setValue("throws", seeObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the message associated with the specified cursor handle.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.management.openmbean.CompositeData")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("createDurableSubscriber", String.class, String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("ClientID", "An identifier that uniquely identifies a client connection. "), createParameterDescriptor("subscriptionName", "The name used to identify this subscription. "), createParameterDescriptor("selector", "Only messages with properties matching the message selector expression are delivered. A value of null or an empty string indicates that there is no message selector for the message consumer. "), createParameterDescriptor("noLocal", "If set, inhibits the delivery of messages published by its own connection. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a durable subscriber on the destination with the specified client ID and subscription name. A message selector and no-local flag may also be specified.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getItems", String.class, Long.class, Integer.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. "), createParameterDescriptor("start", "The new cursor start location. "), createParameterDescriptor("count", "The maximum number of items to return. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns an array of items from the specified cursor location. The new cursor start position will be the location after the old cursor end position. The size of the array returned is determined by the count argument. An array smaller than the \"count\" value is returned if there are fewer items from the specified start position to the end of the result set. A null value is returned if the size of the return array is zero. In this case, the cursor position will not change.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.management.openmbean.CompositeData")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getMessages", String.class, Integer.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("selector", "A valid JMS message selector. "), createParameterDescriptor("timeout", "The last access timeout for the cursor.  The cursor  resources will be reclaimed if it is not accessed within the specified  time interval.  A value of 0 indicates no timeout. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Queries messages on the queue according to the provided message selector and returns a message cursor representing the result set. The timeout parameter specifies the amount of time in seconds for which the cursor is valid. Upon timeout expiration the cursor is invalidated and the associated resources released.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSMessageCursorRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getMessages", String.class, Integer.class, Integer.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("selector", "A valid JMS message selector. "), createParameterDescriptor("timeout", "The last access timeout for the cursor.  The cursor  resources will be reclaimed if it is not accessed within the specified  time interval.  A value of 0 indicates no timeout. "), createParameterDescriptor("state", "A messaging kernel state bitmask.  Refer to the messaging kernel MessageElement interface for a description of the various message states. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Queries messages on the queue according to the provided message selector and state bitmask and returns a message cursor representing the result set. The timeout parameter specifies the amount of time in seconds for which the cursor is valid. Upon timeout expiration the cursor is invalidated and the associated resources released.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSMessageCursorRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getNext", String.class, Integer.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. "), createParameterDescriptor("count", "The maximum number of items to return. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns an array of items starting from the current cursor end position.  The new cursor start position is set to be the location of the first item returned to the caller. The new cursor end position is set according to the size of the array returned, which is determined by the count argument. An array smaller than the \"count\" value is returned if there are fewer items from the specified start position to the end of the result set. A null value is returned if the size of the array is zero. In this case, the cursor position will not change.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.management.openmbean.CompositeData")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getMessage", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("messageID", "The JMS message ID of the requested message. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Given a JMS message ID this method returns the corresponding message from the queue. If no message with the specified message ID exists on the destination, a null value is returned.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getPrevious", String.class, Integer.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. "), createParameterDescriptor("count", "The maximum number of item to return. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns an array of items up to the current cursor start position. The new start position will be placed at the location of the first item in the set returned to the caller. The new cursor end position will be placed at the location after the last item in the set that is returned.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.management.openmbean.CompositeData")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("getCursorSize", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the number of items in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("moveMessages", String.class, CompositeData.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("selector", "A JMS message selector that identifies the messages  to move. "), createParameterDescriptor("targetDestination", "A JMS destination that the messages will  be moved to. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Moves the set of messages that match the specified selector to the target destination. The move operation is guaranteed to be atomic for the selected messages.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("closeCursor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Releases the server-side resources associated with the cursor and removes the runtime MBean instance.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("moveMessages", String.class, CompositeData.class, Integer.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("deleteMessages", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("selector", "A JMS message selector to identify which messages to  delete. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the set of messages from the destination that are qualified by the specified JMS message selector.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("importMessages", CompositeData[].class, Boolean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("messages", "An array of messages in CompositeData representation to be imported. "), createParameterDescriptor("replaceOnly", "When set to true an excetion will be thrown if the message ID does not exist on the target destination. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Imports an array of messages into the destination. If the message ID of the message being imported matches a message already on the destination, then the existing message will be replaced. If an existing message does not exist, then the message will be produced on the destination. A produced message is subject to quota limitations.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("importMessages", CompositeData[].class, Boolean.class, Boolean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("messages", "An array of messages in CompositeData representation to be imported. "), createParameterDescriptor("replaceOnly", "When set to true an excetion will be thrown if the message ID does not exist on the target destination. "), createParameterDescriptor("applyOverrides", "when set to true will apply destination overrides to the imported messages ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Imports an array of messages into the destination. If the message ID of the message being imported matches a message already on the destination, then the existing message will be replaced. If an existing message does not exist, then the message will be produced on the destination. A produced message is subject to quota limitations.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("pause");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.JMSDestinationRuntimeMBean#pauseProduction} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the destination so that new messages are not accepted.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("resume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.JMSDestinationRuntimeMBean#resumeProduction} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the destination so that new messages are accepted.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("pauseProduction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the production on the destination.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("resumeProduction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the production operations on the destination.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("pauseInsertion");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the insertion on the destination.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("resumeInsertion");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the insertion operations on the destination.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("pauseConsumption");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the consumption on the destination.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("resumeConsumption");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the consumption operations on the destination.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("lowMemory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Simulate a low memory event for the subject destination.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("normalMemory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Simulate a normal memory event for the subject destination.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = JMSDestinationRuntimeMBean.class.getMethod("mydelete");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>status of the message logging.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
