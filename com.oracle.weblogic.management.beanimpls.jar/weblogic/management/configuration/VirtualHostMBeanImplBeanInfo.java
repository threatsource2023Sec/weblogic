package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class VirtualHostMBeanImplBeanInfo extends WebServerMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = VirtualHostMBean.class;

   public VirtualHostMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public VirtualHostMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.VirtualHostMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This bean represents the configuration of virtual web server within a WebLogic Server instance. Note that a server may define multiple web servers to support virtual hosts.</p>  <p>This MBean represents a virtual host.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.VirtualHostMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileCount")) {
         getterName = "getLogFileCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileCount";
         }

         currentResult = new PropertyDescriptor("LogFileCount", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("LogFileCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of log files that this server retains when it rotates the log. (This field is relevant only if you check the Limit Number of Retained Log Files box.)</p>  <p>The maximum number of log files that the server creates when it rotates the log. Only valid if <code>LogFileLimitEnabled</code> is true and <code>LogRotationType</code> is either <code>Size</code> or <code>Time</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(7));
         currentResult.setValue("legalMax", new Integer(9999));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getFileCount() ");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileFormat")) {
         getterName = "getLogFileFormat";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileFormat";
         }

         currentResult = new PropertyDescriptor("LogFileFormat", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("LogFileFormat", currentResult);
         currentResult.setValue("description", "<p>The format of the HTTP log file. Both formats are defined by the W3C. With the <code>extended</code> log format, you use server directives in the log file to customize the information that the server records.</p>  <p>Specifies the format of the HTTP log file. Both formats are defined by the W3C. With the extended log format, you use server directives in the log file to customize the information that the server records.</p> ");
         setPropertyDescriptorDefault(currentResult, "common");
         currentResult.setValue("legalValues", new Object[]{"common", "extended"});
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getLogFileFormat(). ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileName")) {
         getterName = "getLogFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileName";
         }

         currentResult = new PropertyDescriptor("LogFileName", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("LogFileName", currentResult);
         currentResult.setValue("description", "<p>The name of the file that stores HTTP requests. If the pathname is not absolute, the path is assumed to be relative to the root directory of the machine on which this server is running.</p>  <p>The name of the file that stores the HTTP-request log. If the pathname is not absolute, the path is assumed to be relative to the server's root directory.</p>  <p>This value is relevant only if HTTP logging is enabled.</p>  <p>The current logfile is always the one whose name equals value of the this attribute. If you have enabled log file rotation, when the current file exceeds the size or time limit, it is renamed.</p>  <p>To include a time and date stamp in the file name when the log file is rotated, add <code>java.text.SimpleDateFormat</code> variables to the file name. Surround each variable with percentage (<code>%</code>) characters.</p>  <p>For example, if the file name is defined to be <code>access_%yyyy%_%MM%_%dd%_%hh%_%mm%.log</code>, the log file will be named <code>access_yyyy_mm_dd_hh_mm.log</code>.</p>  <p>When the log file is rotated, the rotated file name contains the date stamp. For example, if the log file is rotated on 2 April, 2003 at 10:05 AM, the log file that contains the old messages will be named <code>access_2003_04_02_10_05.log</code>.</p>  <p>If you do not include a time and date stamp, the rotated log files are numbered in order of creation. For example, <code>access.log00007</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, "logs/access.log");
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getFileName() ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogRotationPeriodMins")) {
         getterName = "getLogRotationPeriodMins";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogRotationPeriodMins";
         }

         currentResult = new PropertyDescriptor("LogRotationPeriodMins", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("LogRotationPeriodMins", currentResult);
         currentResult.setValue("description", "<p>The number of minutes at which this server saves old HTTP requests to another log file. This field is relevant only if you set Rotation Type to <code>date</code>.</p>  <p>The interval (in minutes) at which the server saves old HTTP requests to another log file. This value is relevant only if you use the <code>date</code>-based rotation type.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1440));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getFileTimeSpan() (hours) ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogRotationTimeBegin")) {
         getterName = "getLogRotationTimeBegin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogRotationTimeBegin";
         }

         currentResult = new PropertyDescriptor("LogRotationTimeBegin", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("LogRotationTimeBegin", currentResult);
         currentResult.setValue("description", "<p>The start time for a time-based rotation sequence of the log file, in the format <code>MM-dd-yyyy-k:mm:ss</code>. (This field is only relevant if you set Rotation Type to <code>>date</code>>.)</p>  <p>Determines the start time for a time-based rotation sequence. At the time that this value specifies, the server renames the current log file. Thereafter, the server renames the log file at an interval that you specify in <code>LogRotationPeriodMins</code>.</p>  <p>Use the following format: <code>MM-dd-yyyy-k:mm:ss</code> where</p>  <ul> <li><code>MM</code>  <p>is the month as expressed in the Gregorian calendar</p> </li>  <li><code>dd</code>  <p>is the day of the month</p> </li>  <li><code>yyyy</code>  <p>is the year</p> </li>  <li><code>k</code>  <p>is the hour in a 24-hour format.</p> </li>  <li><code>mm</code>  <p>is the minute</p> </li>  <li><code>ss</code>  <p>is the second</p> </li> </ul>  <p>If the time that you specify has already past, then the server starts its file rotation immediately.</p>  <p>By default, rotation starts 24 hours from the time that you restart the server instance.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogRotationType")) {
         getterName = "getLogRotationType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogRotationType";
         }

         currentResult = new PropertyDescriptor("LogRotationType", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("LogRotationType", currentResult);
         currentResult.setValue("description", "<p>The criteria for moving old log messages to a separate file.</p>  <p>Criteria for moving old HTTP requests to a separate log file:</p>  <ul> <li><code>size</code>  <p>. When the log file reaches the size that you specify in <code>MaxLogFileSizeKBytes</code>, the server renames the file as <code><i>LogFileName.n</i></code>.</p> </li>  <li><code>date</code>  <p>. At each time interval that you specify in <code>LogRotationPeriodMin</code>, the server renames the file as <code><i>LogFileName.n</i></code>.</p> </li> </ul>  <p>After the server renames a file, subsequent messages accumulate in a new file with the name that you specified in <code>LogFileName</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, "size");
         currentResult.setValue("legalValues", new Object[]{"size", "date"});
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getRotationType() ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogTimeInGMT")) {
         getterName = "getLogTimeInGMT";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogTimeInGMT";
         }

         currentResult = new PropertyDescriptor("LogTimeInGMT", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("LogTimeInGMT", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the time stamps for HTTP log messages are in Greenwich Mean Time (GMT) regardless of the local time zone that the host computer specifies.</p>  <p>Use this method to comply with the W3C specification for Extended Format Log Files. The specification states that all time stamps for Extended Format log entries be in GMT.</p>  <p>This method applies only if you have specified the <code>extended</code> message format.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getLogTimeInGMT(). ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("MaxRequestParamterCount")) {
         getterName = "getMaxRequestParamterCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRequestParamterCount";
         }

         currentResult = new PropertyDescriptor("MaxRequestParamterCount", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("MaxRequestParamterCount", currentResult);
         currentResult.setValue("description", "<p>Max Request Parameter Count this server allows for reading maximum HTTP POST Parameters count in a servlet request.</p>  <p>Gets the maxRequestParamterCount attribute of the WebServerMBean object</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#getMaxRequestParamterCount()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("deprecated", "Use getMaxRequestParameterCount() ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("NetworkAccessPoint")) {
         getterName = "getNetworkAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNetworkAccessPoint";
         }

         currentResult = new PropertyDescriptor("NetworkAccessPoint", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("NetworkAccessPoint", currentResult);
         currentResult.setValue("description", "<p>The dedicated server channel name (NetworkAccessPoint) for which this virtual host will serve http request. If the NetworkAccessPoint for a given http request doesn't match any virtual host's NetworkAccessPoint, incoming HOST header will be matched with the VirtualHostNames in order to resolve the right virtual host. </p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("UriPath")) {
         getterName = "getUriPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUriPath";
         }

         currentResult = new PropertyDescriptor("UriPath", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("UriPath", currentResult);
         currentResult.setValue("description", "Get uri path ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("VirtualHostNames")) {
         getterName = "getVirtualHostNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVirtualHostNames";
         }

         currentResult = new PropertyDescriptor("VirtualHostNames", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("VirtualHostNames", currentResult);
         currentResult.setValue("description", "<p>The list of host names, separated by line breaks, for which this virtual host will serve requests.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileLimitEnabled")) {
         getterName = "isLogFileLimitEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileLimitEnabled";
         }

         currentResult = new PropertyDescriptor("LogFileLimitEnabled", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("LogFileLimitEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the number of files that this WebLogic Server retains to store old messages should be limited. After the server reaches this limit, it overwrites the oldest file.</p>  <p>Indicates whether a server will limit the number of log files that it creates when it rotates the log. The limit is based on <code>getLogFileCount</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getNumberOfFilesLimited() ");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LoggingEnabled")) {
         getterName = "isLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("LoggingEnabled", VirtualHostMBean.class, getterName, setterName);
         descriptors.put("LoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this server logs HTTP requests. (The remaining fields on this page are relevant only if you check this box.)</p>  <p>Gets the loggingEnabled attribute of the WebServerMBean object</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().isLoggingEnabled(). ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
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
         mth = VirtualHostMBean.class.getMethod("addTag", String.class);
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
         mth = VirtualHostMBean.class.getMethod("removeTag", String.class);
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
      Method mth = VirtualHostMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = VirtualHostMBean.class.getMethod("restoreDefaultValue", String.class);
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
