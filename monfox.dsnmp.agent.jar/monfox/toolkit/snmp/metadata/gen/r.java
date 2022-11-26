package monfox.toolkit.snmp.metadata.gen;

import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import monfox.jdom.Attribute;
import monfox.jdom.Element;

class r extends q {
   private static final String a = "$Id: AGENT_CAPABILITIESValidator.java,v 1.2 2003/08/01 12:55:07 sking Exp $";

   boolean f(n var1, Element var2) {
      o var3 = (o)var1;
      String var4 = null;

      try {
         var4 = var2.getAttributeValue(a("\u001f5C2"));
         String var5 = var2.getAttributeValue(a("\u001e=J"));
         if (var5 != null && var4 != null) {
            String var6 = (String)var3.oidToNameMap.get(var5);
            if (var6 != null && !var6.equals(var4)) {
               String var7 = bo.getParentValue(var2, a("<;J\"T\u0014"), a("\u001f5C2"));
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
      int var29 = Message.d;
      o var3 = (o)var1;

      try {
         String var4 = bo.getParentValue(var2, a("<;J\"T\u0014"), a("\u001f5C2"));
         Element var5 = new Element(a("03K9L25^6Z\u00188G#Q\u0014'"));
         String var6 = var2.getAttributeValue(a("\u001f5C2"));
         var5.addAttribute((Attribute)var2.getAttribute(a("\u001f5C2")).clone());
         var5.addAttribute((Attribute)var2.getAttribute(a("\u001e=J")).clone());
         copyStatus(var3, var2, var5);
         copyTextElement(var3, var2, var5, a("!\u0006a\u0013m2\u0000q\u0005}=\u0011o\u0004}"), a("!&A3M\u0012 |2T\u00145]2"));
         copyDescription(var3, var2, var5);
         copyTextElement(var3, var2, var5, a("#\u0011h\u0012j4\u001am\u0012"), a("#1H2J\u0014:M2"));
         Hashtable var7 = new Hashtable();
         List var8 = var2.getChildren(a("\"\u0001~\u0007w#\u0000}"));
         if (var8 != null) {
            ListIterator var9 = var8.listIterator();

            label134:
            do {
               int var10000 = var9.hasNext();

               label131:
               while(true) {
                  if (var10000 == 0) {
                     break label134;
                  }

                  Element var10 = (Element)var9.next();
                  Element var11 = new Element(a("\"!^'W\u0003 ]"));
                  var5.addContent(var11);
                  String var12 = var10.getAttributeValue(a("\u001c;J\"T\u0014"));
                  var7.put(var12, var12);
                  var11.addAttribute((Attribute)var10.getAttribute(a("\u001c;J\"T\u0014")).clone());
                  Element var13 = var10.getChild(a("8\u001am\u001bm5\u0011}"));
                  if (var29 != 0) {
                     return true;
                  }

                  Element var35 = var13;

                  label129:
                  while(true) {
                     Element var17;
                     Element var18;
                     Element var20;
                     if (var35 != null) {
                        Element var14 = new Element(a("8:M;M\u00151]"));
                        var11.addContent(var14);
                        List var15 = var13.getChildren(a("\u0018 K:"));
                        ListIterator var16 = var15.listIterator();

                        while(var16.hasNext()) {
                           var17 = (Element)var16.next();
                           var18 = new Element(a("6&A\"H#1H"));
                           String var19 = var17.getText();
                           var10000 = var19.indexOf(58);
                           if (var29 != 0) {
                              continue label131;
                           }

                           if (var10000 > 0) {
                              var7.put(var19.substring(0, var19.indexOf(58)), var18);
                           }

                           var18.addAttribute(a("\u001f5C2"), var19);
                           if (var3.isRichMode()) {
                              var20 = this.a(var3, var12, var19);
                              if (var20 != null) {
                                 this.a(a("\u001e=J"), var20, var18);
                              }
                           }

                           var14.addContent(var18);
                           if (var29 != 0) {
                              break;
                           }
                        }
                     }

                     List var33 = var10.getChildren(a("'\u0015|\u001ey%\u001da\u0019"));
                     if (var33 == null) {
                        continue label134;
                     }

                     ListIterator var34 = var33.listIterator();

                     while(true) {
                        boolean var37 = var34.hasNext();

                        label125:
                        while(true) {
                           if (!var37) {
                              continue label134;
                           }

                           try {
                              Element var36 = (Element)var34.next();
                              var17 = new Element(a("'5\\>Y\u0005=A9"));
                              var11.addContent(var17);
                              var17.addAttribute((Attribute)var36.getAttribute(a("\u001f5C2")).clone());
                              var17.addAttribute((Attribute)var36.getAttribute(a("\u001e=J")).clone());
                              var18 = var36.getChild(a("\"\r`\u0003y)"));
                              var35 = var18;
                              if (var29 != 0 || var29 != 0) {
                                 continue label129;
                              }

                              Element var21;
                              Element var38;
                              if (var18 != null) {
                                 var38 = var18.getChild(a("%-^2"));
                                 var20 = bi.n(var3, var38);
                                 if (var20 != null) {
                                    var21 = new Element(a("\"-@#Y\t"));
                                    var17.addContent(var21);
                                    var21.addContent(var20);
                                 }
                              }

                              var38 = var36.getChild(a("&\u0006g\u0003}.\u0007w\u0019l0\f"));
                              Element var22;
                              if (var38 != null) {
                                 var20 = var38.getChild(a("%-^2"));
                                 var21 = bi.n(var3, var20);
                                 if (var21 != null) {
                                    var22 = new Element(a("&&G#]\"-@#Y\t"));
                                    var17.addContent(var22);
                                    var22.addContent(var21);
                                 }
                              }

                              var20 = var36.getChild(a("0\u0017m\u0012k\""));
                              if (var20 != null) {
                                 var17.addAttribute(a("\u00107M2K\u0002"), o.e.getProperty(var20.getText(), a("\u001f5")));
                              }

                              var21 = var36.getChild(a("2\u0006k\u0016l8\u001b`\bj4\u0005{\u001ej4\u0007"));
                              if (var21 != null) {
                                 var22 = new Element(a("2&K6L\u0018;@\u0005]\u0000!G%]\u0002"));
                                 var17.addContent(var22);
                                 List var23 = var21.getChildren(a("\u0018 K:"));
                                 ListIterator var24 = var23.listIterator();

                                 while(var24.hasNext()) {
                                    Element var25 = (Element)var24.next();
                                    Element var26 = new Element(a(">6D2[\u0005\u0006K1"));
                                    String var27 = var25.getText();
                                    var26.addAttribute(a("\u001f5C2"), var27);
                                    var37 = var3.isRichMode();
                                    if (var29 != 0) {
                                       continue label125;
                                    }

                                    if (var37) {
                                       Element var28 = this.a(var3, var12, var27);
                                       if (var28 != null) {
                                          this.a(a("\u001e=J"), var28, var26);
                                          this.a(a("\u00029G#A\u00011"), var28, var26);
                                          this.a(a("\u001e6D#A\u00011"), var28, var26);
                                       }
                                    }

                                    var22.addContent(var26);
                                    if (var29 != 0) {
                                       break;
                                    }
                                 }
                              }

                              try {
                                 String var39 = var36.getChild(a("5\u0011h\u0001y=")).getText().trim();
                                 if (var39.startsWith("{") && var39.endsWith("}")) {
                                    var39 = var39.substring(1, var39.length() - 1).trim();
                                 }

                                 var17.addAttribute(a("\u00151H!Y\u001d"), var39);
                              } catch (NullPointerException var30) {
                              }

                              copyDescription(var3, var36, var17);
                           } catch (Exception var31) {
                              if (var29 != 0) {
                                 continue label134;
                              }
                           }
                           break;
                        }
                     }
                  }
               }
            } while(var29 == 0);
         }

         var3.addMetadata(var2, var5, var7.keySet());
      } catch (NullPointerException var32) {
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
               var10003 = 113;
               break;
            case 1:
               var10003 = 84;
               break;
            case 2:
               var10003 = 46;
               break;
            case 3:
               var10003 = 87;
               break;
            default:
               var10003 = 56;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
