package monfox.toolkit.snmp.v1;

import monfox.toolkit.snmp.engine.SnmpSecurityParameters;

public class V1SnmpSecurityParameters implements SnmpSecurityParameters {
   private byte[] a = null;
   private static final String b = "$Id: V1SnmpSecurityParameters.java,v 1.6 2006/09/14 19:21:59 sking Exp $";

   public V1SnmpSecurityParameters(byte[] var1) {
      this.a = var1;
   }

   public int getSecurityModel() {
      return 1;
   }

   public byte[] getSecurityName() {
      return this.a;
   }

   public void setSecurityName(byte[] var1) {
      this.a = var1;
   }

   public int getSecurityLevel() {
      return 0;
   }

   public String toString() {
      return a("2\u0001q%\u001a+]&+\u0017-D2c") + new String(this.a) + "}";
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 68;
               break;
            case 1:
               var10003 = 48;
               break;
            case 2:
               var10003 = 75;
               break;
            case 3:
               var10003 = 94;
               break;
            default:
               var10003 = 121;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
