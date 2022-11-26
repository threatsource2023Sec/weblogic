package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

public class DelegateChunkWriter extends ChunkOutput {
   protected Writer writer;
   private ServletResponse response;
   private boolean autoFlush;
   private String enc;

   public DelegateChunkWriter(ServletResponse rsp, boolean autoFlush, int buffSize) throws IOException {
      if (HTTPDebugLogger.isEnabled()) {
         p("CTR");
      }

      this.response = rsp;
      this.autoFlush = autoFlush;
   }

   protected DelegateChunkWriter(Writer writer) {
      this.writer = writer;
   }

   public String getEncoding() {
      return this.response != null ? this.response.getCharacterEncoding() : null;
   }

   public void reset() {
      if (HTTPDebugLogger.isEnabled()) {
         p("reset");
      }

   }

   public void release() {
      if (HTTPDebugLogger.isEnabled()) {
         p("release");
      }

      if (this.writer instanceof WLOutputStreamWriter) {
         ((WLOutputStreamWriter)this.writer).release();
      }

   }

   public long getTotal() {
      return 0L;
   }

   public int getCount() {
      return 0;
   }

   public int getBufferSize() {
      return this.response != null ? this.response.getBufferSize() : 0;
   }

   public void setBufferSize(int bs) {
   }

   public void setStickyBufferSize(boolean b) {
   }

   public boolean isAutoFlush() {
      if (HTTPDebugLogger.isEnabled()) {
         p("isAutoFlush");
      }

      return this.autoFlush;
   }

   public void setAutoFlush(boolean b) {
      if (HTTPDebugLogger.isEnabled()) {
         p("setAutoFlush:" + b);
      }

      this.autoFlush = b;
   }

   public boolean isChunking() {
      return false;
   }

   public void setChunking(boolean b) {
      if (HTTPDebugLogger.isEnabled()) {
         p("setChunking");
      }

   }

   public void write(int i) throws IOException {
      this.getWriter().write(i);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (this.response != null) {
         this.response.getOutputStream().write(b, off, len);
      }

   }

   public void write(char[] c, int off, int len) throws IOException {
      if (HTTPDebugLogger.isEnabled()) {
         p("write(char[] c , int off, int len) called ");
      }

      this.getWriter().write(c, off, len);
   }

   public void print(String s) throws IOException {
      if (HTTPDebugLogger.isEnabled()) {
         p("print(String s)" + s);
      }

      if (s != null) {
         this.getWriter().write(s, 0, s.length());
      }

   }

   public void commit() throws IOException {
      if (HTTPDebugLogger.isEnabled()) {
         p("commit called !");
      }

   }

   public void clearBuffer() {
      if (HTTPDebugLogger.isEnabled()) {
         p("clearBuffer");
      }

      if (this.response != null) {
         this.response.resetBuffer();
      }

   }

   public void flush() throws IOException {
      if (HTTPDebugLogger.isEnabled()) {
         p("flush");
      }

      this.getWriter().flush();
      if (this.response != null) {
         this.response.flushBuffer();
      }

   }

   public void writeStream(InputStream is, long len, long clean) throws IOException {
   }

   protected Writer getWriter() throws IOException {
      String encoding = this.response.getCharacterEncoding();
      if (this.writer == null || encoding != null && !this.enc.equalsIgnoreCase(encoding)) {
         ServletOutputStream out = this.response.getOutputStream();
         this.enc = encoding;
         if (this.enc == null) {
            this.enc = "ISO-8859-1";
         }

         this.writer = new WLOutputStreamWriter(out, this.enc);
      }

      if (this.writer == null) {
         throw new IOException("Writer already closed ");
      } else {
         return this.writer;
      }
   }

   static void p(String s) {
      HTTPDebugLogger.debug("[DelegateChunkWriter]" + s);
   }

   public static class DelegateJspChunkWriter extends DelegateChunkWriter {
      public DelegateJspChunkWriter(Writer writer) {
         super(writer);
      }

      protected Writer getWriter() throws IOException {
         return this.writer;
      }

      public void clearBuffer() {
      }

      public int getBufferSize() {
         return 0;
      }

      public int getRemaining() {
         return 0;
      }

      public String getEncoding() {
         return null;
      }

      public void flush() throws IOException {
         this.writer.flush();
      }

      public void write(byte[] b, int off, int len) throws IOException {
      }

      public void writeStream(InputStream is, long len, long clen) throws IOException {
         throw new AssertionError("writeStream called ");
      }
   }
}
