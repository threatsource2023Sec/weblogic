package weblogic.j2ee.descriptor.wl60;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WeblogicRdbmsBeanBeanImplBeanInfo extends BaseWeblogicRdbmsBeanBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WeblogicRdbmsBeanBean.class;

   public WeblogicRdbmsBeanBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicRdbmsBeanBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl60");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DataSourceJndiName")) {
         getterName = "getDataSourceJndiName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceJndiName";
         }

         currentResult = new PropertyDescriptor("DataSourceJndiName", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("DataSourceJndiName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dependency", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbName")) {
         getterName = "getEjbName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEjbName";
         }

         currentResult = new PropertyDescriptor("EjbName", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("EjbName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FieldMaps")) {
         getterName = "getFieldMaps";
         setterName = null;
         currentResult = new PropertyDescriptor("FieldMaps", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("FieldMaps", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createFieldMap");
         currentResult.setValue("destroyer", "destroyFieldMap");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Finders")) {
         getterName = "getFinders";
         setterName = null;
         currentResult = new PropertyDescriptor("Finders", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("Finders", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyFinder");
         currentResult.setValue("creator", "createFinder");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolName")) {
         getterName = "getPoolName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPoolName";
         }

         currentResult = new PropertyDescriptor("PoolName", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("PoolName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dependency", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableName")) {
         getterName = "getTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableName";
         }

         currentResult = new PropertyDescriptor("TableName", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("TableName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dependency", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableTunedUpdates")) {
         getterName = "isEnableTunedUpdates";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableTunedUpdates";
         }

         currentResult = new PropertyDescriptor("EnableTunedUpdates", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("EnableTunedUpdates", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicRdbmsBeanBean.class.getMethod("createFieldMap");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FieldMaps");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("destroyFieldMap", FieldMapBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FieldMaps");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("createFinder");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Finders");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("destroyFinder", FinderBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Finders");
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
