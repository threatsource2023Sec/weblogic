package weblogic.security;

import java.sql.Timestamp;
import weblogic.management.ManagementException;
import weblogic.management.runtime.LdapAuthenticatorRuntimeMBean;
import weblogic.management.runtime.RealmRuntimeMBean;
import weblogic.security.utils.CacheStatistics;

public final class LdapAuthenticatorRuntime extends AuthenticatorRuntimeMBeanImpl implements LdapAuthenticatorRuntimeMBean {
   private CacheStatistics userCacheStats;
   private CacheStatistics groupCacheStats;

   public LdapAuthenticatorRuntime(String providerName, RealmRuntimeMBean realmRuntime, CacheStatistics userCacheStats, CacheStatistics groupCacheStats) throws ManagementException {
      super(providerName, realmRuntime);
      this.userCacheStats = userCacheStats;
      this.groupCacheStats = groupCacheStats;
   }

   public long getUserCacheSize() {
      return this.userCacheStats != null ? this.userCacheStats.getCacheEntries() : 0L;
   }

   public long getUserCacheQueries() {
      return this.userCacheStats != null ? this.userCacheStats.getCacheQueries() : 0L;
   }

   public long getUserCacheHits() {
      return this.userCacheStats != null ? this.userCacheStats.getCacheHits() : 0L;
   }

   public Timestamp getUserCacheStatStartTimeStamp() {
      return this.userCacheStats != null ? this.userCacheStats.getStatStartTimeStamp() : null;
   }

   public long getGroupCacheSize() {
      return this.groupCacheStats != null ? this.groupCacheStats.getCacheEntries() : 0L;
   }

   public long getGroupCacheQueries() {
      return this.groupCacheStats != null ? this.groupCacheStats.getCacheQueries() : 0L;
   }

   public long getGroupCacheHits() {
      return this.groupCacheStats != null ? this.groupCacheStats.getCacheHits() : 0L;
   }

   public Timestamp getGroupCacheStatStartTimeStamp() {
      return this.groupCacheStats != null ? this.groupCacheStats.getStatStartTimeStamp() : null;
   }
}
