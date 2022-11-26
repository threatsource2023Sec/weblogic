package weblogic.servlet.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ServletRuntimeMBean;

public class ServletRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServletRuntimeMBean.class;

   public ServletRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServletRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.servlet.internal.ServletRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.servlet.internal");
      String description = (new String("Describes a servlet. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ServletRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ContextPath")) {
         getterName = "getContextPath";
         setterName = null;
         currentResult = new PropertyDescriptor("ContextPath", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ContextPath", currentResult);
         currentResult.setValue("description", "<p>Provides the context path for this servlet.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeAverage")) {
         getterName = "getExecutionTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeAverage", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Provides the average amount of time all invocations of the servlet have executed since created.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeHigh")) {
         getterName = "getExecutionTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeHigh", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Provides the amount of time the single longest invocation of the servlet has executed since created.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeLow")) {
         getterName = "getExecutionTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeLow", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeLow", currentResult);
         currentResult.setValue("description", "<p>Provides the amount of time the single shortest invocation of the servlet has executed since created. Note that for the CounterMonitor, the difference option must be used.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeTotal")) {
         getterName = "getExecutionTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeTotal", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Provides the total amount of time all invocations of the servlet have executed since created.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InvocationTotalCount")) {
         getterName = "getInvocationTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvocationTotalCount", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InvocationTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a total count of the times this servlet has been invoked.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolMaxCapacity")) {
         getterName = "getPoolMaxCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolMaxCapacity", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolMaxCapacity", currentResult);
         currentResult.setValue("description", "<p>Provides the maximum capacity of this servlet for single thread model servlets.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReloadTotalCount")) {
         getterName = "getReloadTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReloadTotalCount", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReloadTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a total count of the number of times this servlet has been reloaded.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletClassName")) {
         getterName = "getServletClassName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServletClassName", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServletClassName", currentResult);
         currentResult.setValue("description", "<p>Provides the servlet class name</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletName")) {
         getterName = "getServletName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServletName", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServletName", currentResult);
         currentResult.setValue("description", "<p>Provides the name of this instance of a servlet.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletPath")) {
         getterName = "getServletPath";
         setterName = null;
         currentResult = new PropertyDescriptor("ServletPath", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServletPath", currentResult);
         currentResult.setValue("description", "<p>Provides the servlet path.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("URL")) {
         getterName = "getURL";
         setterName = null;
         currentResult = new PropertyDescriptor("URL", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("URL", currentResult);
         currentResult.setValue("description", "<p>Provides the value of the URL for this servlet.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("URLPatterns")) {
         getterName = "getURLPatterns";
         setterName = null;
         currentResult = new PropertyDescriptor("URLPatterns", ServletRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("URLPatterns", currentResult);
         currentResult.setValue("description", "<p>Provides a description of the URL patterns for this servlet.</p> ");
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
      Method mth = ServletRuntimeMBean.class.getMethod("invokeAnnotatedMethods", Class.class, Serializable[].class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("annotationToMatch", " The annotation class used to annotate the methods. "), createParameterDescriptor("arguments", " The arguments used for methods call. If there are multiple methods annotated by specified annotation, same arguments will be used for each of these methods call. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Class<? extends Annotation> or Serializable");
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalAccessException       if annotated method does not have public modifier"), BeanInfoHelper.encodeEntities("IllegalArgumentException      if the type or number of the passed arguments do not match the       parameters of invoked method."), BeanInfoHelper.encodeEntities("InvocationTargetException      whenever the invoked method throws exception."), BeanInfoHelper.encodeEntities("ClassCastException      if annotated method's return object can't be serialized.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Invokes annotated public methods on the servlet object which is managed by this ServletRuntimeMBean. The annotation annotated on invoked methods is specified by application.  The annotated methods will be executed under the role of current JMX user, if can't find the JMX user, the method will be executed under the anonymous role. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Class<? extends Annotation> or Serializable");
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
