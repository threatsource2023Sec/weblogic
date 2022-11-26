package weblogic.security.service;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.security.auth.login.LoginException;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.event.DeploymentEventManager;
import weblogic.kernel.Kernel;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.provider.CommandLine;
import weblogic.management.provider.ManagementService;
import weblogic.management.security.RDBMSSecurityStoreMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.security.SecurityInitializationException;
import weblogic.security.SecurityLogger;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.internal.ServerPrincipalValidatorImpl;
import weblogic.security.principal.WLSAbstractPrincipal;
import weblogic.security.principal.WLSKernelIdentity;
import weblogic.security.principal.WLSServerIdentity;
import weblogic.security.providers.authentication.DefaultAuthenticatorMBean;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.WLSGroup;
import weblogic.security.spi.WLSUser;
import weblogic.security.subject.SubjectManager;
import weblogic.security.utils.KeyStoreConfigurationHelper;
import weblogic.security.utils.KeyStoreInfo;
import weblogic.security.utils.MBeanKeyStoreConfiguration;
import weblogic.server.GlobalServiceLocator;
import weblogic.t3.srvr.WebLogicServer;
import weblogic.utils.annotation.Secure;

@Service
@Secure
public final class SecurityServiceManagerDelegate2Impl implements SecurityServiceManagerDelegate2 {
   private static AuthenticatedSubject kernelIdentity = SecurityManager.getKernelIdentity();
   private static Set alreadyLogged = Collections.synchronizedSet(new HashSet());
   private final int NEW_CRED_LEN = 32;
   private AuthenticatedSubject serverIdentity;
   private SecurityServiceManagerDelegate serviceDelegate = null;
   private boolean permitAnonymousAdmin = false;
   private ServerPrincipalValidatorImpl serverValidator;
   private SecurityConfigurationMBean securityConfigMbean = null;
   private String tdsCred = null;
   private boolean isBooting = true;
   private PrincipalAuthenticator defaultRealmNamePA = null;
   private boolean areWebappFilesCaseInsensitive = false;
   private boolean areWebappFilesCaseInsensitiveSet = false;
   private boolean enforceStrictURLPattern = true;
   private boolean enforceValidBasicAuthCredentials = true;

   private SecurityServiceManagerDelegate getServiceDelegate() {
      return this.serviceDelegate;
   }

   private PrincipalAuthenticator getDefaultRealmPrincipalAuthenticator() {
      return this.defaultRealmNamePA;
   }

   public AuthenticatedSubject getASFromAU(AuthenticatedUser user) {
      if (user == null) {
         return SubjectUtils.getAnonymousSubject();
      } else if (user instanceof AuthenticatedSubject) {
         return this.getASFromWire((AuthenticatedSubject)user);
      } else {
         AuthenticatedSubject subject = null;
         if (Kernel.isServer()) {
            subject = this.getASFromAUInServer(user);
            if (subject != null) {
               return subject;
            }
         }

         subject = new AuthenticatedSubject(user);
         subject.getPublicCredentials().add(user);
         return subject;
      }
   }

   public AuthenticatedSubject getSealedSubjectFromWire(AuthenticatedSubject kernelId, AuthenticatedUser user) {
      AuthenticatedSubject subject = this.getASFromAU(user);

      try {
         subject = this.seal(kernelId, subject);
      } catch (SecurityException var5) {
         if (this.securityConfigMbean == null || !this.securityConfigMbean.getDowngradeUntrustedPrincipals()) {
            throw var5;
         }

         SecurityLogger.logDowngradingUntrustedIdentity(subject.toString());
         subject = SubjectUtils.getAnonymousSubject();
      }

      return subject;
   }

   public AuthenticatedSubject getASFromAUInServerOrClient(AuthenticatedUser user) {
      if (!Kernel.isServer()) {
         return this.getASFromAU(user);
      } else {
         AuthenticatedSubject subject = this.getASFromAUInServer(user);
         if (subject == null) {
            subject = SubjectUtils.getAnonymousSubject();
         }

         return subject;
      }
   }

   private AuthenticatedSubject getASFromAUInServer(AuthenticatedUser user) {
      AuthenticatedSubject subject = null;
      PrincipalAuthenticator pa = this.getDefaultRealmPrincipalAuthenticator();

      try {
         subject = pa.assertIdentity("AuthenticatedUser", user);
      } catch (LoginException var5) {
      }

      return subject;
   }

