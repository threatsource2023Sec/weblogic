package weblogic.messaging.saf.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.jms.saf.SAFRemoteEndpointCustomizerBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WSRMRemoteEndpointRuntimeMBean;

public class RemoteEndpointRuntimeDelegateBeanInfo extends SAFRemoteEndpointCustomizerBeanInfo {
   public static final Class INTERFACE_CLASS = WSRMRemoteEndpointRuntimeMBean.class;

   public RemoteEndpointRuntimeDelegateBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RemoteEndpointRuntimeDelegateBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.messaging.saf.internal.RemoteEndpointRuntimeDelegate");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.messaging.saf.internal");
      String description = (new String("This class is used for monitoring a WebLogic SAF remote endpoint for WebServices Reliable Messaging. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WSRMRemoteEndpointRuntimeMBean");
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
         currentResult = new PropertyDescriptor("BytesCurrentCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the current number of bytes. This number does not include the pending bytes.</p> ");
      }

      if (!descriptors.containsKey("BytesHighCount")) {
         getterName = "getBytesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesHighCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesHighCount", currentResult);
         currentResult.setValue("description", "<p>Returns the peak number of bytes since the last reset.</p> ");
      }

      if (!descriptors.containsKey("BytesPendingCount")) {
         getterName = "getBytesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPendingCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPendingCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of pending bytes. Pending bytes are over and above the current number of bytes.</p> ");
      }

      if (!descriptors.containsKey("BytesReceivedCount")) {
         getterName = "getBytesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesReceivedCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes received since the last reset.</p> ");
      }

      if (!descriptors.containsKey("BytesThresholdTime")) {
         getterName = "getBytesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesThresholdTime", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>Returns the amount of time in the threshold condition since the last reset.</p> ");
      }

      if (!descriptors.containsKey("Conversations")) {
         getterName = "getConversations";
         setterName = null;
         currentResult = new PropertyDescriptor("Conversations", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Conversations", currentResult);
         currentResult.setValue("description", "<p>Returns the list of SAFConversationRuntimeMBean instances</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConversationsCurrentCount")) {
         getterName = "getConversationsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConversationsCurrentCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConversationsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the current number of conversations</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConversationsHighCount")) {
         getterName = "getConversationsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConversationsHighCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConversationsHighCount", currentResult);
         currentResult.setValue("description", "<p>Returns the peak number of conversations since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConversationsTotalCount")) {
         getterName = "getConversationsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConversationsTotalCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConversationsTotalCount", currentResult);
         currentResult.setValue("description", "<p>Returns the total number of conversations since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DowntimeHigh")) {
         getterName = "getDowntimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("DowntimeHigh", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DowntimeHigh", currentResult);
         currentResult.setValue("description", "<p>Specifies the longest time, in seconds, that the remote endpoint has not been available since the last reset.</p> ");
      }

      if (!descriptors.containsKey("DowntimeTotal")) {
         getterName = "getDowntimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("DowntimeTotal", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DowntimeTotal", currentResult);
         currentResult.setValue("description", "<p>Specifies the total time, in seconds, that the remote endpoint has not been available since the last reset.</p> ");
      }

      if (!descriptors.containsKey("EndpointType")) {
         getterName = "getEndpointType";
         setterName = null;
         currentResult = new PropertyDescriptor("EndpointType", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EndpointType", currentResult);
         currentResult.setValue("description", "<p>Specifies if the remote endpoint is a JMS or Web Services (WSRM) destination. The possible values are: weblogic.management.runtime.SAFConstants.JMS_ENDPOINT or weblogic.management.runtime.SAFConstants.WS_ENDPOINT.</p> ");
      }

      if (!descriptors.containsKey("FailedMessagesTotal")) {
         getterName = "getFailedMessagesTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedMessagesTotal", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedMessagesTotal", currentResult);
         currentResult.setValue("description", "<p>Returns the total number of messages that have failed to be forwarded since the last reset.</p> ");
      }

      if (!descriptors.containsKey("LastException")) {
         getterName = "getLastException";
         setterName = null;
         currentResult = new PropertyDescriptor("LastException", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastException", currentResult);
         currentResult.setValue("description", "<p>Specifies the exception thrown when message forwarding failed.</p> ");
      }

      if (!descriptors.containsKey("LastTimeConnected")) {
         getterName = "getLastTimeConnected";
         setterName = null;
         currentResult = new PropertyDescriptor("LastTimeConnected", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastTimeConnected", currentResult);
         currentResult.setValue("description", "<p>Specifies the last time that the remote endpoint was connected.</p> ");
      }

      if (!descriptors.containsKey("LastTimeFailedToConnect")) {
         getterName = "getLastTimeFailedToConnect";
         setterName = null;
         currentResult = new PropertyDescriptor("LastTimeFailedToConnect", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastTimeFailedToConnect", currentResult);
         currentResult.setValue("description", "<p>Specifies the last time that the remote endpoint failed to be connected.</p> ");
      }

      if (!descriptors.containsKey("MessagesCurrentCount")) {
         getterName = "getMessagesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesCurrentCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the current number of messages. This number includes the pending messages.</p> ");
      }

      if (!descriptors.containsKey("MessagesHighCount")) {
         getterName = "getMessagesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesHighCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesHighCount", currentResult);
         currentResult.setValue("description", "<p>Returns the peak number of messages since the last reset.</p> ");
      }

      if (!descriptors.containsKey("MessagesPendingCount")) {
         getterName = "getMessagesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPendingCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPendingCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of pending messages. Pending messages are over and above the current number of messages. A pending message is one that has either been sent in a transaction and not committed, or been forwarded but has not been acknowledged.</p> ");
      }

      if (!descriptors.containsKey("MessagesReceivedCount")) {
         getterName = "getMessagesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesReceivedCount", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages received since the last reset.</p> ");
      }

      if (!descriptors.containsKey("MessagesThresholdTime")) {
         getterName = "getMessagesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesThresholdTime", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>Returns the amount of time in the threshold condition since the last reset.</p> ");
      }

      if (!descriptors.containsKey("OperationState")) {
         getterName = "getOperationState";
         setterName = null;
         currentResult = new PropertyDescriptor("OperationState", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OperationState", currentResult);
         currentResult.setValue("description", "<p>Specifies the state of the most recent <code>ExireAll</code> operation.</p> ");
      }

      if (!descriptors.containsKey("URL")) {
         getterName = "getURL";
         setterName = null;
         currentResult = new PropertyDescriptor("URL", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("URL", currentResult);
         currentResult.setValue("description", "<p>The URL of the remote endpoint.</p> ");
      }

      if (!descriptors.containsKey("UptimeHigh")) {
         getterName = "getUptimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("UptimeHigh", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UptimeHigh", currentResult);
         currentResult.setValue("description", "<p>Specifies the longest time, in seconds, that the remote endpoint has been available since the last reset.</p> ");
      }

      if (!descriptors.containsKey("UptimeTotal")) {
         getterName = "getUptimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("UptimeTotal", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UptimeTotal", currentResult);
         currentResult.setValue("description", "<p>Specifies the total time, in seconds, that the remote endpoint has been available since the last reset.</p> ");
      }

      if (!descriptors.containsKey("PausedForForwarding")) {
         getterName = "isPausedForForwarding";
         setterName = null;
         currentResult = new PropertyDescriptor("PausedForForwarding", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PausedForForwarding", currentResult);
         currentResult.setValue("description", "<p>Indicates if the remote endpoint is currently not forwarding messages.</p> ");
      }

      if (!descriptors.containsKey("PausedForIncoming")) {
         getterName = "isPausedForIncoming";
         setterName = null;
         currentResult = new PropertyDescriptor("PausedForIncoming", WSRMRemoteEndpointRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PausedForIncoming", currentResult);
         currentResult.setValue("description", "<p>Indicates if a remote endpoint is currently not accepting new messages.</p> ");
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
      Method mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("closeConversations", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("conversationName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Forcefully closes all outstanding conversations whose name contains the given name.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("getCursorStartPosition", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor start position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("getMessage", String.class, String.class);
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

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("sort", String.class, Long.class, String[].class, Boolean[].class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The handle of the cursor on which to perform the sort operation "), createParameterDescriptor("start", "The location of the message before the sort that will be  the first message returned after the sort.  A value of -1 will place the  cursor start position at the head of the new sort order. "), createParameterDescriptor("fields", "The SAF header attributes on which to sort. "), createParameterDescriptor("ascending", "Determines whether the sort of the corresponding fields  element is in ascending or descending order. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException Thrown when an internal error occurs while  processing the request.")};
         currentResult.setValue("throws", seeObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Sorts the entire message result set managed by the cursor according to the SAF header attributes specified. The cursor position is set to the new position of the message corresponding to the \"start\" location before the sort is performed. The method returns the new cursor position.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("getCursorEndPosition", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor end position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("getMessage", String.class, Long.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The handle of the cursor. "), createParameterDescriptor("messageHandle", "The handle of the message within the cursor. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException Thrown when an error occurs while performing the  operation.")};
         currentResult.setValue("throws", seeObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the message associated with the specified cursor handle.</p> ");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.management.openmbean.CompositeData")};
         currentResult.setValue("see", roleObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("getItems", String.class, Long.class, Integer.class);
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

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("getNext", String.class, Integer.class);
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

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("pauseIncoming");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses a remote endpoint so that new messages are not accepted.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("getPrevious", String.class, Integer.class);
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

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("resumeIncoming");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes a remote endpoint so that new messages are accepted.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("getCursorSize", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the number of items in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("closeCursor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Releases the server-side resources associated with the cursor and removes the runtime MBean instance.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("pauseForwarding");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the forwarding of messages for a remote endpoint. The agent accepts new messages but does not forward them.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("resumeForwarding");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the forwarding of messages for the remote endpoint.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("expireAll");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>All pending messages for a remote destination are processed according to the policy specified by the associated Error Handling configuration and then removed.</p>  <ul> <li><p>When selected, <code>expireAll</code> is performed asynchronously by the server.</p></li>  <li><p>Oracle recommends that the remote endpoint is paused for incoming messages prior to expiring messages. When all pending messages are processed and removed, the remote endpoint can be set to resume and accept new messages.</p></li>  <li><p>The state of the <code>expireAll</code> operation can be determined by the <code>getOperationState</code> method.</p></li> </ul> ");
         currentResult.setValue("role", "operation");
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("purge");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys all conversations and purges all the pending messages for a remote destination.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WSRMRemoteEndpointRuntimeMBean.class.getMethod("getMessages", String.class, Integer.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("selector", "A valid JMS message selector or SAF message selector. "), createParameterDescriptor("timeout", "Specifies the amount of time the message cursor is valid.  A value of 0 indicates the cursor does not expire. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Queries messages on the queue according to the message selector and returns a message cursor representing the result set. The timeout parameter specifies the amount of time in seconds for which the cursor is valid. If the cursor expires, the associated resources are released.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("SAFMessageCursorRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
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
