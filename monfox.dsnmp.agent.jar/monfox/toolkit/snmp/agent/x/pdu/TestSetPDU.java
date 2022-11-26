package monfox.toolkit.snmp.agent.x.pdu;

public class TestSetPDU extends VarBindPDU {
   public TestSetPDU() {
      super(8);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("B\u000f@B\u0014<\u0016\ns\u001f;:tB\u000ee\u001ecrZ3"));
      super.toString(var1);
      var1.append(a("B3-"));
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
               var10003 = 72;
               break;
            case 1:
               var10003 = 78;
               break;
            case 2:
               var10003 = 39;
               break;
            case 3:
               var10003 = 39;
               break;
            default:
               var10003 = 122;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
