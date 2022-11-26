package monfox.toolkit.snmp.agent.x.pdu;

import monfox.toolkit.snmp.SnmpOid;

public class RemoveAgentCapsPDU extends AgentXPDU {
   private SnmpOid a = null;

   public RemoveAgentCapsPDU() {
      super(17);
   }

   public SnmpOid getId() {
      return this.a;
   }

   public void setId(SnmpOid var1) {
      this.a = var1;
   }

   protected EncBuffer encodePayload() throws CoderException {
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      this.encodeContext(var1);
      var1.addOid(this.a);
      return var1;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.decodeContext(var2);
      this.a = new SnmpOid(var2.nextOid());
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("Xa\u000f\u001a;&xE-0?O\u001e\u001a\u00145E\u0006\u000b\u00163P\u001bR\u0005\u0016uH\u0004"));
      super.toString(var1);
      var1.append(a("X\u0000H_<6\u0000H_ur\u0000H_hr")).append(this.a);
      var1.append(a("X]b"));
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
               var10003 = 82;
               break;
            case 1:
               var10003 = 32;
               break;
            case 2:
               var10003 = 104;
               break;
            case 3:
               var10003 = 127;
               break;
            default:
               var10003 = 85;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
