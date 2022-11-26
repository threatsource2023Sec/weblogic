package org.mozilla.javascript.tools.debugger;

import javax.swing.text.BadLocationException;

class SetFilePosition implements Runnable {
   Main db;
   FileWindow w;
   int line;
   boolean activate;

   SetFilePosition(Main var1, FileWindow var2, int var3) {
      this.db = var1;
      this.w = var2;
      this.line = var3;
      this.activate = true;
   }

   SetFilePosition(Main var1, FileWindow var2, int var3, boolean var4) {
      this.db = var1;
      this.w = var2;
      this.line = var3;
      this.activate = var4;
   }

   public void run() {
      FileTextArea var1 = this.w.textArea;

      try {
         if (this.line == -1) {
            this.w.setPosition(-1);
            if (this.db.currentWindow == this.w) {
               this.db.currentWindow = null;
            }
         } else {
            int var2 = var1.getLineStartOffset(this.line - 1);
            if (this.db.currentWindow != null && this.db.currentWindow != this.w) {
               this.db.currentWindow.setPosition(-1);
            }

            this.w.setPosition(var2);
            this.db.currentWindow = this.w;
         }
      } catch (BadLocationException var4) {
      }

      if (this.activate) {
         if (this.w.isIcon()) {
            this.db.desk.getDesktopManager().deiconifyFrame(this.w);
         }

         try {
            this.w.show();
         } catch (Exception var3) {
         }
      }

   }
}
