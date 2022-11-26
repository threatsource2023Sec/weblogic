package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.http.Cookie;

public interface SessionManager {
   Session getSession(Request var1, String var2);

   Session createSession(Request var1);

   String changeSessionId(Request var1, Session var2);

   void configureSessionCookie(Request var1, Cookie var2);

   void setSessionCookieName(String var1);

   String getSessionCookieName();
}
