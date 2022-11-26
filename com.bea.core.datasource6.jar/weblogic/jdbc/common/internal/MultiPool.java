package weblogic.jdbc.common.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ObjectLifeCycle;
import weblogic.common.resourcepool.ResourceDeadException;
import weblogic.common.resourcepool.ResourceDisabledException;
import weblogic.common.resourcepool.ResourceLimitException;
import weblogic.common.resourcepool.ResourceUnavailableException;
import weblogic.common.resourcepool.ResourceUnusableException;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.extensions.ConnectionPoolFailoverCallback;
import weblogic.jdbc.jta.DataSource;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.utils.concurrent.locks.NonRecursiveReadWriteLock;

public final class MultiPool implements ObjectLifeCycle, TimerListener {
   private static final String ALGORITHM_FAILOVER = "Failover";
   private static final int ALG_HA_IDX = 0;
   private static final String ALGORITHM_LOAD_BALANCING = "Load-Balancing";
   private static final int ALG_LB_IDX = 1;
   private NonRecursiveReadWriteLock poolsRWLock = new NonRecursiveReadWriteLock();
   private int numOfPools = 0;
   private int nextPoolToUse = 0;
   private List listOfPools = null;
   private String name = null;
   private String appName = null;
   private String compName = null;
   private boolean failoverRequestIfBusy = false;
   private String algorithm = "Failover";
   private boolean closed = true;
   private ConnectionPoolFailoverCallback failoverCB = null;
   private boolean healthChkEnabled = false;
   private Timer healthChkTimer = null;
   private int healthChkFreqSecs = 0;
   private HashMap disabledPools = new HashMap();
   private HashMap disabledPoolReasons = new HashMap();
   private HashMap disabledPoolReconnectFails = new HashMap();
   private HashMap moduleNames = new HashMap();
   private ConnectionPoolManager cpMgr;
   private DataSource dataSource;
   private transient String txPropName = null;
   private transient boolean pinnedToDBInstanceThruTx = false;
   private JDBCDataSourceBean dsBean;
   private static final AuthenticatedSubject KERNELID = getKernelID();

