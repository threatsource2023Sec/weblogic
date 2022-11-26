package antlr.build;

import antlr.Utils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class Tool {
   public String os = null;
   // $FF: synthetic field
   static Class class$antlr$build$Tool;

   public Tool() {
      this.os = System.getProperty("os.name");
   }

   public static void main(String[] var0) {
      if (var0.length != 1) {
         System.err.println("usage: java antlr.build.Tool action");
      } else {
         String var1 = "antlr.build.ANTLR";
         String var2 = var0[0];
         Tool var3 = new Tool();
         var3.perform(var1, var2);
      }
   }

   public void perform(String var1, String var2) {
      if (var1 != null && var2 != null) {
         Class var3 = null;
         Method var4 = null;
         Object var5 = null;

         try {
            var5 = Utils.createInstanceOf(var1);
         } catch (Exception var10) {
            Exception var6 = var10;

            try {
               if (!var1.startsWith("antlr.build.")) {
                  var3 = Utils.loadClass("antlr.build." + var1);
               }

               this.error("no such application " + var1, var6);
            } catch (Exception var9) {
               this.error("no such application " + var1, var9);
            }
         }

         if (var3 != null && var5 != null) {
            try {
               var4 = var3.getMethod(var2, class$antlr$build$Tool == null ? (class$antlr$build$Tool = class$("antlr.build.Tool")) : class$antlr$build$Tool);
               var4.invoke(var5, this);
            } catch (Exception var8) {
               this.error("no such action for application " + var1, var8);
            }

         }
      } else {
         this.error("missing app or action");
      }
   }

   public void system(String var1) {
      Runtime var2 = Runtime.getRuntime();

      try {
         this.log(var1);
         Process var3 = null;
         if (!this.os.startsWith("Windows")) {
            var3 = var2.exec(new String[]{"sh", "-c", var1});
         } else {
            var3 = var2.exec(var1);
         }

         StreamScarfer var4 = new StreamScarfer(var3.getErrorStream(), "stderr", this);
         StreamScarfer var5 = new StreamScarfer(var3.getInputStream(), "stdout", this);
         var4.start();
         var5.start();
         int var6 = var3.waitFor();
      } catch (Exception var7) {
         this.error("cannot exec " + var1, var7);
      }

   }

   public void antlr(String var1) {
      String var2 = null;

      try {
         var2 = (new File(var1)).getParent();
         if (var2 != null) {
            var2 = (new File(var2)).getCanonicalPath();
         }
      } catch (IOException var4) {
         this.error("Invalid grammar file: " + var1);
      }

      if (var2 != null) {
         this.log("java antlr.Tool -o " + var2 + " " + var1);
         antlr.Tool var3 = new antlr.Tool();
         var3.doEverything(new String[]{"-o", var2, var1});
      }

   }

   public void stdout(String var1) {
      System.out.println(var1);
   }

   public void stderr(String var1) {
      System.err.println(var1);
   }

   public void error(String var1) {
      System.err.println("antlr.build.Tool: " + var1);
   }

   public void log(String var1) {
      System.out.println("executing: " + var1);
   }

   public void error(String var1, Exception var2) {
      System.err.println("antlr.build.Tool: " + var1);
      var2.printStackTrace(System.err);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
