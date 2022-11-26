package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class DeploymentConfigOverridesMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeploymentConfigOverridesMBean.class;

   public DeploymentConfigOverridesMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeploymentConfigOverridesMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.DeploymentConfigOverridesMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Specifies the domain-level attributes related to deployment-config-overrides.xml ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DeploymentConfigOverridesMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Dir")) {
         getterName = "getDir";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDir";
         }

         currentResult = new PropertyDescriptor("Dir", DeploymentConfigOverridesMBean.class, getterName, setterName);
         descriptors.put("Dir", currentResult);
         currentResult.setValue("description", "<p>Specifies the path to directory that contains deployment-config-overrides.xml. If the path is relative, it is resolved relative to domain directory </p> ");
         setPropertyDescriptorDefault(currentResult, "config/");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxOldAppVersions")) {
         getterName = "getMaxOldAppVersions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxOldAppVersions";
         }

         currentResult = new PropertyDescriptor("MaxOldAppVersions", DeploymentConfigOverridesMBean.class, getterName, setterName);
         descriptors.put("MaxOldAppVersions", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum number of the applications that can be retired concurrently in one hot patching cycle</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PollInterval")) {
         getterName = "getPollInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPollInterval";
         }

         currentResult = new PropertyDescriptor("PollInterval", DeploymentConfigOverridesMBean.class, getterName, setterName);
         descriptors.put("PollInterval", currentResult);
         currentResult.setValue("description", "<p>Specifies the poll interval for deployment config overrides file. The value is in seconds</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(900));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
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
