package org.mozilla.javascript.tools.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Vector;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.WrappedException;
import org.mozilla.javascript.tools.ToolErrorReporter;

public class Main {
   protected static ToolErrorReporter errorReporter;
   protected static Global global;
   protected static int exitCode = 0;
   private static final int EXITCODE_RUNTIME_ERROR = 3;
   private static final int EXITCODE_FILE_NOT_FOUND = 4;
   static boolean processStdin = true;
   static Vector fileList = new Vector(5);

   public static Object evaluateReader(Context var0, Scriptable var1, Reader var2, String var3, int var4) {
      Object var5 = Context.getUndefinedValue();

      try {
         var5 = var0.evaluateReader(var1, var2, var3, var4, (Object)null);
      } catch (WrappedException var19) {
         global.getErr().println(var19.getWrappedException().toString());
         var19.printStackTrace();
      } catch (EcmaError var20) {
         String var25 = ToolErrorReporter.getMessage("msg.uncaughtJSException", var20.toString());
         exitCode = 3;
         if (var20.getSourceName() != null) {
            Context.reportError(var25, var20.getSourceName(), var20.getLineNumber(), var20.getLineSource(), var20.getColumnNumber());
         } else {
            Context.reportError(var25);
         }
      } catch (EvaluatorException var21) {
         exitCode = 3;
      } catch (JavaScriptException var22) {
         Object var9 = var22.getValue();
         if (var9 instanceof ThreadDeath) {
            throw (ThreadDeath)var9;
         }

         exitCode = 3;
         Context.reportError(ToolErrorReporter.getMessage("msg.uncaughtJSException", var22.getMessage()));
      } catch (IOException var23) {
         global.getErr().println(var23.toString());
      } finally {
         try {
            var2.close();
         } catch (IOException var18) {
            global.getErr().println(var18.toString());
         }

      }

      return var5;
   }

   public static int exec(String[] var0) {
      Context var1 = Context.enter();
      global = new Global(var1);
      errorReporter = new ToolErrorReporter(false, global.getErr());
      var1.setErrorReporter(errorReporter);
      var0 = processOptions(var1, var0);
      byte var2 = 0;
      if (fileList.size() == 0 && var0.length > 0) {
         var2 = 1;
         fileList.addElement(var0[0]);
      }

      if (processStdin) {
         fileList.addElement((Object)null);
      }

      Object var3 = var0;
      if (var0.length > 0) {
         int var4 = var0.length - var2;
         var3 = new Object[var4];
         System.arraycopy(var0, var2, var3, 0, var4);
      }

      Scriptable var6 = var1.newArray(global, (Object[])var3);
      global.defineProperty("arguments", (Object)var6, 2);

      for(int var5 = 0; var5 < fileList.size(); ++var5) {
         processSource(var1, (String)fileList.get(var5));
      }

      Context.exit();
      return exitCode;
   }

   public static PrintStream getErr() {
      return Global.getInstance(global).getErr();
   }

   public static InputStream getIn() {
      return Global.getInstance(global).getIn();
   }

   public static PrintStream getOut() {
      return Global.getInstance(global).getOut();
   }

   public static ScriptableObject getScope() {
      return global;
   }

   public static void main(String[] var0) {
      int var1 = exec(var0);
      if (var1 != 0) {
         System.exit(var1);
      }

   }

   private static void p(String var0) {
      global.getOut().println(var0);
   }

   public static void processFile(Context var0, Scriptable var1, String var2) {
      Object var3 = null;

      try {
         var3 = new PushbackReader(new FileReader(var2));
         int var4 = ((Reader)var3).read();
         if (var4 != 35) {
            ((Reader)var3).close();
            var3 = new FileReader(var2);
         } else {
            while((var4 = ((Reader)var3).read()) != -1 && var4 != 10 && var4 != 13) {
            }

            ((PushbackReader)var3).unread(var4);
         }

         var2 = (new File(var2)).getCanonicalPath();
      } catch (FileNotFoundException var5) {
         Context.reportError(ToolErrorReporter.getMessage("msg.couldnt.open", var2));
         exitCode = 4;
         return;
      } catch (IOException var6) {
         global.getErr().println(var6.toString());
      }

      evaluateReader(var0, var1, (Reader)var3, var2, 1);
   }

