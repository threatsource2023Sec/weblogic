package weblogic.jaxrs.monitoring.impl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JaxRsResourceMethodRuntimeMBean;

public class JaxRsResourceMethodMBeanImplBeanInfo extends JaxRsResourceMethodBaseMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JaxRsResourceMethodRuntimeMBean.class;

   public JaxRsResourceMethodMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JaxRsResourceMethodMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jaxrs.monitoring.impl.JaxRsResourceMethodMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.jaxrs.monitoring.impl");
      String description = (new String("The runtime MBean of a resource method. A resource method is Java method that is available on a JAX-RS resource class. The resource method is always annotated with exactly one http method designator. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JaxRsResourceMethodRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClassName")) {
         getterName = "getClassName";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassName", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClassName", currentResult);
         currentResult.setValue("description", "<p>Provides the resource class name.</p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ConsumingMediaTypes")) {
         getterName = "getConsumingMediaTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("ConsumingMediaTypes", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConsumingMediaTypes", currentResult);
         currentResult.setValue("description", "<p>Provides the media-types that are consumed by this resource method.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionTimeAverage")) {
         getterName = "getExecutionTimeAverage";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeAverage", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeAverage", currentResult);
         currentResult.setValue("description", "<p>Provides the average execution time (in ms) per execution. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeHigh")) {
         getterName = "getExecutionTimeHigh";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeHigh", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeHigh", currentResult);
         currentResult.setValue("description", "<p>Provides the highest time taken (in ms) by an execution. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeLow")) {
         getterName = "getExecutionTimeLow";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeLow", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeLow", currentResult);
         currentResult.setValue("description", "<p>Provides the lowest time taken (in ms) by an execution. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("ExecutionTimeTotal")) {
         getterName = "getExecutionTimeTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionTimeTotal", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionTimeTotal", currentResult);
         currentResult.setValue("description", "<p>Provides the total execution time (in ms) of all the requests. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("HttpMethod")) {
         getterName = "getHttpMethod";
         setterName = null;
         currentResult = new PropertyDescriptor("HttpMethod", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HttpMethod", currentResult);
         currentResult.setValue("description", "<p>Provides the HTTP designator that is annotated on the resource method.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InvocationCount")) {
         getterName = "getInvocationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvocationCount", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InvocationCount", currentResult);
         currentResult.setValue("description", "<p>Provides the total invocation count.</p> ");
      }

      if (!descriptors.containsKey("LastInvocationTime")) {
         getterName = "getLastInvocationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastInvocationTime", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastInvocationTime", currentResult);
         currentResult.setValue("description", "<p>Provides the last invocation time. Returns 0 if it was never invoked.</p> ");
      }

      if (!descriptors.containsKey("MethodName")) {
         getterName = "getMethodName";
         setterName = null;
         currentResult = new PropertyDescriptor("MethodName", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MethodName", currentResult);
         currentResult.setValue("description", "<p>Provides the method name</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MethodStatistics")) {
         getterName = "getMethodStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("MethodStatistics", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MethodStatistics", currentResult);
         currentResult.setValue("description", "Get execution statistics that contain measurements of times only for execution of resource method. Durations average time, minimum time and maximum time measure only time of execution of resource method code. It does not involve other request processing phases. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ParameterTypes")) {
         getterName = "getParameterTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("ParameterTypes", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ParameterTypes", currentResult);
         currentResult.setValue("description", "<p>Provides the parameter types of the method.</p> ");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Provides the path annotated in the method.</p> ");
      }

      if (!descriptors.containsKey("ProducingMediaTypes")) {
         getterName = "getProducingMediaTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("ProducingMediaTypes", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProducingMediaTypes", currentResult);
         currentResult.setValue("description", "<p>Provides the media-types that are produced by this resource method.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RequestStatistics")) {
         getterName = "getRequestStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestStatistics", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestStatistics", currentResult);
         currentResult.setValue("description", "Get execution statistics that contain measurements of times for whole processing from time when request comes into the Jersey application until the response is written to the underlying IO container. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ReturnType")) {
         getterName = "getReturnType";
         setterName = null;
         currentResult = new PropertyDescriptor("ReturnType", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReturnType", currentResult);
         currentResult.setValue("description", "<p>Provides the return type of the method.</p> ");
      }

      if (!descriptors.containsKey("StartTime")) {
         getterName = "getStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("StartTime", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StartTime", currentResult);
         currentResult.setValue("description", "<p>The start time of this MBean.</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Extended")) {
         getterName = "isExtended";
         setterName = null;
         currentResult = new PropertyDescriptor("Extended", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Extended", currentResult);
         currentResult.setValue("description", "Get the flag indicating whether the resource method is extended or is a core of exposed RESTful API. <p> Extended resource model components are helper components that are not considered as a core of a RESTful API. These can be for example {@code OPTIONS} resource methods added by model processors or {@code application.wadl} resource producing the WADL. Both resource are rather supportive than the core of RESTful API. </p> <p> If not set, the resource will not be defined as extended by default. </p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SubResource")) {
         getterName = "isSubResource";
         setterName = null;
         currentResult = new PropertyDescriptor("SubResource", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubResource", currentResult);
         currentResult.setValue("description", "Indicates whether this MBean represents a sub-resource. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("SubResourceMethod")) {
         getterName = "isSubResourceMethod";
         setterName = null;
         currentResult = new PropertyDescriptor("SubResourceMethod", JaxRsResourceMethodRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubResourceMethod", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this MBean represents a sub-resource method.</p> ");
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
