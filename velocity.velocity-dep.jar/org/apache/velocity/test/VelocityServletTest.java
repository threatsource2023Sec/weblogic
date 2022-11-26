package org.apache.velocity.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.servlet.VelocityServlet;

public class VelocityServletTest extends TestCase {
   public VelocityServletTest() {
      super("VelocityServletTest");
   }

   public static Test suite() {
      return new VelocityServletTest();
   }

   public void runTest() {
      MockVelocityServlet servlet = new MockVelocityServlet();

      try {
         servlet.init(new MockServletConfig());
      } catch (ServletException var3) {
         var3.printStackTrace();
      }

      System.out.println("output.encoding=" + RuntimeSingleton.getProperty("output.encoding"));
      HttpServletResponse res = new MockHttpServletResponse();
      servlet.visibleSetContentType((HttpServletRequest)null, res);
      Assert.assertEquals("Character encoding not set to UTF-8", "UTF-8", res.getCharacterEncoding());
   }

   static class MockHttpServletResponse implements HttpServletResponse {
      private String encoding;

      public void flushBuffer() throws IOException {
      }

      public int getBufferSize() {
         return -1;
      }

      public String getCharacterEncoding() {
         return this.encoding != null ? this.encoding : "ISO-8859-1";
      }

      public Locale getLocale() {
         return null;
      }

      public ServletOutputStream getOutputStream() throws IOException {
         return null;
      }

      public PrintWriter getWriter() throws IOException {
         return null;
      }

      public boolean isCommitted() {
         return false;
      }

      public void reset() {
      }

      public void setBufferSize(int i) {
      }

      public void setContentLength(int i) {
      }

      public void setContentType(String contentType) {
         if (contentType != null) {
            int index = contentType.lastIndexOf(59) + 1;
            if (0 <= index || index < contentType.length()) {
               index = contentType.indexOf("charset=", index);
               if (index != -1) {
                  index += 8;
                  this.encoding = contentType.substring(index).trim();
               }
            }
         }

      }

      public void setLocale(Locale l) {
      }

      public void addCookie(Cookie c) {
      }

      public void addDateHeader(String s, long l) {
      }

      public void addHeader(String name, String value) {
      }

      public void addIntHeader(String name, int value) {
      }

      public boolean containsHeader(String name) {
         return false;
      }

      public String encodeRedirectURL(String url) {
         return url;
      }

      public String encodeRedirectUrl(String url) {
         return url;
      }

      public String encodeURL(String url) {
         return url;
      }

      public String encodeUrl(String url) {
         return url;
      }

      public void sendError(int i) throws IOException {
      }

      public void sendError(int i, String s) throws IOException {
      }

      public void sendRedirect(String s) throws IOException {
      }

      public void setDateHeader(String s, long l) {
      }

      public void setHeader(String name, String value) {
      }

      public void setIntHeader(String s, int i) {
      }

      public void setStatus(int i) {
      }

      public void setStatus(int i, String s) {
      }
   }

   static class MockServletContext implements ServletContext {
      public Object getAttribute(String ignored) {
         return null;
      }

      public Enumeration getAttributeNames() {
         return null;
      }

      public ServletContext getContext(String ignored) {
         return this;
      }

      public String getInitParameter(String ignored) {
         return null;
      }

      public Enumeration getInitParameterNames() {
         return null;
      }

      public int getMajorVersion() {
         return -1;
      }

      public String getMimeType(String ignored) {
         return null;
      }

      public int getMinorVersion() {
         return -1;
      }

      public RequestDispatcher getNamedDispatcher(String ignored) {
         return null;
      }

      public String getRealPath(String ignored) {
         return null;
      }

      public RequestDispatcher getRequestDispatcher(String ignored) {
         return null;
      }

      public URL getResource(String ignored) throws MalformedURLException {
         return null;
      }

      public InputStream getResourceAsStream(String ignored) {
         return null;
      }

      public String getServerInfo() {
         return "Velocity Test Suite";
      }

      public Servlet getServlet(String ignored) throws ServletException {
         return null;
      }

      public Enumeration getServletNames() {
         return null;
      }

      public Enumeration getServlets() {
         return null;
      }

      public void log(Exception e, String msg) {
      }

      public void log(String msg) {
      }

      public void log(String msg, Throwable t) {
      }

      public void removeAttribute(String name) {
      }

      public void setAttribute(String name, Object value) {
      }
   }

   static class MockServletConfig implements ServletConfig {
      public String getInitParameter(String ignored) {
         return null;
      }

      public Enumeration getInitParameterNames() {
         return null;
      }

      public ServletContext getServletContext() {
         return new MockServletContext();
      }

      public String getServletName() {
         return "VelocityServlet";
      }
   }

   class MockVelocityServlet extends VelocityServlet {
      void visibleSetContentType(HttpServletRequest req, HttpServletResponse res) {
         this.setContentType(req, res);
      }

      protected Properties loadConfiguration(ServletConfig config) throws IOException {
         Properties p = new Properties();
         p.setProperty("output.encoding", "UTF-8");
         return p;
      }

      public ServletConfig getServletConfig() {
         return new MockServletConfig();
      }
   }
}
