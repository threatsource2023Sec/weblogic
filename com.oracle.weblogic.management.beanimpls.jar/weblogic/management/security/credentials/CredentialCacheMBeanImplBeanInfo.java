package weblogic.management.security.credentials;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;

public class CredentialCacheMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CredentialCacheMBean.class;

   public CredentialCacheMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CredentialCacheMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.credentials.CredentialCacheMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.security.credentials");
      String description = (new String("Defines methods used to get/set the configuration attributes that are required to support the credential cache. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.credentials.CredentialCacheMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CredentialCacheTTL")) {
         getterName = "getCredentialCacheTTL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialCacheTTL";
         }

         currentResult = new PropertyDescriptor("CredentialCacheTTL", CredentialCacheMBean.class, getterName, setterName);
         descriptors.put("CredentialCacheTTL", currentResult);
         currentResult.setValue("description", "Returns the maximum number of seconds a credential entry is valid in the LRU cache. ");
         setPropertyDescriptorDefault(currentResult, new Integer(600));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CredentialsCacheSize")) {
         getterName = "getCredentialsCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialsCacheSize";
         }

         currentResult = new PropertyDescriptor("CredentialsCacheSize", CredentialCacheMBean.class, getterName, setterName);
         descriptors.put("CredentialsCacheSize", currentResult);
         currentResult.setValue("description", "Returns the maximum size of the LRU cache for holding credentials if caching is enabled. ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CredentialCachingEnabled")) {
         getterName = "isCredentialCachingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialCachingEnabled";
         }

         currentResult = new PropertyDescriptor("CredentialCachingEnabled", CredentialCacheMBean.class, getterName, setterName);
         descriptors.put("CredentialCachingEnabled", currentResult);
         currentResult.setValue("description", "Returns whether non-null credentials are cached by the credential mappers ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
