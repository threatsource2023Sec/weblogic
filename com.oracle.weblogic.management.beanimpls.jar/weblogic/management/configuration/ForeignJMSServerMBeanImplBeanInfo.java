package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ForeignJMSServerMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ForeignJMSServerMBean.class;

   public ForeignJMSServerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ForeignJMSServerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ForeignJMSServerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("obsolete", "9.0.0.0");
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced by the ForeignServerBean type in the new JMS module. ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class represents a JNDI provider that is outside the WebLogic JMS server. It is a parent element of the ForeignJMSConnectionFactory and ForeignJMSDestination MBeans. It contains information that allows WebLogic Server to reach the remote JNDI provider. This way, a number of connection factory and destination objects can be defined on one JNDI directory. <p/> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ForeignJMSServerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionFactories")) {
         getterName = "getConnectionFactories";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactories";
         }

         currentResult = new PropertyDescriptor("ConnectionFactories", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("ConnectionFactories", currentResult);
         currentResult.setValue("description", "<p>The remote connection factories.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addConnectionFactory");
         currentResult.setValue("remover", "removeConnectionFactory");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionURL")) {
         getterName = "getConnectionURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionURL";
         }

         currentResult = new PropertyDescriptor("ConnectionURL", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("ConnectionURL", currentResult);
         currentResult.setValue("description", "<p>The URL that WebLogic Server will use to contact the JNDI provider. The syntax of this URL depends on which JNDI provider is being used. For WebLogic JMS, leave this field blank if you are referencing WebLogic JMS objects within the same cluster. This value corresponds to the standard JNDI property, <tt>java.naming.provider.url</tt>.</p> <p/> <p><i>Note:</i> If this value is not specified, look-ups will be performed on the JNDI server within the WebLogic Server instance where this connection factory is deployed.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Destinations")) {
         getterName = "getDestinations";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinations";
         }

         currentResult = new PropertyDescriptor("Destinations", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("Destinations", currentResult);
         currentResult.setValue("description", "<p>The remote destinations.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addDestination");
         currentResult.setValue("remover", "removeDestination");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by the ForeignServerBean type in the new JMS module. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("ForeignJMSConnectionFactories")) {
         getterName = "getForeignJMSConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignJMSConnectionFactories", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("ForeignJMSConnectionFactories", currentResult);
         currentResult.setValue("description", "Get all the Members ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createForeignJMSConnectionFactory");
         currentResult.setValue("creator", "createForeignJMSConnectionFactory");
         currentResult.setValue("destroyer", "destroyForeignJMSConnectionFactory");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by the ForeignServerBean type in the new JMS module. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("ForeignJMSDestinations")) {
         getterName = "getForeignJMSDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignJMSDestinations", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("ForeignJMSDestinations", currentResult);
         currentResult.setValue("description", "Get all the Members ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyForeignJMSDestination");
         currentResult.setValue("creator", "createForeignJMSDestination");
         currentResult.setValue("creator", "createForeignJMSDestination");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by the ForeignServerBean type in the new JMS module. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("InitialContextFactory")) {
         getterName = "getInitialContextFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialContextFactory";
         }

         currentResult = new PropertyDescriptor("InitialContextFactory", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("InitialContextFactory", currentResult);
         currentResult.setValue("description", "<p>The name of the class that must be instantiated to access the JNDI provider. This class name depends on the JNDI provider and the vendor that are being used. This value corresponds to the standard JNDI property, <tt>java.naming.factory.initial</tt>.</p> <p/> <p><i>Note:</i> This value defaults to <tt>weblogic.jndi.WLInitialContextFactory</tt>, which is the correct value for WebLogic Server.</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.jndi.WLInitialContextFactory");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIProperties")) {
         getterName = "getJNDIProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIProperties";
         }

         currentResult = new PropertyDescriptor("JNDIProperties", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("JNDIProperties", currentResult);
         currentResult.setValue("description", "<p>Any additional properties that must be set for the JNDI provider. These properties will be passed directly to the constructor for the JNDI provider's <tt>InitialContext</tt> class.</p> <p/> <p><i>Note:</i> This value must be filled in using a <tt>name=value&lt;return&gt;name=value</tt> format.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("JNDIPropertiesCredential")) {
         getterName = "getJNDIPropertiesCredential";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIPropertiesCredential";
         }

         currentResult = new PropertyDescriptor("JNDIPropertiesCredential", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("JNDIPropertiesCredential", currentResult);
         currentResult.setValue("description", "<p>The encrypted value of the value set via java.naming.security.credentials property of the JNDIProperties attribute. set via <code> setJNDIPropertiesCredential </code>, ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.EncryptionHelper")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIPropertiesCredentialEncrypted")) {
         getterName = "getJNDIPropertiesCredentialEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIPropertiesCredentialEncrypted";
         }

         currentResult = new PropertyDescriptor("JNDIPropertiesCredentialEncrypted", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("JNDIPropertiesCredentialEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted value of the value set via java.naming.security.credentials property of the JNDIProperties attribute. set via <code> setJNDIPropertiesCredentialEncrypted </code>, ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.EncryptionHelper")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>You must select a target on which an MBean will be deployed from this list of the targets in the current domain on which this item can be deployed. Targets must be either servers or clusters. The deployment will only occur once if deployments overlap.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", ForeignJMSServerMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ForeignJMSServerMBean.class.getMethod("createForeignJMSConnectionFactory", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a new diagnostic deployment that can be targeted to a server</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSConnectionFactories");
      }

      mth = ForeignJMSServerMBean.class.getMethod("destroyForeignJMSConnectionFactory", ForeignJMSConnectionFactoryMBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Delete a diagnostic deployment configuration from the domain.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSConnectionFactories");
      }

      mth = ForeignJMSServerMBean.class.getMethod("createForeignJMSConnectionFactory", String.class, ForeignJMSConnectionFactoryMBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSConnectionFactories");
      }

      mth = ForeignJMSServerMBean.class.getMethod("createForeignJMSDestination", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a new diagnostic deployment that can be targeted to a server</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSDestinations");
      }

      mth = ForeignJMSServerMBean.class.getMethod("createForeignJMSDestination", String.class, ForeignJMSDestinationMBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSDestinations");
      }

      mth = ForeignJMSServerMBean.class.getMethod("destroyForeignJMSDestination", ForeignJMSDestinationMBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Delete a diagnostic deployment configuration from the domain.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSDestinations");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ForeignJMSServerMBean.class.getMethod("addDestination", ForeignJMSDestinationMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destination", "The feature to be added to the Destination attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by the ForeignServerBean type in the new JMS module. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a destination.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Destinations");
      }

      mth = ForeignJMSServerMBean.class.getMethod("addTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the Target attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>You can add a target to specify additional servers on which the deployment can be deployed. The targets must be either clusters or servers.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = ForeignJMSServerMBean.class.getMethod("removeDestination", ForeignJMSDestinationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destination", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by the ForeignServerBean type in the new JMS module. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a destination.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Destinations");
      }

      mth = ForeignJMSServerMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the value of the addTarget attribute.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addTarget")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = ForeignJMSServerMBean.class.getMethod("addConnectionFactory", ForeignJMSConnectionFactoryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("factory", "The feature to be added to the ConnectionFactory attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by the ForeignServerBean type in the new JMS module. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a destination.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "ConnectionFactories");
      }

      mth = ForeignJMSServerMBean.class.getMethod("removeConnectionFactory", ForeignJMSConnectionFactoryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("factory", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by the ForeignServerBean type in the new JMS module. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a destination.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "ConnectionFactories");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ForeignJMSServerMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ForeignJMSServerMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ForeignJMSServerMBean.class.getMethod("lookupForeignJMSConnectionFactory", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignJMSConnectionFactories");
      }

      mth = ForeignJMSServerMBean.class.getMethod("lookupForeignJMSDestination", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignJMSDestinations");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ForeignJMSServerMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ForeignJMSServerMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
      }

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
