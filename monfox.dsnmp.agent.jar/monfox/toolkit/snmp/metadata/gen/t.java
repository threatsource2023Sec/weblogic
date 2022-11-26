package monfox.toolkit.snmp.metadata.gen;

import monfox.jdom.Element;

class t extends q {
   boolean d(n var1, Element var2) {
      String var3 = bo.getParentValue(var2, a("Z34Y\u0003R"), a("z\u0013\u0014y#r"));
      return this.validateStage1(var1, var2, var3);
   }

   boolean f(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         String var4 = bo.getParentValue(var2, a("Z\u0013\u0014y#r"), a("y\u001d\u001di"));
         String var5 = bo.getParentValue(var2, a("Z34Y\u0003R"), a("z\u0013\u0014y#r"));
         if (var5 == null) {
            var5 = var4;
         }

         String var6 = var2.getAttributeValue(a("y\u001d\u001di"));
         String var7 = var2.getAttributeValue(a("x\u0015\u0014"));
         if (var7 == null) {
            var3.a(new ErrorMessage.UndefinedElement(var4, a("X>:I\fC"), a("7\u001a\u001f~oX>:I\fC\\5T\fR,$E\u0000Y\\W") + var6 + a("0v") + a("7\\P$<\u007f\u0013\u0005`+7\u001e\u0015,)x\t\u001eho~\u0012Pa s\t\u001cio0") + var5 + a("0U^")), var2);
         }
      } catch (Exception var8) {
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
               var10003 = 23;
               break;
            case 1:
               var10003 = 124;
               break;
            case 2:
               var10003 = 112;
               break;
            case 3:
               var10003 = 12;
               break;
            default:
               var10003 = 79;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
