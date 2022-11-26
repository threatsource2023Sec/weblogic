package weblogic.jdbc.common.rac.internal;

import oracle.ucp.ConnectionAffinityCallback;
import oracle.ucp.jdbc.oracle.DataBasedConnectionAffinityCallback;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.extensions.DataAffinityCallback;

public class UCPDataAffinityCallbackImpl extends UCPAffinityCallbackImpl implements DataBasedConnectionAffinityCallback {
   UCPDataAffinityCallbackImpl(ConnectionAffinityCallback.AffinityPolicy ucpAffinityPolicy, UCPRACModuleImpl racModule) {
      super(ucpAffinityPolicy, racModule);
   }

   public int getPartitionId() {
      DataAffinityCallback cb = this.racModule.getPool().getDataAffinityCallback();
      if (cb == null) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getPartitionId() no data affinity callback");
         }

         throw new AssertionError("no WL data affinity callback delegate");
      } else {
         return cb.getPartitionId();
      }
   }

   public boolean setDataKey(Object keyObject) {
      DataAffinityCallback cb = this.racModule.getPool().getDataAffinityCallback();
      if (cb == null) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getPartitionId() no data affinity callback");
         }

         throw new AssertionError("no WL data affinity callback delegate");
      } else {
         return cb.setDataKey(keyObject);
      }
   }

   private void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("UCPDataAffinityCallbackImpl [" + this.racModule.getPoolName() + "]: " + msg);
   }
}
