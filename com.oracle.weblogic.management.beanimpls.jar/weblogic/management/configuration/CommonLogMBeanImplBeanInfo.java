package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CommonLogMBeanImplBeanInfo extends LogFileMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CommonLogMBean.class;

   public CommonLogMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CommonLogMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CommonLogMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Configures the basic configuration for the logging system.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CommonLogMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogFileSeverity")) {
         getterName = "getLogFileSeverity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileSeverity";
         }

         currentResult = new PropertyDescriptor("LogFileSeverity", CommonLogMBean.class, getterName, setterName);
         descriptors.put("LogFileSeverity", currentResult);
         currentResult.setValue("description", "<p>The minimum severity of log messages going to the server log file. By default all messages go to the log file.</p> ");
         setPropertyDescriptorDefault(currentResult, "Trace");
         currentResult.setValue("legalValues", new Object[]{"Trace", "Debug", "Info", "Notice", "Warning"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.2.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LoggerSeverity")) {
         getterName = "getLoggerSeverity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoggerSeverity";
         }

         currentResult = new PropertyDescriptor("LoggerSeverity", CommonLogMBean.class, getterName, setterName);
         descriptors.put("LoggerSeverity", currentResult);
         currentResult.setValue("description", "<p>The minimum severity of log messages going to all log destinations.</p> ");
         setPropertyDescriptorDefault(currentResult, "Info");
         currentResult.setValue("legalValues", new Object[]{"Trace", "Debug", "Info", "Notice", "Warning"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.2.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LoggerSeverityProperties")) {
         getterName = "getLoggerSeverityProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoggerSeverityProperties";
         }

         currentResult = new PropertyDescriptor("LoggerSeverityProperties", CommonLogMBean.class, getterName, setterName);
         descriptors.put("LoggerSeverityProperties", currentResult);
         currentResult.setValue("description", "<p>The configuration of the different logger severities keyed by name. The values are one of the predefined Severity strings namely Emergency, Alert, Critical, Error, Warning, Notice, Info, Debug, Trace.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.0.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", CommonLogMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StacktraceDepth")) {
         getterName = "getStacktraceDepth";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStacktraceDepth";
         }

         currentResult = new PropertyDescriptor("StacktraceDepth", CommonLogMBean.class, getterName, setterName);
         descriptors.put("StacktraceDepth", currentResult);
         currentResult.setValue("description", "<p>Determines the no of stacktrace frames to display on standard out. All frames are displayed in the log file. * -1 means all frames are displayed.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StdoutFormat")) {
         getterName = "getStdoutFormat";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutFormat";
         }

         currentResult = new PropertyDescriptor("StdoutFormat", CommonLogMBean.class, getterName, setterName);
         descriptors.put("StdoutFormat", currentResult);
         currentResult.setValue("description", "<p>The output format to use when logging to the console.</p> ");
         setPropertyDescriptorDefault(currentResult, "standard");
         currentResult.setValue("legalValues", new Object[]{"standard", "noid"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StdoutSeverity")) {
         getterName = "getStdoutSeverity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutSeverity";
         }

         currentResult = new PropertyDescriptor("StdoutSeverity", CommonLogMBean.class, getterName, setterName);
         descriptors.put("StdoutSeverity", currentResult);
         currentResult.setValue("description", "<p>The minimum severity of log messages going to the standard out. Messages with a lower severity than the specified value will not be published to standard out.</p> ");
         setPropertyDescriptorDefault(currentResult, "Notice");
         currentResult.setValue("legalValues", new Object[]{"Trace", "Debug", "Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency", "Off"});
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

         currentResult = new PropertyDescriptor("Tags", CommonLogMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", CommonLogMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StdoutLogStack")) {
         getterName = "isStdoutLogStack";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStdoutLogStack";
         }

         currentResult = new PropertyDescriptor("StdoutLogStack", CommonLogMBean.class, getterName, setterName);
         descriptors.put("StdoutLogStack", currentResult);
         currentResult.setValue("description", "<p>Specifies whether to dump stack traces to the console when included in logged message.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
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
         mth = CommonLogMBean.class.getMethod("addTag", String.class);
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
         mth = CommonLogMBean.class.getMethod("removeTag", String.class);
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
      Method mth = CommonLogMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = CommonLogMBean.class.getMethod("restoreDefaultValue", String.class);
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

      mth = CommonLogMBean.class.getMethod("computeLogFilePath");
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
