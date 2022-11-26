package org.mozilla.javascript.debug;

public interface DebuggableEngine {
   boolean getBreakNextLine();

   Debugger getDebugger();

   DebugFrame getFrame(int var1);

   int getFrameCount();

   void setBreakNextLine(boolean var1);

   void setDebugger(Debugger var1);
}
