package weblogic.jdbc.jts;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.JDBCTextTextFormatter;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.ConnectionPoolManager;
import weblogic.jdbc.common.internal.JDBCHelper;
import weblogic.jdbc.common.internal.JDBCUtil;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.extensions.PoolUnavailableSQLException;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.JTSConnection;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.utils.StackTraceUtils;

/** @deprecated */
@Deprecated
public final class Driver implements java.sql.Driver {
   private static final String POOLURL = "jdbc:weblogic:jts";
   private static final boolean remoteEnabled;
   private static final String XIDPROP = "weblogic.jts.xid";
   private static final String JDBCURLPROP = "weblogic.jdbc.remote";
   private static final String JDBCLLRPROP = "weblogic.jdbc.llr";
   private static final String REMOTEDATASOURCE = "weblogic.jts.remotedatasource";
   private static java.sql.Driver poolDriver;
   private static final boolean DEBUGROUTING = false;
   private static String myurl;
   static final long serialVersionUID = 2723748279880996708L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.jts.Driver");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Connection_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Driver_Connect_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Before_Connection_Internal;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public Connection connect(String url, Properties props) throws SQLException {
      LocalHolder var9;
      Object[] var10000;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var9.argsCapture) {
            var9.args = new Object[3];
            var10000 = var9.args;
            var10000[0] = this;
            var10000[1] = url;
            var10000[2] = props;
         }

         if (var9.monitorHolder[1] != null) {
            var9.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var9);
            InstrumentationSupport.preProcess(var9);
         }

         if (var9.monitorHolder[2] != null) {
            var9.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var9);
            InstrumentationSupport.process(var9);
         }

         var9.resetPostBegin();
      }

      Connection var14;
      label444: {
         label445: {
            label446: {
               label447: {
                  label448: {
                     label449: {
                        label450: {
                           try {
                              JTSConnection conn = null;

                              try {
                                 if (!this.acceptsURL(url)) {
                                    var10000 = null;
                                    break label450;
                                 }

                                 if (props != null && props.size() == 0) {
                                    props = null;
                                 }

                                 Xid xid = null;
                                 Transaction tx = this.getTransaction(props);
                                 if (tx != null) {
                                    xid = tx.getXID();
                                 }

                                 if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                                    JdbcDebug.JDBCCONN.debug("JTS/JDBC Connect: url = " + url + ", tx = " + tx + ", props = " + props);
                                 }

                                 String poolName = this.getPoolName(url, props);
                                 if (xid != null) {
                                    conn = getExistingConnection(poolName, tx, props);
                                    if (conn != null) {
                                       var14 = (Connection)this.copyLocalConnection(conn, tx, poolName, props);
                                       break label444;
                                    }

                                    String remoteConnServerURL = getRemoteConnServerURL(tx, poolName);
                                    if (remoteConnServerURL != null && !remoteConnServerURL.equals(getMyURL())) {
                                       if (isLLRAlreadyParticipating(tx)) {
                                          String remoteLLRPoolName = getLLRAlreadyParticipating(tx);
                                          if (!isLLRCapable(props)) {
                                             throw new SQLException("JDBC non-XA non-LLR connection pool '" + poolName + "' can't participate in the current transaction.   There is a LLR connection pool at '" + remoteConnServerURL + "' with pool name '" + remoteLLRPoolName + "' participating in the transaction.  A non-XA non-LLR connection can't participate in the same transaction as an LLR connection.");
                                          }

                                          if (!remoteLLRPoolName.equals(poolName)) {
                                             throw new SQLException("JDBC LLR connection pool '" + poolName + "' can't participate in the current transaction.   There is already a LLR connection pool at '" + remoteConnServerURL + "' with a different pool name '" + remoteLLRPoolName + "' participating in the transaction, but only one LLR resource may participate in a transaction at a time.");
                                          }

                                          var14 = this.getLLRConnectionFromXACoordinator(tx, poolName, props, remoteConnServerURL);
                                          break label445;
                                       }

                                       if (isLLRCapable(props)) {
                                          throw new SQLException("JDBC LLR connection pool '" + poolName + "' can't participate in the current transaction.   There is already a non-LLR non-XA connection pool at  '" + remoteConnServerURL + "' participating in the transaction.");
                                       }

                                       var14 = (Connection)this.createRemoteConnection(tx, poolName, remoteConnServerURL, props);
                                       break label446;
                                    }

                                    if (isLLRCapable(props) && tx.isCoordinatorAssigned() && !tx.isCoordinatorLocal()) {
                                       if (!remoteEnabled) {
                                          throw new SQLException("Unable to obtain LLR connection for data source " + poolName + " on transaction coordinator for " + tx.getXid() + ".  Remote JDBC disabled.");
                                       }

                                       var14 = this.getLLRConnectionFromXACoordinator(tx, poolName, props, remoteConnServerURL);
                                       break label448;
                                    }

                                    var14 = (Connection)createLocalConnection(tx, poolName, props);
                                    break label449;
                                 }

                                 var14 = getNonTxConnection(poolName, props);
                              } catch (Exception var12) {
                                 wrapAndThrowSQLException(var12);
                                 var10000 = null;
                                 break label447;
                              }
                           } catch (Throwable var13) {
                              if (var9 != null) {
                                 var9.th = var13;
                                 var9.ret = null;
                                 if (var9.monitorHolder[1] != null) {
                                    var9.monitorIndex = 1;
                                    InstrumentationSupport.postProcess(var9);
                                 }

                                 if (var9.monitorHolder[0] != null) {
                                    var9.monitorIndex = 0;
                                    InstrumentationSupport.createDynamicJoinPoint(var9);
                                    InstrumentationSupport.process(var9);
                                 }
                              }

                              throw var13;
                           }

                           if (var9 != null) {
                              var9.ret = var14;
                              if (var9.monitorHolder[1] != null) {
                                 var9.monitorIndex = 1;
                                 InstrumentationSupport.postProcess(var9);
                              }

                              if (var9.monitorHolder[0] != null) {
                                 var9.monitorIndex = 0;
                                 InstrumentationSupport.createDynamicJoinPoint(var9);
                                 InstrumentationSupport.process(var9);
                              }
                           }

                           return var14;
                        }

                        if (var9 != null) {
                           var9.ret = var10000;
                           if (var9.monitorHolder[1] != null) {
                              var9.monitorIndex = 1;
                              InstrumentationSupport.postProcess(var9);
                           }

                           if (var9.monitorHolder[0] != null) {
                              var9.monitorIndex = 0;
                              InstrumentationSupport.createDynamicJoinPoint(var9);
                              InstrumentationSupport.process(var9);
                           }
                        }

                        return var10000;
                     }

                     if (var9 != null) {
                        var9.ret = var14;
                        if (var9.monitorHolder[1] != null) {
                           var9.monitorIndex = 1;
                           InstrumentationSupport.postProcess(var9);
                        }

                        if (var9.monitorHolder[0] != null) {
                           var9.monitorIndex = 0;
                           InstrumentationSupport.createDynamicJoinPoint(var9);
                           InstrumentationSupport.process(var9);
                        }
                     }

                     return var14;
                  }

                  if (var9 != null) {
                     var9.ret = var14;
                     if (var9.monitorHolder[1] != null) {
                        var9.monitorIndex = 1;
                        InstrumentationSupport.postProcess(var9);
                     }

                     if (var9.monitorHolder[0] != null) {
                        var9.monitorIndex = 0;
                        InstrumentationSupport.createDynamicJoinPoint(var9);
                        InstrumentationSupport.process(var9);
                     }
                  }

                  return var14;
               }

               if (var9 != null) {
                  var9.ret = var10000;
                  if (var9.monitorHolder[1] != null) {
                     var9.monitorIndex = 1;
                     InstrumentationSupport.postProcess(var9);
                  }

                  if (var9.monitorHolder[0] != null) {
                     var9.monitorIndex = 0;
                     InstrumentationSupport.createDynamicJoinPoint(var9);
                     InstrumentationSupport.process(var9);
                  }
               }

               return var10000;
            }

            if (var9 != null) {
               var9.ret = var14;
               if (var9.monitorHolder[1] != null) {
                  var9.monitorIndex = 1;
                  InstrumentationSupport.postProcess(var9);
               }

               if (var9.monitorHolder[0] != null) {
                  var9.monitorIndex = 0;
                  InstrumentationSupport.createDynamicJoinPoint(var9);
                  InstrumentationSupport.process(var9);
               }
            }

            return var14;
         }

         if (var9 != null) {
            var9.ret = var14;
            if (var9.monitorHolder[1] != null) {
               var9.monitorIndex = 1;
               InstrumentationSupport.postProcess(var9);
            }

            if (var9.monitorHolder[0] != null) {
               var9.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var9);
               InstrumentationSupport.process(var9);
            }
         }

         return var14;
      }

      if (var9 != null) {
         var9.ret = var14;
         if (var9.monitorHolder[1] != null) {
            var9.monitorIndex = 1;
            InstrumentationSupport.postProcess(var9);
         }

         if (var9.monitorHolder[0] != null) {
            var9.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var9);
            InstrumentationSupport.process(var9);
         }
      }

      return var14;
   }

   public static Connection connect(String poolName) throws SQLException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var2.argsCapture) {
            var2.args = new Object[1];
            var2.args[0] = poolName;
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      Connection var10000;
      try {
         String driverURL = "jdbc:weblogic:pool:" + poolName;
         var10000 = getPoolDriver().connect(driverURL, (Properties)null);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            var2.ret = null;
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         var2.ret = var10000;
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }
      }

      return var10000;
   }

   public static Connection createLocalConnection(String poolName, Properties props) throws SQLException {
      Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      Xid xid = tx == null ? null : tx.getXid();
      if (xid == null) {
         return getNonTxConnection(poolName, props);
      } else {
         String remoteConnServerURL = getRemoteConnServerURL(tx, poolName);
         if (remoteConnServerURL != null) {
            JTSConnection conn = getExistingConnection(poolName, tx, props);
            if (conn != null) {
               return (Connection)conn;
            } else {
               try {
                  tx.setRollbackOnly();
               } catch (SystemException var7) {
               }

               throw new SQLException("transaction rolled back");
            }
         } else {
            return (Connection)createLocalConnection(tx, poolName, props);
         }
      }
   }

   private static JTSConnection createLocalConnection(Transaction tx, String poolName, Properties props) throws SQLException {
      String applicationName = null;
      String moduleName = null;
      String compName = null;
      String poolScope = props != null ? props.getProperty("connectionPoolScope") : null;
      if (poolScope != null && poolScope.equalsIgnoreCase("application")) {
         applicationName = (String)props.get("applicationName");
         moduleName = (String)props.get("moduleName");
         compName = (String)props.get("compName");
      }

      String username = null;
      String password = null;
      boolean useDatabaseCredentials = false;
      Properties labels = null;
      if (props != null) {
         labels = (Properties)props.get("RequestedLabels");
         useDatabaseCredentials = Boolean.valueOf((String)props.get("useDatabaseCredentials"));
         if (useDatabaseCredentials) {
            username = (String)props.get("user");
            password = (String)props.get("password");
         }
      }

      JTSConnection conn = newConnection(poolName, tx, applicationName, moduleName, compName, labels, username, password);
      conn.setEmulateTwoPhaseCommit(isEmulate2PCCapable(props));
      String dsName = props != null ? props.getProperty("dataSourceName") : null;
      conn.setDataSourceName(dsName);
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug("enlist. pool = " + poolName + ", tx=" + tx + ", llr=" + isLLRCapable(props));
      }

      Exception e = null;

      try {
         if (isLLRCapable(props)) {
            tx.enlistResource(conn.getLoggingResource());
            setLLRAlreadyParticipating(tx, poolName);
         } else {
            tx.enlistResource(conn.getXAResource());
         }

         setRemoteConnServerURL(tx, poolName);
      } catch (RollbackException var16) {
         e = var16;
      } catch (IllegalStateException var17) {
         e = var17;
      } catch (SystemException var18) {
         e = var18;
      } catch (Exception var19) {
         e = var19;
      }

      if (e != null) {
         try {
            conn.close(true);
         } catch (Exception var15) {
         }

         wrapAndThrowSQLException((Exception)e);
      }

      return conn;
   }

   private JTSConnection copyLocalConnection(JTSConnection jconn, Transaction tx, String poolName, Properties props) throws SQLException {
      boolean enable2pc = false;
      String applicationName;
      if (props != null) {
         applicationName = (String)props.get("enableTwoPhaseCommit");
         if ("true".equals(applicationName)) {
            enable2pc = true;
         }
      }

      applicationName = null;
      String poolScope = props != null ? props.getProperty("connectionPoolScope") : null;
      if (poolScope != null && poolScope.equalsIgnoreCase("application")) {
         applicationName = (String)props.get("applicationName");
      }

      JTSConnection jts_conn = this.copyConnection(jconn, poolName, applicationName, tx);
      String dsName = props != null ? props.getProperty("dataSourceName") : null;
      jts_conn.setDataSourceName(dsName);
      return jts_conn;
   }

   public JTSConnection copyConnection(JTSConnection conn, String poolName, String applicationName, Transaction wtx) throws SQLException {
      JTSConnection jtsConnection = (JTSConnection)JDBCWrapperFactory.getWrapper(1, conn.getConnection(), false);
      jtsConnection.initCopy(conn, wtx, poolName, applicationName);
      return jtsConnection;
   }

   private JTSConnection createRemoteConnection(Transaction tx, String poolName, String remoteConnServerURL, Properties dbprops) throws SQLException {
      try {
         Connection sqlconn = null;
         String dsJndiName = null;

         try {
            if (tx != null && tx.getStatus() != 0 && (tx.getStatus() != 1 || tx.getProperty("DISABLE_TX_STATUS_CHECK") == null)) {
               throw new SQLException("No JDBC connection can be made\nbecause the transaction state is\n" + status2String(tx.getStatus()));
            }
         } catch (SystemException var15) {
            JDBCLogger.logStackTrace(var15);
            throw new SQLException("fail to create jts connection");
         }

         java.sql.Driver myDriver = (java.sql.Driver)Class.forName("weblogic.jdbc.rmi.Driver").newInstance();
         String url = "jdbc:weblogic:rmi";
         Properties rmiProps = new Properties();
         rmiProps.put("weblogic.server.url", remoteConnServerURL);
         Exception re = null;
         dsJndiName = this.getJNDIName(poolName);
         if (dsJndiName != null) {
            rmiProps.put("weblogic.jdbc.datasource", dsJndiName);
            rmiProps.put("weblogic.jts.remotedatasource", "yes");
            String partitionName = dbprops.getProperty("PartitionName");
            if (partitionName != null) {
               rmiProps.setProperty("weblogic.partition.name", partitionName);
            }

            try {
               sqlconn = myDriver.connect(url, rmiProps);
            } catch (Exception var13) {
               re = var13;
            }
         }

         if (sqlconn == null) {
            if (re != null) {
               throw new SQLException(re);
            } else {
               throw new SQLException("Could not get remote server connection url = " + remoteConnServerURL);
            }
         } else {
            try {
               if (tx != null && tx.getStatus() != 0 && (tx.getStatus() != 1 || tx.getProperty("DISABLE_TX_STATUS_CHECK") == null)) {
                  throw new SQLException("No JDBC connection can be made\nbecause the transaction state is\n" + status2String(tx.getStatus()));
               }
            } catch (SystemException var14) {
               JDBCLogger.logStackTrace(var14);
               throw new SQLException("fail to create jts connection");
            }

            JTSConnection conn = this.newConnection(poolName, sqlconn, tx);
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("remoteConn. registerSync, tx = " + tx + ", props = " + dbprops + ", conn = " + sqlconn);
            }

            if (tx != null) {
               tx.registerSynchronization(conn);
            }

            conn.setPool(poolName);
            return conn;
         }
      } catch (Exception var16) {
         wrapAndThrowSQLException(var16);
         return null;
      }
   }

   private Transaction getTransaction(Properties props) throws SQLException {
      Transaction tx = null;
      if (props != null) {
         Xid xid = (Xid)props.get("weblogic.jts.xid");
         if (xid != null) {
            tx = (Transaction)((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).getTransaction(xid);
            if (tx == null) {
               throw new SQLException("Transaction ' " + xid + "' is no longer alive");
            }
         }
      }

      if (tx == null) {
         tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      }

      try {
         if (tx != null && tx.getStatus() != 0 && (tx.getStatus() != 1 || tx.getProperty("DISABLE_TX_STATUS_CHECK") == null)) {
            throw new SQLException("Transaction " + tx.getXID() + " not active anymore. tx status = " + tx.getStatusAsString());
         }
      } catch (SystemException var4) {
         wrapAndThrowSQLException(var4);
      }

      return tx;
   }

   private String getJNDIName(String poolName) {
      Class c;
      try {
         c = Class.forName("weblogic.jdbc.common.internal.DataSourceManager");
      } catch (ClassNotFoundException var7) {
         return poolName;
      }

      try {
         Method m = c.getMethod("getInstance");
         Object instance = m.invoke((Object)null);
         Method m2 = c.getMethod("getJNDINameFromPoolName", String.class);
         return (String)m2.invoke(instance, (Object[])(new String[]{poolName}));
      } catch (Exception var6) {
         throw new AssertionError(var6);
      }
   }

   private static void wrapAndThrowSQLException(Exception e) throws SQLException {
      wrapAndThrowSQLException(e.getMessage(), e);
   }

   private static void wrapAndThrowSQLException(String s, Exception e) throws SQLException {
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug("Exception:", e);
      }

      if (e instanceof PoolUnavailableSQLException) {
         throw (SQLException)e;
      } else if (e instanceof SQLException) {
         if (s.equals(e.getMessage())) {
            throw (SQLException)e;
         } else {
            SQLException sqe = new SQLException(s);
            ((SQLException)e).setNextException(sqe);
            throw (SQLException)e;
         }
      } else {
         throw new SQLException(s + ".\nNested Exception: " + StackTraceUtils.throwable2StackTrace(e));
      }
   }

   private static JTSConnection getExistingConnection(String poolName, Transaction tx, Properties props) throws SQLException {
      JTSConnection conn = null;
      if ((conn = JTSConnection.getConnAssociatedWithTx(poolName, tx)) != null) {
         if (poolName.equals(conn.getPool())) {
            return conn;
         }

         if (!isEmulate2PCCapable(props)) {
            throw new SQLException("Connection has already been created in this tx context for pool named " + conn.getPool() + ". Illegal attempt to create connection from another pool: " + poolName);
         }
      }

      return null;
   }

   private static Connection getNonTxConnection(String poolName, Properties props) throws SQLException {
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug("Transaction = null. Returning an ordinary JDBC conn");
      }

      Properties dbprops = null;
      String driverURL = null;

      try {
         if (poolName == null) {
            if (props != null) {
               driverURL = (String)props.get("weblogic.jts.driverURL");
               dbprops = (Properties)props.get("weblogic.jts.dbprops");
            }

            java.sql.Driver d = DriverManager.getDriver(driverURL);
            return d.connect(driverURL, dbprops);
         } else if (props != null && props.get("weblogic.jts.supportLocalTran") != null) {
            Properties labels = (Properties)props.get("RequestedLabels");
            return (Connection)newConnection(poolName, (Transaction)null, (String)null, (String)null, (String)null, labels);
         } else {
            driverURL = "jdbc:weblogic:pool:" + poolName;
            return getPoolDriver().connect(driverURL, props);
         }
      } catch (Exception var5) {
         wrapAndThrowSQLException("Cannot obtain connection: driverURL = " + driverURL + ", props = " + props, var5);
         return null;
      }
   }

   private Connection getLLRConnectionFromXACoordinator(Transaction tx, String poolName, Properties props, String remoteConnServerURL) throws SQLException {
      Connection c = null;

      SQLException sqle;
      try {
         boolean useSSL = JDBCHelper.getHelper().isRMISecure();
         c = (Connection)tx.invokeCoordinatorService(poolName, props, useSSL);
      } catch (SystemException var8) {
         sqle = new SQLException(var8.toString());
         sqle.initCause(var8);
         throw sqle;
      } catch (RemoteException var9) {
         sqle = new SQLException(var9.toString());
         sqle.initCause(var9);
         throw sqle;
      }

      if (c == null) {
         throw new SQLException("The requested LLR connection can't participate in the  current transaction.  A JDBC LLR connection  that is already participating in the transaction " + (remoteConnServerURL == null ? "" : " at URL= " + remoteConnServerURL) + " has a different pool name than '" + poolName + "' or there is no same-named pool on the transaction's remote coordinator or remote JDBC is disabled.");
      } else {
         return c;
      }
   }

   private static String getJDBCURLProp(String poolName) {
      return "weblogic.jdbc.remote." + poolName;
   }

   private static String getRemoteConnServerURL(Transaction tx, String poolName) {
      return (String)tx.getProperty(getJDBCURLProp(poolName));
   }

   private static void setRemoteConnServerURL(Transaction tx, String poolName) {
      tx.setProperty(getJDBCURLProp(poolName), getMyURL());
   }

   private static String getMyURL() {
      if (myurl == null) {
         myurl = JDBCHelper.getHelper().getDefaultURL();
      }

      return myurl;
   }

   private static void setLLRAlreadyParticipating(Transaction tx, String poolName) {
      tx.setProperty("weblogic.jdbc.llr", poolName);
   }

   private static boolean isLLRAlreadyParticipating(Transaction tx) {
      return tx.getProperty("weblogic.jdbc.llr") != null;
   }

   private static String getLLRAlreadyParticipating(Transaction tx) {
      return (String)tx.getProperty("weblogic.jdbc.llr");
   }

   private String getPoolName(String url, Properties props) {
      String poolName = null;
      if (!url.endsWith(":jts")) {
         poolName = url.substring(url.indexOf(":jts:") + ":jts:".length());
      } else if (props != null) {
         poolName = (String)props.get("weblogic.jts.connectionPoolId");
      }

      return poolName;
   }

   private static boolean isLLRCapable(Properties props) {
      if (props != null) {
         String val = (String)props.get("LoggingLastResource");
         if (val != null) {
            return "true".equals(val);
         }
      }

      return false;
   }

   private static boolean isEmulate2PCCapable(Properties props) {
      if (props != null) {
         String val = (String)props.get("EmulateTwoPhaseCommit");
         if (val != null) {
            return "true".equals(val);
         }
      }

      return false;
   }

   public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
      DriverPropertyInfo[] dpis = new DriverPropertyInfo[3];
      String poolName = this.getPoolName(url, info);
      dpis[0] = new DriverPropertyInfo("connectionPoolID", poolName);
      dpis[0].description = "The ID of the connection pool";
      dpis[0].required = true;
      if (poolName == null) {
         throw new SQLException("connectionPoolID not set in properties or url");
      } else {
         dpis[1] = new DriverPropertyInfo("user", (String)info.get("user"));
         if (dpis[1].value == null) {
            dpis[1].value = "guest";
         }

         dpis[1].description = "The WebLogic user name";
         dpis[2] = new DriverPropertyInfo("password", (String)info.get("password"));
         if (dpis[2].value == null) {
            dpis[2].value = "guest";
         }

         dpis[2].description = "The WebLogic password for the user supplied";
         return dpis;
      }
   }

   public int getMajorVersion() {
      return 1;
   }

   public int getMinorVersion() {
      return 22;
   }

   public boolean jdbcCompliant() {
      return true;
   }

   private static java.sql.Driver getPoolDriver() throws SQLException {
      Class var0 = Driver.class;
      synchronized(Driver.class) {
         try {
            if (poolDriver == null) {
               poolDriver = (java.sql.Driver)Class.forName("weblogic.jdbc.pool.Driver").newInstance();
            }
         } catch (Exception var3) {
            wrapAndThrowSQLException(var3);
         }
      }

      return poolDriver;
   }

   public boolean acceptsURL(String url) {
      return url.startsWith("jdbc:weblogic:jts");
   }

   public JTSConnection newConnection(String poolName, Connection conn, Transaction wtx) throws SQLException {
      JTSConnection jtsConnection = (JTSConnection)JDBCWrapperFactory.getWrapper(1, conn, false);
      jtsConnection.init(poolName, conn, wtx);
      return jtsConnection;
   }

   public static JTSConnection newConnection(String poolId, Transaction tx, String appName, String moduleName, String compName, Properties labels) throws SQLException {
      return newConnection(poolId, tx, appName, moduleName, compName, labels, (String)null, (String)null);
   }

   private static JTSConnection newConnection(String poolId, Transaction tx, String appName, String moduleName, String compName, Properties labels, String username, String password) throws SQLException {
      if (poolId == null) {
         throw new SQLException("JDBC JTS driver has no pool name");
      } else {
         try {
            if (tx != null && tx.getStatus() != 0 && (tx.getStatus() != 1 || tx.getProperty("DISABLE_TX_STATUS_CHECK") == null)) {
               throw new SQLException("No JDBC connection can be made\nbecause the transaction state is\n" + status2String(tx.getStatus()));
            }
         } catch (SystemException var18) {
            JDBCLogger.logStackTrace(var18);
            throw new SQLException("fail to create jts connection from pool " + poolId);
         }

         int timeToLiveSecs = -2;
         if (tx != null) {
            long timeToLiveMillis = tx.getTimeToLiveMillis();
            if (timeToLiveMillis <= 0L) {
               throw new SQLException("No JDBC connection can be made\nbecause the transaction has timed out\n");
            }

            if (timeToLiveMillis > 2000000000L) {
               timeToLiveSecs = (int)(timeToLiveMillis / 1000L);
            } else {
               timeToLiveSecs = (int)((timeToLiveMillis + 999L) / 1000L);
            }
         }

         ConnectionEnv cc = null;

         try {
            cc = ConnectionPoolManager.reserve(poolId, appName, moduleName, compName, timeToLiveSecs, labels, username, password);
            if (cc == null) {
               if (timeToLiveSecs == -2) {
                  throw new SQLException("Cannot obtain connection after waiting for default wait time configured for pool " + poolId);
               }

               throw new SQLException("Cannot obtain connection after " + timeToLiveSecs + " seconds.");
            }

            if (cc.conn == null || cc.conn.jconn == null) {
               throw new SQLException("Connection no longer valid");
            }

            cc.setJTS();
         } catch (Exception var17) {
            String str = null;
            if (timeToLiveSecs == -2) {
               str = "Cannot obtain connection after waiting for default wait time configured for pool " + poolId;
            } else {
               str = "Cannot obtain connection after " + timeToLiveSecs + " seconds.";
            }

            JDBCUtil.wrapAndThrowResourceException(var17, str);
         }

         if (tx != null) {
            int txStatus = 5;

            try {
               txStatus = tx.getStatus();
            } catch (SystemException var15) {
            }

            if (txStatus != 0 && tx.getProperty("DISABLE_TX_STATUS_CHECK") == null) {
               try {
                  ConnectionPoolManager.release(cc);
               } catch (Exception var13) {
                  throw new SQLException("Can't release pool connection!\n" + var13);
               }

               throw new SQLException("No JDBC connection can be made\nbecause the transaction state is\n" + status2String(txStatus));
            }
         }

         JTSConnection jtsConnection = null;

         try {
            jtsConnection = (JTSConnection)JDBCWrapperFactory.getWrapper(1, cc.conn.jconn, false);
            jtsConnection.init(poolId, tx, appName, moduleName, compName, cc);
            cc.setResourceCleanupHandler(jtsConnection);
         } catch (Exception var16) {
            if (cc != null) {
               try {
                  ConnectionPoolManager.release(cc);
               } catch (Exception var14) {
                  throw new SQLException("Can't release pool connection!\n" + var14);
               }
            }
         }

         return jtsConnection;
      }
   }

   private static String status2String(int status) {
      switch (status) {
         case 0:
            return "Active";
         case 1:
            return "Marked Rollback";
         case 2:
            return "Prepared";
         case 3:
            return "Committed";
         case 4:
            return "Rolledback";
         case 5:
            return "Unknown";
         case 6:
            return "No Transaction";
         case 7:
            return "Preparing";
         case 8:
            return "Committing";
         case 9:
            return "Rolling Back";
         default:
            return "Unknown";
      }
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      throw new SQLFeatureNotSupportedException();
   }

   static {
      _WLDF$INST_FLD_JDBC_After_Connection_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Connection_Internal");
      _WLDF$INST_FLD_JDBC_Diagnostic_Driver_Connect_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Driver_Connect_Around_Medium");
      _WLDF$INST_FLD_JDBC_Before_Connection_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Before_Connection_Internal");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Driver.java", "weblogic.jdbc.jts.Driver", "connect", "(Ljava/lang/String;Ljava/util/Properties;)Ljava/sql/Connection;", 106, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Driver_Connect_Around_Medium", "JDBC_Before_Connection_Internal", "JDBC_After_Connection_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), null}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), null}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Connection_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Driver_Connect_Around_Medium, _WLDF$INST_FLD_JDBC_Before_Connection_Internal};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Driver.java", "weblogic.jdbc.jts.Driver", "connect", "(Ljava/lang/String;)Ljava/sql/Connection;", 253, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Driver_Connect_Around_Medium", "JDBC_Before_Connection_Internal", "JDBC_After_Connection_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true)}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true)}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true)})}), (boolean)1);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Connection_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Driver_Connect_Around_Medium, _WLDF$INST_FLD_JDBC_Before_Connection_Internal};
      remoteEnabled = Boolean.valueOf(System.getProperty("weblogic.jdbc.remoteEnabled", "true"));
      poolDriver = null;

      try {
         DriverManager.registerDriver(new Driver());
         new weblogic.jdbc.pool.Driver();
      } catch (SQLException var1) {
         DriverManager.println((new JDBCTextTextFormatter()).driverLoadingError(var1.getClass().getName(), var1.getMessage()));
      }

      myurl = null;
   }
}
