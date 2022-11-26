package weblogic.servlet.internal.session;

public final class MemorySessionData extends SessionData {
   public MemorySessionData() {
   }

   public MemorySessionData(String sessionid, SessionContext context, boolean isNew) {
      super(sessionid, context, isNew);
      if (isNew) {
         this.getWebAppServletContext().getEventsManager().notifySessionLifetimeEvent(this, true);
      }

   }

   protected void logTransientAttributeError(String name) {
      if (this.getContext().getConfigMgr().isSaveSessionsOnRedeployEnabled()) {
         HTTPSessionLogger.logTransientMemoryAttributeError(this.getWebAppServletContext().getLogContext(), name, this.getId());
      }

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
