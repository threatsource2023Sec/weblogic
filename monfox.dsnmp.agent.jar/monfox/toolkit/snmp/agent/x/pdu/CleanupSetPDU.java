package monfox.toolkit.snmp.agent.x.pdu;

public class CleanupSetPDU extends AgentXPDU {
   public CleanupSetPDU() {
      super(11);
   }

   protected EncBuffer encodePayload() throws CoderException {
      return new EncBuffer();
   }

   public void decodePayload(byte[] var1) throws CoderException {
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("_+*\"\u0007!2`\u0004\u00050\u000b#2\u0019\u0006\u000f9j9\u0011?m<"));
      super.toString(var1);
      var1.append(a("_\u0017G"));
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
               var10003 = 85;
               break;
            case 1:
               var10003 = 106;
               break;
            case 2:
               var10003 = 77;
               break;
            case 3:
               var10003 = 71;
               break;
            default:
               var10003 = 105;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
