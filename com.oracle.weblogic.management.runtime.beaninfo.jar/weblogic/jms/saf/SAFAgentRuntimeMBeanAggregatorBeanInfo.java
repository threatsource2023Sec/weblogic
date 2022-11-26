package weblogic.jms.saf;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SAFAgentRuntimeMBean;
import weblogic.messaging.saf.internal.SAFStatisticsCommonMBeanImplBeanInfo;

public class SAFAgentRuntimeMBeanAggregatorBeanInfo extends SAFStatisticsCommonMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SAFAgentRuntimeMBean.class;

   public SAFAgentRuntimeMBeanAggregatorBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SAFAgentRuntimeMBeanAggregatorBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jms.saf.SAFAgentRuntimeMBeanAggregator");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.jms.saf");
      String description = (new String("This class is used for monitoring a WebLogic SAF agent. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SAFAgentRuntimeMBean");
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
         currentResult = new PropertyDescriptor("BytesCurrentCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the current number of bytes. This number does not include the pending bytes.</p> ");
      }

      if (!descriptors.containsKey("BytesHighCount")) {
         getterName = "getBytesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesHighCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesHighCount", currentResult);
         currentResult.setValue("description", "<p>Returns the peak number of bytes since the last reset.</p> ");
      }

      if (!descriptors.containsKey("BytesPendingCount")) {
         getterName = "getBytesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesPendingCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesPendingCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of pending bytes. Pending bytes are over and above the current number of bytes.</p> ");
      }

      if (!descriptors.containsKey("BytesReceivedCount")) {
         getterName = "getBytesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesReceivedCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of bytes received since the last reset.</p> ");
      }

      if (!descriptors.containsKey("BytesThresholdTime")) {
         getterName = "getBytesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesThresholdTime", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>Returns the amount of time in the threshold condition since the last reset.</p> ");
      }

      if (!descriptors.containsKey("Conversations")) {
         getterName = "getConversations";
         setterName = null;
         currentResult = new PropertyDescriptor("Conversations", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Conversations", currentResult);
         currentResult.setValue("description", "<p>A list of SAFConversationRuntimeMBean instances</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConversationsCurrentCount")) {
         getterName = "getConversationsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConversationsCurrentCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConversationsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the current number of conversations</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConversationsHighCount")) {
         getterName = "getConversationsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConversationsHighCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConversationsHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of conversations since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConversationsTotalCount")) {
         getterName = "getConversationsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConversationsTotalCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConversationsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of conversations since the last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailedMessagesTotal")) {
         getterName = "getFailedMessagesTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedMessagesTotal", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedMessagesTotal", currentResult);
         currentResult.setValue("description", "<p>Returns the total number of messages that have failed to be forwarded since the last reset.</p> ");
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The health state of this JMS server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogRuntime")) {
         getterName = "getLogRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("LogRuntime", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogRuntime", currentResult);
         currentResult.setValue("description", "Gets the log runtime instance for this SAFAgent runtime. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("MessagesCurrentCount")) {
         getterName = "getMessagesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesCurrentCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the current number of messages. This number includes the pending messages.</p> ");
      }

      if (!descriptors.containsKey("MessagesHighCount")) {
         getterName = "getMessagesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesHighCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesHighCount", currentResult);
         currentResult.setValue("description", "<p>Returns the peak number of messages since the last reset.</p> ");
      }

      if (!descriptors.containsKey("MessagesPendingCount")) {
         getterName = "getMessagesPendingCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesPendingCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesPendingCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of pending messages. Pending messages are over and above the current number of messages. A pending message is one that has either been sent in a transaction and not committed, or been forwarded but has not been acknowledged.</p> ");
      }

      if (!descriptors.containsKey("MessagesReceivedCount")) {
         getterName = "getMessagesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesReceivedCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages received since the last reset.</p> ");
      }

      if (!descriptors.containsKey("MessagesThresholdTime")) {
         getterName = "getMessagesThresholdTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesThresholdTime", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesThresholdTime", currentResult);
         currentResult.setValue("description", "<p>Returns the amount of time in the threshold condition since the last reset.</p> ");
      }

      if (!descriptors.containsKey("RemoteEndpoints")) {
         getterName = "getRemoteEndpoints";
         setterName = null;
         currentResult = new PropertyDescriptor("RemoteEndpoints", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RemoteEndpoints", currentResult);
         currentResult.setValue("description", "<p>The remote endpoints to which this SAF agent has been storing and forwarding messages.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteEndpointsCurrentCount")) {
         getterName = "getRemoteEndpointsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RemoteEndpointsCurrentCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RemoteEndpointsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of remote endpoints to which this SAF agent has been storing and forwarding messages.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteEndpointsHighCount")) {
         getterName = "getRemoteEndpointsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RemoteEndpointsHighCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RemoteEndpointsHighCount", currentResult);
         currentResult.setValue("description", "<p>The peak number of remote endpoints to which this SAF agent has been storing and forwarding messages since last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteEndpointsTotalCount")) {
         getterName = "getRemoteEndpointsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RemoteEndpointsTotalCount", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RemoteEndpointsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of remote endpoints to which this SAF agent has been storing and forwarding messages since last reset.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PausedForForwarding")) {
         getterName = "isPausedForForwarding";
         setterName = null;
         currentResult = new PropertyDescriptor("PausedForForwarding", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PausedForForwarding", currentResult);
         currentResult.setValue("description", "<p>Indicates whether or not the sending agent is paused for forwarding at the current time.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PausedForIncoming")) {
         getterName = "isPausedForIncoming";
         setterName = null;
         currentResult = new PropertyDescriptor("PausedForIncoming", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PausedForIncoming", currentResult);
         currentResult.setValue("description", "<p>Indicates whether or not the sending agent is paused for incoming messages at the current time.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PausedForReceiving")) {
         getterName = "isPausedForReceiving";
         setterName = null;
         currentResult = new PropertyDescriptor("PausedForReceiving", SAFAgentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PausedForReceiving", currentResult);
         currentResult.setValue("description", "<p>Indicates whether or not the receiving agent is paused for receiving at the current time.</p> ");
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
      Method mth = SAFAgentRuntimeMBean.class.getMethod("pauseIncoming");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the sending agent on accepting new messages.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = SAFAgentRuntimeMBean.class.getMethod("resumeIncoming");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the sending agent for accepting new messages.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = SAFAgentRuntimeMBean.class.getMethod("pauseForwarding");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the sending agent on forwarding messages so that the agent will not forward messages but will accept new messages.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = SAFAgentRuntimeMBean.class.getMethod("resumeForwarding");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the sending agent for forwarding messages.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = SAFAgentRuntimeMBean.class.getMethod("pauseReceiving");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Pauses the receiving agent on receiving messages.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = SAFAgentRuntimeMBean.class.getMethod("resumeReceiving");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the receiving agent for receiving messages.</p> ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
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
