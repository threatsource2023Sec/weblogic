package weblogic.servlet.internal.session;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.security.internal.WebAppSecurity;

public final class MemorySessionContext extends SessionContext {
   public MemorySessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
   }

   public HttpSession getNewSession(String id, ServletRequestImpl req, ServletResponseImpl res) {
      this.checkSessionCount();
      MemorySessionData session = new MemorySessionData(id, this, true);
      session.incrementActiveRequestCount();
      this.addSession(session.id, session);
      this.incrementOpenSessionsCount();
      return session;
   }

   public String getPersistentStoreType() {
      return "memory";
   }

   protected void invalidateSecondarySessions() {
   }

   void unregisterExpiredSessions(ArrayList expired) {
   }

   public SessionData getSessionInternal(String sessionId, ServletRequestImpl req, ServletResponseImpl res) {
      sessionId = SessionData.getID(sessionId);
      MemorySessionData s = (MemorySessionData)this.getOpenSession(sessionId);
      if (s == null) {
         return null;
      } else {
         if (req != null && res != null) {
            if (!s.isValidForceCheck()) {
               return null;
            }

            s.incrementActiveRequestCount();
         }

         return !s.isValidForceCheck() ? null : s;
      }
   }

   public int getCurrOpenSessionsCount() {
      return this.getOpenSessions().size();
   }

   boolean invalidateSession(SessionData session, boolean trigger) {
      this.removeSession(session.id);
      session.remove();
      this.decrementOpenSessionsCount();
      return true;
   }

   public void destroy(boolean serverShutdown) {
      super.destroy(serverShutdown);
      if (!this.configMgr.isSaveSessionsOnRedeployEnabled()) {
         String[] ids = this.getIdsInternal();
         SessionCleanupAction action = new SessionCleanupAction(ids);
         Throwable excp = (Throwable)WebAppSecurity.getProvider().getAnonymousSubject().run((PrivilegedAction)action);
         if (excp != null) {
            HTTPSessionLogger.logUnexpectedErrorCleaningUpSessions(this.getServletContext().getLogContext(), excp);
         }

      }
   }

   public void sync(SessionData session) {
      session.decrementActiveRequestCount();
      if (!session.sessionInUse()) {
         session.syncSession();
      }
   }

   public int getNonPersistedSessionCount() {
      return this.getOpenSessions().size();
   }

   class SessionCleanupAction implements PrivilegedAction {
      private final String[] ids;

      SessionCleanupAction(String[] ids) {
         this.ids = ids;
      }

      public Object run() {
         try {
            for(int i = 0; i < this.ids.length && this.ids[i] != null; ++i) {
               SessionData session = MemorySessionContext.this.getSessionInternal(this.ids[i], (ServletRequestImpl)null, (ServletResponseImpl)null);
               if (session != null) {
                  MemorySessionContext.this.invalidateSession(session, false);
               }
            }

            return null;
         } catch (Throwable var3) {
            return var3;
         }
      }
   }
}
