package monfox.toolkit.snmp.agent.x.pdu;

public class NotifyPDU extends VarBindPDU {
   public NotifyPDU() {
      super(12);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\u007f5{\b\u0019\u0001,1#\u0018\u0001\u001dz\u0014Z%0IM\f"));
      super.toString(var1);
      var1.append(a("\u007f\t\u0016"));
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
               var10003 = 117;
               break;
            case 1:
               var10003 = 116;
               break;
            case 2:
               var10003 = 28;
               break;
            case 3:
               var10003 = 109;
               break;
            default:
               var10003 = 119;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
