package weblogic.ejb.container.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.EJBTimerRuntimeMBean;

public class EJBTimerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EJBTimerRuntimeMBean.class;

   public EJBTimerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EJBTimerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.monitoring.EJBTimerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.ejb.container.monitoring");
      String description = (new String("This interface contains accessor methods for all EJB Timer runtime information collected for an EJB. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EJBTimerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveTimerCount")) {
         getterName = "getActiveTimerCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveTimerCount", EJBTimerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveTimerCount", currentResult);
         currentResult.setValue("description", "<p>Provides the current number of active timers for this EJB</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CancelledTimerCount")) {
         getterName = "getCancelledTimerCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CancelledTimerCount", EJBTimerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CancelledTimerCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total number of timers that have been explicitly cancelled for this EJB.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DisabledTimerCount")) {
         getterName = "getDisabledTimerCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DisabledTimerCount", EJBTimerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DisabledTimerCount", currentResult);
         currentResult.setValue("description", "<p>Provides the current number of timers temporarily disabled for this EJB</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeoutCount")) {
         getterName = "getTimeoutCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TimeoutCount", EJBTimerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TimeoutCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total number of successful timeout notifications that have been made for this EJB.</p> ");
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
      Method mth = EJBTimerRuntimeMBean.class.getMethod("activateDisabledTimers");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Activate any temporarily disabled timers.</p> ");
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
