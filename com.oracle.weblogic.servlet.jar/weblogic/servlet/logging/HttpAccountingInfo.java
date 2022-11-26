package weblogic.servlet.logging;

import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.http.Cookie;
import weblogic.protocol.ServerChannel;

public interface HttpAccountingInfo {
   Object getAttribute(String var1);

   Enumeration getAttributeNames();

   String getCharacterEncoding();

   int getResponseContentLength();

   long getResponseContentLengthLong();

   String getContentType();

   Locale getLocale();

   Enumeration getLocales();

   String getParameter(String var1);

   Enumeration getParameterNames();

   String[] getParameterValues(String var1);

   String getProtocol();

   String getRemoteAddr();

   String getRemoteHost();

   String getScheme();

   String getServerName();

   int getServerPort();

   boolean isSecure();

   String getAuthType();

   String getContextPath();

   Cookie[] getCookies();

   long getDateHeader(String var1);

   String getHeader(String var1);

   Enumeration getHeaderNames();

   Enumeration getHeaders(String var1);

   int getIntHeader(String var1);

   String getMethod();

   String getPathInfo();

   String getPathTranslated();

   String getQueryString();

   void setRemoteUser(String var1);

   String getRemoteUser();

   String getRequestURI();

   String getRequestedSessionId();

   String getServletPath();

   Principal getUserPrincipal();

   boolean isRequestedSessionIdFromCookie();

   boolean isRequestedSessionIdFromURL();

   boolean isRequestedSessionIdFromUrl();

   boolean isRequestedSessionIdValid();

   ByteBuffer getURIAsBytes();

   long getInvokeTime();

   int getResponseStatusCode();

   String getResponseHeader(String var1);

   ServerChannel getServerChannel();
}
