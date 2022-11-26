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

public class ResourceAdapterSecurityBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ResourceAdapterSecurityBean.class;

   public ResourceAdapterSecurityBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ResourceAdapterSecurityBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ResourceAdapterSecurityBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML resource-adapter-securityType. This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ResourceAdapterSecurityBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DefaultPrincipalName")) {
         getterName = "getDefaultPrincipalName";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultPrincipalName", ResourceAdapterSecurityBean.class, getterName, setterName);
         descriptors.put("DefaultPrincipalName", currentResult);
         currentResult.setValue("description", "Gets the \"default-principal-name\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDefaultPrincipalName");
         currentResult.setValue("destroyer", "destroyDefaultPrincipalName");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", ResourceAdapterSecurityBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "Gets the \"id\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManageAsPrincipalName")) {
         getterName = "getManageAsPrincipalName";
         setterName = null;
         currentResult = new PropertyDescriptor("ManageAsPrincipalName", ResourceAdapterSecurityBean.class, getterName, setterName);
         descriptors.put("ManageAsPrincipalName", currentResult);
         currentResult.setValue("description", "Gets the \"manage-as-principal-name\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManageAsPrincipalName");
         currentResult.setValue("destroyer", "destroyManageAsPrincipalName");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunAsPrincipalName")) {
         getterName = "getRunAsPrincipalName";
         setterName = null;
         currentResult = new PropertyDescriptor("RunAsPrincipalName", ResourceAdapterSecurityBean.class, getterName, setterName);
         descriptors.put("RunAsPrincipalName", currentResult);
         currentResult.setValue("description", "Gets the \"run-as-principal-name\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createRunAsPrincipalName");
         currentResult.setValue("destroyer", "destroyRunAsPrincipalName");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunWorkAsPrincipalName")) {
         getterName = "getRunWorkAsPrincipalName";
         setterName = null;
         currentResult = new PropertyDescriptor("RunWorkAsPrincipalName", ResourceAdapterSecurityBean.class, getterName, setterName);
         descriptors.put("RunWorkAsPrincipalName", currentResult);
         currentResult.setValue("description", "Gets the \"run-work-as-principal-name\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createRunWorkAsPrincipalName");
         currentResult.setValue("destroyer", "destroyRunWorkAsPrincipalName");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("Connector 1.6/Cordell", (String)null, this.targetVersion) && !descriptors.containsKey("SecurityWorkContext")) {
         getterName = "getSecurityWorkContext";
         setterName = null;
         currentResult = new PropertyDescriptor("SecurityWorkContext", ResourceAdapterSecurityBean.class, getterName, setterName);
         descriptors.put("SecurityWorkContext", currentResult);
         currentResult.setValue("description", "Gets the \"security-work-context\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "Connector 1.6/Cordell");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceAdapterSecurityBean.class.getMethod("createDefaultPrincipalName");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultPrincipalName");
      }

      mth = ResourceAdapterSecurityBean.class.getMethod("destroyDefaultPrincipalName", AnonPrincipalBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultPrincipalName");
      }

      mth = ResourceAdapterSecurityBean.class.getMethod("createManageAsPrincipalName");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManageAsPrincipalName");
      }

      mth = ResourceAdapterSecurityBean.class.getMethod("destroyManageAsPrincipalName", AnonPrincipalBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ManageAsPrincipalName");
      }

      mth = ResourceAdapterSecurityBean.class.getMethod("createRunAsPrincipalName");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RunAsPrincipalName");
      }

      mth = ResourceAdapterSecurityBean.class.getMethod("destroyRunAsPrincipalName", AnonPrincipalCallerBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RunAsPrincipalName");
      }

      mth = ResourceAdapterSecurityBean.class.getMethod("createRunWorkAsPrincipalName");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RunWorkAsPrincipalName");
      }

      mth = ResourceAdapterSecurityBean.class.getMethod("destroyRunWorkAsPrincipalName", AnonPrincipalCallerBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RunWorkAsPrincipalName");
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
