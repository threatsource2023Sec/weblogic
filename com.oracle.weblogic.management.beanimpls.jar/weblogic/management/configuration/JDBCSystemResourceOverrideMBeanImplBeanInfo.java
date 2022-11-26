package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCSystemResourceOverrideMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCSystemResourceOverrideMBean.class;

   public JDBCSystemResourceOverrideMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCSystemResourceOverrideMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JDBCSystemResourceOverrideMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Defines a partition-specific JDBC data source attribute override of a data source descriptor referenced by a partition resource group definition. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JDBCSystemResourceOverrideMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DataSourceName")) {
         getterName = "getDataSourceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceName";
         }

         currentResult = new PropertyDescriptor("DataSourceName", JDBCSystemResourceOverrideMBean.class, getterName, setterName);
         descriptors.put("DataSourceName", currentResult);
         currentResult.setValue("description", "The name of the data source. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("InitialCapacity")) {
         getterName = "getInitialCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialCapacity";
         }

         currentResult = new PropertyDescriptor("InitialCapacity", JDBCSystemResourceOverrideMBean.class, getterName, setterName);
         descriptors.put("InitialCapacity", currentResult);
         currentResult.setValue("description", "Returns the initial pool capacity for the partition datasource. The value -1 indicates that the attribute is not set. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("JDBCPropertyOverrides")) {
         getterName = "getJDBCPropertyOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCPropertyOverrides", JDBCSystemResourceOverrideMBean.class, getterName, setterName);
         descriptors.put("JDBCPropertyOverrides", currentResult);
         currentResult.setValue("description", "Returns the JDBC property overrides for the partition datasource ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJDBCPropertyOverride");
         currentResult.setValue("destroyer", "destroyJDBCPropertyOverride");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxCapacity")) {
         getterName = "getMaxCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxCapacity";
         }

         currentResult = new PropertyDescriptor("MaxCapacity", JDBCSystemResourceOverrideMBean.class, getterName, setterName);
         descriptors.put("MaxCapacity", currentResult);
         currentResult.setValue("description", "Returns the max pool capacity for the partition datasource. The value -1 indicates that the attribute is not set. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("MinCapacity")) {
         getterName = "getMinCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinCapacity";
         }

         currentResult = new PropertyDescriptor("MinCapacity", JDBCSystemResourceOverrideMBean.class, getterName, setterName);
         descriptors.put("MinCapacity", currentResult);
         currentResult.setValue("description", "Returns the min pool capacity for the partition datasource. The value -1 indicates that the attribute is not set. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", JDBCSystemResourceOverrideMBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "The password in clear text. ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PasswordEncrypted")) {
         getterName = "getPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("PasswordEncrypted", JDBCSystemResourceOverrideMBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "The encrypted password. ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("URL")) {
         getterName = "getURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setURL";
         }

         currentResult = new PropertyDescriptor("URL", JDBCSystemResourceOverrideMBean.class, getterName, setterName);
         descriptors.put("URL", currentResult);
         currentResult.setValue("description", "The connection URL for the data source. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("User")) {
         getterName = "getUser";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUser";
         }

         currentResult = new PropertyDescriptor("User", JDBCSystemResourceOverrideMBean.class, getterName, setterName);
         descriptors.put("User", currentResult);
         currentResult.setValue("description", "The name of the user for the data source. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = JDBCSystemResourceOverrideMBean.class.getMethod("createJDBCPropertyOverride", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the role ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates the named database role ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JDBCPropertyOverrides");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = JDBCSystemResourceOverrideMBean.class.getMethod("destroyJDBCPropertyOverride", JDBCPropertyOverrideMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("role", "The role to remove ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes the specified DatabaseRoleMBean ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JDBCPropertyOverrides");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         Method mth = JDBCSystemResourceOverrideMBean.class.getMethod("lookupJDBCPropertyOverride", String.class);
         ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the JDBC property override ")};
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Looks up the named JDBC property override ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JDBCPropertyOverrides");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

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
