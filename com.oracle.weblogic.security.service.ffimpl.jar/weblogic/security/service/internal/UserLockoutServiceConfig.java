package weblogic.security.service.internal;

interface UserLockoutServiceConfig {
   String getAuditServiceName();

   String getRealmName();

   String getServerName();

   boolean isLockoutEnabled();

   long getLockoutThreshold();

   long getLockoutDuration();

   long getLockoutResetDuration();

   long getLockoutGCThreshold();

   long getLockoutCacheSize();

   String getManagementIDD();
}
