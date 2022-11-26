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
import weblogic.management.runtime.JMSMessageManagementRuntimeMBean;

public class BEMessageManagementRuntimeDelegateBeanInfo extends JMSMessageCursorRuntimeImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSMessageManagementRuntimeMBean.class;

   public BEMessageManagementRuntimeDelegateBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BEMessageManagementRuntimeDelegateBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.backend.BEMessageManagementRuntimeDelegate");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.jms.backend");
      String description = (new String("This interface defines the common management functionality for both Queues and Topics ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JMSMessageManagementRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("MessagesDeletedCurrentCount")) {
         getterName = "getMessagesDeletedCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesDeletedCurrentCount", JMSMessageManagementRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesDeletedCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of messages that have been deleted from the destination.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#deleteMessages(String)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesMovedCurrentCount")) {
         getterName = "getMessagesMovedCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesMovedCurrentCount", JMSMessageManagementRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesMovedCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of messages that have been moved from the destination.</p> ");
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
      Method mth = JMSMessageManagementRuntimeMBean.class.getMethod("getCursorStartPosition", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor start position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getMessage", String.class, String.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("sort", String.class, Long.class, String[].class, Boolean[].class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getCursorEndPosition", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the cursor end position in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getMessage", String.class, Long.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getItems", String.class, Long.class, Integer.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getMessages", String.class, Integer.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getMessages", String.class, Integer.class, Integer.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getNext", String.class, Integer.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getMessage", String.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getPrevious", String.class, Integer.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("getCursorSize", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the number of items in the result set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("moveMessages", String.class, CompositeData.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("closeCursor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursorHandle", "The cursor handle. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Releases the server-side resources associated with the cursor and removes the runtime MBean instance.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("moveMessages", String.class, CompositeData.class, Integer.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("deleteMessages", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("selector", "A JMS message selector to identify which messages to  delete. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the set of messages from the destination that are qualified by the specified JMS message selector.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("importMessages", CompositeData[].class, Boolean.class);
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

      mth = JMSMessageManagementRuntimeMBean.class.getMethod("importMessages", CompositeData[].class, Boolean.class, Boolean.class);
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
