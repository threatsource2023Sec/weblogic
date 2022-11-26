package weblogic.diagnostics.instrumentation.support;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpSession;
import weblogic.common.internal.PassivationUtils;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.diagnostics.context.DiagnosticContextConstants;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitorControl;
import weblogic.diagnostics.instrumentation.DynamicJoinPointImpl;
import weblogic.diagnostics.instrumentation.EventQueue;
import weblogic.diagnostics.instrumentation.InstrumentationConstants;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InstrumentationEvent;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.MonitorLocalHolder;
import weblogic.diagnostics.instrumentation.rtsupport.InstrumentationSupportImpl;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

public final class HttpSessionDebugMonitorSupport implements DiagnosticContextConstants, InstrumentationConstants {
   public static void preProcess(LocalHolder holder) {
      MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
      preProcess((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.monitor);
   }

   public static void preProcess(JoinPoint jp, DiagnosticMonitor mon) {
      if (mon instanceof DiagnosticMonitorControl) {
         DiagnosticMonitorControl monitor = (DiagnosticMonitorControl)mon;
         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("HttpSessionDebugMonitorSupport.preProcess for " + monitor.getType());
         }

         Correlation context = CorrelationFactory.findOrCreateCorrelation();
         if (InstrumentationSupportImpl.dyeMatches(monitor, context)) {
            try {
               if (jp instanceof DynamicJoinPointImpl) {
                  DynamicJoinPointImpl dynamicJp = (DynamicJoinPointImpl)jp;
                  dynamicJp.setMonitorType(mon.getType());
                  Object[] args = dynamicJp.getArguments();
                  dynamicJp.setMonitorType((String)null);
                  Object thisArg = args != null && args.length > 0 ? args[0] : null;
                  if (thisArg instanceof HttpSession) {
                     int sessionSize = -1;

                     try {
                        if (thisArg instanceof Serializable) {
                           sessionSize = PassivationUtils.sizeOf(thisArg);
                        }
                     } catch (IOException var9) {
                        sessionSize = -2;
                     }

                     InstrumentationEvent event = createInstrumentationEvent(jp, monitor, "SessionSize-Before", sessionSize);
                     EventQueue.getInstance().enqueue(event);
                  }
               }
            } catch (Throwable var10) {
               UnexpectedExceptionHandler.handle("Unexpected exception in HttpSessionDebugMonitorSupport.preProcess", var10);
            }

         }
      }
   }

   public static void postProcess(LocalHolder holder) {
      MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
      postProcess((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.monitor);
   }

   public static void postProcess(JoinPoint jp, DiagnosticMonitor mon) {
      if (mon instanceof DiagnosticMonitorControl) {
         DiagnosticMonitorControl monitor = (DiagnosticMonitorControl)mon;
         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("HttpSessionDebugMonitorSupport.postProcess for " + monitor.getType());
         }

         Correlation context = CorrelationFactory.findOrCreateCorrelation();
         if (InstrumentationSupportImpl.dyeMatches(monitor, context)) {
            try {
               if (jp instanceof DynamicJoinPointImpl) {
                  DynamicJoinPointImpl dynamicJp = (DynamicJoinPointImpl)jp;
                  dynamicJp.setMonitorType(mon.getType());
                  Object[] args = dynamicJp.getArguments();
                  Object arg = dynamicJp.getReturnValue();
                  dynamicJp.setMonitorType((String)null);
                  jp = dynamicJp.getDelegate();
                  if (!(arg instanceof HttpSession)) {
                     if (args == null && jp instanceof DynamicJoinPointImpl) {
                        dynamicJp = (DynamicJoinPointImpl)jp;
                        dynamicJp.setMonitorType(mon.getType());
                        args = dynamicJp.getArguments();
                        dynamicJp.setMonitorType((String)null);
                     }

                     arg = args != null && args.length > 0 ? args[0] : null;
                  }

                  if (arg instanceof HttpSession) {
                     int sessionSize = -1;

                     try {
                        if (arg instanceof Serializable) {
                           sessionSize = PassivationUtils.sizeOf(arg);
                        }
                     } catch (IOException var9) {
                        sessionSize = -2;
                     }

                     InstrumentationEvent event = createInstrumentationEvent(jp, monitor, "SessionSize-After", sessionSize);
                     EventQueue.getInstance().enqueue(event);
                  }
               }
            } catch (Throwable var10) {
               UnexpectedExceptionHandler.handle("Unexpected exception in HttpSessionDebugMonitorSupport.postProcess", var10);
            }

         }
      }
   }

   private static InstrumentationEvent createInstrumentationEvent(JoinPoint jp, DiagnosticMonitorControl monitor, String eventType, int sessionSize) {
      Correlation context = CorrelationFactory.findOrCreateCorrelation();
      InstrumentationEvent event = new InstrumentationEvent(monitor, jp);
      if (context != null) {
         event.setContextId(context.getECID());
      }

      event.setEventType(eventType);
      event.setPayload(new Integer(sessionSize));
      return event;
   }
}
