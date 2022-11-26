package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpVarBindList;

class j implements SnmpResponseListener {
   private SnmpJob a = null;
   private static final String b = "$Id: SnmpJob.java,v 1.4 2001/05/21 20:11:31 sking Exp $";

   j(SnmpJob var1) {
      this.a = var1;
   }

   public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      SnmpJobListener var5 = this.a.getJobListener();
      if (var5 != null && !this.a.isCancelled()) {
         var5.handleResponse(this.a, var1, var2, var3, var4);
      }

   }

   public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      SnmpJobListener var5 = this.a.getJobListener();
      if (var5 != null && !this.a.isCancelled()) {
         var5.handleReport(this.a, var1, var2, var3, var4);
      }

   }

   public void handleTimeout(SnmpPendingRequest var1) {
      SnmpJobListener var2 = this.a.getJobListener();
      if (var2 != null && !this.a.isCancelled()) {
         var2.handleTimeout(this.a, var1);
      }

   }

   public void handleException(SnmpPendingRequest var1, Exception var2) {
      SnmpJobListener var3 = this.a.getJobListener();
      if (var3 != null && !this.a.isCancelled()) {
         var3.handleException(this.a, var1, var2);
      }

   }
}
