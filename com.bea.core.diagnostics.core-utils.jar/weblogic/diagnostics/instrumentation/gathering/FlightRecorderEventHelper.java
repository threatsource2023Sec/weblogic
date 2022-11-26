package weblogic.diagnostics.instrumentation.gathering;

import java.security.AccessController;
import java.security.Principal;
import java.util.Iterator;
import javax.security.auth.Subject;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.diagnostics.context.CorrelationManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderBaseEvent;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.LogBaseEvent;
import weblogic.diagnostics.flightrecorder.event.GlobalInformationEventInfo;
import weblogic.diagnostics.flightrecorder.event.GlobalInformationEventInfoHelper;
import weblogic.diagnostics.flightrecorder.event.ThrottleInformationEventInfo;
import weblogic.diagnostics.flightrecorder.event.ThrottleInformationEventInfoHelper;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;
import weblogic.diagnostics.instrumentation.GatheredArgument;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.Kernel;
import weblogic.security.Security;
import weblogic.security.SubjectUtils;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.spi.WLSUser;
import weblogic.transaction.TxHelper;

public class FlightRecorderEventHelper {
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   private static final DebugLogger diagnosticContextDebugLogger = DebugLogger.getDebugLogger("DebugDiagnosticContext");
   private static FlightRecorderEventHelper SINGLETON = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ComponentInvocationContextManager compInvCtxMgr = ComponentInvocationContextManager.getInstance();
   private WLLog4jLogEventClassHelper wlLog4jLogEventClassHelper;

   private FlightRecorderEventHelper() {
      this.initialize();
   }

   private void initialize() {
   }

   public static FlightRecorderEventHelper getInstance() {
      if (SINGLETON == null) {
         Class var0 = FlightRecorderEventHelper.class;
         synchronized(FlightRecorderEventHelper.class) {
            if (SINGLETON == null) {
               SINGLETON = new FlightRecorderEventHelper();
            }
         }
      }

      return SINGLETON;
   }

   public void recordStatelessEvent(DiagnosticMonitor monitor, JoinPoint jp) {
      int volume = DataGatheringManager.getDiagnosticVolume();
      FlightRecorderEvent event = this.getInstantEventInstance(volume, monitor);
      if (event != null) {
         boolean handled = false;
         if (event.isBaseEvent()) {
            FlightRecorderBaseEvent baseEvent = (FlightRecorderBaseEvent)event;
            handled = true;
            this.populateBaseInstantEvent(monitor, jp, baseEvent);
            if (!baseEvent.getThrottled()) {
               CorrelationManager.incrementJFREventCounter();
               baseEvent.callCommit();
            }
         } else {
            Object retVal;
            DynamicJoinPoint djp;
            if (event.isThrottleInformationEvent()) {
               if (jp instanceof DynamicJoinPoint) {
                  djp = (DynamicJoinPoint)jp;
                  retVal = djp.getReturnValue();
                  if (retVal != null) {
                     ThrottleInformationEventInfoHelper.populateExtensions(retVal, (ThrottleInformationEventInfo)event);
                     CorrelationManager.incrementJFREventCounter();
                     event.callCommit();
                  }
               }

            } else {
               if (event.isLoggingEvent()) {
                  if (this.wlLog4jLogEventClassHelper == null) {
                     this.wlLog4jLogEventClassHelper = WLLog4jLogEventClassHelper.getInstance();
                  }

                  Object firstArg = this.getStaticFirstArg(jp);
                  if (firstArg != null && this.wlLog4jLogEventClassHelper.isAvailable(firstArg) && this.wlLog4jLogEventClassHelper.isInstance(firstArg.getClass())) {
                     FlightRecorderEvent wlLogEvent = this.wlLog4jLogEventClassHelper.populateWLLogRecordEvent(firstArg);
                     CorrelationManager.incrementJFREventCounter();
                     wlLogEvent.callCommit();
                     return;
                  }
               }

               if (!event.isWLLogRecordEvent() && !event.isLogRecordEvent() && !event.isLoggingEvent()) {
                  if (event.isGlobalInformationEvent()) {
                     if (jp instanceof DynamicJoinPoint) {
                        djp = (DynamicJoinPoint)jp;
                        retVal = djp.getReturnValue();
                        if (retVal != null) {
                           GlobalInformationEventInfoHelper.populateExtensions(retVal, (GlobalInformationEventInfo)event);
                           CorrelationManager.incrementJFREventCounter();
                           event.callCommit();
                        }
                     }

                  }
               } else {
                  LogBaseEvent wlLogEvent = (LogBaseEvent)event;
                  wlLogEvent.initialize(this.getStaticFirstArg(jp));
                  CorrelationManager.incrementJFREventCounter();
                  event.callCommit();
               }
            }
         }
      }
   }

