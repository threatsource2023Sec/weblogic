package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpVarBindList;

public interface SnmpJobListener {
   void handleResponse(SnmpJob var1, SnmpPendingRequest var2, int var3, int var4, SnmpVarBindList var5);

   void handleReport(SnmpJob var1, SnmpPendingRequest var2, int var3, int var4, SnmpVarBindList var5);

   void handleTimeout(SnmpJob var1, SnmpPendingRequest var2);

   void handleException(SnmpJob var1, SnmpPendingRequest var2, Exception var3);

   void handleStart(SnmpJob var1, SnmpPendingRequest var2);
}
