package monfox.toolkit.snmp.v1;

import monfox.toolkit.snmp.engine.SnmpMessageParameters;

public class V1SnmpMessageParameters implements SnmpMessageParameters {
   private int a = 0;
   private static final String b = "$Id: V1SnmpMessageParameters.java,v 1.4 2002/04/30 14:00:44 samin Exp $";

   public V1SnmpMessageParameters() {
   }

   public V1SnmpMessageParameters(int var1) {
      this.a = var1;
   }

   public int getVersion() {
      return this.a;
   }

   public void setVersion(int var1) {
      this.a = var1;
   }

   public String toString() {
      return a("Nb\u000b\n\u001b");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 56;
               break;
            case 1:
               var10003 = 83;
               break;
            case 2:
               var10003 = 49;
               break;
            case 3:
               var10003 = 113;
               break;
            default:
               var10003 = 102;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
