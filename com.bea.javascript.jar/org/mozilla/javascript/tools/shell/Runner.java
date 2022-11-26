package org.mozilla.javascript.tools.shell;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.ToolErrorReporter;

class Runner implements Runnable {
   private Scriptable scope;
   private Function f;
   private Script s;
   private Object[] args;

   Runner(Scriptable var1, Function var2, Object[] var3) {
      this.scope = var1;
      this.f = var2;
      this.args = var3;
   }

   Runner(Scriptable var1, Script var2) {
      this.scope = var1;
      this.s = var2;
   }

   public void run() {
      Context var1 = Context.enter();

      try {
         if (this.f != null) {
            this.f.call(var1, this.scope, this.scope, this.args);
         } else {
            this.s.exec(var1, this.scope);
         }
      } catch (JavaScriptException var3) {
         Context.reportError(ToolErrorReporter.getMessage("msg.uncaughtJSException", var3.getMessage()));
      }

      Context.exit();
   }
}
