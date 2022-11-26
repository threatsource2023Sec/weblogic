package weblogic.security.service.internal;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.AuditService;
import java.security.AccessController;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ApplicationRemovalException;
import weblogic.security.service.ApplicationVersionCreationException;
import weblogic.security.service.ApplicationVersionRemovalException;
import weblogic.security.service.AuditApplicationVersionEventImpl;
import weblogic.security.service.InvalidParameterException;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.VersionableApplicationProvider;

public class ApplicationVersioningServiceImpl implements ServiceLifecycleSpi {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private LoggerSpi logger;
   private AuditService auditService;
   private VersionableApplicationProvider[] providers;
   private String[] logNames;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityRealm");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityRealm"));
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".init()");
         }

         ApplicationVersioningServiceConfig myconfig = (ApplicationVersioningServiceConfig)config;
         this.auditService = (AuditService)dependentServices.getService(myconfig.getAuditServiceName());
         String[] names = myconfig.getVersionableApplicationProviderNames();
         this.providers = new VersionableApplicationProvider[names.length];
         this.logNames = new String[names.length];

         for(int i = 0; i < names.length; ++i) {
            this.providers[i] = (VersionableApplicationProvider)dependentServices.getService(names[i]);
            this.logNames[i] = dependentServices.getServiceLoggingName(names[i]);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Obtained VersionableApplicationProvider " + this.logNames[i]);
            }
         }

         return new ServiceImpl();
      }
   }

   public void shutdown() {
   }

   private final class ServiceImpl implements ApplicationVersioningService {
      private ServiceImpl() {
      }

      public boolean isApplicationVersioningSupported() {
         boolean debug = ApplicationVersioningServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".isVersionableApplicationSupported" : null;
         if (debug) {
            ApplicationVersioningServiceImpl.this.logger.debug(method);
         }

         return ApplicationVersioningServiceImpl.this.providers.length > 0;
      }

      public void createApplicationVersion(String appIdentifier, String sourceAppIdentifier) throws ApplicationVersionCreationException {
         if ((sourceAppIdentifier == null || sourceAppIdentifier.length() != 0) && appIdentifier != null && appIdentifier.length() != 0) {
            boolean debug = ApplicationVersioningServiceImpl.this.logger.isDebugEnabled();
            String method = debug ? this.getClass().getName() + ".createApplicationVersion" : null;
            if (debug) {
               ApplicationVersioningServiceImpl.this.logger.debug(method + "(" + appIdentifier + "," + sourceAppIdentifier + ")");
            }

            for(int i = 0; i < ApplicationVersioningServiceImpl.this.providers.length; ++i) {
               try {
                  ApplicationVersioningServiceImpl.this.providers[i].createApplicationVersion(appIdentifier, sourceAppIdentifier);
               } catch (Exception var7) {
                  if (debug) {
                     ApplicationVersioningServiceImpl.this.logger.debug(method + " createApplicationVersion got an exception", var7);
                  }

                  SecurityLogger.logVersionableApplicationProviderError(ApplicationVersioningServiceImpl.this.logNames[i], appIdentifier, var7);
                  if (ApplicationVersioningServiceImpl.this.auditService.isAuditEnabled()) {
                     ApplicationVersioningServiceImpl.this.auditService.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.FAILURE, "Security Service", SecurityServiceManager.getCurrentSubject(ApplicationVersioningServiceImpl.kernelId), appIdentifier, "Create Application Version", var7));
                  }

                  throw new ApplicationVersionCreationException(var7);
               }
            }

            if (ApplicationVersioningServiceImpl.this.auditService.isAuditEnabled()) {
               ApplicationVersioningServiceImpl.this.auditService.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.SUCCESS, "Security Service", SecurityServiceManager.getCurrentSubject(ApplicationVersioningServiceImpl.kernelId), appIdentifier, "Create Application Version", (Exception)null));
            }

         } else {
            throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
         }
      }

      public void deleteApplicationVersion(String appIdentifier) throws ApplicationVersionRemovalException {
         if (appIdentifier != null && appIdentifier.length() != 0) {
            boolean debug = ApplicationVersioningServiceImpl.this.logger.isDebugEnabled();
            String method = debug ? this.getClass().getName() + ".deleteApplicationVersion" : null;
            if (debug) {
               ApplicationVersioningServiceImpl.this.logger.debug(method + "(" + appIdentifier + ")");
            }

            for(int i = 0; i < ApplicationVersioningServiceImpl.this.providers.length; ++i) {
               try {
                  ApplicationVersioningServiceImpl.this.providers[i].deleteApplicationVersion(appIdentifier);
               } catch (Exception var6) {
                  if (debug) {
                     ApplicationVersioningServiceImpl.this.logger.debug(method + " deleteApplicationVersion got an exception", var6);
                  }

                  SecurityLogger.logVersionableApplicationProviderError(ApplicationVersioningServiceImpl.this.logNames[i], appIdentifier, var6);
                  if (ApplicationVersioningServiceImpl.this.auditService.isAuditEnabled()) {
                     ApplicationVersioningServiceImpl.this.auditService.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.FAILURE, "Security Service", SecurityServiceManager.getCurrentSubject(ApplicationVersioningServiceImpl.kernelId), appIdentifier, "Delete Application Version", var6));
                  }

                  throw new ApplicationVersionRemovalException(var6);
               }
            }

            if (ApplicationVersioningServiceImpl.this.auditService.isAuditEnabled()) {
               ApplicationVersioningServiceImpl.this.auditService.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.SUCCESS, "Security Service", SecurityServiceManager.getCurrentSubject(ApplicationVersioningServiceImpl.kernelId), appIdentifier, "Delete Application Version", (Exception)null));
            }

         } else {
            throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
         }
      }

      public void deleteApplication(String appName) throws ApplicationRemovalException {
         if (appName != null && appName.length() != 0) {
            boolean debug = ApplicationVersioningServiceImpl.this.logger.isDebugEnabled();
            String method = debug ? this.getClass().getName() + ".deleteApplication" : null;
            if (debug) {
               ApplicationVersioningServiceImpl.this.logger.debug(method + "(" + appName + ")");
            }

            for(int i = 0; i < ApplicationVersioningServiceImpl.this.providers.length; ++i) {
               try {
                  ApplicationVersioningServiceImpl.this.providers[i].deleteApplication(appName);
               } catch (Exception var6) {
                  if (debug) {
                     ApplicationVersioningServiceImpl.this.logger.debug(method + " deleteApplication got an exception", var6);
                  }

                  SecurityLogger.logVersionableApplicationProviderError(ApplicationVersioningServiceImpl.this.logNames[i], appName, var6);
                  if (ApplicationVersioningServiceImpl.this.auditService.isAuditEnabled()) {
                     ApplicationVersioningServiceImpl.this.auditService.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.FAILURE, "Security Service", SecurityServiceManager.getCurrentSubject(ApplicationVersioningServiceImpl.kernelId), appName, "Delete Application", var6));
                  }

                  throw new ApplicationRemovalException(var6);
               }
            }

            if (ApplicationVersioningServiceImpl.this.auditService.isAuditEnabled()) {
               ApplicationVersioningServiceImpl.this.auditService.writeEvent(new AuditApplicationVersionEventImpl(AuditSeverity.SUCCESS, "Security Service", SecurityServiceManager.getCurrentSubject(ApplicationVersioningServiceImpl.kernelId), appName, "Delete Application", (Exception)null));
            }

         } else {
            throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
         }
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}
