package monfox.toolkit.snmp.metadata.gen;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.jdom.Attribute;
import monfox.jdom.Element;
import monfox.toolkit.snmp.util.FormatUtil;

class s extends q {
   private static final String a = "$Id: DocumentValidator.java,v 1.26 2003/07/14 15:25:56 sking Exp $";

   boolean b(n var1, Element var2) {
      switch (var1.a()) {
         case 0:
            this.n(var1, var2);
         default:
            return super.b(var1, var2);
         case 1:
            return true;
         case 2:
            return true;
      }
   }

   void n(n var1, Element var2) {
      int var8 = Message.d;
      Hashtable var3 = new Hashtable();
      List var4 = var2.getChildren(a("I78p3a"));
      ListIterator var5 = var4.listIterator();

      while(var5.hasNext()) {
         label17: {
            Element var6 = (Element)var5.next();
            String var7 = var6.getAttributeValue(a("j91`"));
            if (var3.containsKey(var7)) {
               var1.a((ErrorMessage)(new ErrorMessage.DuplicateModule(var7)), (Element)var6);
               var2.removeContent(var6);
               if (var8 == 0) {
                  break label17;
               }
            }

            var3.put(var7, var6);
         }

         if (var8 != 0) {
            break;
         }
      }

   }

   boolean c(n var1, Element var2) {
      return true;
   }

   boolean d(n param1, Element param2) {
      // $FF: Couldn't be decompiled
   }

   boolean e(n var1, Element var2) {
      int var24 = Message.d;
      o var3 = (o)var1;
      Vector var4 = var3.typeRefs;
      Hashtable var5 = var3.definedTypes;
      this.a(var5, a("C9)b:"));
      this.a(var5, a("G7)k+a*j1"));
      this.a(var5, a("G7)k+a*"));
      this.a(var5, a("G7)k+a*o7"));
      this.a(var5, a("Q6/l8j=8"));
      this.a(var5, a("Q6/l8j=86m"));
      this.a(var5, a("K(=t*a"));
      this.a(var5, a("C9)b:"));
      this.a(var5, a("C9)b:7j"));
      this.a(var5, a("J=(r0v3\u001da;v=/v"));
      this.a(var5, a("M(\u001da;v=/v"));
      this.a(var5, a("P11`\u000bm;7v"));
      this.a(var5, a("M6(`8a*o7"));
      boolean var6 = false;
      int var7 = 0;
      Hashtable var8 = new Hashtable();

      label134:
      while(true) {
         int var28 = 0;
         Enumeration var10000 = var4.elements();

         String var12;
         String var13;
         Hashtable var29;
         int var34;
         do {
            Enumeration var9 = var10000;

            label129:
            while(true) {
               var34 = var9.hasMoreElements();

               do {
                  label125: {
                     if (var34 != 0) {
                        try {
                           Element var10 = (Element)var9.nextElement();
                           String var11 = bo.getParentValue(var10, a("I78p3a"), a("j91`"));
                           var12 = var10.getAttribute(a("p!,`-a>")).getValue();
                           var13 = null;
                           int var14 = var12.indexOf(58);
                           var34 = var14;
                           if (var24 == 0 && var24 == 0) {
                              if (var14 < 0) {
                                 var13 = var11 + ":" + var12;
                              } else {
                                 var13 = var12;
                                 var12 = var12.substring(var14 + 1);
                              }

                              Element var33 = (Element)var5.get(var13);
                              boolean var35 = false;
                              if (var33 == null) {
                                 var33 = (Element)var5.get(var12);
                              }

                              if (var33 == null && var7 > 0) {
                                 var33 = (Element)var5.get("*" + var12);
                                 var35 = true;
                              }

                              if (var33 == null) {
                                 continue label129;
                              }

                              String var17;
                              if (var33.getName().equals(a("m,9h"))) {
                                 var17 = bo.getParentValue(var33, a("B\n\u0013H"), a("i78p3a"));
                                 String var18 = var33.getText();
                                 var10.getAttribute(a("p!,`-a>")).setValue(var17 + ":" + var18);
                                 ++var28;
                                 if (var24 == 0) {
                                    continue label129;
                                 }
                              }

                              var17 = bo.getParentValue(var33, a("I78p3a"), a("j91`"));
                              Attribute var36 = var10.getAttribute(a("p!,`-a>"));
                              var36.setValue(var17 + ":" + var33.getAttributeValue(a("j91`")));
                              Attribute var19 = var10.getAttribute(a("p!,`"));
                              var19.setValue(var33.getAttribute(a("p!,`")).getValue());
                              var4.removeElement(var10);
                              String var20;
                              if (var35 && var8.get(var13) == null) {
                                 var20 = var11 == null ? "" : var11;
                                 var3.a(new ErrorMessage.ModuleMismatch(var17, var20, var12, (String)null));
                                 var8.put(var13, var13);
                              }

                              try {
                                 var20 = var10.getAttributeValue(a("j91`"));
                                 if (var20 != null) {
                                    var5.put(var11 + ":" + var20, var10);
                                    var5.put("*" + var20, var10);
                                    if (var3.moduleAliases.containsKey(var11)) {
                                       String var21 = (String)var3.moduleAliases.get(var11);
                                       StringTokenizer var22 = new StringTokenizer(var21, a(">t|"), false);

                                       while(var22.hasMoreElements()) {
                                          String var23 = var22.nextToken();
                                          var5.put(var23 + ":" + var20, var10);
                                          if (var24 != 0 || var24 != 0) {
                                             break;
                                          }
                                       }
                                    }
                                 }
                              } catch (NullPointerException var26) {
                                 var26.printStackTrace();
                              }

                              ++var28;
                              continue label129;
                           }
                           break label125;
                        } catch (NullPointerException var27) {
                           if (var24 == 0) {
                              continue label129;
                           }
                        }
                     }

                     var34 = var28;
                  }

                  if (var34 == 0) {
                     ++var7;
                  }

                  if (var28 != 0) {
                     continue label134;
                  }

                  var34 = var7;
               } while(var24 != 0);

               if (var7 <= 1) {
                  continue label134;
               }

               var29 = new Hashtable();
               var10000 = var4.elements();
               break;
            }
         } while(var24 != 0);

         Enumeration var30 = var10000;

         while(true) {
            if (var30.hasMoreElements()) {
               try {
                  Element var31 = (Element)var30.nextElement();
                  var12 = var31.getAttribute(a("p!,`-a>")).getValue();
                  var13 = bo.getParentValue(var31, a("I78p3a"), a("j91`"));
                  String var32 = a("Q\u0016\u0017K\u0010S\u0016");
                  String var15 = var12;
                  StringTokenizer var16 = new StringTokenizer(var12, a(">v"), false);
                  var34 = var16.countTokens();
                  if (var24 != 0 || var24 != 0) {
                     break;
                  }

                  if (var34 >= 2) {
                     var32 = var16.nextToken();
                     var15 = var16.nextToken();
                  }

                  this.addMissingElement(var29, var32, var15, var13);
                  continue;
               } catch (NullPointerException var25) {
                  if (var24 == 0) {
                     continue;
                  }
               }
            }

            this.generateMissingErrors(5, a("Q6.`,k4*`;$,%u:wx:w0ix1j;q49"), a("v=:`-a6?`;$:%"), var29, var3);
            var34 = 1;
            break;
         }

         return (boolean)var34;
      }
   }

