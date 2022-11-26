package weblogic.connector.outbound;

import com.bea.connector.diagnostic.OutboundAdapterType;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NoPermissionException;
import javax.naming.Reference;
import javax.resource.spi.ApplicationServerInternalException;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapterAssociation;
import weblogic.application.ApplicationAccess;
import weblogic.common.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.AppDefinedObjectInfo;
import weblogic.connector.common.ConnectorDiagnosticImageSource;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.common.Utils;
import weblogic.connector.deploy.JNDIHandler;
import weblogic.connector.deploy.RAOutboundDeployer;
import weblogic.connector.exception.RAException;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.external.PropSetterTable;
import weblogic.connector.external.RAValidationInfo;
import weblogic.connector.monitoring.ConnectorComponentRuntimeMBeanImpl;
import weblogic.connector.monitoring.outbound.ConnectionPoolRuntimeMBeanImpl;
import weblogic.connector.monitoring.outbound.FailedConnectionPoolRuntimeMBeanImpl;
import weblogic.connector.utils.ValidationMessage;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.UndeploymentException;
import weblogic.management.runtime.ConnectorComponentRuntimeMBean;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;

public class RAOutboundManager {
   private boolean isOutsideAppAccessEnabled = false;
   private final byte SUSPEND = 1;
   private final byte RESUME = 2;
   private final byte SHUTDOWN = 3;
   private final byte FORCE_SUSPEND = 4;
   protected Hashtable mcfMap = new Hashtable();
   protected Hashtable outboundInfoMap = new Hashtable();
   protected HashMap pendingOutboundInfoMap = new HashMap();
   protected Hashtable poolMap = new Hashtable();
   protected Hashtable jndiPoolLookupMap = new Hashtable();
   protected Hashtable failedOutboundInfoMap = new Hashtable();
   protected Hashtable failedPoolRTs = new Hashtable();
   private final AppDefinedConnectionFactoryManager appDefinedConnectionFactoryManager;
   private RAInstanceManager raInstanceMgr;
   private String moduleName;

   protected RAOutboundManager() {
      this.appDefinedConnectionFactoryManager = new AppDefinedConnectionFactoryManager(this.moduleName, this);
   }

   public RAOutboundManager(RAInstanceManager aRAIM) {
      Debug.enter(this, "Constructor");
      this.raInstanceMgr = aRAIM;
      this.isOutsideAppAccessEnabled = this.raInstanceMgr.getRAInfo().isEnableAccessOutsideApp();
      this.appDefinedConnectionFactoryManager = new AppDefinedConnectionFactoryManager(aRAIM.getModuleName(), this);
      Debug.exit(this, "Constructor");
   }

   public void activate() throws RAOutboundException {
      Debug.enter(this, "activate()");

      try {
         Iterator keyIterator = this.mcfMap.keySet().iterator();
         this.debugModule("Looping through all the outbound connections to resume the pools and do a JNDI bind");

         while(keyIterator.hasNext()) {
            UniversalResourceKey key = (UniversalResourceKey)keyIterator.next();
            OutboundInfo outboundInfo = (OutboundInfo)this.outboundInfoMap.get(key);

            try {
               this.preparePoolInternal(key);
               this.activatePoolInternal(key);
            } catch (Throwable var8) {
               this.markPoolAsFailed(outboundInfo);
               this.recordPoolCreationError(outboundInfo, var8);
               this.reThrowPoolCreationErrorIfNeeded(var8);
            }
         }

         this.appDefinedConnectionFactoryManager.activateResources();
         this.debugModule("Looping through all the failed outbound connections to setup the failed pools that in HEALTH_CRITICAL state");
         Iterator var10 = this.failedOutboundInfoMap.values().iterator();

         while(var10.hasNext()) {
            OutboundInfo outboundInfo = (OutboundInfo)var10.next();
            this.shutdownAndCleanupNormalPool(outboundInfo);
            this.setupFailedPool(outboundInfo);
         }
      } finally {
         this.debugModule("Done Looping through all the outbound connections to resume the pools and do a JNDI bind");
         Debug.exit(this, "activate()");
      }

   }

   public void deactivate() throws RAOutboundException {
      Debug.enter(this, "deactivate()");

      try {
         RAOutboundException deactivateException = new RAOutboundException();
         Iterator keys = this.poolMap.keySet().iterator();

         while(keys.hasNext()) {
            UniversalResourceKey key = (UniversalResourceKey)keys.next();

            try {
               this.deactivatePool(key);
            } catch (RAOutboundException var8) {
               deactivateException.addError(var8);
            }
         }

         this.rollbackNormalPools(deactivateException);
         this.shutdownAndCleanupAllFailedPools();
         this.appDefinedConnectionFactoryManager.deactivateResources();
         if (deactivateException.size() > 0) {
            throw deactivateException;
         }
      } finally {
         Debug.exit(this, "deactivate()");
      }

   }

   public void deactivatePool(UniversalResourceKey key) throws RAOutboundException {
      OutboundInfo outboundInfo = (OutboundInfo)this.outboundInfoMap.get(key);
      ConnectionPool connectionPool = (ConnectionPool)this.poolMap.get(key);

      try {
         Debug.println("JNDI unbind : " + key);
         JNDIHandler.unbindConnectionFactory(outboundInfo, this, connectionPool.getConnectionFactoryNoCreate());
      } catch (UndeploymentException var6) {
         throw new RAOutboundException(var6);
      }

      try {
         Debug.println("Suspend the pool");
         connectionPool.forceSuspend(false);
      } catch (ResourceException var5) {
         throw new RAOutboundException(var5);
      }
   }

