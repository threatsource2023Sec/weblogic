package monfox.toolkit.snmp.metadata.gen;

import java.util.Set;
import monfox.jdom.Attribute;
import monfox.jdom.Element;

class bk extends q {
   private static final String a = "$Id: ValueValidator.java,v 1.12 2003/08/01 12:55:07 sking Exp $";

   boolean c(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         Element var4 = var2.getChild(a("=4\u001c\u0016"));
         String var5 = var4.getAttribute(a("\u001d4\u001c\u0016")).getValue();
         if (var5.equals(a("&\u000f&6!=m%7''\u0019%5+,\u001f"))) {
            String var6 = var2.getAttribute(a("\u0007,\u0001\u0016")).getValue();
            String var7 = var2.getText();
            var3.addRef(var6, var7, var2);
         }
      } catch (NullPointerException var8) {
      }

      return true;
   }

   boolean f(n var1, Element var2) {
      super.f(var1, var2);
      o var3 = (o)var1;

      try {
         String var4 = var2.getAttributeValue(a("\u0007,\u0001\u0016"));
         Element var5 = var2.getChild(a("=4\u001c\u0016"));
         if (var5 != null) {
            String var6 = var5.getAttributeValue(a("\u001d4\u001c\u0016"));
            if (var6 != null && var6.equals(a("&\u000f&6!=m%7''\u0019%5+,\u001f")) && var2.getAttributeValue(a("\u0006$\b")) == null) {
               String var7 = bo.getParentValue(var2, a("$\"\b\u0006\u000e\f"), a("\u0007,\u0001\u0016"));
               String var8 = var2.getText();
               if (var8 != null) {
                  var8 = var8.trim();
               }

               if (var4 != null) {
                  var3.a(new ErrorMessage.InvalidOID(var7, a("ImL:\f\u001f,\u0000\u001a\u0006I;\r\u001f\u0017\fmK") + var8 + a("Nm\n\u001c\u0010Ij") + var4 + a("Nc")), var2);
                  if (Message.d == 0) {
                     return true;
                  }
               }

               var3.a(new ErrorMessage.InvalidOID(var7, a("ImL:\f\u001f,\u0000\u001a\u0006I;\r\u001f\u0017\fmK") + var8 + a("Nc")), var2);
            }
         }

         return true;
      } catch (Exception var9) {
         return false;
      }
   }

   boolean g(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         Attribute var4 = (Attribute)var2.getAttribute(a("\u0006$\b")).clone();
         Element var5 = new Element(a("&$\b"));
         var5.addAttribute((Attribute)var2.getAttribute(a("\u0007,\u0001\u0016")).clone());
         var5.addAttribute(var4);
         if (var3.isRichMode()) {
            try {
               String var6 = var2.getText().trim();
               var5.addAttribute(a("\u001b,\u001b%\u0003\u00058\t"), var6);
            } catch (Exception var7) {
            }
         }

         var3.addMetadata((Element)var2, var5, (Set)null);
         return true;
      } catch (NullPointerException var8) {
         return true;
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 105;
               break;
            case 1:
               var10003 = 77;
               break;
            case 2:
               var10003 = 108;
               break;
            case 3:
               var10003 = 115;
               break;
            default:
               var10003 = 98;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
