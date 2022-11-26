package monfox.toolkit.snmp.metadata.gen;

import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import monfox.jdom.Attribute;
import monfox.jdom.Element;
import monfox.log.Logger;

class x extends q {
   private Logger a = null;
   private static final String b = "$Id: MODULE_COMPLIANCEValidator.java,v 1.2 2003/08/01 12:55:07 sking Exp $";

   x() {
      this.a = Logger.getInstance(a("K\u0018\u00163.C\b\u0011)/V\u001b\u001b',E\u0012\u0004\u0007\u000eo33\u0012\rt"));
   }

   boolean f(n var1, Element var2) {
      o var3 = (o)var1;
      String var4 = null;

      try {
         var4 = var2.getAttributeValue(a("h6?\u0003"));
         String var5 = var2.getAttributeValue(a("i>6"));
         if (var5 != null && var4 != null) {
            String var6 = (String)var3.oidToNameMap.get(var5);
            if (var6 != null && !var6.equals(var4)) {
               String var7 = bo.getParentValue(var2, a("K86\u0013\u000ec"), a("h6?\u0003"));
               var3.a(new ErrorMessage.DuplicateOID(var7, var5, var4, var6), var2);
               if (Message.d == 0) {
                  return true;
               }
            }

            var3.oidToNameMap.put(var5, var4);
         }
      } catch (Exception var8) {
      }

      return true;
   }

