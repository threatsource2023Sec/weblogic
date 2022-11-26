package org.glassfish.grizzly.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.function.Supplier;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.http.server.Response;

public class HttpServletResponseImpl implements HttpServletResponse, Holders.ResponseHolder {
   private final ServletOutputStreamImpl outputStream = new ServletOutputStreamImpl(this);
   private ServletWriterImpl writer;
   protected boolean usingOutputStream = false;
   protected boolean usingWriter = false;
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(HttpServletResponseImpl.class, 2);
   protected Response response = null;
   protected HttpServletRequestImpl servletRequest;

   public static HttpServletResponseImpl create() {
      HttpServletResponseImpl response = (HttpServletResponseImpl)ThreadCache.takeFromCache(CACHE_IDX);
      return response != null ? response : new HttpServletResponseImpl();
   }

   protected HttpServletResponseImpl() {
   }

   public void initialize(Response response, HttpServletRequestImpl servletRequest) throws IOException {
      this.response = response;
      this.servletRequest = servletRequest;
      this.outputStream.initialize();
   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   public void finish() throws IOException {
      if (this.response == null) {
         throw new IllegalStateException("Null response object");
      } else {
         this.response.finish();
      }
   }

   public String getCharacterEncoding() {
      if (this.response == null) {
         throw new IllegalStateException("Null response object");
      } else {
         return this.response.getCharacterEncoding();
      }
   }

   public ServletOutputStream getOutputStream() throws IOException {
      if (this.usingWriter) {
         throw new IllegalStateException("Illegal attempt to call getOutputStream() after getWriter() has already been called.");
      } else {
         this.usingOutputStream = true;
         return this.outputStream;
      }
   }

   void recycle() {
      this.response = null;
      this.servletRequest = null;
      this.writer = null;
      this.outputStream.recycle();
      this.usingOutputStream = false;
      this.usingWriter = false;
   }

   public PrintWriter getWriter() throws IOException {
      if (this.usingOutputStream) {
         throw new IllegalStateException("Illegal attempt to call getWriter() after getOutputStream has already been called.");
      } else {
         this.usingWriter = true;
         if (this.writer == null) {
            this.writer = new ServletWriterImpl(this.response.getWriter());
         }

         return this.writer;
      }
   }

   public void setContentLength(int len) {
      if (!this.isCommitted()) {
         this.response.setContentLength(len);
      }
   }

   public void setContentLengthLong(long len) {
      if (!this.isCommitted()) {
         this.response.setContentLengthLong(len);
      }
   }

   public void setContentType(String type) {
      if (!this.isCommitted()) {
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(new SetContentTypePrivilegedAction(type));
         } else {
            this.response.setContentType(type);
         }

      }
   }

   public void setBufferSize(int size) {
      if (this.isCommitted()) {
         throw new IllegalStateException("Illegal attempt to adjust the buffer size after the response has already been committed.");
      } else {
         this.response.setBufferSize(size);
      }
   }

   public int getBufferSize() {
      if (this.response == null) {
         throw new IllegalStateException("Null response object");
      } else {
         return this.response.getBufferSize();
      }
   }

