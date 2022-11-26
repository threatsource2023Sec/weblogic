package weblogic.diagnostics.context;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import oracle.dms.context.internal.wls.WLSContextFamily;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.JFRDebug;
import weblogic.diagnostics.flightrecorder.event.ECIDMappingEvent;
import weblogic.diagnostics.flightrecorder.event.ThrottleInformationEventInfo;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.diagnostics.instrumentation.gathering.DataGatheringManager;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.PropertyHelper;
import weblogic.workarea.NoWorkContextException;
import weblogic.workarea.PropertyReadOnlyException;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;
import weblogic.workarea.spi.AfterCopyContextsListener;
import weblogic.workarea.spi.AfterReceiveRequestListener;
import weblogic.workarea.spi.AfterSendRequestListener;
import weblogic.workarea.spi.BeforeSendRequestListener;

public final class CorrelationManager implements TimerListener, PropertyChangeListener, AfterReceiveRequestListener, AfterSendRequestListener, BeforeSendRequestListener, AfterCopyContextsListener {
   private static final DebugLogger DEBUG_LOGGER;
   private static final CorrelationManager singleton;
   private static final AuthenticatedSubject kernelId;
   private CorrelationCallback dmsCallback = null;
   public static final boolean DMS_HTTP_HEADER_DISABLED;
   public static final String DMS_ECID_HTTP_KEY = "ECID-Context";
   private static final String DMS_ECID_HTTP_KEY_EQL = "ECID-Context=";
   private static final int DMS_ECID_HTTP_KEY_LEN;
   private static final String ASCII = "ascii";
   private boolean enabled;
   private boolean compatibilityMode;
   private volatile boolean initialized;
   private static WLSCorrelationFactoryImpl correlationFactory;
   private ContextWrapperCleaner contextWrapperCleaner;
   private static final String THROTTLE_RATE_REFRESH_PERIOD_PROPERTY = "weblogic.diagnostics.context.throttlerate_refresh_period";
   private static final String THROTTLING_MAX_EVENT_PER_SECOND_GOAL_PROPERTY = "weblogic.diagnostics.context.throttle_max_event_per_second_goal";
   private static final String THROTTLING_MAX_SELECTED_REQUESTS_PER_SECOND_GOAL_PROPERTY = "weblogic.diagnostics.context.throttle_max_selected_requests_per_second_goal";
   private static final String CONTEXT_WRAPPER_CLEANUP_PERIOD_PROPERTY = "weblogic.diagnostics.context.context_wrapper_cleanup_period";
   private static final int THROTTLE_RATE_REFRESH_PERIOD;
   private static final int THROTTLE_RATE_REFRESH_SECS;
   private static final int THROTTLING_MAX_EVENT_PER_SECOND_GOAL;
   private static final int THROTTLING_MAX_SELECTED_REQUESTS_PER_SECOND_GOAL;
   private static final int CONTEXT_WRAPPER_CLEANUP_PERIOD;
   private static AtomicInteger requestSeqId;
   private static int ctxThrottleRate;
   private static AtomicInteger ctxJFREventsInTimerWindow;
   private static long lastRefreshTime;
   private TimerManager timerManager;
   private static AtomicLong runningEventCount;
   private static long previousEventCount;
   private static volatile int requestTotalInPeriod;
   private static volatile int requestSelectedInPeriod;
   private static int previousRequestTotal;
   private static int previousSelectedTotal;
   private static int periodsSinceThrottleChanged;
   private static boolean needToTrackNonJFRWork;
   private static Map CorrelationWrappers;
   private ThrottleInfoImpl throttleInfo = new ThrottleInfoImpl();
   private static final int JMS_PROP_MODES = 48;
   private static final int SOAP_OR_JMS_PROP_MODES = 112;
   private static final int SHADOW_CLEANUP_PROP_MODES = 124;
   private static AuditableThreadLocal localCorrelation;
   static final long serialVersionUID = -7831459399518599495L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.diagnostics.context.CorrelationManager");
   static final DelegatingMonitor _WLDF$INST_FLD_Throttleinfo_Diagnostic_Volume_After_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public static CorrelationManager getCorrelationManager() {
      if (!KernelStatus.isServer()) {
         return singleton;
      } else {
         return !SecurityServiceManager.isKernelIdentity(SecurityServiceManager.getCurrentSubject(kernelId)) ? null : singleton;
      }
   }

   static CorrelationManager getCorrelationManagerInternal() {
      return singleton;
   }

