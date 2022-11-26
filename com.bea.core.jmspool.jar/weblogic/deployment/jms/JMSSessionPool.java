package weblogic.deployment.jms;

import com.oracle.jms.jmspool.ReferenceManager;
import java.lang.ref.ReferenceQueue;
import java.security.AccessController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.QueueConnection;
import javax.jms.TopicConnection;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceFactory;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceLimitException;
import weblogic.common.resourcepool.ResourcePool;
import weblogic.common.resourcepool.ResourcePoolImpl;
import weblogic.kernel.KernelStatus;
import weblogic.management.ManagementException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.LocatorUtilities;
import weblogic.work.WorkManager;

public class JMSSessionPool implements PooledResourceFactory, NakedTimerListener {
   public static final String INITIAL_CONTEXT_FACTORY_PROP = "java.naming.factory.initial";
   public static final String JNDI_URL_PROP = "java.naming.provider.url";
   public static final String CONNECTION_FACTORY_JNDI_NAME_PROP = "JNDIName";
   public static final String RESOURCE_REF_NAME_AS_JNDI_NAME = "ResourceRefNameAsJNDIName";
   public static final String CONNECTION_USER_NAME_PROP = "UserName";
   public static final String CONNECTION_PASSWORD_PROP = "Password";
   public static final String TESTER_ENABLED_PROP = "TesterEnabled";
   public static final String MAX_SESSIONS_PROP = "MaxSessions";
   public static final String APPLICATION_NAME_PROP = "ApplicationName";
   public static final String COMPONENT_NAME_PROP = "ComponentName";
   public static final String COMPONENT_TYPE_PROP = "ComponentType";
   public static final String JMS_APPLICATION_CONTEXT_PROP = "JmsApplicationContext";
   public static final String RUN_AS_SUBJECT_PROP = "RunAsSubject";
   public static final String CONNECTION_FACTORY_PROP = "ConnectionFactory";
   public static final long IDLE_CONNECTION_CLEANUP_INTERVAL = 300000L;
   public static final int SESSION_TYPE_QUEUE = 1;
   public static final int SESSION_TYPE_TOPIC = 2;
   public static final int SESSION_TYPE_EITHER = 3;
   public static final int SESSION_TYPE_DONT_CARE = 4;
   public static final int WRAP_STYLE_NONE = 0;
   public static final int WRAP_STYLE_EJB20 = 1;
   public static final int WRAP_STYLE_SERVLET = 2;
   public static final int WRAP_STYLE_MDB = 3;
   public static final String SESSION_POOL_DEFAULT_MAX_SIZE = "10240";
   public static final String SESSION_POOL_CAPACITY_INCREMENT = "1";
   public static final String SESSION_POOL_RESERVE_TIMEOUT = "5";
   public static final String SESSION_POOL_MAX_RETRIES = "8";
   public static final String SESSION_POOL_MAX_SIZE_PROPERTY = "weblogic.deployment.jms.MaxSessionPoolSize";
   public static final int SESSION_POOL_INITIAL_SIZE = 4;
   public static final String SESSION_POOL_INITIAL_SIZE_PROPERTY = "weblogic.deployment.jms.InitialSessionPoolSize";
   public static final int SESSION_POOL_SHRINK_FREQUENCY = 300;
   public static final String SESSION_POOL_SHRINK_DISABLED = "weblogic.deployment.jms.DisablePoolShrinking";
   public static final String CLIENT_ID_POOLING_ENABLED = "weblogic.deployment.jms.ClientIDPoolingEnabled";
   public static final int SESSION_POOL_RESERVE_ATTEMPTS = 6;
   private String poolName;
   private Map poolProps;
   private boolean closing;
   private JMSSessionPoolManager parent;
   private JMSSessionPoolTester tester;
   protected WrappedClassManager wrapperManager;
   private JMSSessionPoolExceptionListener exceptionListener;
   private HashMap enlistedSessions;
   private HashMap enlistedSecondaryContexts;
   private HashMap helpers;
   private HashMap primaryContextHelpers;
   private ResourcePoolImpl resourcePool;
   private JMSSessionPoolRuntime runtime;
   private Timer cleanupTimer;
   private int sessionPoolInitialSize = 4;
   private boolean poolFullyInitialized;
   private final ReferenceManager referenceManager = (ReferenceManager)LocatorUtilities.getService(ReferenceManager.class);
   private boolean sessionPoolShrinking = true;
   private boolean clientIDPoolingEnabled;
   private Object helpersLock = new Object();
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected JMSSessionPool(JMSSessionPoolManager parent, String poolName, Map poolProps, WrappedClassManager wrapperManager) throws JMSException {
      this.parent = parent;
      this.poolName = poolName;
      this.poolProps = poolProps;
      this.wrapperManager = wrapperManager;
      this.exceptionListener = new JMSSessionPoolExceptionListener();
      this.enlistedSessions = new HashMap();
      this.enlistedSecondaryContexts = new HashMap();
      this.helpers = new HashMap();
      this.primaryContextHelpers = new HashMap();
      SecurityServiceManager.pushSubject(kernelId, kernelId);

      try {
         try {
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Creating a resource pool for session pool " + poolName);
            }

            this.sessionPoolShrinking = true;
            String shrinkingDisabled = System.getProperty("weblogic.deployment.jms.DisablePoolShrinking");
            if (shrinkingDisabled != null && shrinkingDisabled.equalsIgnoreCase("true")) {
               this.sessionPoolShrinking = false;
            }

            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Setting Session Pool Shrinking to " + this.sessionPoolShrinking);
            }

            this.clientIDPoolingEnabled = false;
            String value = System.getProperty("weblogic.deployment.jms.ClientIDPoolingEnabled");
            if (value != null && value.equalsIgnoreCase("true")) {
               this.clientIDPoolingEnabled = true;
            }

            if (JMSPoolDebug.logger.isDebugEnabled() && this.clientIDPoolingEnabled) {
               JMSPoolDebug.logger.debug("Setting Client ID Pooling Enabled to " + this.clientIDPoolingEnabled + " for pool " + poolName);
            }

            this.sessionPoolInitialSize = 4;

            try {
               String poolSize = System.getProperty("weblogic.deployment.jms.InitialSessionPoolSize");
               if (poolSize != null && !poolSize.equals("")) {
                  this.sessionPoolInitialSize = Integer.parseInt(poolSize);
                  if (this.sessionPoolInitialSize > 4 || this.sessionPoolInitialSize < 1) {
                     this.sessionPoolInitialSize = 4;
                  }
               }
            } catch (Throwable var15) {
               (new RuntimeException("error processing weblogic.deployment.jms.InitialSessionPoolSize")).printStackTrace();
            }

            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Setting Initial Session Pool Size " + this.sessionPoolInitialSize);
            }

