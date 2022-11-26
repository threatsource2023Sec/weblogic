package monfox.toolkit.snmp.metadata.gen;

import java.util.List;
import java.util.ListIterator;
import monfox.jdom.Element;

class p {
   private static final String a = "$Id: Validator.java,v 1.5 2003/01/17 20:44:51 sking Exp $";

   boolean a(n var1, Element var2) {
      boolean var3;
      label215: {
         int var4;
         label204: {
            label205: {
               label206: {
                  label207: {
                     label208: {
                        label209: {
                           var4 = Message.d;
                           var3 = true;
                           var3 = this.b(var1, var2) && var3;
                           var3 = this.a(var1, (Object)var2) && var3;
                           switch (var1.a()) {
                              case 0:
                                 var3 = this.c(var1, var2) && var3;
                                 if (var4 == 0) {
                                    break label215;
                                 }
                              case 1:
                                 var3 = this.d(var1, var2) && var3;
                                 if (var4 == 0) {
                                    break label215;
                                 }
                              case 2:
                                 break;
                              case 3:
                                 break label209;
                              case 4:
                                 break label208;
                              case 5:
                                 break label207;
                              case 6:
                                 break label206;
                              case 7:
                                 break label205;
                              case 8:
                                 break label204;
                              default:
                                 throw new RuntimeException(a("x+cpCO>fvI\u000e\u000f}kH\\p/PIX+cpC\u000e\u001cnuNJ+{pH@j_xT]"));
                           }

                           var3 = this.e(var1, var2) && var3;
                           if (var4 == 0) {
                              break label215;
                           }
                        }

                        var3 = this.f(var1, var2) && var3;
                        if (var4 == 0) {
                           break label215;
                        }
                     }

                     var3 = this.g(var1, var2) && var3;
                     if (var4 == 0) {
                        break label215;
                     }
                  }

                  var3 = this.h(var1, var2) && var3;
                  if (var4 == 0) {
                     break label215;
                  }
               }

               var3 = this.i(var1, var2) && var3;
               if (var4 == 0) {
                  break label215;
               }
            }

            var3 = this.j(var1, var2) && var3;
            if (var4 == 0) {
               break label215;
            }
         }

         var3 = this.k(var1, var2) && var3;
         if (var4 != 0) {
            throw new RuntimeException(a("x+cpCO>fvI\u000e\u000f}kH\\p/PIX+cpC\u000e\u001cnuNJ+{pH@j_xT]"));
         }
      }

      var3 = this.b(var1, (Object)var2) && var3;
      return var3;
   }

   boolean b(n var1, Element var2) {
      int var6 = Message.d;
      boolean var3 = true;
      List var4 = var2.getChildren();
      ListIterator var5 = var4.listIterator();

      boolean var10000;
      while(true) {
         if (var5.hasNext()) {
            var10000 = var1.b((Element)var5.next());
            if (var6 != 0) {
               break;
            }

            var3 = var10000 && var3;
            if (var6 == 0) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   boolean a(n var1, Object var2) {
      return true;
   }

   boolean b(n var1, Object var2) {
      return true;
   }

   boolean c(n var1, Element var2) {
      return true;
   }

   boolean d(n var1, Element var2) {
      return true;
   }

   boolean e(n var1, Element var2) {
      return true;
   }

   boolean f(n var1, Element var2) {
      return true;
   }

   boolean g(n var1, Element var2) {
      return true;
   }

   boolean h(n var1, Element var2) {
      return true;
   }

   boolean i(n var1, Element var2) {
      return true;
   }

   boolean j(n var1, Element var2) {
      return true;
   }

   boolean k(n var1, Element var2) {
      return true;
   }

   boolean l(n var1, Element var2) {
      return true;
   }

   boolean m(n var1, Element var2) {
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
               var10003 = 46;
               break;
            case 1:
               var10003 = 74;
               break;
            case 2:
               var10003 = 15;
               break;
            case 3:
               var10003 = 25;
               break;
            default:
               var10003 = 39;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
