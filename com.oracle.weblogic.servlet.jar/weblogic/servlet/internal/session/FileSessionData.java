package weblogic.servlet.internal.session;

import java.io.Externalizable;

public final class FileSessionData extends SessionData implements Externalizable {
   static final long serialVersionUID = -1312849241262491947L;

   public FileSessionData() {
   }

   public FileSessionData(String sessionid, SessionContext context, boolean isNew) {
      super(sessionid, context, isNew);
      if (isNew) {
         this.getWebAppServletContext().getEventsManager().notifySessionLifetimeEvent(this, true);
      }

   }

   protected void logTransientAttributeError(String name) {
      HTTPSessionLogger.logTransientFileAttributeError(this.getWebAppServletContext().getLogContext(), name, this.getId());
   }

   void syncSession() {
      try {
         super.syncSession();
         this.setNeedToSyncNewSessionId(false);
      } finally {
         this.postInvalidate();
      }

   }
}
