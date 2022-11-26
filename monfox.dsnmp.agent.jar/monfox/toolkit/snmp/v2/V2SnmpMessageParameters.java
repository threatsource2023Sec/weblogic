package monfox.toolkit.snmp.v2;

import monfox.toolkit.snmp.engine.SnmpMessageParameters;

public class V2SnmpMessageParameters implements SnmpMessageParameters {
   private int a = 1;
   private static final String b = "$Id: V2SnmpMessageParameters.java,v 1.5 2002/04/30 14:01:17 samin Exp $";

   public V2SnmpMessageParameters() {
   }

   public V2SnmpMessageParameters(int var1) {
      this.a = var1;
   }

   public int getVersion() {
      return this.a;
   }

   public void setVersion(int var1) {
      this.a = var1;
   }

   public String toString() {
      return a(":\u001dP\t:");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 76;
               break;
            case 1:
               var10003 = 47;
               break;
            case 2:
               var10003 = 106;
               break;
            case 3:
               var10003 = 114;
               break;
            default:
               var10003 = 71;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
