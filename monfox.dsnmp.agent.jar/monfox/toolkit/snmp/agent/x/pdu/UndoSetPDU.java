package monfox.toolkit.snmp.agent.x.pdu;

public class UndoSetPDU extends AgentXPDU {
   public UndoSetPDU() {
      super(10);
   }

   protected EncBuffer encodePayload() throws CoderException {
      return new EncBuffer();
   }

   public void decodePayload(byte[] var1) throws CoderException {
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("!?\u0016j$_&\\Z$O\u0011\"j>\u0006.5ZjP"));
      super.toString(var1);
      var1.append(a("!\u0003{"));
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
               var10003 = 43;
               break;
            case 1:
               var10003 = 126;
               break;
            case 2:
               var10003 = 113;
               break;
            case 3:
               var10003 = 15;
               break;
            default:
               var10003 = 74;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
