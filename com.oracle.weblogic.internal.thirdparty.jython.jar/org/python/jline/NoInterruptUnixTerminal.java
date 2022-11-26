package org.python.jline;

public class NoInterruptUnixTerminal extends UnixTerminal {
   private String intr;

   public NoInterruptUnixTerminal() throws Exception {
   }

   public void init() throws Exception {
      super.init();
      this.intr = this.getSettings().getPropertyAsString("intr");
      if ("<undef>".equals(this.intr)) {
         this.intr = null;
      }

      if (this.intr != null) {
         this.getSettings().undef("intr");
      }

   }

   public void restore() throws Exception {
      if (this.intr != null) {
         this.getSettings().set("intr", this.intr);
      }

      super.restore();
   }
}
