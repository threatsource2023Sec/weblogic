package org.jboss.weld.module.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHolder {
   private static final ThreadLocal CURRENT_SESSION = new ThreadLocal();

   private SessionHolder() {
   }

   public static void requestInitialized(HttpServletRequest request) {
      CURRENT_SESSION.set(request.getSession(false));
   }

   public static void sessionCreated(HttpSession session) {
      CURRENT_SESSION.set(session);
   }

   public static HttpSession getSessionIfExists() {
      return (HttpSession)CURRENT_SESSION.get();
   }

   public static HttpSession getSession(HttpServletRequest request, boolean create) {
      HttpSession session = (HttpSession)CURRENT_SESSION.get();
      if (create && session == null) {
         request.getSession(true);
         session = (HttpSession)CURRENT_SESSION.get();
      }

      return session;
   }

   public static void clear() {
      CURRENT_SESSION.remove();
   }
}
