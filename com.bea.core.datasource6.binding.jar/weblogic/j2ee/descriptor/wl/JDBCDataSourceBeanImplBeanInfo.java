package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCDataSourceBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCDataSourceBean.class;

   public JDBCDataSourceBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCDataSourceBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("The top of the JDBC data source bean tree. <p> JDBC data sources all have a JDBCDataSourceBean as their root bean (a bean with no parent).  The schema namespace that corresponds to this bean is \"http://xmlns.oracle.com/weblogic/jdbc-data-source\" </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JDBCDataSourceBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DatasourceType")) {
         getterName = "getDatasourceType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDatasourceType";
         }

         currentResult = new PropertyDescriptor("DatasourceType", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("DatasourceType", currentResult);
         currentResult.setValue("description", "The data source type. Valid types are: <ul> <li>GENERIC - generic data source</li> <li>MDS - multi data source</li> <li>AGL - Active GridLink data source</li> <li>UCP - Universal Connection Pool data source</li> <li>PROXY - proxy for multiple tenant data sources</li> </ul> ");
         currentResult.setValue("legalValues", new Object[]{"GENERIC", "MDS", "AGL", "UCP", "PROXY"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         currentResult = new PropertyDescriptor("Id", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "Gets the Id value ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InternalProperties")) {
         getterName = "getInternalProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("InternalProperties", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("InternalProperties", currentResult);
         currentResult.setValue("description", "Internal Use only, applications must not attempt to configure or use this. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCConnectionPoolParams")) {
         getterName = "getJDBCConnectionPoolParams";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCConnectionPoolParams", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("JDBCConnectionPoolParams", currentResult);
         currentResult.setValue("description", "Gets the connection pool parameters of this data source  <p> Configuration parameters for this data source's connection pool are specified using the connection pool parameters bean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCDataSourceParams")) {
         getterName = "getJDBCDataSourceParams";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCDataSourceParams", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("JDBCDataSourceParams", currentResult);
         currentResult.setValue("description", "Gets the basic usage parameters of this data source  <p> Configuration parameters for the basic usage of this data source are specified using the data source parameters bean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCDriverParams")) {
         getterName = "getJDBCDriverParams";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCDriverParams", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("JDBCDriverParams", currentResult);
         currentResult.setValue("description", "Gets the driver parameters of this data source  <p> Configuration parameters for the JDBC Driver used by this data source are specified using the driver parameters bean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCOracleParams")) {
         getterName = "getJDBCOracleParams";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCOracleParams", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("JDBCOracleParams", currentResult);
         currentResult.setValue("description", "Gets the Oracle-related parameters of this data source  <p> Configuration parameters for this data source's Oracle-related behavior are specified using the Oracle parameters bean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCXAParams")) {
         getterName = "getJDBCXAParams";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCXAParams", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("JDBCXAParams", currentResult);
         currentResult.setValue("description", "Gets the XA-related parameters of this data source  <p> Configuration parameters for this data source's XA-related behavior are specified using the XA parameters bean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "A unique name that identifies this data source in the WebLogic domain. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("restReadOnly", Boolean.FALSE);
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", JDBCDataSourceBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "Gets the \"version\" attribute ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
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
