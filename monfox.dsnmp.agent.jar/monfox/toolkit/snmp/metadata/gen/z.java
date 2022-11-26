package monfox.toolkit.snmp.metadata.gen;

import monfox.jdom.Element;

class z extends q {
   boolean f(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         String var4 = var2.getAttributeValue(a("E6\rxsM"));
         if (var4 == null) {
            var4 = bo.getParentValue(var2, a("e6\rxsM"), a("F8\u0004h"));
         }

         if (!var3.moduleExists(var2, var4)) {
            var3.a(new ErrorMessage.MissingModule(var4), var2);
         }

         Element var5 = var2.getChild(a("e\u0018'I^|\u0016;T@o\u000b&XO{"));
         if (var5 != null) {
            this.a(var1, var2, var5, var4, new String[]{a("g\u001b#H\\|\u0006._P}\t"), a("f\u0016=DYa\u001a(YVg\u00176JMg\f9")});
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
               var10003 = 40;
               break;
            case 1:
               var10003 = 89;
               break;
            case 2:
               var10003 = 105;
               break;
            case 3:
               var10003 = 13;
               break;
            default:
               var10003 = 31;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
