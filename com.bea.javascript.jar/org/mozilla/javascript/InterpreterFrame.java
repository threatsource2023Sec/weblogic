package org.mozilla.javascript;

import org.mozilla.javascript.debug.DebugFrame;
import org.mozilla.javascript.debug.DebuggableScript;

class InterpreterFrame implements DebugFrame {
   private Scriptable scope;
   private InterpreterData data;
   private Scriptable obj;
   private int lineNumber;

   InterpreterFrame(Scriptable var1, InterpreterData var2, Scriptable var3) {
      this.scope = var1;
      this.data = var2;
      this.lineNumber = -1;
      this.obj = var3;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public DebuggableScript getScript() {
      return this.obj instanceof DebuggableScript ? (DebuggableScript)this.obj : null;
   }

   public String getSourceName() {
      return this.data.itsSourceFile;
   }

   public Scriptable getVariableObject() {
      return this.scope;
   }

   public void setLineNumber(int var1) {
      this.lineNumber = var1;
   }
}
