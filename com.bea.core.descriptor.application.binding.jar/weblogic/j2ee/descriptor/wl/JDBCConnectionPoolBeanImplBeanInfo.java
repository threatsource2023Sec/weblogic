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

public class JDBCConnectionPoolBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = JDBCConnectionPoolBean.class;

   public JDBCConnectionPoolBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCConnectionPoolBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.configuration.AppDeploymentMBean} ");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Defines a JDBC connection pool from a domain configuration that was migrated from a previous release. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JDBCConnectionPoolBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AclName")) {
         getterName = "getAclName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAclName";
         }

         currentResult = new PropertyDescriptor("AclName", JDBCConnectionPoolBean.class, getterName, setterName);
         descriptors.put("AclName", currentResult);
         currentResult.setValue("description", "<p>The access control list (ACL) used to control access to this connection pool.</p>  <p>Permissions available to this ACL are:</p>  <ul> <li><code>Reserve</code>  <p>Allows users to get logical connections from this connection pool.</p> </li>  <li><code>Admin</code>  <p>Allows all other operations on this connection pool, including: reset, shrink, shutdown, disable, and enable.</p> </li> </ul>  <p>Lack of an ACL allows any user open access (provided that the user passes other WLS security controls).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactory")) {
         getterName = "getConnectionFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactory", JDBCConnectionPoolBean.class, getterName, setterName);
         descriptors.put("ConnectionFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyConnectionFactory");
         currentResult.setValue("creator", "createConnectionFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataSourceJNDIName")) {
         getterName = "getDataSourceJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceJNDIName";
         }

         currentResult = new PropertyDescriptor("DataSourceJNDIName", JDBCConnectionPoolBean.class, getterName, setterName);
         descriptors.put("DataSourceJNDIName", currentResult);
         currentResult.setValue("description", "<p>The JNDI path to where the data source is bound.</p> ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DriverParams")) {
         getterName = "getDriverParams";
         setterName = null;
         currentResult = new PropertyDescriptor("DriverParams", JDBCConnectionPoolBean.class, getterName, setterName);
         descriptors.put("DriverParams", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDriverParams");
         currentResult.setValue("destroyer", "destroyDriverParams");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolParams")) {
         getterName = "getPoolParams";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolParams", JDBCConnectionPoolBean.class, getterName, setterName);
         descriptors.put("PoolParams", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPoolParams");
         currentResult.setValue("destroyer", "destroyPoolParams");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JDBCConnectionPoolBean.class.getMethod("createConnectionFactory");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionFactory");
      }

      mth = JDBCConnectionPoolBean.class.getMethod("destroyConnectionFactory", ConnectionFactoryBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionFactory");
      }

      mth = JDBCConnectionPoolBean.class.getMethod("createPoolParams");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PoolParams");
      }

      mth = JDBCConnectionPoolBean.class.getMethod("destroyPoolParams", ApplicationPoolParamsBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PoolParams");
      }

      mth = JDBCConnectionPoolBean.class.getMethod("createDriverParams");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DriverParams");
      }

      mth = JDBCConnectionPoolBean.class.getMethod("destroyDriverParams", DriverParamsBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DriverParams");
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
