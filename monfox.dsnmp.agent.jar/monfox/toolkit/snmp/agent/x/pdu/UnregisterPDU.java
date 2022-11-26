package monfox.toolkit.snmp.agent.x.pdu;

import monfox.toolkit.snmp.SnmpOid;

public class UnregisterPDU extends AgentXPDU {
   private int a = 0;
   private int b = 0;
   private SnmpOid c = null;
   private int d = 0;

   public UnregisterPDU() {
      super(4);
   }

   public void setPriority(int var1) {
      this.a = var1;
   }

   public void setRangeSubid(int var1) {
      this.b = var1;
   }

   public void setSubtree(SnmpOid var1) {
      this.c = var1;
   }

   public void setUpperBound(int var1) {
      this.d = var1;
   }

   public int getPriority() {
      return this.a;
   }

   public int getRangeSubid() {
      return this.b;
   }

   public SnmpOid getSubtree() {
      return this.c;
   }

   public int getUpperBound() {
      return this.d;
   }

   public boolean isInstance() {
      return (this.getFlags() & 1) != 0;
   }

   protected EncBuffer encodePayload() throws CoderException {
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      this.encodeContext(var1);
      var1.addPadding(1);
      var1.addByte(this.a);
      var1.addByte(this.b);
      var1.addPadding(1);
      var1.addOid(this.c);
      var1.addInt32((long)this.d);
      return var1;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.decodeContext(var2);
      var2.nextByte();
      this.a = var2.nextByte();
      this.b = var2.nextByte();
      var2.nextByte();
      this.c = new SnmpOid(var2.nextOid());
      this.d = (int)var2.nextInt32();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\u00063\u001awcx*P@hk\u001b\u000efh~_-VX,\t"));
      super.toString(var1);
      var1.append(a("\u0006R]2}~\u001b\u0012`dx\u000b]20,")).append(this.a);
      var1.append(a("\u0006R]2\u007fm\u001c\u001aw^y\u0010\u0014v0,")).append(this.b);
      var1.append(a("\u0006R]2~y\u0010\t`hiR]20,")).append(this.c);
      var1.append(a("\u0006R]2x|\u0002\u0018`Oc\u0007\u0013v0,")).append(this.d);
      var1.append(a("\u0006\u000fw"));
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
               var10003 = 12;
               break;
            case 1:
               var10003 = 114;
               break;
            case 2:
               var10003 = 125;
               break;
            case 3:
               var10003 = 18;
               break;
            default:
               var10003 = 13;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
