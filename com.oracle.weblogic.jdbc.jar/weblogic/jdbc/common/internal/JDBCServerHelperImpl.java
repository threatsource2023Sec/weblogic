package weblogic.jdbc.common.internal;

import java.lang.annotation.Annotation;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilderFactory;
import weblogic.application.ApplicationAccess;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.common.ResourceException;
import weblogic.common.internal.PeerInfo;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.rmi.SerialConnection;
import weblogic.jdbc.rmi.internal.ConnectionImpl;
import weblogic.jdbc.wrapper.Connection;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jndi.Alias;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.URLManagerService;
import weblogic.protocol.UnknownProtocolException;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.EndPoint;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.t3.srvr.T3Srvr;
import weblogic.utils.XXEUtils;

public class JDBCServerHelperImpl extends JDBCHelper {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean remoteEnabled = new Boolean(System.getProperty("weblogic.jdbc.remoteEnabled", "true"));
   private static final UnsupportedOperationException remoteUnsupportedException = new UnsupportedOperationException("Remote JDBC disabled");
   private static ServerMBean localServerMBean = null;

   public Object interopReplace(Connection conn, PeerInfo peerInfo) {
      if (!remoteEnabled) {
         conn.forceClose();
         throw remoteUnsupportedException;
      } else {
         try {
            ConnectionImpl rmiConn = (ConnectionImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.ConnectionImpl", conn, true);
            rmiConn.init(conn);

            try {
               rmiConn.addPeerGoneListener();
            } catch (Exception var5) {
               JDBCLogger.logStackTrace(var5);
            }

            return new SerialConnection((java.sql.Connection)rmiConn);
         } catch (Exception var6) {
            JDBCLogger.logStackTrace(var6);
            return this;
         }
      }
   }

   public boolean getAutoConnectionClose() {
      if (KernelStatus.isServer()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         ServerMBean serverMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
         String flag = serverMBean.getAutoJDBCConnectionClose();
         return !flag.equalsIgnoreCase("false");
      } else {
         return true;
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public String getDefaultURL() {
      String url = null;

      String var4;
      try {
         ServerMBean serverMBean = getLocalServerMBean();
         String serverName = serverMBean.getName();
         if (this.isRMISecure()) {
            try {
               url = getURLManagerService().findURL(serverName, ProtocolManager.getDefaultSecureProtocol());
               if (url != null) {
                  var4 = url;
                  return var4;
               }

               throw new RuntimeException("No secure channel available for JTS driver");
            } catch (UnknownHostException var8) {
               throw new RuntimeException(var8);
            }
         }

         url = ChannelHelper.getDefaultURL();
         var4 = url;
      } finally {
         if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
            JdbcDebug.JDBCRMI.debug("URL for RMI JDBC: " + url);
         }

      }

      return var4;
   }

   public String getCurrentApplicationName() {
      return ApplicationAccess.getApplicationAccess().getCurrentApplicationName();
   }

   public String getDomainName() {
      return ManagementService.getRuntimeAccess(KERNEL_ID).getDomainName();
   }

   public String getServerName() {
      return ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getName();
   }

   public boolean isServerShuttingDown() {
      int runState = T3Srvr.getT3Srvr().getRunState();
      return runState == 18 || runState == 7;
   }

   public boolean isPartitionStartingShuttingDown(String partitionName) {
      if (partitionName == null) {
         return false;
      } else {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
         if (runtimeAccess == null) {
            return false;
         } else {
            ServerRuntimeMBean serverRuntimeMBean = runtimeAccess.getServerRuntime();
            if (serverRuntimeMBean == null) {
               return false;
            } else {
               PartitionRuntimeMBean partitionRuntimeMBean = serverRuntimeMBean.lookupPartitionRuntime(partitionName);
               if (partitionRuntimeMBean == null) {
                  return false;
               } else {
                  PartitionRuntimeMBean.State state = partitionRuntimeMBean.getInternalState();
                  return state == State.SHUTTING_DOWN || state == State.FORCE_SHUTTING_DOWN || state == State.STARTING;
               }
            }
         }
      }
   }

   public DocumentBuilderFactory getDocumentBuilderFactory() {
      return XXEUtils.createDocumentBuilderFactoryInstance();
   }

   public Object createJNDIAlias(String name, Object o) {
      return new Alias(name);
   }

   public boolean isProductionModeEnabled() {
      return ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().isProductionModeEnabled();
   }

   public String getXAMultiPoolName(JDBCDataSourceBean dsBean) {
      String currMPName = null;
      JDBCSystemResourceMBean[] resources = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().getJDBCSystemResources();

      int lcv;
      JDBCDataSourceBean currBean;
      for(lcv = 0; lcv < resources.length; ++lcv) {
         currBean = resources[lcv].getJDBCResource();
         if (currBean != null) {
            String dsList = currBean.getJDBCDataSourceParams().getDataSourceList();
            if (dsList != null && dsList.contains(dsBean.getName())) {
               if (JDBCUtil.getLegacyType(currBean) != 0) {
                  currMPName = currBean.getName();
                  break;
               }

               if (!currBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol().equals("None")) {
                  return currBean.getName();
               }
            }
         }
      }

      if (currMPName == null) {
         return null;
      } else {
         for(lcv = 0; lcv < resources.length; ++lcv) {
            currBean = resources[lcv].getJDBCResource();
            if (currBean != null && JDBCUtil.getLegacyType(currBean) == 4 && currMPName.equals(JDBCUtil.getInternalProperty(currBean, "LegacyPoolName"))) {
               return currMPName;
            }
         }

         return null;
      }
   }

   public List dsToList(String dataSourceList) {
      if (dataSourceList == null) {
         return null;
      } else {
         StringTokenizer st = new StringTokenizer(dataSourceList, ",");
         int count = st.countTokens();
         List newList = new ArrayList();

         for(int i = 0; i < count; ++i) {
            newList.add(st.nextToken());
         }

         return newList;
      }
   }

   public boolean isRACPool(String name, String appName, String moduleName, String compName) {
      ConnectionEnv cc = null;

      boolean var7;
      try {
         cc = ConnectionPoolManager.reserve(name, appName, moduleName, compName, -2);
         DatabaseMetaData metaData = cc.conn.jconn.getMetaData();
         if (metaData != null) {
            String databaseVer = metaData.getDatabaseProductVersion();
            int isRac = false;
            int isRac = databaseVer.indexOf("Real Application Clusters");
            boolean var9;
            if (isRac != -1) {
               var9 = true;
               return var9;
            }

            var9 = false;
            return var9;
         }

         var7 = true;
         return var7;
      } catch (Exception var21) {
         var7 = true;
      } finally {
         if (cc != null) {
            try {
               ConnectionPoolManager.release(cc);
            } catch (Exception var20) {
            }
         }

      }

      return var7;
   }

   public boolean isLLRPool(JDBCDataSourceBean dsBean) {
      return dsBean != null && dsBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol().equals("LoggingLastResource");
   }

   public boolean isLLRTablePerDataSource(String poolName) {
      String tableProperty = "weblogic.llr.table." + poolName;
      String table = System.getProperty(tableProperty);
      return table != null;
   }

   public String getJDBCLLRTableName(String serverName) {
      ServerMBean smb = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().lookupServer(serverName);
      return smb.getJDBCLLRTableName();
   }

   public Boolean isUseFusionForLLR(String serverName) {
      ServerMBean smb = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().lookupServer(serverName);
      return smb.isUseFusionForLLR();
   }

   public int getJDBCLLRTableXIDColumnSize(String serverName) {
      ServerMBean smb = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().lookupServer(serverName);
      return smb.getJDBCLLRTableXIDColumnSize();
   }

   public int getJDBCLLRTablePoolColumnSize(String serverName) {
      ServerMBean smb = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().lookupServer(serverName);
      return smb.getJDBCLLRTablePoolColumnSize();
   }

   public int getJDBCLLRTableRecordColumnSize(String serverName) {
      ServerMBean smb = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().lookupServer(serverName);
      return smb.getJDBCLLRTableRecordColumnSize();
   }

   public boolean isJNDIEnabled() {
      return true;
   }

   public static boolean isExalogicOptimizationsEnabled() {
      return ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().isExalogicOptimizationsEnabled();
   }

   public void addRMIContext(Object remote) throws Exception {
      if (rmiSecure()) {
         EndPoint ep = RemoteHelper.getEndPoint(remote);
         if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
            JdbcDebug.JDBCRMI.debug("setting security context for remote endpoint " + ep);
         }

         AuthenticatedSubject user = null;
         user = RemoteDomainSecurityHelper.getSubject(ep);
         if (user == null) {
            if (this.isSecure(ep)) {
               if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
                  JdbcDebug.JDBCRMI.debug("Secure endpoint");
               }

               user = KERNEL_ID;
            } else if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("Not setting security context, no secure channel");
            }
         }

         if (user != null) {
            if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("setting security context " + user);
            }

            SecurityManager.pushSubject(KERNEL_ID, user);
         }
      }

   }

   private boolean isSecure(EndPoint ep) throws UnknownProtocolException {
      Channel channel = ep.getRemoteChannel();
      if (channel == null) {
         return false;
      } else {
         String protocolName = channel.getProtocolPrefix();
         Protocol protocol = ProtocolManager.findProtocol(protocolName);
         return protocol == null ? false : protocol.isSecure();
      }
   }

   public void removeRMIContext(Object remote) throws Exception {
      if (rmiSecure()) {
         AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
         if (SecurityServiceManager.isKernelIdentity(subject) || RemoteDomainSecurityHelper.getSubject(RemoteHelper.getEndPoint(remote)) != null) {
            if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("removing subject " + subject);
            }

            SecurityManager.popSubject(KERNEL_ID);
         }
      }

   }

   public boolean isRMISecure() {
      return rmiSecure();
   }

   public String getVersionId(String applicationName) {
      return applicationName == null ? null : ApplicationVersionUtils.getVersionId(applicationName);
   }

   public static boolean rmiSecure() {
      ServerMBean server = getLocalServerMBean();
      return server != null && "Secure".equals(server.getDataSource().getRmiJDBCSecurity());
   }

   private static ServerMBean getLocalServerMBean() {
      if (localServerMBean == null) {
         RuntimeAccess runtime = ManagementService.getRuntimeAccess(KERNEL_ID);
         if (runtime != null) {
            localServerMBean = runtime.getServer();
         }
      }

      return localServerMBean;
   }

   public int getServerPort() {
      int port = -1;

      try {
         ServerMBean aServerMBean = getLocalServerMBean();
         if (aServerMBean != null && aServerMBean.isListenPortEnabled()) {
            port = aServerMBean.getListenPort();
         }
      } catch (Exception var3) {
      }

      return port;
   }

   public int getServerSslPort() {
      int port = -1;

      try {
         ServerMBean aServerMBean = getLocalServerMBean();
         if (aServerMBean != null) {
            SSLMBean sslMBean = aServerMBean.getSSL();
            if (sslMBean != null && sslMBean.isEnabled()) {
               port = sslMBean.getListenPort();
            }
         }
      } catch (Exception var4) {
      }

      return port;
   }

   public JDBCConnectionPool getConnectionPool(String jndiName) throws ResourceException {
      try {
         InitialContext ctx = new InitialContext();
         RmiDataSource ds = (RmiDataSource)ctx.lookup(jndiName);
         if (ds == null) {
            return null;
         } else {
            JDBCConnectionPool pool = ConnectionPoolManager.getPool(ds.getPoolName(), ds.getAppName(), ds.getModuleName(), ds.getCompName());
            return pool;
         }
      } catch (NamingException var5) {
         throw new ResourceException(var5);
      }
   }

   public boolean isAdminServer() {
      return ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServer();
   }

   public boolean isServerStarting() {
      return T3Srvr.getT3Srvr().isSvrStarting();
   }

   public void setServerFailedState(Throwable t) {
      T3Srvr.getT3Srvr().setFailedState(t, false);
   }

   public boolean isServerStarted() {
      return T3Srvr.getT3Srvr().isStarted();
   }
}
