package monfox.toolkit.snmp.metadata.gen;

import monfox.jdom.Element;

class bo {
   public static String getParentValue(Element var0, String var1, String var2) {
      Element var3 = getParent(var0, var1);
      return var3 == null ? null : var3.getAttributeValue(var2);
   }

   public static Element getParent(Element var0, String var1) {
      Element var2 = var0.getParent();

      while(var2 != null) {
         if (var2.getName().equals(var1)) {
            return var2;
         }

         var2 = var2.getParent();
         if (Message.d != 0) {
            break;
         }
      }

      return null;
   }
}