   private FlightRecorderEvent getInstantEventInstance(int volume, DiagnosticMonitor monitor) {
      FlightRecorderEvent event = (FlightRecorderEvent)this.getEventClassInstance(monitor, FlightRecorderEvent.class);
      if (event == null) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("No event class found for monitor: " + monitor.getName());
         }

         return event;
      } else {
         if (!event.callShouldWrite()) {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug("Event should not be written: " + event);
            }

            event = null;
         }

         return event;
      }
   }

   private void populateBaseInstantEvent(DiagnosticMonitor monitor, JoinPoint jp, FlightRecorderBaseEvent event) {
      event.setClassName(jp.getClassName());
      event.setMethodName(jp.getMethodName());
      if (event.isECIDEnabled()) {
         Correlation context = CorrelationFactory.findOrCreateCorrelation(true);
         if (context != null) {
            event.setECID(context.getECID());
            event.setRID(context.getRID());
         }
      }

      if (compInvCtxMgr != null) {
         ComponentInvocationContext ctx = compInvCtxMgr.getCurrentComponentInvocationContext();
         if (ctx != null) {
            event.setPartitionName(ctx.getPartitionName());
            event.setPartitionId(ctx.getPartitionId());
         }
      }

      if (diagnosticContextDebugLogger.isDebugEnabled() && !CorrelationManager.isJFRThrottled()) {
         diagnosticContextDebugLogger.debug("Event generated for a throttled request", new Exception());
      }

      if (Kernel.isInitialized()) {
         event.setTransactionID(TxHelper.getTransactionId());
         if (DataGatheringManager.getDiagnosticVolume() >= 2) {
            Subject currentSubject = Security.getCurrentSubject();
            event.setUserID(this.extractCurrentWLSSubject(currentSubject));
            if (event.getUserID() == null) {
               event.setUserID(SubjectUtils.getUsername(currentSubject));
            }
         }
      }

      DynamicJoinPoint djp = null;
      if (jp instanceof DynamicJoinPoint) {
         djp = (DynamicJoinPoint)jp;
      }

      Object retVal = null;
      if (djp != null && djp.isReturnGathered()) {
         retVal = djp.getReturnValue();
         event.setReturnValue(retVal == null ? null : retVal.toString());
      }

      GatheredArgument[] gatheredArgs = djp == null ? null : djp.getGatheredArguments();
      if (retVal != null || gatheredArgs != null) {
         event.populateExtensions(retVal, gatheredArgs == null ? null : djp.getArguments(), djp, true);
      }

   }

   public FlightRecorderBaseEvent getTimedEvent(DiagnosticMonitor monitor, JoinPoint jp) {
      int volume = DataGatheringManager.getDiagnosticVolume();
      if (volume == 0) {
         return null;
      } else {
         FlightRecorderBaseEvent event = this.getTimedEventInstance(volume, monitor);
         if (event == null) {
            return null;
         } else {
            if (event.isEventTimed()) {
               this.populateBaseTimedEventBefore(monitor, jp, event);
               event.generateInFlight();
            }

            return event;
         }
      }
   }

   private FlightRecorderBaseEvent getTimedEventInstance(int volume, DiagnosticMonitor monitor) {
      FlightRecorderBaseEvent event = (FlightRecorderBaseEvent)this.getEventClassInstance(monitor, FlightRecorderBaseEvent.class);
      if (event == null) {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug("No event class found for monitor: " + monitor.getName());
         }

         return event;
      } else {
         if (event != null && !event.callIsEnabled()) {
            event = null;
         }

         return event;
      }
   }

   public void recordTimedEvent(DiagnosticMonitor monitor, JoinPoint jp, FlightRecorderBaseEvent baseEvent) {
      if (jp instanceof DynamicJoinPoint) {
         DynamicJoinPoint dp = (DynamicJoinPoint)jp;
         if (baseEvent.getThrottled()) {
            return;
         }

         if (dp.isReturnGathered()) {
            Object retVal = dp.getReturnValue();
            if (retVal == null) {
               baseEvent.setReturnValue((String)null);
            } else {
               baseEvent.setReturnValue(retVal.toString());
               baseEvent.populateExtensions(retVal, (Object[])null, dp, true);
            }
         } else if (baseEvent.requiresProcessingArgsAfter()) {
            baseEvent.populateExtensions((Object)null, (Object[])null, dp, true);
         }
      }

      if (!baseEvent.getThrottled()) {
         this.populateBaseTimedEventAfter(monitor, jp, baseEvent);
         CorrelationManager.incrementJFREventCounter();
         baseEvent.callCommit();
      }
   }

   private void populateBaseTimedEventAfter(DiagnosticMonitor monitor, JoinPoint jp, FlightRecorderBaseEvent event) {
      if (event.isECIDEnabled()) {
         Correlation context = CorrelationFactory.findOrCreateCorrelation(true);
         if (context != null) {
            event.setECID(context.getECID());
            event.setRID(context.getRID());
         }
      }

   }

   private void populateBaseTimedEventBefore(DiagnosticMonitor monitor, JoinPoint jp, FlightRecorderBaseEvent event) {
      event.setClassName(jp.getClassName());
      event.setMethodName(jp.getMethodName());
      if (event.willGenerateInFlight() && event.isECIDEnabled()) {
         Correlation context = CorrelationFactory.findOrCreateCorrelation(true);
         if (context != null) {
            event.setECID(context.getECID());
            event.setRID(context.getRID());
         }
      }

      if (compInvCtxMgr != null) {
         ComponentInvocationContext ctx = compInvCtxMgr.getCurrentComponentInvocationContext();
         if (ctx != null) {
            event.setPartitionName(ctx.getPartitionName());
            event.setPartitionId(ctx.getPartitionId());
         }
      }

      if (diagnosticContextDebugLogger.isDebugEnabled() && !CorrelationManager.isJFRThrottled()) {
         diagnosticContextDebugLogger.debug("Event generated for a throttled request", new Exception());
      }

      if (Kernel.isInitialized()) {
         event.setTransactionID(TxHelper.getTransactionId());
         if (DataGatheringManager.getDiagnosticVolume() >= 2) {
            Subject currentSubject = Security.getCurrentSubject();
            event.setUserID(this.extractCurrentWLSSubject(currentSubject));
            if (event.getUserID() == null) {
               event.setUserID(SubjectUtils.getUsername(currentSubject));
            }
         }
      }

      if (jp instanceof DynamicJoinPoint) {
         DynamicJoinPoint djp = (DynamicJoinPoint)jp;
         GatheredArgument[] gatheredArgs = djp.getGatheredArguments();
         if (gatheredArgs != null) {
            event.populateExtensions((Object)null, djp.getArguments(), djp, false);
         }
      }
   }

   private Object getEventClassInstance(DiagnosticMonitor monitor, Class eventClassType) {
      Object eventInstance = null;
      Class eventClass = monitor.getEventClass();
      if (eventClass != null && eventClassType.isAssignableFrom(eventClass)) {
         try {
            eventInstance = eventClassType.cast(eventClass.newInstance());
         } catch (Exception var6) {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug("getEventClassInstance failed to get instance of " + eventClass, var6);
            }
         }

         return eventInstance;
      } else {
         return null;
      }
   }

   private Object getStaticFirstArg(JoinPoint jp) {
      if (!(jp instanceof DynamicJoinPoint)) {
         return null;
      } else {
         DynamicJoinPoint dynJp = (DynamicJoinPoint)jp;
         Object[] args = dynJp.getArguments();
         return args != null && args.length != 0 ? args[0] : null;
      }
   }

   private String extractCurrentWLSSubject(Subject currentSubject) {
      String username = null;
      int numPrincipals = currentSubject.getPrincipals().size();
      if (numPrincipals > 0) {
         Iterator principalsIt = currentSubject.getPrincipals().iterator();

         while(principalsIt.hasNext()) {
            Principal next = (Principal)principalsIt.next();
            if (next instanceof WLSUser) {
               username = next.getName();
               break;
            }
         }
      } else {
         username = WLSPrincipals.getAnonymousUsername();
      }

      return username;
   }
}
