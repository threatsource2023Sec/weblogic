package weblogic.servlet.http2;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.PushBuilder;
import weblogic.servlet.http2.hpack.HeaderEntry;
import weblogic.servlet.internal.CookieParser;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.internal.session.SessionConfigManager;
import weblogic.utils.http.BytesToString;
import weblogic.utils.http.HttpRequestParseException;
import weblogic.utils.http.RequestParser;

public class PushBuilderImpl implements PushBuilder {
   private ServletRequestImpl request;
   private String sessionId;
   private boolean isSessionFromCookie;
   private boolean isSessionFromPath;
   private String sessionCookieName;
   private final List cookies = new ArrayList();
   private boolean sessionInCookies = false;
   private Map requestHeaders = new HashMap();
   private String method = "GET";
   private String queryString;
   private String path;
   private String inputEncoding;
   private static HashSet EXCLUDE_HEADERS = new HashSet();
   private static final Set DISALLOWED_METHODS = new HashSet();

   public PushBuilderImpl(ServletRequestImpl request) {
      this.request = request;
      this.inputEncoding = request.getInputEncoding();
      this.initRequestHeaders(request);
      this.initSessionInfo(request);
      this.initCookiesInfo(request);
   }

   private void initCookiesInfo(ServletRequestImpl request) {
      if (request.getCookies() != null) {
         Cookie[] var2 = request.getCookies();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Cookie requestCookie = var2[var4];
            this.cookies.add(requestCookie);
         }
      }

      Iterator var7 = request.getResponse().getCookies().iterator();

