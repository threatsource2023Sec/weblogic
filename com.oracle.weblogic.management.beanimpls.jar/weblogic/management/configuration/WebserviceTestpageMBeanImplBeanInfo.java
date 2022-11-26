package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WebserviceTestpageMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebserviceTestpageMBean.class;

   public WebserviceTestpageMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebserviceTestpageMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebserviceTestpageMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.2.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Controls the configuration of the Web Service Test Page.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebserviceTestpageMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BasicAuthEnabled")) {
         getterName = "isBasicAuthEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBasicAuthEnabled";
         }

         currentResult = new PropertyDescriptor("BasicAuthEnabled", WebserviceTestpageMBean.class, getterName, setterName);
         descriptors.put("BasicAuthEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the basic authentication is automatically used by Web Service Test Page in the current domain.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", WebserviceTestpageMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the WebLogic Server automatically deploys Web Service Test Page in the current domain.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
