package weblogic.servlet.internal.session;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.cluster.wan.PersistenceService;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;

public class WANAsyncSessionContext extends AsyncReplicatedSessionContext {
   private final String contextPath = this.getServletContext().getContextPath();
   private PersistenceService persistenceService = null;

   public WANAsyncSessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
   }

   public String getPersistentStoreType() {
      return "async-replication-across-cluster";
   }

   protected ReplicatedSessionData instantiateReplicatedSessionData(String sessionID, boolean isNew) {
      return new WANAsyncSessionData(sessionID, this, isNew);
   }

   public SessionData getSessionInternal(String incomingid, ServletRequestImpl req, ServletResponseImpl res) {
      SessionData data = super.getSessionInternal(incomingid, req, res);
      String id = SessionData.getID(incomingid);
      if (data == null && req != null && res != null) {
         WANAsyncSessionData wsd = (WANAsyncSessionData)this.createReplicatedSessionData(id, false);
         return this.getPersistenceService().fetchState(id, this.contextPath, wsd) ? wsd : null;
      } else {
         return data;
      }
   }

   public void destroy(boolean serverShutdown) {
      Iterator iterator = this.getOpenSessions().values().iterator();

      while(iterator.hasNext()) {
         WANSessionData data = (WANSessionData)iterator.next();
         this.getPersistenceService().flushUponShutdown(SessionData.getID(data.getIdWithServerInfo()), data.getCreationTime(), data.getContextPath(), data.getMaxInactiveInterval(), data.getLAT(), data.getSessionDiff());
      }

      super.destroy(serverShutdown);
   }

   public int getNonPersistedSessionCount() {
      return 0;
   }

   private PersistenceService getPersistenceService() {
      if (this.persistenceService == null) {
         PersistenceService service = (PersistenceService)GlobalServiceLocator.getServiceLocator().getService(PersistenceService.class, new Annotation[0]);

         assert service != null;

         this.persistenceService = service;
      }

      return this.persistenceService;
   }
}
