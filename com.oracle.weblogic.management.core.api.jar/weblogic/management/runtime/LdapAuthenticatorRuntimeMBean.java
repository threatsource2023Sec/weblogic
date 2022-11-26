package weblogic.management.runtime;

import java.sql.Timestamp;

public interface LdapAuthenticatorRuntimeMBean extends AuthenticatorRuntimeMBean {
   long getUserCacheSize();

   long getUserCacheQueries();

   long getUserCacheHits();

   Timestamp getUserCacheStatStartTimeStamp();

   long getGroupCacheSize();

   long getGroupCacheQueries();

   long getGroupCacheHits();

   Timestamp getGroupCacheStatStartTimeStamp();
}
