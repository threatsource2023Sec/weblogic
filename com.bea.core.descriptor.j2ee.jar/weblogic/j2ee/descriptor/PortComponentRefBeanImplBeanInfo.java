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

public class PortComponentRefBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PortComponentRefBean.class;

   public PortComponentRefBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PortComponentRefBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.PortComponentRefBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.PortComponentRefBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Addressing")) {
         getterName = "getAddressing";
         setterName = null;
         currentResult = new PropertyDescriptor("Addressing", PortComponentRefBean.class, getterName, setterName);
         descriptors.put("Addressing", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createAddressing");
         currentResult.setValue("destroyer", "destroyAddressing");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", PortComponentRefBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MtomThreshold")) {
         getterName = "getMtomThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMtomThreshold";
         }

         currentResult = new PropertyDescriptor("MtomThreshold", PortComponentRefBean.class, getterName, setterName);
         descriptors.put("MtomThreshold", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortComponentLink")) {
         getterName = "getPortComponentLink";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPortComponentLink";
         }

         currentResult = new PropertyDescriptor("PortComponentLink", PortComponentRefBean.class, getterName, setterName);
         descriptors.put("PortComponentLink", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RespectBinding")) {
         getterName = "getRespectBinding";
         setterName = null;
         currentResult = new PropertyDescriptor("RespectBinding", PortComponentRefBean.class, getterName, setterName);
         descriptors.put("RespectBinding", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyRespectBinding");
         currentResult.setValue("creator", "createRespectBinding");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceEndpointInterface")) {
         getterName = "getServiceEndpointInterface";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceEndpointInterface";
         }

         currentResult = new PropertyDescriptor("ServiceEndpointInterface", PortComponentRefBean.class, getterName, setterName);
         descriptors.put("ServiceEndpointInterface", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableMtom")) {
         getterName = "isEnableMtom";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableMtom";
         }

         currentResult = new PropertyDescriptor("EnableMtom", PortComponentRefBean.class, getterName, setterName);
         descriptors.put("EnableMtom", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PortComponentRefBean.class.getMethod("createAddressing");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Addressing");
      }

      mth = PortComponentRefBean.class.getMethod("destroyAddressing", AddressingBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Addressing");
      }

      mth = PortComponentRefBean.class.getMethod("createRespectBinding");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RespectBinding");
      }

      mth = PortComponentRefBean.class.getMethod("destroyRespectBinding", RespectBindingBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RespectBinding");
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
