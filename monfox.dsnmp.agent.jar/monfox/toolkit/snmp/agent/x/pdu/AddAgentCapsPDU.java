package monfox.toolkit.snmp.agent.x.pdu;

import monfox.toolkit.snmp.SnmpOid;

public class AddAgentCapsPDU extends AgentXPDU {
   private SnmpOid a = null;
   private String g = "";

   public AddAgentCapsPDU() {
      super(16);
   }

   public SnmpOid getId() {
      return this.a;
   }

   public String getDescr() {
      return this.g;
   }

   public void setId(SnmpOid var1) {
      this.a = var1;
   }

   public void setDescr(String var1) {
      this.g = var1;
   }

   protected EncBuffer encodePayload() throws CoderException {
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      this.encodeContext(var1);
      var1.addOid(this.a);
      var1.addString(this.g);
      return var1;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.decodeContext(var2);
      this.a = new SnmpOid(var2.nextOid());
      this.g = new String(var2.nextString());
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("}\u001cr4}\u0003\u00058\u0010w\u0013\u001cr4}\u0003\u001et!`Z\rQ\u00043\f"));
      super.toString(var1);
      var1.append(a("}}5qz\u0013}5q3W}5q.W")).append(this.a);
      var1.append(a("}}5qw\u0012.v#3W}5q.W")).append(this.g);
      var1.append(a("} \u001f"));
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
               var10003 = 119;
               break;
            case 1:
               var10003 = 93;
               break;
            case 2:
               var10003 = 21;
               break;
            case 3:
               var10003 = 81;
               break;
            default:
               var10003 = 19;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
