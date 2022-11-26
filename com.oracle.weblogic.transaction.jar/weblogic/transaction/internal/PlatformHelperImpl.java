package weblogic.transaction.internal;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.TransactionRolledbackException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.common.internal.PeerInfo;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.kernel.KernelStatus;
import weblogic.management.DeploymentException;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicObjectName;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.TransactionLogJDBCStoreMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceClient;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.cluster.ThreadPreferredHost;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.RuntimeHandler;
import weblogic.store.StoreWritePolicy;
import weblogic.store.admin.FileAdminHandler;
import weblogic.store.admin.JDBCAdminHandler;
import weblogic.store.admin.RuntimeHandlerImpl;
import weblogic.store.internal.PersistentStoreImpl;
import weblogic.store.io.file.FileStoreIO;
import weblogic.store.io.jdbc.BasicDataSource;
import weblogic.store.io.jdbc.JDBCStoreIO;
import weblogic.t3.srvr.T3Srvr;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.ChannelInterface;
import weblogic.transaction.InterposedTransactionManager;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.TxUtils;
import weblogic.work.Work;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class PlatformHelperImpl extends PlatformHelperCommon {
   static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ServerCoordinatorDescriptorManager scdm = new ServerCoordinatorDescriptorManagerImpl();
   private PersistentStore persistentStore;
   private Map m_serverNameToXAResourceMap = new HashMap();
   private Map m_serverNameToXAExceptionMap = new HashMap();
   private static final Object m_clusterStatusLock = new Object();
   JDBCStoreMBean jBean;
   JDBCStoreMBean crashSiteBean;
   PeerSiteRecoveryCheck peerSiteRecoveryCheck = null;
   static CrossSiteJTARecoveryRuntimeListener registerCrossSiteJTARecoveryRuntimeListener;
   PeerSiteRecoveryLeaseMaintenance peerSiteRecoveryLeaseMaintenance = null;
   PeerSiteRecoveryTakeoverLeaseMaintenance peerSiteRecoveryTakeoverLeaseMaintenance = null;
   private String transactionServiceHalt = "false";
   private static final int JNDI_CONNECT_TIMEOUT = Integer.parseInt(System.getProperty("weblogic.jndi.connectTimeout", "60000"));
   private static final int JNDI_RESPONSE_READ_TIMEOUT = Integer.parseInt(System.getProperty("weblogic.jndi.responseReadTimeout", "60000"));
   JDBCTLogServerMBeanIF m_JDBCTLogServerMBeanIF;
   private WorkManager parallelXAWorkManager;
   private static volatile boolean enableResourceChecks = Boolean.getBoolean("weblogic.transaction.enableResourceChecks");

   public CoordinatorDescriptor findAdminChannel(Object server, Object channel) {
      ProtocolService protocolService = (new ProtocolServiceImpl()).getProtocolService();
      Channel sc = (Channel)protocolService.findServerChannel(server, ProtocolService.PROTOCOL_ADMIN);
      if (sc != null) {
         channel = sc;
      }

      CoordinatorDescriptor cd = new CoordinatorDescriptor(((Channel)channel).getPublicAddress() + ":" + ((Channel)channel).getPublicPort(), ((ServerIdentity)server).getDomainName(), ((ServerIdentity)server).getServerName(), ((Channel)channel).getProtocolPrefix().toLowerCase(Locale.ENGLISH));
      return cd;
   }

   public ChannelInterface findAdminChannel(Object server) {
      ProtocolService protocolService = (new ProtocolServiceImpl()).getProtocolService();
      Channel serverChannel = (Channel)protocolService.findServerChannel(server, ProtocolService.PROTOCOL_ADMIN);
      return serverChannel == null ? null : new weblogic.transaction.internal.Channel(serverChannel);
   }

   public boolean isJNDIEnabled() {
      return true;
   }

   public String getRootName() {
      return "weblogic.transaction";
   }

   public Context getInitialContext(String serverURL) throws NamingException {
      return this.getInitialContext(serverURL, true);
   }

   public Context getInitialContext(String serverURL, boolean replicateBindings) throws NamingException {
      ServerEnvironment env = (ServerEnvironment)GlobalServiceLocator.getServiceLocator().getService(ServerEnvironment.class, new Annotation[0]);
      boolean resetSubjectOnThread = false;
      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelID);

      Context var12;
      try {
         if (serverURL != null) {
            env.setProviderUrl(serverURL);
            int plusPos1 = serverURL.indexOf(58);
            if (plusPos1 != -1) {
               String protocolName = serverURL.substring(0, serverURL.indexOf(58));
               Protocol p = ProtocolManager.getProtocolByName(protocolName);
               if (!p.isSatisfactoryQOS(subject.getQOS())) {
                  resetSubjectOnThread = true;
                  if (p.toByte() != 6 && !protocolName.endsWith("s") && !protocolName.endsWith("S")) {
                     SecurityServiceManager.pushSubject(kernelID, SubjectUtils.getAnonymousSubject());
                  } else {
                     SecurityServiceManager.pushSubject(kernelID, kernelID);
                  }
               }
            }
         }

         env.setCreateIntermediateContexts(true);
         env.setReplicateBindings(replicateBindings);
         env.setInitialContextFactory("weblogic.jndi.WLInitialContextFactory");
         env.setProperty("weblogic.jndi.createUnderSharable", "true");
         env.setConnectionTimeout((long)JNDI_CONNECT_TIMEOUT);
         var12 = env.getInitialContext();
      } finally {
         if (resetSubjectOnThread) {
            SecurityServiceManager.pushSubject(kernelID, subject);
         }

      }

      return var12;
   }

   public boolean extendCoordinatorURL(String aCoURL) {
      String protocol = getProtocol(aCoURL);
      if (protocol == null) {
         protocol = "t3";
      }

      ServerTransactionManagerImpl var10000 = (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
      int mode = ServerTransactionManagerImpl.getInteropMode();
      return !ignoreProtocol(protocol, aCoURL) && mode == 0 && this.isLocalAdminChannelEnabled();
   }

   private static final boolean ignoreProtocol(String protocol, String aCoURL) {
      String extAdminProtocol = getExtendedProtocol(aCoURL);
      return protocol.equalsIgnoreCase("iiop") || protocol.equalsIgnoreCase("tgiop") || extAdminProtocol != null;
   }

   private static final SecurityConfigurationMBean getSecurityConfigurationMBean() {
      return ManagementService.getRuntimeAccess(kernelID).getDomain().getSecurityConfiguration();
   }

   public boolean isCDSEnabled() {
      return getSecurityConfigurationMBean().isCrossDomainSecurityEnabled();
   }

   public boolean isDomainExcluded(String domainName) {
      if (domainName == null) {
         return false;
      } else {
         String[] excludedDomainList = getSecurityConfigurationMBean().getExcludedDomainNames();
         if (excludedDomainList == null) {
            return false;
         } else {
            for(int i = 0; i < excludedDomainList.length; ++i) {
               if (domainName.equals(excludedDomainList[i])) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private static final String getServerName(String aCoUrl) {
      int plusPos = aCoUrl.indexOf(43);
      return plusPos == -1 ? null : aCoUrl.substring(0, plusPos);
   }

   public String getDomainName(String aCoUrl) {
      int plusPos1 = aCoUrl.indexOf(43);
      if (plusPos1 == -1) {
         return null;
      } else {
         int plusPos2 = aCoUrl.indexOf(43, plusPos1 + 1);
         if (plusPos2 == -1) {
            return null;
         } else {
            int plusPos3 = aCoUrl.indexOf(43, plusPos2 + 1);
            return plusPos3 == -1 ? null : aCoUrl.substring(plusPos2 + 1, plusPos3);
         }
      }
   }

   private static final String getProtocol(String aCoUrl) {
      int plusPos1 = aCoUrl.indexOf(43);
      if (plusPos1 == -1) {
         return null;
      } else {
         int plusPos2 = aCoUrl.indexOf(43, plusPos1 + 1);
         if (plusPos2 == -1) {
            return null;
         } else {
            int plusPos3 = aCoUrl.indexOf(43, plusPos2 + 1);
            if (plusPos3 == -1) {
               return null;
            } else {
               int plusPos4 = aCoUrl.indexOf(43, plusPos3 + 1);
               return plusPos4 == -1 ? null : aCoUrl.substring(plusPos3 + 1, plusPos4);
            }
         }
      }
   }

   private static final String getExtendedProtocol(String aCoUrl) {
      int plusPos1 = aCoUrl.indexOf(43);
      if (plusPos1 == -1) {
         return null;
      } else {
         int plusPos2 = aCoUrl.indexOf(43, plusPos1 + 1);
         if (plusPos2 == -1) {
            return null;
         } else {
            int plusPos3 = aCoUrl.indexOf(43, plusPos2 + 1);
            if (plusPos3 == -1) {
               return null;
            } else {
               int plusPos4 = aCoUrl.indexOf(43, plusPos3 + 1);
               if (plusPos4 == -1) {
                  return null;
               } else {
                  int plusPos5 = aCoUrl.indexOf(43, plusPos4 + 1);
                  return plusPos5 == -1 ? null : aCoUrl.substring(plusPos4 + 1, plusPos5);
               }
            }
         }
      }
   }

   private static final String getHostPort(String aCoUrl) {
      int plusPos1 = aCoUrl.indexOf(43);
      int plusPos2 = aCoUrl.indexOf(43, plusPos1 + 1);
      return plusPos2 > plusPos1 ? aCoUrl.substring(plusPos1 + 1, plusPos2) : aCoUrl.substring(plusPos1 + 1);
   }

   private static final String getHost(String aCoUrl) {
      String hostPort = getHostPort(aCoUrl);
      int colonPos = hostPort.indexOf(58);
      return colonPos == -1 ? null : hostPort.substring(0, colonPos);
   }

   private static final String getAdminProtocol(String aCoUrl) {
      int colonPos = aCoUrl.indexOf(58);
      return colonPos == -1 ? null : aCoUrl.substring(0, colonPos);
   }

   public String getAdminPort(String aCoUrl) {
      int colonPos1 = aCoUrl.indexOf(58);
      if (colonPos1 == -1) {
         return null;
      } else {
         int colonPos2 = aCoUrl.indexOf(58, colonPos1 + 1);
         return aCoUrl.substring(colonPos2 + 1);
      }
   }

   public boolean isSSLURL(String aCoURL) {
      return this.isSSLURL(aCoURL, false);
   }

   public boolean isSSLURL(String aCoURL, boolean secureActionURLFormat) {
      String protocol = null;
      if (secureActionURLFormat) {
         protocol = getAdminProtocol(aCoURL);
      } else {
         protocol = getProtocol(aCoURL);
      }

      return protocol != null && (protocol.equalsIgnoreCase("t3s") || protocol.equalsIgnoreCase("https"));
   }

   public void setPrimaryStore(Object store) {
      this.persistentStore = (PersistentStore)store;
   }

   public Object getPrimaryStore() {
      if (this.persistentStore == null) {
         this.openPrimaryStore(false, false);
      }

      return this.persistentStore;
   }

   public boolean openPrimaryStore(boolean isRetry) {
      return this.openPrimaryStore(isRetry, !isRetry);
   }

   public boolean openPrimaryStore(boolean isRetry, boolean isStartup) {
      if (isRetry) {
         try {
            this.closeStore(this.persistentStore);
         } catch (Exception var10) {
            TXLogger.logFailedSetPrimaryStoreRetry(var10);
         }
      }

      TransactionManagerImpl tm = getTM();
      if (!tm.getJdbcTLogEnabled()) {
         this.persistentStore = PersistentStoreManager.getManager().getDefaultStore();
      } else {
         JDBCTLogServerMBeanIF server = this.getJDBCTLogServerMBeanIF();

         try {
            this.getPrimaryStoreProvidedServerMBean(server.getName(), server, tm.getJdbcTLogMaxRetrySecondsBeforeTXException(), false, true);
         } catch (Exception var9) {
            TxDebug.JTATLOG.debug("PlatformHelperImpl.openPrimaryStoreisRetry = [" + isRetry + "], isStartup = [" + isStartup + "] getTM().getSiteName():" + getTM().getSiteName() + " waiting for  getPrimaryStoreProvidedServerMBean justPing exception:" + var9);
         }

         this.startPeerSiteRecovery();

         for(; getTM().getSiteName() != null && !this.peerSiteRecoveryLeaseMaintenance.isFirstEntryMade(); TxDebug.JTATLOG.debug("PlatformHelperImpl.openPrimaryStoreisRetry = [" + isRetry + "], isStartup = [" + isStartup + "] getTM().getSiteName():" + getTM().getSiteName() + " waiting for  peerSiteRecoveryLeaseMaintenance.isFirstEntryMade():" + this.peerSiteRecoveryLeaseMaintenance.isFirstEntryMade())) {
            try {
               Thread.sleep(2000L);
            } catch (InterruptedException var8) {
            }
         }

         try {
            try {
               this.persistentStore = this.getPrimaryStoreProvidedServerMBean(server.getName(), server, tm.getJdbcTLogMaxRetrySecondsBeforeTXException());
            } catch (Exception var11) {
               Exception exception = var11;
               if (getTM().getSiteName() == null) {
                  TXLogger.logFailedSetPrimaryStore(var11);
                  return false;
               }

               TxDebug.JTATLOG.debug("PlatformHelperImpl.openPrimaryStoreisRetry = [" + isRetry + "], isStartup = [" + isStartup + "] getTM().getSiteName():" + getTM().getSiteName() + " exception during getPrimaryStoreProvidedServerMBean:" + var11);

               while(!this.peerSiteRecoveryLeaseMaintenance.isFirstEntryMade()) {
                  Thread.sleep(2000L);
                  TxDebug.JTATLOG.debug("PlatformHelperImpl.openPrimaryStoreisRetry = [" + isRetry + "], isStartup = [" + isStartup + "] getTM().getSiteName():" + getTM().getSiteName() + " exception during getPrimaryStoreProvidedServerMBean:" + exception + " peerSiteRecoveryLeaseMaintenance.isFirstEntryMade():" + this.peerSiteRecoveryLeaseMaintenance.isFirstEntryMade());
               }

               while(this.persistentStore == null) {
                  try {
                     this.persistentStore = this.getPrimaryStoreProvidedServerMBean(server.getName(), server, tm.getJdbcTLogMaxRetrySecondsBeforeTXException());
                  } catch (Exception var7) {
                     Thread.sleep(3000L);
                  }
               }
            }
         } catch (Exception var12) {
            TXLogger.logFailedSetPrimaryStore(var12);
            return false;
         }
      }

      return this.persistentStore == null ? false : isRetry;
   }

   void setJDBCTLogServerMBeanIF(JDBCTLogServerMBeanIF jdbcTLogServerMBeanIF) {
      this.m_JDBCTLogServerMBeanIF = jdbcTLogServerMBeanIF;
   }

   JDBCTLogServerMBeanIF getJDBCTLogServerMBeanIF() {
      return this.m_JDBCTLogServerMBeanIF != null ? this.m_JDBCTLogServerMBeanIF : new JDBCTLogServerMBeanIF() {
         ServerMBean server;

         {
            this.server = ManagementService.getRuntimeAccess(PlatformHelperImpl.kernelID).getServer();
         }

         public TransactionLogJDBCStoreMBean getTransactionLogJDBCStore() {
            return this.server.getTransactionLogJDBCStore();
         }

         public String getName() {
            return this.server.getName();
         }
      };
   }

   void startPeerSiteRecovery() {
      if (!this.isPeerRecoveryAlreadyRunning()) {
         TxDebug.JTATLOG.debug("PlatformHelperImpl.startPeerSiteRecovery");
         if (this.peerSiteRecoveryCheck == null) {
            this.peerSiteRecoveryCheck = new PeerSiteRecoveryCheck(this, this.jBean);
            WorkManagerFactory.getInstance().getSystem().schedule(this.peerSiteRecoveryCheck);
         }

         if (this.peerSiteRecoveryLeaseMaintenance == null) {
            this.peerSiteRecoveryLeaseMaintenance = new PeerSiteRecoveryLeaseMaintenance(this.peerSiteRecoveryCheck);
            WorkManagerFactory.getInstance().getSystem().schedule(this.peerSiteRecoveryLeaseMaintenance);
         }

         if (this.peerSiteRecoveryTakeoverLeaseMaintenance == null) {
            this.peerSiteRecoveryTakeoverLeaseMaintenance = new PeerSiteRecoveryTakeoverLeaseMaintenance(this);
            WorkManagerFactory.getInstance().getSystem().schedule(this.peerSiteRecoveryTakeoverLeaseMaintenance);
         }

      }
   }

   boolean isPeerRecoveryAlreadyRunning() {
      return this.peerSiteRecoveryCheck != null;
   }

   private PersistentStore getPrimaryStoreProvidedServerMBean(String serverName, JDBCTLogServerMBeanIF jdbcTLogServerMBeanIF, int jdbcTLogMaxRetrySecondsBeforeTXException) throws Exception {
      return this.getPrimaryStoreProvidedServerMBean(serverName, jdbcTLogServerMBeanIF, jdbcTLogMaxRetrySecondsBeforeTXException, false, false);
   }

   PersistentStore getPrimaryStoreProvidedServerMBean(String serverName, JDBCTLogServerMBeanIF jdbcTLogServerMBeanIF, int jdbcTLogMaxRetrySecondsBeforeTXException, boolean isCrashDomain, boolean isJustPing) throws Exception {
      PersistentStore storeXA = this.getPersistentStore(serverName, jdbcTLogServerMBeanIF, isCrashDomain);
      if (isJustPing) {
         return storeXA;
      } else {
         HashMap configHashMap = new HashMap();
         configHashMap.put("MaxRetrySeconds", jdbcTLogMaxRetrySecondsBeforeTXException);
         configHashMap.put("ConnectionCachingPolicy", this.jBean.getConnectionCachingPolicy());
         configHashMap.put("OraclePiggybackCommitEnabled", this.jBean.isOraclePiggybackCommitEnabled());
         if (isCrashDomain) {
            configHashMap.put("IslogErrorForMustBeMe", false);
         }

         this.openStoreWithConfig(storeXA, configHashMap);
         return storeXA;
      }
   }

   void openStoreWithConfig(PersistentStore storeXA, HashMap configHashMap) throws PersistentStoreException {
      storeXA.open(configHashMap);
   }

   PersistentStore getPersistentStore(String serverName, JDBCTLogServerMBeanIF jdbcTLogServerMBeanIF, boolean isCrashDomain) throws PersistentStoreException {
      if (isCrashDomain) {
         this.crashSiteBean = new CrashSiteJDBCStoreMBean(jdbcTLogServerMBeanIF.getTransactionLogJDBCStore(), getTM().getRecoverySiteName() + "_" + serverName + "_");
      } else {
         this.jBean = jdbcTLogServerMBeanIF.getTransactionLogJDBCStore();
      }

      RuntimeHandler runtimeHandler = null;
      if (this.isServer()) {
         runtimeHandler = new RuntimeHandlerImpl();
      }

      String jBeanInfo;
      if (isCrashDomain) {
         jBeanInfo = " jBean info jBean.getName():" + this.crashSiteBean.getName() + "jBean.getDataSource().getJDBCResource().getName() :" + this.crashSiteBean.getDataSource() + "jBean.getDataSource():" + this.crashSiteBean.getDataSource().getJDBCResource().getName() + "jBean.getPrefixName():" + this.crashSiteBean.getPrefixName();
      } else {
         jBeanInfo = " jBean info jBean.getName():" + this.jBean.getName() + "jBean.getDataSource().getJDBCResource().getName() :" + this.jBean.getDataSource() + "jBean.getDataSource():" + this.jBean.getDataSource().getJDBCResource().getName() + "jBean.getPrefixName():" + this.jBean.getPrefixName();
      }

      TxDebug.JTATLOG.debug("getPrimaryStoreProvidedServerMBean about to JDBCAdminHandler.makeStore for name:" + serverName + "JTA_JDBCTLOGStore" + " jBean:" + this.jBean + " crashSiteBean:" + this.crashSiteBean + " isCrashDomain:" + isCrashDomain + " jBeanInfo:" + jBeanInfo);
      String siteName = isCrashDomain ? getTM().getRecoverySiteName() : getTM().getSiteName();
      if (siteName == null) {
         siteName = "";
      }

      return JDBCAdminHandler.makeStore(siteName + serverName + "JTA_JDBCTLOGStore", (String)null, (String)null, isCrashDomain ? this.crashSiteBean : this.jBean, (ClearOrEncryptedService)null, runtimeHandler);
   }

   public Object getStore(String serverName, String coordinatorURL) throws Exception {
      if ("~ping".equals(coordinatorURL)) {
         ServerMBean serverMBean = ManagementService.getRuntimeAccess(kernelID).getDomain().lookupServer(getTM().getServerName());
         JDBCTLogServerMBeanIF jdbcTLogServerMBeanIF = this.getJdbctLogServerMBeanIF(serverMBean);
         this.getPrimaryStoreProvidedServerMBean(serverName, jdbcTLogServerMBeanIF, serverMBean.getTransactionLogJDBCStore().getMaxRetrySecondsBeforeTXException(), true, true).close();
         return null;
      } else {
         boolean isCrashDomain = "~".equals(coordinatorURL);
         ServerMBean serverMBean = isCrashDomain ? ManagementService.getRuntimeAccess(kernelID).getDomain().lookupServer(getTM().getServerName()) : ManagementService.getRuntimeAccess(kernelID).getDomain().lookupServer(serverName);
         boolean isJDBCTlog = serverMBean.getTransactionLogJDBCStore().isEnabled();
         if (!isJDBCTlog && !isCrashDomain) {
            FileAdminHandler fileAdminHandler = new FileAdminHandler();

            try {
               fileAdminHandler.createMigratedDefaultStore(serverMBean, false);
            } catch (DeploymentException var8) {
               throw new Exception(var8);
            }

            PersistentStore store = fileAdminHandler.getStore();
            return store;
         } else {
            JDBCTLogServerMBeanIF jdbcTLogServerMBeanIF = this.getJdbctLogServerMBeanIF(serverMBean);
            return this.getPrimaryStoreProvidedServerMBean(serverName, jdbcTLogServerMBeanIF, serverMBean.getTransactionLogJDBCStore().getMaxRetrySecondsBeforeTXException(), isCrashDomain, false);
         }
      }
   }

   JDBCTLogServerMBeanIF getJdbctLogServerMBeanIF(final ServerMBean serverMBean) {
      return new JDBCTLogServerMBeanIF() {
         public TransactionLogJDBCStoreMBean getTransactionLogJDBCStore() {
            return serverMBean.getTransactionLogJDBCStore();
         }

         public String getName() {
            return serverMBean.getName();
         }
      };
   }

   public void closeStore(Object store) throws Exception {
      PersistentStoreManager manager = PersistentStoreManager.getManager();
      manager.closeFileStore(((PersistentStore)store).getName());

      try {
         ((PersistentStore)store).close();
      } catch (Exception var4) {
      }

   }

   public int getQOSAdmin() {
      return 103;
   }

   public boolean isLocalAdminChannelEnabled() {
      ProtocolService protocolService = (new ProtocolServiceImpl()).getProtocolService();
      return protocolService.isLocalAdminChannelEnabled();
   }

   public String findLocalAdminChannelURL(String aCoURL) {
      String serverName = getServerName(aCoURL);

      try {
         return (new ProtocolServiceImpl()).getProtocolService().findAdministrationURL(serverName);
      } catch (Exception var4) {
         TXLogger.logDowngradeAdminURL(var4);
         return null;
      }
   }

   public String findLocalSSLURL(String aCoURL) {
      String serverName = getServerName(aCoURL);
      String domainName = this.getDomainName(aCoURL);
      String host = getHost(aCoURL);

      try {
         ProtocolService protocolService = (new ProtocolServiceImpl()).getProtocolService();
         String sslURL = protocolService.findURL(serverName, protocolService.getDefaultSecureProtocol());
         if (sslURL == null) {
            TXLogger.logDowngradeSSLURL(new Exception("SSL Port can not be obtained"));
            return null;
         } else {
            String sslPort = this.getAdminPort(sslURL);
            String sslProtocol = getAdminProtocol(sslURL);
            return serverName + "+" + host + ":" + sslPort + "+" + domainName + "+" + sslProtocol + "+";
         }
      } catch (Exception var9) {
         TXLogger.logDowngradeSSLURL(var9);
         return null;
      }
   }

   String findProtocolServiceSSLURL(String aCoURL) {
      String serverName = getServerName(aCoURL);
      this.getDomainName(aCoURL);
      String host = getHost(aCoURL);

      try {
         ProtocolService protocolService = (new ProtocolServiceImpl()).getProtocolService();
         String sslURL = protocolService.findURL(serverName, protocolService.getDefaultSecureProtocol());
         if (sslURL == null) {
            TXLogger.logDowngradeSSLURL(new Exception("SSL Port can not be obtained"));
            return null;
         } else {
            return sslURL;
         }
      } catch (Exception var7) {
         TXLogger.logDowngradeSSLURL(var7);
         return null;
      }
   }

   private String constructSSLURL(String sslPort, String sslProtocol, String aCoURL) {
      String serverName = getServerName(aCoURL);
      String domainName = this.getDomainName(aCoURL);
      String host = getHost(aCoURL);
      return serverName + "+" + host + ":" + sslPort + "+" + domainName + "+" + sslProtocol + "+";
   }

   public void setSSLURLFromClientInfo(ClientTransactionManagerImpl ctmImpl, Context initialContext) {
      String sslPort = null;
      String sslProtocol = null;
      String sslURL = null;

      try {
         if (ServerHelper.getClientAddress() != null) {
            EndPoint endPoint = ServerHelper.getClientEndPoint();
            Channel channel = endPoint.getRemoteChannel();
            sslProtocol = channel.getProtocolPrefix();
            sslURL = this.findProtocolServiceSSLURL(ctmImpl.getCoordinatorURL());
         } else {
            Hashtable env = initialContext.getEnvironment();
            sslURL = (String)env.get("java.naming.provider.url");
            if (sslURL != null) {
               sslProtocol = getAdminProtocol(sslURL);
            }
         }

         if (sslProtocol != null && sslURL != null && (sslProtocol.equalsIgnoreCase("t3s") || sslProtocol.equalsIgnoreCase("https"))) {
            sslPort = this.getAdminPort(sslURL);
            ctmImpl.setCoordinatorURL(this.constructSSLURL(sslPort, sslProtocol, ctmImpl.getCoordinatorURL()));
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   public boolean isCheckpointLLR() {
      return false;
   }

   public void dumpTLOG(String tlogPath, String serverName, boolean delete) throws Exception {
      PersistentStoreManager psm = PersistentStoreManager.getManager();
      String name = "_WLS_" + serverName;
      FileStoreIO ios = new FileStoreIO(name, tlogPath);
      PersistentStore store = new PersistentStoreImpl(serverName, ios, (RuntimeHandler)null);
      HashMap config = new HashMap();
      config.put("SynchronousWritePolicy", StoreWritePolicy.CACHE_FLUSH);
      this.openStoreWithConfig(store, config);
      psm.setDefaultStore(store);
      StoreTransactionLoggerImpl stl = new StoreTransactionLoggerImpl(System.out, !delete);
      if (delete) {
         long deleteCount = stl.delete();
         System.out.println("Deleted " + deleteCount + " tlog entries");
      }

   }

   public void dumpJDBCTLOG(String serverName, String rootPath, String dataSourceName, String tableRef) throws Exception {
      PersistentStoreManager psm = PersistentStoreManager.getManager();
      PersistentStore store = null;
      String dbPassword = null;
      String name = serverName + "JTA_JDBCTLOGStore";
      String configFileName = rootPath + "/config/config.xml";
      DomainMBean domainMB = null;
      System.setProperty("weblogic.RootDirectory", rootPath);

      try {
         domainMB = ManagementServiceClient.getClientAccess().getDomain(configFileName, false);
      } catch (Exception var25) {
         var25.printStackTrace();
      }

      JDBCSystemResourceMBean systemResourceMBean = domainMB.lookupJDBCSystemResource(dataSourceName);
      if (systemResourceMBean == null) {
         throw new Exception("DataSourceName " + dataSourceName + " NotFound");
      } else {
         JDBCDataSourceBean dataSourceMBean = systemResourceMBean.getJDBCResource();
         JDBCDriverParamsBean jdbcDriverParams = dataSourceMBean.getJDBCDriverParams();
         dbPassword = jdbcDriverParams.getPassword();
         String url = jdbcDriverParams.getUrl();
         String driver = jdbcDriverParams.getDriverName();
         Properties driverProps = new Properties();
         JDBCPropertiesBean propsBean = jdbcDriverParams.getProperties();
         if (propsBean != null) {
            JDBCPropertyBean[] props = propsBean.getProperties();

            for(int inc = 0; props != null && inc < props.length; ++inc) {
               driverProps.put(props[inc].getName(), props[inc].getValue());
            }
         }

         DataSource dataSource = null;

         try {
            dataSource = new BasicDataSource(url, driver, driverProps, dbPassword);
         } catch (Exception var24) {
            var24.printStackTrace();
         }

         int maxDelBatch = 20;
         int maxInsBatch = 20;
         int maxDelPerStmt = 20;
         JDBCStoreIO ios = new JDBCStoreIO(name, dataSource, tableRef, "", maxDelBatch, maxInsBatch, maxDelPerStmt, false);
         store = new PersistentStoreImpl(name, ios, (RuntimeHandler)null);
         HashMap config = new HashMap();
         this.openStoreWithConfig(store, config);
         config.put("SynchronousWritePolicy", StoreWritePolicy.CACHE_FLUSH);
         psm.setDefaultStore(store);
         new StoreTransactionLoggerImpl(System.out, true);
      }
   }

   public boolean isTransactionServiceRunning() {
      return TransactionService.isRunning();
   }

   public boolean isServerRunning() {
      return T3Srvr.getT3Srvr().getRunState() == 2;
   }

   public String getServerState() {
      return ManagementService.getRuntimeAccess(kernelID).getServerRuntime().getState();
   }

   public String getDomainName() {
      return ManagementService.getRuntimeAccess(kernelID).getDomainName();
   }

   public void doTimerLifecycleHousekeeping() {
      if (!this.isTransactionServiceRunning() && getTM().getNumTransactions() == 0) {
         if (ClientInitiatedTxShutdownService.isSuspending()) {
            ClientInitiatedTxShutdownService.suspendDone();
         }

         if (TransactionService.isSuspending()) {
            if (!getTM().getJdbcTLogEnabled()) {
               ((ServerTransactionManagerImpl)getTM()).checkpoint();
            }

            TransactionService.suspendDone();
         }
      }

   }

   public JTARecoveryRuntime getJTARecoveryRuntime(String serverName) {
      return TransactionRecoveryService.getRuntimeMBean(serverName);
   }

   public JTARecoveryRuntime manageCrossSiteJTARecoveryRuntime(String siteName, String serverName, boolean isRegister) {
      TxDebug.JTARecovery.debug("PlatformHelperImpl.manageCrossSiteJTARecoveryRuntime siteName:" + siteName + " servername:" + serverName);
      return registerCrossSiteJTARecoveryRuntimeListener.manageCrossDomainJTARecoveryRuntime(siteName, serverName, isRegister);
   }

   public void scheduleFailBack(String serverName) {
      TransactionRecoveryService.scheduleFailBack(serverName);
   }

   public Object createJTATransaction(TransactionImpl current) {
      return new JTATransactionImpl((ServerTransactionImpl)current);
   }

   public TransactionHelper getTransactionHelper() {
      return new TransactionHelperImpl();
   }

   public boolean sendRequest(Object endPoint) {
      EndPoint ep = (EndPoint)endPoint;
      TransactionImpl tx = (TransactionImpl)getTM().getTransaction();
      if (ep != null && ep.getHostID() instanceof ServerIdentity) {
         ServerIdentity id = (ServerIdentity)ep.getHostID();
         tx.setPreferredHost(id);
         return tx.setCoordinatorDescriptor(id, new weblogic.transaction.internal.Channel(ep.getRemoteChannel()));
      } else {
         return false;
      }
   }

   static TransactionManagerImpl getTM() {
      return TransactionManagerImpl.getTransactionManager();
   }

   public CoordinatorDescriptorManager getCoordinatorDescriptorManager() {
      return scdm;
   }

   public void associateThreadPreferredHost(TransactionImpl tx, TransactionManagerImpl.TxThreadProperty txp) {
      if (tx != null) {
         if (!txp.preferredHostSetForTx) {
            txp.preTxPreferredHost = ThreadPreferredHost.get();
            txp.preferredHostSetForTx = true;
         }

         tx.setActiveThread(Thread.currentThread());
         ThreadPreferredHost.set((HostID)tx.getPreferredHost());
      } else if (txp.preferredHostSetForTx) {
         ThreadPreferredHost.set((HostID)txp.preTxPreferredHost);
         txp.preferredHostSetForTx = false;
         txp.preTxPreferredHost = null;
      }

   }

   public Object getDisconnectMonitor() {
      return DisconnectMonitorListImpl.getDisconnectMonitor();
   }

   public Object getRemoteSubject(String url) throws IOException, RemoteException {
      AuthenticatedSubject userForSearch = kernelID;
      AuthenticatedSubject user = null;

      try {
         if (!this.isSSLURL(url, true)) {
            userForSearch = SubjectUtils.getAnonymousSubject();
         }

         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), " PlatformHelperImpl.getRemoteSubject userForSearch:" + userForSearch + " url:" + url);
         }

         GetSubjectFromCredentialMap getRemoteSubject = new GetSubjectFromCredentialMap(url);
         SecurityServiceManager.runAs(kernelID, userForSearch, getRemoteSubject);
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), " PlatformHelperImpl.getRemoteSubject user to do RMI call:" + getRemoteSubject.getUser() + " url:" + url);
         }

         return getRemoteSubject != null ? getRemoteSubject.getUser() : null;
      } catch (Exception var5) {
         throw new IOException("Trouble finding the subject in the CredentialMap for  server: " + url + " Exception : " + var5);
      }
   }

   public final boolean isInCluster() {
      return ManagementService.getRuntimeAccess(kernelID).getServer().getCluster() != null;
   }

   public String getClusterName() {
      ClusterMBean clusterConfig = ManagementService.getRuntimeAccess(kernelID).getServer().getCluster();
      return clusterConfig == null ? null : clusterConfig.getName();
   }

   Collection getActiveServersInCluster() {
      ClusterServices cs = null;
      if (this.isInCluster()) {
         cs = Locator.locateClusterServices();
      }

      if (cs == null) {
         return null;
      } else {
         Collection members = cs.getRemoteMembers();
         Iterator it = members.iterator();
         ArrayList servers = new ArrayList();

         while(it.hasNext()) {
            Object next = it.next();
            if (next instanceof ClusterMemberInfo) {
               servers.add((ClusterMemberInfo)next);
            }
         }

         return servers;
      }
   }

   public XAResource refreshITMXAResourceReference(String serverName) throws XAException {
      return this.refreshReferenceInServerNameToXAResourceMap(serverName, this.createJNDIContextFromITMCommunication());
   }

   public void setServerResume() throws Exception {
      ManagementService.getRuntimeAccess(kernelID).getServerRuntime().resume();
   }

   public Map getClustersITMXAResources() throws XAException {
      String[] serverConfigs = this.getClusterServerNames();
      synchronized(m_clusterStatusLock) {
         this.doGetClustersITMXAResources(serverConfigs);
         Map clusterStatusMap = new HashMap();
         Iterator var4 = this.m_serverNameToXAExceptionMap.entrySet().iterator();

         Map.Entry next;
         while(var4.hasNext()) {
            next = (Map.Entry)var4.next();
            clusterStatusMap.put(next.getKey(), new PlatformHelper.ClusterStatus(this, (XAResource)null, (XAException)next.getValue()));
         }

         var4 = this.m_serverNameToXAResourceMap.entrySet().iterator();

         while(var4.hasNext()) {
            next = (Map.Entry)var4.next();
            clusterStatusMap.put(next.getKey(), new PlatformHelper.ClusterStatus(this, (XAResource)next.getValue(), (XAException)null));
         }

         return clusterStatusMap;
      }
   }

   String[] getClusterServerNames() {
      ClusterMBean clusterConfig = ManagementService.getRuntimeAccess(kernelID).getServer().getCluster();
      ServerMBean[] serverMBeans = clusterConfig.getServers();
      String[] serverNames = new String[serverMBeans.length];

      for(int i = 0; i < serverMBeans.length; ++i) {
         serverNames[i] = serverMBeans[i].getName();
      }

      return serverNames;
   }

   private void doGetClustersITMXAResources(String[] serverConfigs) throws XAException {
      this.addClusterServersToXAResourceMap(serverConfigs);
      this.removeDeletedServersFromServerNameToXAResourceMap(serverConfigs);
   }

   private void addClusterServersToXAResourceMap(String[] serverConfigs) throws XAException {
      Context context = this.createJNDIContextFromITMCommunication();
      String[] var3 = serverConfigs;
      int var4 = serverConfigs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String serverName = var3[var5];
         if (!this.m_serverNameToXAResourceMap.containsKey(serverName)) {
            try {
               this.refreshReferenceInServerNameToXAResourceMap(serverName, context);
            } catch (XAException var8) {
               if (TxDebug.JTA2PC.isDebugEnabled()) {
                  TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "addClusterServersToXAResourceMap XAException communicating with serverName:" + serverName + " XAException:" + var8);
               }

               this.m_serverNameToXAExceptionMap.put(serverName, var8);
            }
         }
      }

   }

   Context createJNDIContextFromITMCommunication() throws XAException {
      Context context = null;

      try {
         context = new InitialContext();
         return context;
      } catch (NamingException var4) {
         XAException xaException = new XAException("NamingException encountered while creating initial context for communicating with cluster for InterposedTransactionManager XA call for context:" + context);
         xaException.initCause(var4);
         xaException.errorCode = -7;
         throw xaException;
      }
   }

   private XAResource refreshReferenceInServerNameToXAResourceMap(String serverName, Context context) throws XAException {
      try {
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "refreshReferenceInServerNameToXAResourceMap serverName:" + serverName);
         }

         InterposedTransactionManager interposedTransactionManager = (InterposedTransactionManager)context.lookup("weblogic.transaction.coordinators." + serverName);
         XAResource xaResource = interposedTransactionManager.getXAResource();
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "refreshReferenceInServerNameToXAResourceMap about to put serverName:" + serverName + " xaResource:" + xaResource);
         }

         synchronized(m_clusterStatusLock) {
            this.m_serverNameToXAResourceMap.put(serverName, xaResource);
            this.m_serverNameToXAExceptionMap.remove(serverName);
         }

         return xaResource;
      } catch (NamingException var8) {
         XAException xaException = new XAException("NamingException encountered while communicating with cluster for InterposedTransactionManager XA call for context:" + context);
         xaException.initCause(var8);
         xaException.errorCode = -7;
         throw xaException;
      }
   }

   private void removeDeletedServersFromServerNameToXAResourceMap(String[] serverConfigs) {
      if (this.m_serverNameToXAResourceMap.size() != serverConfigs.length) {
         Set serverNames = this.m_serverNameToXAResourceMap.keySet();
         String serverToRemove = null;
         Iterator var4 = serverNames.iterator();

         while(var4.hasNext()) {
            String serverName = (String)var4.next();
            String[] var6 = serverConfigs;
            int var7 = serverConfigs.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String serverConfig = var6[var8];
               if (serverName.equals(serverConfig)) {
                  serverToRemove = null;
                  break;
               }

               serverToRemove = serverName;
            }

            if (serverToRemove != null) {
               serverNames.remove(serverName);
               this.m_serverNameToXAExceptionMap.remove(serverName);
            }
         }
      }

   }

   public ServerCoordinatorDescriptor findServerInClusterByLocalJNDI(String resName, Collection exceptServerNames) {
      Collection clusterMemberInfos = this.getActiveServersInCluster();
      if (clusterMemberInfos == null) {
         return null;
      } else {
         clusterMemberInfos.removeAll(exceptServerNames);
         if (clusterMemberInfos.isEmpty()) {
            return null;
         } else {
            InitialContext ctx = null;

            label167: {
               ClusterMemberInfo clusterMemberInfo;
               try {
                  ctx = new InitialContext();
                  Iterator var5 = clusterMemberInfos.iterator();

                  while(true) {
                     if (!var5.hasNext()) {
                        break label167;
                     }

                     clusterMemberInfo = (ClusterMemberInfo)var5.next();
                     Object o = null;

                     try {
                        String coUrl;
                        try {
                           o = ctx.lookup("weblogic.transaction.resources." + clusterMemberInfo.serverName() + "." + resName);
                           if (TxDebug.JTA2PC.isDebugEnabled()) {
                              TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "findResourceInCluster: check local JNDI: found XA resource " + resName + " on  " + clusterMemberInfo.serverName() + " object:" + o);
                           }

                           String uri = clusterMemberInfo.identity().getHostURI();
                           coUrl = uri.substring(uri.indexOf(":") + 1 + 2, uri.length());
                           String port = coUrl.substring(coUrl.indexOf(":") + 1, coUrl.length());
                           ServerTransactionImpl.migratedResourceCoordinatorURL = clusterMemberInfo.serverName() + "+" + clusterMemberInfo.hostAddress() + ":" + port + "+" + clusterMemberInfo.domainName() + "+" + uri.substring(0, uri.indexOf("//") - 1) + "+";
                           TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "PlatformHelperImpl.findServerInClusterByLocalJNDI ServerTransactionImpl.migratedResourceCoordinatorURL:" + ServerTransactionImpl.migratedResourceCoordinatorURL + " clusterMemberInfo.identity().getHostURI():" + clusterMemberInfo.identity().getHostURI());
                        } catch (NamingException var26) {
                           try {
                              o = ctx.lookup("weblogic.transaction.nonxaresources." + clusterMemberInfo.serverName() + "." + resName);
                              if (TxDebug.JTA2PC.isDebugEnabled()) {
                                 TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "findResourceInCluster: check local JNDI: found non-XA resource " + resName + " on  " + clusterMemberInfo.serverName() + " object:" + o);
                              }
                           } catch (NamingException var25) {
                              continue;
                           }
                        }

                        ClientTransactionManagerImpl co = (ClientTransactionManagerImpl)ctx.lookup("weblogic.transaction.coordinators." + clusterMemberInfo.serverName());
                        coUrl = co.getCoordinatorURL();
                        ServerCoordinatorDescriptor scd = ((ServerCoordinatorDescriptorManager)this.getCoordinatorDescriptorManager()).getOrCreate(coUrl);
                        ServerCoordinatorDescriptor var11 = scd;
                        return var11;
                     } catch (Exception var27) {
                     }
                  }
               } catch (NamingException var28) {
                  clusterMemberInfo = null;
               } finally {
                  if (ctx != null) {
                     try {
                        ctx.close();
                     } catch (NamingException var24) {
                     }
                  }

               }

               return clusterMemberInfo;
            }

            if (TxDebug.JTA2PC.isDebugEnabled()) {
               TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)TransactionHelper.getTransactionHelper().getTransaction(), "findResourceInCluster: check local JNDI: cannot find resource " + resName + " on any server in cluster");
            }

            return null;
         }
      }
   }

   public XAResource findXAResourceInClusterByRemoteJNDI(String resourceName, Collection exceptServerNames) {
      TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDI resourceName " + resourceName);
      ClusterMBean clusterConfig = ManagementService.getRuntimeAccess(kernelID).getServer().getCluster();
      if (clusterConfig == null) {
         return null;
      } else {
         ServerMBean[] serverMBeans = clusterConfig.getServers();
         ServerMBean[] var5 = serverMBeans;
         int var6 = serverMBeans.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ServerMBean serverMBean = var5[var7];
            String serverListenAddress = serverMBean.getListenAddress();
            if (serverListenAddress == null || serverListenAddress.trim().equals("")) {
               serverListenAddress = "localhost";
            }

            Set serverNames = serverMBean.getServerNames();
            XAResource xaResource = null;
            String serverURL = null;

            try {
               serverURL = "t3://" + serverListenAddress + ":" + serverMBean.getListenPort();
               Context context = this.getInitialContext(serverURL);
               String managedServerName = serverMBean.getName();
               TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDI about to lookup managedServerName:" + managedServerName + " on serverListenAddress:" + serverListenAddress + " serverURL:" + serverURL + " serverNames.size()" + serverNames.size());
               InterposedTransactionManager interposedTransactionManager = (InterposedTransactionManager)context.lookup("weblogic.transaction.coordinators." + managedServerName);
               XAResource xares = interposedTransactionManager.getXAResource();
               TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDI lookup successful for serverURL:" + serverURL + " xares:" + xares);

               try {
                  Xid[] xids = xares.recover(1001003);
                  if (xids == null) {
                     TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDI nullXidArray serverURL:" + serverURL + " xares:" + xares + " recover xids == null");
                  } else {
                     TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDI nonnullXidArray serverURL:" + serverURL + " xares:" + xares + " xids.length" + xids.length);
                     boolean isResName = false;
                     Xid[] var19 = xids;
                     int var20 = xids.length;

                     for(int var21 = 0; var21 < var20; ++var21) {
                        Xid xid = var19[var21];
                        if (isResName) {
                           XAResource var23;
                           if (xid.getFormatId() == 0 && xid.getBranchQualifier().length == 0) {
                              TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDI serverURL:" + serverURL + " xares:" + xares + " found determiner Xid record" + new String(xid.getBranchQualifier()) + " isResName:" + isResName + " (determiner(s) other than the resourceName requested were also found)");
                              var23 = this.getNoOpXaResource();
                              return var23;
                           }

                           var23 = null;
                           return var23;
                        }

                        isResName = resourceName.equals(new String(xid.getBranchQualifier()));
                        TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDI serverURL:" + serverURL + " xares:" + xares + " found determiner Xid record" + new String(xid.getBranchQualifier()) + " isResName:" + isResName);
                     }

                     if (isResName) {
                        TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDI serverURL:" + serverURL + " xares:" + xares + " found only one determiner Xid record found and isResName:" + isResName);
                        XAResource var31 = this.getNoOpXaResource();
                        return var31;
                     }
                  }
               } catch (XAException var28) {
                  TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDIXAException serverURL:" + serverURL + " xaresex:" + var28);
               }
            } catch (NamingException var29) {
               TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterne:" + var29);
            } finally {
               TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findXAResourceInClusterByRemoteJNDI: checked  JNDI: for resourceName:" + resourceName + " on  serverURL:" + serverURL + " xaResource:" + xaResource);
            }
         }

         return null;
      }
   }

   private XAResource getNoOpXaResource() {
      return new XAResource() {
         public void commit(Xid xid, boolean b) throws XAException {
         }

         public void end(Xid xid, int i) throws XAException {
         }

         public void forget(Xid xid) throws XAException {
         }

         public int getTransactionTimeout() throws XAException {
            return 0;
         }

         public boolean isSameRM(XAResource xaResource) throws XAException {
            return false;
         }

         public int prepare(Xid xid) throws XAException {
            return 0;
         }

         public Xid[] recover(int i) throws XAException {
            return new Xid[0];
         }

         public void rollback(Xid xid) throws XAException {
         }

         public boolean setTransactionTimeout(int i) throws XAException {
            return false;
         }

         public void start(Xid xid, int i) throws XAException {
         }
      };
   }

   public boolean isServer() {
      return KernelStatus.isServer();
   }

   public void registerRMITransactionInterceptor(Object interceptor) {
      InterceptorManager.getManager().setTransactionInterceptor((Interceptor)interceptor);
   }

   public int getInteropMode() {
      ServerTransactionManagerImpl var10000 = (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
      return ServerTransactionManagerImpl.getInteropMode();
   }

   public String getPartitionName() {
      return JTAPartitionService.getOrCreateJTAPartitionService().getPartitionName();
   }

   public String getPartitionName(boolean isAdmin) {
      return JTAPartitionService.getOrCreateJTAPartitionService().getPartitionName(isAdmin);
   }

   public ComponentInvocationContext getCurrentComponentInvocationContext() {
      return JTAPartitionService.getOrCreateJTAPartitionService().getCurrentComponentInvocationContext();
   }

   public int getTimeoutPartition() {
      return JTAPartitionService.getOrCreateJTAPartitionService().getTimeoutPartition();
   }

   public int getTimeoutPartition(String partitionName) {
      return JTAPartitionService.getOrCreateJTAPartitionService().getTimeoutPartition(partitionName);
   }

   public String[] getDeterminersPartition() {
      return JTAPartitionService.getOrCreateJTAPartitionService().getDeterminersPartition();
   }

   public String[] getDeterminersPartition(String partitionName) {
      return JTAPartitionService.getOrCreateJTAPartitionService().getDeterminersPartition(partitionName);
   }

   public ClassLoader getContextClassLoader() {
      return Thread.currentThread().getContextClassLoader();
   }

   public Object getCurrentSubject() {
      return SecurityServiceManager.getCurrentSubject(this.getKernelID());
   }

   private AuthenticatedSubject getKernelID() {
      return kernelID;
   }

   WorkManager getParallelXAWorkManager(String parallelXADispatchPolicy) {
      if (this.parallelXAWorkManager == null) {
         if (parallelXADispatchPolicy == null) {
            this.parallelXAWorkManager = WorkManagerFactory.getInstance().getDefault();
         } else {
            this.parallelXAWorkManager = WorkManagerFactory.getInstance().find(parallelXADispatchPolicy);
         }
      }

      return this.parallelXAWorkManager;
   }

   public void makeTransactionAware() {
      TxUtils.makeTransactionAware();
   }

   public boolean executeIfIdleOnParallelXAWorkManager(Runnable runnable, String parallelXADispatchPolicy) {
      return this.getParallelXAWorkManager(parallelXADispatchPolicy).executeIfIdle(runnable);
   }

   public void scheduleCheckStatusRequest(HashMap checkStatusTransactions, Runnable callback) {
      WorkManagerFactory.getInstance().getSystem().schedule(new CheckStatusRequest(checkStatusTransactions, callback));
   }

   public void scheduleWork(final Runnable otsER) {
      Work work = new WorkAdapter() {
         public void run() {
            otsER.run();
         }
      };
      WorkManagerFactory.getInstance().getSystem().schedule(work);
   }

   public void timerManagerSchedule(Object timerListener) {
      TimerManager timerManager;
      if (KernelStatus.isServer()) {
         timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
      } else {
         timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.transaction.Timer", WorkManagerFactory.getInstance().findOrCreate("weblogic.transaction.Timer", 1, -1));
      }

      timerManager.schedule((TimerListener)timerListener, 5000L, 5000L);
   }

   public void initLoggingResourceRetry() {
      LoggingResourceRetry.init();
   }

   public void registerFailedLLRTransactionLoggingResourceRetry(Object serverTransaction) {
      LoggingResourceRetry.registerFailedLLRTransaction(serverTransaction);
   }

   public void checkForSSLOnlyServerRetriction(PropagationContext aTxContext, Transaction tx) throws TransactionRolledbackException {
      PeerInfo peerInfo = (PeerInfo)aTxContext.getPeerInfo();
      if (peerInfo != null && peerInfo.getMajor() == 6 && ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getLocalCoordinatorDescriptor().isSSLOnly()) {
         TransactionRolledbackException rbe = new TransactionRolledbackException("6.x server cannot participate in a transaction with a SSL-only server");
         tx.setRollbackOnly(rbe);
         throw rbe;
      }
   }

   public void runAction(PrivilegedExceptionAction privilegedExceptionAction, String coServerURL, String s) throws Exception {
      SecureAction.runAction(kernelID, privilegedExceptionAction, coServerURL, s);
   }

   public Object runSecureAction(Object kernelIdParam, PrivilegedExceptionAction privilegedExceptionAction, String scServerURL, String s) throws Exception {
      return SecureAction.runAction((AuthenticatedSubject)kernelIdParam, privilegedExceptionAction, scServerURL, s);
   }

   public Object runSecureAction(PrivilegedExceptionAction privilegedExceptionAction, String scServerURL, String s) throws Exception {
      return SecureAction.runAction(kernelID, privilegedExceptionAction, scServerURL, s);
   }

   public void runKernelAction(PrivilegedExceptionAction privilegedExceptionAction, String advertiseResource) throws Exception {
      SecureAction.runKernelAction(kernelID, privilegedExceptionAction, advertiseResource);
   }

   public boolean resourceCheck(String resName, String serverName, String providerURL) {
      if (!enableResourceChecks) {
         return false;
      } else {
         InitialContext ctx = null;
         String aServerURL = null;

         try {
            CoordinatorDescriptor aCd = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getLocalCoordinatorDescriptor();
            aServerURL = CoordinatorDescriptor.getServerURL(aCd.getCoordinatorURL());
            Hashtable o;
            if (aServerURL.equals(providerURL)) {
               TxDebug.JTA2PC.debug("resourceCheck resName:" + resName + " serverName:" + serverName + " providerURL:" + providerURL + " aServerURL:" + aServerURL);
               ctx = new InitialContext();
            } else {
               TxDebug.JTA2PC.debug("resourceCheck resName:" + resName + " serverName:" + serverName + " providerURL:" + providerURL + " aServerURL:" + aServerURL + " jndiConnectTimeout: " + JNDI_CONNECT_TIMEOUT + " jndiResponseReadTimeout: " + JNDI_RESPONSE_READ_TIMEOUT);
               o = new Hashtable();
               o.put("java.naming.provider.url", providerURL);
               o.put("weblogic.jndi.responseReadTimeout", new Long((long)JNDI_RESPONSE_READ_TIMEOUT));
               o.put("weblogic.jndi.connectTimeout", new Long((long)JNDI_CONNECT_TIMEOUT));
               ctx = new InitialContext(o);
            }

            o = null;

            try {
               ctx.lookup("weblogic.transaction.resources." + serverName + "." + resName);
            } catch (NamingException var11) {
               try {
                  ctx.lookup("weblogic.transaction.nonxaresources." + serverName + "." + resName);
               } catch (Exception var10) {
                  return false;
               }
            }
         } catch (Exception var12) {
            TxDebug.JTA2PC.debug("resourceCheck resName:" + resName + " serverName:" + serverName + " providerURL:" + providerURL + " aServerURL:" + aServerURL + " Exception:" + var12);
            return false;
         }

         TxDebug.JTA2PC.debug("resourceCheck return true resName:" + resName + " serverName:" + serverName + " providerURL:" + providerURL + " aServerURL:" + aServerURL);
         return true;
      }
   }

   public String[] getAllServerNamesInDomain() {
      ServerMBean[] serverMBeans = ManagementService.getRuntimeAccess(kernelID).getDomain().getServers();
      if (serverMBeans == null) {
         return null;
      } else {
         String[] serverNames = new String[serverMBeans.length];

         for(int i = 0; i < serverMBeans.length; ++i) {
            serverNames[i] = serverMBeans[i].getName();
         }

         return serverNames;
      }
   }

   public ServerCoordinatorDescriptor findServerInDomains(String resName, Collection exceptServerNames, Collection remoteCoordinators) {
      TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "findServerInDomains resourceName " + resName);
      ServerCoordinatorDescriptor scd = this.findServerInClusterByLocalJNDI(resName, exceptServerNames);
      if (scd != null) {
         return scd;
      } else if (remoteCoordinators.size() == 0) {
         return null;
      } else {
         List activeList = ((ServerCoordinatorDescriptorManager)this.getCoordinatorDescriptorManager()).getAllCheckpointServers();
         List domainServerList = this.getCoorURLsInDomain(exceptServerNames);
         Iterator i;
         String tempUrl;
         if (domainServerList != null && domainServerList.size() != 0) {
            i = domainServerList.iterator();

            while(i.hasNext()) {
               tempUrl = (String)i.next();
               if (!activeList.contains(tempUrl)) {
                  activeList.add(tempUrl);
               }
            }
         }

         if (activeList != null && activeList.size() != 0) {
            i = activeList.iterator();

            while(true) {
               String aServerName;
               String anURL;
               do {
                  if (!i.hasNext()) {
                     return null;
                  }

                  tempUrl = (String)i.next();
                  int plusPos = tempUrl.indexOf(43);
                  aServerName = plusPos == -1 ? null : tempUrl.substring(0, plusPos);
                  anURL = CoordinatorDescriptor.getServerURL(tempUrl);
                  TxDebug.JTA2PC.debug("findServerInDomains resName:" + resName + " aServerName:" + aServerName + " anURL:" + anURL);
               } while(!this.resourceCheck(resName, aServerName, anURL));

               try {
                  Hashtable ht = new Hashtable();
                  ht.put("java.naming.provider.url", anURL);
                  Context ctx = this.getNewInitialContext(ht);
                  ClientTransactionManagerImpl co = (ClientTransactionManagerImpl)ctx.lookup("weblogic.transaction.coordinators." + aServerName);
                  String coUrl = co.getCoordinatorURL();
                  scd = ((ServerCoordinatorDescriptorManager)this.getCoordinatorDescriptorManager()).getOrCreate(coUrl);
                  TxDebug.JTA2PC.debug("findServerInDomains find resource:" + resName + " coUrl:" + coUrl + " serverName:" + aServerName);
                  return scd;
               } catch (Exception var16) {
                  TxDebug.JTA2PC.debug("findServerInDomains find resource:" + resName + " serverName:" + aServerName + " Exception:" + var16);
               }
            }
         } else {
            return null;
         }
      }
   }

   public Context getNewInitialContext(Hashtable ht) throws Exception {
      return new InitialContext(ht);
   }

   private List getCoorURLsInDomain(Collection exceptServerNames) {
      List serverList = new ArrayList();
      ServerMBean[] serverMBeans = ManagementService.getRuntimeAccess(kernelID).getDomain().getServers();
      if (serverMBeans != null) {
         for(int i = 0; i < serverMBeans.length; ++i) {
            String serverName = serverMBeans[i].getName();
            if (!exceptServerNames.contains(serverName)) {
               String listenAddress = serverMBeans[i].getListenAddress();
               String proto = null;
               int port = false;
               int port;
               if (serverMBeans[i].isListenPortEnabled()) {
                  proto = "t3";
                  port = serverMBeans[i].getListenPort();
               } else {
                  SSLMBean sslMBean = serverMBeans[i].getSSL();
                  if (sslMBean == null || !sslMBean.isListenPortEnabled()) {
                     continue;
                  }

                  proto = "t3s";
                  port = sslMBean.getListenPort();
               }

               String serverURL = listenAddress + ":" + port;
               String domainName = this.getDomainName();
               String coURL = serverName + "+" + serverURL + "+" + domainName + "+" + proto + "+";
               serverList.add(coURL);
            }
         }
      }

      return serverList;
   }

   public int getForcedShutdownTimeoutSeconds() {
      return ManagementService.getRuntimeAccess(kernelID).getServer().getServerLifeCycleTimeoutVal();
   }

   public String getTransactionServiceHalt() {
      return this.transactionServiceHalt;
   }

   public void setTransactionServiceHalt(String transactionServiceHalt) {
      this.transactionServiceHalt = transactionServiceHalt;
   }

   public void passivateTransactionRecoveryService() {
      TransactionRecoveryService.passiveModePassivate();
   }

   public void activateTransactionRecoveryService() throws SystemException {
      TransactionRecoveryService.passiveModeActivate();
   }

   public String getPrimaryChannelName() {
      String name = System.getProperty("weblogic.transaction.primaryChannelName");
      if (name != null) {
         return name;
      } else {
         ServerMBean server = ManagementService.getRuntimeAccess(kernelID).getServer();
         return server.getTransactionPrimaryChannelName();
      }
   }

   public String getPublicChannelName() {
      String name = System.getProperty("weblogic.transaction.publicChannelName");
      if (name != null) {
         return name;
      } else {
         ServerMBean server = ManagementService.getRuntimeAccess(kernelID).getServer();
         return server.getTransactionPublicChannelName();
      }
   }

   public String getSecureChannelName() {
      String name = System.getProperty("weblogic.transaction.secureChannelName");
      if (name != null) {
         return name;
      } else {
         ServerMBean server = ManagementService.getRuntimeAccess(kernelID).getServer();
         return server.getTransactionSecureChannelName();
      }
   }

   public String getPublicSecureChannelName() {
      String name = System.getProperty("weblogic.transaction.publicSecureChannelName");
      if (name != null) {
         return name;
      } else {
         ServerMBean server = ManagementService.getRuntimeAccess(kernelID).getServer();
         return server.getTransactionPublicSecureChannelName();
      }
   }

   public boolean usePublicAddressForRemoteDomain(CoordinatorDescriptor targetCoDesc) {
      String targetDomain = targetCoDesc.getDomainName();
      TransactionManagerImpl tm = getTM();
      return tm instanceof ServerTransactionManagerImpl ? ((ServerTransactionManagerImpl)tm).usePublicAddressesForRemoteDomain(targetDomain) : false;
   }

   public boolean useNonSecureAddressForDomain(CoordinatorDescriptor targetCoDesc) {
      String targetDomain = targetCoDesc.getDomainName();
      TransactionManagerImpl tm = getTM();
      return tm instanceof ServerTransactionManagerImpl ? ((ServerTransactionManagerImpl)tm).useNonSecureAddressesforDomain(targetDomain) : false;
   }

   public String getTargetChannelURL(CoordinatorDescriptor targetCoDesc) {
      if (!CoordinatorDescriptor.isCoURLExtendedWithChannelAddresses(targetCoDesc.getCoordinatorURL())) {
         return null;
      } else {
         if (PlatformHelper.getPlatformHelper().usePublicAddressForRemoteDomain(targetCoDesc)) {
            if (targetCoDesc.isPublicSecureURLSet() && !PlatformHelper.getPlatformHelper().useNonSecureAddressForDomain(targetCoDesc)) {
               return targetCoDesc.getPublicSecureURL();
            }

            if (targetCoDesc.isPublicURLSet()) {
               return targetCoDesc.getPublicURL();
            }
         } else if (targetCoDesc.isSecureURLSet() && !PlatformHelper.getPlatformHelper().useNonSecureAddressForDomain(targetCoDesc)) {
            return targetCoDesc.getSecureURL();
         }

         return targetCoDesc.getPrimaryURL();
      }
   }

   public void addRecoveredServerCheckpointToMigratedTRS(String serverName, CoordinatorDescriptor cd) {
      if (serverName != null && cd != null) {
         TransactionRecoveryService migratedtrs = TransactionRecoveryService.get(serverName);
         if (migratedtrs != null) {
            migratedtrs.addCheckpointedServer(cd);
         }

      }
   }

   public boolean allowAutoTRSFailback(String serverName) {
      TransactionRecoveryService trs = TransactionRecoveryService.get(serverName);
      return trs == null ? true : trs.allowAutoFailback();
   }

   public void delayAutoTRSFailback(String serverName) {
      TransactionRecoveryService trs = TransactionRecoveryService.get(serverName);
      if (trs != null) {
         trs.setDelayedFailback();
      }
   }

   public Object defaultTimerManagerSchedule(Object timerListener, long delay, long rate) {
      return TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule((TimerListener)timerListener, delay, rate);
   }

   public boolean isColocated(Object remote) {
      return remote == null ? true : RemoteHelper.isCollocated(remote);
   }

   public class CrashSiteJDBCStoreMBean implements JDBCStoreMBean {
      JDBCStoreMBean jBean;
      String prefixName;

      public CrashSiteJDBCStoreMBean(JDBCStoreMBean jBean, String prefixName) {
         this.jBean = jBean;
         this.prefixName = prefixName;
      }

      public JDBCSystemResourceMBean getDataSource() {
         return this.jBean.getDataSource();
      }

      public void setDataSource(JDBCSystemResourceMBean dataSource) throws InvalidAttributeValueException {
         this.jBean.setDataSource(dataSource);
      }

      public int getDeletesPerBatchMaximum() {
         return this.jBean.getDeletesPerBatchMaximum();
      }

      public void setDeletesPerBatchMaximum(int deletesPerBatchMaximum) throws InvalidAttributeValueException {
         this.jBean.setDeletesPerBatchMaximum(deletesPerBatchMaximum);
      }

      public int getInsertsPerBatchMaximum() {
         return this.jBean.getInsertsPerBatchMaximum();
      }

      public void setInsertsPerBatchMaximum(int deletesPerBatchMaximum) throws InvalidAttributeValueException {
         this.jBean.setInsertsPerBatchMaximum(deletesPerBatchMaximum);
      }

      public int getDeletesPerStatementMaximum() {
         return this.jBean.getDeletesPerStatementMaximum();
      }

      public void setDeletesPerStatementMaximum(int deletesPerBatchMaximum) throws InvalidAttributeValueException {
         this.jBean.setDeletesPerStatementMaximum(deletesPerBatchMaximum);
      }

      public int getWorkerCount() {
         return this.jBean.getWorkerCount();
      }

      public void setWorkerCount(int workerCount) throws InvalidAttributeValueException {
         this.jBean.setWorkerCount(workerCount);
      }

      public int getWorkerPreferredBatchSize() {
         return this.jBean.getWorkerPreferredBatchSize();
      }

      public void setWorkerPreferredBatchSize(int workerLoadSize) throws InvalidAttributeValueException {
         this.jBean.setWorkerPreferredBatchSize(workerLoadSize);
      }

      public int getThreeStepThreshold() {
         return this.jBean.getThreeStepThreshold();
      }

      public void setThreeStepThreshold(int threeStepThreshold) throws InvalidAttributeValueException {
         this.jBean.setThreeStepThreshold(threeStepThreshold);
      }

      public void setOraclePiggybackCommitEnabled(boolean enable) {
         this.jBean.setOraclePiggybackCommitEnabled(enable);
      }

      public boolean isOraclePiggybackCommitEnabled() {
         return this.jBean.isOraclePiggybackCommitEnabled();
      }

      public void setReconnectRetryPeriodMillis(int reconnectRetryPeriodMillis) {
         this.jBean.setReconnectRetryPeriodMillis(reconnectRetryPeriodMillis);
      }

      public int getReconnectRetryPeriodMillis() {
         return this.jBean.getReconnectRetryPeriodMillis();
      }

      public void setReconnectRetryIntervalMillis(int reconnectRetryIntervalMillis) {
         this.jBean.setReconnectRetryIntervalMillis(reconnectRetryIntervalMillis);
      }

      public int getReconnectRetryIntervalMillis() {
         return this.jBean.getReconnectRetryIntervalMillis();
      }

      public String getConnectionCachingPolicy() {
         return this.jBean.getConnectionCachingPolicy();
      }

      public void setConnectionCachingPolicy(String connectionCachingPolicy) {
         this.jBean.setConnectionCachingPolicy(connectionCachingPolicy);
      }

      public TargetMBean[] getTargets() {
         return this.jBean.getTargets();
      }

      public void setTargets(TargetMBean[] targets) throws InvalidAttributeValueException, DistributedManagementException {
         this.jBean.setTargets(targets);
      }

      public boolean addTarget(TargetMBean target) throws InvalidAttributeValueException, DistributedManagementException {
         return this.jBean.addTarget(target);
      }

      public boolean removeTarget(TargetMBean target) throws InvalidAttributeValueException, DistributedManagementException {
         return this.jBean.removeTarget(target);
      }

      public int getDeploymentOrder() {
         return this.jBean.getDeploymentOrder();
      }

      public void setDeploymentOrder(int order) {
         this.jBean.setDeploymentOrder(order);
      }

      public String getLogicalName() {
         return this.jBean.getLogicalName();
      }

      public void setLogicalName(String name) throws InvalidAttributeValueException {
         this.jBean.setLogicalName(name);
      }

      public String getXAResourceName() {
         return this.jBean.getXAResourceName();
      }

      public void setXAResourceName(String name) throws InvalidAttributeValueException {
         this.jBean.setXAResourceName(name);
      }

      public String getDistributionPolicy() {
         return this.jBean.getDistributionPolicy();
      }

      public void setDistributionPolicy(String distributionPolicy) {
         this.jBean.setDistributionPolicy(distributionPolicy);
      }

      public String getMigrationPolicy() {
         return this.jBean.getMigrationPolicy();
      }

      public void setMigrationPolicy(String migrationPolicy) {
         this.jBean.setMigrationPolicy(migrationPolicy);
      }

      public boolean getRestartInPlace() {
         return this.jBean.getRestartInPlace();
      }

      public void setRestartInPlace(boolean restartInPlace) {
         this.jBean.setRestartInPlace(restartInPlace);
      }

      public int getSecondsBetweenRestarts() {
         return this.jBean.getSecondsBetweenRestarts();
      }

      public void setSecondsBetweenRestarts(int seconds) {
         this.jBean.setSecondsBetweenRestarts(seconds);
      }

      public int getNumberOfRestartAttempts() {
         return this.jBean.getNumberOfRestartAttempts();
      }

      public void setNumberOfRestartAttempts(int restartAttempts) {
         this.jBean.setNumberOfRestartAttempts(restartAttempts);
      }

      public long getInitialBootDelaySeconds() {
         return this.jBean.getInitialBootDelaySeconds();
      }

      public void setInitialBootDelaySeconds(long initialBootDelaySeconds) {
         this.jBean.setInitialBootDelaySeconds(initialBootDelaySeconds);
      }

      public long getPartialClusterStabilityDelaySeconds() {
         return this.jBean.getPartialClusterStabilityDelaySeconds();
      }

      public void setPartialClusterStabilityDelaySeconds(long partialClusterStabilityDelaySeconds) {
         this.jBean.setPartialClusterStabilityDelaySeconds(partialClusterStabilityDelaySeconds);
      }

      public long getFailbackDelaySeconds() {
         return this.jBean.getFailbackDelaySeconds();
      }

      public void setFailbackDelaySeconds(long failbackDelaySeconds) {
         this.jBean.setFailbackDelaySeconds(failbackDelaySeconds);
      }

      public int getFailOverLimit() {
         return this.jBean.getFailOverLimit();
      }

      public void setFailOverLimit(int failOverLimit) {
         this.jBean.setFailOverLimit(failOverLimit);
      }

      public String getPrefixName() {
         return this.prefixName;
      }

      public void setPrefixName(String name) throws InvalidAttributeValueException {
         this.prefixName = name;
      }

      public String getCreateTableDDLFile() {
         return this.jBean.getCreateTableDDLFile();
      }

      public void setCreateTableDDLFile(String name) throws InvalidAttributeValueException {
         this.jBean.setCreateTableDDLFile(name);
      }

      public String getName() {
         return this.jBean.getName();
      }

      public void setName(String name) throws InvalidAttributeValueException, ManagementException {
         this.jBean.setName(name);
      }

      public String getType() {
         return this.jBean.getType();
      }

      public WebLogicObjectName getObjectName() {
         return this.jBean.getObjectName();
      }

      public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
         return this.jBean.getAttribute(attribute);
      }

      public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
         this.jBean.setAttribute(attribute);
      }

      public AttributeList getAttributes(String[] attributes) {
         return this.jBean.getAttributes(attributes);
      }

      public AttributeList setAttributes(AttributeList attributes) {
         return this.jBean.setAttributes(attributes);
      }

      public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
         return this.jBean.invoke(actionName, params, signature);
      }

      public MBeanInfo getMBeanInfo() {
         return this.jBean.getMBeanInfo();
      }

      public boolean isCachingDisabled() {
         return this.jBean.isCachingDisabled();
      }

      public WebLogicMBean getParent() {
         return this.jBean.getParent();
      }

      public void setParent(WebLogicMBean parent) throws ConfigurationException {
         this.jBean.setParent(parent);
      }

      public boolean isRegistered() {
         return this.jBean.isRegistered();
      }

      public String getNotes() {
         return this.jBean.getNotes();
      }

      public void setNotes(String notes) throws InvalidAttributeValueException, DistributedManagementException {
         this.jBean.setNotes(notes);
      }

      public boolean isPersistenceEnabled() {
         return this.jBean.isPersistenceEnabled();
      }

      public void setPersistenceEnabled(boolean persist) {
         this.jBean.setPersistenceEnabled(persist);
      }

      public boolean isDefaultedMBean() {
         return this.jBean.isDefaultedMBean();
      }

      public void setDefaultedMBean(boolean defaulted) {
         this.jBean.setDefaultedMBean(defaulted);
      }

      public String getComments() {
         return this.jBean.getComments();
      }

      public void setComments(String comments) {
         this.jBean.setComments(comments);
      }

      public void touch() throws ConfigurationException {
         this.jBean.touch();
      }

      public void freezeCurrentValue(String attributeName) throws AttributeNotFoundException, MBeanException {
         this.jBean.freezeCurrentValue(attributeName);
      }

      public void restoreDefaultValue(String attributeName) throws AttributeNotFoundException {
         this.jBean.restoreDefaultValue(attributeName);
      }

      public boolean isSet(String propertyName) {
         return this.jBean.isSet(propertyName);
      }

      public void unSet(String propertyName) {
         this.jBean.unSet(propertyName);
      }

      public boolean isInherited(String propertyName) {
         return this.jBean.isInherited(propertyName);
      }

      public String[] getInheritedProperties(String[] propertyNames) {
         return this.jBean.getInheritedProperties(propertyNames);
      }

      public boolean isDynamicallyCreated() {
         return this.jBean.isDynamicallyCreated();
      }

      public long getId() {
         return this.jBean.getId();
      }

      public String[] getTags() {
         return this.jBean.getTags();
      }

      public void setTags(String[] tagArray) throws IllegalArgumentException {
         this.jBean.setTags(tagArray);
      }

      public boolean addTag(String tag) throws IllegalArgumentException {
         return this.jBean.addTag(tag);
      }

      public boolean removeTag(String tag) throws IllegalArgumentException {
         return this.jBean.removeTag(tag);
      }

      public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
         return this.jBean.preRegister(server, name);
      }

      public void postRegister(Boolean registrationDone) {
         this.jBean.postRegister(registrationDone);
      }

      public void preDeregister() throws Exception {
         this.jBean.preDeregister();
      }

      public void postDeregister() {
         this.jBean.postDeregister();
      }

      public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
         this.jBean.addNotificationListener(listener, filter, handback);
      }

      public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
         this.jBean.removeNotificationListener(listener);
      }

      public MBeanNotificationInfo[] getNotificationInfo() {
         return this.jBean.getNotificationInfo();
      }

      public Descriptor getDescriptor() {
         return this.jBean.getDescriptor();
      }

      public DescriptorBean getParentBean() {
         return this.jBean.getParentBean();
      }

      public void addPropertyChangeListener(PropertyChangeListener listener) {
         this.jBean.addPropertyChangeListener(listener);
      }

      public void removePropertyChangeListener(PropertyChangeListener listener) {
         this.jBean.removePropertyChangeListener(listener);
      }

      public void addBeanUpdateListener(BeanUpdateListener listener) {
         this.jBean.addBeanUpdateListener(listener);
      }

      public void removeBeanUpdateListener(BeanUpdateListener listener) {
         this.jBean.removeBeanUpdateListener(listener);
      }

      public boolean isEditable() {
         return this.jBean.isEditable();
      }

      public DescriptorBean createChildCopy(String propertyName, DescriptorBean beanToCopy) throws IllegalArgumentException, BeanAlreadyExistsException {
         return this.jBean.createChildCopy(propertyName, beanToCopy);
      }

      public DescriptorBean createChildCopyIncludingObsolete(String propertyName, DescriptorBean beanToCopy) throws IllegalArgumentException, BeanAlreadyExistsException {
         return this.jBean.createChildCopyIncludingObsolete(propertyName, beanToCopy);
      }
   }

   private class GetSubjectFromCredentialMap implements PrivilegedExceptionAction {
      private String url;
      private AuthenticatedSubject user = null;

      GetSubjectFromCredentialMap(String aURL) {
         this.url = aURL;
      }

      public Object run() throws Exception {
         this.user = RemoteDomainSecurityHelper.getSubject2(this.url);
         return null;
      }

      public AuthenticatedSubject getUser() {
         return this.user;
      }
   }

   private class CheckStatusRequest extends WorkAdapter {
      private HashMap txs;
      private Runnable callback;

      private CheckStatusRequest(HashMap txs, Runnable callback) {
         this.txs = txs;
         this.callback = callback;
      }

      public void run() {
         try {
            this.callback.run();
         } catch (Exception var2) {
         }

      }

      // $FF: synthetic method
      CheckStatusRequest(HashMap x1, Runnable x2, Object x3) {
         this(x1, x2);
      }
   }

   public interface CrossSiteJTARecoveryRuntimeListener {
      JTARecoveryRuntime manageCrossDomainJTARecoveryRuntime(String var1, String var2, boolean var3);
   }

   public interface JDBCTLogServerMBeanIF {
      TransactionLogJDBCStoreMBean getTransactionLogJDBCStore();

      String getName();
   }
}