   private static boolean determineDMSHttpDisabled() {
      if (PropertyHelper.getBoolean("weblogic.diagnostics.context.DisableDMSHTTPAlways")) {
         return true;
      } else if (PropertyHelper.getBoolean("weblogic.diagnostics.context.DisableDMSHTTPStandalone")) {
         try {
            Class dmsClass = Class.forName("oracle.dms.wls.DMSStartup");
            return false;
         } catch (Exception var1) {
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean isCorrelationEnabled() {
      return singleton == null ? false : singleton.isEnabled();
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enable) {
      this.enabled = enable;
      CorrelationFactory.setEnabled(enable);
   }

   public boolean isCompatibilityModeEnabled() {
      return this.compatibilityMode;
   }

   public int getPropagationMode() {
      return CorrelationFactory.getPropagationMode();
   }

   public void setPropagationMode(int propagationMode) {
      CorrelationFactory.setPropagationMode(propagationMode);
   }

   public static void initialize() {
      if (singleton != null) {
         singleton.initializeInternal();
      }

   }

   private void initializeInternal() {
      if (!KernelStatus.isServer()) {
         throw new UnsupportedOperationException("Operation not supported on client");
      } else if (!this.initialized) {
         RID.setJFRDebugContributor(CorrelationDebugContributor.getInstance());
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         ServerMBean server = runtimeAccess.getServer();
         DomainMBean domainMBean = runtimeAccess.getDomain();
         if (domainMBean != null) {
            domainMBean.addPropertyChangeListener(this);
         }

         WLDFServerDiagnosticMBean diagnosticMBean = server != null ? server.getServerDiagnosticConfig() : null;
         if (diagnosticMBean != null) {
            this.setEnabled(diagnosticMBean.isDiagnosticContextEnabled());
            diagnosticMBean.addPropertyChangeListener(this);
            correlationFactory = new WLSCorrelationFactoryImpl(this);
            CorrelationFactory.setFactory(correlationFactory);
            TimerManagerFactory timerManagerFactory = TimerManagerFactory.getTimerManagerFactory();
            this.timerManager = timerManagerFactory.getDefaultTimerManager();
            this.timerManager.scheduleAtFixedRate(this, 0L, (long)THROTTLE_RATE_REFRESH_PERIOD);
            this.contextWrapperCleaner = new ContextWrapperCleaner();
            this.timerManager.scheduleAtFixedRate(this.contextWrapperCleaner, 0L, (long)CONTEXT_WRAPPER_CLEANUP_PERIOD);
            this.setupWorkAreaCallbacks(true, domainMBean);
            WLSContextFamily.initialize(correlationFactory);
            CorrelationHttpRequestHeaderContributor.initialize();
            this.initialized = true;
         }
      }
   }

   private void setupWorkAreaCallbacks(final boolean initializing, DomainMBean domainMBean) {
      final CorrelationManager thisManager = this;
      boolean newMode = domainMBean.isDiagnosticContextCompatibilityModeEnabled();
      if (initializing) {
         try {
            SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  WorkContextHelper helper = WorkContextHelper.getWorkContextHelper();
                  if (initializing) {
                     helper.registerAfterReceiveRequestListener(thisManager);
                     helper.registerAfterCopyContextsListener(thisManager);
                     helper.registerBeforeSendRequestListener(thisManager);
                     helper.registerAfterSendRequestListener(thisManager);
                  }

                  return null;
               }
            });
         } catch (Throwable var6) {
            throw new RuntimeException("Failure initializing WorkArea for DiagnosticContext", var6);
         }
      }

      this.compatibilityMode = newMode;
   }

   public void propertyChange(PropertyChangeEvent evt) {
      this.attributesChanged(evt.getSource());
   }

   private void attributesChanged(Object source) {
      if (source instanceof WLDFServerDiagnosticMBean) {
         WLDFServerDiagnosticMBean diagnosticMBean = (WLDFServerDiagnosticMBean)source;
         this.setEnabled(diagnosticMBean.isDiagnosticContextEnabled());
      } else if (source instanceof DomainMBean) {
         this.setupWorkAreaCallbacks(false, (DomainMBean)source);
      }

   }

   public void cleanupContextWrappers() {
      if (this.contextWrapperCleaner != null) {
         this.contextWrapperCleaner.cleanupContextWrappers();
         this.contextWrapperCleaner.checkForExtremelyLongRIDs();
      }

   }

   public int getContextWrapperSize() {
      return CorrelationWrappers.size();
   }

   public static boolean isJFRThrottled() {
      int ctxThrottleRateLocal = ctxThrottleRate;
      if (localCorrelation == null) {
         return true;
      } else {
         CorrelationWrapper wrapper;
         if (ctxThrottleRateLocal <= 1) {
            if (needToTrackNonJFRWork) {
               wrapper = (CorrelationWrapper)localCorrelation.get();
               wrapper.jfrGenerationRequested();
            }

            return true;
         } else {
            wrapper = (CorrelationWrapper)localCorrelation.get();
            wrapper.jfrGenerationRequested();
            return wrapper.isSelected();
         }
      }
   }

   public static String getECID(long threadId) {
      WeakReference ref = (WeakReference)CorrelationWrappers.get(threadId);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Found CorrelationWrapper ref = " + ref + " for thread id = " + threadId);
      }

      if (ref != null) {
         CorrelationWrapper wrapper = (CorrelationWrapper)ref.get();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Found CorrelationWrapper wrapper = " + wrapper + " for thread id = " + threadId);
         }

         if (wrapper != null) {
            Correlation ctx = wrapper.getCorrelation();
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("CorrelationWrapper context = " + ctx);
            }

