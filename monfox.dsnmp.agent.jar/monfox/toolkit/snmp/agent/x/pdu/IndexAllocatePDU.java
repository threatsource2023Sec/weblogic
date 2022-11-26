package monfox.toolkit.snmp.agent.x.pdu;

public class IndexAllocatePDU extends VarBindPDU {
   public IndexAllocatePDU() {
      super(14);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("&d:\u0001fX}p-fH@%%d@J>\u0005|I\b\r ]\f^"));
      super.toString(var1);
      var1.append(a("&XW"));
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
               var10003 = 44;
               break;
            case 1:
               var10003 = 37;
               break;
            case 2:
               var10003 = 93;
               break;
            case 3:
               var10003 = 100;
               break;
            default:
               var10003 = 8;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
