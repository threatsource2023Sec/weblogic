package org.glassfish.grizzly.servlet;

import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.server.DefaultSessionManager;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Session;
import org.glassfish.grizzly.http.server.SessionManager;

public class ServletSessionManager implements SessionManager {
   private final SessionManager defaultManager;
   private String sessionCookieName;

   public static SessionManager instance() {
      return ServletSessionManager.LazyHolder.INSTANCE;
   }

   private ServletSessionManager() {
      this.defaultManager = DefaultSessionManager.instance();
      this.sessionCookieName = "JSESSIONID";
   }

   public Session getSession(Request request, String requestedSessionId) {
      return this.defaultManager.getSession(request, requestedSessionId);
   }

   public Session createSession(Request request) {
      return this.defaultManager.createSession(request);
   }

   public String changeSessionId(Request request, Session session) {
      return this.defaultManager.changeSessionId(request, session);
   }

   public void configureSessionCookie(Request request, Cookie cookie) {
      this.defaultManager.configureSessionCookie(request, cookie);
      HttpServletRequestImpl servletRequest = ServletHandler.getServletRequest(request);

      assert servletRequest != null;

      javax.servlet.SessionCookieConfig cookieConfig = servletRequest.getContextImpl().getSessionCookieConfig();
      if (cookieConfig.getDomain() != null) {
         cookie.setDomain(cookieConfig.getDomain());
      }

      if (cookieConfig.getPath() != null) {
         cookie.setPath(cookieConfig.getPath());
      }

      if (cookieConfig.getComment() != null) {
         cookie.setVersion(1);
         cookie.setComment(cookieConfig.getComment());
      }

      cookie.setSecure(cookieConfig.isSecure());
      cookie.setHttpOnly(cookieConfig.isHttpOnly());
      cookie.setMaxAge(cookieConfig.getMaxAge());
   }

   public void setSessionCookieName(String name) {
      if (name != null && !name.isEmpty()) {
         this.sessionCookieName = name;
      }

   }

   public String getSessionCookieName() {
      return this.sessionCookieName;
   }

   // $FF: synthetic method
   ServletSessionManager(Object x0) {
      this();
   }

   private static class LazyHolder {
      private static final ServletSessionManager INSTANCE = new ServletSessionManager();
   }
}
