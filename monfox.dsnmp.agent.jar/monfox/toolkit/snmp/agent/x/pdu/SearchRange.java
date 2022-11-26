package monfox.toolkit.snmp.agent.x.pdu;

import monfox.toolkit.snmp.SnmpOid;

public class SearchRange {
   private SnmpOid a = null;
   private SnmpOid b = null;
   private boolean c = false;

   public SearchRange(SnmpOid var1, boolean var2, SnmpOid var3) {
      this.a = var1;
      this.b = var3;
      this.c = var2;
   }

   public SnmpOid getStart() {
      return this.a;
   }

   public SnmpOid getEnd() {
      return this.b;
   }

   public boolean isInclude() {
      return this.c;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      var1.append("(" + this.a + a("v\u000e") + (this.c ? a("\";\\") : a(".-\\")) + a("vk") + this.b + ")");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 75;
               break;
            case 1:
               var10003 = 85;
               break;
            case 2:
               var10003 = 63;
               break;
            case 3:
               var10003 = 18;
               break;
            default:
               var10003 = 92;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
