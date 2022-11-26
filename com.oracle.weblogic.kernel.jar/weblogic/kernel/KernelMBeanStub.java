package weblogic.kernel;

import java.util.Map;
import weblogic.logging.Severities;
import weblogic.management.configuration.ExecuteQueueMBean;
import weblogic.management.configuration.IIOPMBean;
import weblogic.management.configuration.KernelDebugMBean;
import weblogic.management.configuration.KernelMBean;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.SSLMBean;

final class KernelMBeanStub extends MBeanStub implements KernelMBean {
   private final LogMBean log = new LogMBeanStub();
   private final SSLMBean ssl = new SSLMBeanStub();
   private final IIOPMBeanStub iiop = new IIOPMBeanStub();
   private final KernelDebugMBeanStub debug = new KernelDebugMBeanStub();
   private final ExecuteQueueMBeanStub[] queues = new ExecuteQueueMBeanStub[]{new ExecuteQueueMBeanStub()};
   private boolean reverseDNSAllowed = false;
   private String defaultProtocol = "t3";
   private String defaultSecureProtocol = "t3s";
   private String defaultAdminProtocol = "t3s";
   private int systemThreadPoolSize = 0;
   private int selfTuningThreadPoolSizeMin = 1;
   private int selfTuningThreadPoolSizeMax = 600;
   private int jmsThreadPoolSize = 0;
   private int messagingBridgeThreadPoolSize = 0;
   private boolean nativeIOEnabled = false;
   private boolean outboundEnabled = false;
   private boolean outboundPrivateKeyEnabled = false;
   private String muxerClass = "weblogic.socket.JavaSocketMuxer";
   private boolean devPollDisabled = false;
   private int socketReaders = -1;
   private int percentSocketReaders = 33;
   private long timePeriod = 0L;
   private int socketReaderTimeoutMinMillis = 500;
   private int socketReaderTimeoutMaxMillis = 1000;
   private int maxMessageSize = 10000000;
   private int connectTimeout = 0;
   private int completeMessageTimeout = 60;
   private int completeT3MessageTimeout = 60;
   private boolean socketBufferSizeAsChunkSize = false;
   private int periodLength = 60000;
   private int idlePeriodsUntilTimeout = 4;
   private int idleTimeout = 0;
   private int rjvmIdleTimeout = 0;
   private int responseTimeout = 0;
   private boolean stdoutEnabled = true;
   private int stdoutSeverityLevel = 64;
   private boolean stdoutDebugEnabled = false;
   private int stacktraceDepth = 5;
   private boolean logRemoteExceptionsEnabled = false;
   private boolean instrumentStackTraceEnabled = true;
   private boolean printStackTraceInProductionMode = false;
   private int maxOpenSockCount = -1;
   private String fmt = "standard";
   private boolean stack = true;
   private int stuckThreadTimerInterval = 600;
   private int stuckThreadMaxTime = 600;
   private boolean tracingEnabled = false;
   private int mtuSize = 1500;
   private boolean enableStubLoadingUsingCCL;
   private boolean refreshClientRuntimeDescriptor = false;
   private boolean use81StyleExecuteQueues = true;
   private int t3clientAbbrevTableSize = 255;
   private int t3serverAbbrevTableSize = 2048;
   private boolean gatheredWritesEnabled = false;
   private boolean scatteredReadsEnabled = false;
   private boolean addWorkManagerThreadsByCpuCount = false;
   private boolean useConcurrentQueueForRequestManager = false;
   int completeWriteTimeout = 60;
   private boolean useDetailedThreadName = false;
   private boolean useEnhancedPriorityQueueForRequestManager = false;
   private boolean allowShrinkingPriorityRequestQueue = true;
   private boolean useEnhancedIncrementAdvisor = false;
   boolean eagerThreadLocalCleanup;
   boolean isolatePartitionThreadLocals;

   KernelMBeanStub() {
      this.initializeFromSystemProperties("weblogic.");

      try {
         String stdoutSeverity = System.getProperty("weblogic.StdoutSeverityLevel");
         if (stdoutSeverity != null) {
            this.getLog().setStdoutSeverity(Severities.severityNumToString(Integer.parseInt(stdoutSeverity)));
         }

         boolean stdoutDebugEnabled = Boolean.getBoolean("weblogic.StdoutDebugEnabled");
         if (stdoutDebugEnabled) {
            this.getLog().setStdoutSeverity("Debug");
         }
      } catch (SecurityException var3) {
      }

   }

