package weblogic.jdbc.extensions;

public interface ConnectionPoolFailoverCallback {
   String IF_NAME = "weblogic.jdbc.extensions.ConnectionPoolFailoverCallback";
   int OPCODE_CURR_POOL_DEAD = 0;
   int OPCODE_CURR_POOL_BUSY = 1;
   int OPCODE_REENABLE_CURR_POOL = 2;
   int OK = 0;
   int RETRY_CURRENT = 1;
   int DONOT_FAILOVER = 2;

   int allowPoolFailover(String var1, String var2, int var3);
}
