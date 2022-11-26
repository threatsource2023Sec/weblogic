package weblogic.jdbc.common.internal;

import com.bea.logging.LogMessageFormatter;
import com.bea.logging.LoggingSupplementalAttribute;
import com.bea.logging.RotatingFileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.ResourcePoolProfiler;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.extensions.ProfileDataRecord;
import weblogic.jdbc.wrapper.Statement;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtils;

public final class ConnectionPoolProfiler implements ResourcePoolProfiler {
   private int NUM_TYPES = 11;
   private int TYPE_CONN_USAGE = 0;
   private int TYPE_CONN_WAIT = 1;
   private int TYPE_CONN_LEAK = 2;
   private int TYPE_CONN_RESV_FAIL = 3;
   private int TYPE_STMT_CACHE_ENTRY = 4;
   private int TYPE_STMT_USAGE = 5;
   private int TYPE_CONN_LAST_USAGE = 6;
   private int TYPE_CONN_MT_USAGE = 7;
   private int TYPE_CONN_UNWRAP_USAGE = 8;
   private int TYPE_CONN_LOCALTX_LEAK = 9;
   private int TYPE_CLOSED_USAGE = 10;
   private ConnectionPool pool;
   private HashMap[] profileData;
   private int profileType = 0;
   private int profileConnectionLeakTimeoutSeconds;
   private BufferedWriter bufferedWriter = null;
   private RotatingFileOutputStream rotatingFileOutputStream = null;
   private final String START = "####";
   private static Object logSyncObject = new Object();

   public ConnectionPoolProfiler(ConnectionPool pool) {
      this.pool = pool;
      this.profileData = new HashMap[this.NUM_TYPES];
      this.profileData[this.TYPE_CONN_USAGE] = new HashMap();
      this.profileData[this.TYPE_CONN_WAIT] = new HashMap();
      this.profileData[this.TYPE_CONN_LEAK] = new HashMap();
      this.profileData[this.TYPE_CONN_RESV_FAIL] = new HashMap();
      this.profileData[this.TYPE_STMT_CACHE_ENTRY] = new HashMap();
      this.profileData[this.TYPE_STMT_USAGE] = new HashMap();
      this.profileData[this.TYPE_CONN_LAST_USAGE] = new HashMap();
      this.profileData[this.TYPE_CONN_MT_USAGE] = new HashMap();
      this.profileData[this.TYPE_CONN_UNWRAP_USAGE] = new HashMap();
      this.profileData[this.TYPE_CONN_LOCALTX_LEAK] = new HashMap();
      this.profileData[this.TYPE_CLOSED_USAGE] = new HashMap();
   }

   public boolean isProfilingEnabled() {
      return this.profileType != 0;
   }

   public boolean isResourceUsageProfilingEnabled() {
      return (this.profileType & 1) > 0;
   }

   public boolean isResourceReserveWaitProfilingEnabled() {
      return (this.profileType & 2) > 0;
   }

   public boolean isResourceReserveFailProfilingEnabled() {
      return (this.profileType & 8) > 0;
   }

   public boolean isResourceLeakProfilingEnabled() {
      return (this.profileType & 4) > 0;
   }

   public boolean isResourceLastUsageProfilingEnabled() {
      return (this.profileType & 64) > 0;
   }

   public boolean isResourceMTUsageProfilingEnabled() {
      return (this.profileType & 128) > 0;
   }

   public boolean isResourceUnwrapUsageProfilingEnabled() {
      return (this.profileType & 256) > 0;
   }

   public boolean isConnectionLocalTxLeakProfilingEnabled() {
      return (this.profileType & 512) > 0;
   }

   public boolean isStmtProfilingEnabled() {
      return (this.profileType & 32) > 0;
   }

   public boolean isStmtCacheProfilingEnabled() {
      return (this.profileType & 16) > 0;
   }

   public boolean isClosedUsageEnabled() {
      return (this.profileType & 1024) > 0;
   }

   public int getProfileConnectionLeakTimeoutSeconds() {
      return this.profileConnectionLeakTimeoutSeconds;
   }

   void setProfileConnectionLeakTimeoutSeconds(int secs) {
      this.profileConnectionLeakTimeoutSeconds = secs;
   }

