package monfox.toolkit.snmp.metadata.gen;

import monfox.jdom.Element;

class v extends q {
   boolean d(n var1, Element var2) {
      String var3 = bo.getParentValue(var2, a(")Q*:i!"), a("\tq\n\u001aI\u0001"));
      return this.validateStage1(var1, var2, var3);
   }

   boolean f(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         String var4 = bo.getParentValue(var2, a(")q\n\u001aI\u0001"), a("\n\u007f\u0003\n"));
         String var5 = bo.getParentValue(var2, a(")Q*:i!"), a("\tq\n\u001aI\u0001"));
         if (var5 == null) {
            var5 = var4;
         }

         String var6 = bo.getParentValue(var2, a(")Q*:i!A- h4R'.k'["), a("\n\u007f\u0003\n"));
         String var7 = var2.getAttributeValue(a("\n\u007f\u0003\n"));
         String var8 = var2.getAttributeValue(a("\u000bw\n"));
         this.a(var3, var2, var5, var7, var6, new String[]{a("+\\$*f0A)=j1N"), a("*Q:&c-]/;l+P1(w+K>")});
         if (var8 == null) {
            var3.a(new ErrorMessage.UndefinedElement(var4, a("+\\$*f0A)=j1N\u0012!j0W(&f%J' k;Y< p4"), a("Dx\u0001\u001d\u0005#L!:uD9") + var7 + a("C\u0014") + a("D>NGV\fq\u001b\u0003AD|\u000bOC\u000bk\u0000\u000b\u0005\rpN\u0002J\u0000k\u0002\n\u0005C") + var5 + a("C7@")), var2);
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
               var10003 = 100;
               break;
            case 1:
               var10003 = 30;
               break;
            case 2:
               var10003 = 110;
               break;
            case 3:
               var10003 = 111;
               break;
            default:
               var10003 = 37;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
