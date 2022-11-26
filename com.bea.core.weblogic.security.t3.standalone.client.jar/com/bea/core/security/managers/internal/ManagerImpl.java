package com.bea.core.security.managers.internal;

import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.AuthorizationService;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.JAASAuthenticationService;
import com.bea.common.security.service.RoleMappingService;
import com.bea.core.security.managers.CEO;
import com.bea.core.security.managers.ManagerService;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditEvent;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;

public class ManagerImpl implements ManagerService {
   private IdentityService identityService;
   private AuthorizationService authorizationService;
   private AuditService auditService;
   private RoleMappingService roleMapper;
   private JAASAuthenticationService jaasService;
   private CredentialMappingService credMapService;

   public void setIdentityService(IdentityService service) {
      this.identityService = service;
   }

   public void setAuthorizationService(AuthorizationService service) {
      this.authorizationService = service;
   }

   public void setAuditService(AuditService service) {
      this.auditService = service;
   }

   public void setRoleMappingService(RoleMappingService service) {
      this.roleMapper = service;
   }

   public void setJAASService(JAASAuthenticationService service) {
      this.jaasService = service;
   }

   public void setCredentialMappingService(CredentialMappingService service) {
      this.credMapService = service;
   }

   public void initialize() {
      CEO.setManager(this);
   }

   public void destroy() {
   }

   private Identity AStoID(AuthenticatedSubject as) {
      Subject subject = as.getSubject();
      return subject == null ? null : this.identityService.getIdentityFromSubject(subject);
   }

   public AuthenticatedSubject getCurrentIdentity() {
      Identity identity = this.identityService.getCurrentIdentity();
      return identity == null ? new AuthenticatedSubject() : new AuthenticatedSubject(identity.getSubject());
   }

   public boolean isAccessAllowed(AuthenticatedSubject aSubject, Map roles, Resource resource, ContextHandler handler, Direction direction) {
      Identity identity = this.AStoID(aSubject);
      return identity == null ? false : this.authorizationService.isAccessAllowed(identity, roles, resource, handler, direction);
   }

   public void writeEvent(AuditEvent event) {
      this.auditService.writeEvent(event);
   }

   public Map getRoles(AuthenticatedSubject aSubject, Resource resource, ContextHandler handler) {
      Identity identity = this.AStoID(aSubject);
      return (Map)(identity == null ? new HashMap() : this.roleMapper.getRoles(identity, resource, handler));
   }

   public AuthenticatedSubject authenticate(CallbackHandler callbackHandler, ContextHandler contextHandler) throws LoginException {
      Identity identity = this.jaasService.authenticate(callbackHandler, contextHandler);
      Subject subject = identity.getSubject();
      return new AuthenticatedSubject(subject);
   }

   public Object[] getCredentials(AuthenticatedSubject requestor, AuthenticatedSubject initiator, Resource resource, ContextHandler handler, String credType) {
      Identity rId = this.AStoID(requestor);
      Identity iId = this.AStoID(initiator);
      return rId != null && iId != null ? this.credMapService.getCredentials(rId, iId, resource, handler, credType) : new Object[0];
   }

   public Object[] getCredentials(AuthenticatedSubject requestor, String initiator, Resource resource, ContextHandler handler, String credType) {
      Identity rId = this.AStoID(requestor);
      return rId == null ? new Object[0] : this.credMapService.getCredentials(rId, initiator, resource, handler, credType);
   }
}
