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

public class PortComponentBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PortComponentBean.class;

   public PortComponentBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PortComponentBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.PortComponentBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.PortComponentBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", PortComponentBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DisplayName")) {
         getterName = "getDisplayName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDisplayName";
         }

         currentResult = new PropertyDescriptor("DisplayName", PortComponentBean.class, getterName, setterName);
         descriptors.put("DisplayName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HandlerChains")) {
         getterName = "getHandlerChains";
         setterName = null;
         currentResult = new PropertyDescriptor("HandlerChains", PortComponentBean.class, getterName, setterName);
         descriptors.put("HandlerChains", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyHandlerChains");
         currentResult.setValue("creator", "createHandlerChains");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Handlers")) {
         getterName = "getHandlers";
         setterName = null;
         currentResult = new PropertyDescriptor("Handlers", PortComponentBean.class, getterName, setterName);
         descriptors.put("Handlers", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyHandler");
         currentResult.setValue("creator", "createHandler");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Icon")) {
         getterName = "getIcon";
         setterName = null;
         currentResult = new PropertyDescriptor("Icon", PortComponentBean.class, getterName, setterName);
         descriptors.put("Icon", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createIcon");
         currentResult.setValue("destroyer", "destroyIcon");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", PortComponentBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortComponentName")) {
         getterName = "getPortComponentName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPortComponentName";
         }

         currentResult = new PropertyDescriptor("PortComponentName", PortComponentBean.class, getterName, setterName);
         descriptors.put("PortComponentName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProtocolBinding")) {
         getterName = "getProtocolBinding";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProtocolBinding";
         }

         currentResult = new PropertyDescriptor("ProtocolBinding", PortComponentBean.class, getterName, setterName);
         descriptors.put("ProtocolBinding", currentResult);
         currentResult.setValue("description", "Gets the \"protocol-binding\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceEndpointInterface")) {
         getterName = "getServiceEndpointInterface";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceEndpointInterface";
         }

         currentResult = new PropertyDescriptor("ServiceEndpointInterface", PortComponentBean.class, getterName, setterName);
         descriptors.put("ServiceEndpointInterface", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceImplBean")) {
         getterName = "getServiceImplBean";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceImplBean", PortComponentBean.class, getterName, setterName);
         descriptors.put("ServiceImplBean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createServiceImplBean");
         currentResult.setValue("destroyer", "destroyServiceImplBean");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WsdlPort")) {
         getterName = "getWsdlPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWsdlPort";
         }

         currentResult = new PropertyDescriptor("WsdlPort", PortComponentBean.class, getterName, setterName);
         descriptors.put("WsdlPort", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WsdlService")) {
         getterName = "getWsdlService";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWsdlService";
         }

         currentResult = new PropertyDescriptor("WsdlService", PortComponentBean.class, getterName, setterName);
         descriptors.put("WsdlService", currentResult);
         currentResult.setValue("description", "Gets the \"wsdl-service\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableMtom")) {
         getterName = "isEnableMtom";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableMtom";
         }

         currentResult = new PropertyDescriptor("EnableMtom", PortComponentBean.class, getterName, setterName);
         descriptors.put("EnableMtom", currentResult);
         currentResult.setValue("description", "Gets the \"enable-mtom\" element ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PortComponentBean.class.getMethod("createIcon");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Icon");
      }

      mth = PortComponentBean.class.getMethod("destroyIcon", IconBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Icon");
      }

      mth = PortComponentBean.class.getMethod("createServiceImplBean");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceImplBean");
      }

      mth = PortComponentBean.class.getMethod("destroyServiceImplBean", ServiceImplBeanBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceImplBean");
      }

      mth = PortComponentBean.class.getMethod("createHandler");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Handlers");
      }

      mth = PortComponentBean.class.getMethod("destroyHandler", PortComponentHandlerBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Handlers");
      }

      mth = PortComponentBean.class.getMethod("createHandlerChains");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "HandlerChains");
      }

      mth = PortComponentBean.class.getMethod("destroyHandlerChains", HandlerChainsBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "HandlerChains");
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
