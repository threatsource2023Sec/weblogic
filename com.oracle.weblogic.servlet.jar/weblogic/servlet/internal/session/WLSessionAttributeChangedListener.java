package weblogic.servlet.internal.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class WLSessionAttributeChangedListener implements HttpSessionAttributeListener {
   public final void attributeAdded(HttpSessionBindingEvent se) {
      if (this.debuggable(se)) {
         this.logAttributeEvent("Add new Attribute", se);
         if ("wl_debug_session".equals(se.getName())) {
            this.logStartStopDebugging(true, se.getSession());
         }

      }
   }

   public final void attributeRemoved(HttpSessionBindingEvent se) {
      if (this.debuggable(se)) {
         this.logAttributeEvent("Remove Attribute", se);
         if ("wl_debug_session".equals(se.getName())) {
            this.logStartStopDebugging(false, se.getSession());
         }

      }
   }

   public final void attributeReplaced(HttpSessionBindingEvent se) {
      if (this.debuggable(se)) {
         this.logAttributeEvent("Replace Attribute", se);
      }
   }

   private final boolean debuggable(HttpSessionBindingEvent se) {
      return se.getSession() != null && se.getSession() instanceof SessionData;
   }

   private final void logAttributeEvent(String action, HttpSessionBindingEvent se) {
      SessionData sess = (SessionData)se.getSession();
      if (sess.isDebuggingSession()) {
         sess.logSessionAttributeChanged(action, se.getName(), se.getValue(), sess.getAttribute(se.getName()));
      }
   }

   private final void logStartStopDebugging(boolean start, HttpSession session) {
      String ctxPath = session.getServletContext() == null ? "" : session.getServletContext().getContextPath();
      String action = start ? "Start to debug session" : "Stop debugging session";
      HTTPSessionLogger.logDebugSessionEvent(action, ctxPath, session.getId());
      SessionData.dumpSessionToLog(session);
   }
}
