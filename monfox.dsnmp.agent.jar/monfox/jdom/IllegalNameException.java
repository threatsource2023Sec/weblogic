package monfox.jdom;

public class IllegalNameException extends IllegalArgumentException {
   public IllegalNameException(String var1, String var2, String var3) {
      super(a("A\u001bP\u0018Ut\u001eP\u0018\u0019") + var1 + a("7S\\K\u001b{\u001cA\u0018Wp\u0014TT\u001bs\u001cG\u0018qQ<x\u0017cX?\u0015") + var2 + a("fI\u0015") + var3 + ".");
   }

   public IllegalNameException(String var1, String var2) {
      super(a("A\u001bP\u0018Ut\u001eP\u0018\u0019") + var1 + a("7S\\K\u001b{\u001cA\u0018Wp\u0014TT\u001bs\u001cG\u0018qQ<x\u0017cX?\u0015") + var2 + a("f]"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 21;
               break;
            case 1:
               var10003 = 115;
               break;
            case 2:
               var10003 = 53;
               break;
            case 3:
               var10003 = 56;
               break;
            default:
               var10003 = 59;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