   public Map getValidProtocols() {
      return null;
   }

   public boolean isReverseDNSAllowed() {
      return this.reverseDNSAllowed;
   }

   public void setReverseDNSAllowed(boolean flag) {
      this.reverseDNSAllowed = flag;
   }

   public String getDefaultProtocol() {
      return this.defaultProtocol;
   }

   public void setDefaultProtocol(String protocol) {
      this.defaultProtocol = protocol;
   }

   public String getDefaultSecureProtocol() {
      return this.defaultSecureProtocol;
   }

   public void setDefaultSecureProtocol(String protocol) {
      this.defaultSecureProtocol = protocol;
   }

   public String getAdministrationProtocol() {
      return this.defaultAdminProtocol;
   }

   public void setAdministrationProtocol(String protocol) {
      this.defaultAdminProtocol = protocol;
   }

   public int getThreadPoolSize() {
      return this.queues[0].getThreadCount();
   }

   public void setThreadPoolSize(int size) {
      this.queues[0].setThreadCount(size);
   }

   public int getSystemThreadPoolSize() {
      return this.systemThreadPoolSize;
   }

   public void setSystemThreadPoolSize(int size) {
      this.systemThreadPoolSize = size;
   }

   public void setSelfTuningThreadPoolSizeMin(int size) {
      this.selfTuningThreadPoolSizeMin = size;
   }

   public int getSelfTuningThreadPoolSizeMin() {
      return this.selfTuningThreadPoolSizeMin;
   }

   public void setSelfTuningThreadPoolSizeMax(int size) {
      this.selfTuningThreadPoolSizeMax = size;
   }

   public int getSelfTuningThreadPoolSizeMax() {
      return this.selfTuningThreadPoolSizeMax;
   }

   public int getJMSThreadPoolSize() {
      return this.jmsThreadPoolSize;
   }

   public void setJMSThreadPoolSize(int size) {
      this.jmsThreadPoolSize = size;
   }

   public int getMessagingBridgeThreadPoolSize() {
      return this.messagingBridgeThreadPoolSize;
   }

   public void setMessagingBridgeThreadPoolSize(int size) {
      this.messagingBridgeThreadPoolSize = size;
   }

   public boolean isNativeIOEnabled() {
      return this.nativeIOEnabled;
   }

   public void setNativeIOEnabled(boolean enable) {
      this.nativeIOEnabled = enable;
   }

   public boolean isOutboundEnabled() {
      return this.outboundEnabled;
   }

   public void setOutboundEnabled(boolean enable) {
      this.outboundEnabled = enable;
   }

   public boolean isOutboundPrivateKeyEnabled() {
      return this.outboundPrivateKeyEnabled;
   }

   public void setOutboundPrivateKeyEnabled(boolean enable) {
      this.outboundPrivateKeyEnabled = enable;
   }

   public String getMuxerClass() {
      return this.muxerClass;
   }

   public void setMuxerClass(String name) {
      this.muxerClass = name;
   }

   public boolean isDevPollDisabled() {
      return this.devPollDisabled;
   }

   public void setDevPollDisabled(boolean disable) {
      this.devPollDisabled = disable;
   }

   public int getSocketReaders() {
      return this.socketReaders;
   }

   public void setSocketReaders(int num) {
      this.socketReaders = num;
   }

   public int getThreadPoolPercentSocketReaders() {
      return this.percentSocketReaders;
   }

   public void setThreadPoolPercentSocketReaders(int percent) {
      this.percentSocketReaders = percent;
   }

   public void setTimedOutRefIsolationTime(long timePeriod) {
      this.timePeriod = timePeriod;
   }

   public long getTimedOutRefIsolationTime() {
      return this.timePeriod;
   }

   public int getSocketReaderTimeoutMinMillis() {
      return this.socketReaderTimeoutMinMillis;
   }

   public void setSocketReaderTimeoutMinMillis(int timeout) {
      this.socketReaderTimeoutMinMillis = timeout;
   }

