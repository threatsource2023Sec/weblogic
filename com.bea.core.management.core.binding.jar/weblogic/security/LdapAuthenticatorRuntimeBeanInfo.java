package weblogic.security;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.LdapAuthenticatorRuntimeMBean;

public class LdapAuthenticatorRuntimeBeanInfo extends AuthenticatorRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LdapAuthenticatorRuntimeMBean.class;

   public LdapAuthenticatorRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LdapAuthenticatorRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.security.LdapAuthenticatorRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.security");
      String description = (new String("<p>This class is used to monitor per LdapAuthenticator runtime cache metrics and connection pool.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.LdapAuthenticatorRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("GroupCacheHits")) {
         getterName = "getGroupCacheHits";
         setterName = null;
         currentResult = new PropertyDescriptor("GroupCacheHits", LdapAuthenticatorRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("GroupCacheHits", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GroupCacheQueries")) {
         getterName = "getGroupCacheQueries";
         setterName = null;
         currentResult = new PropertyDescriptor("GroupCacheQueries", LdapAuthenticatorRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("GroupCacheQueries", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GroupCacheSize")) {
         getterName = "getGroupCacheSize";
         setterName = null;
         currentResult = new PropertyDescriptor("GroupCacheSize", LdapAuthenticatorRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("GroupCacheSize", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GroupCacheStatStartTimeStamp")) {
         getterName = "getGroupCacheStatStartTimeStamp";
         setterName = null;
         currentResult = new PropertyDescriptor("GroupCacheStatStartTimeStamp", LdapAuthenticatorRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("GroupCacheStatStartTimeStamp", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Timestamp");
      }

      if (!descriptors.containsKey("ProviderName")) {
         getterName = "getProviderName";
         setterName = null;
         currentResult = new PropertyDescriptor("ProviderName", LdapAuthenticatorRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProviderName", currentResult);
         currentResult.setValue("description", "Returns name of this security provider. ");
      }

      if (!descriptors.containsKey("UserCacheHits")) {
         getterName = "getUserCacheHits";
         setterName = null;
         currentResult = new PropertyDescriptor("UserCacheHits", LdapAuthenticatorRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UserCacheHits", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserCacheQueries")) {
         getterName = "getUserCacheQueries";
         setterName = null;
         currentResult = new PropertyDescriptor("UserCacheQueries", LdapAuthenticatorRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UserCacheQueries", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserCacheSize")) {
         getterName = "getUserCacheSize";
         setterName = null;
         currentResult = new PropertyDescriptor("UserCacheSize", LdapAuthenticatorRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UserCacheSize", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserCacheStatStartTimeStamp")) {
         getterName = "getUserCacheStatStartTimeStamp";
         setterName = null;
         currentResult = new PropertyDescriptor("UserCacheStatStartTimeStamp", LdapAuthenticatorRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UserCacheStatStartTimeStamp", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Timestamp");
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
