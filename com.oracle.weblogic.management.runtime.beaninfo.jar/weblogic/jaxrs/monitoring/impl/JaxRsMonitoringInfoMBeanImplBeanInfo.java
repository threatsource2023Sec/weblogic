package weblogic.jaxrs.monitoring.impl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JaxRsMonitoringInfoRuntimeMBean;

public class JaxRsMonitoringInfoMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JaxRsMonitoringInfoRuntimeMBean.class;

   public JaxRsMonitoringInfoMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JaxRsMonitoringInfoMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jaxrs.monitoring.impl.JaxRsMonitoringInfoMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "12.2.1.0.0 Functionality abstracted to other JAX-RS Runtime MBeans. ");
      beanDescriptor.setValue("package", "weblogic.jaxrs.monitoring.impl");
      String description = (new String("This interface is to be implemented by various JAX-RS objects that are going provide runtime metrics The list of JAX-RS objects include 1. JAX-RS Application 2. JAX-RS Resource 3. JAX-RS Resource Method ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JaxRsMonitoringInfoRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ExecutionTimeAverage")) {
         getterName = "getExecutionTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeAverage", JaxRsMonitoringInfoRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Provides the average execution time (in ms) per execution. Returns 0 if it was never invoked.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeHigh")) {
         getterName = "getExecutionTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeHigh", JaxRsMonitoringInfoRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Provides the highest time taken (in ms) by an execution. Returns 0 if it was never invoked.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeLow")) {
         getterName = "getExecutionTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeLow", JaxRsMonitoringInfoRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeLow", currentResult);
         currentResult.setValue("description", "<p>Provides the lowest time taken (in ms) by an execution. Returns 0 if it was never invoked.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeTotal")) {
         getterName = "getExecutionTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeTotal", JaxRsMonitoringInfoRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Provides the total execution time (in ms) of all the requests. Returns 0 if it was never invoked.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InvocationCount")) {
         getterName = "getInvocationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvocationCount", JaxRsMonitoringInfoRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InvocationCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total invocation count.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastInvocationTime")) {
         getterName = "getLastInvocationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastInvocationTime", JaxRsMonitoringInfoRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastInvocationTime", currentResult);
         currentResult.setValue("description", "<p>Provides the last invocation time. Returns 0 if it was never invoked.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StartTime")) {
         getterName = "getStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("StartTime", JaxRsMonitoringInfoRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StartTime", currentResult);
         currentResult.setValue("description", "<p>The start time of this MBean.</p> ");
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
