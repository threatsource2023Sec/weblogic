package weblogic.jdbc.common.internal;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.transaction.SystemException;
import oracle.ucp.ConnectionAffinityCallback;
import oracle.ucp.ConnectionLabelingCallback;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.CommonLogger;
import weblogic.common.resourcepool.GroupingPooledResourceLinkedList;
import weblogic.common.resourcepool.IGroupingPooledResourceLinkedList;
import weblogic.common.resourcepool.IGroupingPooledResourceSet;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceDisabledException;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.rac.RACAffinityContextException;
import weblogic.jdbc.common.rac.RACAffinityContextHelper;
import weblogic.jdbc.common.rac.RACAffinityContextHelperFactory;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACInstanceFactory;
import weblogic.jdbc.common.rac.RACModule;
import weblogic.jdbc.common.rac.RACModuleFactory;
import weblogic.jdbc.common.rac.RACModuleFailoverEvent;
import weblogic.jdbc.common.rac.RACModulePool;
import weblogic.jdbc.common.rac.RACPooledConnectionState;
import weblogic.jdbc.extensions.AffinityCallback;
import weblogic.jdbc.extensions.DataAffinityCallback;
import weblogic.management.ManagementException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.utils.StackTraceUtils;

public class HAConnectionPool extends ConnectionPool implements RACModulePool, HAJDBCConnectionPool {
   private RACModule racModule;
   private String serviceName;
   private String loggedServiceName;
   private String databaseName;
   private boolean fanEnabled;
   private String affinityContextKey;
   private TransactionManager tm;
   private boolean isxa;
   private int rlbThreshold;
   private DataSourceServiceImpl dsService;
   private ConnectionAffinityCallback.AffinityPolicy affinityPolicy;
   private AffinityCallback xaAffinityCallback;
   private AffinityCallback sessionAffinityCallback;
   private AtomicInteger gravitationShrinkTime = new AtomicInteger(0);
   private RACAffinityContextHelper racAffinityContextHelper;
   private boolean autoOns = false;
   private Map haDataSourceRuntimes = Collections.synchronizedMap(new HashMap());
   static int MAXDRAIN = 10000;
   static int DRAININTERVAL = 5;

