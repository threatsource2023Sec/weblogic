package weblogic.servlet.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import weblogic.logging.Loggable;
import weblogic.servlet.HTTPLogger;

public class NestedServletResponse extends ServletResponseImpl implements HttpServletResponse {
   protected final HttpServletResponse delegate;
   protected final ServletResponse orig;

   protected NestedServletResponse(ServletResponse dlg) {
      for(this.orig = (ServletResponse)dlg; dlg instanceof NestedServletResponse; dlg = ((NestedServletResponse)dlg).delegate) {
      }

      this.delegate = (HttpServletResponse)dlg;
   }

   public final void flushBuffer() throws IOException {
      this.delegate.flushBuffer();
   }

   public final int getBufferSize() {
      return this.delegate.getBufferSize();
   }

   public final String getCharacterEncoding() {
      return this.delegate.getCharacterEncoding();
   }

   public final Locale getLocale() {
      return this.delegate.getLocale();
   }

   public CharsetMap getCharsetMap() {
      return ((ServletResponseImpl)this.delegate).getCharsetMap();
   }

   public ServletOutputStream getOutputStream() throws IOException {
      return this.delegate instanceof ServletResponseImpl ? ((ServletResponseImpl)this.delegate).getOutputStreamNoCheck() : this.delegate.getOutputStream();
   }

   public ServletOutputStream getOutputStreamNoCheck() {
      return ((ServletResponseImpl)this.delegate).getOutputStreamNoCheck();
   }

   public PrintWriter getWriter() throws IOException {
      return this.delegate instanceof ServletResponseImpl ? ((ServletResponseImpl)this.delegate).getWriterNoCheck() : this.delegate.getWriter();
   }

   public final boolean isCommitted() {
      return this.delegate.isCommitted();
   }

   public final void reset() {
   }

   public final void setBufferSize(int size) {
   }

   public final void setBufferSizeNoWriteCheck(int size) {
   }

   public final void setContentLength(int length) {
   }

   public final void setContentLengthLong(long length) {
   }

   public final void setContentType(String type) {
   }

   public final void setCharacterEncoding(String newEncoding) {
      this.delegate.setCharacterEncoding(newEncoding);
   }

   public final void setLocale(Locale l) {
      this.delegate.setLocale(l);
   }

   public final void resetBuffer() {
   }

   public final void addCookie(Cookie cookie) {
   }

   public final void addDateHeader(String name, long date) {
   }

   public final void addHeader(String name, String val) {
   }

   public final void addIntHeader(String name, int val) {
   }

   public final boolean containsHeader(String name) {
      return this.delegate.containsHeader(name);
   }

   public final String encodeRedirectURL(String url) {
      return this.delegate.encodeRedirectUrl(url);
   }

   public final String encodeRedirectUrl(String url) {
      return this.delegate.encodeRedirectUrl(url);
   }

   public final String encodeURL(String url) {
      return this.delegate.encodeUrl(url);
   }

   public final String encodeUrl(String url) {
      return this.delegate.encodeUrl(url);
   }

   public final void sendError(int statusCode) throws IOException {
      if (statusCode == 404) {
         this.handleResourceNotFoundFromDefaultServlet();
      }

   }

   public final void sendError(int statusCode, String message) throws IOException {
      if (statusCode == 404) {
         this.handleResourceNotFoundFromDefaultServlet();
      }

   }

   public final void sendRedirect(String location) {
   }

   public final void setDateHeader(String name, long date) {
   }

   public final void setHeader(String name, String value) {
   }

   public final void setIntHeader(String name, int value) {
   }

   public final void setStatus(int statusCode) {
   }

   public final void setStatus(int statusCode, String message) {
   }

   public final HttpServletResponse getDelegate() {
      return this.delegate;
   }

   public final ServletResponse getOriginalResponse() {
      return this.orig;
   }

   private void handleResourceNotFoundFromDefaultServlet() throws FileNotFoundException {
      ServletRequestImpl request = ((ServletResponseImpl)this.delegate).getRequest();
      String includedUri = (String)request.getAttribute("javax.servlet.include.request_uri");
      if (includedUri != null) {
         Loggable logger = HTTPLogger.logIncludedFileNotFoundLoggable(includedUri, request.getRequestURI());
         logger.log();
         WebAppServletContext context = (WebAppServletContext)request.getServletContext();
         if (context.isServedByDefaultServlet(includedUri)) {
            throw new FileNotFoundException(logger.getMessage());
         }
      }

   }
}