   public void dumpData() {
      synchronized(this) {
         JDBCLogger.logPoolUsageData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_CONN_USAGE].values().iterator());
         JDBCLogger.logPoolWaitData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_CONN_WAIT].values().iterator());
         JDBCLogger.logPoolLeakData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_CONN_LEAK].values().iterator());
         JDBCLogger.logPoolResvFailData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_CONN_RESV_FAIL].values().iterator());
         JDBCLogger.logStmtCacheEntryData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_STMT_CACHE_ENTRY].values().iterator());
         JDBCLogger.logStmtUsageData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_STMT_USAGE].values().iterator());
         JDBCLogger.logConnLastUsageData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_CONN_LAST_USAGE].values().iterator());
         JDBCLogger.logConnMTUsageData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_CONN_MT_USAGE].values().iterator());
         JDBCLogger.logConnUnwrapUsageData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_CONN_UNWRAP_USAGE].values().iterator());
         JDBCLogger.logConnLocalTxLeakData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_CONN_LOCALTX_LEAK].values().iterator());
         JDBCLogger.logClosedObjectUsageData(this.pool.getResourceName());
         this.printData(this.profileData[this.TYPE_CLOSED_USAGE].values().iterator());
      }
   }

   public void harvestData() {
      if (this.isProfilingEnabled()) {
         synchronized(logSyncObject) {
            this.persistData("WEBLOGIC.JDBC.CONN.USAGE", this.profileData[this.TYPE_CONN_USAGE].values().iterator());
            this.persistData("WEBLOGIC.JDBC.CONN.RESV.WAIT", this.profileData[this.TYPE_CONN_WAIT].values().iterator());
            this.persistData("WEBLOGIC.JDBC.CONN.LEAK", this.profileData[this.TYPE_CONN_LEAK].values().iterator());
            this.persistData("WEBLOGIC.JDBC.CONN.RESV.FAIL", this.profileData[this.TYPE_CONN_RESV_FAIL].values().iterator());
            this.persistData("WEBLOGIC.JDBC.STMT_CACHE.ENTRY", this.profileData[this.TYPE_STMT_CACHE_ENTRY].values().iterator());
            this.persistData("WEBLOGIC.JDBC.STMT.USAGE", this.profileData[this.TYPE_STMT_USAGE].values().iterator());
            this.persistData("WEBLOGIC.JDBC.CONN.LAST_USAGE", this.profileData[this.TYPE_CONN_LAST_USAGE].values().iterator());
            this.persistData("WEBLOGIC.JDBC.CONN.MT_USAGE", this.profileData[this.TYPE_CONN_MT_USAGE].values().iterator());
            this.persistData("WEBLOGIC.JDBC.CONN.UNWRAP_USAGE", this.profileData[this.TYPE_CONN_UNWRAP_USAGE].values().iterator());
            this.persistData("WEBLOGIC.JDBC.CONN.LOCALTX_LEAK", this.profileData[this.TYPE_CONN_LOCALTX_LEAK].values().iterator());
            this.persistData("WEBLOGIC.JDBC.CLOSED_USAGE", this.profileData[this.TYPE_CLOSED_USAGE].values().iterator());
         }
      }

   }

   public void deleteData() {
      synchronized(this) {
         this.deleteLeakData();
         this.deleteResvFailData();
         this.deleteStmtUsageData();
         this.deleteConnLastUsageData();
         this.deleteConnMTUsageData();
         this.deleteConnUnwrapUsageData();
         this.deleteConnLocalTxLeakData();
         this.deleteClosedUsageData();
      }
   }

   public void addUsageData(PooledResource res) {
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), res.toString(), ((ConnectionEnv)res).getCurrentUser(), (new Date()).toString());
      synchronized(this) {
         this.profileData[this.TYPE_CONN_USAGE].put(res, record);
      }
   }

   public void deleteUsageData(PooledResource res) {
      if (this.profileData[this.TYPE_CONN_USAGE].containsKey(res)) {
         synchronized(this) {
            this.profileData[this.TYPE_CONN_USAGE].remove(res);
         }
      }

   }

   public void addWaitData() {
      String thrId = Thread.currentThread().getName();
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), thrId, StackTraceUtils.throwable2StackTrace(new Exception()), (new Date()).toString());
      synchronized(this) {
         this.profileData[this.TYPE_CONN_WAIT].put(thrId, record);
      }
   }

   public void deleteWaitData() {
      String thrId = Thread.currentThread().getName();
      if (this.profileData[this.TYPE_CONN_WAIT].containsKey(thrId)) {
         synchronized(this) {
            this.profileData[this.TYPE_CONN_WAIT].remove(thrId);
         }
      }

   }

   public void addLeakData(PooledResource res) {
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), res.toString(), ((ConnectionEnv)res).getCurrentUser(), (new Date()).toString());
      synchronized(this) {
         this.profileData[this.TYPE_CONN_LEAK].put(res, record);
      }
   }

   private void deleteLeakData() {
      this.profileData[this.TYPE_CONN_LEAK].clear();
   }

   public void addResvFailData(String user) {
      String thrId = Thread.currentThread().getName();
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), thrId, user, (new Date()).toString());
      synchronized(this) {
         this.profileData[this.TYPE_CONN_RESV_FAIL].put(thrId, record);
      }
   }

   private void deleteResvFailData() {
      this.profileData[this.TYPE_CONN_RESV_FAIL].clear();
   }

   void addStmtCacheEntryData(StatementHolder sh) {
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), sh.getKey().toString(), StackTraceUtils.throwable2StackTrace(new Exception()), (new Date()).toString());
      synchronized(this) {
         this.profileData[this.TYPE_STMT_CACHE_ENTRY].put(sh, record);
      }
   }

   void deleteStmtCacheEntryData(StatementHolder sh) {
      synchronized(this) {
         this.profileData[this.TYPE_STMT_CACHE_ENTRY].remove(sh);
      }
   }

   public void addStmtUsageData(Statement stmt, String sql, long startTime) {
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), sql, StackTraceUtils.throwable2StackTrace(new Exception()), Integer.toString((int)(System.currentTimeMillis() - startTime)));
      synchronized(this) {
         this.profileData[this.TYPE_STMT_USAGE].put(stmt, record);
      }
   }

   void setProfileType(int type) {
      this.profileType = type;
   }

   private void deleteStmtUsageData() {
      this.profileData[this.TYPE_STMT_USAGE].clear();
   }

   public void addConnLastUsageData(String currentError, String lastUser, Date errorTimestamp) {
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), currentError, lastUser, errorTimestamp.toString());
      synchronized(this) {
         this.profileData[this.TYPE_CONN_LAST_USAGE].put(record, record);
      }
   }

   private void deleteConnLastUsageData() {
      this.profileData[this.TYPE_CONN_LAST_USAGE].clear();
   }

   public void addConnMTUsageData(String currentThr, String resvThr, Date errorTimestamp) {
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), currentThr, resvThr, errorTimestamp.toString());
      synchronized(this) {
         this.profileData[this.TYPE_CONN_MT_USAGE].put(record, record);
      }
   }

   private void deleteConnMTUsageData() {
      this.profileData[this.TYPE_CONN_MT_USAGE].clear();
   }

   public void addConnUnwrapUsageData(String currentThr, String resvThr, Date errorTimestamp) {
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), currentThr, resvThr, errorTimestamp.toString());
      synchronized(this) {
         this.profileData[this.TYPE_CONN_UNWRAP_USAGE].put(record, record);
      }
   }

   private void deleteConnUnwrapUsageData() {
      this.profileData[this.TYPE_CONN_UNWRAP_USAGE].clear();
   }

   public void addConnLocalTxLeakData(String currentThr, String resvThr, Date errorTimestamp) {
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), currentThr, resvThr, errorTimestamp.toString());
      synchronized(this) {
         this.profileData[this.TYPE_CONN_LOCALTX_LEAK].put(record, record);
      }
   }

   private void deleteConnLocalTxLeakData() {
      this.profileData[this.TYPE_CONN_LOCALTX_LEAK].clear();
   }

   public void addClosedUsageData(String currentThr, String resvThr, Date errorTimestamp) {
      ProfileDataRecord record = new ProfileDataRecord(this.pool.getResourceName(), currentThr, resvThr, errorTimestamp.toString());
      synchronized(this) {
         this.profileData[this.TYPE_CLOSED_USAGE].put(record, record);
      }
   }

   private void deleteClosedUsageData() {
      this.profileData[this.TYPE_CLOSED_USAGE].clear();
   }

   private void printData(Iterator iter) {
      while(iter.hasNext()) {
         ProfileDataRecord record = (ProfileDataRecord)iter.next();
         JDBCLogger.logProfileRecordPoolName(record.getPoolName());
         JDBCLogger.logProfileRecordId(record.getId());
         JDBCLogger.logProfileRecordUser(record.getUser());
         JDBCLogger.logProfileRecordTimestamp(record.getTimestamp());
      }

   }

   private void persistData(String type, Iterator iter) {
      while(true) {
         try {
            if (iter.hasNext()) {
               ProfileDataRecord pdr = (ProfileDataRecord)iter.next();
               this.writeLog(type, pdr);
               continue;
            }
         } catch (Exception var4) {
         }

         return;
      }
   }

   private void writeLog(String type, ProfileDataRecord rec) throws Exception {
      if (this.bufferedWriter == null) {
         DataSourceServiceImpl dsService = (DataSourceServiceImpl)DataSourceManager.getInstance().getDataSourceService();
         this.rotatingFileOutputStream = (RotatingFileOutputStream)dsService.getLogFileOutputStream();
         this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.rotatingFileOutputStream));
      }

      StringBuilder sb = new StringBuilder();
      sb.append("####");
      sb.append(this.formatField(rec.getPoolName())).append(this.formatField(type)).append(this.formatField(rec.getTimestamp())).append(this.formatField(rec.getUser())).append(this.formatField(rec.getId()));
      if (!LogMessageFormatter.isLogFormatCompatibilityEnabled()) {
         Properties props = new Properties();
         String pid = rec.getPartitionId();
         if (pid != null && !pid.isEmpty()) {
            props.put(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_ID.getAttributeName(), pid);
         }

         String pname = rec.getPartitionName();
         if (pname != null && !pname.isEmpty()) {
            props.put(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_NAME.getAttributeName(), pname);
         }

         sb.append(this.formatField(LogMessageFormatter.renderSupplementalAttributes(props)));
      }

      sb.append(PlatformConstants.EOL);
      String string = sb.toString();
      this.bufferedWriter.write(string, 0, string.length());
      this.bufferedWriter.flush();
      this.rotatingFileOutputStream.ensureRotation();
   }

   private String formatField(String data) {
      StringBuilder sb = new StringBuilder();
      sb.append("<").append(data).append("> ");
      return sb.toString();
   }

   public Map getClosedUsageData() {
      return (Map)this.profileData[this.TYPE_CLOSED_USAGE].clone();
   }

   public Map getLeakData() {
      return (Map)this.profileData[this.TYPE_CONN_LEAK].clone();
   }
}