   public void flushBuffer() throws IOException {
      if (System.getSecurityManager() != null) {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws IOException {
                  HttpServletResponseImpl.this.response.flush();
                  return null;
               }
            });
         } catch (PrivilegedActionException var3) {
            Exception ex = var3.getException();
            if (ex instanceof IOException) {
               throw (IOException)ex;
            }
         }
      } else {
         this.response.flush();
      }

   }

   public void resetBuffer() {
      if (this.isCommitted()) {
         throw new IllegalStateException("Illegal attempt to reset the buffer after the response has already been committed.");
      } else {
         this.response.resetBuffer();
      }
   }

   public boolean isCommitted() {
      if (this.response == null) {
         throw new IllegalStateException("Null response object");
      } else {
         return this.response.isCommitted();
      }
   }

   public void reset() {
      if (this.isCommitted()) {
         throw new IllegalStateException("Illegal attempt to reset the response after it has already been committed.");
      } else {
         this.response.reset();
      }
   }

   public void setLocale(Locale loc) {
      if (!this.isCommitted()) {
         this.response.setLocale(loc);
      }
   }

   public Locale getLocale() {
      if (this.response == null) {
         throw new IllegalStateException("Null response object");
      } else {
         return this.response.getLocale();
      }
   }

   public void addCookie(Cookie cookie) {
      if (!this.isCommitted()) {
         CookieWrapper wrapper = new CookieWrapper(cookie.getName(), cookie.getValue());
         wrapper.setWrappedCookie(cookie);
         this.response.addCookie(wrapper);
      }
   }

   public boolean containsHeader(String name) {
      if (this.response == null) {
         throw new IllegalStateException("Null response object");
      } else {
         return this.response.containsHeader(name);
      }
   }

   public String encodeURL(String url) {
      if (this.response == null) {
         throw new IllegalStateException("Null response object");
      } else {
         return this.response.encodeURL(url);
      }
   }

   public String encodeRedirectURL(String url) {
      if (this.response == null) {
         throw new IllegalStateException("Null response object");
      } else {
         return this.response.encodeRedirectURL(url);
      }
   }

   public String encodeUrl(String url) {
      return this.encodeURL(url);
   }

   public String encodeRedirectUrl(String url) {
      return this.encodeRedirectURL(url);
   }

   public void sendError(int sc, String msg) throws IOException {
      if (this.isCommitted()) {
         throw new IllegalStateException("Illegal attempt to call sendError() after the response has been committed.");
      } else {
         this.response.sendError(sc, msg);
      }
   }

   public void sendError(int sc) throws IOException {
      if (this.isCommitted()) {
         throw new IllegalStateException("Illegal attempt to call sendError() after the response has already been committed.");
      } else {
         this.response.sendError(sc);
      }
   }

   public void sendRedirect(String location) throws IOException {
      if (this.isCommitted()) {
         throw new IllegalStateException("Illegal attempt to redirect the response after it has been committed.");
      } else {
         this.response.sendRedirect(location);
      }
   }

   public String getHeader(String string) {
      return this.response.getHeader(string);
   }

   public Collection getHeaderNames() {
      return new ArrayList(Arrays.asList(this.response.getHeaderNames()));
   }

   public Collection getHeaders(String string) {
      return new ArrayList(Arrays.asList(this.response.getHeaderValues(string)));
   }

   public void setDateHeader(String name, long date) {
      if (!this.isCommitted()) {
         this.response.setDateHeader(name, date);
      }
   }

   public void addDateHeader(String name, long date) {
      if (!this.isCommitted()) {
         this.response.addDateHeader(name, date);
      }
   }

   public void setHeader(String name, String value) {
      if (!this.isCommitted()) {
         this.response.setHeader(name, value);
      }
   }

   public void addHeader(String name, String value) {
      if (!this.isCommitted()) {
         this.response.addHeader(name, value);
      }
   }

   public void setIntHeader(String name, int value) {
      if (!this.isCommitted()) {
         this.response.setIntHeader(name, value);
      }
   }

   public void addIntHeader(String name, int value) {
      if (!this.isCommitted()) {
         this.response.addIntHeader(name, value);
      }
   }

   public void setStatus(int sc) {
      if (!this.isCommitted()) {
         this.response.setStatus(sc);
      }
   }

   public void setStatus(int sc, String sm) {
      if (!this.isCommitted()) {
         this.response.setStatus(sc, sm);
      }
   }

   public int getStatus() {
      return this.response.getStatus();
   }

   public String getMessage() {
      return this.response.getMessage();
   }

   public boolean isError() {
      return this.response.isError();
   }

   public String getContentType() {
      return this.response.getContentType();
   }

   public void setCharacterEncoding(String charEnc) {
      this.response.setCharacterEncoding(charEnc);
   }

   public Response getResponse() {
      return this.response;
   }

   public Response getInternalResponse() {
      return this.response;
   }

   public void setTrailerFields(Supplier supplier) {
      this.response.setTrailers(supplier);
   }

   public Supplier getTrailerFields() {
      return this.response.getTrailers();
   }

   private final class SetContentTypePrivilegedAction implements PrivilegedAction {
      private final String contentType;

      public SetContentTypePrivilegedAction(String contentType) {
         this.contentType = contentType;
      }

      public Object run() {
         HttpServletResponseImpl.this.response.setContentType(this.contentType);
         return null;
      }
   }
}
