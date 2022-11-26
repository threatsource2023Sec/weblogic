package weblogic.management.logging;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ServerLogRuntimeMBean;

public class ServerLogRuntimeBeanInfo extends LogRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = ServerLogRuntimeMBean.class;

   public ServerLogRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerLogRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.logging.ServerLogRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.3.0");
      beanDescriptor.setValue("package", "weblogic.management.logging");
      String description = (new String("This interface defines the control operations for a log in the WebLogic Server. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ServerLogRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("LoggedMessagesCountbySeverity")) {
         getterName = "getLoggedMessagesCountbySeverity";
         setterName = null;
         currentResult = new PropertyDescriptor("LoggedMessagesCountbySeverity", ServerLogRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LoggedMessagesCountbySeverity", currentResult);
         currentResult.setValue("description", "Returns a map object containing the total count of messages logged keyed by the Severity of the messages. Only counts of severities Warning, Error, alert, Critical and Emergency are reported. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map<String,Integer>");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogFileStreamOpened")) {
         getterName = "isLogFileStreamOpened";
         setterName = null;
         currentResult = new PropertyDescriptor("LogFileStreamOpened", ServerLogRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogFileStreamOpened", currentResult);
         currentResult.setValue("description", "Gets the opened state of the log file stream represented by this instance. ");
         currentResult.setValue("since", "12.2.1.0.0");
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
      Method mth = ServerLogRuntimeMBean.class.getMethod("forceLogRotation");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException If there is an error during the log file rotation.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Forces the rotation of the underlying log immediately. ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerLogRuntimeMBean.class.getMethod("ensureLogOpened");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException If the log could not be opened successfully.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Ensures that that the output stream to the underlying is opened if it got closed previously due to errors. ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerLogRuntimeMBean.class.getMethod("flushLog");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException If the log could not be flushed successfully.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Flushes the buffer to the log file on disk. ");
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
