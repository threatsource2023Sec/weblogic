package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class LogFileMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LogFileMBean.class;

   public LogFileMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LogFileMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.LogFileMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Configures the location, file-rotation criteria, and number of files that a WebLogic Server uses to store log messages. The methods in this class configure both server and domain log files. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.LogFileMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BufferSizeKB")) {
         getterName = "getBufferSizeKB";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBufferSizeKB";
         }

         currentResult = new PropertyDescriptor("BufferSizeKB", LogFileMBean.class, getterName, setterName);
         descriptors.put("BufferSizeKB", currentResult);
         currentResult.setValue("description", "Gets the underlying log buffer size in kilobytes ");
         setPropertyDescriptorDefault(currentResult, new Integer(8));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DateFormatPattern")) {
         getterName = "getDateFormatPattern";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDateFormatPattern";
         }

         currentResult = new PropertyDescriptor("DateFormatPattern", LogFileMBean.class, getterName, setterName);
         descriptors.put("DateFormatPattern", currentResult);
         currentResult.setValue("description", "<p>The date format pattern used for rendering dates in the  log. The DateFormatPattern string conforms to the specification of the <code>java.text.SimpleDateFormat</code> class.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("FileCount")) {
         getterName = "getFileCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileCount";
         }

         currentResult = new PropertyDescriptor("FileCount", LogFileMBean.class, getterName, setterName);
         descriptors.put("FileCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of log files that the server creates when it rotates the log. This number does not include the file that the server uses to store current messages. (Requires that you enable Number of Files Limited.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isNumberOfFilesLimited")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restProductionModeDefault", new Integer(100));
         setPropertyDescriptorDefault(currentResult, new Integer(7));
         currentResult.setValue("legalMax", new Integer(99999));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileMinSize")) {
         getterName = "getFileMinSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileMinSize";
         }

         currentResult = new PropertyDescriptor("FileMinSize", LogFileMBean.class, getterName, setterName);
         descriptors.put("FileMinSize", currentResult);
         currentResult.setValue("description", "The size (1 - 2097150 kilobytes) that triggers the server to move log messages to a separate file. The default is 500 kilobytes. After the log file reaches the specified minimum size, the next time the server checks the file size, it will rename the current log file as <code><i>SERVER_NAME</i>.log<i>nnnnn</i></code> and create a new one to store subsequent messages. (Requires that you specify a file rotation type of <code>Size</code>.) ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getRotationType")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restProductionModeDefault", new Integer(5000));
         setPropertyDescriptorDefault(currentResult, new Integer(500));
         currentResult.setValue("legalMax", new Integer(2097150));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileName")) {
         getterName = "getFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileName";
         }

         currentResult = new PropertyDescriptor("FileName", LogFileMBean.class, getterName, setterName);
         descriptors.put("FileName", currentResult);
         currentResult.setValue("description", "<p>The name of the file that stores current log messages. Usually it is a computed value based on the name of the parent of this MBean. For example, for a server log, it is <code><i>SERVER_NAME</i>.log</code>.</p> <p> However, if the name of the parent cannot be obtained, the file name is <code>weblogic.log</code>. If you specify a relative pathname, it is interpreted as relative to the server's root directory.</p>  <p>To include a time and date stamp in the file name when the log file is rotated, add <code>java.text.SimpleDateFormat</code> variables to the file name. Surround each variable with percentage (<code>%</code>) characters.</p>  <p>For example, if the file name is defined to be <code>myserver_%yyyy%_%MM%_%dd%_%hh%_%mm%.log</code>, the log file will be named <code>myserver_yyyy_mm_dd_hh_mm.log</code>.</p>  <p>When the log file is rotated, the rotated file name contains the date stamp. For example, if the log file is rotated for the first time on 2 April, 2003 at 10:05 AM, the log file that contains the old messages will be named <code>myserver_2003_04_02_10_05.log00001</code>.</p>  <p>If you do not include a time and date stamp, the rotated log files are numbered in order of creation. For example, <code>myserver.log00007</code>.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileTimeSpan")) {
         getterName = "getFileTimeSpan";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileTimeSpan";
         }

         currentResult = new PropertyDescriptor("FileTimeSpan", LogFileMBean.class, getterName, setterName);
         descriptors.put("FileTimeSpan", currentResult);
         currentResult.setValue("description", "<p>The interval (in hours) at which the server saves old log messages to another file. (Requires that you specify a file rotation type of <code>TIME</code>.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getRotationType")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(24));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogFileRotationDir")) {
         getterName = "getLogFileRotationDir";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileRotationDir";
         }

         currentResult = new PropertyDescriptor("LogFileRotationDir", LogFileMBean.class, getterName, setterName);
         descriptors.put("LogFileRotationDir", currentResult);
         currentResult.setValue("description", "<p>The directory where the rotated log files will be stored. By default the rotated files are stored in the same directory where the log file is stored.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", LogFileMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("RotateLogOnStartup")) {
         getterName = "getRotateLogOnStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRotateLogOnStartup";
         }

         currentResult = new PropertyDescriptor("RotateLogOnStartup", LogFileMBean.class, getterName, setterName);
         descriptors.put("RotateLogOnStartup", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a server rotates its log file during its startup cycle. The default value in production mode is false.</p> ");
         currentResult.setValue("restProductionModeDefault", new Boolean(false));
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RotationTime")) {
         getterName = "getRotationTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRotationTime";
         }

         currentResult = new PropertyDescriptor("RotationTime", LogFileMBean.class, getterName, setterName);
         descriptors.put("RotationTime", currentResult);
         currentResult.setValue("description", "<p>Determines the start time (hour and minute) for a time-based rotation sequence.</p>  <p>At the time that this value specifies, the server renames the current log file. Thereafter, the server renames the log file at an interval that you specify in File Time Span.</p>  <p>Note that WebLogic Server sets a threshold size limit of 500 MB before it forces a hard rotation to prevent excessive log file growth.</p>  <p>Use the following format: <code>H:mm</code>, where</p> <ul> <li><code>H</code> is Hour in day (0-23)</li> <li><code>mm</code> is the minute in hour</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getRotationType"), BeanInfoHelper.encodeEntities("#getFileTimeSpan")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "00:00");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RotationType")) {
         getterName = "getRotationType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRotationType";
         }

         currentResult = new PropertyDescriptor("RotationType", LogFileMBean.class, getterName, setterName);
         descriptors.put("RotationType", currentResult);
         currentResult.setValue("description", "<p>Criteria for moving old log messages to a separate file.</p> <ul> <li><code>NONE</code> Messages accumulate in a single file. You must erase the contents of the file when the size is too large. Note that WebLogic Server sets a threshold size limit of 500 MB before it forces a hard rotation to prevent excessive log file growth.</li>  <li><code>SIZE</code> When the log file reaches the size that you specify in <code>FileMinSize</code>, the server renames the file as <code><i>SERVER_NAME</i>.log<i>nnnnn</i></code>.</li>  <li><code>TIME</code> At each time interval that you specify in <code>TimeSpan</code>, the server renames the file as <code><i>SERVER_NAME</i>.log<i>nnnnn</i></code>.</li> </ul>  <p>After the server renames a file, subsequent messages accumulate in a new file with the name that you specified as the log file name.</p> ");
         setPropertyDescriptorDefault(currentResult, "bySize");
         currentResult.setValue("legalValues", new Object[]{"bySize", "byTime", "none", "bySizeOrTime"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", LogFileMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", LogFileMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("NumberOfFilesLimited")) {
         getterName = "isNumberOfFilesLimited";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumberOfFilesLimited";
         }

         currentResult = new PropertyDescriptor("NumberOfFilesLimited", LogFileMBean.class, getterName, setterName);
         descriptors.put("NumberOfFilesLimited", currentResult);
         currentResult.setValue("description", "<p>Indicates whether to limit the number of log files that this server instance creates to store old messages. (Requires that you specify a file rotation type of <code>SIZE</code> or <code>TIME</code>.)</p>  <p>After the server reaches this limit, it deletes the oldest log file and creates a new log file with the latest suffix.</p>  <p>If you do not enable this option, the server creates new files indefinitely and you must clean up these files as you require.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getRotationType")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restProductionModeDefault", new Boolean(true));
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
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
         mth = LogFileMBean.class.getMethod("addTag", String.class);
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
         mth = LogFileMBean.class.getMethod("removeTag", String.class);
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
      Method mth = LogFileMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = LogFileMBean.class.getMethod("restoreDefaultValue", String.class);
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

      mth = LogFileMBean.class.getMethod("computeLogFilePath");
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
