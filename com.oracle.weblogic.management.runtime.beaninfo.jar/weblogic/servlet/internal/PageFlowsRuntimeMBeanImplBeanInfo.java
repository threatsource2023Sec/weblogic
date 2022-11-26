package weblogic.servlet.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.PageFlowsRuntimeMBean;

public class PageFlowsRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PageFlowsRuntimeMBean.class;

   public PageFlowsRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PageFlowsRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.servlet.internal.PageFlowsRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.1.0");
      beanDescriptor.setValue("package", "weblogic.servlet.internal");
      String description = (new String("This MBean just hides the lazy construction of PageFlowRuntimeMBeans and provides a top-level reset() operation for all PageFlows in the webapp. It also minimizes the impact on the WebAppRuntimeMBean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PageFlowsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AppName")) {
         getterName = "getAppName";
         setterName = null;
         currentResult = new PropertyDescriptor("AppName", PageFlowsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AppName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContextPath")) {
         getterName = "getContextPath";
         setterName = null;
         currentResult = new PropertyDescriptor("ContextPath", PageFlowsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ContextPath", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpServerName")) {
         getterName = "getHttpServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("HttpServerName", PageFlowsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HttpServerName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PageFlows")) {
         getterName = "getPageFlows";
         setterName = null;
         currentResult = new PropertyDescriptor("PageFlows", PageFlowsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PageFlows", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerName")) {
         getterName = "getServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerName", PageFlowsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerName", currentResult);
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
      Method mth = PageFlowsRuntimeMBean.class.getMethod("getPageFlow", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("pageFlowClassName", "the classname of the target pageflow ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = PageFlowsRuntimeMBean.class.getMethod("reset");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "Reset counters for this PageFlow type and all actions. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
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
