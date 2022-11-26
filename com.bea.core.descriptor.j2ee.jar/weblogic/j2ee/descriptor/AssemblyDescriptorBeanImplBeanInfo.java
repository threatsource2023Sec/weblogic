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

public class AssemblyDescriptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = AssemblyDescriptorBean.class;

   public AssemblyDescriptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AssemblyDescriptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.AssemblyDescriptorBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.AssemblyDescriptorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ApplicationExceptions")) {
         getterName = "getApplicationExceptions";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationExceptions", AssemblyDescriptorBean.class, getterName, setterName);
         descriptors.put("ApplicationExceptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyApplicationException");
         currentResult.setValue("creator", "createApplicationException");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContainerTransactions")) {
         getterName = "getContainerTransactions";
         setterName = null;
         currentResult = new PropertyDescriptor("ContainerTransactions", AssemblyDescriptorBean.class, getterName, setterName);
         descriptors.put("ContainerTransactions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createContainerTransaction");
         currentResult.setValue("destroyer", "destroyContainerTransaction");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExcludeList")) {
         getterName = "getExcludeList";
         setterName = null;
         currentResult = new PropertyDescriptor("ExcludeList", AssemblyDescriptorBean.class, getterName, setterName);
         descriptors.put("ExcludeList", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createExcludeList");
         currentResult.setValue("destroyer", "destroyExcludeList");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", AssemblyDescriptorBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterceptorBindings")) {
         getterName = "getInterceptorBindings";
         setterName = null;
         currentResult = new PropertyDescriptor("InterceptorBindings", AssemblyDescriptorBean.class, getterName, setterName);
         descriptors.put("InterceptorBindings", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createInterceptorBinding");
         currentResult.setValue("destroyer", "destroyInterceptorBinding");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageDestinations")) {
         getterName = "getMessageDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDestinations", AssemblyDescriptorBean.class, getterName, setterName);
         descriptors.put("MessageDestinations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMessageDestination");
         currentResult.setValue("creator", "createMessageDestination");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MethodPermissions")) {
         getterName = "getMethodPermissions";
         setterName = null;
         currentResult = new PropertyDescriptor("MethodPermissions", AssemblyDescriptorBean.class, getterName, setterName);
         descriptors.put("MethodPermissions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMethodPermission");
         currentResult.setValue("destroyer", "destroyMethodPermission");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityRoles")) {
         getterName = "getSecurityRoles";
         setterName = null;
         currentResult = new PropertyDescriptor("SecurityRoles", AssemblyDescriptorBean.class, getterName, setterName);
         descriptors.put("SecurityRoles", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSecurityRole");
         currentResult.setValue("destroyer", "destroySecurityRole");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = AssemblyDescriptorBean.class.getMethod("createSecurityRole");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SecurityRoles");
      }

      mth = AssemblyDescriptorBean.class.getMethod("destroySecurityRole", SecurityRoleBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SecurityRoles");
      }

      mth = AssemblyDescriptorBean.class.getMethod("createMethodPermission");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MethodPermissions");
      }

      mth = AssemblyDescriptorBean.class.getMethod("destroyMethodPermission", MethodPermissionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MethodPermissions");
      }

      mth = AssemblyDescriptorBean.class.getMethod("createContainerTransaction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ContainerTransactions");
      }

      mth = AssemblyDescriptorBean.class.getMethod("destroyContainerTransaction", ContainerTransactionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ContainerTransactions");
      }

      mth = AssemblyDescriptorBean.class.getMethod("createInterceptorBinding");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InterceptorBindings");
      }

      mth = AssemblyDescriptorBean.class.getMethod("destroyInterceptorBinding", InterceptorBindingBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InterceptorBindings");
      }

      mth = AssemblyDescriptorBean.class.getMethod("createMessageDestination");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinations");
      }

      mth = AssemblyDescriptorBean.class.getMethod("destroyMessageDestination", MessageDestinationBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinations");
      }

      mth = AssemblyDescriptorBean.class.getMethod("createExcludeList");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExcludeList");
      }

      mth = AssemblyDescriptorBean.class.getMethod("destroyExcludeList", ExcludeListBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExcludeList");
      }

      mth = AssemblyDescriptorBean.class.getMethod("createApplicationException");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ApplicationExceptions");
      }

      mth = AssemblyDescriptorBean.class.getMethod("destroyApplicationException", ApplicationExceptionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ApplicationExceptions");
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
