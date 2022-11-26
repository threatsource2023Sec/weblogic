package weblogic.jms.backend;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import weblogic.management.configuration.JMSConstants;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JMSDurableSubscriberRuntimeMBean;

public class BEDurableSubscriberRuntimeMBeanImplBeanInfo extends BEMessageManagementRuntimeDelegateBeanInfo {
   public static final Class INTERFACE_CLASS = JMSDurableSubscriberRuntimeMBean.class;

   public BEDurableSubscriberRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BEDurableSubscriberRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.backend.BEDurableSubscriberRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jms.backend");
      String description = (new String("This class is used for monitoring a WebLogic JMS durable subscriber. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSDurableSubscriberRuntimeMBean");
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
         currentResult = new PropertyDescriptor("BytesCurrentCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes received by this durable subscriber.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesPendingCount")) {
         getterName = "getBytesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPendingCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes pending by this durable subscriber.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientID")) {
         getterName = "getClientID";
         setterName = null;
         currentResult = new PropertyDescriptor("ClientID", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClientID", currentResult);
         currentResult.setValue("description", "<p>A unique client identifier for this durable subscriber.</p>  <p><b>Note:</b> The client ID is not necessarily equivalent to the WebLogic Server username; that is, a name used to authenticate a user in the WebLogic security realm. You can set the client ID to the WebLogic Server username if it is appropriate for your JMS application.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientIDPolicy")) {
         getterName = "getClientIDPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("ClientIDPolicy", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClientIDPolicy", currentResult);
         currentResult.setValue("description", "<p> The policy for the client identifier for this durable subscriber.</p>  <p><b>Note:</b> The client ID policy is either <code>Restricted</code> or <code>Unrestricted</code>. </p> ");
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("CurrentConsumerInfo")) {
         getterName = "getCurrentConsumerInfo";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentConsumerInfo", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentConsumerInfo", currentResult);
         currentResult.setValue("description", "<p>Specifies information about the current consumer.  The information is returned in the form of an OpenMBean CompositeData object.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.jms.extensions.ConsumerInfo")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      if (!descriptors.containsKey("DestinationInfo")) {
         getterName = "getDestinationInfo";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationInfo", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationInfo", currentResult);
         currentResult.setValue("description", "<p> Specifies information about the durable subscriber's internal destination in JMX open data representation. The resulting object is intended for use in the message management APIs for identifying a target destination. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSMessageManagementRuntimeMBean#moveMessages(String, CompositeData)"), BeanInfoHelper.encodeEntities("JMSMessageManagementRuntimeMBean#moveMessages(String, CompositeData, Integer)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      if (!descriptors.containsKey("DestinationRuntime")) {
         getterName = "getDestinationRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationRuntime", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationRuntime", currentResult);
         currentResult.setValue("description", "<p>The runtime MBean of the Topic to which this durable subscriber is associated.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastMessagesReceivedTime")) {
         getterName = "getLastMessagesReceivedTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastMessagesReceivedTime", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastMessagesReceivedTime", currentResult);
         currentResult.setValue("description", "<p> The time when the last time a subscriber received a message from the subscription. The returned value is a standard java absolute time, which is measured in milliseconds since midnight, January 1, 1970 UTC </p>  This returns the JMS durable subscription boot time if there were no messages that were successfully delivered to any subscriber on this subscription since the subscription was booted. We define the boot time of a durable subscription to be the time the subscription is originally created or recovered during a server reboot or jms migration, which ever is latest. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesCurrentCount")) {
         getterName = "getMessagesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesCurrentCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages still available by this durable subscriber.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesDeletedCurrentCount")) {
         getterName = "getMessagesDeletedCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesDeletedCurrentCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesDeletedCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of messages that have been deleted from the destination.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#deleteMessages(String)")};
         currentResult.setValue("see", seeObjectArray);
      }

      if (!descriptors.containsKey("MessagesHighCount")) {
         getterName = "getMessagesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesHighCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of messages for the durable subscriber since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesMovedCurrentCount")) {
         getterName = "getMessagesMovedCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesMovedCurrentCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesMovedCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of messages that have been moved from the destination.</p> ");
      }

      if (!descriptors.containsKey("MessagesPendingCount")) {
         getterName = "getMessagesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPendingCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages pending (uncommitted and unacknowledged) by this durable subscriber.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesReceivedCount")) {
         getterName = "getMessagesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesReceivedCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages received by the durable subscriber since that reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Selector")) {
         getterName = "getSelector";
         setterName = null;
         currentResult = new PropertyDescriptor("Selector", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Selector", currentResult);
         currentResult.setValue("description", "<p>The message selector defined for this durable subscriber.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscribersCurrentCount")) {
         getterName = "getSubscribersCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SubscribersCurrentCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubscribersCurrentCount", currentResult);
         currentResult.setValue("description", "The number of subscribers that currently share this subscription. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscribersHighCount")) {
         getterName = "getSubscribersHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SubscribersHighCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubscribersHighCount", currentResult);
         currentResult.setValue("description", "The highest number of subscribers that have shared this subscription at the same time since the creation or the last reboot of the subscription, which ever is later. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscribersTotalCount")) {
         getterName = "getSubscribersTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SubscribersTotalCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubscribersTotalCount", currentResult);
         currentResult.setValue("description", "The total number of subscribers that have accessed this subscription since the creation or the last reboot of the subscription, whichever is later. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscriptionLimitDeletedCount")) {
         getterName = "getSubscriptionLimitDeletedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SubscriptionLimitDeletedCount", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubscriptionLimitDeletedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages deleted because of a subscription limit.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscriptionName")) {
         getterName = "getSubscriptionName";
         setterName = null;
         currentResult = new PropertyDescriptor("SubscriptionName", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubscriptionName", currentResult);
         currentResult.setValue("description", "<p>The subscription name for this durable subscriber. This name must be unique for each client ID.</p>  <p>Valid durable subscription names cannot include the following characters: comma \",\", equals \"=\", colon \":\", asterisk \"*\", percent \"%\", or question mark\"?\".</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubscriptionSharingPolicy")) {
         getterName = "getSubscriptionSharingPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("SubscriptionSharingPolicy", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubscriptionSharingPolicy", currentResult);
         currentResult.setValue("description", "The SubscriptionSharingPolicy on this subscriber. ");
         setPropertyDescriptorDefault(currentResult, JMSConstants.SUBSCRIPTION_EXCLUSIVE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Active")) {
         getterName = "isActive";
         setterName = null;
         currentResult = new PropertyDescriptor("Active", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Active", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this subscription is being used by a durable subscriber.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NoLocal")) {
         getterName = "isNoLocal";
         setterName = null;
         currentResult = new PropertyDescriptor("NoLocal", JMSDurableSubscriberRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NoLocal", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this durable subscriber receives local messages that it has published.</p>  <p>To prevent this, set the <code>noLocal</code> parameter to <code>true</code>.</p> ");
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
      Method mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getCursorStartPosition", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor start position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getMessage", String.class, String.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("sort", String.class, Long.class, String[].class, Boolean[].class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getCursorEndPosition", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor end position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getMessage", String.class, Long.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getItems", String.class, Long.class, Integer.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getMessages", String.class, Integer.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getMessages", String.class, Integer.class, Integer.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getNext", String.class, Integer.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getMessage", String.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getPrevious", String.class, Integer.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("getCursorSize", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the number of items in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("moveMessages", String.class, CompositeData.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("closeCursor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Releases the server-side resources associated with the cursor and removes the runtime MBean instance.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("moveMessages", String.class, CompositeData.class, Integer.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("deleteMessages", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("selector", "A JMS message selector to identify which messages to  delete. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the set of messages from the destination that are qualified by the specified JMS message selector.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("importMessages", CompositeData[].class, Boolean.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("importMessages", CompositeData[].class, Boolean.class, Boolean.class);
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

      mth = JMSDurableSubscriberRuntimeMBean.class.getMethod("purge");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "As of WLS 9.0.  Use {@link #deleteMessages(String)} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Purges all the pending and current messages for this durable subscriber.</p> ");
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
