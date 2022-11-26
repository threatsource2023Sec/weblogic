package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SAFAgentMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SAFAgentMBean.class;

   public SAFAgentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SAFAgentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SAFAgentMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This class represents a Store-and-Forward (SAF) agent. A SAF sending agent takes care of storing messages to a persistent storage, forwarding messages to the receiving side, and re-transmitting messages when acknowledgements do not come back in time. A SAF receiving agent takes care of detecting and eliminating duplicate messages sent by the receiving agent, and deliver messages to the final endpoint.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SAFAgentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AcknowledgeInterval")) {
         getterName = "getAcknowledgeInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAcknowledgeInterval";
         }

         currentResult = new PropertyDescriptor("AcknowledgeInterval", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("AcknowledgeInterval", currentResult);
         currentResult.setValue("description", "<p>The maximum interval between two successive acknowledgements sent by the receiving side.</p>  <ul> <li> <p>Applies only to agents with receiving capability.</p> </li>  <li> <p>A value of <code>-1</code> specifies that there is no time limit between successive acknowledgement.</p> </li>  <li> <p>Updating <code>AcknowlegeInterval</code> causes connections starting after the update to use the new value.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesMaximum")) {
         getterName = "getBytesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesMaximum";
         }

         currentResult = new PropertyDescriptor("BytesMaximum", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("BytesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum bytes quota (total amount of bytes) that can be stored in this SAF agent.</p>  <ul> <li> <p>The default value of <code>-1</code> specifies that there is no WebLogic-imposed limit on the number of bytes that can be stored. However, excessive bytes volume can cause memory saturation, so this value should correspond to the total amount of available system memory relative to the rest of your application load.</p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>BytesMaximum</code> affects only new requests.</p> </li> </ul>  <p><b>Range of Values:</b> &gt;= BytesThresholdHigh</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesThresholdHigh")) {
         getterName = "getBytesThresholdHigh";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesThresholdHigh";
         }

         currentResult = new PropertyDescriptor("BytesThresholdHigh", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("BytesThresholdHigh", currentResult);
         currentResult.setValue("description", "<p>The upper threshold value that triggers events based on the number of bytes stored in the SAF agent.</p>  <ul> <li> <p>The default value of <code>-1</code>  disables the events for this SAF agent. </p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>BytesThresholdHigh</code> affects only new requests.</p> </li>  <li> <p>If the number of bytes exceeds this threshold, the triggered events are:</p>  <ul> <li><p><b>Log Messages</b>: A message is logged on the server indicating a high threshold condition.</p> </li>  <li><p><b>Flow Control</b>: The agent becomes armed and instructs producers to begin decreasing their message flow.</p></li> </ul> </li> </ul>  <p><b>Range of Values:</b> &lt;= BytesMaximum; &gt;= BytesThresholdLow</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesThresholdLow")) {
         getterName = "getBytesThresholdLow";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesThresholdLow";
         }

         currentResult = new PropertyDescriptor("BytesThresholdLow", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("BytesThresholdLow", currentResult);
         currentResult.setValue("description", "<p>The lower threshold that triggers events based on the number of bytes stored in the SAF agent.</p>  <ul> <li> <p>The default value of <code>-1</code>  disables the events for this SAF agent. </p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>BytesThresholdLow</code> affects only new requests.</p> </li>  <li> <p>If the number of bytes falls below this threshold, the triggered events are:</p>  <ul> <li><p><b>Log Messages</b>: A message is logged on the server indicating that the threshold condition has cleared.</p> </li>  <li><p><b>Flow Control</b>: The agent becomes disarmed and instructs producers to begin increasing their message flow.</p></li> </ul> </li> </ul>  <p><b>Range of Values:</b> &lt;= BytesThresholdHigh</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConversationIdleTimeMaximum")) {
         getterName = "getConversationIdleTimeMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConversationIdleTimeMaximum";
         }

         currentResult = new PropertyDescriptor("ConversationIdleTimeMaximum", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("ConversationIdleTimeMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time allowed, in milliseconds, before a sending side releases the resources used by a conversation.</p>  <ul> <li> <p>If there is no activity for a conversation for the specified amount of time, the sending agent releases all resources for that conversation and notifies the receiving agent to do the same.</p> </li>  <li> <p>A value of <code>0</code> specifies that resources for a conversation are not released unless the application explicitly closes the conversation.</p> </li>  <li> <p>Updating <code>ConversationIdleTimeMaximum</code> causes connections starting after the update to use the new value.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultRetryDelayBase")) {
         getterName = "getDefaultRetryDelayBase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRetryDelayBase";
         }

         currentResult = new PropertyDescriptor("DefaultRetryDelayBase", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("DefaultRetryDelayBase", currentResult);
         currentResult.setValue("description", "<p>The amount of time, in milliseconds, between the original delivery attempt and the first retry.</p>  <ul> <li> <p>If <code>RetryDelayMultiplier</code> is set to <code>1</code>, this defines the interval between any two successive retry attempts.</p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>DefaultRetryDelayBase</code> causes connections starting after the update to use the new value.</p> </li> </ul>  <p><b>Range of Values:</b> &lt;= RetryDelayMaximum if RetryDelayMultiplier is not 1.0. </p> ");
         setPropertyDescriptorDefault(currentResult, new Long(20000L));
         currentResult.setValue("legalMin", new Long(1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultRetryDelayMaximum")) {
         getterName = "getDefaultRetryDelayMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRetryDelayMaximum";
         }

         currentResult = new PropertyDescriptor("DefaultRetryDelayMaximum", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("DefaultRetryDelayMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time, in milliseconds, between two successive delivery retry attempts.</p>  <ul> <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>DefaultRetryDelayMaximum</code> causes connections starting after the update to use the new value.</p> </li> </ul>  <p><b>Range of Values:</b> &gt; = RetryDelayBase if RetryDelayMultiplier is not 1.0. </p> ");
         setPropertyDescriptorDefault(currentResult, new Long(180000L));
         currentResult.setValue("legalMin", new Long(1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultRetryDelayMultiplier")) {
         getterName = "getDefaultRetryDelayMultiplier";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRetryDelayMultiplier";
         }

         currentResult = new PropertyDescriptor("DefaultRetryDelayMultiplier", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("DefaultRetryDelayMultiplier", currentResult);
         currentResult.setValue("description", "<p>The factor used to multiply the previous delay time to calculate the next delay time to be used.</p>  <ul> <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>DefaultRetryDelayMuliplier</code> causes connections starting after the update to use the new value.</p> </li> </ul>  <p><b>Range of Values:</b> &gt;= 1.</p> ");
         setPropertyDescriptorDefault(currentResult, new Double(1.0));
         currentResult.setValue("legalMin", new Double(1.0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTimeToLive")) {
         getterName = "getDefaultTimeToLive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTimeToLive";
         }

         currentResult = new PropertyDescriptor("DefaultTimeToLive", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("DefaultTimeToLive", currentResult);
         currentResult.setValue("description", "<p>The default amount of time, in milliseconds, that the agent guarantees to reliably send messages.</p>  <ul> <li> <p>A value of 0 means the agent guarantees to reliably send messages for the duration of the conversation.</p> </li>  <li> <p>Updating <code>DefaultTimeToLive</code> causes conversations starting after the update to use the new value.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSSAFMessageLogFile")) {
         getterName = "getJMSSAFMessageLogFile";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSSAFMessageLogFile", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("JMSSAFMessageLogFile", currentResult);
         currentResult.setValue("description", "The jms message log file configuration for this saf agent ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumMessageSize")) {
         getterName = "getMaximumMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumMessageSize";
         }

         currentResult = new PropertyDescriptor("MaximumMessageSize", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("MaximumMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum number of bytes allowed in individual messages on this SAF agent.</p>  <ul> <li> <p>The message size includes the message body, any user-defined properties, and the user-defined JMS header fields: <code>JMSCorrelationID</code> and <code>JMSType</code>.</p> </li>  <li> <p>Producers sending messages that exceed the configured maximum message size for the SAF agent will receive a <code>ResourceAllocationException</code>.</p> </li>  <li> <p>The maximum message size is only enforced for the initial production of a message. Messages that are redirected to an error destination or forwarded to a member of a distributed destination are not checked for size. For instance, if a destination and its corresponding error destination are configured with a maximum message size of 128K bytes and 64K bytes, respectively, a message of 96K bytes could be redirected to the error destination (even though it exceeds the 64K byte maximum), but a producer could not directly send the 96K byte message to the error destination.</p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>MaximumMessageSize</code> affects only incoming messages; stored messages are not affected.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("MessageBufferSize")) {
         getterName = "getMessageBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageBufferSize";
         }

         currentResult = new PropertyDescriptor("MessageBufferSize", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("MessageBufferSize", currentResult);
         currentResult.setValue("description", "<p>The amount of memory used to store message bodies in memory before they are paged out to disk.</p> <ul> <li> <p>A value of -1 (the default) specifies that the server will automatically determine a size based on the maximum heap size of the JVM. This default will be set to either one-third the maximum heap size, or 512 megabytes, whichever is smaller.</p> </li>  <li> <p>The larger the buffer, the more memory JMS will consume when many messages are waiting on queues or topics. Once the buffer is surpassed, JMS may write message bodies to the directory specified by PagingDirectory in an effort to reduce memory usage below this buffer.</p> </li>  <li> <p>Surpassing the buffer size does not stop the JMS server from accepting new messages. It is still possible to run out of memory if messages are arriving faster than they can be written to disk. Users with high messaging loads who wish to support the highest possible availability should consider setting a quota or setting a threshold and enabling flow control.</p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Paging is always supported. </p> </li>  <li> <p>Updating the memory size resets the message count so that only requests arriving after the update are charged against the new memory size.</p> </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPagingDirectory")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageCompressionOptions")) {
         getterName = "getMessageCompressionOptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageCompressionOptions";
         }

         currentResult = new PropertyDescriptor("MessageCompressionOptions", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("MessageCompressionOptions", currentResult);
         currentResult.setValue("description", "<p>Specifies the type of message compression used when JMS message body compression is enabled for Persistent Stores and JMS Paging Stores.</p> <ul> <li>Use <code>GZIP_DEFAULT_COMPRESSION</code> to enable message compression using the JDK GZIP API with <code>DEFAULT_COMPRESSION</code> level.</li> <li>Use <code>GZIP_BEST_COMPRESSION</code> to enable message compression using the JDK GZIP API with <code>BEST_COMPRESSION</code> level.</li> <li>Use <code>GZIP_BEST_SPEED</code> to enable message compression using the JDK GZIP API with <code>BEST_SPEED</code> level.</li> <li>Use <code>LZF</code> to enable message compression using Open Source LZF.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isStoreMessageCompressionEnabled"), BeanInfoHelper.encodeEntities("#isPagingMessageCompressionEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "GZIP_DEFAULT_COMPRESSION");
         currentResult.setValue("legalValues", new Object[]{"GZIP_DEFAULT_COMPRESSION", "GZIP_BEST_COMPRESSION", "GZIP_BEST_SPEED", "LZF"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageCompressionOptionsOverride")) {
         getterName = "getMessageCompressionOptionsOverride";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageCompressionOptionsOverride";
         }

         currentResult = new PropertyDescriptor("MessageCompressionOptionsOverride", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("MessageCompressionOptionsOverride", currentResult);
         currentResult.setValue("description", "<p>The value of the override compression options.</p> <p>The supported options are:</p> <p>  weblogic.jms.common.CompressionFactory=weblogic.jms.common.GZIPCompressionFactoryImpl,weblogic.jms.common.gzip.level=DEFAULT_COMPRESSION: this specifies using JDK GZIP API with DEFAULT_COMPRESSION level</p> <p>  weblogic.jms.common.CompressionFactory=weblogic.jms.common.GZIPCompressionFactoryImpl,weblogic.jms.common.gzip.level=BEST_COMPRESSION: this specifies using JDK GZIP API with BEST_COMPRESSION level</p> <p>  weblogic.jms.common.CompressionFactory=weblogic.jms.common.GZIPCompressionFactoryImpl,weblogic.jms.common.gzip.level=BEST_SPEED: this specifies using JDK GZIP API with BEST_SPEED level</p> <p>weblogic.jms.common.CompressionFactory=weblogic.jms.common.LZFCompressionFactoryImpl: this specifies using Open Source LZF</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMessageCompressionOptionsOverride")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesMaximum")) {
         getterName = "getMessagesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesMaximum";
         }

         currentResult = new PropertyDescriptor("MessagesMaximum", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("MessagesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum message quota (total amount of messages) that can be stored in this SAF agent.</p>  <ul> <li> <p>The default value of <code>-1</code> specifies that the server instance has no limit on the number of messages that can be stored. However, excessive message volume can cause memory saturation, so this value should correspond to the total amount of available system memory relative to the rest of your application load.</p> </li>  <li> <p>Applies only to sending agents.</p> </li>  <li> <p>Updating the maximum message quota resets the message count so that only requests arriving after the update are charged against the new quota.</p> </li> </ul>  <p><b>Range of Values:</b> &gt;= MessagesThresholdHigh</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesThresholdHigh")) {
         getterName = "getMessagesThresholdHigh";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesThresholdHigh";
         }

         currentResult = new PropertyDescriptor("MessagesThresholdHigh", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("MessagesThresholdHigh", currentResult);
         currentResult.setValue("description", "<p>The upper threshold that triggers events based on the number of messages stored in the SAF agent.</p>  <ul> <li> <p>The default value of <code>-1</code> disables the events for this SAF agent. </p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>MessagesThresholdHigh</code> affects only new requests.</p> </li>  <li> <p>If the number of messages exceeds this threshold, the triggered events are:</p>  <ul> <li><p><b>Log Messages</b>: A message is logged on the server indicating a high threshold condition.</p> </li>  <li><p><b>Flow Control</b>: The agent becomes armed and instructs producers to begin decreasing their message flow.</p></li> </ul> </li> </ul>  <p><b>Range of Values:</b> &lt;= MessagesMaximum; &gt;= MessagesThresholdLow</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesThresholdLow")) {
         getterName = "getMessagesThresholdLow";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesThresholdLow";
         }

         currentResult = new PropertyDescriptor("MessagesThresholdLow", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("MessagesThresholdLow", currentResult);
         currentResult.setValue("description", "<p>The low threshold that triggers events based on the number of messages stored in the SAF agent.</p>  <ul> <li> <p>The default value of <code>-1</code> disables the events for this SAF agent. </p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>MessagesThresholdLow</code> affects only new requests.</p> </li>  <li> <p>If the number of messages falls below this threshold, the triggered events are:</p>  <ul> <li><p><b>Log Messages</b>: A message is logged on the server indicating that the threshold condition has cleared.</p> </li>  <li><p><b>Flow Control</b>: The agent becomes disarmed and instructs producers to begin increasing their message flow.</p></li> </ul> </li> </ul>  <p><b>Range of Values:</b> &lt;= MessagesThresholdHigh.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PagingDirectory")) {
         getterName = "getPagingDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingDirectory";
         }

         currentResult = new PropertyDescriptor("PagingDirectory", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("PagingDirectory", currentResult);
         currentResult.setValue("description", "<p> Specifies where message bodies are written when the size of the message bodies in the JMS server exceeds the message buffer size. If unspecified, messages are written to the <code>tmp</code> directory in the host WebLogic Server instance's directory. For example, <code><i>domainName</i>/servers/<i>servername</i>/tmp</code>. </p>  <ul> <li> <p>For best performance, this directory should not be the same as the directory used by the SAF agent's persistent store.</p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li> </ul>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMessageBufferSize")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceType")) {
         getterName = "getServiceType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceType";
         }

         currentResult = new PropertyDescriptor("ServiceType", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("ServiceType", currentResult);
         currentResult.setValue("description", "<p>The type of service that this SAF agent provides. JMS requires only a Sending agent on the sending side for messages. Whereas, Web Services Reliable Messaging requires both a Sending and Receiving agent for messages.</p> <ul> <li><b>Sending-only</b> Configures an agent that stores messages in persistent storage, forwards messages to the receiving side, and re-transmits messages when acknowledgements do not come back in time.</li> <li><b>Receiving-only</b> Configures an agent that detects and eliminates duplicate messages sent by a receiving agent, and delivers messages to the final destination.</li> <li><b>Both</b> Configures an agent that has sending and receiving agent functionality. </li> </ul>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, "Both");
         currentResult.setValue("legalValues", new Object[]{"Both", "Sending-only", "Receiving-only"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Store")) {
         getterName = "getStore";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStore";
         }

         currentResult = new PropertyDescriptor("Store", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("Store", currentResult);
         currentResult.setValue("description", "<p>The persistent disk-based file store or JDBC-accessible database for the SAF agent.</p>  <p>If a store is not configured, the server's default store is used.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WindowInterval")) {
         getterName = "getWindowInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWindowInterval";
         }

         currentResult = new PropertyDescriptor("WindowInterval", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("WindowInterval", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time, in milliseconds, that a JMS sending agent waits before forwarding messages in a single batch. For a distributed queue or topic, <code>WindowInterval</code> setting is ignored.</p>  <p>A sending agent waits to forward the message batch until either: (A) the source destination message count is greater than or equal to the configured <code>Window Size</code>; (B) it has waited a specified <code>Window Interval</code>, whichever comes first.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WindowSize")) {
         getterName = "getWindowSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWindowSize";
         }

         currentResult = new PropertyDescriptor("WindowSize", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("WindowSize", currentResult);
         currentResult.setValue("description", "<p>For JMS messages, the number of messages in a batch. A sending agent waits to forward a message batch until the source destination message count is greater than or equal to this value. For a distributed queue or topic, <code>WindowSize</code> setting is ignored and always internally set to 1 message.</p> <p>For WSRM, the maximum number of unacknowledged messages allowed between a source destination and a target destination during a conversation.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ForwardingPausedAtStartup")) {
         getterName = "isForwardingPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForwardingPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("ForwardingPausedAtStartup", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("ForwardingPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the agent is paused for forwarding messages at the startup time.</p>  <p>When enabled, the sending agent forwards messages. Otherwise, the sending agent does not forward any messages.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IncomingPausedAtStartup")) {
         getterName = "isIncomingPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncomingPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("IncomingPausedAtStartup", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("IncomingPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the agent is paused for incoming messages at startup time.</p>  <p>When enabled, the sending agent accepts new messages. Otherwise, the sending agent does not accept any new messages.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoggingEnabled")) {
         getterName = "isLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("LoggingEnabled", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("LoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a message is logged in the server log file when a message fails to be forwarded.</p>  <ul> <li> <p>If selected, this applies to messages that fail to be forwarded and delivered to the final destination when there is no error handling in a SAF application or the application's error handling fails.</p> </li>  <li> <p>Applies only to agents with receiving capability.</p> </li>  <li> <p>Updating <code>LoggingEnabled</code> causes connections starting after the update to use the new value.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingMessageCompressionEnabled")) {
         getterName = "isPagingMessageCompressionEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingMessageCompressionEnabled";
         }

         currentResult = new PropertyDescriptor("PagingMessageCompressionEnabled", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("PagingMessageCompressionEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables the JMS paging store to perform message body compression on persistent and non-persistent messages. When <code>false</code>, the default value, no compression is performed.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMessageCompressionOptions")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReceivingPausedAtStartup")) {
         getterName = "isReceivingPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReceivingPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("ReceivingPausedAtStartup", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("ReceivingPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the agent is paused for receiving messages at the startup time.</p>  <p>When enabled, the sending agent receives messages. Otherwise, the sending agent does not receive any messages.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreMessageCompressionEnabled")) {
         getterName = "isStoreMessageCompressionEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreMessageCompressionEnabled";
         }

         currentResult = new PropertyDescriptor("StoreMessageCompressionEnabled", SAFAgentMBean.class, getterName, setterName);
         descriptors.put("StoreMessageCompressionEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables the JMS store to perform message body compression. When set to <code>false</code>, the default value, no compression is performed.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMessageCompressionOptions")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SAFAgentMBean.class.getMethod("addTarget", TargetMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the Target attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>You can add a target to specify additional servers on which the deployment can be deployed. The targets must be either clusters or servers.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = SAFAgentMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the value of the addTarget attribute.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addTarget")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = SAFAgentMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = SAFAgentMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SAFAgentMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = SAFAgentMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
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
