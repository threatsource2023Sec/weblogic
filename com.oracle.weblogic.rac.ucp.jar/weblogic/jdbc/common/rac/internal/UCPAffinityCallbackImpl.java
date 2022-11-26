package weblogic.jdbc.common.rac.internal;

import oracle.ucp.ConnectionAffinityCallback;
import oracle.ucp.ConnectionAffinityCallback.AffinityPolicy;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.extensions.AffinityCallback;

public class UCPAffinityCallbackImpl implements ConnectionAffinityCallback {
   private ThreadLocal policyInEffect = new ThreadLocal() {
      protected ConnectionAffinityCallback.AffinityPolicy initialValue() {
         return UCPAffinityCallbackImpl.this.affinityPolicy;
      }
   };
   private ConnectionAffinityCallback.AffinityPolicy affinityPolicy;
   protected UCPRACModuleImpl racModule;

   UCPAffinityCallbackImpl(ConnectionAffinityCallback.AffinityPolicy ucpAffinityPolicy, UCPRACModuleImpl racModule) {
      this.affinityPolicy = ucpAffinityPolicy;
      this.racModule = racModule;
   }

   public ConnectionAffinityCallback.AffinityPolicy getAffinityPolicy() {
      ConnectionAffinityCallback.AffinityPolicy policy = (ConnectionAffinityCallback.AffinityPolicy)this.policyInEffect.get();
      return policy != null ? policy : this.affinityPolicy;
   }

   public Object getConnectionAffinityContext() {
      AffinityCallback cb = null;
      Object context = null;
      switch (this.getAffinityPolicy()) {
         case DATA_BASED_AFFINITY:
            AffinityCallback cb = this.racModule.getPool().getDataAffinityCallback();
            if (cb == null) {
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("getConnectionAffinityContext() no delegate data affinity callback");
               }
            } else {
               context = cb.getConnectionAffinityContext();
            }
            break;
         case WEBSESSION_BASED_AFFINITY:
            cb = this.racModule.getPool().getSessionAffinityCallback();
            context = cb.getConnectionAffinityContext();
            break;
         case TRANSACTION_BASED_AFFINITY:
            cb = this.racModule.getPool().getXAAffinityCallback();
            context = cb.getConnectionAffinityContext();
      }

      return context;
   }

   public void setAffinityPolicy(ConnectionAffinityCallback.AffinityPolicy policy) {
      this.policyInEffect.set(policy);
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("setAffinityPolicy() policy=" + policy);
      }

   }

   public boolean setConnectionAffinityContext(Object context) {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("setConnectionAffinityContext() policy=" + this.affinityPolicy + ", context=" + context);
      }

      AffinityCallback cb = null;
      switch (this.getAffinityPolicy()) {
         case DATA_BASED_AFFINITY:
            cb = this.racModule.getPool().getDataAffinityCallback();
            break;
         case WEBSESSION_BASED_AFFINITY:
            cb = this.racModule.getPool().getSessionAffinityCallback();
            break;
         case TRANSACTION_BASED_AFFINITY:
            cb = this.racModule.getPool().getXAAffinityCallback();
      }

      return ((AffinityCallback)cb).setConnectionAffinityContext(context);
   }

   private boolean isSessionPreferredPolicy() {
      return this.racModule.getPool().getAffinityPolicy() == AffinityPolicy.WEBSESSION_BASED_AFFINITY;
   }

   private void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("UCPAffinityCallbackImpl [" + this.racModule.getPoolName() + "]: " + msg);
   }
}