   boolean g(n var1, Element var2) {
      int var23 = Message.d;
      o var3 = (o)var1;

      try {
         Element var4 = new Element(a("K86\u0013\u000ec\u0014=\u000b\u0012j>3\b\u0001c"));
         String var5 = var2.getAttributeValue(a("h6?\u0003"));
         var4.addAttribute((Attribute)var2.getAttribute(a("h6?\u0003")).clone());
         var4.addAttribute((Attribute)var2.getAttribute(a("i>6")).clone());
         copyStatus(var3, var2, var4);
         copyDescription(var3, var2, var4);
         copyTextElement(var3, var2, var4, a("T\u0012\u0014#0C\u0019\u0011#"), a("T24\u0003\u0010c91\u0003"));
         Hashtable var6 = new Hashtable();
         List var7 = var2.getChildren(a("K\u0018\u00163.C"));
         if (var7 != null) {
            ListIterator var8 = var7.listIterator();

            label111:
            do {
               int var10000 = var8.hasNext();

               while(true) {
                  Element var9;
                  Element var10;
                  String var11;
                  Element var17;
                  String var18;
                  Element var19;
                  label89:
                  while(true) {
                     if (var10000 == 0) {
                        break label111;
                     }

                     var9 = (Element)var8.next();
                     var10 = new Element(a("K4\u001f\t\u0006s;7"));
                     var4.addContent(var10);
                     var11 = var9.getAttributeValue(a("k86\u0013\u000ec"));
                     if (var23 != 0) {
                        return true;
                     }

                     if (var11 != null) {
                        var6.put(var11, var11);
                        var10.addAttribute((Attribute)var9.getAttribute(a("k86\u0013\u000ec")).clone());
                     }

                     Element var12 = var9.getChild(a("K\u0016\u001c\"#R\u0018\u0000?=A\u0005\u001d32U"));
                     if (var12 == null) {
                        break;
                     }

                     Element var13 = new Element(a("K6<\u0002\u0003r8 \u001f%t8'\u0016\u0011"));
                     var10.addContent(var13);
                     List var14 = var12.getChildren(a("o#7\u000b"));
                     ListIterator var15 = var14.listIterator();

                     while(true) {
                        if (!var15.hasNext()) {
                           break label89;
                        }

                        Element var16 = (Element)var15.next();
                        var17 = new Element(a("A%=\u0013\u0012T24"));
                        var18 = var16.getText();
                        var10000 = var18.indexOf(58);
                        if (var23 != 0) {
                           break;
                        }

                        if (var10000 > 0) {
                           var6.put(var18.substring(0, var18.indexOf(58)), var17);
                        }

                        var17.addAttribute(a("h6?\u0003"), var18);
                        if (var3.isRichMode()) {
                           var19 = this.a(var3, var11, var18);
                           if (var19 != null) {
                              this.a(a("i>6"), var19, var17);
                           }
                        }

                        var13.addContent(var17);
                        if (var23 != 0) {
                           break label89;
                        }
                     }
                  }

                  List var26 = var9.getChildren();
                  if (var26 == null) {
                     break;
                  }

                  ListIterator var27 = var26.listIterator();

                  while(true) {
                     if (!var27.hasNext()) {
                        continue label111;
                     }

                     try {
                        Element var28 = (Element)var27.next();
                        var10000 = var28.getName().equals(a("C\u000f\u0011#2R\u001e\u001d("));
                        if (var23 != 0 || var23 != 0) {
                           break;
                        }

                        if (var10000 != 0) {
                           var17 = new Element(a("K4\u001d\u0004\bc4&"));
                           var10.addContent(var17);
                           var17.addAttribute((Attribute)var28.getAttribute(a("h6?\u0003")).clone());
                           var17.addAttribute((Attribute)var28.getAttribute(a("i>6")).clone());
                           Element var29 = var28.getChild(a("U\u000e\u001c2#^"));
                           Element var20;
                           Element var21;
                           if (var29 != null) {
                              var19 = var29.getChild(a("R.\"\u0003"));
                              var20 = bi.n(var3, var19);
                              if (var20 != null) {
                                 var21 = new Element(a("U.<\u0012\u0003~"));
                                 var17.addContent(var21);
                                 var21.addContent(var20);
                              }
                           }

                           var19 = var28.getChild(a("Q\u0005\u001b2'Y\u0004\u000b(6G\u000f"));
                           if (var19 != null) {
                              var20 = var19.getChild(a("R.\"\u0003"));
                              var21 = bi.n(var3, var20);
                              if (var21 != null) {
                                 Element var22 = new Element(a("Q%;\u0012\u0007U.<\u0012\u0003~"));
                                 var17.addContent(var22);
                                 var22.addContent(var21);
                              }
                           }

                           var20 = var28.getChild(a("K\u001e\u001c9#E\u0014\u001751"));
                           if (var20 != null) {
                              var17.addAttribute(a("g41\u0003\u0011u"), o.e.getProperty(var20.getText(), a("h6")));
                           }

                           copyDescription(var3, var28, var17);
                           if (var23 == 0) {
                              continue;
                           }
                        }

                        if (var28.getName().equals(a("A\u0005\u001d32"))) {
                           var17 = new Element(a("K4\u0015\u0014\rs'"));
                           var10.addContent(var17);
                           var17.addAttribute((Attribute)var28.getAttribute(a("h6?\u0003")).clone());
                           if (var3.isRichMode()) {
                              var18 = var28.getAttributeValue(a("h6?\u0003"));
                              var19 = this.a(var3, var11, var18);
                              if (var19 != null) {
                                 this.a(a("i>6"), var19, var17);
                              }
                           }

                           copyDescription(var3, var28, var17);
                        }
                     } catch (Exception var24) {
                        this.a.debug(a("c/1\u0003\u0012r>=\b"), var24);
                        if (var23 != 0) {
                           continue label111;
                        }
                     }
                  }
               }
            } while(var23 == 0);
         }

         var3.addMetadata(var2, var4, var6.keySet());
      } catch (NullPointerException var25) {
         this.a.error(a("c% \t\u0010"), var25);
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
               var10003 = 6;
               break;
            case 1:
               var10003 = 87;
               break;
            case 2:
               var10003 = 82;
               break;
            case 3:
               var10003 = 102;
               break;
            default:
               var10003 = 98;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
