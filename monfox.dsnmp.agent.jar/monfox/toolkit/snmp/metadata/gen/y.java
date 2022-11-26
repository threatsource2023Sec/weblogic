package monfox.toolkit.snmp.metadata.gen;

import java.util.Set;
import monfox.jdom.Attribute;
import monfox.jdom.Element;

class y extends q {
   private static final String a = "$Id: MODULE_IDENTITYValidator.java,v 1.6 2003/07/16 23:40:02 sking Exp $";

   boolean g(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         Element var4 = new Element(a("lbasSDDacQUdq\u007f"));
         var4.addAttribute((Attribute)var2.getAttribute(a("Olhc")).clone());
         var4.addAttribute((Attribute)var2.getAttribute(a("Nda")).clone());

         try {
            Element var5 = var2.getChild(a("mLVR`t]AGkdI"));
            if (var5 != null) {
               var4.addAttribute(a("MlvrjQidrZE"), var5.getText());
            }

            copyTextElement(var3, var2, var4, a("n_BGqhWDRvnC"), a("n\u007fbgQHwdrVNc"));
            copyTextElement(var3, var2, var4, a("bBKR~bYZOqgB"), a("bbkr^ByLhYN"));
            copyTextElement(var3, var2, var4, a("eHVEmh]QOpo"), a("ehveMH}qoPO"));
         } catch (NullPointerException var6) {
         }

         var3.addMetadata((Element)var2, var4, (Set)null);
      } catch (NullPointerException var7) {
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
               var10003 = 33;
               break;
            case 1:
               var10003 = 13;
               break;
            case 2:
               var10003 = 5;
               break;
            case 3:
               var10003 = 6;
               break;
            default:
               var10003 = 63;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