   public int getSocketReaderTimeoutMaxMillis() {
      return this.socketReaderTimeoutMaxMillis;
   }

   public void setSocketReaderTimeoutMaxMillis(int timeout) {
      this.socketReaderTimeoutMaxMillis = timeout;
   }

   public int getMaxMessageSize() {
      return this.maxMessageSize;
   }

   public void setMaxMessageSize(int maxsize) {
      this.maxMessageSize = maxsize;
   }

   public int getConnectTimeout() {
      return this.connectTimeout;
   }

   public void setConnectTimeout(int seconds) {
      this.connectTimeout = seconds;
   }

   public int getCompleteMessageTimeout() {
      return this.completeMessageTimeout;
   }

   public void setCompleteMessageTimeout(int seconds) {
      this.completeMessageTimeout = seconds;
   }

   public int getMaxT3MessageSize() {
      return 10000000;
   }

   public void setMaxT3MessageSize(int maxsize) {
   }

   public int getMaxHTTPMessageSize() {
      return 10000000;
   }

   public void setMaxHTTPMessageSize(int maxsize) {
   }

   public int getMaxIIOPMessageSize() {
      return this.iiop.getMaxMessageSize();
   }

   public void setMaxIIOPMessageSize(int maxsize) {
   }

   public int getMaxCOMMessageSize() {
      return 10000000;
   }

   public void setMaxCOMMessageSize(int maxsize) {
   }

   public int getCompleteT3MessageTimeout() {
      return this.completeT3MessageTimeout;
   }

   public void setCompleteT3MessageTimeout(int seconds) {
      this.completeT3MessageTimeout = seconds;
   }

   public void setSocketBufferSizeAsChunkSize(boolean flag) {
      this.socketBufferSizeAsChunkSize = flag;
   }

   public boolean isSocketBufferSizeAsChunkSize() {
      return this.socketBufferSizeAsChunkSize;
   }

   public int getCompleteHTTPMessageTimeout() {
      return 60;
   }

   public void setCompleteHTTPMessageTimeout(int seconds) {
   }

   public int getCompleteIIOPMessageTimeout() {
      return this.iiop.getCompleteMessageTimeout();
   }

   public void setCompleteIIOPMessageTimeout(int seconds) {
   }

   public int getCompleteCOMMessageTimeout() {
      return 60;
   }

   public void setCompleteCOMMessageTimeout(int seconds) {
   }

   public int getPeriodLength() {
      return this.periodLength;
   }

   public void setPeriodLength(int length) {
      this.periodLength = length;
   }

   public int getIdlePeriodsUntilTimeout() {
      return this.idlePeriodsUntilTimeout;
   }

   public void setIdlePeriodsUntilTimeout(int idlePeriods) {
      this.idlePeriodsUntilTimeout = idlePeriods;
   }

   public int getIdleConnectionTimeout() {
      return this.idleTimeout;
   }

   public void setIdleConnectionTimeout(int timeout) {
      this.idleTimeout = timeout;
   }

   public int getDefaultGIOPMinorVersion() {
      return this.iiop.getDefaultMinorVersion();
   }

   public void setDefaultGIOPMinorVersion(int minor) {
      this.iiop.setDefaultMinorVersion(minor);
   }

   public String getIIOPLocationForwardPolicy() {
      return this.iiop.getLocationForwardPolicy();
   }

   public void setIIOPLocationForwardPolicy(String lcpolicy) {
      this.iiop.setLocationForwardPolicy(lcpolicy);
   }

   public boolean getUseIIOPLocateRequest() {
      return this.iiop.getUseLocateRequest();
   }

   public void setUseIIOPLocateRequest(boolean locate) {
      this.iiop.setUseLocateRequest(locate);
   }

   public String getIIOPTxMechanism() {
      return this.iiop.getTxMechanism();
   }

   public void setIIOPTxMechanism(String txmech) {
      this.iiop.setTxMechanism(txmech);
   }

   public int getIdleIIOPConnectionTimeout() {
      return this.iiop.getIdleConnectionTimeout();
   }

   public void setIdleIIOPConnectionTimeout(int seconds) {
      this.iiop.setIdleConnectionTimeout(seconds);
   }

   public IIOPMBean getIIOP() {
      return this.iiop;
   }