   public HAConnectionPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, ClassLoader classLoader) {
      super((String)appName, moduleName, compName, (ConnectionPoolConfig)(new DataSourceHAConnectionPoolConfig(dsBean, classLoader, appName, moduleName, compName)), classLoader);
      this.dsBean = dsBean;
      this.init();
   }

   public void setFanEnabled(boolean enabled) throws ResourceException {
      if (enabled) {
         this.initOracleHelper();
      } else {
         this.stopRACModule();
      }

   }

   private void init() {
      this.setupDriverInterceptor();
      this.setupConnectionLabelingCallback();
      this.setupConnectionHarvesting();
      this.setupConnectionInitializationCallback();
      String url = this.dsBean.getJDBCDriverParams().getUrl();
      this.serviceName = this.extractAttributeValueFromURL("SERVICE_NAME", url);
      if (this.serviceName != null) {
         this.loggedServiceName = this.serviceName;
      } else {
         int i = url.lastIndexOf("@");
         if (i != -1) {
            this.loggedServiceName = url.substring(i);
         } else {
            this.loggedServiceName = "unknown";
         }
      }

      this.tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
      String t = System.getProperty("weblogic.jdbc.rlbThreshold", "1");
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("weblogic.jdbc.rlbThreshold=" + t);
      }

      double tfactor = Double.valueOf(t);
      if (tfactor < 0.0 || tfactor > 1.0) {
         tfactor = 1.0;
      }

      this.rlbThreshold = (int)((double)this.dsBean.getJDBCConnectionPoolParams().getMaxCapacity() * tfactor);
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("rlb connection threshold=" + this.rlbThreshold);
      }

      this.dsService = (DataSourceServiceImpl)DataSourceManager.getInstance().getDataSourceService();

      try {
         this.racAffinityContextHelper = RACAffinityContextHelperFactory.getInstance();
      } catch (RACAffinityContextException var6) {
         throw new RuntimeException(var6);
      }

      this.xaAffinityCallback = new XAAffinityCallback(this);
      this.sessionAffinityCallback = new SessionAffinityCallback(this);
      String policy = this.dsBean.getJDBCOracleParams().getAffinityPolicy();
      if ("Transaction".equals(policy)) {
         this.affinityPolicy = ConnectionAffinityCallback.AffinityPolicy.TRANSACTION_BASED_AFFINITY;
      } else if ("Session".equals(policy)) {
         this.affinityPolicy = ConnectionAffinityCallback.AffinityPolicy.WEBSESSION_BASED_AFFINITY;
      } else if ("Data".equals(policy)) {
         this.affinityPolicy = ConnectionAffinityCallback.AffinityPolicy.DATA_BASED_AFFINITY;
      } else {
         this.affinityPolicy = null;
      }

   }

   public boolean isXA() {
      return this.isxa;
   }

   private String extractAttributeValueFromURL(String key, String url) {
      String ret = null;
      String[] serviceHalf = url.split(key + "\\s*=");
      if (serviceHalf.length != 2) {
         return null;
      } else {
         int endParen = serviceHalf[1].indexOf(")");
         String value = serviceHalf[1].substring(0, endParen);
         return value.trim();
      }
   }

   public String getServiceName() {
      return this.serviceName;
   }

   public String getDatabaseName() {
      return this.databaseName;
   }

   protected void releasePinnedToThread(ConnectionEnv resource) throws ResourceException {
      if (resource.getOwner() != Thread.currentThread() && JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug("Pinned: Release other thread's connection: " + resource);
         String stackTrace = resource.getStackTrace();
         stackTrace = stackTrace + StackTraceUtils.throwable2StackTrace(new Exception("Pinned: " + Thread.currentThread() + " release other's conn " + resource));
         JdbcDebug.JDBCCONN.debug(stackTrace);
      }

      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug("Pinned: Release pinned connection " + resource + " with thread " + Thread.currentThread());
      }

      resource.cleanup();
      Thread pinnedTo = ((HAConnectionEnv)resource).getPinnedTo();
      if (pinnedTo == null || pinnedTo == Thread.currentThread()) {
         ConnectionPool.ConnectionStore pinned = (ConnectionPool.ConnectionStore)connections.get();
         if (pinned == null) {
            throw new RuntimeException("PinnedToThread is supported ONLY with AuditableThread");
         } else {
            if (!pinned.contains(this, resource)) {
               if (pinned.containsType(this, resource.getPooledResourceInfo())) {
                  if (this.isOracleProxySession()) {
                     resource.OracleProxyConnectionClose();
                  }

                  super.releaseResource(resource);
               } else {
                  ((HAConnectionEnv)resource).setPinnedTo(Thread.currentThread());
                  pinned.put(this, resource);
               }
            }

         }
      }
   }

   protected ConnectionEnv reservePinnedToThread(int waitSeconds, boolean internalUse) throws ResourceException, ResourceDisabledException {
      return super.reservePinnedToThread(waitSeconds, internalUse);
   }

   public ConnectionEnv reserve(AuthenticatedSubject user, int waitSeconds, Properties requestedLabels, String username, String password) throws ResourceException {
      return this.reserve(this.racModule, user, waitSeconds, requestedLabels, username, password);
   }

   public ConnectionEnv reserve(RACModule racModule, AuthenticatedSubject user, int waitSeconds, Properties requestedLabels, String username, String password) throws ResourceException {
      ConnectionEnv ce = null;
      this.doAuthorizationCheck(user);
      boolean var43 = false;

      Object ci;
      RACInstance racInstance;
      label828: {
         label829: {
            Object var74;
            label830: {
               try {
                  label856: {
                     var43 = true;
                     if (requestedLabels != null) {
                        String instanceName = (String)requestedLabels.get("_weblogic.jdbc.instanceName");
                        Properties additionalProperties = (Properties)requestedLabels.get("_weblogic.jdbc.properties");
                        RACInstance racInstance = null;
                        if (instanceName != null) {
                           racInstance = racModule.getOrCreateRACInstance(instanceName);
                           if (racInstance == null) {
                              throw new ResourceException("unknown instance name " + instanceName);
                           }

                           if (additionalProperties == null) {
                              ce = this.getExistingConnectionToInstance(racInstance, waitSeconds, (Properties)null);
                           }
                        }

                        if (instanceName != null || additionalProperties != null) {
                           if (ce == null) {
                              ce = this.openConnectionToInstance(racInstance, true, additionalProperties);
                              if (ce == null) {
                                 throw new ResourceException("could not open connection to " + instanceName);
                              }

                              if (additionalProperties != null) {
                                 ((ConnectionEnv)ce).setInfected(true);
                                 ((ConnectionEnv)ce).setRefreshNeeded(true);
                                 JDBCConnectionPool pool = ConnectionPoolManager.getPool(((ConnectionEnv)ce).getPoolName(), ((ConnectionEnv)ce).getAppName(), ((ConnectionEnv)ce).getModuleName(), ((ConnectionEnv)ce).getCompName());
                                 if (pool == null) {
                                    throw new ResourceException("could not find pool " + ((ConnectionEnv)ce).getPoolName());
                                 }
                              }
                           }

                           this.processOracleProxySession(user, username, password, (ConnectionEnv)ce, false);
                           var74 = ce;
                           var43 = false;
                           break label830;
                        }
                     }

                     if (this.fanEnabled) {
                        ce = racModule.getConnection(requestedLabels);
                        if (ce != null) {
                           if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                              this.debug("Found RCLB connection: " + ce);
                           }

                           this.processOracleProxySession(user, username, password, (ConnectionEnv)ce, false);
                           ci = ce;
                           var43 = false;
                           break label856;
                        }
                     } else {
                        ce = this.reserveWithTxAffinity(user, waitSeconds, requestedLabels);
                        if (ce != null) {
                           if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                              this.debug("Found XA affinity connection: " + ce);
                           }

                           this.processOracleProxySession(user, username, password, (ConnectionEnv)ce, false);
                           ci = ce;
                           var43 = false;
                           break label829;
                        }
                     }

                     if (this.config.isPinnedToThread()) {
                        ce = this.reservePinnedToThread(waitSeconds, false);
                     } else {
                        ci = null;
                        SwitchingContext switchingContext = SwitchingContextManager.getInstance().get();
                        if (switchingContext != null) {
                           Set instances = racModule.getServiceInstanceNames();
                           if (instances != null && instances.size() != 0) {
                              ci = new HAPooledResourceInfo(this.dsBean.getJDBCDriverParams().getUrl(), (RACInstance)null, (String)null, (String)null, instances, (Properties)null, (Properties)null);
                           } else if (switchingContext.getPDBServiceName() != null) {
                              throw new ResourceException("Shared pool " + switchingContext.getPool().getName() + " service " + switchingContext.getPDBServiceName() + " not running on any instances");
                           }
                        } else if (requestedLabels != null) {
                           ci = new LabelingConnectionInfo(requestedLabels);
                        }

                        if (waitSeconds == -2) {
                           ce = (ConnectionEnv)super.reserveResource((PooledResourceInfo)ci, false);
                        } else {
                           ce = (ConnectionEnv)super.reserveResource(waitSeconds, (PooledResourceInfo)ci, false, true);
                        }
                     }

                     this.processOracleProxySession(user, username, password, (ConnectionEnv)ce, false);
                     ci = ce;
                     var43 = false;
                     break label828;
                  }
               } finally {
                  if (var43) {
                     if (this.serviceName == null && ce != null) {
                        RACInstance racInstance = ((RACConnectionEnv)ce).getRACInstance();
                        if (racInstance != null) {
                           this.serviceName = racInstance.getService();
                           this.loggedServiceName = this.serviceName;
                        }
                     }

                     if (requestedLabels != null && ce != null && ((ConnectionEnv)ce).drcpEnabled) {
                        try {
                           ConnectionPoolManager.release((ConnectionEnv)ce);
                        } catch (ResourceException var44) {
                        }

                        ce = null;
                        throw new ResourceException("label used for pooled connection");
                     }

                     if (!this.fanEnabled) {
                        this.setTxAffinityContext((ConnectionEnv)ce);
                     } else {
                        this.setUCPTransactionAffinityContext((ConnectionEnv)ce);
                     }

                     if (ce != null) {
                        try {
                           racModule.replayBeginRequest((RACConnectionEnv)ce, this.replayInitiationTimeout);
                        } catch (SQLException var64) {
                           if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                              this.debug("beginRequest failed: " + StackTraceUtils.throwable2StackTrace(var64));
                           }

                           try {
                              ConnectionPoolManager.release((ConnectionEnv)ce);
                           } catch (ResourceException var46) {
                              throw new ResourceException("beginRequest failed; connection release failed with " + var46.getMessage(), var64);
                           }

                           throw new ResourceException("beginRequest failed", var64);
                        }

                        if (this.labelingCallback == null && this.initializationCallback != null) {
                           try {
                              ((ConnectionEnv)ce).connectionInitialize(this.initializationCallback);
                           } catch (Exception var54) {
                              try {
                                 ConnectionPoolManager.release((ConnectionEnv)ce);
                              } catch (ResourceException var53) {
                                 throw new ResourceException("ConnectionInitializationCallback.initialize callback failed for replay initialize; connection release failed with " + var53.getMessage(), var54);
                              }

                              throw new ResourceException("ConnectionInitializationCallback.initialize failed", var54);
                           }
                        }
                     }

                  }
               }

               if (this.serviceName == null && ce != null) {
                  racInstance = ((RACConnectionEnv)ce).getRACInstance();
                  if (racInstance != null) {
                     this.serviceName = racInstance.getService();
                     this.loggedServiceName = this.serviceName;
                  }
               }

               if (requestedLabels != null && ce != null && ((ConnectionEnv)ce).drcpEnabled) {
                  try {
                     ConnectionPoolManager.release((ConnectionEnv)ce);
                  } catch (ResourceException var58) {
                  }

                  ce = null;
                  throw new ResourceException("label used for pooled connection");
               }

               if (!this.fanEnabled) {
                  this.setTxAffinityContext((ConnectionEnv)ce);
               } else {
                  this.setUCPTransactionAffinityContext((ConnectionEnv)ce);
               }

               if (ce != null) {
                  try {
                     racModule.replayBeginRequest((RACConnectionEnv)ce, this.replayInitiationTimeout);
                  } catch (SQLException var65) {
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        this.debug("beginRequest failed: " + StackTraceUtils.throwable2StackTrace(var65));
                     }

                     try {
                        ConnectionPoolManager.release((ConnectionEnv)ce);
                     } catch (ResourceException var45) {
                        throw new ResourceException("beginRequest failed; connection release failed with " + var45.getMessage(), var65);
                     }

                     throw new ResourceException("beginRequest failed", var65);
                  }

                  if (this.labelingCallback == null && this.initializationCallback != null) {
                     try {
                        ((ConnectionEnv)ce).connectionInitialize(this.initializationCallback);
                     } catch (Exception var59) {
                        try {
                           ConnectionPoolManager.release((ConnectionEnv)ce);
                        } catch (ResourceException var49) {
                           throw new ResourceException("ConnectionInitializationCallback.initialize callback failed for replay initialize; connection release failed with " + var49.getMessage(), var59);
                        }

                        throw new ResourceException("ConnectionInitializationCallback.initialize failed", var59);
                     }
                  }
               }

               return (ConnectionEnv)ci;
            }

            if (this.serviceName == null && ce != null) {
               RACInstance racInstance = ((RACConnectionEnv)ce).getRACInstance();
               if (racInstance != null) {
                  this.serviceName = racInstance.getService();
                  this.loggedServiceName = this.serviceName;
               }
            }

            if (requestedLabels != null && ce != null && ((ConnectionEnv)ce).drcpEnabled) {
               try {
                  ConnectionPoolManager.release((ConnectionEnv)ce);
               } catch (ResourceException var56) {
               }

               ce = null;
               throw new ResourceException("label used for pooled connection");
            }

            if (!this.fanEnabled) {
               this.setTxAffinityContext((ConnectionEnv)ce);
            } else {
               this.setUCPTransactionAffinityContext((ConnectionEnv)ce);
            }

            if (ce != null) {
               try {
                  racModule.replayBeginRequest((RACConnectionEnv)ce, this.replayInitiationTimeout);
               } catch (SQLException var66) {
                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     this.debug("beginRequest failed: " + StackTraceUtils.throwable2StackTrace(var66));
                  }

                  try {
                     ConnectionPoolManager.release((ConnectionEnv)ce);
                  } catch (ResourceException var47) {
                     throw new ResourceException("beginRequest failed; connection release failed with " + var47.getMessage(), var66);
                  }

                  throw new ResourceException("beginRequest failed", var66);
               }

               if (this.labelingCallback == null && this.initializationCallback != null) {
                  try {
                     ((ConnectionEnv)ce).connectionInitialize(this.initializationCallback);
                  } catch (Exception var57) {
                     try {
                        ConnectionPoolManager.release((ConnectionEnv)ce);
                     } catch (ResourceException var52) {
                        throw new ResourceException("ConnectionInitializationCallback.initialize callback failed for replay initialize; connection release failed with " + var52.getMessage(), var57);
                     }

                     throw new ResourceException("ConnectionInitializationCallback.initialize failed", var57);
                  }
               }
            }

            return (ConnectionEnv)var74;
         }

         if (this.serviceName == null && ce != null) {
            racInstance = ((RACConnectionEnv)ce).getRACInstance();
            if (racInstance != null) {
               this.serviceName = racInstance.getService();
               this.loggedServiceName = this.serviceName;
            }
         }

         if (requestedLabels != null && ce != null && ((ConnectionEnv)ce).drcpEnabled) {
            try {
               ConnectionPoolManager.release((ConnectionEnv)ce);
            } catch (ResourceException var60) {
            }

            ce = null;
            throw new ResourceException("label used for pooled connection");
         }

         if (!this.fanEnabled) {
            this.setTxAffinityContext((ConnectionEnv)ce);
         } else {
            this.setUCPTransactionAffinityContext((ConnectionEnv)ce);
         }

         if (ce != null) {
            try {
               racModule.replayBeginRequest((RACConnectionEnv)ce, this.replayInitiationTimeout);
            } catch (SQLException var67) {
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  this.debug("beginRequest failed: " + StackTraceUtils.throwable2StackTrace(var67));
               }

               try {
                  ConnectionPoolManager.release((ConnectionEnv)ce);
               } catch (ResourceException var48) {
                  throw new ResourceException("beginRequest failed; connection release failed with " + var48.getMessage(), var67);
               }

               throw new ResourceException("beginRequest failed", var67);
            }

            if (this.labelingCallback == null && this.initializationCallback != null) {
               try {
                  ((ConnectionEnv)ce).connectionInitialize(this.initializationCallback);
               } catch (Exception var61) {
                  try {
                     ConnectionPoolManager.release((ConnectionEnv)ce);
                  } catch (ResourceException var51) {
                     throw new ResourceException("ConnectionInitializationCallback.initialize callback failed for replay initialize; connection release failed with " + var51.getMessage(), var61);
                  }

                  throw new ResourceException("ConnectionInitializationCallback.initialize failed", var61);
               }
            }
         }

         return (ConnectionEnv)ci;
      }

      if (this.serviceName == null && ce != null) {
         racInstance = ((RACConnectionEnv)ce).getRACInstance();
         if (racInstance != null) {
            this.serviceName = racInstance.getService();
            this.loggedServiceName = this.serviceName;
         }
      }

      if (requestedLabels != null && ce != null && ((ConnectionEnv)ce).drcpEnabled) {
         try {
            ConnectionPoolManager.release((ConnectionEnv)ce);
         } catch (ResourceException var62) {
         }

         ce = null;
         throw new ResourceException("label used for pooled connection");
      } else {
         if (!this.fanEnabled) {
            this.setTxAffinityContext((ConnectionEnv)ce);
         } else {
            this.setUCPTransactionAffinityContext((ConnectionEnv)ce);
         }

         if (ce != null) {
            try {
               racModule.replayBeginRequest((RACConnectionEnv)ce, this.replayInitiationTimeout);
            } catch (SQLException var68) {
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  this.debug("beginRequest failed: " + StackTraceUtils.throwable2StackTrace(var68));
               }

               try {
                  ConnectionPoolManager.release((ConnectionEnv)ce);
               } catch (ResourceException var55) {
                  throw new ResourceException("beginRequest failed; connection release failed with " + var55.getMessage(), var68);
               }

               throw new ResourceException("beginRequest failed", var68);
            }

            if (this.labelingCallback == null && this.initializationCallback != null) {
               try {
                  ((ConnectionEnv)ce).connectionInitialize(this.initializationCallback);
               } catch (Exception var63) {
                  try {
                     ConnectionPoolManager.release((ConnectionEnv)ce);
                  } catch (ResourceException var50) {
                     throw new ResourceException("ConnectionInitializationCallback.initialize callback failed for replay initialize; connection release failed with " + var50.getMessage(), var63);
                  }

                  throw new ResourceException("ConnectionInitializationCallback.initialize failed", var63);
               }
            }
         }

         return (ConnectionEnv)ci;
      }
   }

   public void release(ConnectionEnv resource) throws ResourceException {
      HAConnectionEnv hace = (HAConnectionEnv)resource;
      RACPooledConnectionState rpcs = hace.getRACPooledConnectionState();
      if (rpcs.closeOnRelease()) {
         try {
            super.endRequest(this.racModule, resource);
         } finally {
            hace.destroyForFlush(false);
            this.removeConnection(hace);
         }
      } else {
         super.release(resource);
      }

   }

   protected void destroyResource(PooledResource res) {
      ConnectionEnv ce = (ConnectionEnv)res;
      boolean wasDestroyed = false;
      synchronized(ce) {
         if (!ce.destroyed) {
            res.destroy();
            wasDestroyed = true;
         }
      }

      if (wasDestroyed) {
         this.decrementGroupCapacities(res);
         ++this.destroyed;
      }

   }

   protected void createCollections() {
      super.createCollections();
      if (!this.isSharedPool()) {
         this.available = new GroupingPooledResourceLinkedList();
      }

   }

   public void activate() throws IllegalStateException, ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("activate()");
      }

      super.activate();
   }

   public void forceShutdownExternal() throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("forceShutdownExternal");
      }

      this.turnOffDraining(this);
      super.forceShutdownExternal();
   }

   public void forceSuspend(boolean shuttingDown) throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("forceSuspend() shuttingDown=" + shuttingDown);
      }

      this.turnOffDraining(this);
      super.forceSuspend(shuttingDown);
   }

   public void forceSuspendExternal() throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("forceSuspendExternal()");
      }

      this.turnOffDraining(this);
      super.forceSuspendExternal();
   }

   public void resume() throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("resume()");
      }

      super.resume();
   }

   public void resumeExternal() throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("resumeExternal()");
      }

      super.resumeExternal();
   }

   public void shutdown() throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("shutdown");
      }

      this.stopRACModule();
      super.shutdown();
   }

   public void shutdownExternal(int operationSecs) throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("shutdownExternal");
      }

      this.stopRACModule();
      super.shutdownExternal(operationSecs);
   }

   public void start(Object unused) throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("start()");
      }

      String onsNodes = this.dsBean.getJDBCOracleParams().getOnsNodeList();
      int initialCapacity = 0;
      if (onsNodes == null || onsNodes.equals("")) {
         this.autoOns = true;
         Properties props = this.config.getPoolProperties();
         String val = null;
         if (props != null) {
            props.getProperty("initialCapacity");
         }

         if (val != null) {
            try {
               initialCapacity = Integer.parseInt((String)val);
            } catch (Exception var7) {
               initialCapacity = 0;
            }

            if (initialCapacity != 0) {
               props.setProperty("initialCapacity", "0");
            }
         }
      }

      super.start(unused);
      this.isxa = DataSourceUtil.isXADataSource(this.config.getDriver(), this.classLoader);
      if (this.testOnReserve) {
         this.localValidateOnly = false;
      } else {
         this.localValidateOnly = true;
         this.testOnReserve = true;
      }

      if (this.autoOns) {
         this.initOns();
         if (initialCapacity != 0) {
            this.setInitialCapacity(initialCapacity);
         }

      }
   }

   public void startExternal() throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("startExternal()");
      }

      super.startExternal();
      this.isxa = DataSourceUtil.isXADataSource(this.config.getDriver(), this.classLoader);
   }

   public void suspend(boolean shuttingDown) throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("suspend() shuttingDown=" + shuttingDown);
      }

      super.suspend(shuttingDown);
   }

   public void suspendExternal(int operationSecs) throws ResourceException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("suspendExternal()");
      }

      super.suspendExternal(operationSecs);
   }

   public void addHADataSourceRuntime(HAJDBCConnectionPool pool, HADataSourceRuntime runtime) {
      this.haDataSourceRuntimes.put(pool, runtime);
   }

   public HADataSourceRuntime removeHADataSourceRuntime(HAJDBCConnectionPool pool) {
      HADataSourceRuntime removed = (HADataSourceRuntime)this.haDataSourceRuntimes.remove(pool);
      return removed;
   }

   public void createInstanceRuntime(HAJDBCConnectionPool pool, ResourcePoolGroup instanceGroup, String instanceName) {
      HADataSourceRuntime runtime = (HADataSourceRuntime)this.haDataSourceRuntimes.get(pool);
      if (runtime != null && !runtime.instanceExists(instanceGroup)) {
         try {
            runtime.createInstanceRuntime(instanceGroup, instanceName);
         } catch (ManagementException var6) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("error creating instance runtime; group=" + instanceGroup + ", exception=" + StackTraceUtils.throwable2StackTrace(var6));
            }

            JDBCLogger.logRACInstanceRuntimeCreationFailed(this.name, instanceGroup.getName(), var6.getMessage());
         }
      }

   }

   public void performMaintenance() {
      super.performMaintenance();
      if (!this.doDraining() && !this.config.isPinnedToThread()) {
         this.doGravitation();
      }

   }

   private void doGravitation() {
      int gravitationShrinkFreq = ((DataSourceHAConnectionPoolConfig)this.config).getGravitationShrinkFrequencySeconds();
      if (gravitationShrinkFreq > 0 && this.gravitationShrinkTime.addAndGet(this.maintSecs) >= gravitationShrinkFreq) {
         this.gravitationShrinkTime.set(0);
         List instances = this.racModule.getInstances();
         int percentTotal = 0;

         RACInstance instance;
         for(Iterator var4 = instances.iterator(); var4.hasNext(); percentTotal += instance.getPercent()) {
            instance = (RACInstance)var4.next();
         }

         List toDestroy = new ArrayList();
         synchronized(this) {
            int capacityTotal = this.getCurrCapacity();
            Iterator var7 = instances.iterator();

            while(var7.hasNext()) {
               RACInstance instance = (RACInstance)var7.next();
               int percent = instance.getPercent();
               if (percent > 1) {
                  float targetPercent = (float)percent / (float)percentTotal;
                  ResourcePoolGroup g = this.getGroup("instance", instance.getInstance());
                  if (g != null) {
                     int capacity = g.getCurrCapacity();
                     float delta = (float)capacity - (float)capacityTotal * targetPercent;
                     if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                        this.debug("performMaintenance() gravitation shrinking: " + instance.getInstance() + ": capacity=" + capacity + ", target=" + targetPercent + ", actual=" + (float)capacity / (float)capacityTotal + ", delta=" + delta);
                     }

                     if (delta >= 1.0F) {
                        RACConnectionEnv ce = this.locateConnectionToDestroy(instance, toDestroy, g, capacity);
                        if (ce != null) {
                           toDestroy.add(ce);
                        }
                     }
                  }
               }
            }
         }

         Iterator var19 = toDestroy.iterator();

         while(var19.hasNext()) {
            RACConnectionEnv ce = (RACConnectionEnv)var19.next();

            try {
               this.destroyResource(ce);
               this.racModule.connectionClosed(ce);
               this.incrementResourcesDestroyedByShrinkingCount();
            } catch (ResourceException var16) {
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("performMaintenance() gravitation shrink error destroying connection " + ce);
               }
            }
         }
      }

   }

   private boolean doDraining() {
      return this.doDraining(this);
   }

   public boolean doDraining(HAJDBCConnectionPool haPool) {
      boolean doingDraining = false;
      List toDestroy = new ArrayList();
      List toHarvest = new ArrayList();
      List toDisable = new ArrayList();
      synchronized(this) {
         List instances = haPool.getRACModule().getInstances();
         Iterator var8 = instances.iterator();

         label90:
         while(true) {
            RACInstance instance;
            String instanceName;
            int drainCnt;
            do {
               int intervalDrainCount;
               do {
                  if (!var8.hasNext()) {
                     break label90;
                  }

                  instance = (RACInstance)var8.next();
                  intervalDrainCount = instance.getIntervalDrainCount();
               } while(intervalDrainCount == 0);

               doingDraining = true;
               instanceName = instance.getInstance();
               int currentDrainCount = instance.getCurrentDrainCount();
               int oldCount = currentDrainCount;
               currentDrainCount += intervalDrainCount;
               instance.setCurrentDrainCount(currentDrainCount);
               drainCnt = currentDrainCount / MAXDRAIN - oldCount / MAXDRAIN;
            } while(drainCnt == 0);

            toHarvest.add(instanceName);
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("performMaintenance() draining: pool " + this.getName() + " instance " + instanceName + " drainCnt=" + drainCnt);
            }

            if (!this.locateConnectionsToDrain(instance, toDestroy, drainCnt)) {
               instance.setIntervalDrainCount(0);
               instance.setCurrentDrainCount(0);
               toDisable.add(instanceName);
            }

            Iterator var15 = toDestroy.iterator();

            while(var15.hasNext()) {
               RACConnectionEnv ce = (RACConnectionEnv)var15.next();
               this.available.remove(ce);
            }
         }
      }

      if (doingDraining) {
         Iterator var6 = toDestroy.iterator();

         while(true) {
            while(var6.hasNext()) {
               RACConnectionEnv ce = (RACConnectionEnv)var6.next();
               if (this.isSharedPool() && ce.getSwitchingContext() != null) {
                  this.switchToRootPartitionAndMakeAvailable(ce);
               } else {
                  try {
                     this.destroyResource(ce);
                     this.racModule.connectionClosed(ce);
                  } catch (ResourceException var18) {
                     if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                        this.debug("performMaintenance() draining error destroying connection " + ce);
                     }
                  }
               }
            }

            this.doHarvest(toHarvest, true);
            var6 = toDisable.iterator();

            while(var6.hasNext()) {
               String instanceName = (String)var6.next();
               ResourcePoolGroup group = haPool.getGroupForInstance(instanceName);
               if (group != null) {
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("disabling group " + instanceName);
                  }

                  group.disable();
               }
            }
            break;
         }
      }

      return doingDraining;
   }

   private boolean locateConnectionsToDrain(RACInstance instance, List toDestroy, int drainCnt) {
      return this.locateConnectionsToDrain(this, instance, toDestroy, drainCnt);
   }

   private boolean locateConnectionsToDrain(HAJDBCConnectionPool haPool, RACInstance instance, List toDestroy, int drainCnt) {
      List instanceConnections = haPool.getAvailableConnections(instance, false);
      if (instanceConnections != null) {
         for(int i = 0; i < instanceConnections.size(); ++i) {
            if (drainCnt == 0) {
               return true;
            }

            RACConnectionEnv ce = (RACConnectionEnv)instanceConnections.get(i);
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("drainng identified available connection to destroy: " + ce);
            }

            toDestroy.add(ce);
            --drainCnt;
         }
      }

      if (this.reserved != null) {
         List reservedOnInstance = haPool.getReservedConnections(instance);
         if (reservedOnInstance != null) {
            Iterator var11 = reservedOnInstance.iterator();

            while(var11.hasNext()) {
               PooledResource pr = (PooledResource)var11.next();
               RACConnectionEnv ce = (RACConnectionEnv)pr;
               if (haPool.isSharingPool()) {
                  if (!ce.isRepurposeOnRelease()) {
                     if (drainCnt == 0) {
                        return true;
                     }

                     ce.setRepurposeOnRelease(true);
                     if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                        this.debug("draining marking in-use connection for repurpose on release: " + ce);
                     }

                     --drainCnt;
                  }
               } else if (!ce.getRACPooledConnectionState().isConnectionCloseOnRelease()) {
                  if (drainCnt == 0) {
                     return true;
                  }

                  ce.getRACPooledConnectionState().markConnectionCloseOnRelease();
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("draining marking in-use connection for close on release: " + ce);
                  }

                  --drainCnt;
               }
            }
         }
      }

      return false;
   }

   private RACConnectionEnv locateConnectionToDestroy(RACInstance instance, List toDestroy, ResourcePoolGroup g, int capacity) {
      List instanceConnections = ((IGroupingPooledResourceLinkedList)this.available).getSubList(new HAPooledResourceInfo((String)null, instance, (Properties)null, (Properties)null));
      if (instanceConnections != null && instanceConnections.size() > 0) {
         RACConnectionEnv ce = (RACConnectionEnv)instanceConnections.get(0);
         this.available.remove(ce);
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("gravitation shrinking identified available connection to destroy: " + ce);
         }

         return ce;
      } else {
         if (this.reserved != null && capacity > 0) {
            Iterator it = this.reserved.iterator();

            while(it.hasNext()) {
               RACConnectionEnv ce = (RACConnectionEnv)it.next();
               if (g.equals(ce.getGroup("instance"))) {
                  ce.getRACPooledConnectionState().markConnectionCloseOnRelease();
                  this.incrementResourcesDestroyedByShrinkingCount();
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("gravitation shrinking marking in-use connection for close on release: " + ce);
                  }
                  break;
               }
            }
         }

         return null;
      }
   }

   public long getFailedRCLBBasedBorrowCount() {
      return this.racModule.getFailedRCLBBasedBorrowCount();
   }

   public long getSuccessfulRCLBBasedBorrowCount() {
      return this.racModule.getSuccessfulRCLBBasedBorrowCount();
   }

   public long getFailedAffinityBasedBorrowCount() {
      return this.racModule.getFailedAffinityBasedBorrowCount();
   }

   public long getSuccessfulAffinityBasedBorrowCount() {
      return this.racModule.getSuccessfulAffinityBasedBorrowCount();
   }

   protected void processFailCountExceededDisableThreshold() {
      if (!this.fanEnabled) {
         super.processFailCountExceededDisableThreshold();
      } else if (!this.disabledUponResetFailure) {
         int capacity_now = this.getCurrCapacity();
         synchronized(this.forSynchronizingSelfDisabling) {
            if (!this.disabledUponResetFailure && this.state == 101 && capacity_now == 0) {
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("processFailCountExceededDisableThreshold() currCapacity=0, suspending");
               }

               CommonLogger.logSuspendingPoolDueToFailures(this.name, this.countToDisablePool);
               this.resetFailCount = 0;
               this.disabledUponResetFailure = true;
               this.reason_for_suspension = " It was suspended at " + new Date() + " because of " + this.countToDisablePool + " consecutive connect failures and there are no remaining connections.";
               this.state = 102;
            }
         }
      }

   }

   public void initOracleHelper() throws ResourceException {
      if (this.oracleVersion == -1) {
         throw new ResourceException("Active GridLink can only run with an Oracle driver");
      } else {
         this.fanEnabled = this.dsBean.getJDBCOracleParams().isFanEnabled();
         if (this.racModule == null) {
            this.racModule = RACModuleFactory.createInstance(this);
            this.oracleHelper = this.racModule;
         }

         if (!this.autoOns) {
            this.initOns();
         }
      }
   }

   public void initOns() throws ResourceException {
      this.initOns(this.racModule);
   }

   public void initOns(RACModule racModule) throws ResourceException {
      if (!this.fanEnabled) {
         JDBCLogger.logNotRegisteringForFANEvents(this.dsBean.getName(), this.loggedServiceName);
      } else {
         String onsNodes = this.dsBean.getJDBCOracleParams().getOnsNodeList();
         String wallet = this.dsBean.getJDBCOracleParams().getOnsWalletFile();
         String password = this.dsBean.getJDBCOracleParams().getOnsWalletPassword();
         if ((onsNodes == null || onsNodes.equals("")) && this.oracleVersion < 1200) {
            throw new ResourceException("ONS list required for pre-12c driver");
         } else {
            ONSConfigurationHelper onsHelper = new ONSConfigurationHelper();
            onsHelper.setNodes(onsNodes);
            onsHelper.setWalletFile(wallet);
            if (password != null) {
               onsHelper.setWalletPassword("XXXXXXXX");
            }

            String onsConfig = onsHelper.toString();
            JDBCLogger.logRegisteringForFANEvents(this.dsBean.getName(), this.loggedServiceName, onsConfig);
            onsHelper.setWalletPassword(password);
            onsConfig = onsHelper.toString();
            racModule.setONSConfiguration(onsConfig);

            try {
               racModule.start();
            } catch (Exception var8) {
               JDBCLogger.logONSFailure(this.dsBean.getName());
            }

         }
      }
   }

   private void stopRACModule() throws ResourceException {
      JDBCLogger.logUnregisteringForFANEvents(this.name, this.loggedServiceName, this.dsBean.getJDBCOracleParams().getOnsNodeList());
      if (this.racModule != null) {
         this.racModule.stop();
      }

   }

   public RACModule getRACModule() {
      return this.racModule;
   }

   private int getInitialCapacity() {
      return this.dsBean.getJDBCConnectionPoolParams().getInitialCapacity();
   }

   private int getRemainingCapacity() {
      return this.getMaxCapacity() - this.getCurrCapacity();
   }

   private HAConnectionEnv openConnectionToInstance(RACInstance instance, boolean reserve, Properties additionalProperties) throws ResourceException {
      List clist = new ArrayList();
      PooledResourceInfo pri = null;
      if (instance != null || additionalProperties != null) {
         pri = new HAPooledResourceInfo(this.getJDBCURL(), instance, (Properties)null, additionalProperties);
         ((HAPooledResourceInfo)pri).setRefreshToSpecificInstance(true);
      }

      if (reserve) {
         return (HAConnectionEnv)this.reserveResource(-2, pri, false, true);
      } else {
         PooledResourceInfo[] resourceInfoList = new PooledResourceInfo[1];
         Arrays.fill(resourceInfoList, pri);
         this.createResources(1, resourceInfoList, clist);
         if (clist.size() == 0) {
            return null;
         } else {
            HAConnectionEnv hace = (HAConnectionEnv)clist.get(0);
            HAPooledResourceInfo hapri = (HAPooledResourceInfo)hace.getPooledResourceInfo();
            if (hapri != null && hapri.isRefreshToSpecificInstance()) {
               hapri.setRefreshToSpecificInstance(false);
            }

            return hace;
         }
      }
   }

   PooledResourceInfo getPooledResourceInfo(RACInstance instance) {
      return this.getPooledResourceInfo(instance, (Properties)null);
   }

   public PooledResourceInfo getPooledResourceInfo(RACInstance instance, Properties labels) {
      this.setAffinityContextKeyNameIfNecessary(instance);
      PooledResourceInfo ret = new HAPooledResourceInfo(this.dsBean.getJDBCDriverParams().getUrl(), instance, labels, (Properties)null);
      return ret;
   }

   public int getWeightForInstance(String instanceName) {
      return this.racModule.getInstanceWeight(instanceName);
   }

   public boolean getAffForInstance(String instanceName) {
      return this.racModule.getInstanceAffValue(instanceName);
   }

   public void getAvailableAndBorrowedConnections(List available, List borrowed) {
      PooledResource[] resources = this.getResources();
      if (resources != null && resources.length > 0) {
         for(int i = 0; i < resources.length; ++i) {
            if (resources[i].getUsed()) {
               borrowed.add(resources[i]);
            } else {
               available.add(resources[i]);
            }
         }
      }

   }

   public void getAvailableAndBorrowedConnections(List available, List borrowed, List instances, boolean removeAvailable) {
      if (instances == null) {
         throw new AssertionError("instances argument null");
      } else {
         PooledResourceInfo pri = new HAPooledResourceInfo((String)null, (String)null, instances);
         List matchingAvailable = this.getAvailableMatching(pri);
         if (matchingAvailable != null) {
            available.addAll(matchingAvailable);
            if (removeAvailable) {
               this.removeFromAvailableForProcessing(matchingAvailable);
            }
         }

         List matchingReserved = this.getReservedMatching(pri);
         if (matchingReserved != null) {
            borrowed.addAll(matchingReserved);
         }

      }
   }

   public List getAvailableConnections(RACInstance instance, boolean remove) {
      List toReturn = null;
      PooledResourceInfo pri = new HAPooledResourceInfo((String)null, (String)null, instance);
      List matchingAvailable = this.getAvailableMatching(pri);
      if (matchingAvailable != null) {
         toReturn = new ArrayList();
         toReturn.addAll(matchingAvailable);
         if (remove) {
            this.removeFromAvailableForProcessing(toReturn);
         }
      }

      return toReturn;
   }

   public List getReservedConnections(RACInstance instance) {
      List toReturn = null;
      List matchingAvailable = null;
      if (this.reserved instanceof IGroupingPooledResourceSet) {
         PooledResourceInfo pri = new HAPooledResourceInfo((String)null, (String)null, instance);
         matchingAvailable = this.getReservedMatching(pri);
         if (matchingAvailable != null) {
            toReturn = new ArrayList();
            toReturn.addAll(matchingAvailable);
         }

         return toReturn;
      } else {
         return this.getReservedConnectionsForInstance(instance);
      }
   }

   private List getReservedConnectionsForInstance(RACInstance instance) {
      List toReturn = new ArrayList();
      String instanceName = instance.getInstance();
      ResourcePoolGroup group = this.getGroup("instance", instanceName);
      if (group != null) {
         Iterator it = this.reserved.iterator();

         while(it.hasNext()) {
            RACConnectionEnv ce = (RACConnectionEnv)it.next();
            ResourcePoolGroup instanceGroup = ce.getGroup("instance");
            if (group.equals(ce.getGroup("instance"))) {
               toReturn.add(ce);
            }
         }
      }

      return toReturn.isEmpty() ? null : toReturn;
   }

   public String getAffinityContextKey() {
      return this.affinityContextKey;
   }

   public void initAffinityKeyIfNecessary() throws ResourceException {
      if (this.databaseName == null) {
         RACInstance racInstance = null;
         ConnectionEnv ce = this.reserveInternal(this.reserveTimeoutSecs);
         if (ce == null) {
            throw new ResourceException("Unable to determine database name for affinity context key, no available connection");
         } else {
            try {
               racInstance = ((RACConnectionEnv)ce).getRACInstance();
               if (racInstance == null) {
                  throw new ResourceException("Unable to determine database name for affinity context key; no RAC instance information for connection");
               }

               this.initDatabaseServiceNames(racInstance);
            } finally {
               this.release(ce);
            }

            this.setAffinityContextKeyNameIfNecessary(racInstance);
         }
      }
   }

   private void initDatabaseServiceNames(RACInstance racInstance) {
      this.databaseName = racInstance.getDatabase();
      if (this.databaseName == null) {
         throw new AssertionError("Unable to determine database name for affinity context key");
      } else {
         if (this.serviceName == null) {
            this.serviceName = racInstance.getService();
            this.loggedServiceName = this.serviceName;
            if (this.serviceName == null) {
               throw new AssertionError("Unable to determine service name for affinity context key");
            }
         }

      }
   }

   protected void setAffinityContextKeyNameIfNecessary(RACInstance racInstance) {
      if (this.databaseName == null) {
         this.initDatabaseServiceNames(racInstance);
      }

      if (this.affinityContextKey == null) {
         this.affinityContextKey = HAUtil.getInstance().getAffinityContextKey(this.databaseName, this.serviceName);
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("affinity context key: " + this.affinityContextKey);
         }

      }
   }

   private ConnectionEnv reserveWithTxAffinity(AuthenticatedSubject user, int waitSeconds, Properties requestedLabels) throws ResourceException {
      if (!this.isxa) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("non-XA data source, not checking tx affinity context");
         }

         return null;
      } else {
         this.initAffinityKeyIfNecessary();
         Transaction tx = null;

         try {
            tx = (Transaction)this.tm.getTransaction();
            if (tx != null) {
               Object affinityContext = tx.getProperty(this.getAffinityContextKey());
               if (affinityContext == null) {
                  return null;
               }

               if (affinityContext instanceof Properties) {
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("ignoring Properties tx affinity context: " + affinityContext);
                  }

                  return null;
               }

               RACInstance instance = RACInstanceFactory.getInstance().create(this.racAffinityContextHelper.getDatabaseName(affinityContext), this.racAffinityContextHelper.getInstanceName(affinityContext), this.racAffinityContextHelper.getServiceName(affinityContext));

               try {
                  ConnectionEnv ce = this.getConnectionToInstance(instance, waitSeconds, requestedLabels);
                  if (ce == null && JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("unable to obtain connection based on tx affinity context. instance=" + instance);
                  }

                  return ce;
               } catch (ResourceException var8) {
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("unabled to obtain connection based on tx affinity context. instance=" + instance + ", exception=" + var8);
                  }
               }
            }
         } catch (SystemException var9) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("error while trying to get transaction affinity context: " + var9);
            }
         } catch (RACAffinityContextException var10) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("error while trying to get transaction affinity context: " + var10);
            }
         }

         return null;
      }
   }

   private void setTxAffinityContext(ConnectionEnv ce) {
      if (ce != null) {
         if (this.isxa) {
            Transaction tx = null;

            try {
               tx = (Transaction)this.tm.getTransaction();
               if (tx != null) {
                  Object affinityContext = tx.getProperty(this.getAffinityContextKey());
                  if (affinityContext != null) {
                     return;
                  }

                  HAConnectionEnv hace = (HAConnectionEnv)ce;
                  RACInstance racInstance = hace.getRACPooledConnectionState().getRACInstance();
                  tx.setProperty(this.getAffinityContextKey(), (Serializable)this.racAffinityContextHelper.createAffinityContext(racInstance.getDatabase(), racInstance.getService(), racInstance.getInstance(), true));
               }
            } catch (SystemException var6) {
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("error while trying to set transaction affinity context: " + var6);
               }
            } catch (RACAffinityContextException var7) {
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("error while trying to set transaction affinity context: " + var7);
               }
            }

         }
      }
   }

   private void setUCPTransactionAffinityContext(ConnectionEnv ce) {
      if (ce != null) {
         if (!this.isxa) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("non-XA data source, not setting UCP tx affinity context");
            }

         } else {
            if (this.xaAffinityCallback != null && this.xaAffinityCallback.isApplicationContextAvailable() && this.xaAffinityCallback.getConnectionAffinityContext() == null) {
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("setting Transaction affinity context");
               }

               HAConnectionEnv hace = (HAConnectionEnv)ce;
               RACInstance racInstance = hace.getRACPooledConnectionState().getRACInstance();
               if (racInstance == null) {
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("RACInstance not set on connection state, not setting UCP tx affinity context");
                  }

                  return;
               }

               try {
                  Object affinityContext = this.racAffinityContextHelper.createAffinityContext(racInstance.getDatabase(), racInstance.getService(), racInstance.getInstance(), true);
                  this.xaAffinityCallback.setConnectionAffinityContext(affinityContext);
               } catch (RACAffinityContextException var6) {
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("Error creating UCP affinity context, not setting UCP tx affinity context: " + StackTraceUtils.throwable2StackTrace(var6));
                  }
               }
            }

         }
      }
   }

   public HAConnectionEnv getConnectionToInstance(RACInstance instance, int waitSeconds, Properties requestedLabels) throws ResourceException {
      ResourcePoolGroup group = this.getGroup("instance", instance.getInstance());
      PooledResourceInfo pri = this.getPooledResourceInfo(instance, requestedLabels);
      HAConnectionEnv ret = null;
      boolean var12 = false;

      HAConnectionEnv var7;
      HAPooledResourceInfo hapri;
      label161: {
         label162: {
            try {
               label166: {
                  var12 = true;
                  ((HAPooledResourceInfo)pri).setRefreshToSpecificInstance(true);
                  if (group != null) {
                     if (group.isEnabled()) {
                        ret = (HAConnectionEnv)this.reserveResource(waitSeconds, pri);
                        if (ret != null) {
                           var7 = ret;
                           var12 = false;
                           break label166;
                        }
                     } else if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                        this.debug("getConnectionFromPool() instance=" + instance + ": group disabled");
                     }
                  } else {
                     if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                        this.debug("getConnectionFromPool() no connections exist yet for instance: " + instance);
                     }

                     ret = (HAConnectionEnv)this.reserveResource(waitSeconds, pri);
                     if (ret != null) {
                        var7 = ret;
                        var12 = false;
                        break label161;
                     }
                  }

                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("getConnectionToInstance() unable to get connection to instance " + instance);
                  }

                  var7 = null;
                  var12 = false;
                  break label162;
               }
            } finally {
               if (var12) {
                  if (ret != null) {
                     HAPooledResourceInfo hapri = (HAPooledResourceInfo)ret.getPooledResourceInfo();
                     if (hapri != null && hapri.isRefreshToSpecificInstance()) {
                        hapri.setRefreshToSpecificInstance(false);
                     }
                  }

               }
            }

            if (ret != null) {
               hapri = (HAPooledResourceInfo)ret.getPooledResourceInfo();
               if (hapri != null && hapri.isRefreshToSpecificInstance()) {
                  hapri.setRefreshToSpecificInstance(false);
               }
            }

            return var7;
         }

         if (ret != null) {
            hapri = (HAPooledResourceInfo)ret.getPooledResourceInfo();
            if (hapri != null && hapri.isRefreshToSpecificInstance()) {
               hapri.setRefreshToSpecificInstance(false);
            }
         }

         return var7;
      }

      if (ret != null) {
         hapri = (HAPooledResourceInfo)ret.getPooledResourceInfo();
         if (hapri != null && hapri.isRefreshToSpecificInstance()) {
            hapri.setRefreshToSpecificInstance(false);
         }
      }

      return var7;
   }

   public HAConnectionEnv getExistingConnectionToInstance(RACInstance instance, int waitSeconds, Properties requestedLabels) throws ResourceException {
      return this.getExistingConnectionToInstance(this, instance, waitSeconds, requestedLabels);
   }

   public HAConnectionEnv getExistingConnectionToInstance(HAJDBCConnectionPool haPool, RACInstance instance, int waitSeconds, Properties requestedLabels) throws ResourceException {
      HAConnectionEnv ce;
      PooledResourceInfo pri;
      if (this.config.isPinnedToThread()) {
         ce = null;
         ConnectionPool.ConnectionStore pinned = (ConnectionPool.ConnectionStore)connections.get();
         if (pinned == null) {
            throw new RuntimeException("PinnedToThread is supported ONLY with AuditableThread");
         }

         pri = this.getPooledResourceInfo(instance, (Properties)null);
         if ((ce = (HAConnectionEnv)pinned.get(this, pri)) != null) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("Pinned: Reserved " + ce + " for thread " + Thread.currentThread());
            }

            ce.setUsed(true);
            ce.setOwner(Thread.currentThread());
            return ce;
         }
      }

      ce = null;
      SwitchingContext switchingContext = SwitchingContextManager.getInstance().get();
      HAConnectionEnv c;
      ResourcePoolGroup group;
      if (switchingContext != null) {
         group = haPool.getGroupForInstance(instance.getInstance());
         if (group != null && group.isEnabled()) {
            pri = haPool.getPooledResourceInfo(instance, requestedLabels);
            c = (HAConnectionEnv)this.reserveResource(waitSeconds, pri, false, false);
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("getExistingConnectionToInstance() instance=" + instance + ", pdb=" + ((HAPooledResourceInfo)pri).getPDBName() + " found " + c);
            }

            if (c != null) {
               return c;
            }
         }
      }

      group = this.getGroupForInstance(instance.getInstance());
      if (group == null) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getExistingConnectionToInstance() instance=" + instance + ": no corresponding group");
         }

         return null;
      } else if (!group.isEnabled()) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getExistingConnectionToInstance() instance=" + instance + ": group disabled");
         }

         return null;
      } else {
         pri = this.getPooledResourceInfo(instance, requestedLabels);
         c = (HAConnectionEnv)this.reserveResource(waitSeconds, pri, false, false);
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getExistingConnectionToInstance() instance=" + instance + " returning " + c);
         }

         return c;
      }
   }

   public RACConnectionEnv getExistingConnection(int waitSeconds, Properties requestedLabels) throws ResourceException {
      PooledResourceInfo pri = null;
      Set instances = null;
      SwitchingContext switchingContext = SwitchingContextManager.getInstance().get();
      if (switchingContext != null) {
         HAJDBCConnectionPool scp = (HAJDBCConnectionPool)switchingContext.getPool();
         instances = scp.getRACModule().getServiceInstanceNames();
      }

      if (requestedLabels == null) {
         pri = new HAPooledResourceInfo((String)null, (RACInstance)null, (String)null, (String)null, instances, (Properties)null, (Properties)null);
      } else {
         pri = new HAPooledResourceInfo((String)null, (RACInstance)null, (String)null, (String)null, instances, requestedLabels, (Properties)null);
      }

      HAConnectionEnv c = (HAConnectionEnv)this.reserveResource(waitSeconds, pri, false, false);
      return c;
   }

   public void removePooledResource(RACConnectionEnv conn) throws ResourceException {
      this.removeResource(conn);
   }

   public HAConnectionEnv createConnectionToInstance(RACInstance instance) throws ResourceException {
      if (instance == null) {
         return this.openConnectionToInstance((RACInstance)null, false, (Properties)null);
      } else {
         HAConnectionEnv hace = this.openConnectionToInstance(instance, true, (Properties)null);
         if (this.isXA()) {
            PooledResourceInfo pri = hace.getPooledResourceInfo();
            if (pri instanceof HAPooledResourceInfo) {
               String intendedInstance = instance.getInstance();
               RACInstance actualInstance = ((HAPooledResourceInfo)pri).getRACInstance();
               if (actualInstance == null || intendedInstance == null || !intendedInstance.equals(actualInstance.getInstance())) {
                  this.release(hace);
                  return null;
               }
            }
         }

         return hace;
      }
   }

   public int getMaxPoolSize() {
      return this.getMaxCapacity();
   }

   public int getMinPoolSize() {
      return this.dsBean.getJDBCConnectionPoolParams().isSet("MinCapacity") ? this.dsBean.getJDBCConnectionPoolParams().getMinCapacity() : this.dsBean.getJDBCConnectionPoolParams().getInitialCapacity();
   }

   public String getPoolName() {
      return this.getName();
   }

   public int getRemainingPoolCapacity() {
      return this.getRemainingCapacity();
   }

   public int getCurrentPoolCapacity() {
      return this.getCurrCapacity();
   }

   public String getJDBCURL() {
      return this.config.getURL();
   }

   public void fcfDownEvent(RACModuleFailoverEvent event) throws ResourceException {
      this.fcfDownEvent(this, this.racModule, event);
   }

   public RACConnectionEnv createTemporaryConnection() throws ResourceException {
      RACConnectionEnv res = (RACConnectionEnv)this.getResourceFactory().createResource((PooledResourceInfo)null);
      res.setInfected(true);
      return res;
   }

   public void fcfDownEvent(HAJDBCConnectionPool haPool, RACModule racModule, RACModuleFailoverEvent event) throws ResourceException {
      int drainTimeout = 0;
      int cntPerInterval = 0;
      List instances = null;
      List availableToRepurpose = null;
      synchronized(this) {
         String instance = event.getInstanceName();
         String instanceName;
         Iterator var12;
         if (instance == null) {
            instances = racModule.getInstancesForHost(event.getHostName());
            this.turnOffDraining(haPool);
         } else {
            if (event.isPlanned()) {
               drainTimeout = event.getDrainTimeout();
               if (drainTimeout == 0 && this.dsBean != null) {
                  JDBCPropertyBean drainTimeoutProp = this.dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.drainTimeout");
                  if (drainTimeoutProp != null) {
                     instanceName = drainTimeoutProp.getValue();

                     try {
                        drainTimeout = Integer.parseInt(instanceName);
                        if (drainTimeout < 0) {
                           drainTimeout = 0;
                        } else if (drainTimeout > MAXDRAIN) {
                           drainTimeout = MAXDRAIN;
                        }
                     } catch (NumberFormatException var16) {
                     }
                  }
               }

               if (drainTimeout > 0) {
                  List ins = haPool.getRACModule().getInstances();
                  boolean found = false;
                  var12 = ins.iterator();

                  while(var12.hasNext()) {
                     RACInstance i = (RACInstance)var12.next();
                     if (i.getIntervalDrainCount() <= 0 && !i.getInstance().equals(instance)) {
                        found = true;
                        break;
                     }
                  }

                  if (!found) {
                     drainTimeout = 0;
                     this.turnOffDraining(haPool);
                  }
               }

               if (drainTimeout != 0 && JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("Drain Timeout " + drainTimeout);
               }
            }

            instances = new ArrayList(1);
            ((List)instances).add(instance);
         }

         if (event.isServiceEvent()) {
            JDBCLogger.logServiceDownEvent(this.name, this.loggedServiceName, instances.toString());
         } else {
            JDBCLogger.logNodeDownEvent(this.name, this.loggedServiceName, event.getHostName());
         }

         if (event.isPlanned()) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("PLANNED down event for instance " + instance);
            }
         } else if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("UNPLANNED down event for instance " + instance);
         }

         ArrayList borrowed = new ArrayList();
         ArrayList available = new ArrayList();
         if (haPool.isSharingPool()) {
            haPool.getAvailableAndBorrowedConnections(available, borrowed, (List)instances, true);
         } else {
            haPool.getAvailableAndBorrowedConnections(available, borrowed);
         }

         if (racModule.isReplayDriver()) {
            for(int i = 0; i < borrowed.size(); ++i) {
               ConnectionEnv ce = (ConnectionEnv)borrowed.get(i);
               ce.isReplayed = true;
            }
         }

         if (drainTimeout > 0) {
            RACInstance racInstance = racModule.getRACInstance(instance);
            if (racInstance != null) {
               int drainTotal = 0;
               ResourcePoolGroup g = haPool.getGroupForInstance(racInstance.getInstance());
               if (g != null) {
                  drainTotal = g.getCurrCapacity();
               }

               cntPerInterval = drainTotal * DRAININTERVAL * MAXDRAIN / drainTimeout + 1;
               racInstance.setIntervalDrainCount(cntPerInterval);
               racInstance.setCurrentDrainCount(0);
               racInstance.setDownEvent(event);
            }
         }

         if (cntPerInterval == 0) {
            if (haPool.isSharingPool() && event.isPlanned()) {
               availableToRepurpose = available;
               var12 = borrowed.iterator();

               while(var12.hasNext()) {
                  Object o = var12.next();
                  ((ConnectionEnv)o).setRepurposeOnRelease(true);
               }
            } else {
               racModule.processConnectionsOnDownEvent(event, available, borrowed);
            }
         }

         if (cntPerInterval == 0) {
            Iterator var21 = ((List)instances).iterator();

            while(var21.hasNext()) {
               instanceName = (String)var21.next();
               ResourcePoolGroup group = haPool.getGroupForInstance(instanceName);
               if (group != null) {
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("disabling group " + instanceName);
                  }

                  group.disable();
               }
            }
         }
      }

      if (haPool.isSharingPool() && availableToRepurpose != null) {
         Iterator it = availableToRepurpose.iterator();

         while(it.hasNext()) {
            ConnectionEnv ce = (ConnectionEnv)it.next();
            this.switchToRootPartitionAndMakeAvailable(ce);
         }
      }

      this.doHarvest((List)instances, cntPerInterval > 0);
   }

   public ResourcePoolGroup getPoolGroup() {
      return null;
   }

   public ResourcePoolGroup getGroupForInstance(String instance) {
      return this.getGroup("instance", instance);
   }

   public int fcfUpEvent(RACModuleFailoverEvent event) throws ResourceException {
      return this.fcfUpEvent(this, this.racModule, event);
   }

   public int fcfUpEvent(HAJDBCConnectionPool haPool, RACModule racModule, RACModuleFailoverEvent event) throws ResourceException {
      int ret = false;
      synchronized(this) {
         String instance = event.getInstanceName();
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("Up event for instance " + instance);
         }

         if (event.isServiceEvent()) {
            if (instance == null) {
               JDBCLogger.logServiceUpEvent(this.name, this.loggedServiceName);
            } else {
               JDBCLogger.logServiceInstanceUpEvent(this.name, this.loggedServiceName, instance);
            }
         } else {
            JDBCLogger.logNodeUpEvent(this.name, this.loggedServiceName, event.getHostName());
         }

         List available = new ArrayList();
         List borrowed = new ArrayList();
         haPool.getAvailableAndBorrowedConnections(available, borrowed);
         if (this.state == 102) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("resuming from suspended");
            }

            this.state = 101;
         }

         if (instance != null) {
            RACInstance racInstance = this.racModule.getRACInstance(instance);
            if (racInstance != null) {
               racInstance.setIntervalDrainCount(0);
               racInstance.setCurrentDrainCount(0);
            }
         }

         int ret = racModule.processConnectionsOnUpEvent(event, available, borrowed, this.getMinPoolSize(), this.getMaxCapacity());
         ResourcePoolGroup group = haPool.getGroupForInstance(instance);
         if (group != null) {
            group.enable();
            HADataSourceRuntime dsruntime = (HADataSourceRuntime)this.haDataSourceRuntimes.get(haPool);
            if (dsruntime != null && !dsruntime.instanceExists(group)) {
               try {
                  dsruntime.createInstanceRuntime(group, instance);
               } catch (ManagementException var13) {
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("error creating instance runtime; group=" + group + ", exception=" + StackTraceUtils.throwable2StackTrace(var13));
                  }

                  JDBCLogger.logRACInstanceRuntimeCreationFailed(this.name, instance, var13.getMessage());
               }
            }
         }

         return ret;
      }
   }

   public ConnectionAffinityCallback.AffinityPolicy getAffinityPolicy() {
      return this.affinityPolicy;
   }

   public DataAffinityCallback getDataAffinityCallback() {
      return this.dsService.getDataAffinityCallback();
   }

   public AffinityCallback getSessionAffinityCallback() {
      return this.sessionAffinityCallback;
   }

   public AffinityCallback getXAAffinityCallback() {
      return this.xaAffinityCallback;
   }

   public RACModulePool getSharedRACModulePool() {
      return this;
   }

   public void markConnectionGood(ConnectionEnv pooledConnection) {
      if (pooledConnection instanceof RACConnectionEnv) {
         this.racModule.markConnectionGood((RACConnectionEnv)pooledConnection);
      }

   }

   public PooledResource matchResource(PooledResourceInfo pri) throws ResourceException {
      ConnectionLabelingCallback clc = this.labelingCallback;
      if (clc != null && pri instanceof HAPooledResourceInfo) {
         HAPooledResourceInfo lci = (HAPooledResourceInfo)pri;
         Properties requestedLabels = lci.getLabels();
         RACInstance instance = lci.getRACInstance();
         synchronized(this) {
            int leastCost = Integer.MAX_VALUE;
            int bestMatch = -1;
            boolean bestMatchHighCost = false;
            List instanceConnections = ((IGroupingPooledResourceLinkedList)this.available).getSubList(pri);
            if (instanceConnections == null) {
               return null;
            } else {
               int i;
               for(i = 0; i < instanceConnections.size(); ++i) {
                  ConnectionEnv res = (ConnectionEnv)instanceConnections.get(i);
                  Properties currentLabels = res.getLabels();
                  if (currentLabels == null) {
                     currentLabels = new Properties();
                  }

                  int cost = clc.cost(requestedLabels, currentLabels);
                  boolean isHighCost;
                  if (cost == 0) {
                     isHighCost = this.available.remove(res);
                     if (!isHighCost) {
                     }

                     res.setNeedsLabelingConfigure(false);
                     return res;
                  }

                  isHighCost = cost >= this.config.getConnectionLabelingHighCost();
                  if (cost < leastCost) {
                     leastCost = cost;
                     bestMatch = i;
                     bestMatchHighCost = isHighCost;
                  }
               }

               if (bestMatch == -1) {
                  return null;
               } else {
                  if (bestMatchHighCost) {
                     i = this.getCurrCapacity();
                     if (i < this.getMinCapacity() || i < this.config.getHighCostConnectionReuseThreshold()) {
                        return null;
                     }
                  }

                  ConnectionEnv res = (ConnectionEnv)instanceConnections.get(bestMatch);
                  boolean removed = this.available.remove(res);
                  if (!removed) {
                  }

                  res.setNeedsLabelingConfigure(true);
                  return res;
               }
            }
         }
      } else {
         return super.matchResource(pri);
      }
   }

   protected boolean disableGroupOnTestFailCount() {
      return true;
   }

   protected boolean isRootPartitionGroupEnabled(ConnectionEnv ce) {
      ResourcePoolGroup instanceGroup = ce.getGroup("instance");
      return instanceGroup != null ? instanceGroup.isEnabled() : false;
   }

   public boolean removeFromAvailableForProcessing(List resources) {
      if (resources == null) {
         return true;
      } else {
         boolean allremoved = true;
         Iterator var3 = resources.iterator();

         while(var3.hasNext()) {
            PooledResource pr = (PooledResource)var3.next();
            if (this.available.remove(pr)) {
               ++this.beingProcessed;
            } else {
               allremoved = false;
            }
         }

         return allremoved;
      }
   }

   protected void replayUpdateConnectionState(ConnectionEnv pooledConnection) throws SQLException {
      if (this.reserved instanceof IndexedGroupingPooledResourceSet) {
         synchronized(this) {
            Collection collection = null;
            if (this.reserved.contains(pooledConnection)) {
               this.reserved.remove(pooledConnection);
               collection = this.reserved;
            } else if (this.available.contains(pooledConnection)) {
               this.available.remove(pooledConnection);
               collection = this.available;
            }

            try {
               super.replayUpdateConnectionState(pooledConnection);
            } finally {
               if (collection != null) {
                  ((Collection)collection).add(pooledConnection);
               }

            }
         }
      } else {
         super.replayUpdateConnectionState(pooledConnection);
      }

   }

   private void doHarvest(List instanceNames, boolean draining) {
      if (this.connectionHarvestTriggerCount != -1) {
         if (instanceNames != null && instanceNames.size() != 0) {
            List toharvest = new ArrayList();
            String stackTrace;
            synchronized(this) {
               Iterator reservedIterator = this.reserved.iterator();

               label71:
               while(true) {
                  HAConnectionEnv hace;
                  while(true) {
                     do {
                        if (!reservedIterator.hasNext()) {
                           break label71;
                        }

                        hace = (HAConnectionEnv)reservedIterator.next();
                        stackTrace = hace.getRACInstance().getInstance();
                     } while(!instanceNames.contains(stackTrace));

                     try {
                        if (!hace.isConnectionHarvestableAndLock()) {
                           continue;
                        }
                        break;
                     } catch (SQLException var12) {
                     }
                  }

                  toharvest.add(hace);
               }
            }

            Iterator var4 = toharvest.iterator();

            while(var4.hasNext()) {
               HAConnectionEnv hace = (HAConnectionEnv)var4.next();
               if (hace.connectionHarvestingCallback != null) {
                  try {
                     hace.connectionHarvestingCallback.cleanup();
                  } catch (Throwable var11) {
                     JDBCLogger.logExceptionFromConnectionHarvestingCallback(var11.getMessage());
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        stackTrace = StackTraceUtils.throwable2StackTrace(var11);
                        JdbcDebug.JDBCCONN.debug(stackTrace);
                     }
                  }
               }

               if (hace.connectionHarvestedCallback != null) {
                  hace.connectionHarvestedCallback.connectionHarvested();
               }

               try {
                  if (!draining) {
                     this.releaseResource(hace);
                  }
               } catch (ResourceException var10) {
               }
            }

         }
      }
   }

   private void turnOffDraining(HAJDBCConnectionPool haPool) {
      List instances = haPool.getRACModule().getInstances();
      Iterator var3 = instances.iterator();

      while(var3.hasNext()) {
         RACInstance instance = (RACInstance)var3.next();
         if (instance.getIntervalDrainCount() > 0) {
            instance.setIntervalDrainCount(4000 * MAXDRAIN);
            instance.setCurrentDrainCount(0);
         }
      }

   }

   private void switchToRootPartitionAndMakeAvailable(ConnectionEnv ce) {
      try {
         this.switchToRootPartition(ce);
         synchronized(this) {
            this.available.addFirst(ce);
            --this.beingProcessed;
         }
      } catch (ResourceException var5) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("error switching connection to root partition: " + StackTraceUtils.throwable2StackTrace(var5));
         }

         ce.destroyForFlush(false);
         this.decrementGroupCapacities(ce);
      }

   }

   private final void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("HAConnectionPool[" + this.name + "]: " + msg);
   }

   protected ConnectionPool.ConnectionStoreDelegate createConnectionStoreDelegate() {
      return new HAConnectionStoreDelegate();
   }

   public HAConnectionEnv reserveInternalResource() throws ResourceException {
      return (HAConnectionEnv)this.reserveInternal(0);
   }

   protected static class HAConnectionStoreDelegate extends ConnectionPool.ConnectionStoreDelegate {
      public HAConnectionStoreDelegate() {
      }

      public Object get(Object key) {
         return this.get(key, (PooledResourceInfo)null);
      }

      public Object get(Object key, PooledResourceInfo pri) {
         Object h = this.conns.get(key);
         if (h != null) {
            if (h instanceof Holder) {
               Holder holder = (Holder)h;
               return holder.get(pri);
            }

            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("HAConnectionStoreDelegate.get() key=" + key + ", pri=" + pri + ": invalid connection holder " + h);
            }
         }

         return null;
      }

      public boolean isEmpty(Object key) {
         Holder h = (Holder)this.conns.get(key);
         return h == null ? true : h.isEmpty();
      }

      public void put(Object key, Object value) {
         Object s = this.conns.get(key);
         if (s == null) {
            s = new Holder();
            this.conns.put(key, s);
         }

         ((Holder)s).add((HAConnectionEnv)value);
      }

      public void remove(Object key, Object value) {
         Holder h = (Holder)this.conns.get(key);
         if (h != null) {
            h.remove((HAConnectionEnv)value);
         }
      }

      public boolean contains(Object key, Object value) {
         Holder h = (Holder)this.conns.get(key);
         return h == null ? false : h.contains((HAConnectionEnv)value);
      }

      public boolean containsType(Object key, PooledResourceInfo pri) {
         Holder h = (Holder)this.conns.get(key);
         return h == null ? false : h.containsType(key, pri);
      }

      class Holder {
         Map instances2Conns = new HashMap();

         HAConnectionEnv get() {
            if (this.instances2Conns.isEmpty()) {
               return null;
            } else {
               HAConnectionEnv hace = (HAConnectionEnv)this.instances2Conns.values().iterator().next();
               return hace;
            }
         }

         HAConnectionEnv get(PooledResourceInfo pri) {
            if (pri != null && pri instanceof HAPooledResourceInfo) {
               HAPooledResourceInfo hapri = (HAPooledResourceInfo)pri;
               HAConnectionEnv hace = (HAConnectionEnv)this.instances2Conns.get(hapri.getRACInstance().getInstance());
               return hace;
            } else {
               return this.get();
            }
         }

         void add(HAConnectionEnv ce) {
            RACInstance instance = ce.getRACInstance();
            HAConnectionEnv existing = (HAConnectionEnv)this.instances2Conns.put(instance.getInstance(), ce);
            if (existing != null && JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("HAConnectionStoreDelegate.Holder: existing connection for instance " + instance + ": " + existing);
            }

         }

         void remove(HAConnectionEnv ce) {
            RACInstance instance = ce.getRACInstance();
            this.instances2Conns.remove(instance.getInstance());
         }

         boolean contains(HAConnectionEnv ce) {
            return this.instances2Conns.containsValue(ce);
         }

         public boolean containsType(Object key, PooledResourceInfo pri) {
            return pri != null && pri instanceof HAPooledResourceInfo ? this.instances2Conns.containsKey(((HAPooledResourceInfo)pri).getRACInstance().getInstance()) : false;
         }

         boolean isEmpty() {
            return this.instances2Conns.isEmpty();
         }
      }
   }
}
