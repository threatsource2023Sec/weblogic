package monfox.toolkit.snmp.metadata.gen;

import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import monfox.jdom.Attribute;
import monfox.jdom.Element;
import monfox.log.Logger;

class bg extends q {
   private Logger a = null;
   private static final String b = "$Id: TABLEValidator.java,v 1.26 2006/05/12 02:48:02 sking Exp $";

   bg() {
      this.a = Logger.getInstance(a("\u001bXAQ\r\u0019xot,.mlo"));
   }

   boolean g(n var1, Element var2) {
      int var33 = Message.d;
      o var3 = (o)var1;
      String var4 = a(":whs'8w");

      try {
         String var5 = bo.getParentValue(var2, a("\u0002vgh$*"), a("!xnx"));
         var4 = var2.getAttribute(a("!xnx")).getValue();
         Element var39 = new Element(a("\u001bxaq-"));
         var39.addAttribute((Attribute)var2.getAttribute(a("!xnx")).clone());
         Attribute var7 = var2.getAttribute(a(" pg"));
         if (var7 == null) {
            var3.a(new ErrorMessage.UnresolvedTableOID(var5, var4), var2);
            if (var33 == 0) {
               return true;
            }
         }

         Hashtable var8 = new Hashtable();
         var39.addAttribute((Attribute)var7.clone());
         Element var9 = var2.getParent();
         List var10 = var9.getChildren();
         ListIterator var11 = var10.listIterator();
         String var12 = var4 + a("a(");
         Element var13 = null;

         while(var11.hasNext()) {
            Element var14 = (Element)var11.next();
            String var15 = var14.getAttributeValue(a("=|e"));
            if (var33 != 0) {
               return true;
            }

            if (var15 != null && var15.equals(var12)) {
               var13 = var14;
               break;
            }
         }

         if (var13 == null) {
            var3.a(new ErrorMessage.UndefinedElement(var5, a(";xaq-o|mi:6"), a(")vq=<.{oxhh") + var4 + "'"), var2);
            return true;
         }

         Element var21;
         String var23;
         String var24;
         String var40;
         Element var41;
         List var44;
         ListIterator var45;
         label196: {
            var40 = var13.getAttribute(a(" pg")).getValue() + ".";
            var41 = new Element(a("\nwwo1"));
            var41.addAttribute((Attribute)var13.getAttribute(a("!xnx")).clone());
            var41.addAttribute((Attribute)var13.getAttribute(a(" pg")).clone());
            var39.addContent(var41);
            Element var16 = var13.getChild(a("\u0006WGX\u0010"));
            Element var17 = var13.getChild(a("\u000eLDP\r\u0001MP"));
            Element var42;
            if (var16 != null) {
               try {
                  var42 = new Element(a("\u0006wgx0*j"));
                  var41.addContent(var42);
                  var44 = var16.getChildren();
                  var45 = var44.listIterator();

                  while(true) {
                     if (!var45.hasNext()) {
                        break label196;
                     }

                     var21 = (Element)var45.next();
                     Element var22 = new Element(a("\u0000{ix+;Kf{"));
                     var22.addAttribute(a("!xnx"), var21.getText());
                     var42.addContent(var22);
                     var23 = var21.getAttributeValue(a("&tsq!*}"));
                     if (var33 != 0) {
                        break label196;
                     }

                     if (var23 != null && var23.equals(a(";kvx"))) {
                        var22.addAttribute(a("\"vgt.&|q"), a("&tsq!*}"));
                        if (var45.hasNext()) {
                           var3.a(new ErrorMessage.GenericWarning(var5, a("\u0006TSQ\u0001\n]#p'+pet-=9ls$69u|$&}#{'=9o|;;9JS\f\nA#x$*tfs<opm=<.{oxh*wwo1o>") + var13.getAttribute(a("!xnx")) + "'"));
                        }
                     }

                     var24 = var21.getText();
                     if (var24.indexOf(58) > 0) {
                        String var25 = var24.substring(0, var24.indexOf(58));
                        var8.put(var25, var25);
                     }

                     if (var3.isRichMode()) {
                        Element var51 = this.a(var3, var5, var24);
                        if (var51 != null) {
                           this.a(a(" pg"), var51, var22);
                           this.a(a("<tji1?|"), var51, var22);
                           this.a(a(" {ii1?|"), var51, var22);
                        }
                     }

                     if (var33 != 0) {
                        break label196;
                     }
                  }
               } catch (NullPointerException var37) {
                  if (var33 == 0) {
                     break label196;
                  }
               }
            }

            if (var17 != null) {
               try {
                  var42 = var17.getChild(a("&mfp"));
                  String var19 = var42.getText().trim();
                  Element var20 = new Element(a("\u000eldp-!mp"));
                  var41.addContent(var20);
                  var21 = new Element(a("\u0000{ix+;Kf{"));
                  var21.addAttribute(a("!xnx"), var19);
                  var20.addContent(var21);
                  if (var19.indexOf(58) > 0) {
                     var23 = var19.substring(0, var19.indexOf(58));
                     var8.put(var23, var23);
                  }
                  break label196;
               } catch (NullPointerException var36) {
                  if (var33 == 0) {
                     break label196;
                  }
               }
            }

            String var18 = var13.getAttributeValue(a("!xnx"));
            var3.a(new ErrorMessage.UndefinedElement(var5, a("\u0006WGX\u0010`XVZ\u0005\nWWN"), a(")vq=<.{oxh*wwo1o>") + var18 + "'"), var13);
         }

         Vector var43 = new Vector();
         var44 = var9.getChildren(a("\u0000[IX\u000b\u001b"));
         var45 = var44.listIterator();

         while(var45.hasNext()) {
            var21 = (Element)var45.next();

            try {
               Attribute var47 = var21.getAttribute(a(" pg"));
               if (var33 != 0) {
                  return true;
               }

               if (var47 != null && var47.getValue().startsWith(var40)) {
                  label199: {
                     var23 = null;
                     Element var50 = new Element(a("\fvoh%!"));
                     Attribute var52 = (Attribute)var21.getAttribute(a("!xnx")).clone();
                     var23 = var52.getValue();
                     var50.addAttribute(var52);
                     String var26 = var21.getAttribute(a(" pg")).getValue();
                     int var27 = var26.lastIndexOf(46);
                     String var28 = var26.substring(var27 + 1);
                     var50.addAttribute(a(",voh%!"), var28);
                     Attribute var29 = var21.getAttribute(a(" {ii1?|"));
                     if (var29 != null) {
                        var50.addAttribute((Attribute)var29.clone());

                        try {
                           int var53 = Integer.parseInt(var28);

                           while(var43.size() <= var53) {
                              var43.add((Object)null);
                              if (var33 != 0 && var33 != 0) {
                                 break label199;
                              }
                           }

                           var43.set(var53, var50);
                           break label199;
                        } catch (Exception var34) {
                           var3.a(new ErrorMessage.UndefinedElement(var5, a(";xaq-ozlq=\"w"), a(")vq=<.{oxhh") + var4 + a("h7#T&9xot,oZlq=\"w#T,o>") + var28 + "'"), var13);
                           if (var33 == 0) {
                              break label199;
                           }
                        }
                     }

                     Element var30;
                     String var31;
                     label131: {
                        var30 = var21.getChild(a("\u001b`sx"));
                        var31 = "";
                        if (var30 != null && var30.getAttribute(a(";`sx:*\u007f")) != null) {
                           String var32 = var30.getAttributeValue(a(";`sx:*\u007f"));
                           var31 = a("o9#:") + var32 + a("h9er:ozlq=\"w#:") + var23 + "'" + a("h9l{hh") + var4 + a("h9vo-<vok-+7\t");
                           if (var33 == 0) {
                              break label131;
                           }
                        }

                        var31 = a("o9#~'#lnshh") + var23 + a("h9l{h;xaq-o>") + var4 + a("olmo-<vok-+>-\u0017");
                     }

                     var3.a(new ErrorMessage.UnresolvedType(var5, var31), var30);
                  }

                  if (var23.indexOf(58) > 0) {
                     String var54 = var23.substring(0, var23.indexOf(58));
                     var8.put(var54, var54);
                  }
               }
            } catch (NullPointerException var35) {
               this.a.debug(a("*kqr:opm=<.{oxh=|pr$:mjr&"), var35);
               var23 = var21.getAttributeValue(a("!xnx"));
               if (var23 != null) {
                  var24 = a("o9#:") + var23 + a("h9eo'\"9w|*#|#:") + var4 + a("h9vs:*jlq>*}-=`<|f=8=|ut':j#x:=vqna");
                  var3.a(new ErrorMessage.UnresolvedOID(var5, var24));
               }
            }

            if (var33 != 0) {
               break;
            }
         }

         ListIterator var46 = var43.listIterator();
         int var48 = 0;

         label107: {
            while(var46.hasNext()) {
               Element var49 = (Element)var46.next();
               if (var33 != 0) {
                  break label107;
               }

               label103: {
                  if (var49 != null) {
                     var41.addContent(var49);
                     if (var33 == 0) {
                        break label103;
                     }
                  }

                  if (var48 > 0) {
                  }
               }

               ++var48;
               if (var33 != 0) {
                  break;
               }
            }

            copyStatus(var3, var13, var41);
            copyStatus(var3, var2, var39);
            copyDescription(var3, var13, var41);
            copyDescription(var3, var2, var39);
         }

         var3.addMetadata(var2, var39, var8.keySet());
      } catch (NullPointerException var38) {
         String var6 = bo.getParentValue(var2, a("\u0002vgh$*"), a("!xnx"));
         var3.a(new ErrorMessage.UnresolvedOID(var6, var4), var2);
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
               var10003 = 79;
               break;
            case 1:
               var10003 = 25;
               break;
            case 2:
               var10003 = 3;
               break;
            case 3:
               var10003 = 29;
               break;
            default:
               var10003 = 72;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
