package net.shibboleth.utilities.java.support.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class ThreadLocalHttpServletResponseProxy implements HttpServletResponse {
   public String getCharacterEncoding() {
      return this.getCurrent().getCharacterEncoding();
   }

   public String getContentType() {
      return this.getCurrent().getContentType();
   }

   public ServletOutputStream getOutputStream() throws IOException {
      return this.getCurrent().getOutputStream();
   }

   public PrintWriter getWriter() throws IOException {
      return this.getCurrent().getWriter();
   }

   public void setCharacterEncoding(String charset) {
      this.getCurrent().setCharacterEncoding(charset);
   }

   public void setContentLength(int len) {
      this.getCurrent().setContentLength(len);
   }

   public void setContentType(String type) {
      this.getCurrent().setContentType(type);
   }

   public void setBufferSize(int size) {
      this.getCurrent().setBufferSize(size);
   }

   public int getBufferSize() {
      return this.getCurrent().getBufferSize();
   }

   public void flushBuffer() throws IOException {
      this.getCurrent().flushBuffer();
   }

   public void resetBuffer() {
      this.getCurrent().resetBuffer();
   }

   public boolean isCommitted() {
      return this.getCurrent().isCommitted();
   }

   public void reset() {
      this.getCurrent().reset();
   }

   public void setLocale(Locale loc) {
      this.getCurrent().setLocale(loc);
   }

   public Locale getLocale() {
      return this.getCurrent().getLocale();
   }

   public void addCookie(Cookie cookie) {
      this.getCurrent().addCookie(cookie);
   }

   public boolean containsHeader(String name) {
      return this.getCurrent().containsHeader(name);
   }

   public String encodeURL(String url) {
      return this.getCurrent().encodeURL(url);
   }

   public String encodeRedirectURL(String url) {
      return this.getCurrent().encodeRedirectURL(url);
   }

   public String encodeUrl(String url) {
      return this.getCurrent().encodeUrl(url);
   }

   public String encodeRedirectUrl(String url) {
      return this.getCurrent().encodeRedirectUrl(url);
   }

   public void sendError(int sc, String msg) throws IOException {
      this.getCurrent().sendError(sc, msg);
   }

   public void sendError(int sc) throws IOException {
      this.getCurrent().sendError(sc);
   }

   public void sendRedirect(String location) throws IOException {
      this.getCurrent().sendRedirect(location);
   }

   public void setDateHeader(String name, long date) {
      this.getCurrent().setDateHeader(name, date);
   }

   public void addDateHeader(String name, long date) {
      this.getCurrent().addDateHeader(name, date);
   }

   public void setHeader(String name, String value) {
      this.getCurrent().setHeader(name, value);
   }

   public void addHeader(String name, String value) {
      this.getCurrent().addHeader(name, value);
   }

   public void setIntHeader(String name, int value) {
      this.getCurrent().setIntHeader(name, value);
   }

   public void addIntHeader(String name, int value) {
      this.getCurrent().addIntHeader(name, value);
   }

   public void setStatus(int sc) {
      this.getCurrent().setStatus(sc);
   }

   public void setStatus(int sc, String sm) {
      this.getCurrent().setStatus(sc, sm);
   }

   public int getStatus() {
      return this.getCurrent().getStatus();
   }

   public String getHeader(String name) {
      return this.getCurrent().getHeader(name);
   }

   public Collection getHeaders(String name) {
      return this.getCurrent().getHeaders(name);
   }

   public Collection getHeaderNames() {
      return this.getCurrent().getHeaderNames();
   }

   protected HttpServletResponse getCurrent() {
      return (HttpServletResponse)Constraint.isNotNull(HttpServletRequestResponseContext.getResponse(), "Current HttpServletResponse has not been loaded via HttpServletRequestResponseContext");
   }
}
