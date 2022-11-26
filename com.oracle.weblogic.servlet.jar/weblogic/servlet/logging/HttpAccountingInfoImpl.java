package weblogic.servlet.logging;

import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.http.Cookie;
import weblogic.protocol.ServerChannel;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.spi.SubjectHandle;

public final class HttpAccountingInfoImpl implements HttpAccountingInfo {
   private final ServletRequestImpl request;
   private String remoteUser = null;
   private String requestedSessionId = null;
   private Principal userPrincipal = null;
   private boolean isRequestedSessionIdValid;
   private long invocationTime;

   public HttpAccountingInfoImpl(ServletRequestImpl request) {
      this.request = request;
   }

   public void init(ServletRequestImpl req, SubjectHandle subject) {
      if (subject != null) {
         this.userPrincipal = subject.getPrincipal();
      }

      this.requestedSessionId = req.getRequestedSessionId();
      this.isRequestedSessionIdValid = req.isRequestedSessionIdValid();
   }

   public void clear() {
      this.remoteUser = null;
      this.userPrincipal = null;
      this.requestedSessionId = null;
      this.isRequestedSessionIdValid = false;
      this.invocationTime = -1L;
   }

   public Object getAttribute(String name) {
      return this.request.getAttribute(name);
   }

   public Enumeration getAttributeNames() {
      return this.request.getAttributeNames();
   }

   public String getCharacterEncoding() {
      return this.request.getCharacterEncoding();
   }

   public int getResponseContentLength() {
      return this.request.getResponse().getContentLength();
   }

   public long getResponseContentLengthLong() {
      return this.request.getResponse().getContentLengthLong();
   }

   public String getContentType() {
      return this.request.getContentType();
   }

   public Locale getLocale() {
      return this.request.getLocale();
   }

   public Enumeration getLocales() {
      return this.request.getLocales();
   }

   public String getParameter(String name) {
      return this.request.getParameter(name);
   }

   public Enumeration getParameterNames() {
      return this.request.getParameterNames();
   }

   public String[] getParameterValues(String name) {
      return this.request.getParameterValues(name);
   }

   public String getProtocol() {
      return this.request.getProtocol();
   }

   public String getRemoteAddr() {
      return this.request.getRemoteAddr();
   }

   public String getRemoteHost() {
      return this.request.getRemoteHost();
   }

   public String getScheme() {
      return this.request.getScheme();
   }

   public String getServerName() {
      return this.request.getServerName();
   }

   public int getServerPort() {
      return this.request.getServerPort();
   }

   public boolean isSecure() {
      return this.request.isSecure();
   }

   public String getAuthType() {
      return this.request.getAuthType();
   }

   public String getContextPath() {
      return this.request.getContextPath();
   }

   public Cookie[] getCookies() {
      return this.request.getCookies();
   }

   public long getDateHeader(String name) {
      return this.request.getDateHeader(name);
   }

   public String getHeader(String name) {
      return this.request.getHeader(name);
   }

   public Enumeration getHeaderNames() {
      return this.request.getHeaderNames();
   }

   public Enumeration getHeaders(String name) {
      return this.request.getHeaders(name);
   }

   public int getIntHeader(String name) {
      return this.request.getIntHeader(name);
   }

   public String getMethod() {
      return this.request.getMethod();
   }

   public String getPathInfo() {
      return this.request.getPathInfo();
   }

   public String getPathTranslated() {
      return this.request.getPathTranslated();
   }

   public String getQueryString() {
      return this.request.getQueryString();
   }

   public void setRemoteUser(String username) {
      this.remoteUser = username;
   }

   public String getRemoteUser() {
      return this.remoteUser;
   }

   public String getRequestURI() {
      return this.request.getRequestURI();
   }

   public String getRequestedSessionId() {
      return this.requestedSessionId;
   }

   public String getServletPath() {
      return this.request.getServletPath();
   }

   public Principal getUserPrincipal() {
      return this.userPrincipal;
   }

   public boolean isRequestedSessionIdFromCookie() {
      return this.request.isRequestedSessionIdFromCookie();
   }

   public boolean isRequestedSessionIdFromURL() {
      return this.request.isRequestedSessionIdFromURL();
   }

   public boolean isRequestedSessionIdFromUrl() {
      return this.request.isRequestedSessionIdFromUrl();
   }

   public boolean isRequestedSessionIdValid() {
      return this.isRequestedSessionIdValid;
   }

   public ByteBuffer getURIAsBytes() {
      return this.request.getInputHelper().getRequestParser().getOriginalRequestUriBytes();
   }

   public long getInvokeTime() {
      return this.invocationTime;
   }

   public void setInvokeTime(long time) {
      this.invocationTime = time;
   }

   public int getResponseStatusCode() {
      return this.request.getResponse().getStatus();
   }

   public String getResponseHeader(String name) {
      return this.request.getResponse().getHeader(name);
   }

   public ServerChannel getServerChannel() {
      return this.request.getServerChannel();
   }
}
