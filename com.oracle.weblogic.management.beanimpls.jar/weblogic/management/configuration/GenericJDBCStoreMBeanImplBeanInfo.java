package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class GenericJDBCStoreMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = GenericJDBCStoreMBean.class;

   public GenericJDBCStoreMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public GenericJDBCStoreMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.GenericJDBCStoreMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean defines the parameters for the JDBC store. It is the parent of the JDBCStoreMBean and the deprecated JMSJDBCStoreMBean.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.GenericJDBCStoreMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CreateTableDDLFile")) {
         getterName = "getCreateTableDDLFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCreateTableDDLFile";
         }

         currentResult = new PropertyDescriptor("CreateTableDDLFile", GenericJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("CreateTableDDLFile", currentResult);
         currentResult.setValue("description", "<p>Specifies the DDL (Data Definition Language) file to use for creating the JDBC store's backing table.</p>  <ul> <li> <p>This field is ignored when the JDBC store's backing table, <code>WLStore</code>, already exists.</p> </li>  <li> <p>If a DDL file is not specified and the JDBC store detects that a backing table doesn't already exist, the JDBC store automatically creates the table by executing a preconfigured DDL file that is specific to the database vendor. These preconfigured files are located in the  <code>weblogic\\store\\io\\jdbc\\ddl</code> directory of the <code><i>MIDDLEWARE_HOME</i>\\modules\\com.bea.core.store.jdbc_x.x.x.x.jar</code> file.</p> </li>  <li> <p>If a DDL file is specified and the JDBC store detects that a backing table doesn't already exist, then the JDBC store searches for the DDL file in the file path first, and then if the file is not found, it searches for it in the CLASSPATH. Once found, the SQL within the DDL file is executed to create the JDBC store's database table. If the DDL file is not found and the backing table doesn't already exist, the JDBC store will fail to boot.</p> </li> </ul> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrefixName")) {
         getterName = "getPrefixName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrefixName";
         }

         currentResult = new PropertyDescriptor("PrefixName", GenericJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("PrefixName", currentResult);
         currentResult.setValue("description", "<p>The prefix for the JDBC store's database table (<code>WLStore</code>), in the following format: <code>[[[catalog.]schema.]prefix]</code>.</p>  <p>Each period symbol in the <code>[[catalog.]schema.]prefix</code> format is significant, where schema generally corresponds to username in many databases. When no prefix is specified, the JDBC store table name is simply <code>WLStore</code> and the database implicitly determines the schema according to the JDBC connection's user. As a best practice, you should always configure a prefix for the JDBC <code>WLStore</code> table name.</p> <p> For specific guidelines about using JDBC store prefixes, refer to the \"Using the WebLogic Store\" section of <i>Designing and Configuring WebLogic Server Environments\"</i>.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
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
