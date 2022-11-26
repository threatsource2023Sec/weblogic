package weblogic.diagnostics.instrumentation.gathering;

import com.bea.logging.BaseLogRecord;
import com.bea.logging.LogLevel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.List;
import java.util.logging.LogRecord;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderDebugEvent;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory;
import weblogic.diagnostics.flightrecorder.event.DebugEventContributor;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.WLLogRecord;
import weblogic.management.configuration.LogFilterMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.PropertyHelper;

public final class DataGatheringManager implements PropertyChangeListener {
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   private static final boolean DEBUG_DCCREATE_ENABLED = !PropertyHelper.getBoolean("weblogic.diagnostics.instrumentation.gathering.DebugContributorDCFindOnly");
   private static boolean initialized = false;
   private static boolean gatheringEnabledDetermined = false;
   private static boolean gatheringEnabled = true;
   public static final int OFF = 0;
   public static final int LOW = 1;
   public static final int MEDIUM = 2;
   public static final int HIGH = 3;
   public static final int[] MAX_CHUNK_SIZE_MULTIPLE = new int[]{3, 3, 4, 5};
   public static boolean[] ENABLE_STACK_TRACES = new boolean[]{false, false, false, true};
   public static boolean jfrActionsDisabled = false;
   private static ComponentInvocationContextManager compInvCtxMgr = ComponentInvocationContextManager.getInstance();
   private static FlightRecorderManager flightRecorderMgr = Factory.getInstance();
   static boolean constantPoolsEnabled = true;
   private static int diagnosticVolume = 0;
   private static int severity = 64;
   private static WLDFServerDiagnosticMBean wldfConfig;
   private static LogFilterMBean serverLogFileFilterConfig;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static RuntimeAccess runtimeAccess;
   private static DataGatheringManager SINGLETON;
   private Method isGatheringExtended = null;
   private Method updateServerManagedMonitors = null;
   private Object instrumentationManager = null;
   private static final Class[] updateArgs;
   private static WLLog4jLogEventClassHelper wlLog4jLogEventHelper;
   private static JFRHelper jfrHelper;
   static List eventClassNamesInUse;

   private static RuntimeAccess getRuntimeAccess() {
      if (runtimeAccess == null) {
         Class var0 = DataGatheringManager.class;
         synchronized(DataGatheringManager.class) {
            if (runtimeAccess == null) {
               runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
            }
         }
      }

      return runtimeAccess;
   }

   public static void gatherLoggingEvent(Object event, int level) {
      if (diagnosticVolume != 0) {
         if (recordBasedOnSeverity(level)) {
            DataGatheringManager.Helper.recordLoggingEvent(event);
         }

         if (wlLog4jLogEventHelper.isAvailable(event)) {
            if (wlLog4jLogEventHelper.isInstance(event.getClass()) && wlLog4jLogEventHelper.isGatherable(event)) {
               int messageVolume = convertVolume(wlLog4jLogEventHelper.getDiagnosticVolume(event));
               if (diagnosticVolume >= messageVolume) {
                  DataGatheringManager.Helper.recordLoggingEvent(event);
               }
            }

         }
      }
   }

   public static void gatherLogRecord(LogRecord rec) {
      if (diagnosticVolume != 0) {
         if (recordBasedOnSeverity(LogLevel.getSeverity(rec.getLevel()))) {
            if (rec instanceof WLLogRecord) {
               DataGatheringManager.Helper.recordWLLogRecord(rec);
            } else {
               DataGatheringManager.Helper.recordLogRecord(rec);
            }

         } else if (rec instanceof BaseLogRecord) {
            BaseLogRecord baseRec = (BaseLogRecord)rec;
            if (baseRec.isGatherable()) {
               int messageVolume = convertVolume(baseRec.getDiagnosticVolume());
               if (diagnosticVolume >= messageVolume) {
                  if (rec instanceof WLLogRecord) {
                     DataGatheringManager.Helper.recordWLLogRecord(rec);
                  } else {
                     DataGatheringManager.Helper.recordLogRecord(rec);
                  }
               }

            }
         }
      }
   }

   private static boolean recordBasedOnSeverity(int severity) {
      switch (severity) {
         case 1:
         case 2:
         case 4:
            return true;
         case 8:
            if (diagnosticVolume != 1) {
               return true;
            }
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            return false;
      }
   }

