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

public class WeblogicApplicationClientBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WeblogicApplicationClientBean.class;

   public WeblogicApplicationClientBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicApplicationClientBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CdiDescriptor")) {
         getterName = "getCdiDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("CdiDescriptor", WeblogicApplicationClientBean.class, getterName, setterName);
         descriptors.put("CdiDescriptor", currentResult);
         currentResult.setValue("description", "Gets the cdiDescriptor ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("EjbReferenceDescriptions")) {
         getterName = "getEjbReferenceDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbReferenceDescriptions", WeblogicApplicationClientBean.class, getterName, setterName);
         descriptors.put("EjbReferenceDescriptions", currentResult);
         currentResult.setValue("description", "Gets array of all \"ejb-reference-description\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEjbReferenceDescription");
         currentResult.setValue("destroyer", "destroyEjbReferenceDescription");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", WeblogicApplicationClientBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "Gets the \"id\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageDestinationDescriptors")) {
         getterName = "getMessageDestinationDescriptors";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDestinationDescriptors", WeblogicApplicationClientBean.class, getterName, setterName);
         descriptors.put("MessageDestinationDescriptors", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMessageDestinationDescriptor");
         currentResult.setValue("destroyer", "destroyMessageDestinationDescriptor");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceDescriptions")) {
         getterName = "getResourceDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceDescriptions", WeblogicApplicationClientBean.class, getterName, setterName);
         descriptors.put("ResourceDescriptions", currentResult);
         currentResult.setValue("description", "Gets array of all \"resource-description\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResourceDescription");
         currentResult.setValue("destroyer", "destroyResourceDescription");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceEnvDescriptions")) {
         getterName = "getResourceEnvDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceEnvDescriptions", WeblogicApplicationClientBean.class, getterName, setterName);
         descriptors.put("ResourceEnvDescriptions", currentResult);
         currentResult.setValue("description", "Gets array of all \"resource-env-description\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyResourceEnvDescription");
         currentResult.setValue("creator", "createResourceEnvDescription");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerApplicationName")) {
         getterName = "getServerApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerApplicationName", WeblogicApplicationClientBean.class, getterName, setterName);
         descriptors.put("ServerApplicationName", currentResult);
         currentResult.setValue("description", "Gets the name of the application to which the client should connect to. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceReferenceDescriptions")) {
         getterName = "getServiceReferenceDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceReferenceDescriptions", WeblogicApplicationClientBean.class, getterName, setterName);
         descriptors.put("ServiceReferenceDescriptions", currentResult);
         currentResult.setValue("description", "Gets array of all \"service-reference-description\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createServiceReferenceDescription");
         currentResult.setValue("destroyer", "destroyServiceReferenceDescription");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", WeblogicApplicationClientBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "Gets the \"version\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicApplicationClientBean.class.getMethod("createResourceDescription");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceDescriptions");
      }

      mth = WeblogicApplicationClientBean.class.getMethod("destroyResourceDescription", ResourceDescriptionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceDescriptions");
      }

      mth = WeblogicApplicationClientBean.class.getMethod("createResourceEnvDescription");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceEnvDescriptions");
      }

      mth = WeblogicApplicationClientBean.class.getMethod("destroyResourceEnvDescription", ResourceEnvDescriptionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceEnvDescriptions");
      }

      mth = WeblogicApplicationClientBean.class.getMethod("createEjbReferenceDescription");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbReferenceDescriptions");
      }

      mth = WeblogicApplicationClientBean.class.getMethod("destroyEjbReferenceDescription", EjbReferenceDescriptionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbReferenceDescriptions");
      }

      mth = WeblogicApplicationClientBean.class.getMethod("createServiceReferenceDescription");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceReferenceDescriptions");
      }

      mth = WeblogicApplicationClientBean.class.getMethod("destroyServiceReferenceDescription", ServiceReferenceDescriptionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceReferenceDescriptions");
      }

      mth = WeblogicApplicationClientBean.class.getMethod("createMessageDestinationDescriptor");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinationDescriptors");
      }

      mth = WeblogicApplicationClientBean.class.getMethod("destroyMessageDestinationDescriptor", MessageDestinationDescriptorBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinationDescriptors");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicApplicationClientBean.class.getMethod("lookupMessageDestinationDescriptor", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MessageDestinationDescriptors");
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