   public AuthenticatedSubject getASFromWire(AuthenticatedSubject as) {
      Set principals = as.getPrincipals();
      if (principals.size() == 1) {
         Principal p = (Principal)principals.iterator().next();
         if (p instanceof WLSServerIdentity && this.serverValidator != null) {
            if (as != null && as.isSealed()) {
               return kernelIdentity;
            }

            if (this.serverValidator.validate((WLSServerIdentity)p)) {
               this.seal(kernelIdentity, as);
               return kernelIdentity;
            }

            if (!alreadyLogged.contains(p.getName())) {
               SecurityLogger.logDowngradingUntrustedServerIdentity(p.getName());
               alreadyLogged.add(p.getName());
            }

            return SubjectUtils.getAnonymousSubject();
         }
      }

      return as;
   }

   public AuthenticatedSubject sendASToWire(AuthenticatedSubject as) {
      return this.isKernelIdentity(as) ? this.getServerID() : as;
   }

   public AuthenticatedUser convertToAuthenticatedUser(AuthenticatedUser user) {
      if (user.getClass().equals(AuthenticatedUser.class)) {
         return user;
      } else {
         AuthenticatedSubject as = (AuthenticatedSubject)user;
         AuthenticatedUser rtn = this.getAuthenticatedUserFromPrincipals(as.getPrincipals());
         return rtn;
      }
   }

   private AuthenticatedUser getAuthenticatedUserFromPrincipals(Set principals) {
      WLSServerIdentity serverId = null;
      WLSKernelIdentity kernelId = null;
      WLSUser userId = null;
      Principal principalId = null;
      Iterator i = principals.iterator();

      while(i.hasNext()) {
         Object principal = i.next();
         if (principal instanceof WLSServerIdentity) {
            serverId = (WLSServerIdentity)principal;
         } else if (principal instanceof WLSKernelIdentity) {
            kernelId = (WLSKernelIdentity)principal;
         } else if (principal instanceof WLSUser) {
            userId = (WLSUser)principal;
         } else if (!(principal instanceof WLSGroup)) {
            principalId = (Principal)principal;
         }
      }

      if ((kernelId == null || !SubjectManagerImpl.kernelPrincipal.equals(kernelId)) && (serverId == null || !this.serverValidator.validate(serverId))) {
         if (principals.size() == 0) {
            return null;
         } else if (userId != null) {
            if (!Kernel.isServer() && userId instanceof WLSAbstractPrincipal) {
               WLSAbstractPrincipal wlsPrin = (WLSAbstractPrincipal)userId;
               return new AuthenticatedUser(wlsPrin.getName(), wlsPrin.getSignature(), wlsPrin.getSalt());
            } else {
               return this.certifyUser(userId.getName(), false);
            }
         } else if (principalId != null) {
            return this.certifyUser(principalId.getName(), false);
         } else {
            throw new IllegalArgumentException(SecurityLogger.getPrincipalSetDoesNotContainRAUser());
         }
      } else {
         return this.certifyUser("system", true);
      }
   }

   private AuthenticatedUser certifyUser(String user, boolean wasServerId) {
      if (this.securityConfigMbean == null) {
         this.securityConfigMbean = ManagementService.getRuntimeAccess(kernelIdentity).getDomain().getSecurityConfiguration();
      }

      if (this.tdsCred == null) {
         this.tdsCred = this.securityConfigMbean.getCredential();
      }

      return wasServerId ? new AuthenticatedUser(user, this.tdsCred, 1L) : new AuthenticatedUser(user, this.tdsCred);
   }

   public AuthenticatedSubject getServerIdentity(AuthenticatedSubject kernelID) {
      this.checkKernelIdentity(kernelID);
      return this.serverIdentity;
   }

   public boolean isTrustedServerIdentity(AuthenticatedSubject id) {
      Principal p = SubjectUtils.getOnePrincipal(id, WLSServerIdentity.class);
      return this.serverValidator != null && p != null ? this.serverValidator.validate((WLSServerIdentity)p) : false;
   }

