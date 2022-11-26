package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ServiceEndpointInterfaceMappingBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ServiceEndpointInterfaceMappingBean.class;

   public ServiceEndpointInterfaceMappingBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServiceEndpointInterfaceMappingBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.ServiceEndpointInterfaceMappingBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.ServiceEndpointInterfaceMappingBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", ServiceEndpointInterfaceMappingBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceEndpointInterface")) {
         getterName = "getServiceEndpointInterface";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceEndpointInterface";
         }

         currentResult = new PropertyDescriptor("ServiceEndpointInterface", ServiceEndpointInterfaceMappingBean.class, getterName, setterName);
         descriptors.put("ServiceEndpointInterface", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceEndpointMethodMappings")) {
         getterName = "getServiceEndpointMethodMappings";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceEndpointMethodMappings", ServiceEndpointInterfaceMappingBean.class, getterName, setterName);
         descriptors.put("ServiceEndpointMethodMappings", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createServiceEndpointMethodMapping");
         currentResult.setValue("destroyer", "destroyServiceEndpointMethodMapping");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WsdlBinding")) {
         getterName = "getWsdlBinding";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWsdlBinding";
         }

         currentResult = new PropertyDescriptor("WsdlBinding", ServiceEndpointInterfaceMappingBean.class, getterName, setterName);
         descriptors.put("WsdlBinding", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WsdlPortType")) {
         getterName = "getWsdlPortType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWsdlPortType";
         }

         currentResult = new PropertyDescriptor("WsdlPortType", ServiceEndpointInterfaceMappingBean.class, getterName, setterName);
         descriptors.put("WsdlPortType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ServiceEndpointInterfaceMappingBean.class.getMethod("createServiceEndpointMethodMapping");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceEndpointMethodMappings");
      }

      mth = ServiceEndpointInterfaceMappingBean.class.getMethod("destroyServiceEndpointMethodMapping", ServiceEndpointMethodMappingBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceEndpointMethodMappings");
      }

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
