package monfox.toolkit.snmp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

public class NameUtil {
   private static final int a = 1;
   private static final int b = 2;
   private static final int c = 3;
   private static SimpleDateFormat d = new SimpleDateFormat(a("BP#govM>AjsD7mQ"));
   private Hashtable e = new Hashtable();

   public String resolveVars(String var1) {
      int var10 = WorkItem.d;
      String var2 = var1;
      int var3 = 0;
      int var4 = 1;

      do {
         int var10000 = var4;

         StringBuffer var5;
         label54:
         while(true) {
            if (var10000 <= 0 || var3 >= 5) {
               return var2;
            }

            var4 = 0;
            var5 = new StringBuffer();
            StringBuffer var6 = new StringBuffer();
            byte var7 = 1;
            int var8 = 0;

            while(true) {
               if (var8 >= var2.length()) {
                  break label54;
               }

               char var9 = var2.charAt(var8);
               var10000 = var7;
               if (var10 != 0) {
                  break;
               }

               switch (var7) {
                  case 1:
                     if (var9 == '$') {
                        var7 = 2;
                        if (var10 == 0) {
                           break;
                        }
                     }

                     var5.append(var9);
                     if (var10 == 0) {
                        break;
                     }
                  case 2:
                     if (var9 == '{') {
                        var6 = new StringBuffer();
                        var7 = 3;
                        if (var10 == 0) {
                           break;
                        }
                     }

                     var5.append("$").append(var9);
                     var7 = 1;
                     if (var10 == 0) {
                        break;
                     }
                  case 3:
                     label38: {
                        if (var9 == '}') {
                           ++var4;
                           this.a(var5, var6.toString());
                           var7 = 1;
                           if (var10 == 0) {
                              break label38;
                           }
                        }

                        var6.append(var9);
                     }
               }

               ++var8;
               if (var10 != 0) {
                  break label54;
               }
            }
         }

         var2 = var5.toString();
         ++var3;
      } while(var10 == 0);

      return var2;
   }

   private void a(StringBuffer var1, String var2) {
      int var8 = WorkItem.d;
      if (var2.toLowerCase().equals(a("_H.{"))) {
         this.a(var1, new Date(), d);
         if (var8 == 0) {
            return;
         }
      }

      String var3;
      if (var2.toLowerCase().startsWith(a("_H.{\u0018"))) {
         var3 = var2.substring(5);
         SimpleDateFormat var4 = new SimpleDateFormat(var3);
         this.a(var1, new Date(), var4);
         if (var8 == 0) {
            return;
         }
      }

      var3 = "";
      int var9 = var2.indexOf("=");
      if (var9 > 0) {
         var3 = var2.substring(var9 + 1);
         var2 = var2.substring(0, var9);
      }

      String var5 = null;
      var9 = var2.indexOf(58);
      if (var9 > 0) {
         var5 = var2.substring(var9 + 1);
         var2 = var2.substring(0, var9);
      }

      Object var6 = this.e.get(var2.toLowerCase());
      if (var6 == null) {
         var6 = System.getProperty(var2, var3);
      }

      if (var5 != null && var6 != null && var6 instanceof Date) {
         SimpleDateFormat var7 = new SimpleDateFormat(var5);
         this.a(var1, (Date)var6, var7);
      } else {
         var1.append(var6);
      }
   }

   private void a(StringBuffer var1, Date var2, SimpleDateFormat var3) {
      var1.append(var3.format(var2));
   }

   public void setVariable(String var1, Object var2) {
      if (var1 != null && var2 != null) {
         this.e.put(var1.toLowerCase(), var2);
      }
   }

   public void unsetVariable(String var1) {
      if (var1 != null) {
         this.e.remove(var1.toLowerCase());
      }
   }

   public Object getVariable(String var1) {
      return this.e.get(var1.toLowerCase());
   }

   public Iterator getVariableNames() {
      return this.e.keySet().iterator();
   }

   private static String a(String var0) {
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
               var10003 = 41;
               break;
            case 2:
               var10003 = 90;
               break;
            case 3:
               var10003 = 30;
               break;
            default:
               var10003 = 34;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
