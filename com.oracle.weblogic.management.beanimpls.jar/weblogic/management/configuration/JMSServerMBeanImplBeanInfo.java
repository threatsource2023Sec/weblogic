package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMSServerMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSServerMBean.class;

   public JMSServerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSServerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JMSServerMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This class represents a JMS server. A JMS server manages connections and message requests on behalf of clients.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JMSServerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BlockingSendPolicy")) {
         getterName = "getBlockingSendPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlockingSendPolicy";
         }

         currentResult = new PropertyDescriptor("BlockingSendPolicy", JMSServerMBean.class, getterName, setterName);
         descriptors.put("BlockingSendPolicy", currentResult);
         currentResult.setValue("description", "<p>Determines whether the JMS server delivers smaller messages before larger ones when a destination has exceeded its maximum number of messages. <code>FIFO</code> prevents the JMS server from delivering smaller messages when larger ones are already waiting for space. <code>Preemptive</code> allows smaller send requests to preempt previous larger ones when there is sufficient space for smaller messages on the destination.</p>  <p>This policy is defined only for the JMS server; it cannot be set on individual destinations.</p>  <p>Additional information on the <code>FIFO</code> and <code>Preemptive</code> policies is provided below.</p> <p> <code>FIFO</code> (first in, first out) indicates that all send requests for the same destination are queued up one behind the other until space is available. No send request is permitted to successfully complete if there is another send request waiting for space before it. When space is limited, the FIFO policy prevents the starvation of larger requests because smaller requests cannot continuously use the remaining available space. Smaller requests are delayed, though not starved, until the larger request can be completed. When space does become available, requests are considered in the order in which they were made. If there is sufficient space for a given request, then that request is completed and the next request is considered. If there is insufficient space for a given request, then no further requests are considered until sufficient space becomes available for the current request to complete.</p> <p> <code>Preemptive</code> indicates that a send operation can preempt other blocking send operations if space is available. That is, if there is sufficient space for the current request, then that space is used even if there are other requests waiting for space. When space is limited, the Preemptive policy can result in the starvation of larger requests. For example, if there is insufficient available space for a large request, then it is queued up behind other existing requests. When space does become available, all requests are considered in the order in which they were originally made. If there is sufficient space for a given request, then that request is allowed to continue and the next request is considered. If there is insufficient space for a given request, then that request is skipped and the next request is considered.</p> ");
         setPropertyDescriptorDefault(currentResult, "FIFO");
         currentResult.setValue("legalValues", new Object[]{"FIFO", "Preemptive"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesMaximum")) {
         getterName = "getBytesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesMaximum";
         }

         currentResult = new PropertyDescriptor("BytesMaximum", JMSServerMBean.class, getterName, setterName);
         descriptors.put("BytesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of bytes that can be stored in this JMS server. A value of <code>-1</code> removes any WebLogic Server limits.</p>  <p>Because excessive bytes volume can cause memory saturation, Oracle recommends that this maximum corresponds to the total amount of system memory available after accounting for the rest of your application load.</p>  <p><b>Range of Values:</b> &gt;= BytesThresholdHigh</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
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

         currentResult = new PropertyDescriptor("BytesThresholdHigh", JMSServerMBean.class, getterName, setterName);
         descriptors.put("BytesThresholdHigh", currentResult);
         currentResult.setValue("description", "<p>The upper threshold (number of bytes stored in this JMS server) that triggers flow control and logging events. A value of <code>-1</code> disables the events for this JMS server.</p>  <p>The triggered events are:</p> <ul> <li> <code>Log Messages</code> - A message is logged on the server indicating a high threshold condition. </li> <li> <code>Flow Control</code> - If flow control is enabled, the JMS server becomes armed and instructs producers to begin decreasing their message flow. </li> </ul>  <p><b>Range of Values:</b> &lt;= BytesMaximum; &gt;= BytesThresholdLow</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
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

         currentResult = new PropertyDescriptor("BytesThresholdLow", JMSServerMBean.class, getterName, setterName);
         descriptors.put("BytesThresholdLow", currentResult);
         currentResult.setValue("description", "<p>The lower threshold (number of bytes stored in this JMS server) that triggers flow control and logging events. A value of <code>-1</code> disables the events for this JMS server.</p>  <p>If the number of bytes falls below this threshold, the triggered events are:</p> <ul> <li> <code>Log Messages</code> - A message is logged on the server indicating that the threshold condition has cleared. </li> <li> <code>Flow Control</code> - If flow control is enabled, the JMS server becomes disarmed and instructs producers to begin increasing their message flow. </li> </ul>  <p><b>Range of Values:</b> &lt;= BytesThresholdHigh</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("ConsumptionPausedAtStartup")) {
         getterName = "getConsumptionPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsumptionPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("ConsumptionPausedAtStartup", JMSServerMBean.class, getterName, setterName);
         descriptors.put("ConsumptionPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Indicates whether consumption is paused at startup on destinations targeted to this JMS server at startup. A destination cannot receive any new messages while it is paused.</p>  <p>When the value is set to <code>true</code>, then immediately after the host server instance is booted, then this JMS server and its targeted destinations are modified such that they are in a \"consumption paused\" state, which prevents any message consuming activity on those destinations.</p>  <p>To allow normal message consumption on the destinations, later you will have to change the state of this JMS server to a \"consumption enabled\" state by setting this value to <code>false</code>, and then either redeploy the JMS server or reboot the hosting server instance.</p>  <p>When the value is set to <code>default</code>, then the Consumption Paused At Startup is determined based on the corresponding setting on the individual destination.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.JMSServerRuntimeMBean#resumeConsumption"), BeanInfoHelper.encodeEntities("weblogic.management.runtime.JMSDestinationRuntimeMBean#resumeConsumption")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "default");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("Destinations")) {
         getterName = "getDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("Destinations", JMSServerMBean.class, getterName, setterName);
         descriptors.put("Destinations", currentResult);
         currentResult.setValue("description", "<p>All defined destinations and their associated JNDI names.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addDestination");
         currentResult.setValue("remover", "removeDestination");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced with the functionality of JMS modules. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("ExpirationScanInterval")) {
         getterName = "getExpirationScanInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExpirationScanInterval";
         }

         currentResult = new PropertyDescriptor("ExpirationScanInterval", JMSServerMBean.class, getterName, setterName);
         descriptors.put("ExpirationScanInterval", currentResult);
         currentResult.setValue("description", "<p>The number of seconds between this JMS server's cycles of scanning local destinations for expired messages. A value of <code>0</code> disables active scanning. A very large scan interval effectively disables active scanning.</p>  <p>With scanning disabled, users still do not receive expired messages and any expired messages that are discovered by other system activities are removed. However, expired messages sitting in idle destinations (such as an inactive queue or disconnected durable subscriber) are not removed and continue to consume system resources.</p>  <p>The scanning cycle for expired messages occurs as follows:</p> <ul> <li> After the specified waiting period, the JMS server devotes a separate thread to scan all of its local destinations for expired messages. </li> <li> After the scanning is completed, all located expired messages are processed according to the specified Expiration Policy on the destination (Discard, Log, or Redirect). </li> <li> The entire process repeats after another specified waiting period. </li> </ul>  <p><b>Note:</b> Because a new scan does not start until the current one is finished and until the specified waiting period ends, an expired message could still remain in the system for the maximum scan waiting period plus the amount of time it takes to perform the scan.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InsertionPausedAtStartup")) {
         getterName = "getInsertionPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInsertionPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("InsertionPausedAtStartup", JMSServerMBean.class, getterName, setterName);
         descriptors.put("InsertionPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Indicates whether insertion is paused at startup on destinations targeted to this JMS server. A destination cannot receive any new messages while it is paused.</p>  <p>When the value is set to <code>true</code>, then immediately after the host server instance is booted, then this JMS server and its targeted destinations are modified such that they are in a \"insertion paused\" state, which results preventing messages that are result of the \"in-flight\" work completion to arrive on those destinations.</p>  <p><b>Note:</b> For a detailed definition of \"in-flight\" work/messages, see weblogic.management.runtime.JMSServerRuntimeMBean#resumeInsertion and weblogic.management.runtime.JMSDestinationRuntime#resumeInsertion</p>  <p>To allow in-flight work messages to appear on the destinations, later you will have to change the state of this JMS server to an \"insertion enabled\" state by setting this value to <code>false</code>, and then either redeploy the JMS server or reboot the hosting server instance.</p>  <p>When the value is set to <code>default</code>, then the Insertion Paused At Startup is determined based on the corresponding setting on the individual destination.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.JMSServerRuntimeMBean#resumeInsertion"), BeanInfoHelper.encodeEntities("weblogic.management.runtime.JMSDestinationRuntimeMBean#resumeInsertion")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "default");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSMessageLogFile")) {
         getterName = "getJMSMessageLogFile";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSMessageLogFile", JMSServerMBean.class, getterName, setterName);
         descriptors.put("JMSMessageLogFile", currentResult);
         currentResult.setValue("description", "<p>The message log file configuration for this JMS Server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSQueues")) {
         getterName = "getJMSQueues";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSQueues", JMSServerMBean.class, getterName, setterName);
         descriptors.put("JMSQueues", currentResult);
         currentResult.setValue("description", "<p>Acquire JMSQueues for this JMSServer</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSQueue");
         currentResult.setValue("creator", "createJMSQueue");
         currentResult.setValue("destroyer", "destroyJMSQueue");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("JMSSessionPools")) {
         getterName = "getJMSSessionPools";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSSessionPools", JMSServerMBean.class, getterName, setterName);
         descriptors.put("JMSSessionPools", currentResult);
         currentResult.setValue("description", "<p>The session pools defined for the JMS server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSSessionPool");
         currentResult.setValue("destroyer", "destroyJMSSessionPool");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by message-driven beans. The JMSSessionPoolMBean type was deprecated. See JMSSessionPoolMBean for more information. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSTopics")) {
         getterName = "getJMSTopics";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSTopics", JMSServerMBean.class, getterName, setterName);
         descriptors.put("JMSTopics", currentResult);
         currentResult.setValue("description", "<p>Define JMSTopics for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSTopic");
         currentResult.setValue("destroyer", "destroyJMSTopic");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("MaximumMessageSize")) {
         getterName = "getMaximumMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumMessageSize";
         }

         currentResult = new PropertyDescriptor("MaximumMessageSize", JMSServerMBean.class, getterName, setterName);
         descriptors.put("MaximumMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum number of bytes allowed in individual messages on this JMS server. The size of a message includes the message body, any user-defined properties, and the user-defined JMS header fields <code>JMSCorrelationID</code> and <code>JMSType</code>.</p>  <p>The maximum message size is only enforced for the initial production of a message. Messages that are redirected to an error destination or forwarded to a member of a distributed destination are not checked for size. For instance, if a destination and its corresponding error destination are configured with a maximum message size of 128K bytes and 64K bytes, respectively, a message of 96K bytes could be redirected to the error destination (even though it exceeds the 64K byte maximum), but a producer could not directly send the 96K byte message to the error destination.</p>  <p><b>Note:</b> Any change to this maximum affects only incoming messages; stored messages are not affected.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageBufferSize")) {
         getterName = "getMessageBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageBufferSize";
         }

         currentResult = new PropertyDescriptor("MessageBufferSize", JMSServerMBean.class, getterName, setterName);
         descriptors.put("MessageBufferSize", currentResult);
         currentResult.setValue("description", "<p>The amount of memory (in bytes) that this JMS server can use to store message bodies before it writes them to disk. When the JMS server writes the message bodies to disk, it clears them from memory.</p>  <p>A value of -1 (the default) specifies that the server will automatically determine a size based on the maximum heap size of the JVM. This default will be set to either one-third the maximum heap size, or 512 megabytes, whichever is smaller.</p>  <p>The larger the buffer, the more memory JMS will consume when many messages are waiting on queues or topics. Once the buffer is surpassed, JMS may write message bodies to the directory specified by PagingDirectory in an effort to reduce memory usage below this buffer.</p>  <p>Surpassing the buffer size does not stop the JMS server from accepting new messages. It is still possible to run out of memory if messages are arriving faster than they can be written to disk. Users with high messaging loads who wish to support the highest possible availability should consider setting a quota or setting a threshold and enabling flow control.</p>  <p>Paging is always supported.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPagingDirectory")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
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

         currentResult = new PropertyDescriptor("MessageCompressionOptions", JMSServerMBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("MessageCompressionOptionsOverride", JMSServerMBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("MessagesMaximum", JMSServerMBean.class, getterName, setterName);
         descriptors.put("MessagesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of messages that can be stored in this JMS server. A value of <code>-1</code> removes any WebLogic Server limits.</p>  <p>Because excessive message volume can cause memory saturation, Oracle recommends that this value corresponds to the total amount of system memory available after accounting for the rest of your application load.</p>  <p><b>Range of Values:</b> &gt;= MessagesThresholdHigh.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
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

         currentResult = new PropertyDescriptor("MessagesThresholdHigh", JMSServerMBean.class, getterName, setterName);
         descriptors.put("MessagesThresholdHigh", currentResult);
         currentResult.setValue("description", "<p>The upper threshold (number of messages stored in this JMS server) that triggers flow control and logging events. A value of <code>-1</code> disables the events for this JMS server.</p>  <p>If the number of messages exceeds this threshold, the triggered events are:</p> <ul> <li> <code>Log Messages</code> - A message is logged on the server indicating a high threshold condition. </li> <li> <code>Flow Control</code> - If flow control is enabled, the JMS server becomes armed and instructs producers to begin decreasing their message flow. </li> </ul>  <p><b>Range of Values:</b> &lt;= MessagesMaximum; &gt;= MessagesThresholdLow.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
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

         currentResult = new PropertyDescriptor("MessagesThresholdLow", JMSServerMBean.class, getterName, setterName);
         descriptors.put("MessagesThresholdLow", currentResult);
         currentResult.setValue("description", "<p>The lower threshold (number of messages stored in this JMS server) that triggers flow control and logging events. A value of <code>-1</code> disables the events for this JMS server.</p>  <p>If the number of messages falls below this threshold, the triggered events are:</p> <ul> <li> <code>Log Messages</code> - A message is logged on the server indicating that the threshold condition has cleared. </li> <li> <code>Flow Control</code> - If flow control is enabled, the JMS server becomes disarmed and instructs producers to begin increasing their message flow. </li> </ul>  <p><i>Note:</i> This attribute is dynamically configurable.</p>  <p><b>Range of Values:</b> &lt;= MessagesThresholdHigh</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
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

         currentResult = new PropertyDescriptor("Name", JMSServerMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PagingBlockSize")) {
         getterName = "getPagingBlockSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingBlockSize";
         }

         currentResult = new PropertyDescriptor("PagingBlockSize", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PagingBlockSize", currentResult);
         currentResult.setValue("description", "<p>The smallest addressable block, in bytes, of a file. When a native <code>wlfileio</code> driver is available and the paging block size has not been configured by the user, the store selects the minimum OS specific value for unbuffered (direct) I/O, if it is within the range [512, 8192].</p> <p>A paging store's block size does not change once the paging store creates its files. Changes to block size only take effect for new paging stores or after the current files have been deleted. See \"Tuning the Persistent Store\" in <i>Performance and Tuning for Oracle WebLogic Server</i>.</p> <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(8192));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingDirectory")) {
         getterName = "getPagingDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingDirectory";
         }

         currentResult = new PropertyDescriptor("PagingDirectory", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PagingDirectory", currentResult);
         currentResult.setValue("description", "<p> Specifies where message bodies are written when the size of the message bodies in the JMS server exceeds the message buffer size.</p> <p> If unspecified, messages are written to the default <code>tmp</code> directory inside the <code><i>server-name</i></code> subdirectory of a domain's root directory. For example, <code><i>domain-name</i>/servers/<i>server-name</i>/tmp</code>, where <code><i>domain-name</i></code> is the root directory of your domain. </p> <p> For best performance, this directory should not be the same as the directory used by the JMS server's persistent store. </p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMessageBufferSize")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingIoBufferSize")) {
         getterName = "getPagingIoBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingIoBufferSize";
         }

         currentResult = new PropertyDescriptor("PagingIoBufferSize", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PagingIoBufferSize", currentResult);
         currentResult.setValue("description", "<p>The I/O buffer size, in bytes, automatically rounded down to the nearest power of 2.</p> <ul> <li>When a native <code>wlfileio</code> driver is available, the setting applies to off-heap (native) memory.</li> <li>When a native <code>wlfileio</code> driver is not available, the setting applies to JAVA heap memory.</li> <li>For the best runtime performance, Oracle recommends setting <code>PagingIOBufferSize</code> so that it is larger than the largest write (multiple concurrent store requests may be combined into a single write).</li> <li>See the JMS server runtime MBean attribute <code>PagingAllocatedIOBufferBytes</code> to find out the actual allocated off-heap (native) memory amount.</li> </ul>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(67108864));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingMaxFileSize")) {
         getterName = "getPagingMaxFileSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingMaxFileSize";
         }

         currentResult = new PropertyDescriptor("PagingMaxFileSize", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PagingMaxFileSize", currentResult);
         currentResult.setValue("description", "<p>The paging maximum file size, in bytes.</p> <ul> <li>The <code>PagingMaxFileSize</code> value affects the number of files needed to accommodate a paging store of a particular size (number of files = paging store size/MaxFileSize rounded up).</li>  <li>A paging store automatically reuses space freed by deleted records and automatically expands individual files up to <code>PagingMaxFileSize</code> if there is not enough space for a new record. If there is no space left in exiting files for a new record, a paging store creates an additional file.</li>  <li> A small number of larger files is normally preferred over a large number of smaller files as each file allocates Window Buffer and file handles. </li>  <li> If <code>PagingMaxFileSize</code> is larger than 2^24 * <code>PagingBlockSize</code>, then <code>MaxFileSize</code> is ignored, and the value becomes 2^24 * <code>PagingBlockSize</code>. The default <code>PagingBlockSize</code> is 512, and 2^24 * 512 is 8 GB. </li> </ul> <p>Oracle recommends not setting the Paging Max File Size above the default value of 1,342,177,280.  </p> <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(1342177280L));
         currentResult.setValue("legalMin", new Long(10485760L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingMaxWindowBufferSize")) {
         getterName = "getPagingMaxWindowBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingMaxWindowBufferSize";
         }

         currentResult = new PropertyDescriptor("PagingMaxWindowBufferSize", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PagingMaxWindowBufferSize", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of data, in bytes and rounded down to the nearest power of 2, mapped into the JVM's address space per paging store file. Applies only when a native <code>wlfileio</code> library is loaded.</p>  <p>A window buffer does not consume Java heap memory, but does consume off-heap (native) memory. If the paging store is unable to allocate the requested buffer size, it allocates smaller and smaller buffers until it reaches <code>PagingMinWindowBufferSize</code>, and then fails if it cannot honor <code>PagingMinWindowBufferSize</code>.</p>  <p>Oracle recommends setting the max window buffer size to more than double the size of the largest write (multiple concurrently updated records may be combined into a single write), and greater than or equal to the file size, unless there are other constraints. 32-bit JVMs may impose a total limit of between 2 and 4GB for combined Java heap plus off-heap (native) memory usage.</p>  <p>See the JMS server runtime MBean attribute <code>PagingAllocatedWindowBufferBytes</code> to find out the actual allocated Window Buffer Size.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(1073741824));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingMinWindowBufferSize")) {
         getterName = "getPagingMinWindowBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingMinWindowBufferSize";
         }

         currentResult = new PropertyDescriptor("PagingMinWindowBufferSize", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PagingMinWindowBufferSize", currentResult);
         currentResult.setValue("description", "<p>The minimum amount of data, in bytes and rounded down to the nearest power of 2, mapped into the JVM's address space per paging store file.  Applies only when a native <code>wlfileio</code> library is loaded. See <a href='#getPagingMaxWindowBufferSize'>Paging Maximum Window Buffer Size</a>.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(1073741824));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("PagingStore")) {
         getterName = "getPagingStore";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingStore";
         }

         currentResult = new PropertyDescriptor("PagingStore", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PagingStore", currentResult);
         currentResult.setValue("description", "<p>This parameter has been deprecated. New configurations should use the \"PagingDirectory\" parameter if they wish to control where non-persistent messages will be temporarily stored.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPagingDirectory")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced with the PagingDirectory attribute. ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("PersistentStore")) {
         getterName = "getPersistentStore";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentStore";
         }

         currentResult = new PropertyDescriptor("PersistentStore", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PersistentStore", currentResult);
         currentResult.setValue("description", "<p>The file or database in which this JMS server stores persistent messages. If unspecified, the JMS server uses the default persistent store that is configured on each targeted WebLogic Server instance. If the JMS server has a store configured, then the configured store is used to store persistent messages.</p>  <p>The disk-based file store or JDBC-accessible database store that you specify must be targeted to the same server, cluster, or migratable target instance as this JMS server. Multiple services on the same WebLogic Server instance, including multiple JMS servers, may share the same persistent store. Each service's persistent data will be kept apart.</p>  <p>If you specify a PersistentStore, the deprecated <b>Store</b> field must not be set. If neither the <b>PersistentStore</b> field nor the <b>Store</b> field are set, the JMS server supports persistent messaging using the default persistent store for the targeted WebLogic Server instance.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.PersistentStoreMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProductionPausedAtStartup")) {
         getterName = "getProductionPausedAtStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProductionPausedAtStartup";
         }

         currentResult = new PropertyDescriptor("ProductionPausedAtStartup", JMSServerMBean.class, getterName, setterName);
         descriptors.put("ProductionPausedAtStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether production is paused at server startup on destinations targeted to this JMS server. A destination cannot receive any new messages while it is paused.</p>  <p>When the value is set to <code>true</code>, then immediately after the host server instance is rebooted, then this JMS server and its targeted destinations are modified such that they are in a \"production paused\" state, which results in preventing new message production activities on those destinations.</p>  <p>To resume normal new message production activity, later you will have to change the state of this JMS server to a \"production enabled\" state by setting this value to <code>false</code>, and then either redeploy the JMS server or reboot the hosting server instance. </p>  <p>When the value is set to <code>default</code>, then the Production Paused At Startup is determined based on the corresponding setting on the individual destination.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.JMSServerRuntimeMBean#resumeProduction"), BeanInfoHelper.encodeEntities("weblogic.management.runtime.JMSDestinationRuntimeMBean#resumeProduction")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "default");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionPools")) {
         getterName = "getSessionPools";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionPools";
         }

         currentResult = new PropertyDescriptor("SessionPools", JMSServerMBean.class, getterName, setterName);
         descriptors.put("SessionPools", currentResult);
         currentResult.setValue("description", "<p>The session pools defined for the JMS server. This method is provided for backward compatibility.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeSessionPool");
         currentResult.setValue("adder", "addSessionPool");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by message-driven beans. The JMSSessionPoolMBean type was deprecated. See JMSSessionPoolMBean for more information. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("Store")) {
         getterName = "getStore";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStore";
         }

         currentResult = new PropertyDescriptor("Store", JMSServerMBean.class, getterName, setterName);
         descriptors.put("Store", currentResult);
         currentResult.setValue("description", "<p>The persistent disk-based file or JDBC-accessible database for the JMS server.</p>  <p>A persistent store may only be used by one JMS server. If this value is unset, the value set by the \"PersistentStore\" attribute is used. If neither attribute is set, the default persistent store for the targeted managed server is used. It is an error to set both attributes.</p>  <p>This attribute has been deprecated. New configurations should use the \"PersistentStore\" attribute, which allows multiple subsystems, including multiple JMS servers on the same managed server, to share the same persistent store.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPersistentStore")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced with the PersistentStore attribute. ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("StoreEnabled")) {
         getterName = "getStoreEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreEnabled";
         }

         currentResult = new PropertyDescriptor("StoreEnabled", JMSServerMBean.class, getterName, setterName);
         descriptors.put("StoreEnabled", currentResult);
         currentResult.setValue("description", "<p> Specifies whether message persistence is supported for this JMS server.</p> <ul> <li>When set to <code>true</code>: If the JMS server has no store configured, the targeted WebLogic Server instance's default store is used to store persistent messages. If the JMS server has a store configured, then the configured store is used to store persistent messages. </li> <li> When set to <code>false</code>, then this JMS server does not support persistent messages, and instead downgrades them to non-persistent.</li> <li> The default value is <code>true</code>.</li> </ul>  <p>Oracle recommends not setting this parameter to <code>false</code>. It is available to provide compatibility with older releases.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", JMSServerMBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Targets", JMSServerMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>The server instances, clusters, or a migratable targets defined in the current domain that are candidates for hosting the JMSSerer.</p>  <p>In a clustered environment, a recommended best practice is to use a cluster as a target or target a JMSServer to the same migratable target as the Persistent Store that it uses, so that a member server will not be a single point of failure. A JMSServer can also be configured to automatically migrate from an unhealthy server instance to a healthy server instance with the help of the automatic service migration feature.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("TemporaryTemplate")) {
         getterName = "getTemporaryTemplate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTemporaryTemplate";
         }

         currentResult = new PropertyDescriptor("TemporaryTemplate", JMSServerMBean.class, getterName, setterName);
         descriptors.put("TemporaryTemplate", currentResult);
         currentResult.setValue("description", "<p>The name of an existing JMS template to use when creating all temporary queues and topics for this JMS server. Specifying a value for this field allows JMS applications to create temporary destinations. If Store values are provided as part of a temporary template, they are ignored, because temporary destinations do not support persistent messaging.</p>  <p><i>Note:</i> If this attribute is set to none, attempts to create a temporary destination (queue or topic) will fail.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced with the TemporaryTemplateName and TemporaryTemplateResource attributes. ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("TemporaryTemplateName")) {
         getterName = "getTemporaryTemplateName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTemporaryTemplateName";
         }

         currentResult = new PropertyDescriptor("TemporaryTemplateName", JMSServerMBean.class, getterName, setterName);
         descriptors.put("TemporaryTemplateName", currentResult);
         currentResult.setValue("description", "<p>The name of a configured JMS template that this JMS server uses to create temporary destinations.</p>  <p>Entering a template name, requires you to specify the JMS module that contains this template. However, a template name cannot be specified if the <b>Hosting Temporary Destinations</b> field is disabled.</p>  <p><b>Note:</b> If the specified JMS template provides persistent store values, they are ignored because temporary destinations do not support persistent messaging.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.JMSSystemResourceMBean"), BeanInfoHelper.encodeEntities("weblogic.j2ee.descriptor.wl.TemplateBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TemporaryTemplateResource")) {
         getterName = "getTemporaryTemplateResource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTemporaryTemplateResource";
         }

         currentResult = new PropertyDescriptor("TemporaryTemplateResource", JMSServerMBean.class, getterName, setterName);
         descriptors.put("TemporaryTemplateResource", currentResult);
         currentResult.setValue("description", "<p>The name of a JMS module that contains a template that this JMS server can use to create temporary destinations.</p>  <p>Entering a JMS module name requires you to specify a temporary template name. However, a module name cannot be specified if the <b>Hosting Temporary Destinations</b> field is disabled.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.JMSSystemResourceMBean"), BeanInfoHelper.encodeEntities("weblogic.j2ee.descriptor.wl.TemplateBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowsPersistentDowngrade")) {
         getterName = "isAllowsPersistentDowngrade";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowsPersistentDowngrade";
         }

         currentResult = new PropertyDescriptor("AllowsPersistentDowngrade", JMSServerMBean.class, getterName, setterName);
         descriptors.put("AllowsPersistentDowngrade", currentResult);
         currentResult.setValue("description", "<p>Specifies whether JMS clients will get an exception when sending persistent messages to a destination targeted to a JMS server that does not have a persistent store configured. This parameter only has effect when the Store Enabled parameter is disabled (false).</p>  <p>When set to false, the default, clients will get an exception when sending persistent messages to a JMS server with no store configured. When set to true, then persistent messages are downgraded to non-persistent; however, the send operations are allowed to continue.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesPagingEnabled")) {
         getterName = "isBytesPagingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesPagingEnabled";
         }

         currentResult = new PropertyDescriptor("BytesPagingEnabled", JMSServerMBean.class, getterName, setterName);
         descriptors.put("BytesPagingEnabled", currentResult);
         currentResult.setValue("description", "<p>This parameter has been deprecated. Paging is always enabled. The \"MessageBufferSize\" parameter controls how much memory is used before paging kicks in.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMessageBufferSize")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by defaulting the paging to always be enabled. ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", JMSServerMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("HostingTemporaryDestinations")) {
         getterName = "isHostingTemporaryDestinations";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHostingTemporaryDestinations";
         }

         currentResult = new PropertyDescriptor("HostingTemporaryDestinations", JMSServerMBean.class, getterName, setterName);
         descriptors.put("HostingTemporaryDestinations", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this JMS server can be used to host temporary destinations.</p>  <p>If this field is enabled and no <b>Temporary Template Name</b> is defined, then the temporary destinations created on this JMS server will use all default destination values. If this field is enabled, then the JMS template to be used for creating temporary destinations is specified by the <b>Temporary Template Name</b> field. If this field is disabled, then this JMS server will not host temporary destinations.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesPagingEnabled")) {
         getterName = "isMessagesPagingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesPagingEnabled";
         }

         currentResult = new PropertyDescriptor("MessagesPagingEnabled", JMSServerMBean.class, getterName, setterName);
         descriptors.put("MessagesPagingEnabled", currentResult);
         currentResult.setValue("description", "<p>This parameter has been deprecated. Paging is always enabled. The \"MessageBufferSize\" parameter controls how much memory is used before paging kicks in.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMessageBufferSize")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by defaulting the paging to always be enabled. ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingFileLockingEnabled")) {
         getterName = "isPagingFileLockingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingFileLockingEnabled";
         }

         currentResult = new PropertyDescriptor("PagingFileLockingEnabled", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PagingFileLockingEnabled", currentResult);
         currentResult.setValue("description", "<p>Determines whether OS file locking is used. </p> <p>When file locking protection is enabled, a store boot fails if another store instance already has opened the store files. Do not disable this setting unless you have procedures in place to prevent multiple store instances from opening the same file. File locking is not required but helps prevent corruption in the event that two same-named file store instances attempt to operate in the same directories. This setting applies to both primary and cache files.</p>  <p>If the value is changed and activated, you need to restart any servers to which the MBean is targeted before the value will take effect (even though you do not get a warning in the Console that the server needs to be restarted).</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingMessageCompressionEnabled")) {
         getterName = "isPagingMessageCompressionEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingMessageCompressionEnabled";
         }

         currentResult = new PropertyDescriptor("PagingMessageCompressionEnabled", JMSServerMBean.class, getterName, setterName);
         descriptors.put("PagingMessageCompressionEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables the JMS paging store to perform message body compression on persistent and non-persistent messages. When <code>false</code>, the default value, no compression is performed.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMessageCompressionOptions")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreMessageCompressionEnabled")) {
         getterName = "isStoreMessageCompressionEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreMessageCompressionEnabled";
         }

         currentResult = new PropertyDescriptor("StoreMessageCompressionEnabled", JMSServerMBean.class, getterName, setterName);
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
      Method mth = JMSServerMBean.class.getMethod("createJMSSessionPool", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the sessionPool to add to the JMS server ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by message-driven beans. The JMSSessionPoolMBean type was deprecated. See JMSSessionPoolMBean for more information. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Add a session pool to the JMS server.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSSessionPools");
      }

      mth = JMSServerMBean.class.getMethod("destroyJMSSessionPool", JMSSessionPoolMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sessionPool", "the sessionPool to remove from the JMS server ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by message-driven beans. The JMSSessionPoolMBean type was deprecated. See JMSSessionPoolMBean for more information. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Remove a session pool from the JMS server.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSSessionPools");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("createJMSQueue", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSQueue object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("createJMSQueue", String.class, JMSQueueMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toClone", "is the JMSQueueMBean that is being moved from the DomainMBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSQueue object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("destroyJMSQueue", JMSQueueMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("queue", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSQueue from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("createJMSTopic", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSTopic object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSTopics");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("destroyJMSTopic", JMSTopicMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("topic", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSTopic from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSTopics");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSServerMBean.class.getMethod("addTarget", TargetMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The target where the JMSServer needs to be deployed ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Targets the JMSServer to the specified target instance. The target must be either a server, cluster, or a migratable target.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = JMSServerMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The target that has to be removed from the JMSServer ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Untargets the JMSServer instance from the current target.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addTarget")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = JMSServerMBean.class.getMethod("addSessionPool", JMSSessionPoolMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sessionPool", "the sessionPool to add to the JMS server ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by message-driven beans. The JMSSessionPoolMBean type was deprecated. See JMSSessionPoolMBean for more information. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Add a session pool to the JMS server. This method is provided for backward compatibility.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SessionPools");
      }

      mth = JMSServerMBean.class.getMethod("removeSessionPool", JMSSessionPoolMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sessionPool", "the sessionPool to remove from the JMS server ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by message-driven beans. The JMSSessionPoolMBean type was deprecated. See JMSSessionPoolMBean for more information. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Remove a session pool from the JMS server. This method is provided for backward compatibility.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "SessionPools");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("addDestination", JMSDestinationMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destination", "the destination to be added to the JMS server ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if destination is null or exists")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the functionality of JMS modules. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a JMS destination to the JMS server.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Destinations");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("removeDestination", JMSDestinationMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destination", "the destination to be removed from the JMS server ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if destination is null or does not exist")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the functionality of JMS modules. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a destination from the JNDI tree.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Destinations");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("addTag", String.class);
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
         mth = JMSServerMBean.class.getMethod("removeTag", String.class);
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
      Method mth = JMSServerMBean.class.getMethod("lookupJMSSessionPool", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by message-driven beans. The JMSSessionPoolMBean type was deprecated. See JMSSessionPoolMBean for more information. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Get a session pool by name from the JMS server.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSSessionPools");
      }

      String methodKey;
      ParameterDescriptor[] parameterDescriptors;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("lookupJMSQueue", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("lookupJMSTopic", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSTopics");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSServerMBean.class.getMethod("createJMSSessionPool", String.class, JMSSessionPoolMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toClone", "the JMSSessionPool that is being cloned. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create JMSTopic object</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerMBean.class.getMethod("freezeCurrentValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSServerMBean.class.getMethod("restoreDefaultValue", String.class);
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

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("setDestinations", JMSDestinationMBean[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destinations", "The new destinations value ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the functionality of JMS modules. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Sets the value of the Destinations attribute.</p> ");
            String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getDestinations")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSServerMBean.class.getMethod("createJMSTopic", String.class, JMSTopicMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced with the JMS module functionality. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSTopic object</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
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
