package monfox.toolkit.snmp.agent.x.pdu;

public class CommitSetPDU extends AgentXPDU {
   public CommitSetPDU() {
      super(9);
   }

   protected EncBuffer encodePayload() throws CoderException {
      return new EncBuffer();
   }

   public void decodePayload(byte[] var1) throws CoderException {
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("#p\u0007\u001f4]iM95D\\\t\u000e\tLEM*\u001e|\u0011\u001b"));
      super.toString(var1);
      var1.append(a("#Lj"));
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
               var10003 = 41;
               break;
            case 1:
               var10003 = 49;
               break;
            case 2:
               var10003 = 96;
               break;
            case 3:
               var10003 = 122;
               break;
            default:
               var10003 = 90;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
