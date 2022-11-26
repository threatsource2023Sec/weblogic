package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WebServerLogMBeanImplBeanInfo extends LogFileMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServerLogMBean.class;

   public WebServerLogMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServerLogMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServerLogMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Aggregates the logging attributes for the WebServerMBean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServerLogMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ELFFields")) {
         getterName = "getELFFields";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setELFFields";
         }

         currentResult = new PropertyDescriptor("ELFFields", WebServerLogMBean.class, getterName, setterName);
         descriptors.put("ELFFields", currentResult);
         currentResult.setValue("description", "<p>Returns the list of fields specified for the <code>extended</code> logging format for access.log. </p> ");
         setPropertyDescriptorDefault(currentResult, "date time cs-method cs-uri sc-status");
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

         currentResult = new PropertyDescriptor("FileName", WebServerLogMBean.class, getterName, setterName);
         descriptors.put("FileName", currentResult);
         currentResult.setValue("description", "<p>The name of the log file.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogFileFormat")) {
         getterName = "getLogFileFormat";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileFormat";
         }

         currentResult = new PropertyDescriptor("LogFileFormat", WebServerLogMBean.class, getterName, setterName);
         descriptors.put("LogFileFormat", currentResult);
         currentResult.setValue("description", "<p>The format of the HTTP log file. Both formats are defined by the W3C. With the extended log format, you use server directives in the log file to customize the information that the server records.</p> ");
         setPropertyDescriptorDefault(currentResult, "common");
         currentResult.setValue("legalValues", new Object[]{"common", "extended"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogTimeInGMT")) {
         getterName = "isLogTimeInGMT";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogTimeInGMT";
         }

         currentResult = new PropertyDescriptor("LogTimeInGMT", WebServerLogMBean.class, getterName, setterName);
         descriptors.put("LogTimeInGMT", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the time stamps for HTTP log messages are in Greenwich Mean Time (GMT) regardless of the local time zone that the host computer specifies.</p>  <p>Use this method to comply with the W3C specification for Extended Format log files. The specification states that all time stamps for Extended Format log entries be in GMT.</p>  <p>This method applies only if you have specified the <code>extended</code> message format.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoggingEnabled")) {
         getterName = "isLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("LoggingEnabled", WebServerLogMBean.class, getterName, setterName);
         descriptors.put("LoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this server logs HTTP requests. (The remaining fields on this page are relevant only if you select this check box.)</p>  <p>Gets the loggingEnabled attribute of the WebServerMBean object.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
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
