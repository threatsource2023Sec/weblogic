package monfox.toolkit.snmp.metadata.gen;

import monfox.jdom.Element;

class bf extends q {
   boolean f(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         String var4 = var2.getAttributeValue(a("'7\u0016Hd/"));
         if (!var3.moduleExists(var2, var4)) {
            var3.a(new ErrorMessage.MissingModule(var4), var2);
         }

         Element var5 = var2.getChild(a("\u0003\u00161q]\u000e\u001d!"));
         if (var5 != null) {
            this.a(var1, var2, var5, var4, new String[]{a("\u0005\u001a8xK\u001e\u00075oG\u001f\b"), a("\u0004\u0017&tN\u0003\u001b3iA\u0005\u0016-zZ\u0005\r\"")});
         }
      } catch (Exception var6) {
      }

      return true;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 74;
               break;
            case 1:
               var10003 = 88;
               break;
            case 2:
               var10003 = 114;
               break;
            case 3:
               var10003 = 61;
               break;
            default:
               var10003 = 8;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
