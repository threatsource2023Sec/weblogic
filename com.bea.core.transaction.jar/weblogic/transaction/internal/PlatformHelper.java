package weblogic.transaction.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.TransactionRolledbackException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.transaction.ChannelInterface;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;

public abstract class PlatformHelper {
   private static PlatformHelper singleton;

   public static PlatformHelper getPlatformHelper() {
      if (singleton == null) {
         try {
            singleton = (PlatformHelper)Class.forName("weblogic.transaction.internal.PlatformHelperImpl").newInstance();
         } catch (Exception var7) {
            try {
               singleton = (PlatformHelper)Class.forName("weblogic.transaction.internal.ClientPlatformHelperImpl").newInstance();
            } catch (Exception var6) {
               try {
                  singleton = (PlatformHelper)Class.forName("weblogic.transaction.internal.CEPlatformHelperImpl").newInstance();
               } catch (Exception var5) {
                  try {
                     singleton = (PlatformHelper)Class.forName("weblogic.transaction.internal.OpenPlatformHelperImpl").newInstance();
                  } catch (Exception var4) {
                     throw new IllegalArgumentException(var4.toString());
                  }
               }
            }
         }
      }

      return singleton;
   }

   public static void setPlatformHelper(PlatformHelper helper) {
      singleton = helper;
   }

   public abstract CoordinatorDescriptor findAdminChannel(Object var1, Object var2);

   public abstract ChannelInterface findAdminChannel(Object var1);

   public abstract boolean isJNDIEnabled();

   public abstract String getRootName();

   public abstract Context getInitialContext(String var1) throws NamingException;

   public abstract Context getInitialContext(String var1, boolean var2) throws NamingException;

   public abstract boolean openPrimaryStore(boolean var1);

   public abstract void setPrimaryStore(Object var1);

   public abstract Object getPrimaryStore();

   public abstract boolean isCDSEnabled();

   public abstract boolean isDomainExcluded(String var1);

   public abstract String getDomainName(String var1);

   public abstract Object getStore(String var1, String var2) throws Exception;

   public abstract void closeStore(Object var1) throws Exception;

   public abstract int getQOSAdmin();

   public abstract boolean isLocalAdminChannelEnabled();

   public abstract String findLocalAdminChannelURL(String var1);

   public abstract boolean extendCoordinatorURL(String var1);

   public abstract String getAdminPort(String var1);

   public abstract boolean isCheckpointLLR();

   public abstract String findLocalSSLURL(String var1);

   public abstract boolean isSSLURL(String var1);

   public abstract boolean isSSLURL(String var1, boolean var2);

   public abstract void dumpTLOG(String var1, String var2, boolean var3) throws Exception;

   public abstract void dumpJDBCTLOG(String var1, String var2, String var3, String var4) throws Exception;

   public abstract boolean isTransactionServiceRunning();

   public abstract boolean isServerRunning();

   public String getServerState() {
      return "";
   }

   public abstract String getDomainName();

   public abstract void doTimerLifecycleHousekeeping();

   public abstract JTARecoveryRuntime getJTARecoveryRuntime(String var1);

   public abstract void scheduleFailBack(String var1);

   public abstract Object createJTATransaction(TransactionImpl var1);

   public abstract TransactionHelper getTransactionHelper();

   public abstract boolean sendRequest(Object var1);

   public abstract CoordinatorDescriptorManager getCoordinatorDescriptorManager();

   public abstract void associateThreadPreferredHost(TransactionImpl var1, TransactionManagerImpl.TxThreadProperty var2);

   public abstract Object getDisconnectMonitor();

   public abstract Object getRemoteSubject(String var1) throws IOException;

   public abstract CoordinatorDescriptor findServerInClusterByLocalJNDI(String var1, Collection var2);

   public abstract XAResource findXAResourceInClusterByRemoteJNDI(String var1, Collection var2);

   public abstract boolean isServer();

   public abstract void registerRMITransactionInterceptor(Object var1);

   public abstract int getInteropMode();

   public abstract ClassLoader getContextClassLoader();

   public abstract Object getCurrentSubject();

   public abstract void setSSLURLFromClientInfo(ClientTransactionManagerImpl var1, Context var2);

   public abstract void setServerResume() throws Exception;

   public abstract Map getClustersITMXAResources() throws XAException;

   public abstract CoordinatorFactory getCoordinatorFactory();

   public abstract void runAction(PrivilegedExceptionAction var1, String var2, String var3) throws Exception;

   public abstract Object runSecureAction(Object var1, PrivilegedExceptionAction var2, String var3, String var4) throws Exception;

   public abstract Object runSecureAction(PrivilegedExceptionAction var1, String var2, String var3) throws Exception;

