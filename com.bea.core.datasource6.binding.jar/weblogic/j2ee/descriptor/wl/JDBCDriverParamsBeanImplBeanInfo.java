package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCDriverParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCDriverParamsBean.class;

   public JDBCDriverParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCDriverParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Contains the driver parameters of a data source.</p>  <p>Configuration parameters for the JDBC Driver used by a data source are specified using a driver parameters bean.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DriverName")) {
         getterName = "getDriverName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDriverName";
         }

         currentResult = new PropertyDescriptor("DriverName", JDBCDriverParamsBean.class, getterName, setterName);
         descriptors.put("DriverName", currentResult);
         currentResult.setValue("description", "<p>The full package name of JDBC driver class used to create the physical database connections in the connection pool in the data source.</p>  <p>For example: <code>oracle.jdbc.OracleDriver</code></p>  <p>The driver must be the name of a class that implements the <code>java.sql.Driver</code> interface. Check the driver documentation to find the full pathname.</p>  <p>Note that the driver class must be in the classpath of any server to which the data source is deployed.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", JDBCDriverParamsBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "<p>The password attribute passed to the JDBC driver when creating physical database connections.</p>  <p>The value is stored in an encrypted form in the descriptor file and when displayed in an administration console.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", seeObjectArray);
         currentResult.setValue("owner", "");
         String[] roleObjectArraySet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedSet", roleObjectArraySet);
      }

      if (!descriptors.containsKey("PasswordEncrypted")) {
         getterName = "getPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("PasswordEncrypted", JDBCDriverParamsBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted database password as set with <code>setPassword()</code>, <code>setPasswordEncrypted(byte[] bytes)</code>, or as a key=value pair in the list of JDBC driver properties.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", JDBCDriverParamsBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "<p>The list of properties passed to the JDBC driver when creating physical database connections.</p>  <p>To enable driver-level features, add the driver property and its value to the Properties list. WebLogic Server sets driver-level properties in the Properties list on the driver's <code>ConnectionPoolDataSource</code> object.</p>  <p><b>Note:</b> For Security reasons, when WebLogic Server is running in Production mode, you cannot specify database passwords in this properties list. Data source deployment will fail if a password is specified in the properties list. To override this security check, use the command line argument <code>weblogic.management.allowClearTextPasswords</code> when starting the server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Url")) {
         getterName = "getUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUrl";
         }

         currentResult = new PropertyDescriptor("Url", JDBCDriverParamsBean.class, getterName, setterName);
         descriptors.put("Url", currentResult);
         currentResult.setValue("description", "<p>The URL of the database to connect to. The format of the URL varies by JDBC driver.</p>  <p>The URL is passed to the JDBC driver to create the physical database connections.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UsePasswordIndirection")) {
         getterName = "isUsePasswordIndirection";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUsePasswordIndirection";
         }

         currentResult = new PropertyDescriptor("UsePasswordIndirection", JDBCDriverParamsBean.class, getterName, setterName);
         descriptors.put("UsePasswordIndirection", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the database credentials are to be obtained from the credential mapper using the \"user\" property as the key. When true, the credentials are obtained from the credential mapper. When false, the database user name and password are obtained from the \"user\" property and Password element, respectively.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPassword()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseXaDataSourceInterface")) {
         getterName = "isUseXaDataSourceInterface";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseXaDataSourceInterface";
         }

         currentResult = new PropertyDescriptor("UseXaDataSourceInterface", JDBCDriverParamsBean.class, getterName, setterName);
         descriptors.put("UseXaDataSourceInterface", currentResult);
         currentResult.setValue("description", "<p>Specifies that WebLogic Server should use the XA interface of the JDBC driver.</p>  <p>If the JDBC driver class used to create database connections implements both XA and non-XA versions of a JDBC driver, you can set this attribute to indicate that WebLogic Server should treat the JDBC driver as an XA driver or as a non-XA driver.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
