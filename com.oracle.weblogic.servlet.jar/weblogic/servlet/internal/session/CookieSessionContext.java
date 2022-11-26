package weblogic.servlet.internal.session;

import java.util.ArrayList;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import weblogic.i18n.logging.Loggable;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;

public final class CookieSessionContext extends SessionContext {
   private final String cookieName;

   public CookieSessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
      this.cookieName = this.configMgr.getCookiePersistentStoreCookieName();
   }

   protected void initializeInvalidator() {
   }

   public String getPersistentStoreType() {
      return "cookie";
   }

   String getWLCookieName() {
      return this.cookieName;
   }

   public HttpSession getNewSession(String id, ServletRequestImpl request, ServletResponseImpl response) {
      if (response == null) {
         return null;
      } else {
         response.removeCookie(this.cookieName, "/");
         CookieSessionData sess = new CookieSessionData(id, this, request, response);
         this.incrementOpenSessionsCount();
         return sess;
      }
   }

   public SessionData getSessionInternal(String sessionId, ServletRequestImpl request, ServletResponseImpl response) {
      if (request != null && response != null) {
         sessionId = SessionData.getID(sessionId);
         Cookie cookie = this.getCookie(request);
         if (cookie != null && cookie.getValue() != null && cookie.getValue().length() >= 4 && cookie.getValue().indexOf("|") != -1) {
            try {
               CookieSessionData session = new CookieSessionData(sessionId, this, request, response, cookie);
               return request != null && response != null && !session.isValidForceCheck() ? null : session;
            } catch (Exception var6) {
               cookie.setValue("");
               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public String[] getIdsInternal() {
      return new String[0];
   }

   void unregisterExpiredSessions(ArrayList expired) {
   }

   boolean invalidateSession(SessionData data, boolean trigger) {
      data.remove();
      this.decrementOpenSessionsCount();
      return true;
   }

   public void destroy(boolean serverShutdown) {
   }

   public void sync(SessionData session) {
   }

   public Cookie getCookie(ServletRequestImpl request) {
      Cookie[] cookies = request.getCookies();
      if (cookies == null) {
         return null;
      } else {
         for(int i = cookies.length - 1; i > -1; --i) {
            if (this.cookieName.equals(cookies[i].getName())) {
               if (this.isDebugEnabled()) {
                  Loggable l = HTTPSessionLogger.logFoundWLCookieLoggable(this.cookieName, cookies[i].getValue());
                  DEBUG_SESSIONS.debug(l.getMessage());
               }

               return cookies[i];
            }
         }

         return null;
      }
   }

   public int getNonPersistedSessionCount() {
      return 0;
   }

   public int getCurrOpenSessionsCount() {
      return 0;
   }
}
