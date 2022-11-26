package weblogic.jdbc.extensions;

import weblogic.diagnostics.instrumentation.EventPayload;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;

/** @deprecated */
@Deprecated
public class ProfileDataRecord implements EventPayload {
   public static final int TYPE_NONE = 0;
   public static final String TYPE_NONE_STR = "WEBLOGIC.JDBC.NONE";
   public static final int TYPE_ALL = 4095;
   public static final String TYPE_ALL_STR = "WEBLOGIC.JDBC.ALL";
   public static final int TYPE_CONN_USAGE = 1;
   public static final String TYPE_CONN_USAGE_STR = "WEBLOGIC.JDBC.CONN.USAGE";
   public static final int TYPE_CONN_RESV_WAIT = 2;
   public static final String TYPE_CONN_RESV_WAIT_STR = "WEBLOGIC.JDBC.CONN.RESV.WAIT";
   public static final int TYPE_CONN_LEAK = 4;
   public static final String TYPE_CONN_LEAK_STR = "WEBLOGIC.JDBC.CONN.LEAK";
   public static final int TYPE_CONN_RESV_FAIL = 8;
   public static final String TYPE_CONN_RESV_FAIL_STR = "WEBLOGIC.JDBC.CONN.RESV.FAIL";
   public static final int TYPE_STMT_CACHE_ENTRY = 16;
   public static final String TYPE_STMT_CACHE_ENTRY_STR = "WEBLOGIC.JDBC.STMT_CACHE.ENTRY";
   public static final int TYPE_STMT_USAGE = 32;
   public static final String TYPE_STMT_USAGE_STR = "WEBLOGIC.JDBC.STMT.USAGE";
   public static final int TYPE_CONN_LAST_USAGE = 64;
   public static final String TYPE_CONN_LAST_USAGE_STR = "WEBLOGIC.JDBC.CONN.LAST_USAGE";
   public static final int TYPE_CONN_MT_USAGE = 128;
   public static final String TYPE_CONN_MT_USAGE_STR = "WEBLOGIC.JDBC.CONN.MT_USAGE";
   private String poolName;
   private String id;
   private String timestamp;
   private String user;
   private String partitionId = "";
   private String partitionName = "";

   public ProfileDataRecord(String poolName, String id, String user, String timestamp) {
      this.poolName = poolName;
      this.id = id;
      this.user = user;
      this.timestamp = timestamp;
      ComponentInvocationContextManager compCtxMgr = null;

      try {
         compCtxMgr = ComponentInvocationContextManager.getInstance();
      } catch (NoClassDefFoundError var7) {
      } catch (ExceptionInInitializerError var8) {
      }

      if (compCtxMgr != null) {
         ComponentInvocationContext compCtx = compCtxMgr.getCurrentComponentInvocationContext();
         if (compCtx != null) {
            this.partitionId = compCtx.getPartitionId();
            this.partitionName = compCtx.getPartitionName();
         }
      }

   }

   public String getPoolName() {
      return this.poolName;
   }

   public String getId() {
      return this.id;
   }

   public String getTimestamp() {
      return this.timestamp;
   }

   public String getUser() {
      return this.user;
   }

   public String getPartitionId() {
      return this.partitionId;
   }

   public String getPartitionName() {
      return this.partitionName;
   }
}
