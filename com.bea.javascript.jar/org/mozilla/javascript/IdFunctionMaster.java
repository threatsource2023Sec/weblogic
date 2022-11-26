package org.mozilla.javascript;

public interface IdFunctionMaster {
   Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException;

   int methodArity(int var1);
}
