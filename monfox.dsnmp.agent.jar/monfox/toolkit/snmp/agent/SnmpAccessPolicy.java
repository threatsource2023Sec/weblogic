package monfox.toolkit.snmp.agent;

import monfox.toolkit.snmp.SnmpOid;

public class SnmpAccessPolicy {
   public static final boolean READ_ONLY = false;
   public static final boolean READ_WRITE = true;
   private String a;
   private boolean b;
   private SnmpMibView c;

   public SnmpAccessPolicy(String var1, boolean var2, SnmpMibView var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public SnmpAccessPolicy(String var1, boolean var2) {
      this(var1, var2, new SnmpMibView());
   }

   protected SnmpAccessPolicy() {
   }

   public String getCommunity() {
      return this.a;
   }

   public boolean getAccessMode() {
      return this.b;
   }

   public void setAccessMode(boolean var1) {
      this.b = var1;
   }

   public SnmpMibView getView() {
      return this.c;
   }

   /** @deprecated */
   public boolean checkAccess(SnmpPendingIndication var1) {
      if (this.b) {
         return true;
      } else {
         return var1.getRequestType() != 163;
      }
   }

   /** @deprecated */
   public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpMibNode var3) {
      return this.c == null ? true : this.c.inView(var3.getOid());
   }

   public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpOid var3) {
      return this.c == null ? true : this.c.inView(var3);
   }

   public String toString() {
      StringBuffer var1;
      label11: {
         var1 = new StringBuffer();
         var1.append(a("\\\fI\u0013il\u0001A\u0010[_\rH\nKvX_\u0000Gb\u000fQ\rA{\u001b\u0019"));
         var1.append(this.a);
         var1.append(',');
         var1.append(a("n\u0001G\u0006[|/K\u0007M2"));
         if (this.b) {
            var1.append(a("]'e'wX0m7m"));
            if (!SnmpMibNode.b) {
               break label11;
            }
         }

         var1.append(a("]'e'w@,h:"));
      }

      var1.append(',');
      var1.append(a("y\u000bA\u0014\u0015"));
      var1.append(this.c);
      var1.append('}');
      return var1.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 15;
               break;
            case 1:
               var10003 = 98;
               break;
            case 2:
               var10003 = 36;
               break;
            case 3:
               var10003 = 99;
               break;
            default:
               var10003 = 40;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
