package org.mozilla.javascript.tools.debugger;

import javax.swing.JMenu;

class ExitInterrupt implements Runnable {
   Main db;

   ExitInterrupt(Main var1) {
      this.db = var1;
   }

   public void run() {
      JMenu var1 = this.db.getJMenuBar().getMenu(0);
      var1.getItem(0).setEnabled(true);
      var1 = this.db.getJMenuBar().getMenu(2);
      var1.getItem(0).setEnabled(true);
      int var2 = var1.getItemCount() - 1;

      for(int var3 = 1; var3 < var2; ++var3) {
         var1.getItem(var3).setEnabled(false);
      }

      this.db.context.disable();
      boolean var4 = true;
      int var5 = 0;

      for(int var6 = this.db.toolBar.getComponentCount(); var5 < var6; ++var5) {
         this.db.toolBar.getComponent(var5).setEnabled(var4);
         var4 = false;
      }

   }
}
