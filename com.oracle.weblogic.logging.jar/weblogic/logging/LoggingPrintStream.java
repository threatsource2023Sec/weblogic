package weblogic.logging;

import java.io.OutputStream;
import java.io.PrintStream;

public class LoggingPrintStream extends PrintStream {
   public LoggingPrintStream(OutputStream out) {
      super(out);
   }

   public void println() {
      super.println();
      this.flush();
   }

   public void println(boolean x) {
      super.println(x);
      this.flush();
   }

   public void println(char x) {
      super.println(x);
      this.flush();
   }

   public void println(int x) {
      super.println(x);
      this.flush();
   }

   public void println(long x) {
      super.println(x);
      this.flush();
   }

   public void println(float x) {
      super.println(x);
      this.flush();
   }

   public void println(double x) {
      super.println(x);
      this.flush();
   }

   public void println(char[] x) {
      super.println(x);
      this.flush();
   }

   public void println(String x) {
      super.println(x);
      this.flush();
   }

   public void println(Object x) {
      super.println(x);
      this.flush();
   }
}
