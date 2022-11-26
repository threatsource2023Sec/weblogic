package weblogic.security.service.internal;

public interface WLSJAASLoginServiceConfig {
   String getAuditServiceName();

   String getJAASLoginServiceName();

   String getUserLockoutRuntimeServiceName();
}
