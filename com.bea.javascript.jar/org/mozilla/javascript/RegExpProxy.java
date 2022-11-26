package org.mozilla.javascript;

public interface RegExpProxy {
   int find_split(Scriptable var1, String var2, String var3, Object var4, int[] var5, int[] var6, boolean[] var7, String[][] var8);

   boolean isRegExp(Object var1);

   Object match(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException;

   Object newRegExp(Context var1, Scriptable var2, String var3, String var4, boolean var5);

   Object replace(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException;

   Object search(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException;
}
