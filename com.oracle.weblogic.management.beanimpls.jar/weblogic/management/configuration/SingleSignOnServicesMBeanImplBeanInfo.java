package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SingleSignOnServicesMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SingleSignOnServicesMBean.class;

   public SingleSignOnServicesMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SingleSignOnServicesMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SingleSignOnServicesMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.5.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean represents configuration for SAML 2.0-based local site Single Sign-On Services.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SingleSignOnServicesMBean");
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

         currentResult = new PropertyDescriptor("AllowedTargetHosts", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("AllowedTargetHosts", currentResult);
         currentResult.setValue("description", "List of hosts to compare against the host in the SAML SP target redirect URL. When the list is empty, the target redirect URL will not be checked. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ArtifactMaxCacheSize")) {
         getterName = "getArtifactMaxCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setArtifactMaxCacheSize";
         }

         currentResult = new PropertyDescriptor("ArtifactMaxCacheSize", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ArtifactMaxCacheSize", currentResult);
         currentResult.setValue("description", "<p>The maximum size of the artifact cache.</p>  <p>This cache contains the artifacts issued by the local site that are awaiting referencing by a partner.  Specify '0' to indicate that the cache is unbounded.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ArtifactTimeout")) {
         getterName = "getArtifactTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setArtifactTimeout";
         }

         currentResult = new PropertyDescriptor("ArtifactTimeout", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ArtifactTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum timeout (in seconds) of artifacts stored in the local cache.</p>  <p>This cache stores artifacts issued by the local site that are awaiting referencing by a partner. Artifacts that reach this maximum timeout duration are expired in the local cache even if no reference request has been received from the partner.  If a reference request is subsequently received from the partner, the cache behaves as if the artifact had never been generated.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AssertionEncryptionDecryptionKeyAlias")) {
         getterName = "getAssertionEncryptionDecryptionKeyAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAssertionEncryptionDecryptionKeyAlias";
         }

         currentResult = new PropertyDescriptor("AssertionEncryptionDecryptionKeyAlias", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("AssertionEncryptionDecryptionKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The keystore alias for the certificate and private key to be used to encrypt and decrypt SAML Assertions.</p>  <p> The certificate is published in the SP metadata, which will be used by an external SP to encrypt SAML assertions.</p>  <p>The private key is used to decrypt assertions. If the alias is not specified, the server's configured SSL identity alias is used by default.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AssertionEncryptionDecryptionKeyPassPhrase")) {
         getterName = "getAssertionEncryptionDecryptionKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAssertionEncryptionDecryptionKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("AssertionEncryptionDecryptionKeyPassPhrase", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("AssertionEncryptionDecryptionKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the local site's Assertion key from the keystore.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AssertionEncryptionDecryptionKeyPassPhraseEncrypted")) {
         getterName = "getAssertionEncryptionDecryptionKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAssertionEncryptionDecryptionKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("AssertionEncryptionDecryptionKeyPassPhraseEncrypted", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("AssertionEncryptionDecryptionKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted passphrase used to retrieve the local site's Assertion key from the keystore.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuthnRequestMaxCacheSize")) {
         getterName = "getAuthnRequestMaxCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthnRequestMaxCacheSize";
         }

         currentResult = new PropertyDescriptor("AuthnRequestMaxCacheSize", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("AuthnRequestMaxCacheSize", currentResult);
         currentResult.setValue("description", "<p>The maximum size of the authentication request cache.</p>  <p>This cache stores documents issued by the local Service Provider that are awaiting response from a partner Identity Provider.  </p>  <p>Specify '0' to indicate that the cache is unbounded.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuthnRequestTimeout")) {
         getterName = "getAuthnRequestTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthnRequestTimeout";
         }

         currentResult = new PropertyDescriptor("AuthnRequestTimeout", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("AuthnRequestTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum timeout (in seconds) of &lt;AuthnRequest&gt; documents stored in the local cache.</p>  <p>This cache stores documents issued by the local Service provider that are awaiting response from a partner Identity Provider. Documents that reach this maximum timeout duration are expired from the local cache even if no response is received from the Identity Provider.  If a response is subsequently returned by the Identity Provider, the cache behaves as if the &lt;AuthnRequest&gt; had never been generated.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BasicAuthPassword")) {
         getterName = "getBasicAuthPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBasicAuthPassword";
         }

         currentResult = new PropertyDescriptor("BasicAuthPassword", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("BasicAuthPassword", currentResult);
         currentResult.setValue("description", "<p>The password used to assign Basic Authentication credentials to outgoing HTTPS connections</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BasicAuthPasswordEncrypted")) {
         getterName = "getBasicAuthPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBasicAuthPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("BasicAuthPasswordEncrypted", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("BasicAuthPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted password used assign Basic Authentication credentials to outgoing HTTPS connections.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BasicAuthUsername")) {
         getterName = "getBasicAuthUsername";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBasicAuthUsername";
         }

         currentResult = new PropertyDescriptor("BasicAuthUsername", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("BasicAuthUsername", currentResult);
         currentResult.setValue("description", "The username that is used to assign Basic authentication credentials to outgoing HTTPS connections. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContactPersonCompany")) {
         getterName = "getContactPersonCompany";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setContactPersonCompany";
         }

         currentResult = new PropertyDescriptor("ContactPersonCompany", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ContactPersonCompany", currentResult);
         currentResult.setValue("description", "<p>The contact person's company name.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContactPersonEmailAddress")) {
         getterName = "getContactPersonEmailAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setContactPersonEmailAddress";
         }

         currentResult = new PropertyDescriptor("ContactPersonEmailAddress", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ContactPersonEmailAddress", currentResult);
         currentResult.setValue("description", "<p>The contact person's e-mail address.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContactPersonGivenName")) {
         getterName = "getContactPersonGivenName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setContactPersonGivenName";
         }

         currentResult = new PropertyDescriptor("ContactPersonGivenName", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ContactPersonGivenName", currentResult);
         currentResult.setValue("description", "<p>The contact person given (first) name.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContactPersonSurName")) {
         getterName = "getContactPersonSurName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setContactPersonSurName";
         }

         currentResult = new PropertyDescriptor("ContactPersonSurName", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ContactPersonSurName", currentResult);
         currentResult.setValue("description", "<p>The contact person surname (last name).</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContactPersonTelephoneNumber")) {
         getterName = "getContactPersonTelephoneNumber";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setContactPersonTelephoneNumber";
         }

         currentResult = new PropertyDescriptor("ContactPersonTelephoneNumber", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ContactPersonTelephoneNumber", currentResult);
         currentResult.setValue("description", "<p>The contact person's telephone number.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContactPersonType")) {
         getterName = "getContactPersonType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setContactPersonType";
         }

         currentResult = new PropertyDescriptor("ContactPersonType", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ContactPersonType", currentResult);
         currentResult.setValue("description", "<p>The contact person type.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataEncryptionAlgorithm")) {
         getterName = "getDataEncryptionAlgorithm";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataEncryptionAlgorithm";
         }

         currentResult = new PropertyDescriptor("DataEncryptionAlgorithm", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("DataEncryptionAlgorithm", currentResult);
         currentResult.setValue("description", "Get the preferred data encryption algorithm for SAML assertion encryption. This algorithm is used if it is found in the Service Provider's metadata or if the Service Partner's metadata does not include any data encryption algorithm. ");
         setPropertyDescriptorDefault(currentResult, "aes128-gcm");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultURL")) {
         getterName = "getDefaultURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultURL";
         }

         currentResult = new PropertyDescriptor("DefaultURL", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("DefaultURL", currentResult);
         currentResult.setValue("description", "<p>The Service Provider's default URL.</p>  <p>When an unsolicited SSO response arrives at the Service Provider without an accompanying target URL, the user (if authenticated) is redirected to this default URL.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityID")) {
         getterName = "getEntityID";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEntityID";
         }

         currentResult = new PropertyDescriptor("EntityID", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("EntityID", currentResult);
         currentResult.setValue("description", "<p>The string that uniquely identifies the local site.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ErrorPath")) {
         getterName = "getErrorPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setErrorPath";
         }

         currentResult = new PropertyDescriptor("ErrorPath", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ErrorPath", currentResult);
         currentResult.setValue("description", "Gets the Error Path URL.  Partner sites may redirect users to this URL for more information if SSO fails. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityProviderPreferredBinding")) {
         getterName = "getIdentityProviderPreferredBinding";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityProviderPreferredBinding";
         }

         currentResult = new PropertyDescriptor("IdentityProviderPreferredBinding", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("IdentityProviderPreferredBinding", currentResult);
         currentResult.setValue("description", "<p>Specifies the preferred binding type for endpoints of the Identity Provider services. Must be set to <code>None</code>, <code>HTTP/POST</code>, <code>HTTP/Artifact</code>, or <code>HTTP/Redirect</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, "None");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"None", "HTTP/POST", "HTTP/Artifact", "HTTP/Redirect"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeyEncryptionAlgorithm")) {
         getterName = "getKeyEncryptionAlgorithm";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyEncryptionAlgorithm";
         }

         currentResult = new PropertyDescriptor("KeyEncryptionAlgorithm", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("KeyEncryptionAlgorithm", currentResult);
         currentResult.setValue("description", "Get the preferred key encryption algorithm for SAML assertion encryption. This algorithm is used if it is found in the Service Provider's metadata or if the Service Partner's metadata does not include any key encryption algorithm. ");
         setPropertyDescriptorDefault(currentResult, "rsa-oaep");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginReturnQueryParameter")) {
         getterName = "getLoginReturnQueryParameter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginReturnQueryParameter";
         }

         currentResult = new PropertyDescriptor("LoginReturnQueryParameter", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("LoginReturnQueryParameter", currentResult);
         currentResult.setValue("description", "The name of the query parameter to be used for conveying the login-return URL to the login form web application. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginURL")) {
         getterName = "getLoginURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginURL";
         }

         currentResult = new PropertyDescriptor("LoginURL", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("LoginURL", currentResult);
         currentResult.setValue("description", "<p>The URL of the login form web application to which unauthenticated requests are directed.</p>  <p>By default, the login URL is <code>/saml2/idp/login</code> using Basic authentication. Typically you specify this URL if you are using a custom login web application.</p> ");
         setPropertyDescriptorDefault(currentResult, "/saml2/idp/login");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MetadataEncryptionAlgorithms")) {
         getterName = "getMetadataEncryptionAlgorithms";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMetadataEncryptionAlgorithms";
         }

         currentResult = new PropertyDescriptor("MetadataEncryptionAlgorithms", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("MetadataEncryptionAlgorithms", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("default", new String[]{"aes128-gcm", "aes192-gcm", "aes256-gcm", "aes128-cbc", "aes192-cbc", "aes256-cbc", "rsa-oaep", "rsa-oaep-mgf1p"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OrganizationName")) {
         getterName = "getOrganizationName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrganizationName";
         }

         currentResult = new PropertyDescriptor("OrganizationName", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("OrganizationName", currentResult);
         currentResult.setValue("description", "<p>The organization name.</p>  <p>This string specifies the name of the organization to which a user may refer for obtaining additional information about the local site.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OrganizationURL")) {
         getterName = "getOrganizationURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrganizationURL";
         }

         currentResult = new PropertyDescriptor("OrganizationURL", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("OrganizationURL", currentResult);
         currentResult.setValue("description", "<p>The organization URL.</p>  <p>This string specifies a location to which a user may refer for information about the local site. This string is not used by SAML 2.0 services for the actual handling or processing of messages.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublishedSiteURL")) {
         getterName = "getPublishedSiteURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPublishedSiteURL";
         }

         currentResult = new PropertyDescriptor("PublishedSiteURL", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("PublishedSiteURL", currentResult);
         currentResult.setValue("description", "<p>The published site URL.</p>  <p>When publishing SAML 2.0 metadata, this URL is used as a base URL to construct endpoint URLs for the various SAML 2.0 services.  The published site URL is also used during request processing to generate and/or parse various URLs.</p>  <p>The hostname and port portion of the URL should be the hostname and port at which the server is visible externally; this may not be the same as the hostname and port by which the server is known locally. If you are configuring SAML 2.0 services in a cluster, the hostname and port may correspond to the load balancer or proxy server that distributes client requests to servers in the cluster.</p>  <p>The remainder of the URL should be a single path component corresponding to the application context at which the SAML 2.0 services application is deployed (typically <code>/saml2</code>).</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setPublishedSiteURL(String)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSOSigningKeyAlias")) {
         getterName = "getSSOSigningKeyAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSOSigningKeyAlias";
         }

         currentResult = new PropertyDescriptor("SSOSigningKeyAlias", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("SSOSigningKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The keystore alias for the key to be used when signing documents.</p>  <p>The key is used to generate signatures on all the outgoing documents, such as authentication requests and responses. If you do not specify an alias, the server's configured SSL private key alias from the server's SSL configuration is used by default.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSOSigningKeyPassPhrase")) {
         getterName = "getSSOSigningKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSOSigningKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("SSOSigningKeyPassPhrase", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("SSOSigningKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the local site's SSO signing key from the keystore.</p>  <p>If you do not specify a keystore alias and passphrase, the server's configured private key alias and private key passphrase from the server's SSL configuration is used by default.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSOSigningKeyPassPhraseEncrypted")) {
         getterName = "getSSOSigningKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSOSigningKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("SSOSigningKeyPassPhraseEncrypted", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("SSOSigningKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted passphrase used to retrieve the local site's SSO signing key from the keystore.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceProviderPreferredBinding")) {
         getterName = "getServiceProviderPreferredBinding";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceProviderPreferredBinding";
         }

         currentResult = new PropertyDescriptor("ServiceProviderPreferredBinding", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ServiceProviderPreferredBinding", currentResult);
         currentResult.setValue("description", "Specifies the preferred binding type for endpoints of Service Provider services. Must be set to \"None\", \"POST\", or \"Artifact\". ");
         setPropertyDescriptorDefault(currentResult, "None");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"None", "HTTP/POST", "HTTP/Artifact"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransportLayerSecurityKeyAlias")) {
         getterName = "getTransportLayerSecurityKeyAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransportLayerSecurityKeyAlias";
         }

         currentResult = new PropertyDescriptor("TransportLayerSecurityKeyAlias", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("TransportLayerSecurityKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The string alias used to store and retrieve the server's private key, which is used to establish outgoing TLS/SSL connections.</p>  <p>If you do not specify an alias, the server's configured SSL private key alias from the server's SSL configuration is used for the TLS alias by default.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransportLayerSecurityKeyPassPhrase")) {
         getterName = "getTransportLayerSecurityKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransportLayerSecurityKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("TransportLayerSecurityKeyPassPhrase", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("TransportLayerSecurityKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the server's private key from the keystore.</p>  <p>If you do not specify either an alias or a passphrase, the server's configured SSL private key alias and private key passphrase from the server's SSL configuration is used for the TLS alias and passphrase by default.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransportLayerSecurityKeyPassPhraseEncrypted")) {
         getterName = "getTransportLayerSecurityKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransportLayerSecurityKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("TransportLayerSecurityKeyPassPhraseEncrypted", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("TransportLayerSecurityKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted passphrase used to retrieve the local site's TLS/SSL key from the keystore.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AssertionEncryptionEnabled")) {
         getterName = "isAssertionEncryptionEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAssertionEncryptionEnabled";
         }

         currentResult = new PropertyDescriptor("AssertionEncryptionEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("AssertionEncryptionEnabled", currentResult);
         currentResult.setValue("description", "Get assertion encryption enabled flag ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForceAuthn")) {
         getterName = "isForceAuthn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForceAuthn";
         }

         currentResult = new PropertyDescriptor("ForceAuthn", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ForceAuthn", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Identity Provider must authenticate users directly and not use a previous security context. The default is <code>false</code>. </p>  <p>Note the following:</p> <ol> <li>Setting <code>ForceAuthn</code> to <code>true</code> -- that is, enabling Force Authentication -- has no effect in WebLogic Server. SAML logout is not supported in WebLogic Server, so even if the user is already authenticated at the Identity Provider site and <code>ForceAuthn</code> is set to <code>true</code>, the user is not forced to authenticate again at the Identity Provider site.</li> <li>Setting both <code>ForceAuthn</code> and <code>IsPassive</code> to <code>true</code> -- that is, Force Authentication and Passive are enabled -- is an invalid configuration that causes WebLogic server to generate an exception and also causes the single sign-on session to fail.</li> </ol> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityProviderArtifactBindingEnabled")) {
         getterName = "isIdentityProviderArtifactBindingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityProviderArtifactBindingEnabled";
         }

         currentResult = new PropertyDescriptor("IdentityProviderArtifactBindingEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("IdentityProviderArtifactBindingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Artifact binding is enabled for the Identity Provider.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityProviderEnabled")) {
         getterName = "isIdentityProviderEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityProviderEnabled";
         }

         currentResult = new PropertyDescriptor("IdentityProviderEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("IdentityProviderEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the local site is enabled for the Identity Provider role.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityProviderPOSTBindingEnabled")) {
         getterName = "isIdentityProviderPOSTBindingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityProviderPOSTBindingEnabled";
         }

         currentResult = new PropertyDescriptor("IdentityProviderPOSTBindingEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("IdentityProviderPOSTBindingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the POST binding is enabled for the Identity Provider.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityProviderRedirectBindingEnabled")) {
         getterName = "isIdentityProviderRedirectBindingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityProviderRedirectBindingEnabled";
         }

         currentResult = new PropertyDescriptor("IdentityProviderRedirectBindingEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("IdentityProviderRedirectBindingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Redirect binding is enabled for the Identity Provider.</p> ");
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

         currentResult = new PropertyDescriptor("POSTOneUseCheckEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("POSTOneUseCheckEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the POST one-use check is enabled.</p>  <p>If set, the local site POST binding endpoints will store identifiers of all inbound documents to ensure that those documents are not presented more than once.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Passive")) {
         getterName = "isPassive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassive";
         }

         currentResult = new PropertyDescriptor("Passive", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("Passive", currentResult);
         currentResult.setValue("description", "<p>Determines whether the Identity Provider and the user must not take control of the user interface from the requester and interact with the user in a noticeable fashion. The default setting is <code>false</code>.</p>  <p>The WebLogic Server SAML 2.0 services generate an exception if Passive (<code>IsPassive</code>) is enabled and the end user is not already authenticated at the Identity Provider site.  In this situation, web single sign-on fails.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecipientCheckEnabled")) {
         getterName = "isRecipientCheckEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRecipientCheckEnabled";
         }

         currentResult = new PropertyDescriptor("RecipientCheckEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("RecipientCheckEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the recipient/destination check is enabled. When true, the recipient of the SAML Request/Response must match the URL in the HTTP Request.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReplicatedCacheEnabled")) {
         getterName = "isReplicatedCacheEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplicatedCacheEnabled";
         }

         currentResult = new PropertyDescriptor("ReplicatedCacheEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ReplicatedCacheEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the persistent cache (LDAP or RDBMS) is used for storing SAML 2.0 artifacts and authentication requests.</p>  <p>RDBMS is required by the SAML 2.0 security providers in production environments.  Use LDAP only in development environments.</p>  <p>If this is not set, artifacts and requests are saved in memory.</p>  <p>If you are configuring SAML 2.0 services for two or more WebLogic Server instances in a domain, you must enable the replicated cache individually on each server.  In addition, if you are configuring SAML 2.0 services in a cluster, each Managed Server must also be configured individually.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceProviderArtifactBindingEnabled")) {
         getterName = "isServiceProviderArtifactBindingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceProviderArtifactBindingEnabled";
         }

         currentResult = new PropertyDescriptor("ServiceProviderArtifactBindingEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ServiceProviderArtifactBindingEnabled", currentResult);
         currentResult.setValue("description", "Specifies whether the Artifact binding is enabled for the Service Provider. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceProviderEnabled")) {
         getterName = "isServiceProviderEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceProviderEnabled";
         }

         currentResult = new PropertyDescriptor("ServiceProviderEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ServiceProviderEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the local site is enabled for the Service Provider role.</p>  <p>This attribute must be enabled in order to publish the metadata file.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceProviderPOSTBindingEnabled")) {
         getterName = "isServiceProviderPOSTBindingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceProviderPOSTBindingEnabled";
         }

         currentResult = new PropertyDescriptor("ServiceProviderPOSTBindingEnabled", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("ServiceProviderPOSTBindingEnabled", currentResult);
         currentResult.setValue("description", "Specifies whether the POST binding is enabled for the Service Provider. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SignAuthnRequests")) {
         getterName = "isSignAuthnRequests";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSignAuthnRequests";
         }

         currentResult = new PropertyDescriptor("SignAuthnRequests", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("SignAuthnRequests", currentResult);
         currentResult.setValue("description", "<p>Specifies whether authentication requests must be signed.  If set, all outgoing authentication requests are signed.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WantArtifactRequestsSigned")) {
         getterName = "isWantArtifactRequestsSigned";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWantArtifactRequestsSigned";
         }

         currentResult = new PropertyDescriptor("WantArtifactRequestsSigned", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("WantArtifactRequestsSigned", currentResult);
         currentResult.setValue("description", "<p>Specifies whether incoming artifact requests must be signed. </p>  <p>This attribute can be set if the Artifact binding is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WantAssertionsSigned")) {
         getterName = "isWantAssertionsSigned";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWantAssertionsSigned";
         }

         currentResult = new PropertyDescriptor("WantAssertionsSigned", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("WantAssertionsSigned", currentResult);
         currentResult.setValue("description", "<p>Specifies whether incoming SAML 2.0 assertions must be signed.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WantAuthnRequestsSigned")) {
         getterName = "isWantAuthnRequestsSigned";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWantAuthnRequestsSigned";
         }

         currentResult = new PropertyDescriptor("WantAuthnRequestsSigned", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("WantAuthnRequestsSigned", currentResult);
         currentResult.setValue("description", "Specifies whether incoming authentication requests must be signed. If set, authentication requests that are not signed are not accepted. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WantBasicAuthClientAuthentication")) {
         getterName = "isWantBasicAuthClientAuthentication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWantBasicAuthClientAuthentication";
         }

         currentResult = new PropertyDescriptor("WantBasicAuthClientAuthentication", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("WantBasicAuthClientAuthentication", currentResult);
         currentResult.setValue("description", "<p>Specifies whether Basic Authentication client authentication is required.</p>  <p>If enabled, callers to HTTPS bindings of the local site must specify a Basic authentication header, and the username and password must be validated against the Basic authentication values of the binding client partner.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WantTransportLayerSecurityClientAuthentication")) {
         getterName = "isWantTransportLayerSecurityClientAuthentication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWantTransportLayerSecurityClientAuthentication";
         }

         currentResult = new PropertyDescriptor("WantTransportLayerSecurityClientAuthentication", SingleSignOnServicesMBean.class, getterName, setterName);
         descriptors.put("WantTransportLayerSecurityClientAuthentication", currentResult);
         currentResult.setValue("description", "<p>Specifies whether TLS/SSL client authentication is required.</p>  <p>If enabled, callers to TLS/SSL bindings of the local site must specify client authentication (two-way SSL), and the identity specified must validate against the TLS certificate of the binding client partner.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
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