   public AuthenticatedSubject seal(AuthenticatedSubject kernelID, AuthenticatedSubject as) {
      if (as == null) {
         return null;
      } else if (as.isSealed()) {
         return as;
      } else if (!Kernel.isServer()) {
         return as;
      } else {
         this.checkKernelIdentity(kernelID);
         if (this.isKernelIdentity(as)) {
            return as;
         } else {
            if (this.isBooting) {
               WebLogicServer t3Srvr = (WebLogicServer)GlobalServiceLocator.getServiceLocator().getService(WebLogicServer.class, new Annotation[0]);
               if (t3Srvr.getRunState() != 2) {
                  as.seal(kernelIdentity);
                  return as;
               }

               this.isBooting = false;
            }

            if (!this.getDefaultRealmPrincipalAuthenticator().validateIdentity(as)) {
               throw new SecurityException(SecurityLogger.getInvalidSubject("" + as));
            } else {
               as.seal(kernelIdentity);
               Iterator i = as.getPrincipals().iterator();

               Principal p;
               do {
                  if (!i.hasNext()) {
                     return as;
                  }

                  p = (Principal)i.next();
               } while(!(p instanceof WLSServerIdentity));

               return kernelIdentity;
            }
         }
      }
   }

   public void initializeConfiguration(AuthenticatedSubject kernelID) {
      this.checkKernelIdentity(kernelID);
      this.securityConfigMbean = ManagementService.getRuntimeAccess(kernelIdentity).getDomain().getSecurityConfiguration();
      if (this.securityConfigMbean == null) {
         throw new SecurityServiceRuntimeException(SecurityLogger.getSecConfigUnavailable());
      } else {
         this.tdsCred = this.securityConfigMbean.getCredential();
         if (this.tdsCred != null && this.tdsCred.length() != 0) {
            String jndiProtectionProperty = CommandLine.getCommandLine().getAnonymousAdminLookupEnabledString();
            if (jndiProtectionProperty != null) {
               this.permitAnonymousAdmin = new Boolean(jndiProtectionProperty);
            } else {
               this.permitAnonymousAdmin = this.securityConfigMbean.isAnonymousAdminLookupEnabled();
            }

            this.areWebAppFilesCaseInsensitive();
            this.enforceStrictURLPattern = this.securityConfigMbean.getEnforceStrictURLPattern();
            this.enforceValidBasicAuthCredentials = this.securityConfigMbean.getEnforceValidBasicAuthCredentials();
         } else {
            throw new SecurityServiceRuntimeException(SecurityLogger.getSecCredUnavailable());
         }
      }
   }

   public void initializeDeploymentCallbacks(AuthenticatedSubject kernelID) {
      this.checkKernelIdentity(kernelID);

      try {
         DeploymentListener cb = new DeploymentListener();
         DeploymentEventManager.addDeploymentEventListener(cb, true);
         DeploymentEventManager.addVetoableDeploymentListener(cb);
      } catch (DeploymentException var3) {
         throw new SecurityInitializationException(var3.getMessage(), var3);
      }
   }

   public void initializeServiceDelegate(AuthenticatedSubject kernelID, SecurityServiceManagerDelegate delegate) {
      this.checkKernelIdentity(kernelID);
      this.serviceDelegate = delegate;
      if (this.defaultRealmNamePA == null) {
         this.defaultRealmNamePA = (PrincipalAuthenticator)this.getServiceDelegate().getSecurityService(kernelID, this.getServiceDelegate().getDefaultRealmName(), ServiceType.AUTHENTICATION);
      }

      this.getServerID();
   }

   private static String convertToNewProperty(String oldProperty) {
      if (oldProperty != null && !oldProperty.equals("")) {
         if (oldProperty.equalsIgnoreCase("os")) {
            return "os";
         } else if (oldProperty.equalsIgnoreCase("on")) {
            return "true";
         } else {
            return oldProperty.equalsIgnoreCase("off") ? "false" : "false";
         }
      } else {
         return "false";
      }
   }

