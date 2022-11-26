package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class FederationServicesMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = FederationServicesMBean.class;

   public FederationServicesMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public FederationServicesMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.FederationServicesMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean represents configuration for SAML 1.1-based Federation Services, including the intersite transfer service, assertion consumer service, and assertion retrieval service. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.FederationServicesMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AllowedTargetHosts")) {
         getterName = "getAllowedTargetHosts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowedTargetHosts";
         }

         currentResult = new PropertyDescriptor("AllowedTargetHosts", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("AllowedTargetHosts", currentResult);
         currentResult.setValue("description", "List of hosts to compare against the host in the SAML SP target redirect URL. When the list is empty, the target redirect URL will not be checked. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AssertionConsumerURIs")) {
         getterName = "getAssertionConsumerURIs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAssertionConsumerURIs";
         }

         currentResult = new PropertyDescriptor("AssertionConsumerURIs", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("AssertionConsumerURIs", currentResult);
         currentResult.setValue("description", "<p>The Assertion Consumer URIs.</p> ");
         setPropertyDescriptorDefault(currentResult, BeanInfoHelper.stringArray("/samlacs/acs"));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AssertionRetrievalURIs")) {
         getterName = "getAssertionRetrievalURIs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAssertionRetrievalURIs";
         }

         currentResult = new PropertyDescriptor("AssertionRetrievalURIs", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("AssertionRetrievalURIs", currentResult);
         currentResult.setValue("description", "<p>One or more URIs on which to listen for incoming assertion retrieval requests.</p>  <p>For artifact profile, the destination site receives a SAML artifact that represents a source site (why we need the source site ID values) and an assertion ID. The destination site sends a request containing the artifact to the source site's assertion retrieval URL, and the source site responds with the corresponding assertion. You may configure multiple URIs here, although typically one will be sufficient. The URI includes the application context, followed by the resource context. For example:</p>  <p><code> /my_application/saml/ars</code></p>  <p>which would be accessible from the outside as <code>https://my.example.com/my_application/saml/ars</code></p> ");
         setPropertyDescriptorDefault(currentResult, BeanInfoHelper.stringArray("/samlars/ars"));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AssertionStoreClassName")) {
         getterName = "getAssertionStoreClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAssertionStoreClassName";
         }

         currentResult = new PropertyDescriptor("AssertionStoreClassName", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("AssertionStoreClassName", currentResult);
         currentResult.setValue("description", "<p>The class that provides persistent storage for assertions, if you use an Assertion Store class other than the default class.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AssertionStoreProperties")) {
         getterName = "getAssertionStoreProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAssertionStoreProperties";
         }

         currentResult = new PropertyDescriptor("AssertionStoreProperties", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("AssertionStoreProperties", currentResult);
         currentResult.setValue("description", "<p>Properties passed to Assertion Store class initStore() method.</p> <p>This may be useful if you have implemented a custom Assertion Store class.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntersiteTransferURIs")) {
         getterName = "getIntersiteTransferURIs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIntersiteTransferURIs";
         }

         currentResult = new PropertyDescriptor("IntersiteTransferURIs", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("IntersiteTransferURIs", currentResult);
         currentResult.setValue("description", "<p>The Intersite Transfer URIs.</p> ");
         currentResult.setValue("default", new String[]{"/samlits_ba/its", "/samlits_ba/its/post", "/samlits_ba/its/artifact", "/samlits_cc/its", "/samlits_cc/its/post", "/samlits_cc/its/artifact"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("SSLClientIdentityAlias")) {
         getterName = "getSSLClientIdentityAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLClientIdentityAlias";
         }

         currentResult = new PropertyDescriptor("SSLClientIdentityAlias", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SSLClientIdentityAlias", currentResult);
         currentResult.setValue("description", "<p>The alias used to store and retrieve the Destination Site's SSL client identity in the keystore.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSLClientIdentityPassPhrase")) {
         getterName = "getSSLClientIdentityPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLClientIdentityPassPhrase";
         }

         currentResult = new PropertyDescriptor("SSLClientIdentityPassPhrase", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SSLClientIdentityPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the Destination Site's SSL client identity from the keystore.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSLClientIdentityPassPhraseEncrypted")) {
         getterName = "getSSLClientIdentityPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLClientIdentityPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("SSLClientIdentityPassPhraseEncrypted", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SSLClientIdentityPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted passphrase used to retrieve the Destination Site's SSL client identity from the keystore.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SigningKeyAlias")) {
         getterName = "getSigningKeyAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSigningKeyAlias";
         }

         currentResult = new PropertyDescriptor("SigningKeyAlias", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SigningKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The alias used to store and retrieve the Source Site's signing key in the keystore. This key is used to sign POST profile responses.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SigningKeyPassPhrase")) {
         getterName = "getSigningKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSigningKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("SigningKeyPassPhrase", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SigningKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the Source Site's signing key from the keystore.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SigningKeyPassPhraseEncrypted")) {
         getterName = "getSigningKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSigningKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("SigningKeyPassPhraseEncrypted", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SigningKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted passphrase used to retrieve the Source Site's signing key from the keystore.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceIdBase64")) {
         getterName = "getSourceIdBase64";
         setterName = null;
         currentResult = new PropertyDescriptor("SourceIdBase64", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SourceIdBase64", currentResult);
         currentResult.setValue("description", "<p>The Source Site ID base64-encoded.</p> <p>This read-only value is a Base64 representation of a 20-byte binary value that is calculated from the <code>SourceSiteURL</code>. If you want to configure ARTIFACT profile with another site, you will need to give a <code>SourceId</code> value to the other site. This value is automatically updated when the <code>SourceSiteURL</code> changes.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceIdHex")) {
         getterName = "getSourceIdHex";
         setterName = null;
         currentResult = new PropertyDescriptor("SourceIdHex", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SourceIdHex", currentResult);
         currentResult.setValue("description", "<p>The Source Site ID in hexadecimal.</p> <p>This read-only value is a hexadecimal representation of a 20-byte binary value that is calculated from the <code>SourceSiteURL</code>. If you want to configure ARTIFACT profile with another site, you will need to give a <code>SourceId</code> value to the other site. This value is automatically updated when the <code>SourceSiteURL</code> changes.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceSiteURL")) {
         getterName = "getSourceSiteURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourceSiteURL";
         }

         currentResult = new PropertyDescriptor("SourceSiteURL", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SourceSiteURL", currentResult);
         currentResult.setValue("description", "<p>The URL for the Source Site.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("UsedAssertionCacheClassName")) {
         getterName = "getUsedAssertionCacheClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUsedAssertionCacheClassName";
         }

         currentResult = new PropertyDescriptor("UsedAssertionCacheClassName", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("UsedAssertionCacheClassName", currentResult);
         currentResult.setValue("description", "<p>The class used as the persistent store for the Used Assertion Cache. When no class is specified, the default Used Assertion Cache implementation is used.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UsedAssertionCacheProperties")) {
         getterName = "getUsedAssertionCacheProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUsedAssertionCacheProperties";
         }

         currentResult = new PropertyDescriptor("UsedAssertionCacheProperties", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("UsedAssertionCacheProperties", currentResult);
         currentResult.setValue("description", "<p>Properties to be passed to the Used Assertion Cache class.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ACSRequiresSSL")) {
         getterName = "isACSRequiresSSL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setACSRequiresSSL";
         }

         currentResult = new PropertyDescriptor("ACSRequiresSSL", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("ACSRequiresSSL", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Assertion Consumer Service requires SSL.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ARSRequiresSSL")) {
         getterName = "isARSRequiresSSL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setARSRequiresSSL";
         }

         currentResult = new PropertyDescriptor("ARSRequiresSSL", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("ARSRequiresSSL", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Assertion Retrieval Service requires SSL.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ARSRequiresTwoWaySSL")) {
         getterName = "isARSRequiresTwoWaySSL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setARSRequiresTwoWaySSL";
         }

         currentResult = new PropertyDescriptor("ARSRequiresTwoWaySSL", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("ARSRequiresTwoWaySSL", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Assertion Retrieval Service requires two-way SSL authentication.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationSiteEnabled")) {
         getterName = "isDestinationSiteEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationSiteEnabled";
         }

         currentResult = new PropertyDescriptor("DestinationSiteEnabled", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("DestinationSiteEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Destination Site is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ITSRequiresSSL")) {
         getterName = "isITSRequiresSSL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setITSRequiresSSL";
         }

         currentResult = new PropertyDescriptor("ITSRequiresSSL", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("ITSRequiresSSL", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Intersite Transfer Service requires SSL.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("POSTOneUseCheckEnabled")) {
         getterName = "isPOSTOneUseCheckEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPOSTOneUseCheckEnabled";
         }

         currentResult = new PropertyDescriptor("POSTOneUseCheckEnabled", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("POSTOneUseCheckEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the POST one-use check is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("POSTRecipientCheckEnabled")) {
         getterName = "isPOSTRecipientCheckEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPOSTRecipientCheckEnabled";
         }

         currentResult = new PropertyDescriptor("POSTRecipientCheckEnabled", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("POSTRecipientCheckEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the POST recipient check is enabled. When true, the recipient of the SAML Response must match the URL in the HTTP Request.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceSiteEnabled")) {
         getterName = "isSourceSiteEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourceSiteEnabled";
         }

         currentResult = new PropertyDescriptor("SourceSiteEnabled", FederationServicesMBean.class, getterName, setterName);
         descriptors.put("SourceSiteEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Source Site is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = FederationServicesMBean.class.getMethod("addTag", String.class);
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
         mth = FederationServicesMBean.class.getMethod("removeTag", String.class);
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
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = FederationServicesMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = FederationServicesMBean.class.getMethod("restoreDefaultValue", String.class);
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
