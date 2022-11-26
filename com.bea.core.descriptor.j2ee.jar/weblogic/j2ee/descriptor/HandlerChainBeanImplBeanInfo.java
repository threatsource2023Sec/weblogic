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

public class HandlerChainBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = HandlerChainBean.class;

   public HandlerChainBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public HandlerChainBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.HandlerChainBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.HandlerChainBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Handlers")) {
         getterName = "getHandlers";
         setterName = null;
         currentResult = new PropertyDescriptor("Handlers", HandlerChainBean.class, getterName, setterName);
         descriptors.put("Handlers", currentResult);
         currentResult.setValue("description", "Gets array of all \"handler\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyHandler");
         currentResult.setValue("creator", "createHandler");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", HandlerChainBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "Gets the \"id\" attribute ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortNamePattern")) {
         getterName = "getPortNamePattern";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPortNamePattern";
         }

         currentResult = new PropertyDescriptor("PortNamePattern", HandlerChainBean.class, getterName, setterName);
         descriptors.put("PortNamePattern", currentResult);
         currentResult.setValue("description", "Gets the \"port-name-pattern\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProtocolBindings")) {
         getterName = "getProtocolBindings";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProtocolBindings";
         }

         currentResult = new PropertyDescriptor("ProtocolBindings", HandlerChainBean.class, getterName, setterName);
         descriptors.put("ProtocolBindings", currentResult);
         currentResult.setValue("description", "Gets the \"protocol-bindings\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceNamePattern")) {
         getterName = "getServiceNamePattern";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceNamePattern";
         }

         currentResult = new PropertyDescriptor("ServiceNamePattern", HandlerChainBean.class, getterName, setterName);
         descriptors.put("ServiceNamePattern", currentResult);
         currentResult.setValue("description", "Gets the \"service-name-pattern\" element ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = HandlerChainBean.class.getMethod("createHandler");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Handlers");
      }

      mth = HandlerChainBean.class.getMethod("destroyHandler", PortComponentHandlerBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Handlers");
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