   public static String[] processOptions(Context var0, String[] var1) {
      var0.setTargetPackage("");

      for(int var2 = 0; var2 < var1.length; ++var2) {
         String var3 = var1[var2];
         if (!var3.startsWith("-")) {
            processStdin = false;
            String[] var7 = new String[var1.length - var2];
            System.arraycopy(var1, var2, var7, 0, var1.length - var2);
            return var7;
         }

         double var4;
         if (var3.equals("-version")) {
            ++var2;
            if (var2 == var1.length) {
               usage(var3);
            }

            var4 = Context.toNumber(var1[var2]);
            if (var4 != var4) {
               usage(var3);
            }

            var0.setLanguageVersion((int)var4);
         } else if (!var3.equals("-opt") && !var3.equals("-O")) {
            if (var3.equals("-e")) {
               processStdin = false;
               ++var2;
               if (var2 == var1.length) {
                  usage(var3);
               }

               StringReader var6 = new StringReader(var1[var2]);
               evaluateReader(var0, global, var6, "<command>", 1);
            } else if (var3.equals("-w")) {
               errorReporter.setIsReportingWarnings(true);
            } else if (var3.equals("-f")) {
               processStdin = false;
               ++var2;
               if (var2 == var1.length) {
                  usage(var3);
               }

               fileList.addElement(var1[var2].equals("-") ? null : var1[var2]);
            } else {
               usage(var3);
            }
         } else {
            ++var2;
            if (var2 == var1.length) {
               usage(var3);
            }

            var4 = Context.toNumber(var1[var2]);
            if (var4 != var4) {
               usage(var3);
            }

            var0.setOptimizationLevel((int)var4);
         }
      }

      return new String[0];
   }

   public static void processSource(Context var0, String var1) {
      if (var1 != null && !var1.equals("-")) {
         processFile(var0, global, var1);
      } else {
         var0.setOptimizationLevel(-1);
         BufferedReader var2 = new BufferedReader(new InputStreamReader(global.getIn()));
         int var3 = 1;
         boolean var4 = false;

         while(!var4) {
            if (var1 == null) {
               global.getErr().print("js> ");
            }

            global.getErr().flush();
            String var6 = "";

            do {
               String var7;
               try {
                  var7 = var2.readLine();
               } catch (IOException var11) {
                  global.getErr().println(var11.toString());
                  break;
               }

               if (var7 == null) {
                  var4 = true;
                  break;
               }

               var6 = var6 + var7 + "\n";
               ++var3;
            } while(!var0.stringIsCompilableUnit(var6));

            StringReader var13 = new StringReader(var6);
            Object var8 = evaluateReader(var0, global, var13, "<stdin>", var3);
            if (var8 != Context.getUndefinedValue()) {
               try {
                  global.getErr().println(Context.toString(var8));
               } catch (EcmaError var12) {
                  String var10 = ToolErrorReporter.getMessage("msg.uncaughtJSException", var12.toString());
                  exitCode = 3;
                  if (var12.getSourceName() != null) {
                     Context.reportError(var10, var12.getSourceName(), var12.getLineNumber(), var12.getLineSource(), var12.getColumnNumber());
                  } else {
                     Context.reportError(var10);
                  }
               }
            }

            NativeArray var9 = global.history;
            var9.put((int)var9.jsGet_length(), var9, var6);
         }

         global.getErr().println();
      }

      System.gc();
   }

   public static void setErr(PrintStream var0) {
      Global.getInstance(global).setErr(var0);
   }

   public static void setIn(InputStream var0) {
      Global.getInstance(global).setIn(var0);
   }

   public static void setOut(PrintStream var0) {
      Global.getInstance(global).setOut(var0);
   }

   public static void usage(String var0) {
      p(ToolErrorReporter.getMessage("msg.shell.usage", var0));
      System.exit(1);
   }
}
