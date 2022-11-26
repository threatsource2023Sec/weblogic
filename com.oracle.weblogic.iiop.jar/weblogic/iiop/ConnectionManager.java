package weblogic.iiop;

import java.io.IOException;
import java.io.PrintWriter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.utils.TimeLimitedLogger;
import weblogic.kernel.ExecuteRequest;
import weblogic.kernel.ExecuteThread;
import weblogic.kernel.Kernel;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.io.Chunk;

public final class ConnectionManager {
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private static final int MAX_MESSAGES_PER_MINUTE = 10;
   private static final int SECONDS_PER_MINUTE = 60;
   private static TimeLimitedLogger shutdownErrorLogger;

   public static void dispatch(Connection c, Chunk msg) {
      try {
         EndPoint e = EndPointManager.findOrCreateEndPoint(c);
         e.dispatch(msg);
      } catch (Throwable var3) {
         handleExceptionReceiving(c, var3);
      }

   }

   public static void handleExceptionReceiving(Connection c, Throwable t) {
      if (isIiopDebugEnabled()) {
         IIOPLoggerFacade.logExceptionReceiving(t);
      }

      handleConnectionShutdown(c, t);
   }

   private static boolean isIiopDebugEnabled() {
      return Kernel.DEBUG && debugIIOPDetail.isDebugEnabled();
   }

   static void handleExceptionSending(final Connection c, final IOException ioe) {
      Kernel.execute(new ExecuteRequest() {
         public void execute(ExecuteThread thd) throws Exception {
            ConnectionManager.gotExceptionSending(c, ioe);
         }
      });
   }

   private static void gotExceptionSending(Connection c, IOException ioe) {
      if (isIiopDebugEnabled()) {
         IIOPLoggerFacade.logExceptionSending(ioe);
      }

      handleConnectionShutdown(c, ioe);
   }

   static synchronized void handleConnectionShutdown(Connection c, Throwable t) {
      EndPointManager.forceConnectionShutdown(c, t);
      if (t != null && debugIIOPDetail.isDebugEnabled()) {
         shutdownErrorLogger.log("shutting down " + c + " because <" + StackTraceUtils.throwable2StackTrace(t) + "\n>");
      }

      if (t instanceof Error) {
         if (t instanceof OutOfMemoryError) {
            IIOPLoggerFacade.logOutOfMemory(t);
            throw (OutOfMemoryError)t;
         }

         if (t instanceof ThreadDeath) {
            throw (ThreadDeath)t;
         }
      }

   }

   static {
      shutdownErrorLogger = new TimeLimitedLogger(10, 60L, new PrintWriter(System.out));
      RMIRuntime.getRMIRuntime().addEndPointFinder(new EndPointManager());
   }
}
