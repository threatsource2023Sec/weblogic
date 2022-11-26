package monfox.toolkit.snmp.metadata.gen;

import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import monfox.jdom.Attribute;
import monfox.jdom.Element;

class bi extends q {
   private static final String a = "$Id: TypeValidator.java,v 1.17 2007/05/18 22:53:13 sking Exp $";

   boolean c(n var1, Element var2) {
      o var3 = (o)var1;
      String var4 = bo.getParentValue(var2, a("\u000e\f\u007fhq&"), a("-\u0002vx"));

      String var5;
      try {
         var5 = var2.getAttribute(a("7\u001akxo&\u0005")).getValue();
         var3.typeRefs.addElement(var2);
         return true;
      } catch (NullPointerException var13) {
         try {
            var5 = var2.getAttribute(a("-\u0002vx")).getValue();
            String var6 = var4 + ":" + var5;
            Element var7 = (Element)var3.definedTypes.get(var6);
            if (var7 != null) {
               try {
                  String var8 = bo.getParentValue(var7, a("\u000e\f\u007fhq&"), a("-\u0002vx"));
                  String var9 = var4 + "." + var5;
                  String var10 = var8 + "." + var5;
                  var3.a(new ErrorMessage.DuplicateType(var4, var5, var9, var10), var2);
               } catch (Exception var11) {
                  var11.printStackTrace();
                  System.out.println(var11);
               }
            }

            var3.definedTypes.put(var6, var2);
            var3.definedTypes.put("*" + var5, var2);
         } catch (NullPointerException var12) {
         }

         return true;
      }
   }

   boolean f(n var1, Element var2) {
      super.f(var1, var2);
      o var3 = (o)var1;
      Element var4 = var2.getChild(a("\u0006\u000f~px-\u0017h"));
      if (var4 != null) {
         this.a(var1, var2, var4, a("\f!QX^\u0017"));
      }

      return true;
   }

   boolean g(n var1, Element var2) {
      o var3 = (o)var1;
      a(var1, var2, true);
      return true;
   }

   static Element n(n var0, Element var1) {
      return a(var0, var1, false);
   }

   private static Element a(n var0, Element var1, boolean var2) {
      int var15 = Message.d;
      o var3 = (o)var0;

      try {
         Element var4 = new Element(a("\u0017\u001akx"));
         String var5 = var1.getAttributeValue(a("-\u0002vx"));
         if (var5 != null) {
            var4.addAttribute((Attribute)var1.getAttribute(a("-\u0002vx")).clone());
         }

         var4.addAttribute((Attribute)var1.getAttribute(a("7\u001akx")).clone());
         String var6 = var1.getAttributeValue(a("7\u001akx"));
         String var7 = (String)o.f.get(var6);
         if (var7 == null) {
            return null;
         } else {
            var4.addAttribute(a(",\u0001qid3\u0006"), var7);
            String var8 = var1.getAttributeValue(a("7\u001akxo&\u0005"));
            HashSet var9 = null;
            if (var8 != null) {
               var4.addAttribute(a("7\u001akxo&\u0005"), var8);
               if (var8.indexOf(58) > 0) {
                  var9 = new HashSet();
                  var9.add(var8.substring(0, var8.indexOf(58)));
               }
            }

            List var10 = var1.getChildren();
            ListIterator var11 = var10.listIterator();

            boolean var10000;
            while(true) {
               if (var11.hasNext()) {
                  Element var12 = (Element)var11.next();
                  String var13 = var12.getName();
                  var10000 = var13.equals(a("\u0007*HMQ\u0002:DUT\r7"));
                  if (var15 != 0) {
                     break;
                  }

                  label70: {
                     String var14;
                     if (var10000) {
                        var14 = var12.getText();
                        var4.addAttribute(a("'\nhmq\"\u001aSts7"), var14);
                        if (var15 == 0) {
                           break label70;
                        }
                     }

                     if (var13.equals(a("\u00107ZIH\u0010"))) {
                        var14 = var12.getText();
                        var4.addAttribute(a("0\u0017zih0"), var14);
                        if (var15 == 0) {
                           break label70;
                        }
                     }

                     if (var13.equals(a("\r\u0002vxy\r\u0016v\u007fx1/rni")) || var13.equals(a("\u0010\nax")) || var13.equals(a("\u0011\u0002uzx\u0010\u0013~~"))) {
                        Element var17 = cleanup((Element)var12.clone());
                        var4.addContent(var17);
                     }
                  }

                  if (var15 == 0) {
                     continue;
                  }
               }

               copyDescription(var3, var1, var4);
               var10000 = var2;
               break;
            }

            if (var10000 && var5 != null) {
               var3.addMetadata((Element)var1, var4, var9);
            }

            return var4;
         }
      } catch (NullPointerException var16) {
         return null;
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
               var10003 = 67;
               break;
            case 1:
               var10003 = 99;
               break;
            case 2:
               var10003 = 27;
               break;
            case 3:
               var10003 = 29;
               break;
            default:
               var10003 = 29;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
