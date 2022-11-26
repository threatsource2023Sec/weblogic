package monfox.toolkit.snmp.ext;

import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpPendingRequest;
import monfox.toolkit.snmp.mgr.SnmpResponseListener;

class b implements SnmpResponseListener {
   private SnmpObjectGroup a = null;
   private static final String b = "$Id: SnmpObjectGroup.java,v 1.10 2008/11/25 13:31:52 sking Exp $";

   b(SnmpObjectGroup var1) {
      this.a = var1;
   }

   public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      this.a.a(var1, var2, var3, var4);
   }

   public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      var1.cancel();
      this.a.b(var1, var2, var3, var4);
   }

   public void handleTimeout(SnmpPendingRequest var1) {
      var1.cancel();
      this.a.a(var1);
   }

   public void handleException(SnmpPendingRequest var1, Exception var2) {
      var1.cancel();
      this.a.a(var1, var2);
   }
}
