package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class LoggingBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = LoggingBean.class;

   public LoggingBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LoggingBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.LoggingBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.LoggingBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DateFormatPattern")) {
         getterName = "getDateFormatPattern";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDateFormatPattern";
         }

         currentResult = new PropertyDescriptor("DateFormatPattern", LoggingBean.class, getterName, setterName);
         descriptors.put("DateFormatPattern", currentResult);
         currentResult.setValue("description", "<p>The date format pattern used for rendering dates in the Server log file and stdout output. The DateFormatPattern string conforms to the specification of the <code>java.text.SimpleDateFormat</code> class.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileCount")) {
         getterName = "getFileCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileCount";
         }

         currentResult = new PropertyDescriptor("FileCount", LoggingBean.class, getterName, setterName);
         descriptors.put("FileCount", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(7));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileSizeLimit")) {
         getterName = "getFileSizeLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileSizeLimit";
         }

         currentResult = new PropertyDescriptor("FileSizeLimit", LoggingBean.class, getterName, setterName);
         descriptors.put("FileSizeLimit", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(500));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileTimeSpan")) {
         getterName = "getFileTimeSpan";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFileTimeSpan";
         }

         currentResult = new PropertyDescriptor("FileTimeSpan", LoggingBean.class, getterName, setterName);
         descriptors.put("FileTimeSpan", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(24));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", LoggingBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogFileRotationDir")) {
         getterName = "getLogFileRotationDir";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFileRotationDir";
         }

         currentResult = new PropertyDescriptor("LogFileRotationDir", LoggingBean.class, getterName, setterName);
         descriptors.put("LogFileRotationDir", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogFilename")) {
         getterName = "getLogFilename";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFilename";
         }

         currentResult = new PropertyDescriptor("LogFilename", LoggingBean.class, getterName, setterName);
         descriptors.put("LogFilename", currentResult);
         currentResult.setValue("description", "Gets the \"log-filename\" element ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RotationTime")) {
         getterName = "getRotationTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRotationTime";
         }

         currentResult = new PropertyDescriptor("RotationTime", LoggingBean.class, getterName, setterName);
         descriptors.put("RotationTime", currentResult);
         currentResult.setValue("description", "<p>Determines the start time (hour and minute) for a time-based rotation sequence.  <p>At the time that this value specifies, the server renames the current log file. Thereafter, the server renames the log file at an interval that you specify in File Time Span.</p>  <p>If the specified time has already past, then the server starts its file rotation immediately.</p>  <p> Use the following format: <code>H:mm</code>, where <ul><li><code>H</code> is Hour in day (0-23). <li><code>mm</code> is the minute in hour </ul> <p> ");
         setPropertyDescriptorDefault(currentResult, "00:00");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RotationType")) {
         getterName = "getRotationType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRotationType";
         }

         currentResult = new PropertyDescriptor("RotationType", LoggingBean.class, getterName, setterName);
         descriptors.put("RotationType", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "bySize");
         currentResult.setValue("legalValues", new Object[]{"bySize", "byTime", "none"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoggingEnabled")) {
         getterName = "isLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("LoggingEnabled", LoggingBean.class, getterName, setterName);
         descriptors.put("LoggingEnabled", currentResult);
         currentResult.setValue("description", "Gets the \"logging-enabled\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumberOfFilesLimited")) {
         getterName = "isNumberOfFilesLimited";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumberOfFilesLimited";
         }

         currentResult = new PropertyDescriptor("NumberOfFilesLimited", LoggingBean.class, getterName, setterName);
         descriptors.put("NumberOfFilesLimited", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RotateLogOnStartup")) {
         getterName = "isRotateLogOnStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRotateLogOnStartup";
         }

         currentResult = new PropertyDescriptor("RotateLogOnStartup", LoggingBean.class, getterName, setterName);
         descriptors.put("RotateLogOnStartup", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
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
