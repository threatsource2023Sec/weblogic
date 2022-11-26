package weblogic.security.service.internal;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.PrincipalValidationService;
import java.security.AccessController;
import javax.security.auth.Subject;
import weblogic.security.SecurityLogger;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class WLSIdentityServiceImpl implements IdentityService {
   private LoggerSpi logger;
   private static AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private AuditService auditService;
   private PrincipalValidationService principalValidationService;
   private Identity anonymousIdentity;

   public void initialize(String auditServiceName, String principalValidationServiceName, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityAtn");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityAtn"));
      } else {
         this.auditService = (AuditService)dependentServices.getService(auditServiceName);
         this.principalValidationService = (PrincipalValidationService)dependentServices.getService(principalValidationServiceName);
         this.anonymousIdentity = new WLSIdentityImpl(SubjectUtils.getAnonymousSubject());
         if (this.logger.isDebugEnabled()) {
            String var4 = this.getClass().getName() + ".constructor";
         }

      }
   }

   public Identity getIdentityFromSubject(Subject subject) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getIdentityFromSubject" : null;
      if (debug) {
         this.logger.debug(method + " " + SubjectUtils.displaySubject(subject));
      }

      this.principalValidationService.sign(subject.getPrincipals());
      return new WLSIdentityImpl(new AuthenticatedSubject(subject));
   }

   public Identity getAnonymousIdentity() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getAnonymousIdentity" : null;
      if (debug) {
         this.logger.debug(method);
      }

      return this.anonymousIdentity;
   }

   public Identity getCurrentIdentity() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getCurrentIdentity" : null;
      if (debug) {
         this.logger.debug(method);
      }

      return new WLSIdentityImpl(SecurityServiceManager.getCurrentSubject(kernelID));
   }
}
