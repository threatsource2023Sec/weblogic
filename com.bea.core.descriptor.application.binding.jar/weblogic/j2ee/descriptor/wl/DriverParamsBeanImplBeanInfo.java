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

public class DriverParamsBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = DriverParamsBean.class;

   public DriverParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DriverParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DriverParamsBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DriverParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("PreparedStatement")) {
         getterName = "getPreparedStatement";
         setterName = null;
         currentResult = new PropertyDescriptor("PreparedStatement", DriverParamsBean.class, getterName, setterName);
         descriptors.put("PreparedStatement", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPreparedStatement");
         currentResult.setValue("creator", "createPreparedStatement");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RowPrefetchSize")) {
         getterName = "getRowPrefetchSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRowPrefetchSize";
         }

         currentResult = new PropertyDescriptor("RowPrefetchSize", DriverParamsBean.class, getterName, setterName);
         descriptors.put("RowPrefetchSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(48));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Statement")) {
         getterName = "getStatement";
         setterName = null;
         currentResult = new PropertyDescriptor("Statement", DriverParamsBean.class, getterName, setterName);
         descriptors.put("Statement", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createStatement");
         currentResult.setValue("destroyer", "destroyStatement");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StreamChunkSize")) {
         getterName = "getStreamChunkSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStreamChunkSize";
         }

         currentResult = new PropertyDescriptor("StreamChunkSize", DriverParamsBean.class, getterName, setterName);
         descriptors.put("StreamChunkSize", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RowPrefetchEnabled")) {
         getterName = "isRowPrefetchEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRowPrefetchEnabled";
         }

         currentResult = new PropertyDescriptor("RowPrefetchEnabled", DriverParamsBean.class, getterName, setterName);
         descriptors.put("RowPrefetchEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DriverParamsBean.class.getMethod("createStatement");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Statement");
      }

      mth = DriverParamsBean.class.getMethod("destroyStatement", StatementBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Statement");
      }

      mth = DriverParamsBean.class.getMethod("createPreparedStatement");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreparedStatement");
      }

      mth = DriverParamsBean.class.getMethod("destroyPreparedStatement", PreparedStatementBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreparedStatement");
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
