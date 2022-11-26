package weblogic.servlet.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class BufferredResponseWrapper extends HttpServletResponseWrapper {
   private ServletOutputStream sos;
   private BufferredOutputStream bos = new BufferredOutputStream();
   private PrintWriter writer;
   private int status = 200;
   private boolean verbose = false;

   public BufferredResponseWrapper(HttpServletResponse response) throws IOException {
      super(response);
   }

   public int getStatus() {
      return this.status;
   }

   public byte[] getContent() throws IOException {
      this.bos.flush();
      return this.bos.getContent();
   }

   public boolean getVerbose() {
      return this.verbose;
   }

   public void setVerbose(boolean b) {
      this.verbose = b;
   }

   public void setBufferSize(int size) {
   }

   public void setContentType(String ct) {
   }

   public void setContentLength(int len) {
   }

   public void addCookie(Cookie cookie) {
   }

   public void addDateHeader(String name, long date) {
   }

   public void addHeader(String name, String value) {
   }

   public void addIntHeader(String name, int value) {
   }

   public void sendRedirect(String location) {
   }

   public void setDateHeader(String name, long date) {
   }

   public void setHeader(String name, String value) {
   }

   public void setIntHeader(String name, int value) {
   }

   public void sendError(int sc) {
      this.status = sc;
   }

   public void sendError(int sc, String msg) {
      this.status = sc;
   }

   public void setStatus(int sc) {
      this.status = sc;
   }

   public void setStatus(int sc, String sm) {
      this.status = sc;
   }

   public boolean isCommitted() {
      return false;
   }

   public ServletOutputStream getOutputStream() throws IOException {
      if (this.writer != null) {
         throw new IllegalStateException("Cannot get Writer then OutputStream");
      } else {
         this.sos = this.bos;
         return this.sos;
      }
   }

   public PrintWriter getWriter() throws IOException {
      if (this.sos != null) {
         throw new IllegalStateException("Cannot get OutputStream then Writer");
      } else {
         if (this.writer == null) {
            this.writer = new PrintWriter(new OutputStreamWriter(this.bos, this.getCharacterEncoding()));
         }

         return this.writer;
      }
   }

   public void flushBuffer() {
      try {
         if (this.writer != null) {
            this.writer.flush();
         }

         if (this.sos != null) {
            this.sos.flush();
         }
      } catch (IOException var2) {
         if (this.verbose) {
            var2.printStackTrace();
         }
      }

   }

   public void reset() {
      this.status = 200;
      this.resetBuffer();
   }

   public void resetBuffer() {
      this.bos.reset();
      if (this.writer != null) {
         try {
            this.writer = new PrintWriter(new OutputStreamWriter(this.bos, this.getCharacterEncoding()));
         } catch (UnsupportedEncodingException var2) {
         }
      } else if (this.sos != null) {
         this.sos = this.bos;
      }

   }

   public static class BufferredOutputStream extends ServletOutputStream {
      private ByteArrayOutputStream baos = new ByteArrayOutputStream();

      public byte[] getContent() {
         return this.baos.toByteArray();
      }

      public void write(int b) throws IOException {
         this.baos.write(b);
      }

      public void write(byte[] array, int start, int length) throws IOException {
         this.baos.write(array, start, length);
      }

      public void reset() {
         this.baos.reset();
      }

      public boolean isReady() {
         return false;
      }

      public void setWriteListener(WriteListener writeListener) {
      }
   }
}