            this.resourcePool = new JMSResourcePoolImpl(this);
            Properties resPoolProps = new Properties();
            resPoolProps.setProperty("name", poolName);
            if (poolProps.containsKey("MaxSessions")) {
               resPoolProps.setProperty("maxCapacity", (String)poolProps.get("MaxSessions"));
            } else {
               String maxSessionPool = System.getProperty("weblogic.deployment.jms.MaxSessionPoolSize");
               if (maxSessionPool == null || maxSessionPool.equals("")) {
                  maxSessionPool = "10240";
               }

               JMSPoolDebug.logger.debug("Setting Max Session Pool Size " + maxSessionPool);
               resPoolProps.setProperty("maxCapacity", maxSessionPool);
            }

            resPoolProps.setProperty("initialCapacity", "0");
            resPoolProps.setProperty("resvTimeoutSeconds", "5");
            resPoolProps.setProperty("capacityIncrement", "1");
            resPoolProps.setProperty("maxResvRetry", "8");
            resPoolProps.setProperty("shrinkEnabled", "false");
            resPoolProps.setProperty("testOnReserve", "true");
            resPoolProps.setProperty("quietMessages", "true");
            this.resourcePool.start(resPoolProps);
            this.resourcePool.resume();
         } catch (ResourceException var16) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logResourcePoolErrorLoggable(), var16);
         }

         this.cleanupTimer = parent.getTimerManager().schedule(this, 300000L, 300000L);

         try {
            if (KernelStatus.isServer()) {
               this.runtime = PlatformHelper.getPlatformHelper().createJMSSessionPoolRuntime(poolName, this.resourcePool, this);
            }
         } catch (ManagementException var14) {
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Error registering runtime MBean " + poolName, var14);
            }
         }
      } finally {
         SecurityServiceManager.popSubject(kernelId);
      }

   }

   TimerManager getTimerManager() {
      return this.parent.getTimerManager();
   }

   WorkManager getWorkManager() {
      return this.parent.getWorkManager();
   }

   JMSSessionPoolManager getSessionPoolManager() {
      return this.parent;
   }

   public JMSSessionPoolRuntime getJMSSessionPoolRuntime() {
      return this.runtime;
   }

   private boolean startTesting(JMSConnectionHelperService helper, boolean containerAuth, String userName, String password) {
      String testerEnabled = (String)this.poolProps.get("TesterEnabled");
      if (testerEnabled != null && testerEnabled.length() != 0 && !testerEnabled.equalsIgnoreCase("true")) {
         return false;
      } else if (this.tester != null && !this.tester.isClosed()) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug(this.getName() + " Request to start pool tester ignored, tester is already running" + this.tester);
         }

         return false;
      } else {
         helper.pushSubject();

         try {
            this.createPoolTester(helper, containerAuth, userName, password);
         } finally {
            helper.popSubject();
         }

         return true;
      }
   }

   private void createPoolTester(JMSConnectionHelperService helper, boolean containerAuth, String userName, String password) {
      try {
         Connection connection = helper.getConnection();
         if (connection instanceof QueueConnection) {
            this.tester = new JMSSessionPoolTester(this, helper, 1, containerAuth, userName, password);
         } else if (connection instanceof TopicConnection) {
            this.tester = new JMSSessionPoolTester(this, helper, 2, containerAuth, userName, password);
         } else {
            this.tester = new JMSSessionPoolTester(this, helper, 3, containerAuth, userName, password);
         }

         this.tester.init();
         this.addExceptionListener(this.tester);
      } catch (JMSException var6) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Error starting connection pool tester", var6);
         }

         this.tester = null;
      }

   }

   public boolean isConnectionHealthCheckingEnabled() {
      boolean rtn = true;

      try {
         PlatformHelper.ForeignRefReturn ret = PlatformHelper.getPlatformHelper().checkForeignRef(this.poolProps);
         rtn = "enabled".equals(ret.connectionHealthChecking);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return rtn;
   }

   public PooledSession getSession(int sessionType, int wrapStyle, boolean transacted, int am, boolean containerAuth) throws JMSException {
      this.checkClosed();
      int acknowledgeMode = am;
      if (am == 0) {
         acknowledgeMode = 1;
      }

      return this.doGetSession(wrapStyle, sessionType, transacted, acknowledgeMode, false, containerAuth, (String)null, (String)null);
   }

   public PooledSecondaryContext getSecondaryContext(int wrapStyle, int sessionModeArg, boolean containerAuth, String userName, String password) throws JMSException {
      this.checkClosed();
      return this.doGetSecondaryContext(wrapStyle, sessionModeArg, false, containerAuth, userName, password);
   }

   public PooledSecondaryContext getSecondaryContext(int wrapStyle, int sessionModeArg, boolean containerAuth) throws JMSException {
      this.checkClosed();
      return this.doGetSecondaryContext(wrapStyle, sessionModeArg, false, containerAuth, (String)null, (String)null);
   }

   public PooledSession getSession(int sessionType, int wrapStyle, boolean transacted, int am, boolean containerAuth, String userName, String password) throws JMSException {
      this.checkClosed();
      int acknowledgeMode = am;
      if (am == 0) {
         acknowledgeMode = 1;
      }

      return this.doGetSession(wrapStyle, sessionType, transacted, acknowledgeMode, false, containerAuth, userName, password);
   }

   public PooledSession getNonXASession(int sessionType, int wrapStyle, int am, boolean containerAuth, String userName, String password) throws JMSException {
      this.checkClosed();
      int acknowledgeMode = am;
      if (am == 0) {
         acknowledgeMode = 1;
      }

      return this.doGetSession(wrapStyle, sessionType, false, acknowledgeMode, true, containerAuth, userName, password);
   }

   public PooledSecondaryContext getNonXASecondaryContext(int wrapStyle, int sessionModeArg, boolean containerAuth, String userName, String password) throws JMSException {
      this.checkClosed();
      return this.doGetSecondaryContext(wrapStyle, sessionModeArg, true, containerAuth, userName, password);
   }

   protected JMSConnectionHelperService getConnectionHelper(boolean containerAuth, String userName, String password) throws JMSException {
      this.checkClosed();
      ConnectionHelperKey key = new ConnectionHelperKey(containerAuth, userName, password);
      JMSConnectionHelperService ret = null;
      synchronized(this.helpersLock) {
         ret = (JMSConnectionHelperService)this.helpers.get(key);
      }

      if (ret == null) {
         JMSConnectionHelperServiceGenerator generator = (JMSConnectionHelperServiceGenerator)LocatorUtilities.getService(JMSConnectionHelperServiceGenerator.class);
         if (containerAuth) {
            ret = generator.createJMSConnectionHelperService(this.poolName, this.poolProps, containerAuth, this.wrapperManager);
         } else {
            ret = generator.createJMSConnectionHelperService(this.poolName, this.poolProps, userName, password, this.wrapperManager);
         }

         synchronized(this.helpersLock) {
            JMSConnectionHelperService jchs = (JMSConnectionHelperService)this.helpers.get(key);
            if (jchs != null) {
               ret.close();
               ret = jchs;
            } else if (!ret.getFactorySetClientID() || this.isHelpersEmpty() && this.clientIDPoolingEnabled) {
               try {
                  ret.markAsPooled();
               } catch (JMSException var13) {
                  try {
                     ret.close();
                  } catch (JMSException var12) {
                     JMSPoolDebug.logger.debug("JMSSessionPool.getConnectionHelper(containerAuth=" + containerAuth + ", userName=" + userName + "): Exception closing JMSConnectionHelper[" + ret + "]: " + var12 + " after markAsPooled() exception " + var13);
                  }

                  throw var13;
               }

               ret.getConnection().setExceptionListener(this.getExceptionListener());
               if (this.isHelpersEmpty()) {
                  ret.firstTimeInit();
                  if (this.startTesting(ret, containerAuth, userName, password)) {
                     ret.setUsedForPooltesting(true);
                  }
               }

               this.helpers.put(key, ret);
            }
         }
      }

      return ret;
   }

   protected PrimaryContextHelperService getPrimaryContextHelper(boolean containerAuth, String userName, String password) {
      try {
         this.checkClosed();
      } catch (JMSException var16) {
         throw new JMSRuntimeException(var16.getMessage(), var16.getErrorCode(), var16);
      }

      PrimaryContextHelperKey key = new PrimaryContextHelperKey(containerAuth, userName, password);
      PrimaryContextHelperService ret = null;
      synchronized(this.helpersLock) {
         ret = (PrimaryContextHelperService)this.primaryContextHelpers.get(key);
      }

      if (ret == null) {
         PrimaryContextHelperServiceGenerator generator = (PrimaryContextHelperServiceGenerator)LocatorUtilities.getService(PrimaryContextHelperServiceGenerator.class);

         try {
            if (containerAuth) {
               ret = generator.createPrimaryContextHelperService(this.poolName, this.poolProps, containerAuth, this.wrapperManager);
            } else {
               ret = generator.createPrimaryContextHelperService(this.poolName, this.poolProps, userName, password, this.wrapperManager);
            }
         } catch (JMSException var14) {
            throw new JMSRuntimeException(var14.getMessage(), var14.getErrorCode(), var14);
         }

         synchronized(this.helpersLock) {
            PrimaryContextHelperService pchs = (PrimaryContextHelperService)this.primaryContextHelpers.get(key);
            if (pchs != null) {
               ret.close();
               ret = pchs;
            } else if (!ret.getFactorySetClientID() || this.isHelpersEmpty() && this.clientIDPoolingEnabled) {
               try {
                  ret.markAsPooled();
               } catch (JMSRuntimeException var13) {
                  try {
                     ret.close();
                  } catch (JMSRuntimeException var12) {
                     JMSPoolDebug.logger.debug("JMSSessionPool.getPrimaryContextHelper(containerAuth=" + containerAuth + ", userName=" + userName + "): Exception closing PrimaryContextHelper[" + ret + "]: " + var12 + " after markAsPooled() exception " + var13);
                  }

                  throw var13;
               }

               ret.getPrimaryContext().setExceptionListener(this.getExceptionListener());
               if (this.isHelpersEmpty()) {
                  ret.firstTimeInit();

                  try {
                     JMSConnectionHelperService connectionhelper = this.getConnectionHelper(containerAuth, userName, password);
                     if (this.startTesting(connectionhelper, containerAuth, userName, password)) {
                        ret.setUsedForPooltesting(true);
                     }
                  } catch (JMSException var17) {
                     if (JMSPoolDebug.logger.isDebugEnabled()) {
                        JMSPoolDebug.logger.debug("Error getting connection for session pool tester", var17);
                     }
                  }
               }

               this.primaryContextHelpers.put(key, ret);
            }
         }
      }

      return ret;
   }

   private synchronized void completePoolInitialization(PoolMapKey key) throws ResourceException, JMSException {
      if (!this.poolFullyInitialized) {
         JMSConnectionHelperService helper = this.getConnectionHelper(key.containerAuth, key.userName, key.password);
         if (!helper.getFactorySetClientID() || this.clientIDPoolingEnabled) {
            PoolMapKey[] keys = new PoolMapKey[this.sessionPoolInitialSize];
            Arrays.fill(keys, key);
            this.resourcePool.createResources(this.sessionPoolInitialSize, keys);
            this.resourcePool.setInitialCapacity(this.sessionPoolInitialSize);
            this.resourcePool.setShrinkFrequencySeconds(300);
            this.resourcePool.setShrinkEnabled(this.sessionPoolShrinking);
         }

         this.poolFullyInitialized = true;
      }

   }

   private PooledSession doGetSession(int wrapStyle, int sessionType, boolean transacted, int acknowledgeMode, boolean ignoreXA, boolean containerAuth, String userName, String password) throws JMSException {
      PooledSession retVal = null;
      this.referenceManager.poll();
      ClientTransactionManager tm = TransactionHelper.getTransactionHelper().getTransactionManager();
      Transaction curTran = null;

      try {
         if (tm != null) {
            curTran = tm.getTransaction();
         }
      } catch (SystemException var27) {
      }

      if (curTran != null && !ignoreXA) {
         synchronized(this) {
            retVal = (PooledSession)this.enlistedSessions.remove(curTran);
         }
      }

      if (retVal == null) {
         PoolMapKey key = new PoolMapKey(JMSSessionPool.PoolMapKey.ObjectType.SESSION, sessionType, transacted, acknowledgeMode, ignoreXA, containerAuth, userName, password);
         JMSSessionHolder holder = null;

         try {
            if (!this.poolFullyInitialized) {
               this.completePoolInitialization(key);
            }

            int retryCount = 0;

            while(retryCount < 6) {
               try {
                  holder = (JMSSessionHolder)this.resourcePool.reserveResource(key);
                  break;
               } catch (ResourceLimitException var28) {
                  if (retryCount == 5) {
                     throw var28;
                  }

                  ++retryCount;
               }
            }

            if (holder == null) {
               throw JMSExceptions.getJMSException(JMSPoolLogger.logResourcePoolErrorLoggable());
            }

            ReferenceQueue refQ = this.referenceManager.getReferenceQueue();
            retVal = holder.getConnectionHelper().createPooledWrapper(holder, this.resourcePool, refQ);
            retVal.setWrapStyle(wrapStyle);
            retVal.assignFromPool(this);
            holder = null;
         } catch (ResourceException var29) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logResourcePoolErrorLoggable(), var29);
         } finally {
            if (holder != null) {
               try {
                  holder.setBroken(true);
                  this.resourcePool.releaseResource(holder);
               } catch (ResourceException var25) {
               }
            }

         }
      } else {
         retVal.setWrapStyle(wrapStyle);
         retVal.assignFromPool(this);
      }

      return retVal;
   }

   private PooledSecondaryContext doGetSecondaryContext(int wrapStyle, int sessionMode, boolean ignoreXA, boolean containerAuth, String userName, String password) throws JMSException {
      PooledSecondaryContext retVal = null;
      this.referenceManager.poll();
      ClientTransactionManager tm = TransactionHelper.getTransactionHelper().getTransactionManager();
      Transaction curTran = null;

      try {
         if (tm != null) {
            curTran = tm.getTransaction();
         }
      } catch (SystemException var25) {
      }

      if (curTran != null && !ignoreXA) {
         synchronized(this) {
            retVal = (PooledSecondaryContext)this.enlistedSecondaryContexts.remove(curTran);
         }
      }

      if (retVal == null) {
         PoolMapKey key = new PoolMapKey(JMSSessionPool.PoolMapKey.ObjectType.SECONDARY_CONTEXT, 0, sessionMode == 0, sessionMode, ignoreXA, containerAuth, userName, password);
         SecondaryContextHolder holder = null;

         try {
            if (!this.poolFullyInitialized) {
               this.completePoolInitialization(key);
            }

            int retryCount = 0;

            while(retryCount < 6) {
               try {
                  holder = (SecondaryContextHolder)this.resourcePool.reserveResource(key);
                  break;
               } catch (ResourceLimitException var26) {
                  if (retryCount == 5) {
                     throw var26;
                  }

                  ++retryCount;
               }
            }

            if (holder == null) {
               throw JMSExceptions.getJMSException(JMSPoolLogger.logResourcePoolErrorLoggable());
            }

            ReferenceQueue refQ = this.referenceManager.getReferenceQueue();
            retVal = holder.getPrimaryContextHelper().createPooledWrapper(holder, this.resourcePool, refQ);
            retVal.setWrapStyle(wrapStyle);
            retVal.assignFromPool(this);
            holder = null;
         } catch (ResourceException var27) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logResourcePoolErrorLoggable(), var27);
         } finally {
            if (holder != null) {
               try {
                  holder.setBroken(true);
                  this.resourcePool.releaseResource(holder);
               } catch (ResourceException var23) {
               }
            }

         }
      } else {
         retVal.setWrapStyle(wrapStyle);
         retVal.assignFromPool(this);
      }

      return retVal;
   }

   protected synchronized void sessionEnlistedButAvailable(Transaction curTran, PooledSession session) {
      if (curTran != null) {
         this.enlistedSessions.put(curTran, session);
      }

   }

   protected synchronized void secondaryContextEnlistedButAvailable(Transaction curTran, PooledSecondaryContext secondaryContext) {
      if (curTran != null) {
         this.enlistedSecondaryContexts.put(curTran, secondaryContext);
      }

   }

   protected synchronized void transactionCompleted(Transaction curTran) {
      if (curTran != null) {
         this.enlistedSessions.remove(curTran);
      }

   }

   protected synchronized void transactionOnSecondaryContextCompleted(Transaction curTran) {
      if (curTran != null) {
         this.enlistedSecondaryContexts.remove(curTran);
      }

   }

   public void addExceptionListener(ExceptionListener listener) {
      this.exceptionListener.addExceptionListener(listener);
   }

   public void removeExceptionListener(ExceptionListener listener) {
      this.exceptionListener.removeExceptionListener(listener);
   }

   public ExceptionListener getExceptionListener() {
      return this.exceptionListener;
   }

   protected synchronized boolean markClosing() {
      return this.closing ? false : (this.closing = true);
   }

   protected void doClose(long waitTime) throws JMSException {
      this.doClose(waitTime, false);
   }

   protected void doClose(long waitTime, boolean force) throws JMSException {
      if (this.parent.markPoolClosing(this)) {
         synchronized(this) {
            if (this.cleanupTimer != null) {
               this.cleanupTimer.cancel();
            }

            if (this.tester != null && !this.tester.isClosed()) {
               this.tester.shutDownConnection();
            }

            JMSPoolDebug.logger.debug("Closing a JMS session pool's resource pool " + this.resourcePool);
            SecurityServiceManager.pushSubject(kernelId, kernelId);

            try {
               try {
                  try {
                     if (force) {
                        this.resourcePool.forceSuspend(true);
                     } else {
                        this.resourcePool.suspend(true);
                     }
                  } catch (ResourceException var25) {
                     if (!force) {
                        this.resourcePool.forceSuspend(true);
                     }
                  }

                  this.resourcePool.shutdown();
               } catch (ResourceException var26) {
                  JMSPoolLogger.logJMSSessionPoolCloseError(var26.toString());
               } catch (Throwable var27) {
                  JMSPoolLogger.logJMSSessionPoolCloseError(var27.toString());
               }

               JMSPoolDebug.logger.debug("Closing the primary context helpers");
               synchronized(this.helpersLock) {
                  Iterator allPrimaryContextHelpers = this.primaryContextHelpers.values().iterator();

                  while(allPrimaryContextHelpers.hasNext()) {
                     PrimaryContextHelperService thisPrimaryContextHelper = (PrimaryContextHelperService)allPrimaryContextHelpers.next();

                     try {
                        thisPrimaryContextHelper.close();
                     } catch (JMSRuntimeException var29) {
                        if (JMSPoolDebug.logger.isDebugEnabled()) {
                           JMSPoolDebug.logger.debug("Exception while closing primary context helper", var29);
                        }
                     }
                  }

                  JMSPoolDebug.logger.debug("The primary context helpers are closed");
                  this.primaryContextHelpers.clear();
                  JMSPoolDebug.logger.debug("Closing the connection helpers");
                  Iterator allHelpers = this.helpers.values().iterator();

                  while(allHelpers.hasNext()) {
                     JMSConnectionHelperService helper = (JMSConnectionHelperService)allHelpers.next();

                     try {
                        helper.close();
                     } catch (JMSException var28) {
                        if (JMSPoolDebug.logger.isDebugEnabled()) {
                           JMSPoolDebug.logger.debug("Exception while closing connection helper", var28);
                        }
                     }
                  }

                  JMSPoolDebug.logger.debug("The connection helpers are closed");
                  this.helpers.clear();
               }

               if (this.runtime != null) {
                  try {
                     this.runtime.unregister();
                  } catch (ManagementException var23) {
                     if (JMSPoolDebug.logger.isDebugEnabled()) {
                        JMSPoolDebug.logger.debug("unregister failed " + this.poolName + " " + this.runtime + " " + var23.toString());
                     }
                  } catch (Throwable var24) {
                     if (JMSPoolDebug.logger.isDebugEnabled()) {
                        JMSPoolDebug.logger.debug("unregister failed " + this.poolName + " " + this.runtime + " " + var24.toString());
                     }
                  }
               }
            } finally {
               SecurityServiceManager.popSubject(kernelId);
            }

         }
      }
   }

   public void close(long waitTime) throws JMSException {
      try {
         this.doClose(waitTime);
      } finally {
         this.parent.poolDestroyed(this);
      }

   }

   public void timerExpired(Timer t) {
      LinkedList toProcessHelpers = new LinkedList();
      LinkedList toProcessPrimaryContextHelpers = new LinkedList();
      Iterator allHelpers = null;
      Iterator allPrimaryContextHelpers = null;
      synchronized(this.helpersLock) {
         allHelpers = ((HashMap)this.helpers.clone()).values().iterator();
         allPrimaryContextHelpers = ((HashMap)this.primaryContextHelpers.clone()).values().iterator();
      }

      while(allHelpers != null && allHelpers.hasNext()) {
         JMSConnectionHelperService helper = (JMSConnectionHelperService)allHelpers.next();
         JMSPoolDebug.logger.debug("Closing a JMS connection that is no longer in use");
         synchronized(helper) {
            if (!helper.isUsedForPooltesting() && helper.getReferenceCount() <= 0) {
               try {
                  helper.close();
               } catch (JMSException var15) {
                  JMSPoolLogger.logJMSSessionPoolCloseError(var15.toString());
               }

               toProcessHelpers.add(helper);
            }
         }
      }

      while(allPrimaryContextHelpers != null && allPrimaryContextHelpers.hasNext()) {
         PrimaryContextHelperService thisPrimaryContextHelper = (PrimaryContextHelperService)allPrimaryContextHelpers.next();
         JMSPoolDebug.logger.debug("Closing a primary context helper that is no longer in use");
         synchronized(thisPrimaryContextHelper) {
            if (!thisPrimaryContextHelper.isUsedForPooltesting() && thisPrimaryContextHelper.getReferenceCount() <= 0) {
               try {
                  thisPrimaryContextHelper.close();
               } catch (JMSRuntimeException var13) {
                  JMSPoolLogger.logJMSSessionPoolCloseError(var13.toString());
               }

               toProcessPrimaryContextHelpers.add(thisPrimaryContextHelper);
            }
         }
      }

      synchronized(this.helpersLock) {
         Iterator jchsIt = toProcessHelpers.iterator();

         while(true) {
            while(jchsIt.hasNext()) {
               JMSConnectionHelperService jchs = (JMSConnectionHelperService)jchsIt.next();
               Iterator helperIt = this.helpers.entrySet().iterator();

               while(helperIt.hasNext()) {
                  Map.Entry pair = (Map.Entry)helperIt.next();
                  if (((JMSConnectionHelperService)pair.getValue()).equals(jchs)) {
                     this.helpers.remove(pair.getKey());
                     break;
                  }
               }
            }

            Iterator pchsIt = toProcessPrimaryContextHelpers.iterator();

            while(true) {
               while(pchsIt.hasNext()) {
                  PrimaryContextHelperService pchs = (PrimaryContextHelperService)pchsIt.next();
                  Iterator pchelperIt = this.primaryContextHelpers.entrySet().iterator();

                  while(pchelperIt.hasNext()) {
                     Map.Entry pair = (Map.Entry)pchelperIt.next();
                     if (((PrimaryContextHelperService)pair.getValue()).equals(pchs)) {
                        this.primaryContextHelpers.remove(pair.getKey());
                        break;
                     }
                  }
               }

               return;
            }
         }
      }
   }

   public PooledResource createResource(PooledResourceInfo pInfo) throws ResourceException {
      PoolMapKey key = (PoolMapKey)pInfo;
      if (key == null) {
         key = new PoolMapKey();
      }

      try {
         if (key.objectType == JMSSessionPool.PoolMapKey.ObjectType.SESSION) {
            JMSConnectionHelperService helper = this.getConnectionHelper(key.containerAuth, key.userName, key.password);
            JMSSessionHolder session = helper.getNewSession(key.sessionType, key.transacted, key.ackMode, key.ignoreXA);
            helper.incrementReferenceCount();
            return session;
         } else {
            PrimaryContextHelperService helper = this.getPrimaryContextHelper(key.containerAuth, key.userName, key.password);
            SecondaryContextHolder secondaryContextHolder = helper.getNewSecondaryContext(key.ackMode, key.ignoreXA);
            helper.incrementReferenceCount();
            return secondaryContextHolder;
         }
      } catch (JMSException var5) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Error creating a new PooledSession", var5);
         }

         throw new ResourceException(var5.toString());
      }
   }

   public void refreshResource(PooledResource res) throws ResourceException {
      res.destroy();
      this.createResource((PooledResourceInfo)null);
   }

   private synchronized void checkClosed() throws JMSException {
      if (this.closing) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSSessionPoolShutDownLoggable());
      }
   }

   public synchronized boolean isClosed() {
      return this.closing;
   }

   public String getName() {
      return this.poolName;
   }

   public ResourcePool getResourcePool() {
      return this.resourcePool;
   }

   public int getNumConnectionObjects() {
      synchronized(this.helpersLock) {
         return this.helpers.size();
      }
   }

   public int getNumPrimaryContextObjects() {
      synchronized(this.helpersLock) {
         return this.primaryContextHelpers.size();
      }
   }

   protected boolean getClientIDPoolingEnabled() {
      return this.clientIDPoolingEnabled;
   }

   private boolean isHelpersEmpty() {
      return this.helpers.isEmpty() && this.primaryContextHelpers.isEmpty();
   }

   protected abstract static class AbstractHelperKey {
      private String userName;
      private String password;
      private boolean containerAuth;

      protected AbstractHelperKey(boolean containerAuth, String userName, String password) {
         this.containerAuth = containerAuth;
         this.userName = userName;
         this.password = password;
      }

      public boolean equals(Object o) {
         try {
            AbstractHelperKey k = (AbstractHelperKey)o;
            return this.containerAuth == k.containerAuth && (this.userName == null && k.userName == null || this.userName != null && this.userName.equals(k.userName)) && (this.password == null && k.password == null || this.password != null && this.password.equals(k.password));
         } catch (ClassCastException var3) {
            return false;
         }
      }

      public int hashCode() {
         int ret = this.containerAuth ? 5 : 0;
         if (this.userName != null) {
            ret += this.userName.hashCode();
         }

         if (this.password != null) {
            ret += this.password.hashCode();
         }

         return ret;
      }
   }

   protected static final class PrimaryContextHelperKey extends AbstractHelperKey {
      protected PrimaryContextHelperKey(boolean containerAuth, String userName, String password) {
         super(containerAuth, userName, password);
      }
   }

   protected static final class ConnectionHelperKey extends AbstractHelperKey {
      protected ConnectionHelperKey(boolean containerAuth, String userName, String password) {
         super(containerAuth, userName, password);
      }
   }

   protected static final class PoolMapKey implements PooledResourceInfo {
      protected int sessionType;
      protected boolean transacted;
      protected int ackMode;
      protected boolean ignoreXA;
      protected boolean containerAuth;
      protected String userName;
      protected String password;
      protected ObjectType objectType;

      protected PoolMapKey(ObjectType objectType, int sessionType, boolean transacted, int ackMode, boolean ignoreXA, boolean containerAuth, String userName, String password) {
         this.objectType = objectType;
         this.sessionType = sessionType;
         this.transacted = transacted;
         this.ackMode = ackMode;
         this.ignoreXA = ignoreXA;
         this.containerAuth = containerAuth;
         this.userName = userName;
         this.password = password;
      }

      protected PoolMapKey() {
         this.objectType = JMSSessionPool.PoolMapKey.ObjectType.SESSION;
         this.sessionType = 4;
         this.transacted = this.ignoreXA = false;
         this.ackMode = 1;
         this.containerAuth = true;
         this.userName = this.password = null;
      }

      public boolean equals(PooledResourceInfo o) {
         PoolMapKey k = (PoolMapKey)o;
         return k.objectType == this.objectType && k.transacted == this.transacted && k.sessionType == this.sessionType && k.ackMode == this.ackMode && k.ignoreXA == this.ignoreXA && k.containerAuth == this.containerAuth && (k.userName == null && this.userName == null || this.userName != null && this.userName.equals(k.userName)) && (k.password == null && this.password == null || this.password != null && this.password.equals(k.password));
      }

      public String toString() {
         return "Object type = " + this.objectType + " session type = " + this.sessionType + " transacted = " + this.transacted + " ackMode = " + this.ackMode + " ignoreXA = " + this.ignoreXA + " containerAuth = " + this.containerAuth + " userName = " + this.userName;
      }

      public static enum ObjectType {
         SECONDARY_CONTEXT,
         SESSION;
      }
   }
}
