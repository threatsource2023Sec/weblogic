package org.mozilla.javascript.tools.debugger;

import javax.swing.JMenu;
import org.mozilla.javascript.Context;

class EnterInterrupt implements Runnable {
   Main db;
   Context cx;

   EnterInterrupt(Main var1, Context var2) {
      this.db = var1;
      this.cx = var2;
   }

   public void run() {
      JMenu var1 = this.db.getJMenuBar().getMenu(0);
      var1 = this.db.getJMenuBar().getMenu(2);
      var1.getItem(0).setEnabled(false);
      int var2 = var1.getItemCount();

      for(int var3 = 1; var3 < var2; ++var3) {
         var1.getItem(var3).setEnabled(true);
      }

      boolean var4 = false;
      int var5 = 0;

      for(int var6 = this.db.toolBar.getComponentCount(); var5 < var6; ++var5) {
         this.db.toolBar.getComponent(var5).setEnabled(var4);
         var4 = true;
      }

      this.db.toolBar.setEnabled(true);
      this.db.toFront();
   }
}
