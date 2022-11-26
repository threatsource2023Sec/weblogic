package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class OsgiFrameworkMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = OsgiFrameworkMBean.class;

   public OsgiFrameworkMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public OsgiFrameworkMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.OsgiFrameworkMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("An MBean representing an OSGi framework. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.OsgiFrameworkMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DeployInstallationBundles")) {
         getterName = "getDeployInstallationBundles";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeployInstallationBundles";
         }

         currentResult = new PropertyDescriptor("DeployInstallationBundles", OsgiFrameworkMBean.class, getterName, setterName);
         descriptors.put("DeployInstallationBundles", currentResult);
         currentResult.setValue("description", "Determines if some WebLogic helper bundles will be installed into the framework <p> If this is set to \"populate\" then the bundles found in the WebLogic installation directory under wlserver/server/osgi-lib will be installed into this OSGi framework.  Futhermore a few extra packages will be added to the bootdelegation classpath parameters in order to enable the bundles in the osgi-lib directory if they are not already there.</p> <p> If this is set to \"ignore\" then the bundles found in the WebLogic installation directory will not be installed into the framework.</p> ");
         setPropertyDescriptorDefault(currentResult, "populate");
         currentResult.setValue("legalValues", new Object[]{"populate", "ignore"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FactoryImplementationClass")) {
         getterName = "getFactoryImplementationClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFactoryImplementationClass";
         }

         currentResult = new PropertyDescriptor("FactoryImplementationClass", OsgiFrameworkMBean.class, getterName, setterName);
         descriptors.put("FactoryImplementationClass", currentResult);
         currentResult.setValue("description", "The name of the frameworks implementation class for the org.osgi.framework.launch.FrameworkFactory class. ");
         setPropertyDescriptorDefault(currentResult, "org.apache.felix.framework.FrameworkFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitProperties")) {
         getterName = "getInitProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitProperties";
         }

         currentResult = new PropertyDescriptor("InitProperties", OsgiFrameworkMBean.class, getterName, setterName);
         descriptors.put("InitProperties", currentResult);
         currentResult.setValue("description", "The properties to be used when initializing the framework. All standard properties and all properties specific to the framework can be set. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", OsgiFrameworkMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("OrgOsgiFrameworkBootdelegation")) {
         getterName = "getOrgOsgiFrameworkBootdelegation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrgOsgiFrameworkBootdelegation";
         }

         currentResult = new PropertyDescriptor("OrgOsgiFrameworkBootdelegation", OsgiFrameworkMBean.class, getterName, setterName);
         descriptors.put("OrgOsgiFrameworkBootdelegation", currentResult);
         currentResult.setValue("description", "The name of the org.osgi.framework.bootdelegation property.  Note that this value, if set, will take precedence over anything specified in the init-properties. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OrgOsgiFrameworkSystemPackagesExtra")) {
         getterName = "getOrgOsgiFrameworkSystemPackagesExtra";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrgOsgiFrameworkSystemPackagesExtra";
         }

         currentResult = new PropertyDescriptor("OrgOsgiFrameworkSystemPackagesExtra", OsgiFrameworkMBean.class, getterName, setterName);
         descriptors.put("OrgOsgiFrameworkSystemPackagesExtra", currentResult);
         currentResult.setValue("description", "The name of the org.osgi.framework.system.packages.extra property.  Note that this value, if set, will take precedence over anything specified in the init-properties. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OsgiImplementationLocation")) {
         getterName = "getOsgiImplementationLocation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOsgiImplementationLocation";
         }

         currentResult = new PropertyDescriptor("OsgiImplementationLocation", OsgiFrameworkMBean.class, getterName, setterName);
         descriptors.put("OsgiImplementationLocation", currentResult);
         currentResult.setValue("description", "<p>The location of the OSGi implementation JAR file which contains the org.osgi.framework.launch.FrameworkFactory implementation.  If this field is not set then an appropriate default implementation that is shipped with the product will be used.</p> <p> If this field is relative it must be relative to the start directory of the server and the file must exist everywhere this framework is deployed.  If this field is not relative then the given filename must exist at the same location everywhere this framework is deployed.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RegisterGlobalDataSources")) {
         getterName = "isRegisterGlobalDataSources";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRegisterGlobalDataSources";
         }

         currentResult = new PropertyDescriptor("RegisterGlobalDataSources", OsgiFrameworkMBean.class, getterName, setterName);
         descriptors.put("RegisterGlobalDataSources", currentResult);
         currentResult.setValue("description", "Returns true if global data sources should be added to the OSGi service registry. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RegisterGlobalWorkManagers")) {
         getterName = "isRegisterGlobalWorkManagers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRegisterGlobalWorkManagers";
         }

         currentResult = new PropertyDescriptor("RegisterGlobalWorkManagers", OsgiFrameworkMBean.class, getterName, setterName);
         descriptors.put("RegisterGlobalWorkManagers", currentResult);
         currentResult.setValue("description", "Returns true if global work managers should be added to the OSGi service registry. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
