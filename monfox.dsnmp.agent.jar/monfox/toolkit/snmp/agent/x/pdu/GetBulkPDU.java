package monfox.toolkit.snmp.agent.x.pdu;

public class GetBulkPDU extends GetPDU {
   private int a = 0;
   private int b = 100;

   public GetBulkPDU() {
      super(7);
   }

   public int getNonRepeaters() {
      return this.a;
   }

   public int getMaxRepetitions() {
      return this.b;
   }

   public void setNonRepeaters(int var1) {
      this.a = var1;
   }

   public void setMaxRepetitions(int var1) {
      this.b = var1;
   }

   protected EncBuffer encodePayload() throws CoderException {
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      this.encodeContext(var1);
      var1.addInt16(this.a);
      var1.addInt16(this.b);
      var1.addSearchRangeList(this.getSearchRangeList());
      return var1;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.decodeContext(var2);
      this.a = var2.nextInt16();
      this.b = var2.nextInt16();
      this.setSearchRangeList(var2.nextSearchRangeList());
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("5kM\\\u0018Kr\u0007~\u0013Kh_U\u001d\u0012znlVD"));
      super.toString(var1);
      var1.append(a("5\n\n\u0019\u0018PDx\\\u0006\u0011\n\n\u0019K\u001f")).append(this.a);
      var1.append(a("5\n\n\u0019\u001b^Rx\\\u0006\u0011\n\n\u0019K\u001f")).append(this.b);
      var1.append(a("5\n\n\u0019\u0004^DM\\:VY^\u0019K\u001f\n "));
      this.a(var1);
      var1.append(a("5W "));
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
               var10003 = 63;
               break;
            case 1:
               var10003 = 42;
               break;
            case 2:
               var10003 = 42;
               break;
            case 3:
               var10003 = 57;
               break;
            default:
               var10003 = 118;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
