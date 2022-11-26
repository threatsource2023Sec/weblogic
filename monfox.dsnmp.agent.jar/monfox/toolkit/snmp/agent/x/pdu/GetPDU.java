package monfox.toolkit.snmp.agent.x.pdu;

import java.util.List;
import java.util.ListIterator;

public class GetPDU extends AgentXPDU {
   private List a = null;

   GetPDU(int var1) {
      super(var1);
   }

   public GetPDU() {
      super(5);
   }

   public void setSearchRangeList(List var1) {
      this.a = var1;
   }

   public List getSearchRangeList() {
      return this.a;
   }

   protected EncBuffer encodePayload() throws CoderException {
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      this.encodeContext(var1);
      var1.addSearchRangeList(this.a);
      return var1;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.decodeContext(var2);
      this.a = var2.nextSearchRangeList();
   }

   void a(StringBuffer var1) {
      if (this.a != null) {
         ListIterator var2 = this.a.listIterator();

         while(var2.hasNext()) {
            SearchRange var3 = (SearchRange)var2.next();
            var1.append(a("\u0001%tI"));
            var3.toString(var1);
            if (AgentXPDU.i != 0) {
               break;
            }
         }
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\u0001D3\fq\u007f]y.z\u007f(\u0004-J+~"));
      super.toString(var1);
      var1.append(a("\u0001%tImjk3\fSbv I\"+\u000f"));
      this.a(var1);
      var1.append(a("\u0001x^"));
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
               var10003 = 11;
               break;
            case 1:
               var10003 = 5;
               break;
            case 2:
               var10003 = 84;
               break;
            case 3:
               var10003 = 105;
               break;
            default:
               var10003 = 31;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
