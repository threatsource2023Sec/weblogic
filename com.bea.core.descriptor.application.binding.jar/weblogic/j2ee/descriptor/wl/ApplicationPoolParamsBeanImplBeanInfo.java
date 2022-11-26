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

public class ApplicationPoolParamsBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ApplicationPoolParamsBean.class;

   public ApplicationPoolParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ApplicationPoolParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionCheckParams")) {
         getterName = "getConnectionCheckParams";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionCheckParams", ApplicationPoolParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionCheckParams", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createConnectionCheckParams");
         currentResult.setValue("destroyer", "destroyConnectionCheckParams");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCXADebugLevel")) {
         getterName = "getJDBCXADebugLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJDBCXADebugLevel";
         }

         currentResult = new PropertyDescriptor("JDBCXADebugLevel", ApplicationPoolParamsBean.class, getterName, setterName);
         descriptors.put("JDBCXADebugLevel", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginDelaySeconds")) {
         getterName = "getLoginDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginDelaySeconds";
         }

         currentResult = new PropertyDescriptor("LoginDelaySeconds", ApplicationPoolParamsBean.class, getterName, setterName);
         descriptors.put("LoginDelaySeconds", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SizeParams")) {
         getterName = "getSizeParams";
         setterName = null;
         currentResult = new PropertyDescriptor("SizeParams", ApplicationPoolParamsBean.class, getterName, setterName);
         descriptors.put("SizeParams", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSizeParams");
         currentResult.setValue("destroyer", "destroySizeParams");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XAParams")) {
         getterName = "getXAParams";
         setterName = null;
         currentResult = new PropertyDescriptor("XAParams", ApplicationPoolParamsBean.class, getterName, setterName);
         descriptors.put("XAParams", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createXAParams");
         currentResult.setValue("destroyer", "destroyXAParams");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LeakProfilingEnabled")) {
         getterName = "isLeakProfilingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLeakProfilingEnabled";
         }

         currentResult = new PropertyDescriptor("LeakProfilingEnabled", ApplicationPoolParamsBean.class, getterName, setterName);
         descriptors.put("LeakProfilingEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoveInfectedConnectionsEnabled")) {
         getterName = "isRemoveInfectedConnectionsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoveInfectedConnectionsEnabled";
         }

         currentResult = new PropertyDescriptor("RemoveInfectedConnectionsEnabled", ApplicationPoolParamsBean.class, getterName, setterName);
         descriptors.put("RemoveInfectedConnectionsEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ApplicationPoolParamsBean.class.getMethod("createSizeParams");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SizeParams");
      }

      mth = ApplicationPoolParamsBean.class.getMethod("destroySizeParams", SizeParamsBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SizeParams");
      }

      mth = ApplicationPoolParamsBean.class.getMethod("createXAParams");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XAParams");
      }

      mth = ApplicationPoolParamsBean.class.getMethod("destroyXAParams", XAParamsBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XAParams");
      }

      mth = ApplicationPoolParamsBean.class.getMethod("createConnectionCheckParams");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionCheckParams");
      }

      mth = ApplicationPoolParamsBean.class.getMethod("destroyConnectionCheckParams", ConnectionCheckParamsBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionCheckParams");
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
