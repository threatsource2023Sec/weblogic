package monfox.toolkit.snmp.metadata.gen;

import java.util.List;
import java.util.ListIterator;
import monfox.jdom.Element;

class u extends q {
   private static final String a = "$Id: FROMValidator.java,v 1.2 2003/01/17 20:44:51 sking Exp $";

   boolean c(n var1, Element var2) {
      int var10 = Message.d;
      o var3 = (o)var1;

      try {
         String var4 = var2.getAttribute(a("JH^J#B")).getValue();
         Element var5 = var2.getChild(a("t^W] KkSL;"));
         List var6 = var5.getChildren(a("NS_R"));
         ListIterator var7 = var6.listIterator();

         while(var7.hasNext()) {
            Element var8 = (Element)var7.next();
            String var9 = var8.getText();
            var3.addImport(var9, var4, var8);
            if (var10 != 0 || var10 != 0) {
               break;
            }
         }
      } catch (NullPointerException var11) {
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
               var10003 = 39;
               break;
            case 1:
               var10003 = 39;
               break;
            case 2:
               var10003 = 58;
               break;
            case 3:
               var10003 = 63;
               break;
            default:
               var10003 = 79;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