   private void rollbackNormalPools(RAOutboundException shutdownException) {
      Debug.enter(this, "rollback()");

      try {
         Iterator keys = this.poolMap.keySet().iterator();

         while(keys.hasNext()) {
            UniversalResourceKey key = (UniversalResourceKey)keys.next();

            try {
               this.internalShutdownPool(key);
            } catch (RAOutboundException var8) {
               shutdownException.addError(var8);
            }
         }

         Debug.println("Reset the hashtables");
         this.resetNormalPoolInfos();
      } finally {
         Debug.exit(this, "rollback()");
      }

   }

   public void shutdownPool(UniversalResourceKey key) throws RAOutboundException {
      this.internalShutdownPool(key);
      this.outboundInfoMap.remove(key);
      this.poolMap.remove(key);
   }

   private void internalShutdownPool(UniversalResourceKey key) throws RAOutboundException {
      OutboundInfo outboundInfo = (OutboundInfo)this.outboundInfoMap.get(key);
      ConnectionPool connectionPool = (ConnectionPool)this.poolMap.get(key);
      if (outboundInfo != null && connectionPool != null) {
         try {
            Debug.println("Shutdown the pool");
            connectionPool.shutdown();
         } catch (ResourceException var6) {
            String exMsg = Debug.getExceptionShutdownException(outboundInfo.getJndiName(), "", "");
            throw new RAOutboundException(exMsg, var6);
         }
      }
   }

   public void stop() throws RAOutboundException {
      this.suspend();
      this.appDefinedConnectionFactoryManager.shutdown();
   }

   public void halt() throws RAOutboundException {
      this.changeStateOfPools((byte)3);
      this.shutdownAndCleanupAllFailedPools();
   }

   public void suspend() throws RAOutboundException {
      this.changeStateOfPools((byte)4);
   }

   public void resume() throws RAOutboundException {
      this.changeStateOfPools((byte)2);
   }

   public void createConnectionFactorys() throws RAOutboundException {
      Debug.enter(this, "initialize()");

      try {
         Debug.println("Get OutboundInfos list");
         List outboundList = this.raInstanceMgr.getRAInfo().getOutboundInfos();
         Iterator outboundListIterator = outboundList.iterator();
         this.initModuleName();
         Debug.println("OutboundInfos List size : " + outboundList.size());

         while(outboundListIterator.hasNext()) {
            OutboundInfo outboundInfo = (OutboundInfo)outboundListIterator.next();
            this.createConnectionFactory(outboundInfo);
         }
      } finally {
         Debug.exit(this, "initialize()");
      }

   }

   public void createConnectionFactory(OutboundInfo outboundInfo) throws RAOutboundException {
      if (hasValiadtionOrRuntimeError(outboundInfo)) {
         if (this.getRA().isDeployAsAWhole()) {
            throw new RAOutboundException("should not reach here when deploy-as-a-whole is true AND there is validation errors for pool " + outboundInfo.getJndiName());
         } else {
            Debug.println((Object)this, (String)(".createConnectionFactory() this pool has error in validation phase already, will skip it: " + outboundInfo.getJndiName()));
            this.failedOutboundInfoMap.put(outboundInfo.getJndiName(), outboundInfo);
         }
      } else {
         try {
            this.createConnectionFactoryInternal(outboundInfo);
         } catch (Throwable var3) {
            this.markPoolAsFailed(outboundInfo);
            this.recordPoolCreationError(outboundInfo, var3);
            this.reThrowPoolCreationErrorIfNeeded(var3);
         }

      }
   }

   public void checkForUnPrepare() throws RAException {
      this.appDefinedConnectionFactoryManager.checkForUnPrepare();
   }

   void doCreateAppDefinedConnectionFactory(UniversalResourceKey key, ConnectionFactoryMetaInfo cfMetaInfo) throws javax.resource.ResourceException {
      try {
         cfMetaInfo.mcf = this.initializeMCF(cfMetaInfo.outBoundInfo);
         cfMetaInfo.pool = this.createConnectionPool(key, cfMetaInfo.mcf, cfMetaInfo.outBoundInfo, this.raInstanceMgr.getApplicationName(), this.raInstanceMgr.getComponentName());
         cfMetaInfo.pool.setupRuntime((ConnectorComponentRuntimeMBeanImpl)this.raInstanceMgr.getConnectorComponentRuntimeMBean(), this);
         this.doActivatePool(key, cfMetaInfo.outBoundInfo, cfMetaInfo.pool);
         JNDIHandler.exportObject(cfMetaInfo.pool.getConnectionFactory(), key.getJndi());
         JNDIHandler.setReference(cfMetaInfo.ref, cfMetaInfo.pool.getConnectionFactoryNoCreate());
         JNDIHandler.storeRAOutboundManager(key, this);
         this.jndiPoolLookupMap.put(key, cfMetaInfo.pool);
      } catch (RemoteException var5) {
         String objectClass = cfMetaInfo.pool.getConnectionFactoryNoCreate().getClass().toString();
         this.recordPoolCreationError(cfMetaInfo.outBoundInfo, var5);
         this.processFailedResource(key, cfMetaInfo);
         throw new javax.resource.ResourceException(ConnectorLogger.getExceptionExportObject(objectClass, this.getModuleName(), key.getDefApp(), key.getDefModule(), key.getDefComp(), key.getJndi()));
      } catch (Throwable var6) {
         this.recordPoolCreationError(cfMetaInfo.outBoundInfo, var6);
         this.processFailedResource(key, cfMetaInfo);
         throw new javax.resource.ResourceException("Failed to create/init Connection Factory bean " + key.getJndi(), var6);
      }
   }

