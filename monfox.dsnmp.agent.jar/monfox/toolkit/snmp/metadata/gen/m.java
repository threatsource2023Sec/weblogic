package monfox.toolkit.snmp.metadata.gen;

class m {
   public String fileName;
   public int lineNo;
   public String preText;
   public String errorText;
   public String postText;
   private static final String a = "$Id: SyntaxError.java,v 1.3 2001/04/03 19:34:18 sking Exp $";

   m(String var1, int var2, String var3, String var4, String var5) {
      this.fileName = var1;
      this.lineNo = var2;
      this.preText = var3;
      this.errorText = var4;
      this.postText = var5;
   }

   public String getHeader() {
      StringBuffer var1 = new StringBuffer(30);
      var1.append("(").append(this.fileName).append(':').append(this.lineNo).append(a("\u0019\u001e"));
      return var1.toString();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(200);
      var1.append(this.getHeader());
      var1.append("'").append(this.preText).append(a("\u0018\u0000\u001f"));
      var1.append(this.errorText).append(a("\u0018\u0002\u001f")).append(this.postText).append("'");
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
               var10003 = 48;
               break;
            case 1:
               var10003 = 62;
               break;
            case 2:
               var10003 = 54;
               break;
            case 3:
               var10003 = 7;
               break;
            default:
               var10003 = 37;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
