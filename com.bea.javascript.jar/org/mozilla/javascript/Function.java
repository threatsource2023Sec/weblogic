package org.mozilla.javascript;

public interface Function extends Scriptable {
   Object call(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException;

   Scriptable construct(Context var1, Scriptable var2, Object[] var3) throws JavaScriptException;
}
