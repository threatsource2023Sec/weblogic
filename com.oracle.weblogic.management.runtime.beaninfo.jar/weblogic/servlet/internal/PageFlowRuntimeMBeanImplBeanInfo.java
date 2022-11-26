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
import weblogic.management.runtime.PageFlowRuntimeMBean;

public class PageFlowRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PageFlowRuntimeMBean.class;

   public PageFlowRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PageFlowRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.servlet.internal.PageFlowRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.1.0");
      beanDescriptor.setValue("package", "weblogic.servlet.internal");
      String description = (new String("This MBean represents a PageFlow <code>type</code>. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PageFlowRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Actions")) {
         getterName = "getActions";
         setterName = null;
         currentResult = new PropertyDescriptor("Actions", PageFlowRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Actions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClassName")) {
         getterName = "getClassName";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassName", PageFlowRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClassName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CreateCount")) {
         getterName = "getCreateCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CreateCount", PageFlowRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CreateCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestroyCount")) {
         getterName = "getDestroyCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DestroyCount", PageFlowRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestroyCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastResetTime")) {
         getterName = "getLastResetTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastResetTime", PageFlowRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastResetTime", currentResult);
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
      Method mth = PageFlowRuntimeMBean.class.getMethod("getAction", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("actionName", "the name of the action ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = PageFlowRuntimeMBean.class.getMethod("reset");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "Resets all counters in this MBean ");
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
