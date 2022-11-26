package weblogic.time.server;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.TimeServiceRuntimeMBean;

public class TimerMBeanBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TimeServiceRuntimeMBean.class;

   public TimerMBeanBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TimerMBeanBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.time.server.TimerMBean");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.time.server");
      String description = (new String("This class is used for monitoring the WebLogic Time Service. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.TimeServiceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ExceptionCount")) {
         getterName = "getExceptionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExceptionCount", TimeServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExceptionCount", currentResult);
         currentResult.setValue("description", "<p>Returns the total number of exceptions thrown while executing scheduled triggers.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionCount")) {
         getterName = "getExecutionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionCount", TimeServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionCount", currentResult);
         currentResult.setValue("description", "<p>Returns the total number of triggers executed</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionsPerMinute")) {
         getterName = "getExecutionsPerMinute";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionsPerMinute", TimeServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionsPerMinute", currentResult);
         currentResult.setValue("description", "<p>Returns the average number of triggers executed per minute.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ScheduledTriggerCount")) {
         getterName = "getScheduledTriggerCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ScheduledTriggerCount", TimeServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ScheduledTriggerCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of currently active scheduled triggers.</p> ");
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
