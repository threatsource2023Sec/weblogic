package org.mozilla.javascript.debug;

import org.mozilla.javascript.Context;

public interface Debugger {
   void handleBreakpointHit(Context var1);

   void handleCompilationDone(Context var1, DebuggableScript var2, StringBuffer var3);

   void handleExceptionThrown(Context var1, Object var2);
}
