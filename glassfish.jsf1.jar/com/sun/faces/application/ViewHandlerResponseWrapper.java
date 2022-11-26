package com.sun.faces.application;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ViewHandlerResponseWrapper extends HttpServletResponseWrapper {
   private ByteArrayWebOutputStream basos;
   private WebPrintWriter pw;
   private CharArrayWriter caw;
   private int status = 200;

   public ViewHandlerResponseWrapper(HttpServletResponse wrapped) {
      super(wrapped);
   }

   public void sendError(int sc, String msg) throws IOException {
      super.sendError(sc, msg);
      this.status = sc;
   }

   public void sendError(int sc) throws IOException {
      super.sendError(sc);
      this.status = sc;
   }

   public void setStatus(int sc) {
      super.setStatus(sc);
      this.status = sc;
   }

   public void setStatus(int sc, String sm) {
      super.setStatus(sc, sm);
      this.status = sc;
   }

   public int getStatus() {
      return this.status;
   }

   public boolean isBytes() {
      return null != this.basos;
   }

   public boolean isChars() {
      return null != this.caw;
   }

   public byte[] getBytes() {
      byte[] result = null;
      if (null != this.basos) {
         result = this.basos.toByteArray();
      }

      return result;
   }

   public char[] getChars() {
      char[] result = null;
      if (null != this.caw) {
         result = this.caw.toCharArray();
      }

      return result;
   }

   public String toString() {
      String result = "null";
      if (null != this.caw) {
         result = this.caw.toString();
      } else if (null != this.basos) {
         result = this.basos.toString();
      }

      return result;
   }

   public void flushContentToWrappedResponse() throws IOException {
      ServletResponse wrapped = this.getResponse();
      if (null != this.caw) {
         this.pw.flush();
         this.caw.writeTo(wrapped.getWriter());
         this.caw.reset();
      } else if (null != this.basos) {
         try {
            this.basos.writeTo(wrapped.getWriter(), wrapped.getCharacterEncoding());
         } catch (IllegalStateException var3) {
            this.basos.writeTo(wrapped.getOutputStream());
         }

         this.basos.resetByteArray();
      }

   }

   public void flushToWriter(Writer writer, String encoding) throws IOException {
      if (null != this.caw) {
         this.pw.flush();
         this.caw.writeTo(writer);
         this.caw.reset();
      } else if (null != this.basos) {
         this.basos.writeTo(writer, encoding);
         this.basos.resetByteArray();
      }

      writer.flush();
   }

   public void resetBuffers() throws IOException {
      if (null != this.caw) {
         this.caw.reset();
      } else if (null != this.basos) {
         this.basos.resetByteArray();
      }

   }

   public ServletOutputStream getOutputStream() throws IOException {
      if (this.pw != null && !this.pw.isComitted() && !this.isCommitted()) {
         throw new IllegalStateException();
      } else if (this.pw != null && (this.pw.isComitted() || this.isCommitted())) {
         return ByteArrayWebOutputStream.NOOP_STREAM;
      } else {
         if (null == this.basos) {
            this.basos = new ByteArrayWebOutputStream();
         }

         return this.basos;
      }
   }

   public PrintWriter getWriter() throws IOException {
      if (this.basos != null && !this.basos.isCommitted() && !this.isCommitted()) {
         throw new IllegalStateException();
      } else if (this.basos != null && (this.basos.isCommitted() || this.isCommitted())) {
         return new WebPrintWriter(WebPrintWriter.NOOP_WRITER);
      } else {
         if (null == this.pw) {
            this.caw = new CharArrayWriter(1024);
            this.pw = new WebPrintWriter(this.caw);
         }

         return this.pw;
      }
   }
}
