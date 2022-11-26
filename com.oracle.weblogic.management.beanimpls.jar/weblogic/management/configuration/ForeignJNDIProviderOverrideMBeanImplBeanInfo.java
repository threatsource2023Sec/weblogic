package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ForeignJNDIProviderOverrideMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ForeignJNDIProviderOverrideMBean.class;

   public ForeignJNDIProviderOverrideMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ForeignJNDIProviderOverrideMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ForeignJNDIProviderOverrideMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Defines partition-specific overrides for the most commonly overridden attributes in a ForeignJNDIProvider MBean. It overrides some of the settings in a same-named ForeignJNDIProvider MBean instance in the same multi-tenant scope to which this override MBean belongs. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ForeignJNDIProviderOverrideMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ForeignJNDILinks")) {
         getterName = "getForeignJNDILinks";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignJNDILinks", ForeignJNDIProviderOverrideMBean.class, getterName, setterName);
         descriptors.put("ForeignJNDILinks", currentResult);
         currentResult.setValue("description", "<p>The foreign links.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyForeignJNDILink");
         currentResult.setValue("creator", "createForeignJNDILink");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialContextFactory")) {
         getterName = "getInitialContextFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialContextFactory";
         }

         currentResult = new PropertyDescriptor("InitialContextFactory", ForeignJNDIProviderOverrideMBean.class, getterName, setterName);
         descriptors.put("InitialContextFactory", currentResult);
         currentResult.setValue("description", "<p>The initial context factory to use to connect. This class name depends on the JNDI provider and the vendor you are using. The value corresponds to the standard JNDI property, <code>java.naming.factory.initial</code>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", ForeignJNDIProviderOverrideMBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "<p>The remote server's user password.</p> ");
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

         currentResult = new PropertyDescriptor("PasswordEncrypted", ForeignJNDIProviderOverrideMBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The remote server's encrypted user password.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProperties";
         }

         currentResult = new PropertyDescriptor("Properties", ForeignJNDIProviderOverrideMBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "<p>Any additional properties that must be set for the JNDI provider. These properties will be passed directly to the constructor for the JNDI provider's <code>InitialContext</code> class.</p>  <p><b>Note:</b> This value must be filled in using a <code>name=value&lt;return&gt;name=value</code> format.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProviderURL")) {
         getterName = "getProviderURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProviderURL";
         }

         currentResult = new PropertyDescriptor("ProviderURL", ForeignJNDIProviderOverrideMBean.class, getterName, setterName);
         descriptors.put("ProviderURL", currentResult);
         currentResult.setValue("description", "<p>The foreign JNDI provider URL. This value corresponds to the standard JNDI property, <code>java.naming.provider.url</code>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("User")) {
         getterName = "getUser";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUser";
         }

         currentResult = new PropertyDescriptor("User", ForeignJNDIProviderOverrideMBean.class, getterName, setterName);
         descriptors.put("User", currentResult);
         currentResult.setValue("description", "<p>The remote server's user name.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ForeignJNDIProviderOverrideMBean.class.getMethod("createForeignJNDILink", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the foreign JNDI link ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a ForeignJNDILink resource with the given name.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJNDILinks");
      }

      mth = ForeignJNDIProviderOverrideMBean.class.getMethod("destroyForeignJNDILink", ForeignJNDILinkOverrideMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "foreign link ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroy the given ForeignJNDILink resource.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJNDILinks");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ForeignJNDIProviderOverrideMBean.class.getMethod("lookupForeignJNDILink", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the foreign JNDI link ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Find a ForeignJNDILink resource with the given name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignJNDILinks");
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