   private void processFailedResource(UniversalResourceKey key, ConnectionFactoryMetaInfo cfMetaInfo) {
      if (cfMetaInfo.mcf != null) {
         this.raInstanceMgr.getAdapterLayer().invokePreDestroy(cfMetaInfo.mcf, "managed connection factory");
         cfMetaInfo.mcf = null;
      }

      if (cfMetaInfo.pool != null) {
         try {
            this.shutdownPool(key, cfMetaInfo.pool, true);
            cfMetaInfo.pool = null;
         } catch (Throwable var6) {
            ConnectorLogger.logInternalDestroyFailedPoolError(key.toString(), var6.getMessage(), var6);
         }
      }

      ConnectorComponentRuntimeMBeanImpl connRuntimeMbean = (ConnectorComponentRuntimeMBeanImpl)this.getRA().getConnectorComponentRuntimeMBean();

      try {
         cfMetaInfo.failPoolRunMBean = this.createFailedPoolRuntime(key, cfMetaInfo.outBoundInfo, connRuntimeMbean);
         connRuntimeMbean.addConnPoolRuntime(cfMetaInfo.failPoolRunMBean);
      } catch (ManagementException var5) {
         Debug.logInitCPRTMBeanError(cfMetaInfo.outBoundInfo.getJndiName(), StackTraceUtils.throwable2StackTrace(var5));
      }

   }

   public Reference createAppDefinedConnectionFactory(ConnectionFactoryResourceBean cfBean, UniversalResourceKey key) throws javax.resource.ResourceException {
      AppDefinedConnectionFactoryInfo info = (AppDefinedConnectionFactoryInfo)this.appDefinedConnectionFactoryManager.findCompatibleResource(key, cfBean);
      Reference ref = null;
      if (info == null) {
         OutboundInfo outInfo = this.raInstanceMgr.getRAInfo().buildOutboundInfo(cfBean, key);
         ConnectionFactoryMetaInfo cfMetaInfo = new ConnectionFactoryMetaInfo();
         cfMetaInfo.outBoundInfo = outInfo;

         try {
            ref = JNDIHandler.createReference(outInfo.getCFImpl(), key);
            cfMetaInfo.ref = ref;
         } catch (RAOutboundException var8) {
            throw new javax.resource.ResourceException(var8.getMessage(), var8);
         }

         this.appDefinedConnectionFactoryManager.createAppDefinedConnectionFactoryInfo(key, cfMetaInfo, cfBean);
      }

      return ref;
   }

   public Object revokeAppdefinedConnectionFactory(UniversalResourceKey key) throws javax.resource.ResourceException {
      return this.appDefinedConnectionFactoryManager.revokeResource(key);
   }

   public void destroyAppdefinedConnectionFactory(Object handle) throws javax.resource.ResourceException {
      this.appDefinedConnectionFactoryManager.destroyResource((AppDefinedObjectInfo)handle);
   }

   void doDestroyAppdefinedConnectionFactory(UniversalResourceKey key, ConnectionFactoryMetaInfo cfMetaInfo) throws javax.resource.ResourceException {
      if (cfMetaInfo.pool != null) {
         this.shutdownPool(key, cfMetaInfo.pool, false);
         JNDIHandler.unexportObject(cfMetaInfo.pool.getConnectionFactory(), key.getJndi());
         JNDIHandler.removeRAOutboundManager(key);
         cfMetaInfo.pool = null;
      }

      if (cfMetaInfo.mcf != null) {
         this.raInstanceMgr.getAdapterLayer().invokePreDestroy(cfMetaInfo.mcf, "managed connection factory");
         cfMetaInfo.mcf = null;
      }

      if (cfMetaInfo.failPoolRunMBean != null) {
         this.unregisterFailedConnectionPoolRuntimeMBean(cfMetaInfo.failPoolRunMBean, this.getRA().getConnectorComponentRuntimeMBean());
         cfMetaInfo.failPoolRunMBean = null;
      }

      this.jndiPoolLookupMap.remove(key);
   }

   private void shutdownPool(UniversalResourceKey key, ConnectionPool connectionPool, boolean shutdown) throws javax.resource.ResourceException {
      try {
         connectionPool.forceSuspend(shutdown);
         connectionPool.shutdown();
         Debug.println("shutdownPool# done shutdown Connection pool : key = " + key + " : " + connectionPool);
      } catch (ResourceException var5) {
         throw new javax.resource.ResourceException(var5.getMessage(), var5);
      }
   }

   public void createConnectionFactoryInternal(OutboundInfo outboundInfo) throws RAOutboundException {
      String jndiName = outboundInfo.getJndiName();

      String jndi;
      try {
         if (JNDIHandler.verifyJNDIName(jndiName)) {
            Debug.logJNDINameAlreadyExists(jndiName);
            jndi = Debug.getExceptionCFJndiNameDuplicate(jndiName);
            throw new RAOutboundException(jndi);
         }
      } catch (RAOutboundException var6) {
         throw var6;
      } catch (RAException var7) {
         String exMsg = Debug.getExceptionJndiVerifyFailed(outboundInfo.getJndiName(), "");
         throw new RAOutboundException(exMsg, var7);
      }

      Debug.println((Object)this, (String)".createConnectionFactory() Initialize MCF");
      ManagedConnectionFactory mcf = this.initializeMCF(outboundInfo);
      jndi = outboundInfo.getJndiName();
      UniversalResourceKey key = new UniversalResourceKey(jndi, this.raInstanceMgr.getVersionId());
      Debug.println((Object)this, (String)(".createConnectionFactory() Add to the mcfMap : " + key));
      this.mcfMap.put(key, mcf);
      Debug.println((Object)this, (String)(".createConnectionFactory() Add to the outboundInfoMap : " + key));
      this.outboundInfoMap.put(key, outboundInfo);
   }

