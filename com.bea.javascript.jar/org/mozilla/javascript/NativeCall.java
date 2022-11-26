package org.mozilla.javascript;

public final class NativeCall extends IdScriptable {
   private static final int Id_constructor = 1;
   private static final int MAX_PROTOTYPE_ID = 1;
   NativeCall caller;
   NativeFunction funObj;
   Scriptable thisObj;
   Object[] originalArgs;
   public int debugPC;
   private boolean prototypeFlag;

   private NativeCall() {
   }

   NativeCall(Context var1, Scriptable var2, NativeFunction var3, Scriptable var4, Object[] var5) {
      this.funObj = var3;
      this.thisObj = var4;
      this.setParentScope(var2);
      this.caller = var1.currentActivation;
      var1.currentActivation = this;
      this.originalArgs = var5 == null ? ScriptRuntime.emptyArgs : var5;
      String[] var6 = var3.argNames;
      if (var6 != null) {
         for(int var7 = 0; var7 < var3.argCount; ++var7) {
            Object var8 = var7 < var5.length ? var5[var7] : Undefined.instance;
            super.put(var6[var7], this, var8);
         }
      }

      super.put("arguments", this, new Arguments(this));
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      return this.prototypeFlag && var1 == 1 ? jsConstructor(var3, var6, var2, var5 == null) : super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   NativeCall getActivation(Function var1) {
      NativeCall var2 = this;

      while(var2.funObj != var1) {
         var2 = var2.caller;
         if (var2 == null) {
            return null;
         }
      }

      return var2;
   }

   public NativeCall getCaller() {
      return this.caller;
   }

   public String getClassName() {
      return "Call";
   }

   public Function getFunctionObject() {
      return this.funObj;
   }

   protected String getIdName(int var1) {
      return this.prototypeFlag && var1 == 1 ? "constructor" : null;
   }

   public Object[] getOriginalArguments() {
      return this.originalArgs;
   }

   public Scriptable getThisObj() {
      return this.thisObj;
   }

   static void init(Context var0, Scriptable var1, boolean var2) {
      NativeCall var3 = new NativeCall();
      var3.prototypeFlag = true;
      var3.addAsPrototype(1, var0, var1, var2);
   }

   private static Object jsConstructor(Context var0, Object[] var1, Function var2, boolean var3) {
      if (!var3) {
         throw Context.reportRuntimeError1("msg.only.from.new", "Call");
      } else {
         ScriptRuntime.checkDeprecated(var0, "Call");
         NativeCall var4 = new NativeCall();
         var4.setPrototype(ScriptableObject.getObjectPrototype(var2));
         return var4;
      }
   }

   protected int mapNameToId(String var1) {
      if (!this.prototypeFlag) {
         return 0;
      } else {
         return var1.equals("constructor") ? 1 : 0;
      }
   }

   public int methodArity(int var1) {
      return this.prototypeFlag && var1 == 1 ? 1 : super.methodArity(var1);
   }
}
