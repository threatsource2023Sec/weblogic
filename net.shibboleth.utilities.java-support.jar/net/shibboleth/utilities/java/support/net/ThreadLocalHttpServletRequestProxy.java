package net.shibboleth.utilities.java.support.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class ThreadLocalHttpServletRequestProxy implements HttpServletRequest {
   public Object getAttribute(String name) {
      return this.getCurrent().getAttribute(name);
   }

   public Enumeration getAttributeNames() {
      return this.getCurrent().getAttributeNames();
   }

   public String getCharacterEncoding() {
      return this.getCurrent().getCharacterEncoding();
   }

   public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
      this.getCurrent().setCharacterEncoding(env);
   }

   public int getContentLength() {
      return this.getCurrent().getContentLength();
   }

   public String getContentType() {
      return this.getCurrent().getContentType();
   }

   public ServletInputStream getInputStream() throws IOException {
      return this.getCurrent().getInputStream();
   }

   public String getParameter(String name) {
      return this.getCurrent().getParameter(name);
   }

   public Enumeration getParameterNames() {
      return this.getCurrent().getParameterNames();
   }

   public String[] getParameterValues(String name) {
      return this.getCurrent().getParameterValues(name);
   }

   public Map getParameterMap() {
      return this.getCurrent().getParameterMap();
   }

   public String getProtocol() {
      return this.getCurrent().getProtocol();
   }

   public String getScheme() {
      return this.getCurrent().getScheme();
   }

   public String getServerName() {
      return this.getCurrent().getServerName();
   }

   public int getServerPort() {
      return this.getCurrent().getServerPort();
   }

   public BufferedReader getReader() throws IOException {
      return this.getCurrent().getReader();
   }

   public String getRemoteAddr() {
      return this.getCurrent().getRemoteAddr();
   }

   public String getRemoteHost() {
      return this.getCurrent().getRemoteHost();
   }

   public void setAttribute(String name, Object o) {
      this.getCurrent().setAttribute(name, o);
   }

   public void removeAttribute(String name) {
      this.getCurrent().removeAttribute(name);
   }

   public Locale getLocale() {
      return this.getCurrent().getLocale();
   }

   public Enumeration getLocales() {
      return this.getCurrent().getLocales();
   }

   public boolean isSecure() {
      return this.getCurrent().isSecure();
   }

   public RequestDispatcher getRequestDispatcher(String path) {
      return this.getCurrent().getRequestDispatcher(path);
   }

   public String getRealPath(String path) {
      return this.getCurrent().getRealPath(path);
   }

   public int getRemotePort() {
      return this.getCurrent().getRemotePort();
   }

   public String getLocalName() {
      return this.getCurrent().getLocalName();
   }

   public String getLocalAddr() {
      return this.getCurrent().getLocalAddr();
   }

   public int getLocalPort() {
      return this.getCurrent().getLocalPort();
   }

   public String getAuthType() {
      return this.getCurrent().getAuthType();
   }

   public Cookie[] getCookies() {
      return this.getCurrent().getCookies();
   }

   public long getDateHeader(String name) {
      return this.getCurrent().getDateHeader(name);
   }

   public String getHeader(String name) {
      return this.getCurrent().getHeader(name);
   }

   public Enumeration getHeaders(String name) {
      return this.getCurrent().getHeaders(name);
   }

   public Enumeration getHeaderNames() {
      return this.getCurrent().getHeaderNames();
   }

   public int getIntHeader(String name) {
      return this.getCurrent().getIntHeader(name);
   }

   public String getMethod() {
      return this.getCurrent().getMethod();
   }

   public String getPathInfo() {
      return this.getCurrent().getPathInfo();
   }

   public String getPathTranslated() {
      return this.getCurrent().getPathTranslated();
   }

   public String getContextPath() {
      return this.getCurrent().getContextPath();
   }

   public String getQueryString() {
      return this.getCurrent().getQueryString();
   }

   public String getRemoteUser() {
      return this.getCurrent().getRemoteUser();
   }

   public boolean isUserInRole(String role) {
      return this.getCurrent().isUserInRole(role);
   }

   public Principal getUserPrincipal() {
      return this.getCurrent().getUserPrincipal();
   }

   public String getRequestedSessionId() {
      return this.getCurrent().getRequestedSessionId();
   }

   public String getRequestURI() {
      return this.getCurrent().getRequestURI();
   }

   public StringBuffer getRequestURL() {
      return this.getCurrent().getRequestURL();
   }

   public String getServletPath() {
      return this.getCurrent().getServletPath();
   }

   public HttpSession getSession(boolean create) {
      return this.getCurrent().getSession(create);
   }

   public HttpSession getSession() {
      return this.getCurrent().getSession();
   }

   public boolean isRequestedSessionIdValid() {
      return this.getCurrent().isRequestedSessionIdValid();
   }

   public boolean isRequestedSessionIdFromCookie() {
      return this.getCurrent().isRequestedSessionIdFromCookie();
   }

   public boolean isRequestedSessionIdFromURL() {
      return this.getCurrent().isRequestedSessionIdFromURL();
   }

   public boolean isRequestedSessionIdFromUrl() {
      return this.getCurrent().isRequestedSessionIdFromUrl();
   }

   public ServletContext getServletContext() {
      return this.getCurrent().getServletContext();
   }

   public AsyncContext startAsync() {
      return this.getCurrent().startAsync();
   }

   public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
      return this.getCurrent().startAsync(servletRequest, servletResponse);
   }

   public boolean isAsyncStarted() {
      return this.getCurrent().isAsyncStarted();
   }

   public boolean isAsyncSupported() {
      return this.getCurrent().isAsyncSupported();
   }

   public AsyncContext getAsyncContext() {
      return this.getCurrent().getAsyncContext();
   }

   public DispatcherType getDispatcherType() {
      return this.getCurrent().getDispatcherType();
   }

   public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
      return this.getCurrent().authenticate(response);
   }

   public void login(String username, String password) throws ServletException {
      this.getCurrent().login(username, password);
   }

   public void logout() throws ServletException {
      this.getCurrent().logout();
   }

   public Collection getParts() throws IOException, ServletException {
      return this.getCurrent().getParts();
   }

   public Part getPart(String name) throws IOException, ServletException {
      return this.getCurrent().getPart(name);
   }

   protected HttpServletRequest getCurrent() {
      return (HttpServletRequest)Constraint.isNotNull(HttpServletRequestResponseContext.getRequest(), "Current HttpServletRequest has not been loaded via HttpServletRequestResponseContext");
   }
}
