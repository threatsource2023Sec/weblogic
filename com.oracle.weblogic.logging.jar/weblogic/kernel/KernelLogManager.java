package weblogic.kernel;

import com.bea.logging.LogBufferHandler;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.diagnostics.debug.KernelDebugService;
import weblogic.logging.ConsoleFormatter;
import weblogic.logging.ConsoleHandler;
import weblogic.logging.FileStreamHandler;
import weblogic.management.configuration.KernelMBean;

public class KernelLogManager {
   private static Logger createClientLogger() {
      try {
         return createClientLogger(new KernelMBeanStub());
      } catch (Throwable var1) {
         var1.printStackTrace(System.out);
         throw var1;
      }
   }

   public static Logger getLogger() {
      return KernelLogManager.LoggerMaker.LOGGER;
   }

   public static void setLogger(Logger l) {
      if (l != null) {
         KernelLogManager.LoggerMaker.LOGGER = l;
      }

   }

   public static void initialize(KernelMBean config) {
      KernelLogManager.LoggerMaker.LOGGER = createServerLogger(config);
   }

   private static Logger createClientLogger(KernelMBean config) {
      Logger ret = Logger.getAnonymousLogger();
      ret.setUseParentHandlers(false);
      ret.setLevel(Level.ALL);
      ConsoleHandler console = new ConsoleHandler(config);
      console.setFormatter(new ConsoleFormatter(config));
      ret.addHandler(console);
      if (config.getLog().getFileName() != null) {
         try {
            FileStreamHandler fileHandler = new FileStreamHandler(config.getLog());
            fileHandler.setFormatter(new ConsoleFormatter(config));
            ret.addHandler(fileHandler);
         } catch (IOException var4) {
            System.err.println("Error opening log file " + config.getLog().getFileName());
         }
      }

      ret.addHandler(LogBufferHandler.getInstance());
      KernelDebugService kdbgSvc = KernelDebugService.getKernelDebugService();
      kdbgSvc.initializeDebugLogging(ret);
      kdbgSvc.initializeDebugParameters(config.getKernelDebug().getDebugParameters());
      return ret;
   }

   private static Logger createServerLogger(KernelMBean config) {
      Logger ret = Logger.getAnonymousLogger();
      ret.setUseParentHandlers(false);
      ret.setLevel(Level.ALL);
      Handler[] handlers = ret.getHandlers();
      if (handlers != null) {
         for(int i = 0; i < handlers.length; ++i) {
            ret.removeHandler(handlers[i]);
         }
      }

      ConsoleHandler console = new ConsoleHandler(config);
      console.setFormatter(new ConsoleFormatter(config));
      ret.addHandler(console);
      ret.addHandler(LogBufferHandler.getInstance());
      return ret;
   }

   private static final class LoggerMaker {
      private static Logger LOGGER = KernelLogManager.createClientLogger();
   }
}
