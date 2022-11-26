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

public class PortPolicyBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PortPolicyBean.class;

   public PortPolicyBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PortPolicyBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.PortPolicyBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.PortPolicyBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("OwsmSecurityPolicy")) {
         getterName = "getOwsmSecurityPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("OwsmSecurityPolicy", PortPolicyBean.class, getterName, setterName);
         descriptors.put("OwsmSecurityPolicy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createOwsmSecurityPolicy");
         currentResult.setValue("destroyer", "destroyOwsmSecurityPolicy");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortName")) {
         getterName = "getPortName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPortName";
         }

         currentResult = new PropertyDescriptor("PortName", PortPolicyBean.class, getterName, setterName);
         descriptors.put("PortName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WsPolicy")) {
         getterName = "getWsPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("WsPolicy", PortPolicyBean.class, getterName, setterName);
         descriptors.put("WsPolicy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWsPolicy");
         currentResult.setValue("destroyer", "destroyWsPolicy");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PortPolicyBean.class.getMethod("createWsPolicy");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WsPolicy");
      }

      mth = PortPolicyBean.class.getMethod("createOwsmSecurityPolicy");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OwsmSecurityPolicy");
      }

      mth = PortPolicyBean.class.getMethod("destroyWsPolicy", WsPolicyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WsPolicy");
      }

      mth = PortPolicyBean.class.getMethod("destroyOwsmSecurityPolicy", OwsmSecurityPolicyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OwsmSecurityPolicy");
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