      while(true) {
         while(var7.hasNext()) {
            Object o = var7.next();
            Cookie responseCookie = (Cookie)o;
            if (responseCookie.getMaxAge() < 0) {
               Iterator cookieIterator = this.cookies.iterator();

               while(cookieIterator.hasNext()) {
                  Cookie cookie = (Cookie)cookieIterator.next();
                  if (cookie.getName().equals(responseCookie.getName())) {
                     cookieIterator.remove();
                  }
               }
            } else {
               this.cookies.add(new Cookie(responseCookie.getName(), responseCookie.getValue()));
            }
         }

         return;
      }
   }

   private void initSessionInfo(ServletRequestImpl request) {
      this.sessionCookieName = request.getContext().getSessionContext().getConfigMgr().getCookieName();
      HttpSession session = request.getSession(false);
      if (session != null) {
         this.sessionId = session.getId();
      }

      if (this.sessionId == null) {
         this.sessionId = request.getRequestedSessionId();
      }

      if (!request.isRequestedSessionIdFromCookie() && !request.isRequestedSessionIdFromURL() && this.sessionId != null) {
         Set sessionTrackingModes = request.getServletContext().getEffectiveSessionTrackingModes();
         this.isSessionFromCookie = sessionTrackingModes.contains(SessionTrackingMode.COOKIE);
         this.isSessionFromPath = sessionTrackingModes.contains(SessionTrackingMode.URL);
      } else {
         this.isSessionFromCookie = request.isRequestedSessionIdFromCookie();
         this.isSessionFromPath = request.isRequestedSessionIdFromURL();
      }

   }

   private void initRequestHeaders(ServletRequestImpl request) {
      ArrayList headerNames = request.getInputHelper().getRequestParser().getHeaderNames();
      ArrayList headerValues = request.getInputHelper().getRequestParser().getHeaderValues();

      for(int i = 0; i < headerNames.size(); ++i) {
         String name = ((String)headerNames.get(i)).toLowerCase();
         if (!EXCLUDE_HEADERS.contains(name)) {
            List values = (List)this.requestHeaders.get(name);
            if (values == null) {
               values = new ArrayList();
            }

            ((List)values).add(BytesToString.newString((byte[])((byte[])headerValues.get(i)), this.inputEncoding));
            this.requestHeaders.put(name, values);
         }

         if ("Authorization".equalsIgnoreCase(name) && request.getUserPrincipal() != null) {
            List auth = new ArrayList();
            auth.add(BytesToString.newString((byte[])((byte[])headerValues.get(i)), this.inputEncoding));
            this.requestHeaders.put("Authorization", auth);
         }
      }

      List referer = new ArrayList();
      referer.add(this.composeRefererHeader(request));
      this.requestHeaders.put("Referer", referer);
   }

   public PushBuilder method(String method) {
      if (method == null) {
         throw new NullPointerException("The method for Push Builder must not be Null.");
      } else {
         String upperMethod = method.trim().toUpperCase();
         if (!upperMethod.isEmpty() && !DISALLOWED_METHODS.contains(upperMethod)) {
            this.method = method;
            return this;
         } else {
            throw new IllegalArgumentException("The method must be not the empty String, or any non-cacheable or unsafe methods defined in RFC 7231");
         }
      }
   }

   public PushBuilder queryString(String queryString) {
      this.queryString = queryString;
      return this;
   }

   public PushBuilder sessionId(String sessionId) {
      this.sessionId = sessionId;
      return this;
   }

   public PushBuilder setHeader(String name, String value) {
      List values = (List)this.requestHeaders.get(name);
      if (values == null) {
         values = new ArrayList();
         this.requestHeaders.put(name, values);
      } else {
         ((List)values).clear();
      }

      ((List)values).add(value);
      return this;
   }

   public PushBuilder addHeader(String name, String value) {
      List values = (List)this.requestHeaders.get(name);
      if (values == null) {
         values = new ArrayList();
         this.requestHeaders.put(name, values);
      }

      ((List)values).add(value);
      return this;
   }

   public PushBuilder removeHeader(String name) {
      this.requestHeaders.remove(name);
      return this;
   }

   public PushBuilder path(String path) {
      if (path.startsWith("/")) {
         this.path = path;
      } else {
         String contextPath = this.request.getContextPath();
         StringBuilder sb = new StringBuilder(contextPath.length() + path.length() + 1);
         sb.append(contextPath).append('/').append(path);
         this.path = sb.toString();
      }

      return this;
   }

   public void push() {
      if (this.path == null) {
         throw new IllegalStateException("path() should be call before push()");
      } else {
         Stream stream = this.request.getConnection().getConnectionHandler().getStream();
         if (stream.isPushSupported()) {
            Stream pushStream = null;

            try {
               pushStream = stream.getHTTP2Connection().getStreamManager().createLocalStream();
            } catch (StreamException var12) {
               return;
            }

            ServletRequestImpl pushReq = ((StreamImpl)pushStream).getRequest();
            RequestParser parser = pushReq.getInputHelper().getRequestParser();
            parser.setMethod(this.method);
            parser.setScheme(this.request.getScheme());
            Iterator var5 = this.requestHeaders.entrySet().iterator();

            Iterator cookiesInBytes;
            while(var5.hasNext()) {
               Map.Entry entry = (Map.Entry)var5.next();
               cookiesInBytes = ((List)entry.getValue()).iterator();

               while(cookiesInBytes.hasNext()) {
                  String value = (String)cookiesInBytes.next();

                  try {
                     parser.addHeader((String)entry.getKey(), value.getBytes(this.inputEncoding));
                  } catch (UnsupportedEncodingException var11) {
                  }
               }
            }

            byte[] pushPathBytes = this.composePushPath(parser);
            if (pushPathBytes != null) {
               List headers = new ArrayList();

               try {
                  headers.add(new HeaderEntry(":method", this.request.getMethod().getBytes(this.inputEncoding)));
                  headers.add(new HeaderEntry(":scheme", this.request.getScheme().getBytes(this.inputEncoding)));
                  headers.add(new HeaderEntry(":path", pushPathBytes));
                  if (this.request.getScheme().equalsIgnoreCase("http") && this.request.getServerPort() == 80 || this.request.getScheme().equalsIgnoreCase("https") && this.request.getServerPort() == 443) {
                     headers.add(new HeaderEntry(":authority", this.request.getServerName().getBytes(this.inputEncoding)));
                  } else {
                     headers.add(new HeaderEntry(":authority", (this.request.getServerName() + ":" + this.request.getServerPort()).getBytes(this.inputEncoding)));
                  }

                  cookiesInBytes = this.requestHeaders.entrySet().iterator();

                  while(cookiesInBytes.hasNext()) {
                     Map.Entry e = (Map.Entry)cookiesInBytes.next();
                     Iterator var9 = ((List)e.getValue()).iterator();

                     while(var9.hasNext()) {
                        String value = (String)var9.next();
                        headers.add(new HeaderEntry(((String)e.getKey()).toLowerCase(), value.getBytes(this.inputEncoding)));
                     }
                  }

                  cookiesInBytes = null;
                  byte[] cookiesInBytes = this.buildCookies().getBytes(this.inputEncoding);
                  if (cookiesInBytes != null && cookiesInBytes.length > 0) {
                     parser.addHeader("Cookie", cookiesInBytes);
                     headers.add(new HeaderEntry("cookie", cookiesInBytes));
                  }
               } catch (UnsupportedEncodingException var13) {
               }

               stream.sendPushPromise(headers, pushStream.getId());
               pushStream.sendPushPromise();
               pushStream.handleOnContainer();
               this.path = null;
               this.requestHeaders.remove("if-none-match");
               this.requestHeaders.remove("if-modified-since");
            }
         }
      }
   }

   private String buildCookies() {
      WebAppServletContext context = this.request.getContext();
      SessionConfigManager scm = context == null ? null : context.getSessionContext().getConfigMgr();
      StringBuilder cookiesBuilder = new StringBuilder();
      if (this.cookies.size() > 0) {
         for(int i = 0; i < this.cookies.size(); ++i) {
            Cookie cook = (Cookie)this.cookies.get(i);
            boolean enableHttpOnlyForCookie = cook.isHttpOnly();
            if (scm == null && (cook.getName().equals("JSESSIONID") || cook.getName().equals("_WL_AUTHCOOKIE_JSESSIONID"))) {
               enableHttpOnlyForCookie = true;
            } else if (scm != null && (cook.getName().equals(scm.getCookieName()) || cook.getName().equals(scm.getWLSAuthCookieName()))) {
               enableHttpOnlyForCookie = scm.isCookieHttpOnly();
            }

            if (context.isInternalApp() && ServletResponseImpl.getNoHttpOnlyInternalApps().contains(context.getContextPath())) {
               enableHttpOnlyForCookie = false;
            }

            String c = CookieParser.formatCookie(cook, enableHttpOnlyForCookie);
            if (i != 0) {
               cookiesBuilder.append(';');
            }

            cookiesBuilder.append(c);
         }
      }

      return cookiesBuilder.toString();
   }

   private byte[] composePushPath(RequestParser parser) {
      int queryIndex = this.path.indexOf(63);
      String queryStringInPath = null;
      String pushPath;
      if (queryIndex > -1) {
         pushPath = this.path.substring(0, queryIndex);
         if (queryIndex + 1 < this.path.length()) {
            queryStringInPath = this.path.substring(queryIndex + 1);
         }
      } else {
         pushPath = this.path;
      }

      String pushQueryString = null;
      boolean isQueryStringSet = this.queryString != null && !this.queryString.trim().isEmpty();
      if (isQueryStringSet && queryStringInPath != null) {
         pushQueryString = queryStringInPath + '&' + this.queryString;
      } else if (queryStringInPath != null) {
         pushQueryString = queryStringInPath;
      } else if (isQueryStringSet) {
         pushQueryString = this.queryString;
      }

      if (this.sessionId != null) {
         if (this.isSessionFromPath) {
            pushPath = pushPath + ";" + this.sessionCookieName + "=" + this.sessionId;
         }

         if (this.isSessionFromCookie && !this.sessionInCookies) {
            this.cookies.add(new Cookie(this.sessionCookieName, this.sessionId));
            this.sessionInCookies = true;
         }
      }

      if (pushQueryString != null) {
         pushPath = pushPath + '?' + pushQueryString;
      }

      try {
         byte[] pushPathBytes = pushPath.getBytes(this.inputEncoding);
         parser.parse(pushPathBytes, pushPathBytes.length);
         return pushPathBytes;
      } catch (UnsupportedEncodingException var8) {
         return null;
      } catch (HttpRequestParseException var9) {
         return null;
      }
   }

   private String composeRefererHeader(ServletRequestImpl request) {
      StringBuffer referer = request.getRequestURL();
      if (request.getQueryString() != null) {
         referer.append('?').append(request.getQueryString());
      }

      return referer.toString();
   }

   public String getMethod() {
      return this.method;
   }

   public String getQueryString() {
      return this.queryString;
   }

   public String getSessionId() {
      return this.sessionId;
   }

   public Set getHeaderNames() {
      return Collections.unmodifiableSet(this.requestHeaders.keySet());
   }

   public String getHeader(String name) {
      if ("Cookie".equalsIgnoreCase(name)) {
         return this.buildCookies();
      } else {
         List values = (List)this.requestHeaders.get(name);
         return values == null ? null : (String)values.get(0);
      }
   }

   public String getPath() {
      return this.path;
   }

   private String converterHeaderName(Map mapping, String name) {
      return mapping.containsKey(name) ? (String)mapping.get(name) : name;
   }

   static {
      EXCLUDE_HEADERS.add("if-match");
      EXCLUDE_HEADERS.add("if-none-match");
      EXCLUDE_HEADERS.add("if-modified-since");
      EXCLUDE_HEADERS.add("if-unmodified-since");
      EXCLUDE_HEADERS.add("if-range");
      EXCLUDE_HEADERS.add("range");
      EXCLUDE_HEADERS.add("expect");
      EXCLUDE_HEADERS.add("authorization");
      EXCLUDE_HEADERS.add("referer");
      EXCLUDE_HEADERS.add("cookie");
      DISALLOWED_METHODS.add("POST");
      DISALLOWED_METHODS.add("PUT");
      DISALLOWED_METHODS.add("DELETE");
      DISALLOWED_METHODS.add("CONNECT");
      DISALLOWED_METHODS.add("OPTIONS");
      DISALLOWED_METHODS.add("TRACE");
   }
}
