package org.mozilla.javascript.tools.debugger;

import java.awt.Dimension;
import java.io.File;
import java.util.Vector;
import javax.swing.JComboBox;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.debug.DebugFrame;
import org.mozilla.javascript.debug.DebuggableEngine;

class UpdateContext implements Runnable {
   Main db;
   Context cx;
   DebuggableEngine engine;

   UpdateContext(Main var1, Context var2) {
      this.db = var1;
      this.cx = var2;
      this.engine = var2.getDebuggableEngine();
   }

   public void run() {
      this.db.context.enable();
      JComboBox var1 = this.db.context.context;
      Vector var2 = this.db.context.toolTips;
      this.db.context.disableUpdate();
      int var3 = this.engine.getFrameCount();
      var1.removeAllItems();
      var2.clear();

      for(int var4 = 0; var4 < var3; ++var4) {
         DebugFrame var5 = this.engine.getFrame(var4);
         String var6 = var5.getSourceName();
         String var8 = var6;
         int var9 = var5.getLineNumber();
         if (var6 == null) {
            if (var9 == -1) {
               var6 = "<eval>";
               var8 = "<eval>";
            } else {
               var6 = "<stdin>";
               var8 = "<stdin>";
            }
         } else if (var6.length() > 20) {
            var8 = (new File(var6)).getName();
         }

         String var7 = "\"" + var8 + "\", line " + var9;
         var1.insertItemAt(var7, var4);
         var7 = "\"" + var6 + "\", line " + var9;
         var2.addElement(var7);
      }

      this.db.context.enableUpdate();
      var1.setSelectedIndex(0);
      var1.setMinimumSize(new Dimension(50, var1.getMinimumSize().height));
   }
}