   public OutboundInfo updateOutBoundInfo(String jndi, OutboundInfo outboundInfo) {
      UniversalResourceKey key = new UniversalResourceKey(jndi, this.raInstanceMgr.getVersionId());
      return (OutboundInfo)this.pendingOutboundInfoMap.put(key, outboundInfo);
   }

   private ManagedConnectionFactory initializeMCF(OutboundInfo aOutboundInfo) throws RAOutboundException {
      Debug.enter(this, "initializeMCF(...)");
      String mcfClassName = aOutboundInfo.getMCFClass();
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      ManagedConnectionFactory var7;
      try {
         String exMsg;
         try {
            Debug.println("Instantiate the MCF class : " + mcfClassName);
            ManagedConnectionFactory mcf = (ManagedConnectionFactory)this.raInstanceMgr.getAdapterLayer().createInstance(mcfClassName, true, this.raInstanceMgr.getClassloader(), kernelId);
            if (this.raInstanceMgr.getResourceAdapter() != null && mcf instanceof ResourceAdapterAssociation) {
               try {
                  Debug.println("Set the resource adapter bean in the mcf");
                  this.raInstanceMgr.getAdapterLayer().setResourceAdapter((ResourceAdapterAssociation)mcf, this.raInstanceMgr.getResourceAdapter(), kernelId);
               } catch (Throwable var12) {
                  exMsg = Debug.getExceptionSetRAClassFailed(mcfClassName, "");
                  throw new RAOutboundException(exMsg, var12);
               }
            }

            Debug.println("Configure the MCF class");
            PropSetterTable propSetterTable = null;
            RAValidationInfo raValidationInfo = this.raInstanceMgr.getRAValidationInfo();
            if (raValidationInfo != null) {
               propSetterTable = raValidationInfo.getConnectionFactoryPropSetterTable(aOutboundInfo.getCFInterface());
            }

            Utils.setProperties(this.raInstanceMgr, mcf, aOutboundInfo.getMCFProps().values(), propSetterTable);
            this.raInstanceMgr.getAdapterLayer().invokePostConstruct(mcf);
            this.getRA().getBeanValidator().validate(mcf, "Outbound Connection Pool '" + aOutboundInfo.getJndiName() + "'");
            var7 = mcf;
         } catch (Throwable var13) {
            exMsg = Debug.getExceptionMCFUnexpectedException(aOutboundInfo.getMCFClass(), "");
            throw new RAOutboundException(exMsg, var13);
         }
      } finally {
         Debug.exit(this, "initializeMCF(...)");
      }

      return var7;
   }

   private void changeStateOfPools(byte stateChangeOp) throws RAOutboundException {
      Debug.enter(this, "changeStateOfPools( " + stateChangeOp + " )");

      try {
         List combinedList = new ArrayList(this.poolMap.values());
         this.appDefinedConnectionFactoryManager.getAllConnectionPool(combinedList);
         Iterator connectionPoolList = combinedList.iterator();
         RAOutboundException raOutboundException = null;

         while(connectionPoolList.hasNext()) {
            ConnectionPool connectionPool = (ConnectionPool)connectionPoolList.next();

            try {
               switch (stateChangeOp) {
                  case 1:
                     connectionPool.suspend();
                     break;
                  case 2:
                     connectionPool.resume();
                     break;
                  case 3:
                     connectionPool.shutdown();
                     break;
                  case 4:
                     connectionPool.forceSuspend(false);
               }
            } catch (ResourceException var10) {
               raOutboundException = (RAOutboundException)Utils.consolidateException(new RAOutboundException(), var10);
            }
         }

         if (raOutboundException != null) {
            throw raOutboundException;
         }
      } finally {
         Debug.exit(this, "changeStateOfPools( " + stateChangeOp + " )");
      }

   }

   private void resetNormalPoolInfos() {
      this.shutdownManagedConnectionFactories();
      this.mcfMap.clear();
      this.outboundInfoMap.clear();
      this.poolMap.clear();
      this.jndiPoolLookupMap.clear();
      this.pendingOutboundInfoMap.clear();
   }

   private void shutdownManagedConnectionFactories() {
      Enumeration mcfs = this.mcfMap.elements();

      while(mcfs.hasMoreElements()) {
         ManagedConnectionFactory mcf = (ManagedConnectionFactory)mcfs.nextElement();
         this.raInstanceMgr.getAdapterLayer().invokePreDestroy(mcf, "managed connection factory");
      }

   }

   public void setRA(RAInstanceManager aRA) {
      this.raInstanceMgr = aRA;
      this.initModuleName();
   }

