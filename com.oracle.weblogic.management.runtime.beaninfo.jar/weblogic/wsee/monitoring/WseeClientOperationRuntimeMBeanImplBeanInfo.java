package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.WseeClientOperationRuntimeMBean;

public class WseeClientOperationRuntimeMBeanImplBeanInfo extends WseeBaseOperationRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseeClientOperationRuntimeMBean.class;

   public WseeClientOperationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseeClientOperationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseeClientOperationRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p>Describes the state of a particular Web Service operation, such as deployment state and runtime statistics about the execution of the operation.</p> <p>This MBean can describe the operation on a web service client or service. Request statistics relate to outgoing requests on a client or incoming requests on a service. Response statistics relate to an incoming response on a client or an outgoing response on a service.</p> <p>Time values are reported in milliseconds</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseeClientOperationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DispatchTimeAverage")) {
         getterName = "getDispatchTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("DispatchTimeAverage", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DispatchTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Average operation dispatch time for the current measurement period.</p>  <p>Dispatch time refers to the time for WebLogic Server to process the invocation.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
      }

      if (!descriptors.containsKey("DispatchTimeHigh")) {
         getterName = "getDispatchTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("DispatchTimeHigh", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DispatchTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Longest operation dispatch time for the current measurement period.</p>  <p>Dispatch time refers to the time for WebLogic Server to process the invocation.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
      }

      if (!descriptors.containsKey("DispatchTimeLow")) {
         getterName = "getDispatchTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("DispatchTimeLow", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DispatchTimeLow", currentResult);
         currentResult.setValue("description", "<p>Shortest operation dispatch time for the current measurement period.</p>  <p>Dispatch time refers to the time for WebLogic Server to process the invocation.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
      }

      if (!descriptors.containsKey("DispatchTimeTotal")) {
         getterName = "getDispatchTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("DispatchTimeTotal", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DispatchTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Total time for all operation dispatches in the current measurement period.</p>  <p>Dispatch time refers to the time for WebLogic Server to process the invocation.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
      }

      if (!descriptors.containsKey("ErrorCount")) {
         getterName = "getErrorCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ErrorCount", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ErrorCount", currentResult);
         currentResult.setValue("description", "Number of errors sending or receiving a request. ");
      }

      if (!descriptors.containsKey("ExecutionTimeAverage")) {
         getterName = "getExecutionTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeAverage", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Average operation execution time.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeHigh")) {
         getterName = "getExecutionTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeHigh", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Longest operation execution time.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeLow")) {
         getterName = "getExecutionTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeLow", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeLow", currentResult);
         currentResult.setValue("description", "<p>Shortest operation execution time.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeTotal")) {
         getterName = "getExecutionTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeTotal", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Total time for all operation executions.</p> ");
      }

      if (!descriptors.containsKey("InvocationCount")) {
         getterName = "getInvocationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvocationCount", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InvocationCount", currentResult);
         currentResult.setValue("description", "<p>Total number of operation invocations in the current measurement period.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
      }

      if (!descriptors.containsKey("LastError")) {
         getterName = "getLastError";
         setterName = null;
         currentResult = new PropertyDescriptor("LastError", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastError", currentResult);
         currentResult.setValue("description", "Last error that occurred processing a request. ");
      }

      if (!descriptors.containsKey("LastErrorTime")) {
         getterName = "getLastErrorTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastErrorTime", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastErrorTime", currentResult);
         currentResult.setValue("description", "Time on WebLogic Server of the last error for a request (sending or receiving) was detected expressed as the number of milliseconds since midnight, January 1, 1970 UTC. ");
      }

      if (!descriptors.containsKey("LastInvocationTime")) {
         getterName = "getLastInvocationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastInvocationTime", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastInvocationTime", currentResult);
         currentResult.setValue("description", "<p>Time of the last operation request to be sent or received (or 0 if no requests have been sent or received).</p> ");
      }

      if (!descriptors.containsKey("LastResponseError")) {
         getterName = "getLastResponseError";
         setterName = null;
         currentResult = new PropertyDescriptor("LastResponseError", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastResponseError", currentResult);
         currentResult.setValue("description", "Last response error to arrive for this client/service (or null if no errors have occurred). ");
      }

      if (!descriptors.containsKey("LastResponseErrorTime")) {
         getterName = "getLastResponseErrorTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastResponseErrorTime", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastResponseErrorTime", currentResult);
         currentResult.setValue("description", "Time on WebLogic Server of the last error sending or receiving a response (or 0 if no failures have occurred) expressed as the number of milliseconds since midnight, January 1, 1970 UTC. ");
      }

      if (!descriptors.containsKey("LastResponseTime")) {
         getterName = "getLastResponseTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastResponseTime", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastResponseTime", currentResult);
         currentResult.setValue("description", "Time on WebLogic Server of the last response to arrive for this client/service (or 0 if no responses have been received) expressed as the number of milliseconds since midnight, January 1, 1970 UTC. ");
      }

      if (!descriptors.containsKey("OperationName")) {
         getterName = "getOperationName";
         setterName = null;
         currentResult = new PropertyDescriptor("OperationName", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OperationName", currentResult);
         currentResult.setValue("description", "<p>Name of the operation for which runtime information is being provided.</p> ");
      }

      if (!descriptors.containsKey("ResponseCount")) {
         getterName = "getResponseCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseCount", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseCount", currentResult);
         currentResult.setValue("description", "<p>Total number of oresponses generated from operation invocations.</p> ");
      }

      if (!descriptors.containsKey("ResponseErrorCount")) {
         getterName = "getResponseErrorCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseErrorCount", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseErrorCount", currentResult);
         currentResult.setValue("description", "<p>Total number of errors from responses generated from operation invocations.</p> ");
      }

      if (!descriptors.containsKey("ResponseTimeAverage")) {
         getterName = "getResponseTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeAverage", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Average response time from the responses generated from operation invocations. </p> ");
      }

      if (!descriptors.containsKey("ResponseTimeHigh")) {
         getterName = "getResponseTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeHigh", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Longest response time from the responses generated from operation invocations.</p> ");
      }

      if (!descriptors.containsKey("ResponseTimeLow")) {
         getterName = "getResponseTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeLow", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseTimeLow", currentResult);
         currentResult.setValue("description", "<p>Lowest response time from the responses generated from operation invocations.</p> ");
      }

      if (!descriptors.containsKey("ResponseTimeTotal")) {
         getterName = "getResponseTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeTotal", WseeClientOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Total time for all responses generated from operation invocations.</p> ");
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
