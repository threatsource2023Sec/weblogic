package org.mozilla.javascript.regexp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;

abstract class GlobData {
   static final int GLOB_MATCH = 1;
   static final int GLOB_REPLACE = 2;
   static final int GLOB_SEARCH = 3;
   byte mode;
   int optarg;
   boolean global;
   String str;
   NativeRegExp regexp;
   Scriptable parent;

   abstract void doGlobal(Context var1, Scriptable var2, int var3, RegExpImpl var4) throws JavaScriptException;
}