   public Object getConnectionFactory(UniversalResourceKey key) throws NoPermissionException, javax.resource.ResourceException {
      Debug.enter(this, "getConnectionFactory(...)");

      Object var12;
      try {
         Object connectionFactory;
         try {
            ConnectionPool connPool = (ConnectionPool)this.jndiPoolLookupMap.get(key);
            String currentAppId;
            if (connPool == null) {
               Debug.println("Failed to get the pool for key : " + key + " : " + connPool);
               currentAppId = Debug.getExceptionGetConnectionFactoryFailedInternalError(key.toString());
               throw new ApplicationServerInternalException(currentAppId);
            }

            Debug.println("Got the pool for key : " + key + " : " + connPool);
            Debug.println("Check if access is allowed");
            currentAppId = ApplicationAccess.getApplicationAccess().getCurrentApplicationName();
            if (!this.raInstanceMgr.isGlobalAccessible(currentAppId)) {
               String appId = this.raInstanceMgr.getApplicationId();
               if (Debug.isConnectionsEnabled()) {
                  Debug.connections("For pool '" + connPool.getName() + "' a connection request was made from outside the application and is being rejected.");
                  Debug.connections("Requesting app = " + appId + " and current app = " + currentAppId);
               }

               String exMsg = Debug.getExceptionFailedAccessOutsideApp();
               throw new NoPermissionException(exMsg);
            }

            Debug.println("Get the ConnectionFactory from the connection pool");
            connectionFactory = connPool.getConnectionFactory();
         } catch (javax.resource.ResourceException var10) {
            Debug.logCreateCFforMCFError(key.toString(), var10);
            throw var10;
         }

         var12 = connectionFactory;
      } finally {
         Debug.exit(this, "getConnectionFactory(...)");
      }

      return var12;
   }

   public ConnectionPool getConnectionPool(String jndi) {
      ConnectionPool connPool = null;
      if (jndi != null) {
         UniversalResourceKey key = new UniversalResourceKey(jndi, this.raInstanceMgr.getVersionId());
         connPool = (ConnectionPool)this.poolMap.get(key);
      }

      return connPool;
   }

   public ConnectionPool getConnectionPool(String jndi, String defApp, String defModule, String defComp) {
      ConnectionPool connPool = null;
      if (jndi != null) {
         UniversalResourceKey key = new UniversalResourceKey(defApp, defModule, defComp, jndi, this.raInstanceMgr.getVersionId());
         connPool = (ConnectionPool)this.jndiPoolLookupMap.get(key);
      }

      return connPool;
   }

   public RAInstanceManager getRA() {
      return this.raInstanceMgr;
   }

   public int getAvailableConnetionPoolsCount() {
      return this.raInstanceMgr.getRAInfo().getOutboundInfos().size();
   }

   public ConnectorConnectionPoolRuntimeMBean getConnectionPoolRuntime(String jndi) {
      UniversalResourceKey key = new UniversalResourceKey(jndi, this.raInstanceMgr.getVersionId());
      ConnectorConnectionPoolRuntimeMBean connPoolRuntime = null;
      ConnectionPool connPool = (ConnectionPool)this.poolMap.get(key);
      if (connPool != null) {
         connPoolRuntime = connPool.getRuntimeMBean();
      }

      return connPoolRuntime;
   }

   public OutboundAdapterType[] getXMLBeans(ConnectorDiagnosticImageSource src) {
      boolean timedout = src != null ? src.timedout() : false;
      if (timedout) {
         return new OutboundAdapterType[0];
      } else {
         ConnectorComponentRuntimeMBeanImpl connRuntimeMbean = (ConnectorComponentRuntimeMBeanImpl)this.getRA().getConnectorComponentRuntimeMBean();
         ConnectorConnectionPoolRuntimeMBean[] poolRTs = connRuntimeMbean.getConnectionPools();
         OutboundAdapterType[] outboundXBeans = new OutboundAdapterType[poolRTs.length];

         for(int i = 0; i < poolRTs.length; ++i) {
            outboundXBeans[i] = ((ConnectionPoolRuntimeMBeanImpl)poolRTs[i]).getXMLBean(src);
         }

         return outboundXBeans;
      }
   }

   private void initModuleName() {
      if (this.raInstanceMgr != null) {
         this.moduleName = this.raInstanceMgr.getModuleName();
      }

   }

   public String getModuleName() {
      return this.moduleName;
   }

   private void debugModule(String msg) {
      if (Debug.isDeploymentEnabled()) {
         Debug.deployment("Module '" + this.getModuleName() + "' " + msg);
      }

   }

   public void preparePoolInternal(UniversalResourceKey key) throws RAOutboundException {
      ManagedConnectionFactory mcf = (ManagedConnectionFactory)this.mcfMap.get(key);
      if (mcf == null) {
         String exMsg = Debug.getExceptionOutboundPrepareFailed(key.toString(), "ManagedConnectionFactory was not found");
         throw new RAOutboundException(exMsg);
      } else {
         OutboundInfo outboundInfo = (OutboundInfo)this.outboundInfoMap.get(key);
         if (outboundInfo == null) {
            String exMsg = Debug.getExceptionOutboundPrepareFailed(key.toString(), "Outbound Pool was not found");
            throw new RAOutboundException(exMsg);
         } else {
            String jndiName;
            try {
               this.debugModule("Preparing the pool with KEY id:  '" + key + "'");
               ConnectionPool connectionPool = this.createConnectionPool(key, mcf, outboundInfo, this.raInstanceMgr.getApplicationName(), this.raInstanceMgr.getComponentName());
               connectionPool.setupRuntime((ConnectorComponentRuntimeMBeanImpl)this.raInstanceMgr.getConnectorComponentRuntimeMBean(), this);
               Debug.println("Add to the poolMap : key = " + key + " : " + connectionPool);
               this.poolMap.put(key, connectionPool);
               jndiName = key.getJndi();
               if (JNDIHandler.isJndiNameBound(jndiName)) {
                  String exMsg = Debug.getExceptionJndiNameAlreadyBound(jndiName);
                  throw new RAOutboundException(exMsg);
               } else {
                  Debug.println((Object)this, (String)("Add to the jndiPoolLookupMap : " + jndiName + " : " + connectionPool));
                  this.jndiPoolLookupMap.put(key, connectionPool);
               }
            } catch (DeploymentException var7) {
               jndiName = Debug.getExceptionOutboundPrepareFailed(key.toString(), "");
               throw new RAOutboundException(jndiName, var7);
            }
         }
      }
   }