   public static synchronized void initialize() {
      if (!initialized) {
         try {
            String enable = System.getProperty("weblogic.diagnostics.instrumentation.gathering.JFRConstantPoolsEnabled", "true");
            if (!enable.equalsIgnoreCase("true")) {
               constantPoolsEnabled = false;
            }

            int stacksEnabledVolume = convertVolume(System.getProperty("weblogic.diagnostics.flightrecorder.StackTracesEnabled", "high"));
            ENABLE_STACK_TRACES[1] = stacksEnabledVolume != 0 && stacksEnabledVolume == 1;
            ENABLE_STACK_TRACES[2] = stacksEnabledVolume != 0 && stacksEnabledVolume != 3;
            ENABLE_STACK_TRACES[3] = stacksEnabledVolume != 0;
            jfrActionsDisabled = Boolean.getBoolean("weblogic.diagnostics.instrumentation.gathering.JFRActionsDisabled");
         } catch (SecurityException var2) {
         }

         wldfConfig = getRuntimeAccess().getServer().getServerDiagnosticConfig();
         diagnosticVolume = convertVolume(wldfConfig.getWLDFDiagnosticVolume());
         jfrHelper = JFRHelper.Factory.getInstance();
         if (flightRecorderMgr.isRecordingPossible()) {
            flightRecorderMgr.setDefaultSettings(MAX_CHUNK_SIZE_MULTIPLE[diagnosticVolume], ENABLE_STACK_TRACES[diagnosticVolume]);
            flightRecorderMgr.setDebugEventContributor(new Contributor());
            jfrHelper.initialize(diagnosticVolume);
         }

         SINGLETON = new DataGatheringManager();
         wldfConfig.addPropertyChangeListener(SINGLETON);
         initialized = true;
      }
   }

   public static synchronized void setEventClassNamesInUse(List names) {
      if (eventClassNamesInUse == null) {
         eventClassNamesInUse = names;
      }

   }

   public static boolean isGatheringEnabled() {
      if (gatheringEnabledDetermined) {
         return gatheringEnabled;
      } else {
         Class var0 = DataGatheringManager.class;
         synchronized(DataGatheringManager.class) {
            if (flightRecorderMgr.isRecordingPossible()) {
               gatheringEnabled = true;
               gatheringEnabledDetermined = true;
            } else {
               if (SINGLETON == null) {
                  initialize();
               }

               gatheringEnabled = SINGLETON.invokeIsGatheringExtended();
               gatheringEnabledDetermined = true;
               if (gatheringEnabled) {
                  jfrActionsDisabled = true;
               }
            }
         }

         return gatheringEnabled;
      }
   }

   public static boolean jfrActionsDisabled() {
      return jfrActionsDisabled;
   }

