package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class WebserviceDescriptionBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WebserviceDescriptionBean.class;

   public WebserviceDescriptionBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebserviceDescriptionBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WebserviceDescriptionBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML webservice-descriptionType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WebserviceDescriptionBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("PortComponents")) {
         getterName = "getPortComponents";
         setterName = null;
         currentResult = new PropertyDescriptor("PortComponents", WebserviceDescriptionBean.class, getterName, setterName);
         descriptors.put("PortComponents", currentResult);
         currentResult.setValue("description", "Gets array of all \"port-component\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPortComponent");
         currentResult.setValue("creator", "createPortComponent");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebserviceDescriptionName")) {
         getterName = "getWebserviceDescriptionName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWebserviceDescriptionName";
         }

         currentResult = new PropertyDescriptor("WebserviceDescriptionName", WebserviceDescriptionBean.class, getterName, setterName);
         descriptors.put("WebserviceDescriptionName", currentResult);
         currentResult.setValue("description", "Gets the \"webservice-description-name\" element ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebserviceType")) {
         getterName = "getWebserviceType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWebserviceType";
         }

         currentResult = new PropertyDescriptor("WebserviceType", WebserviceDescriptionBean.class, getterName, setterName);
         descriptors.put("WebserviceType", currentResult);
         currentResult.setValue("description", "Gets the \"webservice-type\" element ");
         setPropertyDescriptorDefault(currentResult, "JAXRPC");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WsdlPublishFile")) {
         getterName = "getWsdlPublishFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWsdlPublishFile";
         }

         currentResult = new PropertyDescriptor("WsdlPublishFile", WebserviceDescriptionBean.class, getterName, setterName);
         descriptors.put("WsdlPublishFile", currentResult);
         currentResult.setValue("description", "Gets the \"wsdl-publish-file\" element ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebserviceDescriptionBean.class.getMethod("createPortComponent");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PortComponents");
      }

      mth = WebserviceDescriptionBean.class.getMethod("destroyPortComponent", PortComponentBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PortComponents");
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
