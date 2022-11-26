package org.glassfish.grizzly.http.server.http2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Session;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.MimeHeaders;

public final class PushBuilder {
   private static final Header[] REMOVE_HEADERS;
   private static final Header[] CONDITIONAL_HEADERS;
   String method;
   String queryString;
   String sessionId;
   MimeHeaders headers;
   String path;
   Request request;
   boolean sessionFromURL;
   List cookies;

   public PushBuilder(Request request) {
      this.method = Method.GET.getMethodString();
      this.request = request;
      this.headers = new MimeHeaders();
      this.headers.copyFrom(request.getRequest().getHeaders());
      int i = 0;

      for(int len = REMOVE_HEADERS.length; i < len; ++i) {
         this.headers.removeHeader(REMOVE_HEADERS[i]);
      }

      this.headers.setValue(Header.Referer).setString(this.composeReferrerHeader(request));
      Session session = request.getSession(false);
      if (session != null) {
         this.sessionId = session.getIdInternal();
      }

      if (this.sessionId == null) {
         this.sessionId = request.getRequestedSessionId();
      }

      this.sessionFromURL = request.isRequestedSessionIdFromURL();
      Cookie[] requestCookies = request.getCookies();
      if (requestCookies != null) {
         this.cookies = new ArrayList(Arrays.asList(requestCookies));
      }

      Cookie[] responseCookies = request.getResponse().getCookies();
      int i;
      int len;
      Cookie c;
      if (responseCookies != null) {
         if (this.cookies == null) {
            this.cookies = new ArrayList(responseCookies.length);
         }

         i = 0;

         for(len = responseCookies.length; i < len; ++i) {
            c = responseCookies[i];
            if (c.getMaxAge() > 0) {
               this.cookies.add(new Cookie(c.getName(), c.getValue()));
            } else {
               int j = 0;

               for(int jlen = this.cookies.size(); j < jlen; ++j) {
                  if (((Cookie)this.cookies.get(j)).getName().equals(c.getName())) {
                     this.cookies.remove(j);
                  }
               }
            }
         }
      }

      if (this.cookies != null && !this.cookies.isEmpty()) {
         i = 0;

         for(len = this.cookies.size(); i < len; ++i) {
            c = (Cookie)this.cookies.get(i);
            this.headers.addValue(Header.Cookie).setString(c.asClientCookieString());
         }
      }

   }

   public PushBuilder method(String method) {
      if (method == null) {
         throw new NullPointerException();
      } else if (!Method.POST.getMethodString().equals(method) && !Method.PUT.getMethodString().equals(method) && !Method.DELETE.getMethodString().equals(method) && !Method.CONNECT.getMethodString().equals(method) && !Method.OPTIONS.getMethodString().equals(method) && !Method.TRACE.getMethodString().equals(method)) {
         this.method = method;
         return this;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public PushBuilder queryString(String queryString) {
      this.queryString = validate(queryString);
      return this;
   }

   public PushBuilder sessionId(String sessionId) {
      this.sessionId = validate(sessionId);
      return this;
   }

   public PushBuilder setHeader(String name, String value) {
      if (nameAndValueValid(name, value)) {
         this.headers.setValue(name).setString(value);
      }

      return this;
   }

   public PushBuilder setHeader(Header name, String value) {
      if (name != null && validValue(value)) {
         this.headers.setValue(name).setString(value);
      }

      return this;
   }

   public PushBuilder addHeader(String name, String value) {
      if (nameAndValueValid(name, value)) {
         this.headers.addValue(name).setString(value);
      }

      return this;
   }

   public PushBuilder addHeader(Header name, String value) {
      if (name != null && validValue(value)) {
         this.headers.addValue(name).setString(value);
      }

      return this;
   }

   public PushBuilder removeHeader(String name) {
      if (validValue(name) && !Header.Referer.getLowerCase().equals(name.toLowerCase())) {
         this.headers.removeHeader(name);
      }

      return this;
   }

   public PushBuilder removeHeader(Header name) {
      if (name != null && Header.Referer != name) {
         this.headers.removeHeader(name);
      }

      return this;
   }

   public PushBuilder path(String path) {
      this.path = validate(path);
      return this;
   }

   public void push() {
      if (this.path == null) {
         throw new IllegalStateException();
      } else if (this.request.isPushEnabled()) {
         String pathLocal = this.path.charAt(0) == '/' ? this.path : this.request.getContextPath() + '/' + this.path;
         if (this.queryString != null) {
            pathLocal = pathLocal + (pathLocal.indexOf(63) != -1 ? '&' + this.queryString : '?' + this.queryString);
         }

         if (this.sessionId != null) {
            if (this.sessionFromURL) {
               pathLocal = pathLocal + ';' + this.request.getSessionCookieName() + '=' + this.sessionId;
            } else {
               this.headers.addValue(Header.Cookie).setString((new Cookie(this.request.getSessionCookieName(), this.sessionId)).asClientCookieString());
            }
         }

         this.path = pathLocal;
         if (!this.request.getContext().getConnection().isOpen()) {
            throw new UncheckedIOException("Unable to push: connection closed", new IOException());
         } else {
            this.request.getContext().notifyDownstream(PushEvent.create(this));
            this.path = null;
            int i = 0;

            for(int len = CONDITIONAL_HEADERS.length; i < len; ++i) {
               this.headers.removeHeader(CONDITIONAL_HEADERS[i]);
            }

         }
      }
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

   public Iterable getHeaderNames() {
      return this.headers.names();
   }

   public String getHeader(String name) {
      return this.headers.getHeader(name);
   }

   public String getPath() {
      return this.path;
   }

   private static boolean nameAndValueValid(String name, String value) {
      return validValue(name) && validValue(value);
   }

   private static boolean validValue(String value) {
      return value != null && !value.isEmpty();
   }

   private static String validate(String value) {
      return validValue(value) ? value : null;
   }

   private String composeReferrerHeader(Request request) {
      StringBuilder sb = new StringBuilder(64);
      String queryString = request.getQueryString();
      sb.append(request.getRequestURL());
      if (queryString != null) {
         sb.append('?').append(queryString);
      }

      return sb.toString();
   }

   static {
      REMOVE_HEADERS = new Header[]{Header.Cookie, Header.ETag, Header.IfModifiedSince, Header.IfNoneMatch, Header.IfRange, Header.IfUnmodifiedSince, Header.IfMatch, Header.LastModified, Header.Referer, Header.AcceptRanges, Header.Range, Header.AcceptRanges, Header.ContentRange, Header.Authorization, Header.ProxyAuthenticate, Header.ProxyAuthorization, Header.WWWAuthenticate};
      CONDITIONAL_HEADERS = new Header[]{Header.IfModifiedSince, Header.IfNoneMatch, Header.IfRange, Header.IfUnmodifiedSince, Header.IfMatch};
   }
}
