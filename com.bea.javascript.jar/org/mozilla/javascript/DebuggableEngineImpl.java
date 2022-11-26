package org.mozilla.javascript;

import org.mozilla.javascript.debug.DebugFrame;
import org.mozilla.javascript.debug.DebuggableEngine;
import org.mozilla.javascript.debug.Debugger;

public class DebuggableEngineImpl implements DebuggableEngine {
   private Context cx;

   public DebuggableEngineImpl(Context var1) {
      this.cx = var1;
   }

   public boolean getBreakNextLine() {
      return this.cx.inLineStepMode;
   }

   public Debugger getDebugger() {
      return this.cx.debugger;
   }

   public DebugFrame getFrame(int var1) {
      return (DebugFrame)this.cx.frameStack.elementAt(this.cx.frameStack.size() - var1 - 1);
   }

   public int getFrameCount() {
      return this.cx.frameStack == null ? 0 : this.cx.frameStack.size();
   }

   public void setBreakNextLine(boolean var1) {
      this.cx.inLineStepMode = var1;
   }

   public void setDebugger(Debugger var1) {
      this.cx.debugger = var1;
   }
}