   public abstract void runKernelAction(PrivilegedExceptionAction var1, String var2) throws Exception;

   public abstract CoordinatorDescriptor getOrCreateCoordinatorDescriptor(Hashtable var1, Object var2, ChannelInterface var3);

   public abstract boolean isSSLEnabled(Object var1, ChannelInterface var2);

   public abstract void initLoggingResourceRetry();

   public abstract void registerFailedLLRTransactionLoggingResourceRetry(Object var1);

   public abstract void txtrace(Object var1, TransactionImpl var2, String var3);

   public abstract void xatxtrace(Object var1, TransactionImpl var2, String var3, XAException var4);

   public abstract Object getPeerInfo(ObjectInput var1);

   public abstract boolean useNewMethod(Object var1);

   public abstract TransactionImpl getTransactionImplFromTxThreadLocal(Object var1, Thread var2);

   public abstract Object getTxThreadPropertyFromTxThreadLocal(Object var1);

   public abstract CoordinatorDescriptor findServerInDomains(String var1, Collection var2, Collection var3);

   public abstract boolean resourceCheck(String var1, String var2, String var3);

   public abstract String[] getAllServerNamesInDomain();

   public abstract String getClusterName();

   public abstract boolean isInCluster();

   public abstract XAResource refreshITMXAResourceReference(String var1) throws XAException;

   public abstract String throwable2StackTrace(Throwable var1);

   public abstract String getEOLConstant();

   public abstract ArraySet newArraySet();

   public abstract ClassLoader getContextClassLoader(ClassLoader var1, Synchronization var2);

   public abstract InputStream newUnsyncByteArrayInputStream(byte[] var1);

   public abstract UnsyncByteArrayOutputStream newUnsyncByteArrayOutputStream();

   public abstract void makeTransactionAware();

   public abstract boolean executeIfIdleOnParallelXAWorkManager(Runnable var1, String var2);

   public abstract void scheduleCheckStatusRequest(HashMap var1, Runnable var2);

   public abstract void scheduleWork(Runnable var1);

   public abstract void timerManagerSchedule(Object var1);

   public abstract void checkForSSLOnlyServerRetriction(PropagationContext var1, Transaction var2) throws TransactionRolledbackException;

   public abstract String readAbbrevString(ObjectInput var1) throws IOException;

   public abstract void writeAbbrevString0(ObjectOutput var1, String var2) throws IOException;

   public abstract int getVersion(ObjectOutput var1) throws IOException;

   public abstract String getPartitionName();

   public abstract String getPartitionName(boolean var1);

   public abstract ComponentInvocationContext getCurrentComponentInvocationContext();

   public abstract int getTimeoutPartition();

   public abstract int getTimeoutPartition(String var1);

   public abstract String[] getDeterminersPartition();

   public abstract String[] getDeterminersPartition(String var1);

   public abstract int getForcedShutdownTimeoutSeconds();

   public abstract String getTransactionServiceHalt();

   public abstract void setTransactionServiceHalt(String var1);

   public abstract JTARecoveryRuntime manageCrossSiteJTARecoveryRuntime(String var1, String var2, boolean var3);

   public abstract void passivateTransactionRecoveryService();

   public abstract void activateTransactionRecoveryService() throws SystemException;

   public String getPrimaryChannelName() {
      return null;
   }

   public String getPublicChannelName() {
      return null;
   }

   public String getSecureChannelName() {
      return null;
   }

   public String getPublicSecureChannelName() {
      return null;
   }

   public boolean usePublicAddressForRemoteDomain(CoordinatorDescriptor targetCoDesc) {
      return false;
   }

   public boolean useNonSecureAddressForDomain(CoordinatorDescriptor targetCoDesc) {
      return false;
   }

   public String getTargetChannelURL(CoordinatorDescriptor targetCoDesc) {
      return null;
   }

   public void addRecoveredServerCheckpointToMigratedTRS(String serverName, CoordinatorDescriptor cd) {
   }

   public boolean allowAutoTRSFailback(String serverName) {
      return true;
   }

   public void delayAutoTRSFailback(String serverName) {
   }

   public Object defaultTimerManagerSchedule(Object timerListener, long delay, long rate) {
      return null;
   }

   public boolean isColocated(Object remote) {
      return true;
   }

   public abstract class ArraySet implements Set {
      public abstract Object clone();
   }

   public abstract class UnsyncByteArrayOutputStream extends OutputStream {
      public abstract byte[] toByteArray();
   }

   public class ClusterStatus {
      XAResource xaResource;
      XAException xaException;

      public ClusterStatus(XAResource xaResource, XAException xaException) {
         this.xaResource = xaResource;
         this.xaException = xaException;
      }
   }
}
