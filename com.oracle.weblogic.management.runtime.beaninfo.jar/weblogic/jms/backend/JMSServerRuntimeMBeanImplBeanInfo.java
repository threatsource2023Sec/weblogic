package weblogic.jms.backend;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JMSServerRuntimeMBean;

public class JMSServerRuntimeMBeanImplBeanInfo extends JMSMessageCursorRuntimeImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSServerRuntimeMBean.class;

   public JMSServerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSServerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.backend.JMSServerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jms.backend");
      String description = (new String("<p>This class is used for monitoring a WebLogic JMS server.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSServerRuntimeMBean");
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
         currentResult = new PropertyDescriptor("BytesCurrentCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of bytes stored on this JMS server.</p>  <p>This number does not include the pending bytes.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesHighCount")) {
         getterName = "getBytesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesHighCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of bytes stored in the JMS server since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesPageableCurrentCount")) {
         getterName = "getBytesPageableCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPageableCurrentCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPageableCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Return the total number of bytes in all the messages that are currently available to be paged out, but which have not yet been paged out. The JMS server attempts to keep this number smaller than the \"MessageBufferSize\" parameter.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesPagedInTotalCount")) {
         getterName = "getBytesPagedInTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPagedInTotalCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPagedInTotalCount", currentResult);
         currentResult.setValue("description", "Return the total number of bytes that were read from the paging directory since the JMS server was started. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesPagedOutTotalCount")) {
         getterName = "getBytesPagedOutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPagedOutTotalCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPagedOutTotalCount", currentResult);
         currentResult.setValue("description", "<p>Return the total number of bytes that were written to the paging directory since the JMS server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesPendingCount")) {
         getterName = "getBytesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPendingCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The current number of bytes pending (unacknowledged or uncommitted) stored on this JMS server.</p>  <p>Pending bytes are over and above the current number of bytes.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesReceivedCount")) {
         getterName = "getBytesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesReceivedCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes received on this JMS server since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesThresholdTime")) {
         getterName = "getBytesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesThresholdTime", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>The amount of time in the threshold condition since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumptionPausedState")) {
         getterName = "getConsumptionPausedState";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumptionPausedState", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumptionPausedState", currentResult);
         currentResult.setValue("description", "<p>Returns the current consumption paused state of the JMSServer as string value.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Destinations")) {
         getterName = "getDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("Destinations", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Destinations", currentResult);
         currentResult.setValue("description", "<p>An array of destinations on this JMS server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationsCurrentCount")) {
         getterName = "getDestinationsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationsCurrentCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of destinations for this JMS server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationsHighCount")) {
         getterName = "getDestinationsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationsHighCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationsHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of destinations on this JMS server since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationsTotalCount")) {
         getterName = "getDestinationsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationsTotalCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of destinations instantiated on this JMS server since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The health state of this JMS server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InsertionPausedState")) {
         getterName = "getInsertionPausedState";
         setterName = null;
         currentResult = new PropertyDescriptor("InsertionPausedState", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InsertionPausedState", currentResult);
         currentResult.setValue("description", "<p>Returns the current insertion paused state of the JMSServer as string value.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogRuntime")) {
         getterName = "getLogRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("LogRuntime", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogRuntime", currentResult);
         currentResult.setValue("description", "Gets the log runtime instance for this JMSServerRuntime. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("MessagesCurrentCount")) {
         getterName = "getMessagesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesCurrentCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of messages stored on this JMS server. This number does not include the pending messages.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesHighCount")) {
         getterName = "getMessagesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesHighCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of messages stored in the JMS server since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesPageableCurrentCount")) {
         getterName = "getMessagesPageableCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPageableCurrentCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPageableCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Return the number of messages that are currently available for paging in this JMS server but have not yet been paged out. Note that due to internal implementation details, this count may be zero even if \"PageableByteCurrentCount\" is zero.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesPagedInTotalCount")) {
         getterName = "getMessagesPagedInTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPagedInTotalCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPagedInTotalCount", currentResult);
         currentResult.setValue("description", "<p>Return the total number of messages that were read from the paging directory since the JMS server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesPagedOutTotalCount")) {
         getterName = "getMessagesPagedOutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPagedOutTotalCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPagedOutTotalCount", currentResult);
         currentResult.setValue("description", "<p>Return the total number of messages that were written to the paging directory since the JMS server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesPendingCount")) {
         getterName = "getMessagesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPendingCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPendingCount", currentResult);
         currentResult.setValue("description", "<p>The current number of messages pending (unacknowledged or uncommitted) stored on this JMS server.</p>  <p>Pending messages are over and above the current number of messages.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesReceivedCount")) {
         getterName = "getMessagesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesReceivedCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages received on this destination since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesThresholdTime")) {
         getterName = "getMessagesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesThresholdTime", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>The amount of time in the threshold condition since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingAllocatedIoBufferBytes")) {
         getterName = "getPagingAllocatedIoBufferBytes";
         setterName = null;
         currentResult = new PropertyDescriptor("PagingAllocatedIoBufferBytes", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PagingAllocatedIoBufferBytes", currentResult);
         currentResult.setValue("description", "See <a href=\"PersistentStoreRuntimeMBean.html#getAllocatedIoBufferBytes\">PersistentStoreRuntimeMBean.AllocatedIoBufferBytes</a> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingAllocatedWindowBufferBytes")) {
         getterName = "getPagingAllocatedWindowBufferBytes";
         setterName = null;
         currentResult = new PropertyDescriptor("PagingAllocatedWindowBufferBytes", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PagingAllocatedWindowBufferBytes", currentResult);
         currentResult.setValue("description", "See <a href=\"PersistentStoreRuntimeMBean.html#getAllocatedWindowBufferBytes\">PersistentStoreRuntimeMBean.AllocatedWindowBufferBytes</a> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingPhysicalWriteCount")) {
         getterName = "getPagingPhysicalWriteCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PagingPhysicalWriteCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PagingPhysicalWriteCount", currentResult);
         currentResult.setValue("description", "See <a href=\"PersistentStoreRuntimeMBean.html#getPhysicalWriteCount\">PersistentStoreRuntimeMBean.PhysicalWriteCount</a> ");
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("PendingTransactions")) {
         getterName = "getPendingTransactions";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingTransactions", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingTransactions", currentResult);
         currentResult.setValue("description", "Returns an array of Xids representing transaction branches that exist onthis JMS server in the pending state, i.e. branches that have been prepared by the transaction manager but not yet committed or rolled back. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.transaction.xa.Xid")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProductionPausedState")) {
         getterName = "getProductionPausedState";
         setterName = null;
         currentResult = new PropertyDescriptor("ProductionPausedState", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProductionPausedState", currentResult);
         currentResult.setValue("description", "<p>Returns the current production paused state of the JMSServer as string value.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionPoolRuntimes")) {
         getterName = "getSessionPoolRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionPoolRuntimes", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionPoolRuntimes", currentResult);
         currentResult.setValue("description", "<p>The session pools running on this JMS server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionPoolsCurrentCount")) {
         getterName = "getSessionPoolsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionPoolsCurrentCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionPoolsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of session pools instantiated on this JMS server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionPoolsHighCount")) {
         getterName = "getSessionPoolsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionPoolsHighCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionPoolsHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of session pools instantiated on this JMS server since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionPoolsTotalCount")) {
         getterName = "getSessionPoolsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionPoolsTotalCount", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionPoolsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of session pools instantiated on this JMS server since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Transactions")) {
         getterName = "getTransactions";
         setterName = null;
         currentResult = new PropertyDescriptor("Transactions", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Transactions", currentResult);
         currentResult.setValue("description", "<p>Returns an array of Xids representing transaction branches that exist on this JMS server in any state.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.transaction.xa.Xid")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsumptionPaused")) {
         getterName = "isConsumptionPaused";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumptionPaused", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumptionPaused", currentResult);
         currentResult.setValue("description", "<p>Returns the current consumption paused state of the JMSServer as boolean value.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InsertionPaused")) {
         getterName = "isInsertionPaused";
         setterName = null;
         currentResult = new PropertyDescriptor("InsertionPaused", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InsertionPaused", currentResult);
         currentResult.setValue("description", "<p>Returns the current insertion paused state of the JMSServer as boolean value.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProductionPaused")) {
         getterName = "isProductionPaused";
         setterName = null;
         currentResult = new PropertyDescriptor("ProductionPaused", JMSServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProductionPaused", currentResult);
         currentResult.setValue("description", "<p>Returns the current production paused state of the JMSServer as boolean value.</p> ");
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
      Method mth = JMSServerRuntimeMBean.class.getMethod("getCursorStartPosition", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor start position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("getMessage", String.class, String.class);
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

      mth = JMSServerRuntimeMBean.class.getMethod("sort", String.class, Long.class, String[].class, Boolean[].class);
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

      mth = JMSServerRuntimeMBean.class.getMethod("getCursorEndPosition", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor end position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("getMessage", String.class, Long.class);
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

      mth = JMSServerRuntimeMBean.class.getMethod("getItems", String.class, Long.class, Integer.class);
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

      mth = JMSServerRuntimeMBean.class.getMethod("getNext", String.class, Integer.class);
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

      mth = JMSServerRuntimeMBean.class.getMethod("getPrevious", String.class, Integer.class);
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

      mth = JMSServerRuntimeMBean.class.getMethod("getCursorSize", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the number of items in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("closeCursor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Releases the server-side resources associated with the cursor and removes the runtime MBean instance.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("pauseProduction");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSException Thrown when an internal JMS error occurs while pausing the production.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the new message production on all the destinations hosted by the JMSServer.</p>  <p>When the production paused, it would prevent any new produce operations from both new and existing producers attached to the destinations. When the destination is &quot;resumed from production pause&quot;, all the new message production is allowed from both new and existing producers attached to that destination.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSServerRuntimeMBean#resumeProduction"), BeanInfoHelper.encodeEntities("JMSServerRuntimeMBean#pauseInsertion"), BeanInfoHelper.encodeEntities("JMSServerRuntimeMBean#pauseConsumption")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("resumeProduction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSException Thrown when an internal JMS error occurs while resuming the production.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the new message production operation on all the destinations hosted by the JMSServer. The state of the JMSServer shall be marked as &quot;production enabled&quot; thus allowing all the new &quot;producing&quot; activity to continue normally. Invoking this API on a JMSServer that is currently not in &quot;production paused&quot; state has no effect.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("pauseInsertion");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSException Thrown when an internal JMS error occurs while pausing the insertion.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the appearance of any messages on all the destinations of the JMSServer, that are result of the in-flight work completion on all the destinations hosted by this JMSServer.</p>  <p><b> Definition of In-Flight work</b></p> <p> The definitions below are based on the current implementation of WebLogic JMS subsystem.</p>  <ul> <li><p>In-flight messages associated with Producers</p> <ul> <li> UN-BORN MESSAGES <p>Messages that are produced by the producer, with &quot;birth time&quot; (TimeToDeliver) set in future are called un-born messages and are counted as &quot;pending&quot; messages in the destination statistics and are not available for consumers yet.</p></li>  <li> UN-COMMITTED MESSAGES <p>These are the messages that are produced by the producer as part of the transaction (using either user transaction or transacted session) and the transaction is still not committed or rolled back. These messages are also counted as &quot;pending&quot; messages in the destination statistics and are not available for consumption.</p></li>  <li> QUOTA BLOCKING SEND <p>These are the messages that are produced by the producers but are not able reach the destination because of (either message or byte or both) quota limit on the destination and the producers are willing to block for a specific period of time for the quota to be available. These messages are invisible to the system and are not counted against any of the destination statistics.</p></li> </ul> </li> <li><p>In-flight messages associated with Consumers</p> <ul> <li> UN-ACKNOWLEDGED (CLIENT ACK PENDING) MESSAGES <p>These are the messages that are successfully consumed by the clients using a &quot;client acknowledge&quot; session, and are awaiting acknowledgements from the clients. These are &quot;pending messages&quot; which will be removed from the destination/system when the acknowledgement is received.</p></li>  <li> UN-COMMITTED MESSAGES <p>These are the messages that are consumed (received) by the clients within a transaction (using either user transaction or transacted session) and the transaction is still not committed or rolled back. When the clients successfully commit the transaction the messages get removed from the system.</p></li>  <li> ROLLED BACK MESSAGES <p>These are the messages that are put back on the destination because of a successful rollback of transactional receive by the consumers. These messages might or might not be ready for consumption (re-delivered) to the clients immediately, depending on the redelivery parameters, RedeliveryDelay and/or RedeliveryDelayOverride and RedeliveryLimit configured on the associated JMSConnectionFactory and JMSDestination respectively.</p>  <p>If there is a redelivery delay configured, then for that &quot;delay&quot; duration, the messages are not available for consumption and are counted as &quot;pending&quot; in the destination statistics and after the &quot;delay&quot; period, if the redelivery limit is not exceeded, then they are delivered (made available for consumption) on that destination and are counted as &quot;current&quot; messages in the destination statistics. If the redelivery limit exceeds, then those messages will be moved to the ErrorDestination, if one configured.</p>  <p>Another parameter that controls the availability of the rolled back messages is RedeliveryLimit.</p></li>  <li> RECOVERED MESSAGES <p>These messages are similar to ROLLED BACK MESSAGES except that these messages appear on the queue because of an explicit call to session &quot;recover&quot; by the client.</p></li>  <li> REDELIVERED MESSAGES <p>These are again similar to ROLLED BACK MESSAGES except that these messages may re-appear on the destination because of an un-successful delivery attempt to the client (consumer crash, close etc.).</p></li> </ul> </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSServerRuntimeMBean#resumeInsertion"), BeanInfoHelper.encodeEntities("JMSServerRuntimeMBean#pauseProduction"), BeanInfoHelper.encodeEntities("JMSServerRuntimeMBean#pauseConsumption")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("resumeInsertion");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSException Thrown when an internal JMS error occurs while resuming the insertion.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the in-flight message production operation on all the destinations hosted by the JMSServer. The state of the JMSServer shall be marked as &quot;insertion enabled&quot; thus allowing all the messages from in-flight work completion are alloed to appear on the destinations.</p>  <p>Invoking this API on a JMSServer that is currently not in &quot;insertion paused&quot; state has no effect.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("pauseConsumption");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the consumption operation on all the destinations hosted by the JMSServer.</p>  <p> When the JMSServer is paused for consumption, all of its destination's state is marked as &quot;consumption paused&quot; and all the new synchronous receive operations will block until the destination is resumed and there are messages available for consumption. All the synchronous receive with blocking timeout will block until the timeout happens during the consumption paused state.</p>  <p>All the asynchronous consumers attached to that destination will not get any messages delivered to them while the destination in &quot;consumption paused&quot; state.</p>  <p> After a successful consumption &quot;pause&quot; operation, the user has to explicitly &quot;resume&quot; the destination to allow for any further consume operations on that destination</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("resumeConsumption");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMSException Thrown when an internal JMS error occurs while resuming the consumption.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the consumption operation on all the destinations hosted by the JMSSever.</p>  <p> The state of the destinations shall be marked as &quot;consumption enabled&quot; thus allowing all the &quot;consuming&quot; activity to continue normally.</p>  <p>Invoking this API on a JMSServer that is currently not in \"consumption paused\" state has no effect.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("getTransactionStatus", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("xid", "An Xid in string representation for a JMS transaction branch. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Given an Xid this method returns the JTA status of the associated JMS transaction branch.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.transaction.xa.Xid"), BeanInfoHelper.encodeEntities("javax.transaction.Status")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("getMessages", String.class, Integer.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("xid", "An Xid in string representation for a JMS transaction branch. "), createParameterDescriptor("timeoutSeconds", "The last access timeout for the cursor.  The cursor resources will be reclaimed if it is not accessed within the specified time interval.  A value of 0 indicates no timeout. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a set of messages that are associated with a JMS transaction branch.  Note that the result set is returned to the caller in the form of a message cursor that may contain messages from several destinations on this JMS server.</p>  <p>The timeout parameter specifies the amount of time in seconds for which the cursor is valid.  Upon timeout expiration the cursor is invalidated and the associated resources released.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("javax.transaction.xa.Xid"), BeanInfoHelper.encodeEntities("JMSMessageCursorRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("forceCommit", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("xid", "An xid in string representation for a JMS transaction branch. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Causes the work associated with the specified transaction branch to be committed.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerRuntimeMBean.class.getMethod("forceRollback", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("xid", "An xid in string representation for a JMS transaction branch. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Causes the work associated with the specified transaction branch to be rolled back.</p> ");
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
