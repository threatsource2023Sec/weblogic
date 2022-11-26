package monfox.jdom;

public class IllegalTargetException extends IllegalArgumentException {
   public IllegalTargetException(String var1, String var2) {
      super(a("\u001c,=EG)6?\u0000Ghf") + var1 + a("jd1\u0016\u0013&+,E_-#9\t\u0013.+*Ey\f\u000b\u0015Jk\u0005\bx5A''=\u0016@!*?Ez&7,\u0017F+01\n];~x") + var2 + ".");
   }

   public IllegalTargetException(String var1) {
      super(a("\u001c,=E]))=E\u0011") + var1 + a("jd1\u0016\u0013&+,E_-#9\t\u0013.+*Ey\f\u000b\u0015Jk\u0005\bx5A''=\u0016@!*?Ez&7,\u0017F+01\n];j"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 72;
               break;
            case 1:
               var10003 = 68;
               break;
            case 2:
               var10003 = 88;
               break;
            case 3:
               var10003 = 101;
               break;
            default:
               var10003 = 51;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
