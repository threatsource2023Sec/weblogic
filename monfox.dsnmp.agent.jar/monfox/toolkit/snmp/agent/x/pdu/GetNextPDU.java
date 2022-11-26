package monfox.toolkit.snmp.agent.x.pdu;

public class GetNextPDU extends GetPDU {
   public GetNextPDU() {
      super(6);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("2s\u0010|^LjZ^UL|\u0012aD\u0015b3L\u0010C"));
      super.toString(var1);
      var1.append(a("2\u0012W9BY\\\u0010||QA\u00039\r\u00188"));
      this.a(var1);
      var1.append(a("2O}"));
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
               var10003 = 56;
               break;
            case 1:
               var10003 = 50;
               break;
            case 2:
               var10003 = 119;
               break;
            case 3:
               var10003 = 25;
               break;
            default:
               var10003 = 48;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
