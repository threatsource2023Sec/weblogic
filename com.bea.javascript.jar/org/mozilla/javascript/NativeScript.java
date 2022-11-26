package org.mozilla.javascript;

import java.io.IOException;
import java.io.StringReader;

public class NativeScript extends NativeFunction implements Script {
   private static final int Id_constructor = 1;
   private static final int Id_toString = 2;
   private static final int Id_compile = 3;
   private static final int Id_exec = 4;
   private static final int MAX_PROTOTYPE_ID = 4;
   private Script script;
   private int prototypeIdShift;

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      return this.exec(var1, var2);
   }

   public static Script compile(Scriptable var0, String var1) {
      Context var2 = Context.getContext();
      StringReader var3 = new StringReader(var1);

      try {
         int[] var4 = new int[1];
         String var5 = Context.getSourcePositionFromStack(var4);
         if (var5 == null) {
            var5 = "<Script object>";
            var4[0] = 1;
         }

         Object var6 = var2.getSecurityDomainForStackDepth(5);
         return var2.compileReader(var0, var3, var5, var4[0], var6);
      } catch (IOException var7) {
         throw new RuntimeException("Unexpected IOException");
      }
   }

   public Scriptable construct(Context var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      throw Context.reportRuntimeError0("msg.script.is.not.constructor");
   }

   public Object exec(Context var1, Scriptable var2) throws JavaScriptException {
      return this.script == null ? Undefined.instance : this.script.exec(var1, var2);
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeIdShift != 0) {
         switch (var1 - this.prototypeIdShift + 1) {
            case 1:
               return jsConstructor(var3, var4, var6);
            case 2:
               return this.realThis(var5, var2, true).jsFunction_toString(var3, var6);
            case 3:
               return this.realThis(var5, var2, false).jsFunction_compile(ScriptRuntime.toString(var6, 0));
            case 4:
               return this.realThis(var5, var2, true).jsFunction_exec();
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   public String getClassName() {
      return "Script";
   }

   public String getFunctionName() {
      return "";
   }

   protected String getIdName(int var1) {
      if (this.prototypeIdShift != 0) {
         switch (var1 - this.prototypeIdShift + 1) {
            case 1:
               return "constructor";
            case 2:
               return "toString";
            case 3:
               return "compile";
            case 4:
               return "exec";
         }
      }

      return super.getIdName(var1);
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeScript var3 = new NativeScript();
      var3.scopeInit(var0, var1, var2);
   }

   public void initScript(Scriptable var1) {
   }

   private static Object jsConstructor(Context var0, Scriptable var1, Object[] var2) {
      String var3 = var2.length == 0 ? "" : ScriptRuntime.toString(var2[0]);
      return compile(var1, var3);
   }

   private Scriptable jsFunction_compile(String var1) {
      this.script = compile((Scriptable)null, var1);
      return this;
   }

   private Object jsFunction_exec() throws JavaScriptException {
      throw Context.reportRuntimeError1("msg.cant.call.indirect", "exec");
   }

   private Object jsFunction_toString(Context var1, Object[] var2) {
      Object var3 = this.script;
      if (var3 == null) {
         var3 = this;
      }

      Scriptable var4 = ScriptableObject.getTopLevelScope(this);
      return var1.decompileScript((Script)var3, var4, 0);
   }

   protected int mapNameToId(String var1) {
      if (this.prototypeIdShift != 0) {
         int var2 = toPrototypeId(var1);
         if (var2 != 0) {
            return var2 + this.prototypeIdShift - 1;
         }
      }

      return super.mapNameToId(var1);
   }

   public int methodArity(int var1) {
      if (this.prototypeIdShift != 0) {
         switch (var1 - this.prototypeIdShift + 1) {
            case 1:
               return 1;
            case 2:
               return 0;
            case 3:
               return 1;
            case 4:
               return 0;
         }
      }

      return super.methodArity(var1);
   }

   private NativeScript realThis(Scriptable var1, IdFunction var2, boolean var3) {
      while(!(var1 instanceof NativeScript)) {
         var1 = this.nextInstanceCheck(var1, var2, var3);
      }

      return (NativeScript)var1;
   }

   private void scopeInit(Context var1, Scriptable var2, boolean var3) {
      this.prototypeIdShift = super.maxInstanceId() + 1;
      this.addAsPrototype(4 + this.prototypeIdShift - 1, var1, var2, var3);
   }

   private static int toPrototypeId(String var0) {
      byte var1 = 0;
      String var2 = null;
      switch (var0.length()) {
         case 4:
            var2 = "exec";
            var1 = 4;
         case 5:
         case 6:
         case 9:
         case 10:
         default:
            break;
         case 7:
            var2 = "compile";
            var1 = 3;
            break;
         case 8:
            var2 = "toString";
            var1 = 2;
            break;
         case 11:
            var2 = "constructor";
            var1 = 1;
      }

      if (var2 != null && var2 != var0 && !var2.equals(var0)) {
         var1 = 0;
      }

      return var1;
   }
}
