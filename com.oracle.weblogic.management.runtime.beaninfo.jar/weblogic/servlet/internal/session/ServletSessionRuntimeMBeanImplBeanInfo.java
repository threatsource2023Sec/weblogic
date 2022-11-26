package weblogic.servlet.internal.session;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ServletSessionRuntimeMBean;

public class ServletSessionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServletSessionRuntimeMBean.class;

   public ServletSessionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServletSessionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.servlet.internal.session.ServletSessionRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "As of WebLogic 9.0, use WebAppComponentRuntimeMBean instead. ");
      beanDescriptor.setValue("package", "weblogic.servlet.internal.session");
      String description = (new String("This class is used for monitoring a WebLogic servlet session within a WebLogic server. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ServletSessionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("MainAttribute")) {
         getterName = "getMainAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("MainAttribute", ServletSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MainAttribute", currentResult);
         currentResult.setValue("description", "<p>Provides a copy (as a string) of an attribute specified by the user, such as user-name, associated with this session.</p>  <p>Gets a Stringified copy of an attribute specified by the user (like user-name) associated with this session. Where should this attribute be specified? In ServletDeploymentRuntimeMBean?</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxInactiveInterval")) {
         getterName = "getMaxInactiveInterval";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxInactiveInterval", ServletSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxInactiveInterval", currentResult);
         currentResult.setValue("description", "<p> Returns the timeout (seconds) for the session </p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeLastAccessed")) {
         getterName = "getTimeLastAccessed";
         setterName = null;
         currentResult = new PropertyDescriptor("TimeLastAccessed", ServletSessionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TimeLastAccessed", currentResult);
         currentResult.setValue("description", "<p>Provides a record of the last time this session was accessed.</p> ");
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
      Method mth = ServletSessionRuntimeMBean.class.getMethod("invalidate");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Invalidates this session.</p> ");
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
