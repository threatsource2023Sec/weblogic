package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpVarBindList;

public interface SnmpResponseListener {
   void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4);

   void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4);

   void handleTimeout(SnmpPendingRequest var1);

   void handleException(SnmpPendingRequest var1, Exception var2);
}
