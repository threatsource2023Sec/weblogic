package monfox.toolkit.snmp.metadata.gen;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import monfox.jdom.Attribute;
import monfox.jdom.Element;

class q extends p {
   private static final String a = "$Id: DEFAULTValidator.java,v 1.20 2003/08/01 12:55:07 sking Exp $";

   boolean c(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         if (var2.getAttribute(c("to;")) != null) {
            return true;
         }

         String var4 = var2.getAttribute(c("ug2I")).getValue();
         String var5 = var2.getAttribute(c("ic9")).getValue();
         var3.addRef(var4, var5, var2);
      } catch (NullPointerException var6) {
      }

      return true;
   }

   boolean d(n var1, Element var2) {
      return this.validateStage1(var1, var2, (String)null);
   }

   protected boolean validateStage1(n var1, Element var2, String var3) {
      o var4 = (o)var1;

      try {
         if (var2.getAttribute(c("to;")) != null) {
            return true;
         }

         if (var3 == null) {
            var3 = bo.getParentValue(var2, c("Vi;Y{~"), c("ug2I"));
         }

         String var5 = var2.getAttribute(c("ug2I")).getValue();
         String var6 = (String)var4.nameToOidMap.get(var3 + ":" + var5);
         if (var6 != null) {
            var2.addAttribute(c("to;"), var6);
            if (!var2.getName().equals(c("Mg3Yr")) && !var2.getName().equals(c("rr:A"))) {
               Element var7 = (Element)var4.oidToElementMap.get(var6);
               if (var7 != null) {
                  String var8 = var7.getAttributeValue(c("ug2I"));
                  if (var8 == null || var5 == null || !var8.equals(var5)) {
                     String var10 = bo.getParentValue(var7, c("Vi;Y{~"), c("ug2I"));
                     String var11 = var10 + ":" + var7.getAttributeValue(c("ug2I"));
                     String var12 = var3 + ":" + var5;
                     var4.a(new ErrorMessage.DuplicateOID(var3, var6, var11, var12), var2);
                  }

                  if (Message.d == 0) {
                     return true;
                  }
               }

               var4.oidToElementMap.put(var6, var2);
            }
         }
      } catch (NullPointerException var13) {
      }

      return true;
   }

   boolean g(n var1, Element var2) {
      o var3 = (o)var1;

      try {
         Element var4 = new Element(c("To;"));
         var4.addAttribute((Attribute)var2.getAttribute(c("ug2I")).clone());
         var4.addAttribute((Attribute)var2.getAttribute(c("to;")).clone());
         var3.addMetadata((Element)var2, var4, (Set)null);
      } catch (NullPointerException var5) {
      }

      return true;
   }

   boolean i(n var1, Element var2) {
      o var3 = (o)var1;
      String var4 = var2.getName();
      if (var3.optimizationTable.containsKey(var4)) {
         Element var5 = var2.getParent();
         var5.removeContent(var2);
      }

      return true;
   }

   boolean a(n var1, Element var2, Element var3, String var4) {
      return this.a(var1, var2, var3, (String)null, new String[]{var4});
   }

   boolean a(n var1, Element var2, Element var3, String var4, String[] var5) {
      return this.a(var1, var2, (Element)var3, var4, (String[])var5, (String[])null);
   }

   boolean a(n var1, Element var2, Element var3, String var4, String[] var5, String[] var6) {
      int var22 = Message.d;
      o var7 = (o)var1;
      String var8 = bo.getParentValue(var2, c("Vi;Y{~"), c("ug2I"));
      String var9 = null;
      if (var4 == null) {
         Element var10 = bo.getParent(var2, c("Vi;Y{~"));
         var9 = bo.getParentValue(var2, c("Vi;Y{~"), c("ug2I"));
      } else {
         var9 = var4;
      }

      String var24 = var2.getAttributeValue(c("ug2I"));

      int var10000;
      try {
         List var11 = var3.getChildren();
         ListIterator var12 = var11.listIterator();

         label136:
         do {
            var10000 = var12.hasNext();

            Element var13;
            String var14;
            String var16;
            label132:
            while(true) {
               if (var10000 == 0) {
                  break label136;
               }

               var13 = (Element)var12.next();
               var14 = var13.getText();
               Element var15 = null;
               var10000 = var14.indexOf(46);
               if (var22 != 0) {
                  return (boolean)var10000;
               }

               if (var10000 > 0 || var14.indexOf(58) > 0) {
                  label153: {
                     var14 = var14.replace('.', ':');
                     var15 = (Element)var7.nameToElementMap.get(var14);
                     if (var15 == null) {
                        var14 = var14.substring(var14.indexOf(58) + 1);
                        if (var22 == 0) {
                           break label153;
                        }
                     }

                     var13.setText(var14);
                  }
               }

               if (var15 == null) {
                  var15 = (Element)var7.nameToElementMap.get(var9 + ":" + var14);
               }

               var16 = null;
               int var17 = 0;
               Element var18 = null;

               Element var25;
               label98: {
                  while(var15 != null) {
                     var25 = var15;
                     if (var22 != 0) {
                        break label98;
                     }

                     if (var15 == var18 || var17 >= 10 || !var15.getName().equals(c("rr:A"))) {
                        break;
                     }

                     var16 = bo.getParentValue(var15, c("]T\u0010a"), c("vi;Y{~"));
                     var18 = var15;
                     var15 = (Element)var7.nameToElementMap.get(var16 + ":" + var14);
                     ++var17;
                     if (var22 != 0) {
                        break;
                     }
                  }

                  var25 = var15;
               }

               boolean var26;
               label147: {
                  if (var25 == null) {
                     boolean var19 = false;
                     if (var6 != null) {
                        int var20 = 0;

                        while(var20 < var6.length) {
                           var26 = var6[var20].equals(var24);
                           if (var22 != 0) {
                              break label147;
                           }

                           if (var26) {
                              var19 = true;
                              if (var22 == 0) {
                                 break;
                              }
                           }

                           ++var20;
                           if (var22 != 0) {
                              break;
                           }
                        }
                     }

                     var15 = (Element)var7.flatNameToElementMap.get(var14);
                     if (var15 != null) {
                        var7.a(new ErrorMessage.ModuleMismatch(var8, var9, var14, var24), var13);
                        var16 = bo.getParentValue(var15, c("Vi;Y{~"), c("ug2I"));
                     }
                  }

                  if (var15 == null) {
                     var7.a(new ErrorMessage.UnresolvedReference(var8, c("Nh-Idtj)Is;t:Jric1Or"), var14, var24), var13);
                     if (var22 == 0) {
                        continue label136;
                     }
                  }

                  var26 = this.a(var15.getName(), var5);
               }

               if (var26) {
                  break;
               }

               this.b(var15.getName());
               StringBuffer var27 = new StringBuffer();
               int var21 = 0;

               while(var21 < var5.length) {
                  var10000 = var27.length();
                  if (var22 != 0) {
                     continue label132;
                  }

                  if (var10000 > 0) {
                     var27.append("|");
                  }

                  var27.append(this.b(var5[var21]));
                  ++var21;
                  if (var22 != 0) {
                     break;
                  }
               }

               var7.a(new ErrorMessage.ElementMismatch(var8, c("Lt0Bp;r&\\r;`0^"), var14, var24, var27.toString()), var13);
               if (var22 == 0) {
                  continue label136;
               }
               break;
            }

            if (var16 != null) {
               var13.setText(var16 + ":" + var14);
            }
         } while(var22 == 0);
      } catch (NullPointerException var23) {
      }

      var10000 = 1;
      return (boolean)var10000;
   }

   void a(String var1, Element var2, Element var3) {
      try {
         var3.addAttribute((Attribute)var2.getAttribute(var1).clone());
      } catch (Exception var5) {
      }

   }

   private boolean a(String var1, String[] var2) {
      int var4 = Message.d;
      if (var1 == null) {
         return false;
      } else {
         int var3 = 0;

         boolean var10000;
         while(true) {
            if (var3 < var2.length) {
               var10000 = var1.equals(var2[var3]);
               if (var4 != 0) {
                  break;
               }

               if (var10000) {
                  return true;
               }

               ++var3;
               if (var4 == 0) {
                  continue;
               }
            }

            var10000 = false;
            break;
         }

         return var10000;
      }
   }

   private String b(String var1) {
      var1 = var1.replace('_', '-');
      return var1;
   }

   protected static void copyTextElement(n var0, Element var1, Element var2, String var3, String var4) {
      Element var5 = var1.getChild(var3);
      if (var5 != null) {
         Element var6 = new Element(var4);
         var6.setText(a(var5.getText()));
         var2.addContent(var6);
      }

   }

   protected static void copyStatus(n var0, Element var1, Element var2) {
      Element var3 = var1.getChild(c("HR\u001exBH"));
      if (var3 != null) {
         var2.addAttribute(c("hr>Xbh"), var3.getText());
      }

   }

   protected static void copyDescription(n var0, Element var1, Element var2) {
      Element var3 = var1.getChild(c("_C\foERV\u000beXU"));
      if (var3 != null && var0.i()) {
         Element var4 = new Element(c("_c,Oerv+Exu"));
         var4.setText(a(var3.getText()));
         var2.addContent(var4);
      }

   }

   protected static Element cleanup(Element var0) {
      int var4 = Message.d;
      if (var0.getAttribute(c("ko")) != null) {
         var0.removeAttribute(c("ko"));
      }

      List var1 = var0.getChildren();
      ListIterator var2 = var1.listIterator();

      Element var10000;
      while(true) {
         if (var2.hasNext()) {
            Element var3 = (Element)var2.next();
            var10000 = cleanup(var3);
            if (var4 != 0) {
               break;
            }

            if (var4 == 0) {
               continue;
            }
         }

         var10000 = var0;
         break;
      }

      return var10000;
   }

   boolean a(n var1, Element var2, String var3, String var4, String var5, String[] var6) {
      int var17 = Message.d;
      o var7 = (o)var1;

      int var19;
      try {
         label92: {
            String var8 = bo.getParentValue(var2, c("Vi;Y{~"), c("ug2I"));
            String var9 = var4;
            Element var10 = null;
            if (var4.indexOf(46) > 0 || var4.indexOf(58) > 0) {
               var9 = var4.replace('.', ':');
               var10 = (Element)var7.nameToElementMap.get(var9);
               if (var10 == null) {
                  var9 = var9.substring(var9.indexOf(58) + 1);
               }
            }

            if (var10 == null) {
               var10 = (Element)var7.nameToElementMap.get(var3 + ":" + var9);
            }

            String var11 = null;
            int var12 = 0;
            Element var13 = null;

            Element var10000;
            label63: {
               while(var10 != null) {
                  var10000 = var10;
                  if (var17 != 0) {
                     break label63;
                  }

                  if (var10 == var13 || var12 >= 10 || !var10.getName().equals(c("rr:A"))) {
                     break;
                  }

                  var11 = bo.getParentValue(var10, c("]T\u0010a"), c("vi;Y{~"));
                  var13 = var10;
                  var10 = (Element)var7.nameToElementMap.get(var11 + ":" + var9);
                  ++var12;
                  if (var17 != 0) {
                     break;
                  }
               }

               var10000 = var10;
            }

            if (var10000 == null) {
               var10 = (Element)var7.flatNameToElementMap.get(var9);
               if (var10 != null) {
                  var7.a(new ErrorMessage.ModuleMismatch(var8, var3, var9, var5), var2);
               }
            }

            if (var10 == null) {
               var7.a(new ErrorMessage.UnresolvedReference(var8, c("Nh-Idtj)Is;t:Jric1Or"), var9, var5), var2);
               if (var17 == 0) {
                  break label92;
               }
            }

            if (!this.a(var10.getName(), var6)) {
               this.b(var10.getName());
               StringBuffer var15 = new StringBuffer();
               int var16 = 0;

               while(var16 < var6.length) {
                  var19 = var15.length();
                  if (var17 != 0) {
                     return (boolean)var19;
                  }

                  if (var19 > 0) {
                     var15.append("|");
                  }

                  var15.append(this.b(var6[var16]));
                  ++var16;
                  if (var17 != 0) {
                     break;
                  }
               }

               var7.a(new ErrorMessage.ElementMismatch(var8, c("Lt0Bp;r&\\r;`0^"), var9, var5, var15.toString()), var2);
            }
         }
      } catch (NullPointerException var18) {
      }

      var19 = 1;
      return (boolean)var19;
   }

   private static String a(String var0) {
      int var8 = Message.d;
      StringBuffer var1 = new StringBuffer();
      if (var0 == null) {
         return null;
      } else {
         var0 = var0.trim();
         char var2 = '+';
         boolean var3 = false;
         int var4 = 80;
         int var5 = 0;
         int var6 = 0;

         String var10000;
         while(true) {
            if (var6 < var0.length()) {
               var10000 = var0;
               if (var8 != 0) {
                  break;
               }

               char var7 = var0.charAt(var6);
               if ((var7 == ' ' || var7 == '\t') && (var2 == '\n' || var2 == '\r')) {
                  var3 = true;
                  var5 = 0;
               }

               label47: {
                  if (var3) {
                     if (var7 == ' ' || var7 == '\t') {
                        ++var5;
                        if (var5 <= var4) {
                           break label47;
                        }

                        var3 = false;
                        var1.append(var7);
                        if (var8 == 0) {
                           break label47;
                        }
                     }

                     if (var5 > 0 && var5 < var4) {
                        var4 = var5;
                     }

                     var3 = false;
                     var1.append(var7);
                     if (var8 == 0) {
                        break label47;
                     }
                  }

                  var1.append(var7);
               }

               var2 = var7;
               ++var6;
               if (var8 == 0) {
                  continue;
               }
            }

            var10000 = var1.toString();
            break;
         }

         return var10000;
      }
   }

   Element a(o var1, String var2, String var3) {
      String var4 = var3.replace('.', ':');
      if (var4.indexOf(58) < 0) {
         var4 = var2 + ':' + var3;
      }

      Element var5 = (Element)var1.nameToElementMap.get(var4);
      if (var5 == null) {
         var5 = (Element)var1.flatNameToElementMap.get(var3);
      }

      return var5;
   }

   private static String c(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 27;
               break;
            case 1:
               var10003 = 6;
               break;
            case 2:
               var10003 = 95;
               break;
            case 3:
               var10003 = 44;
               break;
            default:
               var10003 = 23;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