   public void activatePoolInternal(UniversalResourceKey key) throws RAOutboundException {
      OutboundInfo outboundInfo = (OutboundInfo)this.outboundInfoMap.get(key);
      ConnectionPool connectionPool = (ConnectionPool)this.poolMap.get(key);
      this.doActivatePool(key, outboundInfo, connectionPool);

      String exMsg;
      try {
         this.debugModule("Binding the pool into JNDI with JNDI name '" + key + "'");
         JNDIHandler.bindConnectionFactory(outboundInfo, this, connectionPool.getConnectionFactory());
      } catch (javax.resource.ResourceException var6) {
         exMsg = Debug.getFailedToGetCF(key.toString(), "");
         this.debugModule(exMsg + ":" + var6);
         throw new RAOutboundException(exMsg, var6);
      } catch (DeploymentException var7) {
         this.debugModule("Failed to bind the pool into JNDI, key = '" + key + "':" + var7);
         exMsg = Debug.getExceptionJndiBindFailed(key.toString(), "");
         throw new RAOutboundException(exMsg, var7);
      }
   }

   private void doActivatePool(UniversalResourceKey key, OutboundInfo outboundInfo, ConnectionPool connectionPool) throws RAOutboundException {
      try {
         Debug.println("Set the logger");
         connectionPool.setLogger();
         Debug.pooling("Updating the initial capacity of connection pool for module '" + this.moduleName + "' with key:  '" + key + "'");
         RAOutboundDeployer.updateInitialCapacity(connectionPool, outboundInfo);
         if (!connectionPool.isWLSMessagingBridgeConnection()) {
            connectionPool.setupForXARecovery();
         }

         this.debugModule("Resuming the pool with name '" + connectionPool.getName() + "'");
         connectionPool.resume();
      } catch (ResourceException var9) {
         this.debugModule("Failed to activate the pool with key = '" + key + "':" + var9);
         String exMsg = Debug.getExceptionResumePoolFailed("");
         throw new RAOutboundException(exMsg, var9);
      } finally {
         this.debugModule("Done resuming the pool with key = '" + key + "'");
      }

   }

   public boolean resetPool(String jndi) throws RAOutboundException {
      return this.internalResetPool(jndi, false);
   }

   public void forceResetPool(String jndi) throws RAOutboundException {
      this.internalResetPool(jndi, true);
   }

   private boolean internalResetPool(String jndi, boolean force) throws RAOutboundException {
      UniversalResourceKey key = new UniversalResourceKey(jndi, this.raInstanceMgr.getVersionId());
      OutboundInfo outboundInfo = (OutboundInfo)this.pendingOutboundInfoMap.get(key);
      if (outboundInfo == null) {
         outboundInfo = (OutboundInfo)this.outboundInfoMap.get(key);
         if (outboundInfo == null) {
            outboundInfo = (OutboundInfo)this.failedOutboundInfoMap.get(jndi);
         }
      }

      if (outboundInfo == null) {
         throw new RAOutboundException("Pool does not exist: " + key);
      } else {
         ConnectionPool connectionPool = (ConnectionPool)this.poolMap.get(key);
         if (hasValiadtionOrRuntimeError(outboundInfo)) {
            if (connectionPool != null) {
               throw new RAOutboundException("internal error: find a ConnectionPool instance which should not exist for failed pool " + key);
            }

            this.cleanPoolRunTimeCreationError(outboundInfo);
            if (hasValiadtionOrRuntimeError(outboundInfo)) {
               Debug.println("ResetConnectionPool# Connection pool : key = " + key + " : do nothing since this pool has validation error");
               String msg = ConnectorLogger.logCannotResetConnectionPoolFailedDuringValidationLoggable(jndi).getMessageText();
               throw new RAOutboundException(msg);
            }

            this.shutdownAndCleanupFailedPool(outboundInfo.getJndiName());
         } else {
            if (connectionPool == null) {
               throw new RAOutboundException("internal error: cannot find ConnectionPool instance for pool " + key);
            }

            try {
               boolean destroyed = this.internalDestroyPool(key, force, connectionPool, outboundInfo);
               if (!destroyed) {
                  return false;
               }
            } catch (Throwable var9) {
               this.rethrowRAOutboundException(var9);
            }
         }

         try {
            this.createConnectionFactoryInternal(outboundInfo);
            this.pendingOutboundInfoMap.remove(key);
            this.preparePoolInternal(key);
            this.activatePoolInternal(key);
            if (Debug.getVerbose()) {
               connectionPool = (ConnectionPool)this.poolMap.get(key);
               Debug.println("ResetConnectionPool# activate Connection pool : key = " + key + " : " + connectionPool);
            }

            if (Debug.isPoolingEnabled()) {
               Debug.pooling(ConnectorLogger.logConnectionPoolReset(jndi));
            }

            return true;
         } catch (Throwable var8) {
            this.markPoolAsFailed(outboundInfo);
            this.recordPoolCreationError(outboundInfo, var8);
            this.shutdownAndCleanupNormalPool(outboundInfo);
            this.setupFailedPool(outboundInfo);
            this.rethrowRAOutboundException(var8);
            return false;
         }
      }
   }

