package weblogic.jdbc.common.rac.internal;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import oracle.jdbc.OracleConnection;
import oracle.ucp.UniversalConnectionPoolException;
import oracle.ucp.UniversalPooledConnectionStatus;
import oracle.ucp.jdbc.oracle.FailoverablePooledConnection;
import oracle.ucp.jdbc.oracle.FailoverablePooledConnectionHelper;
import weblogic.common.ResourceException;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACModulePool;
import weblogic.jdbc.common.rac.RACPooledConnectionState;

public class RACPooledConnectionStateImpl implements RACPooledConnectionState, FailoverablePooledConnection {
   private RACConnectionEnv connectionEnv;
   private int databaseVersion;
   private RACInstance racInstance;
   private UniversalPooledConnectionStatus upcStatus;
   private boolean namedInstanceConnection;
   private int instanceNumber;
   private Date instanceStartTime;

   RACPooledConnectionStateImpl(RACConnectionEnv hace) throws ResourceException {
      this.upcStatus = UniversalPooledConnectionStatus.STATUS_NORMAL;
      this.connectionEnv = hace;
      Connection conn = this.connectionEnv.getPooledPhysicalConnection();
      this.racInstance = RACModuleUtils.createRACInstance(conn);

      try {
         this.databaseVersion = FailoverablePooledConnectionHelper.getDatabaseVersion(conn);
         this.instanceNumber = FailoverablePooledConnectionHelper.getInstanceNumber(conn);
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("instanceNumber=" + this.instanceNumber);
         }

         Properties prop = FailoverablePooledConnectionHelper.getSessionInfoOnOracleConnection(conn);
         String val = prop.getProperty("AUTH_SC_INSTANCE_START_TIME");
         if (val != null) {
            this.setInstanceStartTime(this.getInstanceStartTime(val));
         }

      } catch (UniversalConnectionPoolException var5) {
         throw new ResourceException(var5);
      } catch (SQLException var6) {
         throw new ResourceException(var6);
      }
   }

   public RACInstance getRACInstance() {
      return this.racInstance;
   }

   public RACConnectionEnv getConnection() {
      return this.connectionEnv;
   }

   public void markConnectionGood() {
      this.upcStatus = UniversalPooledConnectionStatus.STATUS_NORMAL;
   }

   public void markConnectionCloseOnRelease() {
      this.upcStatus = UniversalPooledConnectionStatus.STATUS_CLOSE_ON_RETURN;
   }

   public boolean isConnectionCloseOnRelease() {
      return this.upcStatus == UniversalPooledConnectionStatus.STATUS_CLOSE_ON_RETURN;
   }

   public void abort() throws UniversalConnectionPoolException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("abort()");
      }

      Connection conn = this.connectionEnv.getPooledPhysicalConnection();
      if (conn != null) {
         FailoverablePooledConnectionHelper.abortOracleConnection(conn);
      }

   }

   public void close(boolean isConnectionBorrowed) throws UniversalConnectionPoolException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("close() isConnectionBorrowed=" + isConnectionBorrowed);
      }

      if (this.connectionEnv.isReplayed) {
         this.connectionEnv.isReplayed = false;
      } else {
         this.connectionEnv.isNodeDown = true;
         this.connectionEnv.destroy();
         this.connectionEnv.isNodeDown = false;
         RACModulePool pool = this.connectionEnv.getRACModulePool();

         try {
            pool.removePooledResource(this.connectionEnv);
         } catch (ResourceException var4) {
            throw new UniversalConnectionPoolException(var4);
         }
      }
   }

   public int getDatabaseVersion() {
      return this.databaseVersion;
   }

   public String getDatabase() {
      return this.racInstance.getDatabase();
   }

   public String getHost() {
      return this.racInstance.getHost();
   }

   public String getInstance() {
      return this.racInstance.getInstance();
   }

   public int getInstanceNumber() {
      return this.instanceNumber;
   }

   public String getService() {
      String service = this.racInstance.getService();
      return service == null ? null : service.toLowerCase();
   }

   public UniversalPooledConnectionStatus getStatus() {
      return this.upcStatus;
   }

   public boolean isNamedInstanceConnection() {
      return this.namedInstanceConnection;
   }

   public void setAsNamedInstanceConnection() {
      this.namedInstanceConnection = true;
   }

   public void setStatus(UniversalPooledConnectionStatus status) throws UniversalConnectionPoolException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("setStatus() status=" + status);
      }

      this.upcStatus = status;
   }

   public boolean closeOnRelease() {
      return this.isConnectionCloseOnRelease();
   }

   public boolean isConnectionUsable() {
      OracleConnection oc = (OracleConnection)this.connectionEnv.getPooledPhysicalConnection();
      return oc.isUsable();
   }

   public boolean isStatusValid() {
      return this.upcStatus == UniversalPooledConnectionStatus.STATUS_NORMAL;
   }

   public boolean pingDatabase() throws SQLException {
      OracleConnection oc = (OracleConnection)this.connectionEnv.getPooledPhysicalConnection();
      return oc.pingDatabase() == 0;
   }

   public Date getInstanceStartTime() {
      return this.instanceStartTime;
   }

   private void setInstanceStartTime(Date time) {
      this.instanceStartTime = time;
   }

   private Date getInstanceStartTime(String timestampString) {
      if (timestampString == null) {
         return null;
      } else {
         StringBuffer instStartTimeString = new StringBuffer(timestampString);
         int nanosecStartIndex = instStartTimeString.indexOf(".", 18);
         instStartTimeString.delete(nanosecStartIndex, nanosecStartIndex + 10);
         instStartTimeString.insert(nanosecStartIndex + 1, "GMT");
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
         format.setLenient(false);
         Date ts = null;

         try {
            ts = format.parse(instStartTimeString.toString());
         } catch (ParseException var7) {
            ts = null;
         }

         return ts;
      }
   }

   public String toString() {
      return "id=" + this.connectionEnv.hashCode() + ",instance=" + this.racInstance + ",status=" + this.upcStatus;
   }

   private void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("RACPooledConnectionState(" + this.connectionEnv.hashCode() + "," + this.connectionEnv.getGroups() + "): " + msg);
   }

   public Properties getConnectionInfo() throws UniversalConnectionPoolException {
      Properties connInfo = null;

      try {
         OracleConnection oc = (OracleConnection)this.connectionEnv.getPooledPhysicalConnection();
         connInfo = FailoverablePooledConnectionHelper.getSessionInfoOnOracleConnection(oc);
      } catch (Exception var3) {
         connInfo = null;
      }

      return connInfo;
   }
}