   private static boolean intrepretWebAppFilesCaseSetting(String property) {
      if (property != null && !property.equals("false")) {
         if (property.equals("os")) {
            String osname = System.getProperty("os.name");
            if (osname != null) {
               osname = osname.toLowerCase();
               if (osname.indexOf("windows") >= 0) {
                  return true;
               }
            }
         } else if (property.equals("true")) {
            return true;
         }

         return false;
      } else {
         return false;
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean isAnonymousAdminLookupEnabled() {
      return this.permitAnonymousAdmin;
   }

   public boolean getEnforceStrictURLPattern() {
      return this.enforceStrictURLPattern;
   }

   public boolean getEnforceValidBasicAuthCredentials() {
      return this.enforceValidBasicAuthCredentials;
   }

   public AuthenticatedSubject getCurrentSubjectForWire(AuthenticatedSubject kernelID) {
      AuthenticatedSubject as = SecurityManager.getCurrentSubject(kernelID);
      return this.sendASToWire(as);
   }

   public boolean isKernelIdentity(AuthenticatedSubject s) {
      return s == kernelIdentity;
   }

   public boolean isServerIdentity(AuthenticatedSubject s) {
      return s == this.serverIdentity;
   }

   public void checkKernelIdentity(AuthenticatedSubject s) {
      if (!this.isKernelIdentity(s)) {
         throw new NotAuthorizedRuntimeException(SecurityLogger.getSubjectIsNotTheKernelIdentity(s == null ? "<null>" : s.toString()));
      }
   }

   public boolean isUserInRole(AuthenticatedSubject user, String role, Map userRoles) {
      return userRoles != null && userRoles.get(role) != null;
   }

   private AuthenticatedSubject createServerID() {
      StringBuffer name = new StringBuffer();
      name.append("<WLS Server ");
      name.append(Kernel.getConfig().getName());
      name.append(">");
      WLSServerIdentity sID = new WLSServerIdentity(name.toString());
      AccessController.doPrivileged(PrivilegedActions.getSignPrincipalAction(this.serverValidator, sID));
      AuthenticatedSubject s = new AuthenticatedSubject();
      Set set = s.getPrincipals();
      set.add(sID);
      return s;
   }

   private AuthenticatedSubject getServerID() {
      if (this.serverIdentity != null) {
         return this.serverIdentity;
      } else {
         synchronized(SubjectManager.getKernelPermission()) {
            if (this.serverIdentity == null) {
               this.serverValidator = new ServerPrincipalValidatorImpl();
               this.serverIdentity = this.createServerID();
            }

            return this.serverIdentity;
         }
      }
   }

   public boolean areWebAppFilesCaseInsensitive() {
      if (this.areWebappFilesCaseInsensitiveSet) {
         return this.areWebappFilesCaseInsensitive;
      } else {
         String oldProperty = System.getProperty("weblogic.security.URLResourceCaseMapping");
         String webAppFilescase;
         if (Kernel.isServer()) {
            webAppFilescase = null;
            DomainMBean domMbean = ManagementService.getRuntimeAccess(kernelIdentity).getDomain();
            if (domMbean != null) {
               webAppFilescase = domMbean.getSecurityConfiguration().getWebAppFilesCaseInsensitive();
            }

            this.areWebappFilesCaseInsensitive = intrepretWebAppFilesCaseSetting(webAppFilescase);
            if (oldProperty != null) {
               String property = convertToNewProperty(oldProperty);
               boolean oldPropertyValue = intrepretWebAppFilesCaseSetting(property);
               if (this.areWebappFilesCaseInsensitive != oldPropertyValue) {
                  throw new SecurityServiceRuntimeException(SecurityLogger.logWebAppFilesCaseMismatch(oldProperty, webAppFilescase));
               }
            }
         } else if (oldProperty != null) {
            webAppFilescase = convertToNewProperty(oldProperty);
            this.areWebappFilesCaseInsensitive = intrepretWebAppFilesCaseSetting(webAppFilescase);
         }

         this.areWebappFilesCaseInsensitiveSet = true;
         return this.areWebappFilesCaseInsensitive;
      }
   }

   public KeyStoreInfo getServerIdentityKeyStore(AuthenticatedSubject kernelID) {
      this.checkKernelIdentity(kernelID);
      return (new KeyStoreConfigurationHelper(MBeanKeyStoreConfiguration.getInstance())).getIdentityKeyStore();
   }

   public KeyStoreInfo[] getServerTrustKeyStores(AuthenticatedSubject kernelID) {
      this.checkKernelIdentity(kernelID);
      return (new KeyStoreConfigurationHelper(MBeanKeyStoreConfiguration.getInstance())).getTrustKeyStores();
   }

   public boolean isCaseSensitiveUserNames() {
      return Boolean.getBoolean("caseSensitiveUserNames");
   }

   public boolean isEmbeddedLdapNeeded(AuthenticatedSubject kernelID) {
      this.checkKernelIdentity(kernelID);
      RealmMBean realm = ManagementService.getRuntimeAccess(kernelIdentity).getDomain().getSecurityConfiguration().getDefaultRealm();
      RDBMSSecurityStoreMBean rdbmsStore = realm.getRDBMSSecurityStore();
      AuthenticationProviderMBean[] authPrvders = realm.getAuthenticationProviders();
      if (rdbmsStore == null) {
         return true;
      } else {
         for(int i = 0; i < authPrvders.length; ++i) {
            if (authPrvders[i] instanceof DefaultAuthenticatorMBean) {
               return true;
            }
         }

         return false;
      }
   }
}
