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

public class WeblogicQueryBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WeblogicQueryBean.class;

   public WeblogicQueryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicQueryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML weblogic-queryType(@http://www.bea.com/ns/weblogic/90). <p/> This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WeblogicQueryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "Gets the \"description\" element ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbQlQuery")) {
         getterName = "getEjbQlQuery";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbQlQuery", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("EjbQlQuery", currentResult);
         currentResult.setValue("description", "Gets the \"ejb-ql-query\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEjbQlQuery");
         currentResult.setValue("destroyer", "destroyEjbQlQuery");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableEagerRefresh")) {
         getterName = "getEnableEagerRefresh";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableEagerRefresh";
         }

         currentResult = new PropertyDescriptor("EnableEagerRefresh", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("EnableEagerRefresh", currentResult);
         currentResult.setValue("description", "Gets the \"enable-eager-refresh\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableQueryCaching")) {
         getterName = "getEnableQueryCaching";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableQueryCaching";
         }

         currentResult = new PropertyDescriptor("EnableQueryCaching", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("EnableQueryCaching", currentResult);
         currentResult.setValue("description", "Gets the \"enable-query-caching\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "Gets the \"id\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxElements")) {
         getterName = "getMaxElements";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxElements";
         }

         currentResult = new PropertyDescriptor("MaxElements", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("MaxElements", currentResult);
         currentResult.setValue("description", "Gets the \"max-elements\" element ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QueryMethod")) {
         getterName = "getQueryMethod";
         setterName = null;
         currentResult = new PropertyDescriptor("QueryMethod", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("QueryMethod", currentResult);
         currentResult.setValue("description", "Gets the \"query-method\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyQueryMethod");
         currentResult.setValue("creator", "createQueryMethod");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SqlQuery")) {
         getterName = "getSqlQuery";
         setterName = null;
         currentResult = new PropertyDescriptor("SqlQuery", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("SqlQuery", currentResult);
         currentResult.setValue("description", "Gets the \"sql-query\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySqlQuery");
         currentResult.setValue("creator", "createSqlQuery");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IncludeResultCacheHint")) {
         getterName = "isIncludeResultCacheHint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncludeResultCacheHint";
         }

         currentResult = new PropertyDescriptor("IncludeResultCacheHint", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("IncludeResultCacheHint", currentResult);
         currentResult.setValue("description", "Gets the \"include-result-cache-hint\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IncludeUpdates")) {
         getterName = "isIncludeUpdates";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncludeUpdates";
         }

         currentResult = new PropertyDescriptor("IncludeUpdates", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("IncludeUpdates", currentResult);
         currentResult.setValue("description", "Gets the \"include-updates\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SqlSelectDistinct")) {
         getterName = "isSqlSelectDistinct";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSqlSelectDistinct";
         }

         currentResult = new PropertyDescriptor("SqlSelectDistinct", WeblogicQueryBean.class, getterName, setterName);
         descriptors.put("SqlSelectDistinct", currentResult);
         currentResult.setValue("description", "Gets the \"sql-select-distinct\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicQueryBean.class.getMethod("createQueryMethod");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "QueryMethod");
      }

      mth = WeblogicQueryBean.class.getMethod("destroyQueryMethod", QueryMethodBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "QueryMethod");
      }

      mth = WeblogicQueryBean.class.getMethod("createEjbQlQuery");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbQlQuery");
      }

      mth = WeblogicQueryBean.class.getMethod("destroyEjbQlQuery", EjbQlQueryBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbQlQuery");
      }

      mth = WeblogicQueryBean.class.getMethod("createSqlQuery");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SqlQuery");
      }

      mth = WeblogicQueryBean.class.getMethod("destroySqlQuery", SqlQueryBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SqlQuery");
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
