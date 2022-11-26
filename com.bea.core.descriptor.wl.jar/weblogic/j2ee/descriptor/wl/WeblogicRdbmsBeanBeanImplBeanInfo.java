package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.j2ee.descriptor.wl60.BaseWeblogicRdbmsBeanBeanImplBeanInfo;
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
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML weblogic-rdbms-beanType(@http://www.bea.com/ns/weblogic/90).  This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AutomaticKeyGeneration")) {
         getterName = "getAutomaticKeyGeneration";
         setterName = null;
         currentResult = new PropertyDescriptor("AutomaticKeyGeneration", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("AutomaticKeyGeneration", currentResult);
         currentResult.setValue("description", "Gets the \"automatic-key-generation\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAutomaticKeyGeneration");
         currentResult.setValue("creator", "createAutomaticKeyGeneration");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CategoryCmpField")) {
         getterName = "getCategoryCmpField";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCategoryCmpField";
         }

         currentResult = new PropertyDescriptor("CategoryCmpField", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("CategoryCmpField", currentResult);
         currentResult.setValue("description", "Gets the \"category-cmp-field\" element ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataSourceJNDIName")) {
         getterName = "getDataSourceJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceJNDIName";
         }

         currentResult = new PropertyDescriptor("DataSourceJNDIName", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("DataSourceJNDIName", currentResult);
         currentResult.setValue("description", "Gets the \"data-source-jndi-name\" element ");
         currentResult.setValue("dependency", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DelayDatabaseInsertUntil")) {
         getterName = "getDelayDatabaseInsertUntil";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDelayDatabaseInsertUntil";
         }

         currentResult = new PropertyDescriptor("DelayDatabaseInsertUntil", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("DelayDatabaseInsertUntil", currentResult);
         currentResult.setValue("description", "Gets the \"delay-database-insert-until\" element ");
         setPropertyDescriptorDefault(currentResult, "ejbPostCreate");
         currentResult.setValue("legalValues", new Object[]{"ejbCreate", "ejbPostCreate"});
         currentResult.setValue("exclude", Boolean.TRUE);
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
         currentResult.setValue("description", "Gets the \"ejb-name\" element ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FieldGroups")) {
         getterName = "getFieldGroups";
         setterName = null;
         currentResult = new PropertyDescriptor("FieldGroups", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("FieldGroups", currentResult);
         currentResult.setValue("description", "Gets array of all \"field-group\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createFieldGroup");
         currentResult.setValue("destroyer", "destroyFieldGroup");
         currentResult.setValue("exclude", Boolean.TRUE);
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
         currentResult.setValue("description", "Gets the \"id\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InstanceLockOrder")) {
         getterName = "getInstanceLockOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInstanceLockOrder";
         }

         currentResult = new PropertyDescriptor("InstanceLockOrder", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("InstanceLockOrder", currentResult);
         currentResult.setValue("description", "Gets the \"instance-lock-order\" element ");
         setPropertyDescriptorDefault(currentResult, "AccessOrder");
         currentResult.setValue("legalValues", new Object[]{"AccessOrder", "ValueOrder"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockOrder")) {
         getterName = "getLockOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLockOrder";
         }

         currentResult = new PropertyDescriptor("LockOrder", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("LockOrder", currentResult);
         currentResult.setValue("description", "Gets the \"lock-order\" element ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RelationshipCachings")) {
         getterName = "getRelationshipCachings";
         setterName = null;
         currentResult = new PropertyDescriptor("RelationshipCachings", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("RelationshipCachings", currentResult);
         currentResult.setValue("description", "Gets array of all \"relationship-caching\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyRelationshipCaching");
         currentResult.setValue("creator", "createRelationshipCaching");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SqlShapes")) {
         getterName = "getSqlShapes";
         setterName = null;
         currentResult = new PropertyDescriptor("SqlShapes", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("SqlShapes", currentResult);
         currentResult.setValue("description", "Gets array of all \"sql-result\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySqlShape");
         currentResult.setValue("creator", "createSqlShape");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableMaps")) {
         getterName = "getTableMaps";
         setterName = null;
         currentResult = new PropertyDescriptor("TableMaps", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("TableMaps", currentResult);
         currentResult.setValue("description", "Gets array of all \"table-map\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createTableMap");
         currentResult.setValue("destroyer", "destroyTableMap");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnknownPrimaryKeyField")) {
         getterName = "getUnknownPrimaryKeyField";
         setterName = null;
         currentResult = new PropertyDescriptor("UnknownPrimaryKeyField", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("UnknownPrimaryKeyField", currentResult);
         currentResult.setValue("description", "Gets the \"unknown-primary-key-field\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createUnknownPrimaryKeyField");
         currentResult.setValue("destroyer", "destroyUnknownPrimaryKeyField");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WeblogicQueries")) {
         getterName = "getWeblogicQueries";
         setterName = null;
         currentResult = new PropertyDescriptor("WeblogicQueries", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("WeblogicQueries", currentResult);
         currentResult.setValue("description", "Gets array of all \"weblogic-query\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWeblogicQuery");
         currentResult.setValue("creator", "createWeblogicQuery");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CheckExistsOnMethod")) {
         getterName = "isCheckExistsOnMethod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCheckExistsOnMethod";
         }

         currentResult = new PropertyDescriptor("CheckExistsOnMethod", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("CheckExistsOnMethod", currentResult);
         currentResult.setValue("description", "Gets the \"check-exists-on-method\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterInvalidationDisabled")) {
         getterName = "isClusterInvalidationDisabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterInvalidationDisabled";
         }

         currentResult = new PropertyDescriptor("ClusterInvalidationDisabled", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("ClusterInvalidationDisabled", currentResult);
         currentResult.setValue("description", "Gets the \"cluster-invalidation-disabled\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseInnerJoin")) {
         getterName = "isUseInnerJoin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseInnerJoin";
         }

         currentResult = new PropertyDescriptor("UseInnerJoin", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("UseInnerJoin", currentResult);
         currentResult.setValue("description", "Gets the \"use-inner-join\" element ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSelectForUpdate")) {
         getterName = "isUseSelectForUpdate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSelectForUpdate";
         }

         currentResult = new PropertyDescriptor("UseSelectForUpdate", WeblogicRdbmsBeanBean.class, getterName, setterName);
         descriptors.put("UseSelectForUpdate", currentResult);
         currentResult.setValue("description", "Gets the \"use-select-for-update\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicRdbmsBeanBean.class.getMethod("createUnknownPrimaryKeyField");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UnknownPrimaryKeyField");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("destroyUnknownPrimaryKeyField", UnknownPrimaryKeyFieldBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UnknownPrimaryKeyField");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("createTableMap");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableMaps");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("destroyTableMap", TableMapBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableMaps");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("createFieldGroup");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FieldGroups");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("destroyFieldGroup", FieldGroupBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FieldGroups");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("createRelationshipCaching");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RelationshipCachings");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("destroyRelationshipCaching", RelationshipCachingBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RelationshipCachings");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("createSqlShape");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SqlShapes");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("destroySqlShape", SqlShapeBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SqlShapes");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("createWeblogicQuery");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicQueries");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("destroyWeblogicQuery", WeblogicQueryBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicQueries");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("createAutomaticKeyGeneration");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AutomaticKeyGeneration");
      }

      mth = WeblogicRdbmsBeanBean.class.getMethod("destroyAutomaticKeyGeneration", AutomaticKeyGenerationBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AutomaticKeyGeneration");
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
