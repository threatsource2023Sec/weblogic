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
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Compatibility")) {
         getterName = "getCompatibility";
         setterName = null;
         currentResult = new PropertyDescriptor("Compatibility", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("Compatibility", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCompatibility");
         currentResult.setValue("destroyer", "destroyCompatibility");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CreateDefaultDbmsTables")) {
         getterName = "getCreateDefaultDbmsTables";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCreateDefaultDbmsTables";
         }

         currentResult = new PropertyDescriptor("CreateDefaultDbmsTables", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("CreateDefaultDbmsTables", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Disabled");
         currentResult.setValue("legalValues", new Object[]{"CreateOnly", "Disabled", "DropAndCreate", "DropAndCreateAlways", "AlterOrCreate"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DatabaseType")) {
         getterName = "getDatabaseType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDatabaseType";
         }

         currentResult = new PropertyDescriptor("DatabaseType", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("DatabaseType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalValues", new Object[]{"DB2", "Informix", "Oracle", "SQLServer", "SQLServer2000", "Sybase", "PointBase", "MySQL", "Derby", "TimesTen"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultDbmsTablesDdl")) {
         getterName = "getDefaultDbmsTablesDdl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultDbmsTablesDdl";
         }

         currentResult = new PropertyDescriptor("DefaultDbmsTablesDdl", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("DefaultDbmsTablesDdl", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "Gets the \"version\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WeblogicRdbmsRelations")) {
         getterName = "getWeblogicRdbmsRelations";
         setterName = null;
         currentResult = new PropertyDescriptor("WeblogicRdbmsRelations", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("WeblogicRdbmsRelations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWeblogicRdbmsRelation");
         currentResult.setValue("creator", "createWeblogicRdbmsRelation");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableBatchOperations")) {
         getterName = "isEnableBatchOperations";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableBatchOperations";
         }

         currentResult = new PropertyDescriptor("EnableBatchOperations", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("EnableBatchOperations", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OrderDatabaseOperations")) {
         getterName = "isOrderDatabaseOperations";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrderDatabaseOperations";
         }

         currentResult = new PropertyDescriptor("OrderDatabaseOperations", WeblogicRdbmsJarBean.class, getterName, setterName);
         descriptors.put("OrderDatabaseOperations", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
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
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicRdbmsBeans");
      }

      mth = WeblogicRdbmsJarBean.class.getMethod("destroyWeblogicRdbmsBean", WeblogicRdbmsBeanBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicRdbmsBeans");
      }

      mth = WeblogicRdbmsJarBean.class.getMethod("createWeblogicRdbmsRelation");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicRdbmsRelations");
      }

      mth = WeblogicRdbmsJarBean.class.getMethod("destroyWeblogicRdbmsRelation", WeblogicRdbmsRelationBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicRdbmsRelations");
      }

      mth = WeblogicRdbmsJarBean.class.getMethod("createCompatibility");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Compatibility");
      }

      mth = WeblogicRdbmsJarBean.class.getMethod("destroyCompatibility", CompatibilityBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Compatibility");
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
