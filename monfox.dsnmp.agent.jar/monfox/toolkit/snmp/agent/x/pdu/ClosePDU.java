package monfox.toolkit.snmp.agent.x.pdu;

public class ClosePDU extends AgentXPDU {
   public static int REASON_OTHER = 1;
   public static int REASON_PARSE_ERROR = 2;
   public static int REASON_PROTOCOL_ERROR = 3;
   public static int REASON_TIMEOUTS = 4;
   public static int REASON_SHUTDOWN = 5;
   public static int REASON_BY_MANAGER = 6;
   private int a = 0;

   public ClosePDU() {
      super(2);
   }

   public int getReason() {
      return this.a;
   }

   public void setReason(int var1) {
      this.a = var1;
   }

   protected EncBuffer encodePayload() throws CoderException {
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      var1.addByte(this.a);
      var1.addPadding(3);
      return var1;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.a = var2.nextByte();
      var2.nextPadding(3);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("#i4lH]p~JJF[6$vm}sr"));
      super.toString(var1);
      var1.append(a("#\bs)TLI fH\t\bs)\u001b\t")).append(this.a);
      var1.append(a("#UY"));
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
               var10003 = 40;
               break;
            case 2:
               var10003 = 83;
               break;
            case 3:
               var10003 = 9;
               break;
            default:
               var10003 = 38;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
