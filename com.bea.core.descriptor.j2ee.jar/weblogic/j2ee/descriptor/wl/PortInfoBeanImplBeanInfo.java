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

public class PortInfoBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PortInfoBean.class;

   public PortInfoBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PortInfoBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.PortInfoBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.PortInfoBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CallProperties")) {
         getterName = "getCallProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("CallProperties", PortInfoBean.class, getterName, setterName);
         descriptors.put("CallProperties", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCallProperty");
         currentResult.setValue("destroyer", "destroyCallProperty");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Operations")) {
         getterName = "getOperations";
         setterName = null;
         currentResult = new PropertyDescriptor("Operations", PortInfoBean.class, getterName, setterName);
         descriptors.put("Operations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOperation");
         currentResult.setValue("creator", "createOperation");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OwsmPolicy")) {
         getterName = "getOwsmPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("OwsmPolicy", PortInfoBean.class, getterName, setterName);
         descriptors.put("OwsmPolicy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOwsmPolicy");
         currentResult.setValue("creator", "createOwsmPolicy");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortName")) {
         getterName = "getPortName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPortName";
         }

         currentResult = new PropertyDescriptor("PortName", PortInfoBean.class, getterName, setterName);
         descriptors.put("PortName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StubProperties")) {
         getterName = "getStubProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("StubProperties", PortInfoBean.class, getterName, setterName);
         descriptors.put("StubProperties", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createStubProperty");
         currentResult.setValue("destroyer", "destroyStubProperty");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WSATConfig")) {
         getterName = "getWSATConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("WSATConfig", PortInfoBean.class, getterName, setterName);
         descriptors.put("WSATConfig", currentResult);
         currentResult.setValue("description", "Gets the WS-AT configuration for this web service port component. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWSATConfig");
         currentResult.setValue("destroyer", "destroyWSATConfig");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PortInfoBean.class.getMethod("createStubProperty");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "StubProperties");
      }

      mth = PortInfoBean.class.getMethod("destroyStubProperty", PropertyNamevalueBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "StubProperties");
      }

      mth = PortInfoBean.class.getMethod("createCallProperty");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CallProperties");
      }

      mth = PortInfoBean.class.getMethod("destroyCallProperty", PropertyNamevalueBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CallProperties");
      }

      mth = PortInfoBean.class.getMethod("createWSATConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates the singleton WSATConfigBean instance on this port. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WSATConfig");
      }

      mth = PortInfoBean.class.getMethod("destroyWSATConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the singleton ConfigBean instance on this port. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WSATConfig");
      }

      mth = PortInfoBean.class.getMethod("createOperation");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "add an Operation instance on this port. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Operations");
      }

      mth = PortInfoBean.class.getMethod("destroyOperation", OperationInfoBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the Operation instance on this port. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Operations");
      }

      mth = PortInfoBean.class.getMethod("createOwsmPolicy");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OwsmPolicy");
      }

      mth = PortInfoBean.class.getMethod("destroyOwsmPolicy", OwsmPolicyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OwsmPolicy");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PortInfoBean.class.getMethod("lookupOperation", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Operations");
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
