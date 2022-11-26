package org.mozilla.javascript.debug;

import org.mozilla.javascript.Scriptable;

public interface DebugFrame {
   int getLineNumber();

   DebuggableScript getScript();

   String getSourceName();

   Scriptable getVariableObject();
}
