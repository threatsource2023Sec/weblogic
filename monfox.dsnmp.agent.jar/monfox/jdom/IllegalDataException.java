package monfox.jdom;

public class IllegalDataException extends IllegalArgumentException {
   public IllegalDataException(String var1, String var2, String var3) {
      super(a("}\u007f\noQHc\u000eo\u0017") + var1 + a("\u000b7\u0006<\u0015Gx\u001boYLp\u000e#\u0015Ox\u001doT\t]+\u0000x\t") + var2 + a("\u00137") + var3 + ".");
   }

   public IllegalDataException(String var1, String var2) {
      super(a("}\u007f\noQHc\u000eo\u0017") + var1 + a("\u000b7\u0006<\u0015Gx\u001boYLp\u000e#\u0015Ox\u001doT\t]+\u0000x\t") + var2 + ".");
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
               var10003 = 23;
               break;
            case 2:
               var10003 = 111;
               break;
            case 3:
               var10003 = 79;
               break;
            default:
               var10003 = 53;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
