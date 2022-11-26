package weblogic.security.service;

import com.bea.common.security.service.AuditService;
import weblogic.management.security.ProviderMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.AuditEvent;

public class AuditorImpl implements SecurityService, Auditor {
   private boolean initialized = false;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityAuditor");
   private AuditService auditService = null;

   public AuditorImpl() {
   }

   public AuditorImpl(AuditService auditService) {
      this.auditService = auditService;
      this.initialized = true;
   }

   public AuditorImpl(String realmName, ProviderMBean[] configuration) {
      throw new IllegalStateException("Should not be getting called with CSS enabled");
   }

   public void initialize(String realmName, ProviderMBean[] configuration) throws InvalidParameterException, ProviderException {
      throw new IllegalStateException("Should not be getting called with CSS enabled");
   }

   public void shutdown() {
      if (this.auditService != null) {
         if (log.isDebugEnabled()) {
            log.debug("Auditor shutdown noop for common audit service");
         }

         this.auditService = null;
      }
   }

   public void start() {
      if (this.auditService != null) {
         if (log.isDebugEnabled()) {
            log.debug("Auditor start noop for common audit service");
         }

      }
   }

   public void suspend() {
      if (this.auditService != null) {
         if (log.isDebugEnabled()) {
            log.debug("Auditor suspend noop for common audit service");
         }

      }
   }

   public void writeEvent(AuditEvent event) throws NotYetInitializedException {
      if (!this.initialized) {
         throw new NotYetInitializedException(SecurityLogger.getAuditorNotInitialized());
      } else if (this.auditService != null) {
         if (log.isDebugEnabled()) {
            log.debug("Auditor writeEvent using common auditService");
         }

         this.auditService.writeEvent(event);
      }
   }
}
