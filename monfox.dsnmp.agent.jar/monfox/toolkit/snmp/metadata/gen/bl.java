package monfox.toolkit.snmp.metadata.gen;

import monfox.jdom.Element;

class bl extends q {
   boolean d(n var1, Element var2) {
      String var3 = bo.getParentValue(var2, a(">q\\%\u0006?p_"), a("\u0000Kh\u0000%\b"));
      return this.validateStage1(var1, var2, var3);
   }

   boolean f(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         String var4 = bo.getParentValue(var2, a(" Kh\u0000%\b"), a("\u0003Ea\u0010"));
         String var5 = bo.getParentValue(var2, a(">q\\%\u0006?p_"), a("\u0000Kh\u0000%\b"));
         String var6 = var2.getAttributeValue(a("\u0003Ea\u0010"));
         String var7 = var2.getAttributeValue(a("\u0002Mh"));
         if (var7 == null) {
            var3.a(new ErrorMessage.UndefinedElement(var4, a("\"fF0\n9XB:\u001d$bE6\b9mC;"), a("MBc\u0007i;e^<\b9mC;iJ") + var6 + a("J.") + a("M\u0004,]:\u0005Ky\u0019-MFiU \u0003\u0004a\u001a-\u0018HiUn") + var5 + a("J\r\"")), var2);
         }

         Element var8 = var2.getChild(a(".vI4\u001d$kB*\u001b(uY<\u001b(w"));
         if (var8 != null) {
            this.a(var1, var2, var8, var5, new String[]{a("\"fF0\n9")});
         }
      } catch (Exception var9) {
      }

      return true;
   }

   boolean g(n var1, Element var2) {
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
               var10003 = 109;
               break;
            case 1:
               var10003 = 36;
               break;
            case 2:
               var10003 = 12;
               break;
            case 3:
               var10003 = 117;
               break;
            default:
               var10003 = 73;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
