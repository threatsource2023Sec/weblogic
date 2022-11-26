package weblogic.security;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SecurityConfigurationValidator;
import weblogic.security.net.ConnectionFilter;
import weblogic.security.net.ConnectionFilterService;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityServiceRuntimeException;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.utils.CertPathTrustManagerUtils;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.annotation.Secure;

@Service
@Named
@RunLevel(10)
@Secure
public class SecurityService extends AbstractServerService {
   @Inject
   @Named("EmbeddedLDAP")
   private ServerService dependencyOnEmbeddedLDAP;
   @Inject
   @Named("PreSecurityService")
   public PreSecurityService s2;
   private static SecurityService singleton = null;
   private SecurityConfigurationMBean secCfgMbean = null;
   private RuntimeMBean runtime;
   private SecurityServiceManager securityServiceManager = null;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityService");
   private static boolean enableConnectionFilter = false;
   private static boolean enableConnectionLogger = false;
   private static boolean enableCompatibilityFilters = false;
   private static Object filterObject;
   private static String filterClass;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private SecurityConfigurationValidator securityConfigurationValidator = null;
   private boolean isPerfDebug;

   public SecurityService() {
      this.isPerfDebug = PreSecurityService.isPerfDebug;
      if (singleton != null) {
         throw new InternalError(SecurityLogger.getSecurityAlreadyConfigured());
      } else {
         singleton = this;
         this.securityConfigurationValidator = SecurityConfigurationValidator.getInstance();
      }
   }

   public static SecurityService getSecurityService() {
      return singleton;
   }

   public void start() throws ServiceFailureException {
      try {
         long startTime = 0L;
         long currentTime = 0L;
         long runTime = 0L;
         if (this.isPerfDebug) {
            startTime = System.currentTimeMillis();
         }

         if (this.s2 == null) {
            this.s2 = PreSecurityService.getSingleton();
         }

         this.secCfgMbean = this.s2.getSecurityConfigurationMBean(kernelId);
         if (this.secCfgMbean == null) {
            throw new SecurityServiceRuntimeException(SecurityLogger.getSecConfigUnavailable());
         } else {
            SecurityServiceManager secmgr = this.s2.getSecurityServiceManager(kernelId);
            secmgr.postInitialize(kernelId);
            if (this.isPerfDebug) {
               currentTime = System.currentTimeMillis();
               runTime = currentTime - startTime;
               startTime = currentTime;
               PreSecurityService.dbgPsr("SecurityService start - post init = " + runTime);
            }

            this.securityServiceManager = secmgr;
            if (this.isPerfDebug) {
               currentTime = System.currentTimeMillis();
               runTime = currentTime - startTime;
               startTime = currentTime;
               PreSecurityService.dbgPsr("SecurityService start - secmgr.initialize = " + runTime);
            }

            this.initializeRuntimeMBeans();
            this.securityConfigurationValidator.start();
            if (this.isPerfDebug) {
               currentTime = System.currentTimeMillis();
               runTime = currentTime - startTime;
               PreSecurityService.dbgPsr("SecurityService start - securityConfigurationValidator.start = " + runTime);
            }

         }
      } catch (SecurityServiceRuntimeException var8) {
         throw new ServiceFailureException(var8);
      } catch (RuntimeException var9) {
         throw var9;
      } catch (Exception var10) {
         SecurityLogger.logStackTrace(var10);
         throw new ServiceFailureException(var10);
      }
   }

   public void stop() throws ServiceFailureException {
      this.securityConfigurationValidator.stop();
      CertPathTrustManagerUtils.stop();
   }

   public void halt() throws ServiceFailureException {
      this.securityConfigurationValidator.halt();
      CertPathTrustManagerUtils.halt();
   }

   public static final boolean getConnectionFilterEnabled() {
      return enableConnectionFilter;
   }

   public static final void setConnectionFilter(ConnectionFilter newFilterObject) {
      filterObject = newFilterObject;
   }

   public static final ConnectionFilter getConnectionFilter() {
      return (ConnectionFilter)filterObject;
   }

   static final void setFilterClass(String fltClass) {
      filterClass = fltClass;
   }

   static final String getFilterClass() {
      return filterClass;
   }

   public static final boolean getConnectionLoggerEnabled() {
      return enableConnectionLogger;
   }

   public static final boolean getCompatibilityConnectionFiltersEnabled() {
      return enableCompatibilityFilters;
   }

   static void setEnableConnectionFilter(boolean enableConnFltr) {
      enableConnectionFilter = enableConnFltr;
   }

   static void setConnectionLoggerEnabled(boolean connLogEnabled) {
      enableConnectionLogger = connLogEnabled;
   }

   static void setCompatibilityConnectionFiltersEnabled(boolean compConnFlterEnabled) {
      enableCompatibilityFilters = compConnFlterEnabled;
   }

   private void initializeRuntimeMBeans() {
      try {
         new SingleSignOnServicesRuntime();
      } catch (ManagementException var2) {
         SecurityLogger.logErrorCreatingSecurityRuntime(var2);
      }

   }

   @Service
   private static final class ConnectionFilterServiceImpl implements ConnectionFilterService {
      public boolean getConnectionFilterEnabled() {
         return SecurityService.getConnectionFilterEnabled();
      }

      public ConnectionFilter getConnectionFilter() {
         return SecurityService.getConnectionFilter();
      }

      public void setConnectionFilter(ConnectionFilter newFilterObject) {
         SecurityService.setConnectionFilter(newFilterObject);
      }

      public boolean getConnectionLoggerEnabled() {
         return SecurityService.getConnectionLoggerEnabled();
      }

      public boolean getCompatibilityConnectionFiltersEnabled() {
         return SecurityService.getCompatibilityConnectionFiltersEnabled();
      }
   }
}
