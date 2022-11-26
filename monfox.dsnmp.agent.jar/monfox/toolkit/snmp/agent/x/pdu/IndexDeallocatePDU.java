package monfox.toolkit.snmp.agent.x.pdu;

public class IndexDeallocatePDU extends VarBindPDU {
   public IndexDeallocatePDU() {
      super(15);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\u001dKhA\u000bcR\"m\u000bsow`\u0000vfcK\u0006v~j\t5S_/_"));
      super.toString(var1);
      var1.append(a("\u001dw\u0005"));
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
               var10003 = 23;
               break;
            case 1:
               var10003 = 10;
               break;
            case 2:
               var10003 = 15;
               break;
            case 3:
               var10003 = 36;
               break;
            default:
               var10003 = 101;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
