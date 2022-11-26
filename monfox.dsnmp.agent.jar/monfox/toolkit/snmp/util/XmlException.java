package monfox.toolkit.snmp.util;

public class XmlException extends Exception {
   private String a = null;
   private int b = -1;

   public XmlException(String var1) {
      super(var1);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(this.getMessage());
      if (this.a != null) {
         var1.append(a(",R*##>\u0014")).append(this.a).append(")");
      }

      if (this.b >= 0) {
         var1.append(a(",X*!#>\u0014")).append(this.b).append(")");
      }

      return var1.toString();
   }

   public void setLineNumber(int var1) {
      this.b = var1;
   }

   public int getLineNumber() {
      return this.b;
   }

   public void setFilename(String var1) {
      this.a = var1;
   }

   public String getFilename() {
      return this.a;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 4;
               break;
            case 1:
               var10003 = 52;
               break;
            case 2:
               var10003 = 67;
               break;
            case 3:
               var10003 = 79;
               break;
            default:
               var10003 = 70;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