   private boolean internalDestroyPool(UniversalResourceKey key, boolean force, ConnectionPool connectionPool, OutboundInfo outboundInfo) throws Throwable {
      if (force) {
         try {
            connectionPool.forceSuspend(true);
            Debug.println("internalDestroyPool# forceSuspend Connection pool : key = " + key + " : " + connectionPool);
         } catch (Throwable var8) {
            ConnectorLogger.logIgnoredErrorOnPool(key.toString(), var8.getMessage(), "internalDestroyPool-connectionPool.forceSuspend()", var8);
         }
      } else {
         synchronized(connectionPool) {
            if (connectionPool.getNumReserved() > 0) {
               if (Debug.isPoolingEnabled()) {
                  Debug.pooling(ConnectorLogger.logCannotResetConnectionPoolInuse(key.toString()));
               }

               return false;
            }

            connectionPool.suspend(true);
            Debug.println("internalDestroyPool# suspend Connection pool : key = " + key + " : " + connectionPool);
         }
      }

      try {
         connectionPool.shutdown();
         Debug.println("internalDestroyPool# done shutdown Connection pool : key = " + key + " : " + connectionPool);
      } catch (Throwable var10) {
         if (!force) {
            Debug.println("internalDestroyPool# got error on Connection pool when do shutdown: key = " + key + " : " + var10.getMessage(), var10);
            throw var10;
         }

         ConnectorLogger.logIgnoredErrorOnPool(key.toString(), var10.getMessage(), "internalDestroyPool-connectionPool.shutdown()", var10);
      }

      try {
         JNDIHandler.unbindConnectionFactory(outboundInfo, this, connectionPool.getConnectionFactoryNoCreate());
      } catch (Throwable var9) {
         if (!force) {
            Debug.println("internalDestroyPool# got error on Connection pool when unbindConnectionFactory: key = " + key + " : " + var9.getMessage(), var9);
            throw var9;
         }

         ConnectorLogger.logIgnoredErrorOnPool(key.toString(), var9.getMessage(), "internalDestroyPool-unbindConnectionFactory", var9);
      }

      this.poolMap.remove(key);
      this.jndiPoolLookupMap.remove(key);
      return true;
   }

   public boolean isOutsideAppAccessEnabled() {
      return this.isOutsideAppAccessEnabled;
   }

   protected ConnectionPool createConnectionPool(UniversalResourceKey key, ManagedConnectionFactory managedConnectionFactory, OutboundInfo outboundInfo, String applicationName, String componentName) throws DeploymentException {
      return RAOutboundDeployer.prepare(key, managedConnectionFactory, outboundInfo, applicationName, componentName, this);
   }

   protected ConnectionPool createConnectionPool(ManagedConnectionFactory managedConnectionFactory, OutboundInfo outboundInfo, String applicationName, String componentName) throws DeploymentException {
      return RAOutboundDeployer.prepare((UniversalResourceKey)null, managedConnectionFactory, outboundInfo, applicationName, componentName, this);
   }

   public static boolean hasValiadtionOrRuntimeError(OutboundInfo outboundInfo) {
      boolean noError = getAllErrorsOfPool(outboundInfo).isEmpty();
      return !noError;
   }

   public boolean isFailedPool(String key) {
      return this.failedOutboundInfoMap.containsKey(key);
   }

   public static List getAllErrorsOfPool(OutboundInfo outboundInfo) {
      ValidationMessage validationMessage = outboundInfo.getRAInfo().getValidationMessage();
      String key = outboundInfo.getJndiName();
      ValidationMessage.SubComponentAndKey msgKey = new ValidationMessage.SubComponentAndKey("Connection Pool", key);
      List errors = validationMessage.getErrorsOfMessageKey(msgKey);
      return errors;
   }

   public void recordPoolCreationError(OutboundInfo outboundInfo, Throwable e) {
      String key = outboundInfo.getJndiName();
      ValidationMessage validationMessage = outboundInfo.getRAInfo().getValidationMessage();
      String error;
      if (e instanceof RAException) {
         error = ((RAException)e).getMessageNoStacktrace();
      } else {
         error = e.getMessage();
      }

      ConnectorLogger.logPoolCreationError(key, error, e);
      validationMessage.error("Connection Pool in Runtime", key, error, 9999);
   }

   public void markPoolAsFailed(OutboundInfo outboundInfo) {
      String jndi = outboundInfo.getJndiName();
      this.failedOutboundInfoMap.put(jndi, outboundInfo);
   }

   public void reThrowPoolCreationErrorIfNeeded(Throwable e) throws RAOutboundException {
      if (this.getRA().isDeployAsAWhole()) {
         this.rethrowRAOutboundException(e);
      }

   }

   public void rethrowRAOutboundException(Throwable e) throws RAOutboundException {
      if (e instanceof RAOutboundException) {
         throw (RAOutboundException)e;
      } else {
         throw new RAOutboundException(e);
      }
   }

   public void cleanPoolRunTimeCreationError(OutboundInfo outboundInfo) {
      if (Debug.isPoolingEnabled()) {
         Debug.pooling("clean runtime creation error for failed pool " + outboundInfo.getJndiName());
      }

      ValidationMessage validationMessage = outboundInfo.getRAInfo().getValidationMessage();
      ValidationMessage.SubComponentAndKey msgKey = new ValidationMessage.SubComponentAndKey("Connection Pool in Runtime", outboundInfo.getJndiName());
      validationMessage.clearErrorsOfMessageKey(msgKey);
   }

