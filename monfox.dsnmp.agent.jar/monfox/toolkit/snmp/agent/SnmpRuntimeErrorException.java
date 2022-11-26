package monfox.toolkit.snmp.agent;

import monfox.toolkit.snmp.Snmp;

public class SnmpRuntimeErrorException extends RuntimeException {
   private String a;
   private int b;
   private static final String c = "$Id: SnmpRuntimeErrorException.java,v 1.1 2008/02/22 17:33:29 sking Exp $";

   public SnmpRuntimeErrorException(int var1, String var2) {
      super(a("~Q\u001dd\u0000XQ\u0004}?Hz\u0002f=_d") + Snmp.errorStatusToString(var1) + ":" + var2 + "]");
      this.a = null;
      this.b = 0;
      this.b = var1;
      this.a = var2;
   }

   public SnmpRuntimeErrorException(int var1) {
      this(var1, a("_Z\u0011g=C\u001f\u0005z9CP\u0007z"));
   }

   public int getErrorStatus() {
      return this.b;
   }

   public String getReason() {
      return this.a;
   }

   public String getErrorStatusString() {
      return Snmp.errorStatusToString(this.b);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 45;
               break;
            case 1:
               var10003 = 63;
               break;
            case 2:
               var10003 = 112;
               break;
            case 3:
               var10003 = 20;
               break;
            default:
               var10003 = 82;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
