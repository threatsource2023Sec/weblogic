package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpVarBindList;

class h implements SnmpResponseListener {
   private SnmpExplorer a = null;
   private static final String b = "$Id: SnmpExplorer.java,v 1.21 2004/04/28 15:07:08 sking Exp $";

   h(SnmpExplorer var1) {
      this.a = var1;
   }

   public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      this.a.a(var1, var2, var3, var4);
   }

   public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      this.a.b(var1, var2, var3, var4);
   }

   public void handleTimeout(SnmpPendingRequest var1) {
      this.a.a(var1);
   }

   public void handleException(SnmpPendingRequest var1, Exception var2) {
      this.a.a(var1, var2);
   }
}