   public void shutdownAndCleanupNormalPool(OutboundInfo outboundInfo) {
      UniversalResourceKey key = new UniversalResourceKey(outboundInfo.getJndiName(), this.getRA().getVersionId());
      if (Debug.isDeploymentEnabled()) {
         Debug.deployment("shutdown and cleanup all info about pool " + key);
      }

      this.outboundInfoMap.remove(key);
      ManagedConnectionFactory mcf = (ManagedConnectionFactory)this.mcfMap.get(key);
      if (mcf != null) {
         this.raInstanceMgr.getAdapterLayer().invokePreDestroy(mcf, "managed connection factory");
         this.mcfMap.remove(key);
      }

      ConnectionPool pool = (ConnectionPool)this.poolMap.get(key);
      if (pool != null) {
         try {
            this.internalDestroyPool(key, true, pool, outboundInfo);
         } catch (Throwable var6) {
            ConnectorLogger.logInternalDestroyFailedPoolError(key.toString(), var6.getMessage(), var6);
         }

         this.poolMap.remove(key);
      }

      this.pendingOutboundInfoMap.remove(key);
   }

   public void setupFailedPool(OutboundInfo outboundInfo) {
      ConnectorLogger.logSetupFailedPool(outboundInfo.getJndiName(), getAllErrorsOfPool(outboundInfo).toString());

      try {
         if (Debug.isPoolingEnabled()) {
            Debug.pooling("creating runtime mbean for failed pools " + outboundInfo.getJndiName());
         }

         FailedConnectionPoolRuntimeMBeanImpl rMBean = this.setupFailedPoolRuntime(outboundInfo);
         this.failedPoolRTs.put(outboundInfo.getJndiName(), rMBean);
      } catch (Throwable var3) {
         Debug.logInitCPRTMBeanError(outboundInfo.getJndiName(), StackTraceUtils.throwable2StackTrace(var3));
      }

   }

   protected FailedConnectionPoolRuntimeMBeanImpl setupFailedPoolRuntime(OutboundInfo outboundInfo) throws ManagementException {
      ConnectorComponentRuntimeMBeanImpl connRuntimeMbean = (ConnectorComponentRuntimeMBeanImpl)this.getRA().getConnectorComponentRuntimeMBean();
      FailedConnectionPoolRuntimeMBeanImpl rMBean = this.createFailedPoolRuntime((UniversalResourceKey)null, outboundInfo, connRuntimeMbean);
      connRuntimeMbean.addConnPoolRuntime(rMBean);
      return rMBean;
   }

   protected FailedConnectionPoolRuntimeMBeanImpl createFailedPoolRuntime(UniversalResourceKey key, OutboundInfo outboundInfo, ConnectorComponentRuntimeMBeanImpl connRuntimeMbean) throws ManagementException {
      if (key == null) {
         key = new UniversalResourceKey(outboundInfo.getJndiName(), (String)null);
      }

      return new FailedConnectionPoolRuntimeMBeanImpl(key, this.getRA().getApplicationName(), this.getRA().getComponentName(), outboundInfo, connRuntimeMbean, this);
   }

   private void shutdownAndCleanupAllFailedPools() {
      if (Debug.isPoolingEnabled()) {
         Debug.pooling("clean up all data about failed pools");
      }

      ArrayList infos = new ArrayList();
      infos.addAll(this.failedOutboundInfoMap.values());
      Iterator var2 = infos.iterator();

      while(var2.hasNext()) {
         OutboundInfo outboundInfo = (OutboundInfo)var2.next();
         this.shutdownAndCleanupFailedPool(outboundInfo.getJndiName());
         this.cleanPoolRunTimeCreationError(outboundInfo);
      }

   }

   public void shutdownAndCleanupFailedPool(String key) {
      if (Debug.isPoolingEnabled()) {
         Debug.pooling("clean up all data about the failed pool " + key);
      }

      FailedConnectionPoolRuntimeMBeanImpl rtMBean = (FailedConnectionPoolRuntimeMBeanImpl)this.failedPoolRTs.remove(key);
      if (rtMBean != null) {
         this.unregisterFailedConnectionPoolRuntimeMBean(rtMBean, this.getRA().getConnectorComponentRuntimeMBean());
      }

      this.failedOutboundInfoMap.remove(key);
   }

   protected void unregisterFailedConnectionPoolRuntimeMBean(FailedConnectionPoolRuntimeMBeanImpl rMBean, ConnectorComponentRuntimeMBean connRuntimeMbean) {
      Utils.unregisterRuntimeMBean(rMBean);
      ((ConnectorComponentRuntimeMBeanImpl)connRuntimeMbean).removeConnPoolRuntime(rMBean);
   }

   public OutboundInfo getOutboundInfo(String jndi) {
      Iterator var2 = this.outboundInfoMap.keySet().iterator();

      UniversalResourceKey key;
      do {
         if (!var2.hasNext()) {
            return (OutboundInfo)this.failedOutboundInfoMap.get(jndi);
         }

         key = (UniversalResourceKey)var2.next();
      } while(!key.getJndi().equals(jndi));

      return (OutboundInfo)this.outboundInfoMap.get(key);
   }

   public Collection getAllConnectionPool() {
      List result = new ArrayList(this.poolMap.values());
      this.appDefinedConnectionFactoryManager.getAllConnectionPool(result);
      return result;
   }
}
