package org.mozilla.javascript.tools.shell;

import java.io.OutputStream;
import javax.swing.SwingUtilities;

class ConsoleWriter extends OutputStream {
   private ConsoleTextArea textArea;
   private StringBuffer buffer;

   public ConsoleWriter(ConsoleTextArea var1) {
      this.textArea = var1;
      this.buffer = new StringBuffer();
   }

   public void close() {
      this.flush();
   }

   public synchronized void flush() {
      if (this.buffer.length() > 0) {
         this.flushBuffer();
      }

   }

   private void flushBuffer() {
      String var1 = this.buffer.toString();
      this.buffer.setLength(0);
      SwingUtilities.invokeLater(new ConsoleWrite(this.textArea, var1));
   }

   public synchronized void write(int var1) {
      this.buffer.append((char)var1);
      if (var1 == 10) {
         this.flushBuffer();
      }

   }

   public synchronized void write(char[] var1, int var2, int var3) {
      for(int var4 = var2; var4 < var3; ++var4) {
         this.buffer.append(var1[var4]);
         if (var1[var4] == '\n') {
            this.flushBuffer();
         }
      }

   }
}
