package org.mozilla.javascript;

import org.mozilla.javascript.debug.DebuggableScript;

public class InterpretedScript extends NativeScript implements DebuggableScript {
   InterpreterData itsData;

   InterpretedScript(Context var1, InterpreterData var2, String[] var3, short var4) {
      this.itsData = var2;
      super.argNames = var3;
      super.argCount = var4;
      super.functionName = "";
      super.nestedFunctions = this.itsData.itsNestedFunctions;
      super.version = (short)var1.getLanguageVersion();
   }

   public Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      var2 = ScriptRuntime.initScript(var1, var2, this, var3, this.itsData.itsFromEvalCode);
      return Interpreter.interpret(var1, var2, var3, var4, this, this.itsData);
   }

   public Object exec(Context var1, Scriptable var2) throws JavaScriptException {
      return this.call(var1, var2, var2, (Object[])null);
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

   public boolean isFunction() {
      return false;
   }

   public boolean placeBreakpoint(int var1) {
      return this.itsData.placeBreakpoint(var1);
   }

   public boolean removeBreakpoint(int var1) {
      return this.itsData.removeBreakpoint(var1);
   }
}
