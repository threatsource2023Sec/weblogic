package monfox.toolkit.snmp.agent.x.pdu;

public class PingPDU extends AgentXPDU {
   public PingPDU() {
      super(13);
   }

   protected EncBuffer encodePayload() throws CoderException {
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      this.encodeContext(var1);
      return var1;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.decodeContext(var2);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("i>M\u0012\u0018\u0017'\u0007'\u001f\r\u0018\u0007'26_Q"));
      super.toString(var1);
      var1.append(a("i\u0002 "));
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
               var10003 = 99;
               break;
            case 1:
               var10003 = 127;
               break;
            case 2:
               var10003 = 42;
               break;
            case 3:
               var10003 = 119;
               break;
            default:
               var10003 = 118;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
