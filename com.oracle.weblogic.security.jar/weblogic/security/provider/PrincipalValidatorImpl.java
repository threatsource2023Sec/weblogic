package weblogic.security.provider;

import com.bea.common.logger.spi.LoggerSpi;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import weblogic.management.security.RealmMBean;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.PrincipalValidator;
import weblogic.utils.LocatorUtilities;

/** @deprecated */
@Deprecated
public class PrincipalValidatorImpl implements PrincipalValidator {
   private static LoggerSpi log = new LoggerAdapter(LoggerWrapper.getInstance("SecurityAtn"));
   private com.bea.common.security.provider.PrincipalValidatorImpl delegate = null;

   public PrincipalValidatorImpl() {
      SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
         public SecurityRuntimeAccess run() {
            return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
         }
      });
      byte[] secret = runtimeAccess.getDomain().getSecurityConfiguration().getCredential().getBytes();
      RealmMBean realmMBean = runtimeAccess.getDomain().getSecurityConfiguration().getDefaultRealm();
      boolean cacheEnabled = realmMBean.isEnableWebLogicPrincipalValidatorCache();
      int sigCacheSize = 500;
      if (cacheEnabled) {
         sigCacheSize = realmMBean.getMaxWebLogicPrincipalsInCache();
         if (sigCacheSize <= 0) {
            sigCacheSize = 500;
         }
      }

      if (log.isDebugEnabled()) {
         log.debug("Initializing principal validator delegate");
      }

      this.delegate = new com.bea.common.security.provider.PrincipalValidatorImpl(log, secret, cacheEnabled, sigCacheSize);
   }

   public boolean validate(Principal principal) throws SecurityException {
      return this.delegate.validate(principal);
   }

   public boolean sign(Principal principal) {
      return this.delegate.sign(principal);
   }

   public Class getPrincipalBaseClass() {
      return this.delegate.getPrincipalBaseClass();
   }

   private static class LoggerAdapter implements LoggerSpi {
      private LoggerWrapper logger;

      LoggerAdapter(LoggerWrapper log) {
         this.logger = log;
      }

      public boolean isDebugEnabled() {
         return this.logger.isDebugEnabled();
      }

      public void debug(Object msg) {
         this.logger.debug(msg);
      }

      public void debug(Object msg, Throwable th) {
         this.logger.debug(msg, th);
      }

      public void info(Object msg) {
         this.logger.info(msg);
      }

      public void info(Object msg, Throwable th) {
         this.logger.info(msg, th);
      }

      public void warn(Object msg) {
         this.logger.warn(msg);
      }

      public void warn(Object msg, Throwable th) {
         this.logger.warn(msg, th);
      }

      public void error(Object msg) {
         this.logger.error(msg);
      }

      public void error(Object msg, Throwable th) {
         this.logger.error(msg, th);
      }

      public void severe(Object msg) {
         this.logger.severe(msg);
      }

      public void severe(Object msg, Throwable th) {
         this.logger.severe(msg, th);
      }
   }
}
