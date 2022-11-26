package weblogic.jaxrs.monitoring.impl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JaxRsApplicationRuntimeMBean;

public class JaxRsApplicationMBeanImplBeanInfo extends JaxRsMonitoringInfoMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JaxRsApplicationRuntimeMBean.class;

   public JaxRsApplicationMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JaxRsApplicationMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jaxrs.monitoring.impl.JaxRsApplicationMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jaxrs.monitoring.impl");
      String description = (new String("This the runtime server MBean for a JAX-RS application. There can be more than one JAX-RS application per Web Application. The list of available JAX-RS applications can be got through the method <code>public JaxRsApplicationMBean[] getJaxRsApplications() at weblogic.management.runtime.WebAppComponentRuntimeMBean</code> The lookup method to find a specific JAX-RS application with given name is <code>public JaxRsApplicationMBean lookupJaxRsApplication(String name)</code> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JaxRsApplicationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AllRegisteredClasses")) {
         getterName = "getAllRegisteredClasses";
         setterName = null;
         currentResult = new PropertyDescriptor("AllRegisteredClasses", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("AllRegisteredClasses", currentResult);
         currentResult.setValue("description", "Get all JAX-RS/Jersey component and provider classes registered either explicitly or via discovery (e.g. class-path scanning, from META-INF/services entries) ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Set<String>");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ApplicationClass")) {
         getterName = "getApplicationClass";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationClass", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ApplicationClass", currentResult);
         currentResult.setValue("description", "Get the application class used for configuration of Jersey application. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationName", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "Get the application name. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ErrorCount")) {
         getterName = "getErrorCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ErrorCount", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ErrorCount", currentResult);
         currentResult.setValue("description", "<p>Provides the errors count, the number of un-handled exceptions from the JAX-RS application</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 Use {@link JaxRsExceptionMapperStatisticsRuntimeMBean#getUnsuccessfulMappings()} instead. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ExceptionMapperStatistics")) {
         getterName = "getExceptionMapperStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("ExceptionMapperStatistics", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ExceptionMapperStatistics", currentResult);
         currentResult.setValue("description", "Get statistics about registered exception mappers. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ExecutionTimeAverage")) {
         getterName = "getExecutionTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeAverage", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ExecutionTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Provides the average execution time (in ms) per execution. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeHigh")) {
         getterName = "getExecutionTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeHigh", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ExecutionTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Provides the highest time taken (in ms) by an execution. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeLow")) {
         getterName = "getExecutionTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeLow", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ExecutionTimeLow", currentResult);
         currentResult.setValue("description", "<p>Provides the lowest time taken (in ms) by an execution. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeTotal")) {
         getterName = "getExecutionTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeTotal", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ExecutionTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Provides the total execution time (in ms) of all the requests. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("HttpMethodCounts")) {
         getterName = "getHttpMethodCounts";
         setterName = null;
         currentResult = new PropertyDescriptor("HttpMethodCounts", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("HttpMethodCounts", currentResult);
         currentResult.setValue("description", "<p>Provides map with all method names(String) as keys and their count(long) as values</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for HashMap");
      }

      if (!descriptors.containsKey("InvocationCount")) {
         getterName = "getInvocationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvocationCount", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("InvocationCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total invocation count.</p> ");
      }

      if (!descriptors.containsKey("LastErrorDetails")) {
         getterName = "getLastErrorDetails";
         setterName = null;
         currentResult = new PropertyDescriptor("LastErrorDetails", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("LastErrorDetails", currentResult);
         currentResult.setValue("description", "<p>Provides details of the last error. It returns null if there is not exception yet</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastErrorMapper")) {
         getterName = "getLastErrorMapper";
         setterName = null;
         currentResult = new PropertyDescriptor("LastErrorMapper", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("LastErrorMapper", currentResult);
         currentResult.setValue("description", "<p>Provides the exception mapper class used against the last error (if any) occurred. It returns null if no error has been mapped yet.</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastErrorTime")) {
         getterName = "getLastErrorTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastErrorTime", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("LastErrorTime", currentResult);
         currentResult.setValue("description", "<p>Provides the date-time at which the last error(if any) occurred</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastHttpMethod")) {
         getterName = "getLastHttpMethod";
         setterName = null;
         currentResult = new PropertyDescriptor("LastHttpMethod", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("LastHttpMethod", currentResult);
         currentResult.setValue("description", "<p>Provides the http method name of the last request</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastInvocationTime")) {
         getterName = "getLastInvocationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastInvocationTime", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("LastInvocationTime", currentResult);
         currentResult.setValue("description", "<p>Provides the last invocation time. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("LastResponseCode")) {
         getterName = "getLastResponseCode";
         setterName = null;
         currentResult = new PropertyDescriptor("LastResponseCode", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("LastResponseCode", currentResult);
         currentResult.setValue("description", "<p>Provides the response code of the last response. Returns -1 if there is no response yet.</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 Use {@link JaxRsResponseStatisticsRuntimeMBean#getLastResponseCode()} instead. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "Get the map of configuration properties converted to strings. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map<String,String>");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RegisteredClasses")) {
         getterName = "getRegisteredClasses";
         setterName = null;
         currentResult = new PropertyDescriptor("RegisteredClasses", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("RegisteredClasses", currentResult);
         currentResult.setValue("description", "Get a set of string names of resource classes registered by the user. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Set<String>");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RegisteredInstances")) {
         getterName = "getRegisteredInstances";
         setterName = null;
         currentResult = new PropertyDescriptor("RegisteredInstances", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("RegisteredInstances", currentResult);
         currentResult.setValue("description", "Get a set of string names of classes of user registered instances. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Set<String>");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RequestStatistics")) {
         getterName = "getRequestStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestStatistics", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("RequestStatistics", currentResult);
         currentResult.setValue("description", "Get the global application statistics of request execution. The statistics are not bound any specific resource or resource method and contains information about all requests that application handles. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ResourceConfig")) {
         getterName = "getResourceConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceConfig", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ResourceConfig", currentResult);
         currentResult.setValue("description", "<p>Provides the resource config MBean of the JAX-RS application.</p> <p> Every JAX-RS application is configured through a resource config. There are several types of resource configs available in Jersey. This method gets the details of the corresponding resource config created by Jersey for this JAX-RS application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "12.2.1.0.0 Use {@link #getProperties()} / {@link #getApplicationClass()} instead. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourcePattern")) {
         getterName = "getResourcePattern";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourcePattern", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ResourcePattern", currentResult);
         currentResult.setValue("description", "Gets the WSM resource pattern for this JAX-RS application. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseCodeCounts")) {
         getterName = "getResponseCodeCounts";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseCodeCounts", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ResponseCodeCounts", currentResult);
         currentResult.setValue("description", "<p>Provides map with all response codes(int) as keys and their count(long) as values</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 Use {@link JaxRsResponseStatisticsRuntimeMBean#getResponseCodes()} instead. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for HashMap");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResponseStatistics")) {
         getterName = "getResponseStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseStatistics", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ResponseStatistics", currentResult);
         currentResult.setValue("description", "Get global application response statistics. The statistics are not bound any specific resource or resource method and contains information about all responses that application creates. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RootPath")) {
         getterName = "getRootPath";
         setterName = null;
         currentResult = new PropertyDescriptor("RootPath", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("RootPath", currentResult);
         currentResult.setValue("description", "Retrieve the root path for this JAX-RS Application.  The path consists of (servlet) context path + application (servlet or filter) path. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("RootResources")) {
         getterName = "getRootResources";
         setterName = null;
         currentResult = new PropertyDescriptor("RootResources", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("RootResources", currentResult);
         currentResult.setValue("description", "<p>Provides the array of root resources MBean</p> <p/> <p>Every JAX-RS application contains 1 or more root resources. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "12.2.1.0.0 Use {@link #getRootResourcesByClass()} instead. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RootResourcesByClass")) {
         getterName = "getRootResourcesByClass";
         setterName = null;
         currentResult = new PropertyDescriptor("RootResourcesByClass", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("RootResourcesByClass", currentResult);
         currentResult.setValue("description", "Get the statistics for each resource {@link Class} that is deployed in the application. Note that one resource class can serve request matched to different URIs. The array contains resource classes which are registered in the resource model plus resource classes of sub resources returned from sub resource locators. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RootResourcesByUri")) {
         getterName = "getRootResourcesByUri";
         setterName = null;
         currentResult = new PropertyDescriptor("RootResourcesByUri", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("RootResourcesByUri", currentResult);
         currentResult.setValue("description", "Get the statistics for each URI that is exposed in the application. The array URIs that are available in application without URIs available in sub resource locators and URIs that are available trough sub resource locators and were already matched by any request. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Servlet")) {
         getterName = "getServlet";
         setterName = null;
         currentResult = new PropertyDescriptor("Servlet", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("Servlet", currentResult);
         currentResult.setValue("description", "<p>Provides the MBean of the servlet that handles the corresponding JAX-RS application.</p> <p>Users can get the servlet related metrics from the returned ServletRuntimeMBean </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StartTime")) {
         getterName = "getStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("StartTime", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("StartTime", currentResult);
         currentResult.setValue("description", "Get the start time of the application (when application was initialized). ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WadlUrl")) {
         getterName = "getWadlUrl";
         setterName = null;
         currentResult = new PropertyDescriptor("WadlUrl", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("WadlUrl", currentResult);
         currentResult.setValue("description", "Retrieve the WADL URI for this JAX-RS Application. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationEnabled")) {
         getterName = "isApplicationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationEnabled";
         }

         currentResult = new PropertyDescriptor("ApplicationEnabled", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("ApplicationEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this JAX-RS application is currently enabled </p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 Currently not supported. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WadlGenerationEnabled")) {
         getterName = "isWadlGenerationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWadlGenerationEnabled";
         }

         currentResult = new PropertyDescriptor("WadlGenerationEnabled", JaxRsApplicationRuntimeMBean.class, getterName, setterName);
         descriptors.put("WadlGenerationEnabled", currentResult);
         currentResult.setValue("description", "Indicates whether this JAX-RS application currently has WADL generation enabled. ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JaxRsApplicationRuntimeMBean.class.getMethod("lookupRootResourcesByClass", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("className", "The name of the resource MBean. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Provides the root resource MBean identified by the class name of the resource. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "RootResourcesByClass");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JaxRsApplicationRuntimeMBean.class.getMethod("lookupRootResourcesByUri", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("uri", "uri (path) of the resource MBean. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Provides the root resource MBean identified by the uri path of the resource. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "RootResourcesByUri");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = JaxRsApplicationRuntimeMBean.class.getMethod("lookupRootResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the resource MBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "12.2.1.0.0 Use {@link #lookupRootResourcesByClass(String)} instead. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the root resource MBean identified by the name</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "RootResources");
      }

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
