package weblogic.servlet.jsp;

import java.io.IOException;
import java.nio.ByteBuffer;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspWriter;
import weblogic.servlet.ServletResponseAttributeEvent;
import weblogic.servlet.ServletResponseAttributeListener;
import weblogic.servlet.internal.ChunkOutputWrapper;
import weblogic.servlet.internal.DelegateChunkWriter;
import weblogic.servlet.internal.ServletOutputStreamImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.utils.http.BytesToString;

public final class JspWriterImpl extends JspWriter implements ByteWriter, ServletResponseAttributeListener {
   private static final String EOL = System.getProperty("line.separator");
   private ChunkOutputWrapper co = null;
   private ServletResponse response;
   private boolean isClosed;
   private String initEncoding = null;
   private boolean isEncodingSupported = false;
   private boolean is8BitEncoding = false;
   private boolean isEncodingChanged = false;
   private boolean isServletResponseWrapper = false;

   public JspWriterImpl(ServletResponse rsp, int bufsize, boolean autoflush) throws IOException {
      super(bufsize, autoflush);
      this.response = rsp;
      if (this.response instanceof ServletResponseImpl) {
         ServletResponseImpl rspi = (ServletResponseImpl)this.response;
         ServletOutputStreamImpl sos = (ServletOutputStreamImpl)rspi.getOutputStreamNoCheck();
         this.co = sos.getOutput();
         this.co.setAutoFlush(super.autoFlush);
         ((ServletResponseImpl)this.response).registerAttributeListener(this);
      } else if (this.response instanceof NestedBodyResponse) {
         this.co = ((NestedBodyResponse)this.response).getBodyContentImpl().getChunkOutputWrapper();
         this.co.setAutoFlush(super.autoFlush);

         ServletResponse tmp;
         for(tmp = ((NestedBodyResponse)this.response).getResponse(); tmp instanceof NestedBodyResponse; tmp = ((NestedBodyResponse)tmp).getResponse()) {
         }

         if (tmp instanceof ServletResponseImpl) {
            ((ServletResponseImpl)tmp).registerAttributeListener(this);
         } else {
            this.isServletResponseWrapper = true;
         }
      } else {
         this.isServletResponseWrapper = true;
         DelegateChunkWriter dcw = new DelegateChunkWriter(this.response, super.autoFlush, super.bufferSize);
         this.co = new ChunkOutputWrapper(dcw);
         this.co.setAutoFlush(super.autoFlush);
      }

   }

   public void clear() throws IOException {
      if (this.response.isCommitted()) {
         throw new IOException("response already committed");
      } else {
         this.co.clearBuffer();
      }
   }

   public void clearBuffer() throws IOException {
      this.co.clearBuffer();
   }

   public void flush() throws IOException {
      this.checkIsClosed();
      this.co.flush();
   }

   public void close() throws IOException {
      if (!this.isClosed) {
         this.co.flush();
         this.isClosed = true;
      }
   }

   public int getBufferSize() {
      return this.co.getBufferSize();
   }

   public int getRemaining() {
      int ret = this.co.getBufferSize() - this.co.getCount();
      return ret > 0 ? ret : 0;
   }

   public boolean isAutoFlush() {
      return this.co.isAutoFlush();
   }

   public void newLine() throws IOException {
      this.checkIsClosed();
      this.co.print(EOL);
   }

   public void print(boolean x) throws IOException {
      this.checkIsClosed();
      this.co.print(String.valueOf(x));
   }

   public void print(char x) throws IOException {
      this.checkIsClosed();
      this.co.write(x);
   }

   public void print(char[] x) throws IOException {
      this.checkIsClosed();
      this.co.write((char[])x, 0, x.length);
   }

   public void print(double x) throws IOException {
      this.checkIsClosed();
      this.co.print(String.valueOf(x));
   }

   public void print(float x) throws IOException {
      this.checkIsClosed();
      this.co.print(String.valueOf(x));
   }

   public void print(int x) throws IOException {
      this.checkIsClosed();
      this.co.print(String.valueOf(x));
   }

   public void print(long x) throws IOException {
      this.checkIsClosed();
      this.co.print(String.valueOf(x));
   }

   public void print(Object x) throws IOException {
      this.checkIsClosed();
      this.co.print(String.valueOf(x));
   }