   public void setIIOPMBean() {
   }

   public int getRjvmIdleTimeout() {
      return this.rjvmIdleTimeout;
   }

   public void setRjvmIdleTimeout(int timeout) {
      this.rjvmIdleTimeout = timeout;
   }

   public int getResponseTimeout() {
      return this.responseTimeout;
   }

   public void setResponseTimeout(int timeout) {
      this.responseTimeout = timeout;
   }

   public boolean isUnsafeClassLoadingEnabled() {
      return false;
   }

   public void setUnsafeClassLoadingEnabled(boolean enabled) {
   }

   public KernelDebugMBean getKernelDebug() {
      return this.debug;
   }

   public void setKernelDebug(KernelDebugMBean debug) {
   }

   public int getDGCIdlePeriodsUntilTimeout() {
      return this.getIdlePeriodsUntilTimeout();
   }

   public void setDGCIdlePeriodsUntilTimeout(int periods) {
   }

   public LogMBean getLog() {
      return this.log;
   }

   public boolean isStdoutEnabled() {
      return this.stdoutEnabled;
   }

   public void setStdoutEnabled(boolean value) {
      this.stdoutEnabled = value;
   }

   public int getStdoutSeverityLevel() {
      return this.stdoutSeverityLevel;
   }

   public void setStdoutSeverityLevel(int value) {
      this.stdoutSeverityLevel = value;
   }

   public boolean isStdoutDebugEnabled() {
      return this.stdoutDebugEnabled;
   }

   public void setStdoutDebugEnabled(boolean value) {
      this.stdoutDebugEnabled = value;
   }

   public int getStacktraceDepth() {
      return this.stacktraceDepth;
   }

   public void setStacktraceDepth(int value) {
      this.stacktraceDepth = value;
   }

   public boolean isLogRemoteExceptionsEnabled() {
      return false;
   }

   public void setLogRemoteExceptionsEnabled(boolean value) {
      this.logRemoteExceptionsEnabled = value;
   }

   public boolean isInstrumentStackTraceEnabled() {
      return this.instrumentStackTraceEnabled;
   }

   public void setInstrumentStackTraceEnabled(boolean enable) {
      this.instrumentStackTraceEnabled = enable;
   }

   public boolean getPrintStackTraceInProduction() {
      return this.printStackTraceInProductionMode;
   }

   public void setPrintStackTraceInProduction(boolean show) {
      this.printStackTraceInProductionMode = show;
   }

   public SSLMBean getSSL() {
      return this.ssl;
   }

   public void setSSLMBean() {
   }

   public ExecuteQueueMBean[] getExecuteQueues() {
      return this.queues;
   }

   public void setExecuteQueues(ExecuteQueueMBean[] queues) {
   }

   public int getMaxOpenSockCount() {
      return this.maxOpenSockCount;
   }

   public void setMaxOpenSockCount(int sockCount) {
      this.maxOpenSockCount = sockCount;
   }

   public String getStdoutFormat() {
      return this.fmt;
   }

   public void setStdoutFormat(String format) {
      if (format.equals("standard") || format.equals("noid")) {
         this.fmt = format;
      }

   }

   public boolean isStdoutLogStack() {
      return this.stack;
   }

   public void setStdoutLogStack(boolean stackValue) {
      this.stack = stackValue;
   }

   public int getStuckThreadTimerInterval() {
      return this.stuckThreadTimerInterval;
   }

   public void setStuckThreadTimerInterval(int value) {
      this.stuckThreadTimerInterval = value;
   }

   public int getStuckThreadMaxTime() {
      return this.stuckThreadMaxTime;
   }

   public void setStuckThreadMaxTime(int value) {
      this.stuckThreadMaxTime = value;
   }

   public boolean getTracingEnabled() {
      return this.tracingEnabled;
   }

   public void setTracingEnabled(boolean value) {
      this.tracingEnabled = value;
   }

   public int getMTUSize() {
      return this.mtuSize;
   }

   public void setMTUSize(int mtuSize) {
      this.mtuSize = mtuSize;
   }

   public void setLoadStubUsingContextClassLoader(boolean enable) {
      this.enableStubLoadingUsingCCL = enable;
   }

