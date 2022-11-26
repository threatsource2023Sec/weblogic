package monfox.toolkit.snmp.ext;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpPendingRequest;
import monfox.toolkit.snmp.mgr.SnmpResponseListener;

class e implements SnmpResponseListener, SnmpObjectSetListener {
   static Logger a = Logger.getInstance(a("67\u0007\"\n"), a("7<\u001d"), a("!\n$\u001f\u000e\u0013\u0006%\n"));
   private SnmpTable b = null;
   private static final String c = "$Id: SnmpTable.java,v 1.21 2013/01/24 17:05:23 sking Exp $";

   e(SnmpTable var1) {
      this.b = var1;
   }

   public void handleUpdated(SnmpObjectSet var1, int[] var2) {
      this.b.a((SnmpRow)var1, var2);
   }

   public void handleError(SnmpObjectSet var1, SnmpError var2) {
      this.b.a(var2);
   }

   public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      this.b.a(var1, var2, var3, var4);
   }

   public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      var1.cancel();
      this.b.b(var1, var2, var3, var4);
   }

   public void handleTimeout(SnmpPendingRequest var1) {
      var1.cancel();
      this.b.a(var1);
   }

   public void handleException(SnmpPendingRequest var1, Exception var2) {
      var1.cancel();
      this.b.a(var1, var2);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 114;
               break;
            case 1:
               var10003 = 100;
               break;
            case 2:
               var10003 = 73;
               break;
            case 3:
               var10003 = 111;
               break;
            default:
               var10003 = 90;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
