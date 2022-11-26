package weblogic.servlet.internal.session;

import java.util.Collections;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;

public class AsyncJDBCSessionContext extends JDBCSessionContext {
   private AsyncJDBCPersistenceManager persistenceManager;

   public AsyncJDBCSessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
      this.persistenceManager = new AsyncJDBCPersistenceManager(this.dataSource, this.updateQuery, this.updateIdQuery, this.insertQuery, this.deleteQuery, this.configMgr.getPersistentSessionFlushInterval(), this.configMgr.getPersistentSessionFlushThreshold(), this.configMgr.getPersistentAsyncQueueTimeout());
   }

   public String getPersistentStoreType() {
      return "async-jdbc";
   }

   public HttpSession getNewSession(String sessionid, ServletRequestImpl req, ServletResponseImpl res) {
      JDBCSessionData sess = AsyncJDBCSessionData.newSession(sessionid, this, this.dataSource, this.properties);
      if (sess == null) {
         return null;
      } else {
         sess.incrementActiveRequestCount();
         if (this.cache != Collections.EMPTY_MAP) {
            this.cache.put(sess.id, sess);
         }

         this.incrementOpenSessionsCount();
         return sess;
      }
   }

   public JDBCSessionData getSessionDataFromDB(String sessionid, Properties properties) {
      return AsyncJDBCSessionData.getFromDB(sessionid, this, this.dataSource, properties);
   }

   protected JDBCSessionData createNewData(String sessionID, SessionContext sesContext, DataSource dataSource, Properties properties, boolean isNew) {
      return new AsyncJDBCSessionData(sessionID, sesContext, dataSource, properties, isNew);
   }

   public AsyncJDBCPersistenceManager getPersistenceManager() {
      return this.persistenceManager;
   }

   public void destroy(boolean forceShutdown) {
      this.persistenceManager.blockingFlush();
      super.destroy(forceShutdown);
   }
}