   public boolean getLoadStubUsingContextClassLoader() {
      return this.enableStubLoadingUsingCCL;
   }

   public void setRefreshClientRuntimeDescriptor(boolean enable) {
      this.refreshClientRuntimeDescriptor = enable;
   }

   public boolean getRefreshClientRuntimeDescriptor() {
      return this.refreshClientRuntimeDescriptor;
   }

   public void setUse81StyleExecuteQueues(boolean enable) {
      this.use81StyleExecuteQueues = enable;
   }

   public boolean getUse81StyleExecuteQueues() {
      return this.use81StyleExecuteQueues;
   }

   public void setT3ClientAbbrevTableSize(int size) {
      this.t3clientAbbrevTableSize = size;
   }

   public int getT3ClientAbbrevTableSize() {
      return this.t3clientAbbrevTableSize;
   }

   public void setT3ServerAbbrevTableSize(int size) {
      this.t3serverAbbrevTableSize = size;
   }

   public int getT3ServerAbbrevTableSize() {
      return this.t3serverAbbrevTableSize;
   }

   public ExecuteQueueMBean createExecuteQueue(String name) {
      return null;
   }

   public ExecuteQueueMBean lookupExecuteQueue(String name) {
      return null;
   }

   public void destroyExecuteQueue(ExecuteQueueMBean exec) {
   }

   public boolean removeExecuteQueue(ExecuteQueueMBean queue) {
      return true;
   }

   public boolean addExecuteQueue(ExecuteQueueMBean queue) {
      return true;
   }

   public boolean isGatheredWritesEnabled() {
      return this.gatheredWritesEnabled;
   }

   public void setGatheredWritesEnabled(boolean enable) {
      this.gatheredWritesEnabled = enable;
   }

   public boolean isScatteredReadsEnabled() {
      return this.scatteredReadsEnabled;
   }

   public void setScatteredReadsEnabled(boolean enable) {
      this.scatteredReadsEnabled = enable;
   }

   public boolean isAddWorkManagerThreadsByCpuCount() {
      return this.addWorkManagerThreadsByCpuCount;
   }

   public void setAddWorkManagerThreadsByCpuCount(boolean flag) {
      this.addWorkManagerThreadsByCpuCount = flag;
   }

   public boolean isUseConcurrentQueueForRequestManager() {
      return this.useConcurrentQueueForRequestManager;
   }

   public void setUseConcurrentQueueForRequestManager(boolean flag) {
      this.useConcurrentQueueForRequestManager = flag;
   }

   public int getCompleteWriteTimeout() {
      return this.completeWriteTimeout;
   }

   public void setCompleteWriteTimeout(int seconds) {
      this.completeWriteTimeout = seconds;
   }

   public void setUseDetailedThreadName(boolean flag) {
      this.useDetailedThreadName = flag;
   }

   public boolean isUseDetailedThreadName() {
      return this.useDetailedThreadName;
   }

   public void setUseEnhancedPriorityQueueForRequestManager(boolean flag) {
      this.useEnhancedPriorityQueueForRequestManager = flag;
   }

   public boolean isUseEnhancedPriorityQueueForRequestManager() {
      return this.useEnhancedPriorityQueueForRequestManager;
   }

   public void setAllowShrinkingPriorityRequestQueue(boolean flag) {
      this.allowShrinkingPriorityRequestQueue = flag;
   }

   public boolean isAllowShrinkingPriorityRequestQueue() {
      return this.allowShrinkingPriorityRequestQueue;
   }

   public void setUseEnhancedIncrementAdvisor(boolean flag) {
      this.useEnhancedIncrementAdvisor = flag;
   }

   public boolean isUseEnhancedIncrementAdvisor() {
      return this.useEnhancedIncrementAdvisor;
   }

   public boolean isEagerThreadLocalCleanup() {
      return this.eagerThreadLocalCleanup;
   }

   public void setEagerThreadLocalCleanup(boolean flag) {
      this.eagerThreadLocalCleanup = flag;
   }

   public boolean isIsolatePartitionThreadLocals() {
      return this.isolatePartitionThreadLocals;
   }

   public void setIsolatePartitionThreadLocals(boolean flag) {
      this.isolatePartitionThreadLocals = flag;
   }
}
