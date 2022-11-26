package org.mozilla.javascript.tools.jsc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.ToolErrorReporter;

public class Main {
   private static boolean hasOutOption = false;
   private static ToolErrorReporter reporter;

   static String getClassName(String var0) {
      char[] var1 = new char[var0.length() + 1];
      int var3 = 0;
      if (!Character.isJavaIdentifierStart(var0.charAt(0))) {
         var1[var3++] = '_';
      }

      for(int var4 = 0; var4 < var0.length(); ++var3) {
         char var2 = var0.charAt(var4);
         if (Character.isJavaIdentifierPart(var2)) {
            var1[var3] = var2;
         } else {
            var1[var3] = '_';
         }

         ++var4;
      }

      return (new String(var1)).trim();
   }

   public static void main(String[] var0) {
      Context var1 = Context.enter();
      reporter = new ToolErrorReporter(true);
      var1.setErrorReporter(reporter);
      var0 = processOptions(var1, var0);
      if (!reporter.hasReportedError()) {
         processSource(var1, var0);
      }

      Context.exit();
   }

   private static void p(String var0) {
      System.out.println(var0);
   }

   public static String[] processOptions(Context var0, String[] var1) {
      var0.setTargetPackage("");
      var0.setGeneratingDebug(false);

      for(int var2 = 0; var2 < var1.length; ++var2) {
         String var3 = var1[var2];
         int var11;
         if (!var3.startsWith("-")) {
            String[] var12 = new String[var1.length - var2];

            for(var11 = var2; var11 < var1.length; ++var11) {
               var12[var11 - var2] = var1[var11];
            }

            return var12;
         }

         try {
            int var10;
            if (var3.equals("-version")) {
               ++var2;
               if (var2 < var1.length) {
                  var10 = Integer.parseInt(var1[var2]);
                  var0.setLanguageVersion(var10);
                  continue;
               }
            }

            if (var3.equals("-opt") || var3.equals("-O")) {
               ++var2;
               if (var2 < var1.length) {
                  var10 = Integer.parseInt(var1[var2]);
                  var0.setOptimizationLevel(var10);
                  continue;
               }
            }
         } catch (NumberFormatException var9) {
            Context.reportError(ToolErrorReporter.getMessage("msg.jsc.usage", var1[var2]));
            continue;
         }

         if (var3.equals("-nosource")) {
            var0.setGeneratingSource(false);
         } else if (!var3.equals("-debug") && !var3.equals("-g")) {
            String var4;
            if (var3.equals("-o")) {
               ++var2;
               if (var2 < var1.length) {
                  var4 = var1[var2];
                  if (!Character.isJavaIdentifierStart(var4.charAt(0))) {
                     Context.reportError(ToolErrorReporter.getMessage("msg.invalid.classfile.name", var4));
                     continue;
                  }

                  for(var11 = 1; var11 < var4.length(); ++var11) {
                     if (!Character.isJavaIdentifierPart(var4.charAt(var11)) && var4.charAt(var11) != '.' || var4.charAt(var11) == '.' && (!var4.endsWith(".class") || var11 != var4.length() - 6)) {
                        Context.reportError(ToolErrorReporter.getMessage("msg.invalid.classfile.name", var4));
                        break;
                     }
                  }

                  var0.setTargetClassFileName(var4);
                  hasOutOption = true;
                  continue;
               }
            }

            if (var3.equals("-package")) {
               ++var2;
               if (var2 < var1.length) {
                  var4 = var1[var2];

                  for(var11 = 0; var11 < var4.length(); ++var11) {
                     if (!Character.isJavaIdentifierStart(var4.charAt(var11))) {
                        Context.reportError(ToolErrorReporter.getMessage("msg.package.name", var4));
                     } else {
                        ++var11;

                        for(int var6 = var11; var6 < var4.length(); ++var11) {
                           if ((var4.charAt(var6) != '.' || var4.charAt(var6 - 1) == '.' || var6 == var4.length() - 1) && !Character.isJavaIdentifierPart(var4.charAt(var6))) {
                              Context.reportError(ToolErrorReporter.getMessage("msg.package.name", var4));
                           }

                           ++var6;
                        }
                     }
                  }

                  var0.setTargetPackage(var4);
                  continue;
               }
            }

            if (var3.equals("-extends")) {
               ++var2;
               if (var2 < var1.length) {
                  var4 = var1[var2];

                  try {
                     var0.setTargetExtends(Class.forName(var4));
                     continue;
                  } catch (ClassNotFoundException var8) {
                     throw new Error(var8.toString());
                  }
               }
            }

            if (var3.equals("-implements")) {
               ++var2;
               if (var2 < var1.length) {
                  var4 = var1[var2];

                  try {
                     Class[] var5 = new Class[]{Class.forName(var4)};
                     var0.setTargetImplements(var5);
                     continue;
                  } catch (ClassNotFoundException var7) {
                     throw new Error(var7.toString());
                  }
               }
            }

            usage(var3);
         } else {
            var0.setGeneratingDebug(true);
         }
      }

      p(ToolErrorReporter.getMessage("msg.no.file"));
      System.exit(1);
      return null;
   }

   public static void processSource(Context var0, String[] var1) {
      if (hasOutOption && var1.length > 1) {
         Context.reportError(ToolErrorReporter.getMessage("msg.multiple.js.to.file", var0.getTargetClassFileName()));
      }

      for(int var2 = 0; var2 < var1.length; ++var2) {
         String var3 = var1[var2];
         File var4 = new File(var3);
         if (!var4.exists()) {
            Context.reportError(ToolErrorReporter.getMessage("msg.jsfile.not.found", var3));
            return;
         }

         if (!var3.endsWith(".js")) {
            Context.reportError(ToolErrorReporter.getMessage("msg.extension.not.js", var3));
            return;
         }

         if (!hasOutOption) {
            String var5 = var4.getName();
            String var6 = var5.substring(0, var5.length() - 3);
            String var7 = getClassName(var6) + ".class";
            String var8 = var4.getParent() == null ? var7 : var4.getParent() + File.separator + var7;
            var0.setTargetClassFileName(var8);
         }

         if (var0.getTargetClassFileName() == null) {
            Context.reportError(ToolErrorReporter.getMessage("msg.no-opt"));
         }

         try {
            FileReader var11 = new FileReader(var3);
            var0.compileReader((Scriptable)null, var11, var3, 1, (Object)null);
         } catch (FileNotFoundException var9) {
            Context.reportError(ToolErrorReporter.getMessage("msg.couldnt.open", var3));
            return;
         } catch (IOException var10) {
            Context.reportError(var10.toString());
         }
      }

   }

   public static void usage(String var0) {
      p(ToolErrorReporter.getMessage("msg.jsc.usage", var0));
      System.exit(1);
   }
}
