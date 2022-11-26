package weblogic.security.service.internal;

interface ServletAuthenticationFilterServiceConfig {
   String getAuditServiceName();

   String[] getServletAuthenticationFilterProviderNames();
}
