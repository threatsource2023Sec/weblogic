package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.WseeAggregatableBaseOperationRuntimeMBean;

public class WseeAggregatableBaseOperationRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseeAggregatableBaseOperationRuntimeMBean.class;

   public WseeAggregatableBaseOperationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseeAggregatableBaseOperationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseeAggregatableBaseOperationRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.6.0");
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p>Describes Web Service operation state, such as deployment state and runtime statistics.  The derived MBean determines whether this state is for a single operation or whether it is aggregated across operations.</p>  <p>This MBean can describe the operation(s) on a web service client or service.  Request statistics relate to outgoing requests on a client or incoming requests on a service. Response statistics relate to an incoming response on a client or an outgoing response on a service.</p> <p>Time values are reported in milliseconds.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseeAggregatableBaseOperationRuntimeMBean");
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
         currentResult = new PropertyDescriptor("DispatchTimeAverage", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DispatchTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Average operation dispatch time for the current measurement period.</p>  <p>Dispatch time refers to the time for WebLogic Server to process the invocation.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DispatchTimeHigh")) {
         getterName = "getDispatchTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("DispatchTimeHigh", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DispatchTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Longest operation dispatch time for the current measurement period.</p>  <p>Dispatch time refers to the time for WebLogic Server to process the invocation.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DispatchTimeLow")) {
         getterName = "getDispatchTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("DispatchTimeLow", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DispatchTimeLow", currentResult);
         currentResult.setValue("description", "<p>Shortest operation dispatch time for the current measurement period.</p>  <p>Dispatch time refers to the time for WebLogic Server to process the invocation.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DispatchTimeTotal")) {
         getterName = "getDispatchTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("DispatchTimeTotal", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DispatchTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Total time for all operation dispatches in the current measurement period.</p>  <p>Dispatch time refers to the time for WebLogic Server to process the invocation.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ErrorCount")) {
         getterName = "getErrorCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ErrorCount", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ErrorCount", currentResult);
         currentResult.setValue("description", "Number of errors sending or receiving a request. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeAverage")) {
         getterName = "getExecutionTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeAverage", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Average operation execution time.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeHigh")) {
         getterName = "getExecutionTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeHigh", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Longest operation execution time.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeLow")) {
         getterName = "getExecutionTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeLow", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeLow", currentResult);
         currentResult.setValue("description", "<p>Shortest operation execution time.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeTotal")) {
         getterName = "getExecutionTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeTotal", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Total time for all operation executions.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InvocationCount")) {
         getterName = "getInvocationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvocationCount", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InvocationCount", currentResult);
         currentResult.setValue("description", "<p>Total number of operation invocations in the current measurement period.</p>  <p>The measurement period typically starts when WebLogic Server is first started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastError")) {
         getterName = "getLastError";
         setterName = null;
         currentResult = new PropertyDescriptor("LastError", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastError", currentResult);
         currentResult.setValue("description", "Last error that occurred processing a request. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastErrorTime")) {
         getterName = "getLastErrorTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastErrorTime", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastErrorTime", currentResult);
         currentResult.setValue("description", "Time on WebLogic Server of the last error for a request (sending or receiving) was detected expressed as the number of milliseconds since midnight, January 1, 1970 UTC. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastInvocationTime")) {
         getterName = "getLastInvocationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastInvocationTime", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastInvocationTime", currentResult);
         currentResult.setValue("description", "<p>Time of the last operation request to be sent or received (or 0 if no requests have been sent or received).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastResponseError")) {
         getterName = "getLastResponseError";
         setterName = null;
         currentResult = new PropertyDescriptor("LastResponseError", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastResponseError", currentResult);
         currentResult.setValue("description", "Last response error to arrive for this client/service (or null if no errors have occurred). ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastResponseErrorTime")) {
         getterName = "getLastResponseErrorTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastResponseErrorTime", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastResponseErrorTime", currentResult);
         currentResult.setValue("description", "Time on WebLogic Server of the last error sending or receiving a response (or 0 if no failures have occurred) expressed as the number of milliseconds since midnight, January 1, 1970 UTC. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastResponseTime")) {
         getterName = "getLastResponseTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastResponseTime", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastResponseTime", currentResult);
         currentResult.setValue("description", "Time on WebLogic Server of the last response to arrive for this client/service (or 0 if no responses have been received) expressed as the number of milliseconds since midnight, January 1, 1970 UTC. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseCount")) {
         getterName = "getResponseCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseCount", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseCount", currentResult);
         currentResult.setValue("description", "<p>Total number of oresponses generated from operation invocations.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseErrorCount")) {
         getterName = "getResponseErrorCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseErrorCount", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseErrorCount", currentResult);
         currentResult.setValue("description", "<p>Total number of errors from responses generated from operation invocations.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseTimeAverage")) {
         getterName = "getResponseTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeAverage", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Average response time from the responses generated from operation invocations. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseTimeHigh")) {
         getterName = "getResponseTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeHigh", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Longest response time from the responses generated from operation invocations.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseTimeLow")) {
         getterName = "getResponseTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeLow", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseTimeLow", currentResult);
         currentResult.setValue("description", "<p>Lowest response time from the responses generated from operation invocations.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseTimeTotal")) {
         getterName = "getResponseTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeTotal", WseeAggregatableBaseOperationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Total time for all responses generated from operation invocations.</p> ");
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
