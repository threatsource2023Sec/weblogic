package weblogic.jdbc.common.internal;

import oracle.ucp.ConnectionAffinityCallback;
import weblogic.common.ResourceException;
import weblogic.jdbc.extensions.AffinityCallback;
import weblogic.transaction.Transaction;
import weblogic.utils.StackTraceUtils;

public class XAAffinityCallback implements AffinityCallback {
   AffinityContextHelper affinityContextHelper = AffinityContextHelperFactory.createXAAffinityContextHelper();
   HAJDBCConnectionPool pool;

   XAAffinityCallback(HAJDBCConnectionPool pool) {
      this.pool = pool;
   }

   public Object getConnectionAffinityContext() {
      if (!this.pool.isXA()) {
         return null;
      } else {
         try {
            this.pool.initAffinityKeyIfNecessary();
         } catch (ResourceException var2) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug(StackTraceUtils.throwable2StackTrace(var2));
            }
         }

         String key = this.pool.getAffinityContextKey();
         return this.affinityContextHelper.getAffinityContext(key);
      }
   }

   public ConnectionAffinityCallback.AffinityPolicy getAffinityPolicy() {
      return ConnectionAffinityCallback.AffinityPolicy.TRANSACTION_BASED_AFFINITY;
   }

   public boolean isApplicationContextAvailable() {
      return this.affinityContextHelper.isApplicationContextAvailable();
   }

   public boolean setConnectionAffinityContext(Object affinityContext) {
      if (!this.pool.isXA()) {
         return false;
      } else {
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
   }

   public void setAffinityPolicy(ConnectionAffinityCallback.AffinityPolicy policy) {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("setAffinityPolicy() policy=" + policy);
      }

   }

   public Object getConnectionAffinityContext(Transaction tx) {
      if (!this.pool.isXA()) {
         return null;
      } else {
         try {
            this.pool.initAffinityKeyIfNecessary();
         } catch (ResourceException var3) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug(StackTraceUtils.throwable2StackTrace(var3));
            }
         }

         String key = this.pool.getAffinityContextKey();
         return ((XAAffinityContextHelper)this.affinityContextHelper).getAffinityContext(tx, key);
      }
   }

   public boolean setConnectionAffinityContext(Transaction tx, Object affinityContext) {
      if (!this.pool.isXA()) {
         return false;
      } else {
         try {
            this.pool.initAffinityKeyIfNecessary();
         } catch (ResourceException var4) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug(StackTraceUtils.throwable2StackTrace(var4));
            }

            return false;
         }

         String key = this.pool.getAffinityContextKey();
         return ((XAAffinityContextHelper)this.affinityContextHelper).setAffinityContext(tx, key, affinityContext);
      }
   }

   private void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("XAAffinityCallback [" + this.pool.getName() + "]: " + msg);
   }
}
