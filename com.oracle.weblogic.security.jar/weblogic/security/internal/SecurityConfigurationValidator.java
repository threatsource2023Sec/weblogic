package weblogic.security.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.List;
import weblogic.descriptor.DescriptorUpdateEvent;
import weblogic.descriptor.DescriptorUpdateListener;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AnyIdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.MultiIdentityDomainAuthenticatorMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.nodemanager.server.NMEncryptionHelper;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.security.utils.SecurityUtils;
import weblogic.server.AbstractServerService;
import weblogic.utils.LocatorUtilities;

public final class SecurityConfigurationValidator extends AbstractServerService implements DescriptorUpdateListener {
   private static SecurityConfigurationValidator singleton = null;
   private static final boolean DEBUG = false;
   private String currentNMUser;
   private byte[] currentNMPass;
   private String proposedNMUser;
   private byte[] proposedNMPass;

   private SecurityConfigurationValidator() {
   }

   public static synchronized SecurityConfigurationValidator getInstance() {
      if (singleton == null) {
         singleton = new SecurityConfigurationValidator();
      }

      return singleton;
   }

   private static void p(String msg) {
   }

   public synchronized void start() {
      SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
         public SecurityRuntimeAccess run() {
            return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
         }
      });
      runtimeAccess.getDomain().getDescriptor().addUpdateListener(this);
   }

   public void prepareUpdate(DescriptorUpdateEvent event) throws DescriptorUpdateRejectedException {
      DomainMBean domain = (DomainMBean)event.getProposedDescriptor().getRootBean();
      RealmMBean defaultRealm = domain.getSecurityConfiguration().getDefaultRealm();
      if (defaultRealm == null) {
         throw new DescriptorUpdateRejectedException(SecurityLogger.getCannotActivateChangesNoDefaultRealmError());
      } else {
         RealmMBean[] realms = domain.getSecurityConfiguration().getRealms();

         for(int i = 0; i < realms.length; ++i) {
            String name0 = realms[i].getName();

            for(int j = i + 1; j < realms.length; ++j) {
               String name1 = realms[j].getName();
               if (name0.equals(name1)) {
                  throw new DescriptorUpdateRejectedException(SecurityLogger.getCannotActivateChangesRealmNameExistsError(name0));
               }
            }

            try {
               realms[i].validate();
            } catch (ErrorCollectionException var9) {
               if (!realms[i].isDefaultRealm()) {
                  throw new DescriptorUpdateRejectedException(SecurityLogger.getCannotActivateChangesImproperlyConfiguredRealmError(name0), var9);
               }

               throw new DescriptorUpdateRejectedException(SecurityLogger.getCannotActivateChangesImproperlyConfiguredDefaultRealmError(), var9);
            }
         }

         validateIDDs(domain);
         DomainMBean proposedDomain = (DomainMBean)event.getProposedDescriptor().getRootBean();
         this.proposedNMUser = proposedDomain.getSecurityConfiguration().getNodeManagerUsername();
         this.proposedNMPass = proposedDomain.getSecurityConfiguration().getNodeManagerPassword().getBytes();
         SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
            public SecurityRuntimeAccess run() {
               return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
            }
         });
         DomainMBean curDomain = runtimeAccess.getDomain();
         this.currentNMUser = curDomain.getSecurityConfiguration().getNodeManagerUsername();
         this.currentNMPass = curDomain.getSecurityConfiguration().getNodeManagerPassword().getBytes();
         if (this.currentNMUser == null) {
            this.currentNMUser = "";
         }

         if (this.currentNMPass == null) {
            this.currentNMPass = "".getBytes();
         }

      }
   }

   public void activateUpdate(DescriptorUpdateEvent event) {
      if (this.proposedNMUser != null || this.proposedNMPass != null) {
         if (this.proposedNMUser == null) {
            this.proposedNMUser = this.currentNMUser;
         }

         if (this.proposedNMPass == null) {
            this.proposedNMPass = this.currentNMPass;
         }

         if (this.proposedNMUser.equals(this.currentNMUser) && Arrays.equals(this.proposedNMPass, this.currentNMPass)) {
            return;
         }

         SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
            public SecurityRuntimeAccess run() {
               return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
            }
         });
         ServerMBean smb = runtimeAccess.getServer();
         NMEncryptionHelper.updateNMHash(smb.getRootDirectory(), this.proposedNMUser, this.proposedNMPass);
      }

      this.currentNMUser = null;
      this.proposedNMUser = null;
      this.currentNMPass = null;
      this.proposedNMPass = null;
   }

   public void rollbackUpdate(DescriptorUpdateEvent event) {
      this.currentNMUser = null;
      this.proposedNMUser = null;
      this.currentNMPass = null;
      this.proposedNMPass = null;
   }

   public static void validateIDDs(DomainMBean domain) throws DescriptorUpdateRejectedException {
      if (SecurityUtils.isIDDDomain(domain)) {
         SecurityConfigurationMBean security = domain.getSecurityConfiguration();
         if ((security.isIdentityDomainDefaultEnabled() || security.getAdministrativeIdentityDomain() != null) && (security.getAdministrativeIdentityDomain() == null || !security.getAdministrativeIdentityDomain().isEmpty())) {
            String adminIDD = security.getAdministrativeIdentityDomain();
            RealmMBean adminRealm = security.getDefaultRealm();
            if (adminIDD != null) {
               validateRealmIDD(adminIDD, adminRealm);
            }

         } else {
            throw new DescriptorUpdateRejectedException(SecurityLogger.getCannotActivateChangesNoAdminIDDSetError());
         }
      }
   }

   private static void validateRealmIDD(String identityDomain, RealmMBean realm) throws DescriptorUpdateRejectedException {
      boolean foundMatchingProvider = false;
      AuthenticationProviderMBean[] var3 = realm.getAuthenticationProviders();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         AuthenticationProviderMBean authenticator = var3[var5];
         if (authenticator instanceof IdentityDomainAuthenticatorMBean && ((IdentityDomainAuthenticatorMBean)authenticator).getIdentityDomain() != null && ((IdentityDomainAuthenticatorMBean)authenticator).getIdentityDomain().equals(identityDomain)) {
            foundMatchingProvider = true;
         }

         if (authenticator instanceof MultiIdentityDomainAuthenticatorMBean) {
            MultiIdentityDomainAuthenticatorMBean multiAtn = (MultiIdentityDomainAuthenticatorMBean)authenticator;
            if (multiAtn.getIdentityDomains() != null && multiAtn.getIdentityDomains().length > 0) {
               List identityDomains = Arrays.asList(multiAtn.getIdentityDomains());
               if (identityDomains.contains(identityDomain)) {
                  foundMatchingProvider = true;
               }
            }
         }

         if (authenticator instanceof AnyIdentityDomainAuthenticatorMBean && ((AnyIdentityDomainAuthenticatorMBean)authenticator).isAnyIdentityDomainEnabled()) {
            foundMatchingProvider = true;
         }
      }

      if (!foundMatchingProvider) {
         throw new DescriptorUpdateRejectedException(SecurityLogger.getCannotActivateChangesNoIDDConfiguredError(identityDomain));
      }
   }
}
