package org.mozilla.javascript.tools.shell;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import org.mozilla.javascript.ClassDefinitionException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.PropertyException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Synchronizer;
import org.mozilla.javascript.tools.ToolErrorReporter;

public class Global extends ImporterTopLevel {
   static final String privateName = "org.mozilla.javascript.tools.shell.Global private";
   NativeArray history;
   public InputStream inStream;
   public PrintStream outStream;
   public PrintStream errStream;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$tools$shell$Global;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Script;

   public Global(Context var1) {
      super(var1);
      String[] var2 = new String[]{"print", "quit", "version", "load", "help", "loadClass", "defineClass", "spawn", "sync"};

      try {
         this.defineFunctionProperties(var2, class$org$mozilla$javascript$tools$shell$Global != null ? class$org$mozilla$javascript$tools$shell$Global : (class$org$mozilla$javascript$tools$shell$Global = class$("org.mozilla.javascript.tools.shell.Global")), 2);
      } catch (PropertyException var4) {
         throw new Error();
      }

      this.defineProperty("org.mozilla.javascript.tools.shell.Global private", (Object)this, 2);
      Environment.defineClass(this);
      Environment var3 = new Environment(this);
      this.defineProperty("environment", (Object)var3, 2);
      this.history = (NativeArray)var1.newArray(this, 0);
      this.defineProperty("history", (Object)this.history, 2);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public static void defineClass(Context var0, Scriptable var1, Object[] var2, Function var3) throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassDefinitionException, PropertyException {
      Class var4 = getClass(var2);
      ScriptableObject.defineClass(var1, var4);
   }

   private static Class getClass(Object[] var0) throws IllegalAccessException, InstantiationException, InvocationTargetException {
      if (var0.length == 0) {
         throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.expected.string.arg"));
      } else {
         String var1 = Context.toString(var0[0]);

         try {
            return Class.forName(var1);
         } catch (ClassNotFoundException var2) {
            throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.class.not.found", var1));
         }
      }
   }

   public PrintStream getErr() {
      return this.errStream == null ? System.err : this.errStream;
   }

   public InputStream getIn() {
      return this.inStream == null ? System.in : this.inStream;
   }

   public static Global getInstance(Scriptable var0) {
      Object var1 = ScriptableObject.getProperty(var0, "org.mozilla.javascript.tools.shell.Global private");
      return var1 instanceof Global ? (Global)var1 : null;
   }

   public PrintStream getOut() {
      return this.outStream == null ? System.out : this.outStream;
   }

   public static void help(Context var0, Scriptable var1, Object[] var2, Function var3) {
      PrintStream var4 = getInstance(var1).getOut();
      var4.println(ToolErrorReporter.getMessage("msg.help"));
   }

   public static void load(Context var0, Scriptable var1, Object[] var2, Function var3) {
      for(int var4 = 0; var4 < var2.length; ++var4) {
         Main.processFile(var0, var1, Context.toString(var2[var4]));
      }

   }

   public static void loadClass(Context var0, Scriptable var1, Object[] var2, Function var3) throws IllegalAccessException, InstantiationException, InvocationTargetException, JavaScriptException {
      Class var4 = getClass(var2);
      if (!(class$org$mozilla$javascript$Script != null ? class$org$mozilla$javascript$Script : (class$org$mozilla$javascript$Script = class$("org.mozilla.javascript.Script"))).isAssignableFrom(var4)) {
         throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.must.implement.Script"));
      } else {
         Script var5 = (Script)var4.newInstance();
         var5.exec(var0, var1);
      }
   }

   public static Object print(Context var0, Scriptable var1, Object[] var2, Function var3) {
      PrintStream var4 = getInstance(var1).getOut();

      for(int var5 = 0; var5 < var2.length; ++var5) {
         if (var5 > 0) {
            var4.print(" ");
         }

         String var6 = Context.toString(var2[var5]);
         var4.print(var6);
      }

      var4.println();
      return Context.getUndefinedValue();
   }

   public static void quit(Context var0, Scriptable var1, Object[] var2, Function var3) {
      System.exit(var2.length > 0 ? (int)Context.toNumber(var2[0]) : 0);
   }

   public void setErr(PrintStream var1) {
      this.errStream = var1;
   }

   public void setIn(InputStream var1) {
      this.inStream = var1;
   }

   public void setOut(PrintStream var1) {
      this.outStream = var1;
   }

   public static Object spawn(Context var0, Scriptable var1, Object[] var2, Function var3) {
      Scriptable var4 = var3.getParentScope();
      Runner var5;
      if (var2.length != 0 && var2[0] instanceof Function) {
         Object[] var6 = null;
         if (var2.length > 1 && var2[1] instanceof Scriptable) {
            var6 = var0.getElements((Scriptable)var2[1]);
         }

         var5 = new Runner(var4, (Function)var2[0], var6);
      } else {
         if (var2.length == 0 || !(var2[0] instanceof Script)) {
            throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.spawn.args"));
         }

         var5 = new Runner(var4, (Script)var2[0]);
      }

      Thread var7 = new Thread(var5);
      var7.start();
      return var7;
   }

   public static Object sync(Context var0, Scriptable var1, Object[] var2, Function var3) {
      if (var2.length == 1 && var2[0] instanceof Function) {
         return new Synchronizer((Function)var2[0]);
      } else {
         throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.spawn.args"));
      }
   }

   public static double version(Context var0, Scriptable var1, Object[] var2, Function var3) {
      double var4 = (double)var0.getLanguageVersion();
      if (var2.length > 0) {
         double var6 = Context.toNumber(var2[0]);
         var0.setLanguageVersion((int)var6);
      }

      return var4;
   }
}
