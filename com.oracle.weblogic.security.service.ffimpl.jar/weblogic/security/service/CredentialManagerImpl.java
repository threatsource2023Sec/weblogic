package weblogic.security.service;

import com.bea.common.security.service.CredentialMappingService;
import com.bea.security.css.CSS;
import java.security.AccessController;
import java.util.Vector;
import weblogic.management.security.ProviderMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.internal.ApplicationVersioningService;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.Resource;

public class CredentialManagerImpl implements SecurityService, CredentialManager {
   private ApplicationVersioningService appVerService = null;
   private CredentialMappingService credentialMappingService = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityCredMap");

   private void assertNotUsingCommon() {
      throw new AssertionError("This code should never be called when using common security");
   }

   public CredentialManagerImpl() {
   }

   public CredentialManagerImpl(RealmServices realmServices, ProviderMBean[] configuration) {
      this.initialize(realmServices, configuration);
   }

   public void initialize(RealmServices realmServices, ProviderMBean[] configuration) throws InvalidParameterException, ProviderException {
      if (realmServices == null) {
         throw new InvalidParameterException(SecurityLogger.getValidRealmNameMustBeSpecifed());
      } else if (configuration != null && configuration.length != 0) {
         if (log.isDebugEnabled()) {
            log.debug("CredentialManager will use common security");
         }

         try {
            CSS css = realmServices.getCSS();
            this.credentialMappingService = (CredentialMappingService)css.getService("CredentialMappingService");
            this.appVerService = (ApplicationVersioningService)css.getService("ApplicationVersioningService");
         } catch (Exception var5) {
            if (log.isDebugEnabled()) {
               SecurityLogger.logStackTrace(var5);
            }

            SecurityServiceRuntimeException ssre = new SecurityServiceRuntimeException(SecurityLogger.getExceptionObtainingService("Common CredentialMappingService", var5.toString()));
            ssre.initCause(var5);
            throw ssre;
         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getInvCredMgrConfigMBean());
      }
   }

   public Object[] getCredentials(AuthenticatedSubject requestor, AuthenticatedSubject initiator, Resource resource, ContextHandler handler, String credType) {
      return this.credentialMappingService.getCredentials(IdentityUtility.authenticatedSubjectToIdentity(requestor), IdentityUtility.authenticatedSubjectToIdentity(initiator), resource, handler, credType);
   }

   public Object[] getCredentials(AuthenticatedSubject requestor, String initiator, Resource resource, ContextHandler handler, String credType) {
      return this.credentialMappingService.getCredentials(IdentityUtility.authenticatedSubjectToIdentity(requestor), initiator, resource, handler, credType);
   }

   /** @deprecated */
   @Deprecated
   public Vector getCredentials(AuthenticatedSubject requestor, AuthenticatedSubject initiator, Resource resource, String[] credentialTypes) throws NotYetInitializedException, InvalidParameterException {
      if (credentialTypes == null) {
         throw new InvalidParameterException(SecurityLogger.getCredentialsTypeNull());
      } else {
         Vector credentials = new Vector();

         for(int i = 0; credentialTypes != null && i < credentialTypes.length; ++i) {
            Object[] creds = this.getCredentials(requestor, (AuthenticatedSubject)initiator, resource, (ContextHandler)null, credentialTypes[i]);

            for(int j = 0; creds != null && j < creds.length; ++j) {
               credentials.add(creds[j]);
            }
         }

         return credentials;
      }
   }

   /** @deprecated */
   @Deprecated
   public Vector getCredentials(AuthenticatedSubject requestor, String initiator, Resource resource, String[] credentialTypes) {
      if (credentialTypes == null) {
         throw new InvalidParameterException(SecurityLogger.getCredentialsTypeNull());
      } else if (credentialTypes == null) {
         throw new InvalidParameterException(SecurityLogger.getCredentialsTypeNull());
      } else {
         Vector credentials = new Vector();

         for(int i = 0; credentialTypes != null && i < credentialTypes.length; ++i) {
            Object[] creds = this.getCredentials(requestor, (String)initiator, resource, (ContextHandler)null, credentialTypes[i]);

            for(int j = 0; creds != null && j < creds.length; ++j) {
               credentials.add(creds[j]);
            }
         }

         return credentials;
      }
   }

   public void shutdown() {
      this.credentialMappingService = null;
   }

   public void start() {
   }

   public void suspend() {
   }

   public boolean isVersionableApplicationSupported() {
      if (log.isDebugEnabled()) {
         log.debug("CredentialManager.isVersionableApplicationSupported");
      }

      return this.appVerService.isApplicationVersioningSupported();
   }

   public void createApplicationVersion(String appIdentifier, String sourceAppIdentifier) throws ApplicationVersionCreationException {
      this.assertNotUsingCommon();
   }

   public void deleteApplicationVersion(String appIdentifier) throws ApplicationVersionRemovalException {
      if (log.isDebugEnabled()) {
         log.debug("CredentialManager.deleteApplicationVersion");
      }

      this.assertNotUsingCommon();
   }

   public void deleteApplication(String appName) throws ApplicationRemovalException {
      if (log.isDebugEnabled()) {
         log.debug("CredentialManager.deleteApplication");
      }

      this.assertNotUsingCommon();
   }
}
