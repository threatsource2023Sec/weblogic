package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceManagementClusterMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceManagementClusterMBean.class;

   public CoherenceManagementClusterMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceManagementClusterMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CoherenceManagementClusterMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class is used to hold JMX information about a coherence cluster in both the standalone case and WLS managed cluster case. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CoherenceManagementClusterMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceManagementAddressProviders")) {
         getterName = "getCoherenceManagementAddressProviders";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceManagementAddressProviders", CoherenceManagementClusterMBean.class, getterName, setterName);
         descriptors.put("CoherenceManagementAddressProviders", currentResult);
         currentResult.setValue("description", "Returns the list of Addresses associated with this configuration MBean. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCoherenceManagementAddressProvider");
         currentResult.setValue("creator", "createCoherenceManagementAddressProvider");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("EncryptedPassword")) {
         getterName = "getEncryptedPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEncryptedPassword";
         }

         currentResult = new PropertyDescriptor("EncryptedPassword", CoherenceManagementClusterMBean.class, getterName, setterName);
         descriptors.put("EncryptedPassword", currentResult);
         currentResult.setValue("description", "Get JMX connection encrypted password. Deprecated. Use getPasswordEncrypted. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("deprecated", "12.2.1.1.0 ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", CoherenceManagementClusterMBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "Get JMX connection password ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getEncryptedPassword()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PasswordEncrypted")) {
         getterName = "getPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("PasswordEncrypted", CoherenceManagementClusterMBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "Get JMX connection encrypted password ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
      }

      if (!descriptors.containsKey("ReportGroupFile")) {
         getterName = "getReportGroupFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReportGroupFile";
         }

         currentResult = new PropertyDescriptor("ReportGroupFile", CoherenceManagementClusterMBean.class, getterName, setterName);
         descriptors.put("ReportGroupFile", currentResult);
         currentResult.setValue("description", "Get the report group file representing the superset of metrics this bean will gather. ");
         setPropertyDescriptorDefault(currentResult, "em/metadata/reports/coherence/report-group.xml");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Username")) {
         getterName = "getUsername";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUsername";
         }

         currentResult = new PropertyDescriptor("Username", CoherenceManagementClusterMBean.class, getterName, setterName);
         descriptors.put("Username", currentResult);
         currentResult.setValue("description", "Get the JMX connection username ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = CoherenceManagementClusterMBean.class.getMethod("createCoherenceManagementAddressProvider", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name used to refer to the address to be created ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Create a new CoherenceManagementAddressProviderMBean representing the specified address and adds it to the list of currently existing beans. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CoherenceManagementAddressProviders");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = CoherenceManagementClusterMBean.class.getMethod("destroyCoherenceManagementAddressProvider", CoherenceManagementAddressProviderMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "the bean to be destroyed ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys the bean representing the specified address and removes it from the list of currently existing beans. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CoherenceManagementAddressProviders");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         Method mth = CoherenceManagementClusterMBean.class.getMethod("lookupCoherenceManagementAddressProvider", String.class);
         ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name used to refer to the address ")};
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "The bean representing the specified address. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "CoherenceManagementAddressProviders");
            currentResult.setValue("since", "12.2.1.0.0");
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
