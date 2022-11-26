package monfox.toolkit.snmp.agent.x.pdu;

import monfox.toolkit.snmp.SnmpOid;

public class OpenPDU extends AgentXPDU {
   private int a = 0;
   private SnmpOid b = null;
   private String g = "";

   public OpenPDU() {
      super(1);
   }

   public int getTimeout() {
      return this.a;
   }

   public SnmpOid getId() {
      return this.b;
   }

   public String getDescr() {
      return this.g;
   }

   public void setTimeout(int var1) {
      this.a = var1;
   }

   public void setId(SnmpOid var1) {
      this.b = var1;
   }

   public void setDescr(String var1) {
      this.g = var1;
   }

   protected EncBuffer encodePayload() throws CoderException {
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      var1.addByte(this.a);
      var1.addPadding(3);
      var1.addOid(this.b);
      var1.addString(this.g);
      return var1;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.a = var2.nextByte();
      var2.nextPadding(3);
      this.b = new SnmpOid(var2.nextOid());
      this.g = new String(var2.nextString());
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("Cl\u001cr-=uVX3,CVG\u0007\u001c\r\u0000"));
      super.toString(var1);
      var1.append(a("C\r[77 @\u001ex6=\r[7~i")).append(this.a);
      var1.append(a("C\r[7*-\r[7ci\r[7~i")).append(this.b);
      var1.append(a("C\r[7',^\u0018eci\r[7~i")).append(this.g);
      var1.append(a("CPq"));
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
               var10003 = 73;
               break;
            case 1:
               var10003 = 45;
               break;
            case 2:
               var10003 = 123;
               break;
            case 3:
               var10003 = 23;
               break;
            default:
               var10003 = 67;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
