package weblogic.management.security;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.security.audit.AuditorMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.PasswordValidatorMBean;
import weblogic.management.security.authorization.AuthorizerMBean;
import weblogic.management.security.authorization.RoleMapperMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.management.security.pk.CertPathProviderMBean;

public class RealmMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = RealmMBean.class;

   public RealmMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RealmMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.RealmMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.security");
      String description = (new String("<p>The MBean that represents configuration attributes for the security realm.</p>  <p>A security realm contains a set of security configuration settings, including the list of security providers to use (for example, for authentication and authorization).</p>  <p>Code using security can either use the default security realm for the domain or refer to a particular security realm by name (by using the JMX display name of the security realm).</p>  <p>One security realm in the WebLogic domain must have the <code>DefaultRealm</code> attribute set to true. The security realm with the <code>DefaultRealm</code> attribute set to true is used as the default security realm for the WebLogic domain. Note that other available security realms must have the <code>DefaultRealm</code> attribute set to false.</p>  <p>When WebLogic Server boots, it locates and uses the default security realm. The security realm is considered active since it is used when WebLogic Server runs. Any security realm that is not used when WebLogic Server runs is considered inactive. All active security realms must be configured before WebLogic Server is boots.</p>  <p>Since security providers are scoped by realm, the <code>Realm</code> attribute on a security provider must be set to the realm that uses the provider.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.RealmMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Adjudicator")) {
         getterName = "getAdjudicator";
         setterName = null;
         currentResult = new PropertyDescriptor("Adjudicator", RealmMBean.class, getterName, setterName);
         descriptors.put("Adjudicator", currentResult);
         currentResult.setValue("description", "Returns the Adjudication provider for this security realm. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAdjudicator");
         currentResult.setValue("creator", "createAdjudicator");
         currentResult.setValue("creator", "createAdjudicator");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("AdjudicatorTypes")) {
         getterName = "getAdjudicatorTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("AdjudicatorTypes", RealmMBean.class, getterName, setterName);
         descriptors.put("AdjudicatorTypes", currentResult);
         currentResult.setValue("description", "Returns the types of Adjudication providers that may be created in this security realm, for example, <code>weblogic.security.providers.authorization.DefaultAdjudicator</code>. Use this method to find the available types to pass to <code>createAdjudicator</code> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuditorTypes")) {
         getterName = "getAuditorTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("AuditorTypes", RealmMBean.class, getterName, setterName);
         descriptors.put("AuditorTypes", currentResult);
         currentResult.setValue("description", "Returns the types of Auditing providers that may be created in this security realm, for example, <code>weblogic.security.providers.audit.DefaultAuditor</code>. Use this method to find the available types to pass to <code>createAuditor</code> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Auditors")) {
         getterName = "getAuditors";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuditors";
         }

         currentResult = new PropertyDescriptor("Auditors", RealmMBean.class, getterName, setterName);
         descriptors.put("Auditors", currentResult);
         currentResult.setValue("description", "Returns the Auditing providers for this security realm (in invocation order). ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAuditor");
         currentResult.setValue("creator", "createAuditor");
         currentResult.setValue("creator", "createAuditor");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("9.2.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AuthMethods")) {
         getterName = "getAuthMethods";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthMethods";
         }

         currentResult = new PropertyDescriptor("AuthMethods", RealmMBean.class, getterName, setterName);
         descriptors.put("AuthMethods", currentResult);
         currentResult.setValue("description", "Returns a comma separated string of authentication methods that should be used when the Web application specifies \"REALM\" as its auth-method. The authentication methods will be applied in order in which they appear in the list. ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.2.0.0");
      }

      if (!descriptors.containsKey("AuthenticationProviderTypes")) {
         getterName = "getAuthenticationProviderTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("AuthenticationProviderTypes", RealmMBean.class, getterName, setterName);
         descriptors.put("AuthenticationProviderTypes", currentResult);
         currentResult.setValue("description", "Returns the types of Authentication providers that may be created in this security realm, for example, <code>weblogic.security.providers.authentication.DefaultAuthenticator</code>. Use this method to find the available types to pass to <code>createAuthenticationProvider</code> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuthenticationProviders")) {
         getterName = "getAuthenticationProviders";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthenticationProviders";
         }

         currentResult = new PropertyDescriptor("AuthenticationProviders", RealmMBean.class, getterName, setterName);
         descriptors.put("AuthenticationProviders", currentResult);
         currentResult.setValue("description", "Returns the Authentication providers for this security realm (in invocation order). ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAuthenticationProvider");
         currentResult.setValue("creator", "createAuthenticationProvider");
         currentResult.setValue("creator", "createAuthenticationProvider");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("AuthorizerTypes")) {
         getterName = "getAuthorizerTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("AuthorizerTypes", RealmMBean.class, getterName, setterName);
         descriptors.put("AuthorizerTypes", currentResult);
         currentResult.setValue("description", "Returns the types of Authorization providers that may be created in this security realm, for example, <code>weblogic.security.providers.authorization.DefaultAuthorizer</code>. Use this method to find the available types to pass to <code>createAuthorizer</code> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Authorizers")) {
         getterName = "getAuthorizers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAuthorizers";
         }

         currentResult = new PropertyDescriptor("Authorizers", RealmMBean.class, getterName, setterName);
         descriptors.put("Authorizers", currentResult);
         currentResult.setValue("description", "Returns the Authorization providers for this security realm (in invocation order). ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createAuthorizer");
         currentResult.setValue("creator", "createAuthorizer");
         currentResult.setValue("destroyer", "destroyAuthorizer");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("CertPathBuilder")) {
         getterName = "getCertPathBuilder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCertPathBuilder";
         }

         currentResult = new PropertyDescriptor("CertPathBuilder", RealmMBean.class, getterName, setterName);
         descriptors.put("CertPathBuilder", currentResult);
         currentResult.setValue("description", "Returns the CertPath Builder provider in this security realm that will be used by the security system to build certification paths.  Returns null if none has been selected.  The provider will be one of this security realm's <code>CertPathProviders</code>. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("CertPathProviderTypes")) {
         getterName = "getCertPathProviderTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("CertPathProviderTypes", RealmMBean.class, getterName, setterName);
         descriptors.put("CertPathProviderTypes", currentResult);
         currentResult.setValue("description", "Returns the types of Certification Path providers that may be created in this security realm, for example, <code>weblogic.security.providers.pk.WebLogicCertPathProvider</code>. Use this method to find the available types to pass to <code>createCertPathProvider</code> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CertPathProviders")) {
         getterName = "getCertPathProviders";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCertPathProviders";
         }

         currentResult = new PropertyDescriptor("CertPathProviders", RealmMBean.class, getterName, setterName);
         descriptors.put("CertPathProviders", currentResult);
         currentResult.setValue("description", "Returns the Certification Path providers for this security realm (in invocation order). ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCertPathProvider");
         currentResult.setValue("creator", "createCertPathProvider");
         currentResult.setValue("creator", "createCertPathProvider");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("CredentialMapperTypes")) {
         getterName = "getCredentialMapperTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("CredentialMapperTypes", RealmMBean.class, getterName, setterName);
         descriptors.put("CredentialMapperTypes", currentResult);
         currentResult.setValue("description", "Returns the types of Credential Mapping providers that may be created in this security realm, for example, <code>weblogic.security.providers.credentials.DefaultCredentialMapper</code>. Use this method to find the available types to pass to <code>createCredentialMapper</code> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CredentialMappers")) {
         getterName = "getCredentialMappers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialMappers";
         }

         currentResult = new PropertyDescriptor("CredentialMappers", RealmMBean.class, getterName, setterName);
         descriptors.put("CredentialMappers", currentResult);
         currentResult.setValue("description", "Returns the Credential Mapping providers for this security realm (in invocation order). ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCredentialMapper");
         currentResult.setValue("creator", "createCredentialMapper");
         currentResult.setValue("destroyer", "destroyCredentialMapper");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3", (String)null, this.targetVersion) && !descriptors.containsKey("DeployableProviderSynchronizationTimeout")) {
         getterName = "getDeployableProviderSynchronizationTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeployableProviderSynchronizationTimeout";
         }

         currentResult = new PropertyDescriptor("DeployableProviderSynchronizationTimeout", RealmMBean.class, getterName, setterName);
         descriptors.put("DeployableProviderSynchronizationTimeout", currentResult);
         currentResult.setValue("description", "Returns the timeout value, in milliseconds, for the deployable security provider synchronization operation. This value is only used if <code>DeployableProviderSynchronizationEnabled</code> is set to <code>true</code> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60000));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3");
      }

      if (BeanInfoHelper.isVersionCompliant("12.3.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("IdentityAssertionCacheTTL")) {
         getterName = "getIdentityAssertionCacheTTL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityAssertionCacheTTL";
         }

         currentResult = new PropertyDescriptor("IdentityAssertionCacheTTL", RealmMBean.class, getterName, setterName);
         descriptors.put("IdentityAssertionCacheTTL", currentResult);
         currentResult.setValue("description", "Returns the time-to-live (TTL), in seconds, of the Identity Assertion cache. This value is used only if <code>IdentityAssertionCacheEnabled</code> is set to <code>true</code>. ");
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.3.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.3.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("IdentityAssertionDoNotCacheContextElements")) {
         getterName = "getIdentityAssertionDoNotCacheContextElements";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityAssertionDoNotCacheContextElements";
         }

         currentResult = new PropertyDescriptor("IdentityAssertionDoNotCacheContextElements", RealmMBean.class, getterName, setterName);
         descriptors.put("IdentityAssertionDoNotCacheContextElements", currentResult);
         currentResult.setValue("description", "Returns the names of the ContextElements that are not stored in the Identity Assertion cache because these elements are present in the ContextHandler of the requests. This value is used only if <code>IdentityAssertionCacheEnabled</code> is set to <code>true</code>. ");
         setPropertyDescriptorDefault(currentResult, BeanInfoHelper.stringArray(new String[0]));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.3.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("IdentityAssertionHeaderNamePrecedence")) {
         getterName = "getIdentityAssertionHeaderNamePrecedence";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityAssertionHeaderNamePrecedence";
         }

         currentResult = new PropertyDescriptor("IdentityAssertionHeaderNamePrecedence", RealmMBean.class, getterName, setterName);
         descriptors.put("IdentityAssertionHeaderNamePrecedence", currentResult);
         currentResult.setValue("description", "<p>Obtain an ordered list of token type names used for Identity Assertion with HTTP request headers.</p>  <p>The list determines the precedence order when multiple HTTP headers are present in an HTTP request based on the list of active token types maintained on the configured Authentication providers.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagementIdentityDomain")) {
         getterName = "getManagementIdentityDomain";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setManagementIdentityDomain";
         }

         currentResult = new PropertyDescriptor("ManagementIdentityDomain", RealmMBean.class, getterName, setterName);
         descriptors.put("ManagementIdentityDomain", currentResult);
         currentResult.setValue("description", "<p>Sets the Management Identity Domain value for the realm.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("MaxWebLogicPrincipalsInCache")) {
         getterName = "getMaxWebLogicPrincipalsInCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxWebLogicPrincipalsInCache";
         }

         currentResult = new PropertyDescriptor("MaxWebLogicPrincipalsInCache", RealmMBean.class, getterName, setterName);
         descriptors.put("MaxWebLogicPrincipalsInCache", currentResult);
         currentResult.setValue("description", "Returns the maximum size of the LRU cache for holding WebLogic Principal signatures. This value is only used if <code>EnableWebLogicPrincipalValidatorCache</code> is set to <code>true</code> ");
         setPropertyDescriptorDefault(currentResult, new Integer(500));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", RealmMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name of this configuration. WebLogic Server uses an MBean to implement and persist the configuration. ");
         setPropertyDescriptorDefault(currentResult, "Realm");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("legal", "");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0", (String)null, this.targetVersion) && !descriptors.containsKey("PasswordValidatorTypes")) {
         getterName = "getPasswordValidatorTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("PasswordValidatorTypes", RealmMBean.class, getterName, setterName);
         descriptors.put("PasswordValidatorTypes", currentResult);
         currentResult.setValue("description", "Returns the types of Password Validator providers that may be created in this security realm, for example, <code>com.bea.security.providers.authentication.passwordvalidator.SystemPasswordValidator</code>. Use this method to find the available types to pass to <code>createPasswordValidator</code> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0", (String)null, this.targetVersion) && !descriptors.containsKey("PasswordValidators")) {
         getterName = "getPasswordValidators";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPasswordValidators";
         }

         currentResult = new PropertyDescriptor("PasswordValidators", RealmMBean.class, getterName, setterName);
         descriptors.put("PasswordValidators", currentResult);
         currentResult.setValue("description", "Returns the Password Validator providers for this security realm (in invocation order). ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPasswordValidator");
         currentResult.setValue("creator", "createPasswordValidator");
         currentResult.setValue("destroyer", "destroyPasswordValidator");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("RDBMSSecurityStore")) {
         getterName = "getRDBMSSecurityStore";
         setterName = null;
         currentResult = new PropertyDescriptor("RDBMSSecurityStore", RealmMBean.class, getterName, setterName);
         descriptors.put("RDBMSSecurityStore", currentResult);
         currentResult.setValue("description", "Returns RDBMSSecurityStoreMBean for this realm, which is a singleton MBean describing RDBMS security store configuration. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#createRDBMSSecurityStore")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyRDBMSSecurityStore");
         currentResult.setValue("creator", "createRDBMSSecurityStore");
         currentResult.setValue("creator", "createRDBMSSecurityStore");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RetireTimeoutSeconds")) {
         getterName = "getRetireTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetireTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("RetireTimeoutSeconds", RealmMBean.class, getterName, setterName);
         descriptors.put("RetireTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the retire timeout for a realm that is restarted. The old realm will be shutdown after the specified timeout period has elapsed.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("RoleMapperTypes")) {
         getterName = "getRoleMapperTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("RoleMapperTypes", RealmMBean.class, getterName, setterName);
         descriptors.put("RoleMapperTypes", currentResult);
         currentResult.setValue("description", "Returns the types of Role Mapping providers that may be created in this security realm, for example, <code>weblogic.security.providers.authorization.DefaultRoleMapper</code>. Use this method to find the available types to pass to <code>createRoleMapper</code> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RoleMappers")) {
         getterName = "getRoleMappers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRoleMappers";
         }

         currentResult = new PropertyDescriptor("RoleMappers", RealmMBean.class, getterName, setterName);
         descriptors.put("RoleMappers", currentResult);
         currentResult.setValue("description", "Returns the Role Mapping providers for this security realm (in invocation order). ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createRoleMapper");
         currentResult.setValue("creator", "createRoleMapper");
         currentResult.setValue("destroyer", "destroyRoleMapper");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("SecurityDDModel")) {
         getterName = "getSecurityDDModel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityDDModel";
         }

         currentResult = new PropertyDescriptor("SecurityDDModel", RealmMBean.class, getterName, setterName);
         descriptors.put("SecurityDDModel", currentResult);
         currentResult.setValue("description", "<p>Specifies the default security model for Web applications or EJBs that are secured by this security realm. You can override this default during deployment.</p>  <p><b>Note:</b> If you deploy a module by modifying the domain's <code>config.xml</code> file and restarting the server, and if you do not specify a security model value for the module in <code>config.xml</code>, the module is secured with the default value of the <code>AppDeploymentMBean SecurityDDModel</code>  attribute (see {@link weblogic.management.configuration.AppDeploymentMBean#getSecurityDDModel() getSecurityDDModel}).</p>  <p>Choose one of these security models:</p> <ul> <li><code>Deployment Descriptors Only (DDOnly)</code> <ul> <li>For EJBs and URL patterns, this model uses only the roles and policies in the J2EE deployment descriptors (DD); the Administration Console allows only read access for this data. With this model, EJBs and URL patterns are not protected by roles and policies of a broader scope (such as a policy scoped to an entire Web application). If an EJB or URL pattern is not protected by a role or policy in the DD, then it is unprotected: anyone can access it. </li>  <li>For application-scoped <i>roles</i> in an EAR, this model uses only the roles defined in the WebLogic Server DD; the Administration Console allows only read access for this data. If the WebLogic Server DD does not define roles, then there will be no such scoped roles defined for this EAR.</li>  <li>For all other types of resources, you can use the Administration Console to create roles or policies. For example, with this model, you can use the Administration Console to create application-scoped <i>policies</i> for an EAR.</li>   <li>Applies for the life of the deployment. If you want to use a different model, you must delete the deployment and reinstall it.</li> </ul> </li>  <li><code>Customize Roles Only (CustomRoles)</code> <ul> <li>For EJBs and URL patterns, this model uses only the <i>policies</i> in the J2EE deployment descriptors (DD). EJBs and URL patterns are not protected by policies of a broader scope (such as a policy scoped to an entire Web application). This model ignores any <i>roles</i> defined in the DDs; an administrator completes the role mappings using the Administration Console.</li>  <li>For all other types of resources, you can use the Administration Console to create roles or policies. For example, with this model, you can use the Administration Console to create application-scoped policies or roles for an EAR.</li>  <li>Applies for the life of the deployment. If you want to use a different model, you must delete the deployment and reinstall it.</li> </ul> </li>  <li><code>Customize Roles and Policies (CustomRolesAndPolicies)</code> <ul> <li>Ignores any roles and policies defined in deployment descriptors. An administrator uses the Administration Console to secure the resources.</li>  <li>Performs security checks for <b>all</b> URLs or EJB methods in the module.</li>  <li>Applies for the life of the deployment. If you want to use a different model, you must delete the deployment and reinstall it.</li> </ul> </li>  <li><code>Advanced (Advanced)</code> <p>You configure how this model behaves by setting values for the following options:</p> <ul> <li><code>When Deploying Web Applications or EJBs</code> <p><b>Note:</b> When using the WebLogic Scripting Tool or JMX APIs, there is no single MBean attribute for this setting. Instead, you must set the values for the <code>DeployPolicyIgnored</code> and <code>DeployRoleIgnored</code> attributes of <code>RealmMBean</code>.</p> </li>  <li><code>Check Roles and Policies (FullyDelegateAuthorization)</code></li>  <li><code>Combined Role Mapping Enabled (CombinedRoleMappingEnabled)</code></li> </ul> <p>You can change the configuration of this model. Any changes immediately apply to all modules that use the Advanced model. For example, you can specify that all modules using this model will copy roles and policies from their deployment descriptors into the appropriate provider databases upon deployment. After you deploy all of your modules, you can change this behavior to ignore roles and policies in deployment descriptors so that when you redeploy modules they will not re-copy roles and policies.</p>  <p><b>Note:</b> Prior to WebLogic Server version 9.0 the Advanced model was the only security model available. Use this model if you want to continue to secure EJBs and Web Applications as in releases prior to 9.0.</p> </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isDeployPolicyIgnored()"), BeanInfoHelper.encodeEntities("#isDeployRoleIgnored()"), BeanInfoHelper.encodeEntities("#isFullyDelegateAuthorization()"), BeanInfoHelper.encodeEntities("#isCombinedRoleMappingEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "DDOnly");
         currentResult.setValue("legalValues", new Object[]{"DDOnly", "CustomRoles", "CustomRolesAndPolicies", "Advanced"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserLockoutManager")) {
         getterName = "getUserLockoutManager";
         setterName = null;
         currentResult = new PropertyDescriptor("UserLockoutManager", RealmMBean.class, getterName, setterName);
         descriptors.put("UserLockoutManager", currentResult);
         currentResult.setValue("description", "Returns the User Lockout Manager for this security realm. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AutoRestartOnNonDynamicChanges")) {
         getterName = "isAutoRestartOnNonDynamicChanges";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoRestartOnNonDynamicChanges";
         }

         currentResult = new PropertyDescriptor("AutoRestartOnNonDynamicChanges", RealmMBean.class, getterName, setterName);
         descriptors.put("AutoRestartOnNonDynamicChanges", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Realm will be auto-restarted if non-dynamic changes are made to the realm or providers within the realm.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CombinedRoleMappingEnabled")) {
         getterName = "isCombinedRoleMappingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCombinedRoleMappingEnabled";
         }

         currentResult = new PropertyDescriptor("CombinedRoleMappingEnabled", RealmMBean.class, getterName, setterName);
         descriptors.put("CombinedRoleMappingEnabled", currentResult);
         currentResult.setValue("description", "<p>Determines how the role mappings in the Enterprise Application, Web application, and EJB containers interact. This setting is valid only for Web applications and EJBs that use the Advanced security model and that initialize roles from deployment descriptors.</p> <p>When enabled:</p>  <ul> <li>Application role mappings are combined with EJB and Web application mappings so that all principal mappings are included. The Security Service combines the role mappings with a logical <code>OR</code> operator.</li>  <li>If one or more policies in the <code>web.xml</code> file specify a role for which no mapping exists in the <code>weblogic.xml</code> file, the Web application container creates an empty map for the undefined role (that is, the role is explicitly defined as containing no principal). Therefore, no one can access URL patterns that are secured by such policies.</li>  <li>If one or more policies in the <code>ejb-jar.xml</code> file specify a role for which no mapping exists in the <code>weblogic-ejb-jar.xml</code> file, the EJB container creates an empty map for the undefined role (that is, the role is explicitly defined as containing no principal). Therefore, no one can access methods that are secured by such policies.</li> </ul>  <p>When disabled:</p>  <ul> <li>Role mappings for each container are exclusive to other containers unless defined by the <code>&lt;externally-defined&gt;</code> descriptor element.</li>  <li><p>If one or more policies in the <code>web.xml</code> file specify a role for which no role mapping exists in the <code>weblogic.xml</code> file, the Web application container assumes that the undefined role is the name of a principal. It therefore maps the assumed principal to the role name. For example, if the <code>web.xml</code> file contains the following stanza in one of its policies:</p>  <p><code>&lt;auth-constraint&gt;<br> &lt;role-name&gt;PrivilegedUser&lt;/role-name&gt;<br> &lt;/auth-constraint&gt;</code></p>  <p>but, if the <code>weblogic.xml</code> file has no role mapping for <code>PrivilegedUser</code>, then the Web application container creates an in-memory mapping that is equivalent to the following stanza:</p>  <p><code>&lt;security-role-assignment&gt;<br> &lt;role-name&gt;PrivilegedUser&lt;/role-name&gt;<br> &lt;principal-name&gt;PrivilegedUser&lt;/principal-name&gt;<br> &lt;/security-role-assignment&gt;</code></p> </li>  <li>Role mappings for EJB methods must be defined in the <code>weblogic-ejb-jar.xml</code> file. Role mappings defined in the other containers are not used unless defined by the <code>&lt;externally-defined&gt;</code> descriptor element.</li> </ul>  <dl> <dt>Note:</dt>  <dd>For all applications previously deployed in version 8.1 and upgraded to version 9.x, the combining role mapping is disabled by default.</dd> </dl> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("DefaultRealm")) {
         getterName = "isDefaultRealm";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultRealm";
         }

         currentResult = new PropertyDescriptor("DefaultRealm", RealmMBean.class, getterName, setterName);
         descriptors.put("DefaultRealm", currentResult);
         currentResult.setValue("description", "Returns whether this security realm is the Default realm for the WebLogic domain. Deprecated in this release of WebLogic Server and replaced by <code>weblogic.management.configuration.SecurityConfigurationMBean.getDefaultRealm</code>. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.configuration.SecurityConfigurationMBean#getDefaultRealm()} ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DelegateMBeanAuthorization")) {
         getterName = "isDelegateMBeanAuthorization";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDelegateMBeanAuthorization";
         }

         currentResult = new PropertyDescriptor("DelegateMBeanAuthorization", RealmMBean.class, getterName, setterName);
         descriptors.put("DelegateMBeanAuthorization", currentResult);
         currentResult.setValue("description", "<p>Configures the WebLogic Server MBean servers to use the security realm's Authorization providers to determine whether a JMX client has permission to access an MBean attribute or invoke an MBean operation.</p> <p>You can continue to use WebLogic Server's default security settings or modify the defaults to suit your needs.</p> <p>If you do not delegate authorization to the realm's Authorization providers, the WebLogic MBean servers allow access only to the four default security roles (Admin, Deployer, Operator, and Monitor) and only as specified by WebLogic Server's default security settings.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.1.0.0");
      }

      if (!descriptors.containsKey("DeployCredentialMappingIgnored")) {
         getterName = "isDeployCredentialMappingIgnored";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeployCredentialMappingIgnored";
         }

         currentResult = new PropertyDescriptor("DeployCredentialMappingIgnored", RealmMBean.class, getterName, setterName);
         descriptors.put("DeployCredentialMappingIgnored", currentResult);
         currentResult.setValue("description", "Returns whether credential mapping deployment calls on the security system are ignored or passed to the configured Credential Mapping providers. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeployPolicyIgnored")) {
         getterName = "isDeployPolicyIgnored";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeployPolicyIgnored";
         }

         currentResult = new PropertyDescriptor("DeployPolicyIgnored", RealmMBean.class, getterName, setterName);
         descriptors.put("DeployPolicyIgnored", currentResult);
         currentResult.setValue("description", "Returns whether policy deployment calls on the security system are ignored or passed to the configured Authorization providers. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeployRoleIgnored")) {
         getterName = "isDeployRoleIgnored";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeployRoleIgnored";
         }

         currentResult = new PropertyDescriptor("DeployRoleIgnored", RealmMBean.class, getterName, setterName);
         descriptors.put("DeployRoleIgnored", currentResult);
         currentResult.setValue("description", "Returns whether role deployment calls on the security system are ignored or passed to the configured Role Mapping providers. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3", (String)null, this.targetVersion) && !descriptors.containsKey("DeployableProviderSynchronizationEnabled")) {
         getterName = "isDeployableProviderSynchronizationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeployableProviderSynchronizationEnabled";
         }

         currentResult = new PropertyDescriptor("DeployableProviderSynchronizationEnabled", RealmMBean.class, getterName, setterName);
         descriptors.put("DeployableProviderSynchronizationEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether synchronization for deployable Authorization and Role Mapping providers is enabled.</p>  <p>The Authorization and Role Mapping providers may or may not support parallel security policy and role modification, respectively, in the security provider database. If the security providers do not support parallel modification, the WebLogic Security Framework enforces a synchronization mechanism that results in each application and module being placed in a queue and deployed sequentially.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3");
      }

      if (!descriptors.containsKey("EnableWebLogicPrincipalValidatorCache")) {
         getterName = "isEnableWebLogicPrincipalValidatorCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableWebLogicPrincipalValidatorCache";
         }

         currentResult = new PropertyDescriptor("EnableWebLogicPrincipalValidatorCache", RealmMBean.class, getterName, setterName);
         descriptors.put("EnableWebLogicPrincipalValidatorCache", currentResult);
         currentResult.setValue("description", "<p>Returns whether the WebLogic Principal Validator caching is enabled.</p>  <p>The Principal Validator is used by Oracle supplied authentication providers and may be used by custom authentication providers. If enabled, the default principal validator will cache WebLogic Principal signatures.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FullyDelegateAuthorization")) {
         getterName = "isFullyDelegateAuthorization";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFullyDelegateAuthorization";
         }

         currentResult = new PropertyDescriptor("FullyDelegateAuthorization", RealmMBean.class, getterName, setterName);
         descriptors.put("FullyDelegateAuthorization", currentResult);
         currentResult.setValue("description", "Returns whether the Web and EJB containers should call the security framework on every access. <p> If false the containers are free to only call the security framework when security is set in the deployment descriptors. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.3.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("IdentityAssertionCacheEnabled")) {
         getterName = "isIdentityAssertionCacheEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityAssertionCacheEnabled";
         }

         currentResult = new PropertyDescriptor("IdentityAssertionCacheEnabled", RealmMBean.class, getterName, setterName);
         descriptors.put("IdentityAssertionCacheEnabled", currentResult);
         currentResult.setValue("description", "<p>Returns whether the Identity Assertion cache is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.3.1.0.0");
      }

      if (!descriptors.containsKey("ValidateDDSecurityData")) {
         getterName = "isValidateDDSecurityData";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidateDDSecurityData";
         }

         currentResult = new PropertyDescriptor("ValidateDDSecurityData", RealmMBean.class, getterName, setterName);
         descriptors.put("ValidateDDSecurityData", currentResult);
         currentResult.setValue("description", "<p>Not used in this release.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = RealmMBean.class.getMethod("createAuditor", String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- The name of this Auditing provider, for example, <code>DefaultAuditor</code> "), createParameterDescriptor("type", "- The type of this Auditing provider, for example, <code>weblogic.security.providers.audit.DefaultAuditor</code> Use <code>getAuditorTypes</code> to find the list of types that may be specified. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates an Auditing provider in this security realm. The new Auditing provider is added to the end of the list of Auditing providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Auditors");
      }

      mth = RealmMBean.class.getMethod("createAuditor", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "- The type of this Auditing provider, for example, <code>weblogic.security.providers.audit.DefaultAuditor</code> Use <code>getAuditorTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates an Auditing provider in this security realm. The new Auditing provider is added to the end of the list of Auditing providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Auditors");
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
      }

      mth = RealmMBean.class.getMethod("destroyAuditor", AuditorMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("auditor", "- The Auditing provider to remove. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the configuration for an Auditing provider in this security realm. It does not remove any persistent data for the Auditing provider (such as databases or files). <code>weblogic.management.configuration.SecurityConfigurationMBean.destroyRealm</code> automatically removes the security realm's Auditing providers. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Auditors");
      }

      mth = RealmMBean.class.getMethod("createAuthenticationProvider", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- The name of this Authentication provider, for example, <code>DefaultAuthenticator</code> "), createParameterDescriptor("type", "- The type of this Authentication provider, for example, <code>weblogic.security.providers.authentication.DefaultAuthenticator</code> Use <code>getAuthenticationProviderTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates an Authentication provider in this security realm. The new Authentication provider is added to the end of the list of Authentication providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AuthenticationProviders");
      }

      mth = RealmMBean.class.getMethod("createAuthenticationProvider", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "- The type of this Authentication provider, for example, <code>weblogic.security.providers.authentication.DefaultAuthenticator</code> Use <code>getAuthenticationProviderTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates an Authentication provider in this security realm. The new Authentication provider is added to the end of the list of Authentication providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AuthenticationProviders");
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
      }

      mth = RealmMBean.class.getMethod("destroyAuthenticationProvider", AuthenticationProviderMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("authenticationProvider", "- The Authentication provider to remove. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the configuration for an Authentication provider in this security realm. It does not remove any persistent data for the Authentication provider (such as databases or files). <code>weblogic.management.configuration.SecurityConfigurationMBean.destroyRealm</code> automatically removes the security realm's Authentication providers. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AuthenticationProviders");
      }

      mth = RealmMBean.class.getMethod("createRoleMapper", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- The name of this Role Mapping provider, for example, <code>DefaultRoleMapper</code> "), createParameterDescriptor("type", "- The type of this Role Mapping provider, for example, <code>weblogic.security.providers.authorization.DefaultRoleMapper</code> Use <code>getRoleMapperTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a Role Mapping provider in this security realm. The new Role Mapping provider is added to the end of the list of Role Mapping providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RoleMappers");
      }

      mth = RealmMBean.class.getMethod("createRoleMapper", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "- The type of this Role Mapping provider, for example, <code>weblogic.security.providers.authorization.DefaultRoleMapper</code> Use <code>getRoleMapperTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a Role Mapping provider in this security realm. The new Role Mapping provider is added to the end of the list of Role Mapping providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RoleMappers");
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
      }

      mth = RealmMBean.class.getMethod("destroyRoleMapper", RoleMapperMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("roleMapper", "- The Role Mapping provider to remove. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the configuration for a Role Mapping provider in this security realm. It does not remove any persistent data for the Role Mapping provider (such as databases or files). <code>weblogic.management.configuration.SecurityConfigurationMBean.destroyRealm</code> automatically removes the security realm's Role Mapping providers. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RoleMappers");
      }

      mth = RealmMBean.class.getMethod("createAuthorizer", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- The name of this Authorization provider, for example, <code>DefaultAuthorizer</code> "), createParameterDescriptor("type", "- The type of this Authorization provider, for example, <code>weblogic.security.providers.authorization.DefaultAuthorizer</code> Use <code>getAuthorizerTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates an Authorization provider in this security realm. The new Authorization provider is added to the end of the list of Authorization providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Authorizers");
      }

      mth = RealmMBean.class.getMethod("createAuthorizer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "- The type of this Authorization provider, for example, <code>weblogic.security.providers.authorization.DefaultAuthorizer</code> Use <code>getAuthorizerTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates an Authorization provider in this security realm. The new Authorization provider is added to the end of the list of Authorization providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Authorizers");
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
      }

      mth = RealmMBean.class.getMethod("destroyAuthorizer", AuthorizerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("authorizer", "- The Authorization provider to remove. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the configuration for an Authorization provider in this security realm. It does not remove any persistent data for the Authorization provider (such as databases or files). <code>weblogic.management.configuration.SecurityConfigurationMBean.destroyRealm</code> automatically removes the security realm's Authorization providers. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Authorizers");
      }

      mth = RealmMBean.class.getMethod("createAdjudicator", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- The name of this Adjudication provider, for example, <code>DefaultAdjudicator</code> "), createParameterDescriptor("type", "- The type of this Adjudication provider, for example, <code>weblogic.security.providers.authorization.DefaultAdjudicator</code> Use <code>getAdjudicatorTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates an Adjudication provider in this security realm and removes this security realm's previous Adjudication provider. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Adjudicator");
      }

      mth = RealmMBean.class.getMethod("createAdjudicator", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "- The type of this Adjudication provider, for example, <code>weblogic.security.providers.authorization.DefaultAdjudicator</code> Use <code>getAdjudicatorTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates an Adjudication provider in this security realm and removes this security realm's previous Adjudication provider. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Adjudicator");
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
      }

      mth = RealmMBean.class.getMethod("destroyAdjudicator");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the configuration this security realm's Adjudication provider (if there is one). It does not remove any persistent data for the Adjudication provider (such as databases or files). <code>weblogic.management.configuration.SecurityConfigurationMBean.destroyRealm</code> automatically removes the security realm's Adjudication provider. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Adjudicator");
      }

      mth = RealmMBean.class.getMethod("createCredentialMapper", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- The name of this Credential Mapping provider, for example, <code>DefaultCredentialMapper</code> "), createParameterDescriptor("type", "- The type of this Credential Mapping provider, for example, <code>weblogic.security.providers.credentials.DefaultCredentialMapper</code> Use <code>getCredentialMapperTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a Credential Mapping provider in this security realm. The new Credential Mapping provider is added to the end of the list of Credential Mapping providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CredentialMappers");
      }

      mth = RealmMBean.class.getMethod("createCredentialMapper", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "- The type of this Credential Mapping provider, for example, <code>weblogic.security.providers.credentials.DefaultCredentialMapper</code> Use <code>getCredentialMapperTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a Credential Mapping provider in this security realm. The new Credential Mapping provider is added to the end of the list of Credential Mapping providers  configured in this security realm. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CredentialMappers");
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
      }

      mth = RealmMBean.class.getMethod("destroyCredentialMapper", CredentialMapperMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("credentialMapper", "- The Credential Mapping provider to remove. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the configuration for a Credential Mapping provider in this security realm. It does not remove any persistent data for the Credential Mapping provider (such as databases or files). <code>weblogic.management.configuration.SecurityConfigurationMBean.destroyRealm</code> automatically removes the security realm's Credential Mapping providers. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CredentialMappers");
      }

      mth = RealmMBean.class.getMethod("createCertPathProvider", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- The name of this Certification Path provider, for example, <code>WebLogicCertPathProvider</code> "), createParameterDescriptor("type", "- The type of this Certification Path provider, for example, <code>weblogic.security.providers.pk.WebLogicCertPathProvider</code> Use <code>getCertPathProviderTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a Certification Path provider in this security realm. The new Certification Path provider is added to the end of the list of Certification Path providers  configured in this security realm.  The active security realm must contain at least one Certification Path provider that is a CertPath Builder provider and at least one Certificate Path provider that is a CertPath Validator provider. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CertPathProviders");
      }

      mth = RealmMBean.class.getMethod("createCertPathProvider", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "- The type of this Certification Path provider, for example, <code>weblogic.security.providers.pk.WebLogicCertPathProvider</code> Use <code>getCertPathProviderTypes</code> to find the list of types that may be specified. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a Certification Path provider in this security realm. The new Certification Path provider is added to the end of the list of Certification Path providers  configured in this security realm. <p> The active security realm must contain at least one Certification Path provider that is a CertPath Builder provider and at least one Certificate Path provider that is a CertPath Validator provider. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CertPathProviders");
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
      }

      mth = RealmMBean.class.getMethod("destroyCertPathProvider", CertPathProviderMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("certPathProvider", "- The Certification Path provider to remove. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the configuration for a Certification Path provider in this security realm. It does not remove any persistent data for the Certification Path provider (such as databases or files). <code>weblogic.management.configuration.SecurityConfigurationMBean.destroyRealm</code> automatically removes the security realm's Certification Path providers. <p> If <code>certPathProvider</code> has been selected as this security realm's <code>CertPathBuilder</code>, then this security realm's will have no <code>CertPathBuilder</code>. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CertPathProviders");
      }

      mth = RealmMBean.class.getMethod("createRDBMSSecurityStore");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      String[] seeObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMException if an error occurs when creating a RDBMS security store")};
         currentResult.setValue("throws", seeObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates configuration for the RDBMS security store. This can be called only once unless the existing instance is destroyed by invoking <code>destroyRDBMSSecurityStore</code> operation. The new security store MBean will have this realm as its parent. ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#destroyRDBMSSecurityStore")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RDBMSSecurityStore");
         currentResult.setValue("excludeFromRest", "REST only supports one creator");
      }

      mth = RealmMBean.class.getMethod("createRDBMSSecurityStore", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of this RDBMS security store ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("JMException if an error occurs when creating a RDBMS security store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates configuration for the RDBMS security store with the specified name. This can be called only once unless the existing instance is destroyed by invoking <code>destroyRDBMSSecurityStore</code> operation. The new security store MBean will have this realm as its parent. ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#destroyRDBMSSecurityStore")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RDBMSSecurityStore");
      }

      mth = RealmMBean.class.getMethod("destroyRDBMSSecurityStore");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes the existing RDBMS security store which is a child of this realm. It only removes the security store configuration, not any data persisted in the store. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#createRDBMSSecurityStore")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RDBMSSecurityStore");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0", (String)null, this.targetVersion)) {
         mth = RealmMBean.class.getMethod("createPasswordValidator", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "String The name for the given Password Validator provider MBean "), createParameterDescriptor("type", "String The type of a Password Validator provider, all available types are in method <code>getPasswordValidatorTypes</code> ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ClassNotFoundException"), BeanInfoHelper.encodeEntities("JMException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates a Password Validator provider in this security realm. The new Password Validator provider is added to the end of the list of Password Validator providers configured in this security realm. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "PasswordValidators");
            currentResult.setValue("since", "10.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.0", (String)null, this.targetVersion)) {
         mth = RealmMBean.class.getMethod("createPasswordValidator", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "String The type of a Password Validator provider, all available types are in method <code>getPasswordValidatorTypes</code> ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ClassNotFoundException"), BeanInfoHelper.encodeEntities("JMException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "10.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates a Password Validator provider in this security realm. The new Password Validator provider is added to the end of the list of Password Validator providers configured in this security realm. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "PasswordValidators");
            currentResult.setValue("since", "10.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.0", (String)null, this.targetVersion)) {
         mth = RealmMBean.class.getMethod("destroyPasswordValidator", PasswordValidatorMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("provider", "PasswordValidatorMBean The Password Validator provider to remove ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Removes the configuration for a Password Validator provider in this security realm. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "PasswordValidators");
            currentResult.setValue("since", "10.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = RealmMBean.class.getMethod("lookupAuditor", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds an Auditing provider in this security realm. Returns null if this security realm has no Auditing provider of the specified name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Auditors");
      }

      mth = RealmMBean.class.getMethod("lookupAuthenticationProvider", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds an Authentication provider in this security realm. Returns null if this security realm has no Authentication provider of the specified name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "AuthenticationProviders");
      }

      mth = RealmMBean.class.getMethod("lookupRoleMapper", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a Role Mapping provider in this security realm. Returns null if this security realm has no Role Mapping provider of the specified name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "RoleMappers");
      }

      mth = RealmMBean.class.getMethod("lookupAuthorizer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds an Authorization provider in this security realm. Returns null if this security realm has no Authorization provider of the specified name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Authorizers");
      }

      mth = RealmMBean.class.getMethod("lookupCredentialMapper", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a Credential Mapping provider in this security realm. Returns null if this security realm has no Credential Mapping provider of the specified name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CredentialMappers");
      }

      mth = RealmMBean.class.getMethod("lookupCertPathProvider", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a Certification Path provider in this security realm. Returns null if this security realm has no Certification Path provider of the specified name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CertPathProviders");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0", (String)null, this.targetVersion)) {
         mth = RealmMBean.class.getMethod("lookupPasswordValidator", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "String The name of a Password Validator provider MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Finds an Password Validator provider in this security realm. Returns null if this security realm has no Password Validator provider with the specified name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "PasswordValidators");
            currentResult.setValue("since", "10.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = RealmMBean.class.getMethod("validate");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0 This method is no longer required since activating a configuration transaction does this check automatically on the default realm, and will not allow the configuration to be saved if the domain does not have a valid default realm configured. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Checks that the realm is valid. ");
         currentResult.setValue("role", "operation");
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
