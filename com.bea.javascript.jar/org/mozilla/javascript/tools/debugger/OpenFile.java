package org.mozilla.javascript.tools.debugger;

import java.io.FileReader;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.debug.DebuggableEngine;

class OpenFile implements Runnable {
   Scriptable scope;
   String fileName;
   Main db;

   OpenFile(Main var1, Scriptable var2, String var3) {
      this.scope = var2;
      this.fileName = var3;
      this.db = var1;
   }

   public void run() {
      Context var1 = Context.enter();
      DebuggableEngine var2 = var1.getDebuggableEngine();
      var2.setBreakNextLine(true);

      try {
         var1.compileReader(this.scope, new FileReader(this.fileName), this.fileName, 1, (Object)null);
      } catch (Exception var10) {
         String var6 = var10.getMessage();
         if (var10 instanceof EcmaError) {
            EcmaError var7 = (EcmaError)var10;
            var6 = var7.getSourceName() + ", line " + var7.getLineNumber() + ": " + var6;
         }

         MessageDialogWrapper.showMessageDialog(this.db, var6, "Error Compiling File", 0);
      } finally {
         Context.exit();
      }

   }
}
