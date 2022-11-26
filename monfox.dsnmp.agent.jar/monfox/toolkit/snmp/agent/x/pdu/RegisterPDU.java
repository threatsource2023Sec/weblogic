package monfox.toolkit.snmp.agent.x.pdu;

import monfox.toolkit.snmp.SnmpOid;

public class RegisterPDU extends AgentXPDU {
   private int a = 0;
   private int b = 127;
   private int c = 0;
   private SnmpOid d = null;
   private int e = 0;

   public RegisterPDU() {
      super(3);
   }

   public void setTimeout(int var1) {
      this.a = var1;
   }

   public void setPriority(int var1) {
      this.b = var1;
   }

   public void setRangeSubid(int var1) {
      this.c = var1;
   }

   public void setSubtree(SnmpOid var1) {
      this.d = var1;
   }

   public void setUpperBound(int var1) {
      this.e = var1;
   }

   public int getTimeout() {
      return this.a;
   }

   public int getPriority() {
      return this.b;
   }

   public int getRangeSubid() {
      return this.c;
   }

   public SnmpOid getSubtree() {
      return this.d;
   }

   public int getUpperBound() {
      return this.e;
   }

   public boolean isInstance() {
      return (this.getFlags() & 1) != 0;
   }

   public void isInstance(boolean var1) {
      if (var1) {
         this.setFlag(1);
         if (AgentXPDU.i == 0) {
            return;
         }
      }

      this.resetFlag(1);
   }

   protected EncBuffer encodePayload() throws CoderException {
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      this.encodeContext(var1);
      var1.addByte(this.a);
      var1.addByte(this.b);
      var1.addByte(this.c);
      var1.addPadding(1);
      var1.addOid(this.d);
      if (this.c != 0) {
         var1.addInt32((long)this.e);
      }

      return var1;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.decodeContext(var2);
      this.a = var2.nextByte();
      this.b = var2.nextByte();
      this.c = var2.nextByte();
      var2.nextByte();
      this.d = new SnmpOid(var2.nextOid());
      if (var2.hasMoreData() && this.c != 0) {
         this.e = (int)var2.nextInt32();
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("A\u00191)\u001b?\u0000{\u001e\u0010,1%8\u00109u\u0006\b k#"));
      super.toString(var1);
      var1.append(a("Axvl\u0001\"53#\u0000?xvlHk")).append(this.a);
      var1.append(a("Axvl\u0005919>\u001c?!vlHk")).append(this.b);
      var1.append(a("Axvl\u0007*61)&>:?(Hk")).append(this.c);
      var1.append(a("Axvl\u0006>:\">\u0010.xvlHk")).append(this.d);
      var1.append(a("Axvl\u0000;(3>7$-8(Hk")).append(this.e);
      var1.append(a("A%\\"));
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
               var10003 = 75;
               break;
            case 1:
               var10003 = 88;
               break;
            case 2:
               var10003 = 86;
               break;
            case 3:
               var10003 = 76;
               break;
            default:
               var10003 = 117;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
