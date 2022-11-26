package weblogic.jaxrs.monitoring.impl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JaxRsResourceRuntimeMBean;

public class JaxRsResourceMBeanImplBeanInfo extends JaxRsUriMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JaxRsResourceRuntimeMBean.class;

   public JaxRsResourceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JaxRsResourceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jaxrs.monitoring.impl.JaxRsResourceMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jaxrs.monitoring.impl");
      String description = (new String("The runtime MBean of a JAX-RS resource. EJB(Stateless and Singleton), POJO are the two component types that are supported. <p/> Each JAX-RS resource has a scope associated with that. Any object that is managed by a container (such as EJB) will have application scope. All other resources by default will have request scope. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JaxRsResourceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ClassName")) {
         getterName = "getClassName";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassName", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClassName", currentResult);
         currentResult.setValue("description", "Get the class name of the resource. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeAverage")) {
         getterName = "getExecutionTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeAverage", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Provides the average execution time (in ms) per execution. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeHigh")) {
         getterName = "getExecutionTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeHigh", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Provides the highest time taken (in ms) by an execution. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeLow")) {
         getterName = "getExecutionTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeLow", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeLow", currentResult);
         currentResult.setValue("description", "<p>Provides the lowest time taken (in ms) by an execution. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeTotal")) {
         getterName = "getExecutionTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeTotal", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Provides the total execution time (in ms) of all the requests. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("InvocationCount")) {
         getterName = "getInvocationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvocationCount", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InvocationCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total invocation count.</p> ");
      }

      if (!descriptors.containsKey("LastInvocationTime")) {
         getterName = "getLastInvocationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastInvocationTime", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastInvocationTime", currentResult);
         currentResult.setValue("description", "<p>Provides the last invocation time. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("MethodsStatistics")) {
         getterName = "getMethodsStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("MethodsStatistics", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MethodsStatistics", currentResult);
         currentResult.setValue("description", "Get execution statistics that contain measurements of times only for execution of resource methods. Durations average time, minimum time and maximum time measure only time of execution of resource methods code. It does not involve other request processing phases. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "The relative path that is assigned to this resource. ");
      }

      if (!descriptors.containsKey("RequestStatistics")) {
         getterName = "getRequestStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestStatistics", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestStatistics", currentResult);
         currentResult.setValue("description", "Get execution statistics that contain measurements of times for whole processing from time when request comes into the Jersey application until the response is written to the underlying IO container. The statistics involves only requests that were matched to resource methods defined in {@link #getMethodsStatistics()}. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ResourceMethods")) {
         getterName = "getResourceMethods";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceMethods", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceMethods", currentResult);
         currentResult.setValue("description", "Return the resource methods available under this resource. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ResourceType")) {
         getterName = "getResourceType";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceType", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceType", currentResult);
         currentResult.setValue("description", "Provides the type (e.g., POJO, EJB, CDI, etc.) of the resource. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StartTime")) {
         getterName = "getStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("StartTime", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StartTime", currentResult);
         currentResult.setValue("description", "<p>The start time of this MBean.</p> ");
      }

      if (!descriptors.containsKey("SubResourceLocators")) {
         getterName = "getSubResourceLocators";
         setterName = null;
         currentResult = new PropertyDescriptor("SubResourceLocators", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubResourceLocators", currentResult);
         currentResult.setValue("description", "Return the resource methods available under this resource. ");
         currentResult.setValue("relationship", "containment");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Extended")) {
         getterName = "isExtended";
         setterName = null;
         currentResult = new PropertyDescriptor("Extended", JaxRsResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Extended", currentResult);
         currentResult.setValue("description", "Get the flag indicating whether the resource is extended or is a core of exposed RESTful API. <p> Extended resource model components are helper components that are not considered as a core of a RESTful API. These can be for example {@code OPTIONS} resource methods added by model processors or {@code application.wadl} resource producing the WADL. Both resource are rather supportive than the core of RESTful API. </p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JaxRsResourceRuntimeMBean.class.getMethod("lookupResourceMethods", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the resource method. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Provides the resource method identified by the given name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ResourceMethods");
      }

      mth = JaxRsResourceRuntimeMBean.class.getMethod("lookupSubResourceLocators", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the resource method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Provides the resource method identified by the given name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SubResourceLocators");
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
