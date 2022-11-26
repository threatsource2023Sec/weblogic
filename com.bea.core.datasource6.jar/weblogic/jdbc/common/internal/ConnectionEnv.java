package weblogic.jdbc.common.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.resource.spi.security.PasswordCredential;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import oracle.ucp.ConnectionHarvestingCallback;
import oracle.ucp.ConnectionLabelingCallback;
import oracle.ucp.jdbc.ConnectionInitializationCallback;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.HangSuspect;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceCleanupHandler;
import weblogic.common.resourcepool.ResourceInfo;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.JDBCTextTextFormatter;
import weblogic.jdbc.extensions.WLConnection;
import weblogic.jdbc.wrapper.Connection;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.JDBCWrapperImpl;
import weblogic.jdbc.wrapper.PoolConnection;
import weblogic.jdbc.wrapper.PooledConnection;
import weblogic.jdbc.wrapper.XAConnection;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.XIDFactory;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.XAUtils;
import weblogic.utils.collections.EmbeddedList;
import weblogic.utils.collections.EmbeddedListElement;
import weblogic.utils.collections.SecondChanceCacheMap;
import weblogic.work.WorkManagerFactory;

public class ConnectionEnv implements PooledResource, ResourceCleanupHandler, ResourceInfo, HangSuspect, EmbeddedListElement {
   public volatile int replayAttemptCount;
   public boolean isReplayed;
   public boolean isNodeDown;
   public JDBCConnectionPool pool;
   public boolean autoCommit;
   public boolean drcpEnabled;
   public ConnectionHolder conn;
   protected boolean enabled;
   private boolean isXA;
   protected boolean isJTS;
   private boolean wrapTypes;
   private boolean wrapJdbc;
   private JDBCDataSourceBean dsBean;
   private int vendorId;
   private boolean connUsed;
   private boolean doInit;
   private String initQuery;
   protected boolean ignore_init_fails;
   public boolean endLocalTxOnXaConWithCommit;
   public boolean endLocalTxOnNonXaConWithCommit;
   public boolean endLocalTxOnNonXaConWithRollback;
   protected boolean destroyed;
   protected volatile boolean attached;
   private String poolname;
   private String appname;
   private String moduleName;
   private String compName;
   private long connectTime;
   private boolean dirtyIsolationLevel;
   private int initialIsolationLevel;
   private ResourceCleanupHandler cleanupHdlr;
   private boolean infected;
   private boolean openProxy;
   public long lastSuccessfulConnectionUse;
   public long secondsToTrustAnIdlePoolConnection;
   public char[] savePassword;
   public String saveUser;
   public Object saveClientId;
   private SecondChanceCacheMap stmtCache;
   private int stmtCacheType;
   private int stmtCacheSize;
   private final Object stmtCacheLock;
   private int cacheHitCount;
   private int cacheMissCount;
   private long cacheAccessCount;
   private long cacheAddCount;
   private long cacheDeleteCount;
   private Throwable currentUser;
   private String currentUserString;
   private String currentThread;
   private volatile long reservingThreadId;
   private Throwable lastUser;
   private Throwable currentError;
   private Date currentErrorTimestamp;
   public ConnectionPoolProfiler profiler;
   protected PooledResourceInfo prInfo;
   private int[] fatalErrorCodes;
   private Properties defaultClientInfo;
   private boolean needRestoreClientInfo;
   private static boolean JDBC4Runtime = false;
   private boolean supportStatementPoolable;
   private boolean supportIsValid;
   protected boolean connectionHarvestable;
   protected boolean connectionHarvested;
   public Connection connectionHarvestedCallback;
   public ConnectionHarvestingCallback connectionHarvestingCallback;
   private final Object harvestLock;
   private Object pdb;
   private boolean inCallback;
   private boolean isClientInfoValid;
   private String BADISVALID;
   private String BADPING;
   private Executor timeoutExecutor;
   private volatile boolean destroyAfterRelease;
   private volatile boolean repurposeOnRelease;
   private Properties labels;
   private volatile XAException xaExceptionDuringTesting;
   protected volatile boolean profileRecordLogged;
   private int attachTimeout;
   private volatile ResourcePoolGroup primaryGroup;
   private Map groups;
   protected volatile SwitchingContext switchingContext;
   protected AtomicBoolean processingSwitch;
   protected SwitchingContext rootSwitchingContext;
   private transient Properties driverProperties;
   private transient XADataSource xaDataSource;
   private transient DataSource dataSource;
   private transient ConnectionPoolDataSource connectionPoolDataSource;
   private String where_disabled;
   private volatile String where_destroyed;
   private boolean testNeeded;
   private Object owner;
   private String stackTrace;
   private boolean refreshNeeded;
   private boolean cleanupNeeded;
   private boolean con_in_use;
   private static int CON_STATE_IN_USE;
   private static int CON_STATE_IDLE_SUSPECT;
   private static int CON_STATE_HANG_SUSPECT;
   private int hang_state;
   private boolean kill_because_hung;
   Connection userCon;
   private PreparedStatement test_ps;
   public Object clientID;
   Object oldClientID;
   Object initClientID;
   private boolean weKnowWeCanSkipOracleBatchReset;
   private boolean weHaveToResetOracleStatements;
   private Method setExecuteBatch;
   private Method getExecuteBatch;
   private Object[] oo;
   boolean needsConfigure;
   private EmbeddedListElement next;
   private EmbeddedListElement prev;
   private EmbeddedList list;

   public ConnectionEnv(Properties poolParams) {
      this.replayAttemptCount = 0;
      this.isReplayed = false;
      this.isNodeDown = false;
      this.autoCommit = true;
      this.drcpEnabled = false;
      this.conn = null;
      this.enabled = true;
      this.isXA = false;
      this.isJTS = false;
      this.wrapTypes = true;
      this.wrapJdbc = true;
      this.dsBean = null;
      this.vendorId = -1;
      this.connUsed = false;
      this.doInit = false;
      this.initQuery = null;
      this.ignore_init_fails = false;
      this.endLocalTxOnXaConWithCommit = false;
      this.endLocalTxOnNonXaConWithCommit = true;
      this.endLocalTxOnNonXaConWithRollback = false;
      this.destroyed = false;
      this.attached = false;
      this.connectTime = 0L;
      this.dirtyIsolationLevel = false;
      this.initialIsolationLevel = 0;
      this.cleanupHdlr = null;
      this.infected = false;
      this.openProxy = false;
      this.lastSuccessfulConnectionUse = 0L;
      this.secondsToTrustAnIdlePoolConnection = 0L;
      this.savePassword = null;
      this.saveUser = null;
      this.saveClientId = null;
      this.stmtCache = null;
      this.stmtCacheType = 0;
      this.stmtCacheSize = 10;
      this.stmtCacheLock = new Object() {
      };
      this.reservingThreadId = -1L;
      this.prInfo = null;
      this.fatalErrorCodes = null;
      this.defaultClientInfo = null;
      this.needRestoreClientInfo = false;
      this.supportStatementPoolable = false;
      this.supportIsValid = false;
      this.connectionHarvestable = true;
      this.connectionHarvested = false;
      this.connectionHarvestedCallback = null;
      this.connectionHarvestingCallback = null;
      this.harvestLock = new Object() {
      };
      this.pdb = null;
      this.inCallback = false;
      this.isClientInfoValid = true;
      this.BADISVALID = "isValid returns false";
      this.BADPING = "pingDatabase returns not OK";
      this.timeoutExecutor = new TimeoutExecutor();
      this.destroyAfterRelease = false;
      this.labels = new Properties();
      this.xaExceptionDuringTesting = null;
      this.attachTimeout = 10000;
      this.groups = Collections.synchronizedMap(new HashMap());
      this.processingSwitch = new AtomicBoolean();
      this.driverProperties = null;
      this.xaDataSource = null;
      this.dataSource = null;
      this.connectionPoolDataSource = null;
      this.where_disabled = null;
      this.where_destroyed = "It is being destroyed now.";
      this.testNeeded = false;
      this.owner = null;
      this.stackTrace = "";
      this.refreshNeeded = false;
      this.cleanupNeeded = false;
      this.con_in_use = false;
      this.hang_state = CON_STATE_IDLE_SUSPECT;
      this.kill_because_hung = false;
      this.userCon = null;
      this.test_ps = null;
      this.clientID = null;
      this.oldClientID = null;
      this.initClientID = null;
      this.weKnowWeCanSkipOracleBatchReset = false;
      this.weHaveToResetOracleStatements = false;
      this.setExecuteBatch = null;
      this.getExecuteBatch = null;
      this.oo = null;
      this.needsConfigure = true;
      String val = poolParams.getProperty("PSCacheSize");
      if (val != null) {
         int cacheSize = Integer.parseInt(val);
         if (cacheSize >= 0 && cacheSize <= 1024) {
            this.stmtCacheSize = cacheSize;
         } else {
            JDBCLogger.logInvalidCacheSize("", cacheSize);
         }
      }

      val = poolParams.getProperty("weblogic.jdbc.attachNetworkTimeout");
      if (val != null) {
         this.attachTimeout = Integer.parseInt(val);
      }

      val = poolParams.getProperty("PSCacheType");
      if (val != null) {
         if (val.equals("FIXED")) {
            this.stmtCacheType = 1;
         } else if (val.equals("LRU")) {
            this.stmtCacheType = 0;
         }
      }

      this.initQuery = poolParams.getProperty("initName");
      String codelist = poolParams.getProperty("FatalErrorCodes");
      String[] codestrings = null;
      if (codelist != null) {
         codestrings = codelist.split(",");
      }

      if (codestrings != null && codestrings.length > 0) {
         this.fatalErrorCodes = new int[codestrings.length];

         for(int i = 0; i < codestrings.length; ++i) {
            try {
               this.fatalErrorCodes[i] = Integer.parseInt(codestrings[i].trim());
            } catch (Exception var8) {
               this.fatalErrorCodes[i] = -1;
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  JdbcDebug.JDBCCONN.debug("Non-numeric fatal error code: " + codestrings[i]);
               }
            }
         }
      }

