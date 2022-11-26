package org.mozilla.javascript.tools.debugger;

import javax.swing.text.BadLocationException;

class CreateFileWindow implements Runnable {
   Main db;
   String url;
   String text;
   int line;
   boolean activate;

   CreateFileWindow(Main var1, String var2, String var3, int var4) {
      this.db = var1;
      this.url = var2;
      this.text = var3;
      this.line = var4;
      this.activate = true;
   }

   CreateFileWindow(Main var1, String var2, String var3, int var4, boolean var5) {
      this.db = var1;
      this.url = var2;
      this.text = var3;
      this.line = var4;
      this.activate = var5;
   }

   public void run() {
      FileWindow var1 = new FileWindow(this.db, this.url, this.text);
      this.db.fileWindows.put(this.url, var1);
      if (this.line != -1) {
         if (this.db.currentWindow != null) {
            this.db.currentWindow.setPosition(-1);
         }

         try {
            var1.setPosition(var1.textArea.getLineStartOffset(this.line - 1));
         } catch (BadLocationException var3) {
            var1.setPosition(-1);
         }
      }

      this.db.desk.add(var1);
      if (this.line != -1) {
         this.db.currentWindow = var1;
      }

      this.db.menubar.addFile(this.url);
      var1.setVisible(true);
      if (this.activate) {
         try {
            var1.setMaximum(true);
            var1.setSelected(true);
            var1.moveToFront();
         } catch (Exception var2) {
         }
      }

   }
}
