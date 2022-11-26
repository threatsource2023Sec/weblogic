package weblogic.security.service.internal;

interface ApplicationVersioningServiceConfig {
   String getAuditServiceName();

   String[] getVersionableApplicationProviderNames();
}
