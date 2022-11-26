package weblogic.servlet.internal.session;

import java.util.ArrayList;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.WebAppServletContext;

public abstract class ExtendableSessionContext extends SessionContext {
   public ExtendableSessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
   }

   protected void postCreateSession(ServletRequestImpl req, SessionData session) {
      session.incrementActiveRequestCount();
   }

   public void incrementSessionCount(SessionData session) {
      session.incrementActiveRequestCount();
      this.incrementOpenSessionsCount();
   }

   public void decrementSessionCount(SessionData session) {
      session.decrementActiveRequestCount();
      this.decrementOpenSessionsCount();
   }

   protected void syncSession(SessionData session) {
   }

   public boolean invalidateSessionFromContext(SessionData session, boolean notify) {
      this.servletContext.getServer().getSessionLogin().unregister(session.id, session.getContextPath());
      session.unregisterRuntimeMBean();
      return true;
   }

   boolean invalidateSession(SessionData session, boolean trigger, boolean notify) {
      return false;
   }

   protected void initializeInvalidator() {
   }

   void unregisterExpiredSessions(ArrayList expired) {
   }
}
