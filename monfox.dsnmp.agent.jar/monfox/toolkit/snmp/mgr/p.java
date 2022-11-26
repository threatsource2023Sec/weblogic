package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.util.WorkItem;

class p extends WorkItem {
   private boolean a;
   private SnmpPendingRequest b;
   private SnmpRequestPDU c;

   public p(SnmpPendingRequest var1, SnmpRequestPDU var2) {
      this(var1, var2, false);
   }

   public p(SnmpPendingRequest var1, SnmpRequestPDU var2, boolean var3) {
      this.a = false;
      this.b = null;
      this.c = null;
      this.c = var2;
      this.b = var1;
      this.a = var3;
   }

   public void perform() {
      SnmpResponseListener var1 = this.b.getResponseListener();

      try {
         int var2 = this.c == null ? this.b.getErrorStatus() : this.c.getErrorStatus();
         int var3 = this.c == null ? this.b.getErrorIndex() : this.c.getErrorIndex();
         SnmpVarBindList var4 = this.c == null ? this.b.getResponseVarBindList() : this.c.getVarBindList();
         if (this.a) {
            this.b.a();
         }

         if (var1 != null) {
            if (this.c != null && this.c.getType() == 168) {
               var1.handleReport(this.b, var2, var3, var4);
               if (!SnmpSession.B) {
                  return;
               }
            }

            var1.handleResponse(this.b, var2, var3, var4);
         }
      } catch (Exception var5) {
         if (var1 != null) {
            var1.handleException(this.b, var5);
         }
      }

   }
}
