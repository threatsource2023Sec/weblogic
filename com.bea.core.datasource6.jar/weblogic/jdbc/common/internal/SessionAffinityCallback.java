package weblogic.jdbc.common.internal;

import oracle.ucp.ConnectionAffinityCallback;
import weblogic.common.ResourceException;
import weblogic.jdbc.extensions.AffinityCallback;
import weblogic.utils.StackTraceUtils;

public final class SessionAffinityCallback implements AffinityCallback {
   private AffinityContextHelper affinityContextHelper = AffinityContextHelperFactory.createSessionAffinityContextHelper();
   private HAJDBCConnectionPool pool;

   SessionAffinityCallback(HAJDBCConnectionPool pool) {
      this.pool = pool;
   }

   public boolean setConnectionAffinityContext(Object affinityContext) {
      try {
         this.pool.initAffinityKeyIfNecessary();
      } catch (ResourceException var3) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var3));
         }

         return false;
      }

      String key = this.pool.getAffinityContextKey();
      return this.affinityContextHelper.setAffinityContext(key, affinityContext);
   }

   public Object getConnectionAffinityContext() {
      try {
         this.pool.initAffinityKeyIfNecessary();
      } catch (ResourceException var2) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var2));
         }

         return null;
      }

      String key = this.pool.getAffinityContextKey();
      return this.affinityContextHelper.getAffinityContext(key);
   }

   public ConnectionAffinityCallback.AffinityPolicy getAffinityPolicy() {
      return ConnectionAffinityCallback.AffinityPolicy.WEBSESSION_BASED_AFFINITY;
   }

   public boolean isApplicationContextAvailable() {
      return this.affinityContextHelper.isApplicationContextAvailable();
   }

   public void setAffinityPolicy(ConnectionAffinityCallback.AffinityPolicy policy) {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("setAffinityPolicy() policy=" + policy);
      }

   }

   private void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("SessionAffinityCallback: " + msg);
   }
}
