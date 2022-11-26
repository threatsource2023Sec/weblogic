package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpVarBindList;

public interface SnmpExplorerListener {
   void handleDiscovered(SnmpExplorer var1, SnmpPeer var2, SnmpParameters var3, int var4, int var5, SnmpVarBindList var6);

   void handleCompleted(SnmpExplorer var1);

   void handleCancelled(SnmpExplorer var1);
}
