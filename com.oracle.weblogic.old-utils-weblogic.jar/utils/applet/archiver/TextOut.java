package utils.applet.archiver;

import java.awt.TextArea;
import java.io.PrintStream;

public class TextOut extends PrintStream {
   static final String nl = "\n";
   TextArea t;
   private static final int MAX_CHARS = 30000;

   void newLine() {
      this.write("\n");
   }

   private void write(String s) {
      if (this.t.getText().length() + s.length() >= 30000) {
         this.t.setText("****<truncated buffer>***\n");
      }

      this.t.appendText(s);
   }

   public static void main(String[] a) throws Exception {
      (new TOFrame("Stdout/Stderr")).run();
   }

   public TextOut(TextArea t) {
      super(new NullOutputStream());
      this.t = t;
   }

   public void write(int b) {
      this.write(String.valueOf((char)b));
   }

   public void write(byte[] buf, int off, int len) {
      this.write(new String(buf, 0, off, len));
   }

   public void print(boolean b) {
      this.write(b ? "true" : "false");
   }

   public void print(char c) {
      this.write(String.valueOf(c));
   }

   public void print(int i) {
      this.write(String.valueOf(i));
   }

   public void print(long l) {
      this.write(String.valueOf(l));
   }

   public void print(float f) {
      this.write(String.valueOf(f));
   }

   public void print(double d) {
      this.write(String.valueOf(d));
   }

   public void print(char[] s) {
      this.print(new String(s));
   }

   public void print(String s) {
      if (s == null) {
         s = "null";
      }

      this.write(s);
   }

   public void print(Object obj) {
      this.write(String.valueOf(obj));
   }

   public void println() {
      this.write("\n");
   }

   public void println(boolean x) {
      synchronized(this) {
         this.print(x);
         this.newLine();
      }
   }

   public void println(char x) {
      synchronized(this) {
         this.print(x);
         this.newLine();
      }
   }

   public void println(int x) {
      synchronized(this) {
         this.print(x);
         this.newLine();
      }
   }

   public void println(long x) {
      synchronized(this) {
         this.print(x);
         this.newLine();
      }
   }

   public void println(float x) {
      synchronized(this) {
         this.print(x);
         this.newLine();
      }
   }

   public void println(double x) {
      synchronized(this) {
         this.print(x);
         this.newLine();
      }
   }

   public void println(char[] x) {
      synchronized(this) {
         this.print(x);
         this.newLine();
      }
   }

   public void println(String x) {
      synchronized(this) {
         this.print(x);
         this.newLine();
      }
   }

   public void println(Object x) {
      synchronized(this) {
         this.print(x);
         this.newLine();
      }
   }
}
