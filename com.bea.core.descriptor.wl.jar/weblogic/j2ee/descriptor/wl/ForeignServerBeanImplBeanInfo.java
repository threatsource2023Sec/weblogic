package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ForeignServerBeanImplBeanInfo extends TargetableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ForeignServerBean.class;

   public ForeignServerBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ForeignServerBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ForeignServerBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("This bean represents foreign resources. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ForeignServerBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionURL")) {
         getterName = "getConnectionURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionURL";
         }

         currentResult = new PropertyDescriptor("ConnectionURL", ForeignServerBean.class, getterName, setterName);
         descriptors.put("ConnectionURL", currentResult);
         currentResult.setValue("description", "<p>The URL that WebLogic Server will use to contact the JNDI provider. The syntax of this URL depends on which JNDI provider is being used. For WebLogic JMS, leave this field blank if you are referencing WebLogic JMS objects within the same cluster.</p>  <p>This value corresponds to the standard JNDI property, <code>java.naming.provider.url</code>.</p>  <p><i>Note:</i> If this value is not specified, look-ups will be performed on the JNDI server within the WebLogic Server instance where this connection factory is deployed.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForeignConnectionFactories")) {
         getterName = "getForeignConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignConnectionFactories", ForeignServerBean.class, getterName, setterName);
         descriptors.put("ForeignConnectionFactories", currentResult);
         currentResult.setValue("description", "Gets an array of all foreign connection factories associated with this foreign server. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyForeignConnectionFactory");
         currentResult.setValue("creator", "createForeignConnectionFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForeignDestinations")) {
         getterName = "getForeignDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignDestinations", ForeignServerBean.class, getterName, setterName);
         descriptors.put("ForeignDestinations", currentResult);
         currentResult.setValue("description", "Gets an array of all foreign destinations associated with this foreign server. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyForeignDestination");
         currentResult.setValue("creator", "createForeignDestination");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialContextFactory")) {
         getterName = "getInitialContextFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialContextFactory";
         }

         currentResult = new PropertyDescriptor("InitialContextFactory", ForeignServerBean.class, getterName, setterName);
         descriptors.put("InitialContextFactory", currentResult);
         currentResult.setValue("description", "<p>The name of the class that must be instantiated to access the JNDI provider. This class name depends on the JNDI provider and the vendor that are being used.</p>  <p>This value corresponds to the standard JNDI property, <code>java.naming.factory.initial</code>.</p>  <p><i>Note:</i> This value defaults to <code>weblogic.jndi.WLInitialContextFactory</code>, which is the correct value for WebLogic Server.</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.jndi.WLInitialContextFactory");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIProperties")) {
         getterName = "getJNDIProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("JNDIProperties", ForeignServerBean.class, getterName, setterName);
         descriptors.put("JNDIProperties", currentResult);
         currentResult.setValue("description", "<p>Any additional properties that must be set for the JNDI provider. These properties will be passed directly to the constructor for the JNDI provider's InitialContext class.</p>  <p>Some foreign providers require other properties to be set while obtaining an initial naming context. These properties can be set with a property bean </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJNDIProperty");
         currentResult.setValue("destroyer", "destroyJNDIProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIPropertiesCredential")) {
         getterName = "getJNDIPropertiesCredential";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIPropertiesCredential";
         }

         currentResult = new PropertyDescriptor("JNDIPropertiesCredential", ForeignServerBean.class, getterName, setterName);
         descriptors.put("JNDIPropertiesCredential", currentResult);
         currentResult.setValue("description", "<p>Any Credentials that must be set for the JNDI provider. These Credentials will be part of the properties will be passed directly to the constructor for the JNDI provider's InitialContext class.</p>  <p>Some foreign providers require other properties to be set while obtaining an initial naming context. These properties can be set with a property bean </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIPropertiesCredentialEncrypted")) {
         getterName = "getJNDIPropertiesCredentialEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIPropertiesCredentialEncrypted";
         }

         currentResult = new PropertyDescriptor("JNDIPropertiesCredentialEncrypted", ForeignServerBean.class, getterName, setterName);
         descriptors.put("JNDIPropertiesCredentialEncrypted", currentResult);
         currentResult.setValue("description", "<p> This should not be called by anyone.  It's required when using the encrypted tag ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ForeignServerBean.class.getMethod("createForeignDestination", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the foreign destination to add to this foreign server. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a foreign destination and adds it to this foreign server ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignDestinations");
      }

      mth = ForeignServerBean.class.getMethod("destroyForeignDestination", ForeignDestinationBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("foreignDestination", "The specific destination to remove from this foreign server ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a foreign destination from this foreign server. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignDestinations");
      }

      mth = ForeignServerBean.class.getMethod("createForeignConnectionFactory", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the foreign connection factory to add to this foreign server ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a foreign connection factory and adds it to this foreign server. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignConnectionFactories");
      }

      mth = ForeignServerBean.class.getMethod("destroyForeignConnectionFactory", ForeignConnectionFactoryBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("foreignConnectionFactory", "The specific factory to remove from this foreign server ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a foreign connection factory from this foreign server. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignConnectionFactories");
      }

      mth = ForeignServerBean.class.getMethod("createJNDIProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("key", "The key of the property to add to this foreign server ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a JNDI property bean and adds it to this foreign server. <p> Some foreign providers require other properties to be set while obtaining an initial naming context.  These properties can be set with a property bean </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JNDIProperties");
      }

      mth = ForeignServerBean.class.getMethod("destroyJNDIProperty", PropertyBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jndiProperty", "The property to remove from this foreign server ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a JNDI property bean from this foreign server. <p> Some foreign providers require other properties to be set while obtaining an initial naming context.  These properties can be set with a property bean </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JNDIProperties");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ForeignServerBean.class.getMethod("lookupForeignDestination", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the destination to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a foreign destination with the given name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignDestinations");
      }

      mth = ForeignServerBean.class.getMethod("lookupForeignConnectionFactory", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the foreign connection factory to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a foreign connection factory bean with the given name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignConnectionFactories");
      }

      mth = ForeignServerBean.class.getMethod("lookupJNDIProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("key", "name The name of the JNDIProperty to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a JNDI Property with the given key. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JNDIProperties");
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
