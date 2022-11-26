package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class LogMBeanImplBeanInfo extends CommonLogMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LogMBean.class;

   public LogMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LogMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.LogMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Configures the threshold severity level and filter settings for logging output.</p> <p>Specifies whether the server logging is based on a Log4j implementation or the default Java Logging APIs.</p> <p>Redirects the JVM stdout and stderr output to the registered log destinations.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.LogMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DomainLogBroadcastFilter")) {
         getterName = "getDomainLogBroadcastFilter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDomainLogBroadcastFilter";
         }

         currentResult = new PropertyDescriptor("DomainLogBroadcastFilter", LogMBean.class, getterName, setterName);
         descriptors.put("DomainLogBroadcastFilter", currentResult);
         currentResult.setValue("description", "<p>The filter configuration for log events being sent to the domain log.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DomainLogBroadcastSeverity")) {
         getterName = "getDomainLogBroadcastSeverity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDomainLogBroadcastSeverity";
         }

         currentResult = new PropertyDescriptor("DomainLogBroadcastSeverity", LogMBean.class, getterName, setterName);
         descriptors.put("DomainLogBroadcastSeverity", currentResult);
         currentResult.setValue("description", "<p>The minimum severity of log messages going to the domain log from this server's log broadcaster. Messages with a lower severity than the specified value will not be published to the domain log.</p> ");
         setPropertyDescriptorDefault(currentResult, "Notice");
         currentResult.setValue("legalValues", new Object[]{"Debug", "Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency", "Off"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DomainLogBroadcasterBufferSize")) {
         getterName = "getDomainLogBroadcasterBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDomainLogBroadcasterBufferSize";
         }

         currentResult = new PropertyDescriptor("DomainLogBroadcasterBufferSize", LogMBean.class, getterName, setterName);
         descriptors.put("DomainLogBroadcasterBufferSize", currentResult);
         currentResult.setValue("description", "<p>Broadcasts log messages to the domain log in batch mode.</p>  <p>The size of the buffer for log messages that are sent to the domain log. The buffer is maintained on the Managed Server and is broadcasted to the domain log when it is full.</p>  <p>If you notice performance issues due to a high rate of log messages being generated, set this value higher. This will cause the buffer to be broadcasted less frequently from the Managed Server to the domain log. In production environments, it is not recommended to set the buffer size lower than the production default of 10.</p> ");
         currentResult.setValue("restProductionModeDefault", new Integer(10));
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogFileFilter")) {
         getterName = "getLogFileFilter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileFilter";
         }

         currentResult = new PropertyDescriptor("LogFileFilter", LogMBean.class, getterName, setterName);
         descriptors.put("LogFileFilter", currentResult);
         currentResult.setValue("description", "<p>The filter configuration for the server log file.</p>  <p>A filter configuration defines simple filtering rules to limit the volume of log messages written to the log file.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogMonitoringIntervalSecs")) {
         getterName = "getLogMonitoringIntervalSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogMonitoringIntervalSecs";
         }

         currentResult = new PropertyDescriptor("LogMonitoringIntervalSecs", LogMBean.class, getterName, setterName);
         descriptors.put("LogMonitoringIntervalSecs", currentResult);
         currentResult.setValue("description", "Timer interval in seconds to check the counts of messages logged during the interval. ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMin", new Integer(5));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogMonitoringMaxThrottleMessageSignatureCount")) {
         getterName = "getLogMonitoringMaxThrottleMessageSignatureCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogMonitoringMaxThrottleMessageSignatureCount";
         }

         currentResult = new PropertyDescriptor("LogMonitoringMaxThrottleMessageSignatureCount", LogMBean.class, getterName, setterName);
         descriptors.put("LogMonitoringMaxThrottleMessageSignatureCount", currentResult);
         currentResult.setValue("description", "Maximum number of unique message signatures that will be monitored during the throttle interval. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("legalMax", new Integer(5000));
         currentResult.setValue("legalMin", new Integer(100));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogMonitoringThrottleMessageLength")) {
         getterName = "getLogMonitoringThrottleMessageLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogMonitoringThrottleMessageLength";
         }

         currentResult = new PropertyDescriptor("LogMonitoringThrottleMessageLength", LogMBean.class, getterName, setterName);
         descriptors.put("LogMonitoringThrottleMessageLength", currentResult);
         currentResult.setValue("description", "Cut-off length of the log message for evaluation during throttle period. The log message length is truncated to this length for evaluation of repeated logging events. ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(500));
         currentResult.setValue("legalMin", new Integer(10));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogMonitoringThrottleThreshold")) {
         getterName = "getLogMonitoringThrottleThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogMonitoringThrottleThreshold";
         }

         currentResult = new PropertyDescriptor("LogMonitoringThrottleThreshold", LogMBean.class, getterName, setterName);
         descriptors.put("LogMonitoringThrottleThreshold", currentResult);
         currentResult.setValue("description", "The threshold number of messages logged during the evaluation period which enables or disables the throttling. <p> Once throttling is enabled messages with the same repeating message signatures are throttled. Every nth repeated message specified by this attribute is logged in a monitoring cycle. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1500));
         currentResult.setValue("legalMin", new Integer(5));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MemoryBufferFilter")) {
         getterName = "getMemoryBufferFilter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemoryBufferFilter";
         }

         currentResult = new PropertyDescriptor("MemoryBufferFilter", LogMBean.class, getterName, setterName);
         descriptors.put("MemoryBufferFilter", currentResult);
         currentResult.setValue("description", "<p>The filter configuration for messages that are stored in the log memory buffer. By default, all log messages are cached.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "12.1.3.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MemoryBufferSeverity")) {
         getterName = "getMemoryBufferSeverity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemoryBufferSeverity";
         }

         currentResult = new PropertyDescriptor("MemoryBufferSeverity", LogMBean.class, getterName, setterName);
         descriptors.put("MemoryBufferSeverity", currentResult);
         currentResult.setValue("description", "<p>The minimum severity of log messages going to the memory buffer of recent log events. Messages with a lower severity than the specified value will not be cached in the buffer.</p> ");
         setPropertyDescriptorDefault(currentResult, "Trace");
         currentResult.setValue("legalValues", new Object[]{"Trace", "Debug", "Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency", "Off"});
         currentResult.setValue("deprecated", "12.1.3.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MemoryBufferSize")) {
         getterName = "getMemoryBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemoryBufferSize";
         }

         currentResult = new PropertyDescriptor("MemoryBufferSize", LogMBean.class, getterName, setterName);
         descriptors.put("MemoryBufferSize", currentResult);
         currentResult.setValue("description", "<p>The size of the memory buffer that holds the last n log records. This is used to support viewing the most recent log record entries (tail viewing) from the WebLogic Administration Console.</p> ");
         currentResult.setValue("restProductionModeDefault", new Integer(500));
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(5000));
         currentResult.setValue("legalMin", new Integer(10));
         currentResult.setValue("deprecated", "12.1.3.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", LogMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("PlatformLoggerLevels")) {
         getterName = "getPlatformLoggerLevels";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatformLoggerLevels";
         }

         currentResult = new PropertyDescriptor("PlatformLoggerLevels", LogMBean.class, getterName, setterName);
         descriptors.put("PlatformLoggerLevels", currentResult);
         currentResult.setValue("description", "<p>Specifies the platform logger and the associated level names set through the WebLogic Server configuration.</p>  <p>Note the following behavior:</p> <ul> <li>The configuration is applicable for <code>java.util.logging.Logger</code> instances in the JDK's default global LogManager.</li> <li>WebLogic Server loggers are configured in terms of <code>weblogic.logging.Severities</code> through the <code>LoggerSeverities</code> attribute. These loggers are not available in the JDK's default global LogManager.</li> <li>If your WebLogic domain includes Oracle JRF and is configured to use Oracle Diagnostic Logging (ODL), the <code>java.util.logging</code> levels set on the <code>LogMBean.PlatformLoggerLevels</code> attribute are ignored. For information about how to configure ODL logging for JDK platform loggers, see <a href=\"http://www.oracle.com/pls/topic/lookup?ctx=wls14110&amp;id=ASADM215\" rel=\"noopener noreferrer\" target=\"_blank\">Managing Log Files and Diagnostics Data</a> in <i>Administering Oracle Fusion Middleware</i>.</li> </ul> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getLoggerSeverityProperties()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StdoutFilter")) {
         getterName = "getStdoutFilter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutFilter";
         }

         currentResult = new PropertyDescriptor("StdoutFilter", LogMBean.class, getterName, setterName);
         descriptors.put("StdoutFilter", currentResult);
         currentResult.setValue("description", "<p>The filter configuration for log events being sent to the standard out.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", LogMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", LogMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Log4jLoggingEnabled")) {
         getterName = "isLog4jLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLog4jLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("Log4jLoggingEnabled", LogMBean.class, getterName, setterName);
         descriptors.put("Log4jLoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the server logging is based on a Log4j implementation. By default, WebLogic logging uses an implementation based on the Java Logging APIs which are part of the JDK.</p>  <p>Applications that use the WebLogic Message Catalog framework or the NonCatalogLogger will not be affected by the underlying Logging implementation.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "12.1.3.0 ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogMonitoringEnabled")) {
         getterName = "isLogMonitoringEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogMonitoringEnabled";
         }

         currentResult = new PropertyDescriptor("LogMonitoringEnabled", LogMBean.class, getterName, setterName);
         descriptors.put("LogMonitoringEnabled", currentResult);
         currentResult.setValue("description", "Enable or disable log monitoring. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RedirectStderrToServerLogEnabled")) {
         getterName = "isRedirectStderrToServerLogEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedirectStderrToServerLogEnabled";
         }

         currentResult = new PropertyDescriptor("RedirectStderrToServerLogEnabled", LogMBean.class, getterName, setterName);
         descriptors.put("RedirectStderrToServerLogEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the stderr of the JVM in which a WebLogic Server instance runs is redirected to the WebLogic Logging system. When this attribute is enabled, the stderr content is published to all the registered log destinations, such as the server terminal console and log file.</p>  <p>Note that JVM messages are redirected asynchronously. In the event of an overload situation, these messages may be dropped. As a best practice, Oracle recommends using one of the supported logging APIs instead.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RedirectStdoutToServerLogEnabled")) {
         getterName = "isRedirectStdoutToServerLogEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedirectStdoutToServerLogEnabled";
         }

         currentResult = new PropertyDescriptor("RedirectStdoutToServerLogEnabled", LogMBean.class, getterName, setterName);
         descriptors.put("RedirectStdoutToServerLogEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the stdout of the JVM in which a WebLogic Server instance runs is redirected to the WebLogic logging system. When this attribute is enabled, the stdout content is published to all the registered log destinations, such as the server terminal console and log file.</p>  <p>Note that JVM messages are redirected asynchronously. In the event of an overload situation, these messages may be dropped. As a best practice, Oracle recommends using one of the supported logging APIs instead.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("ServerLoggingBridgeUseParentLoggersEnabled")) {
         getterName = "isServerLoggingBridgeUseParentLoggersEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerLoggingBridgeUseParentLoggersEnabled";
         }

         currentResult = new PropertyDescriptor("ServerLoggingBridgeUseParentLoggersEnabled", LogMBean.class, getterName, setterName);
         descriptors.put("ServerLoggingBridgeUseParentLoggersEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether application log messages are propagated to the parent node in the Logger tree or to the WebLogic Server log by means of the Logging Bridge. By default, this attribute is disabled, which results in application log messages being propagated to the WebLogic Server log.</p>  <p>Note the following behavior:</p> <ul> <li>If WebLogic Server is configured to be based upon the the Java Logging API (the default), the Logging Bridge is made available as a <code>java.util.logging.Handler</code> object.</li> <li>If  WebLogic Server is configured to be based upon a Log4j implementation, the Logging Bridge is made available as a <code>org.apache.log4j.Appender</code> object.</li> <li>If the <code>ServerLoggingBridgeUseParentLoggersEnabled</code> is disabled, applications that use either the Java Logging API or Log4j have their log messages redirected by the Logging Bridge to the WebLogic Server log. </li> <li>If the <code>ServerLoggingBridgeUseParentLoggersEnabled</code> is enabled, applications that use the Java Logging API have their log messages propagated to the parent node in the global Java Logging Logger tree.</li> <li>If the <code>ServerLoggingBridgeUseParentLoggersEnabled</code> is enabled, applications that use Log4j have their log messages propagated to the parent node in the Log4j Logger tree.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "12.1.3.0 ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = LogMBean.class.getMethod("addTag", String.class);
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
         mth = LogMBean.class.getMethod("removeTag", String.class);
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
      Method mth = LogMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = LogMBean.class.getMethod("restoreDefaultValue", String.class);
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

      mth = LogMBean.class.getMethod("computeLogFilePath");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This method computes the log file path based on the defaults and server directory if the FileName attribute is defaulted. ");
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
