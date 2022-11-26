package weblogic.servlet.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.PageFlowActionRuntimeMBean;

public class PageFlowActionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PageFlowActionRuntimeMBean.class;

   public PageFlowActionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PageFlowActionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.servlet.internal.PageFlowActionRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.1.0");
      beanDescriptor.setValue("package", "weblogic.servlet.internal");
      String description = (new String("This MBean represents the statistics for a single action of a PageFlow ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PageFlowActionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActionName")) {
         getterName = "getActionName";
         setterName = null;
         currentResult = new PropertyDescriptor("ActionName", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActionName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", "<p>Returns the name of the Action being described.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExceptionCount")) {
         getterName = "getExceptionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExceptionCount", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExceptionCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HandledExceptionCount")) {
         getterName = "getHandledExceptionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("HandledExceptionCount", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HandledExceptionCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HandledExceptionDispatchTimeAverage")) {
         getterName = "getHandledExceptionDispatchTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("HandledExceptionDispatchTimeAverage", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HandledExceptionDispatchTimeAverage", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HandledExceptionDispatchTimeHigh")) {
         getterName = "getHandledExceptionDispatchTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("HandledExceptionDispatchTimeHigh", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HandledExceptionDispatchTimeHigh", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HandledExceptionDispatchTimeLow")) {
         getterName = "getHandledExceptionDispatchTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("HandledExceptionDispatchTimeLow", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HandledExceptionDispatchTimeLow", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HandledExceptionDispatchTimeTotal")) {
         getterName = "getHandledExceptionDispatchTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("HandledExceptionDispatchTimeTotal", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HandledExceptionDispatchTimeTotal", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastExceptions")) {
         getterName = "getLastExceptions";
         setterName = null;
         currentResult = new PropertyDescriptor("LastExceptions", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastExceptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SuccessCount")) {
         getterName = "getSuccessCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SuccessCount", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuccessCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SuccessDispatchTimeAverage")) {
         getterName = "getSuccessDispatchTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("SuccessDispatchTimeAverage", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuccessDispatchTimeAverage", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SuccessDispatchTimeHigh")) {
         getterName = "getSuccessDispatchTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("SuccessDispatchTimeHigh", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuccessDispatchTimeHigh", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SuccessDispatchTimeLow")) {
         getterName = "getSuccessDispatchTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("SuccessDispatchTimeLow", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuccessDispatchTimeLow", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SuccessDispatchTimeTotal")) {
         getterName = "getSuccessDispatchTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("SuccessDispatchTimeTotal", PageFlowActionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuccessDispatchTimeTotal", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
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
