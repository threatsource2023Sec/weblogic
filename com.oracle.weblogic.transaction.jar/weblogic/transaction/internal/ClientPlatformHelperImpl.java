package weblogic.transaction.internal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.cluster.ThreadPreferredHost;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.transaction.ChannelInterface;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;

public class ClientPlatformHelperImpl extends PlatformHelperCommon {
   private static final String THICK_SUBJECT_MANAGER = "weblogic.security.service.SubjectManagerImpl";
   private static final String IIOPCLIENT_SUBJECT_MANAGER = "weblogic.corba.client.security.SubjectManagerImpl";
   private static final String T3CLIENT_SUBJECT_MANAGER = "weblogic.security.subject.SubjectManager";
   private static boolean initialized;
   private static final AbstractSubject kernelID = getKernelIdentity();

   private static final AbstractSubject getKernelIdentity() {
      try {
         ensureSubjectManagerInitialized();
         return (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var1) {
         return null;
      }
   }

   public static void ensureSubjectManagerInitialized() {
      if (!initialized) {
         Class subjectManagerImpl;
         try {
            subjectManagerImpl = Class.forName("weblogic.security.service.SubjectManagerImpl");
         } catch (ClassNotFoundException var9) {
            try {
               subjectManagerImpl = Class.forName("weblogic.corba.client.security.SubjectManagerImpl");
            } catch (ClassNotFoundException var8) {
               try {
                  subjectManagerImpl = Class.forName("weblogic.security.subject.SubjectManager");
               } catch (ClassNotFoundException var7) {
                  if (SubjectManager.getSubjectManager() == null) {
                     throw new AssertionError(var7);
                  }

                  subjectManagerImpl = SubjectManager.getSubjectManager().getClass();
               }
            }
         }

         Method ensureInitializedMethod;
         try {
            ensureInitializedMethod = subjectManagerImpl.getMethod("ensureInitialized");
         } catch (NoSuchMethodException var6) {
            throw new AssertionError(var6);
         }

         try {
            ensureInitializedMethod.invoke((Object)null);
         } catch (IllegalAccessException var4) {
            throw new AssertionError(var4);
         } catch (InvocationTargetException var5) {
            throw new AssertionError(var5);
         }

         initialized = true;
      }
   }

   public CoordinatorDescriptor findAdminChannel(Object server, Object channel) {
      return null;
   }

   public ChannelInterface findAdminChannel(Object server) {
      return null;
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
      Hashtable env = new Hashtable();
      if (serverURL != null) {
         env.put("java.naming.provider.url", serverURL);
      }

      env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      env.put("weblogic.jndi.createUnderSharable", "true");
      return new InitialContext(env);
   }

   public boolean extendCoordinatorURL(String aCoURL) {
      return false;
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

   public boolean openPrimaryStore(boolean isRetry) {
      throw new AssertionError("It is not supported");
   }

   public void setPrimaryStore(Object store) {
   }

   public Object getPrimaryStore() {
      return null;
   }

   public Object getStore(String serverName, String arg1) throws Exception {
      throw new AssertionError("It is not supported");
   }

   public void closeStore(Object store) throws Exception {
   }

   public int getQOSAdmin() {
      return 0;
   }

   public boolean isLocalAdminChannelEnabled() {
      return false;
   }

   public String findLocalAdminChannelURL(String aCoURL) {
      return null;
   }

   public String findLocalSSLURL(String aCoURL) {
      return null;
   }

   public boolean isSSLURL(String aCoURL, boolean secureActionURLFormat) {
      return false;
   }

   public boolean isSSLURL(String aCoURL) {
      return getProtocol(aCoURL).equalsIgnoreCase("t3s") || getProtocol(aCoURL).equalsIgnoreCase("https");
   }

   public boolean isCheckpointLLR() {
      return false;
   }

   public void dumpTLOG(String tlogPath, String serverName, boolean delete) throws Exception {
   }

   public void dumpJDBCTLOG(String serverName, String rootPath, String dataSourceName, String tableRef) throws Exception {
   }

   public boolean isTransactionServiceRunning() {
      return false;
   }

   public boolean isServerRunning() {
      return false;
   }

   public String getDomainName() {
      return null;
   }

   public void doTimerLifecycleHousekeeping() {
   }

   public JTARecoveryRuntime getJTARecoveryRuntime(String serverName) {
      return null;
   }

   public void scheduleFailBack(String serverName) {
   }

   public Object createJTATransaction(TransactionImpl current) {
      return null;
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
         return tx.setCoordinatorDescriptor(id, new Channel(ep.getRemoteChannel()));
      } else {
         return false;
      }
   }

   static TransactionManagerImpl getTM() {
      return TransactionManagerImpl.getTransactionManager();
   }

   public CoordinatorDescriptorManager getCoordinatorDescriptorManager() {
      return null;
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
      return null;
   }

   public final boolean isInCluster() {
      return false;
   }

   Collection getActiveServersInCluster() {
      return null;
   }

   public CoordinatorDescriptor findServerInClusterByLocalJNDI(String resName, Collection exceptServerNames) {
      Collection servers = this.getActiveServersInCluster();
      return null;
   }

   public XAResource findXAResourceInClusterByRemoteJNDI(String resName, Collection exceptServerNames) {
      return null;
   }

   public boolean isServer() {
      return false;
   }

   public void registerRMITransactionInterceptor(Object interceptor) {
      InterceptorManager.getManager().setTransactionInterceptor((Interceptor)interceptor);
   }

   public boolean isCDSEnabled() {
      return false;
   }

   public boolean isDomainExcluded(String domainName) {
      return false;
   }

   public int getInteropMode() {
      return 0;
   }

   public ClassLoader getContextClassLoader() {
      return Thread.currentThread().getContextClassLoader();
   }

   public Object getCurrentSubject() {
      return SubjectManager.getSubjectManager().getCurrentSubject(kernelID);
   }

   public void setSSLURLFromClientInfo(ClientTransactionManagerImpl clientTransactionManagerImpl, Context initialContext) {
      String sslPort = null;
      String sslProtocol = null;
      String sslURL = null;

      try {
         Hashtable env = initialContext.getEnvironment();
         sslURL = (String)env.get("java.naming.provider.url");
         if (sslURL != null) {
            sslProtocol = getAdminProtocol(sslURL);
         }

         if (sslProtocol != null && sslURL != null && (sslProtocol.equalsIgnoreCase("t3s") || sslProtocol.equalsIgnoreCase("https"))) {
            sslPort = this.getAdminPort(sslURL);
            clientTransactionManagerImpl.setCoordinatorURL(this.constructSSLURL(sslPort, sslProtocol, clientTransactionManagerImpl.getCoordinatorURL()));
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   private String constructSSLURL(String sslPort, String sslProtocol, String aCoURL) {
      String serverName = getServerName(aCoURL);
      String domainName = this.getDomainName(aCoURL);
      String host = getHost(aCoURL);
      return serverName + "+" + host + ":" + sslPort + "+" + domainName + "+" + sslProtocol + "+";
   }

   public Map getClustersITMXAResources() throws XAException {
      throw new UnsupportedOperationException("getClustersITMXAResources call not valid on ClientPlatformHelperImpl");
   }

   public void runAction(PrivilegedExceptionAction privilegedExceptionAction, String coServerURL, String s) throws Exception {
   }

   public Object runSecureAction(Object kernelID, PrivilegedExceptionAction privilegedExceptionAction, String scServerURL, String s) throws Exception {
      return null;
   }

   public Object runSecureAction(PrivilegedExceptionAction privilegedExceptionAction, String scServerURL, String s) throws Exception {
      return null;
   }

   public XAResource refreshITMXAResourceReference(String serverName) throws XAException {
      throw new UnsupportedOperationException("refreshITMXAResourceReference call not valid on ClientPlatformHelperImpl");
   }

   public void setServerResume() throws Exception {
   }

   public void makeTransactionAware() {
   }

   public boolean executeIfIdleOnParallelXAWorkManager(Runnable runnable, String parallelXADispatchPolicy) {
      return false;
   }

   public void scheduleCheckStatusRequest(HashMap checkStatusTransactions, Runnable callback) {
   }

   public void scheduleWork(Runnable otsER) {
   }

   public void timerManagerSchedule(Object wlsTimer) {
   }

   public void checkForSSLOnlyServerRetriction(PropagationContext aTxContext, Transaction tx) {
   }

   public String getPartitionName() {
      return null;
   }

   public String getPartitionName(boolean isAdmin) {
      return null;
   }

   public ComponentInvocationContext getCurrentComponentInvocationContext() {
      return null;
   }

   public int getTimeoutPartition() {
      return -1;
   }

   public int getTimeoutPartition(String partitionName) {
      return -1;
   }

   public String[] getDeterminersPartition() {
      return null;
   }

   public String[] getDeterminersPartition(String partitionName) {
      return null;
   }

   public int getForcedShutdownTimeoutSeconds() {
      return -1;
   }

   public String getTransactionServiceHalt() {
      return null;
   }

   public void setTransactionServiceHalt(String transactionServiceHalt) {
   }

   public JTARecoveryRuntime manageCrossSiteJTARecoveryRuntime(String siteName, String serverName, boolean isRegister) {
      return null;
   }

   public void passivateTransactionRecoveryService() {
   }

   public void activateTransactionRecoveryService() throws SystemException {
   }
}
