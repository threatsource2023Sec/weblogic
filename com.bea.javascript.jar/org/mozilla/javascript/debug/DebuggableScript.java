package org.mozilla.javascript.debug;

import org.mozilla.javascript.Scriptable;

public interface DebuggableScript {
   int[] getLineNumbers();

   Scriptable getScriptable();

   String getSourceName();

   boolean isFunction();

   boolean placeBreakpoint(int var1);

   boolean removeBreakpoint(int var1);
}
