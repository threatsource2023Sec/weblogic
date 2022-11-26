package org.mozilla.javascript.tools.shell;

class ConsoleWrite implements Runnable {
   private ConsoleTextArea textArea;
   private String str;

   public ConsoleWrite(ConsoleTextArea var1, String var2) {
      this.textArea = var1;
      this.str = var2;
   }

   public void run() {
      this.textArea.write(this.str);
   }
}
