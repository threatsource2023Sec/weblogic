package weblogic.jaxrs.monitoring.impl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JaxRsUriRuntimeMBean;

public class JaxRsUriMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JaxRsUriRuntimeMBean.class;

   public JaxRsUriMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JaxRsUriMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jaxrs.monitoring.impl.JaxRsUriMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.jaxrs.monitoring.impl");
      String description = (new String("Base class for JAX-RS resources. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JaxRsUriRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("MethodsStatistics")) {
         getterName = "getMethodsStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("MethodsStatistics", JaxRsUriRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MethodsStatistics", currentResult);
         currentResult.setValue("description", "Get execution statistics that contain measurements of times only for execution of resource methods. Durations average time, minimum time and maximum time measure only time of execution of resource methods code. It does not involve other request processing phases. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", JaxRsUriRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "The relative path that is assigned to this resource. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestStatistics")) {
         getterName = "getRequestStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestStatistics", JaxRsUriRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestStatistics", currentResult);
         currentResult.setValue("description", "Get execution statistics that contain measurements of times for whole processing from time when request comes into the Jersey application until the response is written to the underlying IO container. The statistics involves only requests that were matched to resource methods defined in {@link #getMethodsStatistics()}. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceMethods")) {
         getterName = "getResourceMethods";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceMethods", JaxRsUriRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceMethods", currentResult);
         currentResult.setValue("description", "Return the resource methods available under this resource. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubResourceLocators")) {
         getterName = "getSubResourceLocators";
         setterName = null;
         currentResult = new PropertyDescriptor("SubResourceLocators", JaxRsUriRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubResourceLocators", currentResult);
         currentResult.setValue("description", "Return the resource methods available under this resource. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Extended")) {
         getterName = "isExtended";
         setterName = null;
         currentResult = new PropertyDescriptor("Extended", JaxRsUriRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Extended", currentResult);
         currentResult.setValue("description", "Get the flag indicating whether the resource is extended or is a core of exposed RESTful API. <p> Extended resource model components are helper components that are not considered as a core of a RESTful API. These can be for example {@code OPTIONS} resource methods added by model processors or {@code application.wadl} resource producing the WADL. Both resource are rather supportive than the core of RESTful API. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JaxRsUriRuntimeMBean.class.getMethod("lookupResourceMethods", String.class);
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

      mth = JaxRsUriRuntimeMBean.class.getMethod("lookupSubResourceLocators", String.class);
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
