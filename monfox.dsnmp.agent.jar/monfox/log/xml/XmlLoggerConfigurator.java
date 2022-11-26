package monfox.log.xml;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import monfox.log.Logger;
import monfox.log.SimpleLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class XmlLoggerConfigurator {
   public static boolean a;

   public void load(String var1) throws IOException, Exception {
      DocumentBuilderFactory var2 = DocumentBuilderFactory.newInstance();
      var2.setValidating(true);
      DocumentBuilder var3 = var2.newDocumentBuilder();
      var3.setErrorHandler(new b(b("WW2lDI"), var1));
      var3.setEntityResolver(new a());
      Document var4 = var3.parse(new InputSource(var1));
      Element var5 = var4.getDocumentElement();
      var5.normalize();
      this.load(var5);
   }

   public void load(Element var1) throws NoSuchElementException {
      if (var1 != null) {
         if (var1.getNodeName().equals(b("WW2lDI"))) {
            this.a(var1);
            if (!a) {
               return;
            }
         }

         throw new NoSuchElementException("'" + var1.getNodeName() + b("\u001c\u0018&cNNT1+C^\u0018rgN\\_0y\u0006"));
      }
   }

   private void a(Element var1) {
      boolean var22 = a;
      String var2 = var1.getAttribute(b("W]#nM"));
      String var3 = var1.getAttribute(b("TM!mHW]"));
      int var4 = c.getIntAttribute(var1, b("WW2[NIL"), -1);
      SimpleLogger.Provider var5 = this.a(var3, var4);
      int var6 = this.a(var2);
      String var38;
      String var41;
      String var44;
      String var48;
      int var55;
      if (var5 != null) {
         Logger.setProvider(var5);
         var5.setLevel(var6);
         String var7 = var1.getAttribute(b("IW9gNM]'XHA]"));
         if (var7 != null) {
            try {
               long var8 = Long.parseLong(var7);
               var5.setRolloverSize(var8);
            } catch (NumberFormatException var24) {
            }
         }

         var38 = var1.getAttribute(b("WW2O@O]"));
         if (var38 != null) {
            boolean var9 = var38.equalsIgnoreCase(b("OJ n"));
            var5.setDateLogged(var9);
         }

         var41 = var1.getAttribute(b("WW2JQKl<fD"));
         if (var41 != null) {
            boolean var10 = var41.equalsIgnoreCase(b("OJ n"));
            var5.setAppTimeLogged(var10);
         }

         var44 = var1.getAttribute(b("WW2H@O]2dSB"));
         if (var44 != null) {
            boolean var11 = var44.equalsIgnoreCase(b("OJ n"));
            var5.setCategoryLogged(var11);
         }

         var48 = var1.getAttribute(b("WW2JQR"));
         if (var48 != null) {
            boolean var12 = var48.equalsIgnoreCase(b("OJ n"));
            var5.setApiLogged(var12);
         }

         String var51 = var1.getAttribute(b("WW2LSTM%"));
         if (var51 != null) {
            boolean var13 = var51.equalsIgnoreCase(b("OJ n"));
            var5.setGroupLogged(var13);
         }

         var55 = c.getIntAttribute(var1, b("WW2FDVW'rlRT9bR"), -1);
         if (var55 > 500) {
            var5.startLoggingMemory(var55);
         }
      }

      Element var25 = c.getChild(var1, b("ZH<x"));
      int var10000;
      List var30;
      int var43;
      if (var25 != null) {
         label219: {
            var3 = var25.getAttribute(b("VW1n"));
            boolean var27 = this.a(var3, true);
            var30 = c.getChildren(var25, b("ZH<"));
            Vector var32 = new Vector();
            Iterator var35 = var30.iterator();

            while(true) {
               if (var35.hasNext()) {
                  Element var39 = (Element)var35.next();
                  var41 = c.getText(var39);
                  if (var22 || var22) {
                     break label219;
                  }

                  if (var41 == null) {
                     continue;
                  }

                  var10000 = var41.length();
                  if (var22) {
                     break;
                  }

                  if (var10000 == 0 && !var22) {
                     continue;
                  }

                  var32.add(var41);
                  var44 = var39.getAttribute(b("TM!mHW]"));
                  if (var44 != null && var44.length() > 0) {
                     var48 = var39.getAttribute(b("W]#nM"));
                     int var53 = this.a(var48);
                     var55 = c.getIntAttribute(var39, b("WW2[NIL"), -1);
                     SimpleLogger.Provider var14 = this.a(var44, var55);
                     if (var14 != null) {
                        var14.setLevel(var53);
                        Logger.setProvider(var41, var14);
                        String var15 = var39.getAttribute(b("IW9gNM]'XHA]"));
                        if (var15 != null) {
                           try {
                              long var16 = Long.parseLong(var15);
                              var14.setRolloverSize(var16);
                           } catch (NumberFormatException var23) {
                           }
                        }

                        String var57 = var39.getAttribute(b("WW2O@O]"));
                        if (var57 != null) {
                           boolean var17 = var57.equalsIgnoreCase(b("OJ n"));
                           var14.setDateLogged(var17);
                        }

                        String var58 = var39.getAttribute(b("WW2JQKl<fD"));
                        if (var58 != null) {
                           boolean var18 = var58.equalsIgnoreCase(b("OJ n"));
                           var14.setAppTimeLogged(var18);
                        }

                        String var59 = var39.getAttribute(b("WW2H@O]2dSB"));
                        if (var59 != null) {
                           boolean var19 = var59.equalsIgnoreCase(b("OJ n"));
                           var14.setCategoryLogged(var19);
                        }

                        String var60 = var39.getAttribute(b("WW2JQR"));
                        if (var60 != null) {
                           boolean var20 = var60.equalsIgnoreCase(b("OJ n"));
                           var14.setApiLogged(var20);
                        }

                        String var61 = var39.getAttribute(b("WW2LSTM%"));
                        if (var61 != null) {
                           boolean var21 = var61.equalsIgnoreCase(b("OJ n"));
                           var14.setGroupLogged(var21);
                        }
                     }
                  }

                  if (!var22) {
                     continue;
                  }
               }

               var10000 = var32.size();
               break;
            }

            String[] var40 = new String[var10000];
            var43 = 0;
            Iterator var46 = var32.iterator();

            while(true) {
               if (var46.hasNext()) {
                  var40[var43] = (String)var46.next();
                  ++var43;
                  if (!var22 || !var22) {
                     continue;
                  }
                  break;
               }

               if (var27) {
                  Logger.enableAPIs(var40);
                  if (!var22) {
                     break label219;
                  }
               }
               break;
            }

            Logger.disableAPIs(var40);
         }
      }

      Element var26 = c.getChild(var1, b("\\J:~QH"));
      if (var26 != null) {
         label222: {
            String var28 = var26.getAttribute(b("VW1n"));
            boolean var31 = this.a(var28, true);
            List var33 = c.getChildren(var26, b("\\J:~Q"));
            Vector var36 = new Vector();
            Iterator var42 = var33.iterator();

            while(true) {
               if (var42.hasNext()) {
                  Element var45 = (Element)var42.next();
                  var44 = c.getText(var45);
                  if (var22 || var22) {
                     break label222;
                  }

                  if (var44 == null) {
                     continue;
                  }

                  var10000 = var44.length();
                  if (var22) {
                     break;
                  }

                  if (var10000 == 0 && !var22) {
                     continue;
                  }

                  var36.add(var44);
                  if (!var22) {
                     continue;
                  }
               }

               var10000 = var36.size();
               break;
            }

            String[] var47 = new String[var10000];
            int var49 = 0;
            Iterator var50 = var36.iterator();

            while(true) {
               if (var50.hasNext()) {
                  var47[var49] = (String)var50.next();
                  ++var49;
                  if (var22) {
                     break;
                  }

                  if (!var22) {
                     continue;
                  }
               }

               if (var31) {
                  Logger.enableGroups(var47);
                  if (!var22) {
                     break label222;
                  }
               }
               break;
            }

            Logger.disableGroups(var47);
         }
      }

      Element var29 = c.getChild(var1, b("XY!nFTJ<nR"));
      if (var29 != null) {
         var30 = c.getChildren(var29, b("XY!nFTJ,"));
         Iterator var34 = var30.iterator();

         do {
            do {
               do {
                  if (!var34.hasNext()) {
                     return;
                  }

                  Element var37 = (Element)var34.next();
                  var38 = var37.getAttribute(b("W]#nM"));
                  var43 = this.a(var38);
                  var44 = c.getText(var37);
               } while(var44 == null);
            } while(var44.length() == 0 && !var22);

            String[] var54;
            label226: {
               StringTokenizer var52 = new StringTokenizer(var44, ".", false);
               var54 = new String[3];
               if (var52.countTokens() == 1) {
                  var54[2] = var52.nextToken();
                  if (!var22) {
                     break label226;
                  }
               }

               if (var52.countTokens() == 2) {
                  var54[0] = null;
                  var54[1] = var52.nextToken();
                  var54[2] = var52.nextToken();
                  if (!var22) {
                     break label226;
                  }
               }

               if (var52.countTokens() == 3) {
                  var54[0] = var52.nextToken();
                  var54[1] = var52.nextToken();
                  var54[2] = var52.nextToken();
               }
            }

            Logger var56 = Logger.getInstance(var54[0], var54[1], var54[2]);
            var56.setLevel(var43);
         } while(!var22);
      }

   }

   private boolean a(String var1, boolean var2) {
      if (var1 != null) {
         return var1.equalsIgnoreCase(b("^V4iM^\\"));
      } else {
         return var2;
      }
   }

   private int a(String var1) {
      if (var1 == null) {
         return 10;
      } else if (var1.equalsIgnoreCase(b("_]!jHW]1"))) {
         return 1;
      } else if (var1.equalsIgnoreCase(b("_]7~F"))) {
         return 2;
      } else if (var1.equalsIgnoreCase(b("XW8fR"))) {
         return 3;
      } else if (var1.equalsIgnoreCase(b("XW;mH\\"))) {
         return 4;
      } else if (var1.equalsIgnoreCase(b("RV3d"))) {
         return 5;
      } else if (var1.equalsIgnoreCase(b("LY'e"))) {
         return 6;
      } else if (var1.equalsIgnoreCase(b("^J'dS"))) {
         return 7;
      } else if (var1.equalsIgnoreCase(b("ZT9"))) {
         return 0;
      } else {
         return var1.equalsIgnoreCase(b("T^3")) ? 10 : 10;
      }
   }

   private SimpleLogger.Provider a(String var1, int var2) {
      return var1 == null && var2 <= 0 ? null : new SimpleLogger.Provider(var1, var2);
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 59;
               break;
            case 1:
               var10003 = 56;
               break;
            case 2:
               var10003 = 85;
               break;
            case 3:
               var10003 = 11;
               break;
            default:
               var10003 = 33;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
