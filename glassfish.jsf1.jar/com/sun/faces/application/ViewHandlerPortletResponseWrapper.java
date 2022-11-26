package com.sun.faces.application;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/** @deprecated */
public class ViewHandlerPortletResponseWrapper implements RenderResponse, InterweavingResponse {
   private RenderResponse response;
   private ByteArrayWebOutputStream bawos;
   private PrintWriter pw;
   private CharArrayWriter caw;

   public ViewHandlerPortletResponseWrapper(RenderResponse response) {
      this.response = response;
   }

   public void resetBuffers() throws IOException {
      if (this.caw != null) {
         this.caw.reset();
      } else if (this.bawos != null) {
         this.bawos.resetByteArray();
      }

   }

   public boolean isBytes() {
      return this.bawos != null;
   }

   public boolean isChars() {
      return this.caw != null;
   }

   public char[] getChars() {
      return this.caw != null ? this.caw.toCharArray() : null;
   }

   public byte[] getBytes() {
      return this.bawos != null ? this.bawos.toByteArray() : null;
   }

   public int getStatus() {
      return Integer.MIN_VALUE;
   }

   public String getContentType() {
      return this.response.getContentType();
   }

   public PortletURL createRenderURL() {
      return this.response.createRenderURL();
   }

   public PortletURL createActionURL() {
      return this.response.createActionURL();
   }

   public String getNamespace() {
      return this.response.getNamespace();
   }

   public void setTitle(String title) {
      this.response.setTitle(title);
   }

   public void setContentType(String type) {
      this.response.setContentType(type);
   }

   public String getCharacterEncoding() {
      return this.response.getCharacterEncoding();
   }

   public Locale getLocale() {
      return this.response.getLocale();
   }

   public void setBufferSize(int size) {
      this.response.setBufferSize(size);
   }

   public int getBufferSize() {
      return this.response.getBufferSize();
   }

   public void flushBuffer() throws IOException {
      this.response.flushBuffer();
   }

   public void resetBuffer() {
      this.response.resetBuffer();
   }

   public boolean isCommitted() {
      return this.response.isCommitted();
   }

   public void reset() {
      this.response.reset();
   }

   public PrintWriter getWriter() throws IOException {
      if (this.bawos != null) {
         throw new IllegalStateException();
      } else {
         if (this.pw == null) {
            this.caw = new CharArrayWriter(1024);
            this.pw = new PrintWriter(this.caw);
         }

         return this.pw;
      }
   }

   public OutputStream getPortletOutputStream() throws IOException {
      if (this.pw != null) {
         throw new IllegalStateException();
      } else {
         if (this.bawos == null) {
            this.bawos = new ByteArrayWebOutputStream();
         }

         return this.bawos;
      }
   }

   public void addProperty(String key, String value) {
      this.response.addProperty(key, value);
   }

   public void setProperty(String key, String value) {
      this.response.setProperty(key, value);
   }

   public String encodeURL(String path) {
      return this.response.encodeURL(path);
   }

   public void flushContentToWrappedResponse() throws IOException {
      if (this.caw != null) {
         this.pw.flush();
         this.caw.writeTo(this.response.getWriter());
         this.caw.reset();
      } else if (this.bawos != null) {
         try {
            this.bawos.writeTo(this.response.getWriter(), this.response.getCharacterEncoding());
         } catch (IllegalStateException var2) {
            this.bawos.writeTo(this.response.getPortletOutputStream());
         }

         this.bawos.resetByteArray();
      }

   }

   public void flushToWriter(Writer writer, String encoding) throws IOException {
      if (this.caw != null) {
         this.pw.flush();
         this.caw.writeTo(writer);
         this.caw.reset();
      } else if (this.bawos != null) {
         this.bawos.writeTo(writer, encoding);
         this.bawos.resetByteArray();
      }

      writer.flush();
   }
}