   private void a(Hashtable var1, String var2) {
      var1.put(var2, (new Element(a("P!,`"))).addAttribute(a("p!,`"), var2));
   }

   public void addMissingElement(Hashtable var1, String var2, String var3, String var4) {
      Hashtable var5 = (Hashtable)var1.get(var2);
      if (var5 == null) {
         var5 = new Hashtable();
         var1.put(var2, var5);
      }

      Hashtable var6 = (Hashtable)var5.get(var3);
      if (var6 == null) {
         var6 = new Hashtable();
         var5.put(var3, var6);
      }

      var6.put(var4, var4);
   }

   public void generateMissingErrors(int var1, String var2, String var3, Hashtable var4, o var5) {
      int var15 = Message.d;
      Enumeration var6 = var4.keys();

      do {
         Enumeration var10000 = var6;

         String var7;
         StringBuffer var8;
         label61:
         while(true) {
            if (!var10000.hasMoreElements()) {
               return;
            }

            var7 = (String)var6.nextElement();
            var8 = new StringBuffer();
            Hashtable var9 = (Hashtable)var4.get(var7);
            Enumeration var10 = var9.keys();

            while(true) {
               boolean var16 = var10.hasMoreElements();

               label56:
               while(true) {
                  if (!var16) {
                     break label61;
                  }

                  String var11 = (String)var10.nextElement();
                  var8.append(a("$x|") + FormatUtil.pad("'" + var11 + "'", 20, 'l'));
                  var8.append(" ").append(var3).append(" ");
                  Hashtable var12 = (Hashtable)var9.get(var11);
                  var10000 = var12.keys();
                  if (var15 != 0) {
                     continue label61;
                  }

                  Enumeration var13 = var10000;

                  while(true) {
                     if (!var13.hasMoreElements()) {
                        break label56;
                     }

                     String var14 = (String)var13.nextElement();
                     var8.append("'").append(var14).append("'");
                     var16 = var13.hasMoreElements();
                     if (var15 != 0) {
                        break;
                     }

                     if (var16) {
                        var8.append(",");
                     }

                     if (var15 != 0) {
                        break label56;
                     }
                  }
               }

               var8.append("\n");
               if (var15 != 0) {
                  break label61;
               }
            }
         }

         switch (var1) {
            case 5:
               var5.a(new ErrorMessage.UnresolvedType(var7, var8.toString()));
               if (var15 == 0) {
                  break;
               }
            case 14:
               var5.a(new ErrorMessage.CircularDependency(var7, var8.toString()));
               if (var15 == 0) {
                  break;
               }
            case 4:
               var5.a(new ErrorMessage.UnresolvedOID(var7, var8.toString()));
               if (var15 == 0) {
                  break;
               }
            case 13:
               var5.a(new ErrorMessage.UndefinedOID(var7, var8.toString()));
               if (var15 == 0) {
                  break;
               }
            case 26:
               var5.a(new ErrorMessage.InvalidOID(var7, var8.toString()));
         }
      } while(var15 == 0);

   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 4;
               break;
            case 1:
               var10003 = 88;
               break;
            case 2:
               var10003 = 92;
               break;
            case 3:
               var10003 = 5;
               break;
            default:
               var10003 = 95;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
