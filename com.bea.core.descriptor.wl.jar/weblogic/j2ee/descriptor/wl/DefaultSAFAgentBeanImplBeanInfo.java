package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DefaultSAFAgentBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DefaultSAFAgentBean.class;

   public DefaultSAFAgentBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DefaultSAFAgentBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DefaultSAFAgentBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.2.0");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("This class represents a Store-and-Forward (SAF) agent. A SAF sending agent takes care of storing messages to a persistent storage, forwarding messages to the receiving side, and re-transmitting messages when acknowledgements do not come back in time. A SAF receiving agent takes care of detecting and eliminating duplicate messages sent by the receiving agent, and deliver messages to the final endpoint.  <h3 class=\"TypeSafeDeprecation\">Deprecation of MBeanHome and Type-Safe Interfaces</h3>  <p class=\"TypeSafeDeprecation\">This is a type-safe interface for a WebLogic Server MBean, which you can import into your client classes and access through <code>weblogic.management.MBeanHome</code>. As of 9.0, the <code>MBeanHome</code> interface and all type-safe interfaces for WebLogic Server MBeans are deprecated. Instead, client classes that interact with WebLogic Server MBeans should use standard JMX design patterns in which clients use the <code>javax.management.MBeanServerConnection</code> interface to discover MBeans, attributes, and attribute types at runtime.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DefaultSAFAgentBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BytesMaximum")) {
         getterName = "getBytesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesMaximum";
         }

         currentResult = new PropertyDescriptor("BytesMaximum", DefaultSAFAgentBean.class, getterName, setterName);
         descriptors.put("BytesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum bytes quota (total amount of bytes) that can be stored in this SAF agent.</p>  <ul> <li> <p>The default value of <code>-1</code> specifies that there is no WebLogic-imposed limit on the number of bytes that can be stored. However, excessive bytes volume can cause memory saturation, so this value should correspond to the total amount of available system memory relative to the rest of your application load.</p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Updating <code>BytesMaximum</code> affects only new requests.</p> </li> </ul>  <p><b>Range of Values:</b> &gt;= BytesThresholdHigh</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultRetryDelayBase")) {
         getterName = "getDefaultRetryDelayBase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRetryDelayBase";
         }

         currentResult = new PropertyDescriptor("DefaultRetryDelayBase", DefaultSAFAgentBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("DefaultRetryDelayMaximum", DefaultSAFAgentBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("DefaultRetryDelayMultiplier", DefaultSAFAgentBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("DefaultTimeToLive", DefaultSAFAgentBean.class, getterName, setterName);
         descriptors.put("DefaultTimeToLive", currentResult);
         currentResult.setValue("description", "<p>The default amount of time, in milliseconds, that the agent guarantees to reliably send messages.</p>  <ul> <li> <p>A value of 0 means the agent guarantees to reliably send messages for the duration of the conversation.</p> </li>  <li> <p>Updating <code>DefaultTimeToLive</code> causes conversations starting after the update to use the new value.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumMessageSize")) {
         getterName = "getMaximumMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumMessageSize";
         }

         currentResult = new PropertyDescriptor("MaximumMessageSize", DefaultSAFAgentBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("MessageBufferSize", DefaultSAFAgentBean.class, getterName, setterName);
         descriptors.put("MessageBufferSize", currentResult);
         currentResult.setValue("description", "<p>The amount of memory used to store message bodies in memory before they are paged out to disk.</p> <ul> <li> <p>A value of -1 (the default) specifies that the server will automatically determine a size based on the maximum heap size of the JVM. This default will be set to either one-third the maximum heap size, or 512 megabytes, whichever is larger.</p> </li>  <li> <p>The larger the buffer, the more memory JMS will consume when many messages are waiting on queues or topics. Once the buffer is surpassed, JMS may write message bodies to the directory specified by PagingDirectory in an effort to reduce memory usage below this buffer.</p> </li>  <li> <p>Surpassing the buffer size does not stop the JMS server from accepting new messages. It is still possible to run out of memory if messages are arriving faster than they can be written to disk. Users with high messaging loads who wish to support the highest possible availability should consider setting a quota or setting a threshold and enabling flow control.</p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li>  <li> <p>Paging is always supported. </p> </li>  <li> <p>Updating the memory size resets the message count so that only requests arriving after the update are charged against the new memory size.</p> </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPagingDirectory")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesMaximum")) {
         getterName = "getMessagesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesMaximum";
         }

         currentResult = new PropertyDescriptor("MessagesMaximum", DefaultSAFAgentBean.class, getterName, setterName);
         descriptors.put("MessagesMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum message quota (total amount of messages) that can be stored in this SAF agent.</p>  <ul> <li> <p>The default value of <code>-1</code> specifies that the server instance has no limit on the number of messages that can be stored. However, excessive message volume can cause memory saturation, so this value should correspond to the total amount of available system memory relative to the rest of your application load.</p> </li>  <li> <p>Applies only to sending agents.</p> </li>  <li> <p>Updating the maximum message quota resets the message count so that only requests arriving after the update are charged against the new quota.</p> </li> </ul>  <p><b>Range of Values:</b> &gt;= MessagesThresholdHigh</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Notes")) {
         getterName = "getNotes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotes";
         }

         currentResult = new PropertyDescriptor("Notes", DefaultSAFAgentBean.class, getterName, setterName);
         descriptors.put("Notes", currentResult);
         currentResult.setValue("description", "<p>Optional information that you can include to describe this named JMS descriptor bean.</p>  <p>JMS module saves this note in the JMS descriptor file as XML PCDATA. All left angle brackets (&lt;) are converted to the XML entity <code>&amp;lt;</code>. Carriage returns/line feeds are preserved.</p>  <dl> <dt>Note:</dt>  <dd> <p>If you create or edit a note from the Administration Console, the Administration Console does not preserve carriage returns/line feeds.</p> </dd> </dl> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PagingDirectory")) {
         getterName = "getPagingDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPagingDirectory";
         }

         currentResult = new PropertyDescriptor("PagingDirectory", DefaultSAFAgentBean.class, getterName, setterName);
         descriptors.put("PagingDirectory", currentResult);
         currentResult.setValue("description", "<p> Specifies where message bodies are written when the size of the message bodies in the JMS server exceeds the message buffer size. If unspecified, messages are written to the <code>tmp</code> directory in the host WebLogic Server instance's directory. For example, <code><em>domainName</em>/servers/<em>servername</em>/tmp</code>. </p>  <ul> <li> <p>For best performance, this directory should not be the same as the directory used by the SAF agent's persistent store.</p> </li>  <li> <p>Applies only to agents with sending capability.</p> </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMessageBufferSize")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WindowInterval")) {
         getterName = "getWindowInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWindowInterval";
         }

         currentResult = new PropertyDescriptor("WindowInterval", DefaultSAFAgentBean.class, getterName, setterName);
         descriptors.put("WindowInterval", currentResult);
         currentResult.setValue("description", "<p>The default amount of time, in milliseconds, that a JMS sending agent waits before forwarding messages in a single batch. For a distributed queue or topic, the <code>WindowInterval</code> setting is ignored.</p> ");
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

         currentResult = new PropertyDescriptor("WindowSize", DefaultSAFAgentBean.class, getterName, setterName);
         descriptors.put("WindowSize", currentResult);
         currentResult.setValue("description", "<p>The default number of messages (batch size) that a sending agent waits to forward a message batch until the source destination message count is greater than or equal to this value. For a distributed queue or topic, <code>WindowSize</code> setting is ignored and always internally set to 1 message.</p>  <p>Updating <code>WindowSize</code> causes connections starting after the update to use the new value.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoggingEnabled")) {
         getterName = "isLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("LoggingEnabled", DefaultSAFAgentBean.class, getterName, setterName);
         descriptors.put("LoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a message is logged in the server log file when a message fails to be forwarded.</p>  <ul> <li> <p>If selected, this applies to messages that fail to be forwarded and delivered to the final destination when there is no error handling in a SAF application or the application's error handling fails.</p> </li>  <li> <p>Applies only to agents with receiving capability.</p> </li>  <li> <p>Updating <code>LoggingEnabled</code> causes connections starting after the update to use the new value.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "since WebLogic 9.0.1 release ");
         currentResult.setValue("dynamic", Boolean.TRUE);
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
