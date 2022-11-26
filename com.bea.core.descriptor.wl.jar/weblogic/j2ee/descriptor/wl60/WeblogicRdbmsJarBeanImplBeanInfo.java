package weblogic.j2ee.descriptor.wl60;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class WeblogicRdbmsJarBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WeblogicRdbmsJarBean.class;

   public WeblogicRdbmsJarBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicRdbmsJarBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl60");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DatabaseType")) {
         getterName = "getDatabaseType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDatabaseType";
         }

         currentResult = new PropertyDescriptor("DatabaseType", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("DatabaseType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalValues", new Object[]{"DB2", "ORACLE", "SYBASE", "INFORMIX", "POINTBASE", "SQL_SERVER", "SQLSERVER"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidateDbSchemaWith")) {
         getterName = "getValidateDbSchemaWith";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidateDbSchemaWith";
         }

         currentResult = new PropertyDescriptor("ValidateDbSchemaWith", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("ValidateDbSchemaWith", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TableQuery");
         currentResult.setValue("legalValues", new Object[]{"MetaData", "TableQuery"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WeblogicRdbmsBeans")) {
         getterName = "getWeblogicRdbmsBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("WeblogicRdbmsBeans", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("WeblogicRdbmsBeans", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWeblogicRdbmsBean");
         currentResult.setValue("creator", "createWeblogicRdbmsBean");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CreateDefaultDbmsTables")) {
         getterName = "isCreateDefaultDbmsTables";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCreateDefaultDbmsTables";
         }

         currentResult = new PropertyDescriptor("CreateDefaultDbmsTables", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("CreateDefaultDbmsTables", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicRdbmsJarBean.class.getMethod("createWeblogicRdbmsBean");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicRdbmsBeans");
      }

      mth = WeblogicRdbmsJarBean.class.getMethod("destroyWeblogicRdbmsBean", WeblogicRdbmsBeanBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicRdbmsBeans");
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
