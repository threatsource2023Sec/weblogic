package org.glassfish.grizzly.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class ServletWriterImpl extends PrintWriter {
   private static final char[] LINE_SEP = System.getProperty("line.separator").toCharArray();
   protected Writer ob;
   protected boolean error = false;

   public ServletWriterImpl(Writer ob) {
      super(ob);
      this.ob = ob;
   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   void clear() {
      this.ob = null;
   }

   void recycle() {
      this.error = false;
   }

   public void flush() {
      if (!this.error) {
         try {
            this.ob.flush();
         } catch (IOException var2) {
            this.error = true;
         }

      }
   }

   public void close() {
      try {
         this.ob.close();
      } catch (IOException var2) {
      }

      this.error = false;
   }

   public boolean checkError() {
      this.flush();
      return this.error;
   }

   public void write(int c) {
      if (!this.error) {
         try {
            this.ob.write(c);
         } catch (IOException var3) {
            this.error = true;
         }

      }
   }

   public void write(char[] buf, int off, int len) {
      if (!this.error) {
         try {
            this.ob.write(buf, off, len);
         } catch (IOException var5) {
            this.error = true;
         }

      }
   }

   public void write(char[] buf) {
      this.write((char[])buf, 0, buf.length);
   }

   public void write(String s, int off, int len) {
      if (!this.error) {
         try {
            this.ob.write(s, off, len);
         } catch (IOException var5) {
            this.error = true;
         }

      }
   }

   public void write(String s) {
      this.write((String)s, 0, s.length());
   }

   public void print(boolean b) {
      if (b) {
         this.write("true");
      } else {
         this.write("false");
      }

   }

   public void print(char c) {
      this.write(c);
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
      this.write(s);
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
      this.write(LINE_SEP);
   }

   public void println(boolean b) {
      this.print(b);
      this.println();
   }

   public void println(char c) {
      this.print(c);
      this.println();
   }

   public void println(int i) {
      this.print(i);
      this.println();
   }

   public void println(long l) {
      this.print(l);
      this.println();
   }

   public void println(float f) {
      this.print(f);
      this.println();
   }

   public void println(double d) {
      this.print(d);
      this.println();
   }

   public void println(char[] c) {
      this.print(c);
      this.println();
   }

   public void println(String s) {
      this.print(s);
      this.println();
   }

   public void println(Object o) {
      this.print(o);
      this.println();
   }
}
