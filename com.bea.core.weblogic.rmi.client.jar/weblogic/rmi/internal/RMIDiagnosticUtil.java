package weblogic.rmi.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.platform.VM;
import weblogic.workarea.LongWorkContext;
import weblogic.workarea.PrimitiveContextFactory;
import weblogic.workarea.PropertyReadOnlyException;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;

public class RMIDiagnosticUtil {
   private static Timer THE_ONE;
   private static boolean timerInitialized = false;
   private static final int interval = 30000;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugRMIRequestPerf");

   private RMIDiagnosticUtil() {
   }

   static boolean isStarted() {
      return THE_ONE != null;
   }

   private static void initializeTimerIfNeeded() {
      if (!timerInitialized) {
         Class var0 = RMIDiagnosticUtil.class;
         synchronized(RMIDiagnosticUtil.class) {
            if (!timerInitialized) {
               if (KernelStatus.isServer() && debugLogger.isDebugEnabled()) {
                  startObserver();
               }

               timerInitialized = true;
            }
         }
      }

   }

   static synchronized void startObserver() {
      if (!isStarted()) {
         try {
            THE_ONE = new Timer("RMI diagnostic timer", true);
            THE_ONE.schedule(new TimerTask() {
               public void run() {
                  if (TimeoutChecker.isThereTimedOutThreads()) {
                     RMIDiagnosticUtil.logThreadDump();
                  }

               }
            }, 30000L, 30000L);
            debugLogger.debug("RMIDiagnosticUtil.startObserver scheduled diag TimerTask.");
         } catch (Throwable var1) {
            debugLogger.debug("RMIDiagnosticUtil.startObserver failed to schedule diag TimerTask. : " + var1);
         }

      }
   }

   public static void logThreadDump() {
      debugLogger.debug("found a thread that reaches client-side read timeout. dump full threads to identify root cause of timeout.");
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      VM.getVM().threadDump(pw);
      pw.close();
      debugLogger.debug(sw.toString());
   }

   public static void setTimeoutToWorkContext(long timeout) {
      if (debugLogger.isDebugEnabled()) {
         if (timeout > 0L) {
            WorkContextMap wcm = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
            WorkContext wcTimeout = PrimitiveContextFactory.create(timeout);

            try {
               wcm.put("rmi.clientTimeout", wcTimeout, 4);
            } catch (PropertyReadOnlyException var5) {
               debugLogger.debug("sending timeout property failed : " + var5);
            }
         }

      }
   }

   public static long getTimeoutFromWorkContext() {
      if (!debugLogger.isDebugEnabled()) {
         return -1L;
      } else {
         initializeTimerIfNeeded();
         WorkContextMap wcm = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
         WorkContext wcTimeout = wcm.get("rmi.clientTimeout");
         if (wcTimeout != null && wcTimeout instanceof LongWorkContext) {
            LongWorkContext lwc = (LongWorkContext)wcTimeout;
            return lwc.longValue();
         } else {
            return -1L;
         }
      }
   }

   public static TimeoutChecker initTimeoutCheckerIfNeeded() {
      return TimeoutChecker.init(getTimeoutFromWorkContext());
   }
}
