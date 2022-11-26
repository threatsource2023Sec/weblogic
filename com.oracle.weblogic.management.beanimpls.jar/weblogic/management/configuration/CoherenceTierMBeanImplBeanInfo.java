package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceTierMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceTierMBean.class;

   public CoherenceTierMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceTierMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CoherenceTierMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This class represents Coherence Tier. A Coherence Tier is formed by one WLS Cluster.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CoherenceTierMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceWebFederatedStorageEnabled")) {
         getterName = "isCoherenceWebFederatedStorageEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCoherenceWebFederatedStorageEnabled";
         }

         currentResult = new PropertyDescriptor("CoherenceWebFederatedStorageEnabled", CoherenceTierMBean.class, getterName, setterName);
         descriptors.put("CoherenceWebFederatedStorageEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether Federated Cache Storage is enabled for the Coherence Web cluster member </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("CoherenceWebLocalStorageEnabled")) {
         getterName = "isCoherenceWebLocalStorageEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCoherenceWebLocalStorageEnabled";
         }

         currentResult = new PropertyDescriptor("CoherenceWebLocalStorageEnabled", CoherenceTierMBean.class, getterName, setterName);
         descriptors.put("CoherenceWebLocalStorageEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether Local Storage is enabled for the Coherence*Web cluster tier </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalStorageEnabled")) {
         getterName = "isLocalStorageEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalStorageEnabled";
         }

         currentResult = new PropertyDescriptor("LocalStorageEnabled", CoherenceTierMBean.class, getterName, setterName);
         descriptors.put("LocalStorageEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether Local Storage is enabled  </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
