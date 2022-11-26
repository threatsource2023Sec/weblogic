package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.security.RealmMBean;

public class SecurityConfigurationMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SecurityConfigurationMBean.class;

   public SecurityConfigurationMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SecurityConfigurationMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SecurityConfigurationMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Provides domain-wide security configuration information.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SecurityConfigurationMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AdministrativeIdentityDomain")) {
         getterName = "getAdministrativeIdentityDomain";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdministrativeIdentityDomain";
         }

         currentResult = new PropertyDescriptor("AdministrativeIdentityDomain", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("AdministrativeIdentityDomain", currentResult);
         currentResult.setValue("description", "Domain's administrative identity domain. ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("BootAuthenticationMaxRetryDelay")) {
         getterName = "getBootAuthenticationMaxRetryDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBootAuthenticationMaxRetryDelay";
         }

         currentResult = new PropertyDescriptor("BootAuthenticationMaxRetryDelay", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("BootAuthenticationMaxRetryDelay", currentResult);
         currentResult.setValue("description", "The maximum length of time, in milliseconds, the boot process will wait before retrying the authentication after a login server not available exception. The boot process will use a backoff algorithm starting at 100 milliseconds increasing on each failure until the delay time reaches the MaxRetryDelay value. ");
         setPropertyDescriptorDefault(currentResult, new Long(60000L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BootAuthenticationRetryCount")) {
         getterName = "getBootAuthenticationRetryCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBootAuthenticationRetryCount";
         }

         currentResult = new PropertyDescriptor("BootAuthenticationRetryCount", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("BootAuthenticationRetryCount", currentResult);
         currentResult.setValue("description", "The maximum number of times the boot process will try to authenticate the boot user with the authentication providers. The authentication will be retried only if a failure occurs that indicates the login server is not available. ");
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CertRevoc")) {
         getterName = "getCertRevoc";
         setterName = null;
         currentResult = new PropertyDescriptor("CertRevoc", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("CertRevoc", currentResult);
         currentResult.setValue("description", "<p>Determines the domain's X509 certificate revocation checking configuration.</p>  <p>A CertRevocMBean is always associated with a domain's security configuration and cannot be changed, although CertRevocMBean attributes may be changed as documented.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CompatibilityConnectionFiltersEnabled")) {
         getterName = "getCompatibilityConnectionFiltersEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompatibilityConnectionFiltersEnabled";
         }

         currentResult = new PropertyDescriptor("CompatibilityConnectionFiltersEnabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("CompatibilityConnectionFiltersEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this WebLogic Server domain enables compatiblity with previous connection filters.</p>  <p>This attribute changes the protocols names used when filtering needs to be performed.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConnectionFilter")) {
         getterName = "getConnectionFilter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFilter";
         }

         currentResult = new PropertyDescriptor("ConnectionFilter", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("ConnectionFilter", currentResult);
         currentResult.setValue("description", "<p>The name of the Java class that implements a connection filter (that is, the <code>weblogic.security.net.ConnectionFilter</code> interface). If no class name is specified, no connection filter will be used.</p>  <p> This attribute replaces the deprecated ConnectionFilter attribute on the SecurityMBean.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConnectionFilterRules")) {
         getterName = "getConnectionFilterRules";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFilterRules";
         }

         currentResult = new PropertyDescriptor("ConnectionFilterRules", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("ConnectionFilterRules", currentResult);
         currentResult.setValue("description", "<p>The rules used by any connection filter that implements the <code>ConnectionFilterRulesListener</code> interface. When using the default implementation and when no rules are specified, all connections are accepted. The default implementation rules are in the format: <code>target localAddress localPort action protocols</code>.</p>  <p> This attribute replaces the deprecated ConnectionFilterRules attribute on the SecurityMBean.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConnectionLoggerEnabled")) {
         getterName = "getConnectionLoggerEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionLoggerEnabled";
         }

         currentResult = new PropertyDescriptor("ConnectionLoggerEnabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("ConnectionLoggerEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this WebLogic Server domain should log accepted connections.</p>  <p>This attribute can be used by a system administrator to dynamically check the incoming connections in the log file to determine if filtering needs to be performed.</p>  <p> This attribute replaces the deprecated ConnectionLoggerEnabled attribute on the SecurityMBean.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("Credential")) {
         getterName = "getCredential";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredential";
         }

         currentResult = new PropertyDescriptor("Credential", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("Credential", currentResult);
         currentResult.setValue("description", "<p>The password for the domain. In WebLogic Server version 6.0, this attribute was the password of the system user. In WebLogic Server version 7.0, this attribute can be any string. For the two domains to interoperate, the string must be the same for both domains.</p>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>UserPasswordEncrypted</code> attribute to the encrypted value.</li> </ol> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getCredentialEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CredentialEncrypted")) {
         getterName = "getCredentialEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialEncrypted";
         }

         currentResult = new PropertyDescriptor("CredentialEncrypted", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("CredentialEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted password for the domain. In WebLogic Server version 6.0, this attribute was the password of the system user. In WebLogic Server version 7.0, this attribute can be any string. For the two domains to interoperate, the string must be the same for both domains.</p>  <p>To set this attribute, pass an unencrypted string to the MBean server's <code>setAttribute</code> method. WebLogic Server encrypts the value and sets the attribute to the encrypted value.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultRealm")) {
         getterName = "getDefaultRealm";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRealm";
         }

         currentResult = new PropertyDescriptor("DefaultRealm", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("DefaultRealm", currentResult);
         currentResult.setValue("description", "Returns the default security realm or null if no realm has been selected as the default security realm. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DowngradeUntrustedPrincipals")) {
         getterName = "getDowngradeUntrustedPrincipals";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDowngradeUntrustedPrincipals";
         }

         currentResult = new PropertyDescriptor("DowngradeUntrustedPrincipals", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("DowngradeUntrustedPrincipals", currentResult);
         currentResult.setValue("description", "Whether or not to downgrade to anonymous principals that cannot be verified. This is useful for server-server communication between untrusted domains. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("EnforceStrictURLPattern")) {
         getterName = "getEnforceStrictURLPattern";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnforceStrictURLPattern";
         }

         currentResult = new PropertyDescriptor("EnforceStrictURLPattern", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("EnforceStrictURLPattern", currentResult);
         currentResult.setValue("description", "Whether or not the system should enforce strict URL pattern or not. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.2", (String)null, this.targetVersion) && !descriptors.containsKey("EnforceValidBasicAuthCredentials")) {
         getterName = "getEnforceValidBasicAuthCredentials";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnforceValidBasicAuthCredentials";
         }

         currentResult = new PropertyDescriptor("EnforceValidBasicAuthCredentials", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("EnforceValidBasicAuthCredentials", currentResult);
         currentResult.setValue("description", "Whether or not the system should allow requests with invalid Basic Authentication credentials to access unsecure resources. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.2");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0", (String)null, this.targetVersion) && !descriptors.containsKey("ExcludedDomainNames")) {
         getterName = "getExcludedDomainNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExcludedDomainNames";
         }

         currentResult = new PropertyDescriptor("ExcludedDomainNames", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("ExcludedDomainNames", currentResult);
         currentResult.setValue("description", "<p> Specifies a list of remote domains for which cross-domain check should not be applied.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0");
      }

      if (!descriptors.containsKey("JASPIC")) {
         getterName = "getJASPIC";
         setterName = null;
         currentResult = new PropertyDescriptor("JASPIC", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("JASPIC", currentResult);
         currentResult.setValue("description", "Creates a Jaspic MBean from which AuthConfigProviders can be created and configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("NodeManagerPassword")) {
         getterName = "getNodeManagerPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNodeManagerPassword";
         }

         currentResult = new PropertyDescriptor("NodeManagerPassword", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("NodeManagerPassword", currentResult);
         currentResult.setValue("description", "<p>The password that the Administration Server uses to communicate with Node Manager when starting, stopping, or restarting Managed Servers. </p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol> <li>Retrieves the value of the <code>NodeManagerPasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol> <li>Encrypts the value.</li> <li>Sets the value of the <code>NodeManagerPasswordEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>NodeManagerPassword</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, you should use <code>NodeManagerPasswordEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getNodeManagerPasswordEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("NodeManagerPasswordEncrypted")) {
         getterName = "getNodeManagerPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNodeManagerPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("NodeManagerPasswordEncrypted", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("NodeManagerPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The password that the Administration Server passes to a Node Manager when it instructs the Node Manager to start, stop, or restart Managed Servers. </p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         setPropertyDescriptorDefault(currentResult, "".getBytes());
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("NodeManagerUsername")) {
         getterName = "getNodeManagerUsername";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNodeManagerUsername";
         }

         currentResult = new PropertyDescriptor("NodeManagerUsername", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("NodeManagerUsername", currentResult);
         currentResult.setValue("description", "<p>The user name that the Administration Server uses to communicate with Node Manager when starting, stopping, or restarting Managed Servers. </p> ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("NonceTimeoutSeconds")) {
         getterName = "getNonceTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNonceTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("NonceTimeoutSeconds", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("NonceTimeoutSeconds", currentResult);
         currentResult.setValue("description", "Returns the value of the nonce timeout in seconds. ");
         setPropertyDescriptorDefault(currentResult, new Integer(120));
         currentResult.setValue("legalMin", new Integer(15));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("RealmBootStrapVersion")) {
         getterName = "getRealmBootStrapVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRealmBootStrapVersion";
         }

         currentResult = new PropertyDescriptor("RealmBootStrapVersion", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("RealmBootStrapVersion", currentResult);
         currentResult.setValue("description", "<p>Indicates which version of the default security realm MBeans should be loaded if none exist. The value is set to current version on initial read if it has not already been set.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"unknown", "1"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "true");
      }

      if (!descriptors.containsKey("Realms")) {
         getterName = "getRealms";
         setterName = null;
         currentResult = new PropertyDescriptor("Realms", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("Realms", currentResult);
         currentResult.setValue("description", "Returns all the realms in the domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyRealm");
         currentResult.setValue("creator", "createRealm");
         currentResult.setValue("creator", "createRealm");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SecureMode")) {
         getterName = "getSecureMode";
         setterName = null;
         currentResult = new PropertyDescriptor("SecureMode", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("SecureMode", currentResult);
         currentResult.setValue("description", "Returns the SecureMode MBean that contains attributes that control the behavior of Secure Mode. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("WebAppFilesCaseInsensitive")) {
         getterName = "getWebAppFilesCaseInsensitive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWebAppFilesCaseInsensitive";
         }

         currentResult = new PropertyDescriptor("WebAppFilesCaseInsensitive", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("WebAppFilesCaseInsensitive", currentResult);
         currentResult.setValue("description", "<p>This property defines the case sensitive URL-pattern matching behavior for security constraints, servlets, filters, virtual-hosts, and so on, in the Web application container and external security policies.  <b>Note:</b> This is a Windows-only flag that is provided for backward compatibility when upgrading from pre-9.0 versions of WebLogic Server. On Unix platforms, setting this value to <code>true</code> causes undesired behavior and is not supported.  When the value is set to <code>os</code>, the pattern matching will be case- sensitive on all platforms except the Windows file system.  Note that on non-Windows file systems, WebLogic Server does not enforce case sensitivity and relies on the file system for optimization. As a result, if you have a Windows Samba mount from Unix or Mac OS that has been installed in case-insensitive mode, there is a chance of a security risk. If so, specify case-insensitive lookups by setting this attribute to <code>true</code>.  Note also that this property is used to preserve backward compatibility on Windows file systems only. In prior releases, WebLogic Server was case- insensitive on Windows. As of WebLogic Server 9.0, URL-pattern matching is strictly enforced.  During the upgrade of older domains, the value of this parameter is explicitly set to <code>os</code> by the upgrade plug-in to preserve backward compatibility.</p> ");
         setPropertyDescriptorDefault(currentResult, "false");
         currentResult.setValue("legalValues", new Object[]{"os", "true", "false"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AnonymousAdminLookupEnabled")) {
         getterName = "isAnonymousAdminLookupEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAnonymousAdminLookupEnabled";
         }

         currentResult = new PropertyDescriptor("AnonymousAdminLookupEnabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("AnonymousAdminLookupEnabled", currentResult);
         currentResult.setValue("description", "<p>Returns true if anonymous JNDI access for Admin MBean home is permitted. This is overridden by the Java property <code>-Dweblogic.management.anonymousAdminLookupEnabled</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClearTextCredentialAccessEnabled")) {
         getterName = "isClearTextCredentialAccessEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClearTextCredentialAccessEnabled";
         }

         currentResult = new PropertyDescriptor("ClearTextCredentialAccessEnabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("ClearTextCredentialAccessEnabled", currentResult);
         currentResult.setValue("description", "<p>Returns true if allow access to credential in clear text. This can be overridden by the system property <code>-Dweblogic.management.clearTextCredentialAccessEnabled</code></p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.2.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConsoleFullDelegationEnabled")) {
         getterName = "isConsoleFullDelegationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsoleFullDelegationEnabled";
         }

         currentResult = new PropertyDescriptor("ConsoleFullDelegationEnabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("ConsoleFullDelegationEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the console is enabled for fully delegate authorization.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.2.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("CredentialGenerated")) {
         getterName = "isCredentialGenerated";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialGenerated";
         }

         currentResult = new PropertyDescriptor("CredentialGenerated", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("CredentialGenerated", currentResult);
         currentResult.setValue("description", "DO NOT USE THIS METHOD..... This method is only here for backward compatibility with old config.xml files which have been persisted and now contain it. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "true");
      }

      if (!descriptors.containsKey("CrossDomainSecurityEnabled")) {
         getterName = "isCrossDomainSecurityEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrossDomainSecurityEnabled";
         }

         currentResult = new PropertyDescriptor("CrossDomainSecurityEnabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("CrossDomainSecurityEnabled", currentResult);
         currentResult.setValue("description", "<p> Indicates whether or not cross-domain security is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("IdentityDomainAwareProvidersRequired")) {
         getterName = "isIdentityDomainAwareProvidersRequired";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityDomainAwareProvidersRequired";
         }

         currentResult = new PropertyDescriptor("IdentityDomainAwareProvidersRequired", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("IdentityDomainAwareProvidersRequired", currentResult);
         currentResult.setValue("description", "Returns true if all role mapping, authorization, credential mapping, and audit providers configured in the domain must support the IdentityDomainAwareProviderMBean interface's administrative identity domain. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("IdentityDomainDefaultEnabled")) {
         getterName = "isIdentityDomainDefaultEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityDomainDefaultEnabled";
         }

         currentResult = new PropertyDescriptor("IdentityDomainDefaultEnabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("IdentityDomainDefaultEnabled", currentResult);
         currentResult.setValue("description", "Returns true if identity domain values should be defaulted for the Administrative Identity Domain, Partition Primary Identity Domain, and Default Authenticator Identity Domain attributes. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("PrincipalEqualsCaseInsensitive")) {
         getterName = "isPrincipalEqualsCaseInsensitive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrincipalEqualsCaseInsensitive";
         }

         currentResult = new PropertyDescriptor("PrincipalEqualsCaseInsensitive", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("PrincipalEqualsCaseInsensitive", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the WebLogic Server principal name is compared using a case insensitive match when the equals method for the principal object is performed.</p>  <p>If this attribute is enabled, matches are case insensitive.</p>  <p><b>Note:</b> Note that principal comparison is not used by the WebLogic Security Service to determine access to protected resources. This attribute is intended for use with JAAS authorization, which may require case insensitive principal matching behavior.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrincipalEqualsCompareDnAndGuid")) {
         getterName = "isPrincipalEqualsCompareDnAndGuid";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrincipalEqualsCompareDnAndGuid";
         }

         currentResult = new PropertyDescriptor("PrincipalEqualsCompareDnAndGuid", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("PrincipalEqualsCompareDnAndGuid", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the GUID and DN data in a WebLogic Server principal object are used when the equals method of that object is invoked. </p>  <p>If enabled, the GUID and DN data (if included among the attributes in a WebLogic Server principal object) and the principal name are compared when this method is invoked.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("RemoteAnonymousJNDIEnabled")) {
         getterName = "isRemoteAnonymousJNDIEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteAnonymousJNDIEnabled";
         }

         currentResult = new PropertyDescriptor("RemoteAnonymousJNDIEnabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("RemoteAnonymousJNDIEnabled", currentResult);
         currentResult.setValue("description", "<p>Returns true if remote anonymous JNDI access is permitted for list and modify operations.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("14.1.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RemoteAnonymousRMIIIOPEnabled")) {
         getterName = "isRemoteAnonymousRMIIIOPEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteAnonymousRMIIIOPEnabled";
         }

         currentResult = new PropertyDescriptor("RemoteAnonymousRMIIIOPEnabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("RemoteAnonymousRMIIIOPEnabled", currentResult);
         currentResult.setValue("description", "<p>Returns true if remote anonymous RMI access via IIOP is permitted. If remote anonymous RMI access is not allowed, then client requests that do not specify a username / password may fail. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "14.1.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("14.1.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RemoteAnonymousRMIT3Enabled")) {
         getterName = "isRemoteAnonymousRMIT3Enabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteAnonymousRMIT3Enabled";
         }

         currentResult = new PropertyDescriptor("RemoteAnonymousRMIT3Enabled", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("RemoteAnonymousRMIT3Enabled", currentResult);
         currentResult.setValue("description", "<p>Returns true if remote anonymous RMI access via T3 is permitted. If remote anonymous RMI access is not allowed, then client requests that do not specify a username / password may fail. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "14.1.1.0.0");
      }

      if (!descriptors.containsKey("UseKSSForDemo")) {
         getterName = "isUseKSSForDemo";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseKSSForDemo";
         }

         currentResult = new PropertyDescriptor("UseKSSForDemo", SecurityConfigurationMBean.class, getterName, setterName);
         descriptors.put("UseKSSForDemo", currentResult);
         currentResult.setValue("description", "<p>Determines whether the Demo Identity and Demo Trust key stores should be obtained from the Oracle Key Store Service (KSS).</p>  <p>If enabled, Weblogic Server will request the Demo Identity and Domain Trust key stores from KSS. Subsequent to installation however, the KSS Demo key stores may have been manipulated such that appropriate Demo certificates or keys are not available.</p>  <p>Please verify the following KSS Demo Identity keystore has an X.509 private key and corresponding public identity certificate signed by the Demo Certificate Authority (CA):</p> <dl> <dt>KSS Stripe</dt> <dd>system</dd> <dt>KSS Key Store</dt> <dd>demoidentity</dd> <dt>KSS Private Key Alias</dt> <dd>DemoIdentity</dd> </dl> <p>Please verify the following KSS Domain Trust keystore has a trusted Demo Certificate Authority X.509 certificate:</p> <dl> <dt>KSS Stripe</dt> <dd>system</dd> <dt>KSS Key Store</dt> <dd>trust</dd> </dl> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setUseKSSForDemo")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SecurityConfigurationMBean.class.getMethod("createRealm", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- The name of this realm, for example, <code>myrealm</code> ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Realms");
      }

      mth = SecurityConfigurationMBean.class.getMethod("createRealm");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a realm.  among all realms in the domain.  If the name can be converted to a JMX object name, then it is used as the provider's JMX object name.  The encouraged convention is: &quot;Security:Name&#61;realmDisplayName&quot;.  For example: &quot;Security:Name&#61;myrealm&quot;. that will be displayed in the console). ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Realms");
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
      }

      mth = SecurityConfigurationMBean.class.getMethod("destroyRealm", RealmMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("realm", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys a realm.  This does not destroy its providers or its user lockout manager. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Realms");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = SecurityConfigurationMBean.class.getMethod("addTag", String.class);
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
         mth = SecurityConfigurationMBean.class.getMethod("removeTag", String.class);
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
      Method mth = SecurityConfigurationMBean.class.getMethod("lookupRealm", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("realm", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a realm given it's name.  The name is often its JMX object name (e.g. Security:Name=myrealm) ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Realms");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SecurityConfigurationMBean.class.getMethod("findRealms");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0  Replaced by {@link #getRealms} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns all the realms in the domain. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = SecurityConfigurationMBean.class.getMethod("findDefaultRealm");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0  Replaced by {@link #getDefaultRealm} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds the default security realm. Returns null if a default security realm is not defined. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = SecurityConfigurationMBean.class.getMethod("findRealm", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("realmDisplayName", "A String containing the realm's display name. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0  Replaced by {@link #lookupRealm} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a realm by name (that is, by the display name of the realm). Returns null no realm with that name has been defined. Throws a configuration error if there are multiple matches. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = SecurityConfigurationMBean.class.getMethod("freezeCurrentValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = SecurityConfigurationMBean.class.getMethod("restoreDefaultValue", String.class);
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

      mth = SecurityConfigurationMBean.class.getMethod("generateCredential");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Generates a new encrypted byte array which can be use when calling #setCredentialEncrypted</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
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
