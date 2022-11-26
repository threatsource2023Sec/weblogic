package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpException;

public class SnmpTimeoutException extends SnmpException {
   private SnmpPendingRequest a = null;
   private static final String b = "$Id: SnmpTimeoutException.java,v 1.3 2001/08/21 20:05:33 sking Exp $";

   public SnmpTimeoutException(String var1) {
      super(var1);
   }

   public SnmpTimeoutException(SnmpPendingRequest var1) {
      super(a("me\u001b~|jb\u001bk3K\u007f-") + var1 + "]");
      this.a = var1;
   }

   public SnmpPendingRequest getSnmpPendingRequest() {
      return this.a;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 62;
               break;
            case 1:
               var10003 = 11;
               break;
            case 2:
               var10003 = 118;
               break;
            case 3:
               var10003 = 14;
               break;
            default:
               var10003 = 92;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
