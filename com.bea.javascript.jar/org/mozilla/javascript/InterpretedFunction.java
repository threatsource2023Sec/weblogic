package org.mozilla.javascript;

import org.mozilla.javascript.debug.DebuggableScript;

class InterpretedFunction extends NativeFunction implements DebuggableScript {
   InterpreterData itsData;
   Scriptable itsClosure;

   InterpretedFunction(Context var1, InterpreterData var2, String[] var3, short var4) {
      this.itsData = var2;
      super.argNames = var3;
      super.argCount = var4;
      this.init(var1);
   }

   InterpretedFunction(InterpretedFunction var1, Scriptable var2, Context var3) {
      this.itsData = var1.itsData;
      super.argNames = var1.argNames;
      super.argCount = var1.argCount;
      this.itsClosure = var2;
      this.init(var3);
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      if (this.itsClosure != null) {
         var2 = this.itsClosure;
      } else if (!this.itsData.itsUseDynamicScope) {
         var2 = this.getParentScope();
      }

      if (this.itsData.itsCheckThis) {
         var3 = ScriptRuntime.getThis(var3);
      }

      if (this.itsData.itsNeedsActivation) {
         var2 = ScriptRuntime.initVarObj(var1, var2, this, var3, var4);
      }

      Object var5;
      try {
         var5 = Interpreter.interpret(var1, var2, var3, var4, this, this.itsData);
      } finally {
         if (this.itsData.itsNeedsActivation) {
            ScriptRuntime.popActivation(var1);
         }

      }

      return var5;
   }

   public int[] getLineNumbers() {
      return this.itsData.itsLineNumberTable.getKeys();
   }

   public Scriptable getScriptable() {
      return this;
   }

   public String getSourceName() {
      return this.itsData.itsSourceFile;
   }

   void init(Context var1) {
      super.functionName = this.itsData.itsName;
      super.source = this.itsData.itsSource;
      super.nestedFunctions = this.itsData.itsNestedFunctions;
      if (var1 != null) {
         super.version = (short)var1.getLanguageVersion();
      }

   }

   public boolean isFunction() {
      return true;
   }

   public boolean placeBreakpoint(int var1) {
      return this.itsData.placeBreakpoint(var1);
   }

   public boolean removeBreakpoint(int var1) {
      return this.itsData.removeBreakpoint(var1);
   }
}
