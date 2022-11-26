package monfox.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

class a {
   private static final int a = 1;
   private static final int b = 2;
   private static final int c = 3;
   private static SimpleDateFormat d = new SimpleDateFormat(a("Svzi5gB.t|uGK=uG\"pc"));
   private Properties e = new Properties();

   public a() {
   }

   public String resolveVars(String var1) {
      int var7 = Logger.j;
      StringBuffer var2 = new StringBuffer();
      StringBuffer var3 = new StringBuffer();
      byte var4 = 1;
      int var5 = 0;

      String var10000;
      while(true) {
         if (var5 < var1.length()) {
            var10000 = var1;
            if (var7 != 0) {
               break;
            }

            char var6 = var1.charAt(var5);
            switch (var4) {
               case 1:
                  if (var6 == '$') {
                     var4 = 2;
                     if (var7 == 0) {
                        break;
                     }
                  }

                  var2.append(var6);
                  if (var7 == 0) {
                     break;
                  }
               case 2:
                  if (var6 == '{') {
                     var3 = new StringBuffer();
                     var4 = 3;
                     if (var7 == 0) {
                        break;
                     }
                  }

                  var2.append("$").append(var6);
                  var4 = 1;
                  if (var7 == 0) {
                     break;
                  }
               case 3:
                  label26: {
                     if (var6 == '}') {
                        this.a(var2, var3.toString());
                        var4 = 1;
                        if (var7 == 0) {
                           break label26;
                        }
                     }

                     var3.append(var6);
                  }
            }

            ++var5;
            if (var7 == 0) {
               continue;
            }
         }

         var10000 = var2.toString();
         break;
      }

      return var10000;
   }

   private void a(StringBuffer var1, String var2) {
      int var5 = Logger.j;
      if (var2.equals(a("Nnwu"))) {
         this.a(var1, d);
         if (var5 == 0) {
            return;
         }
      }

      String var3;
      if (var2.startsWith(a("Nnwu\""))) {
         var3 = var2.substring(5);
         SimpleDateFormat var4 = new SimpleDateFormat(var3);
         this.a(var1, var4);
         if (var5 == 0) {
            return;
         }
      }

      var3 = this.e.getProperty(var2);
      if (var3 == null) {
         var3 = System.getProperty(var2, "");
      }

      var1.append(var3);
   }

   private void a(StringBuffer var1, SimpleDateFormat var2) {
      var1.append(var2.format(new Date()));
   }

   public void setProperty(String var1, String var2) {
      if (var1 != null && var2 != null) {
         this.e.setProperty(var1, var2);
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
               var10003 = 42;
               break;
            case 1:
               var10003 = 15;
               break;
            case 2:
               var10003 = 3;
               break;
            case 3:
               var10003 = 16;
               break;
            default:
               var10003 = 24;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
