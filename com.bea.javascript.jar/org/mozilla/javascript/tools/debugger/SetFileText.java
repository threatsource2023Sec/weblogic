package org.mozilla.javascript.tools.debugger;

class SetFileText implements Runnable {
   FileWindow w;
   String text;

   SetFileText(FileWindow var1, String var2) {
      this.w = var1;
      this.text = var2;
   }

   public void run() {
      this.w.setText(this.text);
   }
}