   public void print(String x) throws IOException {
      this.checkIsClosed();
      if (x == null) {
         this.co.print("null");
      } else {
         this.co.print(x);
      }

   }

   public void println(boolean x) throws IOException {
      this.checkIsClosed();
      this.print(x);
      this.newLine();
   }

   public void println(char x) throws IOException {
      this.checkIsClosed();
      this.print(x);
      this.newLine();
   }

   public void println(char[] x) throws IOException {
      this.checkIsClosed();
      this.print(x);
      this.newLine();
   }

   public void println(double x) throws IOException {
      this.checkIsClosed();
      this.print(x);
      this.newLine();
   }

   public void println(float x) throws IOException {
      this.checkIsClosed();
      this.print(x);
      this.newLine();
   }

   public void println(int x) throws IOException {
      this.checkIsClosed();
      this.print(x);
      this.newLine();
   }

   public void println(long x) throws IOException {
      this.checkIsClosed();
      this.print(x);
      this.newLine();
   }

   public void println(Object x) throws IOException {
      this.checkIsClosed();
      this.print(x);
      this.newLine();
   }

   public void println(String x) throws IOException {
      this.checkIsClosed();
      this.print(x);
      this.newLine();
   }

   public void println() throws IOException {
      this.newLine();
   }

   public void write(char[] x) throws IOException {
      this.checkIsClosed();
      this.co.write((char[])x, 0, x.length);
   }

   public void write(char[] x, int off, int len) throws IOException {
      this.checkIsClosed();
      this.co.write(x, off, len);
   }

   public void write(int c) throws IOException {
      this.checkIsClosed();
      this.co.write(c);
   }

   public void write(String x) throws IOException {
      this.checkIsClosed();
      if (x == null) {
         this.co.print("null");
      } else {
         this.co.print(x);
      }

   }

   public void write(String x, int off, int len) throws IOException {
      this.checkIsClosed();
      this.co.print(x.substring(off, off + len));
   }

   public void write(byte[] b, String s) throws IOException {
      this.checkIsClosed();
      if (this.isEncodingSupported && !this.hasEncodingChanged()) {
         this.co.write((byte[])b, 0, b.length);
      } else {
         this.print(s);
      }

   }

   public boolean writeBytes(byte[] bytes, int offset, int len) throws IOException {
      this.checkIsClosed();
      if (this.isEncodingSupported && !this.hasEncodingChanged()) {
         this.co.write(bytes, offset, len);
         return true;
      } else {
         return false;
      }
   }

   public void write(ByteBuffer buf, String s) throws IOException {
      this.checkIsClosed();
      if (this.isEncodingSupported && !this.hasEncodingChanged()) {
         this.co.write(buf);
      } else {
         this.print(s);
      }

   }

   private boolean hasEncodingChanged() {
      if (this.isServletResponseWrapper) {
         String enc = this.response.getCharacterEncoding();
         if (this.is8BitEncoding) {
            return !BytesToString.is8BitUnicodeSubset(enc);
         } else {
            return enc != null && !enc.equals(this.initEncoding);
         }
      } else {
         return this.isEncodingChanged;
      }
   }

   public void setInitCharacterEncoding(String enc, boolean isEncSupported) {
      if (this.initEncoding != enc) {
         this.initEncoding = enc;
         this.is8BitEncoding = BytesToString.is8BitUnicodeSubset(this.initEncoding);
         this.isEncodingSupported = isEncSupported;
         this.checkAndSetEncodingChange(this.response.getCharacterEncoding());
      }
   }

   private void checkIsClosed() throws IOException {
      if (this.isClosed) {
         throw new IOException("Stream already closed");
      }
   }

   public void attributeChanged(ServletResponseAttributeEvent event) {
      if (event.getName() == "ENCODING") {
         ServletResponse response = event.getResponse();
         String enc = response.getCharacterEncoding();
         this.checkAndSetEncodingChange(enc);
      }

   }

   private void checkAndSetEncodingChange(String enc) {
      if (this.is8BitEncoding) {
         this.isEncodingChanged = !BytesToString.is8BitUnicodeSubset(enc);
      } else {
         this.isEncodingChanged = enc == null ? true : this.initEncoding != null && !this.initEncoding.equals(enc);
      }

   }
}
