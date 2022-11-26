package monfox.jdom;

public class DataConversionException extends JDOMException {
   public DataConversionException(String var1, String var2) {
      super(a("\u0019\u0007d#(\u0000#!`\u001f#\u001cuq\u0005.\u001b!") + var1 + a("m\fnv\u001c)Ool\u0004m\rd#\u0013\"\u0001wf\u00029\ne#\u0004\"O`#") + var2);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 77;
               break;
            case 1:
               var10003 = 111;
               break;
            case 2:
               var10003 = 1;
               break;
            case 3:
               var10003 = 3;
               break;
            default:
               var10003 = 112;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
