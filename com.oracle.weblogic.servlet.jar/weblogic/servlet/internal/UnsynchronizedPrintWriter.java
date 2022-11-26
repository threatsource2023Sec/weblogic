package weblogic.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import weblogic.utils.PlatformConstants;

public class UnsynchronizedPrintWriter extends PrintWriter {
   private IOException exception = null;

   public UnsynchronizedPrintWriter(Writer out) {
      super(out);
   }

   public UnsynchronizedPrintWriter(Writer out, boolean autoFlush) {
      super(out, autoFlush);
   }

   public void flush() {
      try {
         this.out.flush();
      } catch (IOException var2) {
         this.handleIOException(var2);
      }

   }

   public void write(int c) {
      try {
         this.out.write(c);
      } catch (IOException var3) {
         this.handleIOException(var3);
      }

   }

   public void write(char[] buf, int off, int len) {
      try {
         this.out.write(buf, off, len);
      } catch (IOException var5) {
         this.handleIOException(var5);
      }

   }

   public void write(String s, int off, int len) {
      try {
         this.out.write(s, off, len);
      } catch (IOException var5) {
         this.handleIOException(var5);
      }

   }

   public void write(String s) {
      try {
         this.out.write(s);
      } catch (IOException var3) {
         this.handleIOException(var3);
      }

   }

   public void println() {
      try {
         this.out.write(PlatformConstants.EOL);
      } catch (IOException var2) {
         this.handleIOException(var2);
      }

   }

   public void println(boolean x) {
      super.print(x);
      this.println();
   }

   public void println(char x) {
      super.print(x);
      this.println();
   }

   public void println(int x) {
      super.print(x);
      this.println();
   }

   public void println(long x) {
      super.print(x);
      this.println();
   }

   public void println(float x) {
      super.print(x);
      this.println();
   }

   public void println(double x) {
      super.print(x);
      this.println();
   }

   public void println(char[] x) {
      super.print(x);
      this.println();
   }

   public void println(String x) {
      super.print(x);
      this.println();
   }

   public void println(Object x) {
      String s = String.valueOf(x);
      this.print(s);
      this.println();
   }

   public void close() {
      try {
         this.out.close();
      } catch (IOException var2) {
         this.handleIOException(var2);
      }

   }

   private void handleIOException(IOException e) {
      this.exception = e;
      this.setError();
   }

   public IOException getIOException() {
      return this.exception;
   }
}
