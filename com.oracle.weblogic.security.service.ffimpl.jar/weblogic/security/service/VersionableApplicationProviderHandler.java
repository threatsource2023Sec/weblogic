package weblogic.security.service;

import java.security.AccessController;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.VersionableApplicationProvider;

final class VersionableApplicationProviderHandler {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   static void createApplicationVersion(String appIdentifier, String sourceAppIdentifier, VersionableApplicationProvider[] versionableProviders, Auditor auditor, String auditManager, LoggerWrapper log) throws ApplicationVersionCreationException {
      if ((sourceAppIdentifier == null || sourceAppIdentifier.length() != 0) && appIdentifier != null && appIdentifier.length() != 0) {
         if (versionableProviders != null && versionableProviders.length != 0) {
            for(int i = 0; i < versionableProviders.length; ++i) {
               VersionableApplicationProvider p = versionableProviders[i];

               try {
                  p.createApplicationVersion(appIdentifier, sourceAppIdentifier);
                  if (auditor != null) {
                     auditor.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.SUCCESS, auditManager, SecurityServiceManager.getCurrentSubject(kernelId), appIdentifier, "Create Application Version", (Exception)null));
                  }
               } catch (Exception var9) {
                  if (log != null && log.isDebugEnabled()) {
                     log.debug("createApplicationVersion got an exception: " + var9);
                  }

                  SecurityLogger.logVersionableApplicationProviderError(p.getClass().getName(), appIdentifier, var9);
                  if (auditor != null) {
                     auditor.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.FAILURE, auditManager, SecurityServiceManager.getCurrentSubject(kernelId), appIdentifier, "Create Application Version", var9));
                  }

                  throw new ApplicationVersionCreationException(var9);
               }
            }

         } else {
            if (log != null && log.isDebugEnabled()) {
               log.debug("No versionable providers.");
            }

         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      }
   }

   static void deleteApplicationVersion(String appIdentifier, VersionableApplicationProvider[] versionableProviders, Auditor auditor, String auditManager, LoggerWrapper log) throws ApplicationVersionRemovalException {
      if (appIdentifier != null && appIdentifier.length() != 0) {
         if (versionableProviders != null && versionableProviders.length != 0) {
            for(int i = 0; i < versionableProviders.length; ++i) {
               VersionableApplicationProvider p = versionableProviders[i];

               try {
                  p.deleteApplicationVersion(appIdentifier);
                  if (auditor != null) {
                     auditor.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.SUCCESS, auditManager, SecurityServiceManager.getCurrentSubject(kernelId), appIdentifier, "Delete Application Version", (Exception)null));
                  }
               } catch (Exception var8) {
                  if (log != null && log.isDebugEnabled()) {
                     log.debug("deleteApplicationVersion got an exception: " + var8);
                  }

                  SecurityLogger.logVersionableApplicationProviderError(p.getClass().getName(), appIdentifier, var8);
                  if (auditor != null) {
                     auditor.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.FAILURE, auditManager, SecurityServiceManager.getCurrentSubject(kernelId), appIdentifier, "Delete Application Version", var8));
                  }

                  throw new ApplicationVersionRemovalException(var8);
               }
            }

         } else {
            if (log != null && log.isDebugEnabled()) {
               log.debug("No versionable providers.");
            }

         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      }
   }

   static void deleteApplication(String appName, VersionableApplicationProvider[] versionableProviders, Auditor auditor, String auditManager, LoggerWrapper log) throws ApplicationRemovalException {
      if (appName != null && appName.length() != 0) {
         if (versionableProviders != null && versionableProviders.length != 0) {
            for(int i = 0; i < versionableProviders.length; ++i) {
               VersionableApplicationProvider p = versionableProviders[i];

               try {
                  p.deleteApplication(appName);
                  if (auditor != null) {
                     auditor.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.SUCCESS, auditManager, SecurityServiceManager.getCurrentSubject(kernelId), appName, "Delete Application", (Exception)null));
                  }
               } catch (Exception var8) {
                  if (log != null && log.isDebugEnabled()) {
                     log.debug("deleteApplication got an exception: " + var8);
                  }

                  SecurityLogger.logVersionableApplicationProviderError(p.getClass().getName(), appName, var8);
                  if (auditor != null) {
                     auditor.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.FAILURE, auditManager, SecurityServiceManager.getCurrentSubject(kernelId), appName, "Delete Application", var8));
                  }

                  throw new ApplicationRemovalException(var8);
               }
            }

         } else {
            if (log != null && log.isDebugEnabled()) {
               log.debug("No versionable providers.");
            }

         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      }
   }
}
