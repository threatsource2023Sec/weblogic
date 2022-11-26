package com.bea.core.security.managers;

import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.AuthorizationService;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.JAASAuthenticationService;
import com.bea.common.security.service.RoleMappingService;

public interface ManagerService extends Manager {
   void setIdentityService(IdentityService var1);

   void setAuthorizationService(AuthorizationService var1);

   void setAuditService(AuditService var1);

   void setRoleMappingService(RoleMappingService var1);

   void setJAASService(JAASAuthenticationService var1);

   void setCredentialMappingService(CredentialMappingService var1);

   void initialize();

   void destroy();
}
