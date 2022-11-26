package antlr.build;

import java.io.File;
import java.io.FilenameFilter;

public class ANTLR {
   public static String compiler = "javac";
   public static String jarName = "antlr.jar";
   public static String root = ".";
   public static String[] srcdir = new String[]{"antlr", "antlr/actions/cpp", "antlr/actions/java", "antlr/actions/csharp", "antlr/collections", "antlr/collections/impl", "antlr/debug", "antlr/ASdebug", "antlr/debug/misc", "antlr/preprocessor"};

   public ANTLR() {
      compiler = System.getProperty("antlr.build.compiler", compiler);
      root = System.getProperty("antlr.build.root", root);
   }

   public String getName() {
      return "ANTLR";
   }

   public void build(Tool var1) {
      if (this.rootIsValidANTLRDir(var1)) {
         var1.antlr(root + "/antlr/antlr.g");
         var1.antlr(root + "/antlr/tokdef.g");
         var1.antlr(root + "/antlr/preprocessor/preproc.g");
         var1.antlr(root + "/antlr/actions/java/action.g");
         var1.antlr(root + "/antlr/actions/cpp/action.g");
         var1.antlr(root + "/antlr/actions/csharp/action.g");

         for(int var2 = 0; var2 < srcdir.length; ++var2) {
            String var3 = compiler + " -d " + root + " " + root + "/" + srcdir[var2] + "/*.java";
            var1.system(var3);
         }

      }
   }

   public void jar(Tool var1) {
      if (this.rootIsValidANTLRDir(var1)) {
         StringBuffer var2 = new StringBuffer(2000);
         var2.append("jar cvf " + root + "/" + jarName);

         for(int var3 = 0; var3 < srcdir.length; ++var3) {
            var2.append(" " + root + "/" + srcdir[var3] + "/*.class");
         }

         var1.system(var2.toString());
      }
   }

   protected boolean rootIsValidANTLRDir(Tool var1) {
      if (root == null) {
         return false;
      } else {
         File var2 = new File(root);
         if (!var2.exists()) {
            var1.error("Property antlr.build.root==" + root + " does not exist");
            return false;
         } else if (!var2.isDirectory()) {
            var1.error("Property antlr.build.root==" + root + " is not a directory");
            return false;
         } else {
            String[] var3 = var2.list(new FilenameFilter() {
               public boolean accept(File var1, String var2) {
                  return var1.isDirectory() && var2.equals("antlr");
               }
            });
            if (var3 != null && var3.length != 0) {
               File var4 = new File(root + "/antlr");
               String[] var5 = var4.list();
               if (var5 != null && var5.length != 0) {
                  return true;
               } else {
                  var1.error("Property antlr.build.root==" + root + " does not appear to be a valid ANTLR project root (no .java files in antlr subdir");
                  return false;
               }
            } else {
               var1.error("Property antlr.build.root==" + root + " does not appear to be a valid ANTLR project root (no antlr subdir)");
               return false;
            }
         }
      }
   }
}
