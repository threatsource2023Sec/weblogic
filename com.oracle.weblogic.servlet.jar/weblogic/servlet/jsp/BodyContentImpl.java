package weblogic.servlet.jsp;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.ByteBuffer;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import weblogic.servlet.internal.CharChunkOutput;
import weblogic.servlet.internal.ChunkOutputWrapper;
import weblogic.servlet.internal.DelegateChunkWriter;

public final class BodyContentImpl extends BodyContent implements ByteWriter {
   ChunkOutputWrapper co;
   private CharChunkOutput coimpl;
   PageContextImpl pc;
   private boolean usingWriter;
   private static final String EOL = System.getProperty("line.separator");

   public BodyContentImpl(JspWriter enclosing, PageContextImpl pc, Writer writer) {
      super(enclosing);
      this.pc = pc;
      if (writer == null) {
         ServletResponse rsp = pc.getResponse();
         this.coimpl = new CharChunkOutput(rsp);
         this.coimpl.setStickyBufferSize(true);
         this.co = new ChunkOutputWrapper(this.coimpl);
      } else {
         this.usingWriter = true;
         DelegateChunkWriter.DelegateJspChunkWriter impl = new DelegateChunkWriter.DelegateJspChunkWriter(writer);
         this.co = new ChunkOutputWrapper(impl);
      }

   }

   public void clearBuffer() throws IOException {
      this.co.clearBuffer();
   }

   public void clear() throws IOException {
      if (this.usingWriter) {
         throw new IOException("Cannot call clear() on an unbuffered Writer. ");
      } else {
         this.co.clearBuffer();
      }
   }

   public void flush() throws IOException {
      if (!this.usingWriter) {
         throw new IOException("Invalid to flush BodyContent: no backing stream behind it.");
      } else {
         this.co.flush();
      }
   }

   public void close() throws IOException {
   }

   public int getBufferSize() {
      return this.co.getBufferSize();
   }

   public int getRemaining() {
      if (this.usingWriter) {
         return 0;
      } else {
         int ret = this.co.getBufferSize() - this.co.getCount();
         return ret > 0 ? ret : 0;
      }
   }

   public boolean isAutoFlush() {
      return true;
   }

   public void newLine() throws IOException {
      this.co.print(EOL);
   }

   public void print(boolean x) throws IOException {
      this.co.print(String.valueOf(x));
   }

   public void print(char x) throws IOException {
      this.co.write(x);
   }

   public void print(char[] x) throws IOException {
      this.co.write((char[])x, 0, x.length);
   }

   public void print(double x) throws IOException {
      this.co.print(String.valueOf(x));
   }

   public void print(float x) throws IOException {
      this.co.print(String.valueOf(x));
   }

   public void print(int x) throws IOException {
      this.co.print(String.valueOf(x));
   }

   public void print(long x) throws IOException {
      this.co.print(String.valueOf(x));
   }

   public void print(Object x) throws IOException {
      if (x != null) {
         this.co.print(String.valueOf(x));
      }

   }

   public void print(String x) throws IOException {
      if (x != null) {
         this.co.print(x);
      }

   }

   public void println(boolean x) throws IOException {
      this.print(x);
      this.newLine();
   }

   public void println(char x) throws IOException {
      this.print(x);
      this.newLine();
   }

   public void println(char[] x) throws IOException {
      this.print(x);
      this.newLine();
   }

   public void println(double x) throws IOException {
      this.print(x);
      this.newLine();
   }

   public void println(float x) throws IOException {
      this.print(x);
      this.newLine();
   }

   public void println(int x) throws IOException {
      this.print(x);
      this.newLine();
   }

   public void println(long x) throws IOException {
      this.print(x);
      this.newLine();
   }

   public void println(Object x) throws IOException {
      if (x != null) {
         this.print(x);
         this.newLine();
      }

   }

   public void println(String x) throws IOException {
      if (x != null) {
         this.print(x);
         this.newLine();
      }

   }

   public void println() throws IOException {
      this.newLine();
   }

   public void write(char[] x) throws IOException {
      this.co.write((char[])x, 0, x.length);
   }

   public void write(char[] x, int off, int len) throws IOException {
      this.co.write(x, off, len);
   }

   public void write(int c) throws IOException {
      this.co.write(c);
   }

   public void write(String x) throws IOException {
      if (x != null) {
         this.co.print(x);
      }

   }

   public void write(String x, int off, int len) throws IOException {
      this.co.print(x.substring(off, off + len));
   }

   public void clearBody() {
      this.co.clearBuffer();
   }

   public Reader getReader() {
      if (this.usingWriter) {
         return null;
      } else {
         char[] buf = this.coimpl.getCharBuffer();
         int count = this.coimpl.getCount();
         return (Reader)(buf != null && count != 0 ? new CharArrayReader(buf, 0, count) : new StringReader(""));
      }
   }

   public String getString() {
      if (this.usingWriter) {
         return null;
      } else {
         String ret = "";
         char[] cb = this.coimpl.getCharBuffer();
         int count = this.coimpl.getCount();
         return cb != null && count != 0 ? new String(cb, 0, count) : ret;
      }
   }

   public void writeOut(Writer w) throws IOException {
      if (!this.usingWriter) {
         char[] buf = this.coimpl.getCharBuffer();
         int count = this.coimpl.getCount();
         if (buf == null || count == 0) {
            return;
         }

         w.write(buf, 0, count);
      }

   }

   public void write(byte[] b, String s) throws IOException {
      this.write((String)s, 0, s.length());
   }

   public void write(ByteBuffer b, String s) throws IOException {
      this.write((String)s, 0, s.length());
   }

   public boolean writeBytes(byte[] bytes, int offset, int len) throws IOException {
      return false;
   }

   public void setInitCharacterEncoding(String enc, boolean isEncSupported) {
   }

   ChunkOutputWrapper getChunkOutputWrapper() {
      return this.co;
   }

   private static void p(String s) {
      System.err.println("[BC]: " + s);
   }
}