   private static AuthenticatedSubject getKernelID() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   public MultiPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, ConnectionPoolManager mgr) throws ResourceException {
      this.dsBean = dsBean;
      this.name = this.validateName();
      this.validatePoolList();
      this.algorithm = this.validateAlgorithm();
      this.cpMgr = mgr;
      this.appName = appName;
      this.compName = compName;
      this.healthChkFreqSecs = dsBean.getJDBCConnectionPoolParams().getTestFrequencySeconds();
      this.registerFailoverCallbackHandler();
      JDBCLogger.logMultyPoolCreatedInfo(this.name, this.numOfPools, this.algorithm);
      this.txPropName = ("weblogic.jdbc.mp." + this.name).intern();
   }

   public void setModuleNames(HashMap moduleNames) {
      this.moduleNames = moduleNames;
   }

   private void setMPTxProp(Transaction tx, String poolID) {
      if (tx != null) {
         tx.setProperty(this.txPropName, poolID);
      }

   }

   private String getMPTxProp(Transaction tx) {
      String val = null;
      if (tx != null) {
         val = (String)tx.getProperty(this.txPropName);
      }

      return val;
   }

   private String getInfoOnSuspendedDataSources() {
      String header = " The info on suspended datasources, if any is:\n";
      String whys = "";

      Iterator iter;
      String poolName;
      String reason;
      for(iter = this.disabledPoolReasons.keySet().iterator(); iter.hasNext(); whys = whys + " datasource " + poolName + " is suspended because " + reason + "\n") {
         poolName = (String)iter.next();
         reason = (String)((String)this.disabledPoolReasons.get(poolName));
      }

      for(iter = this.disabledPoolReconnectFails.keySet().iterator(); iter.hasNext(); whys = whys + " The last time we tried to revive datasource " + poolName + " " + reason + " ") {
         poolName = (String)iter.next();
         reason = (String)((String)this.disabledPoolReconnectFails.get(poolName));
      }

      return whys;
   }

   public ConnectionEnv findPool(AuthenticatedSubject subject, int waitSecs, String username, String password) throws ResourceException {
      if (this.closed) {
         throw new ResourceDisabledException("MultiPool '" + this.name + "' is closed");
      } else {
         String list_of_errors = "";
         ConnectionEnv cc = null;
         Transaction tx = null;
         String poolToTry = null;
         if (this.pinnedToDBInstanceThruTx) {
            tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
            poolToTry = this.getMPTxProp(tx);
         }

         if (poolToTry != null) {
            JDBCConnectionPool connPool = null;
            ConnectionPoolManager var10000;
            if (this.appName == null) {
               var10000 = this.cpMgr;
               connPool = ConnectionPoolManager.getPool(poolToTry, (String)null, (String)null, (String)null);
            } else {
               var10000 = this.cpMgr;
               connPool = ConnectionPoolManager.getPool(poolToTry, this.appName, this.getModuleName(poolToTry), this.compName);
            }

            if (connPool != null && (!this.disabledPools.containsKey(connPool) || this.healthChkFreqSecs == 0)) {
               try {
                  cc = connPool.reserve(subject, waitSecs);
               } catch (ResourceException var17) {
                  ResourceException re = var17;
                  list_of_errors = list_of_errors + var17.getMessage() + " - ";
                  if (var17 instanceof ResourceDeadException) {
                     try {
                        boolean alreadyDone = false;
                        synchronized(this.disabledPools) {
                           if (!this.disabledPools.containsKey(connPool)) {
                              this.disabledPools.put(connPool, MultiPool.PoolStates.NOT_YET_DISABLED);
                              this.disabledPoolReasons.put(connPool.getName(), "At " + new Date() + " we got " + re.getMessage());
                           } else {
                              alreadyDone = true;
                           }
                        }

                        if (!alreadyDone) {
                           SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedAction() {
                              public Object run() {
                                 MultiPool.this.disablePoolDroppingUsers();
                                 return null;
                              }
                           });
                        }
                     } catch (Exception var16) {
                        JDBCLogger.logDisableFailed(this.name, poolToTry, var16.toString());
                     }
                  }
               }
            } else if (connPool != null && this.disabledPools.containsKey(connPool)) {
               list_of_errors = list_of_errors + "skipping suspended pool " + poolToTry + " ";
            }
         } else {
            try {
               if (this.algorithm.equals("Failover")) {
                  cc = this.searchHighAvail(subject, this.computePerCPWait(waitSecs, 0), username, password);
               } else {
                  cc = this.searchLoadBalance(subject, this.computePerCPWait(waitSecs, 1), username, password);
               }
            } catch (ResourceException var14) {
               throw new ResourceException("No good connections available. " + var14.getMessage() + ".\n" + this.getInfoOnSuspendedDataSources());
            }

            if (this.pinnedToDBInstanceThruTx && tx != null && cc != null) {
               this.setMPTxProp(tx, cc.getPoolName());
            }
         }

         if (cc == null) {
            if (!"".equals(list_of_errors)) {
               list_of_errors = "Errors from datasources tried for this request: " + list_of_errors;
            }

            throw new ResourceException("No good connections available. " + list_of_errors + " " + this.getInfoOnSuspendedDataSources());
         } else {
            return cc;
         }
      }
   }

   private void disablePoolDroppingUsers() {
      HashSet myPoolsToDisable = new HashSet();
      HashSet myPoolsAlreadyDisabled = new HashSet();
      Iterator iter = null;
      synchronized(this.disabledPools) {
         iter = this.disabledPools.keySet().iterator();

         while(iter.hasNext()) {
            ConnectionPool pool = (ConnectionPool)iter.next();
            if (this.disabledPools.get(pool) == MultiPool.PoolStates.NOT_YET_DISABLED) {
               this.disabledPools.put(pool, MultiPool.PoolStates.BEING_DISABLED);
               myPoolsToDisable.add(pool);
            } else if (this.disabledPools.get(pool) == MultiPool.PoolStates.NOT_YET_DISABLED_EXTERNAL_SUSPEND) {
               this.disabledPools.put(pool, MultiPool.PoolStates.BEING_DISABLED_EXTERNAL_SUSPEND);
               myPoolsAlreadyDisabled.add(pool);
            }
         }
      }

      iter = myPoolsToDisable.iterator();

      ConnectionPool pool;
      while(iter.hasNext()) {
         pool = (ConnectionPool)iter.next();
         JDBCLogger.logDisablingPool(this.name, pool.getName());

         try {
            pool.disableDroppingUsers();
         } catch (Exception var11) {
         }

         if (this.healthChkFreqSecs == 0) {
            pool.preventSelfResuming();
         }

         synchronized(this.disabledPools) {
            if (this.healthChkEnabled) {
               this.disabledPools.put(pool, MultiPool.PoolStates.BEEN_DISABLED);
            } else {
               this.disabledPools.remove(pool);
               this.disabledPoolReasons.remove(pool.getName());
            }
         }
      }

      iter = myPoolsAlreadyDisabled.iterator();

      while(iter.hasNext()) {
         pool = (ConnectionPool)iter.next();
         synchronized(this.disabledPools) {
            this.disabledPools.put(pool, MultiPool.PoolStates.BEEN_DISABLED_EXTERNAL_SUSPEND);
         }
      }

   }

   public void start(Object initObj) throws ResourceException {
      this.start(initObj, false);
   }

   public void start(Object initObj, boolean isMemberLLR) throws ResourceException {
      this.setupHealthCheck();
      this.checkLLRDataSource(isMemberLLR);
   }

   public void resume() throws ResourceException {
      this.closed = false;
   }

   public void suspend(boolean unused) throws ResourceException {
      this.closed = true;
   }

   public void forceSuspend(boolean unused) throws ResourceException {
      this.closed = true;
   }

   public void shutdown() throws ResourceException {
      if (this.healthChkTimer != null) {
         try {
            this.healthChkTimer.cancel();
            this.healthChkTimer = null;
         } catch (Exception var2) {
         }
      }

   }

   private ConnectionEnv searchLoadBalance(AuthenticatedSubject user, int waitSecs, String username, String password) throws ResourceException {
      this.poolsRWLock.lockRead();

      try {
         int startPool = this.nextPoolToUse;
         int currPool = startPool;
         String poolToTry = null;
         JDBCConnectionPool connPool = null;
         ConnectionEnv cc = null;
         int head = 0;
         int tail = this.numOfPools;
         boolean initial = true;
         List errors = new ArrayList();
         if (startPool + 1 < this.numOfPools) {
            this.nextPoolToUse = startPool + 1;
         } else {
            this.nextPoolToUse = head;
         }

         while(true) {
            if (cc == null) {
               label263: {
                  if (currPool == startPool && initial) {
                     poolToTry = this.listOfPools.get(currPool++).toString();
                     initial = false;
                  } else {
                     if (currPool == startPool && !initial) {
                        String listMsg = "Errors during this request (if any): ";

                        for(int i = 0; i < errors.size(); ++i) {
                           listMsg = listMsg + (i != errors.size() - 1 && i != 0 ? "," : "") + errors.get(i);
                        }

                        listMsg = "\n" + listMsg;
                        throw new ResourceException(listMsg);
                     }

                     if (currPool >= tail) {
                        if (head == startPool) {
                           break label263;
                        }

                        currPool = head + 1;
                        poolToTry = this.listOfPools.get(head).toString();
                     } else {
                        poolToTry = this.listOfPools.get(currPool++).toString();
                     }
                  }

                  if (poolToTry != null) {
                     ConnectionPoolManager var10000;
                     if (this.appName == null) {
                        var10000 = this.cpMgr;
                        connPool = ConnectionPoolManager.getPool(poolToTry, (String)null, (String)null, (String)null);
                     } else {
                        var10000 = this.cpMgr;
                        connPool = ConnectionPoolManager.getPool(poolToTry, this.appName, this.getModuleName(poolToTry), this.compName);
                     }

                     if (connPool == null || this.disabledPools.containsKey(connPool) && this.healthChkFreqSecs != 0) {
                        continue;
                     }

                     try {
                        cc = connPool.reserve(user, waitSecs, (Properties)null, username, password);
                        if (this.disabledPools.containsKey(connPool)) {
                           synchronized(this.disabledPools) {
                              this.disabledPools.remove(connPool);
                              this.disabledPoolReasons.remove(connPool.getName());
                           }
                        }

                        if (currPool < this.numOfPools) {
                           this.nextPoolToUse = currPool;
                        } else {
                           this.nextPoolToUse = head;
                        }
                     } catch (ResourceException var27) {
                        ResourceException re = var27;
                        if (var27 instanceof ResourceDeadException) {
                           try {
                              boolean alreadyDone = false;
                              synchronized(this.disabledPools) {
                                 if (!this.disabledPools.containsKey(connPool)) {
                                    this.disabledPools.put(connPool, MultiPool.PoolStates.NOT_YET_DISABLED);
                                    this.disabledPoolReasons.put(connPool.getName(), re);
                                    this.disabledPoolReasons.put(connPool.getName(), " At " + new Date() + " we got " + re.getMessage());
                                 } else {
                                    alreadyDone = true;
                                 }
                              }

                              if (!alreadyDone) {
                                 SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedAction() {
                                    public Object run() {
                                       MultiPool.this.disablePoolDroppingUsers();
                                       return null;
                                    }
                                 });
                              }
                           } catch (Exception var25) {
                              JDBCLogger.logDisableFailed(this.name, poolToTry, var25.toString());
                           }
                        }

                        errors.add("\t" + this.name + "(" + poolToTry + "): " + var27.getMessage() + "\n");
                        continue;
                     }

                     if (cc == null) {
                        continue;
                     }
                  }
               }
            }

            ConnectionEnv var30 = cc;
            return var30;
         }
      } finally {
         this.poolsRWLock.unlockRead();
      }
   }

   private void checkLLRDataSource(boolean isMemberLLR) {
      if (isMemberLLR) {
         JDBCHelper jdbcHelper = JDBCHelper.getHelper();
         Iterator iter = this.listOfPools.iterator();
         int allHealthy = this.numOfPools;
         boolean MdsHasNonRACDataSources = false;
         boolean isRAC = false;

         String poolName;
         while(iter.hasNext()) {
            poolName = (String)iter.next();
            JDBCConnectionPool pool = null;
            if (jdbcHelper.isLLRTablePerDataSource(poolName)) {
               return;
            }

            try {
               ConnectionPoolManager var10000 = this.cpMgr;
               pool = ConnectionPoolManager.getPool(poolName, this.appName, this.getModuleName(poolName), this.compName);
            } catch (ResourceException var10) {
            }

            if (pool == null) {
               --allHealthy;
            } else {
               isRAC = jdbcHelper.isRACPool(poolName, this.appName, this.getModuleName(poolName), this.compName);
               if (!isRAC) {
                  MdsHasNonRACDataSources = true;
               }

               if (!this.isPoolHealthy(pool)) {
                  --allHealthy;
               }

               if (!pool.getTestOnReserve()) {
                  JDBCLogger.logWarningTestOnReserveDisabledForMDSLLRRAC(poolName);
               }
            }
         }

         if (allHealthy == 0 || MdsHasNonRACDataSources && allHealthy < this.numOfPools) {
            poolName = "Not all the DataSources for MultiDataSource " + this.name + " are healthy.";
            if (allHealthy == 0) {
               poolName = "None of the DataSources for MultiDataSource " + this.name + " are healthy.";
            }

            TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
            tm.registerFailedLoggingResource(new ResourceException(poolName));
         }

      }
   }

   private ConnectionEnv searchHighAvail(AuthenticatedSubject user, int waitSecs, String username, String password) throws ResourceException {
      this.poolsRWLock.lockRead();

      try {
         int currPool = 0;
         String poolToTry = null;
         String nextPoolToTry = null;
         JDBCConnectionPool connPool = null;
         ConnectionEnv cc = null;
         int howMany = this.numOfPools;
         List errors = new ArrayList();
         boolean retryCurrentPool = true;

         label316:
         while(true) {
            int retCode;
            if (currPool < howMany && cc == null) {
               poolToTry = this.listOfPools.get(currPool++).toString();
               ConnectionPoolManager var10000;
               if (this.appName == null) {
                  var10000 = this.cpMgr;
                  connPool = ConnectionPoolManager.getPool(poolToTry, (String)null, (String)null, (String)null);
               } else {
                  var10000 = this.cpMgr;
                  connPool = ConnectionPoolManager.getPool(poolToTry, this.appName, this.getModuleName(poolToTry), this.compName);
               }

               if (connPool == null || this.disabledPools.containsKey(connPool) && this.healthChkFreqSecs != 0) {
                  continue;
               }

               while(true) {
                  while(true) {
                     if (!retryCurrentPool) {
                        continue label316;
                     }

                     try {
                        if (this.failoverRequestIfBusy && currPool < howMany) {
                           cc = connPool.reserve(user, 1, (Properties)null, username, password);
                        } else {
                           cc = connPool.reserve(user, waitSecs, (Properties)null, username, password);
                        }

                        if (this.disabledPools.containsKey(connPool)) {
                           synchronized(this.disabledPools) {
                              this.disabledPools.remove(connPool);
                              this.disabledPoolReasons.remove(connPool.getName());
                           }
                        }
                        break;
                     } catch (ResourceException var26) {
                        ResourceException re = var26;
                        if (!(var26 instanceof ResourceDeadException) && !(var26 instanceof ResourceDisabledException)) {
                           if (!(var26 instanceof ResourceLimitException) && !(var26 instanceof ResourceUnavailableException)) {
                              if (var26 instanceof ResourceException) {
                                 throw var26;
                              }

                              throw new ResourceException("\n\t" + this.name + "(" + poolToTry + "): " + var26);
                           }

                           if (this.failoverRequestIfBusy && currPool < howMany) {
                              if (this.failoverCB != null) {
                                 nextPoolToTry = this.listOfPools.get(currPool).toString();
                                 retCode = this.failoverCB.allowPoolFailover(poolToTry, nextPoolToTry, 1);
                                 if (retCode != 0) {
                                    throw new ResourceUnusableException("Application has disallowed MultiPool rerouting of request for connection from busy connection pool (" + poolToTry + ") to alternate connection pool (" + nextPoolToTry + ")");
                                 }
                              }

                              errors.add("\t" + this.name + "(" + poolToTry + "): " + var26.getMessage() + "\n");
                              continue label316;
                           }

                           throw var26;
                        }

                        if (this.failoverCB != null) {
                           if (currPool < howMany) {
                              nextPoolToTry = this.listOfPools.get(currPool).toString();
                           } else {
                              nextPoolToTry = null;
                           }

                           retCode = this.failoverCB.allowPoolFailover(poolToTry, nextPoolToTry, 0);
                           if (retCode == 1) {
                              continue;
                           }

                           if (retCode == 2 && nextPoolToTry != null) {
                              throw new ResourceUnusableException("Application has disallowed MultiPool failover from current connection pool (" + poolToTry + ") to next candidate connection pool (" + nextPoolToTry + ")");
                           }
                        }

                        try {
                           boolean alreadyDone = false;
                           synchronized(this.disabledPools) {
                              if (!this.disabledPools.containsKey(connPool)) {
                                 if (re instanceof ResourceDisabledException) {
                                    this.disabledPools.put(connPool, MultiPool.PoolStates.NOT_YET_DISABLED_EXTERNAL_SUSPEND);
                                    this.disabledPoolReasons.put(connPool.getName(), re);
                                    this.disabledPoolReasons.put(connPool.getName(), " At " + new Date() + " we got " + re.getMessage());
                                 } else {
                                    this.disabledPools.put(connPool, MultiPool.PoolStates.NOT_YET_DISABLED);
                                    this.disabledPoolReasons.put(connPool.getName(), " At " + new Date() + " we got " + re.getMessage());
                                 }
                              } else {
                                 alreadyDone = true;
                              }
                           }

                           if (!alreadyDone) {
                              SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedAction() {
                                 public Object run() {
                                    MultiPool.this.disablePoolDroppingUsers();
                                    return null;
                                 }
                              });
                           }
                        } catch (Exception var24) {
                           JDBCLogger.logDisableFailed2(this.name, poolToTry, var24.toString());
                        }

                        errors.add("\t" + this.name + "(" + poolToTry + "): " + var26.getMessage() + "\n");
                        continue label316;
                     }
                  }

                  if (cc != null) {
                     continue label316;
                  }
               }
            }

            if (currPool >= howMany && errors.size() > 0 && cc == null) {
               String listMsg = (String)errors.get(0);

               for(retCode = 1; retCode < errors.size(); ++retCode) {
                  listMsg = listMsg + errors.get(retCode);
               }

               listMsg = "\n" + listMsg;
               throw new ResourceException(listMsg);
            }

            ConnectionEnv var13 = cc;
            return var13;
         }
      } finally {
         this.poolsRWLock.unlockRead();
      }
   }

   private String validateName() throws ResourceException {
      String validatedName = this.dsBean.getName();
      if (validatedName.equals("")) {
         throw new ResourceException("No name defined for multiPool");
      } else {
         return validatedName;
      }
   }

   private void validatePoolList() throws ResourceException {
      String dsList = this.dsBean.getJDBCDataSourceParams().getDataSourceList();
      if (dsList == null) {
         throw new ResourceException("Data Source list is empty for " + this.name);
      } else {
         this.poolsRWLock.lockWrite();

         try {
            List newList = JDBCHelper.getHelper().dsToList(dsList);
            this.numOfPools = newList.size();
            if (this.numOfPools == 0) {
               throw new ResourceException("Data Source list is empty for MultiDataSource " + this.name);
            } else {
               Iterator var3 = newList.iterator();

               while(var3.hasNext()) {
                  String poolName = (String)var3.next();
                  JDBCConnectionPool pool = ConnectionPoolManager.getPool(poolName, this.appName, this.getModuleName(poolName), this.compName);
                  this.validateMember(pool, poolName);
               }

               this.listOfPools = newList;
            }
         } finally {
            this.poolsRWLock.unlockWrite();
         }
      }
   }

   private void validateMember(JDBCConnectionPool pool, String poolName) throws ResourceException {
      if (pool instanceof HAConnectionPool) {
         JDBCLogger.logInvalidGridLinkMultiPoolMember(this.name, poolName);
         throw new RuntimeException("Invalid member data source " + poolName + " specified for Multi data source " + this.name + "; Active GridLink not allowed");
      } else if (pool instanceof SharingConnectionPool) {
         throw new RuntimeException("Invalid member data source " + poolName + " specified for Multi data source " + this.name + "; shared pooling not supported");
      }
   }

   public void setDataSourceList(String dslist) {
      this.poolsRWLock.lockWrite();

      try {
         this.listOfPools = JDBCHelper.getHelper().dsToList(dslist);
         this.numOfPools = this.listOfPools.size();
         if (this.nextPoolToUse >= this.numOfPools) {
            this.nextPoolToUse = 0;
         }

         Iterator it = this.listOfPools.iterator();

         while(it.hasNext()) {
            String poolName = (String)it.next();

            try {
               JDBCConnectionPool pool = ConnectionPoolManager.getPool(poolName, this.appName, this.getModuleName(poolName), this.compName);
               if (pool == null) {
                  throw new RuntimeException("Invalid member data source " + poolName + " specified for Multi data source " + this.name + ". Data source not found");
               }

               if (this.dataSource != null) {
                  this.validateMember(pool, poolName);
                  pool.setDataSource(this.dataSource);
               }
            } catch (ResourceException var8) {
            }
         }
      } finally {
         this.poolsRWLock.unlockWrite();
      }

   }

   private String validateAlgorithm() {
      String anAlgorithm = this.dsBean.getJDBCDataSourceParams().getAlgorithmType();
      if (anAlgorithm.equals("Failover")) {
         this.failoverRequestIfBusy = this.dsBean.getJDBCDataSourceParams().isFailoverRequestIfBusy();
      }

      return !anAlgorithm.equals("Failover") && !anAlgorithm.equals("Load-Balancing") ? "Failover" : anAlgorithm;
   }

   public boolean getFailoverRequestIfBusy() {
      return this.failoverRequestIfBusy;
   }

   public void setFailoverRequestIfBusy(boolean val) {
      JDBCLogger.logMPFailoverFlagChg(this.name, this.failoverRequestIfBusy, val);
      this.failoverRequestIfBusy = val;
   }

   public String getName() {
      return this.name;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public boolean isGloballyScoped() {
      return this.appName == null;
   }

   public void setHealthCheckFrequencySeconds(int val) {
      JDBCLogger.logMPHealthChkFreqChg(this.name, this.healthChkFreqSecs, val);
      this.healthChkFreqSecs = val;
      this.setupHealthCheck();
   }

   public void setupConnPoolRefs() throws ResourceException {
      this.setupTxPinning();
   }

   private void registerFailoverCallbackHandler() {
      Object cbObject = null;
      String cbClassName = this.dsBean.getJDBCDataSourceParams().getConnectionPoolFailoverCallbackHandler();
      if (cbClassName != null) {
         try {
            cbObject = DataSourceUtil.loadDriverClass(cbClassName, (ClassLoader)null).newInstance();
         } catch (Exception var4) {
            JDBCLogger.logFailoverCBLoadError(this.name, cbClassName, var4.toString());
            return;
         }

         if (cbObject instanceof ConnectionPoolFailoverCallback) {
            this.failoverCB = (ConnectionPoolFailoverCallback)cbObject;
            JDBCLogger.logRegisteredCB(this.name, cbClassName);
         } else {
            JDBCLogger.logFailoverCBTypeError(this.name, cbClassName, "weblogic.jdbc.extensions.ConnectionPoolFailoverCallback");
         }
      }

   }

   private void setupHealthCheck() {
      if (this.healthChkTimer != null) {
         this.healthChkTimer.cancel();
      }

      if (this.healthChkFreqSecs > 0) {
         this.healthChkTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, (long)this.healthChkFreqSecs * 1000L, (long)this.healthChkFreqSecs * 1000L);
         this.healthChkEnabled = true;
      } else {
         this.healthChkEnabled = false;
      }

   }

   public void timerExpired(Timer timer) {
      this.checkDisabledPools();
   }

   private synchronized void checkDisabledPools() {
      HashSet healthyPools = new HashSet();
      HashSet myDisabledPools = new HashSet();
      HashSet poolsToReenable = new HashSet();
      ConnectionPool mypool;
      synchronized(this.disabledPools) {
         Iterator iter = this.disabledPools.keySet().iterator();

         while(true) {
            if (!iter.hasNext()) {
               break;
            }

            mypool = (ConnectionPool)iter.next();
            if (this.disabledPools.get(mypool) == MultiPool.PoolStates.BEEN_DISABLED || this.disabledPools.get(mypool) == MultiPool.PoolStates.BEEN_DISABLED_EXTERNAL_SUSPEND) {
               myDisabledPools.add(mypool);
               if (this.disabledPools.get(mypool) == MultiPool.PoolStates.BEEN_DISABLED) {
                  poolsToReenable.add(mypool);
               }
            }
         }
      }

      Iterator iter = myDisabledPools.iterator();

      while(iter.hasNext()) {
         ConnectionPool pool = (ConnectionPool)iter.next();
         if (this.isPoolShutDown(pool)) {
            synchronized(this.disabledPools) {
               this.disabledPools.remove(pool);
               this.disabledPoolReasons.remove(pool.getName());
               this.disabledPoolReconnectFails.remove(pool.getName());
            }
         } else if (this.isPoolHealthy(pool)) {
            if (this.failoverCB != null) {
               int retCode = this.failoverCB.allowPoolFailover(pool.getName(), (String)null, 2);
               if (retCode == 0) {
                  try {
                     if (poolsToReenable.contains(pool)) {
                        JDBCLogger.logReenablingPool(this.name, pool.getName());
                        pool.enable();
                     }

                     healthyPools.add(pool);
                  } catch (ResourceException var11) {
                     JDBCLogger.logEnableFailed(this.name, pool.getName(), var11.toString());
                  }
               } else {
                  JDBCLogger.logPoolReenableDisallowed(this.name, pool.getName());
               }
            } else {
               try {
                  if (poolsToReenable.contains(pool)) {
                     JDBCLogger.logReenablingPool(this.name, pool.getName());
                     pool.enable();
                  }

                  healthyPools.add(pool);
               } catch (ResourceException var10) {
                  JDBCLogger.logEnableFailed2(this.name, pool.getName(), var10.toString());
               }
            }
         }
      }

      iter = healthyPools.iterator();
      synchronized(this.disabledPools) {
         while(iter.hasNext()) {
            mypool = (ConnectionPool)iter.next();
            this.disabledPools.remove(mypool);
            this.disabledPoolReasons.remove(mypool.getName());
            this.disabledPoolReconnectFails.remove(mypool.getName());
         }

      }
   }

   private boolean isPoolHealthy(JDBCConnectionPool pool) {
      ConnectionEnv cc = null;
      boolean poolHealthy = false;

      try {
         cc = pool.reserveInternal(-2);
         if (cc != null && cc.isConnTested()) {
            poolHealthy = true;
         }
      } catch (Exception var14) {
         String reason = " at " + new Date() + " we got " + var14.getMessage();
         this.disabledPoolReconnectFails.put(pool.getName(), reason);
      } finally {
         if (cc != null) {
            try {
               pool.release(cc);
            } catch (Exception var13) {
            }
         }

      }

      return poolHealthy;
   }

   private boolean isPoolShutDown(ConnectionPool pool) {
      return pool.getState().equals("Shutdown");
   }

   String getModuleName(String poolName) {
      return this.moduleNames == null ? poolName : (String)this.moduleNames.get(poolName);
   }

   private void setupTxPinning() throws ResourceException {
      boolean found = false;
      this.poolsRWLock.lockRead();

      try {
         Iterator iter = this.listOfPools.iterator();

         while(!found && iter.hasNext()) {
            String poolName = (String)iter.next();
            ConnectionPoolManager var10000 = this.cpMgr;
            JDBCConnectionPool pool = ConnectionPoolManager.getPool(poolName, this.appName, this.getModuleName(poolName), this.compName);
            if (pool != null) {
               this.pinnedToDBInstanceThruTx = pool.getXARetryDurationSeconds() != 0;
               found = true;
            }
         }
      } finally {
         this.poolsRWLock.unlockRead();
      }

   }

   private int computePerCPWait(int totalWaitSecs, int algorithmType) {
      if (totalWaitSecs <= 0) {
         return totalWaitSecs;
      } else {
         this.poolsRWLock.lockRead();
         int numHealthyCPs = this.listOfPools.size() - this.disabledPools.size();
         this.poolsRWLock.unlockRead();
         if (numHealthyCPs <= 0) {
            return totalWaitSecs;
         } else {
            return algorithmType == 0 && !this.failoverRequestIfBusy ? totalWaitSecs : totalWaitSecs / numHealthyCPs;
         }
      }
   }

   int getMaxCapacity() {
      int total = 0;
      this.poolsRWLock.lockRead();

      try {
         Iterator iter = this.listOfPools.iterator();

         while(true) {
            if (iter.hasNext()) {
               String poolName = (String)iter.next();

               JDBCConnectionPool pool;
               try {
                  ConnectionPoolManager var10000 = this.cpMgr;
                  pool = ConnectionPoolManager.getPool(poolName, this.appName, this.getModuleName(poolName), this.compName);
               } catch (ResourceException var9) {
                  continue;
               }

               if (!pool.isEnabled()) {
                  continue;
               }

               total += pool.getJDBCDataSource().getJDBCConnectionPoolParams().getMaxCapacity();
               if (!this.isPureFailover()) {
                  continue;
               }
            }

            int var11 = total;
            return var11;
         }
      } finally {
         this.poolsRWLock.unlockRead();
      }
   }

   int getMinCapacity() {
      int total = 0;
      this.poolsRWLock.lockRead();

      int var11;
      try {
         Iterator iter = this.listOfPools.iterator();

         while(iter.hasNext()) {
            String poolName = (String)iter.next();

            JDBCConnectionPool pool;
            try {
               ConnectionPoolManager var10000 = this.cpMgr;
               pool = ConnectionPoolManager.getPool(poolName, this.appName, this.getModuleName(poolName), this.compName);
            } catch (ResourceException var9) {
               continue;
            }

            if (pool.isEnabled()) {
               if (pool.getJDBCDataSource().getJDBCConnectionPoolParams().isSet("MinCapacity")) {
                  total += pool.getJDBCDataSource().getJDBCConnectionPoolParams().getMinCapacity();
               } else {
                  total += pool.getJDBCDataSource().getJDBCConnectionPoolParams().getInitialCapacity();
               }

               if (this.isPureFailover()) {
                  break;
               }
            }
         }

         var11 = total;
      } finally {
         this.poolsRWLock.unlockRead();
      }

      return var11;
   }

   private boolean isPureFailover() {
      return this.getAlgorithm().equals("Failover") && !this.getFailoverRequestIfBusy();
   }

   public boolean hasMember(String poolName) {
      this.poolsRWLock.lockRead();

      boolean var2;
      try {
         var2 = this.listOfPools.contains(poolName);
      } finally {
         this.poolsRWLock.unlockRead();
      }

      return var2;
   }

   public JDBCConnectionPool[] getConnectionPools() throws ResourceException {
      this.poolsRWLock.lockRead();

      try {
         JDBCConnectionPool[] pools = new JDBCConnectionPool[this.listOfPools.size()];
         Iterator iter = this.listOfPools.iterator();
         int index = 0;

         while(iter.hasNext()) {
            String currPool = (String)iter.next();
            ConnectionPoolManager var10000 = this.cpMgr;
            JDBCConnectionPool connPool = ConnectionPoolManager.getPool(currPool, this.appName, this.getModuleName(currPool), this.compName);
            if (connPool != null) {
               pools[index++] = connPool;
            }
         }

         JDBCConnectionPool[] var9 = pools;
         return var9;
      } finally {
         this.poolsRWLock.unlockRead();
      }
   }

   public void setDataSource(DataSource ds) throws ResourceException {
      this.dataSource = ds;
      this.poolsRWLock.lockRead();

      try {
         Iterator iter = this.listOfPools.iterator();
         int index = false;

         while(iter.hasNext()) {
            String currPool = (String)iter.next();
            ConnectionPoolManager var10000 = this.cpMgr;
            JDBCConnectionPool connPool = ConnectionPoolManager.getPool(currPool, this.appName, this.getModuleName(currPool), this.compName);
            if (connPool != null) {
               connPool.setDataSource(ds);
            }
         }
      } finally {
         this.poolsRWLock.unlockRead();
      }

   }

   public JDBCDataSourceBean getDataSourceBean() {
      return this.dsBean;
   }

   private static enum PoolStates {
      NOT_YET_DISABLED,
      NOT_YET_DISABLED_EXTERNAL_SUSPEND,
      BEING_DISABLED,
      BEING_DISABLED_EXTERNAL_SUSPEND,
      BEEN_DISABLED,
      BEEN_DISABLED_EXTERNAL_SUSPEND;
   }
}
