package weblogic.security.securityapi;

import java.io.Serializable;
import java.security.AccessController;
import java.security.Principal;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.soteria.authorization.spi.CallerDetailsResolver;
import org.glassfish.soteria.authorization.spi.ResourceAccessResolver;
import org.glassfish.soteria.mechanisms.jaspic.Jaspic;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.Auditor;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.AuditAtnEventV2;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.security.internal.WebAppContextHandler;

public class WlsSecurityContextImpl implements SecurityContext, Serializable {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String REALM_NAME = SecurityServiceManager.getContextSensitiveRealmName();
   private CallerDetailsResolver callerDetailsResolver;
   private ResourceAccessResolver resourceAccessResolver;
   private static Auditor auditor = null;
   @Inject
   private HttpServletRequest req;

   @PostConstruct
   public void init() {
      this.callerDetailsResolver = new WlsCallerDetailsResolver(this.req);
      this.resourceAccessResolver = new WlsResourceAccessResolver(this.req);
   }

   public static AuthenticatedSubject getCurrentSubject() {
      return SecurityServiceManager.getCurrentSubject(KERNEL_ID);
   }

   public Principal getCallerPrincipal() {
      return this.callerDetailsResolver.getCallerPrincipal();
   }

   public Set getPrincipalsByType(Class pType) {
      return this.callerDetailsResolver.getPrincipalsByType(pType);
   }

   public boolean isCallerInRole(String role) {
      return this.callerDetailsResolver.isCallerInRole(role);
   }

   public boolean hasAccessToWebResource(String resource, String... methods) {
      return this.resourceAccessResolver.hasAccessToWebResource(resource, methods);
   }

   public AuthenticationStatus authenticate(HttpServletRequest request, HttpServletResponse response, AuthenticationParameters parameters) {
      try {
         if (Jaspic.authenticate(request, response, parameters)) {
            this.auditAtn(true, (Exception)null);
            return AuthenticationStatus.SUCCESS;
         } else {
            this.auditAtn(false, (Exception)null);
            return Jaspic.getLastAuthenticationStatus(request);
         }
      } catch (IllegalArgumentException var5) {
         var5.printStackTrace();
         SecurityLogger.logWlsSecurityContextAuthenticateException(var5);
         this.auditAtn(false, var5);
         return AuthenticationStatus.SEND_FAILURE;
      }
   }

   private void auditAtn(boolean success, Exception exception) {
      Auditor auditor = this.getAuditor();
      if (auditor != null) {
         auditor.writeEvent(this.generateAuditAtnEvent(success, exception));
      }

   }

   private Auditor getAuditor() {
      if (auditor != null) {
         return auditor;
      } else {
         auditor = (Auditor)SecurityServiceManager.getSecurityService(KERNEL_ID, REALM_NAME, ServiceType.AUDIT);
         SecurityLogger.logWlsSecurityContextInitAuditor(REALM_NAME);
         return auditor;
      }
   }

   private AuditAtnEventV2 generateAuditAtnEvent(boolean success, Exception exception) {
      HttpServletResponse rsp = null;
      if (this.req instanceof ServletRequestImpl) {
         rsp = ((ServletRequestImpl)this.req).getResponse();
      }

      AuditAtnEventV2 atnAuditEvent = new JSR375AuditAtnEvent(success ? AuditSeverity.SUCCESS : AuditSeverity.FAILURE, "", new WebAppContextHandler(this.req, rsp), AtnEventTypeV2.AUTHENTICATE, exception);
      return atnAuditEvent;
   }
}
