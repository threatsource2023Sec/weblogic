package monfox.toolkit.snmp.v2;

import monfox.toolkit.snmp.engine.SnmpSecurityParameters;

public class V2CSnmpSecurityParameters implements SnmpSecurityParameters {
   private byte[] a = null;
   private static final String b = "$Id: V2CSnmpSecurityParameters.java,v 1.6 2006/09/14 19:21:59 sking Exp $";

   public V2CSnmpSecurityParameters(byte[] var1) {
      this.a = var1;
   }

   public int getSecurityModel() {
      return 2;
   }

   public int getSecurityLevel() {
      return 0;
   }

   public byte[] getSecurityName() {
      return this.a;
   }

   public void setSecurityName(byte[] var1) {
      this.a = var1;
   }

   public String toString() {
      return a("l,v\u0017dyqx@jtwaT\"") + new String(this.a) + "}";
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 26;
               break;
            case 1:
               var10003 = 30;
               break;
            case 2:
               var10003 = 21;
               break;
            case 3:
               var10003 = 45;
               break;
            default:
               var10003 = 31;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