      if (this.stmtCacheSize > 0) {
         this.stmtCache = new SecondChanceCacheMap(this.stmtCacheSize);
      }

   }

   public ConnectionEnv(Properties poolParams, boolean isXA) {
      this(poolParams);
      this.isXA = isXA;
   }

   public Properties getDriverProperties() {
      return this.driverProperties;
   }

   public void setDriverProperties(Properties p) {
      this.driverProperties = p;
   }

   public XADataSource getXADataSource() {
      return this.xaDataSource;
   }

   public void setXADataSource(XADataSource v) {
      this.xaDataSource = v;
      if (this.pool != null && this.pool.getOracleHelper() != null) {
         try {
            this.pool.getOracleHelper().registerConnectionInitializationCallback(this);
         } catch (SQLException var3) {
         }
      }

   }

   public DataSource getDataSource() {
      return this.dataSource;
   }

   public void setDataSource(DataSource v) {
      this.dataSource = v;
      if (this.pool != null && this.pool.getOracleHelper() != null) {
         try {
            this.pool.getOracleHelper().registerConnectionInitializationCallback(this);
         } catch (SQLException var3) {
         }
      }

   }

   public ConnectionPoolDataSource getConnectionPoolDataSource() {
      return this.connectionPoolDataSource;
   }

   public void setConnectionPoolDataSource(ConnectionPoolDataSource v) {
      this.connectionPoolDataSource = v;
   }

   public PooledResourceInfo getPooledResourceInfo() {
      return this.prInfo;
   }

   public void setPooledResourceInfo(PooledResourceInfo info) {
      this.prInfo = info;
      if (info instanceof ConnectionInfo && this.driverProperties != null) {
         this.driverProperties.setProperty("user", ((ConnectionInfo)info).getUsername());
         this.driverProperties.setProperty("password", ((ConnectionInfo)info).getPassword());
         String wl_id = ((ConnectionInfo)info).getWLUserID();
         if (wl_id != null && !"".equals(wl_id)) {
            this.driverProperties.setProperty("IMPERSONATE", wl_id);
         }
      }

   }

   public void initialize() throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CE:initialize (10)");
      }

      if (!this.doInit) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < CE:initialize (20) returns");
         }

      } else {
         boolean needDetach = false;
         Statement stmt = null;
         Exception aex = null;
         if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
            JdbcDebug.enter(this.poolname, "Initializing connection " + this.conn.jconn);
         }

         boolean suspendTx = false;
         Transaction curTx = null;
         Xid xid = null;
         XAResource xar = null;
         boolean ddl = false;
         TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();

         try {
            if (!this.attached) {
               this.OracleAttachServerConnection();
               needDetach = true;
            }

            if (this.isXA) {
               if (tm.getTransaction() != null) {
                  curTx = (Transaction)tm.suspend();
                  suspendTx = true;
               }

               String branch = this.poolname;
               if (branch == null || branch.equals("")) {
                  branch = "weblogic.jdbc.xa.test";
               }

               xid = XIDFactory.createXID(branch);
               String check = "";
               if (this.initQuery != null) {
                  check = this.initQuery.toLowerCase().trim();
               }

               if (check.startsWith("create") || check.startsWith("alter") || check.startsWith("truncate") || check.startsWith("drop") || check.startsWith("rename") || check.startsWith("grant") || check.startsWith("revoke")) {
                  ddl = true;
               }

               if (!ddl) {
                  xar = ((XAConnection)this.conn.jconn).getXAResource();
                  xar.start(xid, 0);
               }
            }

            stmt = this.conn.jconn.createStatement();
            if (this.dsBean != null) {
               int queryTimeout = this.dsBean.getJDBCConnectionPoolParams().getStatementTimeout();
               if (queryTimeout > -1) {
                  try {
                     stmt.setQueryTimeout(queryTimeout);
                  } catch (SQLException var17) {
                     JDBCLogger.logSetQueryTOFailed(var17.toString());
                  }
               }
            }

            if (this.ignore_init_fails) {
               try {
                  stmt.execute(this.initQuery);
               } catch (Exception var16) {
               }
            } else {
               stmt.execute(this.initQuery);
            }
         } catch (Exception var19) {
            aex = var19;
         }

         try {
            if (stmt != null) {
               stmt.close();
            }
         } catch (Exception var15) {
         }

         if (this.isXA) {
            try {
               if (!ddl) {
                  xar.end(xid, 67108864);
               }
            } catch (Exception var14) {
            }

            try {
               if (!ddl) {
                  xar.commit(xid, true);
               }
            } catch (Exception var13) {
            }

            if (suspendTx) {
               try {
                  tm.resume(curTx);
               } catch (Exception var12) {
               }
            }
         }

         if (needDetach) {
            try {
               this.OracleDetachServerConnection();
            } catch (Exception var18) {
               if (aex == null && !this.ignore_init_fails) {
                  aex = var18;
               }
            }
         }

         if (aex == null) {
            if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
               JdbcDebug.leave(this.poolname, "Initializing connection " + this.conn.jconn);
            }

            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug(" < CE:initialize (100)");
            }

         } else {
            if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
               JdbcDebug.err((String)this.poolname, "Initializing connection " + this.conn.jconn, aex);
            }

            JDBCLogger.logConnInitFailed(this.poolname, this.initQuery, aex.toString());
            this.destroy();
            throw new ResourceException(aex.toString());
         }
      }
   }

   public void enable() {
      this.enabled = true;
   }

   public void disable() {
      this.enabled = false;
      Exception here = new Exception("It was disabled at " + new Date());
      this.where_disabled = StackTraceUtils.throwable2StackTrace(here);
   }

   public void setup() {
      if (this.profiler.isResourceLeakProfilingEnabled() || this.profiler.isResourceMTUsageProfilingEnabled() || this.profiler.isConnectionLocalTxLeakProfilingEnabled() || this.pool.getInactiveSeconds() > 0) {
         this.currentUser = new Exception();
         this.reservingThreadId = Thread.currentThread().getId();
         if (this.profiler.isResourceLeakProfilingEnabled()) {
            this.currentThread = Thread.currentThread().toString();
         }
      }

   }

   void finalCleanup() {
      if (this.savePassword != null) {
         Arrays.fill(this.savePassword, '\u0000');
      }

      if (this.prInfo != null && this.prInfo instanceof ConnectionInfo) {
         ((ConnectionInfo)this.prInfo).cleanup();
      }

   }

   public void cleanup() {
      this.currentUser = null;
      this.currentUserString = null;
      this.currentThread = null;
      this.userCon = null;
      this.connectionHarvestable = true;
      this.connectionHarvested = false;
      this.connectionHarvestingCallback = null;
      this.lastUser = null;
      this.currentError = null;
      this.currentErrorTimestamp = null;
      this.profileRecordLogged = false;
      this.reservingThreadId = -1L;
      this.setNotInUse();
      if (this.conn != null) {
         java.sql.Connection jconn = this.conn.jconn;
         if (jconn != null) {
            boolean connClosed = false;
            if (this.pool.getOracleVersion() > 0) {
               try {
                  connClosed = jconn.isClosed();
               } catch (Exception var8) {
                  connClosed = false;
               }
            }

            if (connClosed) {
               this.needRestoreClientInfo = false;
               this.autoCommit = true;
               this.dirtyIsolationLevel = false;
            } else {
               if (isJDBC4Runtime() && this.needRestoreClientInfo) {
                  this.needRestoreClientInfo = false;

                  try {
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JdbcDebug.JDBCCONN.debug("jconn.setClientInfo(Properties)");
                        JdbcDebug.JDBCCONN.debug("Properties value: " + this.defaultClientInfo);
                     }

                     jconn.setClientInfo(this.defaultClientInfo);
                  } catch (Throwable var14) {
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JdbcDebug.JDBCCONN.debug("jconn.setClientInfo(Properties) failed.", var14);
                     }
                  }
               }

               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  StringBuffer sb = new StringBuffer(100);
                  sb.append("ConnectionEnv.cleanup, jconn=").append(jconn).append(", isXA=").append(this.isXA).append(", isJTS=").append(this.isJTS);

                  try {
                     if (jconn != null) {
                        sb.append(", jconn.isolationLevel=").append(jconn.getTransactionIsolation());
                        if (this.vendorId == 102 || this.vendorId == 106) {
                           jconn.rollback();
                        }
                     }
                  } catch (SQLException var13) {
                  }

                  sb.append(", initialIsolationLevel=").append(this.initialIsolationLevel);
                  sb.append(", dirtyIsolationLevel=").append(this.dirtyIsolationLevel);
                  JdbcDebug.JDBCCONN.debug(sb.toString());
               }

               if (!this.autoCommit && !this.isXA && !this.isJTS) {
                  try {
                     if (this.endLocalTxOnNonXaConWithCommit) {
                        jconn.commit();
                     } else if (this.endLocalTxOnNonXaConWithRollback) {
                        jconn.rollback();
                     }

                     jconn.setAutoCommit(true);
                     this.autoCommit = true;
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JdbcDebug.JDBCCONN.debug("jconn.setAutoCommit(true): " + jconn);
                     }
                  } catch (Exception var12) {
                     try {
                        if (!jconn.getAutoCommit()) {
                           jconn.rollback();
                        }
                     } catch (Exception var7) {
                     }

                     try {
                        jconn.setAutoCommit(true);
                        this.autoCommit = true;
                     } catch (Exception var11) {
                        if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                           JdbcDebug.JDBCCONN.debug("jconn.setAutoCommit(true) failed", var11);
                        }
                     }
                  }
               }

               this.isJTS = false;
               if (this.dirtyIsolationLevel) {
                  this.dirtyIsolationLevel = false;
                  if (this.isXA) {
                     ((XAConnection)jconn).resetTransactionIsolation(this.initialIsolationLevel);
                  } else {
                     try {
                        jconn.setTransactionIsolation(this.initialIsolationLevel);
                     } catch (Exception var10) {
                        if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                           JdbcDebug.JDBCCONN.debug("ConnectionEnv.cleanup setTransactionIsolation", var10);
                        }

                        try {
                           if (!jconn.getAutoCommit()) {
                              jconn.rollback();
                           }
                        } catch (Exception var6) {
                        }

                        try {
                           jconn.setTransactionIsolation(this.initialIsolationLevel);
                        } catch (Exception var9) {
                           if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                              JdbcDebug.JDBCCONN.debug("ConnectionEnv.cleanup setTransactionIsolation", var9);
                           }
                        }
                     }
                  }
               }

               try {
                  jconn.clearWarnings();
               } catch (Exception var5) {
               }

               if (jconn instanceof PooledConnection) {
                  ((PooledConnection)jconn).cleanup(false);
               }

               if (this.openProxy) {
                  this.OracleProxyConnectionClose();
               }

            }
         }
      }
   }

   public void OracleAttachServerConnection() throws ResourceException {
      if (this.drcpEnabled && this.conn != null && this.conn.oracleAttachServerConnection != null) {
         java.sql.Connection jconn = this.conn.jconn;
         if (jconn == null) {
            throw new ResourceException(JDBCUtil.getTextFormatter().connectionClosed());
         }

         try {
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + jconn + "] oracleAttachServerConnection()");
            }

            if (jconn instanceof javax.sql.XAConnection || jconn instanceof javax.sql.PooledConnection) {
               if (jconn instanceof JDBCWrapperImpl) {
                  jconn = (java.sql.Connection)((JDBCWrapperImpl)jconn).getVendorObj();
               }

               if (this.conn.oraclePhysicalConnectionWithin != null) {
                  jconn = (java.sql.Connection)this.conn.oraclePhysicalConnectionWithin.invoke(jconn, (Object[])null);
               }
            }

            int timeout = 0;
            if (this.attachTimeout > 0) {
               timeout = jconn.getNetworkTimeout();
               jconn.setNetworkTimeout(this.timeoutExecutor, this.attachTimeout);
            }

            this.conn.oracleAttachServerConnection.invoke(jconn, (Object[])null);
            if (this.attachTimeout > 0) {
               if (this.conn.hasPingDatabaseMethod && (Integer)this.conn.pingDatabase.invoke(jconn, 0) != this.conn.pingDatabaseOk) {
                  throw new ResourceException(this.BADPING);
               }

               jconn.setNetworkTimeout(this.timeoutExecutor, timeout);
            }

            this.attached = true;
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + jconn + "] oracleAttachServerConnection() returned");
            }

            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("attachServerConnection: conn=" + this);
            }
         } catch (Throwable var3) {
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + jconn + "] oracleAttachServerConnection failed");
            }

            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("attachServerConnection failed: conn=" + this);
            }

            throw new ResourceException(JDBCUtil.getTextFormatter().attachFailed(), var3);
         }
      }

   }

   public void OracleDetachServerConnection() throws ResourceException {
      if (this.drcpEnabled && this.conn != null && this.conn.oracleDetachServerConnection != null) {
         java.sql.Connection jconn = this.conn.jconn;
         if (jconn == null) {
            throw new ResourceException(JDBCUtil.getTextFormatter().connectionClosed());
         }

         this.clearCache();

         try {
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + jconn + "] oracleDetachServerConnection()");
            }

            if (!(jconn instanceof javax.sql.XAConnection) && !(jconn instanceof javax.sql.PooledConnection)) {
               this.conn.oracleDetachServerConnection.invoke(jconn, (String)null);
            } else {
               if (jconn instanceof JDBCWrapperImpl) {
                  jconn = (java.sql.Connection)((JDBCWrapperImpl)jconn).getVendorObj();
               }

               if (this.conn.oraclePhysicalConnectionWithin != null) {
                  this.conn.oracleDetachServerConnection.invoke(this.conn.oraclePhysicalConnectionWithin.invoke(jconn, (Object[])null), (String)null);
               } else {
                  this.conn.oracleDetachServerConnection.invoke(jconn, (String)null);
               }
            }

            this.attached = false;
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + jconn + "] oracleDetachServerConnection() returned");
            }

            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("detachServerConnection: conn=" + this);
            }
         } catch (Throwable var3) {
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + jconn + "] oracleDetachServerConnection failed");
            }

            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("detachServerConnection failed: conn=" + this);
            }

            throw new ResourceException(JDBCUtil.getTextFormatter().detachFailed(), var3);
         }
      }

   }

   public boolean hasOracleProxyConnectionCloseMethod() {
      return this.conn != null ? this.conn.hasOracleProxyConnectionCloseMethod : false;
   }

   public void OracleProxyConnectionClose() {
      this.openProxy = false;
      if (this.conn != null && this.conn.hasOracleProxyConnectionCloseMethod) {
         java.sql.Connection jconn = this.conn.jconn;
         if (jconn == null) {
            return;
         }

         try {
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + jconn + "] close(OracleConnection.PROXY_SESSION)");
            }

            this.conn.oracleProxyConnectionClose.invoke(jconn, this.conn.proxySession);
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + jconn + "] close returns");
            }
         } catch (Exception var3) {
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + jconn + "] close failed");
            }

            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("destroying connection due to proxy connection close error: conn=" + this + ", exception=" + var3.getMessage());
            }

            this.needDestroyAfterRelease();
         }

         this.clearIdentity();
         this.clearCache();
         this.saveUser = null;
         if (this.savePassword != null) {
            Arrays.fill(this.savePassword, '\u0000');
         }

         this.savePassword = null;
         if (this.switchingContext != null) {
            this.switchingContext.setProxyUser((String)null);
            this.switchingContext.setProxyPassword((char[])null);
         }
      }

   }

   public boolean hasOracleOpenProxySession() {
      return this.conn != null ? this.conn.hasOracleOpenProxySession : false;
   }

   public void OracleOpenProxySession(int type, Properties prop) throws SQLException {
      if (this.conn != null && this.conn.hasOracleOpenProxySession) {
         java.sql.Connection jconn = this.conn.jconn;
         if (jconn == null) {
            throw new SQLException(JDBCUtil.getTextFormatter().connectionClosed());
         }

         this.openProxy = true;

         try {
            Throwable cause;
            try {
               if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
                  JdbcDebug.JDBCSQL.debug("[" + jconn + "] openProxySession(OracleConnection.PROXYTYPE_USER_NAME,props)");
               }

               this.conn.oracleOpenProxySession.invoke(jconn, type, prop);
               if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
                  JdbcDebug.JDBCSQL.debug("[" + jconn + "] openProxySession returns");
               }
            } catch (InvocationTargetException var10) {
               cause = var10.getCause();
               if (cause != null && SQLException.class.isAssignableFrom(cause.getClass())) {
                  throw (SQLException)cause;
               }

               throw new SQLException("oracleOpenProxySession: " + StackTraceUtils.throwable2StackTrace(var10));
            } catch (Exception var11) {
               if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
                  JdbcDebug.JDBCSQL.debug("[" + jconn + "] openProxySession failed");
               }

               if (var11 instanceof SQLException) {
                  throw (SQLException)var11;
               }

               if (var11 instanceof InvocationTargetException) {
                  cause = ((InvocationTargetException)var11).getCause();
                  if (cause != null && cause instanceof SQLException) {
                     throw (SQLException)cause;
                  }
               }

               throw new SQLException("oracleOpenProxySession: " + StackTraceUtils.throwable2StackTrace(var11));
            }
         } finally {
            this.clearIdentity();
            this.clearCache();
         }
      }

   }

   public synchronized void destroy() {
      this.destroy(false);
   }

   public synchronized void forceDestroy() {
      this.destroy(true);
   }

   private synchronized void destroy(boolean emergency) {
      if (!this.destroyed) {
         this.destroyed = true;
         Exception here = new Exception("It was destroyed at " + new Date());
         this.where_destroyed = StackTraceUtils.throwable2StackTrace(here);
         this.finalCleanup();
         this.destroyForFlush(emergency);
         OracleHelper oracleHelper = this.pool.getOracleHelper();
         if (oracleHelper != null && oracleHelper.isReplayDriver()) {
            this.pool.incrementClosedConnectionReplayStatistics(this);
         }

         JDBCLogger.logConnClosedInfo(this.poolname);
         if (this.conn.jconn instanceof XAConnection) {
            ((XAConnection)this.conn.jconn).destroy();
         } else if (this.conn.jconn instanceof PooledConnection) {
            ((PooledConnection)this.conn.jconn).destroy();
         } else {
            try {
               this.conn.jconn.close();
            } catch (Exception var5) {
            }
         }

         this.test_ps = null;
         this.pool.removeCachedPooledResource(this.conn.jconn);
         this.conn.jconn = null;
      }
   }

   public synchronized void destroyForFlush(boolean emergency) {
      try {
         this.cancelStatements();
      } catch (Exception var7) {
      }

      try {
         this.stmtCache.clear();
      } catch (Exception var6) {
      }

      OracleHelper oracleHelper = this.pool.getOracleHelper();
      if (oracleHelper != null && oracleHelper.isReplayDriver()) {
         this.pool.incrementClosedConnectionReplayStatistics(this);
      }

      if (emergency && this.conn.isAbortSupported()) {
         try {
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + this.conn.jconn + "] Abort");
            }

            this.conn.invokeAbort(this.conn.jconn);
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + this.conn.jconn + "] Abort returns");
            }

            return;
         } catch (Exception var8) {
            if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
               JdbcDebug.JDBCSQL.debug("[" + this.conn.jconn + "] Abort failed");
            }
         }
      }

      if (!this.isNodeDown) {
         try {
            if (!this.conn.jconn.getAutoCommit()) {
               this.conn.jconn.rollback();
            }
         } catch (Exception var5) {
         }
      } else {
         this.isNodeDown = false;
      }

      try {
         this.conn.jconn.close();
      } catch (Exception var4) {
      }

   }

   public int test() {
      if (this.destroyed) {
         return -1;
      } else if (this.conn != null && this.conn.jconn != null) {
         if (this.conn.oracleIsUsable != null) {
            try {
               this.conn.oracleIsUsable.invoke(this.conn.jconn, (Object[])null);
            } catch (Exception var2) {
               return -1;
            }
         }

         return this.pool.isLocalValidateOnly() && !this.testNeeded ? 1 : this.test(this.pool.getResourceFactory().getTestQuery());
      } else {
         return -1;
      }
   }

   public boolean isTestNeeded() {
      return this.testNeeded;
   }

   public void setTestNeeded(boolean b) {
      this.testNeeded = b;
   }

   public Object getOwner() {
      return this.owner;
   }

   public void setOwner(Object o) {
      this.owner = o;
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         this.stackTrace = StackTraceUtils.throwable2StackTrace(new Exception("Pinned: " + o + " own " + this));
      }

   }

   public String getStackTrace() {
      return this.stackTrace;
   }

   public void setStackTrace(String s) {
      this.stackTrace = s;
   }

   public boolean isRefreshNeeded() {
      return this.refreshNeeded;
   }

   public void setRefreshNeeded(boolean b) {
      this.refreshNeeded = b;
      if (b && JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug(StackTraceUtils.throwable2StackTrace(new Exception("Pinned: " + this + " is set to be refreshed")));
      }

   }

   public boolean isCleanupNeeded() {
      return this.cleanupNeeded;
   }

   public void setCleanupNeeded(boolean b) {
      this.cleanupNeeded = b;
      if (b && JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug(StackTraceUtils.throwable2StackTrace(new Exception("Pinned: " + this + " is set to be cleanup")));
      }

   }

   public final void forcedCleanup() {
      this.testNeeded = true;
      if (this.kill_because_hung) {
         this.forceDestroy();
      }

   }

   public void setResourceCleanupHandler(ResourceCleanupHandler hdlr) {
      if (this.cleanupHdlr != this) {
         this.cleanupHdlr = hdlr;
      }

   }

   public ResourceCleanupHandler getResourceCleanupHandler() {
      return this.cleanupHdlr;
   }

   public void setInUse() {
      this.hang_state = CON_STATE_IN_USE;
      this.con_in_use = true;
   }

   public void setNotInUse() {
      this.hang_state = CON_STATE_IN_USE;
      this.con_in_use = false;
   }

   public boolean isInUse() {
      return this.con_in_use;
   }

   public final void setUsed(boolean newVal) {
      this.hang_state = CON_STATE_IN_USE;
      this.kill_because_hung = false;
      if (!this.con_in_use) {
         this.connUsed = newVal;
      } else {
         this.connUsed = true;
      }

   }

   public void setAsHangSuspect() {
      this.hang_state = CON_STATE_HANG_SUSPECT;
      this.kill_because_hung = true;
      this.con_in_use = false;
   }

   public final boolean getUsed() {
      if (this.hang_state == CON_STATE_HANG_SUSPECT) {
         this.hang_state = CON_STATE_IN_USE;
         return true;
      } else {
         return this.isInUse() ? true : this.connUsed;
      }
   }

   public boolean isPooled() {
      return this.getPoolName() != null;
   }

   public String getPoolName() {
      return this.poolname;
   }

   public String getAppName() {
      return this.appname;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getCompName() {
      return this.compName;
   }

   public int getVendorId() {
      return this.vendorId;
   }

   public void setPoolName(String val) {
      this.poolname = val;
   }

   public void setAppName(String val) {
      this.appname = val;
   }

   public void setModuleName(String val) {
      this.moduleName = val;
   }

   public void setCompName(String val) {
      this.compName = val;
   }

   ConnectionState getState() {
      return this.conn == null ? null : this.conn.state;
   }

   void setState(ConnectionState aState) {
      if (this.conn != null) {
         this.conn.state = aState;
      }

   }

   public void setJTS() {
      this.isJTS = true;
   }

   public boolean isInfected() {
      return this.infected;
   }

   public void setInfected(boolean val) {
      this.infected = val;
      ConnectionPoolProfiler profiler = (ConnectionPoolProfiler)this.getConnectionPool().getProfiler();
      if (profiler.isResourceUnwrapUsageProfilingEnabled()) {
         profiler.addConnUnwrapUsageData(StackTraceUtils.throwable2StackTrace(new Exception()), this.getCurrentUser(), new Date());
      }

   }

   public void setVendorId(int vid) {
      this.vendorId = vid;
      if (this.stmtCacheSize > 0) {
         if (this.vendorId != 5 && this.vendorId != 103 && this.vendorId != 12 && this.vendorId != 108) {
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               DriverManager.println((new JDBCTextTextFormatter()).debugMessage("DEBUG Initialized statement cache of size (" + this.stmtCacheSize + ") for connection <" + this + "> for pool <" + this.poolname + ">"));
            }
         } else {
            this.stmtCacheSize = 0;
         }
      } else if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         DriverManager.println((new JDBCTextTextFormatter()).debugMessage("DEBUG statement caching disabled for new connection <" + this + "> for pool <" + this.poolname + ">"));
      }

      int[] newcodes;
      int i;
      if (this.vendorId != 100 && this.vendorId != 0 && this.vendorId != 11) {
         if (this.vendorId != 7 && this.vendorId != 15 && this.vendorId != 112 && this.vendorId != 9 && this.vendorId != 105) {
            if (this.vendorId == 13 || this.vendorId == 16 || this.vendorId == 113 || this.vendorId == 110) {
               i = 0;
               if (this.fatalErrorCodes != null) {
                  for(newcodes = new int[this.fatalErrorCodes.length + 9]; i < this.fatalErrorCodes.length; ++i) {
                     newcodes[i] = this.fatalErrorCodes[i];
                  }
               } else {
                  newcodes = new int[9];
               }

               newcodes[i] = -79735;
               newcodes[i + 1] = -79716;
               newcodes[i + 2] = -43207;
               newcodes[i + 3] = -27002;
               newcodes[i + 4] = -25580;
               newcodes[i + 5] = -4499;
               newcodes[i + 6] = -908;
               newcodes[i + 7] = -710;
               newcodes[i + 8] = 43012;
               this.fatalErrorCodes = newcodes;
            }
         } else {
            i = 0;
            if (this.fatalErrorCodes != null) {
               for(newcodes = new int[this.fatalErrorCodes.length + 18]; i < this.fatalErrorCodes.length; ++i) {
                  newcodes[i] = this.fatalErrorCodes[i];
               }
            } else {
               newcodes = new int[18];
            }

            newcodes[i] = -4498;
            newcodes[i + 1] = -4499;
            newcodes[i + 2] = -1776;
            newcodes[i + 3] = -30108;
            newcodes[i + 4] = -30081;
            newcodes[i + 5] = -30080;
            newcodes[i + 6] = -6036;
            newcodes[i + 7] = -1229;
            newcodes[i + 8] = -1224;
            newcodes[i + 9] = -1035;
            newcodes[i + 10] = -1034;
            newcodes[i + 11] = -1015;
            newcodes[i + 12] = -924;
            newcodes[i + 13] = -923;
            newcodes[i + 14] = -906;
            newcodes[i + 15] = -518;
            newcodes[i + 16] = -514;
            newcodes[i + 17] = 58004;
            this.fatalErrorCodes = newcodes;
         }
      } else {
         i = 0;
         if (this.fatalErrorCodes != null) {
            for(newcodes = new int[this.fatalErrorCodes.length + 7]; i < this.fatalErrorCodes.length; ++i) {
               newcodes[i] = this.fatalErrorCodes[i];
            }
         } else {
            newcodes = new int[7];
         }

         newcodes[i] = 3113;
         newcodes[i + 1] = 3114;
         newcodes[i + 2] = 1033;
         newcodes[i + 3] = 1034;
         newcodes[i + 4] = 1089;
         newcodes[i + 5] = 1090;
         newcodes[i + 6] = 17002;
         this.fatalErrorCodes = newcodes;
      }

   }

   public void setConnection(java.sql.Connection c) throws ResourceException {
      if (this.conn == null) {
         this.conn = new ConnectionHolder();
      }

      this.conn.jconn = c;
   }

   public void setConnectionLate() throws ResourceException {
      java.sql.Connection c = this.conn.jconn;
      if (c != null) {
         this.pool.putCachedPooledResource(c, this);
         this.initializeGroups();
         if (isJDBC4Runtime() && c != null) {
            this.initializeDefaultClientInfo(c);
            this.checkStatementPoolable(c);
         }

         this.labels.clear();

         try {
            this.pdb = new ConnectionEnvPDB(this.pool, this);
         } catch (NoClassDefFoundError var3) {
         } catch (Throwable var4) {
            var4.printStackTrace();
         }

      }
   }

   public void setLastSuccessfulConnectionUse() {
      this.lastSuccessfulConnectionUse = System.currentTimeMillis();
      this.pool.zeroResetFailCount();
   }

   public void resetLastSuccessfulConnectionUse() {
      this.lastSuccessfulConnectionUse = 0L;
   }

   public void cancelStatements() {
      if (this.stmtCacheSize != 0) {
         synchronized(this.stmtCacheLock) {
            Iterator iter = this.stmtCache.values().iterator();

            while(true) {
               if (!iter.hasNext()) {
                  break;
               }

               StatementHolder sh = (StatementHolder)iter.next();
               if (!sh.getInUse()) {
                  try {
                     sh.getStatement().cancel();
                  } catch (SQLException var7) {
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        StringBuffer sb = new StringBuffer();
                        sb.append("Exception when cancelling a cached statement for the pool ");
                        sb.append(this.poolname);
                        sb.append(": ");
                        sb.append(var7.toString());
                        JdbcDebug.JDBCCONN.debug(sb.toString());
                     }
                  }
               }
            }
         }

         if (this.userCon != null) {
            this.userCon.cancelAllStatements();
         }

      }
   }

   public void setUserCon(Connection userConn) {
      this.userCon = userConn;
   }

   protected void setSecondsToTrustAnIdlePoolConnection(int value) {
      this.secondsToTrustAnIdlePoolConnection = (long)value;
   }

   protected void clearTestStatement() {
      if (!this.destroyed && this.test_ps != null) {
         try {
            this.test_ps.close();
         } catch (Exception var2) {
         }
      }

      this.test_ps = null;
   }

   public int test(String sqlQuery) {
      if (this.destroyed) {
         return -1;
      } else if (this.refreshNeeded) {
         return 0;
      } else {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" > CE:test (10) sqlQuery = " + sqlQuery);
         }

         if (sqlQuery == null) {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug(" < CE:test (20) returns 0");
            }

            return 0;
         } else {
            if (this.secondsToTrustAnIdlePoolConnection > 0L) {
               long secondsSinceOk = (System.currentTimeMillis() - this.lastSuccessfulConnectionUse) / 1000L;
               if (secondsSinceOk < this.secondsToTrustAnIdlePoolConnection) {
                  if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                     JDBCUtil.JDBCInternal.debug(" < CE:test (23) returns 0");
                  }

                  return 0;
               }
            }

            return this.testInternal(sqlQuery);
         }
      }
   }

   int testDynamic(String sqlQuery) {
      if (this.destroyed) {
         return -1;
      } else if (this.refreshNeeded) {
         return 0;
      } else {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" > CE:test (10) sqlQuery = " + sqlQuery);
         }

         this.clearTestStatement();
         return this.testInternal(sqlQuery);
      }
   }

   int testInternal(String sqlQuery) {
      Exception aex = null;
      this.xaExceptionDuringTesting = null;
      java.sql.Connection jconn = this.conn.jconn;
      if (jconn == null) {
         if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
            JdbcDebug.enter(this.poolname, "Testing connection finds conn.jconn is null");
         }

         return -1;
      } else {
         if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
            JdbcDebug.enter(this.poolname, "Testing connection " + jconn);
         }

         boolean needDetach = false;
         if (!this.attached) {
            try {
               this.OracleAttachServerConnection();
            } catch (Exception var32) {
               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug(" <* CE:test (15) returns -1");
               }

               return -1;
            }

            needDetach = true;
         }

         boolean suspendTx = false;
         Transaction curTx = null;
         Xid xid = null;
         XAResource xar = null;
         TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
         boolean ping = false;
         boolean isvalid = false;
         if (sqlQuery != null && sqlQuery.toLowerCase().trim().equals("pingdatabase")) {
            ping = true;
         } else if (sqlQuery != null && sqlQuery.toLowerCase().trim().equals("isvalid")) {
            isvalid = true;
         }

         String s;
         try {
            if (!ping && !isvalid && this.isXA) {
               if (tm.getTransaction() != null) {
                  curTx = (Transaction)tm.forceSuspend();
                  suspendTx = true;
               }

               s = this.poolname;
               if (s == null || s.equals("")) {
                  s = "weblogic.jdbc.xa.test";
               }

               xid = XIDFactory.createXID(s);
               xar = ((XAConnection)jconn).getXAResource();
               xar.start(xid, 0);
               this.test_ps = null;
            }

            if (!ping && !isvalid && this.test_ps == null) {
               this.test_ps = jconn.prepareStatement(sqlQuery);
               if (this.dsBean != null) {
                  int queryTimeout = this.dsBean.getJDBCConnectionPoolParams().getStatementTimeout();
                  if (queryTimeout > -1) {
                     try {
                        this.test_ps.setQueryTimeout(queryTimeout);
                     } catch (SQLException var31) {
                        JDBCLogger.logSetQueryTOFailed(var31.toString());
                     }
                  }
               }
            }

            this.setInUse();
            ResultSet r = null;
            if (ping) {
               if (!this.conn.hasPingDatabaseMethod) {
                  throw new Exception(JDBCUtil.getTextFormatter().pingNotSupported());
               }

               if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
                  JdbcDebug.JDBCSQL.debug("[" + jconn + "] pingDatabase()");
               }

               if ((Integer)this.conn.pingDatabase.invoke(jconn, 0) != this.conn.pingDatabaseOk) {
                  if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
                     JdbcDebug.JDBCSQL.debug("[" + jconn + "] " + this.BADPING);
                  }

                  throw new Exception(this.BADPING);
               }

               if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
                  JdbcDebug.JDBCSQL.debug("[" + jconn + "] pingDatabase returns OK");
               }
            } else if (isvalid) {
               if (!this.supportIsValid) {
                  throw new Exception(JDBCUtil.getTextFormatter().isValidNotSupported());
               }

               if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
                  JdbcDebug.JDBCSQL.debug("[" + jconn + "] isValid()");
               }

               if (!jconn.isValid(0)) {
                  if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
                     JdbcDebug.JDBCSQL.debug("[" + jconn + "] " + this.BADISVALID);
                  }

                  throw new Exception(this.BADISVALID);
               }

               if (JdbcDebug.JDBCSQL.isDebugEnabled()) {
                  JdbcDebug.JDBCSQL.debug("[" + jconn + "] isValid returns true");
               }
            } else {
               try {
                  this.test_ps.execute();
               } catch (SQLException var34) {
                  this.clearTestStatement();
                  this.clearStatement(false, sqlQuery, -1, -1);
                  String s = var34.toString();
                  if (sqlQuery == null || s == null || s.indexOf("no longer valid") == -1 && s.indexOf("altered") == -1) {
                     throw var34;
                  }

                  this.test_ps = jconn.prepareStatement(sqlQuery);
                  this.test_ps.execute();
               }

               r = this.test_ps.getResultSet();
            }

            this.setNotInUse();
            if (r != null) {
               r.close();
            }

            this.lastSuccessfulConnectionUse = System.currentTimeMillis();
         } catch (XAException var35) {
            this.xaExceptionDuringTesting = var35;
            this.clearTestStatement();
         } catch (Exception var36) {
            aex = var36;
            this.clearTestStatement();
         } finally {
            if (this.drcpEnabled) {
               this.clearTestStatement();
            }

         }

         if (!ping && !isvalid && this.isXA) {
            this.clearTestStatement();

            try {
               xar.end(xid, 67108864);
            } catch (Exception var30) {
            }

            try {
               xar.commit(xid, true);
            } catch (Exception var29) {
            }

            if (suspendTx) {
               tm.forceResume(curTx);
            }

            if (this.destroyed) {
               return -1;
            }
         } else if (!ping && !isvalid) {
            if (this.destroyed) {
               return -1;
            }

            if (!this.autoCommit) {
               s = VendorId.toString(this.vendorId);
               if (s.toUpperCase(Locale.ENGLISH).indexOf("OCI") == -1 && s.toUpperCase(Locale.ENGLISH).indexOf("ORACLE") == -1) {
                  try {
                     jconn.rollback();
                  } catch (Exception var28) {
                  }
               }
            }
         }

         if (aex == null && this.xaExceptionDuringTesting == null) {
            if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
               JdbcDebug.leave(this.poolname, "Testing connection " + jconn);
            }

            if (needDetach) {
               try {
                  this.OracleDetachServerConnection();
               } catch (Exception var33) {
                  if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                     JDBCUtil.JDBCInternal.debug(" <* CE:test (35) returns -1");
                  }

                  return -1;
               }
            }

            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug(" < CE:test (40) returns 1");
            }

            return 1;
         } else {
            if (this.xaExceptionDuringTesting != null) {
               s = XAUtils.appendOracleXAResourceInfo(this.xaExceptionDuringTesting);
               if (s != null) {
                  if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
                     JdbcDebug.err((String)this.poolname, "Testing connection got XAException " + jconn + s, this.xaExceptionDuringTesting);
                  }

                  if (!this.destroyed && this.enabled) {
                     JDBCLogger.logTestFailed(this.poolname, sqlQuery, s);
                  }
               }
            } else if (aex != null) {
               if (JdbcDebug.isEnabled((String)this.poolname, 20)) {
                  JdbcDebug.err((String)this.poolname, "Testing connection got Exception " + jconn, aex);
               }

               if (!this.destroyed && this.enabled) {
                  s = aex.toString();
                  if (s != null) {
                     if (s.indexOf(this.BADISVALID) != -1) {
                        s = this.BADISVALID;
                     } else if (s.indexOf(this.BADPING) != -1) {
                        s = this.BADPING;
                     }
                  }

                  JDBCLogger.logTestFailed(this.poolname, sqlQuery, s);
               }
            }

            if (needDetach) {
               try {
                  this.OracleDetachServerConnection();
               } catch (Exception var27) {
               }
            }

            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug(" <* CE:test (30) returns -1");
            }

            return -1;
         }
      }
   }

   public void checkIfEnabled() throws SQLException {
      this.setUsed(true);
      String reason;
      if (this.destroyed) {
         reason = "unknown reason";
         String myWhereDestroyed = this.where_destroyed;
         if (myWhereDestroyed == null) {
            myWhereDestroyed = " just destroyed, but no stacktrace yet.";
         }

         if (myWhereDestroyed.indexOf("processFailCountExceededDisableThreshold") != -1) {
            reason = JDBCUtil.getTextFormatter().tooManyFailures();
         } else if (myWhereDestroyed.indexOf("disablePoolDroppingUsers") != -1) {
            reason = JDBCUtil.getTextFormatter().failureMDS();
         } else if (this.kill_because_hung) {
            reason = JDBCUtil.getTextFormatter().hungThreads();
         } else {
            reason = JDBCUtil.getTextFormatter().byConsole();
         }

         throw new SQLException(JDBCUtil.getTextFormatter().adminDestroyed(reason, myWhereDestroyed));
      } else if (!this.enabled) {
         reason = JDBCUtil.getTextFormatter().byConsole();
         throw new SQLRecoverableException(JDBCUtil.getTextFormatter().adminDisabled(reason, this.where_disabled));
      }
   }

   void setInitialIsolationLevel(int isolationLevel) {
      this.initialIsolationLevel = isolationLevel;
      this.dirtyIsolationLevel = false;
   }

   public void setDirtyIsolationLevel(int isolationLevel) {
      this.dirtyIsolationLevel = this.initialIsolationLevel != isolationLevel;
   }

   public boolean getDirtyIsolationLevel() {
      return this.dirtyIsolationLevel;
   }

   public void setConnectTime(long aTime) {
      this.connectTime = aTime;
   }

   public long getCreationTime() {
      return this.connectTime;
   }

   void setIdentity(String id, AuthenticatedSubject user) throws SQLException {
      if (this.pool.isCredentialMappingEnabled() && user != null) {
         if (this.dsBean != null && this.dsBean.getJDBCOracleParams().isUseDatabaseCredentials()) {
            Set set = user.getPrincipals();
            Iterator itr = set.iterator();
            if (itr.hasNext()) {
               this.clientID = itr.next().toString();
            }

            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug("ConnectionEnv: set client database id='" + this.clientID + "'.");
            }
         } else {
            Vector dbmsClients = this.pool.getDBMSIdentity(user);
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug("ConnectionEnv: getDBMSIndetity for " + user + " return with " + dbmsClients);
            }

            if (dbmsClients != null && !dbmsClients.isEmpty()) {
               if (id == null) {
                  this.clientID = ((PasswordCredential)dbmsClients.elementAt(0)).getUserName();
               } else {
                  this.clientID = id;

                  int i;
                  for(i = 0; i < dbmsClients.size() && !this.clientID.equals(((PasswordCredential)dbmsClients.elementAt(i)).getUserName()); ++i) {
                  }

                  if (i >= dbmsClients.size()) {
                     this.clientID = null;
                  }
               }
            } else {
               this.clientID = null;
            }
         }

         try {
            if (!this.isXA) {
               this.setIdentity();
            }

         } catch (Throwable var5) {
            throw new SQLException(JDBCUtil.getTextFormatter().identityNotSupported(), var5);
         }
      }
   }

   public void setIdentity() throws SQLException {
      java.sql.Connection jconn = this.conn.jconn;
      if (jconn == null) {
         throw new SQLException(JDBCUtil.getTextFormatter().connectionClosed());
      } else if (this.pool.isCredentialMappingEnabled()) {
         try {
            if (this.clientID == null) {
               if (this.oldClientID != null) {
                  if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                     JDBCUtil.JDBCInternal.debug("ConnectionEnv: Clear DBMS Identity");
                  }

                  this.pool.clearDBMSIdentity(jconn, this.oldClientID, this.initClientID);
               }
            } else if (!this.clientID.equals(this.oldClientID)) {
               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug("ConnectionEnv: Set DBMS Identity as " + this.clientID);
               }

               if (this.initClientID == null) {
                  this.initClientID = this.pool.setDBMSIdentity(jconn, this.clientID);
               } else {
                  this.pool.setDBMSIdentity(jconn, this.clientID);
               }

               this.saveClientId = this.clientID;
            }

            this.oldClientID = this.clientID;
         } catch (Throwable var3) {
            throw new SQLException(JDBCUtil.getTextFormatter().identityNotSupported(), var3);
         }
      }
   }

   void setConnectionPool(JDBCConnectionPool pool) {
      this.pool = pool;
      this.wrapTypes = pool.isWrapTypes();
      this.wrapJdbc = pool.isWrapJdbc();
      this.profiler = (ConnectionPoolProfiler)pool.getProfiler();
      this.dsBean = pool.getJDBCDataSource();
   }

   public final JDBCConnectionPool getConnectionPool() {
      return (ConnectionPool)this.pool;
   }

   public Throwable getLastUser() {
      return this.lastUser;
   }

   void setLastUser(Throwable t) {
      this.lastUser = t;
   }

   public Throwable getCurrentError() {
      return this.currentError;
   }

   public void setCurrentError(Throwable t) {
      this.currentError = t;
   }

   public void setCurrentErrorTimestamp(Date d) {
      this.currentErrorTimestamp = d;
   }

   public Date getCurrentErrorTimestamp() {
      return this.currentErrorTimestamp;
   }

   public String getCurrentUser() {
      if (this.currentUser != null && this.currentUserString == null) {
         this.currentUserString = StackTraceUtils.throwable2StackTrace(this.currentUser);
      }

      return this.currentUserString;
   }

   public String getCurrentThread() {
      return this.currentThread;
   }

   public long getReservingThreadId() {
      return this.reservingThreadId;
   }

   void setupInitSQL() {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CE:setupInitSQL (10) ");
      }

      if (this.initQuery != null && !this.initQuery.trim().equals("")) {
         if (this.initQuery.indexOf("SQL ") == 0) {
            this.initQuery = this.initQuery.substring(4);
         } else {
            this.initQuery = "select count(*) from " + this.initQuery;
         }

         this.doInit = true;
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < CE:setupInitSQL (100) initQuery = " + this.initQuery);
         }

      } else {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < CE:setupInitSQL (20) ");
         }

      }
   }

   boolean isConnTested() {
      return this.pool.getResourceFactory().getTestQuery() != null ? this.pool.areConnsBeingTested() : false;
   }

   void initStmtCache() {
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         if (this.stmtCacheSize > 0) {
            JDBCLogger.logStmtCacheEnabled(this.poolname, this.stmtCacheSize);
         } else {
            JDBCLogger.logDisablingStmtCache(this.poolname);
         }
      }

   }

   public Object getCachedStatement(boolean isCallable, String sql) throws SQLException {
      return this.getCachedStatement(isCallable, sql, -1, -1);
   }

   public Object getCachedStatement(boolean isCallable, String sql, int resType, int resConcurrency) throws SQLException {
      return this.getCachedStatement(isCallable, sql, resType, resConcurrency, -1, -1, (int[])null, (String[])null);
   }

   public Object getCachedStatement(boolean isCallable, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability, int autoGeneratedKeys, int[] columnIndexes, String[] columnNames) throws SQLException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CE:getCS (10) isCallable = " + isCallable + ", sql = " + sql + ", resultSetType = " + resultSetType + ", resultSetConcurrency = " + resultSetConcurrency + ", resultSetHoldability = " + resultSetHoldability + ", autoGeneratedKeys = " + autoGeneratedKeys + ", columnIndexes = " + Arrays.toString(columnIndexes) + ", columnNames = " + Arrays.toString(columnNames));
      }

      if (this.stmtCacheSize == 0) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < CE:getCS (100.1)");
         }

         return this.makeStatement(isCallable, sql, resultSetType, resultSetConcurrency, resultSetHoldability, autoGeneratedKeys, columnIndexes, columnNames);
      } else {
         StatementCacheKey key = new StatementCacheKey(isCallable, sql, resultSetType, resultSetConcurrency, resultSetHoldability, autoGeneratedKeys, columnIndexes, columnNames);
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug("  CE:getCS (25) key = " + key);
         }

         boolean addToCache = true;
         StatementHolder entry;
         synchronized(this.stmtCacheLock) {
            if (this.stmtCacheSize == 0) {
               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug(" < CE:getCS (26)");
               }

               return this.makeStatement(isCallable, sql, resultSetType, resultSetConcurrency, resultSetHoldability, autoGeneratedKeys, columnIndexes, columnNames);
            }

            ++this.cacheAccessCount;
            entry = (StatementHolder)this.stmtCache.get(key);
            if (entry != null) {
               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug("  CE:getCS (30) entry = " + entry);
               }

               if (!entry.getInUse()) {
                  entry.setInUse();
                  entry.incrementHitCount();
                  ++this.cacheHitCount;
                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     DriverManager.println((new JDBCTextTextFormatter()).debugMessage("DEBUG Statement Cache hit (SQL=" + sql + ")"));
                  }

                  if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                     JDBCUtil.JDBCInternal.debug("<  CE:getCS (100.2) entry = " + entry);
                  }

                  return entry;
               }

               addToCache = false;
            }

            ++this.cacheMissCount;
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               DriverManager.println((new JDBCTextTextFormatter()).debugMessage("DEBUG Statement Cache miss (SQL=" + sql + ")"));
            }

            if (this.stmtCacheType == 1 && this.stmtCache.size() == this.stmtCacheSize) {
               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug(" < CE:getCS (100.3)");
               }

               return this.makeStatement(isCallable, sql, resultSetType, resultSetConcurrency, resultSetHoldability, autoGeneratedKeys, columnIndexes, columnNames);
            }

            Statement stmt = this.makeStatement(isCallable, sql, resultSetType, resultSetConcurrency, resultSetHoldability, autoGeneratedKeys, columnIndexes, columnNames);
            entry = new StatementHolder(stmt, key, addToCache, true);
            if (addToCache) {
               StatementHolder ejectedEntry = (StatementHolder)this.stmtCache.put(key, entry);
               ++this.cacheAddCount;
               if (this.profiler.isStmtCacheProfilingEnabled()) {
                  if (ejectedEntry != null) {
                     this.profiler.deleteStmtCacheEntryData(ejectedEntry);
                  }

                  this.profiler.addStmtCacheEntryData(entry);
               }

               if (ejectedEntry != null) {
                  ++this.cacheDeleteCount;
               }

               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug("  CE:getCS (50) ejectedEntry = " + ejectedEntry);
               }

               if (ejectedEntry != null && !ejectedEntry.getInUse()) {
                  if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                     JDBCUtil.JDBCInternal.debug("  CE:getCS (60)");
                  }

                  try {
                     ejectedEntry.getStatement().close();
                  } catch (SQLException var18) {
                     String s = var18.toString();
                     if ((s == null || s.indexOf("not implemented") == -1) && JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JDBCLogger.logStmtCloseFailed(this.poolname, var18.toString());
                     }
                  }
               } else if (ejectedEntry != null) {
                  ejectedEntry.setToBeClosed();
               }
            }
         }

         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < CE:getCS (100.4) entry = " + entry);
         }

         return entry;
      }
   }

   public void returnCachedStatement(StatementHolder entry) {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CE:retCS (10) entry = " + entry);
      }

      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug("  CE:retCS (20) key = " + entry.getKey());
      }

      synchronized(this.stmtCacheLock) {
         entry.clearInUse();
         if (!entry.toBeClosed()) {
            this.cleanUpStatementForReUse((PreparedStatement)((PreparedStatement)entry.getStatement()));
         }

         String s;
         if (this.stmtCacheSize == 0) {
            try {
               entry.getStatement().close();
            } catch (SQLException var7) {
               s = var7.toString();
               if ((s == null || s.indexOf("not implemented") == -1) && JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  JDBCLogger.logStmtCloseFailed(this.poolname, var7.toString());
               }
            }

            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug(" <  CE:retCS (25) key = " + entry.getKey());
            }

            return;
         }

         if (entry.toBeClosed()) {
            if (!entry.wasClosed()) {
               try {
                  entry.closed();
                  Statement st1 = entry.getStatement();
                  if (st1 != null) {
                     st1.close();
                  }
               } catch (SQLException var10) {
                  s = var10.toString();
                  if ((s == null || s.indexOf("not implemented") == -1) && JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JDBCLogger.logStmtCloseFailed(this.poolname, var10.toString());
                  }
               }
            }
         } else if (!this.stmtCache.containsKey(entry.getKey())) {
            entry.setCached();
            StatementHolder ejectedEntry = (StatementHolder)this.stmtCache.put(entry.getKey(), entry);
            ++this.cacheAddCount;
            if (this.profiler.isStmtCacheProfilingEnabled()) {
               if (ejectedEntry != null) {
                  this.profiler.deleteStmtCacheEntryData(ejectedEntry);
               }

               this.profiler.addStmtCacheEntryData(entry);
            }

            if (ejectedEntry != null) {
               ++this.cacheDeleteCount;
            }

            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug("  CE:retCS (30) ejectedEntry = " + ejectedEntry);
            }

            if (ejectedEntry != null && !ejectedEntry.getInUse()) {
               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug("  CE:retCS (40)");
               }

               try {
                  ejectedEntry.setToBeClosed();
                  ejectedEntry.closed();
                  Statement st = ejectedEntry.getStatement();
                  if (st != null) {
                     st.close();
                  }
               } catch (SQLException var9) {
                  String s = var9.toString();
                  if ((s == null || s.indexOf("not implemented") == -1) && JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JDBCLogger.logStmtCloseFailed(this.poolname, var9.toString());
                  }
               }
            } else if (ejectedEntry != null) {
               ejectedEntry.setToBeClosed();
               ejectedEntry.clearCached();
            }
         } else if (!entry.getCached()) {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug("  CE:retCS (50)");
            }

            try {
               entry.setToBeClosed();
               entry.closed();
               entry.getStatement().close();
            } catch (SQLException var8) {
               s = var8.toString();
               if ((s == null || s.indexOf("not implemented") == -1) && JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  JDBCLogger.logStmtCloseFailed(this.poolname, var8.toString());
               }
            }
         }
      }

      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CE:retCS (100) ");
      }

   }

   public void clearIdentity() {
      try {
         java.sql.Connection jconn = this.conn.jconn;
         if (jconn == null) {
            return;
         }

         this.clientID = null;
         this.oldClientID = null;
         if (this.initClientID != null) {
            this.pool.setDBMSIdentity(jconn, this.initClientID);
         } else {
            this.pool.setDBMSIdentity(jconn, "");
         }
      } catch (Throwable var2) {
      }

      this.saveClientId = null;
   }

   public void clearCache() {
      this.clearTestStatement();
      if (this.stmtCacheSize != 0) {
         synchronized(this.stmtCacheLock) {
            Iterator iter = this.stmtCache.values().iterator();

            while(true) {
               StatementHolder sh;
               do {
                  if (!iter.hasNext()) {
                     this.stmtCache.clear();
                     return;
                  }

                  sh = (StatementHolder)iter.next();
               } while(sh.getInUse());

               try {
                  sh.closed();
                  sh.setToBeClosed();
                  sh.getStatement().close();
               } catch (SQLException var7) {
                  String s = var7.toString();
                  if ((s == null || s.indexOf("not implemented") == -1) && JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JDBCLogger.logStmtCloseFailed(this.poolname, var7.toString());
                  }
               }
            }
         }
      }
   }

   public int getStatementCacheSize() {
      synchronized(this.stmtCacheLock) {
         return this.stmtCacheSize == 0 ? 0 : this.stmtCache.size();
      }
   }

   public void setStatementCacheSize(int newCapacity) {
      if (newCapacity != this.stmtCacheSize) {
         synchronized(this.stmtCacheLock) {
            if (this.stmtCacheSize == 0) {
               this.stmtCacheSize = newCapacity;
               this.stmtCache = new SecondChanceCacheMap(this.stmtCacheSize);
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  JDBCLogger.logStmtCacheEnabled(this.poolname, this.stmtCacheSize);
               }

               return;
            }

            if (newCapacity < this.stmtCache.size()) {
               this.shrinkCache(this.stmtCache.size() - newCapacity);
            }

            SecondChanceCacheMap newCache = new SecondChanceCacheMap(newCapacity);
            newCache.putAll(this.stmtCache);
            this.stmtCache = newCache;
            this.stmtCacheSize = newCapacity;
         }

         if (this.stmtCacheSize == 0) {
            JDBCLogger.logStmtCacheDisabled(this.poolname);
         }

      }
   }

   public long getPrepStmtCacheAccessCount() {
      return this.cacheAccessCount;
   }

   public long getPrepStmtCacheAddCount() {
      return this.cacheAddCount;
   }

   public long getPrepStmtCacheDeleteCount() {
      return this.cacheDeleteCount;
   }

   public int getPrepStmtCacheCurrentSize() {
      synchronized(this.stmtCacheLock) {
         return this.stmtCache == null ? 0 : this.stmtCache.size();
      }
   }

   public int getPrepStmtCacheHits() {
      return this.cacheHitCount;
   }

   public int getPrepStmtCacheMisses() {
      return this.cacheMissCount;
   }

   private Statement makeStatement(boolean isCallable, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability, int autoGeneratedKeys, int[] columnIndexes, String[] columnNames) throws SQLException {
      java.sql.Connection jconn = this.conn.jconn;
      if (jconn == null) {
         throw new SQLException(JDBCUtil.getTextFormatter().connectionClosed());
      } else if (isCallable) {
         if (resultSetType == -1 && resultSetConcurrency == -1 && resultSetHoldability == -1) {
            return jconn.prepareCall(sql);
         } else {
            return resultSetHoldability == -1 ? jconn.prepareCall(sql, resultSetType, resultSetConcurrency) : jconn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
         }
      } else if (columnNames != null) {
         return jconn.prepareStatement(sql, columnNames);
      } else if (columnIndexes != null) {
         return jconn.prepareStatement(sql, columnIndexes);
      } else if (autoGeneratedKeys != -1) {
         return jconn.prepareStatement(sql, autoGeneratedKeys);
      } else if (resultSetType == -1 && resultSetConcurrency == -1 && resultSetHoldability == -1) {
         return jconn.prepareStatement(sql);
      } else {
         return resultSetHoldability == -1 ? jconn.prepareStatement(sql, resultSetType, resultSetConcurrency) : jconn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      }
   }

   private void cleanUpStatementForReUse(PreparedStatement p) {
      try {
         p.clearParameters();
         p.clearBatch();
      } catch (Throwable var15) {
      }

      try {
         p.setEscapeProcessing(true);
      } catch (Throwable var14) {
      }

      try {
         if (p.getFetchDirection() != 1000) {
            p.setFetchDirection(1000);
         }
      } catch (Throwable var13) {
      }

      try {
         if (p.getFetchSize() != 0) {
            p.setFetchSize(0);
         }
      } catch (Throwable var12) {
      }

      try {
         if (p.getMaxFieldSize() != 0) {
            p.setMaxFieldSize(0);
         }
      } catch (Throwable var11) {
      }

      try {
         if (p.getMaxRows() != 0) {
            p.setMaxRows(0);
         }
      } catch (Throwable var10) {
      }

      try {
         if (p.getQueryTimeout() != 0) {
            p.setQueryTimeout(0);
         }
      } catch (Throwable var9) {
      }

      try {
         p.clearWarnings();
      } catch (Throwable var8) {
      }

      Method[] meths;
      if (this.weHaveToResetOracleStatements) {
         try {
            meths = null;
            Object myInt = this.getExecuteBatch.invoke(p, meths);
            if (myInt instanceof Integer && (Integer)myInt != 1) {
               this.setExecuteBatch.invoke(p, this.oo);
            }
         } catch (Exception var7) {
            this.weHaveToResetOracleStatements = false;
            this.weKnowWeCanSkipOracleBatchReset = true;
         }
      } else if (!this.weKnowWeCanSkipOracleBatchReset && (this.vendorId == 100 || this.vendorId == 0)) {
         try {
            meths = p.getClass().getMethods();
            boolean we_need_to_reset = false;

            int i;
            for(i = 0; i < meths.length; ++i) {
               if (meths[i].getName().equals("getExecuteBatch")) {
                  Object[] nullo = null;
                  Object myInt = meths[i].invoke(p, (Object[])nullo);
                  if (myInt instanceof Integer && (Integer)myInt != 1) {
                     we_need_to_reset = true;
                  }

                  this.getExecuteBatch = meths[i];
                  this.weHaveToResetOracleStatements = true;
               }
            }

            for(i = 0; i < meths.length; ++i) {
               if (meths[i].getName().equals("setExecuteBatch")) {
                  this.oo = new Object[1];
                  this.oo[0] = Integer.valueOf("1");
                  if (we_need_to_reset) {
                     meths[i].invoke(p, this.oo);
                  }

                  this.setExecuteBatch = meths[i];
                  this.weHaveToResetOracleStatements = true;
               }
            }

            if (!this.weHaveToResetOracleStatements) {
               this.weKnowWeCanSkipOracleBatchReset = true;
            }
         } catch (Exception var16) {
            this.weKnowWeCanSkipOracleBatchReset = true;
         }
      }

   }

   private void shrinkCache(int numToShrink) {
      Object[] keyList = new Object[numToShrink];
      Iterator iter = this.stmtCache.keySet().iterator();

      int i;
      for(i = 0; i < numToShrink && iter.hasNext(); ++i) {
         keyList[i] = iter.next();
      }

      for(i = 0; i < numToShrink; ++i) {
         StatementHolder sh = (StatementHolder)this.stmtCache.remove(keyList[i]);
         if (!sh.getInUse()) {
            try {
               sh.getStatement().close();
            } catch (SQLException var8) {
               String s = var8.toString();
               if ((s == null || s.indexOf("not implemented") == -1) && JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  JDBCLogger.logStmtCloseFailed(this.poolname, var8.toString());
               }
            }
         }
      }

   }

   public boolean clearStatement(boolean isCallable, String sql, int resType, int resConcurrency) {
      return this.clearStatement(isCallable, sql, resType, resConcurrency, false);
   }

   public boolean clearStatement(boolean isCallable, String sql, int resType, int resConcurrency, boolean forceClose) {
      return this.clearStatement(new StatementCacheKey(isCallable, sql, resType, resConcurrency), forceClose);
   }

   public boolean clearStatement(boolean isCallable, String sql, int resType, int resConcurrency, int resultSetHoldability, int autoGeneratedKeys, int[] culumnIndexes, String[] columnNames, boolean forceClose) {
      return this.clearStatement(new StatementCacheKey(isCallable, sql, resType, resConcurrency, resultSetHoldability, autoGeneratedKeys, culumnIndexes, columnNames), forceClose);
   }

   public boolean clearStatement(weblogic.jdbc.wrapper.Statement statement) {
      return this.clearStatement(statement, false);
   }

   public boolean clearStatement(weblogic.jdbc.wrapper.Statement statement, boolean forceClose) {
      return this.clearStatement(new StatementCacheKey(statement), forceClose);
   }

   public boolean clearStatement(StatementCacheKey key, boolean forceClose) {
      StatementHolder sh = null;
      boolean stmtRemoved = false;
      if (this.stmtCacheSize == 0) {
         return stmtRemoved;
      } else {
         try {
            synchronized(this.stmtCacheLock) {
               sh = (StatementHolder)this.stmtCache.remove(key);
            }

            if (sh != null) {
               stmtRemoved = true;
               if (!sh.getInUse() || forceClose) {
                  sh.getStatement().close();
               }
            }
         } catch (SQLException var8) {
            String s = var8.toString();
            if ((s == null || s.indexOf("not implemented") == -1) && JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JDBCLogger.logStmtCloseFailed(this.poolname, var8.toString());
            }
         }

         return stmtRemoved;
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("switchingContext=" + this.switchingContext).append(",");
      sb.append("autoCommit=" + this.autoCommit).append(",");
      sb.append("enabled=" + this.enabled).append(",");
      sb.append("isXA=" + this.isXA).append(",");
      sb.append("isJTS=" + this.isJTS).append(",");
      sb.append("vendorID=" + this.vendorId).append(",");
      sb.append("connUsed=" + this.connUsed).append(",");
      sb.append("doInit=" + this.doInit).append(",");
      sb.append("initQuery=" + this.initQuery != null ? "'" + this.initQuery + "'" : "").append(",");
      sb.append("destroyed=" + this.destroyed).append(",");
      sb.append("poolname=" + this.poolname).append(",");
      sb.append("appname=" + this.appname).append(",");
      sb.append("moduleName=" + this.moduleName).append(",");
      sb.append("connectTime=" + this.connectTime).append(",");
      sb.append("dirtyIsolationLevel=" + this.dirtyIsolationLevel).append(",");
      sb.append("initialIsolationLevel=" + this.initialIsolationLevel).append(",");
      sb.append("infected=" + this.infected).append(",");
      sb.append("lastSuccessfulConnectionUse=" + this.lastSuccessfulConnectionUse).append(",");
      sb.append("secondsToTrustAnIdlePoolConnection=" + this.secondsToTrustAnIdlePoolConnection).append(",");
      sb.append("currentUser=" + this.getCurrentUser()).append(",");
      sb.append("currentThread=" + this.currentThread).append(",");
      sb.append("lastUser=" + this.lastUser).append(",");
      sb.append("currentError=" + this.currentError).append(",");
      sb.append("currentErrorTimestamp=" + this.currentErrorTimestamp).append(",");
      sb.append("JDBC4Runtime=" + JDBC4Runtime).append(",");
      sb.append("supportStatementPoolable=" + this.supportStatementPoolable).append(",");
      sb.append("needRestoreClientInfo=" + this.needRestoreClientInfo).append(",");
      sb.append("defaultClientInfo=" + this.defaultClientInfo).append(",");
      sb.append("supportIsValid=" + this.supportIsValid);
      return sb.toString();
   }

   public void infect() {
      if (!this.isInfected()) {
         try {
            JDBCConnectionPool pool = ConnectionPoolManager.getPool(this.getPoolName(), this.getAppName(), this.getModuleName(), this.getCompName());
            if (pool.isRemoveInfectedConnectionEnabled()) {
               this.setInfected(true);
               this.setRefreshNeeded(true);
               pool.removeConnection(this);
               if (!pool.isCreateConnectionInline()) {
                  WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
                     public void run() {
                        try {
                           PooledResourceInfo[] infoList = new PooledResourceInfo[1];
                           ConnectionPoolManager.getPool(ConnectionEnv.this.getPoolName(), ConnectionEnv.this.getAppName(), ConnectionEnv.this.getModuleName(), ConnectionEnv.this.getCompName()).createResources(1, infoList);
                        } catch (Exception var2) {
                        }

                     }
                  });
               }
            }
         } catch (Exception var2) {
            JDBCLogger.logStackTrace(var2);
         }

      }
   }

   private static boolean checkJDBC4Runtime() {
      try {
         Class.forName("java.sql.SQLXML");
         return true;
      } catch (ClassNotFoundException var1) {
         return false;
      }
   }

   public static boolean isJDBC4Runtime() {
      return JDBC4Runtime;
   }

   public void setRestoreClientInfoFlag() {
      this.needRestoreClientInfo = true;
   }

   private void initializeDefaultClientInfo(java.sql.Connection conn) {
      if (this.isClientInfoValid) {
         try {
            this.defaultClientInfo = conn.getClientInfo();
         } catch (Throwable var3) {
            this.isClientInfoValid = false;
         }

         this.needRestoreClientInfo = false;
      }

   }

   private void checkStatementPoolable(java.sql.Connection conn) {
      this.supportStatementPoolable = true;
      Statement stmt = null;

      try {
         stmt = conn.createStatement();
         stmt.setPoolable(true);
      } catch (Throwable var12) {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("Statement.isPoolable() failed", var12);
         }

         if (var12 instanceof AbstractMethodError) {
            this.supportStatementPoolable = false;
         }
      } finally {
         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException var11) {
            }
         }

      }

   }

   public boolean supportStatementPoolable() {
      return this.supportStatementPoolable;
   }

   public boolean supportIsValid() {
      return this.supportIsValid;
   }

   public void setSupportIsValid(boolean value) {
      this.supportIsValid = value;
   }

   public void setDestroyAfterRelease() {
      this.destroyAfterRelease = true;
   }

   public boolean needDestroyAfterRelease() {
      return this.destroyAfterRelease;
   }

   public void setRepurposeOnRelease(boolean value) {
      this.repurposeOnRelease = value;
   }

   public boolean isRepurposeOnRelease() {
      return this.repurposeOnRelease;
   }

   public ResourcePoolGroup getPrimaryGroup() {
      return this.primaryGroup;
   }

   public Collection getGroups() {
      return this.groups.values();
   }

   public ResourcePoolGroup getGroup(String category) {
      return (ResourcePoolGroup)this.groups.get(category);
   }

   public void initializeGroups() throws ResourceException {
      if (this.pool != null && this.pool.isSharedPool()) {
         if (this.switchingContext != null && this.switchingContext.getPDBName() != null) {
            this.primaryGroup = this.pool.getOrCreateGroup("service_pdbname", JDBCUtil.getServicePDBGroupName(this.switchingContext.getPDBServiceName(), this.switchingContext.getPDBName()));
            this.addGroup(this.primaryGroup);
         } else {
            if (this.primaryGroup != null) {
               this.removeGroup("service_pdbname");
            }

            this.primaryGroup = null;
         }
      } else {
         this.primaryGroup = null;
      }

   }

   public ResourcePoolGroup setPrimaryGroup(ResourcePoolGroup primary) {
      ResourcePoolGroup oldPrimary = this.primaryGroup;
      this.primaryGroup = primary;
      return oldPrimary;
   }

   public ResourcePoolGroup addGroup(ResourcePoolGroup group) {
      return (ResourcePoolGroup)this.groups.put(group.getCategoryName(), group);
   }

   public ResourcePoolGroup removeGroup(String category) {
      return (ResourcePoolGroup)this.groups.remove(category);
   }

   protected void clearGroups() {
      this.groups.clear();
      this.primaryGroup = null;
   }

   public boolean isWrapTypes() {
      return !this.wrapJdbc ? false : this.wrapTypes;
   }

   public void setWrapTypes(boolean wrapTypes) {
      this.wrapTypes = wrapTypes;
   }

   public boolean isWrapJdbc() {
      return this.wrapJdbc;
   }

   public void setWrapJdbc(boolean wrapJdbc) {
      this.wrapJdbc = wrapJdbc;
   }

   public void setNeedsLabelingConfigure(boolean c) {
      this.needsConfigure = c;
   }

   public boolean isNeedsLabelingConfigure() {
      return this.needsConfigure;
   }

   public Properties getLabels() {
      return (Properties)this.labels.clone();
   }

   public void addLabel(String key, String value) {
      this.labels.put(key, value);
   }

   public int[] getFatalErrorCodes() {
      return this.fatalErrorCodes;
   }

   public void removeLabel(String key) {
      this.labels.remove(key);
   }

   public void setConnectionHarvestable(boolean connectionHarvestable) throws SQLException {
      synchronized(this.harvestLock) {
         if (!connectionHarvestable && this.connectionHarvested) {
            throw new SQLException(JDBCUtil.getTextFormatter().connectionClosed());
         } else {
            this.connectionHarvestable = connectionHarvestable;
         }
      }
   }

   public boolean isConnectionHarvestable() throws SQLException {
      return this.connectionHarvestable;
   }

   public boolean isConnectionHarvestableAndLock() throws SQLException {
      synchronized(this.harvestLock) {
         if (this.connectionHarvestable) {
            this.connectionHarvested = true;
         }

         return this.connectionHarvestable;
      }
   }

   public void registerConnectionHarvestingCallback(ConnectionHarvestingCallback cbk) throws SQLException {
      this.connectionHarvestingCallback = cbk;
   }

   public void removeConnectionHarvestingCallback() throws SQLException {
      this.connectionHarvestingCallback = null;
   }

   public void registerConnectionHarvestedCallback(Connection cbk) {
      this.connectionHarvestedCallback = cbk;
   }

   public XAException getXAExceptionDuringTesting() {
      return this.xaExceptionDuringTesting;
   }

   public void labelConfigure(ConnectionLabelingCallback clc, Properties labels, Object conn) throws SQLException {
      try {
         if (this.inCallback) {
            return;
         }

         this.inCallback = true;
         if (conn instanceof java.sql.Connection) {
            clc.configure(labels, this.wrap((java.sql.Connection)conn));
         } else {
            clc.configure(labels, conn);
         }
      } finally {
         this.inCallback = false;
      }

   }

   public void connectionInitialize(ConnectionInitializationCallback initializationCallback) throws SQLException {
      try {
         if (this.inCallback) {
            return;
         }

         this.inCallback = true;
         initializationCallback.initialize(this.wrap(this.conn.jconn));
      } finally {
         this.inCallback = false;
      }

   }

   private java.sql.Connection wrap(java.sql.Connection c) throws SQLException {
      if (!(c instanceof WLConnection)) {
         PoolConnection poolConnection = (PoolConnection)JDBCWrapperFactory.getWrapper(0, c, false);
         poolConnection.initTransient(this);
         this.setResourceCleanupHandler(poolConnection);
         c = (java.sql.Connection)poolConnection;
      }

      return c;
   }

   public void replayAutoCommit() {
      if (this.isXA || this.isJTS) {
         try {
            this.conn.jconn.setAutoCommit(false);
         } catch (Exception var2) {
         }

         this.autoCommit = false;
      }

   }

   public int getReplayAttemptCount() {
      return this.replayAttemptCount;
   }

   public SwitchingContext getSwitchingContext() {
      return this.switchingContext;
   }

   public void setSwitchingContext(SwitchingContext sc) {
      this.switchingContext = sc;
   }

   public SwitchingContext getRootSwitchingContext() {
      return this.rootSwitchingContext;
   }

   void setRootSwitchingContext(SwitchingContext sc) {
      this.rootSwitchingContext = sc;
   }

   public void setNext(EmbeddedListElement next) {
      this.next = next;
   }

   public EmbeddedListElement getNext() {
      return this.next;
   }

   public void setPrev(EmbeddedListElement prev) {
      this.prev = prev;
   }

   public EmbeddedListElement getPrev() {
      return this.prev;
   }

   public void setList(EmbeddedList list) {
      this.list = list;
   }

   public EmbeddedList getList() {
      return this.list;
   }

   public void resetStatistics() {
      this.cacheHitCount = 0;
      this.cacheMissCount = 0;
      this.cacheAccessCount = 0L;
      this.cacheAddCount = 0L;
      this.cacheDeleteCount = 0L;
   }

   static {
      JDBC4Runtime = checkJDBC4Runtime();
      CON_STATE_IN_USE = 1;
      CON_STATE_IDLE_SUSPECT = 2;
      CON_STATE_HANG_SUSPECT = 3;
   }

   private class TimeoutExecutor implements Executor {
      private TimeoutExecutor() {
      }

      public void execute(Runnable runnable) {
         runnable.run();
      }

      // $FF: synthetic method
      TimeoutExecutor(Object x1) {
         this();
      }
   }
}
