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

public class WeblogicEnterpriseBeanBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WeblogicEnterpriseBeanBean.class;

   public WeblogicEnterpriseBeanBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicEnterpriseBeanBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CreateAsPrincipalName")) {
         getterName = "getCreateAsPrincipalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCreateAsPrincipalName";
         }

         currentResult = new PropertyDescriptor("CreateAsPrincipalName", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("CreateAsPrincipalName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DispatchPolicy")) {
         getterName = "getDispatchPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDispatchPolicy";
         }

         currentResult = new PropertyDescriptor("DispatchPolicy", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("DispatchPolicy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbName")) {
         getterName = "getEjbName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEjbName";
         }

         currentResult = new PropertyDescriptor("EjbName", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("EjbName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbReferenceDescriptions")) {
         getterName = "getEjbReferenceDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbReferenceDescriptions", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("EjbReferenceDescriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEjbReferenceDescription");
         currentResult.setValue("destroyer", "destroyEjbReferenceDescription");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityDescriptor")) {
         getterName = "getEntityDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("EntityDescriptor", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("EntityDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IiopSecurityDescriptor")) {
         getterName = "getIiopSecurityDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("IiopSecurityDescriptor", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("IiopSecurityDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIName";
         }

         currentResult = new PropertyDescriptor("JNDIName", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("declaration", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JndiBinding")) {
         getterName = "getJndiBinding";
         setterName = null;
         currentResult = new PropertyDescriptor("JndiBinding", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("JndiBinding", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJndiBinding");
         currentResult.setValue("creator", "createJndiBinding");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalJNDIName")) {
         getterName = "getLocalJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalJNDIName";
         }

         currentResult = new PropertyDescriptor("LocalJNDIName", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("LocalJNDIName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("declaration", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageDrivenDescriptor")) {
         getterName = "getMessageDrivenDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDrivenDescriptor", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("MessageDrivenDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NetworkAccessPoint")) {
         getterName = "getNetworkAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNetworkAccessPoint";
         }

         currentResult = new PropertyDescriptor("NetworkAccessPoint", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("NetworkAccessPoint", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PassivateAsPrincipalName")) {
         getterName = "getPassivateAsPrincipalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassivateAsPrincipalName";
         }

         currentResult = new PropertyDescriptor("PassivateAsPrincipalName", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("PassivateAsPrincipalName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteClientTimeout")) {
         getterName = "getRemoteClientTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteClientTimeout";
         }

         currentResult = new PropertyDescriptor("RemoteClientTimeout", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("RemoteClientTimeout", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoveAsPrincipalName")) {
         getterName = "getRemoveAsPrincipalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoveAsPrincipalName";
         }

         currentResult = new PropertyDescriptor("RemoveAsPrincipalName", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("RemoveAsPrincipalName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceDescriptions")) {
         getterName = "getResourceDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceDescriptions", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("ResourceDescriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResourceDescription");
         currentResult.setValue("destroyer", "destroyResourceDescription");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceEnvDescriptions")) {
         getterName = "getResourceEnvDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceEnvDescriptions", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("ResourceEnvDescriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyResourceEnvDescription");
         currentResult.setValue("creator", "createResourceEnvDescription");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunAsPrincipalName")) {
         getterName = "getRunAsPrincipalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRunAsPrincipalName";
         }

         currentResult = new PropertyDescriptor("RunAsPrincipalName", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("RunAsPrincipalName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceReferenceDescriptions")) {
         getterName = "getServiceReferenceDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceReferenceDescriptions", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("ServiceReferenceDescriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createServiceReferenceDescription");
         currentResult.setValue("destroyer", "destroyServiceReferenceDescription");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingletonSessionDescriptor")) {
         getterName = "getSingletonSessionDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("SingletonSessionDescriptor", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("SingletonSessionDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StatefulSessionDescriptor")) {
         getterName = "getStatefulSessionDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("StatefulSessionDescriptor", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("StatefulSessionDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StatelessSessionDescriptor")) {
         getterName = "getStatelessSessionDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("StatelessSessionDescriptor", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("StatelessSessionDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionDescriptor")) {
         getterName = "getTransactionDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionDescriptor", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("TransactionDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientsOnSameServer")) {
         getterName = "isClientsOnSameServer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientsOnSameServer";
         }

         currentResult = new PropertyDescriptor("ClientsOnSameServer", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("ClientsOnSameServer", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableCallByReference")) {
         getterName = "isEnableCallByReference";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableCallByReference";
         }

         currentResult = new PropertyDescriptor("EnableCallByReference", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("EnableCallByReference", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StickToFirstServer")) {
         getterName = "isStickToFirstServer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStickToFirstServer";
         }

         currentResult = new PropertyDescriptor("StickToFirstServer", WeblogicEnterpriseBeanBean.class, getterName, setterName);
         descriptors.put("StickToFirstServer", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicEnterpriseBeanBean.class.getMethod("createResourceDescription");
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

      mth = WeblogicEnterpriseBeanBean.class.getMethod("destroyResourceDescription", ResourceDescriptionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceDescriptions");
      }

      mth = WeblogicEnterpriseBeanBean.class.getMethod("createResourceEnvDescription");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceEnvDescriptions");
      }

      mth = WeblogicEnterpriseBeanBean.class.getMethod("destroyResourceEnvDescription", ResourceEnvDescriptionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceEnvDescriptions");
      }

      mth = WeblogicEnterpriseBeanBean.class.getMethod("createEjbReferenceDescription");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbReferenceDescriptions");
      }

      mth = WeblogicEnterpriseBeanBean.class.getMethod("destroyEjbReferenceDescription", EjbReferenceDescriptionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbReferenceDescriptions");
      }

      mth = WeblogicEnterpriseBeanBean.class.getMethod("createServiceReferenceDescription");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceReferenceDescriptions");
      }

      mth = WeblogicEnterpriseBeanBean.class.getMethod("destroyServiceReferenceDescription", ServiceReferenceDescriptionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceReferenceDescriptions");
      }

      mth = WeblogicEnterpriseBeanBean.class.getMethod("createJndiBinding");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JndiBinding");
      }

      mth = WeblogicEnterpriseBeanBean.class.getMethod("destroyJndiBinding", JndiBindingBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JndiBinding");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicEnterpriseBeanBean.class.getMethod("lookupJndiBinding", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JndiBinding");
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