   public static synchronized void initializeLogging() {
      if (!initialized) {
         initialize();
      }

      if (!isGatheringEnabled()) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Data Gathering is not enabled, no logger handling is registered for it");
         }

      } else {
         wlLog4jLogEventHelper = WLLog4jLogEventClassHelper.getInstance();
         serverLogFileFilterConfig = getRuntimeAccess().getServer().getLog().getLogFileFilter();
         if (serverLogFileFilterConfig != null) {
            severity = serverLogFileFilterConfig.getSeverityLevel();
         }

         registerLogger();
         if (serverLogFileFilterConfig != null) {
            serverLogFileFilterConfig.addPropertyChangeListener(SINGLETON);
         }

      }
   }

   public void propertyChange(PropertyChangeEvent evt) {
      Class var2 = DataGatheringManager.class;
      synchronized(DataGatheringManager.class) {
         int newVolume = convertVolume(wldfConfig.getWLDFDiagnosticVolume());
         if (newVolume != diagnosticVolume) {
            jfrHelper.handlePropertyChange(newVolume);
            diagnosticVolume = newVolume;
            flightRecorderMgr.enableImageRecordingClientEvents("WLDF ", MAX_CHUNK_SIZE_MULTIPLE[diagnosticVolume], ENABLE_STACK_TRACES[diagnosticVolume]);
            this.invokeUpdateServerManagedMonitors();
         }

         if (serverLogFileFilterConfig != null) {
            int newSeverity = serverLogFileFilterConfig.getSeverityLevel();
            if (severity != newSeverity) {
               severity = newSeverity;
               registerLogger();
            }
         }

      }
   }

   private void invokeUpdateServerManagedMonitors() {
      if (this.updateServerManagedMonitors == null) {
         try {
            Class clazz = Class.forName("weblogic.diagnostics.instrumentation.InstrumentationManager");
            Method getMgr = clazz.getDeclaredMethod("getInstrumentationManager", (Class[])null);
            this.instrumentationManager = getMgr.invoke((Object)null, (Object[])null);
            this.updateServerManagedMonitors = clazz.getDeclaredMethod("updateServerManagedMonitors", updateArgs);
         } catch (Exception var4) {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug("Failed to get InstrumentationManager instance", var4);
            }

            return;
         }
      }

      Object[] args = new Object[]{new Integer(diagnosticVolume)};

      try {
         if (debugLog.isDebugEnabled() && !isGatheringEnabled() && diagnosticVolume != 0) {
            debugLog.debug("The diagnostic volume is not off but the server managed monitors will be disabled as gathering is not enabled");
         }

         this.updateServerManagedMonitors.invoke(this.instrumentationManager, args);
      } catch (Exception var3) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to get InstrumentationManager instance", var3);
         }

      }
   }

   private boolean invokeIsGatheringExtended() {
      if (this.isGatheringExtended == null) {
         try {
            Class clazz = Class.forName("weblogic.diagnostics.instrumentation.InstrumentationManager");
            Method getMgr = clazz.getDeclaredMethod("getInstrumentationManager", (Class[])null);
            this.instrumentationManager = getMgr.invoke((Object)null, (Object[])null);
            this.isGatheringExtended = clazz.getDeclaredMethod("isGatheringExtended", (Class[])null);
         } catch (Exception var4) {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug("Failed to get InstrumentationManager instance", var4);
            }

            return true;
         }
      }

      try {
         return (Boolean)this.isGatheringExtended.invoke(this.instrumentationManager, (Object[])null);
      } catch (Exception var3) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("Failed to determine if gathering is extended, assume it is just in case", var3);
         }

         return true;
      }
   }

   public static int convertVolume(String volumeIn) {
      if (volumeIn != null && !volumeIn.equalsIgnoreCase("Off")) {
         if (volumeIn.equalsIgnoreCase("Low")) {
            return 1;
         } else if (volumeIn.equalsIgnoreCase("Medium")) {
            return 2;
         } else {
            return volumeIn.equalsIgnoreCase("High") ? 3 : 0;
         }
      } else {
         return 0;
      }
   }

   public static int getDiagnosticVolume() {
      return diagnosticVolume;
   }

   private static void registerLogger() {
      if (debugLog.isDebugEnabled()) {
         debugLog.debug("registerLogger() called, volume = " + diagnosticVolume + " severity = " + severity);
      }

      DataGatheringLogService.deregisterFromServerLogger();
      if (diagnosticVolume != 0) {
         DataGatheringLogService.registerToServerLogger(severity);
      }

   }

   static {
      updateArgs = new Class[]{Integer.TYPE};
      eventClassNamesInUse = null;
   }

   private static class Contributor implements DebugEventContributor {
      private Contributor() {
      }

      public void contribute(FlightRecorderDebugEvent event) {
         if (event != null) {
            Correlation context = CorrelationFactory.findOrCreateCorrelation(DataGatheringManager.DEBUG_DCCREATE_ENABLED);
            if (context != null) {
               event.setECID(context.getECID());
               event.setRID(context.getRID());
            }

            if (DataGatheringManager.compInvCtxMgr != null) {
               ComponentInvocationContext ctx = DataGatheringManager.compInvCtxMgr.getCurrentComponentInvocationContext();
               if (ctx != null) {
                  event.setPartitionName(ctx.getPartitionName());
                  event.setPartitionId(ctx.getPartitionId());
               }
            }

         }
      }

      public void contributeBefore(FlightRecorderDebugEvent event) {
         if (DataGatheringManager.compInvCtxMgr != null) {
            ComponentInvocationContext ctx = DataGatheringManager.compInvCtxMgr.getCurrentComponentInvocationContext();
            if (ctx != null) {
               event.setPartitionName(ctx.getPartitionName());
               event.setPartitionId(ctx.getPartitionId());
            }
         }

      }

      public void contributeAfter(FlightRecorderDebugEvent event) {
         if (event != null) {
            Correlation context = CorrelationFactory.findOrCreateCorrelation(DataGatheringManager.DEBUG_DCCREATE_ENABLED);
            if (context != null) {
               event.setECID(context.getECID());
               event.setRID(context.getRID());
            }

         }
      }

      // $FF: synthetic method
      Contributor(Object x0) {
         this();
      }
   }

   static class Helper {
      static final long serialVersionUID = -106921881993537274L;
      static final String _WLDF$INST_VERSION = "9.0.0";
      // $FF: synthetic field
      static Class _WLDF$INST_FLD_class = Class.forName("weblogic.diagnostics.instrumentation.gathering.DataGatheringManager$Helper");
      static final DelegatingMonitor _WLDF$INST_FLD_Logging_Event_Diagnostic_Volume_Before_Low;
      static final DelegatingMonitor _WLDF$INST_FLD_Log_Record_Diagnostic_Volume_Before_Low;
      static final DelegatingMonitor _WLDF$INST_FLD_WLLog_Record_Diagnostic_Volume_Before_Low;
      static final JoinPoint _WLDF$INST_JPFLD_0;
      static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
      static final JoinPoint _WLDF$INST_JPFLD_1;
      static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
      static final JoinPoint _WLDF$INST_JPFLD_2;
      static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

      public static void recordLoggingEvent(Object var0) {
         LocalHolder var1;
         if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
            if (var1.argsCapture) {
               var1.args = new Object[1];
               var1.args[0] = var0;
            }

            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
            var1.resetPostBegin();
         }

      }

      public static void recordLogRecord(LogRecord var0) {
         LocalHolder var1;
         if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
            if (var1.argsCapture) {
               var1.args = new Object[1];
               var1.args[0] = var0;
            }

            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
            var1.resetPostBegin();
         }

      }

      public static void recordWLLogRecord(LogRecord var0) {
         LocalHolder var1;
         if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
            if (var1.argsCapture) {
               var1.args = new Object[1];
               var1.args[0] = var0;
            }

            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
            var1.resetPostBegin();
         }

      }

      static {
         _WLDF$INST_FLD_Logging_Event_Diagnostic_Volume_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Logging_Event_Diagnostic_Volume_Before_Low");
         _WLDF$INST_FLD_Log_Record_Diagnostic_Volume_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Log_Record_Diagnostic_Volume_Before_Low");
         _WLDF$INST_FLD_WLLog_Record_Diagnostic_Volume_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "WLLog_Record_Diagnostic_Volume_Before_Low");
         _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataGatheringManager.java", "weblogic.diagnostics.instrumentation.gathering.DataGatheringManager$Helper", "recordLoggingEvent", "(Ljava/lang/Object;)V", 416, "", "", "", InstrumentationSupport.makeMap(new String[]{"Logging_Event_Diagnostic_Volume_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo((String)null, (String)null, false, false)})}), (boolean)1);
         _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Logging_Event_Diagnostic_Volume_Before_Low};
         _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataGatheringManager.java", "weblogic.diagnostics.instrumentation.gathering.DataGatheringManager$Helper", "recordLogRecord", "(Ljava/util/logging/LogRecord;)V", 417, "", "", "", InstrumentationSupport.makeMap(new String[]{"Log_Record_Diagnostic_Volume_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo((String)null, (String)null, false, false)})}), (boolean)1);
         _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Log_Record_Diagnostic_Volume_Before_Low};
         _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataGatheringManager.java", "weblogic.diagnostics.instrumentation.gathering.DataGatheringManager$Helper", "recordWLLogRecord", "(Ljava/util/logging/LogRecord;)V", 418, "", "", "", InstrumentationSupport.makeMap(new String[]{"WLLog_Record_Diagnostic_Volume_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo((String)null, (String)null, false, false)})}), (boolean)1);
         _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_WLLog_Record_Diagnostic_Volume_Before_Low};
      }
   }
}
