package weblogic.transaction.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.RemoteException;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
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

public class OpenPlatformHelperImpl extends PlatformHelper {
   public CoordinatorDescriptor findAdminChannel(Object server, Object channel) {
      return null;
   }

   public ChannelInterface findAdminChannel(Object server) {
      return null;
   }

   public boolean isJNDIEnabled() {
      return false;
   }

   public String getRootName() {
      return null;
   }

   public Context getInitialContext(String serverURL) throws NamingException {
      return null;
   }

   public Context getInitialContext(String serverURL, boolean replicateBindings) throws NamingException {
      return null;
   }

   public boolean openPrimaryStore(boolean retry) {
      return false;
   }

   public void setPrimaryStore(Object persistentStore) {
   }

   public Object getPrimaryStore() {
      return null;
   }

   public boolean isCDSEnabled() {
      return false;
   }

   public boolean isDomainExcluded(String domainName) {
      return false;
   }

   public String getDomainName(String aCoUrl) {
      return null;
   }

   public Object getStore(String serverName, String coordinatorURL) throws Exception {
      return null;
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

   public boolean extendCoordinatorURL(String aCoURL) {
      return false;
   }

   public String getAdminPort(String aCoURL) {
      return null;
   }

   public boolean isCheckpointLLR() {
      return false;
   }

   public String findLocalSSLURL(String aCoURL) {
      return null;
   }

   public boolean isSSLURL(String aCoURL) {
      return false;
   }

   public boolean isSSLURL(String aCoURLi, boolean secureActionURLFormat) {
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
      return null;
   }

   public boolean sendRequest(Object endPoint) {
      return false;
   }

   public CoordinatorDescriptorManager getCoordinatorDescriptorManager() {
      return null;
   }

   public void associateThreadPreferredHost(TransactionImpl tx, TransactionManagerImpl.TxThreadProperty txp) {
   }

   public Object getDisconnectMonitor() {
      return null;
   }

   public Object getRemoteSubject(String url) throws IOException, RemoteException {
      return null;
   }

   public CoordinatorDescriptor findServerInClusterByLocalJNDI(String resName, Collection exceptServerNames) {
      return null;
   }

   public boolean isServer() {
      return false;
   }

   public void registerRMITransactionInterceptor(Object interceptor) {
   }

   public int getInteropMode() {
      return 0;
   }

   public ClassLoader getContextClassLoader() {
      return null;
   }

   public Object getCurrentSubject() {
      return null;
   }

   public void setSSLURLFromClientInfo(ClientTransactionManagerImpl clientTransactionManagerImpl, Context initialContext) {
   }

   public void setServerResume() throws Exception {
   }

   public Map getClustersITMXAResources() throws XAException {
      return null;
   }

   public Object runSecureAction(Object kernelID, PrivilegedExceptionAction privilegedExceptionAction, String scServerURL, String s) throws Exception {
      return null;
   }

   public void initLoggingResourceRetry() {
   }

   public void registerFailedLLRTransactionLoggingResourceRetry(Object serverTransaction) {
   }

   public TransactionImpl getTransactionImplFromTxThreadLocal(Object txThreadLocal, Thread thread) {
      return null;
   }

   public Object getTxThreadPropertyFromTxThreadLocal(Object txThreadLocal) {
      return null;
   }

   public boolean useNewMethod(Object obj) {
      return false;
   }

   public Object getPeerInfo(ObjectInput oi) {
      return null;
   }

   public void xatxtrace(Object logger, TransactionImpl tx, String msg, XAException ex) {
   }

   public void txtrace(Object logger, TransactionImpl tx, String msg) {
   }

   public CoordinatorDescriptor getOrCreateCoordinatorDescriptor(Hashtable knownServers, Object server, ChannelInterface channel) {
      return null;
   }

   public boolean isSSLEnabled(Object server, ChannelInterface channel) {
      return false;
   }

   public void runKernelAction(PrivilegedExceptionAction privilegedExceptionAction, String advertiseResource) {
   }

   public Object runSecureAction(PrivilegedExceptionAction privilegedExceptionAction, String scServerURL, String s) throws Exception {
      return null;
   }

   public void runAction(PrivilegedExceptionAction privilegedExceptionAction, String coServerURL, String s) throws Exception {
   }

   public CoordinatorFactory getCoordinatorFactory() {
      return new OpenCoordinatorFactory();
   }

   public String getClusterName() {
      return null;
   }

   public XAResource refreshITMXAResourceReference(String serverName) throws XAException {
      return null;
   }

   public String throwable2StackTrace(Throwable e) {
      return null;
   }

   public String getEOLConstant() {
      return null;
   }

   public PlatformHelper.ArraySet newArraySet() {
      return null;
   }

   public ClassLoader getContextClassLoader(ClassLoader oldCL, Synchronization s) {
      return null;
   }

   public InputStream newUnsyncByteArrayInputStream(byte[] record) {
      return null;
   }

   public PlatformHelper.UnsyncByteArrayOutputStream newUnsyncByteArrayOutputStream() {
      return null;
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

   public void checkForSSLOnlyServerRetriction(PropagationContext aTxContext, Transaction tx) throws TransactionRolledbackException {
   }

   public String readAbbrevString(ObjectInput oi) throws IOException {
      return null;
   }

   public void writeAbbrevString0(ObjectOutput oo, String s) throws IOException {
   }

   public int getVersion(ObjectOutput oo) throws IOException {
      return 0;
   }

   public String getPartitionName() {
      return null;
   }

   public String getPartitionName(boolean isAdmin) {
      return null;
   }

   public void partitionTallyCompletion(TransactionImpl tx) {
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

   public ComponentInvocationContext getCurrentComponentInvocationContext() {
      return null;
   }

   public String[] getAllServerNamesInDomain() {
      return null;
   }

   public boolean resourceCheck(String name, String serverName, String providerURL) {
      return false;
   }

   public CoordinatorDescriptor findServerInDomains(String resName, Collection exceptServerNames, Collection remoteDescriptors) {
      return null;
   }

   public XAResource findXAResourceInClusterByRemoteJNDI(String resName, Collection exceptServerNames) {
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

   public boolean isInCluster() {
      return false;
   }

   public void passivateTransactionRecoveryService() {
   }

   public void activateTransactionRecoveryService() throws SystemException {
   }
}
