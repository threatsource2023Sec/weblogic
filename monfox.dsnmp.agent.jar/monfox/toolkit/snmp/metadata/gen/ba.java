package monfox.toolkit.snmp.metadata.gen;

import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import monfox.jdom.Attribute;
import monfox.jdom.Element;

class ba extends q {
   private static final String a = "$Id: NOTIFICATION_GROUPValidator.java,v 1.11 2003/08/01 12:55:07 sking Exp $";

   boolean f(n var1, Element var2) {
      o var3 = (o)var1;
      this.a(var1, var2, var2.getChild(a("1\\$5\u00076P1(\b0]#")), a("1\\$5\u00076P1(\b0]"));
      return true;
   }

   boolean g(n var1, Element var2) {
      int var15 = Message.d;
      o var3 = (o)var1;

      int var10000;
      try {
         String var4 = bo.getParentValue(var2, a("2|\u0014\t-\u001a"), a("\u0011r\u001d\u0019"));
         Element var5 = new Element(a("1|\u0004\u0015'\u0016p\u0011\b(\u0010}7\u000e.\nc"));
         var5.addAttribute((Attribute)var2.getAttribute(a("\u0011r\u001d\u0019")).clone());
         var5.addAttribute((Attribute)var2.getAttribute(a("\u0010z\u0014")).clone());
         Element var6 = new Element(a("1|\u0004\u0015'\u0016p\u0011\b(\u0010}\u0003"));
         Hashtable var7 = new Hashtable();

         try {
            Element var8 = var2.getChild(a("1\\$5\u00076P1(\b0]#"));
            List var9 = var8.getChildren();
            ListIterator var10 = var9.listIterator();

            while(var10.hasNext()) {
               Element var11 = (Element)var10.next();
               Element var12 = new Element(a("1|\u0004\u0015'\u0016p\u0011\b(\u0010}\"\u0019'"));
               String var13 = var11.getText();
               var10000 = var13.indexOf(58);
               if (var15 != 0) {
                  return (boolean)var10000;
               }

               if (var10000 > 0) {
                  var7.put(var13.substring(0, var13.indexOf(58)), var5);
               }

               var12.addAttribute(a("\u0011r\u001d\u0019"), var13);
               if (var3.isRichMode()) {
                  Element var14 = this.a(var3, var4, var13);
                  if (var14 != null) {
                     this.a(a("\u0010z\u0014"), var14, var12);
                  }
               }

               var6.addContent(var12);
               if (var15 != 0) {
                  break;
               }
            }
         } catch (NullPointerException var16) {
         }

         var5.addContent(var6);
         copyStatus(var3, var2, var5);
         copyDescription(var3, var2, var5);
         var3.addMetadata(var2, var5, var7.keySet());
      } catch (NullPointerException var17) {
      }

      var10000 = 1;
      return (boolean)var10000;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 127;
               break;
            case 1:
               var10003 = 19;
               break;
            case 2:
               var10003 = 112;
               break;
            case 3:
               var10003 = 124;
               break;
            default:
               var10003 = 65;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