            if (ctx != null) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Correlation id = " + ctx.getECID() + " for thread id = " + threadId);
               }

               return ctx.getECID();
            }
         }
      }

      return "";
   }

   public void timerExpired(Timer timer) {
      long currentTime = System.currentTimeMillis();
      long periodDuration = currentTime - lastRefreshTime;
      int requestsSeenLastPeriod = requestTotalInPeriod;
      int requestsSelectedLastPeriod = requestSelectedInPeriod;
      int eventsGeneratedLastPeriod = ctxJFREventsInTimerWindow.get();
      if (DEBUG_LOGGER.isDebugEnabled()) {
         long runningEventCountLocal = runningEventCount.get();
         DEBUG_LOGGER.debug("Event counts period (running, period) = " + (runningEventCountLocal - previousEventCount) + ", " + eventsGeneratedLastPeriod);
         previousEventCount = runningEventCountLocal;
      }

      int throttlingRateNew = 1;
      float requestsPerSec = 0.0F;
      float averageEventsPerRequest = 0.0F;
      float maxRequestsToBeSelectedPerSecond = 0.0F;
      float projectedSelectedRequestsPerSec = 0.0F;
      requestsPerSec = (float)(requestsSeenLastPeriod / THROTTLE_RATE_REFRESH_SECS);
      if (requestsSelectedLastPeriod > 0 && eventsGeneratedLastPeriod > 0) {
         averageEventsPerRequest = (float)eventsGeneratedLastPeriod / (float)requestsSelectedLastPeriod;
         if ((double)averageEventsPerRequest < 0.5) {
            needToTrackNonJFRWork = true;
         }

         maxRequestsToBeSelectedPerSecond = (float)THROTTLING_MAX_EVENT_PER_SECOND_GOAL / averageEventsPerRequest;
         throttlingRateNew = Math.round(requestsPerSec / maxRequestsToBeSelectedPerSecond);
      }

      if (throttlingRateNew < 1) {
         throttlingRateNew = 1;
      }

      projectedSelectedRequestsPerSec = requestsPerSec / (float)throttlingRateNew;
      if (projectedSelectedRequestsPerSec > (float)THROTTLING_MAX_SELECTED_REQUESTS_PER_SECOND_GOAL) {
         throttlingRateNew = Math.round(requestsPerSec / (float)THROTTLING_MAX_SELECTED_REQUESTS_PER_SECOND_GOAL);
      }

      if (throttlingRateNew < 1) {
         throttlingRateNew = 1;
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug(" Last period Elapsed time: " + periodDuration + "   requests/sec: " + requestsPerSec + "   selected reqs: " + requestsSelectedLastPeriod + "   total reqs: " + requestsSeenLastPeriod + "   events: " + eventsGeneratedLastPeriod + "   events per request: " + averageEventsPerRequest + "   projected selected requests: " + projectedSelectedRequestsPerSec + "   old throttle rate: " + ctxThrottleRate + "   new throttle rate: " + throttlingRateNew);
      }

      synchronized(this.throttleInfo) {
         this.throttleInfo.setLastPeriodDuration(periodDuration);
         this.throttleInfo.setRequestsSelectedLastPeriod(requestsSelectedLastPeriod);
         this.throttleInfo.setRequestsSeenLastPeriod(requestsSeenLastPeriod);
         this.throttleInfo.setEventsGeneratedLastPeriod(eventsGeneratedLastPeriod);
         this.throttleInfo.setAverageEventsPerRequestLastPeriod(averageEventsPerRequest);
         this.throttleInfo.setProjectedSelectedRequestsPerSecBeforeCapCheck(Math.round(projectedSelectedRequestsPerSec));
         this.throttleInfo.setPreviousThrottleRate(ctxThrottleRate);
         this.throttleInfo.setCurrentThrottleRate(throttlingRateNew);
         this.throttleInfo.setPeriodsSinceLastThrottleChange(periodsSinceThrottleChanged);
      }

      if (ctxThrottleRate != throttlingRateNew) {
         this.triggerThrottleInformationEvent();
         periodsSinceThrottleChanged = 1;
      } else {
         ++periodsSinceThrottleChanged;
      }

      ctxThrottleRate = throttlingRateNew;
      previousRequestTotal = requestsSeenLastPeriod;
      previousSelectedTotal = requestsSelectedLastPeriod;
      requestSelectedInPeriod = 0;
      requestTotalInPeriod = 0;
      lastRefreshTime = currentTime;
      ctxJFREventsInTimerWindow.set(0);
   }

   public static void incrementJFREventCounter() {
      ctxJFREventsInTimerWindow.incrementAndGet();
      runningEventCount.incrementAndGet();
   }

   public ThrottleInformationEventInfo triggerThrottleInformationEvent() {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
      }

      ThrottleInfoImpl var10000;
      try {
         synchronized(this.throttleInfo) {
            var10000 = this.throttleInfo;
         }
      } catch (Throwable var7) {
         if (var3 != null) {
            var3.th = var7;
            var3.ret = null;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         throw var7;
      }

      if (var3 != null) {
         var3.ret = var10000;
         InstrumentationSupport.createDynamicJoinPoint(var3);
         InstrumentationSupport.process(var3);
      }

      return var10000;
   }

   public void requestReceived(WorkContextMap map) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         JFRDebug.generateDebugEvent("CorrelationManager", "requestReceived()", (Throwable)null, CorrelationImpl.getDCDebugContributor("", ""));
         DEBUG_LOGGER.debug("requestReceived called");
      }

      if (DiagnosticContextImpl.incomingDCImplsNotSeen) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("requestReceived no DCImpls seen");
         }

      } else {
         DiagnosticContextImpl dcImpl = this.removeDCFromMap(map);
         if (dcImpl == null) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("requestReceived no DCImpl found");
            }

         } else if (!dcImpl.isUnmarshalled()) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Rogue DiagnosticContext seen in requestReceived: " + dcImpl.getContextId());
            }

         } else {
            CorrelationImpl correlation = correlationFactory.findCorrelation(map, false);
            if (correlation != null && correlation.isUnmarshalled()) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("requestReceived DC and EC both found");
               }

               correlation.setLegacyDCID(dcImpl.getLegacyDCID());
               correlation.setDyeVector(dcImpl.getDyeVector());
               correlation.setPayload(dcImpl.getPayload());
               correlationFactory.updateReconciledCorrelationOnWrapper(correlation);
            } else {
               CorrelationImpl oldCorrelation = correlation;
               correlation = new CorrelationImpl(dcImpl);
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("requestReceived create Correlation from DCImpl: " + dcImpl.getContextId());
               }

               correlationFactory.setCorrelation(correlation, map, oldCorrelation, true);
            }

         }
      }
   }

   public void sendRequest(int propagationMode, WorkContextMap map) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         String propModeStr = modeToStr(propagationMode);
         JFRDebug.generateDebugEvent("CorrelationManager", "sendRequest(" + propModeStr + ")", (Throwable)null, CorrelationImpl.getDCDebugContributor("", ""));
         DEBUG_LOGGER.debug("sendRequest: " + propModeStr);
      }

      int remotePropagationMode = CorrelationFactory.getRemotePropagationMode();
      if ((propagationMode & remotePropagationMode) == 0) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("propagation mode doesn't match, not propagating");
         }

      } else {
         boolean isJMS = (48 & propagationMode) != 0;
         boolean isSOAP = 64 == propagationMode;
         boolean shadowDCRequired = this.compatibilityMode || isJMS;
         CorrelationImpl correlation = correlationFactory.findCorrelation(map, true);
         if (correlation != null) {
            if (!correlation.getInheritable()) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Correlation is not inheritable not sending ShadowDC");
               }

               return;
            }

            if (shadowDCRequired) {
               this.setDCInMap(new DiagnosticContextImpl(correlation), map);
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("sendRequest created ShadowDC");
               }
            }

            if (isJMS || isSOAP) {
               CorrelationImpl.blockPropagationOnceBeforeSend(map);
            }
         } else if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("no correlation found in Map, not shadowing with DC");
         }

      }
   }

   public void requestSent(int propagationMode, WorkContextMap map) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         String propModeStr = modeToStr(propagationMode);
         JFRDebug.generateDebugEvent("CorrelationManager", "requestSent(" + propModeStr + ")", (Throwable)null, CorrelationImpl.getDCDebugContributor("", ""));
         DEBUG_LOGGER.debug("requestSent: " + propModeStr);
      }

      if ((propagationMode & 112) != 0) {
         CorrelationWrapper wrapper = (CorrelationWrapper)localCorrelation.get();
         Correlation correlation = wrapper.getCorrelation();
         if (correlation != null && correlation.getInheritable()) {
            CorrelationImpl.restorePropagationAfterSend(map);
         }
      }

      if ((propagationMode & 124) != 0) {
         this.removeDCFromMap(map);
      }
   }

   public void contextsCopied(int propagationMode, WorkContextMap sourceMap, WorkContextMap copiedMap) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         String propModeStr = modeToStr(propagationMode);
         JFRDebug.generateDebugEvent("CorrelationManager", "contextsCopied(" + propModeStr + ")", (Throwable)null, CorrelationImpl.getDCDebugContributor("", ""));
         DEBUG_LOGGER.debug("contextsCopied: " + propModeStr);
      }

      if (copiedMap != null) {
         if ((propagationMode & 48) == 0) {
            if (RID.deepestRIDSeen < RID.MAX_RID_LENGTH) {
               CorrelationImpl.produceChildCorrelationIfNeeded(copiedMap);
            }
         }
      }
   }

   public Correlation newCorrelation(String ecid, RID rid, long dye) {
      return new CorrelationImpl(ecid, rid, dye);
   }

   public Correlation newCorrelation(String ecid, int[] ridComponents, int kidCount, Map values, long dyeVector, boolean inheritable) {
      return new CorrelationImpl(ecid, ridComponents, kidCount, values, dyeVector, inheritable);
   }

   public void activateCorrelation(Correlation correlation) {
      if (correlation != null && this.initialized) {
         CorrelationImpl corrImpl = (CorrelationImpl)correlation;
         corrImpl.treatAsIfUnMarshalled();
         correlationFactory.setCorrelation(correlation);
      }
   }

   private void setDCInMap(DiagnosticContextImpl dc, WorkContextMap map) {
      try {
         map.put("weblogic.diagnostics.DiagnosticContext", dc, CorrelationFactory.getPropagationMode());
      } catch (PropertyReadOnlyException var4) {
      }

   }

   private DiagnosticContextImpl removeDCFromMap(WorkContextMap map) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Removing legacy DCImpl from map");
      }

      try {
         return (DiagnosticContextImpl)map.remove("weblogic.diagnostics.DiagnosticContext");
      } catch (NoWorkContextException var3) {
      } catch (PropertyReadOnlyException var4) {
      }

      return null;
   }

   void setDMSCorrelationCallback(CorrelationCallback dmsCallback) {
      this.dmsCallback = dmsCallback;
   }

   public static boolean unwrapHTTP(String wrapString) {
      if (wrapString != null && wrapString.length() != 0) {
         Correlation correlation = null;

         try {
            correlation = WrapUtils.unwrap(wrapString);
            if (correlation != null) {
               final Correlation runCtx = correlation;

               try {
                  SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
                     public Object run() throws Exception {
                        WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
                        map.put("oracle.dms.context.internal.wls.WLSContextFamily", CorrelationImpl.getWorkContext(runCtx), runCtx.getInheritable() ? CorrelationFactory.getPropagationMode() : CorrelationFactory.getNonInheritablePropagationMode());
                        return null;
                     }
                  });
               } catch (Throwable var4) {
               }

               correlationFactory.correlationPropagatedIn(correlation);
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  JFRDebug.generateDebugEvent("CorrelationManager", "unwrapHTTP success: " + wrapString, (Throwable)null, CorrelationDebugContributor.getInstance(correlation));
               }
            } else if (DEBUG_LOGGER.isDebugEnabled()) {
               JFRDebug.generateDebugEvent("CorrelationManager", "unwrapHTTP nothing unwrapped: " + wrapString, (Throwable)null, CorrelationDebugContributor.getInstance(correlation));
            }
         } catch (Throwable var5) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("unwrapHTTP failed: " + wrapString, var5);
               JFRDebug.generateDebugEvent("CorrelationManager", "unwrapHTTP failed: " + wrapString, var5, CorrelationDebugContributor.getInstance(correlation));
            }
         }

         return correlation != null;
      } else {
         return false;
      }
   }

   public static String getWrappedContextFromQueryString(String queryString) {
      if (queryString == null) {
         return null;
      } else {
         String parameterValue = null;
         int startOfParamNameWithEquals = queryString.indexOf("ECID-Context=");
         if (startOfParamNameWithEquals != -1) {
            int startOfNextParamName = queryString.indexOf(38, startOfParamNameWithEquals);
            int startOfParamValue = startOfParamNameWithEquals + DMS_ECID_HTTP_KEY_LEN + 1;
            if (startOfNextParamName == -1) {
               parameterValue = queryString.substring(startOfParamValue);
            } else {
               parameterValue = queryString.substring(startOfParamValue, startOfNextParamName);
            }
         }

         if (parameterValue != null) {
            try {
               return URLDecoder.decode(parameterValue, "ascii");
            } catch (UnsupportedEncodingException var6) {
            }
         }

         return null;
      }
   }

   private static AuthenticatedSubject getKernelIdIfOnServer() {
      return !KernelStatus.isServer() ? null : (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   void correlationPropagatedIn(Correlation ctx) {
      correlationFactory.correlationPropagatedIn(ctx);
   }

   private static final String modeToStr(int propagationMode) {
      if (propagationMode == 1) {
         return "LOCAL";
      } else if (propagationMode == 2) {
         return "WORK";
      } else if (propagationMode == 4) {
         return "RMI";
      } else if (propagationMode == 8) {
         return "TRANSACTION";
      } else if (propagationMode == 16) {
         return "JMS_QUEUE";
      } else if (propagationMode == 32) {
         return "JMS_TOPIC";
      } else if (propagationMode == 64) {
         return "SOAP";
      } else if (propagationMode == 128) {
         return "MIME_HEADER";
      } else if (propagationMode == 256) {
         return "ONEWAY";
      } else {
         return propagationMode == 212 ? "GLOBAL" : "UNKNOWN-" + propagationMode;
      }
   }

   static {
      _WLDF$INST_FLD_Throttleinfo_Diagnostic_Volume_After_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Throttleinfo_Diagnostic_Volume_After_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "CorrelationManager.java", "weblogic.diagnostics.context.CorrelationManager", "triggerThrottleInformationEvent", "()Lweblogic/diagnostics/flightrecorder/event/ThrottleInformationEventInfo;", 1124, "", "", "", InstrumentationSupport.makeMap(new String[]{"Throttleinfo_Diagnostic_Volume_After_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("return", (String)null, false, true), (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Throttleinfo_Diagnostic_Volume_After_Low};
      DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticContext");
      singleton = new CorrelationManager();
      kernelId = getKernelIdIfOnServer();
      DMS_HTTP_HEADER_DISABLED = determineDMSHttpDisabled();
      DMS_ECID_HTTP_KEY_LEN = "ECID-Context".length();
      THROTTLE_RATE_REFRESH_PERIOD = PropertyHelper.getInteger("weblogic.diagnostics.context.throttlerate_refresh_period", 2000);
      THROTTLE_RATE_REFRESH_SECS = THROTTLE_RATE_REFRESH_PERIOD / 1000;
      THROTTLING_MAX_EVENT_PER_SECOND_GOAL = PropertyHelper.getInteger("weblogic.diagnostics.context.throttle_max_event_per_second_goal", 800);
      THROTTLING_MAX_SELECTED_REQUESTS_PER_SECOND_GOAL = PropertyHelper.getInteger("weblogic.diagnostics.context.throttle_max_selected_requests_per_second_goal", 128);
      CONTEXT_WRAPPER_CLEANUP_PERIOD = PropertyHelper.getInteger("weblogic.diagnostics.context.context_wrapper_cleanup_period", 600000);
      requestSeqId = new AtomicInteger();
      ctxThrottleRate = 1;
      ctxJFREventsInTimerWindow = new AtomicInteger(0);
      lastRefreshTime = System.currentTimeMillis();
      runningEventCount = new AtomicLong(0L);
      previousEventCount = 0L;
      requestTotalInPeriod = 0;
      requestSelectedInPeriod = 0;
      previousRequestTotal = 0;
      previousSelectedTotal = 0;
      periodsSinceThrottleChanged = 1;
      needToTrackNonJFRWork = false;
      CorrelationWrappers = new ConcurrentHashMap();
      localCorrelation = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue() {
         protected Object initialValue() {
            if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
               CorrelationManager.DEBUG_LOGGER.debug("Invoked DCM.initialValue() for thread id=" + Thread.currentThread().getId() + ", name=" + Thread.currentThread().getName(), new Exception());
            }

            return this.initialValue((CorrelationWrapper)null);
         }

         private Object initialValue(CorrelationWrapper wrapper) {
            boolean newWrapper = false;
            if (wrapper == null) {
               wrapper = new CorrelationWrapper();
               newWrapper = true;
            }

            long id = Thread.currentThread().getId();
            if (!CorrelationManager.CorrelationWrappers.containsKey(id)) {
               if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                  CorrelationManager.DEBUG_LOGGER.debug("Populating DC Wrapper map for thread id=" + Thread.currentThread().getId() + ", name=" + Thread.currentThread().getName() + ", wrapper=" + wrapper);
               }

               CorrelationManager.CorrelationWrappers.put(id, new WeakReference(wrapper));
               wrapper.clearMapCheckNeeded();
            } else if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled() & newWrapper) {
               CorrelationManager.DEBUG_LOGGER.debug("DC Wrapper already exists for thread id=" + Thread.currentThread().getId() + ", name=" + Thread.currentThread().getName() + ", Wrapper for child thread=" + wrapper, new Exception());
            }

            wrapper.reset();
            return wrapper;
         }

         protected Object resetValue(Object currentValue) {
            return this.initialValue((CorrelationWrapper)currentValue);
         }
      });
   }

   private static class ContextWrapperCleaner implements TimerListener {
      private ThreadGroup rootThreadGroup;
      HashSet activeThreadIds;

      public ContextWrapperCleaner() {
         ThreadGroup parent;
         for(this.rootThreadGroup = Thread.currentThread().getThreadGroup(); (parent = this.rootThreadGroup.getParent()) != null; this.rootThreadGroup = parent) {
         }

      }

      public void timerExpired(Timer timer) {
         this.cleanupContextWrappers();
         this.checkForExtremelyLongRIDs();
      }

      private void checkForExtremelyLongRIDs() {
         if (RID.extremeRIDGrowthSeen && RID.deepestRIDSeen > RID.previouslyReportedDeepestRID) {
            RID.previouslyReportedDeepestRID = RID.deepestRIDSeen;
            DiagnosticsLogger.logLongRIDValueCreated(RID.previouslyReportedDeepestRID);
         }
      }

      private void cleanupContextWrappers() {
         long start = 0L;
         if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
            CorrelationManager.DEBUG_LOGGER.debug("ContextWrapperCleaner.cleanupContextWrappers");
            start = System.nanoTime();
         }

         if (CorrelationManager.CorrelationWrappers.size() == 0) {
            if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
               CorrelationManager.DEBUG_LOGGER.debug("ContextWrapperCleaner nothing to do, took " + (System.nanoTime() - start) + " nanos");
            }

         } else {
            this.activeThreadIds = new HashSet();
            this.enumerateActiveThreads(this.rootThreadGroup);
            Set wrapperKeys = CorrelationManager.CorrelationWrappers.keySet();
            HashSet deadWrapperEntries = new HashSet(wrapperKeys.size());
            Iterator var5 = wrapperKeys.iterator();

            Long id;
            while(var5.hasNext()) {
               id = (Long)var5.next();
               if (!this.activeThreadIds.contains(id)) {
                  deadWrapperEntries.add(id);
               }
            }

            if (deadWrapperEntries.size() > 0) {
               if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                  CorrelationManager.DEBUG_LOGGER.debug("ContextWrapperCleaner removing " + deadWrapperEntries.size() + " dead entries");
               }

               var5 = deadWrapperEntries.iterator();

               while(var5.hasNext()) {
                  id = (Long)var5.next();
                  if (id != null) {
                     CorrelationManager.CorrelationWrappers.remove(id);
                  }
               }
            } else if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
               CorrelationManager.DEBUG_LOGGER.debug("ContextWrapperCleaner found no dead entries to remove");
            }

            this.activeThreadIds = null;
            if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
               CorrelationManager.DEBUG_LOGGER.debug("ContextWrapperCleaner took " + (System.nanoTime() - start) + " nanos");
            }

         }
      }

      private void enumerateActiveThreads(ThreadGroup group) {
         if (group != null) {
            int activeThreads = group.activeCount();
            int arraySize;
            if (activeThreads > 0) {
               arraySize = activeThreads * 2;
               Thread[] threads = new Thread[arraySize];

               for(activeThreads = group.enumerate(threads, false); activeThreads >= arraySize; activeThreads = group.enumerate(threads, false)) {
                  if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                     CorrelationManager.DEBUG_LOGGER.debug("ContextWrapperCleaner threads array size(" + arraySize + ") not enough (" + activeThreads + "), increasing and retrying");
                  }

                  arraySize = activeThreads * 2;
                  threads = new Thread[arraySize];
               }

               for(int i = 0; i < activeThreads; ++i) {
                  if (threads[i] != null) {
                     this.activeThreadIds.add(new Long(threads[i].getId()));
                  }
               }
            }

            int activeGroups = group.activeGroupCount();
            if (activeGroups > 0) {
               arraySize = activeGroups * 2;
               ThreadGroup[] groups = new ThreadGroup[arraySize];

               for(activeGroups = group.enumerate(groups, false); activeGroups >= arraySize; activeGroups = group.enumerate(groups, false)) {
                  if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                     CorrelationManager.DEBUG_LOGGER.debug("ContextWrapperCleaner thread groups array size(" + arraySize + ") not enough (" + activeGroups + "), increasing and retrying");
                  }

                  arraySize = activeGroups * 2;
                  groups = new ThreadGroup[arraySize];
               }

               for(int i = 0; i < activeGroups; ++i) {
                  this.enumerateActiveThreads(groups[i]);
               }
            }

         }
      }
   }

   private static class ThrottleInfoImpl implements ThrottleInformationEventInfo {
      private float averageEventsPerRequestLastPeriod;
      private int currentThrottleRate;
      private int eventsGeneratedLastPeriod;
      private long lastPeriodDuration;
      private int previousThrottleRate;
      private int projectedSelectedRequestsPerSecBeforeCapCheck;
      private int requestsSeenLastPeriod;
      private int requestsSelectedLastPeriod;
      private int periodsSinceLastThrottleChange;

      private ThrottleInfoImpl() {
      }

      public float getAverageEventsPerRequestLastPeriod() {
         return this.averageEventsPerRequestLastPeriod;
      }

      public int getCurrentThrottleRate() {
         return this.currentThrottleRate;
      }

      public int getEventsGeneratedLastPeriod() {
         return this.eventsGeneratedLastPeriod;
      }

      public long getLastPeriodDuration() {
         return this.lastPeriodDuration;
      }

      public int getPeriodsSinceLastThrottleChange() {
         return this.periodsSinceLastThrottleChange;
      }

      public int getPreviousThrottleRate() {
         return this.previousThrottleRate;
      }

      public int getProjectedSelectedRequestsPerSecBeforeCapCheck() {
         return this.projectedSelectedRequestsPerSecBeforeCapCheck;
      }

      public int getRequestsSeenLastPeriod() {
         return this.requestsSeenLastPeriod;
      }

      public int getRequestsSelectedLastPeriod() {
         return this.requestsSelectedLastPeriod;
      }

      public void setAverageEventsPerRequestLastPeriod(float averageEventsPerRequestLastPeriod) {
         this.averageEventsPerRequestLastPeriod = averageEventsPerRequestLastPeriod;
      }

      public void setCurrentThrottleRate(int currentThrottleRate) {
         this.currentThrottleRate = currentThrottleRate;
      }

      public void setEventsGeneratedLastPeriod(int eventsGeneratedLastPeriod) {
         this.eventsGeneratedLastPeriod = eventsGeneratedLastPeriod;
      }

      public void setLastPeriodDuration(long lastPeriodDuration) {
         this.lastPeriodDuration = lastPeriodDuration;
      }

      public void setPeriodsSinceLastThrottleChange(int periodsSinceLastThrottleChange) {
         this.periodsSinceLastThrottleChange = periodsSinceLastThrottleChange;
      }

      public void setPreviousThrottleRate(int previousThrottleRate) {
         this.previousThrottleRate = previousThrottleRate;
      }

      public void setProjectedSelectedRequestsPerSecBeforeCapCheck(int projectedSelectedRequestsPerSecBeforeCapCheck) {
         this.projectedSelectedRequestsPerSecBeforeCapCheck = projectedSelectedRequestsPerSecBeforeCapCheck;
      }

      public void setRequestsSeenLastPeriod(int requestsSeenLastPeriod) {
         this.requestsSeenLastPeriod = requestsSeenLastPeriod;
      }

      public void setRequestsSelectedLastPeriod(int requestsSelectedLastPeriod) {
         this.requestsSelectedLastPeriod = requestsSelectedLastPeriod;
      }

      // $FF: synthetic method
      ThrottleInfoImpl(Object x0) {
         this();
      }
   }

   private static final class WLSCorrelationFactoryImpl implements CorrelationFactory.Factory {
      private CorrelationManager manager;

      private WLSCorrelationFactoryImpl() {
         this.manager = null;
      }

      private WLSCorrelationFactoryImpl(CorrelationManager manager) {
         this.manager = null;
         this.manager = manager;
      }

      public Correlation findOrCreateCorrelation(final boolean enabled) {
         Correlation ctx = null;
         final CorrelationWrapper wrapper = (CorrelationWrapper)CorrelationManager.localCorrelation.get();
         ctx = wrapper.getCorrelation();
         if (ctx != null) {
            return ctx;
         } else {
            try {
               ctx = (Correlation)SecurityServiceManager.runAs(CorrelationManager.kernelId, CorrelationManager.kernelId, new PrivilegedExceptionAction() {
                  public Object run() throws Exception {
                     WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
                     CorrelationImpl diagCntx = WLSCorrelationFactoryImpl.this.findCorrelation(map, false);
                     if (diagCntx == null && enabled) {
                        diagCntx = new CorrelationImpl();
                        if (wrapper.localContextNonInheritable()) {
                           diagCntx.setInheritable(false);
                        }

                        int propagationMode = CorrelationFactory.getPropagationMode();
                        map.put("oracle.dms.context.internal.wls.WLSContextFamily", CorrelationImpl.getWorkContext(diagCntx), propagationMode);
                     }

                     wrapper.setCorrelation(diagCntx);
                     if (diagCntx != null && wrapper.isSelected()) {
                        long dyeVector = diagCntx.getDyeVector();
                        diagCntx.setDyeVector(dyeVector | 8589934592L);
                     }

                     return diagCntx;
                  }
               });
            } catch (Throwable var5) {
               if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                  var5.printStackTrace();
               }
            }

            return ctx;
         }
      }

      public CorrelationImpl findCorrelation(WorkContextMap map, boolean align) {
         CorrelationImpl correlation = map == null ? null : CorrelationImpl.getCorrelationFromMap(map);
         if (!align) {
            return correlation;
         } else {
            CorrelationWrapper wrapper = (CorrelationWrapper)CorrelationManager.localCorrelation.get();
            wrapper.setCorrelation(correlation);
            return correlation;
         }
      }

      public void invalidateCache() {
         CorrelationWrapper wrapper = (CorrelationWrapper)CorrelationManager.localCorrelation.get();
         wrapper.setCorrelation((Correlation)null);
      }

      public void setJFRThrottled(Correlation ctx) {
         if (ctx != null) {
            long dyeVector = ctx.getDyeVector();
            if ((dyeVector & 8589934592L) != 0L) {
               CorrelationWrapper wrapper = (CorrelationWrapper)CorrelationManager.localCorrelation.get();
               wrapper.setSelected(true);
            }
         }

      }

      public void correlationPropagatedIn(Correlation ctx) {
         if (ctx == null) {
            if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
               JFRDebug.generateDebugEvent("CorrelationManager", "setPropagatedIn() was null", (Throwable)null, CorrelationImpl.getDCDebugContributor("", ""));
            }

         } else {
            CorrelationWrapper wrapper = (CorrelationWrapper)CorrelationManager.localCorrelation.get();
            this.generateECIDMappingEventIfNeeded(wrapper, ctx.getECID());
            wrapper.setCorrelation((Correlation)null);
            long dyeVector = ctx.getDyeVector();
            if ((dyeVector & 8589934592L) != 0L) {
               wrapper.setSelected(true);
            }

         }
      }

      private void updateReconciledCorrelationOnWrapper(Correlation ctx) {
         if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
            JFRDebug.generateDebugEvent("CorrelationManager", "updateReconciledCorrelationOnWrapper", (Throwable)null, CorrelationImpl.getDCDebugContributor(ctx == null ? null : ctx.getECID(), ctx == null ? null : ctx.getRID()));
         }

         CorrelationWrapper wrapper = (CorrelationWrapper)CorrelationManager.localCorrelation.get();
         wrapper.setCorrelation(ctx);
         if (!wrapper.isSelected()) {
            long dyeVector = ctx.getDyeVector();
            if ((dyeVector & 8589934592L) != 0L) {
               wrapper.setSelected(true);
            }
         }

      }

      private void generateECIDMappingEventIfNeeded(CorrelationWrapper wrapper, String newECID) {
         if (wrapper.selected && wrapper.jfrGenerationRequested && DataGatheringManager.isGatheringEnabled() && DataGatheringManager.getDiagnosticVolume() != 0) {
            CorrelationImpl oldCtx = (CorrelationImpl)wrapper.getCorrelation();
            if (oldCtx != null && !oldCtx.isUnmarshalled()) {
               try {
                  (new ECIDMappingEvent(oldCtx.getECID(), newECID)).commit();
               } catch (Throwable var5) {
                  if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                     var5.printStackTrace();
                  }
               }

            }
         }
      }

      public void updateCorrelation(final Correlation ctx) {
         if (ctx != null) {
            try {
               SecurityServiceManager.runAs(CorrelationManager.kernelId, CorrelationManager.kernelId, new PrivilegedExceptionAction() {
                  public Object run() throws Exception {
                     WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
                     Correlation currentCtx = CorrelationImpl.getCorrelationFromMap(map);
                     if (currentCtx != null && currentCtx != ctx) {
                        return null;
                     } else {
                        WLSCorrelationFactoryImpl.this.setCorrelation(ctx, map, (CorrelationImpl)currentCtx, false);
                        return null;
                     }
                  }
               });
            } catch (Throwable var3) {
               if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                  var3.printStackTrace();
               }
            }

         }
      }

      public void setCorrelation(final Correlation ctx) {
         if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
            JFRDebug.generateDebugEvent("CorrelationManager", "setCorrelation(ctx)", (Throwable)null, CorrelationImpl.getDCDebugContributor(ctx == null ? null : ctx.getECID(), ctx == null ? null : ctx.getRID()));
         }

         try {
            SecurityServiceManager.runAs(CorrelationManager.kernelId, CorrelationManager.kernelId, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
                  WLSCorrelationFactoryImpl.this.setCorrelation(ctx, map, (CorrelationImpl)null, false);
                  return null;
               }
            });
         } catch (Throwable var3) {
            if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
               var3.printStackTrace();
            }
         }

      }

      private void setCorrelation(Correlation ctx, WorkContextMap map, CorrelationImpl oldCorrelation, boolean generateECIDMappingEvent) {
         if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
            JFRDebug.generateDebugEvent("CorrelationManager", "setCorrelation(ctx,map)", (Throwable)null, CorrelationImpl.getDCDebugContributor(ctx == null ? null : ctx.getECID(), ctx == null ? null : ctx.getRID()));
         }

         CorrelationWrapper wrapper = (CorrelationWrapper)CorrelationManager.localCorrelation.get();
         CorrelationImpl previousCorrelation = oldCorrelation == null ? CorrelationImpl.getCorrelationFromMap(map) : oldCorrelation;
         if (ctx == null) {
            if (previousCorrelation != null) {
               try {
                  map.remove("oracle.dms.context.internal.wls.WLSContextFamily");
               } catch (NoWorkContextException var10) {
               } catch (PropertyReadOnlyException var11) {
                  if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                     CorrelationManager.DEBUG_LOGGER.debug("Failure removing Correlation from the Map", var11);
                  }
               }
            }
         } else {
            try {
               map.put("oracle.dms.context.internal.wls.WLSContextFamily", CorrelationImpl.getWorkContext(ctx), ctx.getInheritable() ? CorrelationFactory.getPropagationMode() : CorrelationFactory.getNonInheritablePropagationMode());
            } catch (PropertyReadOnlyException var8) {
               if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                  CorrelationManager.DEBUG_LOGGER.debug("Failure adding Correlation to the Map", var8);
               }
            } catch (IOException var9) {
               if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                  CorrelationManager.DEBUG_LOGGER.debug("Failure adding Correlation to the Map", var9);
               }
            }
         }

         if (this.manager.dmsCallback != null && previousCorrelation != null && previousCorrelation != ctx) {
            this.manager.dmsCallback.onDeactivateCorrelation(previousCorrelation);
         }

         wrapper.setCorrelation(ctx);
         if (generateECIDMappingEvent && previousCorrelation != null && !previousCorrelation.isUnmarshalled()) {
            this.generateECIDMappingEventIfNeeded(wrapper, previousCorrelation.getECID());
         }

      }

      public void handleLocalContextAsNonInheritable() {
         CorrelationWrapper wrapper = (CorrelationWrapper)CorrelationManager.localCorrelation.get();
         wrapper.handleLocalContextAsNonInheritable();
      }

      // $FF: synthetic method
      WLSCorrelationFactoryImpl(CorrelationManager x0, Object x1) {
         this(x0);
      }
   }

   private static final class CorrelationWrapper {
      private boolean jfrGenerationRequested;
      private boolean selected;
      private Correlation ctx;
      private boolean mapCheckNeeded;
      private CorrelationManager manager;
      private boolean handleLocalContextAsNonInheritable;

      private CorrelationWrapper() {
         this.jfrGenerationRequested = false;
         this.mapCheckNeeded = true;
         this.manager = CorrelationManager.getCorrelationManagerInternal();
         this.handleLocalContextAsNonInheritable = false;
      }

      Correlation getCorrelation() {
         return this.ctx;
      }

      void setCorrelation(Correlation ctx) {
         this.ctx = ctx;
         if (this.mapCheckNeeded) {
            this.mapCheckNeeded = false;
            long id = Thread.currentThread().getId();
            if (!CorrelationManager.CorrelationWrappers.containsKey(id)) {
               if (CorrelationManager.DEBUG_LOGGER.isDebugEnabled()) {
                  CorrelationManager.DEBUG_LOGGER.debug("Lazily added DC Wrapper map for thread id=" + Thread.currentThread().getId() + ", name=" + Thread.currentThread().getName() + ", wrapper=" + this);
               }

               CorrelationManager.CorrelationWrappers.put(id, new WeakReference(this));
            }
         }

      }

      boolean isSelected() {
         return this.selected;
      }

      void clearMapCheckNeeded() {
         this.mapCheckNeeded = false;
      }

      void jfrGenerationRequested() {
         this.jfrGenerationRequested = true;
      }

      void setSelected(boolean selected) {
         if (selected) {
            CorrelationManager.requestSeqId.set(0);
            CorrelationManager.requestSelectedInPeriod++;
         }

         this.selected = selected;
      }

      void reset() {
         if (this.ctx != null && this.manager.dmsCallback != null) {
            this.manager.dmsCallback.onDeactivateCorrelation(this.ctx);
         }

         this.ctx = null;
         if (!CorrelationManager.needToTrackNonJFRWork || this.jfrGenerationRequested) {
            this.jfrGenerationRequested = false;
            int ctxThrottleRateLocal = CorrelationManager.ctxThrottleRate;
            if (ctxThrottleRateLocal <= 1) {
               this.selected = true;
               CorrelationManager.requestTotalInPeriod++;
               CorrelationManager.requestSelectedInPeriod++;
            } else {
               this.selected = false;
               int seq = CorrelationManager.requestSeqId.incrementAndGet();
               CorrelationManager.requestTotalInPeriod++;
               if (seq >= ctxThrottleRateLocal) {
                  if (seq == ctxThrottleRateLocal) {
                     this.setSelected(true);
                  } else {
                     CorrelationManager.requestSeqId.set(0);
                  }
               }

            }
         }
      }

      void handleLocalContextAsNonInheritable() {
         this.handleLocalContextAsNonInheritable = true;
      }

      boolean localContextNonInheritable() {
         return this.handleLocalContextAsNonInheritable;
      }

      // $FF: synthetic method
      CorrelationWrapper(Object x0) {
         this();
      }
   }
}
