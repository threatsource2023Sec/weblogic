package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WebServerMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServerMBean.class;

   public WebServerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServerMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This bean represents the configuration of virtual web server within a weblogic server. Note that a server may define multiple web servers to support virtual hosts.</p>  <p>This MBean represents a virtual host.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Charsets")) {
         getterName = "getCharsets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCharsets";
         }

         currentResult = new PropertyDescriptor("Charsets", WebServerMBean.class, getterName, setterName);
         descriptors.put("Charsets", currentResult);
         currentResult.setValue("description", "<p>Provides user defined mapping between internet and Java charset names.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map");
      }

      if (!descriptors.containsKey("ClientIpHeader")) {
         getterName = "getClientIpHeader";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientIpHeader";
         }

         currentResult = new PropertyDescriptor("ClientIpHeader", WebServerMBean.class, getterName, setterName);
         descriptors.put("ClientIpHeader", currentResult);
         currentResult.setValue("description", "<p>Get the Client IP Header from WebSerevrMBean.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("DefaultWebApp")) {
         getterName = "getDefaultWebApp";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultWebApp";
         }

         currentResult = new PropertyDescriptor("DefaultWebApp", WebServerMBean.class, getterName, setterName);
         descriptors.put("DefaultWebApp", currentResult);
         currentResult.setValue("description", "<p>Provides the Servlet 2.3 Web Application that maps to the \"default\" servlet context (where ContextPath = \"/\"). This param has been deprecated 9.0.0.0 starting from 8.1 release. Set context-root=\"\" instead in weblogic.xml or application.xml. Alternatively, use getDefaultWebAppDeployment()</p>  <p>Gets the defaultWebApp attribute of the WebServerMBean object</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DefaultWebAppContextRoot")) {
         getterName = "getDefaultWebAppContextRoot";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultWebAppContextRoot";
         }

         currentResult = new PropertyDescriptor("DefaultWebAppContextRoot", WebServerMBean.class, getterName, setterName);
         descriptors.put("DefaultWebAppContextRoot", currentResult);
         currentResult.setValue("description", "<p>Returns the original context-root for the default Web application for this Web server. Alternatively, you can use the context-root attributes in application.xml or weblogic.xml to set a default Web application. The context-root for a default Web application is /. If \"\" (empty string) is specified, the Web server defaults to /.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("FrontendHTTPPort")) {
         getterName = "getFrontendHTTPPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFrontendHTTPPort";
         }

         currentResult = new PropertyDescriptor("FrontendHTTPPort", WebServerMBean.class, getterName, setterName);
         descriptors.put("FrontendHTTPPort", currentResult);
         currentResult.setValue("description", "<p>The name of the HTTP port to which all redirected URLs will be sent. If specified, WebLogic Server will use this value rather than the one in the HOST header.</p>  <p>Sets the frontendHTTPPort Provides a method to ensure that the webapp will always have the correct PORT information, even when the request is coming through a firewall or a proxy. If this parameter is configured, the HOST header will be ignored and the information in this parameter will be used in its place.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FrontendHTTPSPort")) {
         getterName = "getFrontendHTTPSPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFrontendHTTPSPort";
         }

         currentResult = new PropertyDescriptor("FrontendHTTPSPort", WebServerMBean.class, getterName, setterName);
         descriptors.put("FrontendHTTPSPort", currentResult);
         currentResult.setValue("description", "<p>The name of the secure HTTP port to which all redirected URLs will be sent. If specified, WebLogic Server will use this value rather than the one in the HOST header.</p>  <p>Sets the frontendHTTPSPort Provides a method to ensure that the webapp will always have the correct PORT information, even when the request is coming through a firewall or a proxy. If this parameter is configured, the HOST header will be ignored and the information in this parameter will be used in its place.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FrontendHost")) {
         getterName = "getFrontendHost";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFrontendHost";
         }

         currentResult = new PropertyDescriptor("FrontendHost", WebServerMBean.class, getterName, setterName);
         descriptors.put("FrontendHost", currentResult);
         currentResult.setValue("description", "<p>The name of the host to which all redirected URLs will be sent. If specified, WebLogic Server will use this value rather than the one in the HOST header.</p>  <p>Sets the HTTP frontendHost Provides a method to ensure that the webapp will always have the correct HOST information, even when the request is coming through a firewall or a proxy. If this parameter is configured, the HOST header will be ignored and the information in this parameter will be used in its place.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpsKeepAliveSecs")) {
         getterName = "getHttpsKeepAliveSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpsKeepAliveSecs";
         }

         currentResult = new PropertyDescriptor("HttpsKeepAliveSecs", WebServerMBean.class, getterName, setterName);
         descriptors.put("HttpsKeepAliveSecs", currentResult);
         currentResult.setValue("description", "<p>The amount of time this server waits before closing an inactive HTTPS connection.</p>  <p>Number of seconds to maintain HTTPS keep-alive before timing out the request.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("secureValue", new Integer(60));
         currentResult.setValue("legalMax", new Integer(360));
         currentResult.setValue("legalMin", new Integer(30));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepAliveSecs")) {
         getterName = "getKeepAliveSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepAliveSecs";
         }

         currentResult = new PropertyDescriptor("KeepAliveSecs", WebServerMBean.class, getterName, setterName);
         descriptors.put("KeepAliveSecs", currentResult);
         currentResult.setValue("description", "<p>The amount of time this server waits before closing an inactive HTTP connection.</p>  <p>Number of seconds to maintain HTTP keep-alive before timing out the request.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("secureValue", new Integer(30));
         currentResult.setValue("legalMax", new Integer(3600));
         currentResult.setValue("legalMin", new Integer(5));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileBufferKBytes")) {
         getterName = "getLogFileBufferKBytes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileBufferKBytes";
         }

         currentResult = new PropertyDescriptor("LogFileBufferKBytes", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogFileBufferKBytes", currentResult);
         currentResult.setValue("description", "<p>The maximum size (in kilobytes) of the buffer that stores HTTP requests. When the buffer reaches this size, the server writes the data to the HTTP log file. Use the <code>LogFileFlushSecs</code> property to determine the frequency with which the server checks the size of the buffer.</p>  <p>The maximum size of the buffer that stores HTTP requests.</p>  <p>Gets the logFileBufferKBytes attribute of the WebServerMBean object</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(8));
         currentResult.setValue("legalMax", new Integer(1024));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileCount")) {
         getterName = "getLogFileCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileCount";
         }

         currentResult = new PropertyDescriptor("LogFileCount", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogFileCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of log files that this server retains when it rotates the log. (This field is relevant only if you check the Limit Number of Retained Log Files box.)</p>  <p>The maximum number of log files that the server creates when it rotates the log. Only valid if <code>LogFileLimitEnabled</code> is true and <code>LogRotationType</code> is either <code>Size</code> or <code>Time</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(7));
         currentResult.setValue("legalMax", new Integer(9999));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getFileCount() ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileFlushSecs")) {
         getterName = "getLogFileFlushSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileFlushSecs";
         }

         currentResult = new PropertyDescriptor("LogFileFlushSecs", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogFileFlushSecs", currentResult);
         currentResult.setValue("description", "<p>The interval at which this server checks the size of the buffer that stores HTTP requests. When the buffer exceeds the size that is specified in the Log Buffer Size field, the server writes the data to the HTTP request log file.</p>  <p>The interval (in seconds) at which the server checks the size of the buffer that stores HTTP requests. When the buffer exceeds the size that is specified in the <code>LogFileBufferKBytes</code> property, the server writes the data in the buffer to the HTTP request log file.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(360));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileFormat")) {
         getterName = "getLogFileFormat";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileFormat";
         }

         currentResult = new PropertyDescriptor("LogFileFormat", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogFileFormat", currentResult);
         currentResult.setValue("description", "<p>The format of the HTTP log file. Both formats are defined by the W3C. With the <code>extended</code> log format, you use server directives in the log file to customize the information that the server records.</p>  <p>Specifies the format of the HTTP log file. Both formats are defined by the W3C. With the extended log format, you use server directives in the log file to customize the information that the server records.</p> ");
         setPropertyDescriptorDefault(currentResult, "common");
         currentResult.setValue("legalValues", new Object[]{"common", "extended"});
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getLogFileFormat(). ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileName")) {
         getterName = "getLogFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileName";
         }

         currentResult = new PropertyDescriptor("LogFileName", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogFileName", currentResult);
         currentResult.setValue("description", "<p>The name of the file that stores HTTP requests. If the pathname is not absolute, the path is assumed to be relative to the root directory of the machine on which this server is running.</p>  <p>The name of the file that stores the HTTP-request log. If the pathname is not absolute, the path is assumed to be relative to the server's root directory.</p>  <p>This value is relevant only if HTTP logging is enabled.</p>  <p>The current logfile is always the one whose name equals value of the this attribute. If you have enabled log file rotation, when the current file exceeds the size or time limit, it is renamed.</p>  <p>To include a time and date stamp in the file name when the log file is rotated, add <code>java.text.SimpleDateFormat</code> variables to the file name. Surround each variable with percentage (<code>%</code>) characters.</p>  <p>For example, if the file name is defined to be <code>access_%yyyy%_%MM%_%dd%_%hh%_%mm%.log</code>, the log file will be named <code>access_yyyy_mm_dd_hh_mm.log</code>.</p>  <p>When the log file is rotated, the rotated file name contains the date stamp. For example, if the log file is rotated on 2 April, 2003 at 10:05 AM, the log file that contains the old messages will be named <code>access_2003_04_02_10_05.log</code>.</p>  <p>If you do not include a time and date stamp, the rotated log files are numbered in order of creation. For example, <code>access.log00007</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, "logs/access.log");
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getFileName() ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogRotationPeriodMins")) {
         getterName = "getLogRotationPeriodMins";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogRotationPeriodMins";
         }

         currentResult = new PropertyDescriptor("LogRotationPeriodMins", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogRotationPeriodMins", currentResult);
         currentResult.setValue("description", "<p>The number of minutes at which this server saves old HTTP requests to another log file. This field is relevant only if you set Rotation Type to <code>date</code>.</p>  <p>The interval (in minutes) at which the server saves old HTTP requests to another log file. This value is relevant only if you use the <code>date</code>-based rotation type.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1440));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getFileTimeSpan() (hours) ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogRotationTimeBegin")) {
         getterName = "getLogRotationTimeBegin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogRotationTimeBegin";
         }

         currentResult = new PropertyDescriptor("LogRotationTimeBegin", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogRotationTimeBegin", currentResult);
         currentResult.setValue("description", "<p>The start time for a time-based rotation sequence of the log file, in the format <code>MM-dd-yyyy-k:mm:ss</code>. (This field is only relevant if you set Rotation Type to <code>>date</code>>.)</p>  <p>Determines the start time for a time-based rotation sequence. At the time that this value specifies, the server renames the current log file. Thereafter, the server renames the log file at an interval that you specify in <code>LogRotationPeriodMins</code>.</p>  <p>Use the following format: <code>MM-dd-yyyy-k:mm:ss</code> where</p>  <ul> <li><code>MM</code>  <p>is the month as expressed in the Gregorian calendar</p> </li>  <li><code>dd</code>  <p>is the day of the month</p> </li>  <li><code>yyyy</code>  <p>is the year</p> </li>  <li><code>k</code>  <p>is the hour in a 24-hour format.</p> </li>  <li><code>mm</code>  <p>is the minute</p> </li>  <li><code>ss</code>  <p>is the second</p> </li> </ul>  <p>If the time that you specify has already past, then the server starts its file rotation immediately.</p>  <p>By default, rotation starts 24 hours from the time that you restart the server instance.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogRotationType")) {
         getterName = "getLogRotationType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogRotationType";
         }

         currentResult = new PropertyDescriptor("LogRotationType", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogRotationType", currentResult);
         currentResult.setValue("description", "<p>The criteria for moving old log messages to a separate file.</p>  <p>Criteria for moving old HTTP requests to a separate log file:</p>  <ul> <li><code>size</code>  <p>. When the log file reaches the size that you specify in <code>MaxLogFileSizeKBytes</code>, the server renames the file as <code><i>LogFileName.n</i></code>.</p> </li>  <li><code>date</code>  <p>. At each time interval that you specify in <code>LogRotationPeriodMin</code>, the server renames the file as <code><i>LogFileName.n</i></code>.</p> </li> </ul>  <p>After the server renames a file, subsequent messages accumulate in a new file with the name that you specified in <code>LogFileName</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, "size");
         currentResult.setValue("legalValues", new Object[]{"size", "date"});
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getRotationType() ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogTimeInGMT")) {
         getterName = "getLogTimeInGMT";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogTimeInGMT";
         }

         currentResult = new PropertyDescriptor("LogTimeInGMT", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogTimeInGMT", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the time stamps for HTTP log messages are in Greenwich Mean Time (GMT) regardless of the local time zone that the host computer specifies.</p>  <p>Use this method to comply with the W3C specification for Extended Format Log Files. The specification states that all time stamps for Extended Format log entries be in GMT.</p>  <p>This method applies only if you have specified the <code>extended</code> message format.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getLogTimeInGMT(). ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("MaxLogFileSizeKBytes")) {
         getterName = "getMaxLogFileSizeKBytes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxLogFileSizeKBytes";
         }

         currentResult = new PropertyDescriptor("MaxLogFileSizeKBytes", WebServerMBean.class, getterName, setterName);
         descriptors.put("MaxLogFileSizeKBytes", currentResult);
         currentResult.setValue("description", "<p>The maximum size (in kilobytes) of the HTTP log file. After the log file reaches this size, the server renames it as <code>LogFileName.n</code>. A value of 0 indicates that the log file can grow indefinitely. (This field is relevant only if you set Rotation Type to <code>size</code>.)</p>  <p>The size that triggers the server to move log messages to a separate file. After the log file reaches the specified size, the next time the server checks the file size, it will rename the current log file as <code><i>FileName.n</i></code> and create a new one to store subsequent messages.</p>  <p>This property is relevant only if you choose to rotate files by <code>size</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getFileMinSize() ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("MaxPostSize")) {
         getterName = "getMaxPostSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxPostSize";
         }

         currentResult = new PropertyDescriptor("MaxPostSize", WebServerMBean.class, getterName, setterName);
         descriptors.put("MaxPostSize", currentResult);
         currentResult.setValue("description", "<p>The maximum post size this server allows for reading HTTP POST and PUT data in a servlet request.</p>  <p>A value less than 0 indicates an unlimited size.</p>  <p>Gets the maxPostSize attribute of the WebServerMBean object</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#getMaxPostSize()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxPostTimeSecs")) {
         getterName = "getMaxPostTimeSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxPostTimeSecs";
         }

         currentResult = new PropertyDescriptor("MaxPostTimeSecs", WebServerMBean.class, getterName, setterName);
         descriptors.put("MaxPostTimeSecs", currentResult);
         currentResult.setValue("description", "<p>Max Post Time (in seconds) for reading HTTP POST data in a servlet request. MaxPostTime &lt; 0 means unlimited</p>  <p>Gets the maxPostTimeSecs attribute of the WebServerMBean object</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#getMaxPostTimeSecs()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRequestParameterCount")) {
         getterName = "getMaxRequestParameterCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRequestParameterCount";
         }

         currentResult = new PropertyDescriptor("MaxRequestParameterCount", WebServerMBean.class, getterName, setterName);
         descriptors.put("MaxRequestParameterCount", currentResult);
         currentResult.setValue("description", "<p>Max Request Parameter Count this server allows for reading maximum HTTP POST Parameters count in a servlet request.</p>  <p>Gets the maxRequestParameterCount attribute of the WebServerMBean object</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#getMaxRequestParameterCount()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRequestParamterCount")) {
         getterName = "getMaxRequestParamterCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRequestParamterCount";
         }

         currentResult = new PropertyDescriptor("MaxRequestParamterCount", WebServerMBean.class, getterName, setterName);
         descriptors.put("MaxRequestParamterCount", currentResult);
         currentResult.setValue("description", "<p>Max Request Parameter Count this server allows for reading maximum HTTP POST Parameters count in a servlet request.</p>  <p>Gets the maxRequestParamterCount attribute of the WebServerMBean object</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#getMaxRequestParamterCount()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("deprecated", "Use getMaxRequestParameterCount() ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", WebServerMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("OverloadResponseCode")) {
         getterName = "getOverloadResponseCode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOverloadResponseCode";
         }

         currentResult = new PropertyDescriptor("OverloadResponseCode", WebServerMBean.class, getterName, setterName);
         descriptors.put("OverloadResponseCode", currentResult);
         currentResult.setValue("description", "<p>Get the response code to be used when an application is overloaded. An application can get overloaded when the number of pending requests has reached the max capacity specified in the WorkManager or when the server is low on memory. The low memory condition is determined using {@link OverloadProtectionMBean#getFreeMemoryPercentLowThreshold()}.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("CapacityMBean"), BeanInfoHelper.encodeEntities("OverloadProtectionMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(503));
         currentResult.setValue("legalMax", new Integer(599));
         currentResult.setValue("legalMin", new Integer(100));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostTimeoutSecs")) {
         getterName = "getPostTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPostTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("PostTimeoutSecs", WebServerMBean.class, getterName, setterName);
         descriptors.put("PostTimeoutSecs", currentResult);
         currentResult.setValue("description", "<p>Timeout (in seconds) for reading HTTP POST data in a servlet request. If the POST data is chunked, the amount of time the server waits between the end of receiving the last chunk of data and the end of receiving the next chunk of data in an HTTP POST before it times out. (This is used to prevent denial-of-service attacks that attempt to overload the server with POST data.)</p>  <p>Gets the postTimeoutSecs attribute of the WebServerMBean object</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("secureValue", new Integer(30));
         currentResult.setValue("legalMax", new Integer(120));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", WebServerMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("URLResource")) {
         getterName = "getURLResource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setURLResource";
         }

         currentResult = new PropertyDescriptor("URLResource", WebServerMBean.class, getterName, setterName);
         descriptors.put("URLResource", currentResult);
         currentResult.setValue("description", "<p>Adds a URL connection factory resource into JNDI.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map");
      }

      if (!descriptors.containsKey("WebServerLog")) {
         getterName = "getWebServerLog";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServerLog", WebServerMBean.class, getterName, setterName);
         descriptors.put("WebServerLog", currentResult);
         currentResult.setValue("description", "Returns the Log settings for the WebServer/VirtualHost. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManagerForRemoteSessionFetching")) {
         getterName = "getWorkManagerForRemoteSessionFetching";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkManagerForRemoteSessionFetching";
         }

         currentResult = new PropertyDescriptor("WorkManagerForRemoteSessionFetching", WebServerMBean.class, getterName, setterName);
         descriptors.put("WorkManagerForRemoteSessionFetching", currentResult);
         currentResult.setValue("description", "<p>Set the WorkManager name that will be used to execute servlet requests that need their session retrieved from a remote server since the current server is neither the primary nor the secondary for the request. This can happen if request stickness is lost for example due to hardware LB configuration issues. Creating a dedicated WorkManager with a max threads constraint ensures that threads are available to service requests for which the current server is the primary.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WriteChunkBytes")) {
         getterName = "getWriteChunkBytes";
         setterName = null;
         currentResult = new PropertyDescriptor("WriteChunkBytes", WebServerMBean.class, getterName, setterName);
         descriptors.put("WriteChunkBytes", currentResult);
         currentResult.setValue("description", "<p>The default size of the blocks to be written to the network layer.</p>  <p>Gets the writeChunkBytes attribute of the WebServerMBean object</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(512));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AcceptContextPathInGetRealPath")) {
         getterName = "isAcceptContextPathInGetRealPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAcceptContextPathInGetRealPath";
         }

         currentResult = new PropertyDescriptor("AcceptContextPathInGetRealPath", WebServerMBean.class, getterName, setterName);
         descriptors.put("AcceptContextPathInGetRealPath", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this server allows the inclusion of the context path in the virtual path to <code>context.getRealPath()</code>. (If checked, you cannot use sub directories with the same name as <code>contextPath</code>). This is a compatibility switch that will be deprecated 9.0.0.0 in future releases.</p>  <p>Gets the acceptContextPathInGetRealPath attribute of the WebServerMBean object</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuthCookieEnabled")) {
         getterName = "isAuthCookieEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthCookieEnabled";
         }

         currentResult = new PropertyDescriptor("AuthCookieEnabled", WebServerMBean.class, getterName, setterName);
         descriptors.put("AuthCookieEnabled", currentResult);
         currentResult.setValue("description", "<p>Whether authcookie feature is enabled or not.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ChunkedTransferDisabled")) {
         getterName = "isChunkedTransferDisabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setChunkedTransferDisabled";
         }

         currentResult = new PropertyDescriptor("ChunkedTransferDisabled", WebServerMBean.class, getterName, setterName);
         descriptors.put("ChunkedTransferDisabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the use of Chunk Transfer-Encoding in HTTP/1.1 is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("DebugEnabled")) {
         getterName = "isDebugEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("DebugEnabled", WebServerMBean.class, getterName, setterName);
         descriptors.put("DebugEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the debugEnabled attribute is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 use the ServerDebugMBean ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", WebServerMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("KeepAliveEnabled")) {
         getterName = "isKeepAliveEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepAliveEnabled";
         }

         currentResult = new PropertyDescriptor("KeepAliveEnabled", WebServerMBean.class, getterName, setterName);
         descriptors.put("KeepAliveEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether there should be a persistent connection to this server. (This may improve the performance of your Web applications.)</p>  <p>Gets the keepAliveEnabled attribute of the WebServerMBean object</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LogFileLimitEnabled")) {
         getterName = "isLogFileLimitEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileLimitEnabled";
         }

         currentResult = new PropertyDescriptor("LogFileLimitEnabled", WebServerMBean.class, getterName, setterName);
         descriptors.put("LogFileLimitEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the number of files that this WebLogic Server retains to store old messages should be limited. After the server reaches this limit, it overwrites the oldest file.</p>  <p>Indicates whether a server will limit the number of log files that it creates when it rotates the log. The limit is based on <code>getLogFileCount</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().getNumberOfFilesLimited() ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("LoggingEnabled")) {
         getterName = "isLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("LoggingEnabled", WebServerMBean.class, getterName, setterName);
         descriptors.put("LoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this server logs HTTP requests. (The remaining fields on this page are relevant only if you check this box.)</p>  <p>Gets the loggingEnabled attribute of the WebServerMBean object</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("deprecated", "9.0.0.0 Use getWebServerLog().isLoggingEnabled(). ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("SendServerHeaderEnabled")) {
         getterName = "isSendServerHeaderEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSendServerHeaderEnabled";
         }

         currentResult = new PropertyDescriptor("SendServerHeaderEnabled", WebServerMBean.class, getterName, setterName);
         descriptors.put("SendServerHeaderEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this server name is sent with the HTTP response. (This is useful for wireless applications where there is limited space for headers.)</p>  <p>Indicates whether this server instance includes its name and WebLogic Server version number in HTTP response headers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingleSignonDisabled")) {
         getterName = "isSingleSignonDisabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSingleSignonDisabled";
         }

         currentResult = new PropertyDescriptor("SingleSignonDisabled", WebServerMBean.class, getterName, setterName);
         descriptors.put("SingleSignonDisabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the singleSignonDisabled attribute is enabled</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseHeaderEncoding")) {
         getterName = "isUseHeaderEncoding";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseHeaderEncoding";
         }

         currentResult = new PropertyDescriptor("UseHeaderEncoding", WebServerMBean.class, getterName, setterName);
         descriptors.put("UseHeaderEncoding", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseHighestCompatibleHTTPVersion")) {
         getterName = "isUseHighestCompatibleHTTPVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseHighestCompatibleHTTPVersion";
         }

         currentResult = new PropertyDescriptor("UseHighestCompatibleHTTPVersion", WebServerMBean.class, getterName, setterName);
         descriptors.put("UseHighestCompatibleHTTPVersion", currentResult);
         currentResult.setValue("description", "<p>Enables use of the highest compatible HTTP protocol version-string in the response. E.g. HTTP spec suggests that HTTP/1.1 version-string should be used in response to a request using HTTP/1.0. This does not necessarily affect the response format.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WAPEnabled")) {
         getterName = "isWAPEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWAPEnabled";
         }

         currentResult = new PropertyDescriptor("WAPEnabled", WebServerMBean.class, getterName, setterName);
         descriptors.put("WAPEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the session ID should include JVM information. (Checking this box may be necessary when using URL rewriting with WAP devices that limit the size of the URL to 128 characters, and may also affect the use of replicated sessions in a cluster.) When this box is selected, the default size of the URL will be set at 52 characters, and it will not contain any special characters.</p>  <p>Gets the WAPEnabled attribute of the WebServerMBean object</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#isWAPEnabled()")};
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
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WebServerMBean.class.getMethod("addTag", String.class);
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
         mth = WebServerMBean.class.getMethod("removeTag", String.class);
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
      Method mth = WebServerMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = WebServerMBean.class.getMethod("restoreDefaultValue", String.class);
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
